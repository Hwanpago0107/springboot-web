package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.domain.Category;
import me.ceskim493.springbootdeveloper.domain.ImgFile;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.domain.Option;
import me.ceskim493.springbootdeveloper.dto.AddItemRequest;
import me.ceskim493.springbootdeveloper.dto.UpdateItemRequest;
import me.ceskim493.springbootdeveloper.repository.CategoryRepository;
import me.ceskim493.springbootdeveloper.repository.ImgFileRepository;
import me.ceskim493.springbootdeveloper.repository.ItemRepository;
import me.ceskim493.springbootdeveloper.repository.OptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ImgFileRepository imgFileRepository;
    private final OptionRepository optionRepository;

    @Transactional
    public Item save(AddItemRequest request, MultipartFile imgFile) throws Exception {
        Item item = new Item();

        if (imgFile != null) {
            item = makeFile(imgFile);
        }

        Category category = new Category();
        try {
            category = categoryRepository.findById(request.getCategory_id()).get();
            item.setCategory(category);
        } catch (Exception e) {
            log.info("no category {}", request.getCategory_id());
        }

        // 상품등록
        item = itemRepository.save(request.toEntity(item));

        // 옵션이 있으면 저장해준다.
        if (request.getOptionNames() != null && request.getOptionNames().length > 0 &&
                request.getOptionValues() != null && request.getOptionValues().length > 0) {
            for (int i = 0; i < request.getOptionValues().length; i++) {
                Option option = new Option();
                option.setItem(item);
                option.setName(request.getOptionNames()[i]);
                option.setValue(request.getOptionValues()[i]);
                optionRepository.save(option);
            }
        }

        return item;
    }

    public Item findById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("unexpected Item"));

        List<Option> options = optionRepository.findAllByItem_Id(id);
        item.setOptions(options);

        return item;
    }

    public List<Item> findByDiscountGreaterThanEqual(Float discount) {
        return itemRepository.findByDiscountGreaterThanEqual(discount);
    }

    public List<Item> findBySaleCountsLimit5() {
        return itemRepository.findBySaleCountsLimit5();
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional
    public Item update(long id, UpdateItemRequest request, MultipartFile imgFile) throws Exception {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found " + id));

        //수정 시 업로드 되는 파일이 있으면 파일도 추가
        if (imgFile != null) {
            Item newItem = makeFile(imgFile);
            item.setFileName(newItem.getFileName());
            item.setFilePath(newItem.getFilePath());
            item.setFileSize(newItem.getFileSize());
        }

        Category category = new Category();
        try {
            category = categoryRepository.findById(request.getCategory_id()).get();
            item.setCategory(category);
        } catch (Exception e) {
            log.info("no category {}", request.getCategory_id());
        }

        item.update(request.getName(), request.getPrice(), request.getStockQuantity(),
                request.getDiscount(), item.getFileName(), item.getFilePath(), item.getFileSize(), item.getCategory(),
                request.getDescription(), request.getDetailImgName(), request.getDetailImgPath());

        //Option은 다 지우고 다시 만들어준다.
        optionRepository.deleteAllByItem_Id(id);
        // 옵션이 있으면 저장해준다.
        if (request.getOptionNames() != null && request.getOptionNames().length > 0 &&
                request.getOptionValues() != null && request.getOptionValues().length > 0) {
            for (int i = 0; i < request.getOptionValues().length; i++) {
                Option option = new Option();
                option.setItem(item);
                option.setName(request.getOptionNames()[i]);
                option.setValue(request.getOptionValues()[i]);
                optionRepository.save(option);
            }
        }

        return item;
    }

    public void delete(long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found " + id));

        optionRepository.deleteAllByItem_Id(id);

        itemRepository.delete(item);
    }

    public Item makeFile(MultipartFile imgFile) throws Exception {
        String originFileName = imgFile.getOriginalFilename();
        String imgName = "";
        Long fileSize = imgFile.getSize();

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/upload/img/";

        // UUID 생성하여 서로 다른 이미지 이름 생성
        UUID uuid = UUID.randomUUID();

        String savedFileName = uuid + "_" + originFileName;

        imgName = savedFileName;

        // 디렉토리 경로 없으면 생성
        File folder = new File(projectPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File saveFile = new File(projectPath, imgName);

        imgFile.transferTo(saveFile);

        Item item = new Item();
        item.setFileName(imgName);
        item.setFilePath("/static/upload/img/" + imgName);
        item.setFileSize(fileSize);

        return item;
    }

    public List<ImgFile> uploadImgFiles(List<MultipartFile> files) throws IOException {
        List<ImgFile> imgFiles = new ArrayList<>();
        // 이미지를 일괄 등록한다.
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String oriImgName = file.getOriginalFilename();

            //파일 업로드
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/upload/img/";

            // 디렉토리 경로 없으면 생성
            File folder = new File(projectPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File saveFile = new File(projectPath, oriImgName);

            file.transferTo(saveFile);

            ImgFile imgFile = new ImgFile();
            imgFile.setFileName(oriImgName);
            imgFile.setFilePath("/static/upload/img/" + oriImgName);

            // 저장할 경로에 이미지 있으면 저장하지 않는다.
            if (imgFileRepository.findImgFileByFilePath(imgFile.getFilePath()) != null) {
                log.info("중복이미지: {}", imgFile.getFilePath());
                continue;
            }

            imgFiles.add(imgFileRepository.save(imgFile));
        }

        return imgFiles;
    }

    public List<ImgFile> findAllImgFiles() {return imgFileRepository.findAll();}
}

package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.dto.AddItemRequest;
import me.ceskim493.springbootdeveloper.dto.UpdateItemRequest;
import me.ceskim493.springbootdeveloper.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public Item save(AddItemRequest request, MultipartFile imgFile) throws Exception {
        Item item = makeFile(imgFile);

        return itemRepository.save(request.toEntity(item));
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("unexpected Item"));
    }
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional
    public Item update(long id, UpdateItemRequest request, MultipartFile imgFile) throws Exception {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found " + id));

        //수정 시 업로드 되는 파일이 있으면 파일도 추가
        System.out.println("FILE TEST====" + imgFile);
        if (imgFile != null) {
            Item newItem = makeFile(imgFile);
            item.setFileName(newItem.getFileName());
            item.setFilePath(newItem.getFilePath());
            item.setFileSize(newItem.getFileSize());
        }

        item.update(request.getName(), request.getPrice(), request.getStockQuantity(), item.getFileName(), item.getFilePath(), item.getFileSize());

        return item;
    }

    public void delete(long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found " + id));

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
}

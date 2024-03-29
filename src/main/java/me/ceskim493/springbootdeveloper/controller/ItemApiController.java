package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.domain.ImgFile;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.dto.AddItemRequest;
import me.ceskim493.springbootdeveloper.dto.ItemResponse;
import me.ceskim493.springbootdeveloper.dto.OptionResponse;
import me.ceskim493.springbootdeveloper.dto.UpdateItemRequest;
import me.ceskim493.springbootdeveloper.service.ItemService;
import me.ceskim493.springbootdeveloper.service.OptionService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

@RequiredArgsConstructor
@RestController // http Response Body에 객체 데이터를 JSON 형식으로 변환하는 컨트롤러
@Slf4j
public class ItemApiController {

    private final ItemService itemService;
    private final OptionService optionService;

    // HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/items")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Item> addItem(@RequestPart(value = "request") AddItemRequest request,
                                        @RequestPart(value = "file", required = false) MultipartFile imgFile,
                                        @RequestPart(value = "file2", required = false) MultipartFile detailImgFile) throws Exception {

        Item savedItem = itemService.save(request, imgFile, detailImgFile);

        // 요청한 자원이 성공적으로 생성되었으며 저장된 아이템 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/api/items")
    public ResponseEntity<List<ItemResponse>> findAllItems() {
        List<ItemResponse> items = itemService.findAll()
                .stream()
                .map(ItemResponse::new)
                .toList();

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/items/{id}")
    public ResponseEntity<Item> update(@PathVariable long id,
                                       @RequestPart(value = "request") UpdateItemRequest request,
                                       @RequestPart(value = "file", required = false) MultipartFile imgFile,
                                       @RequestPart(value = "file2", required = false) MultipartFile detailImgFile) throws Exception {
        Item updatedItem = itemService.update(id, request, imgFile, detailImgFile);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/api/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable long id) {
        itemService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public UrlResource showImage(@PathVariable String filename) throws MalformedURLException {

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/upload/img/";
        // 파일 유무 확인
        File file = new File(projectPath, filename);
        if (!file.exists()) {
            log.info("file is empty");
            return null;
        }

        return new UrlResource("file:" + file.getAbsolutePath());
    }

    @PostMapping("/api/images")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<ImgFile> addImages(@RequestParam("imgFiles") List<MultipartFile> imgFiles) throws Exception {

        List<ImgFile> savedItem = itemService.uploadImgFiles(imgFiles);

        // 요청한 자원이 성공적으로 생성되었으며 저장된 아이템 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/api/options/{id}")
    public ResponseEntity<List<OptionResponse>> getOptions(@PathVariable long id) {
        List<OptionResponse> options = optionService.findAllByItem_id(id).stream()
                        .map(OptionResponse::new)
                        .toList();

        log.info("options: {}", options);

        return ResponseEntity.ok()
                .body(options);
    }
}

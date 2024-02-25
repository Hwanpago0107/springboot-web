package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.dto.BrandResponse;
import me.ceskim493.springbootdeveloper.dto.CategoryResponse;
import me.ceskim493.springbootdeveloper.dto.CreateCategoryRequest;
import me.ceskim493.springbootdeveloper.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController // http Response Body에 객체 데이터를 JSON 형식으로 변환하는 컨트롤러
public class CartegoryApiController {

    private final CategoryService categoryService;

    // HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/categories")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request) throws Exception {

        CategoryResponse categoryResponse = new CategoryResponse(categoryService.save(request));

        // 요청한 자원이 성공적으로 생성되었으며 저장된 아이템 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryResponse);
    }

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryResponse>> findAllCategories() {

        List<CategoryResponse> categories = categoryService.findAll().stream()
                .map(CategoryResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(categories);
    }

    @PutMapping("/api/categories/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable long id,
                                       @RequestBody CreateCategoryRequest request) throws Exception {
        CategoryResponse updatedCate = new CategoryResponse(categoryService.update(id, request));

        return ResponseEntity.ok()
                .body(updatedCate);
    }

    @DeleteMapping("/api/categories/{id}")
    public ResponseEntity<Void> deleteCate(@PathVariable long id) {
        categoryService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/api/categories/{id}")
    public ResponseEntity<List<CategoryResponse>> findChildCategoriesByParent(@PathVariable long id) {

        List<CategoryResponse> categories = categoryService.findChildCategoriesByParent(id).stream()
                .map(CategoryResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(categories);
    }

    @GetMapping("/api/brands/{categoryIds}")
    public ResponseEntity<List<BrandResponse>> findBrandCategoriesByCategories(@PathVariable String categoryIds) {

        List<Long> categoryList = Arrays.stream(categoryIds.split(","))
                .map(l -> Long.valueOf(l))
                .toList();

        List<BrandResponse> categories = categoryService.findBrandCategoriesByCategories(categoryList).stream()
                .map(BrandResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(categories);
    }
}

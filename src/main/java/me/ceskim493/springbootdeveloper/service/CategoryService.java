package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.domain.Category;
import me.ceskim493.springbootdeveloper.dto.CreateCategoryRequest;
import me.ceskim493.springbootdeveloper.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category save(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        // 이미 등록된 부모카테고리가 없으면 넘어간다.
        try {
            Category parent = categoryRepository.findById(request.getParent_id()).get();
            category.setParent(parent);
        } catch (Exception e) {
            log.info("no parent");
        }

        category = categoryRepository.save(category);

        return category;
    }

    @Transactional
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("unexpected Category"));
    }

    @Transactional
    public Category update(long id, CreateCategoryRequest request) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found " + id));

        // 이미 등록된 부모카테고리가 없으면 넘어간다.
        try {
            Category parent = categoryRepository.findById(request.getParent_id()).get();
            category.update(request.getName(), parent);
        } catch (Exception e) {
            category.update(request.getName());
            log.info("no parent");
        }

        return category;
    }

    public void delete(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found " + id));

        categoryRepository.delete(category);
    }
}

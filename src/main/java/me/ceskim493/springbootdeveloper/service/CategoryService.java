package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.domain.Category;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.dto.CreateCategoryRequest;
import me.ceskim493.springbootdeveloper.repository.CategoryRepository;
import me.ceskim493.springbootdeveloper.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

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

    public List<Category> findCategoriesByCategoryIds(List<Long> categories) {
        return categoryRepository.findCategoriesByIdIn(categories);
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

    public List<Item> findItemsByCategory(Long id) {
        Category category = findById(id);
        List<Item> items = new ArrayList<>();
        items.addAll(category.getItems());
        List<Category> categories = findChildsByCategories(category.getChild());

        for (Category cate : categories) {
            items.addAll(cate.getItems());
        }
        return items;
    }

    public List<Category> findParentsCategoryByCategory(Long id) {
        List<Category> categories = new ArrayList<>();
        Category category = findById(id);
        categories.add(category);

        // 부모카테고리를 차례로 순회하면서 가져온다.
        while (category != null) {
            category = categoryRepository.findCategoryByChild(category);
            if (category == null) break;
            categories.add(category);
        }
        return categories;
    }

    public List<Category> findChildsByCategories(List<Category> categories) {
        List<Category> list = new ArrayList<>();

        list.addAll(categories);

        for (Category category : categories) {
            list.addAll(findChildsByCategories(category.getChild()));
        }
        return list;
    }

    public  List<Category> findChildCategoriesByParent(Long id){
        Category category = findById(id);
        return categoryRepository.findCategoriesByParent(category) == null ?
                new ArrayList<Category>() : categoryRepository.findCategoriesByParent(category);
    }

    public List<Category> findCategoriesByDepth(String depth) {
        return categoryRepository.findCategoriesByDepth(depth) == null ?
                new ArrayList<Category>() : categoryRepository.findCategoriesByDepth(depth);
    }

    public List<Item> searchItemsWithCategoryAndText(Long category_id, String searchText) {
        Category category = findById(category_id);
        List<Item> items = new ArrayList<>();
        items.addAll(itemRepository.findAllByCategoryAndAndNameContainsIgnoreCase(category, searchText));
        List<Category> categories = findChildsByCategories(category.getChild());

        for (Category cate : categories) {
            items.addAll(itemRepository.findAllByCategoryAndAndNameContainsIgnoreCase(cate, searchText));
        }
        return items;
    }

    public List<Category> findBrandsByCategory(Long category_id) {
        List<Category> list = new ArrayList<>();
        Category category = findById(category_id);

        if ("3".equals(category.getDepth())) {
            list.add(category);
        }
        List<Category> categories = findChildsByCategories(category.getChild());

        for (Category cate : categories) {
            if ("3".equals(cate.getDepth())) {
                list.add(cate);
            }
        }
        return setItemCountsToCategories(list);
    }

    public List<Category> findCategoriesAndCountByDepth(String depth) {
        if (categoryRepository.findCategoriesByDepth(depth) == null) {
            return new ArrayList<Category>();
        }

        return setItemCountsToCategories(categoryRepository.findCategoriesByDepth(depth));
    }

    // 카테고리 별 하위 카테고리까지 포함한 개수 세기
    private List<Category> setItemCountsToCategories(List<Category> categories) {
        for (int i=0; i < categories.size(); i++) {
            Category category = categories.get(i);
            int count = findItemsByCategory(category.getId()) == null ? 0 : findItemsByCategory(category.getId()).size();
            category.setCount(count);
            categories.set(i, category);
        }

        return categories;
    }

    public List<Item> search(List<Category> categories, String searchText, String sortBy, int priceMin, int priceMax) {
        categories = findChildsByCategories(categories);

        return itemRepository.search(categories, searchText, sortBy, priceMin, priceMax);
    }

    public List<Category> findBrandCategoriesByCategories(List<Long> categories) {
        return setItemCountsToCategories(categoryRepository.findBrandsCategoriesByDepth1(categories));
    }
}

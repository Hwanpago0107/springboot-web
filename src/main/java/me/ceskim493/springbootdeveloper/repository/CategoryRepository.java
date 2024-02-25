package me.ceskim493.springbootdeveloper.repository;

import me.ceskim493.springbootdeveloper.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByChild(Category category);
    List<Category> findCategoriesByParent(Category category);
    List<Category> findCategoriesByDepth(String depth);
    List<Category> findCategoriesByIdIn(List<Long> categories);

    @Query(value = "select c from Category c " +
            "where c.parent in (select c2 from Category c2 " +
            "where c2.parent in (select  c3 from Category c3 where c3.id in (:categories)))")
    List<Category> findBrandsCategoriesByDepth1(@Param("categories") Collection<Long> categories);

    @Query(value = "select c from Category c " +
            "where c.parent in (select c2 from Category c2 " +
            "where c2.id in (:categories))")
    List<Category> findBrandsCategoriesByDepth2(@Param("categories") Collection<Long> categories);
}

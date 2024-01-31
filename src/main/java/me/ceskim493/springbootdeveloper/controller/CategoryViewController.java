package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.Category;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.dto.CategoryViewResponse;
import me.ceskim493.springbootdeveloper.service.CategoryService;
import me.ceskim493.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CategoryViewController {

    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/newCategory")
    public String newItem(@RequestParam(required = false) Long id, Model model, @LoginUser SessionUser user) {
        if (id == null) {
            model.addAttribute("category", new CategoryViewResponse());
        } else {
            Category category = categoryService.findById(id);
            model.addAttribute("category", new CategoryViewResponse(category));
        }

        model.addAttribute("categories", categoryService.findAll());

        model.addAttribute("userName", userService.getSessionUserName(user)); // session에 저장된 유저이름 setting

        return "newCategory";
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        List<CategoryViewResponse> categories = categoryService.findAll().stream()
                .map(CategoryViewResponse::new)
                .toList();

        model.addAttribute("categories", categories);

        return  "categoryList";
    }
}

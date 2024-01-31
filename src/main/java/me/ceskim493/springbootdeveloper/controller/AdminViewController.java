package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminViewController {

    private final CategoryService categoryService;
    
    @GetMapping("/admin")
    public String getAdmin(@LoginUser SessionUser user) {
        return "admin"; // admin.html 뷰 조회
    }

}

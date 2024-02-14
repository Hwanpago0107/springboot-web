package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.ImgFile;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.service.AdminService;
import me.ceskim493.springbootdeveloper.service.ItemService;
import me.ceskim493.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminViewController {

    private final UserService userService;
    private final ItemService itemService;
    private final AdminService adminService;
    
    @GetMapping("/admin")
    public String getAdmin(Model model, @LoginUser SessionUser user) {
        model = adminService.getAdminLayout(model, user);

        return "admin"; // admin.html 뷰 조회
    }

    @GetMapping("/newImages")
    public String newImages(Model model, @LoginUser SessionUser user) {
        model = adminService.getAdminLayout(model, user);

        return "newImages";
    }

    @GetMapping("/images")
    public String getImages(Model model, @LoginUser SessionUser user) {
        model = adminService.getAdminLayout(model, user);
        List<ImgFile> images = itemService.findAllImgFiles();

        model.addAttribute("images", images);

        return "imageList";
    }

}

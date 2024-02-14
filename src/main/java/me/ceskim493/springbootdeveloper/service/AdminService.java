package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class AdminService {

    private final UserService userService;

    public Model getAdminLayout(Model model, SessionUser sUser) {
        // start.adminLayout
        // 로그인한 유저
        String username = userService.getSessionUserName(sUser);
        User user = userService.findByEmail(username);
        // end.adminLayout

        model.addAttribute("userInfo", user); // session에 저장된 유저이름 setting

        return model;
    }
}

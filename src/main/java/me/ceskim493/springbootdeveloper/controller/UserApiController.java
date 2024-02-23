package me.ceskim493.springbootdeveloper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.dto.AddUserRequest;
import me.ceskim493.springbootdeveloper.dto.UserResponse;
import me.ceskim493.springbootdeveloper.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/main";
    }

    @PutMapping("/api/users")
    public ResponseEntity<UserResponse> userInfo(@RequestBody AddUserRequest request) {
        UserResponse updatedUser = new UserResponse(userService.update(request));

        return ResponseEntity.ok()
                .body(updatedUser);
    }

    @PutMapping("/api/users/{id}/{type}")
    public ResponseEntity<Void> registerAdmin(@PathVariable("id") long id, @PathVariable("type") String type) {
        userService.updateUser(id, type);

        return ResponseEntity.ok()
                .build();
    }
}

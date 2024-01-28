package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.dto.AddUserRequest;
import me.ceskim493.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String getSessionUserName(SessionUser user) {
        return user == null ? "Guest" : user.getName();
    }

    @Transactional
    public User update(AddUserRequest request) {
        log.info("request: {}", request);
        User user = findByEmail(request.getEmail());
        log.info("user: {}", user);
        user.update(request.getNickname(), request.getName(), request.getPhone(), request.getAddress());

        return user;
    }
}

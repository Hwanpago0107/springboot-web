package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.domain.EmailMessage;
import me.ceskim493.springbootdeveloper.dto.PostEmailRequest;
import me.ceskim493.springbootdeveloper.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // http Response Body에 객체 데이터를 JSON 형식으로 변환하는 컨트롤러
@Slf4j
public class EmailApiController {

    private final EmailService emailService;

    @PostMapping("/api/mail")
    public ResponseEntity<Void> sendMail(@RequestBody PostEmailRequest request) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(request.getMail())
                .subject("ADMIN 권한 요청 메일입니다. - Hwanpago.shop")
                .build();

        emailService.sendMail(emailMessage);

        return ResponseEntity.ok()
                .build();
    }
}

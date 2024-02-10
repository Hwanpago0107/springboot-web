package me.ceskim493.springbootdeveloper.config.oauth;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        User user = saveOrUpdate(oAuth2User);
        httpSession.setAttribute("user", new SessionUser(user));
        return oAuth2User;
    }

    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String nickname = (String) attributes.get("name");
        String picture = (String) attributes.get("picture");
        User user = userRepository.findByEmail(email)
                .map(entity ->entity.update(nickname, name, picture))
                .orElse(User.builder()
                        .email(email)
                        .name(name)
                        .nickname(name)
                        .picture(picture)
                        .build());

        return userRepository.save(user);
    }
}

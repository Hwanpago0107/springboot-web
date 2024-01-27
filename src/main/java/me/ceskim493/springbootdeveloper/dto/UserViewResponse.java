package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Address;
import me.ceskim493.springbootdeveloper.domain.User;

@NoArgsConstructor
@Getter
public class UserViewResponse {

    private Long id;

    private String email;

    private String nickname;

    private Address address;

    public UserViewResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.address = user.getAddress();
    }
}

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

    private String name;

    private String nickname;

    private String phone;

    private Address address;

    private String picture;

    private String role;

    public UserViewResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.picture = user.getPicture();
        this.role = user.getUserRole();
    }
}

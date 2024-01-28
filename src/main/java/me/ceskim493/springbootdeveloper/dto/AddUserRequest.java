package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.Setter;
import me.ceskim493.springbootdeveloper.domain.Address;

@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private Address address;
}

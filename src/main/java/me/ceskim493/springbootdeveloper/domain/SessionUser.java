package me.ceskim493.springbootdeveloper.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;

    public SessionUser(User user) {
        this.name = user.getEmail();
    }
}

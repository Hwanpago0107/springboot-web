package me.ceskim493.springbootdeveloper.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "게스트"),
    USER("ROLE_USER", "일반사용자"),
    ADMIN("ROLE_ADMIN", "일반관리자"),
    SUPERADMIN("ROLE_SUPER_ADMIN", "슈퍼관리자");

    private final String key;
    private final String title;
}

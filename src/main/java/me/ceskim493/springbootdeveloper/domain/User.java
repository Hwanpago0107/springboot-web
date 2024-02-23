package me.ceskim493.springbootdeveloper.domain;

import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import me.ceskim493.springbootdeveloper.dto.UserViewResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone")
    private String phone;

    @Column(name="picture")
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;

    @Column(name="valid")
    private int isValid;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, String nickname, String picture, Role role, int isValid) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.picture = picture;
        this.role = role;
        this.isValid = isValid;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    // 사용자의 id를 반환(고유의 값)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return this.isValid == 1 ? true : false;
    }

    // 사용자 이름 변경
    public User update(String nickname, String name, String picture) {
        this.nickname = nickname;
        this.name = name;
        this.picture = picture;
        return this;
    }
    public void update(String nickname, String name, String phone, Address address) {
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public UserViewResponse createUserView() {
        return new UserViewResponse(this);
    }

    public static User makeGuestUser(HttpSession httpSession) {
        UUID uuid = UUID.randomUUID();
        User user = User.builder()
                .email(uuid + "@mail.com")
                .name(String.valueOf(uuid))
                .nickname(String.valueOf(uuid))
                .role(Role.GUEST)
                .isValid(0)
                .build();
        httpSession.setAttribute("user", new SessionUser(user));
        return user;
    }

    public String getUserRole() {
        return this.getRole() == null ? "" : this.getRole().getKey();
    }
}

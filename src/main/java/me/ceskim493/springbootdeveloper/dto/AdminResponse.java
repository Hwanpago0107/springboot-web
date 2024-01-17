package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Admin;

@NoArgsConstructor
@Getter
public class AdminResponse {

    private String menuId;

    public AdminResponse(Admin admin) {
        this.menuId = admin.getMenuId();
    }
}

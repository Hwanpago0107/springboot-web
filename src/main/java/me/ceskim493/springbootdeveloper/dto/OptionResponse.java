package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import me.ceskim493.springbootdeveloper.domain.Option;

@Getter
public class OptionResponse {

    private Long id;
    private String name;
    private String val;

    public OptionResponse(Option option) {
        this.id = option.getId();
        this.name = option.getName();
        this.val = option.getValue();
    }
}

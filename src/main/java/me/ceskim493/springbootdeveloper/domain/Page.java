package me.ceskim493.springbootdeveloper.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Page {

    private int pageNumber;
    private int pageLimit;
}

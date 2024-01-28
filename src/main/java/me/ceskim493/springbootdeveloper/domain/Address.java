package me.ceskim493.springbootdeveloper.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String zipcode;
    private String streetAddr;
    private String detailAddr;

    protected Address() {

    }

    public Address(String zipcode, String streetAddr, String detailAddr) {
        this.zipcode = zipcode;
        this.streetAddr = streetAddr;
        this.detailAddr = detailAddr;
    }
}

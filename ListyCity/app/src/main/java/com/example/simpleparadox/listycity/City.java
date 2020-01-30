package com.example.simpleparadox.listycity;

import java.io.Serializable;

public class City implements Serializable {
    private String city;
    private String province;

    public City(String city, String province) {
        this.city =city;
        this.province=province;
    }

    String getCityName() {
        return this.city;
    }

    String getProvinceName() {
        return this.province;
    }
}

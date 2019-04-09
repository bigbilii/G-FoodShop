package me.guoxin.dto;

import me.guoxin.pojo.GfsAddress;

import java.io.Serializable;

public class AddressDTO extends GfsAddress implements Serializable {
    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}

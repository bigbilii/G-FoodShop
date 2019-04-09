package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsCity;

import java.util.List;

public interface CityMapper {
    List<GfsCity> selectByName(String cityName);
}

package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsAddress;

import java.util.List;

public interface AddressMapper {
    int insert(GfsAddress gfsAddress);

    List<GfsAddress> selectByUserId(Long id);

    void delete(Long id);
}

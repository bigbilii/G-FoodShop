package me.guoxin.manager.service;

import me.guoxin.dto.AddressDTO;
import me.guoxin.pojo.GfsAddress;

import java.util.List;

public interface AddressService {
    void insert(AddressDTO addressDTO, Long id);

    List<GfsAddress> selectByUserId(Long id);

    void deleteById(Long id);
}

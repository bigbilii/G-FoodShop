package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper {
    int insert(GfsAddress gfsAddress);

    List<GfsAddress> selectByUserId(@Param("id")Long id);

    void delete(Long id);
}

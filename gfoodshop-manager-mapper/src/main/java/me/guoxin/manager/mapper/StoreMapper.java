package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreMapper {
    List<GfsStore> selectByCityId(@Param("cityId") Long cityId);

    List<GfsStore> selectById(@Param("id") Long id);
}

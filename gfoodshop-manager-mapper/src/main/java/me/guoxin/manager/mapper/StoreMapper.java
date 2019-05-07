package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreMapper {
    List<GfsStore> selectByCityId(@Param("cityId") Long cityId);

    List<GfsStore> selectById(@Param("id") Long id);

    List<GfsStore> selectByIds(List<Long> ids);

    int insert(GfsStore gfsStore);

    List<GfsStore> select(@Param("search") String search);

    int update(GfsStore gfsStore);

    void delete(List<Long> ids);
}

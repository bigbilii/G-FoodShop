package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsCity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface CityMapper {
    /**
     * 查询城市信息
     *
     * @param cityName 城市名字
     * @return
     */
    List<GfsCity> selectByName(String cityName);

    /**
     * 插入城市信息
     *
     * @param gfsCity
     * @return
     */
    int insert(GfsCity gfsCity);

    /**
     * 删除城市信息
     *
     * @param ids
     */
    void delete(@Param("set") Set<Long> ids);
}

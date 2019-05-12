package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsFrequent;
import me.guoxin.pojo.GfsRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RecommendMapper {
    void truncateFrequent();

    void truncateRules();

    void insertFrequent(@Param("content")Map<String, Integer> frequentSetMap);

    void insertRules(@Param("content")Map<String, Double> relationRulesMap);

    List<GfsRule> selectRules();

    List<GfsFrequent> selectFrequent();
}

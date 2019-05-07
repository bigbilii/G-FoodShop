package me.guoxin.manager.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface RecommendMapper {
    void truncateFrequent();

    void truncateRules();

    void insertFrequent(@Param("content")Map<String, Integer> frequentSetMap);

    void insertRules(@Param("content")Map<String, Double> relationRulesMap);
}

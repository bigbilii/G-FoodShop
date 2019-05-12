package me.guoxin.manager.service;

import me.guoxin.pojo.GfsFrequent;
import me.guoxin.pojo.GfsProduct;
import me.guoxin.pojo.GfsRule;

import java.util.List;

public interface RecommendService {
    void makeRecommend();

    List<GfsProduct> getRecommend(Long id);

    List<GfsFrequent> getFrequent();

    List<GfsRule> getRules();
}

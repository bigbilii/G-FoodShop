package me.guoxin.manager.service.impl;

import me.guoxin.fp.FpNode;
import me.guoxin.fp.Fptree;
import me.guoxin.manager.mapper.OrderMapper;
import me.guoxin.manager.mapper.RecommendMapper;
import me.guoxin.manager.service.RecommendService;
import me.guoxin.pojo.GfsOP;
import me.guoxin.utils.Apriori2;
import me.guoxin.utils.RecommendUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Resource
    OrderMapper orderMapper;
    @Resource
    RecommendMapper recommendMapper;

    @Override
    @Transactional
    public void makeRecommend() {
        // 查询全部订单信息
        List<GfsOP> ops = orderMapper.selectOP();
        List<String> dataList = RecommendUtil.getAsMatrix(ops);
        // 产生频繁项集
        Map<String, Integer> frequentSetMap = Apriori2.apriori(dataList);
        Set<String> keySet = frequentSetMap.keySet();
        for(String key:keySet)
        {
            System.out.println(key+" : "+frequentSetMap.get(key));
        }

        System.out.println("=关联规则==========");
        // 产生关联规则
        Map<String, Double> relationRulesMap = Apriori2.getRelationRules(frequentSetMap);
        Set<String> rrKeySet = relationRulesMap.keySet();
        for (String rrKey : rrKeySet)
        {
            System.out.println(rrKey + "  :  " + relationRulesMap.get(rrKey));
        }
        recommendMapper.truncateFrequent();
        recommendMapper.truncateRules();
        recommendMapper.insertFrequent(frequentSetMap);
        recommendMapper.insertRules(relationRulesMap);
    }
}

package me.guoxin.manager.service.impl;

import me.guoxin.manager.mapper.*;
import me.guoxin.manager.service.CartService;
import me.guoxin.manager.service.RecommendService;
import me.guoxin.pojo.*;
import me.guoxin.utils.Apriori2;
import me.guoxin.utils.RecommendUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Resource
    OrderMapper orderMapper;
    @Resource
    CartService cartService;
    @Resource
    RecommendMapper recommendMapper;
    @Resource
    ProductMapper productMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    StoreMapper storeMapper;
    @Resource
    AddressMapper addressMapper;

    @Override
    @Transactional
    public void makeRecommend() {
        // 查询全部订单信息
        List<GfsOP> ops = orderMapper.selectOP();
        List<String> dataList = RecommendUtil.getAsMatrix(ops);
        // 产生频繁项集
        Map<String, Integer> frequentSetMap = Apriori2.apriori(dataList);
        // 产生关联规则
        Map<String, Double> relationRulesMap = Apriori2.getRelationRules(frequentSetMap);
        // 清空表
        recommendMapper.truncateFrequent();
        recommendMapper.truncateRules();
        // 插入频繁项集
        recommendMapper.insertFrequent(frequentSetMap);
        // 插入关联规则
        recommendMapper.insertRules(relationRulesMap);
    }

    @Override
    public List<GfsProduct> getRecommend(Long id) {
        List<GfsProduct> result = new ArrayList<>();
        Set<Long> resultIds = new HashSet<>();
        // 根据自信度排序
        Map<Double, Set<Long>> confidence = new TreeMap<>(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return o1.compareTo(o2);
            }
        });
        // 获取购物车产品列表
        GfsCart cart = cartService.selectByUserId(id);
        Set<Long> cartProductsId = new HashSet<>();
        List<GfsProductCart> products = cart.getProducts();
        for (GfsProductCart product : products) {
            cartProductsId.add(product.getId());
        }
        // 获取关联规则
        List<GfsRule> rules = recommendMapper.selectRules();
        for (GfsRule rule : rules) {
            // 解析规则
            rule.make();
            if (cartProductsId.containsAll(rule.getLeft())) {
                resultIds.addAll(rule.getRight());
                Set<Long> value = confidence.get(rule.getConfidence());
                if (value != null && !value.isEmpty()) {
                    value.addAll(rule.getRight());
                } else {
                    confidence.put(rule.getConfidence(), rule.getRight());
                }
            }
        }
        // 移除已经在购物车里的
        for (Long aLong : cartProductsId) {
            if (resultIds.contains(aLong)) {
                resultIds.remove(aLong);
            }
        }
        // 获取规则下的推荐商品
        if (resultIds.isEmpty()) {
            return result;
        }
        List<GfsProduct> list = productMapper.selectByIds(resultIds);
        Set<Double> keySet = confidence.keySet();
        // 遍历所有规则
        for (Double aDouble : keySet) {
            Set<Long> ids = confidence.get(aDouble);
            // 遍历同一自信度下的id
            for (Long aLong : ids) {
                // 找到id对应的实体
                for (GfsProduct product : list) {
                    if (product.getId().equals(aLong)) {
                        // 添加实体
                        result.add(product);
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<GfsFrequent> getFrequent() {
        List<GfsFrequent> frequent = recommendMapper.selectFrequent();
        return frequent;
    }

    @Override
    public List<GfsRule> getRules() {
        List<GfsRule> rules = recommendMapper.selectRules();
        return rules;
    }

    @Override
    public void makeOrder() {
        List<GfsUser> users = userMapper.listUsers(null);
        List<GfsStore> stores = storeMapper.select(null);
        List<GfsProduct> products = productMapper.select(null);
        Random random = new Random();
        for (int i = 0; i < 5000; i++) {
            GfsOrder gfsOrder = new GfsOrder();

            GfsUser user = users.get(random.nextInt(users.size()));
            GfsStore store = stores.get(random.nextInt(stores.size()));
            List<GfsAddress> addresses = addressMapper.selectByUserId(user.getId());
            GfsAddress address = addresses.get(random.nextInt(addresses.size()));

            int productNum = random.nextInt(4);
            Set<GfsProductOrder> pp = new HashSet<>();
            while (pp.size() <= productNum) {
                GfsProduct ppp = products.get(random.nextInt(products.size()));
                GfsProductOrder po = new GfsProductOrder();
                po.hello(ppp);
                po.setNum(random.nextInt(2));
                po.setOrder(gfsOrder);
                pp.add(po);
            }
            List<GfsProductOrder> orderProduct = new ArrayList<>();
            orderProduct.addAll(pp);


            gfsOrder.setBoxPrice(2);
            double allPrice = 0;
            for (GfsProduct product : orderProduct) {
                allPrice += product.getPrice();
            }
            gfsOrder.setAllPrice(allPrice);
            gfsOrder.setDeliveryPrice(8);
            gfsOrder.setTablewareNum(2);
            gfsOrder.setStatus(random.nextInt(5));
            gfsOrder.setType("货到付款");
            Date date = new Date();
            gfsOrder.setSendTime(date);
            gfsOrder.setArriveTime(date);
            gfsOrder.setCreateTime(date);
            gfsOrder.setUser(user);
            gfsOrder.setAddress(address);
            gfsOrder.setStore(store);
            gfsOrder.setProductOrders(orderProduct);
            if (orderMapper.insertOrder(gfsOrder) != 1) {
                throw new IException("操作失败！");
            }
            if (orderMapper.insertOrderProduct(gfsOrder.getProductOrders()) != gfsOrder.getProductOrders().size()) {
                throw new IException("操作失败！");
            }
        }
    }


}

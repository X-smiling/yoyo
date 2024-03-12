package com.example.yoyo.service.impl;

import com.example.yoyo.mapper.GoodsMapper;
import com.example.yoyo.mapper.ItemMapper;
import com.example.yoyo.mapper.OrderMapper;
import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.Item;
import com.example.yoyo.pojo.Order;
import com.example.yoyo.pojo.User;
import com.example.yoyo.service.OrderService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : hgj
 * @date : 2024/1/9 - 16:47
 * @desc :
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Map<String, Object> addGoodsToCart(int goodsId, Order order, User user) {
        Map<String, Object> result = new HashMap<>();
        // 检查商品库存
        Goods goods = orderMapper.selectByGoodId(goodsId);
        if (goods.getStock() <= 0){
            result.put("code",5020);
            result.put("msg","empty");
            return result;
        }
        // 判断购物车是否存在数据
        if (order == null){
            order = new Order();
            order.setTotal(0.0);
            order.setAmount(0);
            order.setUserId(user.getId());
            order.setUser(user);
            order.setItemList(new ArrayList<>());
        }
        // 把商品添加到购物车中
        List<Item> itemList = order.getItemList();
        boolean isExist = false;
        for (Item item : itemList) {
            if (item.getGoodId() == goodsId){
                item.setAmount(item.getAmount()+1);
                isExist = true;
                break;
            }
        }
        if (!isExist){
            Item item = new Item();
            item.setAmount(1);
            item.setGoods(goods);
            item.setPrice(goods.getPrice());
            item.setGoodId(goodsId);
            itemList.add(item);
        }

        // 处理虚拟订单信息
        // 更新
        order.setAmount(order.getAmount()+1);
        order.setTotal(order.getTotal() + goods.getPrice());

        result.put("code",200);
        result.put("order",order);

        return result;
    }

    @Override
    public Map<String, Object> updateCartInfo(int goodsId, Order order) {
        Map<String, Object> result = new HashMap<>();
        List<Item> itemList = order.getItemList();
        for (Item item : itemList) {
            if (item.getGoodId() == goodsId){
                item.setAmount(item.getAmount()-1);
                if (item.getAmount() <= 0){
                    itemList.remove(item);
                }
                order.setTotal(order.getTotal()-item.getPrice());
                order.setAmount(order.getAmount()-1);
                break;
            }
        }
        if (itemList.size() <= 0){
            order = null;
        }
        result.put("code",200);
        result.put("order",order);
        return result;
    }


    public Map<String, Object> delete(int goodsId, Order order) {
        Map<String, Object> result = new HashMap<>();
        List<Item> itemList = order.getItemList();
        for (int i=0;i<itemList.size();i++) {
            Item item = itemList.get(i);
            if (item.getGoodId() == goodsId){
                item.setAmount(item.getAmount()-1);
                order.setTotal(order.getTotal()-item.getPrice());
                order.setAmount(order.getAmount()-1);
                itemList.remove(item);
            }
        }
        if (itemList.size() <= 0){
            order = null;
        }
        result.put("code",200);
        result.put("order",order);
        return result;
    }

    @Override
    public Map<String, Object> selectOrder(int userId) {
        Map<String, Object> result = new HashMap<>();
        List<Order> orders = orderMapper.selectByUserId(userId);
        for (Order order : orders) {
            List<Item> itemList =  itemMapper.findItemByOrderId(order.getId());
            for (Item item : itemList) {
                Goods good = goodsMapper.getGoodsInfoById(item.getGoodId());
                item.setGoods(good);
            }
            order.setItemList(itemList);
        }
        result.put("code",200);
        result.put("order",orders);
        return result;
    }

    @Override
    public Map<String, Object> selectOrderById(int orderId) {
        Map<String, Object> result = new HashMap<>();
        Order order = orderMapper.selectByOrderId(orderId);
        if (order == null){
            result.put("code",5030);
            result.put("msg","支付失败，没有该订单");
            return result;
        }
        result.put("code",200);
        result.put("order",order);
        return result;
    }

    @Override
    public Map<String, Object> createRealOrder(Order order) {
        Map<String, Object> result = new HashMap<>();
        if (order == null){
            result.put("code",5024);
            result.put("msg","提交失败，购物车中没有数据");
            return result;
        }
        // 检查库存
        List<Item> itemList = order.getItemList();
        for (Item item : itemList) {
            int buyNum = item.getAmount();
            int goodsId = item.getGoodId();
            Goods goodsInfo = goodsMapper.getGoodsInfoById(goodsId);
            int stock = goodsInfo.getStock();
            if (buyNum > stock){
                result.put("code",5025);
                result.put("msg","订单创建失败，商品["+goodsInfo.getName()+"]库存不足");
                return result;
            }
        }
        order.setStatus(1);
        order.setSystime(new Date());
        try {
            orderMapper.insertDataToOrder(order);
//        System.out.println(order);
            if (order.getId() <= 0){
                result.put("code",5026);
                result.put("msg","订单创建失败");
                return result;
            }
            for (Item item : order.getItemList()) {
                item.setOrderId(order.getId());
                itemMapper.insertDataToItem(item);
            }
        }catch (Exception e){
            result.put("code",5027);
            result.put("msg","订单创建失败");
            return result;
        }
        result.put("code",200);
        return result;
    }

    @Override
    public Map<String, Object> updateOrder(Order order) {
        Map<String, Object> result = new HashMap<>();
        if (order == null){
            result.put("code",5027);
            result.put("msg","更新失败，指定的订单编号不存在");
            return result;
        }
        order.setStatus(2);
        int res = orderMapper.updateOrder(order);
        if (res>0){
            result.put("code",200);
        }else {
            result.put("code",5028);
            result.put("msg","更新失败,表更错误");
        }
        return result;
    }
}

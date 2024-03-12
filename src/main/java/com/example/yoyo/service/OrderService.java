package com.example.yoyo.service;

import com.example.yoyo.pojo.Order;
import com.example.yoyo.pojo.User;

import java.util.Map;

/**
 * @author : hgj
 * @date : 2024/1/9 - 16:45
 * @desc :
 */
public interface OrderService {

    Map<String,Object> addGoodsToCart(int goodsId, Order order, User user);
    Map<String,Object> updateCartInfo(int goodsId, Order order);
    Map<String,Object> delete(int goodsId, Order order);
    Map<String,Object> selectOrder(int userId);
    Map<String,Object> selectOrderById(int orderId);
    Map<String,Object> createRealOrder(Order order);
    Map<String,Object> updateOrder(Order order);
}

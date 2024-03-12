package com.example.yoyo.pojo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName order
 */
@Data
public class Order implements Serializable {
    /**
     * "订单id"
     */
    private Integer id;

    /**
     * "订单总价"
     */
    private Double total;

    /**
     * "订单中商品总件数"
     */
    private Integer amount;

    /**
     * "订单状态 0表示删除，1表示未付款，2表示已付款，3表示配送中，4表示已完成"
     */
    private Integer status;

    /**
     * "支付方式0表示未支付 1表示微信支付，2表示支付宝支付，3表示货到付款"
     */
    private Integer paytype;

    /**
     * "收货人姓名"
     */
    private String name;

    /**
     * "收货人手机号"
     */
    private String phone;

    /**
     * "收货人地址"
     */
    private String address;

    /**
     * "下单时间"
     */
    private Date systime;

    /**
     * "用户的id，关联user表"
     */
    private Integer userId;

    private List<Item> itemList;
    User user;

    private static final long serialVersionUID = 1L;
}
package com.example.yoyo.pojo;

import lombok.Data;


/**
 * 
 * @TableName item
 */
@Data
public class Item {
    /**
     * "商品项id"
     */
    private Integer id;

    /**
     * "商品单价"
     */
    private Double price;

    /**
     * "购买数量"
     */
    private Integer amount;

    /**
     * "商品的id，关联goods表"
     */
    private Integer goodId;

    /**
     * "订单id，关联order"
     */
    private Integer orderId;

    private Goods goods;


}
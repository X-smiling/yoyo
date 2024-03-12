package com.example.yoyo.mapper;

import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.Item;
import com.example.yoyo.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/9 - 16:50
 * @desc :
 */
@Mapper
public interface OrderMapper {

    @Select("select * from goods where id = #{goodId}")
    Goods selectByGoodId(int goodId);

    @Select("select * from item where good_id = #{goodId}")
    List<Item> selecrByGI(int goodId);

    int insertDataToOrder(Order order);
    int updateOrder(Order order);

    @Select("select * from `order` where user_id = #{userId}")
    List<Order> selectByUserId(int userId);

    @Select("select * from `order` where id = #{orderId}")
    Order selectByOrderId(int orderId);

    @Update("update `order` set status = #{status} where id = #{id}")
    int updateStatus(int id,int status);


}

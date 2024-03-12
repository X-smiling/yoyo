package com.example.yoyo.mapper;

import com.example.yoyo.pojo.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/11 - 21:00
 * @desc :
 */
@Mapper
public interface ItemMapper {

    int insertDataToItem(Item item);

    @Select("select * from item where order_id = #{orderId}")
    List<Item> findItemByOrderId(int orderId);
}

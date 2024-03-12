package com.example.yoyo.service;

import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/1 - 21:18
 * @desc :
 */
public interface HomeService {
    List<Type> getAllTypes();

    List<Goods> getGoodsListByType(int type);
    List<Goods> getGoodsListPageByTopType(@Param("typeId") int typeId,
       @Param("page") int page,@Param("pageSize") int pageSize);
}

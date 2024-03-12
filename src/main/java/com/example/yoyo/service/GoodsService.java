package com.example.yoyo.service;

import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/1 - 23:15
 * @desc :
 */
public interface GoodsService {
    List<Goods> getGoodsListPageByTypeId(int typeId,int page, int pageSize);

    Type getTypeInfoById(int typeId);

    int getGoodsTotalByTypeId(int typeId);

    List<Goods> getGoodsListPageByKey(String name,int page,int size);

    Goods getGoodsInfoById(int goodsId);
}

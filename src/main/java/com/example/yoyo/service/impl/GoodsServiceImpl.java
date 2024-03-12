package com.example.yoyo.service.impl;

import com.example.yoyo.mapper.GoodsMapper;
import com.example.yoyo.mapper.TypeMapper;
import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.Type;
import com.example.yoyo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/1 - 23:16
 * @desc :
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Override
    public List<Goods> getGoodsListPageByTypeId(int typeId, int page, int pageSize) {
        return goodsMapper.getGoodsListPageByTypeId(typeId,(page-1)*pageSize
        ,pageSize);
    }

    /**
     * 获取指定分类的分类信息
     * @param typeId
     * @return
     */
    @Override
    public Type getTypeInfoById(int typeId) {
        return typeMapper.getGoodsTypeById(typeId);
    }

    /**
     * 获取商品总数
     * @param typeId
     * @return
     */
    @Override
    public int getGoodsTotalByTypeId(int typeId) {
        return goodsMapper.getGoodsTotalByTypeId(typeId);
    }

    @Override
    public List<Goods> getGoodsListPageByKey(String name, int page, int size) {
        return goodsMapper.getGoodsListByKye(name,(page-1)*size,size);
    }

    @Override
    public Goods getGoodsInfoById(int goodsId) {
        Goods goods = goodsMapper.getGoodsInfoById(goodsId);
        Integer typeId = goods.getTypeId();
        Type type = typeMapper.getGoodsTypeById(typeId);
        goods.setType(type);
        return goods;
    }
}

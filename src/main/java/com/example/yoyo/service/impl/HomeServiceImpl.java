package com.example.yoyo.service.impl;

import com.example.yoyo.mapper.GoodsMapper;
import com.example.yoyo.mapper.TypeMapper;
import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.Type;
import com.example.yoyo.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/1 - 21:20
 * @desc :
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    public List<Type> getAllTypes() {
        List<Type> allType = typeMapper.getAllType();
        return allType;
    }

    public List<Goods> getGoodsListByType(int type) {
        List<Goods> goods = goodsMapper.getGoodsByList(type);
        for (Goods good : goods) {
            int typeId = good.getTypeId();
            Type type1 = typeMapper.getGoodsTypeById(typeId);
            good.setType(type1);
        }
        return goods;
    }

    public List<Goods> getGoodsListPageByTopType(int typeId,int page,int pageSize){
        return goodsMapper.getGoodsListPageByTopTypeId
                (typeId, (page-1)*pageSize, pageSize);
    }
}

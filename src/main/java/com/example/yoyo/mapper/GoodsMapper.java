package com.example.yoyo.mapper;

import com.example.yoyo.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/1 - 22:09
 * @desc :
 */
@Mapper
public interface GoodsMapper {

    List<Goods> getGoodsByList(int type);
    List<Goods> getGoodsListPageByTopTypeId(@Param("typeId") int typeId,
          @Param("page") int page,@Param("pageSize") int pageSize);

    List<Goods> getGoodsListPageByTypeId(@Param("typeId") int typeId,
            @Param("page") int page,@Param("pageSize") int pageSize);

    int getGoodsTotalByTypeId(int typeId);

    List<Goods> getGoodsListByKye(@Param("name") String name,
          @Param("page") int page,@Param("pageSize") int pageSize);

    Goods getGoodsInfoById(int goodsId);


    List<Goods> getGoodsList(@Param("page") Integer page,@Param("size") Integer size,@Param("status") Integer status);

    List<Goods> getGoodsList2(@Param("page") Integer page,@Param("size") Integer size);


    int updateGoods(Goods goods);

    void deleteGoods(int id);

    int addGoods(Goods goods);
}

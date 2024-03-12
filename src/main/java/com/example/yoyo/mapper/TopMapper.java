package com.example.yoyo.mapper;

import com.example.yoyo.pojo.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/1 - 20:54
 * @desc :
 */
@Mapper
public interface TopMapper {

    int getGoodsCountByGoodsIdAndType(@Param("type") int type,@Param("goodsId") int goodsId);

@Delete("delete from top where good_id = #{id}")
    void deleteTop(int id);
}

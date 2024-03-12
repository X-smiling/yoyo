package com.example.yoyo.mapper;

import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/1 - 20:54
 * @desc :
 */
@Mapper
public interface TypeMapper {
    List<Type> getAllType();

    @Select("select * from type where id = #{typeId} and status = 1")
    Type getGoodsTypeById(int typeId);

    @Insert("insert into type (name) values (#{name})")
    void addGoodType(String name);

    @Update("update type set name = #{name} where id = #{id}")
    void updateGoodType(String name,int id);

    @Select("select * from type where id = #{typeId}")
    Type getType(int id);

    @Delete("delete from type where id = #{id}")
    void deleteType(int id);
}

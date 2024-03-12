package com.example.yoyo.mapper;

import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/2 - 16:14
 * @desc :
 */@Mapper
public interface UserMapper {

//    int addUser(String username,String password,String name,
//                    String  phone,String address,Integer status);
    int addUser(User user);
    User findUserByUsername(String username);

    @Select("select * from user where id = #{id}")
    User findUserById(int id);

    @Select("select * from user where username = #{username} and password = #{password}")
    User findUser(User user);

    @Update("update user set password = #{password} where username = #{username}")
    int updateUserPsw(User user);

    @Update("update user set password = #{password} where id = #{id}")
    int updateUserPsw2(int id ,String password);


    @Update("update user set name = #{name},phone = #{phone},address = #{address} where id = #{id}")
    int updataUserInfo(User user);

    @Select("select * from user limit #{page},#{pageSize}")
    List<User>  selectUserInfo(@Param("page")Integer page,@Param("pageSize")Integer pageSize);


    @Delete("delete from user where id = #{id}")
    int deleteUser(int id);

    int addGoods(Goods goods);
}

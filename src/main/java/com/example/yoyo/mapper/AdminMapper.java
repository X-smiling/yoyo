package com.example.yoyo.mapper;

import com.example.yoyo.dto.AdminDto;
import com.example.yoyo.pojo.Admin;
import com.example.yoyo.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/14 - 20:29
 * @desc :
 */
@Mapper
public interface AdminMapper {
    @Select("select * from admin where username = #{username}")
    Admin findUserByUsername(String username);

    @Select("select * from admin where username = #{username} and password = #{password}")
    Admin findUser(Admin admin);

    @Update(("update admin set password = #{passwordNew} where id = #{id}"))
    int updateUserPsw(AdminDto adminDto);

    List<Order> getOrderListByStatus(@Param("page") Integer page,@Param("pageSize") Integer pageSize,@Param("status") Integer status);
    List<Order> getOrderList(@Param("page") Integer page,@Param("pageSize") Integer pageSize);
}

package com.example.yoyo.service;

import com.example.yoyo.dto.AdminDto;
import com.example.yoyo.pojo.Admin;
import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.Type;
import com.example.yoyo.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : hgj
 * @date : 2024/1/14 - 20:34
 * @desc :
 */
public interface AdminService {

    /**
     * 管理员登录
     * @param admin
     * @return
     */
    Map<String, Object> handleLogin(Admin admin);

    /**\
     * 更新管理员密码
     * @param adminDto
     * @return
     */
    Map<String, Object>  updatePwd(AdminDto adminDto);

    /**
     * 分页查询订单
     * @param page
     * @param size
     * @return
     */
    Map<String, Object> selectOrderList(Integer page, Integer size,Integer status);

    /**
     * 更新操作
     * @param id
     * @param operate
     * @return
     */
    int updateOperate(int id, String operate);

    /**
     * 获取用户信息
     * @param page
     * @param size
     * @return
     */
    Map<String, Object> getUserInfo(Integer page, Integer size);

    Map<String, Object> addUser(User user);

    Map<String, Object> updateUser(User user);

    User getUser(int id);

    Map<String, Object> updateUserPwd(User user);

    int deleteUser(int id);

    void addGoodType(String name);

    void updateGoodType(String name,int id);

    Type getType(int id);

    void deleteType(int id);

    Map<String, Object> getAllGoods(Integer page,Integer size,Integer status);
    boolean handleDeleteGood(int id);

    Map<String, Object> handleUpdateGoods(HttpServletRequest request, MultipartFile cover, MultipartFile image1, MultipartFile image2);
    Map<String, Object> handleAddGoods(HttpServletRequest request, MultipartFile cover, MultipartFile image1, MultipartFile image2);

    Map<String, Object> addGoods(HttpServletRequest request,
                                 MultipartFile cover, MultipartFile image1, MultipartFile image2);
}

package com.example.yoyo.service;

import com.example.yoyo.pojo.User;

import java.util.Map;

/**
 * @author : hgj
 * @date : 2024/1/2 - 16:13
 * @desc :
 */
public interface UserService {

    Map<String,Object> handleReg(User user);
    Map<String,Object> handleLogin(User user);
    Map<String,Object> handlePsw(User user);

    Map<String,Object> handleUserPsw(int id,String password,String passwordNew);

    Map<String,Object> handleUserInfo(User user);
}

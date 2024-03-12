package com.example.yoyo.service.impl;

import com.example.yoyo.mapper.UserMapper;
import com.example.yoyo.pojo.User;
import com.example.yoyo.service.UserService;
import com.example.yoyo.utils.PageUtil;
import com.example.yoyo.utils.SafeUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.ast.UsesStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RelationSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author : hgj
 * @date : 2024/1/2 - 16:13
 * @desc :
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Map<String, Object> handleReg(User user) {
        Map<String, Object> result = new HashMap<>();
        if (user.getUsername() == null || "".equals(user.getUsername())){
            result.put("code",5001);
            result.put("msg","用户名不能为空！");
            return result;
        }
        if (user.getPassword() == null || "".equals(user.getPassword())){
            result.put("code",5002);
            result.put("msg","密码不能为空！");
            return result;
        }
        if (user.getPhone() == null || "".equals(user.getPhone())){
            result.put("code",5003);
            result.put("msg","手机号不能为空！");
            return result;
        }

        User userInfo = userMapper.findUserByUsername(user.getUsername());
        if (userInfo == null){
            String psw = user.getPassword();
            psw = SafeUtil.encode(psw);
            user.setPassword(psw);
            int res = userMapper.addUser(user);
            if (res > 0){
                result.put("code",200);
                result.put("msg","注册成功！");
            }else {
                result.put("code",5005);
                result.put("msg","注册失败，数据库异常！");
            }

        }else {
            result.put("code",5004);
            result.put("msg","注册失败，用户已存在！");
        }

        return result;
    }

    public Map<String,Object> handleLogin(User user){
        Map<String, Object> result = new HashMap<>();
        if (user.getUsername() == null || "".equals(user.getUsername())){
            result.put("code",5001);
            result.put("msg","用户名不能为空！");
            return result;
        }
        if (user.getPassword() == null || "".equals(user.getPassword())){
            result.put("code",5002);
            result.put("msg","密码不能为空！");
            return result;
        }
        User userInfo = userMapper.findUserByUsername(user.getUsername());
        if (userInfo != null){
            String psw = user.getPassword();
            psw = SafeUtil.encode(psw);
            user.setPassword(psw);
            User res = userMapper.findUser(user);
            if (res != null){
                result.put("code",200);
                result.put("msg","登录成功！");
                result.put("userInfo",userInfo);
            }else {
                result.put("code",5007);
                result.put("msg","登录失败，密码错误！");
            }

        }else {
            result.put("code",5006);
            result.put("msg","登录失败，用户不存在！");
        }
        return result;
    }

    public Map<String,Object> handlePsw(User user){
        Map<String, Object> result = new HashMap<>();
        if (user.getUsername() == null || "".equals(user.getUsername())){
            result.put("code",5001);
            result.put("msg","用户名不能为空！");
            return result;
        }
        if (user.getPhone() == null || "".equals(user.getPhone())){
            result.put("code",5002);
            result.put("msg","手机号不能为空！");
            return result;
        }
        User userInfo = userMapper.findUserByUsername(user.getUsername());
        if (userInfo != null){
            String psw = SafeUtil.encode("123456");
            user.setPassword(psw);
            int res = userMapper.updateUserPsw(user);
            if (res > 0){
                result.put("code",200);
                result.put("msg","重置成功！");
            }else {
                result.put("code",5007);
                result.put("msg","重置失败，数据库操作错误！");
            }

        }else {
            result.put("code",5006);
            result.put("msg","重置失败，用户不存在！");
        }
        return result;
    }

    public Map<String,Object> handleUserInfo(User user){
        log.info("user:{}",user.toString());
        Map<String, Object> result = new HashMap<>();
        if (user.getName() == null || "".equals(user.getName())){
            result.put("code",5010);
            result.put("msg","收货人不能为空！");
            return result;
        }
        if (user.getPhone() == null || "".equals(user.getPhone())){
            result.put("code",5011);
            result.put("msg","收货电话不能为空！");
            return result;
        }
        if (user.getAddress() == null || "".equals(user.getAddress())){
            result.put("code",5012);
            result.put("msg","收货地址不能为空！");
            return result;
        }
        int info = userMapper.updataUserInfo(user);
        if (info > 0){
            result.put("code",200);
            result.put("msg","修改成功！");
        }else {
            result.put("code",5013);
            result.put("msg","修改失败，数据库操作错误！");
        }
        return result;
    }

    public Map<String,Object> handleUserPsw(int id,String password,String passwordNew){
        Map<String, Object> result = new HashMap<>();
        if (password == null || "".equals(password)){
            result.put("code",5014);
            result.put("msg","原密码不能为空！");
            return result;
        }
        if (passwordNew == null || "".equals(passwordNew)){
            result.put("code",5015);
            result.put("msg","新密码不能为空！");
            return result;
        }
        if (!Objects.equals(SafeUtil.encode(password), userMapper.findUserById(id).getPassword())){
            result.put("code",5016);
            result.put("msg","原密码输入错误！");
            return result;
        }
        passwordNew =SafeUtil.encode(passwordNew);
        int info = userMapper.updateUserPsw2(id,passwordNew);
        if (info > 0){
            result.put("code",200);
            result.put("msg","修改成功！");
        }else {
            result.put("code",5013);
            result.put("msg","修改失败，数据库操作错误！");
        }
        return result;
    }


}

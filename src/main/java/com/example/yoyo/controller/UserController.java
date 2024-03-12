package com.example.yoyo.controller;

import com.example.yoyo.pojo.Type;
import com.example.yoyo.pojo.User;
import com.example.yoyo.dto.UserDto;
import com.example.yoyo.service.HomeService;
import com.example.yoyo.service.UserService;
import com.example.yoyo.utils.SafeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : hgj
 * @date : 2024/1/2 - 16:00
 * @desc :
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HomeService homeService;

    @RequestMapping("/index/register")
    public String toRegister(HttpServletRequest request,User user){
        request.setAttribute("flag",5);
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);
        Map<String, Object> res = userService.handleReg(user);

        if ("200".equals(res.get("code").toString())){
            return "redirect:/index/login";
        }else {
            request.setAttribute("msg",res.get("msg"));
            return "/index/register.jsp";
        }
    }

    @RequestMapping("/index/login")
    public String toLogin(HttpServletRequest request,User user){
        request.setAttribute("flag",6);
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);
        Map<String, Object> res = userService.handleLogin(user);
        if ("200".equals(res.get("code").toString())){
            HttpSession session = request.getSession();
            session.setAttribute("user",res.get("userInfo"));
            return "redirect:/index/index";
        }else {
            request.setAttribute("msg",res.get("msg"));
            return "/index/login.jsp";
        }
    }

    @GetMapping("/index/logout")
    public String logOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        User userInfo = (User) session.getAttribute("user");
        if (userInfo == null){
            return "redirect:/index/login";
        }else {
            session.removeAttribute("user");
            return "redirect:/index/index";
        }
    }


    @RequestMapping("/index/forget")
    public String toForget(HttpServletRequest request,User user){
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);
        Map<String, Object> res = userService.handlePsw(user);
        if ("200".equals(res.get("code").toString())){
            return "redirect:/index/login";
        }else {
            request.setAttribute("msg",res.get("msg"));
            return "/index/forget.jsp";
        }
    }

    @GetMapping("/index/my")
    public String toMy(HttpServletRequest request){
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);
        HttpSession session = request.getSession();
        User userInfo = (User) session.getAttribute("user");
        if (userInfo == null){
            return "redirect:/index/login";
        }else {
            request.setAttribute("flag",4);
            return "/index/my.jsp";
        }
    }


    @PostMapping("/index/my")
    public String toMy(HttpServletRequest request, UserDto userDto) throws InterruptedException {
        log.info("userdto:{}",userDto.toString());
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);
        HttpSession session = request.getSession();
        User userInfo = (User) session.getAttribute("user");
        Map<String, Object> userInfo1 = new HashMap<>();
        if (userInfo == null){
            return "redirect:/index/login";
        }else {
            request.setAttribute("flag",4);
            if (userDto.getType() == 1){
                User user = new User();
                BeanUtils.copyProperties(userDto,user);
                userInfo1 = userService.handleUserInfo(user);
            }else {
                userInfo1 = userService.handleUserPsw(userDto.getId(), userDto.getPassword(), userDto.getPasswordNew());
            }
            if ("200".equals(userInfo1.get("code").toString())){
                request.setAttribute("msg2",userInfo1.get("msg"));
                return "redirect:/index/login";
            }else {
                request.setAttribute("msg",userInfo1.get("msg"));
                return "/index/my.jsp";
            }
        }
    }




}

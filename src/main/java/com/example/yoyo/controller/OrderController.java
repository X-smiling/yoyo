package com.example.yoyo.controller;

import com.example.yoyo.pojo.Item;
import com.example.yoyo.pojo.Order;
import com.example.yoyo.pojo.Type;
import com.example.yoyo.pojo.User;
import com.example.yoyo.service.HomeService;
import com.example.yoyo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : hgj
 * @date : 2024/1/9 - 16:10
 * @desc :
 */
@Controller
public class OrderController {

    @Autowired
    private HomeService homeService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/index/order")
    public String order(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            return "redirect:/index/login";
        }
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);
        request.setAttribute("flag",3);

        Map<String, Object> res = orderService.selectOrder(user.getId());
        request.setAttribute("orderList",res.get("order"));

        return "/index/order.jsp";
    }

    @GetMapping("/index/cart")
    public String cart(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            return "redirect:/index/login";
        }
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);
        return "/index/cart.jsp";
    }

    @PostMapping("/index/addToCart")
    public void addToCart(HttpServletRequest request, HttpServletResponse response,int goodid) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            response.getWriter().write("login");
            return;
        }
        Order order = (Order) session.getAttribute("order");
        Map<String, Object> res = orderService.addGoodsToCart(goodid, order, user);
        System.out.println(res);
        if ("200".equals(res.get("code").toString())){
            // 获取订单数据
            Order order2 = (Order) res.get("order");
            session.setAttribute("order",order2);
            response.getWriter().write("ok");
        }else {
            response.getWriter().write(res.get("msg").toString());
        }
    }

    @PostMapping("/index/lessen")
    public void lessenToCart(HttpServletRequest request, HttpServletResponse response,int goodid) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            response.getWriter().write("login");
            return;
        }
        Order order = (Order) session.getAttribute("order");
        Map<String, Object> res = orderService.updateCartInfo(goodid, order);
        if ("200".equals(res.get("code").toString())){
            // 获取订单数据
            Order order2 = (Order) res.get("order");
            session.setAttribute("order",order2);
            response.getWriter().write("ok");
        }else {
            response.getWriter().write(res.get("msg").toString());
        }
    }

    @PostMapping("/index/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response,int goodid) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            response.getWriter().write("login");
            return;
        }
        Order order = (Order) session.getAttribute("order");
        Map<String, Object> res = orderService.delete(goodid, order);
        if ("200".equals(res.get("code").toString())){
            // 获取订单数据
            Order order2 = (Order) res.get("order");
            session.setAttribute("order",order2);
            response.getWriter().write("ok");
        }else {
            response.getWriter().write(res.get("msg").toString());
        }
    }

    @GetMapping("/index/placeOrder")
    public String placeOrder(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            return  "redirect:/index/login";
        }
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);

        Order order = (Order) session.getAttribute("order");
        Map<String, Object> res = orderService.createRealOrder(order);
        if ("200".equals(res.get("code").toString())){
            return "/index/pay.jsp";
        }else {
            request.setAttribute("msg2",res.get("msg").toString());
            return "/index/cart.jsp";
        }
    }

    @PostMapping("/index/pay")
    public String pay(HttpServletRequest request,Order order){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            return  "redirect:/index/login";
        }
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);

        Map<String, Object> res = orderService.updateOrder(order);
        if ("200".equals(res.get("code").toString())){
            request.setAttribute("msg","订单支付成功");
        }else {
            request.setAttribute("msg2",res.get("msg").toString());
        }
        return "/index/payok.jsp";
    }

    @RequestMapping("/index/topay")
    public String toPay(Integer orderid,HttpServletRequest request){
        HttpSession session = request.getSession();
        Map<String, Object> res = orderService.selectOrderById(orderid);
        if ("200".equals(res.get("code").toString())){
            request.setAttribute("order",res.get("order"));
            return "/index/pay.jsp";
        }else {
            return "redirect:/index/order";
        }
    }


}

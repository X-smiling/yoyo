package com.example.yoyo.controller;

import com.example.yoyo.dto.AdminDto;
import com.example.yoyo.pojo.Admin;
import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.Type;
import com.example.yoyo.pojo.User;
import com.example.yoyo.service.AdminService;
import com.example.yoyo.service.GoodsService;
import com.example.yoyo.service.HomeService;
import com.example.yoyo.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : hgj
 * @date : 2024/1/14 - 20:07
 * @desc :
 */
@Controller
@Slf4j
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private HomeService homeService;
    @Resource
    private GoodsService goodsService;

//    @GetMapping("/admin/login")
//    public String adminLogin(){
//        return "/admin/login.jsp";
//    }
    @RequestMapping("/admin/login")
    public String adminLogin(Admin admin, HttpServletRequest request){
        Map<String, Object> res = adminService.handleLogin(admin);
        if ("200".equals(res.get("code").toString())){
            HttpSession session = request.getSession();
            session.setAttribute("admin",res.get("adminInfo"));
            return "redirect:/admin/index";
        }else {
            request.setAttribute("msg",res.get("msg"));
            return "/admin/login.jsp";
        }
    }
    @GetMapping("/admin/index")
    public String adminIndex(){
        return "/admin/index.jsp";
    }
    @RequestMapping("/admin/logout")
    public String adminLogout(HttpServletRequest request){
        HttpSession session = request.getSession();
        Admin adminInfo = (Admin) session.getAttribute("admin");
        session.removeAttribute("admin");
        return "/admin/login.jsp";
    }

    /**
     * 修改密码
     * @param request
     * @param adminDto
     * @return
     */
    @RequestMapping("/admin/adminRe")
    public String adminRe(HttpServletRequest request, AdminDto adminDto){
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null){
            return "/admin/login.jsp";
        }
        Map<String, Object> res = adminService.updatePwd(adminDto);
        if ("200".equals(res.get("code").toString())){
            return "/admin/login.jsp";
        }else {
            request.setAttribute("msg",res.get("msg"));
            return "/admin/admin_reset.jsp";
        }
    }

    /**
     * 商品种类管理
     * @param request
     * @return
     */
    @RequestMapping("/admin/typeList")
    public String adminTypeList(HttpServletRequest request){
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        request.setAttribute("flag",4);
        if (admin == null){
            return "/admin/login.jsp";
        }
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);
        return "/admin/type_list.jsp";
    }

    /**
     * 添加商品种类
     * @param
     * @return
     */
    @GetMapping("/admin/typeAdd")
    public String typeAdd(){
        return "/admin/type_list.jsp";
    }
    @PostMapping("/admin/typeAdd")
    public String typeAdd(String name){
        adminService.addGoodType(name);
        return "redirect:/admin/typeList";
    }

    /**
     * 修改商品种类
     * @return
     */
    @GetMapping("/admin/typeEdit")
    public String typeEdit(HttpServletRequest request,int id){
        Type type = adminService.getType(id);
        request.setAttribute("type",type);
        return "/admin/type_edit.jsp";
    }
    @PostMapping("/admin/typeEdit")
    public String typeEdit(HttpServletRequest request,String name,int id){
        adminService.updateGoodType(name, id);
        return "redirect:/admin/typeList";
    }

    /**
     * 删除商品种类
     * @return
     */
    @GetMapping("/admin/typeDelete")
    public String typeDelete(int id){
        adminService.deleteType(id);
        return "redirect:/admin/typeList";
    }

    /**
     * 商品管理
     * @param request
     * @return
     */
    @GetMapping("/admin/goodsList")
    public String adminGoodList(HttpServletRequest request,Integer page,Integer size,Integer status){
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        request.setAttribute("flag",3);
        if (admin == null){
            return "/admin/login.jsp";
        }
        if (page == null){
            page = 1;
        }
        if (size == null){
            size = 16;
        }
        if (status == null){
            status = 0;
        }
        Map<String, Object> res = adminService.getAllGoods(page, size,status);
        request.setAttribute("goodsList",res.get("goodsList"));
        request.setAttribute("status",status);
        return "/admin/goods_list.jsp";
    }

    /**
     * 添加商品
     * @return
     */
    @GetMapping("/admin/goodsAdd")
    public String goodsAdd(HttpServletRequest request){
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);
        return "/admin/goods_add.jsp";
    }
    @PostMapping("/admin/goodsAdd")
    public String goodsAdd(HttpServletRequest request,
               MultipartFile cover,MultipartFile image1,MultipartFile image2){
//        log.info("cover:"+cover);
//        log.info("image1:"+image1);
//        log.info("image2:"+image2);
        Goods goods = new Goods();
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);
        Map<String, Object> res = adminService.addGoods(request,cover,image1,image2);
        request.setAttribute("msg",res.get("msg"));
        return "redirect:/admin/goodsList";
    }

    //8.修改指定商品信息页面(get请求)：http://localhost:8081/admin/goodsEdit(?id=15&status=0)
    @GetMapping("/admin/goodsEdit")
    public String goodsEdit(HttpServletRequest request,int id,int status){
        //1.登录判断
        HttpSession session = request.getSession();
        Admin adminInfo = (Admin) session.getAttribute("admin");
        if (adminInfo == null) {
            return "redirect:/admin/login";
        }
        //2.商品分类展示
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);

        //3.根据商品id获取商品信息
        Goods goodsInfo = goodsService.getGoodsInfoById(id);
        request.setAttribute("goods",goodsInfo);
        //商品类型（今日精选、热销、新品推荐）
        request.setAttribute("status",status);


        return "/admin/goods_edit.jsp";
    }
    //修改指定商品信息页面(post请求)：http://localhost:8081/admin/goodsEdit
    @PostMapping("/admin/goodsEdit")
    public String goodsEdit(HttpServletRequest request, @RequestParam("cover") MultipartFile cover, @RequestParam("image1") MultipartFile image1, @RequestParam("image2") MultipartFile image2){
        //1.登录判断
        HttpSession session = request.getSession();
        Admin adminInfo = (Admin) session.getAttribute("admin");
        if (adminInfo == null) {
            return "redirect:/admin/login";
        }
        //2.修改逻辑
        //商品添加逻辑
        Map<String,Object> res = adminService.handleUpdateGoods(request,cover,image1,image2);
        if (res.get("code").toString().equals("200")) {
            //成功
            request.setAttribute("msg","商品信息修改成功！");
            //跳转至商品列表页(全部商品)
            return "redirect:/admin/goodsList?status=0&page=1&size=16";
        }else{
            //失败
            request.setAttribute("msg",res.get("msg").toString());
            //3.商品分类展示
            List<Type> types = homeService.getAllTypes();
            request.setAttribute("typeList",types);
            //4.根据商品id获取商品信息
            Goods goodsInfo = goodsService.getGoodsInfoById(Integer.valueOf(request.getParameter("id")));
            request.setAttribute("goods",goodsInfo);
            //商品类型（今日精选、热销、新品推荐）
            request.setAttribute("status",0);

            return "/admin/goods_edit.jsp";
        }

    }

    //9.删除指定商品(get请求)：http://localhost:8081/admin/goodsDelete
    @GetMapping("/admin/goodsDelete")
    public String goodsDelete(HttpServletRequest request,int id){
        //1.登录判断
        HttpSession session = request.getSession();
        Admin adminInfo = (Admin) session.getAttribute("admin");
        if (adminInfo == null) {
            return "redirect:/admin/login";
        }

        //2.删除逻辑
        adminService.handleDeleteGood(id);
        return "redirect:/admin/goodsList?status=0&page=1&size=16";
    }


    /**
     * 客户管理
     * @param request
     * @return
     */
    @RequestMapping("/admin/userList")
    public String adminUserList(HttpServletRequest request,Integer page,Integer size){
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        request.setAttribute("flag",2);
        if (admin == null){
            return "/admin/login.jsp";
        }
        if (page == null){
            page = 1;
        }
        if (size == null){
            size = 16;
        }
        Map<String, Object> res = adminService.getUserInfo(page, size);
        request.setAttribute("userList",res.get("userList"));
        return "/admin/user_list.jsp";
    }

    /**
     * 添加客户
     * @return
     */
    @GetMapping("/admin/userAdd")
    public String addUser(){
        return "/admin/user_add.jsp";
    }
    @PostMapping("/admin/userAdd")
    public String addUser(User user,HttpServletRequest request){
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null){
            return "/admin/login.jsp";
        }
        Map<String, Object> res = adminService.addUser(user);
        request.setAttribute("msg",res.get("msg"));
        return "redirect:/admin/userList";
    }

    /**
     * 修改用户信息
     * @return
     */
    @GetMapping("/admin/userEdit")
    public String userEdit(HttpServletRequest request,int id){
        User user = adminService.getUser(id);
        request.setAttribute("user",user);
        return "/admin/user_edit.jsp";
    }
    @PostMapping("/admin/userEdit")
    public String userEdit(HttpServletRequest request,User user){
        Map<String, Object> res = adminService.updateUser(user);
        request.setAttribute("msg",res.get("msg"));
        return "redirect:/admin/userList";
    }

    /**
     * 修改密码
     */
    @GetMapping("/admin/userModifyPwd")
    public String updatePwd(HttpServletRequest request,int id){
        request.setAttribute("flag",5);
        User user = adminService.getUser(id);
        request.setAttribute("user",user);
        return "/admin/user_modifyPwd.jsp";
    }
    @PostMapping("/admin/userModifyPwd")
    public String updatePwd(HttpServletRequest request,User user){
        request.setAttribute("flag",5);
        Map<String, Object> res = adminService.updateUserPwd(user);
        request.setAttribute("msg",res.get("msg"));
        return "redirect:/admin/userList";
    }

    /**
     * 删除客户
     */
    @GetMapping("/admin/userDelete")
    public String userDelete(int id){
        adminService.deleteUser(id);
        return "redirect:/admin/userList";
    }

    /**
     * 订单管理
     * @param request
     * @return
     */
    @RequestMapping("/admin/orderList")
    public String adminOrderList(HttpServletRequest request,Integer page,Integer size,Integer status){
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        request.setAttribute("flag",1);
        if (admin == null){
            return "/admin/login.jsp";
        }
        if (page == null){
            page = 1;
        }
        if (size == null){
            size = 16;
        }
        if (status == null){
            status = 0;
        }
        Map<String, Object> res = adminService.selectOrderList(page,size,status);
        request.setAttribute("orderList",res.get("orderList"));
        request.setAttribute("status",status);

        int total = (int) res.get("total");
        String pageStr = PageUtil.getPageTool(request, total, page, size);
        request.setAttribute("pageTool",pageStr);
        return "/admin/order_list.jsp";
    }

    @RequestMapping("/admin/orderOperate")
    public String orderOperate(int id,String operate){
        adminService.updateOperate(id,operate);
        return "redirect:/admin/orderList";
    }
}

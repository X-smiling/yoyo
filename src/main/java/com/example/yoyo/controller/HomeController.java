package com.example.yoyo.controller;

import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.Type;
import com.example.yoyo.service.HomeService;
import com.example.yoyo.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : hgj
 * @date : 2023/12/26 - 10:34
 * @desc :
 */
@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/")
    public String toindex(){
        return "/index.jsp";

    }
    @GetMapping("/index/index")
    public String index(HttpServletRequest request){
        request.setAttribute("flag",1);
        List<Type> types = homeService.getAllTypes();
//        typeList只能用于jsp页面中
        request.setAttribute("typeList",types);

        // 获取今日商品精选
        List<Goods> goods1 = homeService.getGoodsListByType(1);
        request.setAttribute("top1List",goods1);
        List<Goods> goods2 = homeService.getGoodsListByType(2);
        request.setAttribute("top2List",goods2);
        List<Goods> goods3 = homeService.getGoodsListByType(3);
        request.setAttribute("top3List",goods3);

        return "/index/index.jsp";
    }

    @RequestMapping("/index/top")
    public String top(HttpServletRequest request,int typeid,int page,int size){
        request.setAttribute("flag",typeid + 5);
        request.setAttribute("typeid",typeid);
        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);

        List<Goods> goods = homeService.getGoodsListPageByTopType(typeid, page, size);
        request.setAttribute("goodList",goods);

        int total = goods.size();
        String pageStr = PageUtil.getPageTool(request, total, page, size);
        request.setAttribute("pageTool",pageStr);

        return "/index/goods.jsp";
    }

}

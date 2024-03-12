package com.example.yoyo.controller;

import com.example.yoyo.pojo.Goods;
import com.example.yoyo.pojo.Type;
import com.example.yoyo.service.GoodsService;
import com.example.yoyo.service.HomeService;
import com.example.yoyo.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : hgj
 * @date : 2024/1/1 - 23:03
 * @desc :
 */
@Controller
public class GoodsController {

    @Autowired
    private HomeService homeService;
    @Autowired
    private GoodsService goodsService;


    @GetMapping("/index/goods")
    public String goods(HttpServletRequest request,int typeid,int page,int size){
        request.setAttribute("flag",2);

        List<Type> types = homeService.getAllTypes();
//        typeList只能用于jsp页面中
        request.setAttribute("typeList",types);

        List<Goods> goodsList = goodsService.getGoodsListPageByTypeId(typeid, page, size);
        request.setAttribute("goodList",goodsList);

        Type type = goodsService.getTypeInfoById(typeid);
        request.setAttribute("type",type);

        int total = goodsService.getGoodsTotalByTypeId(typeid);
        String pageTool = PageUtil.getPageTool(request, total, page, size);
        request.setAttribute("pageTool",pageTool);

        return "/index/goods.jsp";
    }


    @PostMapping("/index/search")
    public String search(HttpServletRequest request,String name,int page,int size){

        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);

        List<Goods> goods = goodsService.getGoodsListPageByKey(name, page, size);
        request.setAttribute("goodList",goods);

        request.setAttribute("typeid",4);

        int total = goods.size();
        String pageTool = PageUtil.getPageTool(request, total, page, size);
        request.setAttribute("pageTool",pageTool);

        return "/index/goods.jsp";
    }

    @GetMapping("/index/detail")
    public String detail(HttpServletRequest request,int goodid){

        List<Type> types = homeService.getAllTypes();
        request.setAttribute("typeList",types);

        Goods goodsInfo = goodsService.getGoodsInfoById(goodid);
        request.setAttribute("good",goodsInfo);

        return "/index/detail.jsp";
    }
}

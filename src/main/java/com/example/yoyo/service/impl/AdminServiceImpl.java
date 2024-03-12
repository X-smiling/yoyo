package com.example.yoyo.service.impl;

import com.example.yoyo.dto.AdminDto;
import com.example.yoyo.mapper.*;
import com.example.yoyo.pojo.*;
import com.example.yoyo.service.AdminService;
import com.example.yoyo.service.OrderService;
import com.example.yoyo.utils.SafeUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * @author : hgj
 * @date : 2024/1/14 - 20:34
 * @desc :
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private ItemMapper itemMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private TopMapper topMapper;
    @Override
    public Map<String, Object> handleLogin(Admin admin) {
        Map<String, Object> result = new HashMap<>();
        if (admin.getUsername() == null || "".equals(admin.getUsername())){
            result.put("code",5001);
            result.put("msg","用户名不能为空！");
            return result;
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())){
            result.put("code",5002);
            result.put("msg","密码不能为空！");
            return result;
        }
        Admin adminInfo = adminMapper.findUserByUsername(admin.getUsername());
        if (adminInfo != null){
            String psw = admin.getPassword();
            psw = SafeUtil.encode(psw);
            adminInfo.setPassword(psw);
            Admin res = adminMapper.findUser(adminInfo);
            if (res != null){
                result.put("code",200);
                result.put("msg","登录成功！");
                result.put("adminInfo",adminInfo);
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

    @Override
    public Map<String, Object> updatePwd(AdminDto adminDto) {
        Map<String, Object> result = new HashMap<>();
        String password = adminDto.getPassword();
        String passwordNew = adminDto.getPasswordNew();
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
        if (!Objects.equals(SafeUtil.encode(password), adminMapper.findUserByUsername(adminDto.getUsername()).getPassword())){
            result.put("code",5016);
            result.put("msg","原密码输入错误！");
            return result;
        }
        passwordNew =SafeUtil.encode(passwordNew);
        adminDto.setPasswordNew(passwordNew);
        int info = adminMapper.updateUserPsw(adminDto);
        if (info > 0){
            result.put("code",200);
            result.put("msg","修改成功！");
        }else {
            result.put("code",5013);
            result.put("msg","修改失败，数据库操作错误！");
        }
        return result;
    }

    @Override
    public Map<String, Object> selectOrderList(Integer page, Integer size,Integer status) {
        Map<String, Object> result = new HashMap<>();
        List<Order> orderList = new ArrayList<>();
        if (status == 0){
            orderList = adminMapper.getOrderList(page,size);
        }else {
            orderList = adminMapper.getOrderListByStatus((page-1)*size,size,status);
        }
        if (orderList == null){
            result.put("msg","无数据");
            result.put("orderList", null);
            return result;
        }
        for (Order order : orderList) {
            List<Item> itemList = itemMapper.findItemByOrderId(order.getId());
            for (Item item : itemList) {
                Goods goods = goodsMapper.getGoodsInfoById(item.getGoodId());
                item.setGoods(goods);
            }
            order.setItemList(itemList);
        }
        result.put("code",200);
        result.put("orderList",orderList);
        result.put("total",orderList.size());
        return result;
    }

    @Override
    public int updateOperate(int id, String operate) {
        if ("send".equals(operate)){
            return orderMapper.updateStatus(id,3);
        } else if ("delete".equals(operate)) {
            return orderMapper.updateStatus(id,0);
        } else if ("finish".equals(operate)) {
            return orderMapper.updateStatus(id,4);
        } else {
            return 0;
        }
    }

    @Override
    public Map<String, Object> getUserInfo(Integer page, Integer size) {
        Map<String, Object> result = new HashMap<>();
        List<User> userList = userMapper.selectUserInfo((page - 1) * size, size);
        result.put("userList",userList);
        return result;
    }

    @Override
    public Map<String, Object> addUser(User user) {
        Map<String, Object> result = new HashMap<>();
        if (user == null){
            result.put("msg","无数据");
            return result;
        }
        int i = userMapper.addUser(user);
        if (i <= 0){
            result.put("msg","数据库操作错误");
            return result;
        }
        result.put("msg","添加成功");
        return result;
    }

    @Override
    public Map<String, Object> updateUser(User user) {
        Map<String, Object> result = new HashMap<>();
        int i = userMapper.updataUserInfo(user);
        if (i <= 0){
            result.put("msg","数据库操作错误");
            return result;
        }
        result.put("msg","添加成功");
        return result;
    }

    @Override
    public User getUser(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public Map<String, Object> updateUserPwd(User user) {
        Map<String, Object> result = new HashMap<>();
        int i = userMapper.updateUserPsw2(user.getId(),SafeUtil.encode(user.getPassword()));
        if (i <= 0){
            result.put("msg","数据库操作错误");
            return result;
        }
        result.put("msg","更新成功");
        return result;
    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public void addGoodType(String name) {
        typeMapper.addGoodType(name);
    }

    @Override
    public void updateGoodType(String name,int id) {
        typeMapper.updateGoodType(name,id);
    }

    @Override
    public Type getType(int id) {
        return typeMapper.getType(id);
    }

    @Override
    public void deleteType(int id) {
        typeMapper.deleteType(id);
    }

    @Override
    public Map<String, Object> getAllGoods(Integer page,Integer size,Integer status) {
        Map<String, Object> result = new HashMap<>();
        List<Goods> goodsList = new ArrayList<>();
        if (status == 0){
            goodsList = goodsMapper.getGoodsList2((page - 1) * size, size);
        }else {
            goodsList = goodsMapper.getGoodsList((page - 1) * size, size,status);
        }
        for (Goods goods : goodsList) {
            goods.setType(typeMapper.getType(goods.getTypeId()));
            boolean isTopToToday = topMapper.getGoodsCountByGoodsIdAndType(1, goods.getId()) > 0;
            goods.setTopToday(isTopToToday);
            boolean isTopHot = topMapper.getGoodsCountByGoodsIdAndType(2, goods.getId()) > 0;
            goods.setTopHot(isTopHot);
            boolean isTopNew = topMapper.getGoodsCountByGoodsIdAndType(3, goods.getId()) > 0;
            goods.setTopNew(isTopNew);
        }
        result.put("goodsList",goodsList);
        return result;
    }

    @Override
    public Map<String, Object> addGoods(HttpServletRequest request,
                                        MultipartFile cover, MultipartFile image1, MultipartFile image2) {
        Map<String, Object> result = new HashMap<>();
        String name = request.getParameter("name");
        if (name == null || "".equals(name)){
            result.put("msg","商品标题不能为空");
            result.put("code",5004);
            return result;
        }
        String price = request.getParameter("price");
        if (price == null || "".equals(price)){
            result.put("msg","商品单价不能为空");
            result.put("code",5004);
            return result;
        }
        String intro = request.getParameter("intro");
        if (intro == null || "".equals(intro)){
            result.put("msg","商品介绍不能为空");
            result.put("code",5004);
            return result;
        }
        String stock = request.getParameter("stock");
        if (stock == null || "".equals(stock)){
            result.put("msg","商品库存不能为空");
            result.put("code",5004);
            return result;
        }
        String typeId = request.getParameter("typeId");
        if (typeId == null || "".equals(typeId)){
            result.put("msg","商品分类不能为空");
            result.put("code",5004);
            return result;
        }
        if (cover == null || image1 == null ||image2 == null){
            result.put("msg","商品图片不能为空");
            result.put("code",5004);
            return result;
        }
        String basePath = request.getServletContext().getRealPath("picture");
        String coverPath = handleUploadFile(cover,basePath);
        if (coverPath == null){
            result.put("msg","商品封面图上传失败");
            result.put("code",5010);
            return result;
        }
        String image1Path = handleUploadFile(image1,basePath);
        if (image1Path == null){
            result.put("msg","商品image1上传失败");
            result.put("code",5010);
            return result;
        }
        String image2Path = handleUploadFile(image2,basePath);
        if (image2Path == null){
            result.put("msg","商品image2上传失败");
            result.put("code",5010);
            return result;
        }
        Goods goods = new Goods();
        goods.setName(name);
        goods.setPrice(Double.valueOf(price));
        goods.setIntro(intro);
        goods.setCover(coverPath);
        goods.setImage1(image1Path);
        goods.setImage2(image2Path);
        goods.setStock(Integer.valueOf(stock));
        goods.setTypeId(Integer.valueOf(typeId));

        int i = userMapper.addGoods(goods);
        if (i <= 0){
            result.put("msg","数据库操作错误");
            return result;
        }
        result.put("msg","添加成功");
        return result;
    }

    //3.后台商品管理逻辑
    //添加商品
    @Override
    public Map<String, Object> handleAddGoods(HttpServletRequest request, MultipartFile cover, MultipartFile image1, MultipartFile image2) {
        Map<String, Object> result = new HashMap<>();

        //1.检验数据是否完整
        //商品名称
        String name = request.getParameter("name");
        if (name == null || "".equals(name)) {
            result.put("code", 5032);
            result.put("msg", "商品名称不能为空！");
            return result;
        }
        //商品价格
        String price = request.getParameter("price");
        if (price == null || "".equals(price)) {
            result.put("code", 5033);
            result.put("msg", "商品价格不能为空！");
            return result;
        }
        //商品简要描述
        String intro = request.getParameter("intro");
        if (intro == null || "".equals(intro)) {
            result.put("code", 5034);
            result.put("msg", "商品简要描述不能为空！");
            return result;
        }
        //商品库存
        String stock = request.getParameter("stock");
        if (stock == null || "".equals(stock)) {
            result.put("code", 5035);
            result.put("msg", "商品库存不能为空！");
            return result;
        }
        //商品分类
        String typeId = request.getParameter("typeId");
        if (typeId == null || "".equals(typeId)) {
            result.put("code", 5036);
            result.put("msg", "商品分类不能为空！");
            return result;
        }
        //商品图片
        if (cover == null || image1 == null || image2 == null) {
            result.put("code", 5037);
            result.put("msg", "商品图片不能为空！");
            return result;
        }

        //2.图片处理
        String basePath = request.getServletContext().getRealPath("picture"); //保存图片的文件夹：webapp/picture
        //封面图
        String coverPath = handleUploadFile(cover, basePath);
        if (coverPath == null) {
            result.put("code", 5038);
            result.put("msg", "商品封面图上传失败！");
            return result;
        }
        //主图1
        String image1Path = handleUploadFile(image1, basePath);
        if (image1Path == null) {
            result.put("code", 5039);
            result.put("msg", "商品主图1上传失败！");
            return result;
        }
        //主图2
        String image2Path = handleUploadFile(image2, basePath);
        if (image2Path == null) {
            result.put("code", 5040);
            result.put("msg", "商品主图2上传失败！");
            return result;
        }

        //3.向数据库添加商品信息
        Goods goods = new Goods();
        //向goods中添加信息
        goods.setName(request.getParameter("name"));
        //将string类转换成double类
        Double price1 = Double.parseDouble(request.getParameter("price"));
        goods.setPrice(price1);
        goods.setIntro(request.getParameter("intro"));
        //将string类转换成int类
        int stock1 = Integer.parseInt(request.getParameter("stock"));
        int typeId1 = Integer.parseInt(request.getParameter("typeId"));
        goods.setStock(stock1);
        goods.setTypeId(typeId1);
        goods.setCover(coverPath);
        goods.setImage1(image1Path);
        goods.setImage2(image2Path);

        int addGoods = goodsMapper.addGoods(goods);
        //判断是否成功插入数据
        if (addGoods == 1) {
            //成功
            result.put("code", 200);
        } else {
            //失败
            result.put("code", 5041);
            result.put("msg", "商品添加出错！");
        }
        return result;
    }

    //4.用于图片上传
    //file:要上传的图片, basePath:图片保存的物理路径(目录)    , @Return String:上传成功后的图片路径
    private String handleUploadFile(MultipartFile file, String basePath) {
        //保存上传图片的路径
        String newFilePath = null;
        try {
            //获取上传图片的名称
            String fileName = file.getOriginalFilename();
            //file.getSize()：获取上传图片大小
            if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
                //获取文件名中最后一个点(.)出现的位置
                int dotIndex = fileName.lastIndexOf(".");
                //获取文件扩展名
                String extName = fileName.substring(dotIndex);
                //生成新的文件名
                //UUID:xxxx-xxxx-xxxx
                //replace("-",""):把"-"替换为""（删除-）
                String str = UUID.randomUUID().toString().replace("-", "");
                String newFileName = str + extName;
                //基于新的文件路径创建文件对象
                //windows系统中File.separator=\,其他系统File.separator=/
                File file1 = new File(basePath + File.separator + newFileName);
                //判断文件是否存在
                if (!file1.exists()) {
                    //不存在
                    //创建路径
                    file1.mkdirs();
                }
                //把浏览器发送到后端的文件内容传输给file1对象代表的文件
                file.transferTo(file1);
                newFilePath = "picture/" + newFileName;
            }
        } catch (Exception e) {
            //打印错误异常
            e.printStackTrace();
        }
        return newFilePath;
    }

    //5.修改商品信息逻辑
    @Override
    public Map<String, Object> handleUpdateGoods(HttpServletRequest request, MultipartFile cover, MultipartFile image1, MultipartFile image2) {
        Map<String, Object> result = new HashMap<>();

        //1.检验数据是否完整
        //商品名称
        String name = request.getParameter("name");
        if (name == null || "".equals(name)) {
            result.put("code", 5042);
            result.put("msg", "商品名称不能为空！");
            return result;
        }
        //商品价格
        String price = request.getParameter("price");
        if (price == null || "".equals(price)) {
            result.put("code", 5043);
            result.put("msg", "商品价格不能为空！");
            return result;
        }
        //商品简要描述
        String intro = request.getParameter("intro");
        if (intro == null || "".equals(intro)) {
            result.put("code", 5044);
            result.put("msg", "商品简要描述不能为空！");
            return result;
        }
        //商品库存
        String stock = request.getParameter("stock");
        if (stock == null || "".equals(stock)) {
            result.put("code", 5045);
            result.put("msg", "商品库存不能为空！");
            return result;
        }
        //商品分类
        String typeId = request.getParameter("typeId");
        if (typeId == null || "".equals(typeId)) {
            result.put("code", 5046);
            result.put("msg", "商品分类不能为空！");
            return result;
        }
        //商品图片
        if (cover == null || image1 == null || image2 == null) {
            result.put("code", 5047);
            result.put("msg", "商品图片不能为空！");
            return result;
        }

        //2.图片处理
        String basePath = request.getServletContext().getRealPath("picture"); //保存图片的文件夹：webapp/picture
        //封面图
        String coverPath = handleUploadFile(cover, basePath);
        if (coverPath == null && !cover.getOriginalFilename().equals("")) {
            result.put("code", 5048);
            result.put("msg", "商品封面图上传失败！");
            return result;
        }
        //主图1
        String image1Path = handleUploadFile(image1, basePath);
        if (image1Path == null && !image1.getOriginalFilename().equals("")) {
            result.put("code", 5049);
            result.put("msg", "商品主图1上传失败！");
            return result;
        }
        //主图2
        String image2Path = handleUploadFile(image2, basePath);
        if (image2Path == null && !image2.getOriginalFilename().equals("")) {
            result.put("code", 5050);
            result.put("msg", "商品主图2上传失败！");
            return result;
        }

        //3.修改指定商品信息
        Goods goods = new Goods();
        //向goods中添加信息
        goods.setId(Integer.valueOf(request.getParameter("id")));
        goods.setName(request.getParameter("name"));
        //将string类转换成double类
        Double price1 = Double.parseDouble(request.getParameter("price"));
        goods.setPrice(price1);
        goods.setIntro(request.getParameter("intro"));
        //将string类转换成int类
        int stock1 = Integer.parseInt(request.getParameter("stock"));
        int typeId1 = Integer.parseInt(request.getParameter("typeId"));
        goods.setStock(stock1);
        goods.setTypeId(typeId1);
        goods.setCover(coverPath);
        goods.setImage1(image1Path);
        goods.setImage2(image2Path);

        //根据商品id修改指定商品信息
        int addGoods = goodsMapper.updateGoods(goods);
        //判断是否成功修改成功
        if (addGoods == 1) {
            //成功
            result.put("code", 200);
        } else {
            //失败
            result.put("code", 5051);
            result.put("msg", "商品修改出错！");
        }
        return result;
    }


    //6.删除指定id的商品逻辑实现
    @Override
    public boolean handleDeleteGood(int id) {
        //1.删除top中关于指定id的商品的推荐信息
        topMapper.deleteTop(id);
        //2.删除(软删除)goods表中指定id的商品的商品信息
        goodsMapper.deleteGoods(id);
        return true;
    }
}

<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>商品列表</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="css/bootstrap.css"/>
	<link rel="stylesheet" href="layui/css/layui.css"  media="all">
</head>
<body>
<div class="container-fluid">

	<%@include file="header.jsp" %>
	
	<div class="text-right"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-warm" href="goodsAdd">添加商品</a></div>
	
	<br>
		
	<ul role="tablist" class="nav nav-tabs">
        <li <c:if test='${status==0}'>class="active"</c:if> role="presentation"><a href="goodsList?status=0&page=1&size=16">全部商品</a></li>
        <li <c:if test='${status==1}'>class="active"</c:if> role="presentation"><a href="goodsList?status=1&page=1&size=16">今日推荐</a></li>
        <li <c:if test='${status==2}'>class="active"</c:if> role="presentation"><a href="goodsList?status=2&page=1&size=16">热销推荐</a></li>
        <li <c:if test='${status==3}'>class="active"</c:if> role="presentation"><a href="goodsList?status=3&page=1&size=16">新品推荐</a></li>
    </ul>
    
    <c:if test="${status == 1}"><br><p>首页默认只显示前[1]条记录</p></c:if>
    <c:if test="${status == 2}"><br><p>首页默认只显示前[6]条记录</p></c:if>
    <c:if test="${status == 3}"><br><p>首页默认只显示前[8]条记录</p></c:if>
	
	<br>

	<table class="layui-table" lay-even="" lay-skin="row">

	<tr>
		<th width="5%">ID</th>
		<th width="10%">图片</th>
		<th width="15%">名称</th>
		<th width="30%">介绍</th>
		<th width="5%">价格</th>
		<th width="5%">库存</th>
		<th width="8%">类目</th>
		<th width="22%">操作</th>
	</tr>
	
	<c:forEach var="goods" items="${goodsList}">
         <tr>
         	<td><p>${goods.id}</p></td>
         	<td><p><a href="../index/detail?goodid=${goods.id}" target="_blank"><img src="../${goods.cover}" width="100px" height="100px"></a></p></td>
         	<td><p><a href="../index/detail?goodid=${goods.id}" target="_blank">${goods.name}</a></p></td>
         	<td><p>${goods.intro}</p></td>
         	<td><p>${goods.price}</p></td>
         	<td><p>${goods.stock}</p></td>
         	<td><p>${goods.type.name}</p></td>
			<td>
				<div class="layui-btn-container">
				<c:if test="${goods.topToday}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-warm topDelete" href="javascript:;" type="1" goodsid="${goods.id}" text="加入条幅">移出条幅</a></c:if>
				<c:if test="${!goods.topToday}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-normal topSave" href="javascript:;" type="1" goodsid="${goods.id}" text="移出条幅">加入条幅</a></c:if>
				<c:if test="${goods.topHot}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-warm topDelete" href="javascript:;" type="2" goodsid="${goods.id}" text="加入热销">移出热销</a></c:if>
				<c:if test="${!goods.topHot}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-normal topSave" href="javascript:;" type="2" goodsid="${goods.id}" text="移出热销">加入热销</a></c:if>
				<c:if test="${goods.topNew}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-warm topDelete" href="javascript:;" type="3" goodsid="${goods.id}" text="加入新品">移出新品</a></c:if>
				<c:if test="${!goods.topNew}"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-sm layui-btn-normal topSave" href="javascript:;" type="3" goodsid="${goods.id}" text="移出新品">加入新品</a></c:if>
				</div>
				<div class="layui-btn-container">
						<a href="goodsEdit?id=${goods.id}&status=${status}"><button type="button" class="layui-btn layui-btn-sm"><i class="layui-icon">修改</i></button></a>
						<a href="goodsDelete?id=${goods.id}&status=${status}"><button type="button" class="layui-btn layui-btn-sm layui-btn-danger"><i class="layui-icon">删除</i></button></a>
				</div>
			</td>
       	</tr>
     </c:forEach>
     
</table>

<br>${pageTool}<br>
</div>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
$(function(){
	$(document).on("click", ".topSave", function(){
		var type = $(this).attr("type");
		var goodid = $(this).attr("goodsid");
		$.post("topOperate", {"operate":"join" ,"goodsId": goodid, "type": type}, function(data){
			if(data=="ok"){
				location.reload();
			}else{
				alert("操作失败!");
			}
		}, "text");
	});
	$(document).on("click", ".topDelete", function(){
		var type = $(this).attr("type");
		var goodid = $(this).attr("goodsid");
		$.post("topOperate", {"operate":"delete" ,"goodsId": goodid, "type": type}, function(data){
			if(data=="ok"){
				location.reload();
			}else{
				alert("操作失败!");
			}
		}, "text");
	});
});
</script>
<script src="layui/layui.js" charset="utf-8"></script>
</body>
</html>
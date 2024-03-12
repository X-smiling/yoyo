<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>订单列表</title>
<link rel="stylesheet" href="css/bootstrap.css"/>
	<link rel="stylesheet" href="layui/css/layui.css"  media="all">
</head>
<body>
<div class="container-fluid">

	<%@include file="header.jsp" %>
	
	<br>
	
	<ul role="tablist" class="nav nav-tabs">
        <li <c:if test='${status==0}'>class="active"</c:if> role="presentation"><a href="orderList?status=0&page=1&size=16">全部订单</a></li>
        <li <c:if test='${status==1}'>class="active"</c:if> role="presentation"><a href="orderList?status=1&page=1&size=16">未付款</a></li>
        <li <c:if test='${status==2}'>class="active"</c:if> role="presentation"><a href="orderList?status=2&page=1&size=16">已付款</a></li>
        <li <c:if test='${status==3}'>class="active"</c:if> role="presentation"><a href="orderList?status=3&page=1&size=16">配送中</a></li>
        <li <c:if test='${status==4}'>class="active"</c:if> role="presentation"><a href="orderList?status=4&page=1&size=16">已完成</a></li>
    </ul>
    
    <br>
	
	<table class="layui-table" lay-even="" lay-skin="row">

	<tr>
		<th width="5%">ID</th>
		<th width="5%">总价</th>
		<th width="15%">商品详情</th>
		<th width="20%">收货信息</th>
		<th width="10%">订单状态</th>
		<th width="10%">支付方式</th>
		<th width="10%">下单用户</th>
		<th width="10%">下单时间</th>
		<th width="10%">操作</th>
	</tr>
	
	<c:forEach var="order" items="${orderList}">
         <tr>
         	<td><p>${order.id}</p></td>
         	<td><p>${order.total}</p></td>
         	<td>
	         	<c:forEach var ="item" items="${order.itemList}">
		         	<p>${item.goods.name}(${item.price}) x ${item.amount}</p>
	         	</c:forEach>
         	</td>
         	<td>
         		<p>${order.name}</p>
         		<p>${order.phone}</p>
         		<p>${order.address}</p>
         	</td>
			<td>
				<p>
					<c:if test="${order.status==1}">未付款</c:if>
					<c:if test="${order.status==2}"><span style="color:red;">已付款</span></c:if>
					<c:if test="${order.status==3}">配送中</c:if>
					<c:if test="${order.status==4}">已完成</c:if>
				</p>
			</td>
			<td>
				<p>
					<c:if test="${order.paytype==1}">微信</c:if>
					<c:if test="${order.paytype==2}">支付宝</c:if>
					<c:if test="${order.paytype==3}">货到付款</c:if>
				</p>
			</td>
			<td><p>${order.user.username}</p></td>
			<td><p><fmt:formatDate value="${order.systime}" pattern="yyyy-MM-dd HH:mm:ss" /></p></td>
			<td>
				<c:if test="${order.status==2}">
					<a href="orderOperate?operate=send&id=${order.id}&status=${status}"><button type="button" class="layui-btn layui-btn-sm layui-btn-normal"><i class="layui-icon">发货</i></button></a>
				</c:if>
				<c:if test="${order.status==3}">
					<a href="orderOperate?operate=finish&id=${order.id}&status=${status}"><button type="button" class="layui-btn layui-btn-sm layui-btn-warm"><i class="layui-icon">完成</i></button></a>
				</c:if>
				<a href="orderOperate?operate=delete&id=${order.id}&status=${status}"><button type="button" class="layui-btn layui-btn-sm layui-btn-danger"><i class="layui-icon">删除</i></button></a>
			</td>
       	</tr>
	</c:forEach>
     
</table>

<br>${pageTool}<br>
</div>
<script src="layui/layui.js" charset="utf-8"></script>
</body>
</html>
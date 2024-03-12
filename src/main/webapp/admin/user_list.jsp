<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>客户列表</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="css/bootstrap.css"/>

	<link rel="stylesheet" href="layui/css/layui.css"  media="all">
</head>
<body>
<div class="container-fluid">

	<%@include file="header.jsp"%>
	
	<div class="text-right"><a style="text-decoration: none;color: white" class="layui-btn layui-btn-warm" href="userAdd">添加客户</a></div>
		
	<br>
	
	<table class="layui-table" lay-even="" lay-skin="row">

	<tr>
		<th width="8%">ID</th>
		<th width="8%">用户名</th>
		<th width="8%">真实姓名</th>
		<th width="8%">电话</th>
		<th width="40%">地址</th>
		<th width="28%">操作</th>
	</tr>
	
	<c:forEach var="user" items="${userList}">
         <tr>
         	<td><p>${user.id}</p></td>
         	<td><p>${user.username}</p></td>
         	<td><p>${user.name}</p></td>
         	<td><p>${user.phone}</p></td>
         	<td><p>${user.address}</p></td>
			<td>
				<div class="layui-btn-container">
					<a href="userEdit?id=${user.id}"><button type="button" class="layui-btn layui-btn-sm"><i class="layui-icon">修改信息</i></button></a>
					<a href="userModifyPwd?id=${user.id}"><button type="button" class="layui-btn layui-btn-sm layui-btn-normal"><i class="layui-icon">修改密码</i></button></a>
					<a href="userDelete?id=${user.id}"><button type="button" class="layui-btn layui-btn-sm layui-btn-danger"><i class="layui-icon">删除客户</i></button></a>
				</div>
			</td>
       	</tr>
     </c:forEach>
     
</table>

<br>${pageTool}<br>
</div>
<script src="layui/layui.js" charset="utf-8"></script>
</body>
</html>
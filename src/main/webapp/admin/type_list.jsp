<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>商品种类管理</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="css/bootstrap.css"/>
	<link rel="stylesheet" href="layui/css/layui.css"  media="all">
</head>
<body>
<div class="container-fluid">

	<%@include file="header.jsp"%>
	
	<br>
	
	<div>
		<form class="form-inline" method="post" action="typeAdd">
			<input type="text" class="layui-input" id="input_name" name="name" placeholder="请输入要添加的类目名称" required="required" style="width: 500px"><br>
			<input type="submit" class="layui-btn layui-btn-warm" value="添加商品种类"/>
		</form>
	</div>
	
	<br>

	<table class="layui-table" lay-even="" lay-skin="row">

	<tr>
		<th width="10%">ID</th>
		<th width="70%">名称</th>
		<th width="20%">操作</th>
	</tr>
	
	<c:forEach var="type" items="${typeList}">
         <tr>
         	<td><p>${type.id}</p></td>
         	<td><p>${type.name}</p></td>
			<td>
				<a href="typeEdit?id=${type.id}"><button type="button" class="layui-btn layui-btn-sm"><i class="layui-icon">修改</i></button></a>
				<a href="typeDelete?id=${type.id}"><button type="button" class="layui-btn layui-btn-sm layui-btn-danger"><i class="layui-icon">删除</i></button></a>
			</td>
       	</tr>
     </c:forEach>
     
</table>

</div>
<script src="layui/layui.js" charset="utf-8"></script>
</body>
</html>
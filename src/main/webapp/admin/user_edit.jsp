<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>客户修改</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="css/bootstrap.css"/>
	<link rel="stylesheet" href="layui/css/layui.css"  media="all">
</head>
<body>
<div class="container-fluid">

	<%@include file="header.jsp"%>

	<br><br>
	
	<form class="form-horizontal" action="userEdit" method="post">
		<input type="hidden" name="id" value="${user.id}">
		<div class="form-group">
			<label class="col-sm-1 control-label">用户名</label>
			<div class="col-sm-5">${user.username}</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">真实姓名</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_name" name="name" value="${user.name}">
			</div>
		</div>
		<div class="form-group">
			<label for="input_phone" class="col-sm-1 control-label">电话*</label>
			<div class="col-sm-6">
				<input type="tel" minlength="11" maxlength="11" class="layui-input" id="input_phone" name="phone" value="${user.phone}" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_address" class="col-sm-1 control-label">地址</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_address" name="address" value="${user.address}">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<button type="submit" class="layui-btn">提交修改</button>
			</div>
		</div>
	</form>
	
	<span style="color:red;">${msg}</span>
	
</div>
<script src="layui/layui.js" charset="utf-8"></script>
</body>
</html>
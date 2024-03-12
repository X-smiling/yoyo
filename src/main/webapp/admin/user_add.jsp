<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>客户添加</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="css/bootstrap.css" />
	<link rel="stylesheet" href="layui/css/layui.css"  media="all">
</head>
<body>
<div class="container-fluid">

	<%@include file="header.jsp"%>

	<br><br>
	<form class="form-horizontal" action="userAdd" method="post">
		<div class="form-group">
			<label for="input_username" class="col-sm-1 control-label">用户名*</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_username" name="username" value="" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_password" class="col-sm-1 control-label">密码*</label>
			<div class="col-sm-6">
				<input type="password" class="layui-input" id="input_password" name="password" value="" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">真实姓名</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_name" name="name" value="">
			</div>
		</div>
		<div class="form-group">
			<label for="input_tel" class="col-sm-1 control-label">手机号码*</label>
			<div class="col-sm-6">
				<input type="tel" minlength="11" maxlength="11" class="layui-input" id="input_tel" name="phone" value="" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_address" class="col-sm-1 control-label">地址</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_address" name="address" value="">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<button type="submit" class="layui-btn">提交保存</button>
			</div>
		</div>
	</form>
	
	<span style="color:red;">${msg}</span>
</div>
<script src="layui/layui.js" charset="utf-8"></script>
</body>
</html>
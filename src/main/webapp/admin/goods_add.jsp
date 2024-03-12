<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>商品添加</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="css/bootstrap.css" />
	<link rel="stylesheet" href="layui/css/layui.css"  media="all">
</head>
<body>
<div class="container-fluid">
	<%@include file="header.jsp"%>

	<c:if test="${msg!=null}"><div class="alert alert-danger">${msg}</div></c:if>
	<br><br>
	<form class="form-horizontal" action="goodsAdd" method="post" enctype="multipart/form-data">
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">名称*</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_name" name="name"  required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_price" class="col-sm-1 control-label">价格*</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_price" name="price" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_intro" class="col-sm-1 control-label">介绍*</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_intro" name="intro" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_stock" class="col-sm-1 control-label">库存*</label>
			<div class="col-sm-6">
				<input type="text" class="layui-input" id="input_stock" name="stock" value="10" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_file" class="col-sm-1 control-label">封面图片*</label> 
			<div class="col-sm-6">
				<input type="file" name="cover"  id="input_file" required="required">推荐尺寸: 500 * 500
			</div>
		</div>
		<div class="form-group">
			<label for="input_file1" class="col-sm-1 control-label">详情图片1*</label> 
			<div class="col-sm-6">
				<input type="file" name="image1"  id="input_file1" required="required">推荐尺寸: 500 * 500
			</div>
		</div>
		<div class="form-group">
			<label for="input_file2" class="col-sm-1 control-label">详情图片2*</label> 
			<div class="col-sm-6">
				<input type="file" name="image2"  id="input_file2" required="required">推荐尺寸: 500 * 500
			</div>
		</div>
		<div class="form-group">
			<label for="select_topic" class="col-sm-1 control-label">商品种类*</label>
			<div class="col-sm-6">
				<select class="form-control" id="select_topic" name="typeId">
					<c:forEach var="type" items="${typeList}">
						<option value="${type.id}">${type.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<button type="submit" class="layui-btn">提交保存</button>
			</div>
		</div>
	</form>
</div>
<script src="layui/layui.js" charset="utf-8"></script>
</body>
</html>
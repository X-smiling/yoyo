<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>个人中心</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/simpleCart.min.js"></script>
</head>
<body>

	<jsp:include page="header.jsp"/>
	
	<!--account-->
	<div class="account">
		<div class="container">
			<div class="register">
				<c:if test="${msg!=null}"><div class="alert alert-danger">${msg}</div></c:if>
				<c:if test="${msg2!=null}"><div class="alert alert-success">${msg2}</div></c:if>
				<form action="my" method="post">
					<input type="hidden" name="id" value="${user.id}">
					<input type="hidden" name="type" value="1">
					<div class="register-top-grid">
						<h3>个人中心</h3>
						
						<h4>收货信息</h4>
						<div class="input">
							<span>收货人<label></label></span>
							<input type="text" name="name" value="${user.name}" placeholder="请输入收货人">
						</div>
						<div class="input">
							<span>收货电话</span>
							<input type="tel" minlength="11" maxlength="11" name="phone" value="${user.phone}" placeholder="请输入收货电话"> 
						</div>
						<div class="input">
							<span>收货地址</span>
							<input type="text" name="address" value="${user.address}" placeholder="请输入收货地址"> 
						</div>
						<div class="register-but text-center">
						   <input type="submit" value="提交">
						</div>
					</div>
				</form>			
				<hr>
				<form action="my" method="post"> 
					<input type="hidden" name="id" value="${user.id}">
					<input type="hidden" name="type" value="2">
					<div class="register-top-grid">
						<h4>安全信息</h4>
						<div class="input">
							<span>原密码</span>
							<input type="text" name="password" placeholder="请输入原密码"> 
						</div>
						<div class="input">
							<span>新密码</span>
							<input type="text" name="passwordNew" placeholder="请输入新密码"> 
						</div>
						<div class="clearfix"> </div>
						<div class="register-but text-center">
						   <input type="submit" value="提交">
						</div>	
					</div>
				</form>
				<div class="clearfix"> </div>
			</div>
	    </div>
	</div>
	<!--//account-->

	<jsp:include page="footer.jsp"/>
	<script>
		$(function () {
			setTimeout(function () {
				$(".alert-success").hide()
			}, 2000)

		})
	</script>
</body>
</html>
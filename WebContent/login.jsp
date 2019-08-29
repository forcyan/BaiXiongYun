<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login</title>
<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>
<body id="login">
    <div class="container">
      <form class="form-signin" name="loginForm"  method="post" action="" novalidate>
        <h2 class="form-signin-heading">请登录</h2>
        <label for="inputAccount" class="sr-only">账号</label>
        <input type="text" name="userName" id="userName" class="form-control" placeholder="邮箱地址" >
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" name="pwd" id="pwd" class="form-control" placeholder="密码">
        <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label>
        <button class="btn btn-large btn-primary" onclick="ToLogin()">Login</button>
        <button class="btn btn-large" data-toggle="modal" data-target="#signupModel">注册</button>
      </form>

	</div> <!-- /container -->
	<!-- 注册模态框  -->
	<div class="modal fade" id="signupModel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">注册</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" name="signupForm" method="post"
						action="" novalidate>
						<fieldset>
							<legend>个人信息填写</legend>
							<div class="control-group">
								<label class="control-label" for="focusedInput">邮箱</label>
								<div class="controls">
									<input class="input-xlarge focused" id="signupAccount" name="signupAccount"
										type="text">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="focusedInput">用户名</label>
								<div class="controls">
									<input class="input-xlarge focused" id="signupUsername" name="signupUsername"
										type="text">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="focusedInput">密码</label>
								<div class="controls">
									<input class="input-xlarge focused" id="signupPwd" name="signupPwd"
										type="password">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="focusedInput">确认密码</label>
								<div class="controls">
									<input class="input-xlarge focused" id="signupRepwd" name="signupRepwd"
										type="password">
								</div>
							</div>
							<div class="form-actions">
								<button class="btn btn-large btn-primary" onclick="ToSignup()">提交</button>
								<button class="btn btn-large" data-dismiss="modal">取消</button>
							</div>
						</fieldset>
						<c:if test="${not empty message }">
							<div class="alert alert-danger" id="registerError">
								<strong>${message }</strong>
							</div>
						</c:if>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script src="vendors/jquery-1.9.1.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
	<script type="text/javascript" src="js/signup.js"></script>
</body>
</html>
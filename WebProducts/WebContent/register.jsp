<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCUTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
	<meta name="viewpoint" content="width=device-width,initial-scale=1.0,user-scalable=no,minimum-scale=1,maximum-scale=1">
	<title>壁纸分享平台</title>
	<link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="css/main.css">

	
	</head>
	<body>
		<div class="jumbotron" >
		  <div class="logo"></div>
		  <h1 class="text-center">注册</h1>
		</div>
		<div class="container content">
			<div class="row">
					<form class="form-horizontal" action="MainServlet.do?flag=addUser" post="post">
						  <div class="form-group">
						    <label for="username" class="col-sm-2 col-sm-offset-2 control-label">邮箱</label>
						    <div class="col-sm-4">
						      <input type="email" class="form-control" id="username" name="username" placeholder="邮箱" autocomplete="on">
						    </div>
						    <div class="col-sm-4">
						      	<p class="text-left" id="prompt1"></p>
						    </div>
						  </div>
						  <div class="form-group">
						    <label for="password" class="col-sm-2  col-sm-offset-2 control-label">密码</label>
						    <div class="col-sm-4 ">
						      <input type="password" class="form-control" id="password" name="password" placeholder="密码">
						      <input type="hidden"   name = "flag" value="addUser"/>
						    </div>
						  </div>
						  <div class="form-group">
						    <label for="password" class="col-sm-2  col-sm-offset-2 control-label">再次输入密码</label>
						    <div class="col-sm-4 ">
						      <input type="password" class="form-control" id="password2" placeholder="再次输入密码">
						    </div>
						     <div class="col-sm-4">
						      	<p class="text-left" id="prompt2"></p>
						  	 </div>
						  </div>
						  <div class="form-group">
						    <div class="col-sm-offset-5 col-sm-2">
						      <button type="submit" class="btn btn-primary btn-block disabled" id="registBtn">注册</button>
						  </div>
					 </div>
				</form>
			</div>
		</div>
		<footer class="footer">
      <div class="col-md-2 col-sm-2 col-xs-4"><img src="logo.jpg" class="img-responsive"><small class="text-center">壁纸分享平台</small></div>
      <div class="col-md-offset-1 col-md-2 col-sm-offset-1 col-sm-2 col-xs-offset-1 col-xs-4"><ul><h3>小组成员:</h3><li class="text-right">龚星衡</li><li class="text-right">武子涵</li><li class="text-right">赵育才</li><li class="text-right">周游</li><li class="text-right">邓巧艺</li></ul></div><div class="col-md-offset-1 col-md-4"><div class="row"><ul><h3>版权&copy:</h3><li class="text-right">东北大学软件学院软件工程赴日班猩光闪耀小组</li></ul></div><div class="row"><ul><h3>联系方式:</h3><li class="text-center">805080825@qq.com</li></ul></div></div>
      
    	</footer>
		 <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
   <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
		<script src="js/regist.js"></script>
		
	</body>
</html>
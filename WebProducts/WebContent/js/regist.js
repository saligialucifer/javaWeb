
$(document).ready(function(){
	var registable = false;
	$("#username").blur(function(){
		var username = $("#username").val();
		if(username.length !=0){
			$.post("MainServlet.do",
			  {
				flag:"legalUserName",
			    name: username
			  },function(data){
			  	if(data == "0"){
			  		registable = false;
			  		$("#prompt1").text("该用户名已被注册！");
			  	}else{
			  		registable = true;
			  		$("#prompt1").text("");
			  	}
			  	}
		  );
		}else{
			registable = false;
		}
	});

	$("#password2").blur(function(){
		var password1 = $("#password").val();
		var password2 = $("#password2").val();
		if(password1!=password2){
			$("#prompt2").text("两次密码输入不一致!");
			if(registable==true){
				registable=false;
			}
		}else{
			$("#prompt2").text("");
		}
	});

	setInterval(function(){
		if(registable == true){
			$("#registBtn").removeClass("disabled");
		}
	},300);
});
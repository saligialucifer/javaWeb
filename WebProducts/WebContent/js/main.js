$(document).ready(function(){
	
	
	
	
	$.get("MainServlet.do?flag=auto",function(data,status){
	    var obj = JSON.parse(data);
		if(obj.login == false){
		}else{
			$("#nickname").text(obj.username);
			$("#sign").text(obj.sign);
			$(".head_pic").attr("src","headpicture/" + obj.head_path);
			$(".user-branch").show();
			 $(".nav-branch").hide();
			 $("#loginUI").modal('hide');
		}
	  });
});

$(document).ready(function(){
	
	$(".myImg").click(function(){
		var src = $(this).attr("src");
		$("#modalPic").attr("src",src);
	});
	
	$(window).scroll(function () {
		var a = document.getElementById("header").offsetTop + $("#header").outerHeight();
		if (a >= $(window).scrollTop() && a < ($(window).scrollTop()+$(window).height())) {
			$("#navfixed").slideUp("fast");
		}else{
				$("#navfixed").slideDown("fast");
		}
	});

	$(".classify").hover(function(){
		if(!$(".classify-content").is(":animated")){
			$(".classify-content").slideDown("fast");
		}
	},function(){
		$(".classify-content").slideUp("fast");
	});

	$(".collect-btn").click(function(){
		var picdic = $(this).parents("div[class=thumbnail]").find("img[name=pic-sel]").attr("src");
		var thebtn = $(this);
		
		$.post("MainServlet.do?flag=favour",{pic_dic : picdic},function(json){
			var obj = JSON.parse(json);
			if(obj.islogin == false){
				$("#loginUI").modal("show");
			 
			}else{
				if(obj.iscollected==true){
					thebtn.text("已收藏");
					thebtn.addClass("disabled");
				}
			}
			
			
		});
	})
	
	$(".collect-btn-classify").click(function(){
		var thebtn = $(this);
		var id = $(":checked").attr("id");
		var picdic=$("label[for="+id+"] img").attr("src");
		$.post("MainServlet.do?flag=favour",{pic_dic : picdic},function(json){
			var obj = JSON.parse(json);
			if(obj.islogin == false){
				$("#loginUI").modal("show");
			 
			}else{
				if(obj.iscollected==true){
					$(".collect-result").html("收藏成功！").fadeIn(500).fadeOut(500);
				}else{
					$(".collect-result").html("已收藏过该图片！").fadeIn("fast").fadeOut("fast");
				}
			}
			
			
		});
	})
	
	$(".discollect-btn").click(function(){
		var picdic = $(this).parents("div[class=thumbnail]").find("img[name=pic-sel]").attr("src");
		var thebtn = $(this);
		$.post("MainServlet.do?flag=collected_cancel",{pic_dic : picdic},function(json){
					thebtn.text("已删除");
					thebtn.addClass("disabled");			
		});
	})

	$(".head-pic").click(function(){
		if($("#userImf").is(":hidden")){
			$("#userImf").fadeIn("fast");
		}
		else{
			$("#userImf").fadeOut("fast");
		}
	})

	$("#login").click(function(){
		var user = $("#username").val();
		var psw = $("#password").val();
		var remember = $("input[name='remember']").is(':checked');
		
		
		$.post("MainServlet.do?flag=login",{username:user,password:psw,remember:remember},function(json){
			var obj = JSON.parse(json);
			
			if(obj.login == false){
				
			 $("#isLogin").html("用户名或密码错误");
			}else{
				
				$("#nickname").text(obj.username);
				$("#sign").text(obj.sign);
				$(".head_pic").attr("src","headpicture/" + obj.head_path);
				$(".user-branch").show();
				 $(".nav-branch").hide();
				 $("#loginUI").modal('hide');
				 window.location.reload();
			}
			
			
		});
		
		
	})
	
	$(".btn-line button[type=submit]").click(function(){
		var id = $(":checked").attr("id");
		var picdic=$("label[for="+id+"] img").attr("src");
		$("#picdic").val(picdic);
		$("form").submit();
		
	});
	$(".collect-btn2").click(function(){
		var thebtn = $(this);
		var id = $(":checked").attr("id");
		var picdic=$("label[for="+id+"] img").attr("src");
		$.post("MainServlet.do?flag=favour",{pic_dic:picdic});
		
	});

});
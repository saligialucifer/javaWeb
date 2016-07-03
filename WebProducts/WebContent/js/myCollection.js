$(document).ready(function(){
	var width,height;
	width = $("#headpic").outerWidth();
	height = $("#headpic").outerHeight();
	$("#upload").width(width).height(height);
	$("#upload").change(function(){
		var objUrl = getObjectURL(this.files[0]);
		if (objUrl) {
		$("#headpic").attr("src", objUrl) ;
		}
	});

	function getObjectURL(file) {
	var url = null ; 
	if (window.createObjectURL!=undefined) { // basic
		url = window.createObjectURL(file) ;
	} else if (window.URL!=undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file) ;
	} else if (window.webkitURL!=undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file) ;
	}
	return url ;
	}
	
	$(".delete-btn").click(function(){
		var picdic = $(this).parents("div[class=thumbnail]").find("img[name=pic-sel]").attr("src");
		var thebtn = $(this);
		var base=$(this).parents("div[name=base]");
		
		$.post("DeleteServlet?flag=delete",{pic_dic : picdic},function(){
			
			base.hide();
			
		});
	})
	
	$("#btn-change").click(function(){

		 window.location.reload();
	})
	
	
});


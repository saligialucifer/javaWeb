<%@ page language="java" import="java.util.*,bean.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <%String picture_path   ="picture/";%>
  	<meta charset="UTF-8">
    <title>壁纸分享平台</title>
  	<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/main.css">
  </head>
  <body>
  	<header id="header">
      <div class="header-discribe">
        <h1>Welcome to picture sharing platform!</h1>
      </div>
      <div class="nav-1" id="nav1">
         <div class="col-md-offset-1 col-md-1">
          <img src="logo.jpg" class="img-responsive  hidden-sm hidden-xs">
        </div>
        <div class="col-md-offset-5 col-md-2 nav-branch" >
          <ul class="branch">
            <li><a href="" data-toggle="modal" data-target="#loginUI">登录</a></li>
            <li><a href="register.jsp" >注册</a></li>
          </ul>
      </div>
      <div class="col-md-offset-5 col-md-1 user-branch" hidden>
        <div class="head-pic"><img class=head_pic src="" alt="头像" ></div>
      </div>

      <div class="col-md-1 user-branch" hidden><h5 class="text-left" style="color:white">欢迎登录！</h5></div>
      <div class="user-imf col-md-offset-7 col-md-2" id="userImf" hidden><div class="container"><p><h5>昵称:<small id="nickname"></small></h5></p><p><h5>个性签名:<small id="sign"></small></h5></p>
      <div  class="btn-line"><a class="btn btn-warning btn-sm" href="UploadHandleServlet?flag=page&flag1=page&pageNowCollection=1&pageNowUpload=1&bool=1" role="button">我的收藏</a><form action="MainServlet.do?flag=sendmail" method="post"><button type=submit class="btn btn-info btn-sm"  role="button">绑定邮箱</button></form><form action="MainServlet.do?flag=logout" method="post"><button type=submit class="btn btn-sm" id="quit">退出</button></div></form></div></div>
      <div class="col-md-3">
        <form class="navbar-form navbar-left" role="search" action="SearchServlet?flag=paging&pageNow=1&bool=0" method="post">
         <div class="form-group hidden-sm hidden-xs">
            <input type="text" class="form-control" placeholder="Search" name="keyword">
         </div>
         <button type="submit" class="btn btn-default search-btn hidden-sm hidden-xs"><span class="glyphicon glyphicon-search"></button>
      </form> 
      </div>
      
    </div>
      <div class="nav-2" id="navfixed" hidden>
         <div class="col-md-offset-1 col-md-1">
          <img src="logo.jpg" class="img-responsive hidden-sm hidden-xs">
        </div>
        <div class="col-md-offset-5 col-md-2 nav-branch">
          <ul class="branch">
            <li><a href="" data-toggle="modal" data-target="#loginUI">登录</a></li>
            <li><a href="register.jsp" >注册</a></li>
          </ul>
      </div>
      <div class="col-md-offset-5 col-md-1 user-branch" hidden>
        <div class="head-pic"><img class="head_pic" src="" alt="头像" class="img-responsive"></div>
      </div>

      <div class="col-md-1 user-branch" hidden><h5 class="text-left" style="color:white">欢迎登录！</h5></div>
      <div class="col-md-3">
        <form class="navbar-form navbar-left" role="search" action="SearchServlet?flag=paging&pageNow=1&bool=0" method="post">
         <div class="form-group hidden-sm hidden-xs">
            <input type="text" class="form-control" placeholder="Search" name="keyword">
         </div>
         <button type="submit" class="btn btn-default search-btn hidden-sm hidden-xs"><span class="glyphicon glyphicon-search"></button>
      </form> 
      </div>
      
    </div>
    
    <div class="modal fade" id="loginUI" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
               <div class="modal-dialog">
                  <div class="modal-content">
                     <div class="modal-header">
                        <button type="button" class="close" 
                           data-dismiss="modal" aria-hidden="true">
                              &times;
                        </button>
                        <h4 class="modal-title text-center" id="myModalLabel">
                           登录
                        </h4>
                     </div>
                     
                      <div class="modal-body">
                        <div class="container">
                          <div class="row">
                              <form class="form-horizontal" id="login_form" >
                                  <div class="form-group">
                                    <label for="username" class="col-md-2 col-sm-2 col-xs-2 control-label">用户名</label>
                                    <div class="col-md-3 col-sm-6 col-xs-9">
                                      <input type="text" class="form-control" id="username" placeholder="请输入用户名" required>
                                    </div>
                                  </div>
                                  <div class="form-group">
                                    <label for="password" class="col-md-2 col-sm-2 col-xs-2 control-label">密码</label>
                                    <div class="col-md-3 col-sm-6 col-xs-9">
                                      <input type="password" class="form-control"  id="password" placeholder="请输入密码" required>
                                    </div>
                                  </div>
                                  <div class="col-md-offset-2 col-xs-offset-2 col-sm-offset-2"><div class="checkbox">

                                    <label>
                                    <input name="remember" type="checkbox"> 记住我
                                    </label>
                                 </div></div>
                                  <br>
                                  <div id="isLogin" class="col-md-offset-2 col-xs-offset-2 col-sm-offset-2"></div>
                                  <br>
                                  <div class="form-group">
                                    <div class="col-md-offset-2 col-md-3 col-xs-offset-2 col-xs-3 col-sm-offset-2 col-sm-3">
                                      <button type="button" id="login" class="btn btn-primary btn-block container">登录</button>
                                  </div>
                               </div>
                            </form>
                          </div>
                        </div>
                     </div>
                     
                  </div>
            </div>
        </div>
  	</header>
    <section class="section1">
      <h1 class="text-center">热图推荐</h1>
      <br>
      <div class="container"><div id="myCarousel" class="carousel slide">
           <!-- 轮播（Carousel）指标 -->
           <ol class="carousel-indicators">
              <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
              <li data-target="#myCarousel" data-slide-to="1"></li>
              <li data-target="#myCarousel" data-slide-to="2"></li>
           </ol>   
           <!-- 轮播（Carousel）项目 -->
           <div class="carousel-inner">
           <%
           		ArrayList<PictureBean> PictureSrc = new MethodBean().getThreePictureOrderByClicks();
                
           		for(int i = 0; i < 3; i++)
				{
           			PictureBean picturebean = (PictureBean)PictureSrc.get(i);
					if(i == 0)
					{
						%><div class="item active"><%
					}else if(i == 1 || i ==2)
					{
						%><div class="item"><%
					}
           %>
           			<div class="carousel-pic"><img src="<%=picture_path+picturebean.getPath()%>" class="img-responsive" alt="slide <%=(i + 1)%>"></div>
           			<div class="carousel-caption"><%=picturebean.getPath()%></div>
           			</div>
           <%
				}
           %>
           </div>
           <!-- 轮播（Carousel）导航 -->
           <a class="carousel-control left" href="#myCarousel" 
              data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"></span></a>
           <a class="carousel-control right" href="#myCarousel" 
              data-slide="next"> <span class="glyphicon glyphicon-chevron-right"></span></a>
        </div> </div>

    </section>

	<!-- 图片展示循环 -->
	<section class="classify">
	<div class="modal fade" id="picModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	       <div class="modalContent modal-body">
	       	<img id="modalPic" alt="图片加载失败" src="" class="img-responsive" height="768">
	        </div>
    </div>

      <div class="container">
      <%
      ArrayList<KeywordBean> keywordlist = new MethodBean().returnKeywordList();	
      for(int i = 0; i < keywordlist.size(); i++)
      	{
      		String keyword = keywordlist.get(i).getKeyword();
      		
      		%><div class="row classify-line"><%
      		%><div class="col-md-11"><h4>分类: <%=keyword%></h4></div><%
      		%><div><a class="btn btn-info" href="SearchServlet?flag=paging&pageNow=1&keyword=<%=keyword%>&bool=1">更多</a></div><%
      		%><div class="row"><%
      		ArrayList<PictureBean> pictureList = new MethodBean().getThreeRandomPictureByKeyword(keyword);
      		for(int j = 0; j < 3; j++)
      		{
      			MethodBean methodbean = new MethodBean();
      			PictureBean picturebean = (PictureBean)pictureList.get(j);
      			String pathString = picturebean.getPath();
      			UserBean userbean = methodbean.getUserbean(picturebean.getUploadName());
      			%>
      			<div class="col-sm-6 col-md-4">
              <div class="thumbnail">
                <div class="pic-animate">
                  <img src="<%=picture_path+pathString%>" class="img-responsive myImg" data-toggle="modal" data-target="#picModal" name="pic-sel" width="100%" height="auto" alt="图片加载失败">
                </div>
                <div class="caption">
                  <h3><%=picturebean.getDescribe()%></h3>
                  <p><%=userbean.getNickname()%></p>
                  <p><%=picturebean.getUploadTime()%></p>
                  <%
                  	String account = (String) session.getAttribute("account");
				  	//System.out.println("account:" + account);
				  	boolean bool = new MethodBean().isPicCollected(account, pathString);
				  	if(bool) {    %>
						<p class="col-md-offset-5 col-md-4"><button href="#" class="btn btn-primary collect-btn disabled">已收藏</button></p>
					<% } else {   %>
						<p class="col-md-offset-5 col-md-4"><button href="#" class="btn btn-primary collect-btn "><span class="glyphicon glyphicon-star"></span>收藏</button></p>
					<% }
				  %>
                  
                  <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    下载<span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                      <li><a href="DownloadServlet?flag=download&path=<%=pathString%>"><%=picturebean.getPictureDpi()%></a></li>
                      <li><a href="#">暂无</a></li>
                      <li><a href="#">暂无</a></li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
      			<%
      			
      		}
      		%></div><%
      		%></div><%
      	}
      %>
      
	  </div>
    </section>
    <footer class="footer">
      <div class="container"><div class="col-md-2 col-sm-2 col-xs-4"><img src="logo.jpg" class="img-responsive"><small class="text-center">壁纸分享平台</small></div>
      <div class="col-md-offset-1 col-md-2 col-sm-offset-1 col-sm-2 col-xs-offset-1 col-xs-4"><ul><h3>小组成员:</h3><li class="text-right">龚星衡</li><li class="text-right">武子涵</li><li class="text-right">赵育才</li><li class="text-right">周游</li><li class="text-right">邓巧艺</li></ul></div><div class="col-md-offset-1 col-md-4"><div class="row"><ul><h3>版权&copy:</h3><li class="text-right">东北大学软件学院软件工程赴日班猩光闪耀小组</li></ul></div><div class="row"><ul><h3>联系方式:</h3><li class="text-center">805080825@qq.com</li></ul></div></div></div>
      
    </footer>
     <script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
  	 <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
     <script src="js/main.js"></script>
  </body>
</html>
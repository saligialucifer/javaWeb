<%@ page language="java" import="java.util.*, bean.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


String account = new String("");
boolean bool = false;

UserBean user = new UserBean();
account = (String) session.getAttribute("account");

System.out.println("classaccount:" + account);
if(!"".equals(account) && account != null) {
	bool = true;
	System.out.println(bool);
	user = new MethodBean().getUserbean(account);
}
	


%>


<!DOCUTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
  	<meta charset="UTF-8">
    <title>图片搜索结果</title>
  	<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/classify.css">
  </head>
  <body>
  	<header id="header">
      <div class="nav-1" id="nav1">
         <div class="col-md-offset-1 col-md-1">
          <img src="logo.jpg" class="img-responsive  hidden-sm hidden-xs">
        </div>
        
        <%
        if(!bool) {
        %> 
        <div class="col-md-offset-5 col-md-2 nav-branch" 12 >
          <ul class="branch">
            <li><a href="" data-toggle="modal" data-target="#loginUI">登录</a></li>
            <li>注册</li>
          </ul>
      </div>
        <%
        } else {
        %>
        <div class="col-md-offset-5 col-md-1 user-branch" >
        <div class="head-pic"><img src="headpicture/<%=user.getHead_path() %>" alt="头像" class="img-responsive"></div>
      	</div>

     	 <div class="col-md-1 user-branch" ><h5 class="text-left" style="color:white">欢迎登录！</h5></div>
      	 <div class="user-imf col-md-offset-7 col-md-2" id="userImf" hidden><div class="container"><p class="nickname"><h5>昵称:<small><%=user.getNickname() %></small></h5></p><p class="sign"><h5>个性签名:<small><%=user.getSign() %></small></h5></p><p class="btn-line"></p><button class="btn btn-sm">我的收藏</button><button class="btn btn-sm">绑定邮箱</button><button class="btn btn-sm" id="quit">退出</button></div></div>
        <%
        }
        %>
        
      
     
      <div class="col-md-3">
        <form class="navbar-form navbar-left" role="search" action="SearchServlet?flag=paging&pageNow=1&bool=0" method="post">
         <div class="form-group hidden-sm hidden-xs">
            <input type="text" class="form-control" placeholder="Search" name="keyword">
         </div>
         <button type="submit" class="btn btn-default search-btn hidden-sm hidden-xs"><span class="glyphicon glyphicon-search"></button>
      </form> 
      </div>
      
    </div>
      <div class="nav-2" id="navfixed" 12>
         <div class="col-md-offset-1 col-md-1">
          <img src="logo.jpg" class="img-responsive hidden-sm hidden-xs">
        </div>
        
        <%
        if(!bool) {
        %> 
        <div class="col-md-offset-5 col-md-2 nav-branch">
          <ul class="branch">
            <li><a href="" data-toggle="modal" data-target="#loginUI">登录</a></li>
            <li>注册</li>
          </ul>
      	</div>
        <%
        } else {
        %>
        <div class="col-md-offset-5 col-md-1 user-branch" >
        <div class="head-pic"><img src="<%=user.getHead_path() %>" alt="头像" class="img-responsive"></div>
      	</div>

      	<div class="col-md-1 user-branch" ><h5 class="text-left" style="color:white">欢迎登录！</h5></div>
      
        <%
        }
        %>
        
     
      
      
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
                              <form class="form-horizontal">
                                  <div class="form-group">
                                    <label for="username" class="col-md-2 col-sm-2 col-xs-2 control-label">用户名</label>
                                    <div class="col-md-3 col-sm-6 col-xs-9">
                                      <input type="text" class="form-control" id="username" placeholder="请输入用户名" required>
                                    </div>
                                  </div>
                                  <div class="form-group">
                                    <label for="password" class="col-md-2 col-sm-2 col-xs-2 control-label">密码</label>
                                    <div class="col-md-3 col-sm-6 col-xs-9">
                                      <input type="password" class="form-control" id="password" placeholder="请输入密码" required>
                                    </div>
                                  </div>
                                  <div class="col-md-offset-2 col-xs-offset-2 col-sm-offset-2"><div class="checkbox">

                                    <label>
                                    <input type="checkbox"> 记住我
                                    </label>
                                 </div></div>
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
  	<section class="container">
  	<%
  		ArrayList alPicture = (ArrayList<PictureBean>)request.getAttribute("result");
  		ArrayList alPicture1 = (ArrayList<PictureBean>)request.getAttribute("result1");
        String keyword = (String)request.getAttribute("keyword");
        String filename = (String)request.getAttribute("filename");
        session.setAttribute("keyword", keyword);
        String picture_path   ="picture/";
  	%>
  	<div class="text-center">
  		<br><br><br>
  		<h1><%=keyword%> 的结果为：</h1>
  	</div>
  		<div class="row btn-line">
  		<ul>
  			<li class="collect-result"></li>
  			<li><button href="#" class="btn btn-primary collect-btn-classify"><span class="collect-btn2 glyphicon glyphicon-star"></span>收藏</button></li>
  			<li><form action="DownloadServlet?flag=download1" method="post">
  					<input type="text" id="picdic" name="pic" hidden>	
                    <button type="submit" class="btn btn-default"  aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-download-alt"></span>
                    下载
                    </button>
                   </form></li>
  		</ul>
  		
              
      
                    
                  
              
  		</div>
  		<div class="slider">
  		<%
  			for(int i = 0; i < alPicture.size(); i++)
  			{
  				PictureBean picturebean = (PictureBean)alPicture.get(i);
  				if(i == 0)
  				{
  					%><input type="radio" name="slide_switch" id="<%=i%>" checked="checked"/><%
  				}else
  				{
  					%><input type="radio" name="slide_switch" id="<%=i%>"/><%
  				}
  		%>
  				<label for="<%=i%>">
  					<img src="<%=picture_path+picturebean.getPath() %>" width="140" height="86"/>
  				</label>
  				
  					<img src="<%=picture_path+picturebean.getPath() %>" width="1024" height="640"/>
  				
  		<%
  			}
  		%>
	</div>
	
  	</section>
  	
  	
  	
  	
  	
  	
    <div class="container text-center">
    <ul class="pagination">
    <%
  			//显示页数的超链接
  			session.setAttribute("result1", alPicture1);
  			int pageNow = Integer.parseInt(request.getAttribute("pageNow").toString());
  			//得到pageCount
  			int pageCount = Integer.parseInt(request.getAttribute("pageCount").toString());
  			if(pageNow != 1)
  			{
  				%><li><a href="SearchServlet?&flag=paging&pageNow=<%=(pageNow - 1)%>&bool=1" class="a_post">&laquo;</a></li><%
  			}
  			for(int i = 0; i < pageCount; i++)
  			{
  				if((i + 1) == pageNow)
  				{
  					%><li class="active"><a><%=(i + 1)%></a></li><%
  				}
  				else
  				{
  					%><li><a href="SearchServlet?&flag=paging&pageNow=<%=(i + 1)%>&bool=1" class="a_post"><%=(i + 1)%></a></li><%
  				}
  			}
  			if(pageNow != pageCount)
  			{
  				%><li><a href="SearchServlet?&flag=paging&pageNow=<%=(pageNow + 1)%>&bool=1" class="a_post">&raquo;</a></li><%
  			}
  		%>
      </ul>
    </div>
    
    

    
  	<footer class="footer">
      <div class="container"><div class="col-md-2 col-sm-2 col-xs-4"><img src="logo.jpg" class="img-responsive"><small class="text-center">壁纸分享平台</small></div>
      <div class="col-md-offset-1 col-md-2 col-sm-offset-1 col-sm-2 col-xs-offset-1 col-xs-4"><ul><h3>小组成员:</h3><li class="text-right">龚星衡</li><li class="text-right">武子涵</li><li class="text-right">赵育才</li><li class="text-right">周游</li><li class="text-right">邓巧艺</li></ul></div><div class="col-md-offset-1 col-md-4"><div class="row"><ul><h3>版权&copy:</h3><li class="text-right">东北大学软件学院软件工程赴日班猩光闪耀小组</li></ul></div><div class="row"><ul><h3>联系方式:</h3><li class="text-center">805080825@qq.com</li></ul></div></div></div>
      
    </footer>
  	<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
  	 <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
     <script src="js/main.js"></script>
  </body>
</html>
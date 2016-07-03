<%@ page language="java" import="java.util.*,bean.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<!DOCUTYPE html>
<html>
  <head>
  <%
  	String picture_path   ="picture/";
  	String account = (String)request.getAttribute("account");
    MethodBean m =new MethodBean();

    ArrayList<PictureBean> alPictureCollection = (ArrayList<PictureBean>)request.getAttribute("alPictureCollection");
    ArrayList<PictureBean> alPictureCollectionPageNow = (ArrayList<PictureBean>)request.getAttribute("alPictureCollectionPageNow");
    ArrayList<PictureBean> alPictureUpload = (ArrayList<PictureBean>)request.getAttribute("alPictureUpload");
    ArrayList<PictureBean> alPictureUploadPageNow = (ArrayList<PictureBean>)request.getAttribute("alPictureUploadPageNow");
    String bool =(String)request.getAttribute("bool");
    System.out.println("sb"+alPictureCollection.size());
    System.out.println(bool);
    alPictureCollection = m.getCollectedPicture(account);
    UserBean u =m.getUserbean(account); 
    String nickname = u.getNickname();
    
  %>
  	<meta charset="UTF-8">
    <title>我的收藏</title>
  	<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/myCollection.css">
  </head>
  <body>
  	<section class="container">
  	  <div class="user-imf">
  	  	<div class="row headimf">
          <form enctype="multipart/form-data" action = "UploadHandleServlet?flag=user_pic&flag1=page&pageNowCollection=1&pageNowUpload=1&bool=1"method="post">
  	  		<div class="col-md-4"><img id="headpic" src="headpicture/<%=u.getHead_path()%>" class="img-responsive"><input type="file" name = "pic" id="upload" class="upload" data-toggle="tooltip" data-placement="buttom" title="上传头像"></div>

  	  		<div class="col-md-8"><p><h3>昵称：</h3><textarea class="nickname" name="nickname" placeholder="请设置昵称" rows=1 data-toggle="tooltip" data-placement="buttom" title="修改昵称"><%=nickname%></textarea></p><p><h3>个性签名：</h3></p><textarea class="sign" name="sign" placeholder="请设置签名" rows=4 data-toggle="tooltip" data-placement="buttom" title="修改签名"><%=u.getSign()%></textarea>
  	  		</div>
  	  		<div class="col-md-offset-10 col-md-2"><button type="submit" class="btn btn-success btn-md" id="btn-change">修改信息</button>
			</div>
          </form>
  	  	</div>
        <br>
        <br>
        <br>

        <ul  class="nav nav-tabs">
             <li <% if(bool.equals("1")){%>class="active"<%}%>>
             <a href="#myCollection" class="glyphicon glyphicon-book" data-toggle="tab">&ensp;我的收藏</a>
             </li <%if(bool.equals("0")){%>class="active"<%}%>>
             <li><a href="#myUpload" class="glyphicon glyphicon-tower" data-toggle="tab">&ensp;我上传的</a></li>
        </ul>
  	  	<div id="myTabContent" class="tab-content">
          <div class="tab-pane fade <%if(bool.equals("1")){%>in active<%}%>" id="myCollection">
          <div class="row container">
   
          <%
          	for(int i = 0; i < alPictureCollectionPageNow.size(); i++)
          	{
          		PictureBean picturebean = alPictureCollectionPageNow.get(i);
  	  	  %>
          	<div class="col-md-4" name="base">
              <div class="thumbnail">
                <div class="pic-animate">
                  <img src="<%=picture_path+picturebean.getPath()%>" class="img-responsive" name="pic-sel" alt="图片加载失败">
                </div>
                <div class="caption">
                  <h3><%if(picturebean.getDescribe()==null){%><%}else{%><%=picturebean.getDescribe()%><%}%></h3>
                  <p><%=picturebean.getUploadName()%></p>
                  <p><%=picturebean.getUploadTime()%></p>
                  <p class="col-md-offset-5 col-md-4 col-sm-offset-5 col-sm-4 col-xs-offset-5 col-xs-4"><button class="btn btn-primary delete-btn"><span class="glyphicon glyphicon-remove"></span>&ensp;删除</button></p>
                  <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    下载<span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                      <li><a href="DownloadServlet?flag=download&path=<%=picturebean.getPath()%>"><%=picturebean.getPictureDpi()%></a></li>
                      <li><a href="#">分辨率1</a></li>
                      <li><a href="#">分辨率1</a></li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
			<%
          		}
			%>        
        


  	  	</div>

      <div class="container text-center row">
              <ul class="pagination">
                <%
  			//显示页数的超链接
  			session.setAttribute("alPictureCollection", alPictureCollection);
  			int PageNow = (int)request.getAttribute("pageNowCollection");
  			//得到pageCount
  			int pageCount = (int)request.getAttribute("pageCountCollection");
  			if(PageNow != 1)
  			{
  				%><li><a href="UploadHandleServlet?&flag=page&flag1=page&pageNowCollection=<%=(PageNow - 1)%>&pageNowUpload=1&bool=1">&laquo;</a></li><%
  			}
  			for(int i = 0; i < pageCount; i++)
  			{
  				if((i + 1) == PageNow)
  				{
  					%><li class="active"><a><%=(i + 1)%></a></li><%
  				}
  				else
  				{
  					%><li><a href="UploadHandleServlet?flag=page&flag1=page&pageNowCollection=<%=(i + 1)%>&pageNowUpload=1&bool=1"><%=(i + 1)%></a></li><%
  				}
  			}
  			if(PageNow != pageCount)
  			{
  				%><li><a href="UploadHandleServlet?flag=page&flag1=page&pageNowCollection=<%=(PageNow + 1)%>&pageNowUpload=1&bool=1">&raquo;</a></li><%
  			}
  		%>
              </ul>
        </div>
   
		</div>
        
  	  	
        <div class="tab-pane fade <%if(bool.equals("0")){%>in active<%}%>" id="myUpload">
          <div class="row container">
            <div class="row"><div class="col-md-offset-10"><button class="btn btn-warning upload-btn" data-toggle="modal" 
   data-target="#myModal">上传图片</div></div>
    
           <%
          	for(int i = 0; i < alPictureUploadPageNow.size(); i++)
          	{
          		PictureBean picturebean = alPictureUploadPageNow.get(i);
  	  	  %>
          <div class="col-md-4" name="base">
              <div class="thumbnail">
                <div class="pic-animate">
                  <img src="<%=picture_path+picturebean.getPath()%>" class="img-responsive" name="pic-sel" alt="图片加载失败">
                </div>
                <div class="caption">
                <h3><%=picturebean.getDescribe()%></h3>
                  <p><%=picturebean.getUploadTime()%></p>
                  <p class="col-md-offset-5 col-md-4 col-sm-offset-5 col-sm-4 col-xs-offset-5 col-xs-4"></p>
                  <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    下载<span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                      <li><a href="DownloadServlet?flag=download&path=<%=picturebean.getPath()%>"><%=picturebean.getPictureDpi()%></a></li>
                      <li><a href="#">分辨率1</a></li>
                      <li><a href="#">分辨率1</a></li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
            <%
          		}
			%>
            
            


        </div>
     
      <div class="container text-center row">
              <ul class="pagination">
                <%
  			//显示页数的超链接
  			session.setAttribute("alPictureUpload", alPictureUpload);
  			int PageNowUpload = (int)request.getAttribute("pageNowUpload");
  			//得到pageCount
  			int pageCountUpload = (int)request.getAttribute("pageCountUpload");
  			if(PageNowUpload != 1)
  			{
  				%><li><a href="UploadHandleServlet?&flag=page&flag1=page&pageNowUpload=<%=(PageNowUpload - 1)%>&pageNowCollection=1&bool=0">&laquo;</a></li><%
  			}
  			for(int i = 0; i < pageCountUpload; i++)
  			{
  				if((i + 1) == PageNowUpload)
  				{
  					%><li class="active"><a><%=(i + 1)%></a></li><%
  				}
  				else
  				{
  					%><li><a href="UploadHandleServlet?flag=page&flag1=page&pageNowUpload=<%=(i + 1)%>&pageNowCollection=1&bool=0"><%=(i + 1)%></a></li><%
  				}
  			}
  			if(PageNowUpload != pageCountUpload)
  			{
  				%><li><a href="UploadHandleServlet?flag=page&flag1=page&pageNowUpload=<%=(PageNowUpload + 1)%>&pageNowCollection=1&bool=0">&raquo;</a></li><%
  			}
  		%>
              </ul>
        </div>
  
          <div class="modal fade " id="myModal" tabindex="-1" role="dialog" 
             aria-labelledby="myModalLabel" aria-hidden="true">
             <div class="modal-dialog">
                <div class="modal-content">
                   <div class="modal-header">
                      <button type="button" class="close" 
                         data-dismiss="modal" aria-hidden="true">
                            &times;
                      </button>
                      <h4 class="modal-title text-center" id="myModalLabel">
                         图片上传
                      </h4>
                   </div>
                   <div class="modal-body">
                     <form enctype="multipart/form-data" class="form" role="form" action="UploadHandleServlet?flag=pic_up&flag1=page&pageNowCollection=1&pageNowUpload=1&bool=1" method="post" >
                      
                     <div class="form-group">
                        <h4>选择图片</h4>
                        <input type="file" id="inputfile" name="picture" requied>
                     </div>
                     <div class="form-group">
                        <h4>图片描述</h4>
                        <input type="text" class="form-control" id="name" name = "des"
                           placeholder="请输入图片描述" requied>
                     </div>
                     <div>
                        <h4>选择分类</h4>
                          <input type="checkbox" name="classify" value="风景">风景
                          <input type="checkbox" name="classify" value="美女">美女
                          <input type="checkbox" name="classify" value="动漫">动漫
                          <input type="checkbox" name="classify" value="游戏">游戏
                          <input type="checkbox" name="classify" value="动物">动物
                          <input type="checkbox" name="classify" value="植物">植物
                          <input type="checkbox" name="classify" value="体育">体育
                          <input type="checkbox" name="classify" value="星座">星座
                          <input type="checkbox" name="classify" value="可爱">可爱
                          <input type="checkbox" name="classify" value="其他">其他
                        
                     </div>   
                   </div>
                   <div class="modal-footer">
                      <button type="button" class="btn btn-default" 
                         data-dismiss="modal">关闭
                      </button>
                      <button type="submit" class="btn btn-primary">
                         提交
                      </button>
                   </div>
                    </form>
                </div><!-- /.modal-content -->
          </div><!-- /.modal -->
       
        
        </div>
  	  </div>

  	  </div>
  	</section>
  	<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
  	 <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
     <script src="js/myCollection.js"></script>
  </body>
</html>
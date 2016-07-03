package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import bean.MethodBean;
import bean.PictureBean;
import bean.UserBean;

public class UploadHandleServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String flag = request.getParameter("flag");
		String flag1 = request.getParameter("flag1");
		System.out.println("flag1=" + flag1);
		System.out.println(flag);
		HttpSession session = request.getSession();
		if(flag.equals("pic_up") && flag1.equals("page")){
		if(!session.isNew()) {
			if(request.getCookies() != null) {
				for(Cookie cookie : request.getCookies()) {
					if(cookie.getName().equals("ssid")) {
						String account = (String) session.getAttribute("account");
						upload(request,account);
						/*
						request.setAttribute("account", account);
						request.setAttribute("pageNowCollection", 1);
						request.setAttribute("pageNowUpload", 1);
						request.getRequestDispatcher("myCollection.jsp").forward(request, response);
						*/
						//response.sendRedirect("myCollection.jsp");
					}
				}
			}
			MethodBean methodbean = new MethodBean();
			String account = null;
			int pageNowCollection = Integer.parseInt(request.getParameter("pageNowCollection"));
			int pageNowUpload = Integer.parseInt(request.getParameter("pageNowUpload"));
			String bool = request.getParameter("bool");

			if(!session.isNew()) {
				if(request.getCookies() != null) {
					for(Cookie cookie : request.getCookies()) {
						if(cookie.getName().equals("ssid")) {
							account = (String) session.getAttribute("account");
						}
					}
				}
			}
			
			ArrayList<PictureBean> alPictureCollection = methodbean.getCollectedPicture(account);
			int pageCountCollection = methodbean.getPicturePageCount(alPictureCollection);
			ArrayList<PictureBean> alPictureCollectionPageNow = methodbean.getPictureByPage(pageNowCollection, alPictureCollection);
			
			request.setAttribute("account", account);
			request.setAttribute("alPictureCollection", alPictureCollection);
			request.setAttribute("pageNowCollection", pageNowCollection);
			request.setAttribute("alPictureCollectionPageNow", alPictureCollectionPageNow);
			request.setAttribute("pageCountCollection", pageCountCollection);
			
			UserBean userbean = methodbean.getUserbean(account);
			ArrayList<PictureBean> alPictureUpload = methodbean.showUploadPicture(userbean.getEmail());
			int pageCountUpload = methodbean.getPicturePageCount(alPictureUpload);
			ArrayList<PictureBean> alPictureUploadPageNow = methodbean.getPictureByPage(pageNowUpload, alPictureUpload);
			
			request.setAttribute("alPictureUpload", alPictureUpload);
			request.setAttribute("pageNowUpload", pageNowUpload);
			request.setAttribute("alPictureUploadPageNow", alPictureUploadPageNow);
			request.setAttribute("pageCountUpload", pageCountUpload);
			
			request.setAttribute("bool", bool);
			request.getRequestDispatcher("myCollection.jsp").forward(request, response);
		}
		}else if(flag.equals("user_pic")){
			if(!session.isNew()) {
				if(request.getCookies() != null) {
					for(Cookie cookie : request.getCookies()) {
						if(cookie.getName().equals("ssid")) {
							String account = (String) session.getAttribute("account");
							System.out.println(account);
							uploadheadpicture(request,account);
							/*request.setAttribute("account", account);
							request.setAttribute("pageNowCollection", 1);
							request.setAttribute("pageNowUpload", 1);
							*/
							//response.sendRedirect("myCollection.jsp");
							
						}
					}
				}
			
		}
			MethodBean methodbean = new MethodBean();
			String account = null;
			int pageNowCollection = Integer.parseInt(request.getParameter("pageNowCollection"));
			int pageNowUpload = Integer.parseInt(request.getParameter("pageNowUpload"));
			String bool = request.getParameter("bool");

			if(!session.isNew()) {
				if(request.getCookies() != null) {
					for(Cookie cookie : request.getCookies()) {
						if(cookie.getName().equals("ssid")) {
							account = (String) session.getAttribute("account");
						}
					}
				}
			}
			
			ArrayList<PictureBean> alPictureCollection = methodbean.getCollectedPicture(account);
			int pageCountCollection = methodbean.getPicturePageCount(alPictureCollection);
			ArrayList<PictureBean> alPictureCollectionPageNow = methodbean.getPictureByPage(pageNowCollection, alPictureCollection);
			
			request.setAttribute("account", account);
			request.setAttribute("alPictureCollection", alPictureCollection);
			request.setAttribute("pageNowCollection", pageNowCollection);
			request.setAttribute("alPictureCollectionPageNow", alPictureCollectionPageNow);
			request.setAttribute("pageCountCollection", pageCountCollection);
			
			UserBean userbean = methodbean.getUserbean(account);
			ArrayList<PictureBean> alPictureUpload = methodbean.showUploadPicture(userbean.getEmail());
			int pageCountUpload = methodbean.getPicturePageCount(alPictureUpload);
			ArrayList<PictureBean> alPictureUploadPageNow = methodbean.getPictureByPage(pageNowUpload, alPictureUpload);
			
			request.setAttribute("alPictureUpload", alPictureUpload);
			request.setAttribute("pageNowUpload", pageNowUpload);
			request.setAttribute("alPictureUploadPageNow", alPictureUploadPageNow);
			request.setAttribute("pageCountUpload", pageCountUpload);
			
			request.setAttribute("bool", bool);
			request.getRequestDispatcher("myCollection.jsp").forward(request, response);
		
			
		
		
	}
		else if(flag.equals("page") || flag1.equals("page"))
		{
			MethodBean methodbean = new MethodBean();
			String account = null;
			int pageNowCollection = Integer.parseInt(request.getParameter("pageNowCollection"));
			int pageNowUpload = Integer.parseInt(request.getParameter("pageNowUpload"));
			String bool = request.getParameter("bool");

			if(!session.isNew()) {
				if(request.getCookies() != null) {
					for(Cookie cookie : request.getCookies()) {
						if(cookie.getName().equals("ssid")) {
							account = (String) session.getAttribute("account");
						}
					}
				}
			}
			
			ArrayList<PictureBean> alPictureCollection = methodbean.getCollectedPicture(account);
			int pageCountCollection = methodbean.getPicturePageCount(alPictureCollection);
			ArrayList<PictureBean> alPictureCollectionPageNow = methodbean.getPictureByPage(pageNowCollection, alPictureCollection);
			
			request.setAttribute("account", account);
			request.setAttribute("alPictureCollection", alPictureCollection);
			request.setAttribute("pageNowCollection", pageNowCollection);
			request.setAttribute("alPictureCollectionPageNow", alPictureCollectionPageNow);
			request.setAttribute("pageCountCollection", pageCountCollection);
			
			UserBean userbean = methodbean.getUserbean(account);
			ArrayList<PictureBean> alPictureUpload = methodbean.showUploadPicture(userbean.getEmail());
			int pageCountUpload = methodbean.getPicturePageCount(alPictureUpload);
			ArrayList<PictureBean> alPictureUploadPageNow = methodbean.getPictureByPage(pageNowUpload, alPictureUpload);
			
			request.setAttribute("alPictureUpload", alPictureUpload);
			request.setAttribute("pageNowUpload", pageNowUpload);
			request.setAttribute("alPictureUploadPageNow", alPictureUploadPageNow);
			request.setAttribute("pageCountUpload", pageCountUpload);
			
			request.setAttribute("bool", bool);
			request.getRequestDispatcher("myCollection.jsp").forward(request, response);
		}
}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
	//声称上传文件的文件名，文件名为(uuid + "_" + 文件原始名称)
	private String makeFileName(String filename,String fileExtName)
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
		String time = sdf.format(date);
		int num = (int) Math.round(Math.random() * (451 - 1) + 1);
		String timestamp=time+num;
		return timestamp +fileExtName ;
	}
	private void insertsql(String p,String time,String name) throws FileNotFoundException, IOException{
		File picture = new File(p);
		BufferedImage sourceImg =ImageIO.read(new FileInputStream(picture));
		long pic_size = picture.length();  
        int width = sourceImg.getWidth(); 
        int height = sourceImg.getHeight();
        String pic_dpi = width+"*"+height;
        String path = p.substring(p.lastIndexOf("\\")+1);
        MethodBean m = new MethodBean();
        m.InsertPicture(path, pic_dpi, pic_size,time,name);
	}
	//上传图片
	private void upload(HttpServletRequest pic,String name){
		String uploadPath = this.getServletContext().getRealPath("/picture");
		//上传时生成的临时文件的保存目录
		String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
		System.out.println(tempPath);
		File tempFile = new File(tempPath);
		ArrayList<String> keywords = new ArrayList<String>();
		String saveFilename="";
		String des  ="";
		
		
		try
		{
			//使用Apache文件上传组件处理文件上传步骤
			//1.创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置工厂缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时文件目录中
			//设置上传时生成的临时文件保存目录
			factory.setRepository(tempFile);
			//2.设置一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			//使用ServletFileUpload上传解析器解析上传数据，解析结果返回的是一个List<FileItem>的集合，每个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(pic);
			for(FileItem item : list)
			{
				 if(item.isFormField()){
					 String n = item.getFieldName();
					 
					 String value = "";
					
					 //解决普通输入项的数据的中文乱码问题
					 if(n.equals("des")){
						 des = item.getString("UTF-8");
					 }else{
						 value = item.getString("UTF-8");
						 keywords.add(value);
					 }
					 //value = new String(value.getBytes("iso8859-1"),"UTF-8");
					 
					 
					 continue;
					 }else{//如果fileitem中封装的是上传文件
				
				    
					String filename = item.getName();
					
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分；获取文件扩展名
					if(filename == null || filename.trim().equals(""))
					{
						continue;
					}

					filename = filename.substring(filename.indexOf("\\") + 1);
					String fileExtName = filename.substring(filename.indexOf("."));
					//获取文件存储名和存储地址
					saveFilename = makeFileName(filename,fileExtName);
					
					String realSavePath = saveFilename;
					String picture_path = uploadPath + "\\" + saveFilename;
					//获取文件的输入输出流
					InputStream in = item.getInputStream();
					FileOutputStream out = new FileOutputStream(picture_path);
					
					
					//对文件进行传输
					byte buffer[] = new byte[1024];
					int len = 0;
					while((len = in.read(buffer)) > 0)
					{
						out.write(buffer, 0, len);
					}
					//关闭输入输出流
					in.close();
					out.close();
					//删除上传过程中生成的临时文件
					item.delete();
					Date a= new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(a);
					insertsql(picture_path,date,name);
					
					 }
				}
			new MethodBean().InsertKeywordDB(saveFilename, keywords);
			new MethodBean().uploadPicturedes(saveFilename,des);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void uploadheadpicture(HttpServletRequest pic,String account){
		String uploadPath = this.getServletContext().getRealPath("/headpicture");
		//上传时生成的临时文件的保存目录
		String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
		File tempFile = new File(tempPath);
		try
		{
			//使用Apache文件上传组件处理文件上传步骤
			//1.创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置工厂缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时文件目录中
			//设置上传时生成的临时文件保存目录
			factory.setRepository(tempFile);
			//2.设置一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			//使用ServletFileUpload上传解析器解析上传数据，解析结果返回的是一个List<FileItem>的集合，每个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(pic);
			String nickname  ="";
			String sign = "";
			String saveFilename="";
			for(FileItem item : list)
			{
				
				if(item.isFormField()){
					 String n = item.getFieldName();
					 //解决普通输入项的数据的中文乱码问题
					 if(n.equals("nickname")){
						 nickname = item.getString("UTF-8");
					 }else if(n.equals("sign")){
						 sign = item.getString("UTF-8");
						 
					 }
					
					 
					 
					 continue;
					 }else{//如果fileitem中封装的是上传文件
				
					String filename = item.getName();
					
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分；获取文件扩展名
					if(filename == null || filename.trim().equals(""))
					{
						continue;
					}

					filename = filename.substring(filename.indexOf("\\") + 1);
					String fileExtName = filename.substring(filename.indexOf("."));
					//获取文件存储名和存储地址
					saveFilename = makeFileName(filename,fileExtName);
					
					String realSavePath = saveFilename;
					String picture_path = uploadPath + "\\" + saveFilename;
					//获取文件的输入输出流
					InputStream in = item.getInputStream();
					FileOutputStream out = new FileOutputStream(picture_path);
					
					
					//对文件进行传输
					byte buffer[] = new byte[1024];
					int len = 0;
					while((len = in.read(buffer)) > 0)
					{
						out.write(buffer, 0, len);
					}
					//关闭输入输出流
					in.close();
					out.close();
					//删除上传过程中生成的临时文件
					item.delete();
					
					}
				}
			new MethodBean().uploaduserPicture(account,saveFilename);
			new MethodBean().Updateinfoheadpic(nickname, sign, account);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
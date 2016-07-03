package controller;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	
		//得到要下载的文件名
		String flag = request.getParameter("flag");
		String fileName = null;
		
		if(flag.equals("download"))
		{
			fileName = request.getParameter("path");
		}else if(flag.equals("download1"))
		{
			fileName = request.getParameter("pic");
			fileName = fileName.substring(fileName.indexOf("/") + 1);
		}
		String fileSaveRootPath = this.getServletContext().getRealPath("/picture");
		fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");
		System.out.println(fileSaveRootPath);
		File file = new File(fileSaveRootPath + "\\" + fileName);
		if(!file.exists())
		{
			System.out.println("文件不存在！");
		}else
		{
			System.out.println("文件存在");
		}
		response.setHeader("content-disposition", "attachment; fileName=" + URLEncoder.encode(fileName, "UTF-8"));
		
		FileInputStream in = new FileInputStream(fileSaveRootPath + "\\" + fileName);
		//创建输出流
		OutputStream out = response.getOutputStream();
		
		byte buffer[] = new byte[1024];
		int len = 0;
		while((len = in.read(buffer)) > 0)
		{
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}
		
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
}
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.*;

public class SearchServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String flag = request.getParameter("flag");
		MethodBean methodbean = new MethodBean();
		
		
		
		
		
		
		if(flag.equals("paging"))
		{
			System.out.println(flag);
			
			String keyword = request.getParameter("keyword");
			String bool = request.getParameter("bool");
			System.out.println(bool);
			if(!bool.equals("1"))
			{
				keyword = new String(keyword.getBytes("iso8859-1"), "UTF-8");
			}
			if(keyword != null)
			{
			
				{
					flag = "paging";
					ArrayList<PictureBean> alPicture1 = methodbean.searchPicture(keyword);
					System.out.println(keyword);
					int pageNow = 1;
					int pageCount = methodbean.getPicturePageCount(alPicture1);
					ArrayList<PictureBean> alPicture = methodbean.getPictureByPage(pageNow, alPicture1);
					request.setAttribute("keyword", keyword);
					request.setAttribute("result1", alPicture1);
					request.setAttribute("pageNow", pageNow);
					request.setAttribute("result", alPicture);
					request.setAttribute("pageCount", pageCount);
					request.getRequestDispatcher("classify.jsp").forward(request, response);
				}
			}
			else if(keyword == null)
			{
				int pageNow = Integer.parseInt(request.getParameter("pageNow"));
				ArrayList<PictureBean> alPicture2 = (ArrayList<PictureBean>)request.getSession().getAttribute("result1");
				String keyword1 = (String)request.getSession().getAttribute("keyword");
				int pageNow1 = Integer.parseInt(request.getParameter("pageNow"));
				int pageCount1 = methodbean.getPicturePageCount(alPicture2);
				ArrayList<PictureBean> alPicture = methodbean.getPictureByPage(pageNow1, alPicture2);
				request.setAttribute("keyword", keyword1);
				request.setAttribute("pageNow", pageNow);
				request.setAttribute("result", alPicture);
				request.setAttribute("result1", alPicture2);
				request.setAttribute("pageCount", pageCount1);
				request.getRequestDispatcher("classify.jsp").forward(request, response);
			
			}
		}

		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
}
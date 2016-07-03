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

public class DeleteServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String flag = request.getParameter("flag");
		MethodBean methodbean = new MethodBean();
		
		HttpSession session = request.getSession();
		String account = null;
		System.out.println(flag);
		if(flag.equals("delete"))
		{
			String picdic = request.getParameter("pic_dic");
			System.out.println(picdic);
			String picPath = picdic.substring(picdic.indexOf("/") + 1);
			System.out.println(picPath);
			if(!session.isNew()) {
				if(request.getCookies() != null) {
					for(Cookie cookie : request.getCookies()) {
						if(cookie.getName().equals("ssid")) {
							account = (String) session.getAttribute("account");
						}
					}
				}
			}
			UserBean userbean = methodbean.getUserbean(account);
			int user_id = userbean.getId();
			System.out.println(user_id);
			PictureBean picturebean = methodbean.getPictureFromPath(picPath);
			int pic_id = picturebean.getPictureId();
			methodbean.deleteCollection(user_id, pic_id);
		}
		
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
}
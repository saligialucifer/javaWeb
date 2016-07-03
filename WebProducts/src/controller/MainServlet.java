package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Encryption;
import bean.UserBean;
import bean.MethodBean;
import org.json.*;





/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/LogInServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// 获得标识符
		String flag = request.getParameter("flag");
		// 调用UserBeanCl的方法
		MethodBean userbeancl = new MethodBean();
		// 调用加密算法
		Encryption en = new Encryption();
		request.setCharacterEncoding("UTF-8");   // 设置request编码
	    response.setCharacterEncoding("UTF-8");  // 设置response编码
		System.out.println(flag);
		if(flag != null) {
			if(flag.equals("login")) {
				
				String name = request.getParameter("username");
				String pass = request.getParameter("password");
				String rem = request.getParameter("remember");
				int reint = userbeancl.checkUser(name, pass);
				System.out.println(name+pass+rem);
				PrintWriter out = response.getWriter();
				UserBean user = new UserBean();
				user.setLogin(false);
				
				if(reint == 2) { //登陆成功
					HttpSession session = request.getSession();
					session.setAttribute("account", name);
					
					
					if(rem.equals("true")) {
						String ssid = en.calcMD1(name);
						int timeout = 3600;
						session.setMaxInactiveInterval(timeout);
						Cookie ssidCookie = new Cookie("ssid", ssid);
						ssidCookie.setPath("/");
						ssidCookie.setMaxAge(timeout);
						response.addCookie(ssidCookie);
					}
					//访问数据库，获取用户其他数据
					user = userbeancl.getUserbean(name);
					user.setLogin(true);
					
				}
				//构建的json数据
				//System.out.println(name);
				//System.out.println(pass);
				//System.out.println(rem);
				//System.out.println(user.getUserData());
				out.println(user.getUserData());
				out.close();
			}
			else if(flag.equals("logout")) { 
				Cookie ssidCookie =new Cookie("ssid", "");
				ssidCookie.setPath("/");
				ssidCookie.setMaxAge(0); 
				response.addCookie(ssidCookie);
				HttpSession session = request.getSession(false);
				if(session != null)
					session.invalidate();
				response.sendRedirect("index.jsp");
			}
			// 添加用户
			else if (flag.equals("addUser")) {
					
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					String result=userbeancl.addUser(username, password);
					System.out.println(result);

	                 if(result.equals("suc"))	
						{request.getRequestDispatcher("index.jsp").forward(request, response);
					    }
						else{
						//添加失败
						//request.setAttribute("result", "illegal user name!");
						request.getRequestDispatcher("error.jsp").forward(request, response);
					}
				}
				
				
				
			else if(flag.equals("legalUserName")){
				String username = request.getParameter("name");
				System.out.println(username);
				try {
					String result=userbeancl.legalUserName(username);
					System.out.println(result);
					PrintWriter out=response.getWriter();
					out.print(result);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(flag.equals("favour")) {
				PrintWriter out = response.getWriter();
				String path =  request.getParameter("pic_dic");
				boolean islogin = false;
				boolean iscollected = false;
				
				//收藏量增加
				userbeancl.addClicks(path);
				System.out.println(path);
				
				HttpSession session = request.getSession();
				if(!session.isNew()) {
					String account = (String) session.getAttribute("account");
					System.out.println(account);
					if(account != null && !account.equals("")) {
						islogin = true;
						int i = userbeancl.click_like(0, account, path);
						if(i == 1)
							iscollected = true;
					}
				}
				
				Map map = new HashMap();
				map.put("islogin", islogin);
				map.put("iscollected", iscollected);
				JSONObject json = new JSONObject(map);
				String data = json.toString();
				System.out.println(data);
				out.println(data);
				out.close();
			}
			//发送邮件
			else if(flag.equals("sendmail")){
				MethodBean m = new MethodBean();
				HttpSession session = request.getSession();
				if(!session.isNew()) {
					if(request.getCookies() != null) {
						for(Cookie cookie : request.getCookies()) {
							if(cookie.getName().equals("ssid")) {
								String account = (String) session.getAttribute("account");
								try {
									m.SendMail(account);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
				
			}
			else if(flag.equals("auto")) {
				UserBean user = new UserBean();
				user.setLogin(false);
				PrintWriter out = response.getWriter();
				
				HttpSession session = request.getSession();
				if(!session.isNew()) {
					if(request.getCookies() != null) {
						for(Cookie cookie : request.getCookies()) {
							if(cookie.getName().equals("ssid")) {
								String account = (String) session.getAttribute("account");
								user.setEmail(account);
								if(en.calcMD1(account).equals(cookie.getValue())) {
									user = userbeancl.getUserbean(account);
									user.setLogin(true); 
								}
							}
						}
					}
				}
				System.out.println(user.getUserData());
				out.println(user.getUserData());
				out.close();
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		this.doGet(request, response);
	}

}

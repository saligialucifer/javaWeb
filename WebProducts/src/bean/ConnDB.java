package bean;

import java.sql.*;

public class ConnDB
{
	private Connection conn = null;
	public Connection getConnection(String database, String username, String password)
	{
		try
		{
			//��������
			Class.forName("com.mysql.jdbc.Driver");
			//��������
			conn = DriverManager.getConnection("jdbc:mysql://localhost:"
					+ "3306/" + database + "?user=" + username 
					+ "&password=" + password + "&useUnicode=true"
							+ "&characterEncoding=UTF8");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
}
package bean;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.Comparator;

public class MethodBean {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet res = null;
	private PreparedStatement ps = null;
	private String dbname = "webdb";
	private String username = "root";
	private String pass = "root";

	private int pageSize = 6;
	private int rowCount = 0;// ��ֵ�����ݿ��в�ѯ
	private int pageCount = 0;// ��ֵ��ͨ��pageSize��pageCount

	// �ر���Դ
	public void closeSource() {
		// �رմ򿪵ĸ�����Դ���������Ҫ������
		try {
			if (res != null) {
				res.close();
				res = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
			if(ps != null){
				ps.close();
				ps = null;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	// ��֤�û��Ƿ�Ϸ�
	public int checkUser(String u, String p) {

		int reint = 0;
		try {
			conn = new ConnDB().getConnection(dbname, username, pass); 
			stmt = conn.createStatement();
			res = stmt.executeQuery("select user_pass from user_db "
					+ "where user_account = '"+ u + "'");

			// ���ݽ���ж�
			if (res.next()) {
				// �û������ڣ��ж������Ƿ���ȷ
				reint = 1;
				if (res.getString(1).equals(p)) {
					// �û���������Ϸ�
					reint = 2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSource();
		}
		return reint;
	}
	
	public UserBean icUserbean(String account) {
		UserBean ub = new UserBean();
		ub.setEmail(account);
		//�������ݿ⣬��ȫuserbean��Ϣ
		try {
			conn = new ConnDB().getConnection(dbname, username, pass); 
			stmt = conn.createStatement();
			res = stmt.executeQuery("select user_pass,nickname,"
					+ "user_picture,sign from user_db "
					+ "where user_account = '"+ account + "'");
			
			if(res.next()) {
				ub.setPassword(res.getString(1));
				ub.setNickname(res.getString(2));
				ub.setHead_path(res.getString(3));
				System.out.println("head_path: " + res.getString(3));
				ub.setSign(res.getString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSource();
		}
		
		return ub;
	}
	
	//�ղ���ȡ��
	public int click_like(int ope, String account, String path) {
		path = path.substring(8, path.length());
		
		int reint = 0;
		int uid = 0;
		int pid = 0;
		
		try {
			conn = new ConnDB().getConnection(dbname, username, pass); 
			stmt = conn.createStatement();
			res = stmt.executeQuery("select user_id from user_db "
					+ "where user_account = '"+ account + "'");
			if(res.next()) 
				uid = Integer.parseInt(res.getString(1));
			res = stmt.executeQuery("select pic_id from pic_db "
					+ "where pic_path = '"+ path + "'");
			if(res.next())
				pid = Integer.parseInt(res.getString(1));
			if(uid != 0 && pid != 0) {
				if(ope == 0) { //�ղ�
					stmt = conn.createStatement();
					res = stmt.executeQuery("select user_id from pic_user_relation "
							+ "where user_id = '"+ uid + "' "
							+ "and pic_id = '"+ pid + "'");
					if(res.next()) {
						reint = 2;
					} else {
						PreparedStatement pstmt;
						pstmt = conn.prepareStatement("insert into pic_user_relation "
								+ "values ('" + uid + "', '" + pid + "')");
						pstmt.execute();
						reint = 1;
					}
				} else if(ope == 1) { //ȡ��
					PreparedStatement pstmt;
					pstmt = conn.prepareStatement("delete from pic_user_relation "
							+ "where user_id='" + uid + "' and pic_id='" 
							+ pid + "'");
					pstmt.execute();
				}
			}	
		} catch (Exception e) {
			//�޸�ʧ��
			e.printStackTrace();
		} finally {
			closeSource();
		}
		
		return reint;
	}

	// ����pageCount��ֵ
	public int getPageCount() {
		try {
			conn = new ConnDB().getConnection(dbname, username, pass);
			// ����Statement
			stmt = conn.createStatement();
			// ��ѯ���ݿ�
			res = stmt.executeQuery("select count(*) from login");

			if (res.next()) {
				rowCount = res.getInt(1);
			}

			// ����pageCount,������㷨�ܶ࣬�����Լ����
			if (rowCount % pageSize == 0) {
				pageCount = rowCount / pageSize;
			} else {
				pageCount = rowCount / pageSize + 1;
			}
			// ����pageCount

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSource();
		}
		return pageCount;
	}

	// �õ��û���Ҫ��ʾ���û���Ϣ����ҳ��
	public ArrayList<UserBean> getUserByPage(int pageNow) {
		ArrayList<UserBean> al = new ArrayList<UserBean>();

		try {
			conn = new ConnDB().getConnection(dbname, username, pass);
			// ����Statement
			stmt = conn.createStatement();

			// ��ʾҪ��ѯ�ļ�¼
			// where not in(select * from login limit "+pageSize*(pageNow-1)+")
			System.out.println(pageNow);
			res = stmt.executeQuery("select * from login limit "
					+ ((pageNow - 1) * pageSize) + "," + pageSize + "; ");

			// ��ʼ��res��װ��ArrayList
			while (res.next()) {
				UserBean userbean = new UserBean();

				userbean.setId(res.getInt(3));
				userbean.setNickname(res.getString(1));
				userbean.setPassword(res.getString(2));

				// ��userbean�ŵ�al
				al.add(userbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSource();
		}
		return al;
	}

	// ɾ���û�
	public boolean deleteUser(String user_id) {
		boolean bool = false;
		try {
			conn = new ConnDB().getConnection(dbname, username, pass);
			ps = conn.prepareStatement("delete from login where id = "
					+ user_id + "");
			int a = ps.executeUpdate();
			if (a == 1) {
				// ɾ���ɹ�
				bool = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeSource();
		}
		return bool;
	}

	// ����û�
	public String  addUser(String userName, String passWord) {
		String flag= "fail";
		System.out.println(userName+passWord);
		try {
			conn = new ConnDB().getConnection(dbname, username, pass);
		
			ps = conn
					.prepareStatement("insert into user_db(user_account,user_pass,nickname,user_picture,sign) values('"
							+ userName + "','" + passWord +"','"+" "+"','"+" "+"','"+" "+ "')");
			int i = ps.executeUpdate();
			if (i == 1) {
				flag = "suc";
							}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSource();
		}
		return flag;
	}

	// �޸��û�
	public boolean updateUser(String username, String password, String id) {
		boolean bool = false;
		try {
			conn = new ConnDB().getConnection(dbname, username, pass);
			ps = conn.prepareStatement("update login set username='" + username
					+ "',password='" + password + "' where id = '" + id + "'");
			int i = ps.executeUpdate();
			if (i == 1) {
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSource();
		}
		return bool;
	}

	// ��ѯ�û�
	public ArrayList searchUser(String id, String username, String password) {
		ArrayList al = new ArrayList();
		UserBean userbean = new UserBean();
		try {
			conn = new ConnDB().getConnection(dbname, username, pass);
			
			ps = conn.prepareStatement("select * from login where id = '" + id
					+ "' or username like'%" + username
					+ "%' or password like'%" + password + "%'");
			res = ps.executeQuery();
			while (res.next()) {
				userbean.setId(res.getInt(3));
				userbean.setNickname(res.getString(1));
				userbean.setPassword(res.getString(2));

				al.add(userbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSource();
		}
		return al;
	}
	
	//ע���û�
	public boolean cancleUser(String username,String password){
		boolean bool = false;
		try {
			conn = new ConnDB().getConnection(dbname, username, pass);
			ps = conn.prepareStatement("delete from login where username = '"+username+"' and password = '"+password+"'");
			int i = ps.executeUpdate();
			if(i == 1){
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeSource();
		}
		return bool;
	}
	//��ѯ�Ƿ��ظ�
		public String legalUserName(String userName) throws SQLException{
			String flag="1";
			conn = new ConnDB().getConnection(dbname, username, pass);
			stmt = conn.createStatement();
			res = stmt.executeQuery("select user_pass from user_db "
					+ "where user_account = '"+ userName + "'");
			if (res.next()) {
				
				//System.out.println("illegal");
				flag="0";
				
				}
			return flag;
		}
	
	
	//����pic_db��������ͼƬ·��
		public ArrayList<String> getThreeRandomPicture()
		{
			ArrayList<String> alString = new ArrayList<String>();
			try
			{
				conn = new ConnDB().getConnection(dbname, username, pass);
				stmt = conn.createStatement();
				// ��ѯ���ݿ�
				res = stmt.executeQuery("select count(*) from pic_db");
				int count = 0;
				if (res.next()) {
					count = res.getInt(1);
				}
				while(alString.size() < 3)
				{
					int selectNumber = (int)(Math.random() * (count) + 1);
					System.out.println(selectNumber);
					res = stmt.executeQuery("select * from pic_db where pic_id = '" + selectNumber + "'");
					while(res.next())
					{
						boolean bool = true;
						for(int i = 0; i < alString.size(); i++)
						{
							if(alString.get(i).equals(res.getString(5)))
							{
								bool = false;
							}
						}
						if(bool)
						{
							alString.add(res.getString(5));
						}
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}finally
			{
				this.closeSource();
			}
			return alString;
		}
		
		//����Keyword�б�
		public ArrayList<KeywordBean> returnKeywordList()
		{
			ArrayList<KeywordBean> keywordlist = new ArrayList<KeywordBean>();
			try
			{
				conn = new ConnDB().getConnection(dbname, username, pass);
				stmt = conn.createStatement();
				res = stmt.executeQuery("select * from keyword_db");
				while(res.next())
				{
					KeywordBean keywordbean = new KeywordBean();
					keywordbean.setId(res.getInt(1));
					keywordbean.setKeyword(res.getString(2));
					keywordlist.add(keywordbean);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}finally
			{
				this.closeSource();
			}
			return keywordlist;
		}

		public boolean isPicCollected(String account, String path){
			if("".equals(account) || account == null)
				return false;
			
			ArrayList<String> p = new ArrayList<String>();
			
			try {
				conn = new ConnDB().getConnection(dbname, username, pass);
				ps = conn.prepareStatement("SELECT pic_path FROM "
						+ "pic_user_relation INNER JOIN user_db "
						+ "ON pic_user_relation.user_id=user_db.user_id "
						+ "INNER JOIN pic_db ON pic_db.pic_id="
						+ "pic_user_relation.pic_id "
						+ "WHERE user_account = '" + account + "'");
				res = ps.executeQuery();
				while(res.next()){
					p.add(res.getString(1));
					//System.out.println(res.getString(1));
					
				
				}
				if(!p.isEmpty()) {
					for(int i = 0; i < p.size(); i++) {
						if(p.get(i).equals(path))
							return true;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				this.closeSource();
			}

			return false;
		}
		//�ҵ���ǰ������ղ�ͼƬ
		public ArrayList<PictureBean> getCollectedPicture(String useraccont){
			ArrayList<PictureBean> alPicture = new ArrayList<PictureBean>();
			conn = new ConnDB().getConnection(dbname, username, pass);
			try {
				ps = conn.prepareStatement("SELECT * FROM pic_db NATURAL JOIN pic_user_relation NATURAL JOIN user_db WHERE user_account='"+useraccont+"'");
				res = ps.executeQuery();
				while(res.next()){
					PictureBean picturebean = new PictureBean();
					picturebean.setPictureDpi(res.getString(4));
					picturebean.setPictureId(res.getInt(2));
					picturebean.setClicks(res.getInt(5));
					picturebean.setPath(res.getString(6));
					picturebean.setDescribe(res.getString(9));
					picturebean.setUploadName(res.getString(7));
					picturebean.setUploadTime(res.getString(8));
					alPicture.add(picturebean);
				}
				return alPicture;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		//�����ʼ�
		public void SendMail(String account)throws Exception{
			Properties prop = new Properties();
	        prop.setProperty("mail.host", "smtp.sohu.com");
	        prop.setProperty("mail.transport.protocol", "smtp");
	        prop.setProperty("mail.smtp.auth", "true");
	        //ʹ��JavaMail�����ʼ���5������
	        //1������session
	        Session session = Session.getInstance(prop);
	        //����Session��debugģʽ�������Ϳ��Բ鿴��������Email������״̬
	        session.setDebug(true);
	        //2��ͨ��session�õ�transport����
	        Transport ts = session.getTransport();
	        //3��ʹ��������û��������������ʼ��������������ʼ�ʱ����������Ҫ�ύ������û����������smtp���������û��������붼ͨ����֤֮����ܹ����������ʼ����ռ��ˡ�
	        ts.connect("smtp.sohu.com", "ruanri1401", "t1dCKyfJWiXC");
	        //4�������ʼ�
	        Message message = createSimpleMail(session,account);
	        //5�������ʼ�
	        message.saveChanges();
	        ts.sendMessage(message, message.getAllRecipients());
	        
	        ts.close();
		}
		public MimeMessage createSimpleMail(Session session,String account)
	            throws Exception {
	        //�����ʼ�����
	        MimeMessage message = new MimeMessage(session);
	        //ָ���ʼ��ķ�����
	        message.setFrom(new InternetAddress("ruanri1401@sohu.com"));
	        //ָ���ʼ����ռ��ˣ����ڷ����˺��ռ�����һ���ģ��Ǿ����Լ����Լ���
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(account));
	        //�ʼ��ı���
	        message.setSubject("��ӭע��");
	        //�ʼ����ı�����
	        message.setContent("��ð���", "text/html;charset=UTF-8");
	        //���ش����õ��ʼ�����
	        return message;
	    }
		//����Ӧ��Keyword��picture��ArrayList�����ѡ������
		public ArrayList<PictureBean> getThreeRandomPictureByKeyword(String keyword)
		{
			ArrayList<PictureBean> alPicture = new ArrayList<PictureBean>();
			ArrayList<PictureBean> alPicture1 = new ArrayList<PictureBean>();
			ArrayList<RelationBean> relationList = new ArrayList<RelationBean>();
			int i = 0;
			try
			{
				conn = new ConnDB().getConnection(dbname, username, pass);
				stmt = conn.createStatement();
				res = stmt.executeQuery("select * from keyword_db where keywords= '" + keyword + "'");
				while(res.next())
				{
					i = res.getInt(1);
				}
				res = stmt.executeQuery("select * from pic_keyword_relation where key_id= '" + i + "'");
				while(res.next())
				{
					RelationBean relationbean = new RelationBean();
					relationbean.setKeywordId(res.getInt(2));
					relationbean.setPictureId(res.getInt(1));
					relationList.add(relationbean);
				}
				for(int j = 0; j < relationList.size(); j++)
				{
					res = stmt.executeQuery("select * from pic_db where pic_id= '" + relationList.get(j).getPictureId() + "'");
					while(res.next())
					{
						PictureBean picturebean = new PictureBean();
						picturebean.setPictureDpi(res.getString(3));
						picturebean.setPictureId(res.getInt(1));
						picturebean.setClicks(res.getInt(4));
						picturebean.setPath(res.getString(5));
						picturebean.setUploadName(res.getString(6));
						picturebean.setUploadTime(res.getString(7));
						picturebean.setDescribe(res.getString(8));
						alPicture.add(picturebean);
					}
				}
				while(alPicture1.size() < 3)
				{
					int selectNumber = (int)(Math.random() * (alPicture.size()));
					boolean bool = true;
					for(int j = 0; j < alPicture1.size(); j++)
					{
						if(alPicture1.get(j).equals(alPicture.get(selectNumber)))
						{
							bool = false;
						}
					}
					if(bool)
					{
						alPicture1.add(alPicture.get(selectNumber));
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}finally
			{
				this.closeSource();
			}
			return alPicture1;
		}

				//ȡ����ʾҳ��
				public int getPicturePageCount(ArrayList<PictureBean> alPicture)
				{
					
					rowCount = alPicture.size();
					if(rowCount % pageSize == 0)
					{
						pageCount = rowCount / pageSize;
					}else
					{
						pageCount = rowCount / pageSize + 1;
					}
					return pageCount;
				}
				//����ͼƬ����Ϣ����ҳ��
				public ArrayList<PictureBean> getPictureByPage(int pageNow, ArrayList<PictureBean> alPicture1)
				{
					ArrayList<PictureBean> alPicture2 = new ArrayList<PictureBean>();
					rowCount = alPicture1.size();
					if(rowCount % pageSize == 0)
					{
						pageCount = rowCount / pageSize;
					}else
					{
						pageCount = rowCount / pageSize + 1;
					}
					for(int i = 0; i < Math.min(pageSize, alPicture1.size() - (pageNow - 1) * pageSize); i++)
					{
						PictureBean picturebean = alPicture1.get(pageSize * (pageNow - 1) + i);
						alPicture2.add(picturebean);
					}
					return alPicture2;
				}
				
				public UserBean getUserbean(String account) {
					UserBean ub = new UserBean();
					ub.setEmail(account);
					//�������ݿ⣬��ȫuserbean��Ϣ
					try {
						conn = new ConnDB().getConnection(dbname, username, pass); 
						stmt = conn.createStatement();
						res = stmt.executeQuery("select user_pass,nickname,"
								+ "user_picture,sign,user_id from user_db "
								+ "where user_account = '"+ account + "'");
						
						if(res.next()) {
							ub.setPassword(res.getString(1));
							ub.setNickname(res.getString(2));
							ub.setHead_path(res.getString(3));
							ub.setSign(res.getString(4));
							ub.setId(res.getInt(5));
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						closeSource();
					}
					
					return ub;
				}
				//����ϴ�ͼƬ�����ݿ�
				public void InsertPicture(String path,String dpi,long size,String time,String name){
					try {
						
						
						conn = new ConnDB().getConnection(dbname, username, pass);
					    String sql = "insert into pic_db(pic_size,pic_dpi,pic_favors,pic_path,upload_time,upload_name,pic_describe) values('"+size + "','" + dpi+ "','" +"0"+ "','" +path+"','"+time+"','"+name+"','"+" "+ "')";
					    System.out.println(sql);
						ps = conn.prepareStatement(sql);
						ps.executeUpdate();
						
		
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						this.closeSource();
					}
				}
				//���뵽�ؼ���-ͼƬ��
				public boolean InsertKeywordDB(String path, ArrayList<String> keywords) {
					int kid = 0;
					int pid = 0;
					
					
					try {
						for(int i = 0; i < keywords.size(); i++) {
							conn = new ConnDB().getConnection(dbname, username, pass);
							stmt = conn.createStatement();
							
							stmt = conn.createStatement();
							res = stmt.executeQuery("select pic_id from pic_db "
									+ "where pic_path = '" + path + "'");
							if(res.next())
								pid = res.getInt(1);
							
							res = stmt.executeQuery("select keywords_id from keyword_db "
									+ "where keywords = '" + keywords.get(i) + "'");
							if(res.next())
								kid = res.getInt(1);
							if(kid != 0) {
								PreparedStatement pstmt;
								pstmt = conn.prepareStatement("insert into "
										+ "pic_keyword_relation values ('" 
										+ pid + "', '" + kid + "')");
								pstmt.execute();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						this.closeSource();
					}
					return false;
				}
				//�����û���Ϣ
				public void Updateinfo(String user_pic,String nickname,String single,String account){
					try {
						conn = new ConnDB().getConnection(dbname, username, pass);
						ps = conn.prepareStatement("update user_db set nickname ='" + nickname
								+ "',user_picture='" + user_pic +  "',sign='"+single+"' where user_account = '" + account + "'");
						
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						this.closeSource();
					}
				}
				//�����ղ���
				public boolean addClicks(String pic_path) {
					pic_path = pic_path.substring(8, pic_path.length());
					int click = 0;
					try
					{
						conn = new ConnDB().getConnection(dbname, username, pass);
						stmt = conn.createStatement();
						res = stmt.executeQuery("select pic_favors from pic_db "
								+ "where pic_path = '"+ pic_path + "'");
						if(res.next())
							click = res.getInt(1) + 1;
						PreparedStatement pstmt;
						pstmt = conn.prepareStatement("update pic_db set "
								+ "pic_favors = '" + click 
								+ "' where pic_path = '"+ pic_path + "'");
						pstmt.execute();
						return true;
					}catch(Exception e)
					{
						e.printStackTrace();
					}finally
					{
						this.closeSource();
					}
					return false;
				}
				//����ϴ�ͷ�����ݿ�
				public void uploaduserPicture(String account,String user_picture){
					try {
						conn = new ConnDB().getConnection(dbname, username, pass);
					    String sql = "update user_db set "
								+ "user_picture = '" + user_picture 
								+ "' where user_account = '"+ account + "'";
					    
						ps = conn.prepareStatement(sql);
						ps.executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						this.closeSource();
					}
				}
				//�ϴ�ͷ��ר�ø��·���
				public void Updateinfoheadpic(String nickname,String single,String account){
					try {
						conn = new ConnDB().getConnection(dbname, username, pass);
						ps = conn.prepareStatement("update user_db set nickname ='" + nickname
								 +  "',sign='"+single+"' where user_account = '" + account + "'");
						ps.executeUpdate();
						
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						this.closeSource();
					}
				}
				//���ͼƬ����
				public void uploadPicturedes(String path,String des){
					try {
						conn = new ConnDB().getConnection(dbname, username, pass);
					    String sql = "update pic_db set "
								+ "pic_describe = '" + des 
								+ "' where pic_path = '"+ path + "'";
					    
						ps = conn.prepareStatement(sql);
						ps.executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						this.closeSource();
					}
				}
				//�����ݿ��з��ص������������ͼƬ·��
				public ArrayList<PictureBean> getThreePictureOrderByClicks()
				{
					ArrayList<PictureBean> alPicture = new ArrayList<PictureBean>();
					try
					{
						conn = new ConnDB().getConnection(dbname, username, pass);
						stmt = conn.createStatement();
						res = stmt.executeQuery("SELECT * FROM pic_db ORDER BY pic_favors DESC LIMIT 3");
						while(res.next())
						{
							PictureBean picturebean = new PictureBean();
							picturebean.setPictureDpi(res.getString(3));
							picturebean.setPictureId(res.getInt(1));
							picturebean.setClicks(res.getInt(4));
							picturebean.setPath(res.getString(5));
							picturebean.setUploadName(res.getString(6));
							picturebean.setUploadTime(res.getString(7));
							picturebean.setDescribe(res.getString(8));
							alPicture.add(picturebean);
						}
					}catch(Exception e)
					{
						e.printStackTrace();
					}finally
					{
						this.closeSource();
					}
					return alPicture;
				}

				//ͨ��keyword����ͼƬ����
				public ArrayList<PictureBean> searchPicture(String keyword)
				{
					ArrayList<PictureBean> alPicture = new ArrayList<PictureBean>();
					ArrayList<KeywordBean> alKeyword = new ArrayList<KeywordBean>();;
					ArrayList<RelationBean> alRelation = new ArrayList<RelationBean>();
					try
					{
						conn = new ConnDB().getConnection(dbname, username, pass);
						ps = conn.prepareStatement("select * from keyword_db where keywords like '%" + keyword + "%'");
						System.out.println(keyword);
						res = ps.executeQuery();
						while(res.next())
						{
							KeywordBean keywordbean = new KeywordBean();
							keywordbean.setId(res.getInt(1));
							keywordbean.setKeyword(res.getString(2));
							
							alKeyword.add(keywordbean);
						}

						for(int i = 0; i < alKeyword.size(); i++)
						{
							int keyword_id = alKeyword.get(i).getId();
							ps = conn.prepareStatement("select * from pic_keyword_relation where key_id = '" + keyword_id + "'");
							res = ps.executeQuery();
							while(res.next())
							{
								RelationBean relationbean = new RelationBean();
								relationbean.setKeywordId(res.getInt(2));
								relationbean.setPictureId(res.getInt(1));
								
								alRelation.add(relationbean);
							}

						}
						for(int i = 0; i < alRelation.size(); i++)
						{
							System.out.println(alRelation.get(i).getPictureId());
							ps = conn.prepareStatement("select * from pic_db where pic_id = '" + alRelation.get(i).getPictureId() + "'");
							res = ps.executeQuery();
							while(res.next())
							{
								boolean bool = true;
								PictureBean picturebean = new PictureBean();
								picturebean.setPictureDpi(res.getString(3));
								picturebean.setPictureId(res.getInt(1));
								picturebean.setClicks(res.getInt(4));
								picturebean.setPath(res.getString(5));
								picturebean.setUploadName(res.getString(6));
								picturebean.setUploadTime(res.getString(7));
								picturebean.setDescribe(res.getString(8));
								
								for(int j = 0; j < alPicture.size(); j++)
								{
									if(alPicture.get(j).getPictureId() == res.getInt(1))
									{
										bool = false;
									}
								}
								if(bool)
								{
									alPicture.add(picturebean);
								}
							}

						}
						ps = conn.prepareStatement("select * from pic_db where pic_describe like '%" + keyword + "%'");
						res = ps.executeQuery();
						while(res.next())
						{
							boolean bool = true;
							PictureBean picturebean = new PictureBean();
							picturebean.setPictureDpi(res.getString(3));
							picturebean.setPictureId(res.getInt(1));
							picturebean.setClicks(res.getInt(4));
							picturebean.setPath(res.getString(5));
							picturebean.setUploadName(res.getString(6));
							picturebean.setUploadTime(res.getString(7));
							picturebean.setDescribe(res.getString(8));
							for(int j = 0; j < alPicture.size(); j++)
							{
								if(alPicture.get(j).getPictureId() == res.getInt(1))
								{
									bool = false;
								}
							}
							if(bool)
							{
								alPicture.add(picturebean);
							}
						}
						
					}catch(Exception e)
					{
						e.printStackTrace();
					}finally
					{
						this.closeSource();
					}
					
					Collections.sort(alPicture,new Comparator<PictureBean>(){
			            public int compare(PictureBean arg0, PictureBean arg1) {
			                return (String.valueOf(arg1.getClicks())).compareTo(String.valueOf(arg0.getClicks()));
			            }
			        });
					
					return alPicture;
				}
				//չʾ�û��ϴ�ͼƬ
				public ArrayList<PictureBean> showUploadPicture(String user_account)
				{
					ArrayList<PictureBean> alPicture = new ArrayList<PictureBean>();
					try
					{
						conn = new ConnDB().getConnection(dbname, username, pass);
						ps = conn.prepareStatement("select * from pic_db where upload_name = '" + user_account + "'");
						res = ps.executeQuery();
						while(res.next())
						{
							PictureBean picturebean = new PictureBean();
							picturebean.setPictureDpi(res.getString(3));
							picturebean.setPictureId(res.getInt(1));
							picturebean.setClicks(res.getInt(4));
							picturebean.setPath(res.getString(5));
							picturebean.setUploadName(res.getString(6));
							picturebean.setUploadTime(res.getString(7));
							picturebean.setDescribe(res.getString(8));
							alPicture.add(picturebean);
						}
					}catch(Exception e)
					{
						e.printStackTrace();
					}finally
					{
						this.closeSource();
					}
					return alPicture;
				}
				//�����û���id�Լ�ͼƬid�������ղ�ͼƬ��ɾ��������������һ������ֵ���ɹ�ʱΪtrue
				public boolean deleteCollection(int userId, int pictureId)
				{
					boolean bool = false;
					try
					{
						conn = new ConnDB().getConnection(dbname, username, pass);
						ps = conn.prepareStatement("delete from pic_user_relation where user_id = '" + userId + "' and pic_id = '" + pictureId + "'");
						int a = ps.executeUpdate();
						if(a == 1)
						{
							System.out.println("�����ɹ���");
							bool = true;
						}
					}catch(Exception e)
					{
						e.printStackTrace();
					}finally
					{
						this.closeSource();
					}
					return bool;
				}
	//����pic_path���õ�ͼƬ��PictureBean
	public PictureBean getPictureFromPath(String picPath)
	{
		PictureBean picturebean = new PictureBean();
		try
		{
			conn = new ConnDB().getConnection(dbname, username, pass);
			ps = conn.prepareStatement("select * from pic_db where pic_path = '" + picPath + "'");
			res = ps.executeQuery();
			while(res.next())
			{
				picturebean.setPictureDpi(res.getString(3));
				picturebean.setPictureId(res.getInt(1));
				picturebean.setClicks(res.getInt(4));
				picturebean.setPath(res.getString(5));
				picturebean.setUploadName(res.getString(6));
				picturebean.setUploadTime(res.getString(7));
				picturebean.setDescribe(res.getString(8));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			this.closeSource();
		}
		return picturebean;
	}
}

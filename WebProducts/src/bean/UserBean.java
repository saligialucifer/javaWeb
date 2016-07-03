package bean;

import java.util.HashMap;
import java.util.Map;
import org.json.*;

public class UserBean {

	private int id;
	private String email;
	private String nickname;
	private String password;
	private String head_path;
	private String sign;
	private boolean login;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHead_path() {
		return head_path;
	}
	public void setHead_path(String head_path) {
		this.head_path = head_path;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public boolean getLogin() {
		return login;
	}
	public void setLogin(boolean login) {
		this.login = login;
	}
	
	public String getUserData() {
		Map map = new HashMap();
		map.put("login", login);
		map.put("username", nickname);
		map.put("head_path", head_path);
		map.put("sign", sign);
		JSONObject json = new JSONObject(map);
		String data = json.toString();
		return data;
	}

}
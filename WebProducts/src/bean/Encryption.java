package bean;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Encryption {
	 private final String KEY_MD5 = "MD5";
	 
	 public final String calcMD1(String ss){ 
	       String s = ss == null ? "" : ss;           // ÈôÎªnull·µ»Ø¿Õ
	       byte[] data = s.getBytes();
	       String result = new String("");
	       try {
	    	   MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		       md5.update(data);
		       byte[] b = md5.digest();
		       for (int i = 0; i < b.length; i++) {
		   	    	String tmp = Integer.toHexString(b[i] & 0xFF);
		   	    	if (tmp.length() == 1) {
		   	    		result += "0" + tmp;
		   	    	} else {
		   	    		result += tmp;
		   	    	}
		       }
	       } catch (Exception e) {
	    	   e.printStackTrace();
	       }
	       
	       return result;
	 }
}

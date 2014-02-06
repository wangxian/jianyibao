package exts;

import java.security.MessageDigest;

public final class MD5 {
	public static final String getMD5(String str) {
		try {
			String result = "";
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(str.getBytes("utf-8"));
			for(byte b:bytes){
				String hex = Integer.toHexString(b&0xFF);
				result += ((hex.length() == 1) ? "0" : "") + hex;
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(MD5.getMD5("111111"));
	}
}

package com.traffic.common.utils;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

/**
 * MD5加密工具
 * 
 * @author Administrator
 *
 */
public class MD5Encrypt {
	protected static final Logger log = Logger.getLogger(MD5Encrypt.class);

	public MD5Encrypt() {
	}

	/**
	 * To encrypt the password
	 * 
	 * @param password
	 *            String
	 * @throws ConsoleException
	 * @return String
	 */
	public static String encrypt(String password) {

		String encryptPasswd = "";

		try {
			MessageDigest alg = MessageDigest.getInstance("MD5");
			alg.update(password.getBytes());
			byte[] digesta = alg.digest();

			encryptPasswd = byte2hex(digesta);
		} catch (Exception e) {
			log.error(e);
		}

		return encryptPasswd;
	}
	
	public static String encrypt(String password,String encodingType) {

		String encryptPasswd = "";

		try {
			MessageDigest alg = MessageDigest.getInstance("MD5");
			alg.update(password.getBytes(encodingType));
			byte[] digesta = alg.digest();

			encryptPasswd = byte2hex(digesta);
		} catch (Exception e) {
			log.error(e);
		}

		return encryptPasswd;
	}

	private static String byte2hex(byte[] b) {

		StringBuffer hs = new StringBuffer();
		String stmp = "";
		int len = b.length;
		for (int i = 0; i < len; i++) {
			stmp = (java.lang.Integer.toHexString(b[i] & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			} else
				hs.append(stmp);
		}

		return hs.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(MD5Encrypt
				.encrypt("POST&/v3/violations&Wed, 17 Jun 2015 00:56:34 GMT&0&" + MD5Encrypt.encrypt("uTIYrJn6vJTyt1ztBNbqQQDexDjpAM4m "),"UTF-8"));
		
	}

}

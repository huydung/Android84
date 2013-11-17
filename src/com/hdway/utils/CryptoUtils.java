package com.hdway.utils;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class CryptoUtils {

	public static String encrypt(String text, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

		byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
		return Base64.encodeToString(results, Base64.URL_SAFE);
	}

	public static String decrypt(String text, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		byte[] results = cipher.doFinal(Base64.decode(text.getBytes("UTF-8"),
				Base64.URL_SAFE));
		return new String(results, "UTF-8");
	}
	
	public static String getMD5(String input) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");

			byte[] result = md5.digest(input.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder(2 * result.length);
			for (Byte b : result) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.LogE("Exception while hashing password");
		}
		return null;
	}

}
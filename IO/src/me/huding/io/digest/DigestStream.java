package me.huding.io.digest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.Random;

public class DigestStream {

	public static void main(String[] args) throws Exception {
		byte[] digest = digestOutputStream();
		System.out.println(digest.length);
		System.out.println(toHexString(digest, DIGITS_LOWER));
		
		digest = digestIntputStream();
		System.out.println(digest.length);
		System.out.println(toHexString(digest, DIGITS_LOWER));
	}
	
	
	public static byte[] digestIntputStream() throws Exception{
		InputStream out = new FileInputStream(new File("digestOutputStrem"));
//		MessageDigest digest = MessageDigest.getInstance("MD5");
		MessageDigest digest = MessageDigest.getInstance("SHA");
		DigestInputStream din = new DigestInputStream(out, digest);
		for(int i = 0;i < 1000;i ++){
			din.read();
		}
		din.close();
		return din.getMessageDigest().digest();
	}
	
	
	
	public static byte[] digestOutputStream() throws Exception{
		OutputStream out = new FileOutputStream(new File("digestOutputStrem"));
//		MessageDigest digest = MessageDigest.getInstance("MD5");
		MessageDigest digest = MessageDigest.getInstance("SHA");
		DigestOutputStream dout = new DigestOutputStream(out, digest);
		Random random = new Random();
		for(int i = 0;i < 1000;i ++){
			int b = random.nextInt(10) + '0';
			dout.write(b);
		}
		dout.flush();
		dout.close();
		return dout.getMessageDigest().digest();
	}
	
	
	/**
     * Used to build output as Hex
     */
    static final char[] DIGITS_LOWER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     */
    static final char[] DIGITS_UPPER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	public static String toHexString(byte[] bytes,char[] toDigits){
		char[] chars = new char[bytes.length << 1];
		int j = 0;
		for(int i = 0;i < bytes.length;i ++){
			chars[j++] = toDigits[(0xF0 & bytes[i]) >>> 4];
			chars[j++] = toDigits[(0x0F & bytes[i])];
		}
		return new String(chars);
	}
	
	

	

}

package com.bdtt.rest.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
//import java.security.Signature;

import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;

public class RSAEncrypt {
    
	//public static final String SIGNATURE_ALGORITHM = "MD5withRSA";  

	public static PublicKey loadPublicKey() throws Exception {
	    //String publicKeyPEM = FileUtils.readFileToString(new File("/Users/Ryan/BDTT/rsa_public_key.pem"), StandardCharsets.UTF_8);
	    String publicKeyPEM = FileUtils.readFileToString(new File("rsa_public_key.pem"), StandardCharsets.UTF_8);

	    // strip of header, footer, newlines, whitespaces
	    publicKeyPEM = publicKeyPEM
	            .replace("-----BEGIN PUBLIC KEY-----", "")
	            .replace("-----END PUBLIC KEY-----", "")
	            .replaceAll("\\s", "");
	    //System.out.println(publicKeyPEM);
	    
	    // decode to get the binary DER representation
	    byte[] publicKeyDER = Base64.getDecoder().decode(publicKeyPEM);

	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyDER));
	    return publicKey;
	}
	
	public static String encriptUID(String uid) throws Exception {
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    //Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");//android需用这个
	    //String clearText = uid;

	    PublicKey publicKey = loadPublicKey();
	    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	    byte[] encrypted = cipher.doFinal(uid.getBytes(StandardCharsets.UTF_8));

	    System.out.println("uid: " + uid);
	    //System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));	
	    return Base64.getEncoder().encodeToString(encrypted);
	}
	
	public static String encriptAlipayinfo(JSONObject alipayinfo) throws Exception {
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    //Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");//android需用这个
	    //String clearText = uid;

	    PublicKey publicKey = loadPublicKey();
	    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	    byte[] encrypted = cipher.doFinal(alipayinfo.toString().getBytes(StandardCharsets.UTF_8));//先把JSon对象转化成字符串，再进行加密

	    System.out.println("alipayinfo: " + alipayinfo);
	    //System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));	
	    return Base64.getEncoder().encodeToString(encrypted);
	}
	
	public static void main(String[] args) throws Exception {
		//String uid=PropertiesHandle.readValue("uid");
		//String data = encriptUID(uid);
		//String data = encriptUID("1256918");
		//System.out.println(data);
		
        JSONObject jsonParam = new JSONObject();//新建一个json的格式对象
        jsonParam.put("name", "RyanTestTest");
        jsonParam.put("account","RyanTestTest@163.com");
        RSAEncrypt.encriptAlipayinfo(jsonParam);
		
	}
}
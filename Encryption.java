package com.ubaas.service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.ubaas.Tool.DBConnection;

public class Encryption {
	public PublicKey publicKey;
	public PrivateKey privateKey;
	public String publicKeyString ;
	public String privateKeyString;
	public String getPublicKeyString() {
		return publicKeyString;
	}

	public void setPublicKeyString(String publicKeyString) {
		this.publicKeyString = publicKeyString;
	}

	public String getPrivateKeyString() {
		return privateKeyString;
	}

	public void setPrivateKeyString(String privateKeyString) {
		this.privateKeyString = privateKeyString;
	}

	public static void main(String[] args) throws Exception{
		Encryption encryption = new Encryption();
		System.out.println("\n\nRSA非对称加密/解密");
		encryption.RSA("haonan");
	}

	public PublicKey getPublicKey(){
		return this.publicKey;

	}
	public void setPublicKey(PublicKey x){
		this.publicKey = x;
	}
	public PrivateKey getPrivateKey(){
		return this.privateKey;

	}
	public void setPrivateKey(PrivateKey y){
		this.privateKey = y;
	}
	public void RSA(String username) throws Exception{//生成公私钥
		Encryption encryption = new Encryption();
		//String plainText = "Hello , world !";  

		//创建密钥对KeyPair
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("rsa");  
		keyPairGenerator.initialize(1024);  //密钥长度推荐为1024位
		KeyPair keyPair = keyPairGenerator.generateKeyPair();  

		//获取公钥/私钥
		publicKey = keyPair.getPublic();  
		privateKey = keyPair.getPrivate();
		publicKeyString = new BASE64Encoder().encode(publicKey.getEncoded());//用base64处理
		privateKeyString = new BASE64Encoder().encode(privateKey.getEncoded());//用base64处理
		String sqlInsertPub = "insert into tb_pubkey(userid,pubkey) values(?,?)";
		String sqlInsertPrb = "insert into tb_prbkey(userid,prbkey) values(?,?)";
		String sqlInsertshuju = "insert into encry(shuju,miwen) values(?,?)";
		
		DBConnection db = new DBConnection();
		Connection conn=db.getConn();
		
		PreparedStatement pst=null;
		
		pst = conn.prepareStatement(sqlInsertPub);
		pst.setString(1, username);
		pst.setString(2, publicKeyString);
		int a = pst.executeUpdate();

		pst = conn.prepareStatement(sqlInsertPrb);
		pst.setString(1, username);
		pst.setString(2, privateKeyString);
		int b = pst.executeUpdate();
		pst.close();
		conn.close();
		db.close();
	}
	public String encryptionData(PublicKey k,String s){

		try {
			//加密操作
			Cipher cipher = Cipher.getInstance("rsa");  
			SecureRandom random = new SecureRandom();  
			cipher.init(Cipher.ENCRYPT_MODE, k, random);  
			byte[] cipherData= cipher.doFinal(s.getBytes());
			String cipherData2 = new BASE64Encoder().encode(cipherData);//用base64再次加密
			System.out.println("处理后的密文: " + cipherData2);
			return cipherData2;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	public String dncryptionData(PrivateKey k,String s){

		try {
			//解密操作
			Cipher cipher = Cipher.getInstance("rsa");  
			BASE64Decoder decoder = new BASE64Decoder();
			SecureRandom random = new SecureRandom();  
			cipher.init(Cipher.DECRYPT_MODE, k, random);  
			byte[] plainData = cipher.doFinal(decoder.decodeBuffer(s));//先用base64解密，再用DES解密
			System.out.println("明文: " + new String(plainData));
			return new String(plainData);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	//此方法用于构造公钥
	public PublicKey shiftPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey1 = keyFactory.generatePublic(keySpec);
		return publicKey1;
	}
	//此方法用于构造私钥
	public PrivateKey shiftPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey1 = keyFactory.generatePrivate(keySpec);
		return privateKey1;
	}
}

package com.ubaas.service;

import java.awt.Dialog;
import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;

public class Sha256Class extends JFrame{
	String filePath;
	String fileName;
	Dialog find;
	String filehash;
	public static void main(String[] args){
		Sha256Class sha = new Sha256Class();
		FileDialog fd=new FileDialog(sha, "读文件", FileDialog.LOAD);
		fd.setVisible(true);
		sha.fileName=fd.getFile();
		sha.filePath= fd.getDirectory();
		File file = new File(sha.filePath+sha.fileName);//定义一个file对象，用来初始化FileReader
		FileReader reader;
		String str = null;
		try {
			reader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
			StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
			String s = "";
			while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
				sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
				//System.out.println(s);
			}
			bReader.close();
			reader.close();
			str = sb.toString();
			//System.out.println(str );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//定义一个fileReader对象，用来初始化BufferedReader
		//System.out.println(str);
		String has = sha.SHA256(str);
		System.out.println("has="+has);
		
	}
	public String getHash(String path){
		Sha256Class sha = new Sha256Class();
		File file = new File(path);//定义一个file对象，用来初始化FileReader
		FileReader reader;
		String str = null;
		try {
			reader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
			StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
			String s = "";
			while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
				sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
			}
			bReader.close();
			reader.close();
			str = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}//定义一个fileReader对象，用来初始化BufferedReader
		String has = sha.SHA256(str);
		System.out.println("has="+has);
		return has;
	}
	/**
	 * 传入文本内容，返回 SHA-256 串
	 * 
	 * @param strText
	 * @return
	 */
	public String SHA256(final String strText)
	{
		return SHA(strText, "SHA-256");
	}

	/**
	 * 传入文本内容，返回 SHA-512 串
	 * 
	 * @param strText
	 * @return
	 */
	public String SHA512(final String strText)
	{
		return SHA(strText, "SHA-512");
	}

	/**
	 * 字符串 SHA 加密
	 * 
	 * @param strSourceText
	 * @return
	 */
	private String SHA(final String strText, final String strType)
	{
		// 返回值
		String strResult = null;

		// 是否是有效字符串
		if (strText != null && strText.length() > 0)
		{
			try
			{
				// SHA 加密开始
				// 创建加密对象 并傳入加密類型
				MessageDigest messageDigest = MessageDigest.getInstance(strType);
				// 传入要加密的字符串
				messageDigest.update(strText.getBytes());
				// 得到 byte 類型结果
				byte byteBuffer[] = messageDigest.digest();

				// 將 byte 轉換爲 string
				StringBuffer strHexString = new StringBuffer();
				// 遍歷 byte buffer
				for (int i = 0; i < byteBuffer.length; i++)
				{
					String hex = Integer.toHexString(0xff & byteBuffer[i]);
					if (hex.length() == 1)
					{
						strHexString.append('0');
					}
					strHexString.append(hex);
				}
				// 得到返回結果
				strResult = strHexString.toString();
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
		}

		return strResult;
	}

}

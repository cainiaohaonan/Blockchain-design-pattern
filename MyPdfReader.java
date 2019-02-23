package com.ubaas.service;

import java.awt.Dialog;
import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MyPdfReader extends JFrame{
	static final String filepath="H:\\testfile\\puk.txt";
	String filePath;
	String fileName;
	Dialog find;
	String filehash;
	public static void main(String[] args){
		MyPdfReader p = new MyPdfReader(); 
		//Date date = new Date();
		//Date date1 = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("hh:mm:ss:SSS");
		
		long t1 = System.currentTimeMillis();
		System.out.println(t1);
		System.out.println(sd.format(new Date(t1)));
		
		p.readF();
		
		long t2 = System.currentTimeMillis();
		System.out.println(t2);
		System.out.println(sd.format(new Date(t2)));
		
		System.out.println("鏈牸寮忓寲锛�"+(t2-t1));
		System.out.println("鏍煎紡鍖栵細"+sd.format(new Date(t2-t1)));
	}

	public String getFilehash() {
		return filehash;
	}

	public void setFilehash(String filehash) {
		this.filehash = filehash;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void readF()
	{
		FileDialog fd=new FileDialog(MyPdfReader.this, "璇绘枃浠�", FileDialog.LOAD);
		fd.setVisible(true);
		fileName=fd.getFile();
		filePath= fd.getDirectory();
		System.out.println("鏂囦欢璺緞锛�"+filePath+fileName);
		try {
			//String pdfString =MyPdfReader.this.getTextFromPdf(filePath+fileName);
			//byte[] s_byte = pdfString.getBytes();
			MyPdfReader.this.setFilehash(MyPdfReader.this.getMD5Checksum(filePath+fileName));
			System.out.println(MyPdfReader.this.getMD5Checksum(filePath+fileName));
		} catch (Exception e) {
			System.out.println("MyPdfReader閿欒淇℃伅锛�"+e.getMessage());
		}
	}

	public void toTextFile(byte[] by) {     
		try {
			File f = new File("h:\\mybytes.txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			System.out.println("Write PDF Content to txt file ...");
			FileOutputStream output = new FileOutputStream(f);
			output.write(by);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public byte[] createChecksum(String filename) throws Exception {
		InputStream fis = new FileInputStream(filename);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		return complete.digest();
	}

	public byte[] createChecksumByUrl(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		InputStream fis = url.openConnection().getInputStream();
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		return complete.digest();
	}


	public String getMD5Checksum(String filename) throws Exception {
		byte[] b = createChecksum(filename);
		String result = "";

		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	public String getMD5ChecksumByUrl(String urlStr) throws Exception {
		byte[] b = createChecksumByUrl(urlStr);
		String result = "";

		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
}

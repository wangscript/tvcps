/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.publishmanager.service.remotepublish.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: ftp上传</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-12-24 下午04:06:08
 * @history（历次修订内容、修订人、修订时间等）
 */
public class FtpSender extends Thread {
	
	private static final Logger log = Logger.getLogger(FtpSender.class);
	/** 文件集合 */
	private List<String> fileList; 
	
	/** 端口 */
	private int port;
	
	/** IP地址 */
	private String ipAddress;
	
	/** ftp登录名 */
	private String userName;
	
	/** ftp登录密码 */
	private String passWord;
	
	/** ftp远程文件目录 */
	private String ftpFilePath;	
	
	/** 发送的目录路径(需要替换的) */
	private String  sendDir;
	
	/** ftp操作类型类型*/
	private String type;
	private boolean error; 
	
	/**
	 * 
	 * @param ip 远程ip地址
	 * @param port 端口
	 * @param userName FTP登录名
	 * @param passWord FTP登录密码
	 * @param ftpFilePath FTP文件路径
	 * @param fileList 文件集合
	 */
	public FtpSender(String ip, String port,String userName,String passWord,String ftpFilePath, List<String> fileList, String sendDir,String type) {
		this.fileList = fileList;		 
		this.port = Integer.parseInt(port); 
		this.ipAddress = ip;
		this.userName = userName;
		this.passWord = passWord;
		this.ftpFilePath = ftpFilePath; 
		this.sendDir = sendDir;
		this.type = type;
	}

	/**
	 * 
	 * @param fileList 文件集合
	 * @param sendDir 发送的目录路径
	 */
	public void send(List<String> fileList, String sendDir) {	 
		for (int i = 0; i < fileList.size(); i++) {
			try {
				String fileName = "";// 文件名称		
				String tempFileName = "";
				FtpClient t = new FtpClient();					
				File file = new File(fileList.get(i));
				fileName = file.getPath();
				tempFileName = fileName;
				fileName = fileName.substring(sendDir.length());
	//			System.out.println(file.getAbsolutePath());
		//		FileInputStream in  = new FileInputStream(file);
		//		ftpFilePath = ftpFilePath + "/" + 
				DataInputStream di = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
				String filePath = "";
				log.debug("fileName====================="+fileName);
				fileName = fileName.replaceAll("\\\\", "/");
				String temp[] = fileName.split("/");
				int length = temp.length;
	//			log.debug("length===================="+length);
				if(length > 1){					
					filePath = ftpFilePath + fileName.replace( "/"+temp[length - 1],"");
					fileName = temp[length - 1];
				}
				
	//			log.debug("fileName==============================="+fileName);
				filePath = filePath.replaceAll("\\\\", "/");
	//			log.debug("filePath================================"+filePath);
				
				boolean flag = t.uploadFile(ipAddress, port, userName, passWord, filePath, fileName, di);
				if(!flag){
					log.error(tempFileName + "文件发送失败!");
				}
			} catch (Exception e) {
				// 继续执行
				e.printStackTrace();
				log.error("ftp pulish：send file error.", e);
			}
		}
		 
	}
	
	/**
	 * 
	 * @param fileList 文件集合
	 * @param sendDir 发送的目录路径
	 */
	public void delete(List<String> fileList, String sendDir) {	 
		for (int i = 0; i < fileList.size(); i++) {
			try {
				String fileName = "";// 文件名称				 
				FtpClient t = new FtpClient();					
				File file = new File(fileList.get(i));
				fileName = file.getPath();
				fileName = fileName.substring(sendDir.length());
	//			System.out.println(file.getAbsolutePath());
		//		FileInputStream in  = new FileInputStream(file);
		//		ftpFilePath = ftpFilePath + "/" + 
				DataInputStream di = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
				String filePath = "";
				log.debug("fileName====================="+fileName);
				fileName = fileName.replaceAll("\\\\", "/");
				String temp[] = fileName.split("/");
				int length = temp.length;
				log.debug("length===================="+length);
				if(length > 1){					
					filePath = ftpFilePath + fileName.replace( "/"+temp[length - 1],"");
					fileName = temp[length - 1];
				}
				
				log.debug("fileName==============================="+fileName);
				filePath = filePath.replaceAll("\\\\", "/");
				log.debug("filePath================================"+filePath);
				
				boolean flag = t.delFile(ipAddress, port, userName, passWord, filePath, fileName);
			 
			} catch (Exception e) {
				// 继续执行
				e.printStackTrace();
				log.error("ftp pulish：send file error.", e);
			}
		}
		 
	}
	
	public void run() {
		if(type.equals("send")){
			send(fileList,sendDir);
		}else if(type.equals("delete")){
			delete(fileList,sendDir);
		}
		
	}
}

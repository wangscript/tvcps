/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.publishmanager.service.remotepublish.server;

import java.io.*;
import java.net.*;

import com.j2ee.cms.common.core.util.FileUtil;

/**
 * 
 * @author Administrator
 *@version 1.0 BackupServerHandle类提供了服务器端多线程处理文件备份
 */
public class PublishServerHandle implements Runnable {
	private InputStream inputStream;// 网络输入流
	private OutputStream outputStream;// 网络输出流
	private String receiveDir;// 存储文件的目录
	private Socket clientSocket;// 客户端套接字

	public PublishServerHandle(Socket client, String receiveDir) {
		this.receiveDir = receiveDir;
		// 新建存储目录
		File dir = new File(receiveDir);
		if (!dir.exists()) {
			dir.mkdir();
		}
		clientSocket = client;
	}

	public void run() {
		try {
			File dir = new File(receiveDir);
			// 定义接收的文件名称和长度
			String fileName = "";
			long fileLength = 0;
			// 获得网络输入输出流
			inputStream = clientSocket.getInputStream();
			outputStream = clientSocket.getOutputStream();

			// 构建本地输入输出流
			DataInputStream dataInputStream = new DataInputStream(
					new BufferedInputStream(inputStream));
			fileName = dataInputStream.readUTF();// 读取文件名
			String returnInfo = "";
						
			fileName = dir + fileName;
			
			if (!new File(fileName).exists()) {
				new File(fileName.replaceAll("\\.", ""));
			}
			
			//判断是目录还是文件
			if (fileName.matches("^.+[.].+")) {
				new File(fileName.replaceAll("[/\\\\][^/\\\\]+$", "")).mkdirs();
				fileLength = dataInputStream.readLong();// 读取文件长度

				// 将客户端发送的数据写入磁盘文件中
				DataOutputStream dataOutputStream = new DataOutputStream(
						new BufferedOutputStream(new FileOutputStream(fileName)));
				int first;
				while ((first = dataInputStream.read()) != -1) {
					dataOutputStream.write(first);
				}
				dataOutputStream.flush();
				System.out.println("服务器备份文件 " + fileName + " 成功!");

				// 关闭输入输出流
				dataOutputStream.close();
				// 发送信息给客户端(需要实现另一个Socket来发送信息)
				long tempLength = 0;
				File tempFile = new File(fileName);
				tempLength = tempFile.length();
				System.out.println("Send Length:" + fileLength);
				System.out.println("Get Length:" + tempLength);
				
				System.out.println("========================"  + fileName);
				if (fileName.endsWith("WEB-INF.zip")) {
					System.out.println(fileName);
					FileUtil.unZip(fileName, receiveDir);	
					/** add by 娄伟峰 */
					//需要将客户端使用的web.ml拷贝到web-inf根目录，此web.xml精简了一些配置，也取消了注册码的验证
					FileUtil.copy(receiveDir+"/WEB-INF/conf/client/web.xml", receiveDir+"/WEB-INF");
				}
				
				if (tempLength == fileLength) {
					returnInfo = "服务器备份文件 " + fileName + "成功!";
					SendReturnInfo send = new SendReturnInfo(clientSocket
							.getInetAddress(), returnInfo);
					send.sendReturnInfo();
					System.out.println("已发送复制 " + fileName + " 成功信息给客户端!");
				} else {
					returnInfo = "服务器备份文件 " + fileName + "失败!";
					SendReturnInfo send = new SendReturnInfo(clientSocket
							.getInetAddress(), returnInfo);
					send.sendReturnInfo();
					System.out.println("已发送复制 " + fileName + "失败信息给客户端!");
				}
				System.out.println("---------------------------------------------------");
			} else {
				new File(fileName).mkdirs();
				returnInfo = "服务器备份目录 " + fileName + "成功!";
				SendReturnInfo send = new SendReturnInfo(clientSocket
						.getInetAddress(), returnInfo);
				send.sendReturnInfo();
				System.out.println("已发送复制 " + fileName + " 成功信息给客户端!");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (clientSocket != null) {
					clientSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}

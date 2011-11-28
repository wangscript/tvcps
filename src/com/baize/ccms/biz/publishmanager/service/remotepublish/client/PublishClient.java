/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.publishmanager.service.remotepublish.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * 
 * @author Administrator
 *@version 1.0 BackupClient类实现文件的传输到服务器
 */
public class PublishClient implements Runnable {
	
	private static final Logger log = Logger.getLogger(PublishClient.class);
	
	private int port;// 服务器端口
	private InetAddress ipAddress;// 服务器IP地址
	private Socket clientSocket;// 客户端套接字
	private InputStream inputStream;// 网络输入流
	private OutputStream outputStream;// 网络输出流
	private String sendDir;

	private String filePath;

	// 构造函数(获得服务器端IP地址和监听端口号)
	public PublishClient(InetAddress ipAddress, int port, String filePath, String sendDir) {
		this.sendDir = sendDir;
		this.ipAddress = ipAddress;
		this.port = port;
		this.filePath = filePath;
	}

	// 向服务器端发送文件
	public void run() {
		try {
			String fileName = "";// 文件名称
			long fileLength = 0;// 文件长度
			File file = new File(filePath);
			fileName = file.getPath();
			fileLength = file.length();

			// 连接服务器,获得网络输入输出流
			clientSocket = new Socket(ipAddress, port);
			inputStream = clientSocket.getInputStream();
			outputStream = clientSocket.getOutputStream();

			log.debug("客户端正在发送文件 " + fileName + "\n请等待: ");

			// 获得本地的文件输入流(此处使用BufferedInputStream大大地提高速度)
			DataInputStream di = new DataInputStream(new BufferedInputStream(
					new FileInputStream(file)));
			// 获得网络输出流(此处使用BufferedOutputStream大大地提高速度)
			DataOutputStream ds = new DataOutputStream(
					new BufferedOutputStream(outputStream));
			// 发送文件名和长度
			fileName = fileName.substring(sendDir.length());
			
			ds.writeUTF(fileName);
			ds.writeLong(fileLength);
			ds.flush();
			// 发送文件内容
			int first;
			long j = fileLength / 10;
			long i = 0;
			int flag = 0;
			long begin = System.currentTimeMillis();
			while ((first = di.read()) != -1) {
				ds.write(first);
				// 如果文件大小大于1KB
				if (fileLength > 2048) {
					i++;
					if (i % j == 0) {
						flag += 10;
						if (flag == 100) {
							log.debug("100%!");
						} else {
							log.debug(flag + "%-->");
						}

					}
				}
			}
			ds.flush();
			// 关闭输入输出流
			ds.close();
			di.close();
			long end = System.currentTimeMillis();
			long costTime = end - begin;
			double average = 0;
			String speed = "网络最大值";
			if (costTime > 1000) {
				average = costTime / 1000;
			}
			if (costTime != 0) {
				speed = (fileLength / costTime) + "Kb";
			}

			log.debug("客户端发送文件 " + fileName + " 完毕!");
			log.debug("一共用时: " + average + " 秒   平均速度: " + speed);

			// 接收服务器端返回的信息(此处需要等待服务器的返回信息)(需要实现一个ServerSocket来监听并获得信息)
			//GetReturnInfo getReturnInfo = new GetReturnInfo();
			//getReturnInfo.listeningPort();
			log.debug("---------------------------------------------------------------------------");
		} catch (IOException e) {
			log.debug("remote publish error: send file error.", e);
			//Sender.errorNum++;
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
				log.error("remote publish error: close file error.", e);
			}

		}
	}

}

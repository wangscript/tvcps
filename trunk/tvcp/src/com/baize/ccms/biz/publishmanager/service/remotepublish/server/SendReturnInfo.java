/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.publishmanager.service.remotepublish.server;

import java.io.*;
import java.net.*;

/**
 * @author Administrator
 *@version 1.0 SendReturnInfo类接收服务器端关于文件复制成功与否的信息
 */
public class SendReturnInfo {
	private Socket clientSocket;
	private OutputStream outputStream;
	private InetAddress ipAddress;
	private int port = 199099;
	private String returnInfo;

	public SendReturnInfo(InetAddress ipAddress, String returnInfo) {
		this.ipAddress = ipAddress;
		this.returnInfo = returnInfo;
	}

	public void sendReturnInfo() {
		try {
			clientSocket = new Socket(ipAddress, port);
			outputStream = clientSocket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream, true);
			printWriter.println(returnInfo);
			printWriter.close();
			outputStream.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

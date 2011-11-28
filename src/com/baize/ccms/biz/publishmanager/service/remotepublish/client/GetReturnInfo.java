/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.publishmanager.service.remotepublish.client;

import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

/**
 * @author Administrator
 *@version 1.0 GetReturnInfo类接收服务器端关于文件复制成功与否的信息
 */
public class GetReturnInfo {
	
	private static final Logger log = Logger.getLogger(GetReturnInfo.class);
	
	private static ServerSocket serverSocket;
	private Socket clientSocket;
	private int port = 199099;
	private InputStream inputStream;

	public GetReturnInfo() {
		try {
			if (serverSocket == null) {
				serverSocket = new ServerSocket(port);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listeningPort() {
		try {
			clientSocket = serverSocket.accept();
			inputStream = clientSocket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));
			String returnInfo = "";
			while ((returnInfo = br.readLine()) != null) {
				log.debug(returnInfo);
			}
			br.close();
			inputStream.close();
			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			log.error("remote publish error: return info erro.", e);
		}
	}
}
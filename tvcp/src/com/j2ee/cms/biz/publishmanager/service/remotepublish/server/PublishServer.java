/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.publishmanager.service.remotepublish.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Administrator
 *@version 1.0 BackupServer类提供了服务器端端口监听
 */
public class PublishServer {
	private int port;// 服务器端口
	private ServerSocket serverSocket;// 服务器端套接字
	private Socket clientSocket;// 客户端套接字
	/** 接收端目录 */
	private String receiveDir;
	private int times = 0;

	// 构造函数初始化端口
	public PublishServer(int port, String receiveDir) {
		this.port = port;
		this.receiveDir = receiveDir;
	}

	// 监听端口等待客户端的连接
	public void listeningPort() {
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				System.out.println("正在监听端口 " + port + "...");
				clientSocket = serverSocket.accept();
				// 接收到客户端的连接后，新建一个线程处理连接
				++times;
				System.out.println("[" + times + "]:接收到来自"
						+ clientSocket.getRemoteSocketAddress()
						+ "的主机连接服务器信息...");
				System.out.println("接受客户端连接..." + times);
				new Thread(new PublishServerHandle(clientSocket, receiveDir)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * //关闭服务器 public void shutdownServer(){ if(serverSocket!=null){ try {
	 * serverSocket.close(); } catch (IOException e) { e.printStackTrace(); } }
	 * } public ServerSocket getServerSocket() { return serverSocket; }
	 */
}

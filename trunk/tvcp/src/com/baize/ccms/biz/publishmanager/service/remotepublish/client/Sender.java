/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.publishmanager.service.remotepublish.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-9-13 下午05:42:48
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Sender extends Thread {
	
	private static final Logger log = Logger.getLogger(Sender.class);
	
	private List<String> fileList;
	
	private String buildDir;
	
	/** 端口 */
	private int port;
	
	/** IP地址 */
	private InetAddress ipAddress;
	
	private boolean error;
	
	public static int errorNum;
	
	public Sender(String ip, String port, List<String> fileList, String buildDir) {
		this.fileList = fileList;
		this.buildDir = buildDir;
		this.port = Integer.parseInt(port);
		String[] ipValue = ip.split("\\.");
		byte[] ipByte = new byte[4];
	//	System.out.println();
		ipByte[0] = (byte) Integer.parseInt(ipValue[0]);
		ipByte[1] = (byte) Integer.parseInt(ipValue[1]);
		ipByte[2] = (byte) Integer.parseInt(ipValue[2]);
		ipByte[3] = (byte) Integer.parseInt(ipValue[3]);
		try {
			ipAddress = InetAddress.getByAddress(ipByte);
		} catch (UnknownHostException e) {
			log.info("远程发布——建立远程连接错误！");
			log.error("remote pulish：establish remote link error.", e);
			error = true;
		}
		
		try {
			// 判断连接是否畅通
			new Socket(ipAddress, this.port);
		} catch (IOException e) {
			log.info("远程发布：建立远程连接错误！");
			throw new RuntimeException("publish error: can't connect remote server!");
		}
	}

	public void send(List<String> fileList, String buildDir) {
		if (!error) {
			for (int i = 0; i < fileList.size(); i++) {
				try {
					if (errorNum > 10) {
	//					System.out.println("错误过多，退出连接...");
	//					break;
					}
					new PublishClient(ipAddress, port, fileList.get(i), buildDir).run();
				} catch (Exception e) {
					// 继续执行
					log.error("remote pulish：send file error.", e);
				}
			}
		}
	}
	
	public void run() {
		send(fileList, buildDir);
	}
}

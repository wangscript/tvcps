/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.publishmanager.service.remotepublish.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * <p>标题: —— FTP上传文件</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 发布管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-12-24 上午11:33:13
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class FtpClient
{	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public boolean uploadFile(String url, int port, String username,
			String password, String path, String filename, InputStream input){
		// 初始表示上传失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftp = null;
		try
		{
			//登陆FTP
			ftp = this.login(url, port, username, password);
			if(ftp == null)
				return success;
			String temp[] = path.split("/");
			int length = temp.length;
			if(length > 0){
				String tempPath = "";				
				for(int i = 2 ; i < length ; i++){
					tempPath = temp[i];
			//		System.out.println("tempPath=================="+tempPath);
					ftp.makeDirectory(tempPath);
					ftp.changeWorkingDirectory(tempPath);
				}
			}
			
			 
			
			
			// 转到指定上传目录
		//	ftp.changeWorkingDirectory(path);
			// 将上传文件存储到指定目录
			ftp.storeFile(filename, input);
			ftp.changeWorkingDirectory("/");
	/*		if(length == 1){
				ftp.changeWorkingDirectory(temp[0]);
			}
			if(length > 1){
				ftp.changeWorkingDirectory(temp[1]);
			}*/
			// 关闭输入流
			if(input != null){
				input.close();
			}
			if(ftp != null){
				// 退出ftp
				ftp.logout();
				
			}
			// 表示上传成功
			success = true;
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (ftp.isConnected())
			{
				try
				{
					ftp.disconnect();
				} catch (IOException ioe)
				{
				}
			}
		}
		return success;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return boolean true下载成功false下载失败
	 */
	public boolean downFile(String url, int port, String username,
			String password, String remotePath, String fileName,
			String localPath)
	{
		// 初始表示下载失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftp = null;
		try
		{
			//登陆FTP
			ftp = this.login(url, port, username, password);
			if(ftp==null)
			 return success;
			
			// 转到指定上传目录
			ftp.changeWorkingDirectory(remotePath);
			
			// 列出该目录下所有文件
			FTPFile[] fs = ftp.listFiles();
			// 遍历所有文件，找到指定的文件
			for (FTPFile ff : fs)
			{
				if (ff.getName().equals(fileName))
				{
					// 根据绝对路径初始化文件
					File localFile = new File(localPath + "/" + ff.getName());
					// 输出流
					OutputStream is = new FileOutputStream(localFile);
					// 下载文件
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}
			// 退出ftp
			ftp.logout();
			// 下载成功
			success = true;
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (ftp.isConnected())
			{
				try
				{
					ftp.disconnect();
				} catch (IOException ioe)
				{
				}
			}
		}
		return success;
	}
	
	/**
	 * 
	 * @param url 远程机的url,也就是ip地址
	 * @param port 端口
	 * @param username 登录名
	 * @param password 密码
	 * @param remotePath 远程目录路径，以/开头
	 * @param fileName 需要删除的文件名
	 * @return boolean true删除成功 false删除失败
	 */
	public boolean delFile(String url, int port, String username,
			String password, String remotePath, String fileName){
		// 初始表示删除文件失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftp = null;
		try
		{
			//登陆FTP
			ftp = this.login(url, port, username, password);
			if(ftp==null)
			 return success;
			
			// 转到指定目录
			ftp.changeWorkingDirectory(remotePath);
			
			// 列出该目录下所有文件
			FTPFile[] fs = ftp.listFiles();
			// 遍历所有文件，找到指定的文件
			for (FTPFile ff : fs)
			{
				if (ff.getName().equals(fileName))
				{
					success = ftp.deleteFile(fileName);
				}
			}
			// 退出ftp
			ftp.logout();			
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (ftp.isConnected())
			{
				try
				{
					ftp.disconnect();
				} catch (IOException ioe)
				{
				}
			}
		}
		return success;
	}
	

	 /**
	  * 登录ftp
	  * @param url 远程机的url,也就是ip地址
	  * @param port 端口
	  * @param username 登录名
	  * @param password 密码
	  * @return FTPClient ftp客户端对象
	  * @throws SocketException
	  * @throws IOException
	  */
	public FTPClient login(String url,int port,String username,String password) throws SocketException, IOException
	{
		FTPClient ftp = new FTPClient();
		// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		ftp.connect(url, port);
		// 登录ftp
		ftp.login(username, password);
		
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply))
		{
			ftp.disconnect();
			return null;
		}	
		
		return ftp;
	}
	

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SocketException 
	 */
	public static void main(String[] args) throws SocketException, IOException
	{
		FtpClient t = new FtpClient();
		
		File file = new File("c:/ftp.java");
		System.out.println(file.getAbsolutePath());
		FileInputStream in  = new FileInputStream(file);
		
		boolean flag   =t.uploadFile("192.168.1.20", 21, "lwf", "lwf", "/wwwroot", "Test.java", in);
		//if(flag)
		//	System.out.println("上传成功");
		
	//	flag = t.delFile("192.168.1.20", 21, "lwf", "lwf", "/wwwroot", "test.txt");
		if(flag)
			System.out.println("下载成功");
	}

}
package com.j2ee.cms.biz.publishmanager.service.remotepublish.client;

/*
 * 本程序用于实现文件夹的复制
 * 作者: 金鑫鑫
 * 完成日期:2009/5/19
 * 版本号:1.0
 * 测试说明:在Eclipse工程目录下面新建一个Test文件夹,
 * 然后在里面放入任何你想放入的文件或者目录,可以有多层
 * 目录.运行程序,可以看到在工程目录下面出现了一个Backup
 * 文件夹,看一下是否和Test文件夹中内容相同.
 * 联系方式:xinxinli1234@sina.com
 * 版权说明:没有任何版权,可以任意修改
 * 最后:本程序没有界面,可以自己修改文件的主目录和备份目录.
 */
import java.io.*;
import java.util.*;

public class FileSelection {
	private File rootDirectory;// 根目录
	//private File[] fileArray;// 文件目录下面的文件列表(包括目录,用于多次判断)
	private List<File> fileList; // 用于存储文件列表

	// 初始化参数
	public FileSelection(String filePath) {
		fileList = new ArrayList<File>();
		rootDirectory = new File(filePath);
		//rootDirectory.mkdir();
	}

	// 获得文件(不包括目录)的列表
	public void initFileArray() {

		if (rootDirectory.isDirectory()) {
			// 遍历目录下面的文件和子目录
			File[] fileArray = rootDirectory.listFiles();
			for (int i = 0; i < fileArray.length; i++) {
				// 如果是文件,添加到文件列表中
				if (fileArray[i].isFile()) {
					fileList.add(fileArray[i]);
				}
				// 否则递归遍历子目录
				else if (fileArray[i].isDirectory()) {
					//fileList.add(fileArray[i]);
					//fileList[i].mkdir();
					iterateFile(fileArray[i]);
//					rootDirectory = fileArray[i];
					//initFileArray();
				}

			}
			
		/*	for (int i = 0; i < fileArray.length; i++) {
				System.out.println(fileArray[i]);
			}*/
		}
		
	}
	
	private void iterateFile(File file) {
		File[] fileArray = file.listFiles();
		for (int i = 0; i < fileArray.length; i++) {
			// 如果是文件,添加到文件列表中
			if (fileArray[i].isFile()) {
				fileList.add(fileArray[i]);
				
			// 否则递归遍历子目录
			} else if (fileArray[i].isDirectory()) {
				//fileList.add(fileArray[i]);
				//fileList[i].mkdir();
				iterateFile(fileArray[i]);
			}
		}
	}

	// 将文件信息添加到列表中
	public void addFiles(File f) {
		fileList.add(f);
	}

	// 访问器返回文件列表
	public List<File> getFileList() {
		return fileList;
	}

}
package com.house.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Title: FileUtil.java
 * @Package com.joyque.plugin.house365.util
 * @Description: 文件操作工具类
 * @Company 江苏集群信息产业股份有限公司
 * @author 娄伟峰
 * @date 2011-1-12 上午11:05:43
 * @version V1.0
 */

public class FileUtil {
	/** 日志 */
	private static Logger log = Logger.getLogger(FileUtil.class);

	/**
	 * 将流中的文本读入一个 StringBuffer 中
	 * 
	 * @author LOUWEIFENG
	 * @param buffer
	 *            字符串连接对象
	 * @param is
	 *            输入流
	 */
	public static void readToBuffer(StringBuffer buffer, InputStream is)
			throws IOException {
		String line; // 用来保存每行读取的内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
			buffer.append(line); // 将读到的内容添加到 buffer 中
			buffer.append("\n"); // 添加换行符
			line = reader.readLine(); // 读取下一行
		}
	}

	/**
	 * 将 StringBuffer 中的内容读出到流中
	 * 
	 * @author LOUWEIFENG<br>
	 * @param buffer
	 * @param os
	 */

	public static void writeFromBuffer(StringBuffer buffer, OutputStream os) {
		// 用 PrintStream 可以方便的把内容输出到输出流中
		// 其对象的用法和 System.out 一样
		// （System.out 本身就是 PrintStream 对象）
		PrintStream ps = new PrintStream(os);
		ps.print(buffer.toString());
		ps.flush();
	}

	/**
	 * 从输入流中拷贝内容到输入流中
	 * 
	 * @author LOUWEIFENG<br>
	 * @param is
	 *            输入流
	 * @param os
	 *            输出流
	 * @throws IOException
	 */

	public static void copyStream(InputStream is, OutputStream os)
			throws IOException {
		// 这个读过过程可以参阅 readToBuffer 中的注释
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
		line = reader.readLine();
		while (line != null) {
			writer.println(line);
			line = reader.readLine();
		}
		writer.flush();
		// 最后确定要把输出流中的东西都写出去了
		// 这里不关闭 writer 是因为 os 是从外面传进来的
		// 既然不是从这里打开的，也就不从这里关闭
		// 如果关闭的 writer，封装在里面的 os 也就被关了
	}

	/**
	 * 调用 copyStream(InputStream, OutputStream) 方法拷贝文本文件
	 * 
	 * @author LOUWEIFENG<br>
	 * @param inFilename
	 *            输入文件
	 * @param outFilename
	 *            输出文件
	 */

	public static void copyTextFile(String inFilename, String outFilename)
			throws IOException {
		// 先根据输入/输出文件生成相应的输入/输出流
		InputStream is = new FileInputStream(inFilename);
		OutputStream os = new FileOutputStream(outFilename);
		FileUtil.copyStream(is, os); // 用 copyStream 拷贝内容
		is.close(); // is 是在这里打开的，所以需要关闭
		os.close(); // os 是在这里打开的，所以需要关闭
	}

	/**
	 * 获取从键盘输入的字符，写到文件中
	 * 
	 * @author LOUWEIFENG<br>
	 * @param outFileName
	 *            输出文件
	 */

	public static void write2FileByBuffer(String outFileName) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			File file = new File(outFileName);
			String readString = br.readLine();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			while (!"".equals(readString)) {
				bw.write(readString);
				readString = br.readLine();
			}

			bw.flush();
			br.close();
			bw.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * java 文件写 BASE64加密
	 * 
	 * @author LOUWEIFENG<br>
	 * @param fileContent
	 * @param filePath
	 * @return 文件
	 */

	public static File saveBase64File(String fileContent, String filePath)
			throws IOException {
		if (fileContent == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] picBinary = decoder.decodeBuffer(fileContent);
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);
		out.write(picBinary);
		out.close();
		return file;
	}

	/**
	 * java 文件读 BASE64解密
	 * 
	 * @author LOUWEIFENG<br>
	 * @param filePath
	 * @return String
	 */

	public static String readBase64File(String filePath) {
		try {
			sun.misc.BASE64Encoder encoder = new BASE64Encoder();
			File file = new File(filePath);
			java.io.FileReader reader = new FileReader(file);
			char[] c = new char[1024];
			StringBuffer buf = new StringBuffer();
			int index = -1;
			while ((index = reader.read(c)) > 0) {
				buf.append(c, 0, index);
			}
			String result = encoder.encode(buf.toString().getBytes());
			reader.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建与删除文件
	 * 
	 * @author LOUWEIFENG<br>
	 * @param filePath
	 * @param fileName
	 * @return 创建成功返回true
	 * @throws IOException
	 */

	public static boolean createAndDeleteFile(String filePath, String fileName)
			throws IOException {
		boolean result = false;
		File file = new File(filePath, fileName);
		if (file.exists()) {
			file.delete();
			result = true;
			System.out.println("文件已经删除！");
		} else {
			file.createNewFile();
			result = true;
			System.out.println("文件已经创建！");
		}
		return result;
	}

	/**
	 * 创建和删除目录
	 * 
	 * @author LOUWEIFENG
	 * @param folderName
	 *            文件夹名
	 * @param filePath
	 *            文件路径
	 * @return 删除成功返回true
	 */

	public static boolean createAndDeleteFolder(String folderName,
			String filePath) {
		boolean result = false;
		try {
			File file = new File(filePath + folderName);
			if (file.exists()) {
				file.delete();
				System.out.println("目录已经存在，已删除!");
				result = true;
			} else {
				file.mkdir();
				System.out.println("目录不存在，已经建立!");
				result = true;
			}
		} catch (Exception ex) {
			result = false;
			System.out.println("CreateAndDeleteFolder is error:" + ex);
		}
		return result;
	}

	/**
	 * 输出目录中的所有文件及目录名字
	 * 
	 * @author LOUWEIFENG
	 * @param filePath
	 *            文件路径
	 */

	public static void readFolderByFile(String filePath) {
		File file = new File(filePath);
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].isFile()) {
				System.out.println("File : " + tempFile[i].getName());
			}
			if (tempFile[i].isDirectory()) {
				System.out.println("Directory : " + tempFile[i].getName());
			}
		}
	}

	/**
	 * 检查文件中是否为一个空
	 * 
	 * @author LOUWEIFENG<br>
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @return 为空返回true
	 */

	public static boolean fileIsNull(String filePath, String fileName)
			throws IOException {
		boolean result = false;
		FileReader fr = new FileReader(filePath + fileName);
		if (fr.read() == -1) {
			result = true;
			System.out.println(fileName + " 文件中没有数据!");
		} else {
			System.out.println(fileName + " 文件中有数据!");
		}
		fr.close();
		return result;
	}

	/**
	 * 读取指定文件中的数据，默认编码为UTF-8
	 * 
	 * @author LOUWEIFENG<br>
	 * @param fileName
	 *            文件名称
	 * @return String
	 * @throws IOException
	 */

	public static String readTextFile(String filePath, String fileName)
			throws IOException {
		return FileUtil.readTextFile(filePath, fileName, null);
	}

	/**
	 * 根据encoding编码读取指定文件中的数据
	 * 
	 * @author LOUWEIFENG
	 * @param fileName
	 *            文件名称
	 * @param encoding
	 *            文件路径
	 * @return String
	 */

	public static String readTextFile(String filePath, String fileName,
			String encoding) throws IOException {
		StringBuffer result = new StringBuffer();

		BufferedReader reader = null;
		String line;
		try {
			if ("".equals(encoding) || encoding == null)
				encoding = "UTF-8";
			File file = new File(filePath);
			if (!file.exists()) {
				return "";
			}
			file = new File(filePath, fileName);
			if (!file.exists())
				return "";
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), encoding));

			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
		} catch (UnsupportedEncodingException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				throw e;
			}

		}

		return result.toString();
	}

	/**
	 * 根据默认编码UTF-8读取指定文件中的数据,并返回List<String> LIST中的每人元素为一行
	 * 
	 * @author LOUWEIFENG
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @return List<String>
	 */

	public static List<String> readTextFile2List(String filePath,
			String fileName) throws IOException {
		return readTextFile2List(filePath, fileName, null);
	}

	/**
	 * 根据encoding编码读取指定文件中的数据,并返回List<String> LIST中的每人元素为一行
	 * 
	 * @author LOUWEIFENG
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @param encoding
	 *            编码格式
	 * @return List<String>
	 */

	public static List<String> readTextFile2List(String filePath,
			String fileName, String encoding) throws IOException {
		List<String> result = new ArrayList<String>();

		BufferedReader reader = null;
		String line;
		try {
			if ("".equals(encoding) || encoding == null)
				encoding = "UTF-8";
			File file = new File(filePath);
			if (!file.exists()) {
				return null;
			}
			file = new File(filePath, fileName);
			if (!file.exists())
				return null;
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), encoding));

			while ((line = reader.readLine()) != null) {
				result.add(line);
			}
		} catch (UnsupportedEncodingException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				throw e;
			}

		}

		return result;
	}

	/**
	 * 向指定文件写入数据，默认编码为UTF-8
	 * 
	 * @author LOUWEIFENG
	 * @param fileName
	 *            文件名称
	 * @param testString
	 *            测试字符串
	 * @param filePath
	 *            文件路径
	 */

	public static void writeTextFile(String filePath, String fileName,
			String testString) throws IOException {
		FileUtil.writeTextFile(filePath, fileName, testString, null);
	}

	/**
	 * 根据encoding编码向指定文件写入数据
	 * 
	 * @author LOUWEIFENG
	 * @param fileName
	 *            文件名称
	 * @param testString
	 *            测试字符串
	 * @param encoding
	 *            编码格式
	 */

	public static void writeTextFile(String filePath, String fileName,
			String testString, String encoding) throws IOException {
		BufferedWriter bw = null;
		try {
			if ("".equals(encoding) || encoding == null)
				encoding = "UTF-8";
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(filePath, fileName);
			if (!file.exists())
				file.createNewFile();
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), encoding));
			bw.write(testString);
			bw.flush();
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				throw e;
			}
		}
	}

	/**
	 * 将List<String>数据根据默认编码UTF-8向指定文件写入数据，LIST中的每个元素为一行
	 * 
	 * @author LOUWEIFENG
	 * @param fileName
	 *            文件名称
	 * @param textList
	 *            测试字符串集合
	 * @param encoding
	 *            编码格式
	 */

	public static void writeList2TextFile(String filePath, String fileName,
			List<String> textList) throws IOException {
		writeList2TextFile(filePath, fileName, textList, null);
	}

	/**
	 * 将List<String>数据根据encoding编码向指定文件写入数据，LIST中的每个元素为一行
	 * 
	 * @author LOUWEIFENG
	 * @param fileName
	 *            文件名称
	 * @param textList
	 * @param encoding
	 *            编码格式
	 */

	public static void writeList2TextFile(String filePath, String fileName,
			List<String> textList, String encoding) throws IOException {
		StringBuffer sb = new StringBuffer();
		if (textList != null && textList.size() != 0) {
			for (String tmpstr : textList) {
				sb.append(tmpstr);
				sb.append("\r\n");
			}
		}
		BufferedWriter bw = null;
		try {
			if ("".equals(encoding) || encoding == null)
				encoding = "UTF-8";
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(filePath, fileName);
			if (!file.exists())
				file.createNewFile();
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), encoding));
			bw.write(sb.toString());
			bw.flush();
		} catch (IOException ioe) {
			log.error(ioe);
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 创建单个文件夹。
	 * 
	 * @param dir
	 *            目录路径
	 * @param ignoreIfExitst
	 *            true 表示如果文件夹存在就不再创建了。false是重新创建。
	 */
	public static void createDir(String dir, boolean ignoreIfExitst)
			throws IOException {
		File file = new File(dir);
		if (ignoreIfExitst && file.exists()) {
			return;
		}
		if (file.mkdir() == false) {
			log.error("Cannot create the directory = " + dir);
		}
	}

	/**
	 * 创建多个文件夹
	 * 
	 * @param dir
	 *            目录路径
	 * @param ignoreIfExitst
	 *            true 表示如果文件夹存在就不再创建了。false是重新创建。
	 * @throws IOException
	 */
	public static void createDirs(String dir, boolean ignoreIfExitst)
			throws IOException {
		File file = new File(dir);
		if (ignoreIfExitst && file.exists()) {
			return;
		}
		if (file.mkdirs() == false) {
			log.error("Cannot create the directory = " + dir);
		}
	}

	/**
	 * 新建目录
	 * 
	 * @author LOUWEIFENG<br>
	 * @param folderPath
	 *            要创建的文件夹路径 String 如 c:/FAG
	 * @return boolean TRUE创建成功 FALSE 创建失败
	 */

	public static boolean newFolder(String folderPath) {
		boolean flag = false;

		String filePath = folderPath;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		if (!myFilePath.exists()) {
			flag = myFilePath.mkdir();
		}
		return flag;
	}

	/**
	 * 新建文件
	 * 
	 * @author LOUWEIFENG<br>
	 * @param filePathAndName
	 *            文件路径 String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            写入文件中的内容
	 * @return boolean
	 */

	public static boolean newFile(String filePathAndName, String fileContent)
			throws IOException {
		boolean flag = false;
		String filePath = filePathAndName;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		if (!myFilePath.exists()) {
			flag = myFilePath.createNewFile();
		}
		FileWriter resultFile = new FileWriter(myFilePath);
		PrintWriter myFile = new PrintWriter(resultFile);
		String strContent = fileContent;
		myFile.println(strContent);
		resultFile.close();
		myFile.flush();
		myFile.close();
		return flag;
	}

	/**
	 * 删除文件
	 * 
	 * @author LOUWEIFENG<br>
	 * @param filePathAndName
	 *            文件路径及名称
	 * @return boolean true 文件删除成功 false 文件删除失败
	 */

	public static boolean delFile(String filePathAndName) {
		boolean flag = false;
		String filePath = filePathAndName;
		filePath = filePath.toString();
		File myDelFile = new File(filePath);
		flag = myDelFile.delete();
		return flag;
	}

	/**
	 * 删除文件夹
	 * 
	 * @author LOUWEIFENG<br>
	 * @param folderPath
	 *            文件夹路径及名称
	 * @return boolean TRUE删除成功 FALSE删除失败
	 */

	public static boolean delFolder(String folderPath) {
		boolean flag = false;
		flag = delAllFile(folderPath); // 删除完里面所有内容
		File myFilePath = new File(folderPath);
		flag = flag ? myFilePath.delete() : false; // 删除空文件夹
		return flag;
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @author LOUWEIFENG
	 * @param path
	 *            文件夹路径
	 * @return boolean true 删除成功 false 删除失败
	 */

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				flag = temp.delete();
			}
			if (temp.isDirectory()) {
				flag = delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				flag = flag ? delFolder(path + "/" + tempList[i]) : false;// 再删除空文件夹
			}
		}
		return flag;
	}

	/**
	 * 复制单个文件
	 * 
	 * @author LOUWEIFENG
	 * @param oldPath
	 *            原文件路径
	 * @param newPath
	 *            复制后路径
	 * @return boolean true 复制成功 false 复制失败
	 */

	public static boolean copyFile(String oldPath, String newPath)
			throws IOException {
		boolean flag = false;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
			flag = true;
		} catch (IOException e) {
			log.error(e);
		}
		return flag;
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @author LOUWEIFENG
	 * @param oldPath
	 *            原文件路径
	 * @param newPath
	 *            复制后路径
	 */

	public static void copyFolder(String oldPath, String newPath) {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			try {
				for (int i = 0; i < file.length; i++) {
					if (oldPath.endsWith(File.separator)) {
						temp = new File(oldPath + file[i]);
					} else {
						temp = new File(oldPath + File.separator + file[i]);
					}
					if (temp.isFile()) {
						FileInputStream input = new FileInputStream(temp);
						FileOutputStream output = new FileOutputStream(newPath
								+ "/" + (temp.getName()).toString());
						byte[] b = new byte[1024 * 5];
						int len;
						while ((len = input.read(b)) != -1) {
							output.write(b, 0, len);
						}
						output.flush();
						output.close();
						input.close();
					}
					if (temp.isDirectory()) {// 如果是子文件夹
						copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @author LOUWEIFENG
	 * @param oldPath
	 *            原文件路径
	 * @param newPath
	 *            目标路径
	 */

	public static void moveFile(String oldPath, String newPath)
			throws IOException {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @author LOUWEIFENG<br>
	 * @param oldPath
	 *            原文件路径
	 * @param newPath
	 *            目标路径
	 */

	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * 将CSV格式的字符串写入文件
	 * 
	 * @author LOUWEIFENG
	 * @param pathName
	 * @param fileName
	 * @throws IOException
	 */

	public static void writeStringData2CSV(String pathName, String fileName,
			String csvString) throws IOException {
		writeTextFile(pathName, fileName, csvString);
	}

	/**
	 * 将符合CSV格式的字符串List<String> csvDataList存入文件，csvDataList的每个元素为一行
	 * 
	 * @author LOUWEIFENG
	 * @param pathName
	 * @param fileName
	 * @param csvDataList
	 * @throws IOException
	 */

	public static void writeListData2CSV(String pathName, String fileName,
			List<String> csvDataList) throws IOException {
		StringBuilder sb = new StringBuilder();
		if (csvDataList != null && csvDataList.size() != 0) {
			for (String tmpstr : csvDataList) {
				sb.append(tmpstr);
				sb.append("\r\n");
			}
			writeTextFile(pathName, fileName, sb.toString());
		}
	}

	/**
	 * 将符合CSV格式的字符串List<List<String>> csvDataList存入文件，csvDataList的每个元素为一行
	 * 
	 * @author LOUWEIFENG<br>
	 * @param pathName
	 * @param fileName
	 * @param csvDataList
	 * @throws IOException
	 */

	public static void writeListListData2CSV(String pathName, String fileName,
			List<List<String>> csvDataList) throws IOException {
		StringBuilder sb = new StringBuilder();
		if (csvDataList != null && csvDataList.size() != 0) {
			for (List<String> tmpList : csvDataList) {
				for (String tmpstr : tmpList) {
					sb.append(tmpstr);
					sb.append(",");
				}
				sb.append("\r\n");
			}
			writeTextFile(pathName, fileName, sb.toString());
		}
	}

	/**
	 * 将符合CSV格式的字符串List<String[]> csvDataList存入文件，csvDataList的每个元素为一行
	 * 
	 * @author LOUWEIFENG<br>
	 * @param pathName
	 * @param fileName
	 * @param csvDataList
	 * @throws IOException
	 */

	public static void writeListArrayData2CSV(String pathName, String fileName,
			List<String[]> csvDataList) throws IOException {
		StringBuilder sb = new StringBuilder();
		if (csvDataList != null && csvDataList.size() != 0) {
			for (String[] tmpArray : csvDataList) {
				for (String tmpstr : tmpArray) {
					sb.append(tmpstr);
					sb.append(",");
				}
				sb.append("\r\n");
			}
			writeTextFile(pathName, fileName, sb.toString());
		}
	}

	/**
	 * 将符合CSV格式的字符串String[][] csvDataArray存入文件
	 * 
	 * @author LOUWEIFENG<br>
	 * @param pathName
	 * @param fileName
	 * @param csvDataArray
	 * @throws IOException
	 */

	public static void writeArraysData2CSV(String pathName, String fileName,
			String[][] csvDataArray) throws IOException {
		StringBuilder sb = new StringBuilder();
		if (csvDataArray != null && csvDataArray.length != 0) {
			for (String[] tmpArray : csvDataArray) {
				for (String tmpstr : tmpArray) {
					sb.append(tmpstr);
					sb.append(",");
				}
				sb.append("\r\n");
			}
			writeTextFile(pathName, fileName, sb.toString());
		}
	}

	/**
	 * 读取符合CSV规范的文件，并将文件内容以List<String>形式返回
	 * 
	 * @author LOUWEIFENG<br>
	 * @param pathName
	 * @param fileName
	 * @return List<String>
	 * @throws IOException
	 */

	public static List<String> readCSVData2ListString(String pathName,
			String fileName) throws IOException {
		return readTextFile2List(pathName, fileName);
	}

	/**
	 * 读取符合CSV规范的文件，并将文件内容以String形式返回
	 * 
	 * @author LOUWEIFENG<br>
	 * @param pathName
	 * @param fileName
	 * @return String
	 * @throws IOException
	 */

	public static String readCSVData2String(String pathName, String fileName)
			throws IOException {
		return readTextFile(pathName, fileName);
	}

	public void zip(String inputFileName, String outputFileName)
			throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				outputFileName));
		zip(out, new File(inputFileName), "");
		System.out.println("zip done");
		out.close();
	}

	private void zip(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (System.getProperty("os.name").startsWith("Windows")) {
				out
						.putNextEntry(new org.apache.tools.zip.ZipEntry(base
								+ "\\"));
				base = base.length() == 0 ? "" : base + "\\";
			} else {
				out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
				base = base.length() == 0 ? "" : base + "/";
			}
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			//System.out.println(base);
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}

	public static void main(String[] args) {
		FileUtil m_zip = new FileUtil();
		try {
			m_zip.zip("C:\\workspace\\james", "d:\\2005.zip");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

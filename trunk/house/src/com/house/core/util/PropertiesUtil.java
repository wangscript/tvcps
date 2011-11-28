package com.house.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @Title: PropertiesUtil.java
 * @Package com.joyque.plugin.house365.util
 * @Description: TODO 对properties配置文件的操作
 * @Company 江苏集群信息产业股份有限公司
 * @author 娄伟峰
 * @date 2011-1-6 下午04:59:14
 * @version V1.0
 */
public class PropertiesUtil {
	/** 日志 */
	private static Logger log  = Logger.getLogger(PropertiesUtil.class);
	private static Properties properties = new Properties();
	
	/**
	 * 构造方法，获取配置文件流
	 * @param fileNameTemp  文件名称
	 */
	public PropertiesUtil(String[] fileNameTemp){
		String fileName = null;
		FileInputStream fis = null;
		try {
			if(fileNameTemp != null && fileNameTemp.length>0){
				for(int i=0;i<fileNameTemp.length;i++){
					fileName = fileNameTemp[i];
					fis = new FileInputStream(
							GlobalUtil.getAppClassPath() + fileName);
					properties.load(fis);
				}
			}
		} catch (IOException e) {
			log.error(e);
		}finally{
			try {
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Properties getProperties(String fileName){
		
		FileInputStream fis = null;
		try {
			properties = new Properties();
			fis = new FileInputStream(
					GlobalUtil.getAppClassPath() + fileName);
			properties.load(fis);
		} catch (IOException e) {
			log.error(e);
		}finally{
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return properties;
	}
	
	/**
	 * 获取某个属性
	 * @param key 配置文件中的key值
	 * @return string 返回配置文件key对应的值
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 获取所有属性，返回一个map,不常用 可以试试props.putAll(t)
	 * @return map 返回配置文件中数据
	 */
	public static Map<String,String> getAllProperty(String fileName){
		 Map<String,String> map=new HashMap<String,String>();
		 Enumeration<?> enu = properties.propertyNames();
		 while (enu.hasMoreElements()) {
			 String key = (String) enu.nextElement();
			 String value = properties.getProperty(key);
			 map.put(key, value);
		 }
		 return map;
	 }
	/**
	 * 在控制台上打印出所有属性，调试时用。
	 */
	public static void printProperties(String fileName) {
		getProperties(fileName).list(System.out);
	}

	/**
	 * 
	 * @method :writeProperties
	 * @description: 方法说明
	 * @param key 
	 * @param value
	 * @createTime:2011-1-11下午03:30:08
	 * @author:翟安旭
	 */
	public static void writeProperties(String fileName,String key, String value) {
		Properties properties = null;
		try {
			properties = getProperties(fileName);
			OutputStream fos = new FileOutputStream(fileName);
			properties.setProperty(key, value);
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			properties.store(fos, "『comments』Update key：" + key);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

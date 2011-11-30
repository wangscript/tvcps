/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.sys;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.j2ee.cms.biz.configmanager.service.impl.InitServiceImpl;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-9-6 下午08:07:21
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class ReadSystemXml {
	private static final Logger log = Logger.getLogger(ReadSystemXml.class);
	/**
	 * 创建树的方法
	 * 
	 * @param treeid
	 *            树的节点
	 * @return object 返回一个list的对象
	 */
	public static List<Object> getTreeList(String treeid,String xmlPath) {
		log.debug("获取树的操作");
		List<Object> list = new ArrayList<Object>();
		xmlPath = GlobalConfig.appRealPath+ xmlPath;
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new File(xmlPath));
			Element root = document.getRootElement();
			for (Iterator system = root.elementIterator(); system.hasNext();) {
				Element systemElement = (Element) system.next();
				if (systemElement != null) {
					Object[] obj = new Object[4];
					Element nodeId = systemElement.element("nodeId");
					Element nodeName = systemElement.element("nodeName");
					Element nodeUrl = systemElement.element("nodeUrl");
					Element nodeLeaf = systemElement.element("nodeLeaf");
					Element nodePid = systemElement.element("nodePid");
					if (nodePid.getText().equals(treeid)) {
						obj[0] = nodeId.getText();
						obj[1] = nodeName.getText();
						obj[2] = nodeUrl.getText();
						if (nodeLeaf.getText().equals("true")) {
							obj[3] = true;
						} else {
							obj[3] = false;
						}
						list.add(obj);
					}
				}
			}
		} catch (DocumentException e) {
			log.debug("文件里面没有内容");
		}
		return list;
	}
	
	
	/**
	 *递归获取所有的节点
	 * @param xmlPath xml文件路径
	 * @param str 返回的字符串
	 * @return String 所有节点的字符串
	 */
	public static String getTreeList(String xmlPath) {
		log.debug("获取树的操作");
		String str = "";
		List<Object> list = new ArrayList<Object>();
		xmlPath = GlobalConfig.appRealPath+ xmlPath;
	//	xmlPath = "systemSetting.xml";
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new File(xmlPath));
			Element root = document.getRootElement();
			for (Iterator system = root.elementIterator(); system.hasNext();) {
				Element systemElement = (Element) system.next();
				if (systemElement != null) {				 
					Element nodeId = systemElement.element("nodeId");		 
					str = str + "," + nodeId.getText();					
				}
			}
		} catch (DocumentException e) {
			log.debug("文件里面没有内容");
		}
		
		return str;	
	}
	
	public  static void main(String args[]){
		getTreeList("");
	}
}

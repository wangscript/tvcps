/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.setupmanager.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.baize.ccms.biz.setupmanager.dao.SetDB;
import com.baize.ccms.biz.setupmanager.domain.RegInfo;
import com.baize.ccms.biz.setupmanager.domain.SysInit;
import com.baize.ccms.biz.setupmanager.domain.SysParam;
import com.baize.common.core.util.StringUtil;

/**
 * <p>
 * 标题: 该类设置启动时候，解析和写入文件，以及实现注册码等业务逻辑代码.
 * </p>
 * <p>
 * 描述: —— 负责业务操作
 * </p>
 * <p>
 * 模块: CCMS后台数据启动模块
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * </p>
 * <p>
 * 网址：http://www.baizeweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-6 下午05:36:48
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class SetupBiz {
	/** 声明日志 . **/
	private static final Logger log = Logger.getLogger(SetupBiz.class);

	/**
	 * 根据用户的设置，更新后台hibernate相关的所有配置文件 .
	 * 
	 * @param path
	 * @return 成功返回true，失败返回false
	 */
	public String updateDBxml(String path) {
		String mess = "";
		SetDB setDB = new SetDB();
		SAXReader saxReader = new SAXReader();
		try {
			// 读取setup.xml文件
			InputStream in = new FileInputStream(path + File.separator +"setup"+File.separator +"setup.xml");
			Document document = saxReader.read(in);
			Element el = (Element) document.getRootElement().elementIterator(
					"sysParamset").next();
			Element e2 = (Element) document.getRootElement().elementIterator(
					"dataInitialize").next();

			// 根据用户写入的文件中来获取信息
			String dbType = el.elementText("databaseType").toLowerCase();
			String dbUser = e2.elementText("dataUserName");
			String dbPass = e2.elementText("dataUserPass");
			String serverIP = e2.elementText("serverIP");
			String dbName = e2.elementText("dataBaseName");
			String dbPort = e2.elementText("dataPort");
			String url = "";
			SetDB st = new SetDB();
			log.info("你使用的数据库是:"+dbType);
			// 根据各种不同的数据库类型来调用不同的创建数据的方法
			if (dbType.equals("mysql")) {
				url = "jdbc:mysql://" + serverIP + ":" + dbPort + "/" + dbName
						+ "?characterEncoding=utf8";
				if (!st.isDBexists(path)) {// 先判断数据库是否存在
					st.createDB(serverIP, dbPort, dbType, dbName, dbUser,
							dbPass, dbName); // 调用创建mysql数据库的方法
					mess = "数据初始化成功,请重新启动服务";
					log.info(mess);
				} else {
					mess = "已经使用你原来的数据，请重新启动服务";
					log.info(mess);
				}
				setDB.mysql(dbUser, dbPass, url, path); // 将文件中的内容跟新为mysql的数据类型
				
			} else if (dbType.equals("oracle")) {
				url = "jdbc:oracle:thin:@" + serverIP + ":" + dbPort + ":"
						+ dbName;
				setDB.oracle(dbUser, dbPass, url, path); // 将文件中的内容跟新为oracle的数据类型
				mess = "初始化成功,请重新启动服务器";
				log.info(mess);
			} else if (dbType.equals("db2")) {
				url = "jdbc:db2://" + serverIP + ":" + dbPort
						+ "/sample;DatabaseName=" + dbName
						+ ";charset=uft-8;SendStringParametersAsUnicode=false";
				if (!st.isDBexists(path)) {// 先判断数据库是否存在
					st.createDB(serverIP, dbPort, dbType, dbName, dbUser,
							dbPass, dbName); // 调用创建db2数据库的方法
					mess = "数据初始化成功,请重新启动服务器";
					log.info(mess);
				} else {
					mess = "已经使用你原来的数据，请重新启动服务器";
					log.info(mess);
				}
				setDB.DB2(dbUser, dbPass, url, path); // 将文件中的内容跟新为db2的数据类型
			} else if (dbType.equals("mssql")) {
				url = "jdbc:sqlserver://" + serverIP + ":" + dbPort
						+ ";DatabaseName=" + dbName
						+ ";charset=uft-8;SendStringParametersAsUnicode=false";
				if (!st.isDBexists(path)) {// 先判断数据库是否存在
					st.createDB(serverIP, dbPort, dbType, dbName, dbUser,
							dbPass, dbName); // 调用创建mssql数据库的方法
					mess = "数据初始化成功,请重新启动服务";
					log.info(mess);
				} else {
					mess = "已经使用你原来的数据，请重新启动服务器";
					log.info(mess);
				}
				setDB.sqlserver(dbUser, dbPass, url, path); // 将文件中的内容跟新为mssql的数据类型
			}
			return mess;
		} catch (DocumentException e) {
			log.error("setup.xml文件操作失败 "+e);
			return null;
		} catch (FileNotFoundException e) {
			log.error("setup.xml文件没有找到"+e);
			return null;
		} catch (IOException e) {
			log.error("setup.xml文件操作失败"+e);
			return null;
		} catch (SQLException e) {
			log.error("数据库操作失败"+e);
			return null;
		} catch (ClassNotFoundException e) {
			log.error("数据库操作失败"+e);
			return null;
		}

	}

	/**
	 * 获取初始化数据设置 .
	 * 
	 * @param path
	 *            路径
	 * @return 初始化数据信息
	 */
	public SysInit getSysInit(String path) {
		SysInit init = new SysInit();
		try {
			SAXReader read = new SAXReader();
			InputStream isread = new FileInputStream(path
					+ File.separator +"setup"+File.separator+"setup.xml");
			Document document = read.read(isread);
			Element element = document.getRootElement();
			Element el = (Element) element.elementIterator("dataInitialize")
					.next();
			init.setSiteName(el.elementText("siteName"));
			
			init.setDataBaseName(el.elementText("dataBaseName"));
			init.setServerIP(el.elementText("serverIP"));
			init.setDataPort(el.elementText("dataPort"));
			init.setDataUserName(el.elementText("dataUserName"));
			init.setDataUserPass(el.elementText("dataUserPass"));
			init.setCountId(el.elementText("countId"));
			log.info("获取初始化数据成功");
			return init;
		} catch (FileNotFoundException e) {
			log.error("setup.xml文件没有找到"+e);
			return null;
		} catch (DocumentException e) {
			log.error("setup.xml操作失败"+e);
			return null;
		}
	}

	/**
	 * 将数据初始化配置信息放入文件 .
	 * 
	 * @param path
	 *            文件路径
	 * @param init
	 *            初始化数据的信息
	 */

	public void setSysInit(final String path, SysInit init) {
		XMLWriter writer = null;
		try {
			InputStream isread = new FileInputStream(path
					+ File.separator +"setup"+File.separator +"setup.xml");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isread);
			document.selectSingleNode(
					"//systemSetting//dataInitialize//serverIP").setText(
					init.getServerIP());// 设置IP
			document.selectSingleNode(
					"//systemSetting//dataInitialize//dataPort").setText(
					init.getDataPort());// 端口
			document.selectSingleNode(
					"//systemSetting//dataInitialize//dataBaseName").setText(
					init.getDataBaseName()); // 数据库名字
			document.selectSingleNode(
					"//systemSetting//dataInitialize//dataUserName").setText(
					init.getDataUserName()); // 用户名
			document.selectSingleNode(
					"//systemSetting//dataInitialize//dataUserPass").setText(
					init.getDataUserPass()); // 密码
			document.selectSingleNode(
					"//systemSetting//dataInitialize//countId").setText(
					init.getCountId()); // 第几次，如果为1，说明数据库已经创建好，0为每创建

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			writer = new XMLWriter(new FileOutputStream(new File(path
					+ File.separator + "setup"+File.separator +"setup.xml")), format);
			writer.write(document);// 保存到文件
			log.info("数据保存成功");
		} catch (FileNotFoundException e) {
			log.error("setup.xml文件没有找到"+e);
		} catch (DocumentException e) {
			log.error("setup.xml文件操作失败"+e);
		} catch (IOException e) {
			log.error("setup.xml文件操作失败"+e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 获取参数 .
	 * 
	 * @param path
	 *            文件位置
	 * 
	 * @return 参数信息
	 */
	public SysParam getSysParam(final String path) {
		SysParam info = new SysParam();
		try {
			SAXReader read = new SAXReader();
			InputStream isread = new FileInputStream(path
					+ File.separator + "setup"+File.separator+"setup.xml");
			Document document = read.read(isread);
			Element el = (Element) document.getRootElement().elementIterator(
					"sysParamset").next();
			info.setLoadPath(path);
			info.setServerName(el.elementText("serverName"));
			info.setDatabaseType(el.elementText("databaseType"));
			info.setAppName(path.substring(path.lastIndexOf("/") + 1, path
					.length()));
			info.setPutType(el.elementText("putType"));
			Element el2 = (Element) document.getRootElement().elementIterator(
			"userInfo").next();
			info.setPwd(el2.elementText("userpwd"));
			log.info("参数获取成功");
			return info;
		} catch (FileNotFoundException e) {
			log.error("setup.xml文件不存在"+e);
			return null;
		} catch (DocumentException e) {
			log.error("setup.xml文件操作失败"+e);
			return null;
		}

	}

	/**
	 * 设置服务器系统参数 .
	 * 
	 * @param path
	 * 
	 * @return 设置是否成功
	 */
	public boolean setSysParam(final String path, SysParam info) {
		XMLWriter writer = null;
		try {
			InputStream isread = new FileInputStream(path
					+ File.separator +"setup"+File.separator +"setup.xml");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isread);
			Element el = document.getRootElement();
			Element el2 = (Element) el.elementIterator("userInfo").next();
			// 获取XML文件，并且查找userpwd，将他的值改为实体中的,用户修改了密码才去更该密码，否则不更新
			if (!info.getPwd().equals(el2.elementText("userpwd").trim())) {
				document.selectSingleNode("//systemSetting//userInfo//userpwd")
						.setText(StringUtil.encryptMD5(info.getPwd()));

			}
			document.selectSingleNode("//systemSetting//sysParamset//syspath")
					.setText(path); // 设置系统路径

			document.selectSingleNode(
					"//systemSetting//sysParamset//serverName").setText(
					info.getServerName()); // 设置服务器名字tomcat,weblogic,..

			document.selectSingleNode(
					"//systemSetting//sysParamset//databaseType").setText(
					info.getDatabaseType()); // 设置数据库类型oracle,mysql...

			document.selectSingleNode("//systemSetting//sysParamset//appName")
					.setText(
							path.substring(path.lastIndexOf("/") + 1, path
									.length())); // 设置应用程序名称
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			writer = new XMLWriter(new FileOutputStream(new File(path
					+ File.separator +"setup"+File.separator +"setup.xml")), format);
			writer.write(document); // 写入到文件
			log.info(" 设置服务器系统参数成功");
			return true;
		} catch (FileNotFoundException e) {
			log.error("setup.xml文件不存在"+e);
			return false;
		} catch (DocumentException e) {
			log.error("setup.xml文件操作失败"+e);
			return false;
		} catch (IOException e) {
			log.error("setup.xml文件操作失败"+e);
			return false;
		} finally {
			// 关闭流
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * ccms系统后台登陆验证 .
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param path
	 *            文件路径
	 * @return 返回验证信息
	 */
	public String getUserInfo(String userName, String password, String path) {
		String messInfo = "";
		try {
			InputStream is = new FileInputStream(path + File.separator+"setup"+File.separator+"setup.xml");
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			// 读取文件
			Element foo = (Element) root.elementIterator("userInfo").next();

			String uname = foo.elementText("userName");// 用户名
			String pwd = foo.elementText("userpwd");

			if (StringUtil.encryptMD5(userName).equals(uname)) {
				if (StringUtil.encryptMD5(password).equals(pwd)) {
					messInfo = "登陆成功";
				} else {
					messInfo = "密码输入有误";
				}
			} else {
				messInfo = "用户名输入有误";
			}
			log.info(messInfo);
			return messInfo;
		} catch (IOException e) {
			log.error("setup.xml读取文件失败"+e);
			return null;
		} catch (DocumentException e) {
			log.error("setup.xml没有找到文件"+e);
			return null;
		}
	}

	/**
	 * 获取注册信息 .
	 * 
	 * @param form
	 * 
	 * @param siteId
	 * @return 返回注册信息 map
	 */
	public RegInfo getAllRegiterInfo(String path) {
		RegInfo ri = new RegInfo();
		String mac = getMACMD5(); // MAC.
		InputStream is = null;
		try {
			File file = new File(path + File.separator + "WEB-INF"+File.separator+"ccms.licence");
			if (!file.exists()) {
				log.info("ccms.licence文件不存在");
				return null;
			}
			is = new FileInputStream(path +  File.separator + "WEB-INF"+ File.separator+"ccms.licence");
		} catch (FileNotFoundException e) {
			System.out.println();
			log.error("没有找到文件ccms.licence"+e);
		}
		// 将文件的内容读取并且比较
		List<String> list = readToBuffer(is);
		if (list.size() != 13) {
			return null;
		}
		String str = "";
		if (list != null && list.size() >= 0) {
			for (int i = 0; i < list.size(); i++) {
				str += list.get(i);
			}
			String strSplit[] = new String[8];
			String licence = str;
			for (int i = 1; i < strSplit.length; i++) {
				String ss[] = licence.split(StringUtil.encryptMD5("#" + i));
				strSplit[i] = ss[0];
				licence = licence.replaceFirst(ss[0]
						+ StringUtil.encryptMD5("#" + i), "");
			}
			// 如果注册码不匹配就不继续了.
			if (strSplit[1].equals(StringUtil.encryptMD5(mac))) {
				ri.setRegCode(StringUtil.encryptMD5(strSplit[1]));
				ri.setMessInfo("注册码匹配成功");

				int j = 0;
				while (true) {
					if (StringUtil.encryptMD5(j + "").equals(strSplit[2])) {
						ri.setColumCount(j + "");
						break;
					}
					if (j > 10000) {
						ri=null;
						break;
					}
					j++;
				}

				int w = 0;
				while (true) {
					if (StringUtil.encryptMD5(w + "").equals(strSplit[3])) {
						ri.setSiteCount(w + "");
						break;
					}
					if (w > 1000) {
						ri=null;
						break;
					}
					w++;
				}
				int n = 0;
				while (true) {
					if (StringUtil.encryptMD5(n + "@").equals(strSplit[4])) {
						ri.setDateMax(n + "日");
						break;
					}
					if (StringUtil.encryptMD5(n + "&").equals(strSplit[4])) {
						ri.setDateMax(n + "月");
						break;
					}
					if (n > 200) {// 如果大于200，退出，防止死循环
						ri=null;
						break;
					}
					n++;
				}
				int y = 2000;
				while (true) {
					if (StringUtil.encryptMD5("" + y).equals(strSplit[5])) {
						ri.setYear(y + "");
						break;
					}
					if (y > 3000) {
						ri=null;
						break;
					}
					y++;
				}
				int m = 0;
				while (true) {
					if (StringUtil.encryptMD5("" + m).equals(strSplit[6])) {
						ri.setMonth(m + "");
						break;
					}
					if (m > 1000) {
						ri=null;
						break;
					}
					m++;
				}
				int d = 0;
				while (true) {
					if (StringUtil.encryptMD5(d + "").equals(strSplit[7])) {
						ri.setDay(d + "");
						break;
					}
					if (d > 1000) {
						ri=null;
						break;
					}
					d++;
				}
				ri.setVersion("2.0");
				System.out.println("注册码获取成功");
				return ri;
			} else {
				System.out.println("注册码获取失败");
				return null;
			}
		} else {
			System.out.println("注册没有获取任何信息");
			return null;
		}

	}

	/**
	 * 获取MAC地址并且加密 .
	 * 
	 * @return 返回一个加密的MAC
	 */
	public static String getMACMD5() {
		String macAddr = "";
		String os = getOSName();
		if (os.startsWith("windows")) {
			// 本地是windows
			macAddr = getWindowsMACAddress();
			return StringUtil.encryptMD5(macAddr);
		} else {
			// 本地是非windows系统 一般就是unix
			macAddr = getUnixMACAddress();
			return StringUtil.encryptMD5(macAddr);
		}
	}

	/**
	 * 获取当前操作系统名称.
	 * 
	 * @return 操作系统名称,例如:windows xp,linux 等.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * 获取unix网卡的mac地址.
	 * 
	 * @return 非windows的系统默认调用本方法获取.如果有特殊系统请继续扩充新的取mac地址方法.
	 */
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ifconfig eth0");
			// linux下的命令.一般取eth0作为本地主网卡,显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("hwaddr"); // 寻找标示字符串.
				if (index >= 0) {
					mac = line.substring(index + "hwaddr".length() + 1).trim(); // 取出mac地址并去除2边空格
					break;
				}
			}
		} catch (IOException e) {
			log.error("读取linuxMAC失败"+e);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取widnows网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getWindowsMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ipconfig /all"); //windows下的命令，
			// 显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("physical address"); // 寻找标示字符串

				// physical
				// address
				// ]
				if (index >= 0) { // 找到了
					index = line.indexOf(":"); // 寻找":"的位置
					if (index >= 0) {
						mac = line.substring(index + 1).trim(); // 取出mac地址并去除2边空格
					}
					break;
				}
			}
		} catch (IOException e) {
			log.error("读取XP MAC失败"+e);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 判断是否过期，用于比较.
	 * 
	 * @param year1
	 *            年
	 * @param month1
	 *            月
	 * @param day1
	 *            日
	 * @return TRUE 已经过期，FALSE，没过期
	 */
	public boolean isPass(int year1, int month1, int day1, int addDate,
			String dateText) {
		boolean flag = true;
		SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		if (dateText.equals("日")) {
			c.set(year1, month1 - 1, day1);// 设置当前日期
			c.add(Calendar.DAY_OF_MONTH, addDate);
			if (smdf.format(c.getTime()).compareTo(smdf.format(new Date())) < 0) {// 小于零
				flag = true;// 过期
			} else {// 没有过期
				flag = false;
			}

		} else if (dateText.equals("月")) {
			c.set(year1, month1 - 1, day1);// 设置当前日期
			c.add(Calendar.MONTH, addDate);
			if (smdf.format(c.getTime()).compareTo(smdf.format(new Date())) < 0) {// 小于零过期
				flag = true;// 过期
			} else {
				flag = false;// 没有过期
			}
		}
		return flag;

	}

	/**
	 * 读出一个文件 .
	 * 
	 * @param is
	 * @return list集合
	 * @throws UnsupportedEncodingException 
	 */
	public final List<String> readToBuffer(final InputStream is)   {
		BufferedReader reader = null;
		List<String> list = new ArrayList<String>();
		try {
			reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String line;
			line = reader.readLine();
			while (line != null) { // 如果 line 为空说明读完了
				if(!line.trim().equals("")){
					list.add(line.trim());
				}
				line = reader.readLine(); // 读取下一行
			}
			is.close();
		}catch(UnsupportedEncodingException e){
			log.error("转码失败"+e);
		} catch (IOException e) {
			log.error("没有找到文件"+e);
			return null;
		}finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.debug("关闭失败！！！");
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	/**
	 * 判断是否过期.
	 * @param r注册信息
	 * @return true过期，false没过期
	 */
	public boolean checkDate(RegInfo r){
		boolean flag= true;
		// 获取当前是日 还是月
		String riText = r.getDateMax().substring(
				r.getDateMax().length() - 1, r.getDateMax().length());
		// 判断当前是日还是月
		if (riText.equals("日")) {
			int regAfter = Integer.parseInt(r.getDateMax().substring(0,
					r.getDateMax().length() - 1));// 获取注册的有效期
			// ,由于里面存放的是月或日，所以只截取日
			// ，dateMax-1;
			boolean isPass = isPass(Integer.parseInt(r.getYear()),
					Integer.parseInt(r.getMonth()), Integer.parseInt(r
							.getDay()), regAfter, "日");
			if (!isPass) {// 如果是日，就判断是否过期，按日计算，调用方法
				// 没有过期，将网站数，和栏目数赋值
				
				flag = false;
			} else {
				flag = true;// 已经过期
			}
		} else if (riText.equals("月")) {
			int regAfter = Integer.parseInt(r.getDateMax().substring(0,
					r.getDateMax().length() - 1));// 获取注册的有效期，由于里面存放的是月或日，
			// 所以只截取0，dateMax-1;
			boolean isPass = isPass(Integer.parseInt(r.getYear()),
					Integer.parseInt(r.getMonth()), Integer.parseInt(r
							.getDay()), regAfter, "月");
			if (!isPass) {
				// 没有过期
				flag = false;
			} else {
				// 已经过期了
				flag = true;
			}
		}
		return flag;
	}
	/**
	 * 判断oracle数据库是否存在
	 * @param path 
	 * 文件路径
	 * @return 存在的信息
	 */
	public String isOracleExists(final String path){
		String mess="";
		String sql = "select count(*) from users";
		try {
			SysInit init = getSysInit(path);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url ="jdbc:oracle:thin:@" + init.getServerIP() + ":" + init.getDataPort() + ":"+init.getDataBaseName();
			Connection c = DriverManager.getConnection(url,init.getDataUserName(),init.getDataUserPass());
			PreparedStatement pstm = c.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				mess = rs.getString(1);
			}
		} catch (ClassNotFoundException e) {
			log.error("没有找到数据库");
		} catch (SQLException e) {
			mess="库不存在";
		}
		return mess;
	}

}

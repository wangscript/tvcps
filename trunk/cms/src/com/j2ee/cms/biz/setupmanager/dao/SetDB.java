/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.setupmanager.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

import com.j2ee.cms.biz.setupmanager.service.SetupBiz;

/**
 * <p>
 * 标题: —— 切换数据库 .
 * </p>
 * <p>
 * 描述: ——该类当中有四种数据库，根据各种不同的数据库修改相关配置文件
 * </p>
 * <p>
 * 模块: CPS数据库启动模块
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-9 上午11:39:14
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class SetDB {

	/** 声明日志 . **/
	private final Logger log = Logger.getLogger(SetDB.class);
	/** 数据的驱动 . **/
	private static String driver;
	/** 数据的方言 . **/
	private static String dialect;

	/**
	 * mysql数据库.
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param url
	 *            数据URL
	 * @param innerPath
	 *            配置文件路径
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void mysql(String userName, String password, String url,
			String innerPath) throws IOException, DocumentException {
		log.info("选择了mysql");
		dialect = "org.hibernate.dialect.MySQLDialect";
		driver = "com.mysql.jdbc.Driver";

		modifyDB(url, driver, userName, password, dialect, innerPath); //修改inner-config. xml中的数据

		modifyHibernate(innerPath, dialect); // 修改hibernate.cfg.xml文件中的数据

		modifyOuter(url, driver, userName, password, dialect, innerPath); // 修改outer-dbconf.xml文件的数据

	}

	/**
	 * sqlserver数据库.
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param url
	 *            数据URL
	 * @param innerPath
	 *            配置文件路径
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void sqlserver(String userName, String password, String url,
			String innerPath) throws IOException, DocumentException {
		log.info("选择了sqlserver");
		dialect = "org.hibernate.dialect.SQLServerDialect";
		driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		modifyDB(url, driver, userName, password, dialect, innerPath); //修改inner-config. xml中的数据

		modifyHibernate(innerPath, dialect); // 修改hibernate.cfg.xml文件中的数据

		modifyOuter(url, driver, userName, password, dialect, innerPath); // 修改outer-dbconf.xml文件的数据

	}

	/**
	 * db2数据库.
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param url
	 *            数据URL
	 * @param innerPath
	 *            配置文件路径
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void DB2(String userName, String password, String url,
			String innerPath) throws IOException, DocumentException {
		log.info("选择了DB2");
		dialect = "org.hibernate.dialect.DB2Dialect";
		driver = "jcom.ibm.db2.jdbc.app.DB2Driver";
		modifyDB(url, driver, userName, password, dialect, innerPath); //修改inner-config. xml中的数据

		modifyHibernate(innerPath, dialect); // 修改hibernate.cfg.xml文件中的数据

		modifyOuter(url, driver, userName, password, dialect, innerPath); // 修改outer-dbconf.xml文件的数据

	}

	/**
	 * oracle 数据库 .
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param url
	 *            数据URL
	 * @param innerPath
	 *            配置文件路径
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void oracle(String userName, String password, String url,
			String innerPath) throws IOException, DocumentException {
		log.info("选择了oracle");
		dialect = "org.hibernate.dialect.Oracle10gDialect";
		driver = "oracle.jdbc.driver.OracleDriver";
		modifyDB(url, driver, userName, password, dialect, innerPath); //修改inner-config. xml中的数据

		modifyHibernate(innerPath, dialect); // 修改hibernate.cfg.xml文件中的数据

		modifyOuter(url, driver, userName, password, dialect, innerPath); // 修改outer-dbconf.xml文件的数据

	}

	/**
	 * 修改inner-dbconf.xml配置文件中的数据 .
	 * 
	 * @param url
	 *            数据库URL
	 * @param driver
	 *            数据库驱动
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param dialect
	 *            方言
	 * @param innerPath
	 *            路径
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void modifyDB(String url, String driver, String userName,
			String password, String dialect, String innerPath)
			throws IOException, DocumentException {
		SAXReader saxReader = new SAXReader();
		XMLWriter writer = null;
		try {
			InputStream in = new FileInputStream(innerPath
					+ File.separator + "WEB-INF"+ File.separator +"classes"+ File.separator +"inner-dbconf.xml");

			Document document = saxReader.read(in);
			document.selectSingleNode(
					"//something-else-entirely/proxool/driver-url")
					.setText(url);
			document.selectSingleNode(
					"//something-else-entirely/proxool/driver-class").setText(
					driver);
			List<DefaultElement> nodes = document
					.selectNodes("//something-else-entirely/proxool"
							+ "/driver-properties/property");

			for (DefaultElement node : nodes) {
				List<Attribute> attributes = node.attributes();
				boolean isUser = false;
				for (Attribute attr : attributes) {
					if ("name".equals(attr.getName())) {
						if (attr.getValue().equals("user")) {
							isUser = true;
						}
					}
					if ("value".equals(attr.getName())) {
						if (isUser) {
							attr.setValue(userName);
						} else {
							attr.setValue(password);
						}
					}
				}
			}

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			writer = new XMLWriter(new FileOutputStream(new File(innerPath
					+ File.separator + "WEB-INF"+File.separator + "classes"+File.separator + "inner-dbconf.xml")), format);
			writer.write(document);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					log.debug("流关闭失败");
				}
			}
		}
	}

	/**
	 * 修改hibernate.cfg.xml文件方言 .
	 * 
	 * @param path
	 *            文件路径
	 * @param dialect
	 *            要修改的方言
	 * @throws DocumentException
	 * @throws IOException
	 */

	public void modifyHibernate(String path, String dialect)
			throws DocumentException, IOException {
		SAXReader saxReader = new SAXReader();
		XMLWriter writer = null;
		try {

			// 读取hibernate.cfg.xml文件 .
			InputStream in = new FileInputStream(path
					+ File.separator + "WEB-INF"+File.separator + "classes"+File.separator + "hibernate.cfg.xml");

			Document document = saxReader.read(in);
			// 获取所有property,遍历循环，修改数据方言
			List<DefaultElement> nodes = document
					.selectNodes("//hibernate-configuration/session-factory"
							+ "/property");
			for (DefaultElement node : nodes) {// 遍历节点
				List<Attribute> attributes = node.attributes();
				for (Attribute attr : attributes) { // 遍历属性
					if ("name".equals(attr.getName())) {
						if (attr.getValue().equals("hibernate.dialect")) {
							node.setText(dialect);
						}
					}
				}
			}
			// 保存修改的文件
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			writer = new XMLWriter(new FileOutputStream(path + File.separator + "WEB-INF"+File.separator + "classes"+File.separator + "hibernate.cfg.xml"));
			writer.write(document);
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
	 * 修改outer-dbconf.xml文件中的数据 .
	 * 
	 * @param url
	 *            数据库URL
	 * @param driver
	 *            数据库驱动
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param dialect
	 *            方言
	 * @param innerPath
	 *            路径
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void modifyOuter(String url, String driver, String userName,
			String password, String dialect, String innerPath)
			throws IOException, DocumentException {
		SAXReader saxReader = new SAXReader();
		XMLWriter writer = null;
		try {
			// 读取outer-dbconf.xml文件 .
			InputStream in = new FileInputStream(innerPath
					+ File.separator + "WEB-INF"+File.separator + "classes"+File.separator + "outer-dbconf.xml");

			Document document = saxReader.read(in);
			document.selectSingleNode(
					"//something-else-entirely/proxool/driver-url")
					.setText(url); // 修改里面的属性
			document.selectSingleNode(
					"//something-else-entirely/proxool/driver-class").setText(
					driver);
			// 获取 所有property标签，遍历循环，查找用户名，并且修改它的值
			List<DefaultElement> nodes = document
					.selectNodes("//something-else-entirely/proxool"
							+ "/driver-properties/property");

			for (DefaultElement node : nodes) {
				List<Attribute> attributes = node.attributes();
				boolean isUser = false;
				for (Attribute attr : attributes) {
					if ("name".equals(attr.getName())) {
						if (attr.getValue().equals("user")) {
							isUser = true;
						}
					}
					if ("value".equals(attr.getName())) {
						if (isUser) {
							attr.setValue(userName);
						} else {
							attr.setValue(password);
						}
					}

				}
			}
			// 将修改的内容重新保存到文件
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			writer = new XMLWriter(new FileOutputStream(new File(innerPath
					+ File.separator + "WEB-INF"+File.separator + "classes"+File.separator + "outer-dbconf.xml")), format);
			writer.write(document);
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
	 * 判断数据库是否存在
	 * 
	 * @param path
	 *            路径
	 * @return true存在 false不存在
	 */
	public boolean isDBexists(String path) {
		System.out.println(path);
		Connection conn = null;
		try {
			Map<String, String> map = getDBInfo(path + File.separator + "setup"+File.separator + "setup.xml");
			System.out.println(map.get("driver"));
			System.out.println(map.get("url"));
			System.out.println(map.get("username"));
			System.out.println(map.get("password"));
			
			Class.forName(map.get("driver"));
			conn = DriverManager.getConnection(map.get("url"), map
					.get("username"), map.get("password"));
			return true;
		} catch (ClassNotFoundException e) {
			log.info("数据库驱动不存在");
			return false;
		} catch (SQLException e) {
			log.info("数据库不存在");
			return false;
		} finally {
			// 关闭连接
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	}
	
	public static void main(String[] args) {
		SetDB se = new SetDB();
		System.out.println(se.isDBexists("E:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/tvcp1"));
	}

	/**
	 * 初始化数据,要创建数据库，所以要先获取连接，这个链接从原来数据库中取得，然后创建
	 * ，例如：mysql中只要安装就会有test库，sqlserver2005就有master等 .
	 * 
	 * @param address
	 *            数据库连接地址
	 * @param port
	 *            端口
	 * @param type
	 *            数据库类型
	 * @param dbName
	 *            数据库名字
	 * @param userName
	 *            数据库用户名
	 * @param pass
	 *            数据库用密码
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void createDB(String address, String port, String type,
			String dbName, String userName, String pass,String sid) throws SQLException,
			ClassNotFoundException {
		log.info("开始创建数据库");
		String oldurl = "";
		String sql = "";
		// 如果数据类型是mysql，到information_schema获取连接.
		if (type.equals("mysql")) {
			driver = "com.mysql.jdbc.Driver";
			oldurl = "jdbc:mysql://" + address + ":" + port
					+ "/information_schema?" + "characterEncoding=utf8";
			sql = "create database " + dbName + " default charset utf8";
			// 如果是msslq 就到master中获取连接,下面也一样，省略 .
		} else if (type.equals("mssql")) {
			driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			oldurl = "jdbc:sqlserver://" + address + ":" + port
					+ ";DatabaseName=master;charset=uft-8;";
			sql = "create database " + dbName;
		} else if (type.equals("oracle")) {
			driver = "oracle.jdbc.driver.OracleDriver";
			oldurl = "jdbc:oracle:thin:@" + address + ":" + port + ":"+sid;
			sql = "create database " + dbName;
		} else if (type.equals("db2")) {
			driver = "jcom.ibm.db2.jdbc.app.DB2Driver";
			oldurl = "jdbc:db2://" + address + ":" + port
					+ "/sample;DatabaseName=SYSIBM;"
					+ "charset=uft-8;SendStringParametersAsUnicode=false";
			sql = "create database " + dbName;
		}

		Connection conn = null;
		try {
			// 创建数据库语句
			Class.forName(driver);
			conn = DriverManager.getConnection(oldurl, userName, pass); // 获取一个连接

			Statement smt = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			if (conn != null) {
				smt.executeUpdate(sql); // 执行语句
			}

			log.info("数据库创建成功");
		} finally {
			// 关闭连接
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
	}

	/**
	 * 执行语句
	 * 
	 * @return 是否执行成功
	 */
	public boolean executeSql(String path) {
		log.info("开始初始化语句");
		Connection conn = null;
		try {
			Map<String, String> map = getDBInfo(path + File.separator + "setup"+File.separator + "setup.xml");
			Class.forName(map.get("driver"));
			conn = DriverManager.getConnection(map.get("url"), map
					.get("username"), map.get("password"));
			Statement smt = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			SetupBiz s = new SetupBiz();
			File file = new File(path + File.separator + "setup"+File.separator + "init.txt");
			if (!file.exists()) {
				log.info("init.txt文件不存在");
				return false;
			}
			InputStream in = new FileInputStream(path + File.separator + "setup"+File.separator + "init.txt");
			List<String> list = s.readToBuffer(in);
			if (conn != null) {
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						smt.executeUpdate(list.get(i).toString().trim());
					}
				} else {
					return false;
				}
			}
			log.info("初始化语句成功");
			return true;
		} catch (ClassNotFoundException e) {
			log.error("init.txt不存在");
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("init.txt语句执行失败");
			return false;
		} catch (FileNotFoundException e) {
			log.error("init.txt语句执行失败");
			return false;
		} finally {
			// 关闭连接
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	/***
	 * 检查数据表是否存在
	 * 
	 * @param path
	 *            文件路径
	 * @return 是否存在
	 */
	public boolean checkTableExists(String path) {
		Connection conn = null;
		try {
			Map<String, String> map = getDBInfo(path + File.separator + "setup"+File.separator + "setup.xml");
			Class.forName(map.get("driver"));
			conn = DriverManager.getConnection(map.get("url"), map
					.get("username"), map.get("password"));
			// 判断数据库表是否存在
			ResultSet rs = conn.getMetaData().getTables(null, null, "users",
					null);
			if (rs.next()) {
				log.info("表存在");
				return true;
			} else {
				log.info("表不存在");
				return false;
			}
		} catch (ClassNotFoundException e) {
			log.info("表不存在");
			return false;
		} catch (SQLException e) {
			log.info("表不存在");
			return false;
		} finally {
			// 关闭连接
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}

	}

	/**
	 * 判断表中是否有数据
	 * 
	 * @param path
	 *            路径
	 * @return 返回是否有数据
	 */
	public boolean checkTableContent(String path) {
		Connection conn = null;
		try {
			Map<String, String> map = getDBInfo(path + File.separator + "setup"+File.separator + "setup.xml");
			Class.forName(map.get("driver"));
			conn = DriverManager.getConnection(map.get("url"), map
					.get("username"), map.get("password"));
			// 判断数据库表中是否有数据
			String sql = "select * from users";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				log.info("表中有数据");
				return true;
			} else {
				log.info("表中没有数据");
				return false;
			}
		} catch (ClassNotFoundException e) {
			log.error("没有找到数据库"+e);
			return false;
		} catch (SQLException e) {
			log.error("数据库操作失败"+e);
			return false;
		} finally {
			// 关闭连接
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 读取XML文件用于获取数据操作所需要的，driver,url,username,pass
	 * 
	 * @param path
	 *            文件路径
	 * @return 返回driver,url,username,pass
	 */
	public Map<String, String> getDBInfo(String path) {
		Map<String, String> map = new HashMap<String, String>();
		InputStream in;
		try {
			in = new FileInputStream(path);
			SAXReader read = new SAXReader();
			Document document = read.read(in);
			Element el = (Element) document.getRootElement().elementIterator(
					"sysParamset").next();
			Element e2 = (Element) document.getRootElement().elementIterator(
					"dataInitialize").next();
			String dbType = el.elementText("databaseType").toLowerCase();
			String dbUser = e2.elementText("dataUserName");
			String dbPass = e2.elementText("dataUserPass");
			String serverIP = e2.elementText("serverIP");
			String dbName = e2.elementText("dataBaseName");
			String dbPort = e2.elementText("dataPort");
			String url = "";
			String driver1 = "";
			if (dbType.equals("mysql")) {
				url = "jdbc:mysql://" + serverIP + ":" + dbPort + "/" + dbName
						+ "?characterEncoding=utf8";
				driver1 = "com.mysql.jdbc.Driver";
			} else if (dbType.equals("oracle")) {
				url = "jdbc:oracle:thin:@" + serverIP + ":" + dbPort + ":"
						+ dbName;
				driver1 = "oracle.jdbc.driver.OracleDriver";
			} else if (dbType.equals("db2")) {
				url = "jdbc:db2://" + serverIP + ":" + dbPort
						+ "/sample;DatabaseName=" + dbName
						+ ";charset=uft-8;SendStringParametersAsUnicode=false";
				driver1 = "jcom.ibm.db2.jdbc.app.DB2Driver";
			} else if (dbType.equals("mssql")) {
				driver1 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				url = "jdbc:sqlserver://" + serverIP + ":" + dbPort
						+ ";DatabaseName=" + dbName
						+ ";charset=uft-8;SendStringParametersAsUnicode=false";

			}
			map.put("url", url);
			map.put("driver", driver1);
			map.put("username", dbUser);
			map.put("password", dbPass);
			return map;
		} catch (FileNotFoundException e) {
			log.error("文件没找到"+e);
			return null;
		} catch (DocumentException e) {
			log.error("文件读取失败"+e);
			return null;
		}
	}
}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.junit.Before;
import org.junit.Test;

/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 杨信
 * @version 1.0
 * @since 2009-2-19 上午01:08:09
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SetDB {
	
	private String url;
	
	private String driver;
	
	private String userName;
	
	private String password;
	
	@Before
	public void setUp() {
		
	}
	
	/**
	 * 本机mysql数据库
	 */
	@Test
	public void mysql() {
		String dialect = "org.hibernate.dialect.MySQLDialect";
		url = "jdbc:mysql://127.0.0.1:3306/ccms?characterEncoding=utf8";
		driver = "com.mysql.jdbc.Driver";
		userName = "root";
		password = "q12we3";
		modifyDB(url, driver, userName, password);
	}
	
	/**
	 * 公司数据库
	 */
	@Test
	public void company() {
		String dialect = "org.hibernate.dialect.SQLServerDialect";
		url = "jdbc:sqlserver://192.168.1.100:1433;DatabaseName=ccms;charset=uft-8;SendStringParametersAsUnicode=false";
		driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		userName = "sa";
		password = "sa";
		modifyDB(url, driver, userName, password);
	}
	
	/**
	 * 本机sqlserver2005
	 */
	@Test
	public void sqlserver() {
		String dialect = "org.hibernate.dialect.SQLServerDialect";
		url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=ccms;charset=uft-8;SendStringParametersAsUnicode=false";
		driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		userName = "sa";
		password = "q12we3";
		modifyDB(url, driver, userName, password);
	}
	
	@SuppressWarnings("unchecked")
	public void modifyDB(String url, String driver, String userName, String password) {
		SAXReader saxReader = new SAXReader();
		XMLWriter writer = null;
		try {
			InputStream in = SetDB.class.getClassLoader().getResourceAsStream("proxoolconf.xml");
			Document document = saxReader.read(in);
			
			document.selectSingleNode("//something-else-entirely/proxool/driver-url").setText(url);
			document.selectSingleNode("//something-else-entirely/proxool/driver-class").setText(driver);
			List<DefaultElement> nodes = document.selectNodes("//something-else-entirely/proxool/driver-properties/property");
			
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
			writer = new XMLWriter(new FileWriter(new File("src/proxoolconf.xml")), format);
			writer.write(document);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

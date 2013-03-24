/**
 * project：通用内容管理系统
 * Company:   
 */
package com.j2ee.cms.sys;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

import com.j2ee.cms.biz.articlemanager.service.impl.ArticleServiceImpl;
import com.j2ee.cms.biz.setupmanager.dao.SetDB;
import com.j2ee.cms.common.core.domain.RegInfo;
import com.j2ee.cms.biz.setupmanager.domain.SysParam;
import com.j2ee.cms.biz.setupmanager.service.SetupBiz;
import com.j2ee.cms.common.core.exception.StartException;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.JdbcUtil;
import com.j2ee.cms.common.core.util.RSAHelper;
import com.j2ee.cms.common.core.util.StringUtil;

/**
 * <p>
 * 标题: 应用装载器
 * </p>
 * <p>
 * 描述: 放置一些应用启动期间需要执行的一些任务
 * </p>
 * <p>
 * 模块: 通用平台
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * 
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since Feb 12, 2009 8:22:33 PM
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class ApplicationLoader implements Start {
	private final Logger log = Logger.getLogger(ApplicationLoader.class);

	/**
	 * @param event
	 *            SererletContextEvent事件
	 */
	public void initAppRealPath(final ServletContextEvent event) {
		// 初始化全局配置
		GlobalConfig.appRealPath = event.getServletContext().getRealPath("")
				.replaceAll("\\\\","/");
	}

	public boolean createTable(final ServletContextEvent event) {
		String path = event.getServletContext().getRealPath("");
		SetDB s = new SetDB();
		SetupBiz sb = new SetupBiz();
		log.debug("开始检查数据表是否存在...");
		SysParam sparam = sb.getSysParam(path);
		if (sparam.getDatabaseType().toLowerCase().equals("oracle")) {//如果是oracle
			log.debug("oracle");
			String m = sb.isOracleExists(path);//判断数据库是否存在
			if(m.equals("库不存在")){
				log.debug("数据表不存在，开始创建...");
				Configuration cfg = new Configuration().configure();
				SchemaExport schemaExport = new SchemaExport(cfg);
				schemaExport.create(false, true);
				log.debug("创建表成功,开始初始化语句...");
				if (s.executeSql(path)) {
					log.debug("初始化数据成功...");
					return true;
				} else {
					log.debug("初始化数据失败，请检查语句是否正确");
					return false;
				}
			}else if(m.equals("0")){
				log.debug("表中没数据开始插入...");
				if (s.executeSql(path)) {
					log.debug("初始化数据成功...");
					return true;
				} else {
					log.debug("初始化数据失败，请检查语句是否正确");
					return false;
				}
			}else{
				log.debug("表中已经有数据，开始启动服务...");
				return true;
			}
		} else {
			if (!s.checkTableExists(path)) {// 如果表不存在就创建表
				log.debug("数据表不存在，开始创建...");
				Configuration cfg = new Configuration().configure();
				SchemaExport schemaExport = new SchemaExport(cfg);
				schemaExport.create(false, true);
				// 创建表后，插入数据
				log.debug("创建表成功,开始初始化语句...");
				if (s.executeSql(path)) {
					log.debug("初始化数据成功...");
					return true;
				} else {
					log.debug("初始化数据失败，请检查语句是否正确");
					return false;
				}
			} else { // 表存在，就判断表中是否有数据
				log.info("表已经存在，检查数据库中是否有数据...");
				if (!s.checkTableContent(path)) {// 如果表中没数据，开始插入
					log.debug("表中没有数据，开始插入数据...");
					if (s.executeSql(path)) {
						log.debug("插入数据成功...");
						return true;
					} else {
						log.debug("插入数据失败，请检查语句是否正确");
						return false;
					}
				} else {// 表中有数据了，直接启动服务
					log.debug("表中已经有数据，开始启动服务...");
					return true;
				}
			}
		}

	}

	/**
	 * 加载是验证是否注册，并且是否过期
	 * 
	 * @param event
	 */
	public boolean checkDate(final ServletContextEvent event) {
		System.out.println("检验是否有注册码...");
		boolean flag = true;// 过期
		SetupBiz sb = new SetupBiz();
		String realPath = event.getServletContext().getRealPath("");
		// 获取注册信息
		RegInfo regInfo = RSAHelper.readFile(realPath);	
		// 判断是否获取了注册码
		if (regInfo != null && regInfo.getRegCode() != null && RSAHelper.decodeString(regInfo.getRegCode())) {
			log.debug("注册码存在，检测是否在有效期内");
			// 获取日期实例
			if (regInfo.getDateMax() == null) // 如果期限为空直接返回
				return true;		
			if (RSAHelper.checkDate(regInfo))  {
				log.debug("有效期内");
				GlobalConfig.maxSite = regInfo.getSiteCount();
				GlobalConfig.maxColumn = regInfo.getColumnCount();
				flag = false;
			} else {
				// 已经过期了
				System.out.println("已经过期");
				flag = true;
			}
		} else {// 注册码不正确，程序直接退出
			System.out.println("注册码不正确，程序将退出");
			//System.exit(0);
			flag = false;
		}
		return flag;
	}

	/**
	 * 设置环境变量
	 */
	public void setEnv() {
		System.setProperty("webapp.root", GlobalConfig.appRealPath);

	}

	/**
	 * 初始化网站信息
	 */
	public void initSiteInfo() {
		try {
			Connection conn = JdbcUtil.getConnection();

			boolean isTypesExist = JdbcUtil.isTableExist(conn,
					"article_formats");
			boolean isFieldsExist = false;
			if (isTypesExist) {
				isFieldsExist = JdbcUtil.isColumnExist(conn, "article_formats",
						"fields");
			}

			if (isTypesExist && isFieldsExist) {
				String sql = "SELECT id FROM sites WHERE pid IS NULL";
				PreparedStatement preStmt = conn.prepareStatement(sql);
				ResultSet rs = preStmt.executeQuery();
				while (rs.next()) {
					GlobalConfig.mainSiteId = rs.getString("id");
				}
			}
			JdbcUtil.close(conn);
		} catch (Exception e) {
			throw new StartException("init article fields error.", e);
		}
	}

	/**
	 * 初始化日志文件配置
	 */
	public void initLogConfig() {
		DOMConfigurator.configure(FileUtil.getFileURL("logWriter.xml"));
	}

	/**
	 * 初始化格式对应的字段
	 * 
	 * @return
	 */
	public void initFormatFields() {

		try {
			Connection conn = JdbcUtil.getConnection();

			boolean isTypesExist = JdbcUtil.isTableExist(conn,
					"article_formats");
			boolean isFieldsExist = false;
			if (isTypesExist) {
				isFieldsExist = JdbcUtil.isColumnExist(conn, "article_formats",
						"fields");
			}

			if (isTypesExist && isFieldsExist) {
				String sql = "SELECT id, fields FROM article_formats";
				PreparedStatement preStmt = conn.prepareStatement(sql);
				ResultSet rs = preStmt.executeQuery();
				while (rs.next()) {
					ArticleServiceImpl.formatFields.put(rs.getString("id"), rs
							.getString("fields"));
				}
			}
			JdbcUtil.close(conn);
		} catch (Exception e) {
			throw new StartException("init article fields error.", e);
		}
	}

	public void initPhysicalAddress() {
		// GlobalConfig.physicalAddress = ;
	}

}

package com.j2ee.cms.biz.setupmanager.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.j2ee.cms.biz.setupmanager.domain.SysInit;
import com.j2ee.cms.biz.setupmanager.service.SetupBiz;
import com.j2ee.cms.common.core.util.FileUtil;

/**
 * <p>
 * 标题: 用servlet控制转向
 * </p>
 * <p>
 * 描述: —— 负责业务控制
 * </p>
 * <p>
 * 模块: CPS启动模块
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-6 下午05:36:48
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class CheckInit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void init(ServletConfig config) throws ServletException {}
	public void destroy() {}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * 当数据初始化页面第二次提交到这个servlet
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strDirPath = new File(request.getSession().getServletContext()
				.getRealPath(request.getRequestURI())).getParent();
		String path = strDirPath.substring(0, strDirPath.lastIndexOf("/")); // 截取路径
		String m = "";
		SetupBiz sbiz = new SetupBiz();
		SysInit sysInit = new SysInit();
		String serverIP = request.getParameter("cps_ip");
		String dataPort = request.getParameter("cps_port");
		String dataBaseName = request.getParameter("dataBaseName");
		String dataUserName = request.getParameter("dbUser");
		String dataUserPass = request.getParameter("dbPassword");
		sysInit.setServerIP(serverIP);
		sysInit.setDataPort(dataPort);
		sysInit.setDataBaseName(dataBaseName);
		sysInit.setDataUserName(dataUserName);
		sysInit.setDataUserPass(dataUserPass);
		sysInit.setCountId("0"); // 为1说明数据已经创建
		sbiz.setSysInit(path, sysInit); // 将数据初始化放入文件中，保存，
		// 以备下次读取

		String mess = sbiz.updateDBxml(path); // 跟新后台有关的配置文件
		if (mess != null) {// 更新成功
			sysInit.setCountId("1"); // 为1说明数据已经创建
			sbiz.setSysInit(path, sysInit); // 将数据初始化放入文件中，保存，
			FileUtil.copy(path + File.separator+"WEB-INF"+File.separator+"conf"+File.separator+"web.xml", path
					+ File.separator+"WEB-INF"+File.separator); // 将web.xml文件复制
			m = mess;
		} else {// 更新文件失败
			sysInit.setCountId("0"); // 为0数据数据库创建失败，下次还可以继续创建
			sbiz.setSysInit(path, sysInit); // 将数据初始化放入文件中，保存
			m = "数据初始化失败,请检查填写数据是否正确";
		}
		request.setAttribute("mes", m);
		request.getSession(true).setAttribute("sinit", sysInit);
		request.getRequestDispatcher("/setup/init_detail.jsp").forward(request,
				response);
	}

}

package com.j2ee.cms.biz.setupmanager.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.util.Log;

import com.j2ee.cms.biz.setupmanager.dao.SetDB;
import com.j2ee.cms.biz.setupmanager.domain.SysInit;
import com.j2ee.cms.biz.setupmanager.domain.SysParam;
import com.j2ee.cms.biz.setupmanager.service.SetupBiz;
import com.j2ee.cms.common.core.domain.RegInfo;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.RSAHelper;
import com.j2ee.cms.common.core.util.StringUtil;

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
 

public class SetupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String dealMethod = request.getParameter("dealMethod");
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String realPath = request.getSession().getServletContext().getRealPath(request.getRequestURI());
		System.out.println("realPath==========="+realPath);
		// 获取路径
		String strDirPath = new File(realPath).getParent();
		String path = strDirPath.substring(0, strDirPath.lastIndexOf(File.separator)); // 截取路径
		PrintWriter out = response.getWriter();
		SetupBiz sbiz = new SetupBiz();
		// 这里仿照框架中定义一个dealMethod用来进行业务划分
		if (dealMethod != null) {
			// 登陆
			if (dealMethod.equals("login")) {
				String userName = request.getParameter("user");
				String userPwd = request.getParameter("pass");
				Log.info("开始登陆");
				String mess = sbiz.getUserInfo(userName, userPwd, path); //验证登陆，
				if (mess != null && mess.equals("登陆成功")) {
					String str = RSAHelper.getMd5String();		
					request.getSession().setAttribute("mac",str);// 用于在页面上方显示注册码
					// 设置初始化参数
					SysParam param = sbiz.getSysParam(path);
					// 设置初始化数据
					SysInit sinit = sbiz.getSysInit(path);
					// 设置上传时候读取文件中的信息，
					RegInfo reinfo =  RSAHelper.readFile(path);	
					request.getSession().setAttribute("sysparam", param);// 设置初始化参数

					// 放入session

					request.getSession(true).setAttribute("regInfo", reinfo);
					request.getSession().setAttribute("sinit", sinit);// 设置初始化数据

					// 放入session
					request.getSession().setAttribute("uname", userName);
					// 登录成功，进入主页面
					request.getRequestDispatcher("/setup/setup.jsp").forward(
							request, response);
			/*		response.sendRedirect(request.getContextPath()
							+ "/setup/setup.jsp");*/
				} else {
					// 如果登录不正确，重新到登陆
					request.setAttribute("mess2", mess);
					request.getRequestDispatcher("/setup/login.jsp").forward(
							request, response);
				}

				// 参数设置
			} else if (dealMethod.equals("dataset")) {
				SysParam info = new SysParam();
				String loadPath = request.getParameter("loadRoading");
				String pwd = request.getParameter("password");
				String server = request.getParameter("serNameSelect");
				String dstype = request.getParameter("databaseTypeSelect");
				String appName = request.getParameter("appName");
				String putType = request.getParameter("putType");
				String checkCode = request.getParameter("checkCode");

				info.setLoadPath(loadPath = loadPath == null ? "" : loadPath);
				info.setPwd(pwd = pwd == null ? "" : pwd);
				info.setServerName(server = server == null ? "" : server);
				info.setDatabaseType(dstype = dstype == null ? "" : dstype);
				info.setAppName(appName = appName == null ? "" : appName);
				info.setCheckCode(checkCode = checkCode == null ? ""
						: checkCode);
				info.setPutType(putType = putType == null ? "" : putType);
				// 放入修改
				boolean flag = sbiz.setSysParam(path, info);
				if (flag) {
					request.setAttribute("mess", "设置成功");
				} else {
					request.setAttribute("mess", "设置失败");
				}
				request.getSession(true).setAttribute("sysparam", info);
				request.getRequestDispatcher("/setup/data_set.jsp").forward(
						request, response);

				// 数据初始化,1.首先判断是否有lience文件 2.如果有licence文件,在判断注册码是否正确
				// 3.注册码正确在判断，是否有效期内
			} else if (dealMethod.equals("sysinit")) {
				RegInfo reinfo =  RSAHelper.readFile(path);	
				if (reinfo == null) {// 文件不存在
					request.setAttribute("mes", "请先上传licence文件再初始化数据");
				} else {// 文件存在
					HttpSession session = request.getSession();
					if (session.getAttribute("regInfo") == null) {
						session.setAttribute("regInfo", new RegInfo());
					}
					if (session.getAttribute("sysparam") == null) {
						session.setAttribute("sysparam", new SysParam());
					}
					SysParam s = (SysParam) session.getAttribute("sysparam");
					RegInfo regInfo = (RegInfo) session.getAttribute("regInfo");			
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

					if (regInfo != null && regInfo.getRegCode() != null && RSAHelper.decodeString(regInfo.getRegCode())){// 注册码有效
						if (RSAHelper.checkDate(regInfo)) {// 在有效期内
							SetDB st = new SetDB();
							if (s.getDatabaseType() != null
									&& s.getDatabaseType().equals("oracle")) {// 如果是oracle

								if (!st.isDBexists(path)) {// Oracle数据库的SID不存在
									request.setAttribute("mesIsInit", "1");//设置为1，在页面表示
								} else {
									request.setAttribute("mes", checkInit(request, response));
								}
							} else {// 非oracle
								if (st.isDBexists(path)) {// 数据库存在
									request.setAttribute("mesIsInit",
											"你的数据库已经存在，是否继续");
									session.setAttribute("sinit", null);
									session.setAttribute("sinit", sbiz
											.getSysInit(path));

								} else {// 数据库不存在

									request.setAttribute("mes", checkInit(
											request, response));
								}
							}
						} else {//注册码过期
							request.setAttribute("mes", "注册码已经过期，要继续使用请联系厂商");
						}
					} else {//没有上传licence文件
						request.setAttribute("mes", "请将正确的注册码上传后，再初始化数据");
					}

				}
				request.getRequestDispatcher("/setup/init_detail.jsp").forward(
						request, response);

				// 退出，
			} else if (dealMethod.equals("logout")) {
				HttpSession session = request.getSession();
				session.setAttribute("mac", null);
				session.setAttribute("sysparam", null);
				session.setAttribute("regInfo", null);
				session.setAttribute("sinit", null);
				session.removeAttribute("sysparam");
				session.removeAttribute("mac");
				session.removeAttribute("regInfo");
				session.removeAttribute("sinit");
				session.invalidate();
				out.println("<script>window.top.location='"
						+ request.getContextPath()
						+ "/setup/login.jsp';</script>");
			}
		} else {
			// 如果dealMethod为空直接回到登陆页面
			response
					.sendRedirect(request.getContextPath() + "/setup/login.jsp");
		}
	}

	public String checkInit(HttpServletRequest request,
			HttpServletResponse response) {
		String m = "";
		String strDirPath = new File(request.getSession().getServletContext()
				.getRealPath(request.getRequestURI())).getParent();// 获取系统物理
		String path = strDirPath.substring(0, strDirPath.lastIndexOf(File.separator)); // 截取路径
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
		sysInit.setCountId("1"); // 为1说明数据已经创建
		sbiz.setSysInit(path, sysInit); // 将数据初始化放入文件中，保存，
		// 以备下次读取

		String mess = sbiz.updateDBxml(path); // 跟新后台有关的配置文件
		if (mess != null) {// 更新成功
			FileUtil.copy(path + File.separator + "WEB-INF"+File.separator+"conf"+File.separator+"web.xml", path
					+ File.separator+"WEB-INF"+File.separator); // 将web.xml文件复制
			m = mess;
		} else {// 更新文件失败
			sysInit.setCountId("0"); // 为0数据数据库创建失败，下次还可以继续创建
			sbiz.setSysInit(path, sysInit); // 将数据初始化放入文件中，保存
			m = "数据初始化失败,请检查填写数据是否正确";
		}
		request.getSession(true).setAttribute("sinit", sysInit);
		return m;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		doPost(request, response);
	}

}

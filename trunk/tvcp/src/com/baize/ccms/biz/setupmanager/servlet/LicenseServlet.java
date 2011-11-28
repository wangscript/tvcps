package com.baize.ccms.biz.setupmanager.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baize.ccms.biz.setupmanager.service.SetupBiz;
import com.baize.common.core.domain.RegInfo;
import com.baize.common.core.util.RSAHelper;
import com.baize.common.core.util.StringUtil;
import com.oreilly.servlet.MultipartRequest;
/**
 * <p>
 * 标题: 用servlet上传
 * </p>
 * <p>
 * 描述: —— 上传
 * </p>
 * <p>
 * 模块: CCMS启动模块
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
public class LicenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * 文件上传
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SetupBiz sbiz = new SetupBiz();
		// 声明将上传的文件放置到服务器的目录中
		// 声明限制上传的文件大小为 5 MB
		String strDirPath = new File(request.getSession().getServletContext()
				.getRealPath(request.getRequestURI())).getParent();
		String path = strDirPath.substring(0, strDirPath.lastIndexOf(File.separator));
		String saveDirectory = path + File.separator +"WEB-INF"+File.separator;
		int maxPostSize = 5 * 1024 * 1024;
		// 产生一个新的MultipartRequest 对象
		new MultipartRequest(request, saveDirectory,
				maxPostSize);
		// 声明上传文件名称
		// 设置上传时候读取文件中的信息，
		RegInfo regInfo = RSAHelper.readFile(path);	
		// 判断是否获取了注册码
		if (regInfo != null && regInfo.getRegCode() != null && RSAHelper.decodeString(regInfo.getRegCode())) {
			if (!RSAHelper.checkDate(regInfo)) {//在有效期内
				request.setAttribute("m", "你的注册码已经过期");
			} else {
				request.setAttribute("m", "上传成功");
			}
		} else {
			request.setAttribute("m", "上传失败,注册码不正确。");
		}
		request.getSession(true).setAttribute("regInfo",regInfo);
		request.getRequestDispatcher("/setup/upload_lic.jsp").forward(request,
				response);

	}

	public void destroy() {
		super.destroy();
	}
}

package com.house.core.action;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.house.core.entity.Pagination;
import com.house.core.service.GenericService;
import com.house.core.sys.GlobalConfig;
import com.opensymphony.xwork2.ActionSupport;

public class GenericAction extends ActionSupport{
	
	/**
	 * 日志处理
	 */
	protected final Logger log = Logger.getLogger(getClass()); 
	
	/**
	 * 登录结果
	 */
	protected final String LOGIN = "login";
	
	/**
	 * 错误结果
	 */
	protected final String ERROR = "error";
	
	/**
	 * 分页对象
	 */
	protected Pagination pagination;
	
	/**
	 * 页面选择 的ID集合
	 */
	protected String strChecked;
	
	/**
	 * 系统共用服务类
	 */
	private GenericService genericService;
	/**
	 * 应用名
	 */
	private String appName = GlobalConfig.appName;
	/**
	 * 日期格式化
	 */
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	/**
	 * <p>方法说明：获取aplication对象</p> 
	 * <p>创建时间：2011-7-28下午02:33:06</p>
	 * <p>作者: 刘加东</p>
	 * @return ServletContext
	 */
	protected ServletContext getServletContext() {
        return ServletActionContext.getServletContext();
    }

	/**
	 * <p>方法说明：获取Request对象</p> 
	 * <p>创建时间：2011-7-28下午02:33:50</p>
	 * <p>作者: </p>
	 * @return HttpServletRequest
	 */
    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }
    
    /**
	 * jsp页面路径
	 */
	private String page;
	
	/**
	 * <p>方法说明：跳转指定的jsp页面</p> 
	 * <p>创建时间：2011-8-11下午01:01:51</p>
	 * <p>作者: 刘加东</p>
	 * @throws ServletException
	 * @throws IOException void
	 */
	public void forwardPage() throws ServletException, IOException{
		HttpServletResponse response = getResponse();
		HttpServletRequest request = getRequest();
		//将跳转路径中的 ‘#’ 替换为正常访问路径中的 ‘?’
		String url = page.replaceAll("#", "?");
		//将跳转路径中的 ‘%’ 替换为正常访问路径中的 ‘&’
		url = page.replaceAll("%", "&");
		request.getRequestDispatcher(url).forward(request, response);
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

    /**
     * <p>方法说明：获取Session对象</p> 
     * <p>创建时间：2011-7-28下午02:34:15</p>
     * <p>作者: </p>
     * @return HttpSession
     */
    protected HttpSession getHttpSession() {
        return ServletActionContext.getRequest().getSession();
    }

    /**
     * <p>方法说明：获取Response对象</p> 
     * <p>创建时间：2011-7-28下午02:34:46</p>
     * <p>作者: </p>
     * @return HttpServletResponse
     */
    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

	/**
	 * @return the pagination
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	/**
	 * @return the strChecked
	 */
	public String getStrChecked() {
		return strChecked;
	}

	/**
	 * @param strChecked the strChecked to set
	 */
	public void setStrChecked(String strChecked) {
		this.strChecked = strChecked;
	}

	/**
	 * @param genericService the genericService to set
	 */
	public void setGenericService(GenericService genericService) {
		this.genericService = genericService;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the simpleDateFormat
	 */
	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}

	/**
	 * @param simpleDateFormat the simpleDateFormat to set
	 */
	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
}

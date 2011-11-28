package com.house.core.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.house.core.entity.Pagination;
import com.house.core.sys.GlobalConfig;

/**
 * <p>
 * 标题: —— 分页标签类
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: 通用平台
 * </p>
 * <p>
 * 版权: Copyright (c) 2011 house
 * 
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-4-12 下午03:16:44
 *        <p>
 *        修订历史:娄伟峰
 *        修订时间:2011-4-22 下午15:22:00
 *        </p>
 */
public class PageTag extends BodyTagSupport {
	
	/**
	 * 分页对象
	 */
	private Pagination pagination;

	
	/**
	 * 访问的Action
	 */
	private String pageAction;
	
	/**
	 * 访问的form
	 */
	private String formId;

	public int doEndTag() throws JspException {
		pageAction = "/" + GlobalConfig.appName + this.getPageAction();
		JspWriter writter = pageContext.getOut();
		StringBuilder sb = new StringBuilder();
		long cpageInt = pagination.getCurrPage();	 
		//下一页
	    long prepageInt = cpageInt-1;
	    //上一页
        long lastpageInt = cpageInt+1;
		sb.append("<div class='pagination'>");
		//总页数
		long maxPage = pagination.getMaxPage();
		//每页行数
		int rowsPerPage = pagination.getRowsPerPage();	
		//总行数
		long maxRowCount = pagination.getMaxRowCount();
		/**
		 * 分页状态显示
		 */
		if (maxRowCount == 0) {
			sb.append("<span class='current'>共" + maxRowCount + "条记录 , 每页显示"
					+ rowsPerPage + "条, 共" + maxPage + "页  </span> ");
		} else {
			sb.append("<span class='current'>共" + maxRowCount + "条记录, 每页显示"
					+ rowsPerPage + "条, 第" + cpageInt + "页/共" + maxPage
					+ "页  </span> ");
		}
		if (maxRowCount == 1 || maxRowCount == 0) {
			sb.append("<span class='disabled'>首页 上一页 下一页 末页</span>");
		} else {
			if (cpageInt == 1) {
				sb.append("<span class='disabled'>首页 上一页 </span>");
			} else {
				sb.append("<a href='#' onclick='");
				sb.append("submitForm(\"" + formId + "\",\"1\",\"" + pageAction
						+ "\")");
				sb.append("'>首页</a> ");
				sb.append("<a href='#' onclick='");
				sb.append("submitForm(\"" + formId + "\",\""+prepageInt+"\",\"" + pageAction + "\")");
				sb.append("'>上一页</a> ");
			}
			if (cpageInt == maxPage) {
				sb.append("<span class='disabled'>下一页 末页</span>");
			} else {
				sb.append("<a href='#' onclick='");
				sb.append("submitForm(\"" + formId + "\",\""+lastpageInt+"\",\"" + pageAction
						+ "\")");
				sb.append("'>下一页</a> ");
				sb.append("<a href='#' onclick='");
				sb.append("submitForm(\"" + formId +"\",\""+maxPage+"\",\"" + pageAction
						+ "\")");
				sb.append("'>末页</a>");
			}
		}
		sb.append("<input id=\"currPage\" type=\"hidden\" name=\"pagination.currPage\"  value=\"").append(pagination.getCurrPage()).append("\"/>\n");
		sb.append("<input id=\"rowsPerPage\" type=\"hidden\" name=\"pagination.rowsPerPage\" value=\"").append(pagination.getRowsPerPage()).append("\"/>\n");
		sb.append("<input id=\"maxPage\" type=\"hidden\" name=\"pagination.maxPage\" value=\"").append(pagination.getMaxPage()).append("\"/>\n");
		sb.append("<input id=\"maxRowCount\" type=\"hidden\" name=\"pagination.maxRowCount\" value=\"").append(pagination.getMaxRowCount()).append("\"/>\n");
		sb.append("</div>");

		try {
			writter.print(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;
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
	 * @return the pageAction
	 */
	public String getPageAction() {
		return pageAction;
	}

	/**
	 * @param pageAction the pageAction to set
	 */
	public void setPageAction(String pageAction) {
		this.pageAction = pageAction;
	}

	/**
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}

}

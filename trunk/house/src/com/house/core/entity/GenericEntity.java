
package com.house.core.entity;
/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: —— 设计模块</p>
 * <p>版权: Copyright (c) 2011 
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-10-14 上午11:03:26
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public class GenericEntity {
	
	/**
	 * 当前页数
	 */
	public long currPage = 1;
	
	/** 
	 * 每页多少行 
	 */
	public int rowsPerPage = 10;

	//多少条开始显示
	public int rowscount;
	
	public int getRowscount() {
		return rowscount;
	}
	public void setRowscount(int rowscount) {
		this.rowscount = rowscount;
	}
	
	/**
	 * @return the currPage
	 */
	public long getCurrPage() {
		return currPage;
	}

	/**
	 * @param currPage the currPage to set
	 */
	public void setCurrPage(long currPage) {
		this.currPage = currPage;
	}

	/**
	 * @return the rowsPerPage
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * @param rowsPerPage the rowsPerPage to set
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}


}

/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.configmanager.domain;

import java.io.Serializable;
import java.util.Date;

import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;

/**
 * <p>标题: 文章</p>
 * <p>描述: 用于网站显示的基本元素</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 杨信
 * @version 1.0
 * @since 2009-3-10 下午01:44:55
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class FilterCategory implements Serializable {

	private static final long serialVersionUID = -6841515714963882374L;

		/** 唯一标识符 */
		private String id;

		/**类别名称*/
	    private String   categoryName;	 
	   public String getId() {
			return id;
		}
	
		public void setId(String id) {
			this.id = id;
		}
	
		public String getCategoryName() {
			return categoryName;
		}
	
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

	

}

/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.configmanager.domain;

import java.io.Serializable;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 包坤涛
 * @version 1.0
 * @since 2009-9-4 下午07:50:58
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ReneralSystemSetCategory  implements Serializable {
     
	
	    private static final long serialVersionUID = -5447854927218307208L;
	    /** 唯一标识符 */
	    private String id;
	   /**类别名称*/
        private  String categoryName;
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

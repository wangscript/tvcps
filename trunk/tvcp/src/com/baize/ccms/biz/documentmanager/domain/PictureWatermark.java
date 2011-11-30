/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.domain;

/**
 * <p>标题: 图片水印</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-9-1 下午02:27:54
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class PictureWatermark extends Watermark {

	private static final long serialVersionUID = 3381378693415485068L;
	
	/** 图片位置 */
	private String localPath;

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

}

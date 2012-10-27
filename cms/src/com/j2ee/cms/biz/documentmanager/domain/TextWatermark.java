/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.domain;

/**
 * <p>标题: 文字水印</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-9-1 下午02:28:10
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class TextWatermark extends Watermark {

	private static final long serialVersionUID = -1987374396844411413L;

	/** 字体大小 */
	private String fontSize;
	
	/** 字体颜色 */
	private String color;
	
	/** 字体 （宋体、楷体等）*/
	private String font;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
}

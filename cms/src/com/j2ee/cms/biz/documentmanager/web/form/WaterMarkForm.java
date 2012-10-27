/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.documentmanager.domain.PictureWatermark;
import com.j2ee.cms.biz.documentmanager.domain.TextWatermark;
import com.j2ee.cms.biz.documentmanager.domain.Watermark;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-2 上午08:45:34
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class WaterMarkForm extends GeneralForm {
	
	private static final long serialVersionUID = 5093636927029781384L;
	/**水印父类**/
	private Watermark watermark = new Watermark();
	/**水印文字**/
	private TextWatermark textwatermark = new TextWatermark();
	/**水印图片**/
	private PictureWatermark picwatermark = new PictureWatermark();
	
	/**水印选项**/
	private String selectOption;
	/**水印文字**/
	private String fontName;
	private String localPath;
	/**图片水印下拉列表**/
	private List<PictureWatermark> listPicPath=new ArrayList<PictureWatermark>();
	/**水印文字下拉列表**/
	private List<TextWatermark> listWaterFont=new ArrayList<TextWatermark>();
	
	
	
	
	public List getListPicPath() {
		return listPicPath;
	}
	public void setListPicPath(List listPicPath) {
		this.listPicPath = listPicPath;
	}

	public List getListWaterFont() {
		return listWaterFont;
	}
	public void setListWaterFont(List listWaterFont) {
		this.listWaterFont = listWaterFont;
	}
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		
	}
	public TextWatermark getTextwatermark() {
		return textwatermark;
	}
	public void setTextwatermark(TextWatermark textwatermark) {
		this.textwatermark = textwatermark;
	}
	public PictureWatermark getPicwatermark() {
		return picwatermark;
	}
	public void setPicwatermark(PictureWatermark picwatermark) {
		this.picwatermark = picwatermark;
	}
	public void setSelectOption(String selectOption) {
		this.selectOption = selectOption;
	}
	public String getSelectOption() {
		return selectOption;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public String getFontName() {
		return fontName;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setWatermark(Watermark watermark) {
		this.watermark = watermark;
	}
	public Watermark getWatermark() {
		return watermark;
	}

}

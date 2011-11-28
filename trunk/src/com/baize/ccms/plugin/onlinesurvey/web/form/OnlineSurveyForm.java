/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.plugin.onlinesurvey.web.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.plugin.onlinesurvey.domain.OnlineSurvey;
import com.baize.common.core.web.GeneralForm;

/**
 * <p>标题: 网上调查</p>
 * <p>描述: 网上调查，以便页面和方法中调用</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009南京百泽网络科技有限公司
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:35:25 
 * @history（历次修订内容、修订人、修订时间等） 
*/


public class OnlineSurveyForm extends GeneralForm {

	private static final long serialVersionUID = -5632827958636915339L;
	
	private OnlineSurvey onlineSurvey = new OnlineSurvey();
	//网站内容id
	private  String  updateID;
	//类型id
	private String id;
	//OnlineSurveyForm实体类id
	private  String   categoryId;
	private String idm;
	//用于存储实体对象
	private List<OnlineSurvey> list;
	//用于存储调查问题
	private   List<OnlineSurvey>  listOnlineSurvey;
	//调查结束时间
	private String endTime;
	 
	public OnlineSurvey getOnlineSurvey() {
		return onlineSurvey;
	}
	public void setOnlineSurvey(OnlineSurvey onlineSurvey) {
		this.onlineSurvey = onlineSurvey;
	}
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1){
		// TODO Auto-generated method stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getIdm() {
		return idm;
	}
	public String getUpdateID() {
		return updateID;
	}
	public void setUpdateID(String updateID) {
		this.updateID = updateID;
	}
	public void setIdm(String idm) {
		this.idm = idm;
	}
	/**
	 * @return the list
	 */
	/**
	 * @return the list
	 */
	public List<OnlineSurvey> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<OnlineSurvey> list) {
		this.list = list;
	}
	/**
	 * @return the listOnlineSurvey
	 */
	public List<OnlineSurvey> getListOnlineSurvey() {
		return listOnlineSurvey;
	}
	/**
	 * @param listOnlineSurvey the listOnlineSurvey to set
	 */
	public void setListOnlineSurvey(List<OnlineSurvey> listOnlineSurvey) {
		this.listOnlineSurvey = listOnlineSurvey;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	



}

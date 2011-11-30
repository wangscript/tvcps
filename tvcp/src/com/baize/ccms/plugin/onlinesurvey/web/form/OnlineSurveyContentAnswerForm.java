/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.onlinesurvey.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContentAnswer;
import com.j2ee.cms.common.core.web.GeneralForm;

/**
 * <p>标题: 网上调查表单</p>
 * <p>描述: 网上调查，以便页面和方法中调用</p>
 * <p>模块: 网上调查</p>
 * <p>版权: Copyright (c) 2009 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:35:25 
 * @history（历次修订内容、修订人、修订时间等） 
*/
public class OnlineSurveyContentAnswerForm extends GeneralForm {

	private static final long serialVersionUID = -5632820748626415539L;
    
	//问题id
	private String questionId;
	//答案id
	private String answerId;
	
	//类型id
    private 	String id;
    //网上内容id
    private    String categoryId;
	//网上调查类型id
    private   String  idClass;
    //执行将要被删除的id
    private   String idDelete;
    //图片位置
    private  String path;
	private OnlineSurveyContentAnswer onlineSurveyContentAnswer = new OnlineSurveyContentAnswer();
	
	public OnlineSurveyContentAnswer getOnlineSurveyContentAnswer() {
		return onlineSurveyContentAnswer;
	}

	public void setOnlineSurveyContentAnswer(
			OnlineSurveyContentAnswer onlineSurveyContentAnswer) {
		this.onlineSurveyContentAnswer = onlineSurveyContentAnswer;
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
	/**
	 * @return the idClass
	 */
	public String getIdClass() {
		return idClass;
	}

	/**
	 * @param idClass the idClass to set
	 */
	public void setIdClass(String idClass) {
		this.idClass = idClass;
	}

	protected void validateData(ActionMapping arg0, HttpServletRequest arg1){
	}

	/**
	 * @return the idDelete
	 */
	public String getIdDelete() {
		return idDelete;
	}

	/**
	 * @param idDelete the idDelete to set
	 */
	public void setIdDelete(String idDelete) {
		this.idDelete = idDelete;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the requestionId
	 */
	public String getQuestionId() {
		return questionId;
	}

	/**
	 * @param requestionId the requestionId to set
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the answerId
	 */
	public String getAnswerId() {
		return answerId;
	}

	/**
	 * @param answerId the answerId to set
	 */
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

}

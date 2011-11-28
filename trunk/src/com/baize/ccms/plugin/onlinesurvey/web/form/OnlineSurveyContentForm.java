/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.plugin.onlinesurvey.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.baize.ccms.plugin.onlinesurvey.domain.OnlineSurveyContent;
import com.baize.common.core.web.GeneralForm;
/**
 * <p>标题: 信件表单</p>
 * <p>描述: 网上调查，以便页面和方法中调用</p>
 * <p>模块:网上调查</p>
 * <p>版权: Copyright (c) 2009南京百泽网络科技有限公司
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:35:25 
 * @history（历次修订内容、修订人、修订时间等） 
*/
public class OnlineSurveyContentForm extends GeneralForm {

	private static final long serialVersionUID = -5632827958636915339L;

	//问题 id
	private String questionId; 
	//调查类别id
	private String categoryId;
	//列表样式
	private String listStyle;
	
	
	//问卷列表显示
	private  String colCount;
	//文本框高度
	private String texthight;
	//宽度
	private String  textwidth;
	//网站类型id
	private String     id;
	//网站内容id
	private  String   lasttimes;
	//设置样式
	private String styleContent;
	//绑定栏目（更多）
	private String more;
	//问题列表
	private List listQuestion = new ArrayList();
	//答案列表
	private List listAnswer = new ArrayList();
	
	
	
	private String idm;
	private OnlineSurveyContent onlineSurveyContent = new OnlineSurveyContent();
	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1){
		// TODO Auto-generated method stub
	}
	public OnlineSurveyContent getOnlineSurveyContent() {
		return onlineSurveyContent;
	}
	public void setOnlineSurveyContent(OnlineSurveyContent onlineSurveyContent) {
		this.onlineSurveyContent = onlineSurveyContent;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the styleContent
	 */
	public String getStyleContent() {
		return styleContent;
	}
	/**
	 * @param styleContent the styleContent to set
	 */
	public void setStyleContent(String styleContent) {
		this.styleContent = styleContent;
	}
	/**
	 * @return the colCount
	 */
	public String getColCount() {
		return colCount;
	}
	/**
	 * @return the textwidth
	 */
	public String getTextwidth() {
		return textwidth;
	}
	/**
	 * @param colCount the colCount to set
	 */
	public void setColCount(String colCount) {
		this.colCount = colCount;
	}
	/**
	 * @param textwidth the textwidth to set
	 */
	public void setTextwidth(String textwidth) {
		this.textwidth = textwidth;
	}
	/**
	 * @return the texthight
	 */
	public String getTexthight() {
		return texthight;
	}
	/**
	 * @param texthight the texthight to set
	 */
	public void setTexthight(String texthight) {
		this.texthight = texthight;
	}
	/**
	 * @return the lasttimes
	 */
	public String getLasttimes() {
		return lasttimes;
	}
	/**
	 * @param lasttimes the lasttimes to set
	 */
	public void setLasttimes(String lasttimes) {
		this.lasttimes = lasttimes;
	}
	/**
	 * @return the idm
	 */
	public String getIdm() {
		return idm;
	}
	/**
	 * @param idm the idm to set
	 */
	public void setIdm(String idm) {
		this.idm = idm;
	}
	/**
	 * @return the listStyle
	 */
	public String getListStyle() {
		return listStyle;
	}
	/**
	 * @param listStyle the listStyle to set
	 */
	public void setListStyle(String listStyle) {
		this.listStyle = listStyle;
	}
	/**
	 * @return the more
	 */
	public String getMore() {
		return more;
	}
	/**
	 * @param more the more to set
	 */
	public void setMore(String more) {
		this.more = more;
	}
	/**
	 * @return the listQuestion
	 */
	public List getListQuestion() {
		return listQuestion;
	}
	/**
	 * @param listQuestion the listQuestion to set
	 */
	public void setListQuestion(List listQuestion) {
		this.listQuestion = listQuestion;
	}
	/**
	 * @return the listAnswer
	 */
	public List getListAnswer() {
		return listAnswer;
	}
	/**
	 * @param listAnswer the listAnswer to set
	 */
	public void setListAnswer(List listAnswer) {
		this.listAnswer = listAnswer;
	}
}

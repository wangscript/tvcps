/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.onlinesurvey.service;

import java.util.ArrayList;

import com.baize.ccms.plugin.onlinesurvey.domain.OnlineSurveyContent;
import com.baize.ccms.plugin.onlinesurvey.web.form.OnlineSurveyContentForm;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述网上调查类接口等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 包坤涛
 * @version 1.0
 * @since 2009-10-19 下午02:46:26
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface OnlineSurveyContentService {
	
	/**
	 * 网上调查类型分页
	 * @param category 类型id
	 * @param pagination 分页对象
	 * @return Pagination
	 */
	Pagination findOnlineQuestionPage(Pagination pagination,String nodeId);
	
	/**
	 * 网上调查类型内容
	 * @param requestionId 类型id
	 * @return OnlineSurveyContent
	 */
	OnlineSurveyContent findOnlineContent(String requestionId);
	
	/**
	 * 添加网上调查问题
	 * @param onlineSurveyContent 网上调查类内容
	 * @return 
	 */
    void addOnlineSurveyContent(OnlineSurveyContent onlineSurveyContent);
    
    /**
     * 网上调查类内容分页
     * @param onlineSurveyContent 网上调查类内容
     * @param questionId 调查问题id
     * @return 
     */
    void modifyOnlineSurveyContent(OnlineSurveyContent onlineSurveyContent, String questionId);
      
    /**
     * 删除网上调查问题
     * @param idContent 网上调查类内容id
     * @return String
     */
    void deleteOnlineSurveyContentByQuestionId(String requestionId);
	
 
    /***
     * 根据主题id查找问题分页(单元设置里面)
     * @param themeId
     * @return
     */
    Pagination findQuestionByThemeIdPage(Pagination pagination, String nodeId);
 	
 	
 	
 	
    
    
	/**
	 * 网上调查类分页
	 * @param id 内容类型id
	 * @param pagination 分页对象
	 * @return Pagination
	 */
	Pagination finOnlineAnswerQuestions(Pagination pagination,String id);
    
	/**
	 * 网上调查类分页
	 * @param category 类型id
	 * @param pagination 分页对象
	 * @return Pagination
	 */
	Pagination finSurveyServiceData(Pagination pagination,String category);

      /**
 	  * 保存样式.
 	  * @param Id
 	  * @param content
 	  */
 	 void setStyle(OnlineSurveyContentForm form,String siteId);
 	 /**
 	  *获取样式.
 	  */
  	 public ArrayList<String> getStyle(String siteid);
}

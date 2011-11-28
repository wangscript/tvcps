/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.onlinesurvey.service;

import java.util.List;

import com.baize.ccms.plugin.onlinesurvey.domain.OnlineSurveyContentAnswer;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述网上调查类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 包坤涛
 * @version 1.0
 * @since 2009-10-19 下午02:46:26
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface OnlineSurveyContentAnswerService {
	
	/**
	* 分页显示问题答案
	* @param pagination 分页对象
	* @param questionId 问题id
	* @return Pagination
	*/
	Pagination findOnlineQueryAnswerPage(Pagination pagination, String questionId);
	
	/**
	 * 网上调查内容答案对象查找
	 * @param answerId 网上调查内容答案对象id
	 * @return OnlineSurveyContentAnswer
	 */
     OnlineSurveyContentAnswer findOnlineContentAnswer(String answerId);
     
     /**
 	 * 添加网上调查内容答案对象
 	 * @param onlineSurveyContentAnswer 网上调查内容答案对象
 	 * @return String
 	 */
     void addOnlineSurveyContentAnswer(OnlineSurveyContentAnswer onlineSurveyContentAnswer);
 	 
	 /**
	 * 网上调查内容答案对象修改
	 * @param onlineSurveyContentAnswer 网上调查内容答案对象
	 * @param answerId   网上调查内容答案对象id
	 * @return
	 */
     void modifyOnlineSurveyContentAnswer(OnlineSurveyContentAnswer onlineSurveyContentAnswer, String answerId);
    
     /**
	 * 网上调查内容答案对象删除
	 * @param answerId   删除的id
	 * @return
	 */
     void deleteOnlineSurveyAnswer(String answerId);
    
     /**
  	 * 调查结果分页
  	 * @param pagination 网上调查内容分页对象
  	 * @return Pagination
  	 */
  	Pagination findOnlineResultPage(Pagination pagination);
     
	 /** 
	 * 创建jpg格式的柱状图 
	 * @param histogramName  柱状图名称 
	 * @param xName  目录轴的显示标签(横轴名称) 
	 * @param yName  数值轴的显示标签(纵轴名称) 
	 * @param exportPath  输出路径+文件名称 
	 * @param questionId  调查问题id
	 * @return true/false  成功/失败 
	 */ 
    boolean imageChart3DToJpg(String histogramName, String xName, String yName, String exportPath, String questionId);
    
    /** 
	 * 创建jpg格式的饼状图 
	 * @param histogramName  饼状图名称 
	 * @param xName  目录轴的显示标签(横轴名称) 
	 * @param yName  数值轴的显示标签(纵轴名称) 
	 * @param exportPath  输出路径+文件名称 
	 * @param questionId  调查问题id
	 * @return true/false  成功/失败 
	 */ 
    boolean imagepieChart3DToJpg(String histogramName, String xName, String yName, String exportPath, String questionId);
}

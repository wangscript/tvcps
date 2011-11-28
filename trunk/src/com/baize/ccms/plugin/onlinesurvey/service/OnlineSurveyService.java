/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.onlinesurvey.service;

import java.util.List;
import java.util.Map;

import com.baize.ccms.plugin.onlinesurvey.domain.OnlineSurvey;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类网上调查包含的内容</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 包坤涛
 * @version 1.0
 * @since 2009-10-19 下午02:46:26
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface OnlineSurveyService {
	
	/**
	 * 网上调查类别首页信息显示
	 * @param categoryId 网上类别 id
	 * @return  网上类别对象集合         
	 */
    public List<OnlineSurvey> findOnlineSurvey(String categoryId);
	
	/**
	 * 网上调查类别分页
	 * @param categoryId 类型id
	 * @param pagination 分页对象
	 * @return Pagination
	 */
	Pagination finOnlineSurveyData(Pagination pagination, String categoryId);
	
	/**
	 * 网上调查类别添加
	 * @param onlineSurvey  网上调查类别
	 * @return String
	 */
    void addOnlineSurvey(OnlineSurvey  onlineSurvey); 
    
    /**
     * 网上调查类别修改
     * @param    nodeId     网上调查类别id
     * @param    onlineSurvey  网上调查类别
     * return String
     */
    void modifyOnlineSurvey(OnlineSurvey onlineSurvey,String nodeId);
    
    /**
	 * 得到需要修改的对象  OnlineSurvery
	 * @param nodeId 网上调查类别内容实体   
	 * @return OnlineSurvey
	 */
    OnlineSurvey findEntitlyOnline(String nodeId);
    
    /**
     * 删除网上调查类别
     * @param nodeId  网上类别id
     */
    void deleteOnlineSurvey(String nodeId);
   
    /**
     * 发布在线调查目录
     * @param siteId
     */
    void publishGuestBookDir(String siteId);
    
    /**
     * 获取一般调查指定主题
     * @param unitId
     * @param themeId
     * @param questionId
     * @return
     */
    String getNormalDetailHtml(String unitId, String themeId, String questionId, String display, String appName);
    
    /**
     * 获取一般调查问题分页
     * @param pagination
     * @return
     */
    Pagination getNormalPagination(Pagination pagination, String themeId, String unitId);
    
    /**
     * 
     * @param pagination
     * @param unitId
     * @param flag
     * @return
     */
    String getContent(Pagination pagination, String unitId, boolean flag, String appName);
    
    /**
     * 投票提交页面
     * @param questionId
     * @param siteId
     * @return
     */
    String getDisplayStyle(String questionId, String siteId);
    
    /**
     * 添加投票
     * @param questionId
     * @param answerIds
     * @param feedbackContent
     */
    void addCommit(String questionId, String answerIds, String feedbackContent);
    
    /**
     * 显示问卷调查详细
     * @param unitId
     * @param themeId
     * @return
     */
    String getAnswerDetailHtml(String unitId, String themeId, String appName);
    
    /**
     * 获取问卷调查显示
     * @param themeId
     * @param unitId
     * @return
     */
    String getAnswerDisplayStyle(String themeId, String unitId);

    /**
     * 问卷调查提交
     * @param questionIds
     * @param answerIds
     * @param feedbackContent
     */
    void addAnswerCommit(String questionIds, String answerIds, String feedbackContent);
    /**
	 * 网上调查查询子菜单的内容
	 * @param pagination  分页对象
	 * @param id  网上调查类别id 
	 * @return Pagination
	 */
    Pagination finOnlineAnswerQuestions(Pagination pagination,String id);
    
    /**
	 * 网上调查查询子菜单的内容  方法从写
	 * @param pagination  分页对象 
	 * @return Pagination
	 */
    Pagination  finOnlineAnswerQuestions(Pagination pagination);
    
    /**
     * 获取问卷调查主题分页
     * @param pagination
     * @return
     */
    Pagination getAnswerPagination(Pagination pagination, String unitId, int flag);
    
    /**
     * 获取问卷调查列表
     * @param pagination
     * @param unitId
     * @param appName
     * @return
     */
    String getAnswerList(Pagination pagination, String unitId, String appName);
    
    /***
     * 获取一般调查内容
     * @param pagination
     * @param unitId
     * @param appName
     * @return
     */
    String getNormalContent(Pagination pagination, String unitId, String appName);
    
    /**
     * 查看调查结果
     * @param questionIds
     * @return
     */
    Map getResult(String questionIds, String themeId);
}
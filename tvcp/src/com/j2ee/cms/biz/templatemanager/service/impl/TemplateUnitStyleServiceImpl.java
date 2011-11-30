/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.templatemanager.service.impl;

import java.util.Date;
import java.util.List;

import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitCategoryDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitStyleDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitCategory;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.templatemanager.service.TemplateUnitStyleService;
import com.j2ee.cms.biz.templatemanager.web.form.TemplateUnitStyleForm;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;

/**
 * 
 * <p>标题: ——  模板单元样式业务逻辑处理类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-6-6 下午05:48:29
 * @history（历次修订内容、修订人、修订时间等）
 */

public class TemplateUnitStyleServiceImpl implements TemplateUnitStyleService {
	
	/** 注入单元样式dao */
	private TemplateUnitStyleDao templateUnitStyleDao;
	/** 注入模板单元类别*/
	private TemplateUnitCategoryDao templateUnitCategoryDao;
	/**注入日志dao*/
	private SystemLogDao systemLogDao;
	
	
	public Pagination findTemplateUnitStylePage(Pagination pagination,String categoryId,String siteId){
		String str[] = {"unit_categoryId","siteId"};
		Object obj[] = {categoryId,siteId};
		return templateUnitStyleDao.getPagination(pagination,str,obj);
	}
	
	public String findTemplateUnitCategoryNameByKey(String categoryId){
		TemplateUnitCategory templateUnitCategory = templateUnitCategoryDao.getAndClear(categoryId);
		String name = "";
		if(templateUnitCategory != null){
			name = templateUnitCategory.getName();
		}	
		return name;
	}
	
	
	public TemplateUnitStyle findTemplateUnitStyleByKey(String styleId){
		TemplateUnitStyle templateUnitStyle = new TemplateUnitStyle();
		TemplateUnitStyle templateUnitStyle2 = templateUnitStyleDao.getAndClear(styleId);
		if(templateUnitStyle2 != null){			
			return templateUnitStyle2;
		}else{
			return templateUnitStyle;
		}
	}
	
	public void addTemplateUnitStyle(TemplateUnitStyle templateUnitStyle,String categoryId,String htmlContent,String userId,String siteId){
		templateUnitStyle.setContent(htmlContent);
		TemplateUnitCategory templateUnitCategory = templateUnitCategoryDao.getAndClear(categoryId);
		templateUnitStyle.setTemplateUnitCategory(templateUnitCategory);
		User user = new User();
		user.setId(userId);
		templateUnitStyle.setCreator(user);
		Site site = new Site();
		site.setId(siteId);
		templateUnitStyle.setSite(site);
		templateUnitStyle.setCreateTime(new Date());
		templateUnitStyleDao.save(templateUnitStyle);
		
		// 写入日志文件
		String name = templateUnitCategory.getName();
		String categoryName = "";
		if(name.equals("栏目链接")) {
			categoryName = "模板设置(栏目链接)(样式管理)->添加";
		} else if(name.equals("标题链接")) {
			categoryName = "模板设置(标题链接)(样式管理)->添加";
		} else if(name.equals("当前位置")) {
			categoryName = "模板设置(当前位置)(样式管理)->添加";
		} else if(name.equals("图片新闻")) {
			categoryName = "模板设置(图片新闻)(样式管理)->添加";
		} else if(name.equals("文章正文")) {
			categoryName = "模板设置(文章正文)(样式管理)->添加";
		} else if(name.equals("期刊（按分类）")) {
			categoryName = "模板设置(期刊分类)(样式管理)->添加";
		}
		String param = templateUnitStyle.getName();
		systemLogDao.addLogData(categoryName, siteId, userId, param);
		
	}
	public void modifyTemplateUnitStyle(TemplateUnitStyle templateUnitStyle,String categoryId,String htmlContent,String userId,String siteId,String styleId){
		String displayEffect = templateUnitStyle.getDisplayEffect();
		String stylename = templateUnitStyle.getName();
		templateUnitStyle = templateUnitStyleDao.getAndClear(styleId);
		if(templateUnitStyle != null){
			templateUnitStyle.setName(stylename);
			templateUnitStyle.setContent(htmlContent);
			TemplateUnitCategory templateUnitCategory = templateUnitCategoryDao.getAndClear(categoryId);
			templateUnitStyle.setTemplateUnitCategory(templateUnitCategory);
			User user = new User();
			user.setId(userId);
			templateUnitStyle.setCreator(user);
			Site site = new Site();
			site.setId(siteId);
			templateUnitStyle.setSite(site);
			templateUnitStyle.setDisplayEffect(displayEffect);
			templateUnitStyleDao.update(templateUnitStyle);
			
			// 写入日志文件
			String name = templateUnitCategory.getName();
			String categoryName = "";
			if(name.equals("栏目链接")) {
				categoryName = "模板设置(栏目链接)(样式管理)->修改";
			} else if(name.equals("标题链接")) {
				categoryName = "模板设置(标题链接)(样式管理)->修改";
			} else if(name.equals("当前位置")) {
				categoryName = "模板设置(当前位置)(样式管理)->修改";
			} else if(name.equals("图片新闻")) {
				categoryName = "模板设置(图片新闻)(样式管理)->修改";
			} else if(name.equals("文章正文")) {
				categoryName = "模板设置(文章正文)(样式管理)->修改";
			} else if(name.equals("期刊（按分类）")) {
				categoryName = "模板设置(期刊分类)(样式管理)->修改";
			}
			String param = templateUnitStyle.getName();
			systemLogDao.addLogData(categoryName, siteId, userId, param);
		}
	}
	
	/**
	 * 按照类别id查找所有样式名称
	 * @return styleNameStr 样式名称
	 */
	@SuppressWarnings("unchecked")
	public String findStyleNameStr(String categoryId) {
		List list = templateUnitStyleDao.findByNamedQuery("findStyleNameStr", "categoryId", categoryId);
		String styleNameStr = "";
		for(int i = 0; i < list.size(); i++) {
			styleNameStr += "," + String.valueOf(list.get(i)); 
		}
		String str = "";
		str = styleNameStr.replaceFirst(",", "");
		return str;
	}
	
	/**
	 * 根据多个主键删除数据
	 * @param ids          主键集合
	 * @param siteId
	 * @param sessionID
	 * @param categoryId
	 */
	public void deleteTemplateUnitStyleByIds(String ids, String siteId, String sessionID, String categoryId){
		
		// 写入日志文件
		TemplateUnitCategory templateUnitCategory = templateUnitCategoryDao.getAndClear(categoryId);
		String name = templateUnitCategory.getName();
		String categoryName = "";
		if(name.equals("栏目链接")) {
			categoryName = "模板设置(栏目链接)(样式管理)->删除";
		} else if(name.equals("标题链接")) {
			categoryName = "模板设置(标题链接)(样式管理)->删除";
		} else if(name.equals("当前位置")) {
			categoryName = "模板设置(当前位置)(样式管理)->删除";
		} else if(name.equals("图片新闻")) {
			categoryName = "模板设置(图片新闻)(样式管理)->删除";
		} else if(name.equals("文章正文")) {
			categoryName = "模板设置(文章正文)(样式管理)->删除";
		}else if(name.equals("期刊（按分类）")) {
			categoryName = "模板设置(期刊分类)(样式管理)->删除";
		}
		String[] str = ids.split(",");
		if(!StringUtil.isEmpty(str[0])) {
			TemplateUnitStyle templateUnitStyle = null;
			for(int i = 0; i < str.length; i++) {
				templateUnitStyle = templateUnitStyleDao.getAndClear(str[i]);
				String param = templateUnitStyle.getName();
				systemLogDao.addLogData(categoryName, siteId, sessionID, param);
			}
		}
		
		ids = SqlUtil.toSqlString(ids);
		templateUnitStyleDao.deleteByIds(ids);
	}
	
	public void findStyleByStyleId(TemplateUnitStyleForm templateUnitStyleForm,String siteId){
		String unit_categoryId = templateUnitStyleForm.getCategoryId();		
		String styleId = templateUnitStyleForm.getStyleId();
		String str[] = {"styleId","unit_categoryId","siteId"};
		Object obj[] = {styleId,unit_categoryId,siteId};
		List templateUnitStyleList = templateUnitStyleDao.findByNamedQuery("findTemplateUnitStyleByStyleIdAndCategoryIdAndSiteId", str, obj);
		if(templateUnitStyleList != null && templateUnitStyleList.size() > 0){
			TemplateUnitStyle templateUnitStyle = (TemplateUnitStyle)templateUnitStyleList.get(0);
			String content = templateUnitStyle.getContent();			
			String displayEffect = templateUnitStyle.getDisplayEffect();
			templateUnitStyleForm.setStylePreview(displayEffect);
			templateUnitStyleForm.setStyleContent(content);
		} else {
			templateUnitStyleForm.setStylePreview(null);
			templateUnitStyleForm.setStyleContent(null);
		}
	}
	
	/**
	 * @param templateUnitStyleDao the templateUnitStyleDao to set
	 */
	public void setTemplateUnitStyleDao(TemplateUnitStyleDao templateUnitStyleDao) {
		this.templateUnitStyleDao = templateUnitStyleDao;
	}
	/**
	 * @param templateUnitCategoryDao the templateUnitCategoryDao to set
	 */
	public void setTemplateUnitCategoryDao(
			TemplateUnitCategoryDao templateUnitCategoryDao) {
		this.templateUnitCategoryDao = templateUnitCategoryDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

}

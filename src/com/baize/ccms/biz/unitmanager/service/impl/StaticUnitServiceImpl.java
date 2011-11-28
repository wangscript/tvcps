/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.unitmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitDao;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnit;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitCategory;
import com.baize.ccms.biz.unitmanager.service.StaticUnitService;
import com.baize.ccms.biz.unitmanager.web.form.ColumnLinkForm;
import com.baize.ccms.biz.unitmanager.web.form.StaticUnitForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.util.StringUtil;

/**
 * <p>标题: 静态单元</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 单元管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-6-1 上午12:30:19
 * @history（历次修订内容、修订人、修订时间等）
 */
public class StaticUnitServiceImpl implements StaticUnitService {
	private static final Logger log = Logger.getLogger(StaticUnitServiceImpl.class);
	/** 注入模板单元dao */
	private TemplateUnitDao templateUnitDao;
	
	/** 注入日志dao*/
	private SystemLogDao systemLogDao;

	public void saveConfig(String unitId, String categoryId, String content, String siteId, String sessionID){
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		if (unit != null) {
			TemplateUnitCategory templateUnitCategory = new TemplateUnitCategory();
			templateUnitCategory.setId(categoryId);
			unit.setTemplateUnitCategory(templateUnitCategory);
			if(!StringUtil.isEmpty(content)) {
				unit.setHtml(content);
				templateUnitDao.saveOrUpdate(unit);
			}
		}
		
		// 写入日志
		String categoryName = "模板设置(静态单元)->保存";
		String param = unit.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}
	
	public String findConfig(String unitId, String categoryId) {
		String rs = "";
		String str1[] = {"unitId", "CategoryId"};
		Object obj1[] = {unitId, categoryId};
		List tempUnitList = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitIdAndUnitCategory", str1, obj1);
		if(tempUnitList != null && tempUnitList.size() > 0 ){
			TemplateUnit unit = (TemplateUnit)tempUnitList.get(0);
			rs = unit.getHtml();
		} 
		return rs;
	}

	public void saveSiteConfig(StaticUnitForm form, String unitId, String siteId, String sessionID) {
		List<TemplateUnit> list = new ArrayList<TemplateUnit>();
		list = this.findTemplateUnitByUnitName(unitId);
		
		for(int i = 0; i < list.size(); i++) {
			TemplateUnit newTemplateUnit = new TemplateUnit();
			newTemplateUnit = (TemplateUnit) list.get(i);
			form.setUnit_unitId(newTemplateUnit.getId());
			//保存生成的代码到数据库
			this.saveConfigContent(form, siteId, sessionID);
		}
	}
	
	/**
	 * 按照单元名字查找模板单元
	 * @param unitId   要站内保存的某个单元id（通过此id获得要的单元名字）
	 * @return
	 */
	private List<TemplateUnit> findTemplateUnitByUnitName(String unitId) {
		List<TemplateUnit> list = new ArrayList<TemplateUnit>();
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		// 要查找的单元名字
		String name = templateUnit.getName();
		list = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitName", "name", name);	
		return list;
	}
	
	/**
	 * 保存配置内容
	 * @param titleLinkForm
	 * @param siteId
	 * @param sessionID
	 */
	public void saveConfigContent(StaticUnitForm form, String siteId, String sessionID) {
		String unitId = form.getUnit_unitId();
		String categoryId = form.getUnit_categoryId();
		String htmlContent = form.getStaticContent();
//		log.debug("htmlContent====================1=="+htmlContent);
//		htmlContent = StringUtil.isoToUTF8(htmlContent);
//		log.debug("htmlContent====================2=="+htmlContent);
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		if (unit != null) {
			TemplateUnitCategory templateUnitCategory = new TemplateUnitCategory();
			templateUnitCategory.setId(categoryId);
			unit.setTemplateUnitCategory(templateUnitCategory);
			unit.setHtml(htmlContent);
			unit.setCss(form.getUnit_css());
			if(!StringUtil.isEmpty(form.getUnit_columnId()) && !form.getUnit_columnId().equals("null")){
				unit.setColumnIds(form.getUnit_columnId());
			}
			templateUnitDao.saveOrUpdate(unit);
		}
		
		// 写入日志
		String categoryName = "模板设置(静态单元)->保存";
		String param = unit.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}
	
	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

}

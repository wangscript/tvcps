/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.articlemanager.dao.ArticleAttributeDao;
import com.baize.ccms.biz.articlemanager.domain.ArticleAttribute;
import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.ccms.biz.columnmanager.dao.ColumnDao;
import com.baize.ccms.biz.columnmanager.domain.Column;
import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitCategoryDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitStyleDao;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnit;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitCategory;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnitStyle;
import com.baize.ccms.biz.unitmanager.service.ColumnLinkService;
import com.baize.ccms.biz.unitmanager.web.form.ColumnLinkForm;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.FileUtil;
import com.baize.common.core.util.IDFactory;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.util.XmlUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 郑荣华
 * @version 1.0
 * @since 2009-6-1 下午05:42:58
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ColumnLinkServiceImpl implements ColumnLinkService {
	
	private static final Logger log = Logger.getLogger(TitleLinkServiceImpl.class);
	
	/** 注入模板单元dao */
	private TemplateUnitDao templateUnitDao;
	/** 注入栏目dao **/
	private ColumnDao columnDao;
	/** 注入文章属性dao **/
	private ArticleAttributeDao articleAttributeDao;
	/** 注入模板单元样式dao **/
	private TemplateUnitStyleDao templateUnitStyleDao;
	/**注入模板单元类别dao**/
	private TemplateUnitCategoryDao templateUnitCategoryDao;
	/** 注入日志dao*/
	private SystemLogDao systemLogDao;
	
	/**
	 * 查找配置文件
	 * @param form  栏目链接表单
	 */
	public void findConfig(ColumnLinkForm form) {	
		//单元ID
		String unitId = form.getUnit_unitId();
		//栏目ID
		String columnId = form.getUnit_columnId();
		//查询栏目表数据
		Column column = columnDao.getAndNonClear(columnId);
		List<ArticleAttribute> articleAttributeList = new ArrayList<ArticleAttribute>();
		if(column != null){
			//根据栏目对象查询出文章格式
			ArticleFormat articleFormat = column.getArticleFormat();
			if(articleFormat != null){
				//文章格式ID
				String articleFormatId = articleFormat.getId();
				articleAttributeList = articleAttributeDao.findByNamedQuery("findAttributesByFormatId", "formatId", articleFormatId);
			}
		}
		//页面显示列表使用的属性名(字段标签)
		form.setArticleAttributeList(articleAttributeList);
		String categoryId = form.getUnit_categoryId();
		String str1[] = {"unitId", "CategoryId"};
		Object obj1[] = {unitId, categoryId};
		List tempUnitList = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitIdAndUnitCategory", str1, obj1);
		if(tempUnitList != null && tempUnitList.size() > 0 ){
			TemplateUnit unit = (TemplateUnit)tempUnitList.get(0);
			String htmlContent = unit.getHtml();			
			String filePath = unit.getConfigFile();
			if(filePath != null && !filePath.equals("")) {
				form = this.setXmlData(GlobalConfig.appRealPath + filePath, form);
			} else {
				String haveconfigFilePath = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + File.separator+"column_link.xml";
				form = this.setXmlData(haveconfigFilePath, form);
			}
			form.setHtmlContent(htmlContent);
			form.setUnit_css(unit.getCss());
			
			String columnStyle = form.getColumnStyle();
			String viewImg = "";
			if(!StringUtil.isEmpty(columnStyle)) {
				TemplateUnitStyle templateUnitStyle = (TemplateUnitStyle) templateUnitStyleDao.getAndNonClear(columnStyle);
				viewImg = templateUnitStyle.getDisplayEffect();
			}else{
				viewImg = "";
			}
			form.setViewImg(viewImg);
			
		}else{
			String configFile = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + File.separator+"column_link.xml";
			this.setXmlData(configFile, form);
		}
	}
	
	/**
	 * 获取xml的值来设置form 	
	 * @param filePath xml文件的路径
	 * @param titleLinkForm 标题链接form对象
	 * @return TitleLinkForm 标题链接form对象
	 */
	private ColumnLinkForm setXmlData(String filePath, ColumnLinkForm form) {
		log.debug("filePath======================"+filePath);
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		// 获取xml文件中的字段信息
		String style = xmlUtil.getNodeText("/baize/column-link/column-style");
		String type = xmlUtil.getNodeText("/baize/column-link/column-type");
		String fixedColumn = xmlUtil.getNodeText("/baize/column-link/fixedColumn");
		String row = xmlUtil.getNodeText("/baize/column-link/display-column-row");
		String col = xmlUtil.getNodeText("/baize/column-link/display-column-col");
		String prefix = xmlUtil.getNodeText("/baize/column-link/column-prefix");
		String prefixDate = xmlUtil.getNodeText("/baize/column-link/column-prefix-date");
		String prefixPic = xmlUtil.getNodeText("/baize/column-link/column-prefix-picture");
		String suffix = xmlUtil.getNodeText("/baize/column-link/column-suffix");
		String suffixDate = xmlUtil.getNodeText("/baize/column-link/column-suffix-date");
		String suffixPic = xmlUtil.getNodeText("/baize/column-link/column-suffix-picture");
		// 向表单中加入栏目链接的各种信息
		form.setColumnStyle(style);
		form.setColumnType(type);
		form.setFixedColumn(fixedColumn);
		form.setColumnRow(row);
		form.setColumnCol(col);
		form.setColumnPrefix(prefix);
		form.setColumnPrefixDate(prefixDate);
		form.setColumnPrefixPic(prefixPic);
		form.setColumnSuffix(suffix);
		form.setColumnSuffixDate(suffixDate);
		form.setColumnSuffixPic(suffixPic);
		return form;
	}
	
	/**
	 * 按照类别id和网站id查询模板单元样式
	 * @param unit_categoryId
	 * @param siteId
	 * @return
	 */
	public List<TemplateUnitStyle> findTemplateUnitStyleByCategoryIdAndSiteId(String unit_categoryId, String siteId) {
		String[] param = {"unit_categoryId", "siteId"};
		Object[] value = {unit_categoryId, siteId};
		List<TemplateUnitStyle> templateUnitStyleList = templateUnitStyleDao.findByNamedQuery("findTemplateUnitStyleByCategoryIdAndSiteId", param, value);
		if(templateUnitStyleList != null && templateUnitStyleList.size() > 0){
			return templateUnitStyleList;
		}else{
			return new ArrayList<TemplateUnitStyle>();
		}
	}
	
	/**
	 * 保存配置文件
	 * @param titleLinkForm
	 * @return
	 */
	public String saveConfigXml(ColumnLinkForm form) {
		String unitId = form.getUnit_unitId();
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		String configFilePath = templateUnit.getConfigFile();
		String configDir = templateUnit.getConfigDir();
		String haveconfigFilePath = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/column_link.xml";
		String filePath = haveconfigFilePath;
		//新xml文件的保存路径
		String newFilePath = "";
		// 模板单元的配置文件存在则先删除
		if(configFilePath != null && !configFilePath.equals("")) {
			FileUtil.delete(GlobalConfig.appRealPath + configFilePath);
		}
		newFilePath = GlobalConfig.appRealPath + configDir + "/" + IDFactory.getId() + ".xml";
		this.setXmlData(filePath, newFilePath, form);
		return newFilePath;
	}
	
	/**
	 * 设置xml的值	
	 * @param filePath        xml文件的路径
	 * @param newFilePath     新xml文件的路径
	 * @param columnLinkForm  栏目链接form对象
	 */
	private void setXmlData(String filePath, String newFilePath, ColumnLinkForm columnLinkForm) {
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);		
		xmlUtil.setNodeCDATAText("baize/column-link/column-style", columnLinkForm.getColumnStyle());
		xmlUtil.setNodeCDATAText("baize/column-link/column-type", columnLinkForm.getColumnType());
		xmlUtil.setNodeCDATAText("baize/column-link/fixedColumn", columnLinkForm.getFixedColumn());
		xmlUtil.setNodeCDATAText("baize/column-link/display-column-row", columnLinkForm.getColumnRow());
		xmlUtil.setNodeCDATAText("baize/column-link/display-column-col", columnLinkForm.getColumnCol());
		xmlUtil.setNodeCDATAText("baize/column-link/column-prefix", columnLinkForm.getColumnPrefix());
		xmlUtil.setNodeCDATAText("baize/column-link/column-prefix-date", columnLinkForm.getColumnPrefixDate());
		xmlUtil.setNodeCDATAText("baize/column-link/column-prefix-picture", columnLinkForm.getColumnPrefixPic());
		xmlUtil.setNodeCDATAText("baize/column-link/column-suffix", columnLinkForm.getColumnSuffix());
		xmlUtil.setNodeCDATAText("baize/column-link/column-suffix-date", columnLinkForm.getColumnSuffixDate());
		xmlUtil.setNodeCDATAText("baize/column-link/column-suffix-picture", columnLinkForm.getColumnSuffixPic());
		xmlUtil.save(newFilePath);
	}
	
	/**
	 * 保存配置内容
	 * @param titleLinkForm
	 * @param filePath
	 * @param siteId
	 * @param sessionID
	 */
	public void saveConfigContent(ColumnLinkForm form, String filePath, String siteId, String sessionID) {
		String unitId = form.getUnit_unitId();
		String categoryId = form.getUnit_categoryId();
		String htmlContent = form.getHtmlContent();
		TemplateUnit unit = templateUnitDao.getAndClear(unitId);
		if (unit != null) {
			TemplateUnitCategory templateUnitCategory = new TemplateUnitCategory();
			templateUnitCategory.setId(categoryId);
			unit.setTemplateUnitCategory(templateUnitCategory);
			unit.setHtml(htmlContent);
			unit.setConfigFile(filePath);
			unit.setCss(form.getUnit_css());
			// 获取涉及到的栏目ids
			String columnIds = "";
			columnIds = getColumnIds(form.getUnit_columnId(), filePath, siteId);
			if(!StringUtil.isEmpty(columnIds) && !columnIds.equals("null")){
				unit.setColumnIds(columnIds);
			}
			templateUnitDao.saveOrUpdate(unit);
		}
		
		// 写入日志
		String categoryName = "模板设置(栏目链接)->保存";
		String param = unit.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
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
	 * 站内保存
	 * @param form    栏目链接表单对象
	 * @param unitId  单元id
	 * @param siteId
	 * @param sessionID
	 */
	public void saveSiteConfig(ColumnLinkForm form, String unitId, String siteId, String sessionID) {
		List<TemplateUnit> list = new ArrayList<TemplateUnit>();
		list = this.findTemplateUnitByUnitName(unitId);
		
		for(int i = 0; i < list.size(); i++) {
			TemplateUnit newTemplateUnit = new TemplateUnit();
			newTemplateUnit = (TemplateUnit) list.get(i);
			form.setUnit_unitId(newTemplateUnit.getId());
			//保存配置信息到xml文件
			String filePath = this.saveConfigXml(form);
			filePath = filePath.replace(GlobalConfig.appRealPath, "");
			//保存生成的代码到数据库
			this.saveConfigContent(form, filePath, siteId, sessionID);
		}
	}
	
	/**
	 * 获得栏目ids
	 * @param columnId
	 * @param siteId
	 * @param fixedColumn
	 * @param column
	 * @param columnIds
	 * @param type
	 * @return
	 */
	private String getColumnIds(String columnId, String filePath, String siteId) {
		String columnIds = "";
		Column column = columnDao.getAndClear(columnId);
		//获取到当前模板实例的xml配置文件路径
		filePath = GlobalConfig.appRealPath + filePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		// 指定栏目
		String fixedColumn = xmlUtil.getNodeText("/baize/column-link/fixedColumn");
		// 栏目类型
		String type = xmlUtil.getNodeText("/baize/column-link/column-type");
		
		// 判断栏目的类型
		int columnType = StringUtil.parseInt(type);
		if(columnType > 0) {
			// 第一级栏目
			if(columnType == 1) {
				// 按照网站id查出栏目信息
				List<Column> columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
				if(columnList != null && columnList.size() > 0) {
					for(int i = 0; i < columnList.size(); i++) {
						columnIds += "," + columnList.get(i).getId();
					}
				}
				
			// 当前栏目	
			} else if(columnType == 2) {
				columnIds = String.valueOf(columnId);
				
			// 当前栏目的父栏目	
			} else if(columnType == 3) {
				if(column != null && column.getParent() != null) {
					columnIds = String.valueOf(column.getParent().getId());
				}
				
			// 当前栏目的子栏目	
			} else if(columnType == 4) {
				if(column != null && column.getChildren().size() > 0) {
					// 换一种查找方法
					List<Column> list = columnDao.findByNamedQuery("findColumnByParentId", "pid", column.getId());
					if(!CollectionUtil.isEmpty(list)) {
						for(int i = 0; i < list.size(); i++) {
							columnIds += "," + list.get(i).getId();
						}
					}
				}
				
			// 当前栏目的同级栏目	
			} else if(columnType == 5) {
				if(column != null) {
					if(column.getParent() != null) {
						String parentId = column.getParent().getId();
						if(parentId != null) {
							Column parentColumn = columnDao.getAndNonClear(parentId);
							// 换一种查找方法
							List<Column> list = columnDao.findByNamedQuery("findColumnByParentId", "pid", parentColumn.getId());
							if(!CollectionUtil.isEmpty(list)) {
								for(int i = 0; i < list.size(); i++) {
									columnIds += "," + list.get(i).getId();
								}
							}
						}
					// 查找第一级
					} else {
						// 按照网站id查出栏目信息
						List<Column> columnList = columnDao.findByNamedQuery("findFirstColumnBySystemAdmin", "siteId", siteId);
						if(columnList != null && columnList.size() > 0) {
							for(int i = 0; i < columnList.size(); i++) {
								columnIds += "," + columnList.get(i).getId();
							}
						}
					}
				}
				
			// 指定栏目	
			} else if(columnType == 6) {
				String[] str = fixedColumn.split("##");
				columnIds = str[0];
				
			// 指定栏目的父栏目	
			} else if(columnType == 7) {
				String[] str = fixedColumn.split("##");
				String id = str[0];
				if(id != null) {
					Column newcolumn = columnDao.getAndClear(id);
					if(newcolumn.getParent() != null) {
						columnIds = String.valueOf(newcolumn.getParent().getId());
					}
				}
				
			// 指定栏目的子栏目	
			} else if(columnType == 8) {
				String[] str = fixedColumn.split("##");
				String id = str[0];
				if(id != null) {
					Column newColumn = columnDao.getAndNonClear(id);
					if(newColumn != null) {
						// 换一种查找方法
						List<Column> list = columnDao.findByNamedQuery("findColumnByParentId", "pid", newColumn.getId());
						if(!CollectionUtil.isEmpty(list)) {
							for(int i = 0; i < list.size(); i++) {
								columnIds += "," + list.get(i).getId();
							}
						}
					}
				}
			}
		}
		// 如果栏目ids以逗号 开头则删除逗号
		if(!StringUtil.isEmpty(columnIds) && columnIds.startsWith(",")) {
			columnIds = columnIds.replaceFirst(",", "");
		}
		return columnIds;
	}
	
	
	
	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}

	/**
	 * @param columnDao the columnDao to set
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	/**
	 * @param articleAttributeDao the articleAttributeDao to set
	 */
	public void setArticleAttributeDao(ArticleAttributeDao articleAttributeDao) {
		this.articleAttributeDao = articleAttributeDao;
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

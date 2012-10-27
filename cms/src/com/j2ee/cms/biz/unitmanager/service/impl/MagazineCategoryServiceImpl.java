/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.unitmanager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Node;

import com.j2ee.cms.biz.articlemanager.dao.ArticleAttributeDao;
import com.j2ee.cms.biz.articlemanager.dao.ArticleFormatDao;
import com.j2ee.cms.biz.articlemanager.dao.EnumerationDao;
import com.j2ee.cms.biz.articlemanager.domain.ArticleAttribute;
import com.j2ee.cms.biz.articlemanager.domain.ArticleFormat;
import com.j2ee.cms.biz.articlemanager.domain.Enumeration;
import com.j2ee.cms.biz.columnmanager.dao.ColumnDao;
import com.j2ee.cms.biz.columnmanager.domain.Column;
import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitStyleDao;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitCategory;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnitStyle;
import com.j2ee.cms.biz.unitmanager.service.MagazineCategoryService;
import com.j2ee.cms.biz.unitmanager.web.form.MagazineCategoryForm;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.util.XmlUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-8 上午11:03:18
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class MagazineCategoryServiceImpl implements MagazineCategoryService {

	/** 注入模版单元dao */
	private TemplateUnitDao templateUnitDao;
	
	/** 注入模板单元样式dao **/
	private TemplateUnitStyleDao templateUnitStyleDao;
	
	/** 注入栏目dao */
	private ColumnDao columnDao;
	
	/** 文章属性dao */
	private ArticleAttributeDao articleAttributeDao;
	
	/** 文章格式dao */
	private ArticleFormatDao articleFormatDao;
	
	/** 注入枚举dao */
	private EnumerationDao enumerationDao;
	
	/** 注入日志dao*/
	private SystemLogDao systemLogDao;
	
	public void findConfig(MagazineCategoryForm form) {
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
			if(articleFormat != null) {
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
				String infoSource = form.getContentSource();
				
				// 指定栏目
				if(infoSource.equals("2")) {
					String fixedColumn = form.getFixedColumn();
					String fixedColumnId = fixedColumn.split("##")[0];
					Column fixedcolumn = columnDao.getAndNonClear(fixedColumnId);
					List<ArticleAttribute> fixedColumnarticleAttributeList = new ArrayList<ArticleAttribute>();
					if(fixedcolumn != null){
						//根据栏目对象查询出文章格式
						ArticleFormat articleFormat = fixedcolumn.getArticleFormat();
						if(articleFormat != null) {
							//文章格式ID
							String articleFormatId = articleFormat.getId();
							fixedColumnarticleAttributeList = articleAttributeDao.findByNamedQuery("findAttributesByFormatId", "formatId", articleFormatId);
						}
					}
					//页面显示列表使用的属性名(字段标签)
					form.setArticleAttributeList(fixedColumnarticleAttributeList);
				}
				
			} else {
				String haveconfigFilePath = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/magazine_category.xml";
				form = this.setXmlData(haveconfigFilePath, form);
			}
			form.setHtmlContent(htmlContent);
			form.setUnit_css(unit.getCss());
			
			String style = form.getMagazineCategoryStyle();
			String viewImg = "";
			if(!StringUtil.isEmpty(style)) {
				TemplateUnitStyle templateUnitStyle = (TemplateUnitStyle) templateUnitStyleDao.getAndNonClear(style);
				viewImg = templateUnitStyle.getDisplayEffect();
			}else{
				viewImg = "";
			}
			form.setViewImg(viewImg);
			
		}else{
			String configFile = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/magazine_category.xml";
			this.setXmlData(configFile, form);
		}
	}

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

	public void saveConfigContent(MagazineCategoryForm form, String filePath, String siteId, String sessionID) {
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
			String columnIds = "";
			columnIds = getColumnIds(filePath, form.getUnit_columnId());
			if(!StringUtil.isEmpty(columnIds) && !columnIds.equals("null")){
				unit.setColumnIds(columnIds);
			}
			templateUnitDao.saveOrUpdate(unit);
		}
		
		// 写入日志文件
		String categoryName = "模板设置(期刊分类)->保存";
		String param = unit.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
	}

	public String saveConfigXml(MagazineCategoryForm form) {
		String unitId = form.getUnit_unitId();
		TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
		String configFilePath = templateUnit.getConfigFile();
		String configDir = templateUnit.getConfigDir();
		String haveconfigFilePath = GlobalConfig.appRealPath + TemplateUnit.UNIT_CONFIG_DIR + "/magazine_category.xml";
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
	private void setXmlData(String filePath, String newFilePath, MagazineCategoryForm magazineCategoryForm) {
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);		
		xmlUtil.setNodeCDATAText("/j2ee.cms/magazine-category/magazineCategory-style", magazineCategoryForm.getMagazineCategoryStyle());
		xmlUtil.setNodeCDATAText("/j2ee.cms/magazine-category/magazineCategory-source", magazineCategoryForm.getContentSource());
		xmlUtil.setNodeCDATAText("/j2ee.cms/magazine-category/fixedColumn", magazineCategoryForm.getFixedColumn());
		xmlUtil.setNodeCDATAText("/j2ee.cms/magazine-category/infoCategory", magazineCategoryForm.getInfoCategory());
		xmlUtil.setNodeCDATAText("/j2ee.cms/magazine-category/titleSize", String.valueOf(magazineCategoryForm.getTitleSize()));
		xmlUtil.setNodeCDATAText("/j2ee.cms/magazine-category/title-prefix", magazineCategoryForm.getTitlePrefix());
		xmlUtil.setNodeCDATAText("/j2ee.cms/magazine-category/title-prefix-picture", magazineCategoryForm.getTitlePrefixPic());
		xmlUtil.setNodeCDATAText("/j2ee.cms/magazine-category/title-suffix", magazineCategoryForm.getTitleSuffix());
		xmlUtil.setNodeCDATAText("/j2ee.cms/magazine-category/title-suffix-picture", magazineCategoryForm.getTitleSuffixPic());
		xmlUtil.save(newFilePath);
	}
	
	/**
	 * 获取xml的值来设置form 
	 * @param filePath
	 * @param form
	 * @return
	 */
	private MagazineCategoryForm setXmlData(String filePath, MagazineCategoryForm form) {
		XmlUtil xmlUtil = XmlUtil.getInstance(filePath);
		// 获取xml文件中的字段信息
		String style = xmlUtil.getNodeText("/j2ee.cms/magazine-category/magazineCategory-style");
		String type = xmlUtil.getNodeText("/j2ee.cms/magazine-category/magazineCategory-source");
		String fixedColumn = xmlUtil.getNodeText("/j2ee.cms/magazine-category/fixedColumn");
		String infoCategory = xmlUtil.getNodeText("/j2ee.cms/magazine-category/infoCategory");
		String titleSize = xmlUtil.getNodeText("/j2ee.cms/magazine-category/titleSize");
		String titlePrefix = xmlUtil.getNodeText("/j2ee.cms/magazine-category/title-prefix");
		String titlePrefixPic = xmlUtil.getNodeText("/j2ee.cms/magazine-category/title-prefix-picture");
		String titleSuffix = xmlUtil.getNodeText("/j2ee.cms/magazine-category/title-suffix");
		String titleSuffixPic = xmlUtil.getNodeText("/j2ee.cms/magazine-category/title-suffix-picture");
		
		// 向表单中加入期刊分类的各种信息
		form.setMagazineCategoryStyle(style);
		form.setContentSource(type);
		form.setFixedColumn(fixedColumn);
		form.setInfoCategory(infoCategory);
		form.setTitleSize(StringUtil.parseInt(titleSize));
		form.setTitlePrefix(titlePrefix);
		form.setTitlePrefixPic(titlePrefixPic);
		form.setTitleSuffix(titleSuffix);
		form.setTitleSuffixPic(titleSuffixPic);
		return form;
	}
	
	/**
	 * 查找信息类别
	 * @param columnId
	 * @param unitId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map findInfoCategory(String columnId, String unitId) {
		Map map = new HashMap();
		if(!StringUtil.isEmpty(unitId) && !unitId.equals("0")) {
			TemplateUnit templateUnit = templateUnitDao.getAndClear(unitId);
			String filePath = templateUnit.getConfigFile();
			// 有配置文件
			if(!StringUtil.isEmpty(filePath)) {
				filePath = templateUnit.getConfigFile();
				if(FileUtil.isExist(GlobalConfig.appRealPath + filePath)) {
					XmlUtil xmlUtil = XmlUtil.getInstance(GlobalConfig.appRealPath+filePath);
					Node node = xmlUtil.getNode("/j2ee.cms/magazine-category");
					// 是期刊分类类别 
					if(node != null) {
						String source = xmlUtil.getNodeText("/j2ee.cms/magazine-category/magazineCategory-source");
						// 指定栏目	
						if(source.equals("2")) {
							String fixedColumn = xmlUtil.getNodeText("/j2ee.cms/magazine-category/fixedColumn");
							String[] str = fixedColumn.split("##");
							columnId = str[0];
						}
						String infoCategory = xmlUtil.getNodeText("/j2ee.cms/magazine-category/infoCategory");
						
						// 对栏目
						if(!StringUtil.isEmpty(columnId) && !columnId.equals("0") && !columnId.equals("null")) {
							Column column = columnDao.getAndClear(columnId);
							String formatId = column.getArticleFormat().getId();
							List<ArticleAttribute> list = articleAttributeDao.findByNamedQuery("findAttributesByFormatId", "formatId", formatId);
							ArticleAttribute articleAttribute = null;
							// 获取枚举个数
							int enumCount = 0;
							for(int i = 0; i < list.size(); i++) {
								String articleAttributeType = list.get(i).getAttributeType();
								// 有枚举类型
								if(articleAttributeType.equals("enumeration")) {
									enumCount++;
								}
							}
							Object chooseEnumData[] = new Object[enumCount];
							// id##values:::id##values:::id##values
							if(!StringUtil.isEmpty(infoCategory)) {
								String[] str = infoCategory.split(":::");
								enumCount = 0;
								for(int m = 0; m < list.size(); m++) {
									if(list.get(m).getAttributeType().equals("enumeration")) {
										Enumeration enu = enumerationDao.getAndClear(list.get(m).getEnumeration().getId());
										List lists = enu.getEnumValues();
										String cId = enu.getId();
										String cName = enu.getName();
										List enumList = new ArrayList();
										for(int n = 0; n < lists.size(); n++) {
											for(int i = 0; i < str.length; i++) {
												String id = str[i].split("##")[0];
												String values = str[i].split("##")[1];
												Object[] obj = new Object[4];
												if(cId.equals(id) && values.equals(lists.get(n))) {
													obj[0] = String.valueOf(n);
													obj[1] = values;
													obj[2] = id;
													obj[3] = cName;
													enumList.add(obj);
	//												break;
												} 
											}
										}
										chooseEnumData[enumCount] = enumList;
										enumCount++;
									}
								}
								map.put("chooseEnumData", chooseEnumData);
							}
						}
					}
				}
			} 
		}
		map = findInfoCategoryMapByColumnId(columnId, map);
		return map;
	}
	
	private Map findInfoCategoryMapByColumnId(String columnId, Map map) {
		// 对栏目
		if(!StringUtil.isEmpty(columnId) && !columnId.equals("0") && !columnId.equals("null")) {
			Column column = columnDao.getAndClear(columnId);
			String formatId = column.getArticleFormat().getId();
			List<ArticleAttribute> list = articleAttributeDao.findByNamedQuery("findAttributesByFormatId", "formatId", formatId);
			ArticleAttribute articleAttribute = null;
			// 获取枚举个数
			int enumCount = 0;
			for(int i = 0; i < list.size(); i++) {
				String articleAttributeType = list.get(i).getAttributeType();
				// 有枚举类型
				if(articleAttributeType.equals("enumeration")) {
					enumCount++;
				}
			}
			Object allEnumData[] = new Object[enumCount]; 
			if(enumCount != 0) {
				enumCount = 0;
				for(int i = 0; i < list.size(); i++) {
					articleAttribute = list.get(i);
					String articleAttributeType = articleAttribute.getAttributeType();
					// 有枚举类型
					if(articleAttributeType.equals("enumeration")) {
						String enumId = articleAttribute.getEnumeration().getId();
						String enumName = articleAttribute.getEnumeration().getName();
						List listtemp = articleAttribute.getEnumeration().getEnumValues();
						List enumList = new ArrayList();
						for(int j = 0; j < listtemp.size(); j++) {
							Object[] obj = new Object[4];
							String enumValue = listtemp.get(j).toString();
							obj[0] = String.valueOf(j);
							obj[1] = enumValue;
							obj[2] = enumId;
							obj[3] = enumName;
							enumList.add(obj);
						}
						allEnumData[enumCount] = enumList;
						enumCount++;
					}
				}
				map.put("allEnumData", allEnumData);
			}
		
		// 对网站 (查询所有的枚举) -- 有待考虑是否该有	
		} else {
			
		}
		return map;
	}
	
	/**
	 * 查找指定栏目格式属性
	 * @param magazineCategoryForm
	 */
	public void findArticleAttributeByColumnId(MagazineCategoryForm magazineCategoryForm) {
		List articleAttributeList = new ArrayList();
		String columnId = "";
		columnId = magazineCategoryForm.getColumnId();
		String articleAttributeFieldName = "";
		String articleAttributeName = "";
		if(!StringUtil.isEmpty(columnId)) {
			//查询栏目表数据
			Column column = new Column();
			column = columnDao.getAndNonClear(columnId);
			if(column != null) {
				// 根据栏目对象查询出文章格式
				ArticleFormat articleFormat = column.getArticleFormat();
				if(articleFormat != null) {
					//文章格式ID
					String articleFormatId = articleFormat.getId();
					articleAttributeList = articleAttributeDao.findByNamedQuery("findAttributesByFormatId", "formatId", articleFormatId);
					if(articleAttributeList != null && articleAttributeList.size() > 0) {
						for(int i = 0; i < articleAttributeList.size(); i++) {
							ArticleAttribute articleAttribute = (ArticleAttribute) articleAttributeList.get(i);
							if(!StringUtil.isEmpty(articleAttributeFieldName)) {
								articleAttributeFieldName += ":::" + articleAttribute.getFieldName();
							} else {
								articleAttributeFieldName = articleAttribute.getFieldName();
							}
							if(!StringUtil.isEmpty(articleAttributeName)) {
								articleAttributeName += ":::" + articleAttribute.getAttributeName();
							} else {
								articleAttributeName = articleAttribute.getAttributeName();
							}
						}
					}
				}
			}
		}
		magazineCategoryForm.setArticleAttributeFieldName(articleAttributeFieldName);
		magazineCategoryForm.setArticleAttributeName(articleAttributeName);
	}

	private String getColumnIds(String configFilePath, String columnId){
		//获取到当前模板实例的xml配置文件路径
		configFilePath = GlobalConfig.appRealPath + configFilePath;
		XmlUtil xmlUtil = XmlUtil.getInstance(configFilePath);
		// 期刊来源
		String source = xmlUtil.getNodeText("/j2ee.cms/magazine-category/magazineCategory-source");
		// 指定栏目
		String fixedColumn = xmlUtil.getNodeText("/j2ee.cms/magazine-category/fixedColumn");
		if(source.equals("2")) {
			String[] str = fixedColumn.split("##");
			columnId = str[0];
		}
		return columnId;
	}
	
	/**
	 * @param templateUnitStyleDao the templateUnitStyleDao to set
	 */
	public void setTemplateUnitStyleDao(TemplateUnitStyleDao templateUnitStyleDao) {
		this.templateUnitStyleDao = templateUnitStyleDao;
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
	 * @param articleFormatDao the articleFormatDao to set
	 */
	public void setArticleFormatDao(ArticleFormatDao articleFormatDao) {
		this.articleFormatDao = articleFormatDao;
	}

	/**
	 * @param enumerationDao the enumerationDao to set
	 */
	public void setEnumerationDao(EnumerationDao enumerationDao) {
		this.enumerationDao = enumerationDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}
}

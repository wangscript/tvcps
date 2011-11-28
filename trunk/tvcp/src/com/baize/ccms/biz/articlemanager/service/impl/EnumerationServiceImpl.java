/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.articlemanager.service.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.baize.ccms.biz.articlemanager.dao.ArticleAttributeDao;
import com.baize.ccms.biz.articlemanager.dao.EnumerationDao;
import com.baize.ccms.biz.articlemanager.domain.Enumeration;
import com.baize.ccms.biz.articlemanager.service.EnumerationService;
import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateUnitDao;
import com.baize.ccms.biz.templatemanager.domain.TemplateUnit;
import com.baize.ccms.biz.usermanager.dao.UserDao;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.FileUtil;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.util.XmlUtil;
import org.apache.log4j.Logger;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-9-2 上午11:29:15
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class EnumerationServiceImpl implements EnumerationService {
	
	private final Logger log = Logger.getLogger(EnumerationServiceImpl.class);
	
	/** 注入枚举dao*/ 
	private EnumerationDao enumerationDao;
	
	/** 注入用户dao*/
	private UserDao userDao;
	
	/** 注入系统日志dao*/
	private SystemLogDao systemLogDao;
	
	/** 注入模版单元dao */
	private TemplateUnitDao templateUnitDao;
	
	/** 注入文章属dao */
	private ArticleAttributeDao articleAttributeDao;
	
	/**
	 * 分页显示枚举类别
	 * @return pagination 分页对象
	 */
	public Pagination findEnumerationPagination(Pagination pagination) {
		return enumerationDao.getPagination(pagination);
	}
	
	/**
	 * 保存枚举类别
	 * @param enumerationName 枚举类别
	 * @param creatorId 创建者id
	 * @param siteId	
	 * @return message
	 */
	public String saveEnumerationName(String enumerationName, String creatorId, String siteId) {
		String message = "";
		Enumeration enumeration = new Enumeration();
		User creator = userDao.getAndClear(creatorId);
		enumeration.setCreator(creator);
		enumeration.setName(enumerationName);
		enumerationDao.save(enumeration);
		message = "保存成功";
		
		// 写入日志
		String categoryName = "枚举类型->添加";
		systemLogDao.addLogData(categoryName, siteId, creatorId, enumeration.getName());
		
		return message;
	}
	
	/**
	 * 删除枚举类别
	 * @param ids
	 * @param siteId
	 * @param userId
	 * @return
	 */
	public String deleteEnumerationByIds(String ids, String siteId, String userId) {
		String infoMessage = "";
		// 判断枚举类别是否被引用
		List list = articleAttributeDao.findByDefine("findArticleAttributeByEnumerationId", "enumerationIds", SqlUtil.toSqlString(ids));
		if(CollectionUtil.isEmpty(list)) {
			// 写入日志
			String categoryName = "枚举类型->删除";
			if(!StringUtil.isEmpty(ids)) {
				String[] str = ids.split(",");
				Enumeration enumeration = null;
				for(int i = 0; i < str.length; i++) {
					enumeration = enumerationDao.getAndClear(str[i]);
					systemLogDao.addLogData(categoryName, siteId, userId, enumeration.getName());
				}
			}
			ids = SqlUtil.toSqlString(ids);
			enumerationDao.deleteByIds(ids);
			infoMessage = "删除成功";
		} else {
			infoMessage = "删除失败，删除枚举中某些被引用";
		}
		return infoMessage;
	}
	
	/**
	 * 查找枚举名称
	 * @param enumerationId 枚举类别 id
	 * @return enumerationName
	 */
	public String findEnumerationNameById(String enumerationId) {
		Enumeration enumeration = enumerationDao.getAndClear(enumerationId);
		String enumerationName = "";
		enumerationName = enumeration.getName();
		return enumerationName;
	}
	
	/**
	 * 修改枚举类别
	 * @param enumerationId    枚举id
	 * @param enumerationName  枚举名
	 * @param siteId
	 * @param userId
	 * @return
	 */
	public String modifyEnumerationName(String enumerationId, String enumerationName, String siteId, String userId) {
		Enumeration enumeration = enumerationDao.getAndClear(enumerationId);
		enumeration.setName(enumerationName);
		enumerationDao.update(enumeration);
		
		// 写入日志
		String categoryName = "枚举类型->修改";
		systemLogDao.addLogData(categoryName, siteId, userId, enumerationName);
		
		return "修改成功";
	}
	
	/**
	 * 添加枚举值
	 * @param enumerationId 枚举id
	 * @param enumValuesStr 枚举值字符串
	 * @param  separator 字符串分隔符
	 * @return message
	 */
	@SuppressWarnings("unchecked")
	public String modifyEnumValues(String enumerationId, String enumValuesStr, String separator) {
		Enumeration enumeration = enumerationDao.getAndClear(enumerationId);
		List enumValuesList = new ArrayList();
		
		//如果字符串为空
		if(separator.equals("null")) {
			enumValuesList.add(enumValuesStr);
			
		} else {
			String[] enumValues = enumValuesStr.split(separator);
			for(int i = 0; i < enumValues.length; i++) {
				enumValuesList.add(enumValues[i]);
			}
		}
		enumeration.setEnumValues(enumValuesList);
		enumerationDao.update(enumeration);
		return "添加枚举值成功";
	}
	
	/**
	 * 添加枚举信息
	 * @param enumerationName 枚举名
	 * @param enumValuesStr 枚举值字符串
	 * @param creatorId 创建者id
	 * @param siteId
	 * @return message
	 */
	@SuppressWarnings("unchecked")
	public String addEnum(String enumerationName, String enumValuesStr, String creatorId, String siteId) {
		Enumeration enumeration = new Enumeration();
		
		//保存枚举类别名称
		enumeration.setName(enumerationName);
		//保存创建者
		User creator = userDao.getAndClear(creatorId);
		enumeration.setCreator(creator);
		//添加枚举值
		if(enumValuesStr.length() > 0) {
			List enumValuesList = new ArrayList();
			//去掉最后一个逗号
			enumValuesStr = enumValuesStr.substring(0, enumValuesStr.length()-1);
			String[] enumValues = enumValuesStr.split(",");
			for(int i = 0; i < enumValues.length; i++) {
				enumValuesList.add(enumValues[i]);
			}
			enumeration.setEnumValues(enumValuesList);
		}
		enumeration.setId(StringUtil.UUID());
		enumerationDao.save(enumeration);
		
		
		// 写入添加枚举日志
		String categoryName = "枚举类型->添加";
		systemLogDao.addLogData(categoryName, siteId, creatorId, enumeration.getName());
		
		
		return "添加枚举信息成功";
	}
	
	/**
	 * 查找枚举值
	 * @param enumerationId 枚举类别 id
	 * @return enumValuesStr
	 */
	@SuppressWarnings("unchecked")
	public String findEnumValuesStrById(String enumerationId) {
		Enumeration enumeration = enumerationDao.getAndClear(enumerationId);
		String enumValuesStr = "";
		List list = enumeration.getEnumValues();
		if(list != null) {
			for(int i = 0; i < list.size(); i++) {
				enumValuesStr += list.get(i) + ","; 
			}
			enumValuesStr = enumValuesStr.substring(0, enumValuesStr.length()-1);
		}
		return enumValuesStr;
	}
	
	/**
	 * 修改枚举信息
	 * @param enumerationName 枚举名
	 * @param enumValuesStr 枚举值字符串
	 * @param enumerationId 枚举id
	 * @param siteId 
	 * @param userId
	 * @return message
	 */
	@SuppressWarnings("unchecked")
	public String modifyEnum(String enumerationName, String enumValuesStr, String enumerationId, String siteId, String userId) {
		Enumeration enumeration = enumerationDao.getAndClear(enumerationId);
		
		//保存枚举类别名称
		enumeration.setName(enumerationName);
		//添加枚举值
		if(enumValuesStr.length() > 0) {
			List enumValuesList = new ArrayList();
			//去掉最后一个逗号
			enumValuesStr = enumValuesStr.substring(0, enumValuesStr.length()-1);
			String[] enumValues = enumValuesStr.split(",");
			for(int i = 0; i < enumValues.length; i++) {
				enumValuesList.add(enumValues[i]);
			}
			enumeration.setEnumValues(enumValuesList);
		}
		enumerationDao.update(enumeration);
		
		String enumId = enumeration.getId();
		// 排序枚举的同时，也将模版设置里面的期刊分类名称重新排序
		List list = enumeration.getEnumValues();
		if(!CollectionUtil.isEmpty(list)) {
			// 查找模版单元里面模版单元类别id是t8(即期刊分类)的模版单元
			List<TemplateUnit> unitList = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitCategory", "CategoryId", "t8");
			if(!CollectionUtil.isEmpty(unitList)) {
				String path = "";
				for(int i = 0; i < unitList.size(); i++) {
					String configFile = unitList.get(i).getConfigFile();
					if(!StringUtil.isEmpty(configFile)) {
						path = GlobalConfig.appRealPath + configFile;
						if(FileUtil.isExist(path)) {
							log.debug("path=============================================="+path);
							XmlUtil xmlUtil = XmlUtil.getInstance(path);
							// infoCategory  (  2009092915375476524##aa:::2009092915375476525##分类1:::2009092915375476525##分类2:::2009092915375476525##分类3:::2009092915375476526##bb )
							String infoCategory = xmlUtil.getNodeText("/baize/magazine-category/infoCategory");
							if(!StringUtil.isEmpty(infoCategory)) {
								String[] str = infoCategory.split(":::");
								if(str.length != 0 && !StringUtil.isEmpty(str[0])) {
									// 用来存放和要修改的枚举类别相同的枚举
									String temp = "";
									for(int j = 0; j < str.length; j++) {
										if(str[j].split("##")[0].equals(enumId)) {
											if(temp.equals("")) {
												temp = str[j];
											} else {
												temp += ":::" + str[j];
											}
										}
									}
									String newEnum = "";
									if(!StringUtil.isEmpty(temp)) {
										String[] str1 = temp.split(":::");
										for(int k = 0; k < list.size(); k++) { 
											for(int m = 0; m < str1.length; m++) {
												if(str1[m].split("##")[1].equals(list.get(k).toString())) {
													if(StringUtil.isEmpty(newEnum)) {
														newEnum = str1[m];
													} else {
														newEnum += ":::" + str[m];
													}
												}
											}
										}		
									}
									infoCategory = infoCategory.replaceAll(temp, newEnum);
									xmlUtil.setNodeText("/baize/magazine-category/infoCategory", infoCategory);
									xmlUtil.save();
								}// end if
							}
						} // enf if fileExist
					} // end if (!StringUtil.isEmpty(configFile)
				}
			}
		}
		
		// 写入日志
		String categoryName = "枚举类型->修改";
		systemLogDao.addLogData(categoryName, siteId, userId, enumerationName);
		
		return "修改枚举信息成功";
	}
	
	/**
	 * 查找所有枚举名称字符串
	 * @return allEnumNameStr
	 */
	@SuppressWarnings("unchecked")
	public String findAllEnumNameStr() {
		List list = enumerationDao.findByNamedQuery("findAllEnumNameOfAdd");
		String allEnumNameStr = "";
		for(int i = 0; i < list.size(); i++) {
			allEnumNameStr += "," + String.valueOf(list.get(i)); 
		}
		return allEnumNameStr;
	}
	
	/**
	 * 查找除当前之外所有枚举名称字符串
	 * @param enumerationId 枚举id
	 */
	public String findAllEnumNameStr(String enumerationId) {
		List list = enumerationDao.findByNamedQuery("findAllEnumNameOfModify", "enumerationId", enumerationId);
		String allEnumNameStr = "";
		for(int i = 0; i < list.size(); i++) {
			allEnumNameStr += "," + String.valueOf(list.get(i)); 
		}
		return allEnumNameStr;
	}
	
	/**
	 * 导出枚举
	 * 
	 * @param exportFormatIds
	 *            导出的枚举ids
	 * @param path
	 *            导出枚举的路径
	 * @param siteId
	 * @param userId           
	 * @return message
	 */
	@SuppressWarnings("unchecked")
	public String exportEnumerations(String exportEnumIds, String path, String siteId, String userId) {
		// 创建根节点
		Element root = new Element("list");
		// 根节点添加到文档中
		Document document = new Document(root);

		exportEnumIds = SqlUtil.toSqlString(exportEnumIds);
		// 根据id查询要导出的枚举型
		List<Enumeration> list = enumerationDao.findByDefine("findExportEnumsByIds", "ids", exportEnumIds);
		String categoryName = "枚举类型->导出";
		for (int i = 0; i < list.size(); i++) {
			Enumeration enumeration = list.get(i);

			Element elements = new Element("enumeration");
			// 给enumeration 节点添加id 属性
			elements.setAttribute("id", "" + (i + 1));

			// 给 enumeration 节点添加子节点并赋值；
			String id = "" + enumeration.getId();
			elements.addContent(new Element("id").setText(id));
			
			// 给 enumeration 节点添加子节点并赋值；
			String name = "" + enumeration.getName();
			elements.addContent(new Element("name").setText(name));

			List valueList = enumeration.getEnumValues();
			String enumValues = "";
			if(!CollectionUtil.isEmpty(valueList)){
				for(int j = 0; j < valueList.size(); j++) {
					enumValues = enumValues + valueList.get(j) + ",";
				}
				enumValues = enumValues.substring(0, enumValues.length()-2);
			}
			elements.addContent(new Element("enumValues").setText(enumValues));
			String creator_id = "";
			if (enumeration.getCreator() != null) {
				creator_id = "" + enumeration.getCreator().getId();
			}
			elements.addContent(new Element("creator_id").setText(creator_id));

			// 给父节点list添加user子节点
			root.addContent(elements);
			
			
			// 枚举导出日志
			systemLogDao.addLogData(categoryName, siteId, userId, enumeration.getName());
		}

		XMLOutputter XMLOut = new XMLOutputter();
		// 输出 format.xml 文件
		try {
			XMLOut.output(document, new FileOutputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String message = "导出成功";
		return message;
	}
	
	/**
	 * 导入枚举
	 * 
	 * @param xmlpath
	 *            文件路径
	 * @param siteId
	 * @param creatorId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importEnumerationsXml(String xmlpath, String siteId, String creatorId) {
		String message = "";

		// 使用JDOM首先要指定使用什么解析器,可以给参数false、true
		SAXBuilder builder = new SAXBuilder();
		try {
			// 得到Document，我们以后要进行的所有操作都是对这个Document操作的：
			Document doc = builder.build(new FileInputStream(xmlpath));
			// 得到根元素
			Element enums = doc.getRootElement();
			// 得到元素（节点）的集合：
			List list = enums.getChildren();
			String categoryName = "枚举类型->导入";
			// 循环导入所有枚举型
			for (int i = 0; i < list.size(); i++) {
				// 得到根节点下面的第一个枚举型
				Element enumeration = (Element) list.get(i);
				Enumeration newEnum = new Enumeration();
				
				Element id = enumeration.getChild("id");
				String newId = "" + id.getText();
				String ids = SqlUtil.toSqlString(newId);
				// 根据id查询要导出的枚举型
				List<Enumeration> existList = enumerationDao.findByDefine("findImportEnumsById", "id", ids);
				if(existList != null && existList.size() > 0) {
					return "该枚举型已存在,无需导入";
				}
				newEnum.setId(newId);

				Element name = enumeration.getChild("name");
				String newName = "" + name.getText();
				newEnum.setName(newName);
				
				Element enumValues = enumeration.getChild("enumValues");
				String strOfValues = "" + enumValues.getText();
				String[] str = strOfValues.split(",");
				List valueList = new ArrayList();
				for(int j = 0; j < str.length; j++) {
					valueList.add(str[j]);
				}
				newEnum.setEnumValues(valueList);
				
				User creator = new User();
				creator.setId(creatorId);
				newEnum.setCreator(creator);
				
				enumerationDao.save(newEnum);
				
				// 枚举导入日志
				systemLogDao.addLogData(categoryName, siteId, creatorId, newEnum.getName());
			}
			message = "导入成功";
		} catch (Exception e) {
			message = "导入失败";
		}
		
		return message;
	}
				
				
	
	public EnumerationDao getEnumerationDao() {
		return enumerationDao;
	}

	public void setEnumerationDao(EnumerationDao enumerationDao) {
		this.enumerationDao = enumerationDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

	/**
	 * @param templateUnitDao the templateUnitDao to set
	 */
	public void setTemplateUnitDao(TemplateUnitDao templateUnitDao) {
		this.templateUnitDao = templateUnitDao;
	}

	/**
	 * @param articleAttributeDao the articleAttributeDao to set
	 */
	public void setArticleAttributeDao(ArticleAttributeDao articleAttributeDao) {
		this.articleAttributeDao = articleAttributeDao;
	}


}

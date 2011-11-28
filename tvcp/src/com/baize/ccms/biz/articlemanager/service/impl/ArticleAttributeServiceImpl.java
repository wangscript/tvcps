/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.articlemanager.service.impl;

import java.util.List;

import com.baize.ccms.biz.articlemanager.dao.ArticleAttributeDao;
import com.baize.ccms.biz.articlemanager.dao.ArticleFormatDao;
import com.baize.ccms.biz.articlemanager.dao.EnumerationDao;
import com.baize.ccms.biz.articlemanager.domain.ArticleAttribute;
import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.ccms.biz.articlemanager.domain.Enumeration;
import com.baize.ccms.biz.articlemanager.service.ArticleAttributeService;
import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.BeanUtil;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;
import org.apache.log4j.Logger;

/**
 * <p>标题: 属性数据访问对象</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-30 下午04:35:57
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleAttributeServiceImpl implements ArticleAttributeService {
	
	private final Logger log = Logger.getLogger(ArticleAttributeServiceImpl.class);
	
	/** 注入属性数据访问对象 */
	private ArticleAttributeDao articleAttributeDao;
	
	/** 注入格式数据访问对象 */
	private ArticleFormatDao articleFormatDao;
	
	/** 注入系统数据库日志dao */
	private SystemLogDao systemLogDao;
	
	/** 注入枚举dao**/
	private EnumerationDao enumerationDao;
	

	/**
	 * 添加属性
	 * @param attribute
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	public String addAttribute(ArticleAttribute attribute, String siteId, String sessionID) {
		String infoMessage = "";
		ArticleFormat format = attribute.getArticleFormat();
		// 根据格式id判断属性名称是否相同
		String formateId = format.getId();
		String name = attribute.getAttributeName();
		String[] params = {"formateId", "attributeName"};
		Object[] value = {formateId, name};
		List list = articleAttributeDao.findByNamedQuery("findAttributeByFormateIdAndAttributeName", params, value);
		if(list == null  || list.size() == 0) {
			// 当前格式中当前类型号 如： bool : 2 -> bool2
			int currTypeNum = articleFormatDao.findCurrTypeNumByAttribute(attribute);
			// 1.添加属性
			String fieldName = attribute.getAttributeType() + (++currTypeNum);
			if (currTypeNum > 20) {
				return infoMessage;
			}
			attribute.setFieldName(fieldName);
			articleAttributeDao.save(attribute);
			
			// 2.更新格式中字段集合
			String fields = format.getFields() + "," + attribute.getFieldName();
			format.setFields(fields);
			String field = "curr" + StringUtil.firstUpperCase(attribute.getAttributeType());
			BeanUtil.setFieldValue(format, 
					"com.baize.ccms.biz.articlemanager.domain.ArticleFormat", 
					field, currTypeNum, new Class[]{int.class});
			articleFormatDao.updateFormatFieldsAndCurrNumByAttribute(attribute);
			
			// 3.更新缓存中的字段集合
			
			infoMessage = "添加成功";
		} else {
			infoMessage = "该属性已经存在";
		}
		
		// 写入日志
		String categoryName = "格式管理（属性管理）->添加";
		String param = "[" + name + "]" + infoMessage;
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
		return infoMessage;
	}

	/**
	 * 删除属性
	 * @param attribute
	 * @param siteId
	 * @param sessionID
	 */
	public void deleteAttribute(ArticleAttribute attribute, String siteId, String sessionID) {
		// 1.查询属性
		attribute = articleAttributeDao.getAndClear(attribute.getId());
		ArticleFormat format = attribute.getArticleFormat();
		// 当前格式中当前类型号 
		int currTypeNum = articleFormatDao.findCurrTypeNumByAttribute(attribute);
		
		// 2.删除属性
		articleAttributeDao.delete(attribute);
		
		// 3.更新格式中字段集合
		String fields = StringUtil.replace(format.getFields(), ","+attribute.getFieldName(), "");
		format.setFields(fields);
		
		String field = "curr" + StringUtil.firstUpperCase(attribute.getAttributeType());
		BeanUtil.setFieldValue(format, 
				"com.baize.ccms.biz.articlemanager.domain.ArticleFormat", 
				field, currTypeNum, new Class[]{int.class});
		articleFormatDao.updateFormatFieldsAndCurrNumByAttribute(attribute);
		
		// 3.更新缓存中的字段集合
		
		// 4. 写入日志
		String categoryName = "格式管理（属性管理）->删除";
		String param = attribute.getAttributeName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
	}

	public ArticleAttribute findAttributeById(String id) {
		return articleAttributeDao.getAndClear(id);
	}

	/**
	 * 修改属性
	 * @param attribute
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	public String modifyAttribute(ArticleAttribute attribute, String siteId, String sessionID) {
		// 首先判断名称是否有重复
		String infoMessage = "";
		// 根据格式id判断属性名称是否相同
		String formateId = attribute.getArticleFormat().getId();
		String name = attribute.getAttributeName();
		String[] params = {"formateId", "attributeName"};
		Object[] value = {formateId, name};
		List list = articleAttributeDao.findByNamedQueryAndClear("findAttributeByFormateIdAndAttributeName", params, value);
		if(list == null  || list.size() < 2) {
		//	System.out.println(list.size());
			articleAttributeDao.update(attribute);
			infoMessage = "修改成功";
		} else {
			infoMessage = "修改失败，此名称已存在";
		}
		
		// 4. 写入日志
		String categoryName = "格式管理（属性管理）->修改";
		String param = "[" + attribute.getAttributeName() + "]" + infoMessage;
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
		return infoMessage;
	}

	public Pagination findAttributePage(Pagination pagination, String formatId) {
		return articleAttributeDao.getPagination(pagination, "formatId", formatId);
	}

	/** 查询格式名称
	 * @param formatId 格式Id
	 */
	public String findFormatName(String formatId) {
		String formatName = "";
		List list = articleFormatDao.findByNamedQuery("findFormatNameByFormatId", "formatId", formatId);
		formatName = (String)list.get(0);
		return formatName;
	}
	
	/** 查询枚举信息
	 * return enumInfoStr
	 */
	public String findEnumInfo() {
		String enumInfoStr = "";
		String valueStr = "";
		List list = enumerationDao.findAll();
		for(int i = 0; i < list.size(); i++) {
			valueStr = "";
			Enumeration enumeration = (Enumeration)list.get(i);
			List valuesList = enumeration.getEnumValues();
			if(!CollectionUtil.isEmpty(valuesList)) {
				for(int j = 0; j < valuesList.size(); j++) {
					valueStr += valuesList.get(j) + ",";
				}
				enumInfoStr += enumeration.getId() + "," + enumeration.getName() + ",#" + valueStr + "::";
			}
		}
		return enumInfoStr;
	}
	
	/**
	 * 查找所有的属性名称
	 * @param formatId 格式id
	 * @return
	 */
	public String findAllAttributeNameStr(String formatId) {
		List<ArticleAttribute> list = articleAttributeDao.findByNamedQuery("findAttributeNameStrByFormatId", "formatId", formatId);
		String attributeNameStr = "";
		for(int i = 0; i < list.size(); i++) {
			ArticleAttribute articleAttribute = list.get(i);
			attributeNameStr += articleAttribute.getAttributeName() + ",";
		}
		return attributeNameStr;
	}
	
	/**
	 * 根据格式id查找所有的属性信息
	 * @param formatId  格式id
	 * @return
	 */
	public String findAttributeInfoStr(String formatId) {
		List list = articleAttributeDao.findByNamedQuery("findAttributeInfoByFormatId", "formatId", formatId);
		String attributeInfoStr = "";
		for(int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			attributeInfoStr += obj[0] + "," + obj[1] + ",#"; 
		}
		return attributeInfoStr;
	}
	
	/**
	 * 排序
	 * @param attributeIdStr 属性ids
	 * @return
	 */
	public String modifyAttributeSort(String attributeIdStr) {
		String[] ids = attributeIdStr.split(",");
		for(int i=1; i < ids.length; i++) {
			ArticleAttribute attribute = articleAttributeDao.getAndClear(ids[i]);
			attribute.setOrders(i);
			articleAttributeDao.update(attribute);
		}
		return "排序成功";
	}
	
	/**
	 * 批量删除属性
	 * @param ids 属性id
	 * @return
	 */
	public String deleteAttributesByIds(String ids) {
		ids = SqlUtil.toSqlString(ids);
		articleAttributeDao.deleteByIds(ids);
		return "删除成功";
	}
	
	/**
	 * 通过属性名称查找属性id 
	 * @param formatId 格式id
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public String findDefaultAttrIdsByName(String formatId) {
		String attrIds = "";
		formatId = SqlUtil.toSqlString(formatId);
		String attrName = "'标题','链接地址','是否删除','是否审核','是否发布','创建时间','发布时间','显示时间','审核时间','失效时间','置顶','过滤关键词'";
		String[] str = {"formatId", "attrName"};
		String[] value = {formatId, attrName};
		List list = articleAttributeDao.findByDefine("findDefaultAttrIdsByNames", str, value);
		for(int i = 0; i < list.size(); i++) {
			attrIds += (String) list.get(i) + ",";
		}
		return attrIds;
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
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}
	
	public void setEnumerationDao(EnumerationDao enumerationDao) {
		this.enumerationDao = enumerationDao;
	}
}

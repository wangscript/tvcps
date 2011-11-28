/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.articlemanager.service.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.baize.ccms.biz.articlemanager.dao.ArticleAttributeDao;
import com.baize.ccms.biz.articlemanager.dao.ArticleFormatDao;
import com.baize.ccms.biz.articlemanager.dao.EnumerationDao;
import com.baize.ccms.biz.articlemanager.domain.ArticleAttribute;
import com.baize.ccms.biz.articlemanager.domain.ArticleFormat;
import com.baize.ccms.biz.articlemanager.domain.Enumeration;
import com.baize.ccms.biz.articlemanager.service.ArticleFormatService;
import com.baize.ccms.biz.columnmanager.dao.ColumnDao;
import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.templatemanager.dao.TemplateDao;
import com.baize.ccms.biz.templatemanager.domain.TemplateInstance;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.util.CollectionUtil;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;
import org.apache.log4j.Logger;

/**
 * <p>标题: 格式服务类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author 杨信
 * @version 1.0
 * @since 2009-3-12 下午03:29:00
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class ArticleFormatServiceImpl implements ArticleFormatService {
	
	private Logger log = Logger.getLogger(ArticleFormatServiceImpl.class);

	/** 注入文章格式dao */
	private ArticleFormatDao articleFormatDao;
	
	/** 注入文章属性dao */
	private ArticleAttributeDao articleAttributeDao;
	
	/** 注入栏目dao**/
	private ColumnDao columnDao;
	
	/** 注入系统数据库日志dao */
	private SystemLogDao systemLogDao;
	
	/** 注入模板dao */
	private TemplateDao templateDao;
	
	/** 注入枚举dao*/ 
	private EnumerationDao enumerationDao;
	

	public String addFormat(ArticleFormat format, String siteId, String sessionID) {
		log.debug("default attributes 's size : " + ArticleFormat.ATTRIBUTES_DEFAULT.size());
		String infoMessage = "";
		// 判断格式名称是否重复
		String formatName = format.getName(); 
		List list = articleFormatDao.findByNamedQuery("findFormatByFormatName", "formatName", formatName);
		if(CollectionUtil.isEmpty(list)) {
			format.setId(StringUtil.UUID());
			articleFormatDao.save(format);
			for (ArticleAttribute attribute : ArticleFormat.ATTRIBUTES_DEFAULT) {
				attribute.setArticleFormat(format);
				attribute.setCreateTime(new Date());
				articleAttributeDao.save(attribute);
			}
			// 设置缓存状态
			ArticleFormat.setNeedUpdated(true);
			infoMessage = "添加格式成功";
		} else {
			infoMessage = "添加失败，此格式已经存在";
		}
		
		// 写入日志
		String categoryName = "格式管理->添加";
		String param = "[" + formatName + "]" + infoMessage;
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
		return infoMessage;
	}

	public void deleteFormat(ArticleFormat format) {
		// 1.删除格式对应的属性
		articleAttributeDao.bulkUpdate("deleteAttributeByFormatId", "id", format.getId());
		// 2.删除格式
		articleFormatDao.delete(format); 
		// 设置缓存状态
		ArticleFormat.setNeedUpdated(true);
	}

	/**
	 * 修改格式 
	 * @param format
	 * @param siteId
	 * @param sessionID
	 * @return
	 */
	public String modifyFormat(ArticleFormat format, String siteId, String sessionID) {
		String infoMessage = "";
		ArticleFormat newFormat = articleFormatDao.getAndClear(format.getId());
		if(newFormat.getName().equals(format.getName())) {
			infoMessage = "修改成功";
			newFormat.setDescription(format.getDescription());
			articleFormatDao.update(newFormat);
		} else {
			// 判断格式名称是否重复
			String formatName = format.getName(); 
			List list = articleFormatDao.findByNamedQuery("findFormatByFormatName", "formatName", formatName);
			if(list == null || list.size() == 0) {
				infoMessage = "修改成功";
				newFormat.setName(format.getName());
				newFormat.setDescription(format.getDescription());
				articleFormatDao.update(newFormat);
			} else {
				infoMessage = "修改失败，此格式已经存在";
			}
		}
		
		// 写入日志
		String categoryName = "格式管理->修改";
		String param = "[" +newFormat.getName()+ "]"+ infoMessage ;
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
		// 设置缓存状态
		ArticleFormat.setNeedUpdated(true);
		return infoMessage;
	}
	
	public Pagination findFormatPagination(Pagination pagination) {
		return articleFormatDao.getPagination(pagination);
	}
	
	/**
	 * 批量删除格式
	 * @param ids
	 * @param siteId
	 * @param sessionID
	 * @return 影响的记录数
	 */
	public String deleteFormatByIds(String ids, String siteId, String sessionID) {
		String[] arr = ids.split(",");
		String infoMessage = "";
		
		// 写入日志
		String categoryName = "格式管理->删除";
		String param = "";
		
		for(int i = 0; i < arr.length; i++) {
			List list = columnDao.findByNamedQuery("findColumnByFormatId", "formateId", arr[i]);
			if(list != null && list.size() > 0) {
				infoMessage = "删除失败，格式被引用";
				systemLogDao.addLogData(categoryName, siteId, sessionID, param);
				break;
			}
		}
		if(StringUtil.isEmpty(infoMessage)) {
			ArticleFormat aticleFormat = null;
			for(int i = 0; i < arr.length; i++) {
				aticleFormat = articleFormatDao.getAndClear(arr[i]);
				param = aticleFormat.getName();
				systemLogDao.addLogData(categoryName, siteId, sessionID, param);
			}
			
			String newIds = SqlUtil.toSqlString(ids);
			// 设置缓存状态
			ArticleFormat.setNeedUpdated(true);
			// 1.删除格式对应的属性
			articleAttributeDao.updateByDefine("deleteAttributeByFormatIds", "ids", newIds);
			// 2.删除格式
			articleFormatDao.deleteByIds(newIds);
			infoMessage = "删除格式成功";
		}
		return infoMessage;
	}
	
	/**
	 * 导出格式
	 * 
	 * @param exportFormatIds
	 *            导出的格式ids
	 * @param path
	 *            导出格式的路径
	 * @return message
	 */
	@SuppressWarnings("unchecked")
	public String exportFormats(String exportFormatIds, String path) {
		// 创建根节点
		Element root = new Element("list");
		// 根节点添加到文档中
		Document document = new Document(root);

		exportFormatIds = SqlUtil.toSqlString(exportFormatIds);
		// 根据id查询要导出的文章
		List<ArticleFormat> list = articleFormatDao.findByDefine("findExportFormatsByIds", "ids", exportFormatIds);
		for (int i = 0; i < list.size(); i++) {
			ArticleFormat format = list.get(i);

			Element elements = new Element("format");
			// 给format 节点添加id 属性
			elements.setAttribute("id", "" + (i + 1));

			// 给 format 节点添加子节点并赋值；
			String id = "" + format.getId();
			elements.addContent(new Element("id").setText(id));
			
			String name = "" + format.getName();
			elements.addContent(new Element("name").setText(name));

			String fields = "" + format.getFields();
			elements.addContent(new Element("fields").setText(fields));

			String currInteger = "" + format.getCurrInteger();
			elements.addContent(new Element("currInteger").setText(currInteger));
			
			String currFloat = "" + format.getCurrFloat();
			elements.addContent(new Element("currFloat").setText(currFloat));

			String currText = "" + format.getCurrText();
			elements.addContent(new Element("currText").setText(currText));
			
			String currTextArea = "" + format.getCurrTextArea();
			elements.addContent(new Element("currTextArea").setText(currTextArea));
			
			String currDate = "" + format.getCurrDate();
			elements.addContent(new Element("currDate").setText(currDate));
			
			String currBool = "" + format.getCurrBool();
			elements.addContent(new Element("currBool").setText(currBool));
			
			String currPic = "" + format.getCurrPic();
			elements.addContent(new Element("currPic").setText(currPic));
			
			String currAttach = "" + format.getCurrAttach();
			elements.addContent(new Element("currAttach").setText(currAttach));
			
			String currMedia = "" + format.getCurrMedia();
			elements.addContent(new Element("currMedia").setText(currMedia));
			
			String currEnumeration = "" + format.getCurrEnumeration();
			elements.addContent(new Element("currEnumeration").setText(currEnumeration));
			
			String defaults = "" + format.isDefaults();
			elements.addContent(new Element("defaults").setText(defaults));
			
			String description = "" + format.getDescription();
			elements.addContent(new Element("description").setText(description));
			
			String createTime = "" + format.getCreateTime();
			elements.addContent(new Element("createTime").setText(createTime));
			
			String template_id = "";
			if (format.getTemplate() != null) {
				template_id = "" + format.getTemplate().getId();
			}
			elements.addContent(new Element("template_id").setText(template_id));
			
			String creator_id = "";
			if (format.getCreator() != null) {
				creator_id = "" + format.getCreator().getId();
			}
			elements.addContent(new Element("creator_id").setText(creator_id));

			// 给父节点list添加user子节点
			root.addContent(elements);
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
	 * 导入格式
	 * 
	 * @param xmlpath
	 *            文件路径
	 * @param siteId
	 * @param creatorId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importFormatsXml(String xmlpath, String siteId, String creatorId) {
		String message = "";

		// 使用JDOM首先要指定使用什么解析器,可以给参数false、true
		SAXBuilder builder = new SAXBuilder();
		try {
			// 得到Document，我们以后要进行的所有操作都是对这个Document操作的：
			Document doc = builder.build(new FileInputStream(xmlpath));
			// 得到根元素
			Element formats = doc.getRootElement();
			// 得到元素（节点）的集合：
			List list = formats.getChildren();
			// 循环导入所有格式
			for (int i = 0; i < list.size(); i++) {
				// 得到根节点下面的第一个格式
				Element format = (Element) list.get(i);
				ArticleFormat newFormat = new ArticleFormat();
				// 判断格式中是否包含枚举值
				Element enumeration = format.getChild("currEnumeration");
				String currEnumeration = enumeration.getText();
				if (currEnumeration != null && !currEnumeration.equals("0") && !currEnumeration.equals("")) {
					// 根据id查询要导出的枚举型
					int enumNum = StringUtil.parseInt(currEnumeration);
					String fieldName = "'enumeration" + enumNum + "'";
					List<ArticleAttribute> existList0 = null;
					for(int k = 1; k <= enumNum; k++) {
						existList0 = articleAttributeDao.findByDefine("findExportEnumsByFieldName", "fieldName", fieldName);
						if(existList0 != null && existList0.size() > 0) {
							ArticleAttribute tempArt = existList0.get(0);
							Enumeration tempEnum = enumerationDao.getAndClear(tempArt.getEnumeration().getId());
							if(tempEnum == null) {
								return "改格式中包含枚举型："+tempArt.getAttributeName()+",请先将其导入";
							}
						}
					}
				}
				
				Element id = format.getChild("id");
				String newId = "" + id.getText();
				
				Element name = format.getChild("name");
				String newName = "" + name.getText();
				newFormat.setName(newName);
				
				// 根据id查询要导入的格式
//				List<ArticleFormat> existList = articleFormatDao.findByDefine("findImportFormatsById", "id", SqlUtil.toSqlString(newId));
//				newFormat.setId(newId);
				
				List existList = articleFormatDao.findByNamedQuery("findFormatByFormatName", "formatName", newName);
				if(!CollectionUtil.isEmpty(existList)) {
					return "该格式已存在,无需导入";
				}
				
				newFormat.setId(StringUtil.UUID());
				
				Element fields = format.getChild("fields");
				String newFields = "" + fields.getText();
				newFormat.setFields(newFields);

				Element currInteger = format.getChild("currInteger");
				int newCurrInteger = StringUtil.parseInt(currInteger.getText());
				newFormat.setCurrInteger(newCurrInteger);
				
				Element currFloat = format.getChild("currFloat");
				int newCurrFloat = StringUtil.parseInt(currFloat.getText());
				newFormat.setCurrFloat(newCurrFloat);
				
				Element currText = format.getChild("currText");
				int newCurrText = StringUtil.parseInt(currText.getText());
				newFormat.setCurrText(newCurrText);
				
				Element currTextArea = format.getChild("currTextArea");
				int newCurrTextArea = StringUtil.parseInt(currTextArea.getText());
				newFormat.setCurrTextArea(newCurrTextArea);
				
				Element currDate = format.getChild("currDate");
				int newCurrDate = StringUtil.parseInt(currDate.getText());
				newFormat.setCurrDate(newCurrDate);
				
				Element currBool = format.getChild("currBool");
				int newCurrBool = StringUtil.parseInt(currBool.getText());
				newFormat.setCurrBool(newCurrBool);
				
				Element currPic = format.getChild("currPic");
				int newCurrPic = StringUtil.parseInt(currPic.getText());
				newFormat.setCurrPic(newCurrPic);
				
				Element currAttach = format.getChild("currAttach");
				int newCurrAttach = StringUtil.parseInt(currAttach.getText());
				newFormat.setCurrAttach(newCurrAttach);
				
				Element currMedia = format.getChild("currMedia");
				int newCurrMedia = StringUtil.parseInt(currMedia.getText());
				newFormat.setCurrMedia(newCurrMedia);
				
				Element defaults = format.getChild("defaults");
				String newdefaults = defaults.getText();
				if (newdefaults.equals("true")) {
					newFormat.setDefaults(true);
				} else {
					newFormat.setDefaults(false);
				}
				
				Element description = format.getChild("description");
				String newDescription = description.getText();
				newFormat.setDescription(newDescription);
				
				Element template_id = format.getChild("template_id");
				String newTemplate_id = template_id.getText();
				if(newTemplate_id != null && !newTemplate_id.equals("")) {
					TemplateInstance templateInstance = new TemplateInstance();
					templateInstance.setId(newTemplate_id);
					newFormat.setTemplate(templateInstance);
				} else {
					newFormat.setTemplate(null);
				}
				User creator = new User();
				creator.setId(creatorId);
				newFormat.setCreator(creator);
				newFormat.setCreateTime(new Date());
				// 保存格式
//				message = addFormat(newFormat, siteId, creatorId);
//				public String addFormat(ArticleFormat format, String siteId, String sessionID)
				
				log.debug("default attributes 's size : " + ArticleFormat.ATTRIBUTES_DEFAULT.size());
				String infoMessage = "";
				// 判断格式名称是否重复
				String formatName = newFormat.getName(); 
				List list1 = articleFormatDao.findByNamedQuery("findFormatByFormatName", "formatName", formatName);
				if(CollectionUtil.isEmpty(list1)) {
					newFormat.setId(StringUtil.UUID());
					articleFormatDao.save(newFormat);
					for (ArticleAttribute attribute : ArticleFormat.ATTRIBUTES_DEFAULT) {
						attribute.setArticleFormat(newFormat);
						attribute.setCreateTime(new Date());
						articleAttributeDao.save(attribute);
					}
					// 设置缓存状态
					ArticleFormat.setNeedUpdated(true);
					infoMessage = "添加格式成功";
				} else {
					infoMessage = "添加失败，此格式已经存在";
				}
				
				// 写入日志
				String categoryName = "格式管理->添加";
				String param = "[" + formatName + "]" + infoMessage;
				systemLogDao.addLogData(categoryName, siteId, creatorId, param);
				
				
				if(message.equals("添加失败，此格式已经存在")) {
					return message;
				}
			}
			message = "导入成功";
		} catch (Exception e) {
			message = "导入失败";
		}
		return message;
	}
		


	public ArticleFormat findFormatById(String id) {
		return articleFormatDao.getAndClear(id);
	}

	/**
	 * @param articleFormatDao the articleFormatDao to set
	 */
	public void setArticleFormatDao(ArticleFormatDao articleFormatDao) {
		this.articleFormatDao = articleFormatDao;
	}

	/**
	 * @param articleAttributeDao the articleAttributeDao to set
	 */
	public void setArticleAttributeDao(ArticleAttributeDao articleAttributeDao) {
		this.articleAttributeDao = articleAttributeDao;
	}

	/**
	 * @return the columnDao
	 */
	public ColumnDao getColumnDao() {
		return columnDao;
	}

	/**
	 * @param columnDao the columnDao to set
	 */
	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	/**
	 * @param logManagerDao the logManagerDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public void setEnumerationDao(EnumerationDao enumerationDao) {
		this.enumerationDao = enumerationDao;
	}
}

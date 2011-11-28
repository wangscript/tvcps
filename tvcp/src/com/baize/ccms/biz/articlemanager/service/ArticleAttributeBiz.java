/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
*/
package com.baize.ccms.biz.articlemanager.service;

import java.util.Map;

import com.baize.ccms.biz.articlemanager.domain.ArticleAttribute;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>标题: 属性业务类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 文章管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-3-31 上午09:42:19
 * @history（历次修订内容、修订人、修订时间等）
 */
public class ArticleAttributeBiz extends BaseService {
	
	/** 属性文章服务类 */
	private ArticleAttributeService articleAttributeService;

	@SuppressWarnings("unchecked")
	@Override
	public void performTask(RequestEvent requestEvent, ResponseEvent responseEvent)
			throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object,Object> requestParam = requestEvent.getReqMapParam();
		Map<Object,Object> responseParam = responseEvent.getRespMapParam();
		String siteId = requestEvent.getSiteid();
		String sessionID = requestEvent.getSessionID();
		
		//是不是默认格式的属性
		String fromDefault = (String) requestParam.get("fromDefault");
		responseParam.put("fromDefault", fromDefault);
		
		// 分页显示属性
		if (dealMethod.equals("")) {
			log.info("显示属性列表");
			String formatId = (String) requestParam.get("formatId");
			//默认的属性ids
			String defaultAttrIds = articleAttributeService.findDefaultAttrIdsByName(formatId);
			
			String formatName = articleAttributeService.findFormatName(formatId);
			Pagination pagination = (Pagination) requestParam.get("pagination");
			pagination = articleAttributeService.findAttributePage(pagination, formatId);
			
			responseParam.put("pagination", pagination);
			responseParam.put("formatName", formatName);
			responseParam.put("defaultAttrIds", defaultAttrIds);
			
		// 添加属性	
		} else if (dealMethod.equals("add")) {
			log.info("添加属性");
			ArticleAttribute attribute = (ArticleAttribute) requestParam.get("attribute");
			String infoMessage = "";
			infoMessage = articleAttributeService.addAttribute(attribute, siteId, sessionID);
			responseParam.put("infoMessage", infoMessage);
		
		// 删除属性	
		}  else if (dealMethod.equals("delete")) {
			log.info("删除属性");
			ArticleAttribute attribute = (ArticleAttribute) requestParam.get("attribute");
			articleAttributeService.deleteAttribute(attribute, siteId, sessionID);
			
		// 修改属性	
		} else if (dealMethod.equals("modify")) {
			ArticleAttribute attribute = (ArticleAttribute) requestParam.get("attribute");
			ArticleAttribute oldAttr = articleAttributeService.findAttributeById(attribute.getId());
			String infoMessage = "";
			oldAttr.setAttributeName(attribute.getAttributeName());
			oldAttr.setTip(attribute.getTip());
			oldAttr.setShowed(attribute.isShowed());
			oldAttr.setModified(attribute.isModified());
			oldAttr.setEmpty(attribute.isEmpty());
			infoMessage = articleAttributeService.modifyAttribute(oldAttr, siteId, sessionID);
			responseParam.put("infoMessage", infoMessage);
			
		// 显示属性detail	
		} else if (dealMethod.equals("detail")) {
			String formatId = (String) requestParam.get("formatId");
			String formatFields = (String) requestParam.get("formatFields");
			String formatName = (String) requestParam.get("formatName");
			//枚举信息字符串,形式为：id1,name1,#value11,value12,value13::id2,name2#value21,value22...
			String enumInfoStr = articleAttributeService.findEnumInfo();
			//属性名称
			String attributeNameStr = articleAttributeService.findAllAttributeNameStr(formatId);
			responseParam.put("formatId", formatId);
			responseParam.put("formatFields", formatFields);
			responseParam.put("formatName", formatName);
			responseParam.put("enumInfoStr", enumInfoStr);
			responseParam.put("attributeNameStr", attributeNameStr);
			
			// 获得排序属性信息
		} else if (dealMethod.equals("findSortAttribute")) {
			String formatId = (String) requestParam.get("formatId");
			String attributeInfoStr = articleAttributeService.findAttributeInfoStr(formatId);
			responseParam.put("attributeInfoStr", attributeInfoStr);
			responseParam.put("formatId", formatId);
			
			// 排序
		} else if (dealMethod.equals("sort")) {
			String formatId = (String) requestParam.get("formatId");
			String attributeIdStr = (String) requestParam.get("attributeIdStr");
			String message = articleAttributeService.modifyAttributeSort(attributeIdStr);
			responseParam.put("message", message);
			responseParam.put("formatId", formatId);
			
			// 批量删除
		} else if (dealMethod.equals("deleteAttributes")) {
			String formatId = (String) requestParam.get("formatId");
			String ids = (String) requestParam.get("ids");
			String message = articleAttributeService.deleteAttributesByIds(ids);
			responseParam.put("message", message);
			responseParam.put("formatId", formatId);
		}
	}

	@Override
	public ResponseEvent validateData(RequestEvent reuqest) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param articleAttributeService the articleAttributeService to set
	 */
	public void setArticleAttributeService(
			ArticleAttributeService articleAttributeService) {
		this.articleAttributeService = articleAttributeService;
	}

}

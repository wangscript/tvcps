/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.guestbookmanager.service;

import java.util.Map;

import com.baize.ccms.plugin.guestbookmanager.domain.GuestBookSensitiveWord;
import com.baize.ccms.plugin.guestbookmanager.web.form.GuestBookForm;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.service.BaseService;
import com.baize.common.core.web.event.RequestEvent;
import com.baize.common.core.web.event.ResponseEvent;

/**
 * <p>
 * 标题: —— 留言板业务处理类最高接口
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: 插件管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-5-18 下午04:20:28
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class GuestBookBiz extends BaseService {
	/** 注入敏感词serivce */
	private GuestBookService guestBookService;
	/** 注入属性设置service */
	private GuestBookAttributeService attributeService;
	/** 注入分发权限设置service */
	private GuestBookAutorityService autorityService;

	@Override
	public void performTask(RequestEvent requestEvent,
			ResponseEvent responseEvent) throws Exception {
		String dealMethod = requestEvent.getDealMethod();
		Map<Object, Object> requestParam = requestEvent.getReqMapParam();
		Map<Object, Object> responseParam = responseEvent.getRespMapParam();
		// 网站id
		String siteId = requestEvent.getSiteid();
		GuestBookForm form = (GuestBookForm) requestParam.get("guestBookForm");
		String sessionId = requestEvent.getSessionID();
		// 敏感词分页
		if (dealMethod.equals("wordList")) {
			Pagination p = (Pagination) requestParam.get("pagination");
			Pagination pa = guestBookService.getWordPagination(p, siteId);
			if (pa.getData().size() == 0) {
				pa.currPage = pa.currPage - 1;
				pa = guestBookService.getWordPagination(p, siteId);
			}
			responseParam.put("pagination", pa);
		
		// 新增敏感词
		} else if (dealMethod.equals("addWord")) {
			String word = form.getWord().getSensitiveWord();
			String mess = guestBookService.addSensitiveWord(word, siteId);
			form.setInfoMessage(mess);
		
		// 显示添加敏感词页面
		} else if (dealMethod.equals("showWord")) {
			String wordName = guestBookService
					.getAllSensitiveWordBySiteId(siteId);// 获取所有敏感词，以便在页面判断是否存在
			form.setSensitiveword(wordName);
		
		// 编辑敏感词
		} else if (dealMethod.equals("editWord")) {
			String ids = form.getIds();
			String sensitiveWord = form.getWord().getSensitiveWord();
			String mess = guestBookService.modifySensitiveWord(ids,
					sensitiveWord, siteId);
			form.setInfoMessage(mess);
		
		// 删除敏感词
		} else if (dealMethod.equals("deleteWord")) {
			guestBookService.deleteSensitiveWord(form.getIds());
			
		// 敏感词详细
		} else if (dealMethod.equals("wordetail")) {
			String wordName = guestBookService
					.getAllSensitiveWordBySiteId(siteId);// 获取所有敏感词，以便在页面判断是否存在
			form.setSensitiveword(wordName);
			GuestBookSensitiveWord w = guestBookService
					.getSensitiveWordById(form.getIds());
			form.setWord(w);
			// 属性设置详细
		} else if (dealMethod.equals("propertyDetail")) {
			attributeService.getAttributeSetForm(form, siteId);
			// 保存修改的属性设置
		} else if (dealMethod.equals("savepropertySet")) {
			String mess = attributeService.writerAttribute(siteId, form, sessionId);
			form.setInfoMessage(mess);
			
		// 获取样式
		} else if (dealMethod.equals("getStyle")) {
			String content = attributeService.getGuestBookStyle(siteId);
			form.setStyleContent(content);
			
		// 保存样式
		} else if (dealMethod.equals("saveStyle")) {
			String mess = attributeService.setGuestBookStyle(form
					.getStyleContent(), siteId);
			form.setInfoMessage(mess);
		
		// 已分发权限的用户列表
		} else if (dealMethod.equals("autorityList")) {
			Pagination p = (Pagination) requestParam.get("pagination");
			Pagination p1 = autorityService.getAutorityUser(p);
			if (null != p1.getData()) {
				if (p1.getData().size() == 0) {
					p1.currPage = p1.currPage - 1;
					p1 = autorityService.getAutorityUser(p);
				}
			}
			responseParam.put("pagination", p1);
		
		// 显示要被分发的用户列表
		} else if (dealMethod.equals("autorityDetail")) {
			Pagination p = (Pagination) requestParam.get("pagination");
			Pagination p1 = autorityService.getAllUser(p);
			if (p1.getData().size() == 0) {
				p1.currPage = p1.currPage - 1;
				p1 = autorityService.getAllUser(p);
			}
			responseParam.put("pagination", p1);
		
		// 获取选择的用户
		} else if (dealMethod.equals("getSelectUser")) {
			autorityService.addAutoriyUser(form.getIds());

		// 删除分发用户
		} else if (dealMethod.equals("deleteAutority")) {
			String ids = form.getIds();
			autorityService.deleteAutoriyUser(ids);
			
		// 发布留言本目录	
		} else if(dealMethod.equals("publishGuestBook")){
			guestBookService.publishGuestBookDir(siteId);
		}
		responseParam.put("guestBookForm", form);
	}

	@Override
	public ResponseEvent validateData(RequestEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param guestBookService
	 *            the guestBookService to set
	 */
	public void setGuestBookService(GuestBookService guestBookService) {
		this.guestBookService = guestBookService;
	}

	/**
	 * @param attributeService
	 *            the attributeService to set
	 */
	public void setAttributeService(GuestBookAttributeService attributeService) {
		this.attributeService = attributeService;
	}

	/**
	 * @param autorityService
	 *            the autorityService to set
	 */
	public void setAutorityService(GuestBookAutorityService autorityService) {
		this.autorityService = autorityService;
	}
}

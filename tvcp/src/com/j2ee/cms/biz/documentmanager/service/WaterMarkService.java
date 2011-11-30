/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.service;

import java.util.List;

import com.j2ee.cms.biz.documentmanager.domain.PictureWatermark;
import com.j2ee.cms.biz.documentmanager.domain.TextWatermark;
import com.j2ee.cms.biz.documentmanager.domain.Watermark;
import com.j2ee.cms.biz.documentmanager.web.form.WaterMarkForm;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CPS
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-1 下午03:27:17
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public interface WaterMarkService {
	/**
	 * 添加水印文字的信息
	 * 
	 * @param waterMark
	 *            水印信息
	 */
	String addWaterFont(TextWatermark waterMark, String siteId, String createId);

	/**
	 * 修改水印文字
	 * 
	 * @param waterMark
	 *            水印信息
	 */

	void modifyWaterFont(WaterMarkForm form, String id);

	/**
	 * 根据id查询水印文字信息
	 * 
	 * @param id
	 * @return 水印信息
	 */
	TextWatermark findByWaterMarkId(String id);

	/**
	 * 
	 * 按照文档中属于的类别的id查询并分页显示
	 * 
	 * @param pagination
	 *            分页对象
	 * @param siteId
	 *            网站ID
	 * @param isSystemManager
	 *            是否是系统管理员以上级别
	 * @return 返回一个文档的分页对象
	 */
	Pagination findDocumentListByCategoryId(Pagination pagination,
			String siteId, boolean isSystemManager);

	/**
	 * 查询文字列表
	 * 
	 * @param form
	 * 
	 * @return list集合
	 */
	List findByWaterName(String siteId, boolean isSystemManager);

	/**
	 * 查询图片列表
	 * 
	 * @param form
	 * 
	 * @return list集合
	 */
	List findByPicPath(String siteId, boolean isSystemManager);

	/**
	 * 上传图片
	 * 
	 * @param markForm
	 *            要上传的对象
	 * @param siteId
	 *            网站ID
	 * @return 上传的成功信息
	 * 
	 */
	String uploadImg(WaterMarkForm markForm, String siteId, String createId);

	/**
	 * 
	 * @param pic
	 *            图片对象
	 * @param siteId
	 *            网站ID
	 * @param createId
	 *            创建人
	 */
	void addPic(PictureWatermark pic, String siteId, String createId,
			String fileName);

	/**
	 * 根据ID删除文字水印信息
	 * 
	 * @param ids
	 *            根据ID删除信息
	 * @return
	 */
	String deleteWaterFontMark(String ids);

	/**
	 * 根据ID查找水印信息
	 * 
	 * @param id
	 * @return
	 */
	PictureWatermark findByPicId(String id);

	/**
	 * 根据ID删除图片水印
	 * 
	 * @param ids
	 * @return 
	 */
	String deletePicWaterMark(String ids);

	/**
	 * 查询默认选中的文字
	 * @param siteid
	 * 网站ID
	 * @param isSystemManager
	 * 是否为管理员
	 * @return 选中的文字对象
	 */
	Watermark findByFontDefault(String siteid, boolean isSystemManager);

	/**
	 * 根据ID修改图片默认设置
	 * @param id
	 * 要修改的ID
	 * @param siteId
	 * 网站ID
	 * @param isSystemManager
	 * 是否为管理员
	 * @return
	 */
	String modifyWaterDefault(String id,String siteId, boolean isSystemManager);
	/**
	 * 查找文字水印默认设置为true的
	 * @param siteId
	 * @param isSystemManager
	 * @return
	 */
	TextWatermark findByTextWater(String siteId, boolean isSystemManager);

	/**
	 * 查找文字水印中默认设置为true的
	 * 
	 * @return
	 */
	PictureWatermark findByPicWater(String siteId, boolean isSystemManager);


	/**
	 * 获取字体
	 * 
	 * @param id
	 *            字体ID
	 * @return 字体
	 */
	String getFont(String id);

	/**
	 * 根据ID获取所有水印图片,用ajax，所以用字符拼结
	 * 
	 * @param id
	 *            水印ID
	 * @return 图片信息
	 */
	String findByPicAll(String id);

	/**
	 * 根据id获取所有文字水印,用ajax，所以用字符拼结
	 * 
	 * @param id
	 *            文字id
	 * @return 文字信息
	 */
	String findByWaterAll(String id);
}

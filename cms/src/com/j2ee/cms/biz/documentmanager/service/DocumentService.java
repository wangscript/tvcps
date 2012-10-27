/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.service;

import java.util.List;

import com.j2ee.cms.biz.documentmanager.domain.Document;
import com.j2ee.cms.biz.documentmanager.domain.DocumentCategory;
import com.j2ee.cms.biz.documentmanager.web.form.DocumentForm;
import com.j2ee.cms.common.core.dao.Pagination;

/**
 * <p>
 * 标题: 文档的service
 * </p>
 * <p>
 * 描述: 文档的接口，用于存放方法
 * </p>
 * <p>
 * 模块: 文档管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * 
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-23 上午09:46:19
 * @history（历次修订内容、修订人、修订时间等） 
 */
 
public interface DocumentService {

	/**
	 *添加数据
	 * 
	 * @param document
	 *            添加的文档对象
	 */
	void addDocument(Document document);
	/**
	 * 修改数据
	 * 
	 * @param document
	 *            要修改的文档对象
	 */
	void modifyDocument(Document document);

	/**
	 * 按照文档的id查询文档信息
	 * 
	 * @prame id 根据栏目id获取整条数据
	 * @return Document 返回一个文档的对象
	 */
	Document findDocumentById(String id);

	/**
	 * 按照文档中属于的类别的id查询并分页显示  管理员
	 * 
	 * @param pagination
	 *            类别分页对象
	 * @param id
	 *            要查询的文档id
	 * @param value
	 *            要查询的id的值
	 * @return Pagination 返回一个文档的分页对象
	 */
	Pagination findDocumentListByCategoryId(Pagination pagination, String id,
			Object value);
	/**
	 * 按照文档中属于的类别的id查询并分页显示 普通用户
	 * 
	 * @param pagination
	 *            类别分页对象
	 * @param id
	 *            要查询的文档id
	 * @param value
	 *            要查询的id的值
	 * @param siteid
	 * 				当前网站的Id
	 * @return Pagination 返回一个文档的分页对象
	 */
	Pagination findDocumentListByCategoryIdBySiteId(Pagination pagination, String id,
			Object value,String siteid);
	/**
	 * 获得图片分页的数据
	 * 
	 * @param pagination
	 *            传递一个文档中图片的分页对象
	 * @return Pagination 返回一个分页对象
	 */
	Pagination proccessPicturePagination(Pagination pagination,String pathName);
	/**
	 * 图片缩放
	 * @param form 图片缩放
	 * @return
	 */
	 String scaleImg(DocumentForm form,String pathName);
	/**
	 * 处理图片的上传操作
	 * 
	 * @param sessionID
	 *            当前登录用户的id值
	 * @param siteId
	 *            当前网站的id值
	 * @param nodeid
	 *            节点id
	 * @param url
	 *            图片地址
	 * @param form
	 *            文档表单
	 * @param documentService
	 *            文档业务对象
	 * @param categoryName 
	 * 			  文档类别
	 * @return 返回是否上传成功
	 */
	String proccessPictureUpload(String sessionID, String siteId,
			String nodeid, String url, DocumentForm form,
			DocumentService documentService);

	/**
	 * 上传flash的处理
	 * 
	 * @param sessionID
	 *            当前登录用户的id值
	 * @param siteId
	 *            当前网站的id值
	 * @param nodeid
	 *            节点id
	 * @param url
	 *            flash地址
	 * @param form
	 *            文档表单
	 * @param documentService
	 *            文档业务对象
	 * @return 返回是否上传成功
	 */
	String proccessFlashUpload(String sessionID, String siteId, String nodeid,
			String url, DocumentForm form, DocumentService documentServic);

	/**
	 * 添加附件的处理
	 * 
	 * @param sessionID
	 *            当前登录用户的id值
	 * @param siteId
	 *            当前网站的id值
	 * @param nodeid
	 *            节点id
	 * @param url
	 *            附件地址
	 * @param form
	 *            文档表单
	 * @param documentService
	 *            文档业务对象
	 * @return 返回是否上传成功
	 */
	String proccessAttachmentUpload(String sessionID, String siteId,
			String nodeid, String url, DocumentForm form,
			DocumentService documentService);

	/**
	 * 删除文档，flash、附件,js
	 * 
	 * @param ids
	 *            要删除的文档的id，此处的文档包括图片、flash、附件
	 */
	String deleteDocument(String ids, String siteId, String sessionID, String categoryName);
	/**
	 * 删除图片操作
	 * @param ids 图片ID
	 * @param siteId 网站ID
	 * @param sessionID 用户ID 
	 * @param categoryName 类别名称
	 * @return 信息
	 */
	String deletePic(String ids, String siteId, String sessionID, String categoryName);
	/**
	 * 获取文档所有的图片
	 * 
	 * @param pagination
	 *            图片分页对象
	 * @return 返回所有图片的分页对象
	 */
	Pagination findDocumentList(Pagination pagination);

	/**
	 * 查找附件类别
	 * 
	 * @param siteid
	 *            网站id
	 * @return 返回该网站下的附件类别
	 */
	List<DocumentCategory> findAttachmentCategory(String siteid);

	/**
	 * 查找图片类别
	 * 
	 * @param siteid
	 *            网站id
	 * @return 返回该网站下的图片类别
	 */
	List<DocumentCategory> findPictureCategory(String siteid);

	/**
	 * 查找Flash类别
	 * 
	 * @param siteid
	 *            网站id
	 * @return 返回该网站下的Flash类别
	 */
	List<DocumentCategory> findFlashCategory(String siteid);

	/**
	 * 按照类别id查找附件的信息
	 * 
	 * @param categoryId
	 *            附件类别的id
	 * @return 返回附件的列表
	 */
	List<Document> findAttachmentListByCategoryId(String categoryId);

	/**
	 * 获取节点名称
	 * 
	 * @param nodeId
	 *            节点id
	 * @return nodeNameStr
	 */
	public String findNodeNameByNodeId(String nodeId);
	
	/**
	 * 添加js脚本文件的处理
	 * 
	 * @param sessionID
	 *            当前登录用户的id值
	 * @param siteId
	 *            当前网站的id值
	 * @param nodeid
	 *            节点id
	 * @param url
	 *            脚本文件地址
	 * @param form
	 *            文档表单
	 * @param documentService
	 *            文档业务对象
	 * @return 返回是否上传成功
	 */
	String proccessJsFileUpload(String sessionID, String siteId,
			String nodeid, String url, DocumentForm form,
			DocumentService documentService);
	
	/**
	 * 查找脚本类别
	 * 
	 * @param siteid
	 *            网站id
	 * @return 返回该网站下的脚本类别
	 */
	List<DocumentCategory> findJsCategory(String siteid);
	
	/**
	 * 按照类别id查找脚本的信息
	 * 
	 * @param categoryId
	 *            脚本类别的id
	 * @return 返回脚本的列表
	 */
	List<Document> findJsListByCategoryId(String categoryId);
	/**
	 * 读取JS文件
	 * @param jsPath js文件路径
	 * @return js文件内容
	 */
	String editJsFile(String jsPath);
	/**
	 * 保存编辑后的脚本
	 * @param path js文件路径
	 * @param content js文件内容
	 * @return 是否修改成功
	 */
	 String saveJsFile(String path,String content,String siteId,String updateId);
}

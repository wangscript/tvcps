/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.service;

import com.j2ee.cms.biz.templatemanager.domain.Template;
import com.j2ee.cms.biz.templatemanager.web.form.TemplateForm;
import com.j2ee.cms.common.core.dao.Pagination;


/**
 * <p>标题: 模板服务类</p>
 * <p>描述: 主要是模块中的模板的一些功能的实现的调用，这里是接口</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午03:27:30
 * @history（历次修订内容、修订人、修订时间等）
 */
public interface TemplateService {
	
	/**
	 * 添加模板
	 * @param template   模板对象
	 */
	void addTemplate(Template template, String siteId, String sesionID);
	
	/**
	 * 获取模板分页
	 * @param pagination     模板分页对象
	 * @param nodeid         要分页的模板类别id
	 * @return               返回模板分页对象
	 */
	Pagination templatePage(Pagination pagination, String nodeid, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin);
	
	/**
	 * 处理模板的粘贴
	 * @param id              要粘贴的位置
	 * @param pasteIds        要复制的模板
	 * @param siteId		  网站id
	 * @param url             地址
	 * @param sessionID		  创建者id
	 * @param tempalteService 模板业务对象
	 * @return                返回是否成功信息        
	 */
	String pasteTemplate(String id, String pasteIds, String siteId, StringBuffer url, String sessionID, TemplateService tempalteService);
	
	/**
	 * 按照模板id查找模板
	 * @param templateId   模板id
	 * @param siteId
	 * @param sessionID
	 * @param categoryName
	 * @return            返回模板对象
	 */
	Template  getTemplateByTemplateId(String templateId, String siteId, String sessionID, String categoryName);
	
	/**
	 * 处理模板导入
	 * @param form             要导入的模板表单
	 * @param url              获得请求的地址
	 * @param siteId           网站id
	 * @param nodeId           节点id
	 * @param sessionID		   创建者id
	 * @param templateService  模板业务对象
 	 * @return                 返回是否成功信息
	 */
	String templateImport(TemplateForm form, StringBuffer url, String siteId, String nodeId, String sessionID, TemplateService templateService);
	
	/**
	 * 处理模板删除
	 * @param ids               要删除的id
	 * @return                  返回是否删除成功
	 */
	String deleteTemplate(String ids, String siteId, String sessonID);
	
	/**
	 * 处理模板修改的操作
	 * @param form             模板表单
	 * @param siteId           网站id
	 * @param sessionID        修改者id
	 * @param nodeId           模板类别id
	 * @return				   返回是否修改成功                        
	 */
	String modifyTemplate(TemplateForm form, String siteId, String sessionID, String nodeId);
	
	/**
	 * 查找要下载的模板的路径
	 * @param template     要下载的模板的实例
	 * @return             返回要下载的模板的路径
	 */
	String findTemplateLocalPath(Template template);
	/**
	 * 编辑模板
	 * @param tempPath 模板路径
	 * @return 编辑是否成功
	 */
	String editTempFile(String tempPath);
	/**
	 * 保存编辑后的模板
	 * @param path 模板路径
	 * @param content 模板内容
	 * @return 是否保存成功
	 */
	String saveTempFile(String path,String content,String updateId,String templeteId,String siteId);
}

/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.biz.templatemanager.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.dao.TemplateDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateInstanceDao;
import com.j2ee.cms.biz.templatemanager.dao.TemplateUnitDao;
import com.j2ee.cms.biz.templatemanager.domain.Template;
import com.j2ee.cms.biz.templatemanager.domain.TemplateCategory;
import com.j2ee.cms.biz.templatemanager.domain.TemplateInstance;
import com.j2ee.cms.biz.templatemanager.domain.TemplateUnit;
import com.j2ee.cms.biz.templatemanager.service.TemplateService;
import com.j2ee.cms.biz.templatemanager.web.form.TemplateForm;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.domain.UploadedFile;
import com.j2ee.cms.common.core.util.CollectionUtil;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.WebClientUtil;

/**
 * <p>标题: 模板服务类</p>
 * <p>描述: 这里主要是模板服务的一些功能具体实现</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since 2009-4-9 下午03:31:16
 * @history（历次修订内容、修订人、修订时间等）
 */
public class TemplateServiceImpl implements TemplateService {

	private final Logger log = Logger.getLogger(TemplateServiceImpl.class);
	
	/** 注入模板数据访问对象 */
	private TemplateDao templateDao;
	/** 注入模板实例对象 */
	private TemplateInstanceDao templateInstanceDao;
	/** 注入日志dao*/
	private SystemLogDao systemLogDao;
	/** 注入模版单元*/
	private TemplateUnitDao templateUnitDao;
	
	/**
	 * 添加模板
	 * @param template 模板对象
	 */
	public void addTemplate(Template template, String siteId, String sessionID) {
		templateDao.save(template);
		
		// 写入日志文件
		String categoryName = "模板管理（模板管理）->导入";
		String param = template.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
	}
	
	/**
	 * 按照模板id查找模板
	 * @param templateId     模板id
	 * @return               返回模板对象
	 */ 
	public Template getTemplateByTemplateId(String templateId, String siteId, String sessionID, String categoryName) {
		Template template =  templateDao.getAndClear(templateId);
		
		// 写入日志
		String param = template.getName();
		systemLogDao.addLogData(categoryName, siteId, sessionID, param);
		
		return template;
	}
	
	/**
	 * 获取模板分页
	 * @param pagination  模板分页对象
	 * @param nodeid      要分页的模板类别id
	 * @return            返回模板分页对象
	 */
    public Pagination templatePage(Pagination pagination, String nodeId, String siteId, String sessionID, boolean isUpSystemAdmin, boolean isSiteAdmin) {
    	// 系统管理员及以上
    	if(isUpSystemAdmin) {
//    		pagination =  templateDao.getPagination(pagination, "templateCategoryId", nodeId);
    		String[] params = {"templateCategoryId" , "siteId"};
    		Object[] values = {nodeId, siteId};
    		pagination =  templateDao.getPagination(pagination, params, values);
    		
    	//	网站管理员
    	} else if(isSiteAdmin) {
    		String[] params = {"templateCategoryId" , "siteId"};
    		Object[] values = {nodeId, siteId};
    		pagination =  templateDao.getPagination(pagination, params, values);

    	//	普通用户
    	} else {
    		String[] params = {"templateCategoryId" , "siteId", "creatorId"};
    		Object[] values = {nodeId, siteId, sessionID};
    		pagination =  templateDao.getPagination(pagination, params, values);
    	}
    	List list = pagination.getData();
    	if(list != null && list.size() > 0) {
    		for(int i = 0 ; i < list.size(); i++) {
	        	Object[] object = (Object[]) list.get(i);
	        	// 获得模板实例名称
	        	String name = (String) object[1];
	        	//获取模板实例地址
	        	String url = (String)object[6];
	        	object[1] = "<a href=\"" + url + "\" target=\"_blank\" style=\"text-decoration:none;\">" + name + "</a>";
	        }
    	}
    	return pagination;
	}
    
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
	public String templateImport(TemplateForm form, StringBuffer url, String siteid, String nodeId, String sessionID, TemplateService templateService) {
		String creatorid = sessionID;
		String localPath = SiteResource.getTemplateDir(siteid, false);
		String infoMessage = "";
		List<UploadedFile> list;
		try {
			list = WebClientUtil.uploadFile(form, localPath, 1024*1024*10);
			if(list != null && list.size() != 0) {
				infoMessage = form.getInfoMessage();
				log.debug("infoMessage================"+infoMessage);
				if(infoMessage.equals("附件上传成功") || infoMessage.equals("")) {
					/*// 检查上传的文件是否是以zip结尾或者文件大小是否大于10M
					for(int t = 0; t < list.size(); t++) {
						UploadedFile uploadedFile = (UploadedFile) list.get(t);
						// 判断上传的文件是否符合大小
						if(StringUtil.isEmpty(uploadedFile.getFileName()) || uploadedFile.getFileName() == "") {
							infoMessage = "导入模板大小不能大于10M";
							return infoMessage;
						}
						// 判断上传文件的类型
						if(uploadedFile.getSuffixName().equals("zip") || uploadedFile.getSuffixName().equals("ZIP")) {
							continue;
						} else {
							infoMessage = "导入模板必须是zip或者ZIP文件";
							// 删除上传的zip文件
							FileUtil.delete(localPath + "/" + uploadedFile.getChangefileName());
							return infoMessage;
						}
					}*/
					// 获取文件的访问地址
					String url2 = StringUtil.delete(url.toString(), "/template.do");
					// 设置模板类别
					TemplateCategory templateCategory = new TemplateCategory();
					templateCategory.setId(nodeId);
					User creator = new User();
					creator.setId(creatorid);
					// 循环处理模板上传
					for(int i = 0; i < list.size(); i++) {
						UploadedFile uploadedFile = (UploadedFile) list.get(i);
						long currentDate = System.currentTimeMillis();
						String newFolder = localPath + File.separator + currentDate;
						// 新建一个目录
						FileUtil.makeDirs(newFolder); 
						// 解压zip文件
						FileUtil.unZip(localPath + File.separator + uploadedFile.getChangefileName(), newFolder);
						File file = new File(newFolder);
						File[] files = file.listFiles();
						// 判断文件中是否包含htm、html、HTM、HTML这些文件中的一个
						int m = 0;
						int temp = 0;
						for(m = 0; m < files.length; m++) {
							if(FileUtil.isHtml(files[m].getName())) { 
								temp = 1;
							}else if(!files[m].isDirectory()) {
								if(!FileUtil.isHtml(files[m].getName())) {
									infoMessage = "打包后上传的图片或者css文件要在压缩包的images目录下,请看模板导入说明";
									return infoMessage;
								}
							}else{
								if(files[m].isDirectory()) {
									String dir = files[m].getName();
									if(!dir.equals("images")) {
										infoMessage = "打包后上传的图片或者css文件要在压缩包的images目录下,请看模板导入说明";
										return infoMessage;
									}
								}
							}
						}
						// 上传文件中不包含htm、html、HTM、HTML中任何一个
						if(temp == 0) {
							infoMessage = "上传文件压缩包中不包含htm、html、HTM、HTML中任何一个，请看模板导入说明";
							FileUtil.delete(localPath + File.separator + uploadedFile.getChangefileName());
							FileUtil.delete(newFolder);
							break;
						}
						// 保存数据库
						for(int j = 0; j < files.length; j++) {
							// 如果上传的文件包括.html 或者 .htm 则保存到数据库
							if(FileUtil.isHtml(files[j].getName())) {
								// 用于存取文件名字
								String name = "";
								name = files[j].getName();
								String path = newFolder + File.separator + StringUtil.strLChar(uploadedFile.getChangefileName(), '.') + ".htm";
								File newfile = new File(path);
								// 重新命名html文件名
								files[j].renameTo(newfile);
								// 添加模板
								Template template = new Template();
								template.setName(StringUtil.strLChar(name, '.'));
								// 获得解压后的模板名字
								String fileName = StringUtil.strLChar(uploadedFile.getChangefileName(), '.') + ".htm";
								template.setFileName(fileName);
								template.setLocalPath(File.separator+"release"+File.separator+"site" + siteid + File.separator+"template"+File.separator + currentDate + File.separator + fileName);
								template.setUrl(url2 + File.separator+"release"+File.separator+"site" + siteid + File.separator+"template"+File.separator + currentDate + File.separator + fileName);
								template.setCreateTime(new Date());
								template.setTemplateCategory(templateCategory);
								template.setCreator(creator);
								Site site = new Site();
								site.setId(siteid);
								template.setSite(site);
								templateService.addTemplate(template, siteid, sessionID);
								break;
							}
						}
						infoMessage = "上传成功";
						// 删除上传的zip文件
						FileUtil.delete(localPath + File.separator + uploadedFile.getChangefileName());
					}
				//模板超过规定大小(10兆)
				}else {
					infoMessage = form.getInfoMessage();
					log.debug("infoMessage========"+infoMessage);
					infoMessage = "您上传的模板大于10M，上传失败,请看模板导入说明";
				}
			} else {
				infoMessage = form.getInfoMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
			infoMessage = "上传文件失败,请看模板导入说明";
		}
		return infoMessage;
	}
    
	/**
	 * 处理模板删除
	 * @param ids     要删除的id
	 * @return        返回是否删除成功
	 */
	public String deleteTemplate(String ids, String siteId, String sessionID) {
		String infoMessage = "";
		String[] strid = ids.split(",");
		// 判断模板是否有模板实例
		for(String templateId : strid) {
			List list = templateInstanceDao.findByNamedQuery("findTemplateInstanceByTemplateId", "templateId", templateId);
			if(list == null || list.size() == 0) {
				infoMessage = "删除模板成功";
			} else {
				infoMessage = "该模板下存在实例，删除失败";
				return infoMessage;
			}
		}
		for(String templateId : strid) {
			Template template =  templateDao.getAndClear(templateId);
			
			// 写入日志文件
			String categoryName = "模板管理（模板管理）->删除";
			String param = template.getName();
			systemLogDao.addLogData(categoryName, siteId, sessionID, param);
			
			// 调用删除
			templateDao.deleteByKey(templateId);
			String localPath = GlobalConfig.appRealPath + template.getLocalPath();
			// 将路径以"/"隔开
			String[] str = localPath.split(File.separator);
			// 获得最后的目录
			String folder =File.separator + str[str.length-1];
			String newFolder = StringUtil.delete(localPath, folder);
			File file = new File(newFolder);
			if(file.exists()) {
				// 调用模板的删除方法
				FileUtil.delete(newFolder);
			}
		}
		return infoMessage;
	} 
    
    /**
	 * 处理模板的粘贴
	 * @param id              要粘贴的模板位置
	 * @param pasteIds        要复制的模板
	 * @parm  siteId          网站id
	 * @param url			  地址
	 * @param sessionID		  创建者id
	 * @param tempalteService 模板业务对象
	 */
	public String pasteTemplate(String id, String pasteIds, String siteid, StringBuffer url, String creatorid, TemplateService templateService) {
		String infoMessage = "";
		String url2 = StringUtil.delete(url.toString(), "/template.do");
		String[] ids = pasteIds.split(",");
		// 设置模板类别
		TemplateCategory templateCategory = new TemplateCategory();
		templateCategory.setId(id);
		// 设置创建者
		User creator = new User();
		creator.setId(creatorid);
		Site site = new Site();
		site.setId(siteid);
		Template pasteTemplate = new Template();
		Template template = new Template();
		// 循环处理粘贴
		for(String pasteid : ids) {
			long currentDate = System.currentTimeMillis();
			template = templateDao.getAndClear(pasteid); 
			// 获得本地路径
			String path = GlobalConfig.appRealPath + template.getLocalPath();
			File file1 = new File(path);
			if(!file1.exists()) {
				infoMessage = "要复制的模板文件不存在";
				return infoMessage;
			}
			String str[] = path.split(File.separator);
			String zipFilePath = StringUtil.delete(path, File.separator + str[str.length-2] + File.separator + str[str.length-1]);
			// 获得要复制的模板的目录
			String param = StringUtil.delete(path, File.separator + str[str.length-1]);
			// 新建一个目录
			String newFolder = zipFilePath + File.separator + currentDate;
			FileUtil.makeDirs(newFolder);
			// 压缩要复制的目录
			FileUtil.zipDirectory(param, zipFilePath + File.separator + currentDate + ".zip");
			// 解压要复制的zip包
			FileUtil.unZip(zipFilePath + File.separator + currentDate + ".zip", newFolder);
			// 删除压缩的zip包
			FileUtil.delete(zipFilePath + File.separator + currentDate + ".zip");
			File file = new File(newFolder);
			// 获取解压后的文件
			File[] files = file.listFiles();
			for(int i = 0; i < files.length; i++) {
				// 获得复制的html文件
				if(FileUtil.isHtml(files[i].getName())) {
					String suffix = StringUtil.strRChar(files[i].getName(), '.');
					File newFile = new File(newFolder + File.separator + currentDate + "." + suffix);
					files[i].renameTo(newFile); 
					// 模板实例
					pasteTemplate.setName(template.getName());
					pasteTemplate.setFileName(currentDate + "." + suffix);
					pasteTemplate.setLocalPath(File.separator+"release"+File.separator+"site" + siteid + File.separator+"template"+File.separator + currentDate + File.separator + currentDate + "." + suffix);
					pasteTemplate.setUrl(url2 + File.separator+"release"+File.separator+"site" + siteid + File.separator+"template"+File.separator + currentDate + File.separator + currentDate + "." + suffix);
					pasteTemplate.setCreateTime(new Date());
					pasteTemplate.setCreator(creator);
					pasteTemplate.setTemplateCategory(templateCategory);
					pasteTemplate.setSite(site);
					templateService.addTemplate(pasteTemplate, siteid, creatorid);
					
					// 写入日志文件
					String categoryName = "模板管理（模板管理）->复制";
					String param1 = template.getName();
					systemLogDao.addLogData(categoryName, siteid, creatorid, param1);
					
					infoMessage = "粘贴成功";
					break;
				} else {
					infoMessage = "粘贴失败,不存在html文件";
				}
			}
		}
		return infoMessage;
	}
	
	/**
	 * 处理模板修改的操作
	 * @param form             模板表单
	 * @param siteId           网站id
	 * @param sessionID        修改者id
	 * @param nodeId           模板类别id
	 * @return				   返回是否修改成功                        
	 */
	public String modifyTemplate(TemplateForm form, String siteid, String sessionID, String nodeid) {
		String infoMessage = "";
		Template template = form.getTemplate();
		String templateId = template.getId();
		int temp = 0;
		// 全路径
		String templatePath = SiteResource.getTemplateDir(siteid, false);
		try {
			List<UploadedFile> list = WebClientUtil.uploadFile(form, templatePath, 1024*1024); // 10M
			if(form.getInfoMessage().equals("附件上传成功")) {
				if(!CollectionUtil.isEmpty(list)) {
					long currentDate = System.currentTimeMillis();
					// 判断上传的文件名是否与要更新的文件名一致
					UploadedFile uploadedFile = list.get(0);
					String name = uploadedFile.getFileName();
					String str[] = template.getLocalPath().split(File.separator);
					// 获得模版html所在的文件目录 :  release/..../template/12345678
			    	String localPath = StringUtil.delete(template.getLocalPath(), File.separator + str[str.length-1]);
			    	// 模版图片路径
					String imagesPath = GlobalConfig.appRealPath + localPath  +File.separator+"images";
					// 如果图片路径不存在
					if(!FileUtil.isExist(imagesPath)) {
						FileUtil.makeDirs(imagesPath);
					}
					
					// 建立一个临时存放上传文件路径，等处理完模版后，在操作中将上传的文件整理放入临时文件中，最后一起拷贝到模版实例里面去
					String instanceImagesPath = GlobalConfig.appRealPath + localPath + File.separator+"temp"+File.separator+"images";
					String instancePath = GlobalConfig.appRealPath + localPath + File.separator+"temp";
					FileUtil.makeDirs(instanceImagesPath);
					
					// 上传的文件名字以zip或者ZIP结尾
				    if(name != null && !name.equals("") && !name.equals("null") && FileUtil.isZip(name)) {
				    	// 建立一个临时目录，存放上传文件
				    	String tempPath = templatePath + File.separator + currentDate;
				    	FileUtil.makeDirs(tempPath);
				    	
						// 先解压上传的zip文件
						FileUtil.unZip(templatePath +File.separator+ uploadedFile.getChangefileName(), tempPath);
						File file = new File(tempPath);
						File[] files = file.listFiles();
						// 设置变量用于判断是否存在html文件
						int a = 0;
						// 判断解压后的文件夹中是否存在html文件，如果存在则判断名字是否与要更新的模板名字一致
						for(int j = 0; j < files.length; j++) {
							if(FileUtil.isHtml(files[j].getName())) {
								String fileName = StringUtil.strLChar(files[j].getName(), '.');
							    if(!fileName.equals(template.getName())) {
								   infoMessage = "上传的文件与原来文件里的文件名不一致，无法更新原文件里的文件内容！";
								   FileUtil.delete(tempPath);
								   FileUtil.delete(templatePath + File.separator + uploadedFile.getChangefileName());
								   FileUtil.delete(instancePath);
								   return infoMessage;
							    } else {
								   a = 1;
							    }
							    break;
							}
						}
						// 如果解压的文件夹中存在html文件
						if(a == 1) {
							for(int i = 0; i < files.length; i++) {
								String filePath = tempPath + File.separator + files[i].getName();
								// 更新html文件
								if(FileUtil.isHtml(files[i].getName())) {
									// 删除将要更新的html文件
							    	FileUtil.delete(GlobalConfig.appRealPath + template.getLocalPath());
									// 将上传的html文件拷贝到要更新的目录下面
									FileUtil.copy(filePath, GlobalConfig.appRealPath +  localPath);
									// 旧文件名
							    	File oldfile = new File(GlobalConfig.appRealPath + localPath + File.separator + files[i].getName());
							    	// 新文件名
							    	File newFile = new File(GlobalConfig.appRealPath + template.getLocalPath()); 
							    	if(newFile.exists()){
							    		newFile.delete();
							    	}
							    	// 重命名文件
							    	oldfile.renameTo(newFile);
							    	
							    	FileUtil.copy(filePath, instancePath);
													    	
								// 更新非html文件
								} else {
									// 如果不是目录直接拷贝到images或者css下面
									if(!files[i].isDirectory()) {
										FileUtil.copy(filePath, imagesPath);
										
										FileUtil.copy(filePath, instanceImagesPath);
									} else {
										FileUtil.copy(filePath, imagesPath, false);
										
										FileUtil.copy(filePath, instanceImagesPath, false);
									}
							    }
						    }
							temp = 1;
					    	
						// 解压后的文件不包含htm等文件则把zip包上传上去并解压到images目录下
						} else 	if(a == 0) {
							for(int j = 0; j < files.length; j++) {
								if(files[j].isDirectory()) {
									FileUtil.copy(tempPath + File.separator + files[j].getName(), imagesPath , false);
									
									FileUtil.copy(tempPath + File.separator + files[j].getName(), instanceImagesPath, false);
								} else {
									FileUtil.copy(tempPath + File.separator + files[j].getName(), imagesPath);
									
									FileUtil.copy(tempPath + File.separator + files[j].getName(), instanceImagesPath);
								}
							}
							temp = 1;
						}
						// 删除解压的目录
					    FileUtil.delete(tempPath);
					    
					// 上传的文件以htm或者html或者HTM或者HTML结尾
					} else if(name != null && !name.equals("") && !name.equals("null") && FileUtil.isHtml(name)) {
					    String fileName = StringUtil.strLChar(name, '.'); 
					    // 如果上传的html文件名字与要更新的模板文件名字一致
						if(fileName.equals(template.getName())) {
							// 删除将要更新的html文件
					    	FileUtil.delete(GlobalConfig.appRealPath + template.getLocalPath());
					    	// 将上传的html文件拷贝到要更新的目录下面
					    	FileUtil.copy(templatePath +File.separator+ uploadedFile.getChangefileName(), GlobalConfig.appRealPath + localPath);
					    	// 旧文件名
					    	File file = new File(GlobalConfig.appRealPath + localPath + File.separator + uploadedFile.getChangefileName());
					    	// 新文件名
					    	File newFile = new File(GlobalConfig.appRealPath + template.getLocalPath()); 
					    	if(newFile.exists()){
					    		newFile.delete();
					    	}
					    	// 重命名文件
					    	file.renameTo(newFile);
					    	temp = 1;
					    	FileUtil.copy(templatePath +File.separator+ uploadedFile.getChangefileName(), instancePath);
					     } else {
					    	 infoMessage = "模板文件名字不一致";
					    	 // 删除上传的文件
							 FileUtil.delete(templatePath +File.separator+ uploadedFile.getChangefileName());
							 FileUtil.delete(instancePath);
					    	 return infoMessage;
					     }
						
					// 上传的不是zip、html文件, 直接上传到images下面
					} else {
						FileUtil.copy(templatePath +File.separator+ uploadedFile.getChangefileName(), imagesPath);
						File file = new File(imagesPath + File.separator + uploadedFile.getChangefileName());
						File file1 = new File(imagesPath + File.separator + uploadedFile.getFileName());
						if(file1.exists()){
							file1.delete();
						}
						file.renameTo(file1);
						temp = 1;
						// 处理模版实例
						FileUtil.copy(imagesPath + File.separator + uploadedFile.getFileName(), instanceImagesPath);
					}
					
					// 判断是否有html文件,先处理html文件的同步问题
					File files = new File(instancePath);
					File[] fileList = files.listFiles();
					for(int i = 0; i < fileList.length; i++) {
						if(FileUtil.isHtml(fileList[i].getName())) {
							String newHtml = FileUtil.read(instancePath + File.separator + fileList[i].getName());
							this.modifyTempalteInstance(template, newHtml);
							break;
						}
					}
					List<TemplateInstance> templateInstanceList = templateInstanceDao.findByNamedQuery("findTemplateInstanceByTemplateId", "templateId", templateId);
			    	if(!CollectionUtil.isEmpty(templateInstanceList)) {
						// 循环替换模版内容(替换所有模版实例)
			    		for(int n = 0; n < templateInstanceList.size(); n++) {
			    			String[] str2 = templateInstanceList.get(n).getLocalPath().split("/");
			    			String inPath = StringUtil.delete(templateInstanceList.get(n).getLocalPath(), str2[str2.length-1]);
			    			String images = GlobalConfig.appRealPath + inPath + File.separator+"images";
			    			if(!FileUtil.isExist(images)) {
								FileUtil.makeDirs(images);
							}
			    			FileUtil.copy(instanceImagesPath, images, false);
			    		}
			    	}
			    	// 删除上传的文件
					FileUtil.delete(templatePath +File.separator+ uploadedFile.getChangefileName());
			    	// 删除临时模版实例目录
					FileUtil.delete(instancePath);
					
					// 更新数据库		
					if(temp == 1) {
				    	this.modifyTemplate(templateId, nodeid, sessionID);
				    	infoMessage = "更新成功";
					}
					
				} else {
					infoMessage = form.getInfoMessage();
				}
			} else {
				infoMessage = form.getInfoMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
			//infoMessage = "上传出现异常";
		}
		
		// 写入日志文件
		String categoryName = "模板管理（模板管理）->更新";
		String param = template.getName();
		systemLogDao.addLogData(categoryName, siteid, sessionID, param);
		
		log.debug("infoMessage==============="+infoMessage);
		return infoMessage;
	}
	
	/**
	 * 替换模版实例
	 * @param template
	 * @param newHtml
	 */
	private void modifyTempalteInstance(Template template, String newHtml) {
		String templateId = template.getId();
		List<TemplateInstance> templateInstanceList = templateInstanceDao.findByNamedQuery("findTemplateInstanceByTemplateId", "templateId", templateId);
    	if(!CollectionUtil.isEmpty(templateInstanceList)) {
    		TemplateInstance templateInstance = templateInstanceList.get(0);
			
			//1. 判断原来的html文件内容是否与现在上传的html内容是否相等(若相等，则不改动，若不等则改动实例)								
			String unitRegex = TemplateUnit.REGEX_ORIGINAL;
			Pattern unitPatn = Pattern.compile(unitRegex);
			Matcher unitMatcher = unitPatn.matcher(newHtml);
			String unitTextRegex = TemplateUnit.REGEX_SPAN;
			Pattern unitTextPatn = Pattern.compile(unitTextRegex);
			String unitStr = "";
			String unitName = "";
			while (unitMatcher.find()) {
				unitStr = unitMatcher.group(1);
				Matcher textMatcher = unitTextPatn.matcher(unitStr);
				while (textMatcher.find()) {
					unitName += "," + textMatcher.group(2).trim();
				}
			}
			if(!StringUtil.isEmpty(unitName)) {
				unitName = unitName.replaceFirst(",", "");
			}
			String names = SqlUtil.toSqlString(unitName);
			String[] params = {"unitNames", "templateInstanceId"};
			String[] values = {names, "'"+templateInstance.getId()+"'"};
			List<TemplateUnit> list1 = templateUnitDao.findByDefine("findSameTemplateUnitByTemplateInstanceId", params, values);
			String[] str1 = unitName.split(",");
			List templist = new ArrayList();
			for(int t = 0; t < str1.length; t++) {
				templist.add(str1[t]);
			}
			for(int j = 0; j < list1.size(); j++) {
				for(int m = 0; m < templist.size(); m++) {
					if(templist.get(m).toString().equals(list1.get(j).getName())) {
						templist.remove(m);
						break;
					}
				}
			}
			// 删除原来模版单元里面不需要的单元
    		String[] params1 = {"unitNames", "templateId"};
			String[] values1 = {names, "'"+template.getId()+"'"};
			log.debug("names=============="+names);
    		List<TemplateUnit> list2 = templateUnitDao.findByDefine("findDifferentTemplateUnitByTemplateId", params1, values1);
    		if(!CollectionUtil.isEmpty(list2)) {
    			String ids = "";
    			for(int k = 0; k < list2.size(); k++) {
    				ids += "," + list2.get(k).getId();
    			}
    			log.debug("deletedIds================"+ids);
    			ids = ids.replaceFirst(",", "");
    			ids = SqlUtil.toSqlString(ids);
    			templateUnitDao.deleteByIds(ids);
    		}
			// 循环替换模版内容(替换所有模版实例)
    		for(int n = 0; n < templateInstanceList.size(); n++) {
    			String outFile = GlobalConfig.appRealPath + templateInstanceList.get(n).getLocalPath();
    			String[] str2 = templateInstanceList.get(n).getLocalPath().split("/");
    			String configDir = StringUtil.delete(templateInstanceList.get(n).getLocalPath(), str2[str2.length-1]) + "conf"; 
    			this.replaceUpdateTemplate(newHtml, outFile, configDir, templateInstanceList.get(n), templist);
    		}
    	}
		
	}
	
	/**
	 * 替换模板的html文件为系统可设置的模板
	 * @param inFile 模板对应的html文件
	 * @param outFile 替换后的html文件位置 
	 * @param configDir 单元配置路径
	 * @param templateInstance 模板实例
	 */
	private void replaceUpdateTemplate(String html, String outFile, String configDir, TemplateInstance templateInstance, List templist) {
		StringBuilder buf = new StringBuilder();
		String pre = "<!--{%[";
		String middleBegin = "]--><div class=\"_unitcls\" style=\"border:1px dashed red;cursor:pointer;font:12px italic;color:#FF0000;HEIGHT\" onclick=\"openSetWin(";
		String middleEnd = ");\">";
		String suffix = "</div><!--%}-->";
		StringBuffer sb = new StringBuffer();
		String unitRegex = TemplateUnit.REGEX_ORIGINAL;
		String unitTextRegex = TemplateUnit.REGEX_SPAN;//"<\\s*span.*\\s*>([^<]*)</\\s*span\\s*>";
		Pattern unitPatn = Pattern.compile(unitRegex);
		Pattern unitTextPatn = Pattern.compile(unitTextRegex);
		Matcher unitMatcher = unitPatn.matcher(html);
		String unitStr = "";
		String unitName = "";
		String unitHeight = "";
		while (unitMatcher.find()) {
			// 添加单元到数据库
			TemplateUnit unit = new TemplateUnit();
			unitStr = unitMatcher.group(1);
			String middleStr = middleBegin;
			Matcher textMatcher = unitTextPatn.matcher(unitStr);
			while (textMatcher.find()) {
				unitHeight = textMatcher.group(1).trim();
				middleStr = middleStr.replaceAll("HEIGHT", unitHeight);
				unitName = textMatcher.group(2).trim();
			}
			log.debug("unitName==============="+unitName);
			int temp = 0;
			for(int i = 0; i < templist.size(); i++) {
				if(templist.get(i).toString().equals(unitName)) {
					unit.setName(unitName);
					unit.setTemplateInstance(templateInstance);
					unit.setConfigDir(configDir);
					unit.setSite(templateInstance.getSite());
					templateUnitDao.saveAndClear(unit);
					temp = 1;
					break;
				}
			}
			if(temp == 0) {
				String[] params = {"unitName", "templateInstanceId"};
				Object[] values = {unitName, templateInstance.getId()};
				List list = templateUnitDao.findByNamedQuery("findTemplateUnitByUnitNameAndTemplateInstanceId", params, values);
				if(!CollectionUtil.isEmpty(list)) {        
					unit = (TemplateUnit) list.get(0);
				}
			}
			// 向div中添加单元id
			middleStr = middleStr.replaceFirst("<div", "<div uid=\""+unit.getId()+"\"");
			// 替换单元脚本
			unitMatcher.appendReplacement(sb, 
					buf.append(pre)
					.append(unit.getId())
					.append(middleStr)
					.append("'"+unit.getId()+"'")
					.append(",'0'")
					.append(middleEnd)
					.append("{" + unitName + "}->未设")
					.append(suffix)
					.toString());
			buf.delete(0, buf.length());
		}
		unitMatcher.appendTail(sb);
		FileUtil.write(outFile, sb.toString());
	}
	
	
	/**
	 * 更新数据库
	 * @param template          要更新的模板
	 * @param nodeid			模板类别id
	 * @param updatorid         修改者id
	 * @param creatorid         模板创建者id
	 * @param createDate        模板创建日期
	 */
	private void modifyTemplate(String templateId, String nodeid, String updatorid) {
		Template template = templateDao.getAndNonClear(templateId);
		template.setUpdateTime(new Date());
	   	User updator = new User();
		updator.setId(updatorid);
		template.setUpdator(updator);
	   	templateDao.update(template);
	}
	
	/**
	 * 查找要下载的模板的路径
	 * @param template     要下载的模板的实例
	 * @return             返回要下载的模板的路径
	 */
	public String findTemplateLocalPath(Template template) {
		String localPath = "";
		String str[] = template.getLocalPath().split(File.separator);
		// 获得模板所在的目录名
		String folder = GlobalConfig.appRealPath + StringUtil.delete(template.getLocalPath(), File.separator + str[str.length-1]); 
		// 获得压缩后的包名
		String newFolder = GlobalConfig.appRealPath + StringUtil.delete(template.getLocalPath(), File.separator + str[str.length-2] + File.separator + str[str.length-1]);
		localPath = newFolder + File.separator + System.currentTimeMillis() + ".zip";
		// 压缩该目录
		FileUtil.zipDirectory(folder, localPath);
		log.debug("======="+folder);
		log.debug("----------"+localPath);
		/*
			String name = template.getName();
			String htmlPath = GlobalConfig.appRealPath + template.getLocalPath();
			String suffixName = str[str.length-1];
			String newPath = folder + File.separator + name + "." + suffixName;
			File file = new File(htmlPath);
			File newFile = new File(newPath);
			// 下载时还原下载文件的文件名（变成导入时的文件名）
			file.renameTo(newFile);
			// 压缩该目录
			FileUtil.zipDirectory(folder, localPath);
			// 下载完后将文件的名称还原，改为可以浏览的文件名
			newFile.renameTo(file);
		*/
		return localPath;
	}
	/**
	 * 编辑模板内容后更新数据库 .
	 * @param template          要更新的模板
	 * @param updatorid         修改者id
	 */
	private void modifyTemplate(String templateId,String updatorid) {
		Template template = templateDao.getAndNonClear(templateId);
		template.setUpdateTime(new Date());
	   	User updator = new User();
		updator.setId(updatorid);
		template.setUpdator(updator);
	   	templateDao.update(template);
	}
	
	public String saveTempFile(String path,String content,String templeteId,String updateId,String siteId){
		String temppath = GlobalConfig.appRealPath + path;
		String mess = outFile(new File(temppath),content);//修改文件
		//开始更新模板实例
		Template template = new Template();
		template.setId(templeteId);
		this.modifyTempalteInstance(template, content);//修改模板实例
		modifyTemplate(templeteId,updateId);//修改数据库中更新的用户和时间
		
		// 写入日志文件
		String categoryName = "模板管理（模板管理）->编辑";
		String param =path.substring(path.lastIndexOf(File.separator)+1,path.length());
		systemLogDao.addLogData(categoryName, siteId, updateId, param);
		
		return mess;
	}
	/**
	 * 写一个文件
	 * 
	 * @param fileIn
	 *            文件路径
	 * @param content
	 *            要先写入的内容
	 */
	public static String outFile(File fileIn, String content) {
		String mess = "";
		OutputStream fos = null;
		try {
			if(!fileIn.exists()){
				fileIn.createNewFile();
				byte[] b = content.getBytes("utf-8");
				fos =new FileOutputStream(fileIn);
				for (int i = 0; i < b.length; i++) {
					fos.write(b[i]);
				}
				mess = "你的文件丢失了，系统为你重新创建了文件并已经保存";
			}
			byte[] bytOut = content.getBytes("utf-8");
			fos = new FileOutputStream(fileIn);
			for (int i = 0; i < bytOut.length; i++) {
				fos.write((int) bytOut[i]);
			}
			mess = "保存成功";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mess;

	}
	public String editTempFile(String tempPath){
		String mess = "";
		String path = GlobalConfig.appRealPath + tempPath;
		if (!FileUtil.isExist(path)) {
			mess = "没有找到该文件";
			return mess;
		}
		try {
			InputStream is = new FileInputStream(path);
			mess = readToBuffer(is).toString();
		} catch (FileNotFoundException e) {
			log.error("文件没有找到" + e);
			mess = null;
		}
		return mess;
	}
	/**
	 * 读出一个文件 .
	 * 
	 * @param is
	 *            文件流
	 * @return StringBuffer
	 * @throws UnsupportedEncodingException
	 */
	public final StringBuffer readToBuffer(final InputStream is) {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String line;
			line = reader.readLine();
			while (line != null) { // 如果 line 为空说明读完了
				sb.append(line + "\n");
				line = reader.readLine(); // 读取下一行
			}
			is.close();
		} catch (UnsupportedEncodingException e) {
			log.error("转码失败" + e);
			return null;
		} catch (IOException e) {
			log.error("文件读取失败" + e);
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.debug("关闭失败！！！");
					e.printStackTrace();
				}
			}
		}
		return sb;
	}
	/**
	 * @param templateDao the templateDao to set
	 */
	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	/**
	 * @return the templateInstanceDao
	 */
	public TemplateInstanceDao getTemplateInstanceDao() {
		return templateInstanceDao;
	}

	/**
	 * @param templateInstanceDao the templateInstanceDao to set
	 */
	public void setTemplateInstanceDao(TemplateInstanceDao templateInstanceDao) {
		this.templateInstanceDao = templateInstanceDao;
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

}

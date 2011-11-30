/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.documentmanager.dao.PicWaterMarkDao;
import com.j2ee.cms.biz.documentmanager.dao.WaterMarkDao;
import com.j2ee.cms.biz.documentmanager.dao.impl.WaterMarkDaoImpl;
import com.j2ee.cms.biz.documentmanager.domain.Document;
import com.j2ee.cms.biz.documentmanager.domain.PictureWatermark;
import com.j2ee.cms.biz.documentmanager.domain.TextWatermark;
import com.j2ee.cms.biz.documentmanager.domain.Watermark;
import com.j2ee.cms.biz.documentmanager.service.WaterMarkService;
import com.j2ee.cms.biz.documentmanager.web.form.WaterMarkForm;
import com.j2ee.cms.biz.sitemanager.dao.SiteDao;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.templatemanager.domain.Template;
import com.j2ee.cms.biz.templatemanager.domain.TemplateCategory;
import com.j2ee.cms.biz.templatemanager.service.TemplateService;
import com.j2ee.cms.biz.templatemanager.web.form.TemplateForm;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.sys.GlobalConfig;
import com.j2ee.cms.sys.SiteResource;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.domain.UploadedFile;
import com.j2ee.cms.common.core.util.FileUtil;
import com.j2ee.cms.common.core.util.IDFactory;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;
import com.j2ee.cms.common.core.web.WebClientUtil;

/**
 * <p>
 * 标题: —— 要求能简洁地表达出类的功能和职责
 * </p>
 * <p>
 * 描述: —— 简要描述类的职责、实现方式、使用注意事项等
 * </p>
 * <p>
 * 模块: CPS水印模块
 * </p>
 * <p>
 * 版权: Copyright (c) 2009  
 * </p>
 * <p>
 * 网址：http://www.j2ee.cmsweb.com
 * 
 * @author 曹名科
 * @version 1.0
 * @since 2009-9-1 下午03:25:52
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class WaterMarkServiceImpl implements WaterMarkService {
	/** 日志. */
	private final Logger log = Logger.getLogger(WaterMarkDaoImpl.class);
	/** 注入水印dao. */
	private WaterMarkDao waterMarkDao;
	/** 图片水印dao. */
	private PicWaterMarkDao picwaterMarkDao;
	/** 注入日志dao. */
	private SystemLogDao systemLogDao;
	/** 注入网站dao **/
	private SiteDao siteDao;

	public void setPicwaterMarkDao(PicWaterMarkDao picwaterMarkDao) {
		this.picwaterMarkDao = picwaterMarkDao;
	}

	public void setWaterMarkDao(WaterMarkDao waterMarkDao) {
		this.waterMarkDao = waterMarkDao;
	}

	public String findSiteParentId() {
		List list = siteDao.findByNamedQuery("findSiteParentId");
		if (list != null && list.size() > 0)
			return list.get(0).toString();
		return null;
	}

	public String addWaterFont(TextWatermark waterMark, String siteId,
			String createId) {
		String mess = "";
		if (waterMark != null) {
			// 设置创建者
			User creator = new User();
			creator.setId(createId);
			waterMark.setCreator(creator);
			// 设置网站
			Site site = new Site();
			site.setId(siteId);
			waterMark.setSite(site);
			waterMark.setCreateTime(new Date());
			waterMarkDao.save(waterMark);
			mess = "添加成功";
		} else {
			mess = "对象为空";
		}
		return mess;
	}

	public void modifyWaterFont(WaterMarkForm form, String id) {

		TextWatermark waterMark = form.getTextwatermark();
		TextWatermark newwaterMark = findByWaterMarkId(id);
		newwaterMark.setName(waterMark.getName());
		newwaterMark.setBoder(waterMark.getBoder());
		newwaterMark.setColor(waterMark.getColor());
		newwaterMark.setCreateTime(new Date());
		newwaterMark.setFont(waterMark.getFont());
		newwaterMark.setPosition(waterMark.getPosition());
		newwaterMark.setTransparency(waterMark.getTransparency());
		newwaterMark.setFontSize(waterMark.getFontSize());
		waterMarkDao.update(newwaterMark);
	}

	public TextWatermark findByWaterMarkId(String id) {
		TextWatermark ma = waterMarkDao.getAndClear(id);
		return ma;
	}

	public PictureWatermark findByPicId(String id) {
		PictureWatermark pic = picwaterMarkDao.getAndClear(id);
		return pic;
	}

	public Pagination findDocumentListByCategoryId(Pagination pagination,
			String siteId, boolean isSystemManager) {
		Pagination page = null;
		if (isSystemManager) {
			page = waterMarkDao.getPagination(pagination);
		} else {
			page = waterMarkDao.getPagination(pagination, "siteId", siteId);
		}
		return page;
	}

	public Watermark findByFontDefault(String siteId, boolean isSystemManager) {
		// 获取文字水印中为TRUE的
		List list = null;
		List list2 = null;
		if (isSystemManager) {
			list = waterMarkDao.findByNamedQuery(
					"findFontWaterMarkByDefaultedIsTrue", "siteId",
					findSiteParentId());
			list2 = picwaterMarkDao.findByNamedQuery(
					"findPicMarkByDefaultedIsTrue", "siteId",
					findSiteParentId());
		} else {
			list = waterMarkDao.findByNamedQuery(
					"findFontWaterMarkByDefaultedIsTrue", "siteId", siteId);
			list2 = picwaterMarkDao.findByNamedQuery(
					"findPicMarkByDefaultedIsTrue", "siteId", siteId);
		}
		Watermark p = null;
		if (list != null && list.size() > 0) {
			p = (Watermark) list.get(0);
			log.info("获取的名字" + p.getName());
		} else if (list != null && list2.size() > 0) {
			p = (Watermark) list2.get(0);
			log.info("获取的名字" + p.getName());
		}
		return p;
	}

	public TextWatermark findByTextWater(String siteId, boolean isSystemManager) {
		List list = null;
		if (isSystemManager) {
			list = waterMarkDao.findByNamedQuery(
					"findFontWaterMarkByDefaultedIsTrue", "siteId",
					findSiteParentId());
		} else {
			list = waterMarkDao.findByNamedQuery(
					"findFontWaterMarkByDefaultedIsTrue", "siteId", siteId);
		}

		TextWatermark tw = null;
		if (list != null && list.size() > 0) {
			tw = (TextWatermark) list.get(0);
		}
		return tw;
	}

	public PictureWatermark findByPicWater(String siteId,
			boolean isSystemManager) {
		List list2 = null;
		if (isSystemManager) {
			list2 = picwaterMarkDao.findByNamedQuery(
					"findPicMarkByDefaultedIsTrue", "siteId",
					findSiteParentId());
		} else {
			list2 = picwaterMarkDao.findByNamedQuery(
					"findPicMarkByDefaultedIsTrue", "siteId", siteId);
		}
		PictureWatermark p = null;
		if (list2 != null && list2.size() > 0) {
			p = (PictureWatermark) list2.get(0);
		}
		return p;
	}

	public String findByPicAll(String id) {
		String picWater = "";
		List list = picwaterMarkDao.findByNamedQuery("findpMarkByAll", "picid",
				id);
		PictureWatermark p = null;
		if (list.size() > 0) {
			p = (PictureWatermark) list.get(0);
			picWater = p.getPosition() + "@@" + p.getTransparency() + "@@"
					+ p.getBoder();
		}
		return picWater;
	}

	public String findByWaterAll(String id) {
		String waterFont = "";
		List list = waterMarkDao.findByNamedQuery("findWaterMarkByAll",
				"waterid", id);
		TextWatermark tw = null;
		if (list.size() > 0) {
			tw = (TextWatermark) list.get(0);

			waterFont = tw.getFont() + "@@" + tw.getFontSize() + "@@"
					+ tw.getColor() + "@@" + tw.getPosition() + "@@"
					+ tw.getTransparency() + "@@" + tw.getBoder();
		}
		return waterFont;
	}

	/**
	 * 删除文字水印
	 * 
	 * @param ids
	 *            获取id，如果ID为很多则根据ID删除多条信息
	 * @return 删除的成功或失败的信息
	 */
	public String deleteWaterFontMark(String ids) {
		String infoMessage = "";
		String[] checkedIds = StringUtil.split(ids, ",");
		try {
			for (String deletedId : checkedIds) { // 遍历ID
				TextWatermark water = findByWaterMarkId(deletedId);
				if (water == null) {
					return "";
				} else if (water != null) {
					waterMarkDao.deleteByKey(deletedId);
					infoMessage = "文字水印删除成功";
				} else {
					infoMessage = "文字水印删除失败";
					break;
				}
			}

			log.debug(infoMessage);
		} catch (Exception e) {
			e.printStackTrace();
			infoMessage = "删除失败";
			log.debug(infoMessage);
		}
		return infoMessage;
	}

	public String deletePicWaterMark(String ids) {
		String infoMessage = "";
		String[] checkedIds = StringUtil.split(ids, ",");
		try {
			for (String deletedId : checkedIds) { // 遍历ID
				PictureWatermark water = findByPicId(deletedId);
				if (water == null) {
					return "";
				} else {
					if (water != null && water.getLocalPath().length() > 0) {
						String localPath = GlobalConfig.appRealPath
								+ water.getLocalPath();
						// 先删除实际文件
						if (FileUtil.isExist(localPath)) {
							FileUtil.delete(localPath);
//							String fipath = localPath.substring(0, localPath
//									.lastIndexOf("/"));
//							File f = new File(fipath);
//							if (f.list().length == 0) {
//								log.info("文件已经为空，系统将自动删除");
//								FileUtil.delete(fipath);
//							}
						}else{
							infoMessage = "图片水印文件不存在";
						}
						picwaterMarkDao.deleteByKey(deletedId);
					} else {
						infoMessage = "图片水印删除失败";
						break;
					}
				}
			}
			log.debug(infoMessage);
		} catch (Exception e) {
			infoMessage = "删除失败";
			log.debug(infoMessage);
		}
		return infoMessage;
	}

	public String modifyWaterDefault(String id, String siteId,
			boolean isSystemManager) {
		String mess = "";
		int i1 = 0, i = 0;
		if (isSystemManager) {
			i1 = waterMarkDao.updateByDefine("modifyAllPicWatermark", "siteId",
					SqlUtil.toSqlString(findSiteParentId()));
			i = waterMarkDao.updateByDefine("modifyPicWatermark", "ids",
					SqlUtil.toSqlString(id));
		} else {
			i1 = waterMarkDao.updateByDefine("modifyAllPicWatermark", "siteId",
					SqlUtil.toSqlString(siteId));
			i = waterMarkDao.updateByDefine("modifyPicWatermark", "ids",
					SqlUtil.toSqlString(id));
		}
		if (i > 0 && i1 > 0)
			mess = "修改成功";
		else
			mess = "无操作记录";
		return mess;
	}

	public String getFont(String id) {
		List l = waterMarkDao.findByNamedQuery("findWaterMarkById", "waterid",
				id);
		return l.get(0).toString();
	}

	public List findByWaterName(String siteId, boolean isSystemManager) {
		List list = null;
		if (isSystemManager) {
			list = waterMarkDao.findByNamedQuery("findTxtWaterMarkByDefaulted",
					"siteId", findSiteParentId());// 系统管理员，调用父网站的id
		} else {
			list = waterMarkDao.findByNamedQuery("findTxtWaterMarkByDefaulted",
					"siteId", siteId);// 普通用户调用网站的id
		}
		return list;
	}

	public List findByPicPath(String siteId, boolean isSystemManager) {
		List list = null;
		if (isSystemManager) {
			list = waterMarkDao.findByNamedQuery("findPicWaterMarkByDefaulted",
					"siteId", findSiteParentId());// 系统管理员，调用父网站的id
		} else {
			list = waterMarkDao.findByNamedQuery("findPicWaterMarkByDefaulted",
					"siteId", siteId);// 普通用户调用网站的id
		}
		return list;
	}

	/**
	 * 图片上传 .
	 * 
	 * @param form
	 *            要导入的图片表单
	 * @param siteId
	 *            网站id
	 * @param nodeId
	 *            节点id
	 * @param sessionID
	 *            创建者id
	 * @return 返回是否成功信息
	 */
	public String uploadImg(WaterMarkForm form, String siteId, String createId) {
		String creatorid = createId;
		String infoMessage = "";
		String filePath = SiteResource.getWaterDir(siteId, false); // 获取相对路径
		List<UploadedFile> list;
		try {
			list = WebClientUtil.uploadFile(form, filePath, 1024 * 1024 * 10); // 获取图片集合
			if (list != null && list.size() != 0) {
				PictureWatermark pwk = form.getPicwatermark();
				for (int i = 0; i < list.size(); i++) { // 遍历循环上传的图片集合
					UploadedFile uploadedFile = (UploadedFile) list.get(i);
					infoMessage = form.getInfoMessage();
					log.debug("infoMessage================" + infoMessage);
					if (FileUtil.isPicture(uploadedFile.getFileName())) { // 判断是否是图片
						PictureWatermark pw = new PictureWatermark();
						pw.setBoder(pwk.getBoder());
						pw.setCreator(pwk.getCreator());
						pw.setPosition(pw.getPosition());
						pw.setTransparency(pwk.getTransparency());
						pw.setDefaulted(false);
						String fileName = uploadedFile.getFileName(); // 获取图片原来就有的名称
						String fileChange = uploadedFile.getChangefileName(); // 获取图片改变名称后的名字
						pw.setName(fileName); // 将图片的旧名字放入
						addPic(pw, siteId, createId,fileChange); // 调用方法，添加到数据库
					} else {
						infoMessage = "上传文件中不包含图片";
					}

				}

			}
			log.debug(infoMessage);
		} catch (Exception e) {
			e.printStackTrace();
			infoMessage = "上传文件中不包含图片";
			log.debug(infoMessage);
		}
		return infoMessage;
	}

	public void addPic(PictureWatermark pic, String siteId, String createId,
			String fileName) {
		// 设置创建者
		User creator = new User();
		creator.setId(createId);
		pic.setCreator(creator);
		// 设置网站
		Site site = new Site();
		site.setId(siteId);
		pic.setSite(site);
		pic.setCreateTime(new Date());
		pic.setLocalPath("/release/site" + siteId + "/upload/water/"
						+ fileName);

		picwaterMarkDao.save(pic);
	}

	/**
	 * @param systemLogDao
	 *            the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

	/**
	 * @param siteDao
	 *            the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

}

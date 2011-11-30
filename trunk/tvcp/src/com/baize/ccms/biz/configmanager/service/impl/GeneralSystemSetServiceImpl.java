/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.configmanager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.configmanager.dao.GeneralSystemSetDao;
import com.j2ee.cms.biz.configmanager.dao.ReneralSystemSetCategoryDao;
import com.j2ee.cms.biz.configmanager.domain.GeneralSystemSet;
import com.j2ee.cms.biz.configmanager.domain.ReneralSystemSetCategory;
import com.j2ee.cms.biz.configmanager.service.GeneralSystemSetService;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;

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
 * @author 包坤涛
 * @version 1.0
 * @since 2009-9-5 下午02:22:02
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class GeneralSystemSetServiceImpl implements GeneralSystemSetService {
	private static final Logger log = Logger
			.getLogger(GeneralSystemSetServiceImpl.class);
	/** 系统设置DAO */
	private GeneralSystemSetDao generalSystemSetDao;
	/** 系统设置类别dao */
	private ReneralSystemSetCategoryDao reneralSystemSetCategoryDao;

	/**
	 * 根据根节点查找作者数据
	 * 
	 * @param pagination
	 *            分页对象 
	 * @param nodeid
	 *            节点ID
	 * @return Pagination 分页对象
	 */

	public Pagination findGeneralSystemSetData(Pagination pagination, String categoryId) {
		if (categoryId != null && !categoryId.equals("")) {
			return generalSystemSetDao.getPagination(pagination, "categoryId",
					categoryId);
		} else {
			return new Pagination();
		}
	}

	/**
	 * 根据根节点查找系统数据
	 * 
	 * @param id
	 *            系统数据唯一编号
	 * @return 系统数据对象
	 */
	public GeneralSystemSet findGeneralSystemSetByKey(String id) {
		return generalSystemSetDao.getAndNonClear(id);
	}

	/**
	 * 根据根系统数据对象修改属性
	 * 
	 * @param generalSystemSet
	 *            系统数据对象
	 */
	public String modifyGenralSystemSetServic(
			GeneralSystemSet generalSystemSet, String categoryId) {
		log
				.debug("GeneralSystemSetServiceImpl 的modifyGenralSystemSetServic 方法类开始执行");
		String message = "1";
		// 查找满足条件的用户
		List list = generalSystemSetDao.findAll();
		GeneralSystemSet gs = null;
		// 判断用户有没有修改，数据库是否里存在类似的用户
		for (int i = 0; i < list.size(); i++) {
			// 将每一个用户查出来
			gs = (GeneralSystemSet) list.get(i);
			if (null != generalSystemSet.getSetContent()
					&& (!generalSystemSet.getSetContent().equals(""))) {
				if (null != gs.getSetContent()
						&& (!gs.getSetContent().equals(""))) {
					if (gs.getSetContent().equals(
							generalSystemSet.getSetContent())) {
						if (!gs.getId().equals(generalSystemSet.getId())) {
							if (gs.getReneralSystemSetCategory().getId()
									.equals(categoryId)) {
								return message;
							}
						}
					}

				}
			}
		}
		// 根据id查出对应的系统类
		GeneralSystemSet genSystemSet = generalSystemSetDao
				.getAndClear(generalSystemSet.getId());
		// 得到内容对象
		genSystemSet.setSetContent(generalSystemSet.getSetContent());
		// 如果连接地址不为空的话就对地址进行改变
		if (null != generalSystemSet.getUrl()) {
			genSystemSet.setUrl(generalSystemSet.getUrl());
		}
		// 得到默认设置
		genSystemSet.setDefaultSet(generalSystemSet.getDefaultSet());
		message = "3";
		// 对数据进行更改
		generalSystemSetDao.update(genSystemSet);
		log
				.debug("GeneralSystemSetServiceImpl 的modifyGenralSystemSetServic 方法类执行结束");
		return message;
	}

	/**
	 * 根据根节点删除系统数据对象
	 * 
	 * @param id
	 *            系统数据唯一编号
	 * @return
	 */
	public void deleteGenralSystemSetServic(String id) {
		log
				.debug("GeneralSystemSetServiceImpl的 删除 方法deleteGenralSystemSetServic类开始执行");
		generalSystemSetDao.deleteByIds(SqlUtil.toSqlString(id));
		log
				.debug("GeneralSystemSetServiceImpl的 删除 方法deleteGenralSystemSetServic类执行结束");
	}

	/**
	 * 根据根节点添加系统数据,作者内容添加
	 * 
	 * @param GeneralSystemSet
	 *            系统设置对象
	 * @param separator
	 *            分隔符
	 * @param categoryId
	 *            树节点ID
	 * @return 消息对象 分页对象
	 */
	public String addgeneralSystemSetService(GeneralSystemSet generalSystemSet,
			String separator, String categoryId, String overDefault,
			String sessionID, String siteId) {
		// 用户类型
		User user = new User();
		user.setId(sessionID);
		generalSystemSet.setUser(user);
		// 栏目类型
		Site site = new Site();
		site.setId(siteId);
		generalSystemSet.setSite(site);
		generalSystemSet.setCreateTime(new Date());
		String message = "0";
		/**如果separator不为空，覆盖为“”*/
	/*	if(null!=separator&&(!separator.equals(""))){
			message="3";
			return message  ;
		}
			*/
		
		
		
		if (null == separator || separator.equals("")) {
			List listHave;
			// 判断作者名是否重复
			String setNameHave = generalSystemSet.getSetContent();
			// 作者名对应的键
			String[] keyNumbers = { "categoryId", "setNameHave" };
			// 作者名对应的值
			String[] keyvalues = { categoryId, setNameHave };
			if (null != setNameHave && (!setNameHave.equals(""))) {
				listHave = generalSystemSetDao.findByNamedQuery("findNumbersHave", keyNumbers, keyvalues);
				if (listHave.size() > 0) {
					message = "1";
					log.debug("查询作者名是否重复");
					return message;
				}
			}
		}

		// findCountByGeneralSystemSet
		// 查寻默认设置是否存在
		log.debug("查寻默认设置是否存在");
		List count = generalSystemSetDao.findByNamedQuery(
				"findCountByGeneralSystemSet", "categoryId", categoryId);
		if (null != overDefault && overDefault.equals("overDefault")) {
			log.debug("默认设置是否覆盖");
		} else if (null != generalSystemSet.getDefaultSet()
				&& generalSystemSet.getDefaultSet().equals(true)) {
			if (count.size() > 0 && (null==separator||(separator.equals("")))) {
				message = "2";
				log.debug("确定默认设置覆盖");
				return message;
			}
		}

		// 如果覆盖仔细修改命令
		List overdefaults = generalSystemSetDao.findByNamedQuery(
				"findOverDefaultsByGeneralSystemSet", "categoryId", categoryId);
		if (null != overDefault && (!overDefault.equals(""))) {
			if (overDefault.equals("overDefault")) {
				for (int f = 0; f < overdefaults.size(); f++) {
					GeneralSystemSet getSet = (GeneralSystemSet) overdefaults
							.get(f);
					getSet.setDefaultSet((Boolean) false);
					generalSystemSetDao.update(getSet);
					log.debug("禁用原来覆盖的内容");
				}
				// 将新的内容添加进表
				// 将对应与主表相关的id插入
				log.debug("满足条件的主表相关的id插入");
				ReneralSystemSetCategory reneralSystemSetCategory = new ReneralSystemSetCategory();
				reneralSystemSetCategory.setId(categoryId);
				generalSystemSet.setReneralSystemSetCategory(reneralSystemSetCategory);
				generalSystemSetDao.save(generalSystemSet);
				log.debug("满足条件的内容保存"); 
				message = "3";
				return message;
			}
		}

		// 如果是单个的储存 并且没有覆盖命令
		if (null == separator || separator.equals("")) {
			List lists;
			ArrayList arrayList = new ArrayList();
			// 判断作者名是否重复
			String setName = generalSystemSet.getSetContent();
			// 作者名对应的键
			String[] keyNumber = { "categoryId", "setName" };
			// 作者名对应的值
			String[] keyvalue = { categoryId, setName };
			if (null != setName && (!setName.equals(""))) {
				lists = generalSystemSetDao.findByNamedQuery("findNumbers",	keyNumber, keyvalue);
				if (lists.size() > 0) {
					message = "1";
					log.debug("没有覆盖命令的内容保存！");
				} else {
					// 将对应与主表相关的id插入
					ReneralSystemSetCategory reneralSystemSetCategory = new ReneralSystemSetCategory();
					reneralSystemSetCategory.setId(categoryId);
					generalSystemSet.setReneralSystemSetCategory(reneralSystemSetCategory);
					generalSystemSetDao.save(generalSystemSet);
					message = "3";
					return message;
				}
			}
			return message;
		}

		if (null != separator && (!separator.equals(""))) {
			ArrayList arrayList = new ArrayList();
			String sp = separator.toString();
			// 前台传来的内容数量
			int lengths = generalSystemSet.getSetContent().toString().split(sp).length;
			// 创建一个数组装内容
			String[] array = new String[lengths];
			array = generalSystemSet.getSetContent().toString().split(sp);
			// 判断前台的内容本身是否重复
			for (int n = 0; n < lengths; n++) {
				if (array[n] != null && (!array[n].equals(""))) {
					for (int m = n + 1; m < array.length; m++) {
						if (array[n].equals(array[m])) {
							message = "1";
							log.debug(" 判断前台的内容本身是重复;提示并请从新输入");
							break;
						}
					}
					// 如果不重复
					if (null != message && (!message.equals(""))
							&& (!message.equals("1"))) {
						log.debug(" 判断前台的内容与数据库中内容是是否重复;保存不重复的内容");
						arrayList.add(array[n]);
					}
				}
			}
			for (int i = 0; i < arrayList.size(); i++) {
				// 创建新的对象
				if (null != arrayList.get(i) && (!arrayList.get(i).equals(""))) {
					// 判断作者名是否重复
					String setName = arrayList.get(i).toString();
					// 作者名对应的键
					String[] keyNumber = { "categoryId", "setName" };
					// 作者名对应的值
					String[] keyvalue = { categoryId, setName };
					// 判断数据库里是否存在
					List list = generalSystemSetDao.findByNamedQuery(
							"findNumbers", keyNumber, keyvalue);
					int k = 1;
					if (list.size() > 0) {
						message = "1";
						log.debug("有分割符的内容" + list.size());
					} else {
						k++;
						// 创建一个新对象用于保存 newSet
						GeneralSystemSet newSet = new GeneralSystemSet();
						// 设置时间
						newSet.setCreateTime(new Date());
						// 设置主体内容
						newSet.setSetContent(arrayList.get(i).toString());
						// 默认全是否
						newSet.setDefaultSet(false);
						// 将对应与主表相关的id插入
						ReneralSystemSetCategory reneralSystemSetCategory = new ReneralSystemSetCategory();
						reneralSystemSetCategory.setId(categoryId);
						newSet
								.setReneralSystemSetCategory(reneralSystemSetCategory);

						// 用户类型
						// User user1=new User();
						// user1.setId(sessionID);
						newSet.setUser(user);
						// 栏目类型
						// Site site1=new Site();
						// site.setId(siteId);
						newSet.setSite(site);
						generalSystemSetDao.save(newSet);

					}
					// 判断有没有数据插入成功。
					if (k > 1) {
						log.debug("系统设置成功");
						message = "3";
					}
				}
			}
		}
		/*
		 * ReneralSystemSetCategory reneralSystemSetCategory = new
		 * ReneralSystemSetCategory();
		 * 
		 * reneralSystemSetCategory.setId(categoryId); //
		 * reneralSystemSetCategory.setId("1"); //
		 * 设置主表中对应的类别添加到reneralSystemSetCategory表中 generalSystemSet
		 * .setReneralSystemSetCategory(reneralSystemSetCategory); if (null !=
		 * separator && !separator.equals("")) { String sp =
		 * separator.toString(); log.debug("前台传来的内容数量"); // 前台传来的内容数量 int
		 * lengths = generalSystemSet.getSetContent().toString()
		 * .split(sp).length; String[] array = new String[lengths]; array =
		 * generalSystemSet.getSetContent().toString().split(sp);
		 * 
		 * // 判断前台的内容本身是否重复 for (int n = 0; n < array.length; n++) { if
		 * (array[n] != null && (!array[n].equals(""))) { for (int m = n + 1; m
		 * < array.length; m++) { if (array[n].equals(array[m])) { return
		 * message = "1"; // 3 } } } } }
		 */

		message = "3";
		return message;
	}

	/**
	 * 根据根节添加图片数据对象
	 * 
	 * @param categoryId
	 *            树节点数据唯一编号
	 * @param generalSystemSet
	 *            系统数据对象
	 * @return
	 */
	public Boolean addPectureGeneralSystemSetService(
			GeneralSystemSet generalSystemSet, String categoryId) {
		Boolean message = false;
		// 判断关系表中的内容是否为null
		if (categoryId != null && (!categoryId.equals(""))) {
			ReneralSystemSetCategory reneralSystemSetCategory = new ReneralSystemSetCategory();
			reneralSystemSetCategory.setId(categoryId);
			generalSystemSet
					.setReneralSystemSetCategory(reneralSystemSetCategory);
			List list = generalSystemSetDao.findAll();
			// 判断图片原来设置是 还是否
			Boolean bool = true;
			// 判断图片原来设置情况
			for (int i = 0; i < list.size(); i++) {
				if (null != categoryId && (!(categoryId.equals("")))) {
					GeneralSystemSet gs = (GeneralSystemSet) list.get(i);

					if (!categoryId.equals(gs.getReneralSystemSetCategory()
							.getId())) {
						if (i == (list.size() - 1)) {
							generalSystemSet.setSetContent("0");
							generalSystemSetDao.save(generalSystemSet);
							bool = false;
						}
					} else {
						break;
					}
				}
			}
			if (null != generalSystemSet.getDefaultSet()
					&& (!generalSystemSet.getDefaultSet().equals(""))) {
				if (bool) {
					for (int i = 0; i < list.size(); i++) {
						if (null != categoryId && (!(categoryId.equals("")))) {
							GeneralSystemSet gs = (GeneralSystemSet) list
									.get(i);
							if (categoryId.equals(gs
									.getReneralSystemSetCategory().getId())) {
								// System.out.println(gs.getDefaultSet());
								gs.setDefaultSet(generalSystemSet
										.getDefaultSet());
								generalSystemSetDao.update(gs);
								break;
							}
						}
					}
				}
			}

			// 查找当前图片设置状态
			log.debug("查找当前图片设置状态");
			for (int i = 0; i < list.size(); i++) {
				if (null != categoryId && (!(categoryId.equals("")))) {
					GeneralSystemSet gs = (GeneralSystemSet) list.get(i);
					if (categoryId.equals(gs.getReneralSystemSetCategory()
							.getId())) {
						// System.out.println(gs.getDefaultSet());
						// 判断默认状态是否为true
						if (null != gs.getDefaultSet()
								&& !gs.getDefaultSet().equals("")) {
							if (gs.getDefaultSet().toString().equals("true")) {
								message = true;
								break;
							} else {
								message = false;
								break;
							}
						}

					}
				}
			}
		}
		return message;
	}

	public String addLinkgeneralSystemSetService(
			GeneralSystemSet generalSystemSet, String categoryId,
			String sessionID, String siteId) {
		List lists;
		String message = "1";
		// 用户类型
		User user = new User();
		user.setId(sessionID);
		generalSystemSet.setUser(user);
		// 栏目类型
		Site site = new Site();
		site.setId(siteId);
		generalSystemSet.setSite(site);
		// 判断关系表中的内容是否为null
		if (categoryId != null && (!categoryId.equals(""))) {
			ReneralSystemSetCategory reneralSystemSetCategory = new ReneralSystemSetCategory();
			reneralSystemSetCategory.setId(categoryId);
			// 判断链接名是否重复
			String setName = generalSystemSet.getSetContent();
			// 链接名对应的键
			String[] keyNumber = { "categoryId", "setName" };
			// 链接名对应的值
			String[] keyvalue = { categoryId, setName };
			if (null != setName && (!setName.equals(""))) {
				lists = generalSystemSetDao.findByNamedQuery("findNumbers",
						keyNumber, keyvalue);
				if (lists.size() > 0) {
					message = "1";
					log.debug("添加链接失败");
					return message;
				} else {
					generalSystemSet
							.setReneralSystemSetCategory(reneralSystemSetCategory);
					generalSystemSet.setCreateTime(new Date());
					generalSystemSet.setSetContent(setName);
					generalSystemSetDao.save(generalSystemSet);
					message = "3";

				}
			}
		}
		return message;

	}

	/**
	 * 根据根系统数据对象修改属性
	 * 
	 * @param generalSystemSet
	 *            系统数据对象
	 */
	public String modifyAuthorSystem(GeneralSystemSet generalSystemSet, String categoryId, String overDefault) {
		log.debug("modifyAuthorSystem根系统数据对象修改属性开始执行开始执行");
		String message = "1";
		String setName = generalSystemSet.getSetContent();
		List  listDefault = generalSystemSetDao.findByNamedQuery("findOverDefaultsByGeneralSystemSet", "categoryId", categoryId);
		String[] keyNumber = { "categoryId", "setName" };
		// 作者名对应的值
		String[] keyvalue = { categoryId, setName };
		if(!StringUtil.isEmpty(setName)) {
			// 查找用户名存是否存在
			List lists = generalSystemSetDao.findByNamedQuery("findNumbers", keyNumber, keyvalue);
			// 是否没有修改
			// gs数据库中的与传来的id相同的对象
			GeneralSystemSet gs = generalSystemSetDao.getAndClear(generalSystemSet.getId());
			if ((!gs.getSetContent().equals(generalSystemSet.getSetContent()))) {
				// 如果用户名存在返回 提示用户名存在
				if (lists.size() > 0) {
					return message;
				}
			}
			log.debug("如果用户名不存在并且不是启动执行修改");
			// 如果用户名不存在并且不是启动执行修改
			if (null != generalSystemSet.getDefaultSet() && (!generalSystemSet.equals("")) 
					&& ((null != generalSystemSet.getDefaultSet()) && generalSystemSet.getDefaultSet().equals(false))) {
				GeneralSystemSet genSystemSet = generalSystemSetDao.getAndClear(generalSystemSet.getId());
				genSystemSet.setSetContent(generalSystemSet.getSetContent());
				genSystemSet.setDefaultSet(generalSystemSet.getDefaultSet());
				generalSystemSetDao.update(genSystemSet);
				message = "3";
			}
			log.debug("如果用户状态是启动的");
			if (StringUtil.isEmpty(overDefault)) {
				if ((null != generalSystemSet.getDefaultSet())	&& (!generalSystemSet.getDefaultSet().equals(""))) {
					if (generalSystemSet.getDefaultSet().equals(true)) {
						if(listDefault.size()>0){
							if(generalSystemSet.getId().equals(((GeneralSystemSet)(listDefault.get(0))).getId())) {
								message = "6";
							}else{
								message = "5";
							}
							return message;
						}else{
			                GeneralSystemSet genSystemSet = generalSystemSetDao.getAndClear(generalSystemSet.getId());
							genSystemSet.setDefaultSet(true);
							genSystemSet.setSetContent(generalSystemSet.getSetContent());
					        generalSystemSetDao.update(genSystemSet);
					        return message="3";
						}
					}
				}
			}
			// 如果用户状态需要启用
			if (null != generalSystemSet.getDefaultSet()
					&& (!generalSystemSet.equals(""))
					&& overDefault.equals("overDefault")) {

				// String [] objectKey={"categoryId",""};
				// 作者名对应的值
				// String [] objectValue={categoryId,setName};
				

				if (listDefault.size() > 0) {
					// 将原来作者对象状态设置为false禁用
					for (int j = 0; j < listDefault.size(); j++) {
						// 遍历符合条件的
						GeneralSystemSet genSys = (GeneralSystemSet) listDefault
								.get(j);
						genSys.setDefaultSet(false);
						generalSystemSetDao.update(genSys);
					}
					// 对当前对象进行保存
					GeneralSystemSet genSystemSet = generalSystemSetDao
							.getAndClear(generalSystemSet.getId());
					genSystemSet
							.setSetContent(generalSystemSet.getSetContent());
					genSystemSet
							.setDefaultSet(generalSystemSet.getDefaultSet());
					generalSystemSetDao.update(genSystemSet);
					message = "3";
				} else {
					// 对当前对象进行保存
					log.debug("对当前对象本身进行保存");
					GeneralSystemSet genSystemSet = generalSystemSetDao
							.getAndClear(generalSystemSet.getId());
					genSystemSet
							.setSetContent(generalSystemSet.getSetContent());
					genSystemSet
							.setDefaultSet(generalSystemSet.getDefaultSet());
					generalSystemSetDao.update(genSystemSet);
					message = "3";
				}
			}
		}

		return message;
	}

	public void setGeneralSystemSetDao(GeneralSystemSetDao generalSystemSetDao) {
		this.generalSystemSetDao = generalSystemSetDao;
	}

	/**
	 * @param pagination
	 *            分页对象
	 * @return Pagination 分页对象
	 */
	public final Pagination findGeneralSystemSetData(final Pagination pagination) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setReneralSystemSetCategoryDao(
			ReneralSystemSetCategoryDao reneralSystemSetCategoryDao) {
		this.reneralSystemSetCategoryDao = reneralSystemSetCategoryDao;
	}

	public String addgeneralSystemSetService(GeneralSystemSet generalSystemSet,
			String separator, String categoryId, String overDefault) {
		// TODO Auto-generated method stub
		return null;
	}

}

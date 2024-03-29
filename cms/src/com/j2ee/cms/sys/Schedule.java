/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.sys;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.j2ee.cms.biz.articlemanager.dao.ArticleDao;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  </p>
 * <p>网址：http://www.j2ee.cmsweb.com
 * @author <a href="mailto:sean_yang@163.com">杨信</a>
 * @version 1.0
 * @since 2009-8-31 下午04:10:23
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class Schedule {
	
	private ArticleDao articleDao;
	
	public void publish() {
		/*System.out.println("[[[[[[[[[[[[[");
		System.out.println(articleDao.findAll().get(0));
		System.out.println("=================-------" +
				"----=====++++++");*/
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

}

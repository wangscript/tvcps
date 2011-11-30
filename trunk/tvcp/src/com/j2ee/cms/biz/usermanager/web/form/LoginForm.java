/**
 * 
 */
package com.j2ee.cms.biz.usermanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * cps通用信息管理系统 
 * package: com.j2ee.cms.biz.login.web.form
 * File: LoginForm.java 创建时间:2009-1-6下午03:21:55
 * Title: 标题（要求能简洁地表达出类的功能和职责）
 * Description: 描述（简要描述类的职责、实现方式、使用注意事项等）
 * Copyright: Copyright (c) 2009  
 * Company:  
 * 模块: 用户管理模块
 * @author  娄伟峰
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等） 
 */
public class LoginForm extends ActionForm {
	private String id;
	private String name;
	private String password;
	private List list = new ArrayList();
	private int count = 0;
	private String message;
	private String rand;

	/**
	 * @return count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count 要设置的 count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return list
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list 要设置的 list
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	 /**
	   * Formbean中的Validate方法，对用户名和密码进行录入型的校验
	   * 这里校验用户名和密码不能为空即可了
	   * @return  ActionErrors
	 */
	protected ActionErrors validateData(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
	      /**@todo: finish this method, this is just the skeleton.*/
	      ActionErrors errors = new ActionErrors();
	      if (name == null || password.equals("")) {
	          errors.add("user", new ActionMessage("01607"));
	      }
	      if (password == null || password.equals("")) {
	          errors.add("password", new ActionMessage("01608"));
	      }
	      if (errors.isEmpty())
	          return null;
	      return errors;
	}
	
	  /**
	 * Formbean中的reset方法，对用户名和密码进行置空
	 */

	  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	    name=null;
	    password=null;
	  }

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the rand
	 */
	public String getRand() {
		return rand;
	}

	/**
	 * @param rand the rand to set
	 */
	public void setRand(String rand) {
		this.rand = rand;
	}

}

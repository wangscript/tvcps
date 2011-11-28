/**
 * <p>标题: —— 异常拦截器</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: —— 设计模块</p>
 * <p>版权: Copyright (c) 2011 ackwin
 * @author 
 * @version 1.0
 * @since 2011-8-10 下午04:47:57
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
package com.house.core.exception;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailException;


import com.house.core.common.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor {

	/**
	 * 属性描述：
	 */
	private static final long serialVersionUID = 9118028427900056668L;
	
	/**
	 * 日志处理
	 */
	protected final Logger log = Logger.getLogger(getClass()); 

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com
	 * .opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		ActionContext context = invocation.getInvocationContext();
		//访问action名称
		String actionName = context.getName();
		String userName = (String) context.getSession().get(Constant.LOGINNAME);
	/*	if (!actionName.equals("login") && (userName == null || userName.equals(""))) {
			return "login";
		}*/
		String result = "";
		try {
			result = invocation.invoke();
		} catch (DataAccessException ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("数据库操作失败，");
		} catch (NullPointerException ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("调用了未经初始化的对象或者是不存在的对象，");
		} catch (IOException ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("IO异常，");
		} catch (ClassNotFoundException ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("指定的类不存在，");
		} catch (ArithmeticException ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("数学运算异常，");
		} catch (ArrayIndexOutOfBoundsException ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("数组下标越界，");
		} catch (IllegalArgumentException ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("方法的参数错误，");
		} catch (MailException ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("邮件发送出现异常，");
		} catch (ClassCastException ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("类型强制转换错误，");
		} catch (SQLException ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("操作数据库异常，");
		} catch (NoSuchMethodException ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("方法末找到异常，");
		} catch (Exception ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new SystemException("程序内部错误，操作失败，");
		}
	    return result;
	}
}

/**
 * <p>标题: —— 系统自定义异常</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: —— 设计模块</p>
 * <p>版权: Copyright (c) 2011 ackwin
 * @author 
 * @version 1.0
 * @since 2011-7-29 下午04:18:50
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
package com.house.core.exception;

public class SystemException extends Exception {
	private static final long serialVersionUID = 0xc1a865c45ffdc5f9L;

	public SystemException(String msg) {
		super(SystemExceptionMessage(msg));
	}

	public SystemException(Throwable throwable) {
		super(throwable);
	}

	public SystemException(String msg,Throwable throwable) {
		super(SystemExceptionMessage(msg),throwable);
	}
	
	private static String SystemExceptionMessage(String msgBody) {
		String prefixStr = "抱歉，";
		String suffixStr = " 请稍后再试或与管理员联系！";
		StringBuilder friendlyErrMsg = new StringBuilder("");
		friendlyErrMsg.append(prefixStr);
		friendlyErrMsg.append(msgBody);
		friendlyErrMsg.append(suffixStr);
		String str = friendlyErrMsg.toString();
		return str;
	}

}
package com.house.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
/**
 * 
 * <p>标题: —— 通过反射,获得定义Class时声明的父类的范型参数的类型. </p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-11-14 下午05:16:09
 * @history（历次修订内容、修订人、修订时间等）
 */
public class GenericsUtils {
	/**  
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型.  
	 * 如public BookManager extends GenricManager<Book>  
	 *  
	 * @param clazz The class to introspect  
	 * @return the first generic declaration, or <code>Object.class</code> if cannot be determined  
	 */
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**  
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型.  
	 * 如public BookManager extends GenricManager<Book>  
	 *  
	 * @param clazz clazz The class to introspect  
	 * @param index the Index of the generic ddeclaration,start from 0.  
	 */
	public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}
}
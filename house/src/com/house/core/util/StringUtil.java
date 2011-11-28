/**
 * project：电视互联网项目
 * Company: 江苏集群信息产业股份有限公司

*/
package com.house.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>标题: —— 字符串处理工具类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2011 江苏集群信息产业股份有限公司
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-4-22 下午01:54:26
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public class StringUtil {
	/**
	 * 
	  * <p>方法说明:正则表达式替换</p> 
	  * <p>创建时间:2011-4-22下午01:54:42</p>
	  * <p>作者: 娄伟峰</p>
	  * @param paramString1
	  * @param paramString2
	  * @param paramString3
	  * @return String
	 */
	 public static String replaceIgnoreCase(String paramString1, String paramString2, String paramString3)
	  {
	    Pattern localPattern = Pattern.compile(paramString3, 2);
	    Matcher localMatcher = localPattern.matcher(paramString1);
	    return localMatcher.replaceAll(paramString2);
	  }
	 
	 /**
	  * <p>方法说明：去除字符串首位(0位)的分隔符</p>
	  * <p>创建时间：2011-5-30 11:20</p>
	  * <p>作者：刘加东</p>
	  * @param String str
	  * @return
	  */
	 public static String deleteFirstDot(String str,String dot){
		 if(str != null && str.length()>0){
			 while(str.indexOf(dot) == 0){
				 str = str.substring(1);
			 }
		 }
		 return str;
	 }
	 /**
	  * <p>方法说明：去除字符串尾部的分隔符</p>
	  * <p>创建时间：2011-6-1 14:38</p>
	  * <p>作者：刘加东</p>
	  * @param String str
	  * @return
	  */
	 public static String deleteLastDot(String str,String dot){
		 if(str != null && str.length()>0){
			 while(str.lastIndexOf(dot) == str.length()-1){
				 str = str.substring(0,str.length()-1);
			 }
		 }
		 return str;
	 }
	 /**
	  * 
	   * <p>方法说明：字符串大小写反转</p> 
	   * <p>创建时间:2011-6-14下午02:07:01</p>
	   * <p>作者: 娄伟峰</p>
	   * @param str
	   * @return String
	  */
	 public static String convertString(String str){ 
		 if(str != null && !str.equals("")){
			 return str.toLowerCase();
		 }else{
			 return ""; 
		 }         
	 } 
	 
	 /**
	  * 把以逗号隔开的ID字符串转换为list
	  * @param ids
	  * @return
	  */
	 public static List<String> stringToArray(String ids){
			List<String> list = new ArrayList<String>();
			if(ids != null){
				String strId[] = ids.split(",");
				for(int i = 0 ; i < strId.length ; i++){
					if(!strId[i].equals("")){
						list.add(strId[i]);
					}
				}
			}
			return list;
	 }
	 
	 public static void main(String args[]){
		 System.out.println(StringUtil.convertString("GSDFAsasas"));
	 }
}

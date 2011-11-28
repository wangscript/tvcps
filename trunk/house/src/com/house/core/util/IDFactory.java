package com.house.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/** 
 * Title: IDFactory.
 * Description:  根据时间序列生成ID编号
 * Copyright: Copyright (c) 2008； 
 * Company: 
 * @author  louwf 2008-12-17
 * @version 1.0
 */
public class IDFactory {
  /**  
   * @return String  根据时间序列生成ID编号
   */
  public static String getId() {
    SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    String strTime = sFormat.format(new Date());
    int strMath =  10000+(int)(Math.random()*(99999-10000));
    String str =String.valueOf(strMath); 
	String id=strTime+str;
	return id;
  }
  
	
}
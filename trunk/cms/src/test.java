import java.util.HashMap;
import java.util.Map;

import com.j2ee.cms.common.core.domain.Constant;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.RSAHelper;

/**
 * project：进销存管理系统
 * Company: 娄伟峰
 */

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2012  娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2012-3-2 下午05:34:19
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class test {
    public static void main(String[] args) throws Exception { 
      
    //    String str = encrypt("tJjb2rPYQJiZzOWRPA80vj/KriEuXLqOq+Jwxk4bQbtGWxuIi7FdMYsmXV6BS2qq2FYajQNCE7DI pdViCPnHg4d1yrwjSOrsQhbeCc71P64mXFLlyXthvzHOqxJ9+kc+/exBnca7W9zF6KUaUIfhuFkM 62lLOhgqxhsPf+Axh94= ",publicKeyString);
     //  System.out.println("str============="+str);
    	RSAHelper helper = new RSAHelper();
       String str = "0/mcg6EqHCLBg43BiQfv2pdveMy8PEB/wtwwgKc2yXNzMidKDpzJCs8EWZMHYwIQILAsugsT/D2fZRusLiLembQaKGeRc1M+ZQQIoM/E54pLfJe3NVcvOtchisvv7L9FhN8Mc7riy1AuKwcZ3BSldiDa49H/NDLRvi8VG+u+i6Y= ";
    //   BASE64Decoder decoder = new BASE64Decoder();
       String tempStr = RSAHelper.decrypt(str,Constant.privateKeyString);
        System.out.println("new==="+tempStr);
         
        Map map = new HashMap();
        map.put("mac", "'"+ RSAHelper.decrypt(str,Constant.privateKeyString)+"'");
        map.put("siteCount", 3);
        map.put("columnCount",100);
        map.put("validityDate", 8);
        map.put("registDate", DateUtil.getCurrDate());
        map.put("version", "'v1.0'");
        String mapString =  RSAHelper.encrypt(map.toString(),"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCUBMHyRRmTpmp050rACzGhg5JwvCEIrTPm4YPiZ1kaEfdIV3Vm2YOVvJSk1HMMif2RrXNZzZ+C0nmOteAtdxIfEgJnbUVmJ01n+vWLwn9F+x/QhA7j/z6Id2nW/bs5WgAWet08K4u6D1fJDfxaztKxkmfPW5yfXgSDFv0ZV9bDpwIDAQAB");
        System.out.println("mapString===="+mapString);
      
		// JSONObject jsonObject = JSONObject.fromObject("{validityDate=10, columnCount=5000, siteCount=10, mac12='f2fac6f7e5383f3e77426390bbf52057', version='v1.0', registDate=2011-10-26}");	


  }
}

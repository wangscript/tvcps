 

import com.baize.common.core.util.StringUtil;


public class code {
	public static void main(String[] args) {
		getcode("24d794dfc756320ffadb905d526299bcbefa6e4b04354c358a17345776fe8017");		
	}
	
	public static void getcode(String code){
		System.out.println(StringUtil.encryptMD5(code.substring(0, 32)));
		System.out.println(StringUtil.encryptMD5("#"+1));
		System.out.println(StringUtil.encryptMD5("1000"));//栏目数
		System.out.println(StringUtil.encryptMD5("#"+2));
		System.out.println(StringUtil.encryptMD5("10")); //网站数
		System.out.println(StringUtil.encryptMD5("#"+3));
		System.out.println(StringUtil.encryptMD5(10 + "&")); //月份
		System.out.println(StringUtil.encryptMD5("#"+4));
		System.out.println(StringUtil.encryptMD5("2011")); //注册日期
		System.out.println(StringUtil.encryptMD5("#"+5));
		System.out.println(StringUtil.encryptMD5("10"));
		System.out.println(StringUtil.encryptMD5("#"+6));
		System.out.println(StringUtil.encryptMD5("24"));
		 
	}
}


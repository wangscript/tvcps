 

import com.baize.common.core.util.StringUtil;


public class TestCode {
	public static void main(String[] args) {
	//	System.out.print(StringUtil.encryptMD5("00 1f 29 81 9e 4a"));
	//	String s = StringUtil.decodeBase64String("MjdiYjgwZjMzYWY0ZWJlNjY3Njk3MDViNjI2MmI2MWNkMTIyZTc2NmY1M2MzNTkzYmE5OTZiNDJkZWMxOGUwNg==");
	//	String s = StringUtil.decodeBase64String("YmRjZDQ3Yzk0NGY0YTg3MzMwMTQwZjE5Y2I3OTdiOGQxODk3MjVkMWI2YjVlZTEzOTFmNDAwMTQ1NjI4Y2NiZA==");
	//	System.out.println("s====================="+s);
		getcode("21015cc7649a0ec5e0b44ab8f5eca601ef9b96119689feb20de469c75c2842b9");
		//getcode(s);
	}
	
	public static void getcode(String code){
				System.out.println(StringUtil.encryptMD5(code.substring(0, 32)));
		System.out.println(StringUtil.encryptMD5("#"+1));
		System.out.println(StringUtil.encryptMD5("5000"));//栏目数
		System.out.println(StringUtil.encryptMD5("#"+2));
		System.out.println(StringUtil.encryptMD5("10")); //网站数
		System.out.println(StringUtil.encryptMD5("#"+3));
		System.out.println(StringUtil.encryptMD5(30 + "&")); //月份
		System.out.println(StringUtil.encryptMD5("#"+4));
		System.out.println(StringUtil.encryptMD5("2011")); //注册日期
		System.out.println(StringUtil.encryptMD5("#"+5));
		System.out.println(StringUtil.encryptMD5("8"));
		System.out.println(StringUtil.encryptMD5("#"+6));
		System.out.println(StringUtil.encryptMD5("9"));
		/*	System.out.print(StringUtil.encryptMD5(code.substring(0, 32)));
		System.out.print(StringUtil.encryptMD5("#"+1));
		System.out.print(StringUtil.encryptMD5("5000"));//��Ŀ��
		System.out.print(StringUtil.encryptMD5("#"+2));
		System.out.print(StringUtil.encryptMD5("10")); //��վ��
		System.out.print(StringUtil.encryptMD5("#"+3));
		System.out.print(StringUtil.encryptMD5(30 + "&")); //����@��ʾ��&��ʾ��
		System.out.print(StringUtil.encryptMD5("#"+4));
		System.out.print(StringUtil.encryptMD5("2009")); //ע������
		System.out.print(StringUtil.encryptMD5("#"+5));
		System.out.print(StringUtil.encryptMD5("9"));
		System.out.print(StringUtil.encryptMD5("#"+6));
		System.out.print(StringUtil.encryptMD5("18"));*/
	/*String s = StringUtil.encryptMD5(code.substring(0, 32))
			+StringUtil.encryptMD5("#"+1)
			+StringUtil.encryptMD5("5000")//��Ŀ��
			+StringUtil.encryptMD5("#"+2)
			+StringUtil.encryptMD5("10")//��վ��
			+StringUtil.encryptMD5("#"+3)
			+StringUtil.encryptMD5(30 + "&")//����@��ʾ��&��ʾ��
			+StringUtil.encryptMD5("#"+4)
			+StringUtil.encryptMD5("2010")//ע������
			+StringUtil.encryptMD5("#"+5)
			+StringUtil.encryptMD5("7")
			+StringUtil.encryptMD5("#"+6)
			+StringUtil.encryptMD5("22");*/
//	System.out.println(s);
 //	System.out.println(StringUtil.encodeBase64String(s));
//	String ss="c8131671abb805f8f497f484c38a23544c635835d09a5b2fe007ffd8656af2e36b48295d6dcbe6f37a62c5c0a9893dc1445c9a7e01d1cfaa2ea232ee92548cfdb8c37e33defde51cf91e1e03e51657da20311f2ad0e306003867ee754463de914503dbf0cba0187d6308175a7d956c5409bf2e39822f584a656a08bfc9c2d78701643584e419eba6f2beace3a2abb92740ae74453a35941e65701d4b44a0d19fac627ab1ccbdb62ec96e702f07f6425b5386e785d0571938fcfe081f183a5362b4568df26077653eeadf29596708c94bYzgxMzE2NzFhYmI4MDVmOGY0OTdmNDg0YzM4YTIzNTQ0YzYzNTgzNWQwOWE1YjJmZTAwN2ZmZDg2NTZhZjJlMzZiNDgyOTVkNmRjYmU2ZjM3YTYyYzVjMGE5ODkzZGMxNDQ1YzlhN2UwMWQxY2ZhYTJlYTIzMmVlOTI1NDhjZmRiOGMzN2UzM2RlZmRlNTFjZjkxZTFlMDNlNTE2NTdkYTIwMzExZjJhZDBlMzA2MDAzODY3ZWU3NTQ0NjNkZTkxNDUwM2RiZjBjYmEwMTg3ZDYzMDgxNzVhN2Q5NTZjNTQwOWJmMmUzOTgyMmY1ODRhNjU2YTA4YmZjOWMyZDc4NzAxNjQzNTg0ZTQxOWViYTZmMmJlYWNlM2EyYWJiOTI3NDBhZTc0NDUzYTM1OTQxZTY1NzAxZDRiNDRhMGQxOWZhYzYyN2FiMWNjYmRiNjJlYzk2ZTcwMmYwN2Y2NDI1YjUzODZlNzg1ZDA1NzE5MzhmY2ZlMDgxZjE4M2E1MzYyYjQ1NjhkZjI2MDc3NjUzZWVhZGYyOTU5NjcwOGM5NGI=";
//	System.out.println(StringUtil.decodeBase64String(StringUtil.encodeBase64String(s)));
	}
}


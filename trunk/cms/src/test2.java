import java.io.File;

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
 * @since 2012-3-3 下午08:37:37
 * @history（历次修订内容、修订人、修订时间等） 
 */

public class test2 {
	public static void main(String args[]){
		String str = "\\release\\site201202261116344841\\template\\1330236824468\\201202261413444372696299691.htm";
		String temp[] = str.split(File.separator);
		System.out.println(temp.length);
	}
}

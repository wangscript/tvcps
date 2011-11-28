/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.unitmanager.label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 模板管理</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * @author <a href="mailto:xinyang921@gmail.com">包坤涛</a>
 * @version 1.0
 * @since 2009-6-9 上午11:04:54
 * @history（历次修订内容、修订人、修订时间等）
 */
public final class OnlineSurverySetLabel {
	
	/**for标签 */
	public final static String FOR_LABEL = "<!--for--> <!--/for-->";
	/**调查主题*/
	public final static String THEME = "<!--theme-->";
	/**序号*/
	public final static String NUMBER = "<!--number-->";
	/**调查问题*/
	public final static String QUESTION = "<!--question-->";         
	/**调查答案*/
	public final static String ANSWER = "<!--answer-->";  
	/**问题id*/
	public final static String QUESTIONID = "<!--questionId-->";
	/**答案id*/
	public final static String ANSWERID = "<!--answerId-->";
	/** 更多链接 */
	public final static String MORE = "<!--more-->";
	/** 投票样式是否显示 */
	public final static String DISPLAYSTYLE = "<!--display-->";
	/** 链接地址 */
	public final static String URL = "<!--url-->";
	/** 类型名称 */
	public final static String KINDNAME = "<!--kindname-->";
	/** 显示更多 */
	public final static String DISPLAYMORE = "<!--displayMore-->";
	
	/**所有标签 */
	public final static Map map = new HashMap();
	/**所有单元标签的*/
	public final static List onlineSurceySetLabels = new ArrayList();

	static {
		map.put("循环", FOR_LABEL);
		map.put("调查主题", THEME);
		map.put("序号", NUMBER);
		map.put("调查问题", QUESTION);
		map.put("调查答案", ANSWER);
		map.put("问题ID", QUESTIONID);
		map.put("答案ID", ANSWERID);
		map.put("更多", MORE);
		map.put("样式", DISPLAYSTYLE);
		map.put("地址", URL);
		map.put("类型名称", KINDNAME);
		map.put("显示更多", DISPLAYMORE);
		onlineSurceySetLabels.add(map);
	}
	
	private OnlineSurverySetLabel() {
		
	}
}

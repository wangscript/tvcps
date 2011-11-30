/**
 * project：通用内容管理系统
 * Company:  
*/
package com.j2ee.cms.plugin.letterbox.web.form;

import com.j2ee.cms.plugin.letterbox.domain.Letter;
import com.j2ee.cms.plugin.letterbox.domain.LetterReply;

/**
 * <p>标题: 回复信件</p>
 * <p>描述: 回复信件的表单数据，以便页面和方法中调用</p>
 * <p>模块: 信件箱</p>
 * <p>版权: Copyright (c) 2009 
 * @author 杨信
 * @version 1.0
 * @since 2009-6-18 下午05:03:00 
 * @history（历次修订内容、修订人、修订时间等） 
*/


public class LetterReplyForm {
	
	private static final long serialVersionUID = -5669807958636915339L;
	
	private Letter letter = new Letter();
	
	private LetterReply letterReply = new LetterReply();
	
	/**
	 * @param letter the letter to set
	 */
	public void setLetter(Letter letter) {
		this.letter = letter;
	}
	/**
	 * @return the letter
	 */
	public Letter getLetter() {
		return letter;
	}
	/**
	 * @param letterReply the letterReply to set
	 */
	public void setLetterReply(LetterReply letterReply) {
		this.letterReply = letterReply;
	}
	/**
	 * @return the letterReply
	 */
	public LetterReply getLetterReply() {
		return letterReply;
	}

}

/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.documentmanager.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.j2ee.cms.biz.documentmanager.domain.Attachment;
import com.j2ee.cms.biz.documentmanager.domain.Document;
import com.j2ee.cms.biz.documentmanager.domain.DocumentCategory;
import com.j2ee.cms.biz.documentmanager.domain.Flash;
import com.j2ee.cms.biz.documentmanager.domain.Js;
import com.j2ee.cms.biz.documentmanager.domain.Picture;
import com.j2ee.cms.biz.documentmanager.domain.PictureWatermark;
import com.j2ee.cms.biz.documentmanager.domain.TextWatermark;
import com.j2ee.cms.biz.documentmanager.domain.Watermark;
import com.j2ee.cms.biz.usermanager.domain.User;
import com.j2ee.cms.common.core.web.GeneralForm;
/**
 * <p>标题: 文档的form</p>
 * <p>描述: 文档的表单数据，以便页面和方法中调用</p>
 * <p>模块: 文档管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-23 上午11:00:00
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class DocumentForm extends GeneralForm {

	private static final long serialVersionUID = 2835523620175167997L;
	/**水印父类**/
	private Watermark watermark = new Watermark();
	/**水印文字**/
	private TextWatermark textwatermark = new TextWatermark();
	/**水印图片**/
	private PictureWatermark picwatermark = new PictureWatermark();
	
	/**图片水印下拉列表**/
	private List<PictureWatermark> listPicPath=new ArrayList<PictureWatermark>();
	/**水印文字下拉列表**/
	private List<TextWatermark> listWaterFont=new ArrayList<TextWatermark>();
	
	
	/** 创建一个用户对象 **/
	private User user = new User();
	/** 创建一个flash对象 **/
	private Flash flash = new Flash();
	/** 创建一个图片对象 **/
	private Picture picture = new Picture();
	/** 创建一个文档对象 **/
	private Document document = new Document();
	/** 创建一个附件对象 **/
	private Attachment attachment = new Attachment();
	/** 创建一个js脚本对象 **/
	private Js js = new Js();
	/**创建一个JS内容*/
	private String jsContent;
	/**js路径*/
	private String jsPath;
	/** 创建一个ids以便获得页面的id **/
	private String ids;
	/** 用户的id **/
	private String userid; 	
	/** 网站的id **/
	private String siteid;
	/** 创建时间 **/
	private String createTime;
	/**图片地址**/
	private String picurl;
	/**原图片宽度**/
	private int width;
	/**原图片高度**/
	private int height;
	/**缩略图片宽*/
	private String suolvwidth;
	/**缩略图片高*/
	private String suolvheight;
	/** 文档列表 **/
	private List<DocumentCategory> list;
	/** 判断是否是是在缩略图中上传图片 **/
	private int isScaleImage;
	/** 判断是否是在高级编辑器中上传flash**/
	private int isScaleFlash;
	/**是否是栏目链接**/
	private int columnLink;
	
	/** 存放所有附件的名字 **/
	private String attachmentNames;
	/**存放所有附件的地址**/
	private String attachmentUrl;
	/** 类别id **/
	private String categoryId;
	/** 存放所有附件的id **/
	private String attachmentIds;
	/** 文章图片 **/
	private String articlePicture;
	/** 文章flash**/
	private String articleFlash;
	
	/** 文章js脚本**/
	private String articleJs;
	
	/** 节点名称**/
	private String nodeNameStr;
	
	/**水印文字**/
	private String writerFont;
	/**文字大小**/
	private int fontsize;
	/**文字样式**/
	private String fontStyle;
	/**文字颜色**/
	private String fontColor;
	/**文字透明度**/
	private float qualNum;
	/**文字宽高**/
	private String imgXY;
	/**水印边距**/
	private int margin;
	/**水印图片名字**/
	private String img;
	/**水印选项**/
	private int selectOption;
	/**水印图片文件路径**/
	private String imgPath;
	/**是否要在上传图片时候加上水印**/
	private String isWater;
	/**用于ajax获取图片**/
	private String pic ;
	/**用于ajax获取文字**/
	private String waterfont;
	/**用于初始化页面是出现水印文字还是水印图片**/
	private String check;
	/**
	 * @return the pic
	 */
	public String getPic() {
		return pic;
	}

	/**
	 * @param pic the pic to set
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}

	/**
	 * @return the waterfont
	 */
	public String getWaterfont() {
		return waterfont;
	}

	/**
	 * @param waterfont the waterfont to set
	 */
	public void setWaterfont(String waterfont) {
		this.waterfont = waterfont;
	}

	public int getSelectOption() {
		return selectOption;
	}

	public void setSelectOption(int selectOption) {
		this.selectOption = selectOption;
	}

	public String getWriterFont() {
		return writerFont;
	}

	public void setWriterFont(String writerFont) {
		this.writerFont = writerFont;
	}

	public int getFontsize() {
		return fontsize;
	}

	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}

	public String getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public float getQualNum() {
		return qualNum;
	}

	public void setQualNum(float qualNum) {
		this.qualNum = qualNum;
	}

	public String getImgXY() {
		return imgXY;
	}

	public void setImgXY(String imgX) {
		this.imgXY = imgX;
	}


	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * @return the articleFlash
	 */
	public String getArticleFlash() {
		return articleFlash;
	}

	/**
	 * @param articleFlash the articleFlash to set
	 */
	public void setArticleFlash(String articleFlash) {
		this.articleFlash = articleFlash;
	}

	/**
	 * @return the articleAtta
	 */
	public String getArticleAtta() {
		return articleAtta;
	}

	/**
	 * @param articleAtta the articleAtta to set
	 */
	public void setArticleAtta(String articleAtta) {
		this.articleAtta = articleAtta;
	}

	/**文章附件**/
	private String articleAtta;
	/**
	 * @return the articlePicture
	 */
	public String getArticlePicture() {
		return articlePicture;
	}

	/**
	 * @param articlePicture the articlePicture to set
	 */
	public void setArticlePicture(String articlePicture) {
		this.articlePicture = articlePicture;
	}

	/**
	 * @return the columnLink
	 */
	public int getColumnLink() {
		return columnLink;
	}

	/**
	 * @param columnLink the columnLink to set
	 */
	public void setColumnLink(int columnLink) {
		this.columnLink = columnLink;
	}

	/**
	 * @return the attachmentIds
	 */
	public String getAttachmentIds() {
		return attachmentIds;
	}

	/**
	 * @param attachmentIds the attachmentIds to set
	 */
	public void setAttachmentIds(String attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

	/**
	 * @return the attachmentNames
	 */
	public String getAttachmentNames() {
		return attachmentNames;
	}

	/**
	 * @param attachmentNames the attachmentNames to set
	 */
	public void setAttachmentNames(String attachmentNames) {
		this.attachmentNames = attachmentNames;
	}

	/**
	 * @return the attachmentUrl
	 */
	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	/**
	 * @param attachmentUrl the attachmentUrl to set
	 */
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	
	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the isScaleImage
	 */
	public int getIsScaleImage() {
		return isScaleImage;
	}

	/**
	 * @param isScaleImage the isScaleImage to set
	 */
	public void setIsScaleImage(int isScaleImage) {
		this.isScaleImage = isScaleImage;
	}

	/**
	 * @return the list
	 */
	public List<DocumentCategory> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<DocumentCategory> list) {
		this.list = list;
	}

	
	/**
	 * @return the picurl
	 */
	public String getPicurl() {
		return picurl;
	}

	/**
	 * @param picurl the picurl to set
	 */
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the picture
	 */
	public Picture getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	/**
	 * @return the flash
	 */
	public Flash getFlash() {
		return flash;
	}

	/**
	 * @param flash the flash to set
	 */
	public void setFlash(Flash flash) {
		this.flash = flash;
	}

	/**
	 * @return the attachment
	 */
	public Attachment getAttachment() {
		return attachment;
	}

	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * @param ids 要设置的 ids
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

		/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	protected void validateData(ActionMapping arg0, HttpServletRequest arg1) {
	}

	/**
	 * @param isScaleFlash the isScaleFlash to set
	 */
	public void setIsScaleFlash(int isScaleFlash) {
		this.isScaleFlash = isScaleFlash;
	}

	/**
	 * @return the isScaleFlash
	 */
	public int getIsScaleFlash() {
		return isScaleFlash;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the siteid
	 */
	public String getSiteid() {
		return siteid;
	}

	/**
	 * @param siteid the siteid to set
	 */
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public void setNodeNameStr(String nodeNameStr) {
		this.nodeNameStr = nodeNameStr;
	}

	public String getNodeNameStr() {
		return nodeNameStr;
	}

	public void setJs(Js js) {
		this.js = js;
	}

	public Js getJs() {
		return js;
	}

	public void setArticleJs(String articleJs) {
		this.articleJs = articleJs;
	}

	public String getArticleJs() {
		return articleJs;
	}

	/**
	 * @return the listPicPath
	 */
	public List<PictureWatermark> getListPicPath() {
		return listPicPath;
	}

	/**
	 * @param listPicPath the listPicPath to set
	 */
	public void setListPicPath(List<PictureWatermark> listPicPath) {
		this.listPicPath = listPicPath;
	}

	/**
	 * @return the listWaterFont
	 */
	public List<TextWatermark> getListWaterFont() {
		return listWaterFont;
	}

	/**
	 * @param listWaterFont the listWaterFont to set
	 */
	public void setListWaterFont(List<TextWatermark> listWaterFont) {
		this.listWaterFont = listWaterFont;
	}

	/**
	 * @return the watermark
	 */
	public Watermark getWatermark() {
		return watermark;
	}

	/**
	 * @param watermark the watermark to set
	 */
	public void setWatermark(Watermark watermark) {
		this.watermark = watermark;
	}

	/**
	 * @return the textwatermark
	 */
	public TextWatermark getTextwatermark() {
		return textwatermark;
	}

	/**
	 * @param textwatermark the textwatermark to set
	 */
	public void setTextwatermark(TextWatermark textwatermark) {
		this.textwatermark = textwatermark;
	}

	/**
	 * @return the picwatermark
	 */
	public PictureWatermark getPicwatermark() {
		return picwatermark;
	}

	/**
	 * @param picwatermark the picwatermark to set
	 */
	public void setPicwatermark(PictureWatermark picwatermark) {
		this.picwatermark = picwatermark;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setIsWater(String isWater) {
		this.isWater = isWater;
	}

	public String getIsWater() {
		return isWater;
	}

	/**
	 * @return the suolvwidth
	 */
	public String getSuolvwidth() {
		return suolvwidth;
	}

	/**
	 * @param suolvwidth the suolvwidth to set
	 */
	public void setSuolvwidth(String suolvwidth) {
		this.suolvwidth = suolvwidth;
	}

	/**
	 * @return the suolvheight
	 */
	public String getSuolvheight() {
		return suolvheight;
	}

	/**
	 * @param suolvheight the suolvheight to set
	 */
	public void setSuolvheight(String suolvheight) {
		this.suolvheight = suolvheight;
	}

	public void setJsContent(String jsContent) {
		this.jsContent = jsContent;
	}

	public String getJsContent() {
		return jsContent;
	}

	public void setJsPath(String jsPath) {
		this.jsPath = jsPath;
	}

	public String getJsPath() {
		return jsPath;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}


}

/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.biz.documentmanager.service.impl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.baize.ccms.biz.configmanager.dao.SystemLogDao;
import com.baize.ccms.biz.documentmanager.dao.BreviaryImgDao;
import com.baize.ccms.biz.documentmanager.dao.CategoryDao;
import com.baize.ccms.biz.documentmanager.dao.DocumentDao;
import com.baize.ccms.biz.documentmanager.dao.PicWaterMarkDao;
import com.baize.ccms.biz.documentmanager.dao.WaterMarkDao;
import com.baize.ccms.biz.documentmanager.domain.Attachment;
import com.baize.ccms.biz.documentmanager.domain.BreviaryImg;
import com.baize.ccms.biz.documentmanager.domain.Document;
import com.baize.ccms.biz.documentmanager.domain.DocumentCategory;
import com.baize.ccms.biz.documentmanager.domain.Flash;
import com.baize.ccms.biz.documentmanager.domain.Js;
import com.baize.ccms.biz.documentmanager.domain.Picture;
import com.baize.ccms.biz.documentmanager.service.DocumentService;
import com.baize.ccms.biz.documentmanager.web.form.DocumentForm;
import com.baize.ccms.biz.sitemanager.dao.SiteDao;
import com.baize.ccms.biz.sitemanager.domain.Site;
import com.baize.ccms.biz.usermanager.domain.User;
import com.baize.ccms.sys.GlobalConfig;
import com.baize.ccms.sys.SiteResource;
import com.baize.common.core.dao.Pagination;
import com.baize.common.core.domain.UploadedFile;
import com.baize.common.core.util.FileUtil;
import com.baize.common.core.util.IDFactory;
import com.baize.common.core.util.NewScaleImage;
import com.baize.common.core.util.SqlUtil;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.WebClientUtil;

/**
 * <p>
 * 标题: 文档的service
 * </p>
 * <p>
 * 描述: 文档的dao实现，调用dao的方法
 * </p>
 * <p>
 * 模块: 文档管理
 * </p>
 * <p>
 * 版权: Copyright (c) 2009 南京百泽网络科技有限公司
 * 
 * @author 郑荣华
 * @version 1.0
 * @since 2009-3-23 上午09:49:15
 * @history（历次修订内容、修订人、修订时间等） 
 */
 

public class DocumentServiceImpl implements DocumentService {

	private final Logger log = Logger.getLogger(DocumentServiceImpl.class);

	/** 注入文档的dao **/
	private DocumentDao documentDao;
	/** 注入类别dao **/
	private CategoryDao categoryDao;
	/** 注入日志dao */
	private SystemLogDao systemLogDao;
	/** 注入水印dao. */
	private WaterMarkDao waterMarkDao;
	/** 图片水印dao. */
	private PicWaterMarkDao picwaterMarkDao;
	/** 图片缩放dao . */
	private BreviaryImgDao breviaryImgDao;
	/** 网站dao */
	private SiteDao siteDao;

	/**
	 *添加数据
	 * 
	 * @param document
	 *            添加的文档对象
	 */
	public void addDocument(Document document) {
		documentDao.save(document);
	}

	/**
	 * 获取水印路径.
	 * 
	 * @param id
	 * @return
	 */
	public String getPath(String id) {
		List l = waterMarkDao.findByNamedQuery("findpMarkById", "picid", id);
		return l.get(0).toString();
	}

	/**
	 * 获取字体
	 * 
	 * @param id
	 *            字体ID
	 * @return 字体
	 */
	public String getFont(String id) {
		List l = waterMarkDao.findByNamedQuery("findWaterMarkById", "waterid",
				id);
		return l.get(0).toString();
	}

	/**
	 * 修改数据
	 * 
	 * @param document
	 *            要修改的文档对象
	 */
	public void modifyDocument(Document document) {
		documentDao.update(document);
	}

	/**
	 * 按照文档的id查询文档信息
	 * 
	 * @prame id 根据栏目id获取整条数据
	 * @return Document 返回一个文档的对象
	 */
	public Document findDocumentById(String id) {
		return documentDao.getAndClear(id);
	}

	/**
	 * 按照文档中属于的类别的id查询并分页显示
	 * 
	 * @param pagination
	 *            类别分页对象
	 * @param id
	 *            要查询的文档id
	 * @param value
	 *            要查询的id的值
	 * @return Pagination 返回一个文档的分页对象
	 */
	public Pagination findDocumentListByCategoryId(Pagination pagination,
			String id, Object value) {
		Pagination page = documentDao.getPagination(pagination, id, value);

		return page;
	}

	public Pagination findDocumentListByCategoryIdBySiteId(
			Pagination pagination, String id, Object value, String siteid) {
		String str[] = { "categoryid", "siteId" };
		String str2[] = { value.toString(), siteid };
		Pagination page = documentDao.getPagination(pagination, str, str2);
		return page;
	}

	/**
	 * 按照类别id查找附件的信息
	 * 
	 * @param categoryId
	 *            附件类别的id
	 * @return 返回附件的列表
	 */
	public List<Document> findAttachmentListByCategoryId(String categoryId) {
		return documentDao.findByNamedQuery("findAttachmentListByCategoryId",
				"categoryid", categoryId);
	}

	/**
	 * 按照类别id查找脚本的信息
	 * 
	 * @param categoryId
	 *            脚本类别的id
	 * @return 返回脚本的列表
	 */
	public List<Document> findJsListByCategoryId(String categoryId) {
		return documentDao.findByNamedQuery("findScriptListByCategoryId",
				"categoryid", categoryId);
	}

	/**
	 * 获得附件分页的数据.
	 * 
	 * @param pagination
	 *            传递一个文档中附件的分页对象
	 * @return Pagination 返回一个分页对象
	 */
	public Pagination proccessPicturePagination(Pagination pagination,
			String pathName) {
		List list = pagination.getData();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Object[] object = (Object[]) list.get(i);
				// 获取图片
				String url = (String) object[1];
				object[1] = "<img class=\"preview\" name=\"pic\" id=\"pic\" style=\"width:25px;height:25px;z-index:999\" src=\""
						+ pathName + url + "\"/>";
			}
		}
		return pagination;
	}

	public String scaleImg(DocumentForm form, String pathName) {
		String mess = "";
		String apppath = GlobalConfig.appRealPath;
		String picurl = form.getPicurl();
		File fi = new File(apppath + picurl);// 原始图片的地址 ...
		if (!fi.exists()) {
			mess = "目录不存在";
			return mess;
		}
		String str[] = picurl.split("/");// 截取图片.
		String newUrl = StringUtil.delete(picurl, str[str.length - 1]);// 获取图片路径不包括图片名称
		// .
		String suffixName = StringUtil.strRChar(str[str.length - 1], '.');// 图片的后缀名
		// .
		String pidName = IDFactory.getId();
		String scaleUrl = apppath + newUrl + "s" + pidName + "." + suffixName;// 图片缩小后所存储的目录及名字
		// .

		NewScaleImage ima;
		try {
			ima = new NewScaleImage(fi, scaleUrl);// 缩放图片类 .
			ima.resize(Integer.valueOf(form.getSuolvwidth()), Integer
					.valueOf(form.getSuolvheight()));// 图片缩放
			addBreviaryImg(form.getIds(), scaleUrl.substring(scaleUrl
					.lastIndexOf("/") + 1, scaleUrl.length()), scaleUrl);// 插入到数据库
			mess = pathName + newUrl + "s" + pidName + "." + suffixName;
			log.info("缩放成功");
		} catch (IOException e) {
			e.printStackTrace();
			mess = "图片缩放失败，请检查图片的宽高填写是否正确";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			mess = "图片缩放失败，请检查图片的宽高填写是否正确";
		}
		return mess;
	}

	/**
	 * 将缩略图保存到数据库 .
	 * 
	 * @param id
	 *            文档ID
	 * @param picName
	 *            图片名称
	 * @param picPath
	 *            图片路径
	 */
	public void addBreviaryImg(String id, String picName, String picPath) {
		BreviaryImg bi = new BreviaryImg();
		bi.setCreateTime(new Date());
		Document documents = new Document();
		documents.setId(id);
		bi.setDocuments(documents);
		bi.setImgName(picName);
		bi.setImgPath(picPath);
		breviaryImgDao.save(bi);
		log.info("缩略图保存成功");
	}

	/**
	 * 图片水印 .
	 * 
	 * @param right
	 *            水印位置
	 * @param pressImg
	 *            水印图片
	 * @param targetImg
	 *            目标图片
	 * @param alpha
	 *            透明度
	 * @param Spacing
	 *            间距
	 */
	private void pressImage(String right, String pressImg, String targetImg,
			float alpha, int Spacing) {
		if(!FileUtil.isExist(pressImg)){
			log.debug("水印图片的路径："+pressImg);
			return ;
		}
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int width_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			int imgWidth = 0;
			int imgHeight = 0;
			if (right == null && right.equals("")) {
				imgWidth = ((width - width_biao) - Spacing);
				imgHeight = ((height - height_biao) - Spacing);
			}
			if (right.equals("bt1")) {
				imgWidth = (0 + Spacing);
				imgHeight = (0 + Spacing);
			}
			if (right.equals("bt2")) {
				imgWidth = (width / 2 - width_biao / 2);
				imgHeight = (0 + Spacing);

			}
			if (right.equals("bt3")) {
				imgWidth = (width - width_biao - Spacing);
				imgHeight = (0 + Spacing);
			}
			if (right.equals("bt4")) {
				imgWidth = (0 + Spacing);
				imgHeight = (height / 2 - height_biao / 2);
			}
			if (right.equals("bt5")) {
				imgWidth = (width / 2 - width_biao / 2);
				imgHeight = (height / 2 - height_biao / 2);
			}
			if (right.equals("bt6")) {
				imgWidth = (width - width_biao - Spacing);
				imgHeight = (height / 2 - height_biao / 2);
			}
			if (right.equals("bt7")) {
				imgWidth = (0 + Spacing);
				imgHeight = (height - height_biao - Spacing);
			}
			if (right.equals("bt8")) {
				imgWidth = (width / 2 - width_biao / 2);
				imgHeight = (height - height_biao - Spacing);
			}
			if (right.equals("bt9")) {
				imgWidth = ((width - width_biao) - Spacing);
				imgHeight = ((height - height_biao) - Spacing);
			}
			imgWidth = imgWidth < 0 ? 0 : imgWidth;
			imgHeight = imgHeight < 0 ? 0 : imgHeight;
			// 开始绘水印,并且给定水印的位置
			g.drawImage(src_biao, imgWidth, imgHeight, width_biao, height_biao,
					null);
			// 水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpg", img);
			log.info("图片水印添加成功");
		} catch (Exception e) {
			log.error("图片水印添加失败" + e);
		}
	}

	/**
	 * 文字水印 .
	 * 
	 * @param filePath
	 *            图片路径
	 * @param fontName
	 *            文字主题
	 * @param markContentColor
	 *            文字颜色
	 * @param font
	 *            字体
	 * @param right
	 *            字体位置
	 * @param fontSize
	 *            字体大小
	 * @param Spacing
	 *            间距
	 * @param alpha
	 *            透明度
	 * @return 水印绘制成功返回TRUE
	 */
	private boolean pressText(String filePath, String fontName,
			String fontColor, String fontStyle, String right, int fontSize,
			int Spacing, float alpha) {

		File img = new File(filePath);// 开始操作图片

		try {
			Image src = ImageIO.read(img);
			int x = src.getWidth(null);
			int y = src.getHeight(null);
			BufferedImage image = new BufferedImage(x, y,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, x, y, null);
			String c = null;
			if (fontColor != null && !fontColor.equals(""))
				c = fontColor.substring(1, fontColor.length());
			else
				c = "ff0000";
			Color color = new Color(Integer.parseInt(c, 16));
			g.setColor(color);
			Font f = new Font(fontStyle, Font.PLAIN, fontSize);
			FontMetrics fm = new JLabel().getFontMetrics(f);
			g.setFont(f);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));// 设置图片透明度

			int fontWidth = 0;
			int fontHeight = 0;
			if (right.equals("bt1")) {
				fontWidth = 0 + Spacing;
				fontHeight = fm.getHeight() + Spacing;
			}
			if (right.equals("bt2")) {
				fontWidth = x / 2 - fm.stringWidth(fontName) / 2;
				fontHeight = fm.getHeight() + Spacing;
			}
			if (right.equals("bt3")) {
				fontWidth = (x - fm.stringWidth(fontName)) - Spacing;
				fontHeight = (fm.getHeight() + Spacing);
			}
			if (right.equals("bt4")) {
				fontWidth = (0 + Spacing);
				fontHeight = (y / 2);
			}
			if (right.equals("bt5")) {
				fontWidth = (x / 2 - fm.stringWidth(fontName) / 2);
				fontHeight = (y / 2 - fm.getHeight() / 2);
			}
			if (right.equals("bt6")) {
				fontWidth = ((x - fm.stringWidth(fontName)) - Spacing);
				fontHeight = (y / 2);
			}
			if (right.equals("bt7")) {
				fontWidth = (0 + Spacing);
				fontHeight = (y - fm.getHeight() / 4 - Spacing);
			}
			if (right.equals("bt8")) {
				fontWidth = (x / 2 - fm.stringWidth(fontName) / 2);
				fontHeight = (y - fm.getHeight() / 4 - Spacing);
			}
			if (right.equals("bt9")) {
				fontWidth = ((x - fm.stringWidth(fontName)) - Spacing);
				fontHeight = (y - fm.getHeight() / 4 - Spacing);
			}
			fontWidth = fontWidth < 0 ? 0 : fontWidth;
			fontHeight = fontHeight < 0 ? 0 : fontHeight;
			g.drawString(fontName, fontWidth, fontHeight);
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpg", img);
			image.flush();
			log.info("文字水印添加成功");
		} catch (IOException e) {
			log.error("文字水印添加失败" + e);
			return false;
		}
		return true;

	}

	
	public String saveJsFile(String path,String content,String siteId,String updateId){
		String jspath = GlobalConfig.appRealPath + path;
		String mess = outFile(new File(jspath),content);//修改文件
		// 写入日志文件
		String categoryName = "文档管理(JS脚本管理)->编辑";
		String param =path.substring(path.lastIndexOf("/")+1,path.length());
		systemLogDao.addLogData(categoryName, siteId, updateId, param);
		return mess;
	}
	/**
	 * 写一个文件
	 * 
	 * @param fileIn
	 *            文件路径
	 * @param content
	 *            要先写入的内容
	 */
	public static String outFile(File fileIn, String content) {
		String mess = "";
		OutputStream fos = null;
		try {
			if (!fileIn.exists()) {
				fileIn.createNewFile();
				byte[] bytOut = content.getBytes("utf-8");
				fos = new FileOutputStream(fileIn);
				for (int i = 0; i < bytOut.length; i++) {
					fos.write((int) bytOut[i]);
				}
				mess = "你的文件丢失了，系统为你重新创建了文件并已经保存";
				return mess;
			}
			byte[] bytOut = content.getBytes("utf-8");
			fos = new FileOutputStream(fileIn);
			for (int i = 0; i < bytOut.length; i++) {
				fos.write((int) bytOut[i]);
			}
			mess = "保存成功";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mess;

	}
	public String editJsFile(String jsPath) {
		String mess = "";
		String path = GlobalConfig.appRealPath + jsPath;
		if (!FileUtil.isExist(path)) {
			mess = "没有找到该js文件";
			return mess;
		}
		try {
			InputStream is = new FileInputStream(path);
			mess = readToBuffer(is).toString();
		} catch (FileNotFoundException e) {
			log.error("js文件没有找到" + e);
			mess = null;
		}
		return mess;
	}
	/**
	 * 读出一个文件 .
	 * 
	 * @param is
	 *            文件流
	 * @return StringBuffer
	 * @throws UnsupportedEncodingException
	 */
	public final StringBuffer readToBuffer(final InputStream is) {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String line;
			line = reader.readLine();
			while (line != null) { // 如果 line 为空说明读完了
				sb.append(line + "\n");
				line = reader.readLine(); // 读取下一行
			}
			is.close();
		} catch (UnsupportedEncodingException e) {
			log.error("转码失败" + e);
			return null;
		} catch (IOException e) {
			log.error("文件读取失败" + e);
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.debug("关闭失败！！！");
					e.printStackTrace();
				}
			}
		}
		return sb;
	}

	/**
	 * 处理图片的上传操作 .
	 * 
	 * @param sessionID
	 *            当前登录用户的id值
	 * @param siteId
	 *            当前网站的id值
	 * @param nodeid
	 *            节点id
	 * @param url
	 *            图片地址
	 * @param form
	 *            文档表单
	 * @param documentService
	 *            文档业务对象
	 * @return 返回是否上传成功
	 */
	@SuppressWarnings("unchecked")
	public String proccessPictureUpload(String sessionID, String siteId,
			String nodeid, String url, DocumentForm form,
			DocumentService documentService) {
		String infoMessage = "";
		Document document = form.getDocument();

		// 设置上传的路径
		String f = SiteResource.getPictureDir(siteId, false);
		if (!FileUtil.isExist(f)) {
//			infoMessage = "文件目录不存在";
//			return infoMessage;
			FileUtil.makeDirs(f);
		}
		boolean isSelectOption = form.getSelectOption() == 0 ? true : false;
		List<UploadedFile> list;
		String name = "";
		String fid = IDFactory.getId();
		FileUtil.makeDirs(f + "/" + fid);
		String filePath = f + "/" + fid;
		try {
			list = WebClientUtil.uploadFile(form, filePath, 10 * 1024 * 1024);
			if (list != null && list.size() != 0) {
				UploadedFile uploadedFile = list.get(0);
				String fileName = uploadedFile.getChangefileName();
				String description = document.getDescription();// 获取描述
				String writerFont = form.getWriterFont(); // 水印字体
				String fontColor = form.getFontColor();// 颜色
				String fontStyle = form.getFontStyle(); // 字体样式
				String imgPos = form.getImgXY(); // 水印位置
				int fontSize = form.getFontsize(); // 字体大小
				int margin = form.getMargin(); // 间距

				float qualNum = form.getQualNum() / 100;
				String suffixName = uploadedFile.getSuffixName();
				// 上传的是图片
				if (FileUtil.isPicture(uploadedFile.getChangefileName())) {
					name = StringUtil.strLChar(uploadedFile.getFileName(), '.');
					int size = uploadedFile.getFileSize();
					if (form.getIsWater().equals("1")) { // 选中了上传加水印复选框
						if (isSelectOption) {// 文字水印
							log.info("用户选择了上传并添加文字水印");
							String fontname = getFont(writerFont);
							pressText(filePath + "/" + fileName, fontname,
									fontColor, fontStyle, imgPos, fontSize,
									margin, qualNum);
						} else {// 图片水印
							String waterPath = getPath(form.getImg());
							log.info("用户选择了上传并添加图片水印");
							pressImage(imgPos,GlobalConfig.appRealPath+waterPath,
									filePath + "/" + fileName, qualNum, margin);
						}
					}
					addPicture(name, fid + "/" + fileName, suffixName, size,
							siteId, description, nodeid, sessionID, url,
							documentService);
					infoMessage = "上传成功";
					
				// 上传的是zip包
				} else if (FileUtil.isZip(fileName)) {
					long currentDate = System.currentTimeMillis();
					FileUtil.makeDirs(filePath + "/" + currentDate);
					FileUtil.unZip(filePath + "/" + fileName, filePath + "/"
							+ currentDate);
					File file = new File(filePath + "/" + currentDate);
					File[] files = file.listFiles();
					// 定义一个标记标示zip包里是否包含图片文件
					int flag = 0;
					// 循环获取上传zip包的图片
					int len = files.length;
					for (int i = 0; i < len; i++) {
						if (FileUtil.isPicture(files[i].getName())) {
							// zip中含图片文件
							flag = 1;
							String suffix = StringUtil.strRChar(files[i]
									.getName(), '.');
							long newCurrentDate = System.currentTimeMillis();
							String newPath = filePath + "/" + newCurrentDate
									+ "." + suffix;
							File file1 = new File(newPath);
							files[i].renameTo(file1);
							// FileUtil.copy(newPath, filePath);
							name = StringUtil.strLChar(files[i].getName(), '.');
							String newFileName = newCurrentDate + "." + suffix;
							int size = (int) file1.length();
							if (form.getIsWater().equals("1")) { // 选中了上传加水印复选框
								if (isSelectOption) {// 文字水印
									log.info("用户选择了上传并添加文字水印");
									String fontname = getFont(writerFont);
									pressText(filePath + "/" + newFileName,
											fontname, fontColor, fontStyle,
											imgPos, fontSize, margin, qualNum);
								} else {// 图片水印
									String waterPath = getPath(form.getImg());
									// 用于获取路径
									log.info("用户选择了上传并添加图片水印");
									pressImage(imgPos, GlobalConfig.appRealPath+waterPath, filePath + "/"
											+ newFileName, qualNum, margin);
								}
							}
							addPicture(name, fid + "/" + newFileName, suffix,
									size, siteId, description, nodeid,
									sessionID, url, documentService); // 将图片添加到数据库
						}
					}
					if (flag == 0) {
						infoMessage = "您上传的文件中没有图片";
					} else {
						FileUtil.delete(filePath + "/" + fileName);
						FileUtil.delete(filePath + "/" + currentDate);
						infoMessage = "上传成功";
					}
				} else {
					infoMessage = "文件大小不能超过10M";
				}
			} else {
				infoMessage = form.getInfoMessage();
			}
		} catch (Exception e) {
			infoMessage = "上传失败,具体请看上传说明";
			log.error("上传失败,具体请看上传说明" + e);
		}
		if(infoMessage.equals("上传成功")){
			// 添加图片的同时，也将图片添加到生成和发布目录中去
			Site site = siteDao.getAndClear(siteId);
			String publishPicturePath = site.getPublishDir()+"/upload/picture";
			if(!FileUtil.isExist(publishPicturePath)){
				FileUtil.makeDirs(publishPicturePath);
			}
			String buildPicturePath = SiteResource.getBuildStaticDir(siteId, false)+"/upload/picture";
			if(!FileUtil.isExist(buildPicturePath)){
				FileUtil.makeDirs(buildPicturePath);
			}
			// 要拷贝的图片路径    filePath
			if(FileUtil.isExist(filePath)){
				FileUtil.copy(filePath, publishPicturePath, true);
				FileUtil.copy(filePath, buildPicturePath, true);
			}
		}
		
		return infoMessage;
	}

	/**
	 * 向数据库中添加图片类型数据 .
	 * 
	 * @param name
	 *            图片名称
	 * @param fileName
	 *            文件名
	 * @param suffixName
	 *            上传文件的后缀名
	 * @param size
	 *            文件大小
	 * @param siteid
	 *            网站id
	 * @param description
	 *            描述
	 * @param nodeid
	 *            类别id
	 * @param sessionID
	 *            用户id
	 * @param url
	 *            图片地址
	 */
	private void addPicture(String name, String fileName, String suffixName,
			int size, String siteid, String description, String nodeid,
			String sessionID, String url, DocumentService documentService) {
		Picture picture = new Picture();
		picture.setName(name);
		picture.setFileName(fileName.substring(fileName.lastIndexOf("/") + 1,
				fileName.length()));
		picture.setFileSuffix(suffixName);
		// 处理上传文件的大小
		String fileSize = proccessFileSize(size);
		picture.setFileSize(fileSize);
		String url2 = StringUtil.delete(url.toString(), "/picture.do");
		picture.setUrl(url2 + "/release/site" + siteid + "/upload/picture/"
				+ fileName);
		picture.setLocalPath("/release/site" + siteid + "/upload/picture/"
				+ fileName);
		picture.setCreateTime(new Date());
		picture.setDescription(description);
		picture.setPicDirectoryPath("/release/site" + siteid
				+ "/upload/picture/"
				+ fileName.substring(0, fileName.lastIndexOf("/")));
		// 文档类别
		if (!StringUtil.isEmpty(nodeid)) {
			DocumentCategory category = new DocumentCategory();
			category.setId(nodeid);
			picture.setDocumentCategory(category);
		}
		// 设置创建者
		User creator = new User();
		creator.setId(sessionID);
		picture.setCreator(creator);
		// 设置网站
		Site site = new Site();
		site.setId(siteid);
		picture.setSite(site);
		documentService.addDocument(picture);

		// 写入日志
		DocumentCategory category = categoryDao.getAndClear(nodeid);
		String categoryName = "图片类别->上传图片";
		String param = category.getName() + "->" + name;
		systemLogDao.addLogData(categoryName, siteid, sessionID, param);
	}

	/**
	 * 上传flash的处理.
	 * 
	 * @param sessionID
	 *            当前登录用户的id值
	 * @param siteId
	 *            当前网站的id值
	 * @param nodeid
	 *            节点id
	 * @param url
	 *            flash地址
	 * @param form
	 *            文档表单
	 * @param documentService
	 *            文档业务对象
	 * @return 返回是否上传成功
	 */
	public String proccessFlashUpload(String sessionID, String siteId,
			String nodeid, String url, DocumentForm form,
			DocumentService documentService) {
		String infoMessage = "";
		Document document = form.getDocument();
		// 设置上传的路径
		String randomFile = IDFactory.getId();// 随机生成子文件夹
		String oldPath = SiteResource.getFlashDir(siteId, false);// 获取上传flash路径
		FileUtil.makeDirs(oldPath + "/" + randomFile);
		String filePath = oldPath + "/" + randomFile;
		List<UploadedFile> list;
		String name = "";
		if (!FileUtil.isExist(oldPath)) {
			infoMessage = "文件目录不存在";
			return infoMessage;
		}
		try {
			list = WebClientUtil.uploadFile(form, filePath, 10 * 1024 * 1024);
			int i1 = 0;
			if (list != null && list.size() != 0) {
				UploadedFile uploadedFile = list.get(0);
				String fileName = uploadedFile.getChangefileName();

				String description = document.getDescription();
				String suffixName = uploadedFile.getSuffixName();
				// 上传的是zip包
				if (FileUtil.isZip(fileName)) {
					long currentDate = System.currentTimeMillis();
					FileUtil.makeDirs(filePath + "/" + currentDate);
					FileUtil.unZip(filePath + "/" + fileName, filePath + "/"
							+ currentDate);
					File file = new File(filePath + "/" + currentDate);
					File[] files = file.listFiles();
					// 定义一个标记标示zip包里是否包含flash文件
					int flag = 0;
					// 循环获取上传zip包的flash文件
					int len  = files.length;
					for (int i = 0; i < len; i++) {
						if (FileUtil.isFlash(files[i].getName())) {
							// zip中含flash文件
							flag = 1;
							String suffix = StringUtil.strRChar(files[i]
									.getName(), '.');
							long newCurrentDate = System.currentTimeMillis();
							File file1 = new File(filePath + "/" + newCurrentDate
									+ "." + suffix);
							files[i].renameTo(file1);
							name = StringUtil.strLChar(files[i].getName(), '.');
							String newFileName = newCurrentDate + "." + suffix;
							int size = (int) file1.length();
							addFlash(name, randomFile + "/" + newFileName,
									suffix, size, siteId, description, nodeid,
									sessionID, url, documentService);
						}
					}
					if (flag == 0) {
						infoMessage = "您上传的文件中没有flash文件";
					} else {
						infoMessage = "上传成功";
						FileUtil.delete(filePath + "/" + fileName);
						FileUtil.delete(filePath + "/" + currentDate);
					}
					// 上传的是flash
				} else {
					name = StringUtil.strLChar(uploadedFile.getFileName(), '.');
					int size = uploadedFile.getFileSize();
					addFlash(name, randomFile + "/" + fileName, suffixName,
							size, siteId, description, nodeid, sessionID, url,
							documentService);
					infoMessage = "上传成功";
				}
			} else {
				infoMessage = "文件大小不能超过10M";
				log.debug("infoMessage==========" + infoMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			infoMessage = "上传失败,具体请看上传说明";
		}
		
		if(infoMessage.equals("上传成功")){
			// 添加falsh的同时，也将falsh添加到生成和发布目录中去
			Site site = siteDao.getAndClear(siteId);
			String publishFlashPath = site.getPublishDir()+"/upload/flash";
			if(!FileUtil.isExist(publishFlashPath)){
				FileUtil.makeDirs(publishFlashPath);
			}
			String buildFlashPath = SiteResource.getBuildStaticDir(siteId, false)+"/upload/flash";
			if(!FileUtil.isExist(buildFlashPath)){
				FileUtil.makeDirs(buildFlashPath);
			}
			// 要拷贝的falsh路径    filePath
			if(FileUtil.isExist(filePath)){
				FileUtil.copy(filePath, publishFlashPath, true);
				FileUtil.copy(filePath, buildFlashPath, true);
			}
		}
		return infoMessage;
	}

	/**
	 * 向数据库中添加flash类型数据
	 * 
	 * @param name
	 *            flash名称
	 * @param fileName
	 *            文件名
	 * @param suffixName
	 *            上传文件的后缀名
	 * @param size
	 *            文件大小
	 * @param siteid
	 *            网站id
	 * @param description
	 *            描述
	 * @param nodeid
	 *            类别id
	 * @param sessionID
	 *            用户id
	 * @param url
	 *            flash地址
	 */
	private void addFlash(String name, String fileName, String suffixName,
			int size, String siteid, String description, String nodeid,
			String sessionID, String url, DocumentService documentService) {
		Flash flash = new Flash();
		flash.setName(name);
		flash.setFileName(fileName.substring(fileName.lastIndexOf("/") + 1,
				fileName.length()));
		flash.setFileSuffix(suffixName);
		// 处理上传文件的大小
		String fileSize = proccessFileSize(size);
		flash.setFileSize(fileSize);
		String url2 = StringUtil.delete(url.toString(), "/flash.do");
		log.info("---------------路径-------------" + url2 + "/release/site"
				+ siteid + "/upload/flash/" + fileName);
		flash.setUrl(url2 + "/release/site" + siteid + "/upload/flash/"
				+ fileName);
		flash.setLocalPath("/release/site" + siteid + "/upload/flash/"
				+ fileName);
		flash.setPicDirectoryPath("/release/site" + siteid + "/upload/flash/"
				+ fileName.substring(0, fileName.lastIndexOf("/")));
		flash.setCreateTime(new Date());
		flash.setDescription(description);
		// 文档类别
		DocumentCategory category = new DocumentCategory();
		category.setId(nodeid);
		flash.setDocumentCategory(category);
		// 设置创建者
		User creator = new User();
		creator.setId(sessionID);
		flash.setCreator(creator);
		// 设置网站
		Site site = new Site();
		site.setId(siteid);
		flash.setSite(site);
		documentService.addDocument(flash);

		// 写入日志
		DocumentCategory newcategory = categoryDao.getAndClear(nodeid);
		String param = newcategory.getName() + "->" + name;
		String categoryName = "flash类别->上传flash";
		systemLogDao.addLogData(categoryName, siteid, sessionID, param);
	}

	/**
	 * 添加附件的处理
	 * 
	 * @param sessionID
	 *            当前登录用户的id值
	 * @param siteId
	 *            当前网站的id值
	 * @param nodeid
	 *            节点id
	 * @param url
	 *            附件地址
	 * @param form
	 *            文档表单
	 * @param documentService
	 *            文档业务对象
	 * @return 返回是否上传成功
	 */
	public String proccessAttachmentUpload(String sessionID, String siteId,
			String nodeid, String url, DocumentForm form,
			DocumentService documentService) {
		String infoMessage = "";
		Document document = form.getDocument();
		// 设置上传的路径
		String randomFile = IDFactory.getId();// 生成随机子目录
		String oldPath = SiteResource.getAttachmentDir(siteId, false);
		FileUtil.makeDirs(oldPath + "/" + randomFile);
		String filePath = oldPath + "/" + randomFile;
		if (!FileUtil.isExist(oldPath)) {
			infoMessage = "文件目录不存在";
			return infoMessage;
		}
		List<UploadedFile> list;
		
		try {
			list = WebClientUtil.uploadFile(form, filePath, 10 * 1024 * 1024);
			if (list != null && list.size() != 0) {
				infoMessage = form.getInfoMessage();
				log.debug("----------infoMessage-------------" + infoMessage);
				if (infoMessage.equals("附件上传成功")) {
					UploadedFile uploadedFile = list.get(0);
					String fileName = uploadedFile.getChangefileName();
					String suffixName = uploadedFile.getSuffixName();
					String description = document.getDescription();
					// 上传的是附件
					if (FileUtil.isPermitAttachment(uploadedFile
							.getChangefileName()) || suffixName.equals("docx")) {
					String	name = StringUtil.strLChar(uploadedFile.getFileName(),
								'.');
						int size = uploadedFile.getFileSize();
						addAttachment(name, randomFile + "/" + fileName,
								suffixName, size, siteId, description, nodeid,
								sessionID, url, documentService);
						infoMessage = "上传成功";
					} else {
						infoMessage = "您上传的文件不符合要求";
					}
				} else {
					infoMessage = "文件大小不能超过10M";
				}
			} else {
				// infoMessage = form.getInfoMessage();
				infoMessage = "文件中没有你所要上传的文件";
				log.debug("infoMessage==========" + infoMessage);
			}
		} catch (Exception e) {
			infoMessage = "上传失败,具体请看上传说明";
			log.error("附件上传失败" + e);
		}
		
		if(infoMessage.equals("上传成功")){
			// 添加附件的同时，也将附件添加到生成和发布目录中去
			Site site = siteDao.getAndClear(siteId);
			String publishAttachmentPath = site.getPublishDir()+"/upload/attachment";
			if(!FileUtil.isExist(publishAttachmentPath)){
				FileUtil.makeDirs(publishAttachmentPath);
			}
			String buildAttachmentPath = SiteResource.getBuildStaticDir(siteId, false)+"/upload/attachment";
			if(!FileUtil.isExist(buildAttachmentPath)){
				FileUtil.makeDirs(buildAttachmentPath);
			}
			// 要拷贝的附件路径    filePath
			if(FileUtil.isExist(filePath)){
				FileUtil.copy(filePath, publishAttachmentPath, true);
				FileUtil.copy(filePath, buildAttachmentPath, true);
			}
		}
		return infoMessage;
	}

	/**
	 * 向数据库中添加附件类型数据
	 * 
	 * @param name
	 *            附件名称
	 * @param fileName
	 *            文件名
	 * @param suffixName
	 *            上传文件的后缀名
	 * @param size
	 *            文件大小
	 * @param siteid
	 *            网站id
	 * @param description
	 *            描述
	 * @param nodeid
	 *            类别id
	 * @param sessionID
	 *            用户id
	 * @param url
	 *            附件地址
	 */
	private void addAttachment(String name, String fileName, String suffixName,
			int size, String siteid, String description, String nodeid,
			String sessionID, String url, DocumentService documentService) {
		Attachment attachment = new Attachment();
		attachment.setName(name);
		attachment.setFileName(fileName.substring(
				fileName.lastIndexOf("/") + 1, fileName.length()));
		attachment.setFileSuffix(suffixName);
		// 处理上传文件的大小
		String fileSize = proccessFileSize(size);
		attachment.setFileSize(fileSize);
		String url2 = StringUtil.delete(url.toString(), "/attachment.do");
		attachment.setUrl(url2 + "/release/site" + siteid
				+ "/upload/attachment/" + fileName);
		attachment.setLocalPath("/release/site" + siteid
				+ "/upload/attachment/" + fileName);
		attachment.setPicDirectoryPath("/release/site" + siteid
				+ "/upload/attachment/"
				+ fileName.substring(0, fileName.lastIndexOf("/")));
		attachment.setCreateTime(new Date());
		attachment.setDescription(description);
		// 文档类别
		DocumentCategory category = new DocumentCategory();
		category.setId(nodeid);
		attachment.setDocumentCategory(category);
		// 设置创建者
		User creator = new User();
		creator.setId(sessionID);
		attachment.setCreator(creator);
		// 设置网站
		Site site = new Site();
		site.setId(siteid);
		attachment.setSite(site);
		documentService.addDocument(attachment);

		// 写入日志
		DocumentCategory newcategory = categoryDao.getAndClear(nodeid);
		String param = newcategory.getName() + "->" + name;
		String categoryName = "附件类别->上传附件";
		systemLogDao.addLogData(categoryName, siteid, sessionID, param);
	}

	/**
	 * 处理图片、flash、附件、js脚本的文件大小
	 * 
	 * @param size
	 *            上传文件的大小（数字形式）
	 * @return 返回上传文件的大小（字符串形式）
	 */
	private String proccessFileSize(int size) {
		String fileSize = "";
		if (size > 1024) {
			size = size / 1024;
			if (size > 1024) {
				size = size / 1024;
				fileSize = String.valueOf(size) + "M";
			} else {
				fileSize = String.valueOf(size) + "K";
			}
		} else {
			fileSize = "1K";
		}
		return fileSize;
	}

	public String deletePic(String ids, String siteId, String sessionID,
			String categoryName) {
		log.debug("图片的删除操作");
		String infoMessage = "";
		String[] checkedIds = StringUtil.split(ids, ",");
		try {
			for (String deletedId : checkedIds) {
				Document document = documentDao.getAndNonClear(deletedId);
				String tempCategoryName=null;
				if (document == null) {
					return "";
				} else {
					// 图片本身路径(不是缩略图路径)
					
					tempCategoryName = document.getDocumentCategory()
					.getName();
					String localPath = GlobalConfig.appRealPath
							+ document.getPicDirectoryPath() + "/"
							+ document.getFileName();
					// 先删除实际文件
					if (FileUtil.isExist(localPath)) {// 图片存在
						FileUtil.delete(localPath);// 删除图片
						List list = getBreviaryImgByDid(document.getId());// 查询该图片是否
						// 有缩略图
						if (list != null && list.size() > 0) {// 数据库中有缩略图
							int size =list.size();
							for (int i = 0; i < size; i++) {
								if (FileUtil.isExist(list.get(i).toString())) {// 目录中有缩略图
									FileUtil.delete(list.get(i).toString());// 删除缩略图
								}
							}
						}

						// 下面这段代码用于当文件夹为空时，自动删除它
						File f = new File(GlobalConfig.appRealPath
								+ document.getPicDirectoryPath());
						if (f.list().length == 0) {
							log.info("文件已经为空，系统将自动删除");
							FileUtil.delete(GlobalConfig.appRealPath
									+ document.getPicDirectoryPath());
						}
						infoMessage = "删除成功";

					}
					deleteBre(document.getId());// 删除数据库中关联的缩略图
					documentDao.deleteByKey(deletedId);
					// 写入日志
					systemLogDao.addLogData(categoryName, siteId, sessionID,
							tempCategoryName + "->" + document.getName());
					documentDao.clearCache();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			infoMessage = "删除失败";
		}
		return infoMessage;
	}

	/**
	 * 删除文档，flash、附件、 js脚本
	 * 
	 * @param ids
	 *            要删除的文档的id，此处的文档包括图片、flash、附件、 js脚本
	 */
	public String deleteDocument(String ids, String siteId, String sessionID,
			String categoryName) {
		log.debug("处理文档的删除操作");
		String infoMessage = "";
		String[] checkedIds = StringUtil.split(ids, ",");
		try {
			for (String deletedId : checkedIds) {
				Document document = documentDao.getAndNonClear(deletedId);
				String tempCategoryName = document.getDocumentCategory()
						.getName();
				if (document == null) {
					return "";
				} else {
					String localPath = GlobalConfig.appRealPath
							+ document.getPicDirectoryPath() + "/"
							+ document.getFileName();
					// 先删除实际文件
					if (FileUtil.isExist(localPath)) {
						FileUtil.delete(localPath);
						// 下面这段代码用于当文件夹为空时，自动删除它
						File f = new File(GlobalConfig.appRealPath
								+ document.getPicDirectoryPath());
						if (f.list().length == 0) {
							log.info("文件已经为空，系统将自动删除");
							FileUtil.delete(GlobalConfig.appRealPath
									+ document.getPicDirectoryPath());
						}
						infoMessage = "删除成功";
					}
					documentDao.deleteByKey(deletedId);
					// 写入日志
					systemLogDao.addLogData(categoryName, siteId, sessionID,
							tempCategoryName + "->" + document.getName());
					documentDao.clearCache();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			infoMessage = "删除失败";
		}
		return infoMessage;
	}

	/**
	 * 
	 *删除缩略图
	 * 
	 * @param pid
	 *            图片文档ID
	 */
	public void deleteBre(String pid) {
		breviaryImgDao.updateByDefine("deleteBreByDocId", "docid", SqlUtil
				.toSqlString(pid));
	}

	/**
	 * 根据文档id获取图片的路径
	 * 
	 * @param pid
	 *            文档ID
	 * @return List集合
	 */
	public List getBreviaryImgByDid(String pid) {
		return breviaryImgDao.findByDefine("findBreByDid", "docid", SqlUtil
				.toSqlString(pid));
	}

	/**
	 * 查找图片类别
	 * 
	 * @param siteid
	 *            网站id
	 * @return 返回该网站下的图片类别
	 */
	public List<DocumentCategory> findPictureCategory(String siteid) {
		List list = categoryDao.findByNamedQuery("findPictureCategoryBySiteId",
				"siteId", siteid);
		log.info("-----------categoryList.size()==========================="
				+ list.size());
		return list;
	}

	/**
	 * 查找flash类别
	 * 
	 * @param siteid
	 *            网站id
	 * @return 返回该网站下的flash类别
	 */
	public List<DocumentCategory> findFlashCategory(String siteid) {
		return categoryDao.findByNamedQuery("findFlashCategoryBySiteId",
				"siteId", siteid);
	}

	/**
	 * 查找附件类别
	 * 
	 * @param siteid
	 *            网站id
	 * @return 返回该网站下的附件类别
	 */
	public List<DocumentCategory> findAttachmentCategory(String siteid) {
		return categoryDao.findByNamedQuery("findAttachmentCategoryBySiteId",
				"siteId", siteid);
	}

	/**
	 * 查找脚本类别
	 * 
	 * @param siteid
	 *            网站id
	 * @return 返回该网站下的脚本类别
	 */
	public List<DocumentCategory> findJsCategory(String siteid) {
		List list = categoryDao.findByNamedQuery("findJsCategoryBySiteId",
				"siteId", siteid);
		log.info("-----------categoryList.size()==========================="
				+ list.size());
		return list;
	}

	/**
	 * 获取图片、flash、附件分页
	 * 
	 * @param pagination
	 *            分页对象
	 * @return 返回图片、flash、附件分页对象
	 */
	public Pagination findDocumentList(Pagination pagination) {
		pagination = documentDao.getPagination(pagination);
		return pagination;
	}

	/**
	 * 获取节点名称
	 * 
	 * @param nodeId
	 *            节点id
	 * @return nodeNameStr
	 */
	public String findNodeNameByNodeId(String nodeId) {
		String nodeNameStr = "";
		List list = categoryDao.findByNamedQuery(
				"findDocumentCategoryNameByNodeId", "id", nodeId);
		if (list!=null && list.size() > 0) {
			nodeNameStr = (String) list.get(0);
		}
		return nodeNameStr;
	}

	/**
	 * 添加js脚本文件的处理
	 * 
	 * @param sessionID
	 *            当前登录用户的id值
	 * @param siteId
	 *            当前网站的id值
	 * @param nodeid
	 *            节点id
	 * @param url
	 *            脚本文件地址
	 * @param form
	 *            文档表单
	 * @param documentService
	 *            文档业务对象
	 * @return 返回是否上传成功
	 */
	public String proccessJsFileUpload(String sessionID, String siteId,
			String nodeid, String url, DocumentForm form,
			DocumentService documentService) {
		String infoMessage = "";
		Document document = form.getDocument();
		// 设置上传的路径
		String randomFile = IDFactory.getId();// 生成随机目录
		String oldPath = SiteResource.getJsDir(siteId, false);
		FileUtil.makeDirs(oldPath + "/" + randomFile);
		String filePath = oldPath + "/" + randomFile;

		if (!FileUtil.isExist(filePath)) {
			infoMessage = "文件目录不存在";
			return infoMessage;
		}
		List<UploadedFile> list;
		String name = "";
		try {
			list = WebClientUtil.uploadFile(form, filePath, 10 * 1024 * 1024);
			int i1 = 0;
			if (list != null && list.size() != 0) {
				UploadedFile uploadedFile = list.get(0);
				String fileName = uploadedFile.getChangefileName();
				String description = document.getDescription();
				String suffixName = uploadedFile.getSuffixName();

				// 上传的是zip包
				if (FileUtil.isZip(fileName)) {
					long currentDate = System.currentTimeMillis();
					FileUtil.makeDirs(filePath + "/" + currentDate);
					FileUtil.unZip(filePath + "/" + fileName, filePath + "/"
							+ currentDate);
					File file = new File(filePath + "/" + currentDate);
					File[] files = file.listFiles();
					// 定义一个标记标示zip包里是否包含js脚本
					int flag = 0;
					// 循环获取上传zip包的js脚本
					int len = files.length;
					for (int i = 0; i < len; i++) {
						if (FileUtil.isJs(files[i].getName())) {
							// zip中含js脚本
							flag = 1;
							String suffix = StringUtil.strRChar(files[i]
									.getName(), '.');
							long newCurrentDate = System.currentTimeMillis();
							String newPath = filePath + "/" + newCurrentDate
									+ "." + suffix;
							File file1 = new File(newPath);
							files[i].renameTo(file1);
							// FileUtil.copy(newPath, filePath);
							name = StringUtil.strLChar(files[i].getName(), '.');
							String newFileName = newCurrentDate + "." + suffix;
							int size = (int) file1.length();
							addJs(name, randomFile + "/" + newFileName, suffix,
									size, siteId, description, nodeid,
									sessionID, url, documentService);
							infoMessage = "上传成功";
						}
					}
					if (flag == 0) {
						infoMessage = "您上传的文件中没有JS文件";
					} else {
						FileUtil.delete(filePath + "/" + fileName);
						FileUtil.delete(filePath + "/" + currentDate);
					}
					// 上传的是脚本
				} else {
					name = StringUtil.strLChar(uploadedFile.getFileName(), '.');
					int size = uploadedFile.getFileSize();
					addJs(name, randomFile + "/" + fileName, suffixName, size,
							siteId, description, nodeid, sessionID, url,
							documentService);
					infoMessage = "上传成功";
				}
			} else {
				infoMessage = "文件大小不能超过10M";
				log.debug(" infoMessage==========" + infoMessage);
			}
		} catch (Exception e) {
			infoMessage = "上传失败,具体请看上传说明";
			e.printStackTrace();
		}
		
		if(infoMessage.equals("上传成功")){
			// 添加js的同时，也将js添加到生成和发布目录中去
			Site site = siteDao.getAndClear(siteId);
			String publishJsPath = site.getPublishDir()+"/upload/js";
			if(!FileUtil.isExist(publishJsPath)){
				FileUtil.makeDirs(publishJsPath);
			}
			String buildJsPath = SiteResource.getBuildStaticDir(siteId, false)+"/upload/js";
			if(!FileUtil.isExist(buildJsPath)){
				FileUtil.makeDirs(buildJsPath);
			}
			// 要拷贝的js路径    filePath
			if(FileUtil.isExist(filePath)){
				FileUtil.copy(filePath, publishJsPath, true);
				FileUtil.copy(filePath, buildJsPath, true);
			}
		}
		return infoMessage;
	}

	/**
	 * 向数据库中添加js脚本类型数据
	 * 
	 * @param name
	 *            脚本名称
	 * @param fileName
	 *            文件名
	 * @param suffixName
	 *            上传文件的后缀名
	 * @param size
	 *            文件大小
	 * @param siteid
	 *            网站id
	 * @param description
	 *            描述
	 * @param nodeid
	 *            类别id
	 * @param sessionID
	 *            用户id
	 * @param url
	 *            附件地址
	 */
	private void addJs(String name, String fileName, String suffixName,
			int size, String siteid, String description, String nodeid,
			String sessionID, String url, DocumentService documentService) {
		Js js = new Js();
		js.setName(name);
		js.setFileName(fileName.substring(fileName.lastIndexOf("/") + 1,
				fileName.length()));
		js.setFileSuffix(suffixName);
		// 处理上传文件的大小
		String  fileSize = proccessFileSize(size);
		js.setFileSize(fileSize);
		String url2 = StringUtil.delete(url.toString(), "/js.do");
		js.setUrl(url2 + "/release/site" + siteid + "/upload/js/" + fileName);
		js.setLocalPath("/release/site" + siteid + "/upload/js/" + fileName);
		js.setPicDirectoryPath("/release/site" + siteid + "/upload/js/"
				+ fileName.substring(0, fileName.lastIndexOf("/")));
		js.setCreateTime(new Date());
		js.setDescription(description);
		// 文档类别
		DocumentCategory category = new DocumentCategory();
		category.setId(nodeid);
		js.setDocumentCategory(category);
		// 设置创建者
		User creator = new User();
		creator.setId(sessionID);
		js.setCreator(creator);
		// 设置网站
		Site site = new Site();
		site.setId(siteid);
		js.setSite(site);
		documentService.addDocument(js);

		// 写入日志
		DocumentCategory newcategory = categoryDao.getAndClear(nodeid);
		String param = newcategory.getName() + "->" + name;
		String categoryName = "js类别->上传js";
		systemLogDao.addLogData(categoryName, siteid, sessionID, param);
	}

	/**
	 * @param documentDao
	 */
	public void setDocumentDao(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	/**
	 * @param categoryDao
	 */
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	/**
	 * @param systemLogDao
	 *            the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}

	/**
	 * @param waterMarkDao
	 *            the waterMarkDao to set
	 */
	public void setWaterMarkDao(WaterMarkDao waterMarkDao) {
		this.waterMarkDao = waterMarkDao;
	}

	/**
	 * @param picwaterMarkDao
	 *            the picwaterMarkDao to set
	 */
	public void setPicwaterMarkDao(PicWaterMarkDao picwaterMarkDao) {
		this.picwaterMarkDao = picwaterMarkDao;
	}

	/**
	 * @param breviaryImgDao
	 *            the breviaryImgDao to set
	 */
	public void setBreviaryImgDao(BreviaryImgDao breviaryImgDao) {
		this.breviaryImgDao = breviaryImgDao;
	}

	/**
	 * @param siteDao the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

}

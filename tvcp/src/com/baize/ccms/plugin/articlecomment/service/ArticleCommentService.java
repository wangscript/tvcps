/**
 * project：通用内容管理系统
 * Company: 南京百泽网络科技有限公司
 */
package com.baize.ccms.plugin.articlecomment.service;

import com.baize.ccms.plugin.articlecomment.domain.ArticleComment;
import com.baize.ccms.plugin.articlecomment.domain.ArticleCommentsReplace;
import com.baize.ccms.plugin.articlecomment.web.form.ArticleCommentForm;
import com.baize.common.core.dao.Pagination;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CCMS</p>
 * <p>版权: Copyright (c) 2009 南京百泽网络科技有限公司</p>
 * <p>网址：http://www.baizeweb.com
 * @author 曹名科
 * @version 1.0
 * @since 2009-10-20 下午03:30:39
 * @history（历次修订内容、修订人、修订时间等） 
 */
public interface ArticleCommentService {
	/**
	 * 获取文章评论设置的值.
	 * @param form 
	 * @param siteId
	 */
	void getCommentForForm(ArticleCommentForm form,String siteId);
	/**
	 * 修改文章评论设置.
	 * @param form
	 */
	 void setCommentForForm(ArticleCommentForm form,String sessionId,String siteId);
	 /**
	  * 替换设置分页.
	  * @param pagination 分页对象
	  * @param siteId 网站ID；
	  * @return 分页
	  */
	 Pagination getPagination(Pagination pagination,String siteId);
	 /**
	  * 添加替换对象.
	  * @param form
	  */
	 String addReplaceWord(ArticleCommentForm form,String siteId,String userId);
	/**
	 * 修改过滤词.
	 * @param form
	 */
	 String modifyReplace(ArticleCommentForm form);
	 /**
	  * 根据ID查询过滤词
	  * @param id
	  * @return
	  */
	 ArticleCommentsReplace findByReplaceId(String id);
	/**
	 * 删除过滤词.
	 * @param ids 过滤词ID
	 */
	 void deleteReplace(String ids);
	 /**
	  * 获取样式.
	  * @param id
	  * 网站ID 
	  * @return
	  */
	 String getStyle(String siteId);
	 /**
	  * 保存样式.
	  * @param Id
	  * @param content
	  */
	 void setStyle(String content,String siteId);
	 /**
	  * 根据审核条件查询分页.
	  * @param pagination
	  * @return
	  */
	 Pagination getCommentOnauditededPagination(Pagination pagination,String isAudi);
	 /**
	  * 回收站查询分页.
	  * @param pagination
	  * @param isDelete
	  * @return
	  */
	 Pagination getCommentOndeletePagination(Pagination pagination);
	 /**
	  * 根据条件修改文章评论的delete,1为已经放入回收站，0为没删除.
	  * @param ids
	  * @param deleteWhere
	  */
	  void modifyCommentById(String ids,String deleteWhere);
	 /**
	  * 根据IDS删除文章评论.
	  * @param ids
	  */
	  void deleteCommentById(String ids);
	  /**
	   * 置顶.
	   * @param Id 要置顶的ID
	   * @param orders 最大的order
	   * @param isToped 是否置顶
	   * @param siteId 网站ID
	   * @param userId 用户ID
	   */
	  void modifyArticleCommentTop(String Id, String isToped,
				String siteId, String userId);
	  /**
	   * 设置精华.
	   * @param id 要设置精华的ID
	   * @param isEssence 是否精华
	   * @param siteId 网站ID
	   * @param userId 用户ID
	   */
	  void modifyArticleCommentDeleteByEssence(String id,String isEssence,String siteId,String userId);
	  /**
	   * 开始审核.
	   * @param ids 要审核的ID
	   * @param flag 是否通过
	   * @param siteId 网站ID
	   * @param userId 用户ID
	   */
	  void modifyArticleCommentDeleteByAuditid(String ids, String flag,
				String siteId, String userId);
	  /**
	   * 将文章评论还原或放入回收站
	   * @param ids 评论ID
	   * @param isDel 标记
	   * @param siteId
	   * @param userId
	   */
	  void modifyArticleCommentDelete(String ids, String isDel,
				String siteId, String userId);
	  /**
	   * 发表评论
	   * @param form
	   */
	  String commitComment(ArticleCommentForm form, String artileId);
	  /**
	   * 投票支持
	   * @param id 评论的ID
	   */
	  String addSuport(String id,String articleId);
	   /**
	    * 投票反对
	    * @param id 评论的ID
	    */
	  String addIronfoe(String id);
	   /**
	    * 获取评论,并且将样式加入
	    * @return 评论内容
	    */
	   String getAnalyStyle(Pagination pagination,ArticleCommentForm form,String articleId);
	   /**
	    * 获取文章评论分页
	    * @param pagination
	    * @return
	    */
	   Pagination getCommentPagination(Pagination pagination);
	   /**
	    * 获取评论详细
	    * @param id
	    * @return
	    */
	   ArticleComment getArticleCommentById(String id);
	   
	   /**
	    * 发布文章评论目录
	    * @param siteId
	    */
	   void publishArticleCommentDir(String siteId);
}

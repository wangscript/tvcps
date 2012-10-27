/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.onlineBulletin.domain;
        
import java.io.Serializable;
import java.util.Date;

import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.biz.usermanager.domain.User;

/**
 * <p>标题:网上公告</p>
 * <p>描述:网上公告包含的内容</p>
 * <p>模块:网上公告</p>
 * <p>版权: Copyright (c) 2009  </p>
 * @author <a href="mailto:xinyang921@gmail.com">包坤涛</a>
 * @version 1.0
 * @since 2009-10-3 下午04:24:10
 * @history（历次修订内容、修订人、修订时间等）
 */
public class OnlineBulletin implements Serializable {
	      
	   private static final long serialVersionUID = 224508496618140950L;
	   /**主建*/ 
	   private    String id;
	   
	   /**公告标题*/
	   private  String     name;    
       /**公告内容*/
	   private  String     context;
       /**截止日期*/
	   private  Date      endTime ;
	   /**创建时间*/
	   private  Date      createTime;
       /**窗口名称*/
	   private  String    windowName;
       /**窗口大小(宽度)*/              
	   private  String    widowWidth;
       /**窗口大小(高度)*/              
	   private  String    widowHeight;
       /**窗口上下边界(上)*/              
	   private  String    windowTop;
       /**窗口上下边界(左)*/              
	   private  String    windowLeft;
       /**显示工具栏*/              
	   private  boolean   showTool;
       /**显示菜单栏)*/              
	   private  boolean   showMenu;
       /**显示滚动条*/              
	   private  boolean   showScroll;
       /**可改变窗口大小*/              
	   private  boolean   changeSize;
       /**显示地址栏*/              
	   private  boolean   showAddress;
       /**显示状态信息*/              
	   private  boolean   showStatus;
	   /**当前网站*/
	   private Site       site;
	   /** 用户*/
	   private User user;
	   /** 绑定的栏目ids */
	   private String columnIds;
	   
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @return the context
		 */
		public String getContext() {
			return context;
		}
		/**
		 * @return the endTime
		 */
		public Date getEndTime() {
			return endTime;
		}
		/**
		 * @return the createTime
		 */
		public Date getCreateTime() {
			return createTime;
		}
		/**
		 * @return the windowName
		 */
		public String getWindowName() {
			return windowName;
		}
		/**
		 * @return the widowWidth
		 */
		public String getWidowWidth() {
			return widowWidth;
		}
		/**
		 * @return the widowHeight
		 */
		public String getWidowHeight() {
			return widowHeight;
		}
		/**
		 * @return the windowTop
		 */
		public String getWindowTop() {
			return windowTop;
		}
		/**
		 * @return the windowLeft
		 */
		public String getWindowLeft() {
			return windowLeft;
		}
		/**
		 * @return the showTool
		 */
		public boolean isShowTool() {
			return showTool;
		}
		/**
		 * @return the showMenu
		 */
		public boolean isShowMenu() {
			return showMenu;
		}
		/**
		 * @return the showScroll
		 */
		public boolean isShowScroll() {
			return showScroll;
		}
		/**
		 * @return the changeSize
		 */
		public boolean isChangeSize() {
			return changeSize;
		}
		/**
		 * @return the showAddress
		 */
		public boolean isShowAddress() {
			return showAddress;
		}
		/**
		 * @return the showStatus
		 */
		public boolean isShowStatus() {
			return showStatus;
		}
		/**
		 * @return the site
		 */
		public Site getSite() {
			return site;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @param context the context to set
		 */
		public void setContext(String context) {
			this.context = context;
		}
		/**
		 * @param endTime the endTime to set
		 */
		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		
		/**
		 * @param createTime the createTime to set
		 */
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		/**
		 * @param windowName the windowName to set
		 */
		public void setWindowName(String windowName) {
			this.windowName = windowName;
		}
		/**
		 * @param widowWidth the widowWidth to set
		 */
		public void setWidowWidth(String widowWidth) {
			this.widowWidth = widowWidth;
		}
		/**
		 * @param widowHeight the widowHeight to set
		 */
		public void setWidowHeight(String widowHeight) {
			this.widowHeight = widowHeight;
		}
		/**
		 * @param windowTop the windowTop to set
		 */
		public void setWindowTop(String windowTop) {
			this.windowTop = windowTop;
		}
		/**
		 * @param windowLeft the windowLeft to set
		 */
		public void setWindowLeft(String windowLeft) {
			this.windowLeft = windowLeft;
		}
		/**
		 * @param showTool the showTool to set
		 */
		public void setShowTool(boolean showTool) {
			this.showTool = showTool;
		}
		/**
		 * @param showMenu the showMenu to set
		 */
		public void setShowMenu(boolean showMenu) {
			this.showMenu = showMenu;
		}
		/**
		 * @param showScroll the showScroll to set
		 */
		public void setShowScroll(boolean showScroll) {
			this.showScroll = showScroll;
		}
		/**
		 * @param changeSize the changeSize to set
		 */
		public void setChangeSize(boolean changeSize) {
			this.changeSize = changeSize;
		}
		/**
		 * @param showAddress the showAddress to set
		 */
		public void setShowAddress(boolean showAddress) {
			this.showAddress = showAddress;
		}
		/**
		 * @param showStatus the showStatus to set
		 */
		public void setShowStatus(boolean showStatus) {
			this.showStatus = showStatus;
		}
		/**
		 * @param site the site to set
		 */
		public void setSite(Site site) {
			this.site = site;
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
		/**
		 * @return the columnIds
		 */
		public String getColumnIds() {
			return columnIds;
		}
		/**
		 * @param columnIds the columnIds to set
		 */
		public void setColumnIds(String columnIds) {
			this.columnIds = columnIds;
		}
      
	}

package com.baize.ccms.biz.usermanager.web.event;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.baize.common.core.exception.BaseBizException;
import com.baize.common.core.exception.BaseSystemException;
import com.baize.common.core.util.StringUtil;
import com.baize.common.core.web.SessionIDGenerator;
import com.baize.common.core.web.event.RequestEvent;


/**
 * 
 * SessionManager是应用了singletone的模式来实现对已经登录系统的用户的管理。已经登录的用户
 * 都是存放在后台的用户登录池中。对这些登录用户进行管理包括：添加或删除一个已登录的用户信息，
 * 检查用户是否已经存在，可以定期清理所有的已登录用户信息。
 * package: com.baize.common.core.web
 * File: SessionManager.java 创建时间:2009-1-12上午10:25:56
 * Title: 标题（要求能简洁地表达出类的功能和职责）
 * Description: 描述（简要描述类的职责、实现方式、使用注意事项等）
 * Copyright: Copyright (c) 2009 南京百泽网络科技有限公司
 * Company: 南京百泽网络科技有限公司
 * 模块: 平台架构
 * @author  娄伟峰
 * @version 1.
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class SessionManager {
	/** 日志 */
	private Logger log = Logger.getLogger(SessionManager.class);
    /**
     * 用户登录池，存放已经登录的信息，存放的格式是：用户的sessionid用户对象，以 key=object 的方式来存放。
     */
    private static Map sessionPool = null;

    /**
     * 创建SessionManager的实例
     */
    private static SessionManager sessionInstance = new SessionManager();

    /**
     * 构造器
     */
    private SessionManager() {
        this.sessionPool = Collections.synchronizedMap(new HashMap());
    }

    /**
     * 取的SessionManager的唯一实例
     * @return SessionManager的唯一实例
     */
    public static SessionManager singleton() {
        return sessionInstance;
    }

    /**
     * 获取用户登录的信息,根据用户的sessionid来获取用户的信息。
     * @param sessionid 用户登录后生成的id
     * @return UserEntry 用户对象的信息
     */
    public UserEntry getSessionEntry(String sessionid) {
        if((sessionid==null)||"".equals(sessionid.trim())) {
            return null;
        }
        String userid = "";
        String sessionId = StringUtil.trim(sessionid);
        userid=sessionId;
    /*    String [] str=sessionId.split(";");
        if (!sessionId.equals("")) {
        	userid=str[0];           
        }*/
        UserEntry userEntry = new UserEntry();
        userEntry.setSessionid(sessionId);
        userEntry.setUserLogonID(userid);
        log.debug("sessionid = " + sessionid + "//" + userEntry.getSessionid());
        log.debug("user = " + userEntry.getUserLogonID());
        return userEntry;
    }
    
    /**
     * 将登录的用户添加到已登录用户池中，即：sessoinid=用户登录ID；
     * 同时将这个信息以seesionid,用户登录名的方式存放在数据库的表中保存起来。
     * 输入参数：
     * entryargs[]:String,用户登录的参数列表，排列次序为：
     * userid:用户登录名称
     * chaneltype:String，渠道类型
     * siteId:String，网站ID
     * roleName :String 角色名
     * @throws BaseBizExeption
     */
    public String addSessionEntry( String[] entryargs, RequestEvent req) throws BaseBizException ,SQLException{
        String sessionid = SessionIDGenerator.generateSessionID(entryargs[0]);     
        Date date = new Date();
        UserEntry userEntry = new UserEntry(entryargs[0], entryargs[1],entryargs[2], date, sessionid,entryargs[3]);
        this.addSessionToPool(sessionid, userEntry);
        log.debug("userid = " + userEntry.getUserLogonID());
        log.debug("sessionid = " + userEntry.getSessionid());
        log.debug("siteid = " + userEntry.getSiteId());
        log.debug("roleName = " + userEntry.getRoleName());
        return sessionid;
    }

    /**
     * 判断用户是否已经登录，根据输入的用户的sessionid来查找对应的用户登录信息。
     * @param sessionid :用户登录后的id
     * @return  true:用户已经登录 false:用户没有登录
     */
    public boolean checkSessionPoolEntry(String sessionid) {
        boolean result = false;
        if (sessionPool.containsKey(sessionid))
            result = true;
        log.debug("checkSessionPoolEntry : userEntry = " + sessionPool.get(sessionid));
        return result;
    }

    /**
     *
     * @param sessionid
     * @param userobj
     */
    private synchronized void addSessionToPool(String sessionid, UserEntry userobj) {
        this.sessionPool.put(sessionid, userobj);
    }

    /**
     * 从用户登录池中去掉一个用户。
     * 输入参数：
     *     sessionid:用户登录后的ID
     * 返回：无
     */
    public synchronized void reomveLoginEntry(String sessionid) {
        this.sessionPool.remove(sessionid);
    }


/*    *//**
     * 判断用户是否已经登录，根据输入的用户的sessionid来查找对应的用户登录信息,
     * 分别检查内存中的Session信息和数据库中的Session信息
     * @param sessionid
     * @return
     * @throws TaxBaseSystemException
     *//*
    public boolean checkLoginUserEntry(String sessionid) throws   BaseSystemException,SQLException {
        boolean result = false;

        if (sessionid == null)return false;
        //在sessionPool中已经存在该用户
        result = checkSessionPoolEntry(sessionid);
        log.debug("checkLoginUserEntry: result = " + result);
        if (result)return result;
        result = this.checkDBUserEntry(sessionid, con);

         
        return result;
    }*/

    /**
     * 清除sessionpool中的sessionID，放在定时任务中执行。
     * @param excludeUserId
     * @throws Exception
     */
    public static synchronized void clean(String[] excludeUserId) throws Exception{
        if (sessionPool != null) {
            sessionPool.clear();
        }                 
    }

    public static synchronized int getSessionPoolSize() {
        return sessionPool.size();
    }

}

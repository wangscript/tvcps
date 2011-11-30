
package com.j2ee.cms.sys;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.j2ee.cms.common.core.util.GetMACAddress;
/**
 * 
 * <p>标题: —— 系统过滤器</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2009  
 * @author 娄伟峰
 * @version 1.0
 * @since 2009-4-11 下午03:39:30
 * @history（历次修订内容、修订人、修订时间等）
 */

public class SystemFilter implements Filter {
	private static final Logger log = Logger.getLogger(SystemFilter.class);
	public static String xx ;

    /**
     * Take this filter out of service.
     */
    public void destroy() {


    }
    /**
     *
     * @param request The servlet request we are processing
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
	throws IOException, ServletException {    	
    
    	//获取远程IP地址
    	String remoteaddr = request.getRemoteAddr();
    	//获取本地IP地址
    	String localaddr = request.getLocalAddr();
    	GetMACAddress getMACAddress = new GetMACAddress();
    	//获取MAC地址
    	String remotemacaddr = getMACAddress.getMACAddress(remoteaddr);
    	String localmaclocaladdr = getMACAddress.getMACAddress(localaddr);
    	if(!remotemacaddr.equals(xx)){
    		GlobalConfig.allMacAddress = GlobalConfig.allMacAddress +";"+ remotemacaddr;
    	}    	
    	xx = remotemacaddr;
    	String allAddr = GlobalConfig.allMacAddress;    	
    	String maxConnection[] = allAddr.split(";");    	
    	
    	if(maxConnection.length > 0 ){
    		if(GlobalConfig.maxConnection < (maxConnection.length-1)){
    			response.setContentType("text/html");
    			response.setCharacterEncoding("utf-8");
    			PrintWriter out=response.getWriter();
    			out.println("cms超过最大连接数!如想获得更大连接数，请与产品供应商联系!");    			
    			out.flush();
    			return ;
    		}
    	}
	// Pass control on to the next filter
        chain.doFilter(request, response);

    }


    /**
     * Place this filter into service.
     *
     * @param filterConfig The filter configuration object
     */
    public void init(FilterConfig filterConfig) throws ServletException {


    }

}

/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.configmanager.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;

import com.j2ee.cms.biz.configmanager.dao.SystemLogDao;
import com.j2ee.cms.biz.configmanager.domain.SystemLog;
import com.j2ee.cms.biz.configmanager.service.SystemLogService;
import com.j2ee.cms.biz.configmanager.web.form.SystemLogForm;
import com.j2ee.cms.biz.sitemanager.domain.Site;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.DateUtil;
import com.j2ee.cms.common.core.util.SqlUtil;
import com.j2ee.cms.common.core.util.StringUtil;

/**
 * <p>标题: —— 要求能简洁地表达出类的功能和职责</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: CPS</p>
 * <p>版权: Copyright (c) 2009  
 * @author 郑荣华
 * @version 1.0
 * @since 2009-9-5 上午10:12:40
 * @history（历次修订内容、修订人、修订时间等） 
 */
public class SystemLogServiceImpl implements SystemLogService {
	
	private final Logger log = Logger.getLogger(SystemLogServiceImpl.class);
	
	/** 注入日志管理dao*/
	private SystemLogDao systemLogDao;

	/**
	 * 按照网站id查找日志文件
	 * @param siteId
	 * @param pagination
	 * @return
	 */
	public Pagination findLogBySiteIdPage(String siteId, Pagination pagination) {
		pagination = systemLogDao.getPagination(pagination, "siteId", siteId);
		List list = pagination.getData();
		System.out.println("============="+list.size());
		for(int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			String category = (String) object[2];
			category = category.split("->")[0];
			object[2] = category;
		}
		return pagination;
	}
	
	/**
	 * 删除日志文件
	 * @param ids
	 */
	public void deleteLogsByIds(String ids) {
		log.debug("deletedIds============="+ids);
		ids = SqlUtil.toSqlString(ids);
		systemLogDao.deleteByIds(ids);
	}

	/**
	 * 导出日志到excel
	 * @param form
	 * @param siteId
	 * @param excelPath
	 */
	public void exportLogsToExcel(SystemLogForm form, String siteId, String excelPath) {
		String keyWords = form.getKeyWords();
		String extent = form.getExtent();
		String startTime = form.getStartTime();
		String endTime = form.getEndTime();
		List<SystemLog> list = new ArrayList<SystemLog>();
		// 按照条件查找
		if(!StringUtil.isEmpty(keyWords) && !keyWords.equals("null") && !keyWords.equalsIgnoreCase("")) {
			try {
				keyWords = new String(keyWords.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Date start = new Date();
			Date end = new Date();
			//  关键字、内容范围、时间
			if(!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)) {
				start = DateUtil.toDate(startTime);
				end = DateUtil.toDate(endTime);
			}
			Object[] values = {siteId, start, end};
			// 按照网站id和规定时间范围查询日志信息
			String[] params = {"siteId", "startTime", "endTime"};
			list = systemLogDao.findByNamedQuery("findLogsExtentTimeAndSiteId", params, values);
			if(list != null && list.size() > 0) {
				String ids = "";
				for(int i = 0; i < list.size(); i++) {
					ids += "," + list.get(i).getId();
				}
				ids = ids.replaceFirst(",", "");
				ids = SqlUtil.toSqlString(ids);
				
				String[] value = {ids, "'%"+ keyWords +"%'"};
				// 1. 按照用户名导出
				if(extent.equals("userName")) {
					String[] param = {"logIds", "userName"};
					list = systemLogDao.findByDefine("findLogsExtentUserName", param, value);
					
				// 2. 按照模块名导出	
				} else if(extent.equals("moduleName")) {
					String[] param = {"logIds", "moduleName"};
					list = systemLogDao.findByDefine("findLogsExtentModuleName", param, value);
				}
			}
			
		// 按照网站id查询所有	
		} else {
			list = systemLogDao.findByNamedQuery("findLogsBySiteId", "siteId", siteId);
		}
		this.exportExcel(list, siteId, excelPath);
	}
	
	/**
	 * 导出excel
	 * @param list
	 */
	private void exportExcel(List<SystemLog> list, String siteId, String excelPath) {
		log.debug("导出数据到excel");
		if(list != null && list.size() > 0) {
			try {
				FileOutputStream fos = new FileOutputStream(excelPath);
				WritableWorkbook wwb = Workbook.createWorkbook(fos);
			    // 创建一个工作表
			    WritableSheet ws = wwb.createSheet("系统日志", 0);
			    // 设置单元格的文字格式
			    WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLUE);
			    WritableCellFormat wcf = new WritableCellFormat(wf);
			    wcf.setVerticalAlignment(VerticalAlignment.CENTRE); 
			    wcf.setAlignment(Alignment.CENTRE); 
			    ws.setRowView(1, 500);
			    // 初始化头部(col, row)
			    ws.addCell(new Label(0, 0, "时间", wcf));
			    ws.addCell(new Label(1, 0, "用户名", wcf));
			    ws.addCell(new Label(2, 0, "模块名", wcf));
			    ws.addCell(new Label(3, 0, "描述", wcf));
			    ws.addCell(new Label(4, 0, "所属网站", wcf));
			    ws.addCell(new Label(5, 0, "ip地址", wcf));
			   
			    SystemLog systemLog = null;
			    // 循环写入excel文件
			    for(int i = 0; i < list.size(); i++) {
			    	
			    	systemLog = list.get(i);
		    		ws.addCell(new Label(0, i+1, DateUtil.toString(systemLog.getOperationTime())));
		    		ws.addCell(new Label(1, i+1, systemLog.getOperator().getName()));
		    		ws.addCell(new Label(2, i+1, systemLog.getModuleCategory().getName()));
		    		ws.addCell(new Label(3, i+1, systemLog.getOperationContent()));
		    	    ws.addCell(new Label(4, i+1, systemLog.getSite().getName()));
		    	    ws.addCell(new Label(5, i+1, systemLog.getIp()));
			    }
			    log.debug("导出成功");
			    
			    wwb.write();
			    wwb.close();
			    fos.close();
			    	
			} catch (FileNotFoundException e) {
				log.debug("文件没有找到");
			}catch (IOException e) {
				e.printStackTrace();
			}catch (WriteException e) {
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * 按照网站id删除所有的日志
	 * @param siteId
	 */
	public void deleteAllLogsBySiteId(String siteId) {
		siteId = SqlUtil.toSqlString(siteId);
		systemLogDao.updateByDefine("deleteAllLogsBySiteId", "siteId", siteId);
	}
	
	
	/**
	 * @param systemLogDao the systemLogDao to set
	 */
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
	}
}

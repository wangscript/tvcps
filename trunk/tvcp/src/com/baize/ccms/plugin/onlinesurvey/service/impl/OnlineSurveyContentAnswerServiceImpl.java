/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.plugin.onlinesurvey.service.impl;

import java.awt.Font;
import java.awt.Shape;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.AttributedString;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import com.j2ee.cms.plugin.onlinesurvey.dao.OnlineSurveyAnswerContentDao;
import com.j2ee.cms.plugin.onlinesurvey.domain.OnlineSurveyContentAnswer;
import com.j2ee.cms.plugin.onlinesurvey.service.OnlineSurveyContentAnswerService;
import com.j2ee.cms.common.core.dao.Pagination;
import com.j2ee.cms.common.core.util.SqlUtil;

/**
 * <p>
 * 标题: 网上调查问题的业务类
 * </p>
 * <p>
 * 描述: 网上调查问题添加，查找等业务
 * </p>
 * <p>
 * 模块: 网上调查问题/p>
 * <p>
 * 版权: Copyright (c) 2009 
 * 
 * @author 包坤涛
 * @version 1.0
 * @since 2009-6-14 下午02:30:23
 * @history（历次修订内容、修订人、修订时间等） 
*/
 

public class OnlineSurveyContentAnswerServiceImpl implements OnlineSurveyContentAnswerService {
	
	/** 注入问题答案dao */
	private OnlineSurveyAnswerContentDao onlineSurveyAnswerContentDao;

	public Pagination findOnlineQueryAnswerPage(Pagination pagination, String questionId) {
		return onlineSurveyAnswerContentDao.getPagination(pagination, "questionId", questionId);
	}
	
	public OnlineSurveyContentAnswer findOnlineContentAnswer(String answerId) {
		return onlineSurveyAnswerContentDao.getAndNonClear(answerId);
	}

	public void addOnlineSurveyContentAnswer(OnlineSurveyContentAnswer onlineSurveyContentAnswer) {
		onlineSurveyAnswerContentDao.save(onlineSurveyContentAnswer);
	}

	public void modifyOnlineSurveyContentAnswer(OnlineSurveyContentAnswer onlineSurveyContentAnswer, String answerId) {
		OnlineSurveyContentAnswer onlineSurveyContentAnswerEntitly = onlineSurveyAnswerContentDao.getAndClear(answerId);
		if (null != onlineSurveyContentAnswer.getAnswer()) {
			onlineSurveyContentAnswerEntitly.setAnswer((onlineSurveyContentAnswer.getAnswer()));
		}
		if (null != onlineSurveyContentAnswer.getVoteCount()) {
			onlineSurveyContentAnswerEntitly.setVoteCount((onlineSurveyContentAnswer.getVoteCount()));
		}
		onlineSurveyAnswerContentDao.update(onlineSurveyContentAnswerEntitly);
	}
	
	public void deleteOnlineSurveyAnswer(String answerId) {
		onlineSurveyAnswerContentDao.deleteByIds(SqlUtil.toSqlString(answerId));
	}
	
	public Pagination findOnlineResultPage(Pagination pagination) {
		return onlineSurveyAnswerContentDao.getPagination(pagination);
	}
	
    public  boolean imageChart3DToJpg(String histogramName, String xName, String yName, String exportPath, String questionId) { 
        /** 柱状图的标签总类及数量 */ 
        DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        List<OnlineSurveyContentAnswer> list = onlineSurveyAnswerContentDao.findByNamedQuery("findChart", "questionId", questionId);
        OnlineSurveyContentAnswer onlineSurveyContentAnswer = null;
        for(int i = 0; i < list.size(); i++){
        	onlineSurveyContentAnswer = list.get(i);
        	String answer = onlineSurveyContentAnswer.getAnswer();
        	int voteCount = Integer.parseInt(onlineSurveyContentAnswer.getVoteCount());
        	//添加数据集 
        	dataset.addValue(voteCount, "", answer);  
        }    
        // PlotOrientation.VERTICAL设置图标方向
        JFreeChart chart = ChartFactory.createBarChart3D(histogramName, xName, yName, dataset, PlotOrientation.VERTICAL, false, false, false); 
        /** 设置标题 */ 
        chart.setTitle(new TextTitle(histogramName, new Font("黑体", Font.ITALIC, 15))); 
        /** 设置图表部分 */ 
        CategoryPlot plot = (CategoryPlot) chart.getPlot(); 
        
        /** 取得横轴 */ 
        CategoryAxis categoryAxis = plot.getDomainAxis(); 
        /** 设置横轴显示标签的字体 */ 
        categoryAxis.setLabelFont(new Font("宋体", Font.BOLD,15)); 
        /** 分类标签平行显示 */ 
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
//        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 8.0));
        /** 分类标签字体 */ 
        categoryAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 18)); 
        
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setBaseItemLabelsVisible(true);
        BarRenderer r = (BarRenderer) renderer;
        r.setItemMargin(0.2);
        
        /** 取得纵轴 */ 
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis(); 
        /** 设置纵轴显示标签字体 */ 
        numberAxis.setLabelFont(new Font("宋体", Font.BOLD, 20)); 
        FileOutputStream fos = null; 
        try { 
            fos = new FileOutputStream(exportPath); 
            /** 生成png格式图片 */ 
            ChartUtilities.writeChartAsPNG(fos, chart, 400, 300);  
            fos.close(); 
            return true; 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return false; 
    } 
    
    public  boolean imagepieChart3DToJpg(String histogramName, String xName, String yName, String exportPath, String questionId) { 
        /** 饼状图的标签总类及数量 */ 
    	DefaultPieDataset dataset = new DefaultPieDataset();
        List<OnlineSurveyContentAnswer> list = onlineSurveyAnswerContentDao.findByNamedQuery("findChart", "questionId", questionId);
        OnlineSurveyContentAnswer onlineSurveyContentAnswer = null;
        for(int i = 0; i < list.size(); i++){
        	onlineSurveyContentAnswer = list.get(i);
        	String answer = onlineSurveyContentAnswer.getAnswer();
        	int voteCount = Integer.parseInt(onlineSurveyContentAnswer.getVoteCount());
        	//添加数据集 
        	dataset.setValue(answer, voteCount);
        }    
        JFreeChart chart = ChartFactory.createPieChart3D(histogramName, dataset, true, true, false);
//        chart.setTitle(new TextTitle(histogramName, new Font("黑体", Font.ITALIC, 15)));
        PiePlot3D piePlot3D = (PiePlot3D) chart.getPlot();
        piePlot3D.setStartAngle(0);  //设置角度
        piePlot3D.setDirection(Rotation.CLOCKWISE); //设置方向
        piePlot3D.setForegroundAlpha(0.6f);   //设置透明度
        piePlot3D.setNoDataMessage("No data to display");
        piePlot3D.setCircular(true);
        
        
//        piePlot3D.setLabelGenerator(new CustomLabelGenerator());
        
        /*
        // PlotOrientation.VERTICAL设置图标方向
        JFreeChart chart = ChartFactory.createBarChart3D(histogramName, xName, yName, dataset, PlotOrientation.VERTICAL, true, true, false); 
        *//** 设置标题 *//* 
        chart.setTitle(new TextTitle(histogramName, new Font("黑体", Font.ITALIC, 15))); 
        *//** 设置图表部分 *//* 
        CategoryPlot plot = (CategoryPlot) chart.getPlot(); 
        *//** 取得横轴 *//* 
        CategoryAxis categoryAxis = plot.getDomainAxis(); 
        *//** 设置横轴显示标签的字体 *//* 
        categoryAxis.setLabelFont(new Font("宋体", Font.BOLD,15)); 
        *//** 分类标签平行显示 *//* 
//      categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD); 
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 8.0));
        *//** 分类标签字体 *//* 
        categoryAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 18)); 
        
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setBaseItemLabelsVisible(true);
        BarRenderer r = (BarRenderer) renderer;
        r.setItemMargin(0.2);
        
        *//** 取得纵轴 *//* 
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis(); 
        *//** 设置纵轴显示标签字体 *//* 
        numberAxis.setLabelFont(new Font("宋体", Font.BOLD, 20)); 
        */
        FileOutputStream fos = null; 
        try { 
            fos = new FileOutputStream(exportPath); 
            /** 生成png格式图片 */ 
            ChartUtilities.writeChartAsPNG(fos, chart, 400, 300);  
            fos.close(); 
            return true; 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return false; 
    }
    
    
    /**
     * 根据传入的参数，生成柱状图，并保存到文件中，返回文件名
     * @param title String 图形标题
     * @param session HttpSession
     * @param data CategoryDataset 数据集
     * @param pw PrintWriter 输出流
     * @return String 返回图形的文件名
     */
    public static String generateBarChart(String title, HttpSession session,  CategoryDataset data, PrintWriter pw) {
        String filename = null;
        try {
            JFreeChart chart = ChartFactory.createBarChart3D(
                title, //  图表标题
                "时间", //  目录轴的显示标签
                "访问反馈量", //  数值轴的显示标签
                data, //  数据集
                PlotOrientation.VERTICAL, //  图表方向：水平、垂直
                true, //  是否显示图例(对于简单的柱状图必须是false)
                false, //  是否生成工具
                false //  是否生成URL链接
            );
            //使用ChartFactory创建柱状JFreeChart
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, info, session);
            //把生成的图片放到临时目录
            //500是图片长度，300是图片高度
            pw.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }
	public void setOnlineSurveyAnswerContentDao(OnlineSurveyAnswerContentDao onlineSurveyAnswerContentDao) {
		this.onlineSurveyAnswerContentDao = onlineSurveyAnswerContentDao;
	}
	
	static class CustomLabelGenerator implements PieSectionLabelGenerator {
        public String generateSectionLabel(PieDataset dataset, Comparable key) {
            String result = null;    
            if (dataset != null) {
                if (!key.equals("PHP")) {
                    result = key.toString();   
                }
            }
            return result;
        }
        
        public AttributedString generateAttributedSectionLabel(
                PieDataset dataset, Comparable key) {
            return null;
        }
   
    }
}
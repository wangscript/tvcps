<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>样式管理</title>
<script type="text/javascript">
	function fck_insertHtml(value){	 
		var fck = FCKeditorAPI.GetInstance("styleContent");
		fck.InsertHtml(value);	
	}
</script>
</head>
<body scroll="auto"  topMargin="0" marginwidth="0" marginheight="0" >
<form action="onlineSurveyConcret.do" method="post"  name="onlineSurveyContentForm" id="onlineSurveyContentForm">
	<input type="hidden" name="dealMethod" id="dealMethod" value="editStyle" /> 
	<input type="hidden" name="idm" id="idm" value="${onlineSurveyForm.idm}" /> 
	<input type="hidden" name="message" id="message" value="${onlineSurveyContentForm.infoMessage}" />
	<div>
		  <fieldset style="width:80%">
		  <legend>标签说明&nbsp;</legend>
			<textarea rows="5" cols="113" name="example" class="input_textarea" readonly>
网上调查script代码代码主要是对显示在前台网上调查显示的样式的控制，其中所用到的标签解释如下：
	 调查答案：&nbsp;&nbsp;<!--answer-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;调查问题：<!--question-->&nbsp;&nbsp;&nbsp;&nbsp;for循环：&nbsp; <!--for--><!--/for-->	
	 序   号： <!--number-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类型名称：<!--kindname-->&nbsp;&nbsp;&nbsp;&nbsp;链接地址：<!--url-->
			</textarea>
		 </fieldset>    
	</div>	

<table width="100%" border="0" cellspacing="1" cellpadding="0" align="center" >
	<tr>
		<td class="td_left" width="10%">	      	
	                        问卷列表显示：
		</td>
		<td>
			<input type="text" name="colCount" style="width:40px;" class="input_text_normal" value="${onlineSurveyContentForm.colCount}" id="colCount">&nbsp;列
		</td>	
	</tr> 
    <tr>
    	<td class="td_left" width="10%"> 
			文本框大小 ：
		</td>
		<td>	
			高度&nbsp;<input  type="text" name="texthight" value="${onlineSurveyContentForm.texthight}" style="width:40px;" class="input_text_normal"/>&nbsp;px
			&nbsp;&nbsp;
         	宽度&nbsp;<input  type="text" name="textwidth" value="${onlineSurveyContentForm.textwidth}" style="width:40px;" class="input_text_normal"/>&nbsp;px
		</td>
	</tr>
	<tr>
		<td align="left">
			投票样式：
		</td>
	</tr>
    <tr> 
		<td colspan="2" class="td_left" >    
	    	<FCK:editor basePath="/script/fckeditor" instanceName="styleContent" value="${onlineSurveyContentForm.styleContent}" toolbarSet="CPS_font" height="210" ></FCK:editor>
     	</td> 
	</tr>
	<tr>
		<td align="left">
			列表样式：
		</td>
	</tr>
	<tr>
		<td style="padding-left: 2px;" colspan="2">
			<textarea name="listStyle" id="listStyle" cols="115" rows="5">${onlineSurveyContentForm.listStyle }</textarea>
		</td>               
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="submit" value="保存" class="btn_normal"/>
		</td>
	</tr>
  	</table>
</form>
</body>
</html>
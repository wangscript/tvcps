<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>留言样式设置</title>
</head>
<script type="text/javascript">      
	$(function laodStyle(){
		var mess=$("#infoMessage").val();
		if(mess!=""){
			alert(mess);
			closeWindow(rightFrame.getWin());
		}
	});
	function fck_insertHtml(value){	 
		var fck = FCKeditorAPI.GetInstance("styleContent");
		fck.InsertHtml(value);	
	}
</script>
<body>
<form action="<c:url value='/guestBook.do?dealMethod=saveStyle'/>" name="guestBookForm" method="post">
<input type="hidden" name="dealMethod" id="dealMethod" value="saveStyle"/>
<input type="hidden" name="infoMessage" id="infoMessage" value="${guestBookForm.infoMessage }"/>
<div>
	  <fieldset style="width:80%">
	  <legend>标签说明&nbsp;</legend>
		<textarea rows="8" cols="110" name="example" class='input_textarea' readonly=true>
留言效果script代码"主要是对显示在前台留言内容的样式的控制，其中所用到的标签解释如下：
&nbsp;&nbsp;&nbsp;留言人:<!--author-->&nbsp;&nbsp;&nbsp;	留言时间: <!--date-->
&nbsp;&nbsp;&nbsp;留言主题 :<!--title-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;留言内容: <!--content-->
&nbsp;&nbsp;&nbsp;回复状态:<!--replyState-->&nbsp;&nbsp;&nbsp; IP地址:<!--ip--> 	
		</textarea>
	 </fieldset>    
</div>
<FCK:editor basePath="/script/fckeditor" instanceName="styleContent" value="${guestBookForm.styleContent}" toolbarSet="CPS_openbasic" height="300"></FCK:editor>
<center><input type="submit" value="保存" class="btn_normal"/></center>
</form>
</body>
</html>
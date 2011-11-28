<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>替换词设置</title>
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
<form action="<c:url value='/articleComment.do?dealMethod=editStyle'/>" name="articleCommentForm" method="post">
<input type="hidden" name="dealMethod" id="dealMethod" value="editStyle"/>
<input type="hidden" name="infoMessage" id="infoMessage" value="${articleCommentForm.infoMessage }"/>
<input type="hidden" name="ids" id="strId" value="${articleCommentForm.ids }"/>
<div>
	  <fieldset style="width:80%">
	  <legend>标签说明&nbsp;</legend>
		<textarea rows="5" cols="110" name="example" class="input_textarea" readonly>
评论效果script代码"主要是对显示在前台评论内容的样式的控制，其中所用到的标签解释如下：
    发表人：&nbsp;&nbsp;&nbsp;<!--author-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发表时间： <!--date-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评论 ID：&nbsp;<!--commentId-->
   IP 地址：&nbsp;&nbsp;<!--ip-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评论内容： <!--content-->&nbsp;&nbsp;&nbsp;&nbsp;反对票数：<!--unsupport-->
   支持票数： <!--ballot-->		 
		</textarea>
	 </fieldset>    
</div>
<c:if test="${articleCommentForm.styleContent != ''}">
<FCK:editor basePath="/script/fckeditor" instanceName="styleContent" value="${articleCommentForm.styleContent}" toolbarSet="Ccms_openbasic" height="300"></FCK:editor>
</c:if>
<c:if test="${articleCommentForm.styleContent == ''}">
<FCK:editor basePath="/script/fckeditor" instanceName="styleContent" value=" " toolbarSet="Ccms_openbasic" height="300"></FCK:editor>
</c:if>
<center><input type="submit" value="保存"></center>
</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>替换词设置</title>
</head>
<script type="text/javascript"> 
	$(document).ready(function(){
		$("#replaceWordId").focus();
	});
	
	function String.prototype.Trim(){return   this.replace(/(^\s*)|(\s*$)/g,"");}   //去掉前后空格
	function editReplace(){
			var filterword = $("#replaceFilterId").val();
			var replaceword = $("#replaceWordId").val();
			if(filterword.Trim()==""){
					alert("请填写过滤词");
					return false;
			}else if(replaceword.Trim()==""){
					alert("请填写替换词");
					return false;
			}else{
				$("#dealMethod").val("saveReplace");

				var options = {	 	
			    		url: "<c:url value='/articleComment.do'/>",
			   		 success: function(msg) {
						if(msg.trim()!=""){
					    	alert(msg);
					    	}
					    	top.document.getElementById("rightFrame").src = "<c:url value='/articleComment.do?dealMethod=replaceSetList'/>";
							closeWindow(rightFrame.getWin());
					    
			    	} 
				};
				$('#articleCommentForm').ajaxSubmit(options);
			}			
		}
		function addReplace(){
			var filterword = $("#replaceFilterId").val();
			var replaceword = $("#replaceWordId").val();
			if(filterword.Trim()==""){
					alert("请填写过滤词");
					return false;
			}else if(replaceword.Trim()==""){
					alert("请填写替换词");
					return false;
			}else{
				$("#dealMethod").val("addReplace");

				var options = {	 	
			    		url: "<c:url value='/articleComment.do'/>",
			   		 success: function(msg) {
				    	if(msg.trim()!=""){
					    	alert(msg);
					    }else{
					    	top.document.getElementById("rightFrame").src = "<c:url value='/articleComment.do?dealMethod=replaceSetList'/>";
							closeWindow(rightFrame.getWin());
					    }	
			    	} 
				};
				$('#articleCommentForm').ajaxSubmit(options);
			}			
		}
			
</script>
<body>
<form action="<c:url value='/articleComment.do'/>" method="post" name="articleCommentForm" id="articleCommentForm">
<input type="hidden" name="ids" id="strid" value="${articleCommentForm.ids}"/>
<input type="hidden" name="dealMethod" id="dealMethod"/>
<table align="center">
	<tr>
		<td><span style="color:red;">*&nbsp;&nbsp;</span>过滤词：</td>
		<td>
			<c:if test="${articleCommentForm.ids != null}">
				<input type="text" name="replace.filterWord" id="replaceFilterId" value="${articleCommentForm.replace.filterWord }" disabled="disabled"/>
			</c:if>
			<c:if test="${articleCommentForm.ids == null}">
				<input type="text" name="replace.filterWord" id="replaceFilterId" value="${articleCommentForm.replace.filterWord }" valid="string" tip="过滤词不能为空"/>
			</c:if>
		</td>
	</tr>
	<tr>
		<td><span style="color:red;">*&nbsp;&nbsp;</span>替换词：</td>
		<td> 
			<input type="text" name="replace.replaceWord" id="replaceWordId" value="${articleCommentForm.replace.replaceWord }" valid="string" tip="替换词不能为空" onkeydown="if(event.keyCode==13){return false;}"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<c:if test="${articleCommentForm.ids != null}"><input type="button" value="保存" onclick="editReplace();"/></c:if>		
			<c:if test="${articleCommentForm.ids == null}"><input type="button" value="添加" onclick="addReplace();"/></c:if>	
		</td>
	</tr>
</table>
</form>
</body>
</html>
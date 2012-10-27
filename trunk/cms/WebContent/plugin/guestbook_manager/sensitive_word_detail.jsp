<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

</head>
<script type="text/javascript">
//原来类别名称
var nameStr;
$(document).ready(function(){
	nameStr= $("#sensitiveWordId").val();
		$('#sensitiveWordId').focus();
	});
	function addWord(){
			var filterword = $("#sensitiveWordId").val();
			if(filterword.trim()==""){
				alert("请填写敏感词");
				return false;
			}
			var names=$("#SensitivewordId").val();
			var array = new Array();
			array=names.split(",");
			for(var i=0;i<array.length;i++){
				if(array[i]==filterword){
					alert("该敏感词已经存在");
					return false;
				}
			}
			
				$("#dealMethod").val("addWord");
				var options = {	 	
			    		url: "<c:url value='/guestBook.do'/>",
			   		 success: function(msg) {
						if(msg.trim()!=""){
					    	alert(msg);
					    }
					   top.document.getElementById("rightFrame").src = "<c:url value='/guestBook.do?dealMethod=wordList'/>";
					   closeWindow(rightFrame.getWin());
			    	} 
				};
				$('#guestBookForm').ajaxSubmit(options);
		}
	function editWord(){
		var filterword = $("#sensitiveWordId").val();
		if(filterword.trim()==""){
				alert("请填写敏感词");
				return false;
		}
		var names=$("#SensitivewordId").val();
		var array = new Array();
		array=names.split(",");
		
		if(filterword==nameStr){//当用户每有改敏感词时
			$("#dealMethod").val("editWord");
			var options = {	 	
		    		url: "<c:url value='/guestBook.do'/>",
		   		 success: function(msg) {
					if(msg.trim()!=""){
				    	alert(msg);
				    	}
				    	top.document.getElementById("rightFrame").src = "<c:url value='/guestBook.do?dealMethod=wordList'/>";
						closeWindow(rightFrame.getWin());
		    	} 
			};
			$('#guestBookForm').ajaxSubmit(options);
		}else{
			for(var i=0;i<array.length;i++){
				if(array[i]==filterword){
					alert("该敏感词已经存在");
					return false;
				}
			}
			$("#dealMethod").val("editWord");
			var options = {	 	
		    		url: "<c:url value='/guestBook.do'/>",
		   		 success: function(msg) {
					if(msg.trim()!=""){
				    	alert(msg);
				    	}
				    	top.document.getElementById("rightFrame").src = "<c:url value='/guestBook.do?dealMethod=wordList'/>";
						closeWindow(rightFrame.getWin());
		    	} 
			};
			$('#guestBookForm').ajaxSubmit(options);
		}
}
	function cancelPro(){
		closeWindow(rightFrame.getWin());
	}
</script>
<body>
<form action="<c:url value='/guestBook.do'/>" method="post" name="guestBookForm" id="guestBookForm">
<input type="hidden" name="dealMethod" id="dealMethod" value="addWord"/>
<input type="hidden" name=sensitiveword id="SensitivewordId" value="${guestBookForm.sensitiveword}"/>
<input type="hidden" name="ids" id="strId" value="${guestBookForm.ids}"/>
	<div align="center" style="margin-top:10px;"><span style="color:red;">*&nbsp;&nbsp;</span>敏感词:<input type="text" name="word.sensitiveWord" id="sensitiveWordId" value="${guestBookForm.word.sensitiveWord}" valid="string" tip="敏感词不能为空" onkeydown="if(event.keyCode==13){return false;}"/>
<br><br><c:if test="${guestBookForm.ids==null}"><input class="btn_normal"  type="button" onclick="addWord();" value="添加"/></c:if>
<c:if test="${guestBookForm.ids!=null}"><input type="button" onclick="editWord();" class="btn_normal" value="保存"/></c:if>
&nbsp;&nbsp;<input type="button" value="退出" onclick="cancelPro()" class="btn_normal"/>
</div>	
</form>
</body>
</html>
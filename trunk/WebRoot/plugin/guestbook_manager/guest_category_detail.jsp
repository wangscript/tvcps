<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>留言类别</title>

</head>
<script type="text/javascript">
//原来类别名称
var nameStr;
$(document).ready(function(){
	nameStr = document.getElementById("categoryId").value;//获取类别名称
		$('#categoryId').focus();
	});
	function addWord(){
			var filterword = $("#categoryId").val();
			
			if(filterword.trim()==""){
					alert("请填写类别");
					return false;
			}
			var names = $("#categoryName").val();
			var arr = new Array();
			arr = names.split(",");
			for(var i = 0; i < arr.length; i++) {
				if(arr[i] == filterword) {
					alert("该类别已存在!");
					return false;
				}
			}
				$("#dealMethod").val("addCategory");
				var options = {	 	
			    		url: "<c:url value='/guestCategory.do'/>",
			   		 success: function(msg) {
						if(msg.trim()!=""){
					    	alert("不能添加重复类别");
					    }else{
					   top.document.getElementById("rightFrame").src = "<c:url value='/guestCategory.do?dealMethod=categoryList'/>";
					   closeWindow(rightFrame.getWin());
					    }
			    	} 
				};
				$('#guestBookForm').ajaxSubmit(options);
					
		}
	function editWord(){
		var filterword = $("#categoryId").val();
		if(filterword.trim()==""){
				alert("请填写类别");
				return false;
		}
		var names = $("#categoryName").val();
		var arr = new Array();
		arr = names.split(",");
		//如果名字没被修改
		if(nameStr == filterword) {
			$("#dealMethod").val("categorySave");
			var options = {	 	
		    		url: "<c:url value='/guestCategory.do'/>",
		   		 success: function(msg) {
				    	top.document.getElementById("rightFrame").src = "<c:url value='/guestCategory.do?dealMethod=categoryList'/>";
						closeWindow(rightFrame.getWin());
				    	
					} 
			};
			$('#guestBookForm').ajaxSubmit(options); 
		} else {//如果名字被修改，并且类别存在
			for(var i = 0; i < arr.length; i++) {
				if(arr[i] == filterword) {
					alert("该类别已存在!");
					return false;
				}
			}
			$("#dealMethod").val("categorySave");
			var options = {	 	
		    		url: "<c:url value='/guestCategory.do'/>",
		   		 success: function(msg) {
				    	top.document.getElementById("rightFrame").src = "<c:url value='/guestCategory.do?dealMethod=categoryList'/>";
						closeWindow(rightFrame.getWin());
					} 
			};
			$('#guestBookForm').ajaxSubmit(options);
		}	
	}
</script>
<body>
<form action="<c:url value='/guestCategory.do'/>" method="post" name="guestBookForm" id="guestBookForm">
<input type="hidden" name="dealMethod" id="dealMethod"/>
<input type="hidden" name="categoryName" id="categoryName" value="${guestBookForm.categoryName}"/>
<input type="hidden" name="ids" id="strId" value="${guestBookForm.ids}"/>
	<center><div align="center"><span style="color:red;">*&nbsp;&nbsp;</span>类别名称:<input type="text" name="guestCategory.categoryName" id="categoryId" value="${guestBookForm.guestCategory.categoryName}"valid="string" tip="类别不能为空" onkeydown="if(event.keyCode==13){return false;}"/>

<c:if test="${guestBookForm.ids==null}"><br/><br/><input type="button" onclick="addWord();" value="添加" class="btn_normal"/></c:if>
<c:if test="${guestBookForm.ids!=null}"><br/><br/><input type="button" onclick="editWord();" value="保存" class="btn_normal"/></c:if>

<input type="reset" value="重置" class="btn_normal"/></div>	</center>
</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>发布列表</title>
	<%@include file="/templates/headers/header.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			if($("#message").val() != null && $("#message").val() != "") {
				rightFrame.window.location.href = "<c:url value='/publish.do?dealMethod=&"+ getUrlSuffixRandom() + "'/>";
			}
		});
		//刷新
		function button_refresh_onclick() {
			window.location.reload();
		}
		//发布
		function button_publish_onclick() {
			$("#strid").val($("#xx").val());
			$("#dealMethod").val("publishArticles");
			$("#publishForm").submit();
		}
		//删除
		function button_delete_onclick() {
			if($("#xx").val() == ""){
				alert("请选择记录");
				return false;
			}
			if(confirm("确定删除")){
				$("#strid").val($("#xx").val());
				$("#dealMethod").val("deleteBuildArtilces");
				$("#publishForm").submit();
			}
		}
		//清空
		function button_clear_onclick(){
			if(confirm("确定清空")){
				$("#dealMethod").val("clear");
				$("#publishForm").submit();
			}
		}
	</script>
</head>
<body>
<div class="currLocation">发布列表</div>
<form id="publishForm" name="publishForm" action="<c:url value='/publish.do'/>">
	<input type="hidden" id="dealMethod" name="dealMethod" value="publishArticles" />
	<input type="hidden" id="columnIds" name="columnIds"/>
	<input type="hidden" name="ids" id="strid"/>
	<input type="hidden" name="message" id="message" value="${publishForm.infoMessage}" />
	<input type="hidden" id="publishType" name="publishType" value="<%=request.getParameter("publishType")%>"/>
	<complat:button buttonlist="delete|0|clear|0|publish|0|refresh"  buttonalign="left"/>
	<complat:grid ids="xx" width="*,*,*,*,*"  head="文章标题,创建时间,所属栏目,所属网站,发布状态"  
		coltext="[col:5,false:失败,true:发布中, :发布]" 	page="${publishForm.pagination}" form="publishForm" action="publish.do" />
</form>
</body>
</html>

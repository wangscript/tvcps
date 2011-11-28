<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>生成列表</title>
	<%@include file="/templates/headers/header.jsp"%>
	<script type="text/javascript">
	 
		//刷新
		function button_refresh_onclick() {
			window.location.reload();
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
				$("#buildForm").submit();
			}
		}
		//清空
		function button_clear_onclick(){
			if(confirm("确定清空")){
				$("#dealMethod").val("clear");
				$("#buildForm").submit();
			}
		}
	</script>
</head>
<body>
<div class="currLocation">生成列表</div>
<form id="buildForm" name="buildForm" action="<c:url value="/build.do"/>" method="post">		
	<input type="hidden" name="ids" id="strid"/>	
	<input type="hidden" name="dealMethod" id="dealMethod"/>
	<complat:button buttonlist="delete|0|clear|0|refresh"  buttonalign="left"/>
	<complat:grid ids="xx" width="*,*,*,*,*"  head="文章标题,创建时间,所属栏目,所属网站,生成状态"  
			coltext="[col:5,false:失败,true:生成中, :生成中]" 	page="${buildForm.pagination}" form="buildForm" action="build.do" />
</form>
</body>
</html>

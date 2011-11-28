<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>

<head>
<title>文档管理中的图片管理</title>

<style type="text/css"> 
      #large{
	  	height:140px;
	  	weight:200px;
	  	float:left;
	  	margin-left:25px;
	  	margin-top:20px;	
	  } 
</style>
<script type="text/javascript">

function button_add_onclick(ee){	
	var url = "<c:url value='/module/document_manager/picture/add_water_font.jsp'/>";
	win = showWindow("addWaterFonts","新增文字水印",url, 293, 0, 337, 353);	 
}
function button_delete_onclick(ee){	
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null){
		alert("请至少选择一条记录操作！");
		return false;
	}
	if(confirm("确定要删除吗？")){
		document.getElementById("strid").value = ids;
	    $("#dealMethod").val("deleteWaterFont");
		$("#waterMarkForm").submit();  
	}else
		return false;
	}
		function show(id) {
			win = showWindow("showPic","编辑文字水印","<c:url value='/waterMark.do?dealMethod=findWaterFont&ids="+id+"'/>", 293, 0, 337, 353);
			}
</script>
</head>

<body>
    <div class="currLocation">系统设置→ 网站水印设置→ 文字水印</div>
	<form id="waterMarkForm" action="<c:url value='/waterMark.do'/>" method="post" name="waterMarkForm">	
        <input type="hidden" name="dealMethod" id="dealMethod"/>
    	<input type="hidden" name="ids" id="strid"/>
		<input type="hidden" name="url" id="url"/>
		<complat:button  name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="*,*,*,*,*"  
		head="文字内容,创建人,操作时间,所属网站,操作" 
		element="[1,link,onclick,show][5,button,onclick,show,修改]"
		page="${waterMarkForm.pagination}" form="waterMarkForm" action="waterMark.do"/>
        <div id="large"></div> 
	</form>
</body> 
</html>










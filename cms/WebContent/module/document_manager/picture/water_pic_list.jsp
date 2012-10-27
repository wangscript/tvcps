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
 var picIds = new Array;
var  currIdAndRowNum=0;
$(document).ready(function() {			
	$(":checkbox").each(function(i){
		picIds[i] = $(this).val();//将所有图片id放入数组
	}); 
});
function button_add_onclick(ee){	
	var url = "<c:url value='/module/document_manager/picture/add_water_pic.jsp'/>";
	win = showWindow("addWaterFont","新增图片",url, 293, 0, 531, 476);	 
}
function button_delete_onclick(ee){	
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null){
		alert("请至少选择一条记录操作！");
		return false;
	}
	if(confirm("确定要删除吗？")){
		document.getElementById("strid").value = document.getElementById("xx").value;

	    $("#dealMethod").val("delPicWater");
		$("#waterMarkForm").submit();  
	}else
		return false;
}
		function show(id) {
			for (var i=1; i < picIds.length; i++) {
				if (picIds[i] == id) {
					currIdAndRowNum = id+","+i;
				}
			}
			var dd = id + "_4";
			win = showWindow("showPic","预览图片","<c:url value='/module/document_manager/picture/show_water_pic.jsp'/>", 293, 0, 950, 540);
			}


		//就将ID和行号
		function getIdAndRowNum() {
			return currIdAndRowNum;
		}
		//获取当前id;
		function getId(id) {
			return picIds[id];
		}
		//获取所有图片ID；
		function getArray(){
			return picIds;
		}
		//获取图片路径
		function getPicPath(id) {
			return $("#"+id+"_4").html();
		}

		
</script>
</head>

<body>
    <div class="currLocation">系统设置→ 网站水印设置→ 图片水印</div>
	<form id="waterMarkForm" action="<c:url value='/waterMark.do'/>" method="post" name="waterMarkForm">	
        <input type="hidden" name="dealMethod" id="dealMethod" value="picWater"/>
    	<input type="hidden" name="ids" id="strid"/>
		<input type="hidden" name="id" id="id""/>
		<input type="hidden" name="url" id="url"/>
		<complat:button  name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="*,*,*,0,*,*"  
		head="图片名称,创建人,创建时间,,所属网站,预览" 
		element="[1,link,onclick,show][6,button,onclick,show,预览]"
		page="${waterMarkForm.pagination}" form="waterMarkForm" action="waterMark.do"/>
        <div id="large"></div> 
	</form>
</body> 
</html>










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
		// 所有图片ID
		var picIds = new Array;
		// 当前选中id和行号，中间","隔开
		var currIdAndRowNum = 0;
		//循环获取checkbox id
		$(document).ready(function() {
			/*if(document.getElementById("message").value == "删除成功") {
				top.reloadAccordion("/${appName}/module/document_manager/refresh_Tree.jsp");
				var url = "<c:url value='/picture.do?dealMethod=&nodeId=${columnForm.nodeId}&operationType=column&localNodeName=${columnForm.localNodeName}&" + getUrlSuffixRandom() + "'/>";
				parent.changeFrameUrl("rightFrame", url);
			}*/
			$(":checkbox").each(function(i){
				picIds[i] = $(this).val();//将所有图片id放入数组
			}); 
		});
	
		function button_add_onclick(ee){	
			
		var url = "<c:url value='/picture.do?dealMethod=getColsWater&nodeId=${documentForm.nodeId}&" + getUrlSuffixRandom() + "'/>";
			win = showWindow("newpicture","上传图片",url, 293, 0, 495, 450);	 
	    }
		
		function button_delete_onclick(ee){	
			var ids = document.getElementById("xx").value;
			if(ids == "" || ids == null){
				alert("请至少选择一条记录操作！");
				return false;
			}
			if(confirm("确定要删除吗？")){
				document.getElementById("strid").value = document.getElementById("xx").value;
			    $("#dealMethod").val("delete");
				$("#documentForm").submit();  
			}else
				return false;
		}

		function closeNewChild() {
			closeWindow(win);
		}
		function closeDetailChild() {
			closeWindow(win);
		}
		function closeSetChild() {		
			closeWindow(win);
		}

		function getSmall(){
			min();
		}
		function getBig(){
			max();
		}

		//预览图片
		function show(id) {
			for (var i=1; i < picIds.length; i++) {
				if (picIds[i] == id) {//如果ID=当前ID；就将ID和行号存储
					currIdAndRowNum = id+","+i;
				}
			}
			var dd = id + "_8";
			document.getElementById("url").value = $("#"+dd+"").html();
			win = showWindow("showPic","预览图片","<c:url value='/module/document_manager/picture/showPic.jsp'/>", 293, 0, 950, 540);
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
			return $("#"+id+"_8").html();
		}
</script>
</head>
<body>
    <div class="currLocation">文档管理→ 图片类别→ ${documentForm.nodeNameStr}</div>
	<form id="documentForm" action="picture.do" method="post" name="documentForm">	
        <input type="hidden" name="dealMethod" id="dealMethod"/>
        <input type="hidden" name="nodeId" id="nodeId" value="${documentForm.nodeId }"/>
		<input type="hidden" name="message" id="message" value="${documentForm.infoMessage}"/>
        <input type="hidden" name="ids" id="strid"/>
		<input type="hidden" name="url" id="url"/>
		<complat:button  name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="*,*,*,*,*,*,0,0"  
		head="图片,图片名称,图片类型,图片大小,创建时间,描述, , " 
		element="[1,link,onclick,show]"
		page="${documentForm.pagination}" form="documentForm" action="picture.do"/>
        <div id="large"></div> 
	</form>
</body> 
</html>










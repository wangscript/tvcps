<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>指定标题链接栏目</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<script>

	var firstWin = top.getWin();		
	var temp = firstWin.split("###");
	var dialogProperty = temp[0];
	var detailFrame = "_DialogFrame_"+dialogProperty;	 		   
	var tempWindow = top.document.getElementById(detailFrame).contentWindow;
	var secondTempWindow = tempWindow.document.getElementById("unitEditArea").contentWindow;		
	var secondWin = secondTempWindow.getWin();		
	var secondtemp = secondWin.split("###");
	var seconddialogProperty = secondtemp[0];
	var seconddetailFrame = "_DialogFrame_"+seconddialogProperty;	
	var secondWindow = top.document.getElementById(seconddetailFrame).contentWindow;	
		

	function button_sure_onclick(ee) {		
		if($("#tree_checkedIds").val() == "") {
			alert("请选择指定栏目");
			return false;
		}			
		secondTempWindow.document.getElementById("morefixedColumnExample").value = $("#tree_checkedTexts").val(); 
		secondTempWindow.document.getElementById("morefixedColumn").value = $("#tree_checkedIds").val() + "##" + $("#tree_checkedTexts").val();	
		$("#dealMethod").val("findFieldCode");	
		var nodeId = $("#tree_checkedIds").val();
		var type = $("#contextFrom").val();
		if(nodeId.split(",").length > 1) {
			nodeId = 0;
		}
		$.ajax({
			url : '<c:url value="/titleLink.do?dealMethod=findFieldCode&nodeId="/>'+ nodeId +"&contextFrom="+ type,
			type: "post",
		success : function(msg) {
				var str = msg.split("###");
				var selobj = secondTempWindow.document.getElementById("fieldCode");
				selobj.disabled = false;  
				if(str[0] != "") {
					var attributeName = str[0];
					var attributeFieldName = str[1];
					var name = attributeName.split(":::");			 // 标题
					var fieldName = attributeFieldName.split(":::"); // title
					selobj.length = name.length+1;
					selobj.options[0].value = "";
					selobj.options[0].text = "---字段标签---";
					for(var i = 0; i < name.length; i++) {
						selobj.options[i+1].value = "<!--"+fieldName[i]+"-->";
						selobj.options[i+1].text = name[i];
					}
				} else {   
					selobj.length = 1;
					selobj.options[0].value = "";
					selobj.options[0].text = "---字段标签---";
				}
				top.closeWindow(secondWin);		    				  	
			}
	});
		
	}
	

	     
	function button_quit_onclick(ee) {	
		top.closeWindow(secondWin);	
	}
</script>

</head>
<body>
<form action="<c:url value="/titleLink.do"/>" id="titleLinkForm" name="titleLinkForm" >
	<div id="treeboxbox_tree" style="width:250; height:150;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
		<complat:tree  unique="column" classname="Column" checkbox="true" treeurl="../../../../column.do?dealMethod=findTemplateSetColumnLinkTree&nodeId=0&nodeName=null"/>
	</div>
	<input type="hidden" name="nodeId" id="nodeId"/>
 
	<input type="hidden" name="dealMethod" id="dealMethod"/>
	<input type="hidden" name="contextFrom" id="contextFrom" value="<%=request.getParameter("type")%>"/>
 	<input  type="button" name="button_sure" class="btn_normal" value="确定" onClick="javascript:button_sure_onclick(this);" >
	<input  type="button" name="button_quit" class="btn_normal" value="退出" onClick="javascript:button_quit_onclick(this);" >
</form>
</body>
</html>
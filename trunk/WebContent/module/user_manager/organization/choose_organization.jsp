<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>选择机构</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<script>
//新增加的方法
var temp = rightFrame.win.split("###");
var dialogProperty = temp[0];
var detailFrame = "_DialogFrame_"+dialogProperty;	 		   
var tempWindow = top.document.getElementById(detailFrame).contentWindow;

function button_sure_onclick(ee){		
	
		var checkid = document.getElementById("checkid").value;
		var orgIds = tempWindow.document.getElementById("orgIds").value;
		if(document.getElementById("checkid").value == ""){
			alert("请选择一个机构");
			return false;
		}
		//当前自己的节点
		var id = tempWindow.document.getElementById("id").value;
		var ids = orgIds.split(",");
		for(var i = 0; i < ids.length; i++) {
			if(ids[i] == checkid) {
				alert("不能选择该机构下的子机构！");
				return false;
			}else if(checkid == id){
				alert("不能选择自己作为自己的父机构！");
				return false;
			} 
		}
		tempWindow.document.getElementById("pid").value = document.getElementById("checkid").value;	
		tempWindow.document.getElementById("pname").value = document.getElementById("checkname").value;	
		tempWindow.document.getElementById("pname").focus();
		document.all("dealMethod").value="getChildName";	
	 	var options = {	 	
 		    	url: "<c:url value="/organization.do"/>",
 		    success: function(msg) { 		  
 		
	 		    tempWindow.document.getElementById("orgNames").value = msg;   		
	 		    tempWindow.closeChild();
 		    } 
 		  };
		$('#organizationForm').ajaxSubmit(options);			
}

function tree_onclick(node){	
	document.getElementById("checkid").value = node.id;
	document.getElementById("checkname").value = node.text;
}
     
function button_quit_onclick(ee){	
	  tempWindow.closeChild();
}

function test2(){
	var temp = rightFrame.win.split("###");
	var dialogProperty = temp[0];
	var detailFrame = "_DialogFrame_"+dialogProperty;	
	//第一种方法
	var temp2 = top.document.getElementById(detailFrame).contentWindow;	
	//var xx  = temp2.getChildOrgWin();
//	var xx = temp2.childOrgWin;	
//	top.closeWindow(xx);
	//第二种方法
	temp2.closeChild();
}
</script>

</head>
<body>
	<form id="organizationForm"  action="<c:url value="/organization.do"/>" method="post" name="organizationForm">
<div style="width:250px;height:200px; margin: 10px 24px;">
		<div id="treeboxbox_tree" style="width:250; height:170;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
			<complat:tree  unique="organization" treeurl="../../../organization.do?dealMethod=gettree"  />
		</div>		
		<input type="hidden" name="checkid" id="checkid"/>
		<input type="hidden" name="checkname" id="checkname"/>
		<input type="hidden" name="dealMethod" id="dealMethod"/>	
		<div style="text-align:center;">	
		<input  type="button"  class="btn_normal"    name="button_sure" value="确定" onClick="javascript:button_sure_onclick(this);" >&nbsp;&nbsp;&nbsp;&nbsp;
		<input  type="button"  class="btn_normal"   name="button_quit" value="退出" onClick="javascript:button_quit_onclick(this);" >
<!-- <input type="button" name="tt" id="tt" value="ttttttttt" onclick="test2()"/> -->
		</div>
</div>
</form>
</body>
</html>
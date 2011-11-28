<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户栏目权限设置</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
	<style type="text/css">
	 #saveArea{
	 	padding:400px 0 20px 280px;
	 }
	</style>
<script language="javascript"><!--

	//页面加载时打开左侧操作页面
	$(document).ready(function() {		
		$("#changeSiteId").val("${userForm.siteId}");
	 
	}); 

	// 退出 
	function button_quit_onclick(ee){	
		rightFrame.closeNewChild();
	}
	// 保存
	function button_save_onclick(ee){	
		var frameobj = document.frames("rightFrame");	 
		//操作ID
		$("#stroperationid").val(frameobj.document.getElementById("checkedTreeIds").value);
		$("#setChild").val(frameobj.document.getElementById("setChildColumn").checked);		
		document.all("dealMethod").value="setColumnSave";
	 
	 	var options = {	 	
 		    	url: "user.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		  };
		$('#userForm').ajaxSubmit(options);	
	}
	//点击左侧的栏目树触发的事件
	function tree_onclick(node){
		$("#itemid").val(node.id);	
		document.frames("rightFrame").document.location="user.do?dealMethod=operationlist&itemid="+node.id+"&siteId="+$("#siteId").val()+"&id="+$("#userid").val()+"&operationType=${userForm.operationType}&t="+ new Date();		
	}


	//根据选择的网站切换栏目及操作	
	function changeSiteColumn(obj){ 
//		url2 = "user.do?dealMethod=getColumnTree&id=${userForm.id}&siteId="+obj.value+"";  
 		document.getElementById("siteId").value = obj.value;
		document.userForm.action="user.do?dealMethod=findColumn&id="+$("#userid").val()+"&operationType="+$("#operationType").val()+"&siteId="+obj.value; 	
		$('#userForm').submit();
	
		

	}
//
--></script>
</head>
<body style="background-color: white">
  	<form id="userForm" action="<c:url value="/user.do"/>" method="post" name="userForm">
		<input type="hidden" name="dealMethod" id="dealMethod" value="setColumnSave"/>		
		<input type="hidden" name="operationid" id="stroperationid"/>
		<input type="hidden" name="itemid" id="itemid"/>
		<input type="hidden" name="message" id="message" value="${userForm.infoMessage}"/>	
		<input type="hidden" name="id" id="userid" value="${userForm.id}"/>
		<input type="hidden" name="siteId" id="siteId" value="${userForm.siteId}"/>
		<input type="hidden" name="setChild" id="setChild"/>
		<div style="padding:2px 0 0 25px;top:4px">
				<span>请选择网站：</span>
				<select id="changeSiteId" name="changeSiteId" onchange="changeSiteColumn(this)">
						<c:forEach items="${userForm.siteList}" var="site">
							 <option value="${site.id}">${site.name}</option>

						</c:forEach>
				</select>
		</div> 
		<div id="main" style="position:absolute; left:13px; top:10px; width:545px; height:390px; z-index:1">			
			<div id="left" style="position:absolute; left:14px; top:17px; width:217px; height:360px; z-index:2">	
	            <div id="column" style="width:200;height:360; ;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">						          		
	             	<complat:tree treeurl="${userForm.treeUrl}" pageurl="" siteid="${userForm.siteId}"/>					 
				</div>     					
			</div>
			<div id="right" style="position:absolute; left:220px; top:17px; width:410px; height:360px; z-index:3;background:white">					
			 	<font style="font-size: 12px;font-style: 宋体" >如果不选择单个栏目,则默认对网站进行设置</font>
				<iframe  style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%;background:white; Z-INDEX: 1" 
							src="user.do?dealMethod=operationlist&itemid=0&id=${userForm.id}&siteId=${userForm.siteId}"  name="rightFrame" id="rightFrame"  frameborder="0" scrolling="auto"></iframe>		
					 
			</div>
		</div>
		<div id="saveArea" class="form_cls">			
			<ul>
				<li>		
			 		<input  type="button"  value="保存" class="btn_normal"  name="button_save" onClick="javascript:button_save_onclick(this);" >			
					<input  type="button"  value="退出" class="btn_normal"  name="button_quit" onClick="javascript:button_quit_onclick(this);" >
				</li>
			</ul>
		</div>			
		</div>
	</form>
</body>
</html>
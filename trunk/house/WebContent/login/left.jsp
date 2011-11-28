<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理后台登录</title>
<script language="javascript" src="<c:url value="/script/jquery.ztree.core-3.0.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/style/zTreeStyle/zTreeStyle.css"/>"、>
	<SCRIPT LANGUAGE="JavaScript">
		<!--
		var setting = {
			data: {
				key: {
					//url:"url"
				},
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick
			}
		};

		var zNodes =[
		 			{ id:1, pId:0, name:"家政服务平台", open:true},		 		
		 			{ id:11, pId:1, name:"业务管理", open:true},
		 			{ id:111, pId:11, name:"业主钟点工需求", url:"<c:url value="/demandApply/queryDemandApply.shtml"/>",target:"rightFrame"},
		 			{ id:112, pId:11, name:"家政服务人员管理 ", url:"<c:url value="/servicePerson/queryServicePerson.shtml"/>",target:"rightFrame"},
		 			{ id:113, pId:11, name:"家政公司管理", url:"<c:url value="/houseKeeping/queryHouseKeeping.shtml"/>",target:"rightFrame"},
		 			{ id:12, pId:1, name:"系统管理", open:true},
		 			{ id:121, pId:12, name:"管理员用户管理", url:"<c:url value="/adminuser/queryAdminUser.shtml"/>",target:"rightFrame"},
		 			{ id:122, pId:12, name:"小区管理", url:"<c:url value="/village/queryVillage.shtml"/>",target:"rightFrame"},
		 			{ id:123, pId:12, name:"服务人员状态管理", url:"<c:url value="/personStatus/queryPersonStatus.shtml"/>",target:"rightFrame"},
		 			{ id:124, pId:12, name:"家政服务类型管理", url:"<c:url value="/houseKeepType/queryHouseKeepType.shtml"/>",target:"rightFrame"}
		 	
		 			 			
		 					 			
		 		];
 		

		var className = "dark";
		function beforeClick(treeId, treeNode, clickFlag) {
			className = (className === "dark" ? "":"dark");			
			return (treeNode.click != false);
		}
		
		function onClick(event, treeId, treeNode, clickFlag) {	
			if(treeNode.url != "undefined" && undefined != null){		
				changeFrameUrl("rightFrame",treeNode.url);
			}
		}		
		


	$(document).ready(function(){
			$.fn.zTree.init($("#leftTree"), setting, zNodes);
		});
		//-->
	</SCRIPT>
</head>
<body>
	<div id="leftTree" class="ztree"></div>
</body>
</html>
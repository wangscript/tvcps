<%@ page language="java" contentType="text/html; charset=utf-8" import="com.j2ee.cms.common.core.util.DateUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>机构管理</title>
<%@include file="/templates/headers/header.jsp"%>
<script>
	$(document).ready(function() {
		var temp = "${organizationForm.temp}"
		 
		if(temp == "delete"){			
		 
	    	top.reloadAccordion("/${appName}/module/user_manager/organization/index2.jsp?t="+new Date()+"");
		//	top.document.getElementById("rightFrame").src="<c:url value='/organization.do?dealMethod='/>";	
	 	
	    	top.dhxAccord.cells("userManager").attachURL("/"+"${appName}/module/user_manager/user/index.jsp?t="+new Date()+"");		 		  	
		}
		if(document.getElementById("message").value!=null &&  document.getElementById("message").value!=""){
			alert(document.getElementById("message").value);	
		//	parent.dhxLayout.cells("c").attachURL("/"+"${appName}"+"/module/user_manager/organization/index.jsp");	
		}
	});
	
	function button_add_onclick(ee){	
		win = showWindow("neworganization","新增机构","organization.do?dealMethod=showDetail&nodeId="+$("#nodeId").val(),0,0,560,320);	
	}
	
	function button_delete_onclick(ee){	
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null){
			alert("请至少选择一条记录操作！");
			return false;
		}
		if(confirm("你确定要删除吗？")) {
			document.getElementById("strid").value = document.getElementById("xx").value;
			document.all("dealMethod").value="delete";				
		    document.forms[0].submit(); 	
		  
		} else { 
	 		return false;
		 }
	}
	
	function orgdetail(id){
		win = showWindow("organizationdetail","机构信息","organization.do?dealMethod=detail&id="+id+"",0,0,560,320);
	}
	
	function closeNewChild() {	
		closeWindow(win);
	}
</script>
</head>
<body>
	<form id="orgForm" action="<c:url value="/organization.do"/>" method="post" name="organizationForm">
	<div class="currLocation">机构管理<complat:guide className="Organization" nodeId="${organizationForm.nodeId}" sign="→ " ></complat:guide>
	</div>
		<input type="hidden" name="dealMethod" id="dealMethod"  value="${organizationForm.dealMethod}" />
		<input type="hidden" name="ids" id="strid"/>
 		<input type="hidden" name="message" id="message" value="${organizationForm.infoMessage}" />
		<input type="hidden" name="nodeId" id="nodeId" value="${organizationForm.nodeId}"/> 
		<complat:button  buttonlist="add|0|delete"  buttonalign="left"  />
		<complat:grid ids="xx" width="*,*" head="机构名称,上级机构" 
		element="[1,link,onclick,orgdetail]" 
		page="${organizationForm.pagination}" form="orgForm" action="orginaziton.do"/>
	</form>
</body>
</html>

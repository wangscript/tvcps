<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>	
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
 <script>
function orgdetail(id){  
	var categoryId=$("#categoryId").val();
    var url = "<c:url value='/infoFilter.do?dealMethod=updateOne&categoryId="+categoryId+"&authorId="+id+"'/>";
 //   alert(id); 
    
    win = showWindow("generalSystemSet", "修改信息过滤设置", url, 250, 90, 370, 250);
}

function button_add_onclick(ee) {	 
	     var categoryId=$("#categoryId").val();             
	     var url = "<c:url value='/infoFilter.do?dealMethod=detail&categoryId="+categoryId+"'/>";
      win = showWindow("generalSystemSet", "信息过滤设置", "infoFilter.do?dealMethod=detail&categoryId="+categoryId+"", 250, 90, 400, 250);		 
}

function closeNewChild() {	
	closeWindow(win);
}
function button_delete_onclick(ee) {	
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null) {
		alert("请至少选择一条记录操作！");
		return false;
    }
	if(confirm("你确定要删除吗？")){
		document.getElementById("id").value = document.getElementById("xx").value;
		document.all("dealMethod").value = "delete";	
	    document.forms[0].submit(); 
	} else { 
 		  return false;
 	}
}
 </script>
</head> 
<body>
	
<form id="generalSystemSetForm" action="<c:url value="/infoFilter.do"/>" method="post" 

name="informationFilterForm">
	<div class="currLocation">系统设置→信息过滤设置
	 </div>
         <input type="hidden" name="informationFilter.id" id="id"  value="${informationFilterForm.informationFilter.id}"/>
        <input type ="hidden" name="categoryId"  id="categoryId" value="${informationFilterForm.categoryId}"/>
		<input type="hidden" name="dealMethod" id="dealMethod"  value="${informationFilterForm.dealMethod}" />		
		<complat:button  buttonlist="add|0|delete"  buttonalign="left"  />
		<complat:grid ids="xx" width="*,*,*,*,*,*" head="替换前字段1,替换前字段2,替换后字段1,替换后字段2,状态,创建时间" element="[1,link,onclick,orgdetail]" page="${informationFilterForm.pagination}"
		 coltext="[col:5,true:启用,false:禁用]"  form="informationFilterForm" action="infoFilter.do"/>
</form>
</body>
</html>

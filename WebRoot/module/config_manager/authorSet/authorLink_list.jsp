<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>热点链接设置</title>	
 <script>
function orgdetail(id){
	var categoryId=$("#categoryId").val();
	//alert(categoryId);
    var url = "<c:url value='/author.do?dealMethod=linkdetail&categoryId="+categoryId+"&authorId="+id+"'/>";
   // alert
     // alert(url); 
    win = showWindow("generalSystemSet", "热点连接", url, 250, 90, 370, 250);
}

function button_add_onclick(ee) {	 

	     //alert("111111111111111");
	     var categoryId=$("#categoryId").val();
	  //   alert(categoryId);
         //window.location.href="<c:url value='/author.do?dealMethod=add'/>";
         var url = "<c:url value='/author.do?dealMethod=detail&categoryId="+categoryId+"'/>";
	     //var url = "<c:url value='/module/config_manager/authorSet/author_detail.jsp'/>";
         win = showWindow("generalSystemSet", "热点链接", "author.do?dealMethod=linkdetail&categoryId="+categoryId+"", 250, 90, 370, 241);	
         //window.location.href="<c:url value='/authorSet/author_detail.jsp'/>";                       
         //	win = showWindow("newcolumn", "新增栏目", "<c:url value='/column.do?dealMethod=findAllFormats&nodeId=${columnForm.nodeId}&nodeName=${columnForm.nodeName}'/>", 250, 90, 360, 341);	 
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
			document.all("dealMethod").value = "deleteLink";	
		    document.forms[0].submit(); 
		} else { 
	 		  return false;
	 	}
}
 </script>
</head> 
<body>
	<form id="generalSystemSetForm" action="<c:url value="/author.do"/>" method="post" name="generalSystemSetForm">
	<div class="currLocation">系统设置→热点链接
	 </div>
        <input type="hidden" name="generalSystemSet.id" id="id"  value="${generalSystemSetForm.generalSystemSet.id}"/>
        <input type="hidden" name="generalSystemSet.setContent" id="setContent"  value="${generalSystemSetForm.generalSystemSet.setContent}"/>
        <input type ="hidden" name="categoryId"  id="categoryId" value="${generalSystemSetForm.categoryId}"/>
		<input type="hidden" name="dealMethod" id="dealMethod"  value="${generalSystemSetForm.dealMethod}" />
     
         <input type ="hidden" name="siteName"  id="site.name" value="${generalSystemSetForm.generalSystemSet.site.name}"/>		
		<complat:button  buttonlist="add|0|delete"  buttonalign="left"  />
		<complat:grid ids="xx" width="200,100,200,*" head="热链文字,热链地址,创建时间,网站名"  
		element="[1,link,onclick,orgdetail]" 
		page="${generalSystemSetForm.pagination}" form="generalSystemSetForm" action="author.do"/>
	</form>
</body>
</html>

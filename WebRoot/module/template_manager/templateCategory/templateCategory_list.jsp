 <%--
	功能：用于显示模板模板类别的列表
	作者：郑荣华
 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>模板类别列表</title>
	<script type="text/javascript">

		$(document).ready(function() {
            if(!$("#message").val().isEmpty()) {
				var message = $("#message").val();
                if(message != "删除成功") {
                	alert($("#message").val()); 
                } else if($("#message").val() == "删除成功") {
    	         	parent.reloadAccordion("/${appName}/module/template_manager/refresh_Tree.jsp");
    	         	//window.location.href = "<c:url value='/templateCategory.do?nodeId=${templateCategoryForm.nodeId}&dealMethod='/>";
    	    	} else if($("#message").val() == "删除的模板类别下有模板存在") {
    				alert(message);
    				window.location.href = "<c:url value='/templateCategory.do?nodeId=${templateCategoryForm.nodeId}&dealMethod='/>";
    		    }
            }
		});

		function button_add_onclick(ee){
			var url = "<c:url value='/module/template_manager/templateCategory/templateCategory_detail.jsp?nodeId=${templateCategoryForm.nodeId}&"+getUrlSuffixRandom()+"'/>";	
			win = showWindow("newTemplateCategory","新增模板类别",url,250, 90, 360, 180);	 
	    }
		
		function button_delete_onclick(ee){	
			var ids = document.getElementById("xx").value;
			if(ids == "" || ids == null) {
				alert("请至少选择一条记录操作！");
				return false;
			}
			if(confirm("确定要删除吗？")){
				document.getElementById("strid").value = document.getElementById("xx").value;
			    $("#dealMethod").val("delete");
				$("#templateCategoryForm").submit();
		    } else {
				return false;
			}
		}

		function templateCategorydetail(ids){
			var url = "templateCategory.do?nodeId=${templateCategoryForm.nodeId}&dealMethod=detail&ids="+ids+"&"+getUrlSuffixRandom();
			win = showWindow("templateCategorydetail","模板类别",url,250, 90, 360, 180);	 
		}
		
		// 关闭窗口
		function closeDetailWin() {		
			closeWindow(win);
		}

	</script>
</head> 
<body>
   	<div class="currLocation">模板类别管理 </div>

	<form id="templateCategoryForm" action="templateCategory.do" method="post" name="templateCategoryForm">
        <input type="hidden" name="dealMethod" id="dealMethod" />
        <input type="hidden" name="nodeId"     id="nodeId"    value="${templateCategoryForm.nodeId }" />
		<input type="hidden" name="message"    id="message"   value="${templateCategoryForm.infoMessage}" />
		<input type="hidden" name="siteid"     id="siteid"    value="${templateCategoryForm.siteid }" />
		<input type="hidden" name="creatorid"  id="creatorid" value="${templateCategoryForm.creatorid }" />
        <input type="hidden" name="ids"        id="strid" />

		<complat:button name="button" buttonlist="add|0|delete" buttonalign="left"/>
		<complat:grid ids="xx" width="*,*,*" head="模板类别,创建者,所属网站" 
		     element="[1,link,onclick,templateCategorydetail]" 
		     page="${templateCategoryForm.pagination}" form="templateCategoryForm" action="templateCategory.do" />
	</form>
</body>
</html>
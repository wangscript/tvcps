 <%--
	功能：用于显示模板实例列表
	作者：郑荣华
 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>模板实例列表</title>
<script type="text/javascript">

	$(document).ready(function(){
		var message = $("#message").val();
		if(!message.isEmpty()) {
			if(message != "删除模板实例成功") {
				alert(message);	
			}
		}
	});
	function button_add_onclick() {
		var nodeid = $("#nodeId").val();
		win = showWindow("newTemplateInstance", "新增模板实例", "<c:url value='/module/template_manager/templateInstance/templateInstance_detail.jsp?templateId=${templateInstanceForm.templateId}&nodeId="+ nodeid +"&'/>"+getUrlSuffixRandom(), 180, 30, 360, 180);
	}

	function button_delete_onclick(obj) {	
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null) {
			alert("请至少选择一条记录操作！");
			return false;
		}
		var idsAndUsedNum = document.templateInstanceForm.idsAndUsedNum.value;
		var arr = idsAndUsedNum.split("::");
		var id = new Array();
		var num = new Array();
		for(var i = 0; i < arr.length; i++) {
			var param = arr[i].split(":");
			id[i] = param[0];
			num[i] = param[1];			
		}
		var t = 0;
		var test = ids.split(",");
		for(var j = 0; j < id.length; j++) {
			for(var k = 0; k < test.length; k++) {
				if(test[k] == id[j]) {
					if(num[j] > 0) {
						t = 1;
						break;
					}
				}
			}
			if(t == 1) {
				break;
			}
		}
		if(t == 1) {
			if(confirm("要删除的模板已经被使用，确定删除？")) {
				document.getElementById("strid").value = document.getElementById("xx").value;
			    $("#dealMethod").val("delete");
				$("#templateInstanceForm").submit();
			 } else {
				return false;
			}
		} else {
			if(confirm("确定要删除吗？")) {
				document.getElementById("strid").value = document.getElementById("xx").value;
			    $("#dealMethod").val("delete");
				$("#templateInstanceForm").submit();
		    } else {
				return false;
			}
		}
	}

	function changeTemplateInstanceName(id) {
		var nodeId = $("#nodeId").val();
		win = showWindow("changeTemplateInstance", "更新模板实例名称", "<c:url value='/templateInstance.do?dealMethod=templateInstance&templateId=${templateInstanceForm.templateId}&nodeId="+ nodeId +"&templateInstanceId=" + id + "'/>"+"&"+getUrlSuffixRandom(),180, 30, 360, 180);
	}

	function button_return_onclick(ee){
		var nodeId = $("#nodeId").val();
		top.document.getElementById("rightFrame").src = "<c:url value='/template.do?nodeId=" + nodeId + "&dealMethod='/>"+"&"+getUrlSuffixRandom();
	}
	
	function closeChild() {
		closeWindow(win);
	}

	function bindColumn(templateInstanceId) {
		var templateCategory = $("#nodeId").val();
		var templateId = $("#templateId").val();
		win = showWindow("bindColumn", "绑定栏目", "<c:url value='/templateInstance.do?dealMethod=findBind&bind=column&templateInstanceId='/>"+templateInstanceId+"&nodeId="+templateCategory+"&templateId="+templateId+"&"+getUrlSuffixRandom(), 0, 0, 350, 400);
	}

	function bindArticle(templateInstanceId) {
		var templateCategory = $("#nodeId").val();
		var templateId = $("#templateId").val();
		win = showWindow("bindArticle", "绑定文章", "<c:url value='/templateInstance.do?dealMethod=findBind&bind=article&templateInstanceId='/>"+templateInstanceId+"&nodeId="+templateCategory+"&templateId="+templateId+"&"+getUrlSuffixRandom(), 0, 0, 350, 400);
	}
</script>
</head>
<body>
	<div class="currLocation">模板实例管理</div>
	<form id="templateInstanceForm" action="templateInstance.do" method="post" name="templateInstanceForm">
		<input type="hidden" name="idAndUsedNum" id="idsAndUsedNum" value="${templateInstanceForm.idAndUsedNum }"/>
        <input type="hidden" name="dealMethod" id="dealMethod" value=""/>
		<input type="hidden" name="ids"        id="strid" /> 
		<input type="hidden" name="nodeId"     id="nodeId"   value="${templateInstanceForm.nodeId }" />
		<input type="hidden" name="message"    id="message" value="${templateInstanceForm.infoMessage }"/>
		<input type="hidden" name="templateId" id="templateId"  value="${templateInstanceForm.templateId }" />

		<complat:button name="button" buttonlist="add|0|delete|0|return" buttonalign="left"/>
		<complat:grid ids="xx" width="*,*,*,0,*,*,*,*" head="实例名称,创建者,创建日期,,被用次数,绑定栏目,绑定文章,更名" 
		     element="[6,button,onclick,bindColumn,绑定栏目][7,button,onclick,bindArticle,绑定文章][8,button,onclick,changeTemplateInstanceName,更名]"
		     page="${templateInstanceForm.pagination}" form="templateInstanceForm" action="templateInstance.do?dealMethod=" />
	</form>
</body>
</html>
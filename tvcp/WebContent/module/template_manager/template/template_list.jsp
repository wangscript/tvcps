 <%--
	功能：用于显示模板列表
	作者：郑荣华
 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<%@include file="/templates/headers/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>模板列表</title>
	<script type="text/javascript">
		$(document).ready(function() {
            if($("#message").val() != null && $("#message").val() != "") {
				if($("#message").val() != "删除模板成功") {
                	alert($("#message").val());
				} 
            }
            if($("#url").val() != null && $("#url").val() != "") {
                var url = $("#url").val();
                window.open(url, "", "width=" + screen.width + ",height=" + screen.height + ",top=0, left=0, statues=yes, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes, location=yes, status=yes'");
            }
		});

		function button_delete_onclick(obj){	
			var ids = document.getElementById("xx").value;
			if(ids == "" || ids == null) {
				alert("请至少选择一条记录操作！");
				return false;
			}
			if(confirm("确定要删除吗？")){
				document.getElementById("strid").value = document.getElementById("xx").value;
			    $("#dealMethod").val("delete");
				$("#templateForm").submit();
		    } else {
				return false;
			}
		}

		function button_import_onclick(obj) { 
			var url = "<c:url value='/module/template_manager/template/template_detail.jsp?nodeId=${templateForm.nodeId}&" + getUrlSuffixRandom() + "'/>"; 
			win = showWindow("newTemplate", "导入模板", url, 250, 0, 550, 445);
		}
		
		function button_update_onclick(obj) {
			var ids = document.getElementById("xx").value;
			if(ids == "" || ids == null) {
				alert("请选择一条记录操作！");
				return false;
			}
			var param = ids.split(",");
			if(param.length > 1) {
				alert("只能选择一条记录操作！");
				return false;
			}
			win = showWindow("updateTemplate", "更新模板", "template.do?dealMethod=detail&nodeId=${templateForm.nodeId}&ids="+ids+"&"+getUrlSuffixRandom(), 250, 0, 520, 370);
		}

		function button_copy_onclick(obj) {
			var ids = document.getElementById("xx").value;
			if(ids == "" || ids == null) {
				alert("请至少选择一条记录操作！");
				return false;
			}
			win = showWindow("newTemplate","选择粘贴位置","<c:url value='/module/template_manager/template/choosePaste_template.jsp?nodeId=${templateForm.nodeId}&pasteIds="+ids+"'/>"+"&"+getUrlSuffixRandom(), 250, 0, 315, 290);
		}

		function templatedetail(id) {
			$("#strid").val(id);
			$("#dealMethod").val("showModel");
			$("#templateForm").submit();
		}

		function downLoadTemplate(obj) {
			$("#strid").val(obj);
			$("#dealMethod").val("downLoadTemplate");
			var nodeId = $("#nodeId").val();  
			var url = "<c:url value='/template.do?dealMethod=downLoadTemplate&ids='/>" + obj + "&nodeId=" + nodeId+"&"+getUrlSuffixRandom();
			window.location.href = url;
			//top.document.getElementById("rightFrame").src = url;
		}

		// 模板下载
		function downLoadLocalTemplate(obj) {
			$("#strid").val(obj);
			$("#dealMethod").val("downLoadLocalTemplate");
			var nodeId = $("#nodeId").val();
			var url = "<c:url value='/template.do?dealMethod=downLoadLocalTemplate&ids='/>" + obj + "&nodeId=" + nodeId+"&"+getUrlSuffixRandom();
			window.location.href = url;
			//top.document.getElementById("rightFrame").src = url; 
		}
		
		function templateySet(ids){
			var nodeid = $("#nodeId").val();
			var url = "<c:url value='/templateInstance.do?dealMethod=&templateId="+ ids + "&nodeId=" + nodeid + "'/>"+"&"+getUrlSuffixRandom();
			top.document.getElementById("rightFrame").src = url;	   
		}
		
		function closeDetailChild() {
			closeWindow(win);
		}
		function editTemp(id){
				var url= $("#"+id+"_7").html();
				win = showWindow("editTemp","模板编辑器","<c:url value='/template.do?dealMethod=editTemplate&nodeId=${templateForm.nodeId }&tempPath="+url+"&ids="+id+"'/>"+"&"+getUrlSuffixRandom(), 293, 0, 1000, 530);
		}
	</script>
</head>
<body>
    <div class="currLocation">模板管理</div>
	<form id="templateForm" action="template.do" method="post" name="templateForm">
        <input type="hidden" name="dealMethod" id="dealMethod" />
		<input type="hidden" name="ids"        id="strid" />
		<input type="hidden" name="pasteIds"   id="pasteIds" value="${templateForm.pasteIds }"/>
        <input type="hidden" name="nodeId"     id="nodeId"   value="${templateForm.nodeId }" />
		<input type="hidden" name="message"    id="message"  value="${templateForm.infoMessage }" />
		<input type="hidden" name="url"        id="url"      value="${templateForm.url }" />		

		<complat:button name="button" buttonlist="import|0|update|0|delete|0|copy" buttonalign="left"/>
		<complat:grid ids="xx" width="*,*,*,*,*,0,0,110,90,110,*" head="模板名称,创建者,修改者,创建日期,修改日期, , ,页面下载,模板下载,模板实例,在线编辑" 
		     element="[1,link,onclick,templatedetail][8,button,onclick,downLoadTemplate,页面下载][9,button,onclick,downLoadLocalTemplate,模板下载][10,button,onclick,templateySet,模板实例][11,button,onclick,editTemp,编辑]" 
		     page="${templateForm.pagination}" form="templateForm" action="template.do" />
	</form>
</body>
</html>
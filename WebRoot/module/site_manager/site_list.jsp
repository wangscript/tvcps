<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/templates/headers/header.jsp"%>
<title>网站管理</title>
<script>

	var childNewSiteWin = "";
	var childDetailSiteWin = "";
	$(document).ready(function(){
		var message = $("#message").val();
		if(!message.isEmpty()) {
			if(message != "删除网站成功！") {
				alert(message);
			}
		}
	});
	
	// 添加网站
	function button_add_onclick(ee) {	
		win = showWindow("newSite", "新增网站", "<c:url value='/module/site_manager/site_detail.jsp'/>", 250, 90, 600, 550);	 
	}

	// 删除网站
	function deleteSite(id) {
        $("#siteid").val(id);
		if(confirm("确定要删除网站？若删除则网站下面的所有资源将被删除!")) { 
			$("#dealMethod").val("delete");
			$("#siteForm").submit(); 
		}else{
			return false;
		}
	}
	
	// 单个网站信息
	function sitedetail(id) {
		win = showWindow("sitedetail", "网站信息", "site.do?dealMethod=detail&siteid=" + id + "", 250, 90, 600, 550);	 
	}

	function closeNewChild() {
		closeWindow(win);
	}
	
	function closeDetailChild() {
		closeWindow(win);
	}
	
</script>
</head>
<body>
	<div class="currLocation">网站管理</div>	
	<form id="siteForm" action="<c:url value="/site.do"/>" method="post" name="siteForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="message"    id="message" value="${siteForm.infoMessage}"/>
		<input type="hidden" name="siteid"     id="siteid"/>
		<complat:button name="button" buttonlist="add" buttonalign="left"/>
		<complat:grid ids="xx" width="*,*,*,*" head="网站名称,创建时间,父网站,删除网站"
			element="[1,link,onclick,sitedetail][4,button,onclick,deleteSite,删除]"
			page="${siteForm.pagination}" form="siteForm" action="site.do"/>
	</form>
</body>
</html>

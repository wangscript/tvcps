<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<%@include file="/templates/headers/header.jsp"%>
	<script type="text/javascript">
	    var childNewOrgWin = "";
		//显示消息内容
		function showContent(id){
			window.location.href = "sitemessage.do?dealMethod=showContent&ids=" + id + "&nodeId=" +document.getElementById("nodeId").value ;
		}
	
		function button_delete_onclick(ee) {	
			var ids = document.getElementById("xx").value;
			if(ids == "" || ids == null){
				alert("请至少选择一条记录操作！");
				return false;
			}
			if(confirm("确定要删除吗？")) {
				document.getElementById("strid").value = document.getElementById("xx").value;
				$("#dealMethod").val("delete");
				$("#siteMessageForm").submit();
			} else {
				return false;
			}
		}
	</script>
</head>
<body>
	 <form id="siteMessageForm" name="siteMessageForm" action="<c:url value="/sitemessage.do"/>" method="post" >
	 <div class="currLocation">发件箱</div>
		<input type="hidden" name="dealMethod" id="dealMethod" value="sendMessageBox"/>
		<input type="hidden" name="ids" id="strid"/>
		<input type="hidden" name="nodeId" id="nodeId" value="2"/>
		<complat:button name="button"  buttonlist="delete" buttonalign="left" ></complat:button>
		<complat:grid ids="xx" width="*,*,*" head="消息标题,收件人,发送时间"  element="[1,link,onclick,showContent]" 
					 page="${siteMessageForm.pagination}"
					 form="siteMessageForm" action="sitemessage.do"/>
</body>
</html>
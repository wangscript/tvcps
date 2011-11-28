<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title></title>
	<%@include file="/templates/headers/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/ajaxpagination.css"/>">

	<script type="text/javascript" src="<c:url value="/script/jquery.pagination.js"/>"></script>
	<script type="text/javascript">
	
		$(document).ready(function() {
			if(document.getElementById("flag").value == 1) {
				window.location.href = "userLetter.do?dealMethod=list"
			}
		});
		function findByReplyCode() {
			var replyCode = document.getElementById("replyCode").value;
			if(replyCode == null || replyCode == "") {
				return false;
			}
			$("#dealMethod").val("findByReplyCode");
    		$("#letterForm").submit();
		}

		function findByCategory() {
			
			if(document.getElementById("categoryName").value == null) {
				return false;
			}
			$("#dealMethod").val("findByCategory");
    		$("#letterForm").submit();
		}

		function change(obj) {
			var id = "";
			if(obj.value == "" || obj.value == null) {
				id = "";
			} else {
				id = obj.value;
			}
			document.getElementById("categoryName").value = id;	
		}
		
		function showUserLetterDetail(id, status) {
			window.location.href = "userLetter.do?dealMethod=showUserLetter&ids=" + id + "&letterStatus=" + status;
		}
		
	</script>
</head>
<body>
	<form id="letterForm" action="userLetter.do" name="letterForm">
	<input type="hidden" id="dealMethod" name="dealMethod" value=""/>
	<input type="hidden" id="letterStatus" name="letterStatus" />
	<input type="hidden" id="flag" name="flag" value="${letterForm.flag}"/>
	<div id="main">
		<ul>	
			<li>
				<label for="code">根据回执编号查询：</label>
				<input id="replyCode" name="replyCode" size="20"/>
				<input type="button" class="btn_small" value="查询" onclick="findByReplyCode()">
			</li>
			<li>
				<label for="category">根据信件类别查询：</label>
				<select name="categoryName" id="categoryName" style="width: 155px; "  onChange="change(this)" >
					<option value="0" selected="selected">所有类别</option>
					<c:forEach items="${letterForm.categoryList}" var="list">
					<option value="${list[0]}">${list[1]}</option>
					</c:forEach> 
				</select>
				<input type="button" class="btn_small" value="查询" onclick="findByCategory()">
			</li>
		</ul>
	</div>
	
	<complat:ajaxpage page="${letterForm.pagination}" pageId="pp" form="letterForm" action="userLetter.do?dealMethod=list_msg"/>
	<div id="pp" >
	<table width="80%" border="1" align="center" cellpadding="1" cellspacing="1">
		<tr style="background:#cccccc; height:40px;">
			<td><center>编　号</center></td>
			<td><center>类　别</center></td>
			<td><center>标　题</center></td>
			<td><center>状　态</center></td>
			<td><center>写　信　日　期</center></td>
		</tr>
		<tr>
			<c:forEach items="${letterForm.list}" var="list" >
			<td>${list[1]}</td>
			<td>${list[2]}</td>
			<td onClick="showUserLetterDetail('${list[0]}', ${list[4]})">${list[3]}</td>
			<c:if test="${list[4] == 1}"> 
                <td>未处理</td>           
        	</c:if>
        	<c:if test="${list[4] == 2}"> 
                <td>待处理</td>           
        	</c:if>
			<c:if test="${list[4] == 3}"> 
                <td>已处理</td>           
        	</c:if>  
			<td>${list[5]}</td>
			</th>
		</tr>
		</c:forEach>
	</table>
	</div>

	</form>
</body>
</html>
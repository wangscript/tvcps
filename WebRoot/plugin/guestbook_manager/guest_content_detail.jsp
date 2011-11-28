<%@ page language="java" import="java.util.Date,com.baize.common.core.util.DateUtil" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>留言详细页</title>

</head>

<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
	$(function loadMsg(){
			$("#replayContentId").focus();
		});
	function btnSave(){
		$("#dealMethod").val("saveContent");
		var options={
				url:"<c:url value='/guestCategory.do'/>",
				success:function(msg){
					if(msg!=""){
						alert("保存成功");
						}else{
							alert("操作失败,请联系管理员");
						}
					 top.document.getElementById("rightFrame").src = "<c:url value='/guestCategory.do?dealMethod='/>";
					  closeWindow(rightFrame.getWin());
			}};
		$("#guestBookForm").ajaxSubmit(options);
	}
	function btnAudit(){//审核
		$("#dealMethod").val("auditContent");
		var options={
				url:"<c:url value='/guestCategory.do'/>",
				success:function(msg){
					if(msg!=""){
						alert("审核成功");
						}else{
							alert("操作失败,请联系管理员");
						}
					 top.document.getElementById("rightFrame").src = "<c:url value='/guestCategory.do?dealMethod='/>";
					  closeWindow(rightFrame.getWin());
			}};
		$("#guestBookForm").ajaxSubmit(options);
	}
	function btnauditAndReplay(){//审核并回复
		$("#dealMethod").val("auditAndReplay");
		var options={
				url:"<c:url value='/guestCategory.do'/>",
				success:function(msg){
					if(msg!=""){
						alert("已将该留言审核，并已经回复");
						}else{
							alert("操作失败,请联系管理员");
						}
					 top.document.getElementById("rightFrame").src = "<c:url value='/guestCategory.do?dealMethod='/>";
					  closeWindow(rightFrame.getWin());
			}};
		$("#guestBookForm").ajaxSubmit(options);
	}
	function btnnoaudit(){//留言置未审核
		$("#dealMethod").val("noaudit");
		var options={
				url:"<c:url value='/guestCategory.do'/>",
				success:function(msg){
					if(msg!=""){
						alert("已经将留言设置为未审核");
						}else{
							alert("操作失败,请联系管理员");
						}
					 top.document.getElementById("rightFrame").src = "<c:url value='/guestCategory.do?dealMethod='/>";
					  closeWindow(rightFrame.getWin());
			}};
		$("#guestBookForm").ajaxSubmit(options);
	}
	function btnhandle(){//不处理
		$("#dealMethod").val("handle");
		var options={
				url:"<c:url value='/guestCategory.do'/>",
				success:function(msg){
					if(msg!=""){
						alert("已将留言设置为不处理");
					}else{
						alert("操作失败,请联系管理员");
					}
					 top.document.getElementById("rightFrame").src = "<c:url value='/guestCategory.do?dealMethod='/>";
					  closeWindow(rightFrame.getWin());
			}};
		$("#guestBookForm").ajaxSubmit(options);
	}
</script>
<body>
<form action="<c:url value='/guestCategory.do'/>" method="post"	name="guestBookForm" id="guestBookForm">
<input type="hidden" name="dealMethod" id="dealMethod" /> 
<input type="hidden" name="ids"	id="strId" value="${guestBookForm.guestContent.id}" />
<table cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td>留言主题:</td>
		<td><textarea id="id" cols="50" rows="2">${guestBookForm.guestContent.title}</textarea></td>
	</tr>
	<tr>
		<td>发表人IP:</td>
		<td>${guestBookForm.guestContent.ip}</td>
	</tr>
	<tr>
		<td>所属省份:</td>
		<td>${guestBookForm.guestContent.area}</td>
	</tr>

	<tr>
		<td>联系电话:</td>
		<td>${guestBookForm.guestContent.phone}</td>
	</tr>

	<tr>
		<td>电子邮件:</td>
		<td>${guestBookForm.guestContent.email}</td>
	</tr>

	<tr>
		<td>发表时间:</td>
		<td>${guestBookForm.guestContent.toParseDate}</td>
	</tr>

	<tr>
		<td>留言人:</td>
		<td>
			<c:out value="${guestBookForm.guestContent.bookName}" default="匿名"/>
		</td>

	</tr>
	<tr>
		<td>留言内容:</td>
		<td><textarea id="id" cols="50" rows="5">${guestBookForm.guestContent.bookContent}</textarea></td>
	</tr>
	<tr>
		<td>回复审核状态:</td>
		<td>
			<c:choose>
					<c:when test="${guestBookForm.guestContent.auditStatus eq '0'}">
						未审核
					</c:when>
					<c:when test="${guestBookForm.guestContent.auditStatus eq '1'}">
						已审核
					</c:when>
					<c:when test="${guestBookForm.guestContent.auditStatus eq '2'}">
						不处理
					</c:when>
					<c:otherwise>
						不审核
					</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td>回复时间:</td>
		<td>
			<input type='text' name="reply.toParseDate" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${guestBookForm.reply.toParseDate}"/>
		</td>
	</tr>
	<tr>
		<td>回复内容:</td>
		<td><textarea id="id" cols="50" rows="5" name="reply.revertContent" id="replayContentId">${guestBookForm.reply.revertContent}</textarea></td>
	</tr>
</table>
<center>
<c:choose>
		
		<c:when test="${guestBookForm.guestContent.auditStatus eq '0'}">
				<input type="submit" id="id" value="审核留言" onclick="btnAudit();"/>
				<input type="submit" id="id" value="审核并回复" onclick="btnauditAndReplay();"/>
				<input type="submit" id="id" value="不处理" onclick="btnhandle();"/>
		</c:when>
		<c:when test="${guestBookForm.guestContent.auditStatus eq '1'}">
				<input type="submit" id="id" value="留言置为未审" onclick="btnnoaudit();"/>
				<input type="submit" id="id" value="审核并回复" onclick="btnauditAndReplay();"/>
				<input type="submit" id="id" value="不处理" onclick="btnhandle();"/>
		</c:when>
		<c:when test="${guestBookForm.guestContent.auditStatus eq '2'}">
				<input type="submit" id="id" value="留言置为未审" onclick="btnnoaudit();"/>
		</c:when>
		<c:when test="${guestBookForm.guestContent.auditStatus eq '3'}">
				<input type="submit" id="id" value="保存" onclick="btnSave();"/>
		</c:when>
</c:choose>
</center>
</form>
</body>
</html>
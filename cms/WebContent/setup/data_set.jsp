<%@ page language="java" contentType="text/html; charset=utf-8"
	errorPage="error.jsp"%>
<%@include file="/templates/headers/header.jsp"%>
<jsp:useBean id="sysparam"
	class="com.j2ee.cms.biz.setupmanager.domain.SysParam" scope="session">
</jsp:useBean>
<c:if test="${sysparam.pwd eq null}">
	<%
		response
				.sendRedirect(request.getContextPath() + "/setup/error.jsp");
	%>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>服务器参数设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
#main td {
	padding: 3px 10px 3px 5px;
	background-color: white;
	font-size: 12px;
	font-family: "宋体", Arial;
}
</style>
<script type="text/javascript">
	//验证数据输入是否合法
	function sub() {
		var main_pwd1 = document.getElementById("password").value;
		var main_pwd2 = document.getElementById("surepw").value;
		if (main_pwd1 == "") {
			alert("请输入密码");
			return false;
		}
		if (main_pwd1 != main_pwd2) {
			alert("您两次输入的密码不一致!");
			return false;
		}
		$("#dealMethod").val("dataset");//将dealMethod设值
		$("#initForm").submit();//提交
	}

	//页面加载的时候，应加载的数据
	$( function loadParam() {
		
		//根据用户选择的数据库类型读取到下来框中
		var dsType = $("#databaseType").val();
		$.each($("#databaseTypeSelect option"), function(i, n) {
			if (n.value == dsType) {
				$(n).attr("selected", "selected");
			}
		});
		//根据用户选择的服务器类型读取到下来框中
		var sysServer = $("#serverNameId").val();
		$.each($("#serverNamesSelect option"), function(i, n) {
			if (n.value == sysServer) {
				$(n).attr("selected", "selected");
			}
		});
		//显示页面提示信息
		var mesInfo = $("#mesId").val();
		if (mesInfo != "") {
			alert(mesInfo);
		}
	});
</script>

</head>
<body>
<form id="initForm" name="initForm" method="post"
	action="<c:url value='/SetupServlet'/>">
	<input type="hidden"name="dealMethod" id="dealMethod" value="dataset" />
	<input	type="hidden" name="serverName" id="serverNameId" value="${sysparam.serverName}" />
	<input type="hidden" name="databaseType" id="databaseType" value="${sysparam.databaseType}" />
	<input type="hidden" name="mess" id="mesId" value="${requestScope.mess}" />
	<table width="100%" border="0" cellspacing="0" cellpadding="20">
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title"><font style="margin-left:15px;"color="#0099FF" size="3">服务器参数设置</font></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td height="24" class="tips">
							<font style="margin-left:15px;"color="#666666">请保证服务器参数设置正确</font>
						</td>
					</tr>
					<tr>
						<td>
							<hr />
						</td>
					</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
		<table width="100%" border="0" id="main" align="left" class="form_cls">
			<tr>
				<td width="110" nowrap align="right">
					<font style="font-size:13px; margin-left:10px;">系统安装路径</font>
				</td>
				<td>
					<input type="text" name="loadRoading" id="loadRoading" style="width: 444px" value="${sysparam.loadPath}" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td height="23" align="right">
					<font style="font-size:13px; margin-left:10px;">后台admin密码</font>
				</td>
				<td>
					<input type="password" name="password" id="password" style="width: 444px" value="${sysparam.pwd}" />
				</td>
			</tr>
			<tr>
				<td height="23" align="right">
					<font style="font-size:13px; margin-left:10px;">确认密码</font>
				</td>
				<td>
					<input type="password" name="surepw" id="surepw" style="width: 444px" value="${sysparam.pwd}" />
				</td>
			</tr>
			<tr>
				<td height="20" align="right">
					<font style="font-size:13px; margin-left:10px;">服务器(中间件)</font>
				</td>
				<td>
					<select name="serNameSelect" id="serverNamesSelect" width="275px;">
						<option value="websphere">websphere</option>
						<option value="weblogic">weblogic</option>
						<option value="tomcat">tomcat</option>
					</select>
				</td>
			</tr>

			<tr>
				<td height="20" align="right">
					<font style="font-size:13px; margin-left:10px;">数据库类型</font>
				</td>
				<td>
					<select name="databaseTypeSelect" style="width: 275px;" id="databaseTypeSelect">
						<option value="mssql">mssql</option>
						<option value="oracle">oracle</option>
						<option value="db2">db2</option>
						<option value="mysql">mysql</option>
					</select>
				</td>
			</tr>
			<tr>
				<td height="23" align="right">
					<font style="font-size:13px; margin-left:10px;">部署应用名称</font>
				</td>
				<td>
					<input name="appName" type="text" id="name"  value="${sysparam.appName}" style="width: 444px" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td height="23" colspan="2" align="center">
					<input type="button" class="btn_normal" value="保存" onClick="sub()" />
				</td>
			</tr>
		</table>
</form>
</body>
</html>

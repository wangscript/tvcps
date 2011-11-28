<%@ page language="java" contentType="text/html; charset=utf-8"
	autoFlush="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CCMS后台管理制作端</title>
<link href="<%=request.getContextPath() %>/images/setupimages/setup.css" rel="stylesheet" type="text/css" />
<style type="text/css">
input.test { 
	width: 85px;height:16px;
	margin:10px 0 0 0px;
}
.userFont{
	font-family:"宋体";
	font-size:12px;
	margin:0 0 0 33px;
	>margin:0 0 0 30px;
	line-height:15px;
}
input.submit {
	width: 46px; height: 18px; border: none;
	background:url(<%=request.getContextPath() %>/images/login/niu.gif)  no-repeat;
	margin:1px 0 0 1px;>margin:1px 0 0 1px;
	font: 12px Arial, Sans-Serif; padding:1px;>padding:3px; color: #FFF; 
}
</style>
<script language="javascript">
	//提交是验证
	function funSubmit(){
		var username = document.formaction.user.value ;
		var password = document.formaction.pass.value ;
		if(username == ""&&password == ""){
			alert("请输入用户名和密码!");
			return false;
		}
		if( username == "" ){
			alert("请输入用户名!");
			return false;
		}
		if(password == ""){
			alert("请输入密码!");
			return false;
		}
		return true;	
	}
		//设置鼠标焦点
		function setFocus(){
				var m = document.getElementById("mes").value;
				if(m!=""){
					alert(m);
				}
			  document.formaction.user.focus();
		}
			//加载时调用事件
			window.attachEvent("onload",setFocus);
</script>
</head>
<body oncontextmenu="self.event.returnValue=false" onselectstart="false">
<table width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td>
		<form action="<c:url value='/SetupServlet'/>" name="formaction" 	method="post" onsubmit="return funSubmit();">
			<input type="hidden" name="dealMethod" value="login">
			<input type="hidden" name="mes" id="mes" value="${requestScope.mess2}"/>
		<table width="450" border="0" align="center" cellpadding="0" cellspacing="0" style="border: 1px solid #3A80D1; padding: 1px;" bgcolor="#EFF8FF">
			<tr>
				<td>
				<table width="100%" height="100%" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="160"><img
							src="<%=request.getContextPath()%>/images/setupimages/stup_03.jpg"
							width="160" height="64" /></td>
						<td width="290"><img
							src="<%=request.getContextPath()%>/images/setupimages/stup_04.jpg"
							width="290" height="64" /></td>
					</tr>
					<tr>
						<td colspan="2" bgcolor="#3a80d1" height="27"
							style="border-top: 1px solid #FFFFFF;">&nbsp;</td>
					</tr>
				</table>

				<table border="0" align="center" cellpadding="0" cellspacing="0">
					<tr align="center"> 
					</tr>
					<tr>
						<td width="70" height="30">
							<font size="2">用户名：</font>
						</td>
						<td>
							<input type="text" name="user" id="user" style="border:1px solid #3A80D1;width:150px;"/>
						</td>
					</tr>
					<tr>
						<td width="70" height="30"><font size="2">密码：</font></td>
						<td>
							<input  onpaste="return false" type="password" name="pass" id="pass" style="border: 1px solid #3A80D1; width: 150px;" />
						</td>
					</tr>
					<tr>
						<td height="30" align="center">&nbsp;</td>
						<td height="30">
							<input type="submit" value="提交" class="submit" />
							<input type="reset" value="重置" class="submit" />
						</td>
					</tr>
					<tr>
						<td height="40">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8"  errorPage="error.jsp"%>
<%@include file="/templates/headers/header.jsp"%>
<jsp:useBean id="sinit"
	class="com.baize.ccms.biz.setupmanager.domain.SysInit" scope="session">
</jsp:useBean>
<c:if test="${sinit.dataBaseName eq null}">
<% response.sendRedirect(request.getContextPath()+"/setup/error.jsp");%>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>服务器参数设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css" media="all">
#main td {
	padding: 3px 10px 3px 5px;
	background-color: white;
	font-size: 12px;
	font-family: "宋体", Arial;
}
</style>

<script type="text/javascript">
	//判断数据是否合法
	function submitForm() {
		var main_dbname = document.getElementById("dataBaseNameID").value;
		var main_port = document.getElementById("ccms_port").value;
		var main_pwd1 = document.getElementById("dbPassword").value;
		var main_pwd2 = document.getElementById("dbPassword1").value;
		var main_ip = document.getElementById("ccms_ip").value;
		var main_user = document.getElementById("dbUser").value;
		
		if( main_ip == "" ) {
			alert("请输入数据库服务器IP");
			 document.initForm.ccms_ip.focus();
			return false;

			//这个是验证IP是否输入正确
		}else if(!testIP(main_ip)){
				alert("请输入正确的IP");
				document.initForm.ccms_ip.focus();
				return false;
		}else if( main_port == "" ) {
			alert("请输入数据库连接端口");
			document.initForm.ccms_port.focus();
			return false;
		}else if(isNaN(main_port)){
			alert("请输入正确的端口号");
			return false;
		}else if( main_dbname == "" ) {
			alert("请输入数据库名");
			document.initForm.dataBaseName.focus();
			return false;
		}else if( main_user == "" ) {
			alert("请输入数据库用户名");
			document.initForm.dbUser.focus();
			return false;
		}else if( main_pwd1 == ""  ) {
			alert("请输入密码");
			document.initForm.dbPassword.focus();
			return false;
		}else if( main_pwd1 != main_pwd2 ) {
			alert("您两次输入的密码不一致!");
			document.initForm.dbPassword.select();
			return false;
		}else{
		$("#initForm").submit();
		return true;
		}
	}
	//文本中端口只能是数字
	function checkPort(){ 
		      if(event.keyCode<48||event.keyCode>57){     
		          event.returnValue=false;        
		  }   
		}
	//验证IP,用正则表达式
	function  testIP(s){   
	      var   arr=s.match(/^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/);
	      //为空直接返回   
	      if(arr==null)
		      return   false;   
	      //匹配地址
	      for(i=1;i<arr.length;i++)
		      if(String(Number(arr[i]))!=arr[i]||Number(arr[i])>255)
			      return   false;   
	      return   true;   
	  } 

		//页面加载所需信息
		$(function loadSet(){
				var dsUserName = $("#countId").val();
				if(dsUserName.trim()!=""){
					if(dsUserName=="1"){
						document.initForm.btnJCMSSave.style.display='none';	
					}else if(dsUserName=="0"){
						document.initForm.btnJCMSSave.style.display='block';	
					}
				}
			//提示信息
			var mess = $("#messInfoId").val();
			var m =$("#mid").val();
			if(m.trim()!=""){
				if(m=="1"){
						alert('你选择的是oracle，系统没有检测到你的数据库SID，请联系管理员建好SID后再初始化数据');
				}else if(confirm('你的数据库已经存在,是否继续？\n 使用该数据库点击确定，重新创建点击取消')){
					document.initForm.action="<c:url value='/CheckInit'/>";
					$("#initForm").submit();
					
					}
				}
			if(mess.trim()!="")
			{
				alert(mess);
			}
		});
</script>

</head>
<body>
<form id="initForm" name="initForm" method="post" action="<c:url value='/SetupServlet'/>">
		<input type="hidden" name="dealMethod" id="dealMethod" value="sysinit" /> 
		<input type="hidden" name="countId" id="countId" value="${sinit.countId}" />
		<input type="hidden" name="messInfo" id="messInfoId" value="${requestScope.mes}" />
		<input type="hidden" name="m" id ="mid" value="${requestScope.mesIsInit}"/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<font style="margin-left:15px;" color="#0099FF" size="3">数据初始化 </font>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td height="24" class="tips">
				<font style="margin-left:15px;" color="#666666">数据库初始化设置</font>
			</td>
		</tr>
		<tr>
			<td height="40" colspan="2" style="font-size: 12px; color: blue;">
				<font style="margin-left:15px;">
					<c:if test="${sinit.countId eq 1}">
						<c:out value="数据库已被创建！"></c:out>
					</c:if>
				</font>
			</td>
		</tr>
		<tr>
			<td width="100%">
				<hr>
			</td>
		</tr>
	</table>

	<table width="100%" border="0" id="main" align="left" class="form_cls">
		<tr>
			<td width="110" nowrap align="right">
				<font style="font-size:13px; margin-left:10px;">数据库服务器IP</font>
			</td>
			<td>
				<input type="text"  name="ccms_ip" id="ccms_ip" style="width: 444px" value="${sinit.serverIP}" />
			</td>
		</tr>
		<tr>
			<td width="110" nowrap align="right">
				<font style="font-size:13px; margin-left:10px;">数据库连接端口</font>
			</td>
			<td>
				<input type="text" name="ccms_port" onKeyPress="checkPort();" id="ccms_port" style="width: 444px" value="${sinit.dataPort}" />
			</td>
		</tr>
		<tr>
			<td width="110" nowrap align="right">
				<font style="font-size:13px; margin-left:10px;">数据库名称</font>
			</td>
			<td>
				<input type="text" name="dataBaseName" id="dataBaseNameID" style="width: 444px" value="${sinit.dataBaseName}" />
				<font style="font-size:14px; margin-left:5px;">提示:若数据库为oracle,则填写数据库的SID值</font>
			</td>
		</tr>
		<tr>
			<td width="110" nowrap align="right">
				<font style="font-size:13px; margin-left:10px;">数据库用户</font>
			</td>
			<td>
				<input type="text" name="dbUser" id="dbUser" style="width: 444px" value="${sinit.dataUserName}" />
			</td>
		</tr>
		<tr>
			<td width="110" nowrap align="right">
				<font style="font-size:13px; margin-left:10px;">数据库密码</font>
			</td>
			<td>
				<input type="password" name="dbPassword" id="dbPassword" style="width: 444px" value="${sinit.dataUserPass}" />
			</td>
		</tr>
		<tr>
			<td width="110" nowrap align="right">
				<font style="font-size:13px; margin-left:10px;">确认密码</font>
			</td>
			<td>
				<input type="password" name="dbPassword1" id="dbPassword1" style="width: 444px" value="${sinit.dataUserPass}" />
			</td>
		</tr>
		<tr>
			<td width="110" nowrap align="center">
				<input type="button" name="btnJCMSSave" value="数据初始化" style='display: block;' class="btn_ok" onclick="return submitForm();">
			</td>
			<td align="left"></td>
		</tr>
	</table>
</form>
</body>
</html>

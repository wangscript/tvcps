<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"  errorPage="error.jsp"%>
<%@include file="/templates/headers/header.jsp"%>
<jsp:useBean id="regInfo" class="com.baize.common.core.domain.RegInfo" scope="session">
</jsp:useBean>
<c:if test="${sessionScope.uname eq null}">
<% response.sendRedirect(request.getContextPath()+"/setup/error.jsp");%>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>服务器参数设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css" media="all">
#container label {
	width: 80px;
}

#btn {
	padding: 20px 0 0 100px;
}
</style>

<script type="text/javascript">
function lastname(fileName){

	//再对文件名进行截取，以取得后缀名
	var three=fileName.split("."); 
	 //获取截取的最后一个字符串，即为后缀名
	var last=three[three.length-1];
	//添加需要判断的后缀名类型
	var tp ="licence"; 
	//返回符合条件的后缀名在字符串中的位置
	var rs=tp.indexOf(last); 
	//如果返回的结果大于或等于0，说明包含允许上传的文件类型
	if(rs>=0){
	 return true;
	 }else{
		// alert('上传的文件不正确!');
	 return false;
	  }
	}

//上传提交时验证，格式必须是licence
	function jsUpload()
	{
		
			var ext = GetFileExtension(document.uploadForm.myfile.value);
			if(ext=="")
			{
				alert("请选择文件");
				return false;
			}else if(!lastname(ext.trim())){
				alert('上传的文件不正确!');
				document.uploadForm.myfile.focus();
				return false;
			}else{
				document.uploadForm.submit();
				return true;
			}
	}
//返回文件后缀名为lience
	function GetFileExtension(name)
	{
		var ext = name.substring(name.lastIndexOf("\\")+1, name.length);
		return ext.toLowerCase();        
	}
	//显示上传时否成功
	$(function loadMeg(){
			var mes = $("#messId").val();
			if(mes!="null"&&mes.trim()!=""){
				alert(mes);
			}
	});
	</script>
</head>

<body>

<table width="100%" height="100%" border="0" cellspacing="0"
	cellpadding="0">
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellspacing="0" cellpadding="20">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title">
							<font  style="margin-left:15px;" color="#0099FF" size="3">申请Licence</font>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td height="24" class="tips">
							<font style="margin-left:15px;" color="#666666">请根据注册码申请Licence文件</font>
						</td>
					</tr>
					<tr>
						<td>
						<hr />
						</td>
					</tr>
				</table>
				<table border="0" cellspacing="0" cellpadding="0" width="95%">
					<tr>
						<td width="10%" height="24">
							<font style="font-size:13px; margin-left:20px;">注册码</font>
						</td>
						<td width="80%" height="24"	style="color: blue; font-size: 14px; word-wrap: break-word;word-break:break-all">						
							<c:out value="${mac}" default="没有获取到" />
						</td>
					</tr>					
				</table>
				<br/>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title">
							<font style="font-size:13px; margin-left:20px;">Licence文件上传</font>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td height="24" class="tips">
							<font style="font-size:13px; margin-left:20px;">请将Licence文件上传至服务器上</font>
						</td>
					</tr>
					<tr>
						<td>
							<hr />
						</td>
					</tr>
				</table>

				<form name="uploadForm" enctype="multipart/form-data" method="post"
					action="<c:url value='/upload'/>" >
				<input type="hidden" name="mess" id="messId" value="<%=request.getAttribute("m")%>"/>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100">
							<font style="font-size:13px; margin-left:20px;"> Licence文件</font>
						</td>
						<td height="24">
							<input type="file" name="myfile" size="50"	onkeydown="return false;">&nbsp;&nbsp;
							<input type="button" value="上传" onClick="jsUpload();">
						</td>
					</tr>
					<tr>
						<td width="100" height="30">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>

				</form>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title" colspan="3"><font style="font-size:13px; margin-left:20px;">版本信息</font>
					</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td height="24" class="tips"><font style="font-size:13px; margin-left:20px;">CCMS版本信息</font></td>
					</tr>
					<tr>
						<td>
							<hr />
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="5">
					<tr>
						<td width="100" bgcolor="#EFF8FF">
							<font style="font-size:13px; margin-left:20px;">版本号&nbsp;</font>
						</td>
						<td bgcolor="#EFF8FF">
							<c:if test="${regInfo.version != null}">
								<jsp:getProperty name="regInfo" property="version" />
							</c:if>
						</td>
					</tr>
					<tr>
						<td>
							<font style="font-size:13px; margin-left:20px;">序列号&nbsp;</font>
						</td>
						<td>
							<c:if test="${regInfo.regCode != null}">
								<jsp:getProperty name="regInfo" property="regCode" />
							</c:if>
						</td>
					</tr>
					<tr>
						<td bgcolor="#EFF8FF">
							<font style="font-size:13px; margin-left:20px;">网站数&nbsp;</font>
						</td>
						<td bgcolor="#EFF8FF">
							<c:if test="${regInfo.siteCount != null}">
									<jsp:getProperty name="regInfo" property="siteCount" />
							</c:if>
						</td>
					</tr>
					<tr>
						<td>
							<font style="font-size:13px; margin-left:20px;">栏目数&nbsp;</font>
						</td>
						<td>
							<c:if test="${regInfo.columnCount != null}">
								<jsp:getProperty name="regInfo" property="columnCount" />
							</c:if>
						</td>
					</tr>
					<tr>
						<td bgcolor="#EFF8FF"><font style="font-size:13px; margin-left:20px;">期限&nbsp;</font></td>
						<td bgcolor="#EFF8FF">
							<c:if test="${regInfo.dateMax != null}">
								<jsp:getProperty name="regInfo" property="dateMax" />月
							</c:if>
						</td>
					</tr>
					<tr>
						<td><font style="font-size:13px; margin-left:20px;">注册日期&nbsp;</font></td>
						<td >
							<c:if test="${regInfo.registDate != null}">
								<jsp:getProperty name="regInfo" property="registDate" />
							</c:if>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8" errorPage="error.jsp"%>
<%@include file="/templates/headers/header.jsp"%>
<c:if test="${sessionScope.uname eq null}">
	<%
		response
				.sendRedirect(request.getContextPath() + "/setup/error.jsp");
	%>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>CCMS</title>
<link href="<%=request.getContextPath() %>/images/setupimages/setup.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
	function cdiv(obj){
		
		document.getElementById("shuju1").style.background="";
			for(var i=1;i<=3;i++){
			if("shuju"+i==obj.id){
			 with(obj.style)
			 {
				background="url(<%=request.getContextPath() %>/images/setupimages/stup_11.jpg)";
				backgroundRepeat ="no-repeat";
				backgroundPosition="right";
				backgroundWidth="170px";
				backgroundHeight="30px";
				fontWeight="bold";
			}
			}
			else
			{
				document.getElementById("shuju"+i).style.background="";
			}
		}
	}
	

	function first()
	{
		with(document.getElementById("shuju1").style)
		{
			background ="url(<%=request.getContextPath() %>/images/setupimages/stup_11.jpg)";
			backgroundRepeat ="no-repeat";
			backgroundPosition="right";
			backgroundWidth="170px";
			backgroundHeight="30px";
			fontWeight="bold";
		}
	}
	
</script>

</head>
<body style="margin:0 5px; background:#ebecee;" onload="first()">
<table width="160" border="0" align="left" cellpadding="0" cellspacing="0" >
  <tr >
    <td class="menuleft" align="left" valign="middle">
	<div onclick="cdiv(this);"  id="shuju1">
	<a href="<%=request.getContextPath()%>/setup/upload_lic.jsp" target="mainFrame" class="two"><font style="margin-left:25px;" >licence文件上传</font></div></a></td>
  </tr>
  <tr>
    <td class="menuleft" align="left" valign="middle">
		<div onclick="cdiv(this);"   id="shuju2">
	<a href="<%=request.getContextPath()%>/setup/data_set.jsp" target="mainFrame" class="two"><font style="margin-left:25px;">服务器参数设置</font></div></a></td>
  </tr>
  <tr>
    <td class="menuleft" align="left" valign="middle">
	<div onclick="cdiv(this);" id="shuju3" >
	<a href="<%=request.getContextPath()%>/setup/init_detail.jsp" target="mainFrame" class="two"><font style="margin-left:25px;">数据初始化</font></a></div></td>
  </tr>
</table>
</body>
</html>
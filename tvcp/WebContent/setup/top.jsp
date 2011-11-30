<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>CPS后台管理</title>
<link href="<%=request.getContextPath() %>/images/setupimages/setup.css" type="text/css" rel="stylesheet" />

</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="<%=request.getContextPath() %>/images/setupimages/stup_06.jpg">
  <tr>
    <td width="160"><img src="<%=request.getContextPath() %>/images/setupimages/stup_03.jpg" width="160" height="64" /></td>
    <td width="203"><img src="<%=request.getContextPath() %>/images/setupimages/stup_04.jpg" width="203" height="64" /></td>
    <td class="one"><img src="<%=request.getContextPath() %>/images/setupimages/stup_06.jpg" width="2" height="64" /></td>

		 <td width="17" class="one"  valign="bottom"><img src="<%=request.getContextPath() %>/images/setupimages/stup_04.gif" width="17" height="18" class="three"/></td>
	  <td width="50" class="one" align="left" valign="bottom" >
<a href="<%=request.getContextPath() %>/SetupServlet?dealMethod=logout" class="three"><font style="margin-left:5px">退出</font></a>
	
</td>
  </tr>
</table>
</body>
</html>

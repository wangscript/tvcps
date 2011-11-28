<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<%@include file="/templates/headers/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta   http-equiv="Expires"   CONTENT="0">     
  <meta   http-equiv="Cache-Control"   CONTENT="no-cache">     
  <meta   http-equiv="Pragma"   CONTENT="no-cache"> 
<title>提示没有网站</title>
  <script   language="JavaScript">     
  <!--     
  javascript:window.history.forward(1);     
  //-->     
  </script>
<script type="text/javascript">

function gotoPage() { 	
	window.location = "<c:url value="/module/site_manager/site_detail.jsp?siteNull=1"/>"; 
	} 

function logout(){		
	top.document.location="loginaction.do?logintype=loginout";
}
</script>
</head>
<body bgcolor="#CCCCCC" LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>
<table width="100%" height="500" border="0" cellpadding="0" cellspacing="0">
  <tr align="center">
    <td>
		<table width="100" border="1" align="center" cellpadding="8" cellspacing="0" bordercolor="#A0A0A0">
        <tr>
          <td bgcolor="#E6E6E6"> 
            <TABLE WIDTH=390 BORDER=0 align="center" CELLPADDING=0 CELLSPACING=0>
              <TR> 
                <TD height="200" bgcolor="#FFFFFF"> 
                  <table width="300" border="0" align="center" cellpadding="7" cellspacing="4">
                    <tr>
	                    <td colspan="5">
							<font color="#990000">您当前没有网站，请在创建之后再登陆</font>
						</td>
					</tr>
					<tr align="center"> 
						<td>
							<font color="#990000">点击<a onclick="gotoPage()" style="color:blue;cursor:hand;">创建网站</a>页面</font>
						</td>
					</tr>
					<tr>
					</tr>
					<tr align="center">
						<td>
							<input type="button" class="button" value="返 回" onClick="logout();">
						</td>
                    </tr>
                  </table>   
               </TD>
              </TR>
            </TABLE>
          </td>
        </tr>
      </table>
	</td>
  </tr>
</table>
</BODY>
</html>
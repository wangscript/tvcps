<%@ page contentType="text/html;charset=UTF-8"%>

<HTML>
<HEAD>
<TITLE>登陆信息出错</TITLE>
<script>
	function logout(){		
		top.document.location="loginaction.do?logintype=loginout";
	}
</script>
</HEAD>
<BODY BGCOLOR=#CCCCCC LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><table width="100" border="1" align="center" cellpadding="6" cellspacing="0" bordercolor="#A0A0A0">
        <tr>
          <td bgcolor="#E6E6E6"> 
            <TABLE WIDTH=406 BORDER=0 align="center" CELLPADDING=0 CELLSPACING=0>
              
              <TR> 
                <TD height="160" bgcolor="#FFFFFF"> 
                  <table width="273" border="0" align="center" cellpadding="6" cellspacing="3">
                    <tr>
                      <td colspan="2"><font color="#990000"><%=request.getAttribute("message")%></font><input type="button" class="button" value="返 回" onClick="logout();"></td>
                    </tr>
                  </table>
               </TD>
              </TR>

            </TABLE>
          </td>
        </tr>
      </table></td>
  </tr>
</table>
</BODY>
</HTML>
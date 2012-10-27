<%@ page contentType="text/html;charset=UTF-8"%>

<HTML>
<HEAD>
<TITLE>登陆时没有网站</TITLE>
<script>
	function logout(){		
		top.document.location="loginaction.do?logintype=loginout";
	}
</script>
</HEAD>
<BODY BGCOLOR=#CCCCCC LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><table width="100" border="1" align="center" cellpadding="8" cellspacing="0" bordercolor="#A0A0A0">
        <tr>
          <td bgcolor="#E6E6E6"> 
            <TABLE WIDTH=390 BORDER=0 align="center" CELLPADDING=0 CELLSPACING=0>
              
              <TR> 
                <TD height="160" bgcolor="#FFFFFF"> 
                  <table width="300" border="0" align="center" cellpadding="7" cellspacing="4">
                    <tr>
                      <td colspan="5"><font color="#990000">你当前没有网站，请联系管理员!</font><input type="button" class="button" value="返 回" onclick="logout()"></td>
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
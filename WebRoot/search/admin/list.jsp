<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8" import="com.baize.ccms.search.util.GlobalFunc"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/complat.tld" prefix="complat"%>
<script> 
var app = "${appSearchPath}"; 
javascript:window.history.forward(1);     
</script> 
<c:if test="${sessionScope.searchUser eq null}">
	<%
		response.sendRedirect(request.getContextPath() + "/search/admin/index.jsp");
	%>
</c:if>
<html>
	<head>
		<title>全文检索 -百泽检索系统后台管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="description" content="南京百泽,搜索引擎,全文检索、百泽搜索">
		<meta name="keywords" content="网页搜索,图片搜索,全文检索,新闻搜索,百泽搜索,搜索引擎,搜索">
		<meta name="author" content="百泽网络">
		<link href="<c:url value="/css/search.css"/>" type="text/css"	rel="stylesheet">	
		<link href="<c:url value="/css/style.css"/>" type="text/css"	rel="stylesheet">
		<script type="text/javascript" src="<c:url value='/script/jquery-1.2.6.js'/>" ></script>
		<script type="text/javascript" src="<c:url value='/script/global.js'/>" ></script>		 
		<script type="text/javascript" src="<c:url value='/script/jquery.funkyUI.js'/>" ></script>
	</head>
	<script>
	/**修改密码*/
	function changepassword(){
		var url = "<c:url value="/search/admin/changePassword.jsp"/>"; 
		win = showWindow("changepassword","修改密码",url,0,0,400,250);
	}
	function closeNewChild() {		
		closeWindow(win);
	}
	function logout(){
		window.location.href="<c:url value="/searchAdminAction.do?dealMethod=logout"/>";
	}
	//删除网站
	function deleteSite(id){
		document.getElementById("dealMethod").value = "deleteSite";
		document.getElementById("siteId").value = id;
		document.searchForm.submit();//提交		 
	}
	//修改网站
	function modifySite(id){
		document.getElementById("dealMethod").value = "modifySite";
		document.getElementById("siteId").value = id;
		$("#newSiteId").val($("#newSiteId_"+id).val()); 
		$("#newSiteName").val($("#newSiteName_"+id).val());
		document.searchForm.submit();//提交		 
	}
	//添加网站
	function addSite(){
		document.getElementById("dealMethod").value = "addSite";
		document.searchForm.submit();//提交	
	}
	</script>
<body>
<table width="1002" height="26" border="0" align="center" cellpadding="0" cellspacing="0" background="<c:url value="/images/search/bg.jpg"/>">
  <tr> 
    <td width="60"></td>
	<td align="left" valign="top" ><img src="<c:url value="/images/search/banner.gif"/>"  height="80" alt="百泽全文检索系统"></td>
	<td width="60" style="color:#ffffff;">[帮助]</td>
  </tr>
</table><br>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#efebef">
  <tr>
	<td width="250" align="center" bgcolor="#ffffff" valign="top">
	    <table cellSpacing=0 cellPadding=2 border=0 width="80%" style="line-height:26px; border:#999999 1px solid;">
		  <tr>
			<td bgcolor="#eeeeee" style="border-bottom:#999999 1px solid;"><STRONG>个人信息管理</STRONG></td>
		  </tr>
		  <tr>
			<td align="left" valign="top" style="padding-left:20px">用户信息：admin</td>
		  </tr>
  		  <tr>
			<td align="left" valign="top" height="80"  style="padding-left:20px">版本信息：v1.0</td>
		  </tr>
		  <tr>
		    <td align="center" ><hr width="95%" style=" color:#999999;"></td>
		  </tr>	 
		  <tr>
			<td align="center" >
				<INPUT  type=button  class="btn_normal" value=信息修改 name=submit onclick="changepassword()">&nbsp;&nbsp;
				<INPUT  type=button  class="btn_normal" value=退出登陆 name=submit onclick="logout()">
			</td>
		  </tr>
		</table>
    </td>
	<td height=400 align="left" bgcolor="#ffffff" valign="top">
		<table width="98%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td height="10"></td>
			</tr>
			<tr>
				<td style="line-height:26px; border:#999999 1px solid;">&nbsp;□网站管理</td>
			</tr>
			<tr>
				<td height="10"></td>
			</tr>
		</table>
		
		<form name="searchForm" method="post" action="searchAdminAction.do" id="searchForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/> 
		<input type="hidden" name="newSiteId" id="newSiteId"/>
		<input type="hidden" name="newSiteName" id="newSiteName"/>
		<table width="95%" align="center" cellpadding="1" cellspacing="0" style="line-height:26px;">
			<tr>
			  <td align="left" width="300">网站ID：<input type="text"  class="input_text_normal" style="width:130px" name="siteId" id="siteId"/></td>
		      <td align="left" width="300">网站名称：<input type="text"  class="input_text_normal" style="width:130px" name="siteName" id="siteName"/></td>
		      <td align="left"><input type="button" name="button"  class="btn_normal"  value="添加" onclick="addSite()"/></td>
		    </tr>
			<tr>
			  <td height="10"></td>
		      <td></td>
		      <td></td>
		    </tr>		 
		 
		</table>
		<table width="95%" align="center" cellpadding="1" cellspacing="0" style="border:#999999 1px solid;">
			<c:forEach items="${searchForm.resultList}" var="obj">
			<tr>
				<td align="left" height="28" width="300">网站ID：<input type="text"  class="input_text_normal" style="width:130px" name="newSiteId1" id="newSiteId_${obj[0]}" value="${obj[0]}"></td>
			    <td align="left" width="300">网站名称：<input type="text"  class="input_text_normal" style="width:130px" name="newSiteName1" id="newSiteName_${obj[0]}" value="${obj[1]}"></td>
			    <td align="left">
					<input type="button" name="update"  class="btn_normal" value="修改" onclick="modifySite('${obj[0]}')"/>&nbsp;&nbsp;
					<input type="button" name="delete"  class="btn_normal" onclick="deleteSite('${obj[0]}')" value="删除"/>
				</td>
			</tr>
		    </c:forEach>
		</table>
		</form>
		 
	</td>
  </tr>
</table>
<br>
<table width="1002" border="0" align="center" cellpadding="2" cellspacing="0">
  <tr> 
    <td align="center" bgcolor="#eeeeee" height="5"></td>
  </tr>
  <tr> 
    <td align="center">Copyright&nbsp;1999-2005 www.baizeweb.com All Rights Reserved<br>南京百泽网络科技有限公司版权所有 </td>
  </tr>
</table>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>全文检索 -百泽检索系统后台管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="description" content="南京百泽,搜索引擎,全文检索、百泽搜索">
<meta name="keywords" content="网页搜索,图片搜索,全文检索,新闻搜索,百泽搜索,搜索引擎,搜索">
<meta name="author" content="百泽网络"> 
<style type="text/css">
	body,p,tr,td,div,input {font-size:10.5pt;}
	a{font-size:9pt;text-decoration: none}
</style>
<script> 
	//提交是验证
	function funSubmit(){
		var username = document.searchForm.user.value ;
		var password = document.searchForm.pass.value ;
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
	function setFocus(){
		var m = document.getElementById("infoMessage").value;
		if(m!=""){
			alert(m);
		}
	    document.searchForm.userName.focus();
	}
	//加载时调用事件
	window.attachEvent("onload",setFocus);
</script>
</head>
<body onload=document.searchForm.userName.focus();>
<form id="searchForm" name="searchForm" action="<c:url value="/searchAdminAction.do"/>" method="post" onsubmit="return funSubmit();">
<input type="hidden" name="dealMethod" id="dealMethod" value="checkLogin"> 
<input type="hidden" name="infoMessage" id="infoMessage" value="${searchForm.infoMessage}"/>
<br><br><br><br>
<table cellSpacing=0 cellPadding=0 align="center" border=0>
  <tr valign=top>
    <td align=middle height=245><img src="<c:url value="/images/search/search_logo.gif"/>" border="0" width="506" height="192"></td>
  </tr>
  <tr>
    <td align=middle height=40><br>
      <table width=300 border=0 align="center" cellPadding=4 cellSpacing=1 bgColor="#808080">
        <tr>
          <td align=middle bgColor=#efefef><b>检 索 系 统 管 理</b></td>
		</tr>
        <tr>
          <td align=center bgColor=#ffffff>           
            <table cellSpacing=0 cellPadding=0 border=0>
              <tr>
                <td width="30">用户</td>
                <td><input id=userName style="WIDTH: 12em" maxLength=15 size=12 name=userName></td>
                <td>&nbsp;&nbsp;</td>
			  </tr>
              <tr>
                <td width="30">口令</td>
                <td><input id=passWord style="WIDTH: 12em" type=password maxLength=15 size=12 name=passWord></td>
                <td width="40" align="center"><input class=button type=submit value=登录 name=submit></td>
			  </tr>
			</table>
		  </td>
		</tr>
	  </table>
	</td>
  </tr>
</table>
 </form>
</body>
</html>

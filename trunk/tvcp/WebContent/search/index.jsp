<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>全文检索 -检索系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="description" content="搜索引擎,全文检索、搜索">
<meta name="keywords" content="网页搜索,图片搜索,全文检索,新闻搜索,搜索,搜索引擎,搜索">
<meta name="author" content="网络">
<link rel="icon" type="image/x-icon" href="../favicon.ico" >
<link rel="shortcut icon" type="image/x-icon" href="../favicon.ico" >
<link rel="bookmark" type="image/x-icon" href="../favicon.ico"> 
<style type="text/css">
	body,p,tr,td,div,input {font-size:10.5pt;}
	a{font-size:9pt;text-decoration: none}
</style>
</head>
<body onLoad="document.searchForm.keyValue.focus();">
<table width="600" border="0" align="center" cellpadding="2" cellspacing="2">
  <tr>
    <td align="center"><img src="<c:url value="/images/search/search_logo.gif"/>" border="0" width="506" height="192"></td>
  </tr>
</table>
<br>
<table width="600" border="0" align='center' cellpadding="2" cellspacing="2" >
  <form action="<c:url value="/searchAction.do"/>" method="post" name="searchForm">
	<input type="hidden" name="dealMethod" value="searchlist"/>
    <tr>
      <td width="444" align="right">
		<input name="keyValue" id="keyValue" size="40" maxlength="50">&nbsp;&nbsp;
        <input type="submit" value=" 搜 索 " >        
      </td>
      <td width="15" align="left" valign="middle">&nbsp;</td>
      <td width="121" align="left" valign="middle"><a href="JavaScript:showHelp()">搜索帮助</a>
        </td>
    </tr>
  </form>
</table>
</body>
</html>

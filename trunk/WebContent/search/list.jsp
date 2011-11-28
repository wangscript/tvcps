<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/complat.tld" prefix="complat"%>
<HTML>
<HEAD>
	<title>全文检索 -百泽检索系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta name="description" content="南京百泽,搜索引擎,全文检索、百泽搜索">
	<meta name="keywords" content="网页搜索,图片搜索,全文检索,新闻搜索,百泽搜索,搜索引擎,搜索">
	<meta name="author" content="百泽网络">
	<LINK href="<c:url value="/css/search.css"/>" type="text/css"	rel="stylesheet">	
	<script type="text/javascript" src="<c:url value='/script/jquery-1.2.6.js'/>" ></script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/ajaxpagination.css"/>">
	<script type="text/javascript" src="<c:url value="/script/jquery.pagination.js"/>"></script>
</HEAD>
<BODY>
<TABLE> 
  <TR>
    <TD> 
		<img src="<c:url value="/images/search/search.gif"/>" border="0" width="150" height="55" align="absmiddle" />
	</TD>
    <TD>
     <form action="searchAction.do" method="post" name="searchForm">
		 <input type="hidden" name="dealMethod" value="searchlist"/>	
		 <a class=tip onclick=JavaScript:h(this) href="">设为首页</a>&nbsp;&nbsp; 
	     <a class=tip href="javascript:af()">加入收藏</a>&nbsp;&nbsp;
		 <a class=tip href="javascript:showHelp()">搜索帮助</a>&nbsp;&nbsp; 
		 <BR>
		 <input name="keyValue" id="keyValue" size="40" maxlength="50">
		 <input type=submit value=全文检索>  	 
     </form>
	</TD>
  </TR> 
</TABLE><BR>
<div id="pp" >
<A class=totalleft>搜索：${searchForm.keyValue}</A><A class=totalright>共有 <B>${searchForm.resultCount}</B> 条结果， 这是第 <B>${searchForm.infoStart} - ${searchForm.infoEnd}</B> 条。 [搜索用时：<B>${searchForm.searchTime}</B> 毫秒] </A><BR>
	<div style="margin-left: 15px" >
		<c:forEach var="searchEntity" items="${searchForm.resultList}">
		    <P><A class="subject" href="${searchEntity.href}" target="_blank" title="${searchEntity.href}">${searchEntity.name}</A>
				<BR>${searchEntity.result} <BR>
			<A class=green>${searchEntity.href}&nbsp;-&nbsp;${searchEntity.fileSize }&nbsp;-&nbsp;${searchEntity.lastModifyTime}</A></P> 
		</c:forEach>
	</div>
 </div>
<complat:ajaxpage page="${searchForm.pagination}" pageId="pp" form="searchForm" action="searchAction.do?dealMethod=searchlistmsg&keyValue2=${searchForm.keyValue}"/>
<br><br><br>
<CENTER>
     <form action="searchAction.do" method="post" name="searchForm">
		<input type="hidden" name="dealMethod" value="searchlist"/>	
		<input name="keyValue" id="keyValue" size="40" maxlength="50">
		<input type=submit value=全文检索>  	 
     </form>
</CENTER>
<CENTER>Powered by <A class=copyright href="http://www.baizeweb.com/" 
target=_blank>百泽网络</A><BR></CENTER></BODY></HTML>

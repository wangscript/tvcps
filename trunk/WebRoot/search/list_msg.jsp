<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/complat.tld" prefix="complat"%>
<HTML>
	<HEAD></HEAD>
	<BODY>	 
	<A class=totalleft>搜索：${searchForm.keyValue}</A><A class=totalright>共有 <B>${searchForm.resultCount}</B> 条结果， 这是第 <B>${searchForm.infoStart} - ${searchForm.infoEnd}</B> 条。 [搜索用时：<B>${searchForm.searchTime}</B> 毫秒] </A><BR>
		<c:forEach var="searchEntity" items="${searchForm.resultList}">
		    <P><A class=subject href="${searchEntity.href}" target=_blank>${searchEntity.name}</A><BR>${searchEntity.result} <BR>
			<A class=green>${searchEntity.href}&nbsp;-&nbsp;${searchEntity.fileSize }&nbsp;-&nbsp;${searchEntity.lastModifyTime}</A></P> 
		</c:forEach>
	</BODY>
</HTML>

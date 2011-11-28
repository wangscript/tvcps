<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@taglib uri="/WEB-INF/tld/complat.tld" prefix="complat"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>留言分页列表</title>
		<link href='images/commentStyle.css' rel='stylesheet' type='text/css' />
		<!--  		
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>" ></script>
		<script type="text/javascript" src="<c:url value="/script/jquery-1.2.6.js"/>"></script>
		<link rel="stylesheet" type="text/css"	href="<c:url value="/css/ajaxpagination.css"/>">
		<script type="text/javascript"	src="<c:url value="/script/jquery.pagination.js"/>"></script>
		<script type="text/javascript"  src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
 		-->

		<!-- -->
		<link rel="stylesheet" type="text/css" href="css/style.css"></script>
		<script type="text/javascript" src="script/jquery-1.2.6.js"></script>
		<link rel="stylesheet" type="text/css"	href="css/ajaxpagination.css"/>
		<script type="text/javascript"	src="script/jquery.pagination.js"></script>
		<script type="text/javascript"  src="script/My97DatePicker/WdatePicker.js"></script>
 		
<script type="text/javascript">
	$(document).ready(function (){
			var codition = $("#conditionId").val();
			var guestBookCategoryid = $("#guestBookCategoryId").val();
			if(guestBookCategoryid!="null"&&guestBookCategoryid!=""){
				$.each($("#guestBookCategoryId1 option"),function(i,n){
						if(n.value==guestBookCategoryid){
								$(n).attr("selected","selected");
							}
					});
			}
			if(codition!="null"&&codition!=""){
					$.each($("#conditionId1 option"),function(i,n){
							if(n.value==codition){
									$(n).attr("selected","selected");
								}
						});
				}
		});
	function categorChange(){//类别选择改变事件
			$("#dealMethod").val("categoryPageList");
			$("#guestBookForm").submit();
	}
</script>
</head>
<body>
<form action="<c:url value='/guestOuter.do'/>" name="guestBookForm" id="guestBookForm" method="post">
<input type="hidden" name="dealMethod" id="dealMethod" value="conditionPageList">
<input type="hidden" id="conditionId" value="${requestScope.condition }">
<input type="hidden" id="guestBookCategoryId" value="${requestScope.guestBookCategory}">
<table width="100%">
	<tr>
		<td>
			搜索选项:	<select name="condition" id="conditionId1">
						 <option value="title" selected>留言主题</option> 
		                 <option value="bookContent">留言内容</option> 
		                 <option value="bookName">用户名</option> 
		                 <option value="address">联系地址</option> 
		                 <option value="area">所属省份</option> 
		                 <option value="phone">联系电话</option> 
		                 <option value="email">电子邮件</option> 
					</select>
			
			发表时间:	<input type="text" name="starTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"readonly="readonly" value="${requestScope.startTime}"class="Wdate"/>
				至	<input type="text" name="endTime"onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" value="${requestScope.endTime}" class="Wdate"/>
		</td>
	</tr>	
	<tr>
		<td>
			<div align="center">
			关键字:	<input type="text" name="keyword" value="${requestScope.keyword}"/>
					<input type="submit" value="搜索"/>
					<input type="button" class="btn" onClick="javascript:window.location='<c:url value='/guestOuter.do?dealMethod=showAddContent'/>'" value="我要留言"/>
			</div>
		</td>
	</tr>
	<tr>
		<td align="center">
				<div align="center">留言类别:<select id="guestBookCategoryId1" name="guestBookCategory" onchange="categorChange();">
							<option selected="selected" value="">全部</option>
						<c:forEach items="${sessionScope.categoryList}" var="list">
							<option value="${list.id}">${list.categoryName}</option>
						</c:forEach>
					</select>
				</div>
		</td>
	</tr>	
</table>
	<complat:ajaxpage page='${guestBookForm.pagination}' pageId='pp'	form='guestBookForm'	action='guestOuter.do?dealMethod=showContentList1' />
<div id='pp' >
	${requestScope.content}
</div>
</body>
</form>
</html>
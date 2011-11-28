<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>属性管理</title>
	<%@include file="/templates/headers/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			if($("#message").val() != null && $("#message").val() != "") {
				var publishType = $("#publishType").val();
				if (publishType == "column") {
					parent.changeFrameUrl("rightFrame", "<c:url value="/module/publish_manager/column_tree.jsp?publishType=column&"/>" + getUrlSuffixRandom());
				} else if (publishType == "article"){
					parent.changeFrameUrl("rightFrame","<c:url value="/module/publish_manager/column_tree.jsp?publishType=article&"/>" + getUrlSuffixRandom());
				} else if(publishType == "publishHome"){
					parent.changeFrameUrl("rightFrame","<c:url value="/module/publish_manager/column_tree.jsp?publishType=publishHome&"/>" + getUrlSuffixRandom());
				} else if(publishType == "createIndex"){
					parent.changeFrameUrl("rightFrame","<c:url value="/module/publish_manager/column_tree.jsp?publishType=createIndex&"/>" + getUrlSuffixRandom());
				} else {					
					parent.changeFrameUrl("rightFrame","<c:url value="/module/publish_manager/column_tree.jsp?publishType=column&"/>" + getUrlSuffixRandom());
				}
			}
		});
	
		function publish() {
			$("#columnIds").val($("#tree_checkedIds").val());
			$("#publishForm").submit();
		}
	
		function tree_onclick(node) {
			var columnId = node.id;
			var formatId = 0;
			if (node.attributes.formatid) {
				formatId = node.attributes.formatid;
			}
		}

		function publishAllSite() {
			$("#publishAll").val("yes");
			$("#publishForm").submit();
		}

		function indexAllSite(){
			$("#dealMethod").val("index");
			$("#publishAll").val("yes");
			$("#publishForm").submit();
		}

		function index(){
			$("#columnIds").val($("#tree_checkedIds").val());
			$("#dealMethod").val("index");
			$("#publishForm").submit();
		}
	</script>
</head>
<body>
<form id="publishForm" name="publishForm" action="<c:url value='/publish.do'/>">
	<input type="hidden" name="message" id="message" value="${publishForm.infoMessage}" />
	<input type="hidden" id="dealMethod" name="dealMethod" value="publish" />
	<input type="hidden" id="publishAll" name="publishAll" value="${publishForm.publishAll }" />
	<input type="hidden" id="columnIds" name="columnIds"/>
	<input type="hidden" id="publishType" name="publishType" value="<%=request.getParameter("publishType")%>"/>
	<fieldset style="width:400px;">
		<legend>
		<%
			request.setAttribute("publishType", request.getParameter("publishType"));
		%>
			<c:choose>
				<c:when test="${publishType == 'column'}">
					发布栏目页——是否发布模板: <input id="publishTemplate" type="checkbox" name="publishTemplate" />
				</c:when>
				<c:when test="${publishType == 'article'}">
					发布文章页
				</c:when>
				<c:when test="${publishType == 'publishHome'}">
					发布首页
				</c:when>
				<c:when test="${publishType == 'createIndex'}">
					创建索引
				</c:when>
				<c:otherwise>
					失败列表<br/>
				</c:otherwise>
			</c:choose> 
		</legend> 
		<c:choose>
			<c:when test="${publishType == 'column'}">
				<complat:tree unique="column1" checkbox="true"  treeurl="../../column.do?dealMethod=getTree&nodeId=0&nodeName=null"/>
				<input type="button" value="整站发布" class="btn_normal" onclick="publishAllSite();"/>
				<input  type="button" value="发布" class="btn_normal" onclick="publish();"/>
			</c:when>
			<c:when test="${publishType == 'article'}">
				<complat:tree unique="column1" checkbox="true"  treeurl="../../column.do?dealMethod=getTree&nodeId=0&nodeName=null"/>
				<input type="button" value="整站发布" class="btn_normal" onclick="publishAllSite();"/>
				<input  type="button" value="发布" class="btn_normal" onclick="publish();"/>
			</c:when>
			<c:when test="${publishType == 'createIndex'}">
				<complat:tree unique="column1" checkbox="true"  treeurl="../../column.do?dealMethod=getTree&nodeId=0&nodeName=null"/>
				<input type="button" value="整站索引" class="btn_normal" onclick="indexAllSite();"/>
				<input type="button" value="索引" class="btn_normal" onclick="index();"/>
			</c:when>
			<c:otherwise>
				<input  type="button" value="发布" class="btn_normal" onclick="publish();"/>
			</c:otherwise>
		</c:choose>
		
	</fieldset>
</form>
</body>
</html>

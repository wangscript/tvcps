<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>栏目管理</title>	

	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>	
<script>
	var childNewOrgWin = "";
	var childDetailOrgWin = "";

	$(document).ready(function() {
		var message = $("#message").val();
		if(!message.isEmpty()){
			if(message == "删除栏目成功") {
				top.reloadAccordion("/${appName}/module/column_manager/refresh_Tree.jsp?" + getUrlSuffixRandom());
				//window.location.href = "<c:url value='/column.do?nodeId=${columnForm.nodeId}&dealMethod='/>";
			//	var url = "<c:url value='/column.do?dealMethod=&nodeId=${columnForm.nodeId}&operationType=column&localNodeName=${columnForm.localNodeName}&" + getUrlSuffixRandom() + "'/>";
			//	parent.changeFrameUrl("rightFrame", url);
			} 
			if(message == "增加栏目成功" || message == "修改栏目成功" || message == "您已经超出能建立的最大栏目数,请与管理员联系") {
				top.reloadAccordion("/${appName}/module/column_manager/refresh_Tree.jsp?" + getUrlSuffixRandom());
			//	window.location.href="<c:url value='/column.do?dealMethod=&nodeId=${columnForm.nodeId}&localNodeName=${columnForm.localNodeName}&" + getUrlSuffixRandom() + "'/>";
				var url = "<c:url value='/column.do?dealMethod=&nodeId=${columnForm.nodeId}&operationType=column&localNodeName=${columnForm.localNodeName}&" + getUrlSuffixRandom() + "'/>";
				parent.changeFrameUrl("rightFrame", url);
			}
			if(message != "增加栏目成功" && message != "修改栏目成功" && message != "删除栏目成功" && message != "栏目导入成功" && message != "粘贴成功" && message != "栏目排序成功") {
				alert(message);
			} 
		}
	});
	
	function button_add_onclick(ee) {	 
		window.location.href="column.do?dealMethod=findAllFormats&nodeId=${columnForm.nodeId}&localNodeName=${columnForm.localNodeName}&" + getUrlSuffixRandom();
		//	win = showWindow("newcolumn", "新增栏目", "<c:url value='/column.do?dealMethod=findAllFormats&nodeId=${columnForm.nodeId}&nodeName=${columnForm.nodeName}'/>", 250, 90, 360, 341);	 
	}
	
	function button_delete_onclick(ee) {	
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null) {
			alert("请至少选择一条记录操作！");
			return false;
		}
 		if(confirm("删除栏目会将该栏目下的文章全部删除,你确定要删除吗？")) {
 			document.getElementById("strid").value = document.getElementById("xx").value;
 			document.all("dealMethod").value = "delete";	
			document.forms[0].submit(); 
 		} else { 
 	 		return false;
 	 	}
	}
	
	function button_import_onclick(ee) {
		var url = "<c:url value='/module/column_manager/column_import.jsp?nodeId=${columnForm.nodeId}&localNodeName=${columnForm.localNodeName}&" + getUrlSuffixRandom() + "'/>";
		win = showWindow("importcolumn", "栏目导入", url, 250, 90, 500, 300);
	}
	 
	function button_export_onclick(ee) {
		var exportColumnIds = document.getElementById("xx").value;
		if(confirm("你确定要导出吗？")) {
			$.ajax({
				url:  "<c:url value='/column.do?dealMethod=export&nodeId=${columnForm.nodeId}&localNodeName=${columnForm.localNodeName}&exportColumnIds="+ exportColumnIds +"'/>",
			   type: "post", 
		    success: function(msg) {
				    var arr = msg.split("##");
					var url = "<c:url value='/column.do?dealMethod=savaExportColumn&nodeId="+ arr[1] +"&localNodeName="+ arr[2] +"'/>";
					parent.changeFrameUrl("rightFrame", url);
	     		}
			}); 
 		} else { 
 	 		return false;
 	 	}
	}
	
	function showResult(btn) {

		if(btn == "yes") {
			document.getElementById("needchild").value = 1;
		} else if(btn == "no") {
			document.getElementById("needchild").value = 0;
		} else {
			return false;
		}
		var ids = document.getElementById("xx").value;
		var needchild = document.getElementById("needchild").value;
		var url = "<c:url value='/module/column_manager/choose_column.jsp?nodeId=${columnForm.nodeId}&ids="+ids+"&needchild="+needchild+"&localNodeName=${columnForm.localNodeName}&isCopy=1?" + getUrlSuffixRandom()+"'/>"; 
		win = showWindow("copyColumn", "选择要粘贴的栏目位置", url, 250, 90, 370, 500);
	}
	
	function button_copy_onclick(ee) {
		var ids = document.getElementById("xx").value;
		if(ids == "" || ids == null){
			alert("你必须先选择复制对象");
			return false;
		}
		confirmQuestion("是否要粘贴栏目下的子栏目？");	  
	}
	
	function button_sort_onclick(ee) {
		var url = "column.do?dealMethod=findSortColumn&nodeId=${columnForm.nodeId}&localNodeName=${columnForm.localNodeName}&" + getUrlSuffixRandom();
		win = showWindow("ordercolumn", "栏目排序", url, 250, 90, 500, 450);
	}
	
	function button_choose_onclick(ee) {
		win = showWindow("selectcolumn", "选择父网站栏目", "column.do?dealMethod=findParentSiteColumn&nodeId=${columnForm.nodeId}&localNodeName=${columnForm.localNodeName}", 250, 90, 370, 500);
	}


	function columnDetail(id) {
        document.getElementById("strid").value = id;
        document.getElementById("dealMethod").value = "detail";
	//	win = showWindow("columndetail", "栏目信息", encodeURI("column.do?dealMethod=detail&nodeId=${columnForm.nodeId}&nodeName=${columnForm.nodeName}&ids="+id), 250, 90, 372, 388);
		$("#columnForm").submit();	 
	}

	  
 
	function closeChild() {
		closeWindow(win);
	}

</script>
</head> 
<body>
	<div class="currLocation">栏目管理  <complat:guide className="Column" nodeId="${columnForm.nodeId}" sign="→ " ></complat:guide>
	</div>
	<form id="columnForm" action="<c:url value="column.do"/>" method="post" name="columnForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="needchild" id="needchild" value="${columnForm.needchild }"/>
		<input type="hidden" name="ids" id="strid" value="<%=request.getParameter("ids") %>" />
        <input type="hidden" name="nodeId" id="nodeId" value="${columnForm.nodeId }" />
		<input type="hidden" name="localNodeName" id="localNodeName" value="${columnForm.localNodeName }" />
		<input type="hidden" name="message" id="message" value="${columnForm.infoMessage}" />
		<input type="hidden" name="exportColumnIds" id="exportColumnIds" value="${columnForm.exportColumnIds }"/>
		<input type="hidden" name="isRootSite" id="isRootSite" value="${columnForm.isRootSite}"/>
		<input type="hidden" name="operationType" value="column" />
		<c:if test="${columnForm.isRootSite eq 'yes'}">
			<complat:button buttonlist="add|0|delete|0|import|0|export|0|copy|0|sort"  buttonalign="left" operationList="${columnForm.operationList}"/>
		</c:if>
		<c:if test="${columnForm.isRootSite eq 'no'}">
			<complat:button buttonlist="add|0|delete|0|import|0|export|0|copy|0|sort|0|choose"  buttonalign="left" operationList="${columnForm.operationList}"/>
		</c:if>
		<complat:grid ids="xx" width="*,*,*,*,*"  head="栏目名称,上级栏目,所属网站,创建者,创建时间"  
			element="[1,link,onclick,columnDetail]" 
			page="${columnForm.pagination}" form="columnForm" action="column.do" />
	</form>
</body>
</html>

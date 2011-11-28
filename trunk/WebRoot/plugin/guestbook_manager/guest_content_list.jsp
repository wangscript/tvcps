<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>留言管理列表</title>
	<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">
function button_all_onclick(){	//全部
	    $("#dealMethod").val("");
		$("#guestBookForm").submit();
}
function button_undispense_onclick(){//未分发
    $("#dealMethod").val("btnNoSend");
	$("#guestBookForm").submit();
}
	//分发
function button_dispense_onclick() {
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null){
		alert("请至少选择一条记录操作！");
		return false;
	}else{
		win = showWindow("edit","分发用户","<c:url value='/guestCategory.do?dealMethod=showAllUser&ids="+ids+"'/>", 293, 0, 750, 520);
	}
}
function button_unrevert_onclick(){//待回复
    $("#dealMethod").val("replaying");
	$("#guestBookForm").submit();
}
function button_reverted_onclick(){//已回复
    $("#dealMethod").val("replayed");
	$("#guestBookForm").submit();
}
function button_unaudit_onclick(){//未审核
    $("#dealMethod").val("auditing");
	$("#guestBookForm").submit();
}
function button_audited_onclick(){//已审核
    $("#dealMethod").val("audited");
	$("#guestBookForm").submit();
}
function button_nodispose_onclick(){//不处理
    $("#dealMethod").val("nonoice");
	$("#guestBookForm").submit();
}
function button_delete_onclick(){//删除
	var ids = document.getElementById("xx").value;
	if(ids == "" || ids == null){
		alert("请至少选择一条记录操作！");
		return false;
	}
	if(confirm("确定删除所选留言！(提示:如果该留言已经回复，回复的内容也将删除)")){
		document.getElementById("strid").value = ids;
	    $("#dealMethod").val("deleteContent");
		$("#guestBookForm").submit();
	}
}
function show(id) {
	win = showWindow("edit","留言","<c:url value='/guestCategory.do?dealMethod=contentDetail&ids="+id+"'/>", 293, 0, 688, 500);
}
</script>
</head>
<body>
<form action="<c:url value='/guestCategory.do'/>" method="post" name="guestBookForm" id="guestBookForm">
<div class="currLocation">功能单元→ 留言本→ 留言管理</div>
	<input type="hidden" name="ids" id="strid"/>
	<input type="hidden" name="dealMethod" id="dealMethod"/>

<span style="margin:0 0 0 0;cursor:pointer;" onclick="button_all_onclick(this);">
<img style="vertical-align:middle;cursor:pointer;width:21px;height:21px;" src="<c:url value='/images/button/out/All.jpg'/>" 
onmouseover="changeImgBg(this,'<c:url value='/images/button/over/All.jpg'/>')"
onmouseout="changeImgBg(this,'<c:url value='/images/button/out/All.jpg'/>')"/> 
<font style="vertical-align:middle;color:#000;">全部</font></span>

<c:if test="${guestBookForm.isPopedom eq 'yes'}">
<span style="margin:0 0 0 0;cursor:pointer;" onclick="button_dispense_onclick(this);">
<img style="vertical-align:middle;cursor:pointer;width:21px;height:21px;" src="<c:url value='/images/button/out/dispense.jpg'/>" 
onmouseover="changeImgBg(this,'<c:url value='/images/button/over/dispense.jpg'/>')"
onmouseout="changeImgBg(this,'<c:url value='/images/button/out/dispense.jpg'/>')"/> 
<font style="vertical-align:middle;color:#000;">分发</font></span>

<span style="margin:0 0 0 0;cursor:pointer;" onclick="button_undispense_onclick(this);">
<img style="vertical-align:middle;cursor:pointer;width:21px;height:21px;" src="<c:url value='/images/button/out/undispense.jpg'/>" 
onmouseover="changeImgBg(this,'<c:url value='/images/button/over/undispense.jpg'/>')"
onmouseout="changeImgBg(this,'<c:url value='/images/button/out/undispense.jpg'/>')"/> 
<font style="vertical-align:middle;color:#000;">未分发</font></span>
</c:if>

<span style="margin:0 0 0 0;cursor:pointer;" onclick="button_unrevert_onclick(this);">
<img style="vertical-align:middle;cursor:pointer;width:21px;height:21px;" src="<c:url value='/images/button/out/unrevert.jpg'/>" 
onmouseover="changeImgBg(this,'<c:url value='/images/button/over/unrevert.jpg'/>')"
onmouseout="changeImgBg(this,'<c:url value='/images/button/out/unrevert.jpg'/>')"/> 
<font style="vertical-align:middle;color:#000;">未回复</font></span>

<span style="margin:0 0 0 0;cursor:pointer;" onclick="button_reverted_onclick(this);">
<img style="vertical-align:middle;cursor:pointer;width:21px;height:21px;" src="<c:url value='/images/button/out/reverted.jpg'/>" 
onmouseover="changeImgBg(this,'<c:url value='/images/button/over/reverted.jpg'/>')"
onmouseout="changeImgBg(this,'<c:url value='/images/button/out/reverted.jpg'/>')"/> 
<font style="vertical-align:middle;color:#000;">已回复</font></span>

<span style="margin:0 0 0 0;cursor:pointer;" onclick="button_nodispose_onclick(this);">
<img style="vertical-align:middle;cursor:pointer;width:21px;height:21px;" src="<c:url value='/images/button/out/nodispose.jpg'/>" 
onmouseover="changeImgBg(this,'<c:url value='/images/button/over/nodispose.jpg'/>')"
onmouseout="changeImgBg(this,'<c:url value='/images/button/out/nodispose.jpg'/>')"/> 
<font style="vertical-align:middle;color:#000;">不处理</font></span>

<span style="margin:0 0 0 0;cursor:pointer;" onclick="button_unaudit_onclick(this);">
<img style="vertical-align:middle;cursor:pointer;width:21px;height:21px;" src="<c:url value='/images/button/out/unaudit.jpg'/>" 
onmouseover="changeImgBg(this,'<c:url value='/images/button/over/unaudit.jpg'/>')"
onmouseout="changeImgBg(this,'<c:url value='/images/button/out/unaudit.jpg'/>')"/> 
<font style="vertical-align:middle;color:#000;">未审核</font></span>

<span style="margin:0 0 0 0;cursor:pointer;" onclick="button_audited_onclick(this);">
<img style="vertical-align:middle;cursor:pointer;width:21px;height:21px;" src="<c:url value='/images/button/out/audited.jpg'/>" 
onmouseover="changeImgBg(this,'<c:url value='/images/button/over/audited.jpg'/>')"
onmouseout="changeImgBg(this,'<c:url value='/images/button/out/audited.jpg'/>')"/> 
<font style="vertical-align:middle;color:#000;">已审核</font></span>

<span style="margin:0 0 0 0;cursor:pointer;" onclick="button_delete_onclick(this);">
<img style="vertical-align:middle;cursor:pointer;width:21px;height:21px;" src="<c:url value='/images/button/out/delete.jpg'/>" 
onmouseover="changeImgBg(this,'<c:url value='/images/button/over/delete.jpg'/>')"
onmouseout="changeImgBg(this,'<c:url value='/images/button/out/delete.jpg'/>')"/> 
<font style="vertical-align:middle;color:#000;">删除</font></span>

<complat:grid ids="xx" width="*,*,*,*,*,*,*,*"  
		head="留言主题,所属类别,发表人,发表时间,回复人,回复状态,审核状态,操作" 
		coltext="[col:6,0:【未回复】,1:【已回复】][col:7,0:【未审核】,1:【已审核】,2:【不处理】,3:【不审核】 ]"
		element="[1,link,onclick,show][8,button,onclick,show,查看]"
		page="${guestBookForm.pagination}" form="guestBookForm" action="/guestCategory.do"/>
        <div id="large"></div> 
</form>
</body>
</html>
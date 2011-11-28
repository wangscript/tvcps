<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章属性设置</title>
<script type="text/javascript">
	$(function loadPage(){
		var openType = $("#openType").val();//开放类型
		if(openType=="0"){
			document.articleCommentForm.isopen[0].checked=true;
		}else{
			document.articleCommentForm.isopen[1].checked=true;
		}
		var isLook = $("#isLook").val();//是否审核
		if(isLook=="0"){
			document.articleCommentForm.ischeck[0].checked=true;
		}else{
			document.articleCommentForm.ischeck[1].checked=true;;
		}
		var ipStyle = $("#ipStyle").val();//IP显示
		if(ipStyle=="0"){
			document.articleCommentForm.ipstyle[0].checked=true;
		}else if(ipStyle=="1"){
			document.articleCommentForm.ipstyle[1].checked=true;
		}else{
			document.articleCommentForm.ipstyle[2].checked=true;
		}
		var haveReplace = $("#haveReplace").val();//有过滤词时替换设置
		if(haveReplace=="0"){
			document.articleCommentForm.isfilter[2].checked=true;
		}else if(haveReplace=="1"){
			document.articleCommentForm.isfilter[1].checked=true;
		}else{
			document.articleCommentForm.isfilter[0].checked=true;
		}
		var replaceArea = $("#replaceArea").val();
		if(replaceArea=="0"){//取词范围
			document.articleCommentForm.filterrange[0].checked=true;
		}else{
			document.articleCommentForm.filterrange[1].checked=true;
		}
		var articleCommentCount = $("#articleCommentCount").val();
		if(articleCommentCount!="0"){
			document.articleCommentForm.ismaxtime[1].checked=true;
			$("#maxtimeId").val(articleCommentCount);
		}else{
			document.articleCommentForm.ismaxtime[0].checked=true;
		}
		var creamCount = $("#creamCount").val();
		if(creamCount!="0"){
			document.articleCommentForm.isballot[1].checked=true;
			$("#ballottimeId").val(creamCount);
		}else{
			document.articleCommentForm.isballot[0].checked=true;
		}
		var timeOutSet = $("#timeOutSet").val();
		if(timeOutSet!="0"){
			document.articleCommentForm.commentimeOut[1].checked=true;
			$("#ballottimeId1").val(timeOutSet);
		}else{
			document.articleCommentForm.commentimeOut[0].checked=true;
		}
		if(document.articleCommentForm.isballot[0].checked){
			document.articleCommentForm.ballottime.disabled=true;
		}
		if(document.articleCommentForm.commentimeOut[0].checked){
			document.articleCommentForm.ballottime1.disabled=true;
		}
		if(document.articleCommentForm.ismaxtime[0].checked){
			document.articleCommentForm.maxtime.disabled=true;
		}
	});

	
	function submitArticle(){
		if(document.articleCommentForm.isopen[0].checked){//开发类型
			$("#openType").val("0")
		}else{
			$("#openType").val("1")
		}
		if(document.articleCommentForm.ischeck[0].checked){//是否审核
			$("#isLook").val("0");
		}else{
			$("#isLook").val("1");
		}
		
		if(document.articleCommentForm.ipstyle[0].checked){//IP显示样式
			$("#ipStyle").val("0");
		}else if(document.articleCommentForm.ipstyle[1].checked){
			$("#ipStyle").val("1");
		}else{
			$("#ipStyle").val("2");
		}
		
		if(document.articleCommentForm.isfilter[2].checked){//当有过滤词时
			$("#haveReplace").val("0");
		}else if(document.articleCommentForm.isfilter[1].checked){
			$("#haveReplace").val("1");
		}else{
			$("#haveReplace").val("2");
		}
		if(document.articleCommentForm.filterrange[0].checked){//过滤取词范围
			$("#replaceArea").val("0");
		}else{
			$("#replaceArea").val("1");
		}
		if(document.articleCommentForm.ismaxtime[1].checked){//文章最大评论数
			var m=$("#maxtimeId").val();
			if(m.trim()!=""&&parseInt(m.trim())>0&&!isNaN(m)){
				$("#articleCommentCount").val(m);
			}else{
				alert("文章最大评论数必须大于零，并且是必须数字");
				return false;
			}
		}else{
			$("#articleCommentCount").val("0");
		}
		if(document.articleCommentForm.isballot[1].checked){//精华数
			var b=$("#ballottimeId").val();
			
			if(b.trim()!=""&&parseInt(b)>0&&!isNaN(b)){
				
				$("#creamCount").val(b);
			}else{
				alert("精华票数必须大于零，并且是必须数字");
				return false;
			}
		}else{
			$("#creamCount").val("0");
		}
	
		if(document.articleCommentForm.commentimeOut[1].checked){//评论过期时间
			var b1=$("#ballottimeId1").val();
			if(b1.trim()!=""&&parseInt(b1)>0&&!isNaN(b1)){
				
				$("#timeOutSet").val(b1);
			}else{
				alert("评论过期时间必须大于零，并且是必须数字");
				return false;
			}
			
		}else{
			$("#timeOutSet").val("0");
		}
			$("#articleCommentForm").submit();
	}
	function checkNum(){ 
	      if(event.keyCode<48||event.keyCode>57){     
	          event.returnValue=false;        
	  }   
	}
	function clickRad1(obj,obj2){
		if(obj.checked){
			obj2.disabled=true;
		}
	}
	function clickRad2(obj,obj2){
		if(obj.checked){
			obj2.disabled=false;
		}
	}
</script>
</head>
<body scroll="auto" oncontextmenu="return true;" topMargin="0" marginwidth="0" marginheight="0">
<form action="<c:url value='/articleComment.do?dealMethod=saveComment'/>" method="post" name="articleCommentForm" id="articleCommentForm">
<input type="hidden" name="openType" id="openType" value="${articleCommentForm.openType }"/>
<input type="hidden" name="isLook" id="isLook" value="${articleCommentForm.isLook }"/>
<input type="hidden" name="ipStyle" id="ipStyle" value="${articleCommentForm.ipStyle }"/>
<input type="hidden" name="haveReplace" id="haveReplace" value="${articleCommentForm.haveReplace }"/>
<input type="hidden" name="replaceArea" id="replaceArea" value="${articleCommentForm.replaceArea }"/>
<input type="hidden" name="articleCommentCount" id="articleCommentCount" value="${articleCommentForm.articleCommentCount }"/>
<input type="hidden" name="creamCount" id="creamCount" value="${articleCommentForm.creamCount }"/>
<input type="hidden" name="timeOutSet" id="timeOutSet" value="${articleCommentForm.timeOutSet }"/>
<div class="currLocation">功能单元→ 文章评论→ 基本设置→属性设置</div>

<fieldset style="width:70%">
<legend>基本属性</legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="0" align="center" class="opr_tb">
		<tr class="opr_tr">
			<td class='opr_left_td' width="125" height="18" align='right'>开放类型</td>
			<td width="662" class="opr_center_td" >
				<input type="radio" name="isopen" value="0">关闭
				<input type="radio" name="isopen" value="1">开放		
			</td>
		</tr>
		<tr class="opr_tr">
			<td class='opr_left_td' width="125" height="18" align='right'>是否审核</td>
			<td class="opr_center_td" >
				<input type="radio" name="ischeck"  value="0">否&nbsp;&nbsp;
				<input type="radio" name="ischeck"  value="1">是
			</td>
		</tr>
		<tr class="opr_tr">
			<td class='opr_left_td' width="125" height="18" align='right'>IP显示样式	</td>
			<td class="opr_center_td" >
				<input type="radio" name="ipstyle"  value="0">不显示&nbsp;&nbsp;
				<input type="radio" name="ipstyle"  value="1">全部显示
				<input type="radio" name="ipstyle"  value="2">隐藏末尾
			</td>
		</tr>
		<tr class="opr_tr">
			<td class='opr_left_td' width="125" height="18" align='right'>当有过滤词时</td>
			<td class="opr_center_td" >
				<input type="radio" name="isfilter"  value="2">禁止发布
				<input type="radio" name="isfilter"  value="1">替换发布
				<input type="radio" name="isfilter"  value="0">回收站
			</td>
		</tr>
		<tr class="opr_tr">
			<td class='opr_left_td' width="125" height="18" align='right'>过滤词取词范围	</td>
			<td class="opr_center_td" >
				<input type="radio" name="filterrange" value="0">系统内
				<input type="radio" name="filterrange" value="1">评论内
			</td>
		</tr>
		<tr class="opr_tr">
			<td class='opr_left_td' width="125" height="18" align='right'>文章最大评论数	</td>
			<td class="opr_center_td" >			
				<input type="radio" name="ismaxtime"  value="0" onclick="clickRad1(this,document.articleCommentForm.maxtime);">无效
				<input type="radio" name="ismaxtime"  value="1"  onclick="clickRad2(this,document.articleCommentForm.maxtime);">有效&nbsp;&nbsp;
				<input type="text" name="maxtime" id="maxtimeId" class="input_text" size="6" onkeypress="checkNum()"> 条
			</td>
		</tr>
		<tr class="opr_tr">
			<td class='opr_left_td' width="125" height="18" align='right'>自动推荐为精华	</td>
			<td class="opr_center_td" >			
				<input type="radio" name="isballot"  value="0"  onclick="clickRad1(this,document.articleCommentForm.ballottime);">无效
				<input type="radio" name="isballot"  value="1"  onclick="clickRad2(this,document.articleCommentForm.ballottime);">有效&nbsp;&nbsp;
				<input type="text" name="ballottime" id="ballottimeId"  class="input_text" size="6" onkeypress="checkNum()"> 票数转为精华
			</td>
		</tr>
		<tr class="opr_tr">
			<td class='opr_left_td' width="125" height="18" align='right'>评论过期时间</td>
			<td class="opr_center_td" >
				<input type="radio" name="commentimeOut"  value="0"  onclick="clickRad1(this,document.articleCommentForm.ballottime1);">无效
				<input type="radio" name="commentimeOut"  value="1"  onclick="clickRad2(this,document.articleCommentForm.ballottime1);">有效&nbsp;&nbsp;
				<input type="text" name="ballottime1" id="ballottimeId1" class="input_text" size="6" onkeypress="checkNum()">日
			</td>
		</tr>
	</table>
</fieldset>
	<table width="100%" border="0" height="30" cellspacing="1" cellpadding="0" align="center" class="opr_tb">
		<tr class="opr_tr">
			<td align="center" class="opr_td" >
				<input id="editbutton" class='btn_ok' type="button" onclick="submitArticle();" value="保存设置">
			</td>
		</tr>
	</table>
</form>
</body>
</html>

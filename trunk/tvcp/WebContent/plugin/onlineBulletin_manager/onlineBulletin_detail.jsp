<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>网上公告信息添加</title>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/fckeditor/fckeditor.js"/>"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#name").focus();
	   	if(document.getElementById("message").value =="1"){
			rightFrame.window.location.href="<c:url value='/onlineBulletin.do?dealMethod=list&" + getUrlSuffixRandom() + "'/>";
		}	
	});

	// 返回列表
	function back(){
		rightFrame.window.location.href="<c:url value='/onlineBulletin.do?dealMethod=list&" + getUrlSuffixRandom() + "'/>";
	}
	
    // 网上公告信息添加
	function save(){
		$("#onlineBulletinForm").submit();
	}

	function fck_insertHtml(value){	 
		var fck = FCKeditorAPI.GetInstance("onlineBulletin.context");
		fck.InsertHtml(value);	
	}
</script>
</head>
<body>
	<div class="currLocation">网上公告</div>
	<form action="onlineBulletin.do"  method="post" name="onlineBulletinForm" id="onlineBulletinForm" >
   	<input type="hidden" name="dealMethod" id="dealMethod" value="detail" />
    <input type="hidden" name="message" id="message" value="${onlineBulletinForm.infoMessage}"/>
    <input	type="hidden" name="id" id="id" />                                  
	<table width="100%" border="0">
	<tr>
		<td width="10%" class="td_left">公告标题：</td>
		<td width="90%">
			<input type="text" name="onlineBulletin.name"  class="input_text_normal" style="width:85%"  valid="string" tip="公告标题不能为空"/>
		</td>
    </tr>
    <tr>
		<td class="td_left">公告内容：</td> 
		<td>
			<FCK:editor basePath="/script/fckeditor" instanceName="onlineBulletin.context" value=" " toolbarSet="CPS_font" height="160" width="85%"></FCK:editor>
		</td>
    </tr>
    <tr>
		<td class="td_left">载止日期：</td>
	    <td>
			<input type="text" name="onlineBulletin.endTime" class="Wdate" style="width:210px;" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
		</td>
    </tr>
    <tr>
		<td class="td_left">窗口名称：</td>
		<td>
			<input type="text" name="onlineBulletin.windowName" style="width:210px;"  valid="string" tip="公告标题不能为空"/>
		</td>
    </tr>
    <tr>
		<td class="td_left">显示工具栏：</td>
		<!--${onlineBulletin.showTool}-->
		<td>
			<input type="radio"  name="onlineBulletin.showTool" value="true"/>是
			<input type="radio" name="onlineBulletin.showTool"  checked value="false"/>否
		</td>
    </tr>
    <tr>
		<td class="td_left">显示菜单栏：</td>
	    <!--${onlineBulletin.showMenu}-->
		<td>
			<input type="radio"   name="onlineBulletin.showMenu" value="true"/>是
			<input type="radio" name="onlineBulletin.showMenu" checked value="false" />否
		</td>
    </tr>
    <tr>
		<td class="td_left">显示滚动条：</td>
	    <!--${onlineBulletin.showScroll}-->
		<td>
			<input type="radio"   name="onlineBulletin.showScroll"  value="true"/>是
			<input type="radio" name="onlineBulletin.showScroll" checked value="false" />否
		</td>  
    </tr>
    <tr>
		<td class="td_left">改变窗口大小：</td>
	    <!--${onlineBulletin.changeSize}-->
		<td>
			<input type="radio" name="onlineBulletin.changeSize"  value="true"/>是
			<input type="radio" name="onlineBulletin.changeSize" checked value="false" />否
		</td>
    </tr>
    <tr>
		<td class="td_left">显示地址栏：</td> 
	    <!--${onlineBulletin.showAddress}-->
		<td>
			<input type="radio" name="onlineBulletin.showAddress"  value="true"/>是
			<input type="radio" name="onlineBulletin.showAddress"  checked value="false" />否
		</td>
	</tr>
	<tr> 
		<td class="td_left">显示状态信息：</td>
	    <!--${onlineBulletin.showStatus}-->
		<td>
			<input type="radio" name="onlineBulletin.showStatus"   value="true"/>是
			<input type="radio" name="onlineBulletin.showStatus" checked value="false" />否
		</td>
	</tr>
	<tr>
		<td class="td_left">窗口大小：</td>
		<td>
			宽度：<input type="text" name="onlineBulletin.widowWidth" class="input_text_normal" style="width:48px;" value="500" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')" valid="num" tip="不能为空"/>px
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			高度：<input type="text"  name="onlineBulletin.widowHeight" class="input_text_normal" style="width:48px;" value="200" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')" valid="num" tip="不能为空"/>px
		</td>
	</tr>
    <tr>
		<td class="td_left">窗口上下边界：</td>
		<td>
			窗口距离屏幕上方：<input type="text" name="onlineBulletin.windowTop" class="input_text_normal" style="width:48px;" value="200" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')" valid="num" tip="不能为空"/>px
			&nbsp;&nbsp;&nbsp;
			窗口距离屏幕左侧：<input type="text" name="onlineBulletin.windowLeft" class="input_text_normal" style="width:48px;" value="200" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')" valid="num" tip="不能为空"/>px
		</td>
    </tr>
	</table>
	<br>
	<table cellpadding="0" cellspacing="0" border="0" width="90%">
		<tr>
			<td align="center" width="100%">
 				<input type="button" class="btn_normal" onclick="save()" value="保存"/>&nbsp;&nbsp;
				<input type="button" class="btn_normal" onclick="back()" value="返回"/>
			</td>
		</tr>
	</table><a href="" target=""/>
</form>
</body>
</html>


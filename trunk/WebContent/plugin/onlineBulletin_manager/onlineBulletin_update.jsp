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
		    return false;
		}
		if("${onlineBulletinForm.onlineBulletin.showTool}" == "true"){
			document.getElementById("showTool").checked = true;
		}else{
			document.getElementById("showTool2").checked = true;
		}
	 	if("${onlineBulletinForm.onlineBulletin.showMenu}" == "true"){
			document.getElementById("showMenu").checked = true;
		}else{
			document.getElementById("showMenu2").checked = true;
	    }
	  	if("${onlineBulletinForm.onlineBulletin.showScroll}" == "true"){
			document.getElementById("showScroll").checked = true;
		}else{
			document.getElementById("showScroll2").checked = true;
	    }
	   	if("${onlineBulletinForm.onlineBulletin.changeSize}" == "true"){
			document.getElementById("changeSize").checked = true;
		}else{
			document.getElementById("changeSize2").checked = true;
	    }
	   	if("${onlineBulletinForm.onlineBulletin.showAddress}" == "true"){
			document.getElementById("showAddress").checked = true;
		}else{
			document.getElementById("showAddress2").checked = true;
	    }
	   	if("${onlineBulletinForm.onlineBulletin.showStatus}" == "true"){
			document.getElementById("showStatus").checked = true;
		}else{
			document.getElementById("showStatus2").checked = true;
	    }
	});
	
   	//网上公告信息添加
	function save(){
	 	$("#onlineBulletinForm").submit();
	}

	// 返回列表
	function back(){
		rightFrame.window.location.href="<c:url value='/onlineBulletin.do?dealMethod=list&" + getUrlSuffixRandom() + "'/>";
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
    <input type="hidden" name="dealMethod" id="dealMethod" value="updated" />
    <input type="hidden" name="message" id="message" value="${onlineBulletinForm.infoMessage}"/>
     <input type="hidden" name="onlineBulletin.id" id="ids" value="${onlineBulletinForm.onlineBulletin.id}"/>
    <input	type="hidden" name="id" id="id" />
	<table width="100%" border="0">
	<tr>
		<td width="10%" class="td_left">公告标题：</td>
		<td width="90%">
			<input type="text"  name="onlineBulletin.name" id="name"  class="input_text_normal" value="${onlineBulletinForm.onlineBulletin.name}"  style="width:85%" valid="string" tip="公告标题不能为空"/>
		</td>
    </tr>
    <tr>
		<td class="td_left">公告内容：</td> 
		<td>
			<FCK:editor basePath="/script/fckeditor" instanceName="onlineBulletin.context" value=" ${onlineBulletinForm.onlineBulletin.context}" toolbarSet="Ccms_font" height="160" width="85%"></FCK:editor>
		</td>
    </tr>
	<tr>
		<td class="td_left">载止日期：</td>
	    <td>
			<input type="text" name="endTime" class="Wdate" style="width:210px;" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${onlineBulletinForm.endTime}"/>
		</td>
    </tr>	
	<tr>
		<td class="td_left">窗口名称：</td>
		<td>
			<input type="text" name="onlineBulletin.windowName" style="width:210px;" value="${onlineBulletinForm.onlineBulletin.windowName}" valid="string" tip="公告标题不能为空"/>
		</td>
    </tr>
	<tr>
		<td class="td_left">显示工具栏：</td>
		<!--${onlineBulletin.showTool}-->
		<td>
			<input type="radio"  name="onlineBulletin.showTool" id="showTool" value="true"/>是
			<input type="radio" name="onlineBulletin.showTool"  id="showTool2" value="false"/>否
		</td>
    </tr>
	<tr>
		<td class="td_left">显示菜单栏：</td>
	    <!--${onlineBulletin.showMenu}-->
		<td>
			<input type="radio"   name="onlineBulletin.showMenu" id="showMenu" value="true"/>是
			<input type="radio" name="onlineBulletin.showMenu" id="showMenu2" value="false" />否
		</td>
    </tr>
	<tr>
		<td class="td_left">显示滚动条：</td>
	    <!--${onlineBulletin.showScroll}-->
		<td>
			<input type="radio"   name="onlineBulletin.showScroll" id="showScroll" value="true"/>是
			<input type="radio" name="onlineBulletin.showScroll" id="showScroll2" value="false" />否
		</td>  
    </tr>
	<tr>
		<td class="td_left">改变窗口大小：</td>
	    <!--${onlineBulletin.changeSize}-->
		<td>
			<input type="radio" name="onlineBulletin.changeSize"  id="changeSize"  value="true"/>是
			<input type="radio" name="onlineBulletin.changeSize" id="changeSize2"  value="false" />否
		</td>
    </tr>
	<tr>
		<td class="td_left">显示地址栏：</td> 
	    <!--${onlineBulletin.showAddress}-->
		<td>
			<input type="radio" name="onlineBulletin.showAddress" id="showAddress"  value="true"/>是
			<input type="radio" name="onlineBulletin.showAddress"  id="showAddress2" value="false" />否
		</td>
	</tr>
	<tr> 
		<td class="td_left">显示状态信息：</td>
	    <!--${onlineBulletin.showStatus}-->
		<td>
			<input type="radio" name="onlineBulletin.showStatus"  id="showStatus" value="true"/>是
			<input type="radio" name="onlineBulletin.showStatus" id="showStatus2" value="false" />否
		</td>
	</tr>
	<tr>
		<td class="td_left">窗口大小：</td>
		<td>
			宽度：<input type="text" name="onlineBulletin.widowWidth"  class="input_text_normal" style="width:48px;" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')" valid="num" tip="不能为空"  value="${onlineBulletinForm.onlineBulletin.widowWidth}"/>px
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			高度：<input type="text"  name="onlineBulletin.widowHeight" class="input_text_normal" style="width:48px;" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')" valid="num" tip="不能为空" value="${onlineBulletinForm.onlineBulletin.widowHeight}"/>px
		</td>
	</tr>
	<tr>
		<td class="td_left">窗口上下边界：</td>
		<td>
			窗口距离屏幕上方：<input type="text" name="onlineBulletin.windowTop" class="input_text_normal" style="width:48px;" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')" valid="num" tip="不能为空" value="${onlineBulletinForm.onlineBulletin.windowTop}"/>px
			&nbsp;&nbsp;&nbsp;
			窗口距离屏幕左侧：<input type="text" name="onlineBulletin.windowLeft" class="input_text_normal" style="width:48px;" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')" valid="num" tip="不能为空" value="${onlineBulletinForm.onlineBulletin.windowLeft}"/>px
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
	</table>
</form>
</body>
</html>


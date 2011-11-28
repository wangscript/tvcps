<%@ page language="java" contentType="text/html; charset=utf-8"  import="java.util.*" %>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RSS 设置</title>
</head>
<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<script type="text/javascript">
	$(function loadRss(){
		if("${rssForm.infoMessage}" == "发布Rss目录成功"){
			alert("${rssForm.infoMessage}");
		}
		var c=$("#columnsId").val();
		if(c=="0"){
			document.rssForm.columnRss[0].checked=true;
		}else{
			document.rssForm.columnRss[1].checked=true;
		}
		});
	function saveRss(){
		var time=$("#timeoutId").val();
		var columnIds = $("#tree_checkedIds").val();
		if(time==""){
			alert("请输入时间!");
			return false;
		}else if(isNaN(time)){
			alert("时间必须是数字");
			return false;
		}else if(columnIds==""){
			alert("请选择栏目");
			return false;
		}else{
			if(document.rssForm.columnRss[0].checked){
					$("#columnsId").val("0");
			}else{
					$("#columnsId").val("1");
			}
			var columnIds = $("#tree_checkedIds").val();
			$("#strid").val(columnIds);
			copyContent(columnIds);

			var fck = FCKeditorAPI.GetInstance("staticContent");
			$("#rssContent").val(fck.GetHTML().trim());	
			
			document.rssForm.submit();
			return true;
		}
	}
	function copyContent(columnId) {
	    window.clipboardData.setData("Text","/rssOuter.do?dealMethod=getList&columnId="+columnId); 
	  //  alert("复制成功。现在您可以粘贴（Ctrl+v）到模板中使用了。");
	}
	function tree_onclick(node) {
		var columnIds = $("#tree_checkedIds").val();
		return false;
	}

	function publishRss1() {
		$("#dealMethod").val("publishRss");
		document.rssForm.submit();
	}
</script>

<body>
<form action="<c:url value='/rssInner.do'/>" name="rssForm" id="rssForm" method="post" >
<input type="hidden" name="dealMethod" id="dealMethod" value="save"/>
<input type="hidden" name="message" id="message"/>
<input type="hidden" name="ids" id="strid"/>
<input type="hidden" name="isColumnOrMoreColumn" id="columnsId" value="${rssForm.isColumnOrMoreColumn}"/>
<input type="hidden" name="rssContent" id="rssContent" />
<fieldset >
	<legend style="color:blue">RSS设置</legend>
	<div align="center">
	<div>
		时间间隔<input type="text" name="spacingTime" id="timeoutId" value="${rssForm.spacingTime}"/>分钟
	</div><br/>
		<input type="radio" name="columnRss" />生成单栏目脚本<br/>
		<input type="radio" name="columnRss"/>生成多栏目脚本<br/>
	<div style="width:350px">
		<complat:tree unique="column" checkbox="true"  treeurl="column.do?dealMethod=getTree&nodeId=0&nodeName=null"/>
	</div><br/>
		<input type="button" name="columnsRss" onclick="saveRss();" value="保存" class="btn_normal"/>
		&nbsp;&nbsp;
		<input type="button" name="publishRss" onclick="publishRss1()" value="发布" class="btn_normal"/>
		<br/>
	<div align="left">
		生成单栏目脚本：选中的多个栏目生只成一个RSS链接<br/>
		生成多栏目脚本：选中的栏目分别生成RSS链接
	</div>
</div>
</fieldset>
<fieldset>
	<legend style="color:blue">RSS显示样式设置</legend>
	栏目名称：<input type="text" name="columnName"  class="input_text_normal" value="<!--columnName-->" readonly="readonly"/>
    <p>Rss地址：<input type="text" name="columnUrl"  class="input_text_normal" value="<!--rssUrl-->" readonly="readonly"/>
	<FCK:editor basePath="/script/fckeditor" instanceName="staticContent" value=" " toolbarSet="Ccms_openbasic" height="300"></FCK:editor>
	<p><br>
	参考样式：(提示：修改图片地址后可以使用)<p>
	<textarea rows="6" cols="110" readonly><span id="fck_dom_range_temp_1260261984375_162" /></p><table cellspacing="0" cellpadding="0" width="100%" border="0" style="line-height: 30px"><tbody><tr><td align="center" width="40"><a href="<!--rssUrl-->"><img height="14" alt="" width="36" border="0" src="" /></a></td><td align="left"><a class="bt_link" href="<!--rssUrl-->"><!--columnName--></a></td><td align="center" width="120"><a href="javascript:void(0);%20onclick=rsstry('rssfeed://<!--rssUrl-->')"><img height="19" alt="" width="99" border="0" src="" /></a></td></tr></tbody></table></textarea>
	<p><br>
	模版设置里的静态单元样式：
	<textarea rows="2" cols="110" readonly><script type="text/javascript" src="/<!--appName-->/plugin/rss/rss.jsp?siteId=<!--siteId-->&appName=<!--appName-->"></script><div id="rss"></div></textarea>
</fieldset>
<p><p><br><br>
</form>
</body>
</html>
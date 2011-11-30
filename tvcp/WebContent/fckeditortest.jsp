<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">	
<title></title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">      
	var win;
	
    function setVal(val) {
		win = val;
    }
       
	function fck_insertHtml(value){
		var cc = FCKeditorAPI.GetInstance("vc_Content"); 
		cc.InsertHtml(value);
	}   

	function closeWin() {
		win.close();
	}

	function check() {
		var cc = FCKeditorAPI.GetInstance("vc_Content"); 
		alert(cc.GetHTML());
	}

</script>
</head>

<body>

<FCK:editor basePath="/script/fckeditor" instanceName="vc_Content" value="Gov" toolbarSet="CPS_openbasic" height="300"></FCK:editor>

<input type="text" onClick="WdatePicker()" class="Wdate">

<input type="text"  class="Wdate"/>

<input type="button" onclick="check();">
</body>
</html>


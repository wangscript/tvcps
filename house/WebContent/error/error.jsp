<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#errorBody{
		width: 504px;
		height: 331px;
		overflow:hidden;
		background: url(../images/error.gif) no-repeat;
		text-align:right;
		line-height: 540px;
	}
	#errorDetail{
		width: 100%;
		display: none;
	}
</style>
<script type="text/javascript">
	function showDetail(obj){
		var detail = document.getElementById("errorDetail");
		var dstyle = detail.style.display;
		if(dstyle == "block"){
			detail.style.display = "none";
			obj.innerHTML = "查看详细";
		}else{
			detail.style.display = "block";
			obj.innerHTML = "隐藏详细";
		}
	}
</script>
</head>
<body>
<div id="errorBody">
	<a href="#" onclick="showDetail(this)">查看详细</a>
</div>
<div id="errorDetail">
	<s:property value="exceptionStack" />
</div>
</body>
</html>
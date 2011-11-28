<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>error page</title>
<%@include file="/templates/headers/header.jsp"%>
<style>
BODY {
	margin: 0;
	padding: 0;
	border: 0;
	font-size:12px;	
}
#main {margin:10% 0 0 20%; width:555px; height:300px; overflow:hidden; background:url(images/error/2_03.jpg) repeat-y; color:#666666; line-height:22px;}
#l_img {float:left; width:80px; height:80px; padding:0; margin:30px 0 0 30px; background:url(images/error/tu_03.png) no-repeat;	BEHAVIOR: url("images/error/iepngfix.htc");}
#r_con {float:right; margin:0 30px 0 10px; width:350px;}
#r_con ul {list-style:none; margin:0;}
h3{	color: #657b86;	padding-top:40px;padding-bottom:4px;border-bottom: 2px solid #b9d8ea;}
#t_area{
	width: 313px;
	height: 64px;
	line-height:24px;
	border: none;
}
UL a{
	margin-left: 1%;
	margin-right: 1%; 
	font-style: italic;
	color:#67aedc;
}
#all_p1{
	color: #1066b3;
	font-size:14px;
	margin:5px 0;
}
#all_p2{
	font-size:14px;
	margin:5px 0;
}
</style>
<script>
	function logout(){		
		top.document.location="loginaction.do?logintype=loginout";
	}
</script>
</head>

<body>

	<div id="main">
		<div id="l"><div id="l_img"></div></div>
		<div id="r_con">
			
			<h3>系统提示</h3>
			<p id="all_p1">操作失败！</p>
			   <ul> <li><textarea name="textarea" cols="50" id="t_area" readonly="readonly"><%=request.getAttribute("message")%></textarea></li>
				<li><p id="all_p2">请尝试以下操作</p></li>
				<li>.单击系统<a  onclick="logout()" style="cursor: hand">注销</a>按钮，退出后重新登录</li>
				<li>.单击浏览器<a  style="cursor: hand" onclick="window.location.reload()">刷新</a>按钮，或稍后重试</li>
				<li>.单击浏览区<a href="javascript:history.go(-1)">后退</a>按钮，或尝试其他链接</li>
		</ul>
	</div>
	</div>
</body>
</html>
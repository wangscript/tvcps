<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
BODY {
	margin: 0;
	padding: 0;
	border: 0;
	background-color: #dedfe1;
}

#main{
	padding:0;
	width: 554px;
	height: 332px;
	margin: 10% auto;
	_margin: 10% 14% 10% 28%;
	padding: 0;
	background: url("images/2_03.jpg") repeat-y;
}

#img{
	float:left;
	height:2px;
	width:55px;
	_height:43px;
	BACKGROUND: url(images/tu_03.png) no-repeat;
	BEHAVIOR: url("images/iepngfix.htc");
	padding-top:8%;
	margin-left:14%;
	margin-top:9%;
	_margin-left: 6%;
	_margin-top: 7%;
	clear: left;
}

#all{
	float:right;
	margin-top:-21%;
	margin-right:14%;
	_margin-top:-19%;
	_margin-left:1%;
	width:450px;
	_width: 450px;
}

h3{
	color: #657b86;
	_font-size:15px;
	margin-left: 29%;
	padding-top:9.5%;
	padding-bottom:4px;
	_margin:4% 0 0 30%;
	border-bottom: 3px solid #b9d8ea;
}

#all_p1{
	color: #1066b3;
	font-size: 18px;
	margin-left:29%;
	margin-top:-2%;
	_margin-left:29%;
	_margin-top:3%;
}

#textarea{
	margin-left:29%;
	margin-top:-2%;
	margin-bottom:4%;
	_margin-left:29%;
	width: 313px;
	height: 64px;
	border: none;
}

#all_p2{
	color:#30383b;
	font-size: 14px;
	_font_size:15px;
	margin-left:29%;
	_margin-left:29%;
}

ul{
	font-size: 14px;
	color: #30383b;
	margin-left:25%;
	margin-top:-3%;
	_margin-top:-4%;
	_margin-left:33%;
}

ul li{
	line-height: 1.8em;
}

UL a{
	margin-left: 2%;
	margin-right: 2%; 
	font-style: italic;
	color:#67aedc;
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
				<li>.单击浏览器<a   style="cursor: hand" onclick="window.location.reload()">刷新</a>按钮，或稍后重试</li>
				<li>.单击浏览区<a href="javascript:history.go(-1)">后退</a>按钮，或尝试其他链接</li>
			</ul>
		</div>
	</div>
</body>
</html>
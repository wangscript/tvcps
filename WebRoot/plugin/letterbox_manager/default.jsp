<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

	<style type="text/css">
		body,div,span,p,b,img,a,ul,li{
		margin:0;
		padding:0;
		border:0;}
		
		*{
		font-size:13px;}
		
		a{
		text-decoration:none;}
		
		.clear{
		clear:both;}
		
		ul li{
		list-style-type:none;}
		
		#main{
		width:790px;
		height:630px;
		margin:0 auto;}
		
		#top{
		width:788px;
		height:26px;}
		
		#login{
		float:right;
		display:block;
		width:300px;
		margin:3px 0 0 0;}
		
		.login-input{
		float:left;
		width:81px;
		height:17px;
		border:1px solid #AEC7DB;
		margin:0 10px 0 0;
		font-size:14px;}
		
		.login-bottons{
		float:right;
		margin:0 6px 0 0;}
		
		#nav{
		width:786px;
		display:block;
		border-top:1px solid #00348E;}
		
		#left-login{
		float:left;
		width:200px;
		height:161px;
		margin:3px 0 0 0;
		border:1px solid #AEC7DB;}
		
		#left-login ul li{
		width:200px;
		height:26px;
		background-color:#0486DC;
		margin:2px 0;
		padding:12px 0 0 0;
		text-align:center;}
		
		#left-login ul li a{
		font-weight:bold;
		font-size:16px;
		color:#FFFFFF;
		}
		
		#left-login ul li a:hover{
		color:#FF6699;}
		
		#right-box{
		float:right;
		width:582px;
		height:600px;
		margin:3px 0 0 0;
		border:1px solid #AEC7DB;}
		
		#right-box-info{
		width:581px;
		height:250px;
		border-bottom:1px solid #AEC7DB;}
		
		#right-box-info img{
		float:left;
		margin:5px 0 0 5px;}
		
		#right-box-info p{
		width:420px;
		height:243px;
		font-size:14px;
		text-indent:28px;
		line-height:2em;
		margin:0 0 0 160px;}
	</style>

<title></title>
	<%@include file="/templates/headers/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
	<script>
		function writeLetter(obj) {
			//alert(obj);
			window.location.href = "userLetter.do?dealMethod=showLetterDetail&ids=" + obj;
		}
	</script>
</head>
<body>
	<div id="main">
		<div id="top">
			<div id="login">
				<input type="text" value="请输入用户名" onclick="if(this.value='请输入用户名')this.value='';"  class="login-input" />
				<input type="password" class="login-input"  />
				<input type="button" value="登陆" class="btn_small" />
				&nbsp;
				<input type="button" value="注册"  class="btn_small" />
			</div>
		</div>
		<div id="nav">
			<div id="left-login">
				<ul>
					<li><a href="userLetter.do?dealMethod=showLetterDetail&ids=">给市长写信</a></li>
					<li><a href="userLetter.do?dealMethod=list">查看信件</a></li>
					<li><a href="#">退出</a></li>
					<li><a href="userLetter.do?dealMethod=showOrgList">000</a></li>
				</ul>
			</div>
			<div id="right-box">
				<div id="right-box-info">
					<img src="images/sjxx_08.jpg" />
					<p>朱善璐，男，汉族，1953年11月生，辽宁锦县人，1978年7月加入中国共产党，1968年参加工作，大学毕业（北京大学哲学专业），教授。历任黑龙江省拜泉县第一中学教师、县棉毯厂工人、车间负责人、厂校教师，曾任北京大学团委副书记、书记，北京大学党委学生工作部副部长、部长、组织部部长、副书记，北京市海淀区委副书记、1998年4月任书记，2002年任北京市委常委、教育工委书记，北京市海淀区委书记。2008年2月中共中央决定朱善璐同志任江苏省委常委、南京市委书fg</p>
				</div>
				<table width="500px;" align="center"  border="0">
					<tr height="10px;">
						<c:forEach items="${letterForm.orgList}" var="orgList" varStatus="status">
							<td onClick="writeLetter('${orgList[0]}')">${orgList[1]}</td>
						    <c:if test="${status.count%3 == 0}">   
								<tr height="10px;"><td></td></tr>
						   	</c:if>
						</c:forEach>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta   http-equiv="Expires"   CONTENT="0">     
  <meta   http-equiv="Cache-Control"   CONTENT="no-cache">     
  <meta   http-equiv="Pragma"   CONTENT="no-cache">  
<title>百泽ccms系统登录页面</title>
<script type="text/javascript" src="<c:url value='/script/jquery-1.2.6.js'/>" ></script>

<script type="text/javascript" src="<c:url value='/script/global.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/script/jquery.form.js'/>" ></script>
  <script   language="JavaScript">     
  <!--     
  javascript:window.history.forward(1);     
  //-->     
  </script>
<style>

body{
	margin:0;
	padding:0;
	background-color:#fff;
}
#main{
	width:640px;
	height:351px;
	margin:10% auto 0 auto;
	border:1px solid #fff;
	background:url(images/login/dl-bg1.gif) no-repeat 0px 0px;
}

#sub{
	width:510px;
	height:92px;
	margin:240px 0px 0 105px;
	>margin:240px 0px 0 100px;
}

#sub p{
	font-family:"宋体";
	font-size:12px;
	margin:0 0 0 33px;
	>margin:0 0 0 30px;
	line-height:15px;
}

.proc{
	font-family:Arial, Helvetica, sans-serif;
	font-size:11px;
	line-height:20px;
	color:#666666;
	float:left;
}

input.infomation { 
	width: 85px;height:16px;
	margin:10px 0 0 0px;
}

input.test { 
	width: 50px;height:16px;
	margin:10px 0 0 0px;
}

#img{
	float:right;
	width:40px;
	height:11px;
	border:1px solid #945753;
}

input.submit {
	width: 46px; height: 18px; border: none;
	background:url(images/login/niu.gif)  no-repeat;
	margin:1px 0 0 1px;>margin:1px 0 0 1px;
	font: 12px Arial, Sans-Serif; padding:1px;>padding:3px; color: #FFF; 
}

#newrand{
	width:60px;
	vertical-align:bottom;
}
</style>

<script>
function check(){
	if(document.getElementById("name").value.trim() == "") {
		alert("用户名不能为空");
		return false;
	}
	if(document.getElementById("password").value == "") {
		alert("密码不能为空");
		return false;
	}	
	var newrand = $("#rand").val();
	var options = {	 	
		    	url: "loginaction.do?logintype=checkRand",
		    success: function(msg) { 	
				    if(msg == 1){
				    	alert("验证码错误，请重新输入!"); 
				    	$("#newrand").click(change()); 
				    	return false;
				    }else{
					$("#loginbean").submit();
				    //	document.forms[0].submit();	
				    }		    		  		    		
		    } 
		  };	  		
		$("#loginbean").ajaxSubmit(options);	
}

function change(){
//	alert("sdfds");
//	$("#newrand").html(public/image.jsp) ;
	var img = document.getElementById("newrand"); 
	img.src = "public/image.jsp?t="+ new Date();			
}

function SubmitKey() {    
	
  if (event.keyCode == 13){        
        event.returnValue = false; 
        document.all['loginbutton'].click(); 
  } 
} 
$(function(){
	$("#name").focus();	
});

</script>
</head>

<body>
<form name="loginbean" id="loginbean" method="post" action="loginaction.do">
<%session.invalidate();%>
	<div id="main">
		<div id="sub">
		  <p>用户名:		<input type="text" name="name" id="name" class="infomation " /> 
			 密码:       <input type="password" name="password" id="password" class="infomation " /> 
 			验证码:     <input type="text" name="rand" id="rand" class="test "  onkeypress="SubmitKey()" />
			<img id="newrand" src="public/image.jsp" onclick="change()" alt="点击获取验证码"/><br/><br/></p>
		  <div class="proc">Copyright &copy; 2008 Baizeweb.com Inc. All rights reserved.  百泽网络  版权所有</div>			　
		  <input name="loginbutton" type="button" class="submit" value="登录"  onclick="check()"/>
		  <input name="reset" type="reset" class="submit" value="重置"/>
		</div>
	</div>
</form>
</body>
</html>

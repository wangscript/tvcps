<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Expires" CONTENT="0">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
<title>内容管理系统后台管理</title>
<script type="text/javascript" src="<c:url value='/script/jquery-1.2.6.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/script/global.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/script/jquery.form.js'/>" ></script>
<style>
body{
    background:url(images/login/bg0.gif) repeat;
    border:0 solid #fff;
    margin:0;
}
.main{
    width:706px;
    height:434px;
    margin:5% auto 2% auto;
    padding:0;
    border:0 solid #439DDB; 
    background:url(images/login/dl-bg1.jpg) no-repeat;
}

.main .sub{
    width:210px;
    height:92px;
    padding:20px 0px 0 355px;
}

.main .logo{
    width:182px;
    height:55px;
    padding:60px 0 0 355px;
}

.logo p{
    margin:0 0 0 33px;
    line-height:15px;
}

.sub p{
    font-family:"宋体";
    font-size:12px;
    margin:0 0 0 33px;
    line-height:15px;
}

.proc{
    font-family:Arial, Helvetica, sans-serif;
    font-size:11px;
    line-height:20px;
    color:#666666;
    float:right;
}

input.infomation { 
    width: 125px;height:15px;
    margin:10px 0 0 0px;
}

input.test { 
    width: 57px;height:15px;
    margin:10px 0 0 0px;
}

#img{
    float:right;
    width:40px;
    height:15px;
    border:1px solid #945753;
}

input.submit {
    width: 46px; 
    height: 18px; 
    border: none;
    /*background:url(images/login/niu.gif) no-repeat;*/
    margin:1px 0 0 1px;
    font: 12px Arial, Sans-Serif; 
    padding:1px;
    color: #FFF; 
}

#newrand{
    width:60px;
    vertical-align:bottom;
}
</style>

<script language="JavaScript" type="text/JavaScript">
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
                }
        } 
    };
    $("#loginbean").ajaxSubmit(options);
    return false;
}

function change(){
    var img = document.getElementById("newrand");
    img.src = "public/image.jsp?t="+ new Date();
}

function SubmitKey(){
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
    <div id="main" class="main">
        <div id="logo" class="logo">
            <p><img src="images/login/logo.png" width="183" height="55"/></p>
        </div>
        <div id="sub" class="sub">
            <p>用户名:<input type="text" name="name" id="name" class="infomation"/></p>
            <p>密&nbsp;&nbsp;码:<input type="password" name="password" id="password" class="infomation"/></p>
            <p>验证码:<input type="text" name="rand" id="rand" class="test "  onkeypress="SubmitKey()"/>
            <img id="newrand" src="public/image.jsp" onclick="change()" alt="点击获取验证码"/><br/><br/></p>
          <!-- <div class="proc">Copyright &copy; 2011 Inc. All rights reserved.  版权所有</div> -->
          <div class="proc">
             <input name="loginbutton" type="image" src="images/login/login.gif"  onclick="return check();"/>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <input type="image" src="images/login/reset.gif" onclick="this.form.reset();return false"/>
              <!-- 
              <input name="loginbutton" type="button" class="submit" value="登录" onclick="check()"/>
              <input name="reset" type="reset" class="submit" value="重置"/>
               -->
          </div>
        </div>
    </div>
</form>
</body>
</html>

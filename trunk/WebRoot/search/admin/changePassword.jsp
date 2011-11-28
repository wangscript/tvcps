<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<html>
	<head>
		<title>全文检索 -百泽检索系统后台管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="description" content="南京百泽,搜索引擎,全文检索、百泽搜索">
		<meta name="keywords" content="网页搜索,图片搜索,全文检索,新闻搜索,百泽搜索,搜索引擎,搜索">
		<meta name="author" content="百泽网络">
		<link href="<c:url value="/css/search.css"/>" type="text/css"	rel="stylesheet">
		<link href="<c:url value="/css/style.css"/>" type="text/css"	rel="stylesheet">	 
		<script type="text/javascript" src="<c:url value='/script/jquery-1.2.6.js'/>" ></script>
	<script>
		/***修改密码 */
		function changePassword(){	
			var main_pwd = document.getElementById("passWord").value;
			var main_pwd1 = document.getElementById("newPassWord").value;
			var main_pwd2 = document.getElementById("newpassword2").value;
			if (main_pwd == "") {
				alert("请输入旧密码");
				return false;
			}
			if (main_pwd1 == "") {
				alert("请输入新密码");
				return false;
			}
			if (main_pwd2 == "") {
				alert("请再次输入新密码");
				return false;
			}
			if (main_pwd1 != main_pwd2) {
				alert("您两次输入的密码不一致!");
				return false;
			}
			document.searchForm.submit();//提交			
			
		}
		function setFocus(){
			var m = document.getElementById("infoMessage").value;		 	
			if(m != null && m == "0"){				
				top.closeNewChild();	
			}else if(m != null && m != ""){
				alert(m);
			}
		    document.searchForm.passWord.focus();
		}
		//加载时调用事件
		window.attachEvent("onload",setFocus);
	</script>	 
	</head>

	<body onload="document.searchForm.passWord.focus();"> 
	    <form name="searchForm" method="post" action="<c:url value="/searchAdminAction.do"/>" id="searchForm"> 
		<input type="hidden" name="dealMethod" id="dealMethod" value="changePassWord"> 
		<input type="hidden" name="infoMessage" id="infoMessage" value="${searchForm.infoMessage}"/>
		<br>
		<table width="90%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#cccccc"> 
	
			    <tr align="center" bgcolor="#efefef"  class="input_text_normal"> 
			      <td colspan="2">修改密码</td> 
			    </tr> 
			    <tr bgcolor="#FFFFFF"  class="input_text_normal"> 
			      <td width="100" align="center">帐号名</td> 
			      <td  align="left">admin</td> 
			    </tr> 
			    <tr bgcolor="#FFFFFF"> 
			      <td align="center">旧密码</td> 
			      <td align="left"><input name="passWord"  class="input_text_normal" type="password"  id="passWord" style="width:130px"></td> 
			    </tr> 
			    <tr bgcolor="#FFFFFF"> 
			      <td align="center">新密码</td> 
			      <td align="left"><input name="newPassWord" type="password"  class="input_text_normal" id="newPassWord" style="width:130px"></td> 
			    </tr> 
			    <tr bgcolor="#FFFFFF"> 
			      <td align="center">新密码确认</td> 
			      <td align="left"><input name="newpassword2" type="password"  class="input_text_normal" id="newpassword2" style="width:130px"></td> 
			    </tr> 
			    <tr align="center" bgcolor="#FFFFFF"> 
			      <td colspan="2">
				  	<input name="changeButton" type="button" class="btn_normal" id="changeButton" value="修改" onclick="changePassword()" > &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset" name="reset" class="btn_normal"   id="reset" value="重置">
			      </td> 
			    </tr> 
	
		</table>
	    </form>
	</body>
</html>
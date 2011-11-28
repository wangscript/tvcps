<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/public/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>样式例子</title>

</head>

<body>
<form id="formId"   action="demo/demo_paging.action" method="post">
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>><a href="<c:url value='/user/findUserList.action'/>"><span>用户管理</span></a></div>
	<div class="findText">
		<li><span>用户名：</span><input type="text" name="textName" id="textName"></input></li>
	 	<li><span>用户名：</span><input type="text" name="textName" id="textName1"></input></li> 
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button" name="find" id="find" class="buttonStyle" value="查询" /></span>
		<span><input type="button" name="add" id="add" class="buttonStyle" value="增加" /></span>
		<span><input type="button" name="delete" id="delete" class="buttonStyle"  value="删除" /></span>
		<span><input type="button" name="modify" id="modify" class="buttonStyle"  value="修改" /></span>
	</div>
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"><input type="checkbox" name="allChecked" id="checkbox" onclick="checkedAll()"/></th>
	    <th>标题11111111111</th>
	    <th>标题22222222222222</th>
	    <th>标题3333333333333</th>
	  </tr>
	  <tr>
	    <td  class="firstTd"><input type="checkbox" name="checkbox" id="checkbox" /></td>
	    <td>&nbsp;11111111</td>
	    <td>&nbsp;2222222222</td>
	    <td>&nbsp;22222222222</td>
	  </tr>
	  <tr>
	    <td class="firstTd"><input type="checkbox" name="checkbox" id="checkbox" /></td>
	    <td>&nbsp;11111111</td>
	    <td>&nbsp;2222222222</td>
	    <td>&nbsp;22222222222</td>
	  </tr>
	</table>
	
	<joyque:page  pageBean="${pageBean}" formId="formId"   pageAction="demo/demo_paging.action"/>
</form>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/message.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache"/> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/> 
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT"/>
<base target="_self"/>
<title>选择雇主</title>
<script type="text/javascript" language="JavaScript">
    function queryEmployer(){
        var employerForm = document.getElementById("employerForm");
        employerForm.submit(); 
    }
    function chooseEmployer(){
		 var strChecked = new Array();
		 $("input[id='employerId']").each(function(i){
             if(this.checked)
            	 strChecked.push($(this).val());
		});
		 if(strChecked.length == 0){
			 alert("请选择一条记录！");
		  	 return false;
		 }else if(strChecked.length > 1){
			 alert("只能选择一条记录！");
		  	 return false;
		 }
		 var employerId = strChecked.valueOf();
		 var arr = new Array();
		 arr[0] = employerId;
		 arr[1] = $("#"+employerId).html();
		 window.returnValue = arr;
		 top.close();
	}
</script>
</head>
<body>
<form id="employerForm" action="<c:url value="/employer/chooseEmployer.shtml"/>" method="post">
	<input type="hidden" name="strChecked" id="strChecked" />
	<div class="currentLocationStyle"><img src="<c:url value='/images/common/currentLocation.png'/>"/>当前位置>>
		<span>雇主列表</span>
	</div>
	<div class="findText">
		<li><span>联系人：</span><input type="text" id="linkMan" name="employer.linkMan" value="${employer.linkMan}"/></li>
	</div>	
	<div class="buttonGeneralStyle">
		<span><input type="button"  class="buttonStyle" value="查询" onclick="queryEmployer()" /></span>
		<span><input type="button"  class="buttonStyle" value="选择" onclick="chooseEmployer()" /></span>
	</div> 
	
	<table class="tableStyle">
	  <tr  class="firstTr">
	  	<th class="firstTd"></th>
	    <th>用户名</th>
	    <th>密码</th>
	    <th>联系电话</th>
	    <th>联系人</th>
	  </tr>
	  <c:forEach items="${pagination.data}" var="employerEntity">
	  <tr>
	    <td class="firstTd"><input type="checkbox" id="employerId" value="${employerEntity.employerId}" /></td>
	    <td id="${employerEntity.employerId}">${employerEntity.loginName}</td>
	    <td>${employerEntity.passWord}</td>
	    <td>${employerEntity.tel}</td>
	    <td>${employerEntity.linkMan}</td>
	  </tr>
	  </c:forEach>
	</table>
	<house:page  pagination="${pagination}" formId="employerForm" pageAction="/employer/chooseEmployer.shtml"/>
</form>
</body>
</html>

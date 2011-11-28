<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
<%@include file="/templates/headers/header.jsp"%>

<script type="text/javascript">
	var childOrgWin;	
	var x = 0;
	$(document).ready(function() {
		$("#loginName").focus(); 
		if($("#message").val() != null && $("#message").val() != "") {
			alert($("#message").val());
			if($("#message").val() != "") {
				rightFrame.window.location.href = "<c:url value='/user.do?dealMethod=personalInfoDetail&"+getUrlSuffixRandom()+"'/>";
			}  
		}
	}); 

	function button_save_onclick(ee){
		if($("#userid").val() != null && $("#userid").val() != "" && $("#userid").val() != 0) {	
			document.all("organization.id").value=document.getElementById("pid").value;
			$("#dealMethod").val("modifyPersonalInfo");
			$("#userForm").submit();									
		} else {
			return false;
		} 
	}
	
	function checkOrg(){
		win = showWindow("choseorganization","选择上级机构","<c:url value='/module/user_manager/personalInfo_set/choose_person_organization.jsp'/>",0,0,300,270);	 			
	}

	
	function closeChild() {		
		top.closeWindow(win);
	}

</script>
</head>
<body>
<div class="currLocation">个人信息管理 </div>
 <form action="<c:url value="/user.do"/>" method="post" name="userForm" id="userForm">
		<input type="hidden" name="dealMethod" id="dealMethod" />
		<input type="hidden" name="ids" id="strids" />
		<input type="hidden" name="loginName" id="loginName"/>
	    <input type="hidden" name="organizationid" id="organizationid" value="${userForm.user.organization.id}" />
		<input type="hidden" name="id" id="id"/>
	 	<input type="hidden" name="user.id" id="userid" value="${userForm.user.id}"/>
		<input type="hidden" name="message" id="message" value="${userForm.infoMessage}"/>
		<input type="hidden" name="nodeId" id="nodeId" value="${userForm.nodeId}"/>	
		<div id="container" class="form_div">	
		<table  width="85%" cellspacing="10" cellpadding="20">	
		<tr>			
			<td  class="td_left" width="10%">登录名：</td>
			<td>
				<input name="user.loginName"  class="input_text_normal"  type="text" id="loginName" readonly="readonly" value="${userForm.user.loginName}" />
			</td>	
			<td class="td_left" width="10%"><i>*</i>真实姓名：</td>
			<c:if test="${userForm.user.name != '网站管理员' and userForm.user.name != '超级管理员'}">
			<td>
				<input type="text" class="input_text_normal" name="user.name" id="name" value="${userForm.user.name}" valid="notempty" tip="真实姓名不能为空"/>
			</td>
			</c:if>	
			<c:if test="${userForm.user.name eq '网站管理员' or userForm.user.name eq '超级管理员'}">
			<td>
				<input type="text" class="input_text_normal" name="user.name"  readonly="readonly" id="name" value="${userForm.user.name}" valid="notempty" tip="真实姓名不能为空"/>
			</td>
			</c:if>	
		</tr>
		<tr>	
			<td class="td_left"><i>*</i>登录密码：</td>
			<td>
				<input type="password" class="input_text_normal" name="user.password" id="password" value="${userForm.user.password}" valid="string" tip="登录密码不能为空"/>
			</td>
			
		<c:if test="${userForm.user.name != '超级管理员'}">
			<td class="td_left">E-mail：</td>
			<td>
				<input type="text" class="input_text_normal"  name="user.email"  id="email"  value="${userForm.user.email}"  empty="true" valid="email" tip="请输入合法的email地址" />
			</td>
		</tr>
		<tr>
			<c:if test="${userForm.user.name != '网站管理员'}">
			<td class="td_left"><i>*</i>所属机构：</td>		
			<td>
				<input type="text" name="pname" class="input_text_normal"  id="pname" readonly="readonly"  value="${userForm.user.organization.name}" empty="false" valid="string" tip="必须选择机构"/>
				<input type="button" name="chooseOrg" class="btn_small" id="chooseOrg" value="选择" onclick="checkOrg()"/>
				<input type="hidden" name="organization.id" id="pid" value="${userForm.user.organization.id}" />
			</td>
			</c:if>	
			<c:if test="${userForm.user.name == '网站管理员'}">
			<td class="td_left">所属机构：</td>		
			<td>
				<input type="text" name="pname" class="input_text_normal"  id="pname" readonly="readonly"  value="${userForm.user.organization.name}"/>
				<input type="button" name="chooseOrg" class="btn_small" id="chooseOrg" value="选择" onclick="checkOrg()"/>
				<input type="hidden" name="organization.id" id="pid" value="${userForm.user.organization.id}" />
			</td>
			</c:if>	
			<td class="td_left">职务：</td>
			<td>
				<input name="user.position" type="text" class="input_text_normal"  id="position"  value="${userForm.user.position}" empty="true" tip="所有字符（包括空格），可以为空" />
			</td>
		</tr>
		<tr>
			<td class="td_left">个人主页：</td>
			<td>
				<input type="text" class="input_text_normal"  name="user.personHomePage"  id="personHomePage" value="${userForm.user.personHomePage}"  />
			</td>
		
			<td class="td_left">移动电话：</td>
			<td>
				<input name="user.mobileTel" class="input_text_normal"  type="text" id="mobileTel"  value="${userForm.user.mobileTel}" valid="num" tip="请输入合法电话,格式为88888888888" maxlength="11" empty="true" />
			</td>
		</tr>
    	<tr>
			<td class="td_left">办公电话：</td>
			<td>
				<input name="user.officeTel" class="input_text_normal"  type="text" id="officeTel"  value="${userForm.user.officeTel}"  valid="num" tip="请输入合法电话02588888888" maxlength="12" empty="true" />
			</td>		
			<td class="td_left">家庭电话：</td>
			<td>
				<input name="user.homeTel" class="input_text_normal"  type="text" id="homeTel"  value="${userForm.user.homeTel}" valid="num" tip="请输入合法电话02588888888" maxlength="12" empty="true" />
			</td>
		</tr>
		</c:if>


		<c:if test="${userForm.user.name == '超级管理员'}">
			<td class="td_left">E-mail：</td>
			<td>
				<input type="text" class="input_text_normal"  name="user.email" readonly id="email"  value="${userForm.user.email}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">所属机构：</td>		
			<td>
				<input type="text" name="pname" class="input_text_normal"  id="pname" readonly="readonly"  value="${userForm.user.organization.name}"  />
				<input type="hidden" name="organization.id" id="pid" value="${userForm.user.organization.id}" />
			</td>	
			<td class="td_left">职务：</td>
			<td>
				<input name="user.position" type="text" class="input_text_normal" readonly id="position"  value="${userForm.user.position}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">个人主页：</td>
			<td>
				<input type="text" class="input_text_normal"  name="user.personHomePage" readonly id="personHomePage" value="${userForm.user.personHomePage}"  />
			</td>
		
			<td class="td_left">移动电话：</td>
			<td>
				<input name="user.mobileTel" class="input_text_normal"  type="text" id="mobileTel" readonly value="${userForm.user.mobileTel}"/>
			</td>
		</tr>
    	<tr>
			<td class="td_left">办公电话：</td>
			<td>
				<input name="user.officeTel" class="input_text_normal"  type="text" id="officeTel"  readonly value="${userForm.user.officeTel}"/>
			</td>		
			<td class="td_left">家庭电话：</td>
			<td>
				<input name="user.homeTel" class="input_text_normal"  type="text" id="homeTel" readonly value="${userForm.user.homeTel}"/>
			</td>
		</tr>
		</c:if>
		<tr><td colspan="4"></td></tr>
		<tr><td colspan="4"></td></tr>
		<tr><td colspan="4"></td></tr>
		<tr><td colspan="4"></td></tr>
		<tr>
			<td colspan="4" align="center" >
				<input  type="button"  class="btn_normal"  name="button_save" value="保存" onClick="javascript:button_save_onclick(this);" />&nbsp;&nbsp;&nbsp;&nbsp;		
				<input  type="reset"  class="btn_normal"  name="button_reset" value="重置" />	
			</td>
		</tr>
	  </table>
   </div>


 </form>
</body>
</html>
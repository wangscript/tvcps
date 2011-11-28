<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
<%@include file="/templates/headers/header.jsp"%>

<script type="text/javascript"><!--
	var childOrgWin;	
	var x = 0;
	var check = 0;
	$(document).ready(function() {	
		/*解决页面失去焦点*/	
		var obj = $('input[name=user.loginName]');
		for(i=0;i<obj.length;i++){
			obj[i].focus();
		}
		if("${userForm.dealMethod eq 'detail'}" == "true"){
			$("#password").focus();
		}
		if(document.getElementById("message").value!=null &&  document.getElementById("message").value!=""){			
			top.document.getElementById("rightFrame").src="<c:url value='/user.do?dealMethod=&nodeId="+$("#nodeId").val()+"&"+getUrlSuffixRandom()+"'/>";
			var winid = rightFrame.getWin();
			closeWindow(winid);		
		}
}); 

	function button_save_onclick(ee){
		var temp = "${userForm.dealMethod}";		
		if(document.all("user.loginName").value == "" &&  temp != "detail") {
			alert("请输入登录名");
			return false;
			
		}
		var obj = document.all("user.loginName").value;
		if(temp != "detail"){
		//	checkLoginName(document.all("user.loginName").value);
			document.all("dealMethod").value="checkLoginName";
			$("#loginName").val(document.all("user.loginName").value);
			
			var options = {	 	
	 		    	url: "<c:url value='/user.do'/>",
	 		    	success: function(msg) {	 	 		    	
	 	 		    	if(msg == 0 && obj != ""){
	 	 	 		    	alert("对不起，该登录名已经被注册!");
	 	 	 		    	check = 1;
	 	 	 		    	return false;
	 	 		    	}else{ 	 	 		    	
	 	 	 		    	check = 0;
	 	 	 		    	var name = document.getElementById('name').value;
	 	 	 			//	preventSave();
	 	 	 				if(	document.getElementById("userid").value != null && 	document.getElementById("userid").value != "" && document.getElementById("userid").value != 0){	
	 	 	 					if(x == 0){
	 	 	 						document.all("organization.id").value=document.getElementById("organizationid").value;	
	 	 	 					}	
	 	 	 					document.all("dealMethod").value="modify";									
	 	 	 				}else{	
	 	 	 					if(document.all("organization.id").value == "") {
	 	 	 						alert("请选择机构");
	 	 	 						return false;
	 	 	 					}	
	 	 	 					document.all("dealMethod").value="insert";	
	 	 	 				}
	 	 	 				$("#userForm").submit();	
	 	 		    	}
	 		    	}
			 };		 
			 if(obj != ""){
				$("#userForm").ajaxSubmit(options);
			 }			
		}else{
			if(	document.getElementById("userid").value != null && 	document.getElementById("userid").value != "" && document.getElementById("userid").value != 0){	
					if(x == 0){
						document.all("organization.id").value=document.getElementById("organizationid").value;	
					}	
					document.all("dealMethod").value="modify";									
			}
				$("#userForm").submit();	
		}
	
	//	var name = document.getElementById('name').value;
	//	preventSave();
		
	/**	if(check == 0){		
			if(	document.getElementById("userid").value != null && 	document.getElementById("userid").value != "" && document.getElementById("userid").value != 0){	
				if(x == 0){
					document.all("organization.id").value=document.getElementById("organizationid").value;	
				}	
				document.all("dealMethod").value="modify";									
			}else{	
				if(document.all("organization.id").value == "") {
					alert("请选择机构");
					return false;
				}	
				document.all("dealMethod").value="insert";	
			}
			$("#userForm").submit();	
				
		}else{
			alert("该登录名已被注册!");
			return false;
		}

		function preventSave(event){
			var e = window.event||event;
			if(name.trim() == ''){
				if(window.event){
					e.returnValue = false;
				}else if(e.preventDefault){
					e.preventDefault();
				}
			}
		}
		*/
	}
	
	function checkOrg(){
		x = 1;
		win = showWindow("choseorganization","选择上级机构","<c:url value='/module/user_manager/user/choose_organization.jsp'/>",0,0,300,220);	 			
	}
	
	function closeChild() {		
		top.closeWindow(win);
	}


	function checkLoginName(obj){	

		document.all("dealMethod").value="checkLoginName";
		$("#loginName").val(obj);
		 
		var options = {	 	
 		    	url: "<c:url value='/user.do'/>",
 		    	success: function(msg) { 	 		    	
 	 		    	if(msg == 0 && obj != ""){
 	 	 		    	alert("对不起，该登录名已经被注册!");
 	 	 		    	setTimeout($.unblockUI, 1000); 
 	 	 		    	check = 1;
 	 	 		    	return false;
 	 		    	}else{ 	 	 		    	
 	 	 		    	check = 0;
 	 		    	}
 		    	}
		 };		 
		 if(obj != ""){
			$("#userForm").ajaxSubmit(options);
		 }			
	}
--></script>
</head>
<body> 


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
	<table  width="100%">	
	<tr>	
		<c:if test="${userForm.dealMethod eq 'detail'}">		
			<td  class="td_left"  width="10%">登录名：</td>
			<td>
				<input name="user.loginName"  class="input_text_normal"  type="text" id="loginName" readonly="readonly" value="${userForm.user.loginName}" />
			</td>	
		</c:if>
		<c:if test="${userForm.dealMethod eq 'showDetail'}">		
			<td class="td_left"  width="10%"><i>*</i>登录名：</td>
			<td>
				<input name="user.loginName" class="input_text_normal"  type="text" id="loginName"  value="${userForm.user.loginName}" empty="false"  valid="string" tip="所有字符（包括空格）" />
			</td>		
		</c:if>
			<td class="td_left"   width="10%"><i>*</i>真实姓名：</td>
			<td>
				<c:if test="${userForm.user.name eq '网站管理员' or userForm.user.name eq '超级管理员'}">
					<input  type="text" class="input_text_normal"  name="user.name" id="name" readonly="readonly" value="${userForm.user.name}"  tip="真实姓名不能为空"/>
				</c:if>
				<c:if test="${userForm.user.name != '网站管理员' and userForm.user.name != '超级管理员'}">
					<input  type="text" class="input_text_normal"  name="user.name" id="name" empty="false" valid="string"  value="${userForm.user.name}" tip="真实姓名不能为空"/>
				</c:if>
			</td>	
	</tr>
	<tr>	
			<td class="td_left"><i>*</i>登录密码：</td>
			<td>
				<input type="password" class="input_text_normal"   name="user.password" id="password" value="${userForm.user.password}"  valid="string" tip="登录密码不能为空"/>
			</td>
					<c:if test="${userForm.user.name != '超级管理员'}">	
							<td class="td_left">E-mail：</td>
							<td>
								<input type="text" class="input_text_normal"  name="user.email"  id="email"  value="${userForm.user.email}" empty="true" valid="email" tip="请输入合法的email地址" />
							</td>		
					</tr>
					<tr>
							<td class="td_left"><i>*</i>所属机构：</td>		
							<td>	
							<c:if test="${userForm.dealMethod eq 'showDetail'}">
							    <input type="text" name="pname" class="input_text_normal"  id="pname" readonly="readonly"  value="${userForm.organization.name}"/>
								<input type="button" name="chooseOrg" class="btn_small" id="chooseOrg" value="选择" onclick="checkOrg()"/>					
								<input type="hidden" name="organization.id" id="pid"  value="${userForm.organization.id}"/>
							</c:if>
							<c:if test="${userForm.dealMethod eq 'detail'}">
								<input type="text" name="pname" class="input_text_normal" id="pname" readonly="readonly" value="${userForm.user.organization.name}"/>
								<input type="button" name="chooseOrg" class="btn_small" id="chooseOrg" value="选择" onclick="checkOrg()"/>					
								<input type="hidden" name="organization.id" id="pid" value="${userForm.user.organization.id}" />
							</c:if>
							</td>	
							<td class="td_left">职务：</td>
							<td>
								<input name="user.position" type="text" class="input_text_normal"  id="position"  value="${userForm.user.position}" tip="所有字符（包括空格），可以为空" />
							</td>
					</tr>
					<tr>
							<td class="td_left">个人主页：</td>
							<td>
								<input type="text" class="input_text_normal"  name="user.personHomePage"  id="personHomePage" value="${userForm.user.personHomePage}"  />
							</td>
						
							<td class="td_left">移动电话：</td>
							<td>
								<input name="user.mobileTel" class="input_text_normal"  type="text" id="mobileTel" value="${userForm.user.mobileTel}" valid="num" tip="请输入合法电话,格式为88888888888" maxlength="11" empty="true"/>
							</td>
					</tr>
					<tr>
							<td class="td_left">办公电话：</td>
							<td>
								<input name="user.officeTel" class="input_text_normal"  type="text" id="officeTel" value="${userForm.user.officeTel}"  valid="num" tip="请输入合法电话02588888888" maxlength="12" empty="true"/>
							</td>		
							<td class="td_left">家庭电话：</td>
							<td>
								<input name="user.homeTel" class="input_text_normal"  type="text" id="homeTel" value="${userForm.user.homeTel}" valid="num" tip="请输入合法电话02588888888" maxlength="12" empty="true" />
							</td>
					</tr>
				</c:if>	


				<c:if test="${userForm.user.name == '超级管理员'}">
					<td class="td_left"><i>*</i>E-mail：</td>
					<td>
						<input type="text" class="input_text_normal"  name="user.email"  id="email"  readonly="readonly" value="${userForm.user.email}" />
					</td>		
				</tr>

				<tr>
					<td class="td_left"><i>*</i>所属机构：</td>	
					<td>
						<input type="text" name="pname" class="input_text_normal"  id="pname" readonly="readonly"  value="${userForm.user.organization.name}"  />
						<input type="button" style="display:none;" name="chooseOrg" class="btn_small" id="chooseOrg" value="选择" onclick="checkOrg()"/>			
						<input type="hidden" name="organization.id" id="pid" value="${userForm.user.organization.id}" />
					</td>	
					<td class="td_left">职务：</td>
					<td>
						<input name="user.position" type="text" class="input_text_normal"  id="position" readonly="readonly"  value="${userForm.user.position}"/>
					</td>
				</tr>
				<tr>
					<td class="td_left">个人主页：</td>
					<td>
						<input type="text" class="input_text_normal"  name="personHomePage" readonly="readonly"  id="user.personHomePage" value="${userForm.user.personHomePage}"  />
					</td>
				
					<td class="td_left">移动电话：</td>
					<td>
						<input name="user.mobileTel" class="input_text_normal"  type="text" id="mobileTel" readonly="readonly" value="${userForm.user.mobileTel}"/>
					</td>
				</tr>
				<tr>
					<td class="td_left">办公电话：</td>
					<td>
						<input name="user.officeTel" class="input_text_normal"  type="text" id="officeTel" readonly="readonly" value="${userForm.user.officeTel}"/>
					</td>		
					<td class="td_left">家庭电话：</td>
					<td>
						<input name="user.homeTel" class="input_text_normal"  type="text" id="homeTel" readonly="readonly" value="${userForm.user.homeTel}" />
					</td>
				</tr>
			</c:if>	
	<tr>			
	</tr>
	<tr>			
	</tr>
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
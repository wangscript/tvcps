<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增角色</title>

<script type="text/javascript">
	var childOrgWin;	
	var check = 0;
	$(document).ready(function() {
		$("#role").focus();
		if(document.getElementById("message").value != null &&  document.getElementById("message").value != ""){
			top.document.getElementById("rightFrame").src="<c:url value='/role.do?dealMethod=&treeId=${roleForm.siteId}'/>";
			closeWindow(rightFrame.getWin());							
		}
	}); 
	function closeChild() {
		childOrgWin.close();
	}
	
	function checkRoleName(obj){	
		document.all("dealMethod").value="checkRoleName";
		var des = document.getElementById("role.description").value;		
		var olddes = document.getElementById("oldroledescription").value;
		$("#roleName").val(obj);
		//角色名改过
		if((obj != $("#oldRoleName").val()) ){	
			var options = {	 	
	 		    	url: "<c:url value='/role.do'/>",
	 		    	success: function(msg) {	
	 		    	    	
	 	 		    	if(msg == 0){
	 	 	 		    	alert("对不起，该网站已有该角色！");
	 	 	 		    	check = 1;
	 	 	 		    	return false;
	 	 		    	}else{		 	 		    
	 	 		    		if(	document.getElementById("id").value != null && 	document.getElementById("id").value != "" && document.getElementById("id").value != 0){			
	 	 						document.all("dealMethod").value="modify";				
	 	 					}else{		
	 	 						document.all("dealMethod").value="insert";		       
	 	 					}
	 	 		    	
 	 						document.forms[0].submit(); 
	 	 		    	}
	 		    	}
			 };
			$("#roleForm").ajaxSubmit(options);	
		//角色名没改	
		}else {		
    		if(	document.getElementById("id").value != null && 	document.getElementById("id").value != "" && document.getElementById("id").value != 0){			
				document.all("dealMethod").value="modify";				
			}else{		
				document.all("dealMethod").value="insert";		       
			}
			document.forms[0].submit(); 
		}
	}

	function button_save_onclick(ee){
		var rolename = document.getElementById("role.name").value;	
		if(rolename == null || rolename == "") {
			alert("请输入角色名称");
			return false;
		}	
		if(rolename == "系统管理员"){
			alert("用户自定义角色名不能为系统管理员");
			return false;
		}
		if(rolename == "网站管理员"){
			alert("用户自定义角色名不能为网站管理员");
			return false;
		}
		checkRoleName(rolename);
	}

</script>
</head>
<body>
 <form action="<c:url value="/role.do"/>" method="post" name="roleForm">
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="ids" id="strids" />
	<input type="hidden" name="role.id" id="id" value="${roleForm.role.id}"/>
	<input type="hidden" name="message" id="message" value="${roleForm.infoMessage}"/>
	<input type="hidden" name="siteId" id="siteId" value="${roleForm.tempSiteId}"/>
	<input type="hidden" name="roleName" id="roleName" />
 	<input type="hidden" name="oldRoleName" id="oldRoleName" value="${roleForm.role.name}"/>
	<input type="hidden" name="oldroledescription" id="oldroledescription" value="${roleForm.role.description}" />
	<div id="container" class="form_div">
		<table width="100%">
			<tr>
			 	<td class="td_left" width="20%"><i>*</i>角色名称：</td>
				<td>
					<input type="text"  class="input_text_normal" name="role.name" id="role" style="width:240px;"  valid="String" tip="角色名称不能为空" value="${roleForm.role.name}" />
				</td>				
			</tr>
			<tr>
				<td class="td_left">所属网站：</td>	
				<td>		
					<select class="input_select_normal" name="siteName" style="width:240px;" tip="请至少选择一项" >
						<c:forEach var="site" items="${roleForm.list}">		
							<c:if test="${roleForm.siteId eq site.id}">						
				    			<option selected  id="${site.id}">${site.name}</option>
							</c:if>
							<c:if test="${roleForm.siteId != site.id}">				
								<option   id="${site.id}">${site.name}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="td_left">角色描述：</td>
				<td height="160">	
					<textarea    class="input_textarea_normal"  id="role.description" name="role.description">${roleForm.role.description}</textarea>
				</td>				
			</tr>
		<tr>
		</tr>
		<tr>
		</tr>
			<tr>
				<td colspan="2" align="center">
					<input  type="button"  name="button_save"   class="btn_normal"  value="保存" onClick="javascript:button_save_onclick(this);" >&nbsp;&nbsp;&nbsp;&nbsp;		
					<input  type="reset"  name="button_reset"   class="btn_normal"  value="重置"  >
				</td>
			</tr>
		</table>
	</div>
	
 </form>
</body>
</html>
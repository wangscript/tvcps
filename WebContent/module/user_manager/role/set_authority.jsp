<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单权限设置</title>

<script language="javascript">
	/** 退出 */
	function button_quit_onclick(ee){	
		rightFrame.closeNewChild();
	}
	/** 保存 */
	function button_save_onclick(ee){
		document.getElementById("strmenuid").value = $("#checkedTreeIds").val(); 	 
		document.all("dealMethod").value="setSave";		 
		document.roleForm.submit();	
		rightFrame.closeNewChild();	    
	}


</script> 
</head>
<body>
  	<form id="roleForm" action="<c:url value="/role.do"/>" method="post" name="roleForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="roleid" id="strroleid" value="${roleForm.roleid}"/>
		<input type="hidden" name="strmenuid" id="strmenuid">
		<center>	 
		<table border="0" style="height:100%;width:80%;">
			<tr>
				<td>
					<fieldset style="height:100%" id="obj1" style="display:">
						<legend style="color:#0000ff;">角色权限设置</legend>
						<complat:treecheckbox allData="${roleForm.allData}" colNum="2" checkData="${roleForm.chooseData}" ></complat:treecheckbox>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input  type="button"  value="保存" class="btn_normal"  name="button_save" onClick="javascript:button_save_onclick(this);" >&nbsp;&nbsp;&nbsp;&nbsp;			
				    <input  type="button"  value="退出" class="btn_normal"  name="button_quit" onClick="javascript:button_quit_onclick(this);" >
				</td>
			</tr>		
		</table>
		</center>
	 </form>
</body>
</html>
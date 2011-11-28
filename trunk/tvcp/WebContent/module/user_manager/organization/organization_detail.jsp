<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增机构</title>

<script type="text/javascript">	

	$(document).ready(function() {
		$("#orgname").focus();
	});
	var x = 0;
	function button_save_onclick(ee){	
		var orgName = document.getElementById("orgname").value;
		document.getElementById("orgname").focus();
		if(orgName == null || orgName == "") {
			alert("请输入机构名称");
			return false;	
		}
		
		if($("#dealMethod").val() == "detail"){			
			document.all("dealMethod").value="modify";
		}else{
			document.all("dealMethod").value="insert";
		}
		var name = $("#orgname").val();				
		var names = $("#orgNames").val();
		var reg = /(\r?\n)*/g;
		names = names.replace(reg,"");		
		var ids = names.split(",");	
		var test = 0;
		for(var i = 0; i < ids.length; i++) {
			if((ids[i] == name) && (name != null) && (name != "")){
				if($("#temporgName").val() != name){									
					alert("同一级机构下不能有相同的机构!");
					test = 1;	
				}
			} 
		}
		var options = {	 	
 		    		url: "<c:url value='/organization.do'/>",
 		    	success: function(msg) { 		    		
		    			var detailWin = rightFrame.getWin();		    
 	 	 		    	if(msg == 0){ 	 	 	 		    	
 	 	 		    		alert("同一级机构下不能有相同的机构!");
 	 	 		    		return false;
 	 	 		    	}else{ 	 	 	 	 		    	
 	 	 		    	
 	 	 		    		top.reloadAccordion("/${appName}/module/user_manager/organization/index.jsp?t="+new Date());
 	 	 		    		//top.document.getElementById("rightFrame").src="<c:url value='/organization.do?dealMethod='/>"; 
 	 	 		    		
 	 	 		    		top.dhxAccord.cells("userManager").attachURL("/"+"${appName}/module/user_manager/user/index.jsp?t="+ new Date());	 	 		    		 	 	 		    			  			 	
 	 	 		    	//	top.document.getElementById("rightFrame").src="organization.do?dealMethod=";
 	 	 		    		top.document.getElementById("rightFrame").src="<c:url value='/organization.do?dealMethod=&t="+ new Date()+"'/>";
 	 	 		   	}
 	 	 		 	closeWindow(detailWin);			 		
 		    	}	    
 		  };	
		if(test != 1){	
			$("#organizationForm").ajaxSubmit(options);		 				
		}	 	
	}

	function checkOrg(){
		x = 1;
		win = showWindow("choseorganization","选择上级机构","<c:url value='/module/user_manager/organization/choose_organization.jsp'/>",0,0,300,220);	 			
	}

	function closeChild() {		
		top.closeWindow(win);
	}

	function test(){		
		rightFrame.closeNewChild();		
	}

</script>
</head>
<body>

 <form id="organizationForm"  action="<c:url value="/organization.do"/>" method="post" name="organizationForm">
	 <input type="hidden" name="dealMethod" id="dealMethod" value="${organizationForm.dealMethod}" />
	 <input type="hidden" name="message" id="message" value="${organizationForm.infoMessage}" />
	 <input type="hidden" name="ids" id="strids" value="22"  />
	 <input type="hidden" name="id" id="id"  value="${organizationForm.organization.id}"/>
	 <input type="hidden"  name="parent.id" id="parent.id" value="${organizationForm.organization.parent.id}"/>
	 <input type="hidden" name="orgIds" id="orgIds" value="${organizationForm.orgIds}"/>
	 <input type="hidden" name="orgNames" id="orgNames"  value="${organizationForm.orgNames}"/>
	 <div id="container"  class="form_div">	
	 <table width="100%">		
		<tr>
    		<td class="td_left" width="20%"><i>*</i>机构名称：</td>	
			<td>
				<c:if test="${organizationForm.dealMethod eq 'detail'}">
					<input type="text"  class="input_text_normal" name="organization.name" valid="string" tip="机构名称不能为空" id="orgname"  style="width:250px;" value="${organizationForm.organization.name}"/>
						<input type="hidden" name="temporgName" id="temporgName" value="${organizationForm.organization.name}"/>
				</c:if>
				<c:if test="${organizationForm.dealMethod eq 'showDetail'}">
					<input type="text"  class="input_text_normal" name="organization.name" valid="string" tip="机构名称不能为空"  id="orgname"  style="width:250px;" />
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="td_left">上级机构：</td>
			<td>
				<c:if test="${organizationForm.dealMethod eq 'showDetail'}">
					<input type="hidden" name="pid" id="pid" value="${organizationForm.organization.id}"/>
					<input type="text"  class="input_text_normal" readonly="readonly" name="pname" id="pname"   value="${organizationForm.organization.name}"  style="width:250px;"/>
				</c:if>
				<c:if test="${organizationForm.dealMethod eq 'detail'}">
					<input type="hidden" name="pid" id="pid" value="${organizationForm.pid}"/>
					<input type="text"  class="input_text_normal" readonly="readonly" name="pname" id="pname"  value="${organizationForm.pname}" style="width:250px;"/>
				</c:if>
				<input type="button" class="btn_small" name="button" id="button" value="选择" onclick="checkOrg()" />
			</td>
		</tr>
		<tr>
			<td class="td_left">机构描述：</td>	
			<td height="160" >		
				<textarea  class="input_textarea_normal"  name="organization.description">${organizationForm.organization.description}</textarea>
			</td>				
		</tr>
		<tr>
		</tr>
		<tr>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button"  class="btn_normal"   name="button_save" value="保存" onClick="javascript:button_save_onclick(this);" />&nbsp;&nbsp;&nbsp;&nbsp;			
				<input type="reset"   class="btn_normal"   name="button_reset" value="重置"  >	
			</td>		
		</tr>
	</table>	
	</div>

 </form>
</body>
</html>
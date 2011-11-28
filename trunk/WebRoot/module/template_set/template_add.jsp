<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<%@include file="/templates/headers/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>模板选择</title> 
<script type="text/javascript">	
$(document).ready(function() {		
	$("#templateInstanceName").focus();
}); 

	function changeTemplate(obj) {
		var categoryId = obj.value;
		var str = document.templateUnitForm.templateStr.value;
		if(str == "" || str == null) {
			return false;
		}
		var selObj = document.templateUnitForm.template;
		var values = str.split("::::");
		var value = new Array();
		var templateCategoryIds = new Array();
		var templateArr = new Array();
		var templateIds = new Array();
		var templateNames = new Array();
		var templateUrls = new Array();
		for(var i = 0; i < values.length; i++) {
			value[i] = values[i];
			templateCategoryIds[i]  = (value[i].split("####"))[0];
			templateArr[i] = (value[i].split("####"))[1];
			var test = templateArr[i].split(",,");
			templateIds[i] = test[0];
			templateNames[i] = test[1];
			templateUrls[i] = test[2];
		}	
		var j = 0;
		var t = 0;
		for(j = 0; j < templateCategoryIds.length; j++) {
			// 换值
			if(categoryId == templateCategoryIds[j]) {
				t++;
			} 
		}
		// 如果没有此类别id
		if(t == 0) {
			selObj.length = 0;
		} else {
			selObj.length = t;
			var n = 0;
			for(var q = 0; q < templateCategoryIds.length; q++) {
				if(templateCategoryIds[q] == categoryId) {
					selObj.options[n].value = templateIds[q] + "###" + templateUrls[q];
					selObj.options[n].text = templateNames[q];
					n++;
				}	
			}				
		}
	}

	function showModel() {
		var url = "";
		var arr = document.templateUnitForm.template;
		for(var i = 0; i < arr.length; i++) {
			if(arr.options[i].selected) {
				var test = arr.options[i].value.split("###");
				url = test[1];
			}
		}
		if(url != "") {
			window.open(url, "", "width=" + screen.width + ",height=" + screen.height + ",top=0, left=0, statues=yes, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes, location=yes, status=yes'");
		} else {
			alert("请选择预览记录");
		}
	}

	function addTemplateInstance() {
		var id = 0;
		var arr = document.templateUnitForm.template;
		for(var i = 0; i < arr.length; i++) {
			if(arr.options[i].selected) {
				var test = arr.options[i].value.split("###");
				id = test[0];
				break;
			}
		}
		if(id == 0){
			alert("请选择模版");
			return false;
		}
		var templateInstanceName = $("#templateInstanceName").val();
		if(templateInstanceName == null || templateInstanceName.trim() == "") {
			alert("请填写实例名称");
			return false;
		}
		if(id != 0) {
			var templateType = $("#templateType").val();
			var nodeId = $("#nodeId").val();
			var templateInstance = $("#templateInstance").val();
			var templateInstanceIdValue = $("#templateInstanceIdValue").val();
			var isTemplateSeted = $("#isTemplateSeted").val();
			$("#dealMethod").val("add");
			$("#templateSet").val("1");
			$("#templateId").val(id);
			$("#instanceName").val(templateInstanceName);
			$.blockUI({  }); 
			var options = {
				url: "<c:url value='/templateInstance.do'/>",
		    success: function(msg) {
				    var str = msg.split("##");
					var message = str[0];
					var templateInstanceId = str[1];
					if(message == "添加模板实例成功") {
						$.blockUI({  }); 
						$.ajax({
							 url: "<c:url value='/templateUnit.do?dealMethod=update&templateType='/>"+templateType+"&nodeId="+nodeId+"&ids="+templateInstanceId+"&templateSet=1&"+getUrlSuffixRandom(),
						    type: "post", 
					     success: function(msg) {
								rightFrame.document.getElementById(templateInstance).value = templateInstanceName;
								rightFrame.document.getElementById(templateInstanceIdValue).value = templateInstanceId;
								if(isTemplateSeted != "" && isTemplateSeted != null && isTemplateSeted != "null") {
									rightFrame.document.getElementById(isTemplateSeted).innerHTML = '<font color="red">√</font>';
								}
								 setTimeout($.unblockUI, 2000); 
								rightFrame.closeNewChild();	
						  }
						}); 
					} else {
						setTimeout($.unblockUI, 2000); 
						//alert(message);
					}
	     		}
			}; 
			$("#templateInstanceForm").ajaxSubmit(options);
		} 
	}
</script>
</head>
<body>
<form action="templateInstance.do" id="templateInstanceForm" name="templateInstanceForm" method="post">
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="templateSet" id="templateSet" />
	<input type="hidden" name="templateId" id="templateId" />
	<input type="hidden" name="templateInstanceName" id="instanceName" />
</form>

<form action="templateUnit.do" id="templateUnitForm" name="templateUnitForm" method="post">	
	<input type="hidden" name="templateSet" id="templateSet" />
	<input type="hidden" name="templateId" id="templateId" />
	<input type="hidden" name="message" id="message" value="${templateUnitForm.infoMessage}" />
	<input type="hidden" name="templateType" id="templateType" value="${templateUnitForm.templateType }" />
	<input type="hidden" name="nodeId" id="nodeId" value="${templateUnitForm.nodeId }"/>
	<input type="hidden" name="templateStr" id="templateStr" value="${templateUnitForm.templateStr}" />
	<input type="hidden" name="isTemplateSeted" id="isTemplateSeted" value="${templateUnitForm.isTemplateSeted }">
	<input type="hidden" name="templateInstanceIdValue" id="templateInstanceIdValue" value="${templateUnitForm.templateInstanceIdValue }"/>
	<input type="hidden" name="templateInstance" id="templateInstance" value="${templateUnitForm.templateInstance }">

	<table border="0" align="center" style="width:90%;margin-top: 10px">
		<tr>
			<td>
				<fieldset style="height:400"><legend>&nbsp;<font size="-1" style='color:blue;font:12px;font-weight:bold'>模板类别</font>&nbsp;</legend>
				<span style="overflow-x:auto; width:105px;" >    
					<select id="category" size="23" onChange="changeTemplate(this)" name="category">
						<c:forEach items="${templateUnitForm.templateCategoryList}" var="list">
							<option value="${list.id}">${list.name}</option>
						</c:forEach> 
					</select> 
				</span>
				</fieldset>
			</td>
			<td>		 
				<fieldset style="height:400"><legend>&nbsp;<font size="-1" style='color:blue;font:12px;font-weight:bold'>模板信息</font>&nbsp;</legend>
					<span style="overflow-x:auto; width:395px" > 
						<select id="template" size="23" style="width:395px">
						</select>
					</span>
				</fieldset>
			</td>
	   </tr>
		<tr>
			<td colspan="2" style="height: 50px">
				 <label>实例名称：</label>
	             <input type="text" class="input_text_normal" style="width:450px;" name="templateInstanceName" id="templateInstanceName" valid="string" tip="实例名称不能为空"
						onkeydown="if(event.keyCode==13){return false;}"/>
	 		</td>
		</tr>
		<tr>
			<td align="center"  colspan="2">
				<input type="button" class="btn_normal"  value="预览" onclick="showModel()"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn_normal"  value="保存" onclick="addTemplateInstance()"/>	
			</td>
		</tr>
	</table>
		 
	
</form>
</body>
</html>
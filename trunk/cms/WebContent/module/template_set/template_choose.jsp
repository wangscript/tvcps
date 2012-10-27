<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<%@include file="/templates/headers/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>模板选择</title>
<script type="text/javascript">

	$(document).ready(function() {
		if(!$("#message").val().isEmpty()) {			
			/*var winid = rightFrame.getWin();
			closeWindow(winid);	
			top.document.getElementById("rightFrame").src= "<c:url value='/templateUnit.do?nodeId=${templateUnitForm.nodeId}&dealMethod='/>";
		*/
			var templateInstance = $("#templateInstance").val();
			var instanceName = $("#instanceName").val();
			rightFrame.document.getElementById(templateInstance).value = instanceName;

			var templateInstanceIdValue = $("#templateInstanceIdValue").val();
			var templateInstanceId = $("#strid").val();
			rightFrame.document.getElementById(templateInstanceIdValue).value = templateInstanceId;

			var isTemplateSeted = $("#isTemplateSeted").val();
			if(isTemplateSeted != "" && isTemplateSeted != null && isTemplateSeted != "null") {
				rightFrame.document.getElementById(isTemplateSeted).innerHTML = '<font color="red">√</font>';
			}
			//rightFrame.document.getElementById(templateInstanceIdValue).value = templateInstanceId;
			rightFrame.closeNewChild();	
		}
	});

	function changeInstance(obj) {
		var categoryId = obj.value;
		var str = document.templateUnitForm.templateInstanceStr.value;
		if(str == "" || str == null) {
			return false;
		}
		var selObj = document.templateUnitForm.instance;
		var values = str.split("::::");
		var value = new Array();
		var templateCategoryIds = new Array();
		var instanceArr = new Array();
		var instanceIds = new Array();
		var instanceNames = new Array();
		var instanceUrls = new Array();
		for(var i = 0; i < values.length; i++) {
			value[i] = values[i];
			templateCategoryIds[i]  = (value[i].split("####"))[0];
			instanceArr[i] = (value[i].split("####"))[1];
			var test = instanceArr[i].split(",,");
			instanceIds[i] = test[0];
			instanceNames[i] = test[1];
			instanceUrls[i] = test[2];
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
					selObj.options[n].value = instanceIds[q] + "###" + instanceUrls[q];
					selObj.options[n].text = instanceNames[q];
					n++;
				}	
			}				
		}
	}

	function showModel() {
		var url = "";
		var arr = document.templateUnitForm.instance;
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

	function saveInstance() {
		var id = 0;
		var arr = document.templateUnitForm.instance;
		for(var i = 0; i < arr.length; i++) {
			if(arr.options[i].selected) {
				var test = arr.options[i].value.split("###");
				id = test[0];
			}
		}
		if(id != 0) {
			$("#dealMethod").val("update");
			$("#strid").val(id);
			$("#templateUnitForm").submit();	
		} else {
			alert("请选择实例");
		}
	}
</script>
</head>
<body>
<form action="templateUnit.do" id="templateUnitForm" name="templateUnitForm" method="post">
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="templateSet" id="templateSet" value="0"/>
	<input type="hidden" name="ids" id="strid" value="${templateUnitForm.ids }"/>
	<input type="hidden" name="isTemplateSeted" id="isTemplateSeted" value="${templateUnitForm.isTemplateSeted }">
	<input type="hidden" name="templateInstanceIdValue" id="templateInstanceIdValue" value="${templateUnitForm.templateInstanceIdValue }"/>
	<input type="hidden" name="instanceName" id="instanceName" value="${templateUnitForm.instanceName }"/>
	<input type="hidden" name="templateInstance" id="templateInstance" value="${templateUnitForm.templateInstance }">
	<input type="hidden" name="message" id="message" value="${templateUnitForm.infoMessage}" />
    <input type="hidden" name="templateType" id="templateType" value="${templateUnitForm.templateType }" />
	<input type="hidden" name="nodeId" id="nodeId" value="${templateUnitForm.nodeId }"/>
	<input type="hidden" name="templateInstanceStr" id="templateInstanceStr" value="${templateUnitForm.templateInstanceStr}" />
	<table border="0" align="center" style="width:90%;margin-top: 10px">
		<tr>
			<td>
				<fieldset style="height:400"><legend>&nbsp;<font size="-1" style='color:blue;font:12px;font-weight:bold'>模板类别</font>&nbsp;</legend>
				<span style="overflow-x:auto; width:105px;" >    
					<select id="category" size="23"  onChange="changeInstance(this)">  
						<c:forEach items="${templateUnitForm.templateCategoryList}" var="list">
							<option value="${list.id}">${list.name}</option>
						</c:forEach> 
					</select> 
				</span>
				</fieldset>
			</td>
			<td>
				<fieldset style="height:400"><legend>&nbsp;<font size="-1" style='color:blue;font:12px;font-weight:bold'>模板和实例信息</font>&nbsp;</legend>
					<span style="overflow-x:auto; width:395px" > 
						<select id="instance" size="23" style="width:395px">
						</select>
					</span>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td align="center"  colspan="2" style="height: 50px">
				<input type="button" class="btn_normal"  value="预览" onclick="showModel()"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn_normal"  value="保存" onclick="saveInstance()"/>	
		 	</td>
		</tr>
	</table>
</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>单元设置</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">
	var appName = "/"+"${appName}";
	var instanceId = "${templateUnitForm.instanceId}";
	var columnId = "${templateUnitForm.columnId}";
	var unitId = "";
	
	var typeId = "";
	
	$(document).ready(function() {
		var beforChangeValue = $("#selectUnitList :selected").val();
		$("#beforChangeValue").val(beforChangeValue);
	});
	
	// 改变左侧单元列表事件
	function changeUnitList(obj) {
		
		var windd = document.getElementById("unitEditArea").contentWindow;
		var hasSaved = windd.document.getElementById("hasSaved").value;		
		if(hasSaved == "N") {
			//if(confirm("您的信息还未保存，是否确定要切换模板单元")) {
				$("#beforChangeValue").val(obj.value);
				
			/*} else {
				var selObj = document.templateunitForm.selectUnitList;
				var beforChangeValue = document.getElementById("beforChangeValue").value;
				for(var i = 0; i < selObj.length; i++) {
					if(beforChangeValue == selObj.options[i].value) {
						selObj.options[i].selected = true;
						return false;
					}
				}
			}*/
		}
		var unitId = $("#selectUnitList :selected").val();
		//var typeId = $("#selectCategoryList :selected").val();
		var typeId = $("#selectUnitList :selected").attr("type");
		if(typeId == null || typeId == "") {
			typeId = "t1"; 
		}
		$.each($("#selectCategoryList option"), function(i, n){
			if (typeId == n.value) {
				// 选中单元类型
				$(n).attr("selected", "selected");
				var configUrl = $(n).attr("configurl");
				configUrl = configUrl + "?dealMethod=findConfig&unit_categoryId=" + typeId 
									  + "&unit_unitId=" + unitId
									  + "&unit_columnId=" + columnId;
				$("#unitEditArea").attr("src", appName + "/" + configUrl);
			//	$("#unitEditArea").attr("src", appName + "/" + $(n).attr("configurl"));
			}
		});
	}

	// 改变右上类别列表事件
	function changeCategoryList() {
		unitId = $("#selectUnitList :selected").val();
		var configUrl = $("#selectCategoryList :selected").attr("configurl");
		var categoryId = $("#selectCategoryList :selected").val();
		configUrl = configUrl + "?dealMethod=findConfig&unit_categoryId=" + categoryId 
							  + "&unit_unitId=" + unitId
							  + "&unit_columnId=" + columnId;
		if (typeId > 0) {
			categoryId = typeId;
		}

		$("#unitEditArea").attr("src", appName + "/" + configUrl);
	}

	// 刷新模板
	function refreshTemplate() {
		unitId = $("#selectUnitList :selected").val();
		parent.window.location.href = appName + "/templateUnit.do?dealMethod=templateSet" 
							   + "&unitId=" + unitId
							   + "&instanceId=" + instanceId;
	}

</script>
</head>
<body>
<form id="templateunitForm" name="templateunitForm">
	<input type="hidden" name="beforChangeValue" id="beforChangeValue" value="" />
<div id="main" style="position:absolute; left:9px; top:12px; width:823px; height:550px; z-index:1">
	<div id="left" style="position:absolute; left:6px; top:6px; width:186px; height:534px; z-index:2">
		<fieldset>
			<legend style="color:#0000ff;"><b>模板单元列表区</b>&nbsp;</legend>		
				<select id="selectUnitList" name="selectUnitList" style="width:160px;height:520px;"  size="70" onchange="changeUnitList(this)">
					<c:forEach var="unit" items="${templateUnitForm.templateUnits}" varStatus="s" step="1">
						<c:choose>
							<c:when test="${templateUnitForm.unitId == unit.id}">
								<option value="${unit.id}" type="${unit.templateUnitCategory.id}" selected="selected">${s.index+1}.${unit.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${unit.id}" type="${unit.templateUnitCategory.id}">${s.index+1}.${unit.name}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
		</fieldset>
	</div>
	<div id="right" style="position:absolute; left:200px; top:5px; width:622px; height:544px; ">	
		 <div id="topleft" style="position:absolute; left:3px; top:1px; width:386px; height:60px;">
			  <fieldset style="width:375px;height:50px;"><legend style="color:#0000ff;"><b>单元类型切换区</b>&nbsp;</legend>	
				 <select id="selectCategoryList" name="selectCategoryList" style="width:200px;"  onchange="changeCategoryList()" >
					<c:forEach var="category" items="${templateUnitForm.templateUnitCategories}">
						<c:choose>
							<c:when test="${templateUnitForm.categoryId == category.id}">
								<option value="${category.id}" configurl="${category.configUrl}" selected="selected">${category.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${category.id}" configurl="${category.configUrl}">${category.name}</option>
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
				 </select>
		   	  </fieldset>
   	  	</div>
		 <div id="topright" style="position:absolute; left:395px; top:1px; width:230px; height:60px;">
			  <fieldset style="width:223px;height:50px;"><legend style="color:#0000ff;"><b>模板刷新区</b>&nbsp;</legend>
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="刷新模板" style="margin-top:3px;" class="btn_small"  onclick="refreshTemplate()"/>
			  </fieldset>		 
		 </div>		
		
		 <div id="below" style="position:absolute; left:4px; top:65px; width:616px; height:450px;">
			 <fieldset style="width:616px;height:420px;"><legend style="color:#0000ff;"><b>模板单元编辑区</b>&nbsp;</legend> 
				<iframe id="unitEditArea" style="width:610px;height:450px;" src="${templateUnitForm.configUrl}">
				</iframe>
			</fieldset>
		 </div>

		<!-- 
		<div id="mainbelow" style="position:absolute; left:3px; top:512px; width:609px; height:26px; z-index:2; text-align:center">				
			  <input type="button" name="saveValue" value="保存"/>
			  <input type="button" name="" value="网站内保存"/>
			  <input type="reset" name="resetValue" value="重置"/>
	   </div>
	    -->
    </div>
</div>


</form>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tld/ccms.tld" prefix="ccms"%>
<%@taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<script type="text/javascript" src="<c:url value="/script/fckeditor/fckeditor.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/script/jquery-tooltip/css/tooltip.css"/>" >
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>" >
<script type="text/javascript" src="<c:url value="/script/jquery-tooltip/js/tooltip.js"/>"></script>
<script type="text/javascript">

	$(document).ready(function() {
		genSet();
	    changeValues();
	});

	function genSet() {
		var selObj = document.articleForm.generalSystemSetHtml;
		if(selObj == null) {
			return false;
		}
	  	//为作者select控件对象赋值
		var generalSystemSet = document.getElementById("generalSystemSetList").value;
		var generalSystemSetList = generalSystemSet.split("*");
		var selectData = document.getElementById('generalSystemSetHtml');
		selectData.options.add(new Option("--请选择作者--", ""));
		if(generalSystemSet != null && generalSystemSet != "") {
			for(var i = 0; i < generalSystemSetList.length-1; i++) {
				if((generalSystemSetList[i]!= null)&&generalSystemSetList[i]!="" ){
				    var option = new Option(generalSystemSetList[i],generalSystemSetList[i]);
				    selectData.options.add(option);	 
				 } 
	  		}
		}
  		for(var i = 0; i < selectData.length; i++) {
  	  		if($("#author").val() != null && $("#author").val() != "") {
	  	  		if($("#author").val() == selectData[i].value){
	  				selectData[i].selected = true;
	  		  	}
  	  		}else{
				var defaultAuthor = generalSystemSetList[generalSystemSetList.length-1];
				if(defaultAuthor != null && defaultAuthor != "") {
					if($("#articleId").val() == null || $("#articleId").val() == ""){
						$("#author").val(defaultAuthor);
					}
				}
  	  		}
  		}

  		
		//来源设置
		var generalSystemSetOrgin = document.getElementById("generalSystemSetOrgin").value;
		var  generalSystemSetOrginList = generalSystemSetOrgin.split("*");
		var selectDataOrgin = document.getElementById('yy');
		selectDataOrgin.options.add(new Option("--请选择来源--", ""));
		if(generalSystemSetOrgin != null && generalSystemSetOrgin != "") {
		    for(var j = 0; j < generalSystemSetOrginList.length-1; j++) {				
				if(generalSystemSetOrginList[j]!=null&&generalSystemSetOrginList[j]!=""){
					var orgin = new Option(generalSystemSetOrginList[j],generalSystemSetOrginList[j]);			
				    selectDataOrgin.options.add(orgin);
				}
	  		}
		}
	    for(var i = 0; i < selectDataOrgin.length; i++) {
	    	if($("#infoSource").val() != null && $("#infoSource").val() != "") {
	  	  		if($("#infoSource").val() == selectDataOrgin[i].value){
	  	  			selectDataOrgin[i].selected = true;
	  		  	}
  	  		}else{
				var defaultInfoSource = generalSystemSetOrginList[generalSystemSetOrginList.length-1];
				if(defaultInfoSource != null && defaultInfoSource != "") {
					if($("#articleId").val() == null || $("#articleId").val() == ""){
						$("#infoSource").val(defaultInfoSource);
					}
				}
  	  		}
  		}
	}

	function changeValues(){
		//枚举信息字符串,形式为：id1,value11,value12,value13::id2,value21,value22...
		//写入枚举值
		var enumValues = document.getElementById("enumInfoStr").value;
		//id,value1,value2,value3
		var arr1 = enumValues.split("::");
		//fleName1,enumerationId1#fleName2,enumerationId2#...
		var enumerationId = document.getElementById("enumerationId").value;
		//fleName,enumerationId
		var arr2 = enumerationId.split("#");
		//enumeration1,enumeration2,...
		var selectNameStr = document.getElementById("selectNameStr").value;
		var arr3 = selectNameStr.split(",");
		var selObj = null;
		for(var i = 0; i < arr3.length; i++) {
			if(arr3[i] == "enumeration1") {
				selObj = document.articleForm.enumeration1;
				//清空
				document.getElementById("enumeration1").options.length = 0;
			}
			if(arr3[i] == "enumeration2") {
				selObj = document.articleForm.enumeration2;
				//清空
				document.getElementById("enumeration2").options.length = 0;
			}
			if(arr3[i] == "enumeration3") {
				selObj = document.articleForm.enumeration3;
				//清空
				document.getElementById("enumeration3").options.length = 0;
			}
			if(arr3[i] == "enumeration4") {
				selObj = document.articleForm.enumeration4;
				//清空
				document.getElementById("enumeration4").options.length = 0;
			}
			if(arr3[i] == "enumeration5") {
				selObj = document.articleForm.enumeration5;
				//清空
				document.getElementById("enumeration5").options.length = 0;
			}
			if(selObj != null) {
			for(var j = 0; j < arr2.length-1; j++) {
				var arr4 = arr2[j].split(",");
				if(arr4[0] == arr3[i]) {
					for(var k = 0; k < arr1.length-1; k++) {
						var arr5 = arr1[k].split(",");
						if(arr5[0] == arr4[1]) {
							for(var m = 1; m < arr5.length-1; m++) {
								if(selObj.length == 0) {
									selObj.length = 1;
									if($("#articleId").val().isEmpty() || $("#articleId").val() == 0) {
										var arrId = arr3[i] + "0";
										$(arrId).val(arr5[1]);
										if(arr3[i] == "enumeration1") {
											$("#enumeration10").val(arr5[1]);
										}
										if(arr3[i] == "enumeration2") {
											$("#enumeration20").val(arr5[1]);
										}
										if(arr3[i] == "enumeration3") {
											$("#enumeration30").val(arr5[1]);
										}
										if(arr3[i] == "enumeration4") {
											$("#enumeration40").val(arr5[1]);
										}
										if(arr3[i] == "enumeration5") {
											$("#enumeration50").val(arr5[1]);
										}
									}
								} else {
									selObj.length++;
								}
								selObj.options[selObj.length-1].value = arr5[m];
								selObj.options[selObj.length-1].text = arr5[m];
								if(!$("#articleId").val().isEmpty() && $("#articleId").val() != 0) {
									var arrId = arr3[i] + "0";
									var selValue = document.getElementById(arrId).value;
									if(selValue == arr5[m]) {
										selObj.options[selObj.length-1].selected = true;
										if(arr3[i] == "enumeration1") {
											$("#enumeration10").val(selValue);
										}
										if(arr3[i] == "enumeration2") {
											$("#enumeration20").val(selValue);
										}
										if(arr3[i] == "enumeration3") {
											$("#enumeration30").val(selValue);
										}
										if(arr3[i] == "enumeration4") {
											$("#enumeration40").val(selValue);
										}
										if(arr3[i] == "enumeration5") {
											$("#enumeration50").val(selValue);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}

</script>

<input type="hidden" id="colId" name="colId" value="${articleForm.article.column.id}">
<input type="hidden" id="forId" name="forId" value="${articleForm.article.articleFormat.id}">
<input type="hidden" name="siteId" id="siteId" value="${articleForm.siteId}"/>
<input type="hidden" name="creatorId" id="creatorId" value="${articleForm.creatorId}"/>
<input type="hidden" name="orders"     id="orders"   value="${articleForm.orders}" />
<input type="hidden" name="refId"      id="refId"    value="${articleForm.refId}" />
<input type="hidden" name="isref"      id="isref"    value="${articleForm.isref}" />
<input type="hidden" name="auditorId" id="auditorId" value="${articleForm.auditorId }"/> 

                     

<ul>
	<ccms:form article="${articleForm.article}" format="${articleForm.articleFormat}" attributes="${articleForm.attributeList}"/>
</ul>

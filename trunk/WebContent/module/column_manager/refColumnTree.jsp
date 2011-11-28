<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<title></title>
	<%@include file="/templates/headers/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
	<script language="javascript">

	$(document).ready(function() {
		parent.document.getElementById("sameFormatColumns").value = document.getElementById("sameFormatColumns").value;
		document.getElementById("columnId").value = parent.document.getElementById("columnId").value;
	});

	var refSel = false;
	function tree_oncheck(node) {	
		var sameValue = document.getElementById("columnId").value;
		if(sameValue == node.id && node.attributes.checked == true) {
			if(!refSel){
				alert("不能同步到自己！");
				refSel = true;
			}
			return false;
		}else if(sameValue == node.id && node.attributes.checked == false){
			refSel = false;								
		}
		var refSiteName = document.getElementById("refSiteName").value;
		if(node.id != "0" && node.id != "") {
			var flag = ""+node.attributes.checked;
			var selObj = parent.document.columnForm.selColumnNameStr;
			var theObjOptions = selObj.options;
			var repeat = "no";
			
			for(var i = 0; i < theObjOptions.length; i++) {
				if(node.id == theObjOptions[i].value) {
					repeat = "yes";
				} 
			}
			
			//添加
			if(flag == "true") {
				//判断重复
				if(repeat == "no") {
					if(selObj.length == 0) {
						selObj.length = 1;
						if(node.id != null && node.id != "0" && node.id != "" && node.text != null && node.text != "") {
							selObj.options[selObj.length-1].value = node.id;
							selObj.options[selObj.length-1].text = node.text+"【"+refSiteName+"】";
						}
					} else {
						selObj.length++;
						if(repeat == "no" && node.id != null && node.id != "0" && node.id != "" && node.text != null && node.text != "") {
							selObj.options[selObj.length-1].value = node.id;
							selObj.options[selObj.length-1].text = node.text+"【"+refSiteName+"】";
						}
					}
				}
				
			} else {
				
				//删除
				if(repeat == "yes") {
					//先把值取出来
					var strid = "";
					var strname = "";
					for(var j = 0; j < theObjOptions.length; j++) {
						if( theObjOptions[j].value != node.id ) {
							strname = strname + theObjOptions[j].text + ",";
							strid = strid + theObjOptions[j].value + ",";
						}
					}
					//清空
					parent.document.getElementById("selColumnNameStr").options.length = 0;
					//再把值写进去
					var arr1 = strname.split(",");
					var arr2 = strid.split(",");
					for(var k = 0; k < arr1.length-1; k++) {
						if(selObj.length == 0) {
							selObj.length = 1;
						} else {
							selObj.length++;
						}
						selObj.options[selObj.length-1].value = arr2[k];
						selObj.options[selObj.length-1].text = arr1[k];
					}	
				}
			}
		}
	}

	function tree_onclick(node) {	
		return false;
	}

</script>
	
</head>
<body>
	<input type="hidden" name="sameFormatColumns" id="sameFormatColumns" value="${columnForm.sameFormatColumns}"/>
	<input type="hidden" name="refSiteName" id="refSiteName"  value="${columnForm.refSiteName}"/>
	<input type="hidden" name="columnId" id="columnId"  value=""/>
	<complat:tree  unique="column" checkbox="true" siteid="${columnForm.refSiteId}" treeurl="${columnForm.treeUrl}" />
	
</body>
</html>
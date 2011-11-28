<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>选择信息分类</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">

	var firstWin = top.getWin();		
	var temp = firstWin.split("###");
	var dialogProperty = temp[0];
	var detailFrame = "_DialogFrame_"+dialogProperty;	 		   
	var tempWindow = top.document.getElementById(detailFrame).contentWindow;
	var secondTempWindow = tempWindow.document.getElementById("unitEditArea").contentWindow;		
	var secondWin = secondTempWindow.getWin();		
	var secondtemp = secondWin.split("###");
	var seconddialogProperty = secondtemp[0];
	var seconddetailFrame = "_DialogFrame_"+seconddialogProperty;	
	var secondWindow = top.document.getElementById(seconddetailFrame).contentWindow;
	
	/** 退出 */
	function button_quit_onclick() {	
		top.closeWindow(secondWin);	
	}

	Array.prototype.distinct = function() {
		var ArrayObj  = {};
		var returnArray = [];
		for(var i = 0; i < this.length; i++) {
			if(ArrayObj[this[i]]) 
				continue;
		    ArrayObj[this[i]] = this[i];
		    returnArray.push(this[i])
		}
		return returnArray
	}

	String.prototype.startWith=function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		    return false;
		if(this.substr(0,str.length)==str)
			return true;
		else
			return false;
	 	return true;
	 }

	/** 保存设置 */
	function button_save_onclick(ee) {	
		var checkIds = $("#checkedTreeIds").val();
		var checkNames = $("#checkTreeNames").val();
		if(checkIds == null || checkIds == "" || checkIds == ",") {
			secondTempWindow.document.getElementById("infoCategory").value = "";
			secondTempWindow.document.getElementById("infoCategoryTemp").value = "";
			top.closeWindow(secondWin);
			return false;
		}
		if(checkIds.startWith(",")) {
			checkIds = checkIds.substring(1, checkIds.length);
			checkNames = checkNames.substring(1, checkNames.length);
		}
		var ids = checkIds.split(",");
		var names = checkNames.split(",");
		var dis = new Array();
		if(ids != null && ids != "") {
			// 在页面上没有做任何处理（没有任何操作就确定了,直接关闭就可以了）
			if(ids[0] == null || ids[0] == "") {
				top.closeWindow(secondWin);
				return false;
			}
			for(var j = 0; j < ids.length; j++) {
				dis.push(ids[j].split("#")[1]);
			}
			var cIds = dis.distinct();
			if(cIds != null && cIds.toString() != "") {
				if(cIds.toString().indexOf(",") == 0) {
					cIds = cIds.toString().substring(0, cIds.length);
				} 
			} else {
				secondTempWindow.document.getElementById("infoCategory").value = "";
				secondTempWindow.document.getElementById("infoCategoryTemp").value = "";
				top.closeWindow(secondWin);
				return false;
			}	
			
		} else {
			secondTempWindow.document.getElementById("infoCategory").value = "";
			secondTempWindow.document.getElementById("infoCategoryTemp").value = "";
			top.closeWindow(secondWin);
			return false;
		}
		// 去除重复类别后的类别ids
		var enumArr = new Array();
		var nameArr = new Array();
		for(var i = 0; i < cIds.length; i++) {
			var arr = new Array();
			var arr1 = new Array();
			for(var k = 0; k < ids.length; k++) {
				if(ids[k].split("#")[1] == cIds[i]) {
					arr.push(ids[k]);
					arr1.push(names[k]);
				}
			}
			for(var m = 0; m < arr.length; m++) {
				for(var n = 0; n < arr.length-m-1; n++) {
					var temp = "";
					if(arr[n].split("#")[0] > arr[n+1].split("#")[0]) {
						temp = arr[n];
						arr[n] = arr[n+1];
						arr[n+1] = temp;
						temp = arr1[n];
						arr1[n] = arr1[n+1];
						arr1[n+1] = temp;
					}
				}
			}
			for(var a = 0; a < arr.length; a++) {
				enumArr.push(arr[a]);
				nameArr.push(arr1[a]);
			} 
		}
		var infoCategory = ""; 
		var infoCategoryTemp = "";
		for(var t = 0; t < enumArr.length; t++) {
			var temp1 = enumArr[t].split("#")[1];
			var name = nameArr[t].split("#")[1];
			if(infoCategory != "") {
				infoCategory += ":::" + temp1 + "##" + name;
			} else {
				infoCategory = temp1 + "##" + name;
			}
			infoCategoryTemp += "【" + name + "】";
		}
		secondTempWindow.document.getElementById("infoCategory").value = infoCategory;
		secondTempWindow.document.getElementById("infoCategoryTemp").value = infoCategoryTemp;
		top.closeWindow(secondWin);	
	}	
	

</script>
</head>
<body>
<form id="magazineCategoryForm" action="<c:url value="/magazineCategory.do"/>" name="magazineCategoryForm" method="post">
	<c:if test="${magazineCategoryForm.allData == null}">
		<div style="margin:160 0 0 100;" ><font size="2" color="red">该栏目格式属性不存在枚举类型,请<a onclick="javascript:button_quit_onclick(this);" style="cursor: hand">返回</a></font></div>
	</c:if>     
	<c:if test="${magazineCategoryForm.allData != null}">
	<div style="HEIGHT: 250px;left:115px; top:16px; VISIBILITY: inherit; WIDTH:550px;background:white; " name="first" id="first">
		<complat:treecheckbox allData="${magazineCategoryForm.allData}" checkData="${magazineCategoryForm.chooseData}" colNum="3" names="checkTreeNames"></complat:treecheckbox>	
	</div>	
	<table width="100%">
		<tr>
			<td align="center">
				<input  type="button"  name="button_save" class="btn_normal"  value="保存" onClick="javascript:button_save_onclick(this);" >
				&nbsp;&nbsp;
				<input  type="button"  name="button_quit" class="btn_normal"  value="退出" onClick="javascript:button_quit_onclick(this);" >
			</td>
		</tr>
	</table>
	</c:if>
</form>
</body>
</html>
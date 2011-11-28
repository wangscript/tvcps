<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>属性管理</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript" src="<c:url value="/script/fckeditor/editor/js/fckfilemanager.js"/>"></script>
<style type="text/css" media="all">
	
</style>
<script type="text/javascript">
	//新增加的方法
	var temp = rightFrame.win.split("###");
	var dialogProperty = temp[0];
	var detailFrame = "_DialogFrame_"+dialogProperty;	 	
	var tempWindow = top.document.getElementById(detailFrame).contentWindow;

	//初始属性名称,判断重名
	var attributeName = "";
	$(document).ready(function() {
		$("#attributeName").focus();
		attributeName = document.getElementById("attributeName").value;
		if(!$("#message").val().isEmpty()){
			if($("#message").val() == "添加成功") {
				document.getElementById("confirm").disabled="disabled";
				var formatId = $("#formatId").val();
				var formatName = $("#formatName").val();
				var fromDefault = document.getElementById("fromDefault").value;
				var url = "<c:url value='/articleAttribute.do?dealMethod=&formatId="+ formatId +"&formatName="+ formatName + "&fromDefault=" + fromDefault + "&" + getUrlSuffixRandom() + "'/>";
				top.document.getElementById("rightFrame").src = url;
				closeWindow(rightFrame.getWin());
			}
		}
	}); 
	// 保存
	function btn_confirm() {
		var name = $("#attributeName").val();
		if(name.trim().isEmpty()){
			alert("请输入属性名称");
			return false;
		}
		if($("[id=attribute.attributeType] :selected").val() == "enumeration") {
			if($("#enumName").find("option").length == 0) {
				alert("不存在枚举类别");
				return false;
			}
		}
		var names = $("#attributeNameStr").val();
		var arr = new Array();
		arr = names.split(",");
		for ( var i = 0; i < arr.length; i++) {
			if(arr[i] == name) {
				alert("该类别已存在!");
				return false;
			}
		}
		document.getElementById("confirm").disabled="disabled";
		if ($("#attributeId").val().isEmpty()) {
			$("#dealMethod").val("add");
		} else {
			$("#dealMethod").val("modify");
		}
		$("#articleAttributeForm").submit();
	}

	function changeType(obj) {
		//枚举信息字符串,形式为：id1,name1,#value11,value12,value13::id2,name2#value21,value22...
		if(obj.value == "enumeration") {
			document.getElementById("valid2").style.display = "";
			document.getElementById("valid0").style.display = "none";
			//清空枚举类别
			document.getElementById("enumName").options.length = 0;
			//写入枚举类别
			var enumValues = document.getElementById("enumInfoStr").value;
			if(enumValues.isEmpty()){
				alert("不存在枚举类型，请到枚举管理下添加枚举");
				return false;
			}
			var arr1 = enumValues.split("::");
			var selObj = document.articleAttributeForm.enumName;
			for(var i = 0; i < arr1.length-1; i++) {
				var arr2 = arr1[i].split(",");
				if(selObj.length == 0) {
					selObj.length = 1;
					$("#enumerationId").val(arr2[0]);
				} else {
					selObj.length++;
				}
				selObj.options[selObj.length-1].value = arr2[0];
				selObj.options[selObj.length-1].text = arr2[1];
		/**		//写入第一个枚举类别的值
				var selObj2 = document.articleAttributeForm.enumValue;
				if(i == 0) {
					var arr3 = arr1[i].split("#");
					var arr4 = arr3[1].split(",");
					for(var j = 0; j < arr4.length-1; j++) {
						if(selObj2.length == 0) {
							selObj2.length = 1;
						} else {
							selObj2.length++;
						}
						selObj2.options[selObj2.length-1].value = arr4[j];
						selObj2.options[selObj2.length-1].text = arr4[j];
					}
				}
		*/
			}//结束
		} else {
			document.getElementById("valid2").style.display = "none";
			document.getElementById("valid0").style.display = "";
		}
	}

	
	function changeValue(obj) {
		$("#enumerationName").val(obj.text);
		$("#enumerationId").val(obj.value);

	/**	
		//枚举信息字符串,形式为：id1,name1,#value11,value12,value13::id2,name2#value21,value22...
		//清空值
		document.getElementById("enumValue").options.length = 0;
		//写入枚举值
		var enumValues = document.getElementById("enumInfoStr").value;
		var selObj = document.articleAttributeForm.enumValue;
		var arr1 = enumValues.split("::");
		for(var i = 0; i < arr1.length; i++) {
			var arr2 = arr1[i].split(",");
			//根据类别写入对应值
			if(arr2[0] == obj.value) {
				var arr3 = arr1[i].split("#");
				var arr4 = arr3[1].split(",");
				for(var j = 0; j < arr4.length-1; j++) {
					if(selObj.length == 0) {
						selObj.length = 1;
					} else {
						selObj.length++;
					}
					selObj.options[selObj.length-1].value = arr4[j];
					selObj.options[selObj.length-1].text = arr4[j];
				}
			}
		}
	*/
	}
	
	// 退出
	function btn_quit() {
		//tempWindow.closeAttrWin();
		closeWindow(rightFrame.getWin());
	}

</script>
</head>
<body>
    <form name="articleAttributeForm" id="articleAttributeForm" action="<c:url value="/articleAttribute.do"/>" method="post">
    <input type="hidden" name="dealMethod" id="dealMethod" />
    <input type="hidden" name="message" id="message" value="${articleAttributeForm.infoMessage}" />
    <input type="hidden" name="attribute.id" id="attributeId" value="${articleAttributeForm.attribute.id}"/>
    <input type="hidden" id="formatId" name="formatId" value="${articleAttributeForm.formatId}"/>
    <input type="hidden" id="formatName" name="formatName" value="${articleAttributeForm.formatName}"/>
    <input type="hidden" id="formatFields" name="formatFields" value="${articleAttributeForm.formatFields}"/>
    <input type="hidden" id="enumInfoStr" name="enumInfoStr" value="${articleAttributeForm.enumInfoStr}"/>
	<input type="hidden" id="attributeNameStr" name="attributeNameStr" value="${articleAttributeForm.attributeNameStr}"/>
	<input type="hidden" id="fromDefault" name="fromDefault" value="${articleAttributeForm.fromDefault}" />
	<div class="form_div">
		<table width="100%"> 
			<tr>
				<td class="td_left" width="35%"><i>*</i>属性名称：</td>
				<td><input type="text" style="width:155px;" class="input_text_normal" name="attribute.attributeName" id="attributeName" value="${articleAttributeForm.attribute.attributeName}"  tip="请输入属性名称" valid="string"/></td>
			</tr>
			<tr>  
				<td class="td_left">提示信息：</td>
				<td><input type="text"  style="width:155px;" class="input_text_normal" name="attribute.tip" id="tip" value="${articleAttributeForm.attribute.tip}" maxlength="30" empty="true" valid="string"/></td>
			</tr>
			<tr>
				<td class="td_left"><i>&nbsp;</i>属性类型：</td>
				<td>
					<c:choose>
					<c:when test="${articleAttributeForm.attribute.id == null}">
						<select name="attribute.attributeType" id="attribute.attributeType" style="width:155px;" class="input_select_normal" onchange="changeType(this)">
					</c:when>
					<c:otherwise>
						<select name="attribute.attributeType" id="attributeType" class="input_select_normal" style="width: 155px;" disabled>
					</c:otherwise>
					</c:choose>
							<option value="text">字符</option>
							<option value="bool">布尔</option>
							<option value="textArea">文本</option>
							<option value="date">日期</option>
							<option value="float">小数</option>
							<option value="integer">整数</option>
							<option value="pic">图片</option>
							<option value="attach">附件</option>
							<option value="media">媒体</option>
							<option value="enumeration">枚举</option>
						</select>
				</td>
			</tr>
			
			<tr id="valid2" style="display:none" >
				<td class="td_left"><i>&nbsp;</i>枚举类别：</td>
				<td>
					<select name="enumName" id="enumName"  style="width:155px;" class="input_select_normal" onchange="changeValue(this)">
					</select>
					<input type="hidden" id="enumerationName" name="enumerationName" value=""/>
					<input type="hidden" id="enumerationId" name="enumerationId" value=""/>
				</td>
			</tr> 
			<tr id="valid1" style="display:none" >
				<td class="td_left"><i>&nbsp;</i>枚举值：</td>
				<td>
					<select name="enumValue" id="enumValue" style="width:155px;" class="input_select_normal">
					</select>
				</td>
			</tr> 

			<tr id="valid0" style="display:">
				<td class="td_left"><i>&nbsp;</i>有效值：</td>
				<td>
					<select name="attribute.validValue" style="width:155px;" class="input_select_normal">
						<option value="ch">中文</option>
						<option value="en">英文</option>
						<option value="num">数字</option>
						<option value=float>浮点</option>
						<option value="string">字符串</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="td_left"><i>&nbsp;</i>是否显示：</td>
				<td>
					<input name="attribute.showed" type="radio" checked="checked" value="true"/>是
					<input name="attribute.showed" type="radio" value="false"/>否
				</td>
			</tr>
			<tr>
				<td class="td_left"><i>&nbsp;</i>可否修改：</td>
				<td>
					<input name="attribute.modified" type="radio" checked="checked" value="true"/>是
					<input name="attribute.modified" type="radio" value="false"/>否
				</td>
			</tr>
			<tr>
				<td class="td_left"><i>&nbsp;</i>可否为空：</td>
				<td>
					<input type="radio" id="attribute.empty" name="attribute.empty"  value="true" checked/>是
					<input type="radio" id="attribute.empty" name="attribute.empty"  value="false"/>否
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="确定" id="confirm" class="btn_normal" onclick="btn_confirm();"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn_normal" onclick="btn_quit()" value="取消" />
				</td>
			</tr>
		</table>
	</div>
 </form>
</body>
</html>

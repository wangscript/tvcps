<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>属性管理</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript">

	$(document).ready(function() {
		var fromDefault = document.getElementById("fromDefault").value;
		if(fromDefault == "no") {
			var defaultAttrIds = document.getElementById("defaultAttrIds").value;
			var arr = defaultAttrIds.split(",");
			var id = "";
			var nameId = "";
			//属性名称
			var attrName = "";
			for(var i = 0; i < arr.length-1; i++) {
				id = arr[i]+"_0_checkbox";
				if(document.getElementById(id) != null){
					// 不允许删除默认属性
					document.getElementById(id).disabled = true;
					nameId = "1_"+arr[i]+"_text"
				
					document.getElementById(nameId).disabled = true;
				
					attrName = $("#1_"+arr[i]+"_text").val();
				
					if(attrName != "标题") {
						modId = arr[i]+"_10";
						document.getElementById(modId).disabled = true;
					}
					document.getElementById(arr[i]+"_7").disabled = true;
					document.getElementById(arr[i]+"_6").disabled = true;
					document.getElementById(arr[i]+"_5").disabled = true;
					delId = arr[i]+"_11";
					document.getElementById(delId).disabled = true;
				}
			}
		}
	});

	// 显示明细
	function showDetail(id) {
		var fromDefault = document.getElementById("fromDefault").value;
		var url = "articleAttribute.do?dealMethod=detail&attributeId="+id + "&fromDefault=" + fromDefault;
		win = showWindow("attrWin","属性信息",url,50, 0,500,500);
	}
	
	function button_add_onclick(btn) {
		var fromDefault = document.getElementById("fromDefault").value;
		var formatId = $("#formatId").val();
		var formatFields = $("#formatFields").val();
		var formatName = $("#formatName").val();
		var url = "<c:url value='articleAttribute.do?dealMethod=detail&formatId=" + formatId + "&formatFields=" + formatFields + "&fromDefault=" + fromDefault + "&formatName=" + formatName + "'/>" ;
		win = showWindow("addWin","添加属性",url,50, 0, 320, 350);
	}
	
	function button_delete_onclick(btn) {
		var ids = document.getElementById("xx").value;
		$("#ids").val(ids);
		if (ids.isEmpty()) {
			alert("请至少选择一条记录操作！");
			return false;
		} else {
			$("#dealMethod").val("deleteAttributes");
			$("#articleAttributeForm").submit();
		}
	}
	
	// 修改单个属性
	function modifyOneAttr(id) {
		var defaultAttrIds = document.getElementById("defaultAttrIds").value;
		var arr = defaultAttrIds.split(",");
		//属性名称
		var attrName = $("#1_"+id+"_text").val();

		for(var i = 0; i < arr.length-1; i++) {
			if(id == arr[i] && attrName != "标题") {
			//	alert("默认属性不可以删除");
				return false;
			}
		}
		
		var attrTip = $("#3_"+id+"_text").val();
		var attrShow = $("#5_"+id+"_checkbox").attr("checked");
		var attrModify = $("#6_"+id+"_checkbox").attr("checked");
		var attrEmpty = $("#7_"+id+"_checkbox").attr("checked");
		$("#attributeId").val(id);
		$("#attributeName").val(attrName);
		$("#attributeTip").val(attrTip);
		$("#attributeShowed").val(attrShow);
		$("#attributeModified").val(attrModify);
		$("#attributeEmpty").val(attrEmpty);
		$("#dealMethod").val("modify");
		$("#articleAttributeForm").ajaxSubmit({
			  success: function(msg) {
			  	alert(msg);
				if(msg == "修改失败，此名称已存在") {
					$("#articleAttributeForm").resetForm();
				} 
				$("#dealMethod").val("");
			  }, 
			  dataType: "text",
			  type: "post"
		});
		return false;
	}

	// 删除单个属性
	function deleteOneAttr(id) {
		var defaultAttrIds = document.getElementById("defaultAttrIds").value;
		var arr = defaultAttrIds.split(",");
		//属性名称
		for(var i = 0; i < arr.length-1; i++) {
			if(id == arr[i]) {
			//	alert("默认属性不可以删除");
				return false;
			}
		}
					
		$("#attributeId").val(id);
		$("#dealMethod").val("delete");
		$("#articleAttributeForm").submit();
	}

	// 属性排序 
	function button_sort_onclick() {
		var fromDefault = document.getElementById("fromDefault").value;
		var formatName = $("#formatName").val();  
		var formatId = $("#formatId").val();
		win = showWindow("sortArticleAttributes", formatName + "属性排序", "<c:url value='/articleAttribute.do?dealMethod=findSortAttribute&formatId="+ formatId + "&fromDefault=" + fromDefault+"'/>", 0, 0, 500, 450);	  
	}

	// 返回
	function button_return_onclick() {
		var url = "<c:url value='/articleFormat.do?dealMethod=&" + getUrlSuffixRandom() + "'/>";
		window.location.href = url;
	}
	
	function closeAttrWin() {
		top.closeWindow(win);
	}
</script>
</head>
<body>
	<div class="currLocation">属性管理→ ${articleAttributeForm.formatName}</div>
	<form id="articleAttributeForm" action="<c:url value='articleAttribute.do'/>" method="post" name="articleAttributeForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="ids" id="ids"/>
		<input type="hidden" name="formatId" id="formatId" value="${articleAttributeForm.formatId}"/>
		<input type="hidden" name="formatName" id="formatName" value="${articleAttributeForm.formatName }"/>
		<input type="hidden" name="formatFields" id="formatFields" value="${articleAttributeForm.formatFields}"/>
		<input type="hidden" name="message" id="message" value="${articleAttributeForm.infoMessage}" />
		<input type="hidden" id="defaultAttrIds" name="defaultAttrIds" value="${articleAttributeForm.defaultAttrIds}" />
		<input type="hidden" id="fromDefault" name="fromDefault" value="${articleAttributeForm.fromDefault}" />
		<%-- attribute的属性 --%> 
		<input type="hidden" id="attributeId" name="attribute.id" value="" />
		<input type="hidden" id="attributeName" name="attribute.attributeName" value="" />
		<input type="hidden" id="attributeTip" name="attribute.tip" value="" />
		<input type="hidden" id="attributeShowed" name="attribute.showed" value="" />
		<input type="hidden" id="attributeModified" name="attribute.modified" value="" />
		<input type="hidden" id="attributeEmpty" name="attribute.empty" value="" />
		<input type="hidden" id="validValue" name="attribute.validValue" value="" />
		
		<c:if test="${articleAttributeForm.formatId != 'f1'}">
			<complat:button name="button"  buttonlist="add|0|delete|0|sort|0|return" buttonalign="left"></complat:button>
			<complat:grid ids="xx"
				width="*,80,*,80,80,80,80,0,0,45,45" 
				head="属性名称,属性类型,提示信息,有效值,是否显示,可否修改,可否为空, , ,修改,删除" 
				element="[1,text,onclick,showDetail][3,text,onclick,showDetail][5,checkbox,onclick,ch][6,checkbox,onclick,ch][7,checkbox,onclick,ch][10,button,onclick,modifyOneAttr,修改][11,button,onclick,deleteOneAttr,删除]"
				coltext="[col:2,text:字符,date:日期,float:小数,bool:布尔,integer:整数,textArea:文本,pic:图片,attach:附件,media:媒体,enumeration:枚举][col:4,ch:中文,en:英文,num:数字,string:字符串,all:所有字符,float:浮点]"
				page="${articleAttributeForm.pagination}"  
				form="articleAttributeForm" 
				action="articleAttribute.do"/>
		</c:if>
		<c:if test="${articleAttributeForm.formatId == 'f1'}">
			<complat:grid ids="xx"
				width="*,80,*,80,80,80,80,0,0" 
				head="属性名称,属性类型,提示信息,有效值,是否显示,可否修改,可否为空, , ," 
				element="[1,text,onclick,showDetail][3,text,onclick,showDetail][5,checkbox,onclick,ch][6,checkbox,onclick,ch][7,checkbox,onclick,ch]"
				coltext="[col:2,text:字符,date:日期,float:小数,bool:布尔,integer:整数,textArea:文本,pic:图片,attach:附件,media:媒体,enumeration:枚举][col:4,ch:中文,en:英文,num:数字,string:字符串,all:所有字符,float:浮点]"
				page="${articleAttributeForm.pagination}"  
				form="articleAttributeForm" 
				action="articleAttribute.do"/>
		</c:if>
	</form>
</body>
</html>

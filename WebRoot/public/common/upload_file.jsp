<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">	
<title>附件缩略图</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript" src="<c:url value="/script/fckeditor/editor/js/fckfilemanager.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/officecolor.js"/>"></script>

	<script type="text/javascript">
		var childNewOrgWin = "";
		var childWin = "";
		$("document").ready(function (){
			var selectValue = $("#category :selected").val();
		    $("#nodeId").val(selectValue);
		});
		
		function button_delete_onclick(ee) {
			var ids = document.getElementById("xx").value;
			if(ids == null || ids == "" || ids == "null") {
				alert("删除操作不能为空");
				return false;
			}
			var selectValue = $("#category :selected").val();
			$("#nodeId").val(selectValue);
			$("#strid").val(ids);
			if(confirm("确定要删除吗？")){
				$("#dealMethod").val("deleteAttachment");
				$("#documentForm").submit();
			}else{
				return false;
			}
		}

		// 改变select中的值并分页显示选择的类别下的值
		function change(obj) {
		    var categoryid = obj.value;	
			$("#nodeId").val(categoryid);	
			$("#documentForm").submit();
		}
		
		function button_add_onclick(ee) {
			if(document.getElementById("category").value == "") {
				alert("请先创建附件类别");
				return false;
			}
			var category = $("#category :selected").val();
			if(category.isEmpty()) {
				alert("请先选择类别");
				return false;
			}
			var articleAtta = $("#articleAtta").val();
			var isScaleImage = $("#isScaleImage").val();
			var selectValue = $("#category : selected").val();
			win = showWindow("newAttachment","上传附件","<c:url value='/module/document_manager/attachment/attachment_detail.jsp?articleAtta="+ articleAtta +"&isScaleImage="+ isScaleImage +"&nodeId="+ category +"'/>", 293, 0, 480, 450);	
		}

		function sure() {	

			// 先处理表单中的一些值 ,设置id、name、url
			var strIds = document.documentForm.attachmentIds.value;
			var arrId = strIds.split(":::");
		 	var strUrl = document.documentForm.attachmentUrl.value;
		 	var arrUrl = strUrl.split(":::");
		 	var selObj = document.documentForm.linkText;
		 	var arrIds = new Array();
			var arrNames = new Array();
			var arrUrls = new Array();
			if(selObj.length == 0) {
				alert("你没有选择任何记录");
				return false;
			} else {
				for(var i = 0; i < selObj.length; i++) {
					arrIds[i] = selObj[i].value;
					arrNames[i] = selObj[i].text;
					for(var j = 0; j < arrId.length; j++) {
						if(arrId[j] == selObj[i].value) {
							arrUrls[i] = "/${appName}" + arrUrl[j];
							break;
						}
					}
				}
			}
			
			var firstWin = top.getWin();	

			//文章管理 -->高级编辑器
			if(firstWin == null || firstWin == "undefined") {
				var articleAtta = $("#articleAtta").val();
				if(!articleAtta.isEmpty()) {
					if(arrUrls.length > 1) {
						alert("只能选择一个附件");
						return false;
					} else {
						var app = "${appName}";
						var url = arrUrls[0].substring(app.length+1, arrUrls[0].length);
						rightFrame.document.getElementById(articleAtta).value = url;
						rightFrame.closeChild();
						return null;
					}
				} else {
					var returnvalue = proccessAttachmentValue(document.documentForm, "sure", arrNames, arrUrls);
					rightFrame.fck_insertHtml(returnvalue);
					rightFrame.closeWindow(rightFrame.getWin());	
					return null;			
				} 
			}
					
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
	
			var articleAtta = $("#articleAtta").val();
			if(!articleAtta.isEmpty()) {
				if(arrUrls.length > 1) {
					alert("只能选择一个附件");
					return false;
				} else {
					rightFrame.document.getElementById(articleAtta).value = arrUrls[0];
					rightFrame.closeChild();
				}
			} else {
				var returnvalue = proccessAttachmentValue(document.documentForm, "sure", arrNames, arrUrls);	
				secondTempWindow.fck_insertHtml(returnvalue);
				top.closeWindow(secondWin);	
			//	top.window.fck_insertHtml(returnvalue);
			//	top.window.closeWin();
			}
		}
				
		function closeNewChild() {
			closeWindow(win);
		}

		function checkbox_onclick(id) {
		 	var strIds = document.documentForm.attachmentIds.value;
		 	var strNames = document.documentForm.attachmentNames.value;
		 	var strUrl = document.documentForm.attachmentUrl.value;
			var selObj = document.documentForm.linkText;
			// 在strIds中是以:::来区分的
			var arrIds = strIds.split(":::");
		    var arrNames = strNames.split(":::");
			if(selObj.length == 0) {
				selObj.length = 1;
				selObj.options[0].value = id;
	    		var i = 0;
	    		for(i = 0; i <  arrIds.length; i++) {
					if(arrIds[i] == id) {
						break;
					}
	    		}
	    		selObj.options[0].text = arrNames[i];	
			} else {
				// 判断是否是取消选择
				var k = 0;
				for(var j = 0; j < selObj.length; j++) {
					if(selObj[j].value == id) {
						// 减少值
						selObj.remove(j); 
						k = 1;
						break;
					}
				}
				if(k == 0) {
					// 增值
					selObj.length = selObj.length + 1;
					selObj.options[selObj.length-1].value = id;
					var m = 0;
		    		for(m = 0; m <  arrIds.length; m++) {
						if(arrIds[m] == id) {
							break;
						}
		    		}
					selObj.options[selObj.length-1].text = arrNames[m];	 
				} 
			}
		}
		
		function changeAttName(obj) {
			if(obj.value == null || obj.value == "") {
				return false;
			}
			var strIds = document.documentForm.attachmentIds.value;
		 	var strNames = document.documentForm.attachmentNames.value;
		 	var arrIds = strIds.split(":::");
		 	var arrNames = strNames.split(":::");
			var i = 0;
		 	for(i = 0; i < arrIds.length; i++) {
				if(obj.value == arrIds[i]) {
                	break;
				} 
		 	} 
			win = showWindow("modifyAttachment", "修改附件", "<c:url value='/public/common/modifyAttachment.jsp?selectedIndex=" + i + "&selectedValue=" + obj.value + "&attachmentName=" + encodeURI(arrNames[i]) + "'/>", 293, 0, 580, 500);
		}

		function setVal(newName, selectedIndex, selectedValue) {
			var selObj = document.documentForm.linkText;
			selObj.options[selectedIndex].value = selectedValue;
			selObj.options[selectedIndex].text = newName;
			closeChildWin();
		}
		
		function closeChildWin(){
			closeWindow(win);
		}
	</script> 

</head>
<body>
	<form id="documentForm" action="<c:url value="/attachment.do"/>" method="post" name="documentForm" >	
        <input type="hidden" name="dealMethod" id="dealMethod" value="uploadAttachmentList"/>
        <input type="hidden" name="nodeId" id="nodeId"/>
		<input type="hidden" name="message" id="message" value="${documentForm.infoMessage}"/>
        <input type="hidden" name="ids" id="strid"/>
		<input type="hidden" name="categoryId" id="categoryId" value="${documentForm.categoryId}"/>
		<input type="hidden" name="isScaleImage" id="isScaleImage" value="1"/>
		<input type="hidden" name="attachmentIds" id="attachmentIds" value="${documentForm.attachmentIds }" />
		<input type="hidden" name="attachmentNames" id="attachmentNames" value="${documentForm.attachmentNames }" />
		<input type="hidden" name="attachmentUrl" id="attachmentUrl" value="${documentForm.attachmentUrl }" />
		<input type="hidden" name="articleAtta" id="articleAtta" value="${documentForm.articleAtta}"/>
		
		   <div id="Layer2" style="position:absolute;  overflow: scroll;  left:4px; top:10px; width:642px; height:520px; ">
				<complat:button  name="button" buttonlist="add|0|delete" buttonalign="left"/>
				选择类别    
				<select id="category" style="width: 150px;" onChange="change(this)" class="input_select_normal">
					<c:forEach items="${documentForm.list}" var="list">
						<c:if test="${list.id == documentForm.categoryId}">
							<option value="${list.id}" selected="true">${list.name}</option>
						</c:if>
						<c:if test="${list.id != documentForm.categoryId}">
							<option value="${list.id}">${list.name}</option>
						</c:if>
					</c:forEach> 
				</select> 
				<complat:grid ids="xx" width="*,*" head="附件名称,附件类型" page="${documentForm.pagination}" form="documentForm" action="attachment.do"/>
		    </div>
			<div id="Layer3" style="position:absolute; left:655px; top:11px; width:236px; height:390px; z-index:1">
				<fieldset style="height:335"><legend>&nbsp;<font color=blue style="color:blue;font:12px;font-weight:bold">附件属性</font>&nbsp;</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" align='center'>
					<tr> 
					  <td>对齐方式</td><td> 
						<select name="align" style="width:160px;" class="input_select_normal">
						  <option value="left" selected>左对齐</option>
						  <option value="center">居中</option>
						  <option value="right">右对齐</option>
						</select>
					  </td>
					 </tr><tr>
					  <td><br>已选附件</td><td>&nbsp;</td>
					</tr>	
					<tr>
					<td align="right" width="60"></td>
					<td colspan="2"> 
					    <select style="width:160px;height:240px;" class="input_select_normal"  size="20" id="linkText" name="linkText" ondblclick="changeAttName(this)">
						</select>
						<input type="hidden" name="hyperlink" id="hyperlink">
					</td>
					</tr>
					<tr>
						<td align="right" width="60"></td>
						<td><input type="button"  class="btn_normal" value="确 定" onclick="sure()"></td>
					</tr>					
					</table>
				</fieldset>
			</div>
		</div>
	</form>
	<iframe src="" name="delhidden" id="delhidden" width="0" height="0"></iframe>
</body>
</html>
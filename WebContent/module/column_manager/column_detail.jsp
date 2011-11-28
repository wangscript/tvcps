<%@page language="java" contentType="text/html; charset=utf-8" import="com.baize.common.core.util.StringUtil"%>
<html>
<head>
<title>新增栏目</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		$("#columnName").focus();
		var msgVal = $("#message").val();
		if(!msgVal.isEmpty()){
			if(msgVal != "增加栏目成功" && msgVal != "修改栏目成功" && msgVal != "栏目导入成功" && msgVal != "粘贴成功" && msgVal != "栏目排序成功") {
				alert(msgVal);
			} 
			top.reloadAccordion("/${appName}/module/column_manager/refresh_Tree.jsp?" + getUrlSuffixRandom());
			rightFrame.window.location.href="<c:url value='/column.do?dealMethod=&nodeId=${columnForm.nodeId}&operationType=column&localNodeName=${columnForm.localNodeName}&" + getUrlSuffixRandom() + "'/>";
			closeWindow(rightFrame.getWin());
		}

		var columnId = $("#columnId").val();
		if(columnId != "" && columnId != 0) {
			$("#articleId").attr("readonly", true);
		}

		var publishWay = $("#publishWay").val();
		if(publishWay == "2") {
		//	document.getElementById("valid1").style.display = "none";
			document.getElementById("valid2").style.display = "";
		} else {
		//	document.getElementById("valid1").style.display = "";
			document.getElementById("valid2").style.display = "none";
		}
		
	});
	
	function checkValue() {
		var articleId = $("#articleId :selected").val();
		if($("#columnName").val().isEmpty() || $("#columnName").val().trim().isEmpty()) {
			alert("请输入栏目名称");
			return false;
		} else {
			/*if(articleId.isEmpty()) {
				alert("栏目必须绑定文章格式");
				return false;
			}*/
			var separator = document.getElementById("separator").value;
			var name = $("#columnName").val();
			if(!separator.trim().isEmpty()){
				var arr = name.split(separator);
				for(var i = 0; i < arr.length; i++) {
					if(arr[i].trim().isEmpty()){
						alert("分隔的栏目中不能有空栏目");
						return false;
					}
				}
			}
			if(separator == "" || separator == null || separator == "null" || separator.trim() == "") {
				document.getElementById("separator").value = "";
			}
        	$("#dealMethod").val("add");
    		$("#columnForm").submit();
		}   
	}
	function changeValue() {
		document.all("dealMethod").value = "modify";
		if(document.getElementById("columnName").value == null || document.getElementById("columnName").value == "" || document.getElementById("columnName").value.trim() == ""){
			alert("栏目名称不能为空");
			return false;
		} else {
			$("#columnForm").submit();
		}
	} 

	function chooseParentColumn(ee) {
		var localNodeName = $("#localNodeName").val();
		win = showWindow("chosecolumn", "选择上级栏目", "<c:url value='/column.do?dealMethod=checkColumn&nodeId=${columnForm.nodeId}&columnId=${columnForm.column.id}&localNodeName=" + localNodeName + "'/>", 0, 0, 370, 500);
	}

	function setParent(pid, pname) {
		$("#parentid").val(pid);
		$("#pname").val(pname);
	}

	function selRefColumn() {
		var articleFormatId = document.getElementById("articleFormatId").value;
		var columnId = document.getElementById("columnId").value;
		win = showWindow("presentArticle", "选择同步发送栏目", "<c:url value='/column.do?dealMethod=findRefColumn&articleFormatId="+ articleFormatId + "&columnId=" + columnId +"'/>", 0, 0, 540, 520);
	}
	
	function closeChild() {
		closeWindow(win);
	}

	function backColumnList() {
		var url = '<c:url value="/column.do?dealMethod=&nodeId=${columnForm.nodeId}&localNodeName=${columnForm.localNodeName}&operationType=column"/>';
		parent.changeFrameUrl("rightFrame", url);
	}

	function setDefault() {
		var initUrl = document.getElementById("initUrl").value;
		$("#url").val(initUrl);
	}

	function changeFormat() {
		var refColumn = document.getElementById("columnNames").value;
		if(refColumn != null && refColumn != "") {
			alert("文章格式已改变,请重新选取同步发送栏目");
			$("#columnNames").val("");
			$("#refColumnIds").val("");
		}
		return false;
	}

	function clearRefColumn() {
		$("#columnNames").val("");
		$("#refColumnIds").val("");
	}

	function changePubWay(obj) {
		$("#publishWay").val(obj.value);
		if(obj.value == "2") {
		//	document.getElementById("valid1").style.display = "none";
			document.getElementById("valid2").style.display = "";
		} else {
		//	document.getElementById("valid1").style.display = "";
			document.getElementById("valid2").style.display = "none";
		}
	}
	
</script>

</head>
<body>
<div class="currLocation">栏目管理</div> 
<form action="<c:url value="/column.do"/>" method="post" name="columnForm" id="columnForm" >
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="ids" id="strids" />
	<input type="hidden" name="message" id="message" value="${columnForm.infoMessage}" />
	<input type="hidden" name="nodeId" id="nodeId" value="${columnForm.nodeId}"/>
	<input type="hidden" name="localNodeName" id="localNodeName" value="${columnForm.parentName}"/>
	<input type="hidden" name="parentid" id="parentid" value="${columnForm.column.parent.id}" />
	<input type="hidden" name="column.id" id="columnId" value="${columnForm.column.id }"/>
	<input type="hidden" name="columnId" id="columnid" value="${columnForm.column.id }"/>
    <input type="hidden" name="modifyparentid" id="modifyparentid" value="${columnForm.column.parent.id }"/>
	<input type="hidden" name="operationType" value="column" />
	<input type="hidden" name="column.publishWay" id="publishWay" value="${columnForm.column.publishWay}"/>
	
	<div class="form_div">
	<table style="width:950px; font:12px;" >
		<tr>
			<td class="td_left" width="120px;"> 
				<i>*</i>栏目名称：
			</td>
			<c:if test="${columnForm.isAddColumn != 0}">
				<td>
					<input type="text" name="column.name" id="columnName" class="input_text_normal" style="width:400px;"   value="${columnForm.column.name}" valid="string" tip="栏目名称不能为空" />
					<i>　</i>分隔符：
					<input type="text" name="separator" id="separator" class="input_text_normal" valid="string" empty="true" value="#"  style="width:40px;" />
				</td>
			</c:if>
			<c:if test="${columnForm.isAddColumn == 0}">
				<td>
					<input type="text" name="column.name" id="columnName" class="input_text_normal" style="width:514px;"   value="${columnForm.column.name}" valid="string" tip="栏目名称不能为空" />
				</td>
			</c:if> 
		</tr> 
		<tr>
			<td class="td_left" width="120px;"> 链接地址：</td>
			<td>
				<input type="text" name="column.url" id="url" class="input_text_normal" style="width:514px;" empty="true"  value="${columnForm.column.url}" valid="string"  />
				<c:if test="${columnForm.isAddColumn == 0}">
					<input type="button" value="恢复默认" class="btn_small" onClick="setDefault()"/>
					<input type="hidden" name="column.initUrl" id="initUrl" class="input_text_normal" style="width:514px;"   value="${columnForm.column.initUrl}" />
				</c:if>
			</td>
		</tr>
		<c:if test="${columnForm.isAddColumn == 0}">
		<tr>	 
			<td class="td_left" width="120px;">
				<i>&nbsp;</i>上级栏目：
			</td>
			<td> 
				<c:if test="${columnForm.nodeId != null}">
					<input type="text" name="pname" id="pname" readonly class="input_text_normal" style="width:514px;" value="${columnForm.parentName }" />
				</c:if>
				<c:if test="${columnForm.nodeId == null}">
					<input type="text" name="pname" readonly id="pname" class="input_text_normal" style="width:514px;" />
				</c:if>
				<input type="button" value="选择" class="btn_small" onClick="chooseParentColumn()"/>
			</td>
		</tr> 
		</c:if> 
	<!-- <tr>
			<td class="td_left" width="120px;">
			 	<i>&nbsp;</i>自我展示页：
			</td>
			<td>
				<input type="text" name="column.selfShowPage" id="column.selfShowPage" class="input_text_normal" style="width:514px" empty="true" valid="string" value="${columnForm.column.selfShowPage}"/>
			</td>
		</tr>
	 -->	
		<tr>
			<td class="td_left" width="120px;">
			 	积分方式：
			</td>
			<c:if test="${columnForm.isAddColumn != 0}">
				<td colspan="3">&nbsp;&nbsp;
					默认信息积分（分）：
				 	<input type="text" name="column.infoScore" id="nfoScore" class="input_text_normal" style="width:40px" value="1"/>
					<i>&nbsp;&nbsp;&nbsp;&nbsp;</i>默认图片积分（分）：<input type="text" name="column.picScore" id="picScore" class="input_text_normal" style="width:40px" value="0"/>
				</td>
			</c:if>
			<c:if test="${columnForm.isAddColumn == 0}">
				<td colspan="3">&nbsp;&nbsp;
					默认信息积分（分）：
				 	<input type="text" name="column.infoScore" id="nfoScore" class="input_text_normal" style="width:40px" value="${columnForm.column.infoScore}"/>
					<i>&nbsp;&nbsp;&nbsp;&nbsp;</i>默认图片积分（分）：<input type="text" name="column.picScore" id="picScore" class="input_text_normal" style="width:40px" value="${columnForm.column.picScore}"/>
				</td>
			</c:if>
		</tr>
		<tr>
			<td class="td_left" width="120px;">
			 		<i>*</i>前台显示：
			</td>
			<td>
				<c:if test="${columnForm.column.showInFront == true}">
					<input type="radio" name="column.showInFront" value="1" checked/>是
					<input type="radio" name="column.showInFront" value="0" />否
				</c:if>
				<c:if test="${columnForm.column.showInFront == false}">
					<input type="radio" name="column.showInFront" value="1" />是
					<input type="radio" name="column.showInFront" value="0" checked />否
				</c:if>
			</td>
		</tr>			
		<tr>
			<td class="td_left" width="120px;">
                <i>*</i>审&nbsp;&nbsp;&nbsp;&nbsp;核：
			</td>
			<td>
			<c:if test="${columnForm.column.audited == true}">
				<input type="radio" name="column.audited" value="1" checked/>是
				<input type="radio" name="column.audited" value="0" />否
			</c:if>
			<c:if test="${columnForm.column.audited == false}">
				<input type="radio" name="column.audited" value="1" />是
				<input type="radio" name="column.audited" value="0" checked/>否
			</c:if>
			</td>
		</tr>
		<tr>
			<td class="td_left" width="120px;">
                <i>*</i>栏目类型：
			</td>
			<td>
				<select name="column.columnType"  class="input_select_normal" style="width:160px;">
					<c:if test="${columnForm.isAddColumn == 0}">
						<c:if test="${columnForm.column.columnType == 'multi'}">
							<option value="multi" selected>多信息栏目</option>
							<option value="single">单信息栏目</option>
						</c:if> 
						<c:if test="${columnForm.column.columnType == 'single'}">
							<option value="multi" >多信息栏目</option>
							<option value="single" selected>单信息栏目</option>
						</c:if>
					</c:if>
					<c:if test="${columnForm.isAddColumn != 0}"> 
						<option value="multi" selected>多信息栏目</option>
						<option value="single">单信息栏目</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left" width="120px;">
				<i>*</i>文章格式：
			</td> 
			<td> 
			<c:if test="${columnForm.column.id != null and columnForm.column.id != '0' and columnForm.column.id != ''}">
				<c:if test="${columnForm.hasArticle == 1}">
				<select name="articlehidden"  class="input_select_normal" style="width:160px;" disabled>
					<c:forEach var="format" items="${columnForm.articleFormats}">
						<c:choose>
							<c:when test="${columnForm.column.articleFormat.id == format.id}">
								<option value="${format.id}" selected="selected">${format.name}</option>
							</c:when>
							<c:otherwise>
								<c:if test="${columnForm.column.articleFormat.id == null}">
									<c:if test="${format.name eq '默认格式'}"> 
										<option value="${format.id}" selected>${format.name}</option>
									</c:if>
									<c:if test="${format.name != '默认格式'}">
										<option value="${format.id}">${format.name}</option>
									</c:if>
								</c:if>
								<c:if test="${columnForm.column.articleFormat.id != null}">
									<option value="${format.id}">${format.name}</option>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<span style="display:none">
				<select name="articleFormatId" id="articleId">
					<c:forEach var="format" items="${columnForm.articleFormats}">
						<c:choose>
							<c:when test="${columnForm.column.articleFormat.id == format.id}">
								<option value="${format.id}" selected="selected">${format.name}</option>
							</c:when>
							<c:otherwise>
								<c:if test="${columnForm.column.articleFormat.id == null}">
									<c:if test="${format.name eq '默认格式'}"> 
										<option value="${format.id}" selected>${format.name}</option>
									</c:if>
									<c:if test="${format.name != '默认格式'}">
										<option value="${format.id}">${format.name}</option>
									</c:if>
								</c:if>
								<c:if test="${columnForm.column.articleFormat.id != null}">
									<option value="${format.id}">${format.name}</option>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				</span>
				</c:if>
				<c:if test="${columnForm.hasArticle != 1}">
				<select name="articleFormatId"  class="input_select_normal" style="width:160px;" onchange="changeFormat()">
					<c:forEach var="format" items="${columnForm.articleFormats}">
						<c:choose>
							<c:when test="${columnForm.column.articleFormat.id == format.id}">
								<option value="${format.id}" selected="selected">${format.name}</option>
							</c:when>
							<c:otherwise>
								<c:if test="${columnForm.column.articleFormat.id == null}">
									<c:if test="${format.name eq '默认格式'}"> 
										<option value="${format.id}" selected>${format.name}</option>
									</c:if>
									<c:if test="${format.name != '默认格式'}">
										<option value="${format.id}">${format.name}</option>
									</c:if>
								</c:if>
								<c:if test="${columnForm.column.articleFormat.id != null}">
									<option value="${format.id}">${format.name}</option>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				</c:if>
			</c:if>
			<c:if test="${columnForm.column.id == null or columnForm.column.id == '0' or columnForm.column.id == ''}">
				<select name="articleFormatId" id="articleId"  class="input_select_normal" style="width:160px;" onchange="changeFormat()">
					<c:forEach var="format" items="${columnForm.articleFormats}">
						<c:choose>
							<c:when test="${columnForm.column.articleFormat.id == format.id}">
								<option value="${format.id}" selected="selected">${format.name}</option>
							</c:when>
							<c:otherwise>
								<c:if test="${columnForm.column.articleFormat.id == null}">
									<c:if test="${format.name eq '默认格式'}"> 
										<option value="${format.id}" selected>${format.name}</option>
									</c:if>
									<c:if test="${format.name != '默认格式'}">
										<option value="${format.id}">${format.name}</option>
									</c:if>
								</c:if>
								<c:if test="${columnForm.column.articleFormat.id != null}">
									<option value="${format.id}">${format.name}</option>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</c:if>
			</td>
		</tr>
		<tr>
			<td class="td_left" width="120px;">  
				<i>&nbsp;</i>更新时间：
			</td> 
			<td>  
				<input type="text" name="updateTime" id="updateTime" readonly  class="Wdate" style="width:160px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${columnForm.updateTime}"/>
			</td> 
		</tr>
		
		<tr>
			<td class="td_left" width="120px;">  
				<i>&nbsp;</i>发布方式：
			</td> 
			<td>  
				
				<select name="pubWay" id="pubWay"  class="input_select_normal" style="width:160px;" onchange="changePubWay(this)">
					<c:if test="${columnForm.isAddColumn != 0}">
						<option value="0" selected>手动发布</option>
						<option value="1">自动发布</option>
						<option value="2">定时发布</option>
					</c:if>
					
					<c:if test="${columnForm.isAddColumn == 0}">
						<c:if test="${columnForm.column.publishWay == 0}">
						<option value="0" selected>手动发布</option>
						<option value="1">自动发布</option>
						<option value="2">定时发布</option>
						</c:if>
						<c:if test="${columnForm.column.publishWay == 1}">
							<option value="0">手动发布</option>
							<option value="1" selected>自动发布</option>
							<option value="2">定时发布</option>
						</c:if>
						<c:if test="${columnForm.column.publishWay == 2}">
							<option value="0">手动发布</option>
							<option value="1">自动发布</option>
							<option value="2" selected>定时发布</option>
						</c:if>
					</c:if>
				</select>
				
				<a id="valid2" style="display:none">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时间设定：
					<input type="text" name="column.timeSetting" style="width:50px;" id="timeSetting" value="${columnForm.column.timeSetting}"/>(秒)
				</a>

			</td> 
		</tr>

		<tr>
			<td class="td_left" width="120px;">  
				<i>&nbsp;</i>同步接收：
			</td>
			<td>
				
				
				<i>&nbsp;&nbsp;&nbsp;</i>是否启用：
				<c:if test="${columnForm.column.receiveMenu == true}">
					<input type="radio" name="column.receiveMenu" value="1" checked/>是
					<input type="radio" name="column.receiveMenu" value="0" />否
				</c:if>
				<c:if test="${columnForm.column.receiveMenu == false}">
					<input type="radio" name="column.receiveMenu" value="1" />是
					<input type="radio" name="column.receiveMenu" value="0" checked />否
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="td_left" width="120px;">  
				<i>&nbsp;</i>同步发送：
			</td>
			<td>	
				<i>&nbsp;&nbsp;&nbsp;</i>是否启用：
				<c:if test="${columnForm.column.sendMenu == true}">
					<input type="radio" name="column.sendMenu" value="1" checked/>是
					<input type="radio" name="column.sendMenu" value="0" />否
				</c:if>
				<c:if test="${columnForm.column.sendMenu == false}">
					<input type="radio" name="column.sendMenu" value="1" />是
					<input type="radio" name="column.sendMenu" value="0" checked />否
				</c:if>

				<i>&nbsp;&nbsp;&nbsp;&nbsp;</i>允许修改：
				<c:if test="${columnForm.column.allowModify == true}">
					<input type="radio" name="column.allowModify" value="1" checked/>是
					<input type="radio" name="column.allowModify" value="0" />否
				</c:if>
				<c:if test="${columnForm.column.allowModify == false}">
					<input type="radio" name="column.allowModify" value="1" />是
					<input type="radio" name="column.allowModify" value="0" checked />否
				</c:if>
			</td>
		</tr>
		<tr id="refcolumns" style="display: ">
			<td class="td_left" width="120px;">  
				<i>　　　　</i>
			</td>
			<td colspan="5">
				
				<input type="text" class="input_text_normal" readonly style="width:485px;" name="columnNames" id="columnNames" value="${columnForm.columnNames}"/>
				<input type="hidden" name="column.refColumnIds" id="refColumnIds" value="${columnForm.column.refColumnIds}"/>
				<input type="button" value="发送栏目" class="btn_small" name="buttonRefCol" onclick="selRefColumn();"/>&nbsp;&nbsp;&nbsp;
				<input type="button" value="清空" class="btn_small" name="clearRefCol" onclick="clearRefColumn();"/>
			</td>
		</tr>
		
		<tr>
			<td class="td_left" width="120px;">
				<i>&nbsp;</i>栏目描述：
			</td> 
			<td height="160">
				<textarea id="column.description" class="input_textarea_normal" style="width:680px; height:230px;"  name="column.description">${columnForm.column.description }</textarea>				
			</td>
		</tr>

		<tr>	
			<td width="120px;"></td>
			<td><center>
				<c:if test="${columnForm.isAddColumn == 1}">
					<input type="button" name="saveValue" class="btn_normal" value="保存" onClick="checkValue()"/>
				</c:if>
 				<c:if test="${columnForm.isAddColumn == 0}">
					<input type="button"  value="修改" class="btn_normal" onClick="changeValue()"/>
				</c:if>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="reset" name="resetValue"  class="btn_normal" value="重置"/>
				&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="btn_normal" onclick="backColumnList()" value="返回"/></center>
			</td>
		</tr>
	</table>
	</div>
</form>
</body>
</html>
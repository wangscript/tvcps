<%@page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>新增作者</title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
	$(document).ready(function() {					
		var categoryid = document.getElementById("categoryId").value;
		if (document.getElementById("defaultSet").value == "true") {
			document.getElementById("defaultOne").checked = true;
		} else {
			document.getElementById("defaultTwo").checked = true;
		}
		$("#authorName").focus();
		if (document.getElementById("message").value == "4") {
			//是否覆盖之前默认启动
			if (confirm("是否启用默认设置？")) {
				document.getElementById("overDefault").value = "overDefault";
				$("#generalSystemSetForm").submit();
			}
		}
		if (document.getElementById("message").value == "5") {
			//是否覆盖之前默认启动
			if (confirm("是否覆盖已启用的作者？")) {
				document.getElementById("overDefault").value = "overDefault";
				document.getElementById("message").value = "4";
				$("#generalSystemSetForm").submit();
			}
		}
		if (document.getElementById("message").value == "6") {
			//是否覆盖之前默认启动
			document.getElementById("overDefault").value = "overDefault";
			document.getElementById("message").value = "4";
			$("#generalSystemSetForm").submit();
		}
		if (document.getElementById("message").value == "2") {
			if (confirm("是否覆盖已启用的作者？")) {
				document.getElementById("overDefault").value = "overDefault";
				$("#generalSystemSetForm").submit();
			}
		}
		if (document.getElementById("message").value == "3") {
			rightFrame.window.location.href = "<c:url value='/author.do?dealMethod=&categoryId="
					+ categoryid
					+ "&setContent="
					+ $("#authorName").val()
					+ "&"
					+ getUrlSuffixRandom()
					+ "'/>";
			closeWindow(rightFrame.getWin());
		}
		if (document.getElementById("message").value == "0") {
			rightFrame.window.location.href = "<c:url value='/author.do?dealMethod=&categoryId="
					+ categoryid
					+ "&"
					+ getUrlSuffixRandom()
					+ "'/>";
			closeWindow(rightFrame.getWin());
		}
		if (document.getElementById("message").value == "1") {
			alert("作者名不能重复！");
			rightFrame.window.location.href = "<c:url value='/author.do?dealMethod=&categoryId="
					+ categoryid
					+ "&"
					+ getUrlSuffixRandom()
					+ "'/>";
			closeWindow(rightFrame.getWin());
		}
		var columnId = $("#columnId").val();
		if (columnId != "" && columnId != 0) {
			$("#articleId").attr("readonly", true);
		}
	});

	
	function back() {
		rightFrame.closeNewChild();
	}

	function save() {
		var name = $("#authorName").val();
		var separator = $("#separator").val();
		if(!separator.isEmpty()) {
			if(name.split(separator).length > 1) {
				if($("#defaultOne").attr("checked")) {
					alert("添加多个作者时不能启用默认设置");
					return false;
				}
			}
		}
		$("#generalSystemSetForm").submit();
	}

	function update() {
		$("#generalSystemSetForm").submit();
	}
</script>
</head>


<form action="author.do" method="post" name="generalSystemSetForm" id="generalSystemSetForm">
	<c:if test="${generalSystemSetForm.generalSystemSet.setContent== null}">
		<input type="hidden" name="dealMethod" id="dealMethod" value="add" />
	</c:if>
	<c:if test="${generalSystemSetForm.infoMessage==2}">
		<input type="hidden" name="dealMethod" id="dealMethod" value="add" />
	</c:if> 
	<c:if test="${generalSystemSetForm.infoMessage!=2}">
		<c:if test="${generalSystemSetForm.generalSystemSet.setContent!= null}">
			<input type="hidden" name="dealMethod" id="dealMethod" value="update" />
		</c:if>
	</c:if> 
	<input type="hidden" name="message" id="message" value="${generalSystemSetForm.infoMessage}" /> 
	<input type="hidden" name="generalSystemSet.id" id="id"	value="${generalSystemSetForm.generalSystemSet.id}" /> 
	<input type="hidden" name="generalSystemSet.createTime" id="createTime"	value="${generalSystemSetForm.generalSystemSet.createTime}" /> 
	<input type="hidden" name="categoryId" id="categoryId"	value="${generalSystemSetForm.categoryId}" /> 
	<input type="hidden" name="defaultSet" id="defaultSet"	value="${generalSystemSetForm.generalSystemSet.defaultSet}" /> 
	<input type="hidden" name="overDefault" id="overDefault" value="${generalSystemSetForm.overDefault}" />
	<div class="form_div">
	<table style="width: 770px;; font: 12px;">
	<tr>
		<td class="td_left" width="90px;"><i>*</i>作者名称：</td>
		<td>
			<input type="text" name="generalSystemSet.setContent" id="authorName" class="input_text_normal" style="width: 230px;"
			value="${generalSystemSetForm.generalSystemSet.setContent}"	valid="string" tip="作者名不能为空" />
		</td>
	</tr>
	<tr>
		<c:if test="${generalSystemSetForm.generalSystemSet.setContent== null}">
			<td class="td_left" width="90px;"><i></i>分隔符：</td>
			<td>
				<input type="text" name="separator" id="separator"	class="input_text_normal" valid="string" empty="true" style="width: 30px;" />
			</td>
			<td></td>
		</c:if>
	</tr>
	<tr>
		<td class="td_left" width="90px;"><i>*</i>默认设置：</td>
		<td>
			<input type="radio" name="generalSystemSet.defaultSet" id="defaultOne" value="1" />是 
			<input type="radio" name="generalSystemSet.defaultSet" id="defaultTwo" value="0" />否
		</td>
	</tr>
	<c:if test="${generalSystemSetForm.generalSystemSet.setContent== null}">
	<tr>		
		<td colspan="2">
			<span style="margin:0 0 0 25px;"><font color="red">*&nbsp;注:一次添加多个作者时不能启用默认设置</font></span>
		</td>
	</tr>
	</c:if>
	<tr height="50px">
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<td>
	</tr>
	<tr hegight="200px">
		<td width="90px;"></td>
		<td>
			<c:if test="${generalSystemSetForm.generalSystemSet.setContent== null}">
				<input type="button" name="saveValue" class="btn_normal" onclick="save()" value="保存" />
			</c:if> 
			<c:if test="${generalSystemSetForm.infoMessage==2}">
				<input type="button" name="saveValue" class="btn_normal" onclick="save()" value="保存" />
			</c:if> 
			&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 
			<c:if test="${generalSystemSetForm.infoMessage!=2}">
				<c:if test="${generalSystemSetForm.generalSystemSet.setContent!= null}">
					<c:if test="${generalSystemSetForm.infoMessage!=4}">
						<input type="button" name="saveValue" class="btn_normal" onclick="update()" value="修改" />
					</c:if>
				</c:if>
			</c:if> 
			<c:if test="${generalSystemSetForm.infoMessage==4}">
				<input type="button" name="saveValue" class="btn_normal" onclick="update()" value="修改" />
			</c:if> 
			&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 
			<input type="button" class="btn_normal" onclick="back()" value="退出" />
		</td>
	</tr>
	</table>
</div>
</form>
</body>
</html>
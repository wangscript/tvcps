<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<title>文档管理中的图片中的详细管理</title>
<style type="text/css" media="all">
</style>
<script type="text/javascript">
	$( function loadDefault() {
		//页面加载时将下拉列表选中
	//	var picName = $("#picNameId").val();
		var fontName =$("#fontNameId").val();
			//选中字体列表的值
			$.each($("#picNameSelect option"), function(i, n){
				if (n.value == fontName) {
						document.waterMarkForm.radCheck[1].checked=true;
					$(n).attr("selected", "selected");
				}
			});
	
			//选中字体列表的值
			$.each($("#fontNameIdselect option"), function(i, n){
				if (n.value == fontName) {
					document.waterMarkForm.radCheck[0].checked=true;
					$(n).attr("selected", "selected");
				}
			});
			
	});

	//保存提交
	function saveDefault() {
				//选中文字水印
				if (document.waterMarkForm.radCheck[0].checked == true){
					var r=$("#waterType1").val();//获取文字选项的值0
					$("#selectRad").val(r); //赋值给selectRad
					var fontwater =$("#fontNameIdselect").val(); //获取选项的值
					$("#deFid").val(fontwater); //赋值给deFid
					document.waterMarkForm.action="<c:url value='/waterMark.do?dealMethod=savedefaulted&check=0' />";
				}
				//选中图片水印
				if (document.waterMarkForm.radCheck[1].checked == true){
					
					var r=$("#waterType2").val();  //获取图片选项的值1
					$("#selectRad").val(r); //赋值给selectRad
					var picwater =$("#picNameSelect").val();//获取选项的值
					$("#deFid").val(picwater);//赋值给deFid
					document.waterMarkForm.action="<c:url value='/waterMark.do?dealMethod=savedefaulted&check=1' />";
				}
		}
</script>
<body>
<div class="currLocation">系统设置→ 网站水印设置→ 默认设置</div>
<form id="waterMarkForm" method="post" name="waterMarkForm">
<!-- 隐藏字段 -->
													<!--fontNameId 用于存储ID，去匹配下拉列表，并选中  -->
<input type="hidden" name="watermark.id" id="fontNameId" value="${waterMarkForm.watermark.id}" />
													<!--fontNameId 修改时要传的ID  -->
<input type="hidden" name="ids" id="deFid" value="${waterMarkForm.watermark.id}"/>
													<!--单选按钮，0为文字水印，1为图片水印  -->
<input type="hidden" name="selectOption" id="selectRad" value="0"/>
							
<input type="hidden" name="dealMethod" id="dealMethod" value="savedefaulted" />
<!-- 隐藏字段结束 -->
<fieldset><legend>默认水印</legend>
	<table border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td height="24">
				<input type="radio" name="radCheck"	id="waterType1" value="0" />
			</td>
			<td height="24">文字水印</td>
			<td height="24">
					<select name="fontName" id="fontNameIdselect"style="width: 200px"
						class="input_select">
								<option value="" selected="selected">————请选择类别————</option>
							<c:forEach items="${waterMarkForm.listWaterFont}" var="TetWater">
								<option value="${TetWater.id}">${TetWater.name}</option>
							</c:forEach> 
					</select>
			</td>
		</tr>
		<tr>
			<td height="24">
				<input type="radio" name="radCheck"	id="waterType2" value="1" />
			</td>
			<td height="24">图片水印</td>
			<td height="24">
				<select name="picN" style="width: 200px"class="input_select" id="picNameSelect">
						<option value="" selected="selected">————请选择类别————</option>
					<c:forEach items="${waterMarkForm.listPicPath}" var="picWater">
							<option value="${picWater.id}">${picWater.name}</option>
					</c:forEach> 
				</select>
			</td>
		</tr>
	</table>
</fieldset>
<br />
<input class='btn_ok' type='submit' onclick="saveDefault();" value="保存设置" /></form>
</body>
</html>



<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<title>文档管理中的图片中的详细管理</title>
<style type="text/css" media="all">
.up {
	background-color: #ffffff;
}

.down {
	background-color: #5B69A6;
}
</style>

<script type="text/javascript">
	//btnClick(obj)函数用于设置按钮的样式和显示的位置
	function btnClick(obj) {
		for ( var i = 1; i <= 9; i++) {
			document.getElementById("bt" + i).className = "up";
		}
		obj.className = "down";
		var arr = ["左上","上","右上","左","中","右","左下","下","右下"];
		var index = obj.id.substring(2,3);
		document.getElementById("curposition").innerText = arr[parseInt(index,10)-1];
		document.getElementById("btnPos").value = obj.id;//获取9个方向按钮选中的值

	}

	//保存编辑的文字
	function savePicture() {
		var waterfont = $("#writerFont").val();
		if (waterfont.trim() == "") {
			alert("请输入要编辑的文字");
			return false;
			}
		var selectFontId = $("#fontStyle").val();
		document.getElementById("selectFontids").value=selectFontId;
		$("#dealMethod").val("editWaterFont");
		var options = {	 	
		    		url: "<c:url value='/waterMark.do'/>",
		   		 success: function(msg) {
				var arr = msg.split("##");
				top.document.getElementById("rightFrame").src = "<c:url value='/waterMark.do?dealMethod=textWater&'/>"+getUrlSuffixRandom();
				closeWindow(rightFrame.getWin());		
		    } 
		};
		$('#waterMarkForm').ajaxSubmit(options);
	}
	
	$( function loadJSP() {
		$("#writerFont").focus();
		var fontstyle = $("#selectFontids").val();
		var imgPos = $("#btnPos").val();
			$.each($("#fontStyle option"), function(i, n){//ID遍历
				if (n.value == fontstyle) {
					$(n).attr("selected", "selected");
				}
			});	
			//页面加载时选中的方向安扭
			var imgpos=document.getElementById("btnPos").value;
			 if(imgpos!="null"&&imgpos!=""){
					for(var i=1;i<=9;i++){
						if(document.getElementById("bt"+i).id==imgpos){
				       	 document.getElementById("bt"+i).className = "down";
						}
				    }
				    //用文字显示方向
					var index = imgpos.substring(2,3);
					var arr = ["左上","上","右上","左","中","右","左下","下","右下"];
				    document.getElementById("curposition").innerText = arr[parseInt(index,10)-1];
					 }else{
						 document.getElementById("bt9").className = "down";
						 document.getElementById("curposition").innerText="右下";
					}
			
	});
	function checkNum(){ 
	      if(event.keyCode<48||event.keyCode>57){     
	          event.returnValue=false;        
	  }   
	}
</script>
<body>
<script type="text/javascript"
	src="<c:url value="/script/officecolor.js"/>"></script>
<form action="<c:url value="/waterMark.do"/>" method="POST"
	name="waterMarkForm">
<fieldset><legend>水印文字</legend> 
<!-- 隐含变量开始 --> 

<input type="hidden"	name="dealMethod" id="dealMethod" value="editWaterFont"/> 
<input type="hidden"	name="textwatermark.id" id="id" value="${waterMarkForm.textwatermark.id}"/>
<input type="hidden"	name="textwatermark.position" id="btnPos" value="${waterMarkForm.textwatermark.position}" />
<input type="hidden" name="textwatermark.font" id="selectFontids"	value="${waterMarkForm.textwatermark.font}" /> 


	<!-- 隐含变量结束 -->
<table width="100%" border="0" cellspacing="1" cellpadding="0"
	align="center">
	<tr>
		<td><input type="text" name="textwatermark.name"  id="writerFont"
			value="${waterMarkForm.textwatermark.name}" size="40"
			style="width: 280px"></td>
	</tr>
</table>
</fieldset>
<br />
<fieldset><legend>水印选项</legend>
<table width="100%" border="0" cellspacing="1" cellpadding="3"
	align="center">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align='right'>水印位置：</td>
			<td>
			<table id="position" border="0" cellspacing="1" cellpadding="0">
				<tr>
						<td colspan="3" height="20" style="color: red;">位置：<span
							id="curposition"></span>
						</td>
				</tr>
				<tr>
					<td><input type="button" value="↖" class="up" id="bt1" name="btn1"
						onclick="btnClick(this)" /></td>
					<td><input type="button" value="↑" id="bt2" class="up" name="btn2"
						onclick="btnClick(this)" /></td>
					<td><input type="button" value="↗" id="bt3" class="up" name="btn3"
						onclick="btnClick(this)" /></td>
				</tr>
				<tr>
					<td><input type="button" value="←" id="bt4" class="up" name="btn4"
						onclick="btnClick(this)" /></td>
					<td><input type="button" value="•" id="bt5" class="up" name="btn5"
						onclick="btnClick(this)" /></td>
					<td><input type="button" value="→" id="bt6" class="up" name="btn6"
						onclick="btnClick(this)" /></td>
				</tr>
				<tr>
					<td><input type="button" value="↙" id="bt7" class="up" name="btn7"
						onclick="btnClick(this)" /></td>
					<td><input type="button" value="↓" id="bt8" class="up" name="btn8"
						onclick="btnClick(this)" /></td>
					<td><input type="button" value="↘" id="bt9" class="up" name="btn9"
						onclick="btnClick(this)" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td align='right'>水印边距：</td>
			<td><input class="input_text" type="text" name="textwatermark.boder"
				id="i_padding" size="7" maxlength="2" onkeypress="checkNum();"
				value="${waterMarkForm.textwatermark.boder}" /> px</td>
		</tr>

		<tr>
			<td align='right'>不透明度：</td>
			<td><input type="text" name="textwatermark.transparency" size="7" maxlength="2"
				class="input_text" value="${waterMarkForm.textwatermark.transparency}" onkeypress="checkNum();"/>
			%</td>
		</tr>
		<tr id='shuiyintr13' style="display: block">
			<td align='right'>字体大小：</td>
			<td><input type="text" name="textwatermark.fontSize" id="fontsize" size="7"
				class="input_text" maxlength="2" onkeypress="checkNum();"
				value="${waterMarkForm.textwatermark.fontSize}"></td>
		</tr>
		<tr id='shuiyintr14' style="display: block">
			<td align='right'>文字颜色：</td>
			<td><input type="text" name="textwatermark.color" size="7" maxlength="7" id="fontColor"
				class="input_text" value="${waterMarkForm.textwatermark.color}"
				onclick="colordialog();"/></td>
		</tr>
		<tr id='shuiyintr12' style="display: block">
			<td align='right'>文字字体：</td>
			<td><select class="input_select" name="textwatermark.font" id="fontStyle"
				style="width: 150px;">
				<option>....</option>
				<option value="Arial">Arial</option>
				<option value="Comic Sans MS">Comic Sans MS</option>
				<option value="Courier New">Courier New</option>
				<option value="Tahoma">Tahoma</option>
				<option value="Times New Roman;Verdana">Times New
				Roman;Verdana</option>
				<option value="方正舒体">方正舒体</option>
				<option value="方正姚体">方正姚体</option>
				<option value="仿宋_UTF-8">仿宋_UTF-8</option>
				<option value="黑体" >黑体</option>
				<option value="华文彩云">华文彩云</option>
				<option value="华文细黑">华文细黑</option>
				<option value="华文新魏">华文新魏</option>
				<option value="华文行楷">华文行楷</option>
				<option value="华文中宋">华文中宋</option>
				<option value="楷体_UTF-8">楷体_UTF-8</option>
				<option value="隶书">隶书</option>
				<option value="宋体">宋体</option>
				<option value="新宋体">新宋体</option>
				<option value="幼圆">幼圆</option>
			</select></td>
		</tr>
	</table>
</table>
</fieldset>
<table width="100%" border="0" cellspacing="1" cellpadding="5"
	align="center" class="opr_tb">
	<tr>
		<td align="center"><input id="editbutton" class='btn_normal' type="button"
			 value="保  存"  onClick="savePicture()"></td>
	</tr>
</table>
</form>
</body>
</html>



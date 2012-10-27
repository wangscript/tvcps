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
		document.getElementById("imgHW").value = obj.id;//获取9个方向按钮选中的值

	}
	function checkNum(){ 
	      if(event.keyCode<48||event.keyCode>57){     
	          event.returnValue=false;        
	  }   
	}
	$( function() {
		$("#boderSize").focus();
		document.getElementById("bt9").className="down";
		document.getElementById("curposition").innerText="右下";
	});

	function clear1(value) {
		var param = "myfile" + value;
		var obj = document.getElementById(param);
		obj.outerHTML = obj.outerHTML;
	}
	//添加上传文本框
	var fileNum = 1;
	function addFile() {
		fileNum += 1;
		var a = fileNum - 1;
		var b = "#file" + a;
			if(fileNum<10){
			$(b).after(
					"<li id=\"file"
							+ fileNum
							+ "\">文件 "
							+ fileNum
							+ ": <input type=\"file\" class=\"btn_small\" name=\"files("
							+ a
							+ ")\" value='浏览' id='myfile"
							+ fileNum
							+ "' style='background:#eeeeee;font:12px;' size='50' onkeydown='return false;'/> <input type='button' value='清空' class='btn_small' onclick='clear1("
							+ fileNum + ")'/></li>");
			}else{
				$(b).after(
						"<li id=\"file"
								+ fileNum
								+ "\">文件"
								+ fileNum
								+ ": <input type=\"file\" class=\"btn_small\" name=\"files("
								+ a
								+ ")\" value='浏览' id='myfile"
								+ fileNum
								+ "' style='background:#eeeeee;font:12px;' size='50' onkeydown='return false;'/> <input type='button' value='清空' class='btn_small' onclick='clear1("
								+ fileNum + ")'/></li>");
				}
		
	}
	//清空内容
	function clearUrl() {
		var obj = document.getElementById("myfile1");
		obj.outerHTML = obj.outerHTML;
	}

	//上传提交图片
	function savePicture() {
		var a = 0;

		//遍历循环判断文件类型是否合法
		for ( var i = 0; i < fileNum; i++) {
			var b = i + 1;
			var newFile = "#myfile" + b;
			var tmp = $(newFile).val();
			if (tmp != "") {
				a = 1;
				var suffixName = (tmp.substring(tmp.lastIndexOf(".") + 1,
						tmp.length)).toLowerCase();
				if (suffixName != "jpg" && suffixName != "jpeg"
						&& suffixName != "gif" && suffixName != "bmp"
						&& suffixName != "png") {
					alert("上传的文件类型必须是图片");
					return false;
				}
			}
		}
		// 判断上传文件是否为空 
		if (a == 0) {
			alert("请选择文件");
			return false;
		}
		$("#dealMethod").val("addPicWater");
		var options = {
			url :"<c:url value='/waterMark.do'/>",
			success : function(msg) {
				var arr = msg.split("##");
				if (arr[0] != "") {
					alert(msg);
				}
				top.document.getElementById("rightFrame").src = "<c:url value='/waterMark.do?dealMethod=picWater&'/>"+getUrlSuffixRandom();
				closeWindow(rightFrame.getWin());
			}
		};
		$('#waterMarkForm').ajaxSubmit(options);
	}
</script>
<body>
<script type="text/javascript"
	src="<c:url value="/script/officecolor.js"/>"></script>
<form method="post" name="waterMarkForm" enctype="multipart/form-data"
	id="waterMarkForm" action="<c:url value="/waterMark.do"/>"><!-- 隐含变量开始 -->
<input type="hidden" name="picwatermark.position" id="imgHW" value="bt9" />
<input type="hidden" name="dealMethod" id="dealMethod"
	value="addPicWater" /> <!-- 隐含变量结束 -->

<div class="form_div" style="padding:0 10px 10px 20px;">	
<fieldset><legend>水印图片导入选项及说明</legend>

<table width="100%" border="0" cellspacing="1" cellpadding="2"
	align="center">
	<tr>
		<td width="100">说明：</td>
		<td colspan="2">水印图片文件名后缀只能为（.jpg .gif .png .JPG .GIF .PNG）之一,&nbsp;&nbsp;&nbsp;建议上传的水印图片不要超过900×500</td>
	</tr>
	<tr>
		<td>水印位置：</td>
		<td>
		<table id="position" border="0" cellspacing="1" cellpadding="0">
			<tr>
						<td colspan="3" height="20" style="color: red;">位置：<span
							id="curposition"></span>
						</td>
			</tr>
			<tr>
				<td><input type="button" value="↖" class="up" id="bt1"
					name="btn1" onclick="btnClick(this)" /></td>
				<td><input type="button" value="↑" id="bt2" class="up"
					name="btn2" onclick="btnClick(this)" /></td>
				<td><input type="button" value="↗" id="bt3" class="up"
					name="btn3" onclick="btnClick(this)" /></td>
			</tr>
			<tr>
				<td><input type="button" value="←" id="bt4" class="up"
					name="btn4" onclick="btnClick(this)" /></td>
				<td><input type="button" value="•" id="bt5" class="up"
					name="btn5" onclick="btnClick(this)" /></td>
				<td><input type="button" value="→" id="bt6" class="up"
					name="btn6" onclick="btnClick(this)" /></td>
			</tr>
			<tr>
				<td><input type="button" value="↙" id="bt7" class="up"
					name="btn7" onclick="btnClick(this)" /></td>
				<td><input type="button" value="↓" id="bt8" class="up"
					name="btn8" onclick="btnClick(this)" /></td>
				<td><input type="button" value="↘" id="bt9" class="up"
					name="btn9" onclick="btnClick(this)" /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>水印边距：</td>
		<td><input class="input_text" type="text"
			name="picwatermark.boder" size="7" id="boderSize" maxlength="2"
			value="5" onkeypress="checkNum();"/> px</td>
	</tr>
	<tr>
		<td>不透明度：</td>
		<td><input class="input_text" type="text"
			name="picwatermark.transparency" id="alpu" size="7" maxlength="2"
			value="50" onkeypress="checkNum();">&nbsp;%</td>
	</tr>
</table>
</fieldset>
<br>
<fieldset><legend>选择文件</legend> <br />
<ul>
	<li id="file1">文件 1:
	 <input type="file" name="files(0)"
		class="btn_small" id="myfile1"
		style="background: #eeeeee; font: 12px;" size="50"
		onkeydown="return false;" value="浏览" /> <input type="button"
		value="清空" class="btn_small" onclick="clearUrl()" /></li>
	<li id="btn">
	<center><input class="btn_normal" type="button" value="上传"
		onclick="savePicture()" /> &nbsp;&nbsp; <input class="btn_normal"
		type="button" value="添加" onclick="addFile()" /></center>
	</li>

</ul>
</fieldset>
</div>
</form>
</body>
</html>



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
.up2 {
	background-color: #000fff;
}
</style>



<script type="text/javascript">
	$(document).ready(function(){
		document.getElementById('description').focus();
	});
/**
    //首先隐藏图片控件ImgH，上传时显示图片。
	window.onload = function HidenImg() {
		document.getElementById("imgH").style.display = "none" //隐藏图片控件
    };  
    
  */  

  //btnClick(obj)函数用于设置按钮的样式和显示的位置
  function btnClick(obj){
	    for(var i=1;i<=9;i++){
	        document.getElementById("bt"+i).className = "up";
	    }
	    obj.className = "down";
	    var arr = ["左上","上","右上","左","中","右","左下","下","右下"];
	    var index = obj.id.substring(2,3);
	    document.getElementById("curposition").innerText = arr[parseInt(index,10)-1];
	    document.getElementById("imgHW").value=obj.id;//获取9个方向按钮选中的值
	}
	//funRadio()函数用于选择文字水印和图片水印
  function funRadio()
	{
		if(document.documentForm.selectOption[0].checked )//如果选中文字水印，就显示文字水印的设置
		{
			document.all.shuiyintr11.style.display = "block"; //显示水印文字
			document.all.shuiyintr12.style.display = "block";//显示文字字体
			document.all.shuiyintr13.style.display = "block";//显示字体大小
			document.all.shuiyintr14.style.display = "block";//显示文字颜色
			document.all.shuiyintr2.style.display = "none";//隐藏水印图片
		}
		else
		{
			document.all.shuiyintr11.style.display = "none";//隐藏水印文字
			document.all.shuiyintr12.style.display = "none";//隐藏文字字体
			document.all.shuiyintr13.style.display = "none";//隐藏字体大小
			document.all.shuiyintr14.style.display = "none";//隐藏文字颜色
			document.all.shuiyintr2.style.display = "block";//显示水印图片
		}
	}
  function checkNum(){ 
      if(event.keyCode<48||event.keyCode>57){     
          event.returnValue=false;        
  }   
}
  $( function loadDefault() {
	 
	//页面加载时将下拉列表选中
		var fontName =$("#fontNameId").val();//字体ID
		var fonts = $("#fontColorId").val();//颜色
		var fontsiz=$("#fontsizeid").val();//字体大小
		var fontpad=$("#i_padding").val();//边距
		var fontpam=$("#qualNumid").val();//透明度
		if(fonts==""){
			$("#fontColorId").val("#ff0000");
			}
		if(fontsiz==""){
			$("#fontsizeid").val("20");
			}
		if(fontpad==""){
			$("#i_padding").val("5")
			}
		if(fontpam==""){
			$("#qualNumid").val("50");
			}

		var fontstyle = $("#selectFontids").val();
		var imgPos = $("#btnPos").val();
		$.each($("#fontStyle option"), function(i, n){//ID遍历
			if (n.value == fontstyle) {
				$(n).attr("selected", "selected");
			}
		});	
	
		//选中字体列表的值
		$.each($("#picNameSelect option"), function(i, n){
			if (n.value == fontName) {
				$(n).attr("selected", "selected");
			}
		});
		//选中字体列表的值
		$.each($("#fontNameIdselect option"), function(i, n){
			if (n.value == fontName) {
				$(n).attr("selected", "selected");
			}
		});
		//页面加载时选中的方向安扭
		var imgpos=document.getElementById("imgHW").value;		
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
			//document.getElementById("imgHW").innerText="bt9";
	 	}
		
		funRadio();//选择文字水印或者图片水印
			
	});
	
	function ViewImg() {
		if(document.getElementById("imgH").style.display == "none") {
			document.getElementById("imgH").style.display = "";   //显示图片
		}
		document.getElementById("imgH").src = document.getElementById("myfile").value;
	}

	
	
	function savePicture(obj) {
		var myfile = $("#myfile").val();
		if(myfile.trim().isEmpty()){
			alert("请选择文件");
			return false;
		}
		var suffixName = (myfile.substring(myfile.lastIndexOf(".") + 1, myfile.length)).toLowerCase();
		if(suffixName == "jpg" || suffixName == "jpeg" || suffixName == "gif" || suffixName == "bmp" || suffixName == "png" || suffixName == "zip") {
			//if($("#isScaleImage").val() == 1) {
			//	$("#dealMethod").val("uploadScaleImage");
			//} else {
			$("#dealMethod").val("upload");
			//}

			var url = "<c:url value="/picture.do"/>";
			//如果用户选中了上传加水印，就必须选择图片，否则就不提示
			if(document.documentForm.needwebimage.checked){
				if(document.documentForm.selectOption[1].checked==true){
					if(document.documentForm.img[0].selected){
						alert("请选择水印图片！  \n 1.没有水印图片，可在系统设置中添加  \n 2.不加水印，不勾选复选框");
						return false;
					}
				}
				if(document.documentForm.selectOption[0].checked==true){
					if(document.documentForm.writerFont[0].selected){
						alert("请选择水印文字！\n 1.没有水印文字，可在系统设置中添加 \n 2.不加水印，不勾选复选框");
						return false;
					}
				}
			}

			//return false;			
			//用户是否选中了水印上传
			if(document.documentForm.needwebimage.checked){
				$("#isWaterId").val("1");
			}else{
				$("#isWaterId").val("0");
			}
		 	var options = {	 	
	 		    	url: url,
	 		    success: function(msg) { 		
	 		 		    var strmsg = msg.split("###");
	 		 		  
	 		 		  	var message = strmsg[0];
	 		 		  	var isScaleImage = strmsg[1];
	 		 		  	var columnLink = strmsg[2];    
	 		 		  	var articlePicture = strmsg[3];	
	 		 		  	var nodeId = strmsg[4];	
						
	 		    		if(message != null && message != "" ) {
	 		     			if(message != "上传成功") {
	 		   					alert(message);
	 		                }
	 		   			if(isScaleImage == 1 || columnLink == 1 || columnLink == 2 || columnLink == 3 || columnLink == 4 || (articlePicture != null && articlePicture != "null" && articlePicture != "")) {
	 		   			//	var articlePicture = $("#articlePicture").val();
	 		   				var firstWin = top.getWin();
	 		   				//文章管理中高级编辑器上传图片
	 		   				if(firstWin == null || firstWin == "") {
	 		   					var picWin = rightFrame.getWin();
	 		   					var picTemp = picWin.split("###");
	 		   					var picDialogProperty = picTemp[0];
	 		   					var picDetailFrame = "_DialogFrame_"+picDialogProperty;	
	 		   					var picTempWindow = top.document.getElementById(picDetailFrame); 
	 		   					var newPicTempWindow = top.document.getElementById(picDetailFrame).contentWindow; 
	 		   					var pictureWin = newPicTempWindow.getWin();
	 		   					picTempWindow.src = "<c:url value='/picture.do?nodeId="+nodeId+"&dealMethod=uploadPicList&columnLink=" + columnLink + "&articlePicture="+ articlePicture + "&" + getUrlSuffixRandom() + "'/>";
	 		   					top.closeWindow(newPicTempWindow.getWin());	
	 		   					return null;
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
	 		   				var newPicWindow = secondWindow.getWin();
	 		   				//样式管理专用
	 		   				var thirdtemp = newPicWindow.split("###");
	 		   				var thirddialogProperty = thirdtemp[0];
	 		   				var thirddetailFrame = "_DialogFrame_"+thirddialogProperty;	
	 		   				var thirdWindow = top.document.getElementById(thirddetailFrame).contentWindow;
	 		   				var otherPicWindow = thirdWindow.getWin();
	 		   				if(otherPicWindow != null && otherPicWindow != "undefined" && otherPicWindow != "null" && otherPicWindow != "") {
	 		   					top.closeWindow(otherPicWindow);	
	 		   					top.document.getElementById(thirddetailFrame).src =  "<c:url value='/picture.do?nodeId="+nodeId+"&dealMethod=uploadPicList&columnLink=" +columnLink + "&articlePicture="+ articlePicture + "&" + getUrlSuffixRandom() + "'/>";
	 		   				} else {
	 		   					top.closeWindow(newPicWindow);	
	 		   					top.document.getElementById(seconddetailFrame).src =  "<c:url value='/picture.do?nodeId="+nodeId+"&dealMethod=uploadPicList&columnLink=" + columnLink + "&articlePicture="+ articlePicture + "&" + getUrlSuffixRandom() + "'/>";
	 		   				}
	 		   			} else {
	 		   				closeWindow(rightFrame.getWin());
	 		   				top.document.getElementById("rightFrame").src = "<c:url value='/picture.do?nodeId="+nodeId+"&dealMethod=&'/>" + getUrlSuffixRandom();
	 		   				
	 		   			}
	 		   		}	
	 		    } 
	 		  };
			$('#documentForm').ajaxSubmit(options);	
			
		} else {
			alert("格式不正确，请看图片上传说明");
			return false;
		}
	}
	function checkimg(obj)
	{
		var f = obj.checked;
		if( f ){
			document.all.shuiyin.style.display = "block";
		}else{
			document.all.shuiyin.style.display = "none";
		}
	}
   	function closeChild() {
   		closeWindow(win);
	}

	
	//下拉文字列表改变事件,用Ajax实现
	function ChangWater(obj){
		document.getElementById("fontNameId").value=obj.value;
		document.all("dealMethod").value="changWater";
		$.ajax({
			url : '<c:url value="/picture.do?dealMethod=changWater&fontNameId="/>'+obj.value,
			type: "post",
			success : function(msg) {
				var strmsg = msg.split("###");
	 		  	var listPic = strmsg[0];
	 		  	var listFont = strmsg[1];
	 		  	if(listPic.trim()!=""){
	 		  		var lip = listPic.split("@@");
	 		  		$("#imgHW").val(lip[0].trim());
	 		  		$("#qualNumid").val(lip[1]);
	 		  		$("#i_padding").val(lip[2]);
	 		  		var arr = ["左上","上","右上","左","中","右","左下","下","右下"];
	 			    var index = lip[0].trim().substring(2,3);
	 			    document.getElementById("curposition").innerText = arr[parseInt(index,10)-1];
	 			    for(var i=1;i<=9;i++){
	 			        if("bt"+i==lip[0].trim()){
	 			      		document.getElementById(lip[0].trim()).className = "down";
		 			    }else{
		 			    	document.getElementById("bt"+i).className = "up";
			 			}
	 			    }
		 		 }else if(listFont.trim()!=""){
	 			  	var lip = listFont.split("@@");
	 			  	$("#fontsizeid").val(lip[1]);
	 			  	$("#fontColorId").val(lip[2]);
	 			  	$("#imgHW").val(lip[3]);
	 			  	$("#qualNumid").val(lip[4]);
	 			  	$("#i_padding").val(lip[5]);
	 			  	$.each($("#fontStyle option"),function(i,n){
						if(n.value==lip[0]){
							$(n).attr("selected","selected");
						}
	 			  	});
	 			  	var arr = ["左上","上","右上","左","中","右","左下","下","右下"];
	 			    var index = lip[3].substring(2,3);
	 			    document.getElementById("curposition").innerText = arr[parseInt(index,10)-1];
	 			    for(var i=1;i<=9;i++){
	 			        if("bt"+i==lip[3]){
	 			      		document.getElementById(lip[3]).className = "down";
		 			    }else{
		 			    	document.getElementById("bt"+i).className = "up";
			 			}
	 			    }
	 		  	}
			}
		});
	}
		
</script>

</head>
<body>
<script type="text/javascript"
	src="<c:url value="/script/officecolor.js"/>"></script>
<form method="post" name="documentForm" enctype="multipart/form-data" id="documentForm" action="<c:url value="/picture.do"/>">
			<input type="hidden" name="dealMethod" id="dealMethod" />
			<input type="hidden" name="nodeId" id="nodeId"	value="<%=request.getParameter("nodeId")%>" /> 
			<input type="hidden" name="name" id="name" value="<%=request.getParameter("nodeName")%>" />
			<input type="hidden" name="message" id="message"	value="${documentForm.infoMessage}" />
			<input type="hidden" name="categoryid" value="${documentForm.document.documentCategory.id }" />
			<input type="hidden" name="isScaleImage" id="isScaleImage"	value="<%=request.getParameter("isScaleImage")%>" /> 
			<input type="hidden" name="columnLink" id="columnLink"	value="<%=request.getParameter("columnLink")%>" />
			<input type="hidden" name="articlePicture" id="articlePicture"	value="<%=request.getParameter("articlePicture")%>" />
			<input type="hidden" name="imgXY" id="imgHW"	value="${documentForm.watermark.position}" /> 
			<!--fontNameId 用于存储ID，去匹配下拉列表，并选中  -->
			<input type="hidden" name="watermark.id" id="fontNameId"	value="${documentForm.watermark.id}" />
			<!-- 是否要在上传图片时候加上水印 -->
			<input type="hidden" name="isWater" id="isWaterId"/>
			<input type="hidden" name="textwatermark.font" id="selectFontids"	value="${documentForm.textwatermark.font}" /> 
			<input type="hidden" id="imgpathid" name="imgPath" value="${documentForm.picwatermark.localPath}" />
<div class="form_div" style="padding:0 10px 10px 10px;">
		<fieldset><legend>上传图片</legend>
	<table style="font: 12px; width: 460px;"> 
		<tr>
			<td class="td_left" width="50px;"></td>
			<td class="td_left" width="400px;">
			<center>图片上传说明</center>
			</td>
		</tr>
		<tr>
			<td class="td_left" width="50px;">□</td>
			<td width="400px;">图片文件（文件名后缀：jpg、 jpeg、gif、 bmp、 png、 JPG、JPEG、
			GIF、BMP、 PNG）</td>
		</tr>
		<tr>
			<td class="td_left" width="50px;">□</td>
			<td width="400px;">压缩包（文件名后缀：zip、 ZIP）</td>
		</tr>
		<tr>
			<td class="td_left" width="50px;">□</td>
			<td width="400px;">上传文件大小不能超过10M！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="checkbox" name="needwebimage" id="isWaters"
				onClick="checkimg(this)" checked="checked">上传图片加上水印</td>
		</tr>
		<tr>
			<td class="td_left" width="50px;"><i>&nbsp;</i>路径：</td>
			<td><input type="file" name="myfile" id="myfile"
				class="input_text" size="40"
				onkeydown="return false;" value="浏览" /></td>
		</tr>
		<tr>
			<td class="td_left" width="50px;"><i>&nbsp;</i>描述：</td>
			<td><input type="text" name="document.description"
				class="input_text" size="50"
				maxlength="10" id="description"></td>
		</tr>
</table>
</fieldset>
<fieldset id="shuiyin" style="display: ;width:465px;" style="padding:10px 10px 0 10px;"><legend>水印选项
<c:choose>
	<c:when test="${documentForm.check == 1}">
		<input type="radio" value="0" name="selectOption" onClick="funRadio();"> 文字水印&nbsp; <input type="radio" value="1"
	name="selectOption" onClick="funRadio();" checked="checked"> 图片水印
	</c:when>
	<c:otherwise>
		<input type="radio" value="0" name="selectOption" onClick="funRadio();" checked="checked"> 文字水印&nbsp; <input type="radio" value="1"
	name="selectOption" onClick="funRadio();"> 图片水印
	</c:otherwise>
</c:choose>
 </legend>
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr style="display: block">
				<td align='right'>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr id='shuiyintr11' style="display: block">
				<td align='right'>水印文字：</td>
				<td><select name="writerFont" id="fontNameIdselect"
					style="width: 200px" class="input_select" onchange="ChangWater(this);">
					<option value="" selected="selected">请选择类别</option>
					<c:forEach items="${documentForm.listWaterFont}" var="TetWater">
						<option value="${TetWater.id}">${TetWater.name}</option>
					</c:forEach>
				</select></td>
			</tr>
			<tr id='shuiyintr12' style="display: block">
				<td align='right'>文字字体：</td>
				<td><select class="input_select" name="fontStyle"
					style="width: 150px;" id="fontStyle">
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
					<option value="黑体" selected>黑体</option>
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
			<tr id='shuiyintr13' style="display: block">
				<td align='right'>字体大小：</td>
				<td><input type="text" name="fontsize" size="7"
					class="input_text" maxlength="2" id="fontsizeid" onkeypress="checkNum();"
					value="${documentForm.textwatermark.fontSize}"></td>
			</tr>
			<tr id='shuiyintr14' style="display: block">
				<td align='right'>文字颜色：</td>
				<td><input type="text" name="fontColor" size="7" maxlength="7"
					class="input_text" id="fontColorId" value="${documentForm.textwatermark.color}"
					onclick="colordialog();" /></td>
			</tr>
			<tr id='shuiyintr2' style="display: none">
				<td align='right'>水印图片：</td>
				<td><select class="input_select" name='img'
					style="width: 150px" id="picNameSelect" onchange="ChangWater(this)">
					<option value="" selected="selected">请选择类别</option>
					<c:forEach items="${documentForm.listPicPath}" var="picWater">
						<option value="${picWater.id}">${picWater.name}</option>
					</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td align='right'>不透明度：</td>
				<td><c:choose>
					<c:when test="${documentForm.textwatermark.transparency eq null}">
						<input type="text" name="qualNum" size="7" maxlength="2"
							class="input_text" id="qualNumid"
							value="${documentForm.picwatermark.transparency}" onkeypress="checkNum();"> %
						</c:when>
					<c:otherwise>
						<input type="text" name="qualNum" size="7" maxlength="2"
							class="input_text" id="qualNumid" onkeypress="checkNum();"
							value="${documentForm.textwatermark.transparency}"> %
						</c:otherwise>
				</c:choose></td>
			</tr>

		</table>
		</td>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align='right'>水印位置：</td>
				<td>
				<table id="position" border="0" cellspacing="1" cellpadding="0">
					<tr>
						<td colspan="3" height="20" style="color: red;">位置：<span
							id="curposition"></span></td>
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
				<td><c:choose>
					<c:when test="${documentForm.textwatermark.boder eq null}">
						<input class="input_text" type="text" name="margin" id="i_padding"
							size="7" maxlength="2" value="${documentForm.picwatermark.boder}" onkeypress="checkNum();"/> px
						</c:when>
					<c:otherwise>
						<input class="input_text" type="text" name="margin" id="i_padding"
							size="7" maxlength="4"
							value="${documentForm.textwatermark.boder}" onkeypress="checkNum();"/> px
						</c:otherwise>
				</c:choose></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</fieldset>
<table>
	<tr>
		<td class="td_left" width="50px;"><i>&nbsp;</i></td>
		<td width="400px;">
		<center><input type="button" class="btn_normal"
			onclick="savePicture();" value="上传" /></center>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>



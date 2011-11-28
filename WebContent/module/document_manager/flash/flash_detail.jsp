<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>文档管理中的flash中的详细管理</title>
<style  type="text/css" media="all">
	
</style>

<script type="text/javascript">

	$(document).ready(function() {
		$("#description").focus();
	});

 	function saveFlash(obj){
 		var myfile = $("#myfile").val();
 		var description = $('#description').val();
 		if(myfile.isEmpty()) {
			alert("请选择文件");
			return false;
		}
 		if(description.length > 200){
			alert('描述过长，请不要大于200个字，上传失败!');
			return false;
        }
 //		var isScaleFlash = $("#isScaleFlash").val();
		var suffixName = (myfile.substring(myfile.lastIndexOf(".") + 1, myfile.length)).toLowerCase();
		if(suffixName == "zip" || suffixName == "swf") {
			$("#dealMethod").val("upload");

			var options = {	 	
	 		    	url: "<c:url value="/flash.do"/>",
	 		    success: function(msg) { 		
	 		 		    var strmsg = msg.split("###");
	 		 		  	var message = strmsg[0];
	 		 		  	var isScaleFlash = strmsg[1];
	 		 		  	var articleFlash = strmsg[2];	
	 		 		  	var nodeId = strmsg[3];	

	 	//	 		  alert("----message------" + message + "-----isScaleFlash------" + isScaleFlash + "-----articleFlash----" + articleFlash);
	 		 		  if(message != null && message != ""){
	 		 			if(message != "上传成功") {
	 		 				alert(message);
	 		             }
	 		 			if(isScaleFlash == 1 || (articleFlash != null && articleFlash != "null" && articleFlash != "")) {
	 		 				var firstFrame = top.getWin();
	 		 				//文章管理中高级编辑器上传flash
	 		 				if(firstFrame == null || firstFrame == "") {
	 		 					var flaWin = rightFrame.getWin();
	 		 					var flaTemp = flaWin.split("###");
	 		 					var flaDialogProperty = flaTemp[0];
	 		 					var flaDetailFrame = "_DialogFrame_"+flaDialogProperty;	
	 		 					var flaTempWindow = top.document.getElementById(flaDetailFrame).contentWindow; 
	 		 					flaTempWindow.location.href = "<c:url value='/flash.do?nodeId=" + nodeId + "&dealMethod=insertFlashList&articleFlash="+ articleFlash + "&isScaleFlash=" + isScaleFlash + "&" + getUrlSuffixRandom() + "'/>";
	 		 					top.closeWindow(flaTempWindow.getWin());	
	 		 					return null;
	 		 				}
	 		 				var temp = firstFrame.split("###");
	 		 				var dialogProperty = temp[0];
	 		 				var detailFrame = "_DialogFrame_"+dialogProperty;	 	
	 		 				var firsttempWindow = top.document.getElementById(detailFrame).contentWindow;
	 		 				var secondFrame = firsttempWindow.getWin();
	 		 	//			var articleFlash = $("#articleFlash").val();
	 		 				if(secondFrame == null || secondFrame == "null" || secondFrame == "") {
	 		 					var secondTempWindow = firsttempWindow.document.getElementById("unitEditArea").contentWindow;
	 		 					var secondWin = secondTempWindow.getWin();					
	 		 					var secondtemp = secondWin.split("###");
	 		 					var seconddialogProperty = secondtemp[0];
	 		 					var seconddetailFrame = "_DialogFrame_"+seconddialogProperty;			
	 		 					var secondWindow = top.document.getElementById(seconddetailFrame).contentWindow;
	 		 					var newPicWindow = secondWindow.getWin();
	 		 					top.closeWindow(newPicWindow);
	 		 					top.document.getElementById(seconddetailFrame).src = "<c:url value='/flash.do?nodeId=" + nodeId + "&dealMethod=insertFlashList&articleFlash="+ articleFlash + "&isScaleFlash=" + isScaleFlash + "&" + getUrlSuffixRandom() + "'/>";
	 		 				} else {
	 		 					top.closeWindow(secondFrame);
	 		 					top.document.getElementById(detailFrame).src = "<c:url value='/flash.do?nodeId=" + nodeId + "&dealMethod=insertFlashList&articleFlash="+ articleFlash + "&isScaleFlash=" + isScaleFlash + "&" + getUrlSuffixRandom() + "'/>";
	 		 				}
	 		 			} else {
	 		 				rightFrame.window.location.href = "<c:url value='/flash.do?nodeId=" + nodeId + "&dealMethod=&articleFlash="+ articleFlash + "&isScaleFlash=" + isScaleFlash + "&" + getUrlSuffixRandom() + "'/>";
	 		 				closeWindow(rightFrame.getWin());
	 		 		    }
	 		     	}
	 		    	}
	 		    	};
	 		 	$('#documentForm').ajaxSubmit(options);	
	 			       // $("#documentForm").submit();
		} else {
			alert("格式不正确，请看falsh上传说明");
			return false;
		}
 	}

  	function closeChild() {
  		closeWindow(win);
 	}

</script>

</head>
<body>
    <form method="post" name="documentForm" enctype="multipart/form-data" id="documentForm" action="<c:url value="/flash.do"/>">
        <input type="hidden" name="dealMethod"  id="dealMethod" />
        <input type="hidden" name="message"     id="message"  value="${documentForm.infoMessage}" />
		<input type="hidden" name="nodeId"      id="nodeId"   value="<%=request.getParameter("nodeId")%>" />
        <input type="hidden" name="categoryid"  value="${documentForm.document.documentCategory.id }" />
		<input type="hidden" name="isScaleFlash" id="isScaleFlash" value="<%=request.getParameter("isScaleFlash")%>"/>
		<input type="hidden" name="articleFlash" id="articleFlash" value="<%=request.getParameter("articleFlash") %>"/>
        <div class="form_div">
			<table id="main" style="font:12px; width:460px;">
				<tr>
					<td class="td_left" width="50px;"></td>
					<td class="td_left" width="400px;"><center>flash上传说明</center></td>
				</tr>
				<tr>
					<td class="td_left" width="50px;">□</td>
					<td width="400px;"> flash文件（文件名后缀：swf、SWF）</td>
				</tr>
				<tr>
					<td class="td_left" width="50px;">□</td>
					<td width="400px;"> 压缩包（文件名后缀：zip、 ZIP）</td>
				</tr>
				<tr>
					<td class="td_left" width="50px;">□</td>
					<td width="400px;"> 上传的zip中不能包含文件夹，含有的非swf格式文件系统自动删掉</td>
				</tr>
				<tr>
					<td class="td_left" width="50px;">□</td>
					<td width="400px;"> 上传flash文件大小不能超过10M！</td>
				</tr>
				
				<tr>
					<td class="td_left" width="50px;"><i>&nbsp;</i>路径：</td>
					<td><input type="file" name="myfile" id="myfile" value="浏览" style="background:#eeeeee;font:12px; width:380px;" onkeydown="return false;" /> </td>
				</tr>
				<tr>
					<td class="td_left" width="50px;"><i>&nbsp;</i>描述：</td>
					<td><textarea rows="5" cols="30" name="document.description" id="description" class="input_textarea_normal"></textarea></td>
				</tr>
				<tr>
					<td class="td_left" width="50px;"><i>&nbsp;</i></td>
					<td width="400px;"><center>
						<input type="button" class="btn_normal" onclick="saveFlash()" value="上传" />
						&nbsp;&nbsp;&nbsp;&nbsp;
		        		<input type="reset" class="btn_normal" value="重置"/></center>
					</td>
				</tr>
			</table>
        </div>
	</form>
</body>
</html> 





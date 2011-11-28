<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>文档管理中的js脚本中的详细管理</title>
<style  type="text/css" media="all">
	
</style>

<script type="text/javascript">


	$(document).ready(function() {
		$("#description").focus();
	});

    function saveJs(obj) {
    	var myfile = $("#myfile").val();
    	var description = $('#description').val();
    	if(description.length > 200){
			alert('描述过长，请不要大于200个字，上传失败!');
			return false;
        }
		if($("#myfile").val().isEmpty()) {
			alert("请选择文件");
			return false;
		} else {
			var suffixName = (myfile.substring(myfile.lastIndexOf(".") + 1, myfile.length)).toLowerCase();
			if(suffixName != "zip" && suffixName != "js") {
				alert("格式不正确，请看JS上传说明");
				return false;
			}
			$("#dealMethod").val("upload");
			var options = {	 	
	 		    	url: "<c:url value="/js.do"/>",
	 		    success: function(msg) {
	 		 		    var strmsg = msg.split("###");
	 		 		  	var message = strmsg[0];
	 		 		  	var isScaleImage = strmsg[1];
	 		 		  	var articleJs = strmsg[2];	
	 		 		  	var nodeId = strmsg[3];	
			 			if(message != null && message != "") {
			        		if(message != "上传成功") {
								alert(message);
			            	}
			           		if(isScaleImage == 1 || (articleJs != null && articleJs != "null" && articleJs != "")) {
				           		var firstFrame = top.getWin();
				           		//文章管理中高级编辑器上传flash
								if(firstFrame == null || firstFrame == "") {
									var fileWin = rightFrame.getWin();
									var fileTemp = fileWin.split("###");
									var fileDialogProperty = fileTemp[0];
									var fileDetailFrame = "_DialogFrame_"+fileDialogProperty;	
									var fileTempWindow = top.document.getElementById(fileDetailFrame).contentWindow; 
									fileTempWindow.location.href = "<c:url value='/js.do?nodeId=" + nodeId + "&dealMethod=uploadScriptList&articleJs="+ articleJs + "&" + getUrlSuffixRandom() +"'/>";
									top.closeWindow(fileTempWindow.getWin());	
								}
								var temp = firstFrame.split("###");
								var dialogProperty = temp[0];
								var detailFrame = "_DialogFrame_"+dialogProperty;	 	
								var firsttempWindow = top.document.getElementById(detailFrame).contentWindow;
								var secondFrame = firsttempWindow.getWin();
				               	if(secondFrame == null || secondFrame == "null" || secondFrame == "") {
									var secondTempWindow = firsttempWindow.document.getElementById("unitEditArea").contentWindow;
									var secondWin = secondTempWindow.getWin();					
									var secondtemp = secondWin.split("###");
									var seconddialogProperty = secondtemp[0];
									var seconddetailFrame = "_DialogFrame_"+seconddialogProperty;			
									var secondWindow = top.document.getElementById(seconddetailFrame).contentWindow;
									var newPicWindow = secondWindow.getWin();
									top.closeWindow(newPicWindow);
									top.document.getElementById(seconddetailFrame).src = "<c:url value='/js.do?nodeId=" + nodeId + "&dealMethod=uploadScriptList&articleJs="+ articleJs + "&" + getUrlSuffixRandom() + "'/>";
								} else {
				               		top.closeWindow(secondFrame);
				               		top.document.getElementById(detailFrame).src = "<c:url value='/js.do?nodeId=" + nodeId + "&dealMethod=uploadScriptList&articleJs="+ articleJs + "&" + getUrlSuffixRandom() + "'/>";
				               	}
							} else {
								rightFrame.window.location.href = "<c:url value='/js.do?nodeId=" + nodeId + "&dealMethod=&" + getUrlSuffixRandom() + "'/>";
								closeWindow(rightFrame.getWin());
							}
	  					}
				}     
  			};
			$('#documentForm').ajaxSubmit(options);	
		} 
	}

    function closeChild() {
    	closeWindow(win);
  	}
  	  
</script>

</head>
<body>
    <form method="post" name="documentForm" enctype="multipart/form-data"  id="documentForm" action="<c:url value="/js.do"/>">
        <input type="hidden"  name="dealMethod" id="dealMethod" />
        <input type="hidden" name="message" id="message" value="${documentForm.infoMessage}" />
		<input type="hidden" name="nodeId" id="nodeId" value="<%=request.getParameter("nodeId")%>" />
        <input type="hidden" name="categoryid" value="${documentForm.document.documentCategory.id }" />
        <input type="hidden" name="articleJs" id="articleJs" value="<%=request.getParameter("articleJs")%>" />
		<input type="hidden"  name="isScaleImage" id="isScaleImage" value="<%=request.getParameter("isScaleImage")%>"/> 
        <div class="form_div">
			<table id="main" style="font:12px; width:460px;">
				<tr>
					<td class="td_left" width="50px;"></td>
					<td class="td_left" width="400px;"><center> js脚本上传说明</center> </td>
				</tr>
				<tr>
					<td class="td_left" width="50px;">□</td>
					<td width="400px;"> js脚本文件（文件名后缀：js,JS）</td>
				</tr>
				<tr>
					<td class="td_left" width="50px;">□</td>
					<td width="400px;"> 压缩包（文件名后缀：zip、 ZIP）</td>
				</tr>
				<tr>
					<td class="td_left" width="50px;">□</td>
					<td width="400px;"> 上传文件大小不能超过10M！</td>
				</tr>
				
				<tr>
					<td class="td_left" width="50px;"><i>&nbsp;</i>路径：</td>
					<td><input type="file" name="myfile" id="myfile" value="浏览" style="background:#eeeeee;font:12px; width:380px;" size="51" onkeydown="return false;" /></td>
				</tr>
				<tr>
					<td class="td_left" width="50px;"><i>&nbsp;</i>描述：</td>
					<td><textarea rows="5" cols="30" name="document.description" id="description" class="input_textarea_normal"></textarea></td>
				</tr>
				<tr>
					<td class="td_left" width="50px;"><i>&nbsp;</i></td>
					<td width="400px;"><center>
						<input type="button" class="btn_normal" onclick="saveJs()" value="上传" />
						&nbsp;&nbsp;&nbsp;&nbsp;
		        		<input type="reset" class="btn_normal" value="重置"/></center>
					</td>
				</tr>
			</table>		
        </div>
	</form>
</body>
</html> 





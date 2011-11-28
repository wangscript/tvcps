<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>文档管理中的附件中的详细管理</title>
<style  type="text/css" media="all">
	
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$("#description").focus();
	});

    function saveAttachment(obj) {
    	var description = $('#description').val();
    	if(description.length > 200){
			alert('描述过长，请不要大于200个字，上传失败!');
			return false;
        }
		if($("#myfile").val().isEmpty()) {
			alert("请选择文件");
			return false;
		} else {
			var myfile = $("#myfile").val();
			var suffixName = (myfile.substring(myfile.lastIndexOf(".") + 1, myfile.length)).toLowerCase();
			var fname = suffixName;
			
			if(fname != "htm" && fname != "html" && fname != "gif" && fname != "jpg"&& fname != "png" && fname != "bmp" && fname != "txt" && fname != "jpeg" && fname != "doc" && fname != "docx" && fname != "swf"&& fname != "ico" && fname != "zip" && fname != "xls" && fname != "rar") {
				alert("格式不正确，请看附件上传说明");
				return false;
			}
			$("#dealMethod").val("upload");
			var options = {	 	
	 		    	url: "<c:url value="/attachment.do"/>",
	 		    success: function(msg) { 		
	 		 		    var strmsg = msg.split("###");
	 		 		  	var message = strmsg[0];
	 		 		  	var isScaleImage = strmsg[1];
	 		 		  	var articleAtta = strmsg[2];	
	 		 		  	var nodeId = strmsg[3];	

	 		if(message != null && message != "") {
        		if(message != "上传成功") {
					alert(message);
            	}
	           	if(isScaleImage == 1 || (articleAtta != null && articleAtta != "null" && articleAtta != "")) {
	           		var firstFrame = top.getWin();
	
	           	//文章管理中高级编辑器上传flash
					if(firstFrame == null || firstFrame == "") {
						var fileWin = rightFrame.getWin();
						var fileTemp = fileWin.split("###");
						var fileDialogProperty = fileTemp[0];
						var fileDetailFrame = "_DialogFrame_"+fileDialogProperty;	
						var fileTempWindow = top.document.getElementById(fileDetailFrame).contentWindow; 
						fileTempWindow.location.href = "<c:url value='/attachment.do?nodeId=" + nodeId + "&dealMethod=uploadAttachmentList&articleAtta="+ articleAtta + "&" + getUrlSuffixRandom() + "'/>";
						top.closeWindow(fileTempWindow.getWin());	
					}
	           		
					var temp = firstFrame.split("###");
					var dialogProperty = temp[0];
					var detailFrame = "_DialogFrame_"+dialogProperty;	 	
					var firsttempWindow = top.document.getElementById(detailFrame).contentWindow;
					var secondFrame = firsttempWindow.getWin();
	   //            	var articleAtta = $("#articleAtta").val();
	
	               	if(secondFrame == null || secondFrame == "null" || secondFrame == "") {
						var secondTempWindow = firsttempWindow.document.getElementById("unitEditArea").contentWindow;
						var secondWin = secondTempWindow.getWin();					
						var secondtemp = secondWin.split("###");
						var seconddialogProperty = secondtemp[0];
						var seconddetailFrame = "_DialogFrame_"+seconddialogProperty;			
						var secondWindow = top.document.getElementById(seconddetailFrame).contentWindow;
						var newPicWindow = secondWindow.getWin();
						top.closeWindow(newPicWindow);
						top.document.getElementById(seconddetailFrame).src = "<c:url value='/attachment.do?nodeId=" + nodeId + "&dealMethod=uploadAttachmentList&articleAtta="+ articleAtta + "&" + getUrlSuffixRandom() + "'/>";
					} else {
	               		top.closeWindow(secondFrame);
	               		top.document.getElementById(detailFrame).src = "<c:url value='/attachment.do?nodeId=" + nodeId + "&dealMethod=uploadAttachmentList&articleAtta="+ articleAtta + "&" + getUrlSuffixRandom() + "'/>";
	               	}
				} else {
					rightFrame.window.location.href = "<c:url value='/attachment.do?nodeId=" + nodeId + "&dealMethod=&" + getUrlSuffixRandom() + "'/>";
					closeWindow(rightFrame.getWin());
				}
  				}
	 	
			//	$("#documentForm").submit();
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
    <form method="post" name="documentForm" enctype="multipart/form-data"  id="documentForm" action="<c:url value="/attachment.do"/>">
        <input type="hidden"  name="dealMethod" id="dealMethod" />
        <input type="hidden" name="message" id="message" value="${documentForm.infoMessage}" />
		<input type="hidden" name="nodeId" id="nodeId" value="<%=request.getParameter("nodeId")%>" />
        <input type="hidden" name="categoryid" value="${documentForm.document.documentCategory.id }" />
        <input type="hidden" name="articleAtta" id="articleAtta" value="<%=request.getParameter("articleAtta")%>" />
		<input type="hidden"  name="isScaleImage" id="isScaleImage" value="<%=request.getParameter("isScaleImage")%>"/> 
        <div class="form_div">
			<table id="main" style="font:12px; width:500px;">
				<tr>
					<td class="td_left" width="50px;"></td>
					<td class="td_left" width="400px;"><center> 附件上传说明</center> </td>
				</tr>
				<tr>
					<td class="td_left" width="50px;">□</td>
					<td width="400px;"> 附件包括htm,html,txt,doc,xls,rar,zip,jpg,gif,jpeg,bmp,png,ico,swf</td>
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
						<input type="button" class="btn_normal" onclick="saveAttachment()" value="上传" />
						&nbsp;&nbsp;&nbsp;&nbsp;
		        		<input type="reset" class="btn_normal" value="重置"/></center>
					</td>
				</tr>
			</table>		
        </div>
	</form>
</body>
</html> 





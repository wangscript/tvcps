 <%--
	功能：用于导入模板、修改模板界面的显示
	作者：郑荣华
 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>模板导入、修改</title>
<%@include file="/templates/headers/header.jsp"%>
<style  type="text/css" media="all">
	#main {
	 	margin:30px 10px 30px 10px;
	}
	#main li {
	 	line-height:22px;
	}
	#main label {
		width:45px;
		float:left;
	}
	#btn {
		padding:20px 0 0 150px;
	}
	#description {
		border-bottom:2px solid #ccc;
		margin-bottom:10px;
		height:30px;
	}	
	#btn_update {
		padding:20px 0 0 200px;
	}	
</style>

<script type="text/javascript"> 
    
	$(document).ready(function() {
		if($("#message").val() != null && $("#message").val() != "") {
			if($("#message").val() != "粘贴成功") {
				alert($("#message").val());
			}
			top.document.getElementById("rightFrame").src = "<c:url value='/template.do?nodeId=${templateForm.nodeId}&dealMethod='/>"+"&"+getUrlSuffixRandom();
			closeWindow(rightFrame.getWin());
		}
	});

	function clear1(value) {
		var param = "myfile" + value;
		var obj = document.getElementById(param);
	    obj.outerHTML = obj.outerHTML;
	}
	
	function clearUrl() {
		var obj = document.getElementById("myfile1");
	    obj.outerHTML = obj.outerHTML;
	}

	var fileNum = 1;
	function addFile() {    
	  	fileNum += 1;
		var a = fileNum-1;
		var b = "#file" + a;
		if(fileNum < 10) {
			$(b).after("<li id=\"file"+ fileNum +"\">文件 "+ fileNum +":&nbsp;&nbsp; <input type=\"file\" class=\"btn_small\" name=\"files("+ a +")\" value='浏览' id='myfile"+ fileNum +"' style='background:#eeeeee;font:12px;' size='50' onkeydown='return false;'/> <input type='button' value='清空' class='btn_small' onclick='clear1("+ fileNum +")'/></li>"); 
		} else {
			$(b).after("<li id=\"file"+ fileNum +"\">文件 "+ fileNum +": <input type=\"file\" class=\"btn_small\" name=\"files("+ a +")\" value='浏览' id='myfile"+ fileNum +"' style='background:#eeeeee;font:12px;' size='50' onkeydown='return false;'/> <input type='button' value='清空' class='btn_small' onclick='clear1("+ fileNum +")'/></li>");
		}
	}    

    function checkImport() {
        var a = 0;
		// 判断上传文件是否为空 
		for(var i = 0; i < fileNum; i++) {
			var b = i+1;
			var newFile = "#myfile" + b;
			var tmp = $(newFile).val();
			if(tmp != "") {
				a = 1;
				if($("#templateId").val().isEmpty()) {
					var suffixName = (tmp.substring(tmp.lastIndexOf(".")+1, tmp.length)).toLowerCase(); 
					if(suffixName != "zip") {
						alert("上传的文件类型必须是zip或者ZIP");
						return false;
					}
				} 
			}
		}
		if(a == 0) {
			alert("请选择文件");
			return false;
		}	
        if($("#templateId").val().isEmpty()) {
        	$("#dealMethod").val("import");
        } else {
            if(confirm("确定要更新？")) {
				$("#dealMethod").val("update");
            } else {
				return false;
            }
        } 
		var options = {	 	
 		    	url: "<c:url value='/template.do'/>",
 		    success: function(msg) {
					var arr = msg.split("##");
					if(arr[0] != "上传成功") {
						alert(arr[0]); 
					}
					top.document.getElementById("rightFrame").src = "<c:url value='/template.do?nodeId="+ arr[1] +"&dealMethod='/>"+"&"+getUrlSuffixRandom();
					closeWindow(rightFrame.getWin());		
 		    } 
 		};
 		$('#templateForm').ajaxSubmit(options);	
    }
 
</script>

</head>
<body>
<form action="<c:url value="/template.do"/>"  enctype="multipart/form-data" method="post" name="templateForm" id="templateForm">
	<input type="hidden" name="dealMethod"         id="dealMethod"/>
	<input type="hidden" name="message"            id="message"        value="${templateForm.infoMessage}" />
	<input type="hidden" name="nodeId"             id="nodeId"         value="<%=request.getParameter("nodeId")%>" />
	<input type="hidden" name="template.id"        id="templateId"     value="${templateForm.template.id }" />
	<input type="hidden" name="template.name"      id="templateName"   value="${templateForm.template.name }" />
	<input type="hidden" name="template.fileName"  id="fileName"       value="${templateForm.template.fileName }" />
	<input type="hidden" name="template.localPath" id="localPath"      value="${templateForm.template.localPath }" />
	<input type="hidden" name="template.url"       id="url"            value="${templateForm.template.url }" />
	<input type="hidden" name="createDate"         id="createDate"     value="${templateForm.template.createTime }" />
	<input type="hidden" name="creatorId"          id="creatorId"      value="${templateForm.template.creator.id }" />
	<div id="main" class="form_cls"> 
	<ul>
		<li id="description">
			<c:if test="${templateForm.template.id == null}">
				<center>模板导入说明</center>  
			</c:if>
			<c:if test="${templateForm.template.id != null}">
				<center>模板更新说明</center>
			</c:if> 
		</li>
		<li>
			□ 模板样式单文件（文件名后缀：.css .CSS）
		</li>
		<li>
			□ 模板文件（文件名后缀：.htm .html .HTM .HTML）
		</li>	
		<c:if test="${templateForm.template.id == null}">
			<li>
				□ 压缩包中如果包含图片和样式单，需要放在images目录下
			</li>
			<li>
				□ 一次导入，可以导入多个模板文件，即多个zip或者ZIP文件
			</li>
			<li>
				□ 模板文件、图片文件和样式单文件必须打成压缩包(文件名后缀：.zip .ZIP)
			</li>
		</c:if>
		<li>
			□ 模板图片文件（文件名后缀：.jpg .gif .bmp .png .JPG .GIF .BMP .PNG）
		</li>
		<c:if test="${templateForm.template.id != null}">
			<li>
				□ 更新时，如果是模版文件必须和原来的模版文件名称一致,否则失败
			</li>
			<li>
				□ 更新时，可以是一张图片，也可以使一个zip包，也可以是一个模版文件
			</li>
		</c:if>
		<li>
			□ 每次导入模板最大不超过10兆
		</li>

		<!-- 
		<table id="idTB" border="0" height="1px">
	    	<tr id="idTR">
				<td></td>
	  		</tr>
		</table>
		 -->
		<li id="file1"> 
			文件&nbsp;&nbsp;1:&nbsp;&nbsp; 
			<input type="file" name="files(0)" class="btn_small" id="myfile1" style="background:#eeeeee;font:12px;" size="50" onkeydown="return false;" value="浏览"/>
			<input type="button" value="清空" class="btn_small" onclick="clearUrl()"/>
		</li>
		
		<c:if test="${templateForm.template.id == null}">
			<li id="btn">	
				<input class="btn_normal" type="button" value="导入" onclick="checkImport()"  /> 
				&nbsp;&nbsp;	
				<input class="btn_normal" type="button" value="添加" onclick="addFile()" />
			</li>	
		</c:if>
		<c:if test="${templateForm.template.id != null}">
			<li id="btn_update">		
				<input class="btn_normal" type="button" value="更新" onclick="checkImport()" align="middle" />
			</li> 
		</c:if>
		
	</ul>
	</div>
</form>
</body>
</html>
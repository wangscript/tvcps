<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" import="com.baize.common.core.util.StringUtil" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@include file="/templates/headers/header.jsp"%>
<style  type="text/css" media="all">
	
</style>
	<script language=javascript>
        var childDetailOrgWin = "";

    	$(document).ready(function() {
			var infoMessage = document.getElementById("infoMessage").value;
			if(infoMessage == "发送失败") {
				alert("发送失败！");
				return false;
			}
			if(infoMessage == "发送成功") {
				var url = "<c:url value='/sitemessage.do?dealMethod=sendMessageBox&" + getUrlSuffixRandom() + "'/>";
				window.location.href = url;
				return;
			} 
			if(infoMessage == "回复成功") {
				var url = "<c:url value='/sitemessage.do?dealMethod=receiveMessageBox&" + getUrlSuffixRandom() + "'/>";
				window.location.href = url;
				return;
			} 
			
			$("#title").focus();
    		var str;
    		if($("#userfulContacterStr").val() == "" || $("#userfulContacterStr").val() == null) {
    			str = $("#str").val();
        	} else {
				str = $("#userfulContacterStr").val();
            }
			var arr = new Array();
			arr = str.split(",");
			var ids = arr[0];
			var names = arr[1];
			var idArr = new Array();
			idArr = ids.split("^");
			var nameArr = new Array();
			nameArr = names.split("^"); 
			document.getElementById("listUser").length = idArr.length;
			for(var i = 0; i < idArr.length; i++) {
				document.getElementById("listUser").options[i].value = idArr[i];
				document.getElementById("listUser").options[i].text = nameArr[i];
			}
			
		});

		//发送消息
    	function send(){
    		if(document.getElementById('contacterName').value == null || document.getElementById('contacterName').value == "") {
        		alert("请选择收件人!");
        		return false;
        	} else if(document.getElementById('title').value == null || document.getElementById('title').value == "") {
        		alert("请输入标题!");
        		return false;
        	} else {

        		/**var names = $("#contacterName").val();
        		var arrNames = names.split(",");
        		var ids = $("#strOperationId").val();
        		var arrIds = ids.split(",");
        		var namestr = $("#nameStr").val();
        		var arrNamestr = namestr.split(",");

        		var newIds = "";
        		for(var i = 0; i < arrNames.length; i++) {
					for(var j = 0; j < arrNamestr.length; j++){
						if(arrNames[i] == arrNamestr[j]) {
							newIds = newIds + arrIds[j] + ",";
						}
					}
            	}
            	if(newIds == "") {
					alert("当前联系人不存在");
					return false;
                }
            	$("#strOperationId").val(newIds);**/
            	
				$("#dealMethod").val("send");
        		$("#siteMessageForm").submit();
        	}
    	}
        
        //显示所有用户
		function listshow() {
			var t = document.getElementById("title").value;
			var c = document.getElementById('content').value;
			var title = new Array();
			var content = new Array();
			title = t.split(" ");
			content = c.split(" ");
			var message = "";
			var messagec="";
			do{
				message = "";
				for(i=0;i<title.length;i++){
					message += title[i]+"spaces";
				}
				title = message.split(" ");
			}while(title.length>1);
			do{
				messagec = "";
				for(i=0;i<content.length;i++){
					messagec += content[i]+"spaces";
				}
				content = messagec.split(" ");
			}while(content.length>1);
			win = showWindow("categorydetail","添加收件人","<c:url value='/module/message_manager/contacter/contacter_detail.jsp?message="+message+"splits"+messagec+"'/>",293, 0,500,450);
        }
		
		function closeChild() {
			closeWindow(win);
		}

		function change(obj) {
			var id = obj.value;
			var objName = obj.name;
			if(id == "" || id == null ) {
				return false;
			}
			var name = $("#listUser :selected").html();
    		var names = $("#contacterName").val();
    		var ids = $("#strOperationId").val();
    		if(ids == "null" || ids == null) {
				ids = "";
        	}
    		var arr = new Array();
			arr = ids.split(",");
			for(var i = 0; i < arr.length; i++) {
				if(arr[i] == id) {
					alert("联系人已存在!");
					return false;
				}
			}	
			names = names + "," + name;
			ids = ids + "," + id;
			var regExp = /(^,)|(,$)/gi;
			ids = ids.replace(regExp, "");
			names = names.replace(regExp, "");
    		$("#contacterName").val(names);
    		$("#strOperationId").val(ids);

    		//保存联系人名称副本，修改时用$("#nameStr").val(names);
		}

		function clearContent() {
        	$("#contacterName").val('');
    		$("#title").val("");
    		$("#strOperationId").val("");
    		$("#content").val("");
		}
		
		//显示所有常用联系人
		function contacterManage() {
			/*
			var listuser = document.getElementById("listUser");
			alert(listuser.options[0]);
			if(listuser.options.length==0||listuser.options.length==1&&listuser.options[0]==null){
			window.location.href = "<c:url value='/sitemessage.do?dealMethod=showUsefulContacter'/>";
			}else{*/
				var t = document.getElementById("title").value;
				var c = document.getElementById('content').value;
				if(t == null){
					t="";
				}
				if(c == null){
					c='';
				}
				var title = new Array();
				var content = new Array();
				title = t.split(" ");
				content = c.split(" ");
				var message = "";
				var messagec="";
				do{
					message = "";
					for(i=0;i<title.length;i++){
						message += title[i]+"spaces";
					}
					title = message.split(" ");
				}while(title.length>1);
				do{
					messagec = "";
					for(i=0;i<content.length;i++){
						messagec += content[i]+"spaces";
					}
					content = messagec.split(" ");
				}while(content.length>1);

				var names = $("#contacterName").val();
	    		var ids = $("#strOperationId").val();
	    		var regExp = /(^,)|(,$)/gi;
				ids = ids.replace(regExp, "");
				names = names.replace(regExp, "");
				window.location.href = "<c:url value='/sitemessage.do?dealMethod=showUsefulContacter&message="+message+"splits"+messagec+"splits"+names+"splits"+ids+"'/>";
			//}
        }

        function docontent(ob){
			ob.focus();
        }
	</script>
</head>
<body>
	<form id="siteMessageForm" name="siteMessageForm" action="<c:url value="/sitemessage.do"/>" method="post" >
	<div class="currLocation">发送消息</div>
	<input type="hidden" name="dealMethod" id="dealMethod"/>
	<input type="hidden" name="contacterId" id="contacterId">
	<input type="hidden" name="userfulContacterStr" id="userfulContacterStr" value="${siteMessageForm.userfulContacterStr}">
	<input type="hidden" name="id" id="id" value="${siteMessageForm.id}">
	<input type="hidden" name="nodeId" id="nodeId" value="${siteMessageForm.nodeId}">
	<input type="hidden" name="flags" id="flags" value="${siteMessageForm.flags}">
	<input type="hidden" name="infoMessage" id="infoMessage" value="${siteMessageForm.infoMessage}">
	<input type="hidden" name="replyContacterName" id="replyContacterName" value="${siteMessageForm.contacterName}">
	<input type="hidden" name="strOperationId" id="strOperationId" value="${siteMessageForm.strOperationId}">
	<input type="hidden" name="nameStr" id="nameStr" value="${siteMessageForm.contacterName}">
	
	<input type="hidden" id="reTitle" value = "${siteMessageForm.title}">
	<input type="hidden" id="reContent" value = "${siteMessageForm.content}">
 	<div id="container"  class="form_div">	
	<table style="width:100%;">
		<tr>
			<td class="td_left" style="width:10%"><i>*</i>收件人：</td>
			<td style="width:60%">
				<input type="text" id="contacterName" class="input_text_normal" name="contacterName"   value="${siteMessageForm.contacterName}" onkeydown="return false;" valid="string" tip="选择或修改用户请点击右边的按钮">
				<input type="button" class="btn_small" value="选择收件人" onclick="listshow()">
			</td>
			<td style="hight:20px;width:10%">常用联系人：</td>
			<td style="width:10%">
			 	<input  style="hight:20px;margin-top:1px;" type="button" class="btn_small" value="管理" onclick="contacterManage()">	
			</td>
		</tr>
		<tr>
			<td class="td_left" style="width:10%"><i>*</i>标题：</td>
			<td style="width:60%">
				<c:if test="${siteMessageForm.title != null}">
					<input type="text" id="title" class="input_text_normal" name="title"   valid="string" tip="标题不能为空" value="${siteMessageForm.title}" onblur="docontent($('#content'))">
				</c:if>	
				<c:if test="${siteMessageForm.title == null}">
					<input type="text" id="title" class="input_text_normal" name="title"   valid="string" tip="标题不能为空" value="${title }" onblur="docontent($('#content'))">
				</c:if>
			</td>
	 
			<td rowspan="2" align="left" colspan="2" style="width:20%">
				<select id="listUser" ondblclick="change(this)" style="width: 150px; height:360px;overflow:scroll" size="12">
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left"  style="width:10%">消息内容：</td>
			<td style="width:60%">
				<c:if test="${siteMessageForm.content != null}">
					<textarea id="content" class="input_textarea_normal" name="content"  style="height:320px;width:500px;">${siteMessageForm.content}</textarea>
				</c:if>
				<c:if test="${siteMessageForm.content == null}">
					<textarea id="content" class="input_textarea_normal" name="content"  style="height:320px;width:500px;">${content}</textarea>
				</c:if>
			</td>
			<td  style="width:10%"></td>
		</tr>
		<tr>
			<td colspan="2" align="center"> 
				<input  type="button"  value="发送" class="btn_normal"  onClick="send()" />		
				&nbsp;&nbsp;&nbsp;&nbsp;	
				<input  type="button"  value="清空" class="btn_normal" onClick="clearContent()"/>
			</td>
		</tr>
	</table>
	</div>
</form>	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<title>选择收件人</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
<style  type="text/css" media="all">
	#contacter {
	 text-align:right;
	 float:right;
	 margin-right:10px;
	 margin-top:10px;
	}
	#treeboxbox_tree {
	 float:left;
	 margin-left:5px;
	 margin-top:10px;
	}
</style>

	<script type="text/javascript"><!--

		//判断是否有选择用户
		var flag = "no";
		$(document).ready(function() {	

			var title = document.getElementById('title').value;
			var content = document.getElementById("content").value;
			if(document.getElementById("message").value == "添加收件人") {
		//		var idAndNameStr = document.getElementById("userfulContacterStr").value;
		//		var contacterName = document.getElementById("contacterName").value;
				
				rightFrame.window.location.href = "<c:url value='/sitemessage.do?dealMethod=transforward&strOperationId=${siteMessageForm.strOperationId}'/>";
				top.closeWindow(rightFrame.getWin());	
			}
		});
	
		function sure(){
			if(flag == "no") {
				alert("您未选择收件人");
				return false;
			}
       		var frameobj = document.frames("rightFrame");
       		//已选择的收件人ids
            var strOperationId = frameobj.document.getElementById("checkedIds").value;
                        
			if(strOperationId == null || strOperationId == "" || strOperationId == "0") {
				alert("您未选择收件人");
				return false;
			}
       		window.location.href = "<c:url value='/sitemessage.do?dealMethod=addContacterNameStr&strOperationId="+strOperationId+"&check=" + document.getElementById("check").value+"'/>";
    	}
	
		function tree_onclick(node) {
			/*
			if(node.id == 0 || node.id == "" || node.id == null) {
				document.frames("rightFrame").document.location = "<c:url value='/sitemessage.do?dealMethod=findContacter&orgId=0'/>";
				flag = "yes";	
			//	alert("");
			//	document.frames("rightFrame").document.location = "<c:url value='/sitemessage.do?dealMethod=findContacter&orgId=" + node.id + "'/>";
			} else {
				document.frames("rightFrame").document.location = "<c:url value='/sitemessage.do?dealMethod=findContacter&orgId=" + node.id + "'/>";
				flag = "yes";
			}*/
		}

        var nodes="";
		function  tree_oncheck(node){	
			var event = window.event;
			var obj = event.srcElement;
			if(!node.leaf){
				node._io = open ;
			}
			if(nodes.indexOf(node.id)<0 && obj.checked){
				nodes+=node.id+"s";
			} else if(!obj.checked && nodes.indexOf(node.id)>0){
				nodes=nodes.replace(node.id+"s","");
			}
		    if(node.id == 0 || node.id == "" || node.id == null||nodes==null||nodes==""){
			  	 document.frames("rightFrame").document.location = "<c:url value='/sitemessage.do?dealMethod=findContacter&orgId=0'/>";
				 flag = "yes";	
		    }else{  
				 document.frames("rightFrame").document.location = "<c:url value='/sitemessage.do?dealMethod=findContacter&orgId="+nodes+ "'/>";
				 flag = "yes";  
		    }
		}
		function changetype() {
			//保存至常用联系人
			document.getElementById("check").value = "1";
		}
	
		// 退出 
		function button_quit_onclick(ee){	
			top.closeWindow(rightFrame.getWin());
		}
	--></script> 

</head>

<%
	//解决乱码问题!!!
	request.setCharacterEncoding("utf-8");
	//String t = (String)request.getAttribute("title");
	//String c = (String)request.getAttribute("content");
	
	String title = "";
	String content = "";
	String t = "";
	String c = "";
	String message = request.getParameter("message");
	if(message == null){
		title = null;
		content = null;
		t = null;
		c = null;
	}else if("".equals(message.trim())){
		title = "";
		content = "";
		t = "";
		c = "";
	}else {
		System.out.println(message);
		String[] messages = message.split("splits");
		if(messages.length <= 1){
			title = "";
			content = "";
			t = "";
			c = "";
		}else{
			title = messages[0];
			content = messages[1];
			String tt[] = title.split("spaces");
			for(int i=0;i<tt.length;i++){
				t += tt[i]+" ";
			}
			String ct[] = content.split("spaces");
			for(int j=0;j<ct.length;j++){
				c += ct[j]+" ";
			}
		}
	}

	if(t == null){
		t = (String) session.getAttribute("title");
		session.removeAttribute("title");
	}
	if(c == null){
		c = (String) session.getAttribute("content");
		session.removeAttribute("content");
	}
	
	/*
	System.out.println(title+"---------------------------------+==============");
	byte bt[] = title.getBytes("ISO-8859-1");
	byte bc[] = content.getBytes("ISO-8859-1");
	String t = new String(bt,"utf-8");
	String c = new String(bc,"utf-8");
	System.out.println(t+"---------------------------------+==============");
	*/
	
	session.setAttribute("title",t);
	session.setAttribute("content",c);
%>
<body>
	<form id="siteMessageForm" action="sitemessage.do" method="post" name="siteMessageForm">
		<input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" id="strOperationId" name="strOperationId" value="${siteMessageForm.strOperationId}"/>
		<input type="hidden" name="message" id="message" value="${siteMessageForm.infoMessage}"/>
		<input type="hidden" name="title" id="title" value="${title }"/>
		<input type="hidden" name="content" id="content" value="${content }"/>
		<table style="font:12px;">
			<tr>
				<td class="td_left"><input type="checkbox" id="check" name="check" value="0" onchange="changetype()"><i>&nbsp;</i>添加到常用联系人</td>
			</tr>
		</table>
		<div>
			<div id="treeboxbox_tree" style="width:300px; height:340px;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
           <complat:tree unique="user" checkbox="true"  treeurl="../../../organization.do?dealMethod=gettree" /> 
			</div> 
			<div id="contacter" style="width:130px; height:340px;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
				<iframe  style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1" name="rightFrame" id="rightFrame"  frameborder="0" scrolling="auto">
				</iframe>
			</div>
			<center>
					<input  type="button"  value="确定" class="btn_normal"  onClick="sure()" >		
					&nbsp;&nbsp;&nbsp;&nbsp;	
					<input  type="button"  value="退出" class="btn_normal"  name="button_quit" onClick="javascript:button_quit_onclick(this);" ></center>
		</div>
	</form>
</body>
</html>

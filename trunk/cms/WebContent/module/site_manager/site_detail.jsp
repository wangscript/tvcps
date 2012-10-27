<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增网站</title>
<meta   http-equiv="Expires"   CONTENT="0">     
  <meta   http-equiv="Cache-Control"   CONTENT="no-cache">     
  <meta   http-equiv="Pragma"   CONTENT="no-cache"> 
<%@include file="/templates/headers/header.jsp"%>
  <script type="text/javascript" src="<c:url value='/script/ChineseAscii_pack.js'/>" ></script>
<style>
	#main td{
		padding:3px 10px 3px 5px;
		background-color:white; 
		font-size:12px;
		font-family: "宋体",Arial;
		
	}
</style>
  <script   language="JavaScript">     
  <!--     
  javascript:window.history.forward(1);     
  //-->     
  </script>
<script type="text/javascript">

	var childSiteWin;	
	$(document).ready(function() {
		$("#site").focus();
		if(document.getElementById("site").value != null || document.getElementById("site").value != "") {
			document.getElementById("oldSiteName").value = document.getElementById("site").value;
		}
		$("#site").blur(function() {
			var siteStr = $(this).val();
			var temp=siteStr.split(''),i=-1,len=temp.length;
		    while(++i<len){
		    	temp[i]=(temp[i].charCodeAt()<1000||temp[i].charCodeAt()>60000)?temp[i]:lookUpWord(temp[i]);
		    }
		 
			var	publishDir = $("#publishDir").val();
		   if($("#siteid").val() == null || $("#siteid").val() == ""){
			    var index = publishDir.indexOf("/wwwroot");
			    if (index > -1) {
			    	publishDir = publishDir.substring(0, index) ;
			    	publishDir = publishDir.replace("/"+app,"");	
			    	$("#publishDir").val(publishDir + "/wwwroot/" + temp.join(""));
			    }
		   }
		});
		if ($("#publishWay").val() == "local" || $("#publishWay").val() == "") {
			$("#remoteTr").hide();
			$("#ftpTr").hide();
			$("#ftpTr1").hide();
			$("#localTr").show();
		} else if ($("#publishWay").val() == "remote"){
			$("#localTr").hide();
			$("#ftpTr").hide();
			$("#ftpTr1").hide(); 
		}else if ($("#publishWay").val() == "ftp"){
			$("#localTr").hide();		
		}
		
			
	//	}
		if ($("#publishDir").val() != null && $("#publishDir").val() !="" && $("#publishWay").val() == "ftp") {			
			$("#publishDir").val(document.getElementById("ftpFilePath").value);
		}else{		
			if($("#publishDir").val() == null || $("#publishDir").val() == ""){
				var path = '${appRealPath}';
				path = path.replace("/"+app,"");				
				$("#publishDir").val(path+'/wwwroot');
			}
			
		}
		$("#publishWay").bind("change", function() {			
	
			if ($("#publishWay").val() == "local" || $("#publishWay").val() == "") {
				$("#remoteTr").hide();
				$("#ftpTr").hide();
				$("#ftpTr1").hide();
				$("#localTr").show();
			} else if ($("#publishWay").val() == "remote"){
				$("#localTr").hide();
				$("#ftpTr").hide();
				$("#ftpTr1").hide();
				$("#remoteTr").show();
			}else if ($("#publishWay").val() == "ftp"){
				$("#localTr").hide();	
				$("#remoteTr").show();				
				$("#ftpTr").show();
				$("#ftpTr1").show();		 
			}
			if ($("#publishDir").val().isEmpty() && $("#publishWay").val() != "ftp") {
				var path = '${appRealPath}';
				path = path.replace("/"+app,"");		
				$("#publishDir").val(path+'/wwwroot');
			}else{			
				$("#publishDir").val(document.getElementById("ftpFilePath").value);
			}
		});
		/*var urlSuffix = "${siteForm.site.urlSuffix}";
		var len = document.getElementById("urlSuffix").options.length;
		for(var i = 0; i < len; i++) {
			var value = document.getElementById("urlSuffix").options[i].value;
			if(value == urlSuffix){
				document.getElementById("urlSuffix").options[i].selected = true;
				break;
			}
		}*/
	}); 

	function saveSite(obj) {
		var siteName = document.getElementById("site").value;
		var domainName = document.getElementById("domainName").value;
		var homePageTitle = document.getElementById("homePageTitle").value;
		if(siteName == null || siteName == "" ) {
			alert("请输入网站名称");
			return false;
		}
		if(domainName == null || domainName == "" ) {
			alert("请输入域名！");
			return false;
		}
		if(homePageTitle == null || homePageTitle == "") {
			alert("请输入主页标题！");
			return false;
		}
	/*	
		if($("#siteNull").val() == 1) {
			$("#dealMethod").val("addSite");
			$("#siteForm").submit(); 
		} else {

*/
		if($("#publishWay").val() == "ftp"){			
			$("#publishDir").val(document.getElementById("ftpFilePath").value);
		}

		if($("#siteNull").val() == 1) {	
			$("#dealMethod").val("addSite");
		}else{
			$("#dealMethod").val("add");
		}
			var options = {	 	
	 		    	url: "<c:url value='/site.do'/>",
	 		    success: function(msg) { 
	 		    	// 第一次添加网站失败
		 		    if(msg.indexOf("#")>0){
		 		   		// 添加网站成功		 
		 		    	if(msg.split("#")[0] == "添加网站成功") {
		 		    		rightFrame.window.location.href = "<c:url value='/site.do?dealMethod=addSiteSuccess'/>";
		 					closeWindow(rightFrame.getWin());
		 		    	} else {
			 		    	if(msg.split("#")[0] != null && msg.split("#")[0] != ""){
								alert(msg.split("#")[0]);
			 		    	}
							$("#site").focus();
		 		    	} 
		 		    }else{
		 		    	// 添加网站成功		 
		 		    	if(msg == "添加网站成功") {
		 		    		rightFrame.window.location.href = "<c:url value='/site.do?dealMethod='/>";
		 					closeWindow(rightFrame.getWin());
		 		    	} else {
							if(msg.split("#")[0] != null && msg.split("#")[0] != ""){
								alert(msg.split("#")[0]);
							}
							$("#site").focus();
		 		    	} 
		 		    }		    		
	 		    },
	 		   error: function(){
	 	 		   alert("保存失败!");
	 		    }
	 		};
			$('#siteForm').ajaxSubmit(options);	
		//}
		
	}

	function button_update_onclick() {
		var siteName = document.getElementById("site").value;
		var domainName = document.getElementById("domainName").value;
		var homePageTitle = document.getElementById("homePageTitle").value;
		if(siteName == null || siteName == "" ) {
			alert("请输入网站名称！");
			return false;
		}
		if(domainName == null || domainName == "" ) {
			alert("请输入域名！");
			return false;
		}
		if(homePageTitle == null || homePageTitle == "") {
			alert("请输入主页标题！");
			return false;
		}
		$("#dealMethod").val("modify");
		if($("#publishWay").val() == "ftp"){			
			$("#publishDir").val(document.getElementById("ftpFilePath").value);
		}
		var options = {	 	
 		    	url: "<c:url value='/site.do'/>",
 		    success: function(msg) { 
 		    	if(msg == "修改网站成功") {
 		    		rightFrame.window.location.href = "<c:url value='/site.do?dealMethod='/>";
 		    		closeWindow(rightFrame.getWin()); 
 		    	} else {
					alert(msg);
					$("#siteForm").resetForm(); 
					$("#site").focus();
 		    	} 		    		
 		    } 
 		};
		$('#siteForm').ajaxSubmit(options);	
	}
	
	function closeChild() {
		closeWindow(win);
	}

	function getKey(){
		var event = window.event;
		if(event.keyCode == 9){
			return true;
		}
		return false;
	}

</script>
</head>
<body>
<br>
<br>
 	<form action="<c:url value="/site.do"/>" method="post" name="siteForm" id="siteForm">	
		 <input type="hidden" name="dealMethod" id="dealMethod" />
		 <input type="hidden" name="message" id="message" value="${siteForm.infoMessage}"/>
         <input type="hidden" name="site.id" id="siteid" value="${siteForm.site.id }"/>
         <input type="hidden" name="siteNull" id="siteNull" value="<%=request.getParameter("siteNull") %>"/>	
		 <input type="hidden" name="oldSiteName" id="oldSiteName"/>
		 
 		 <div class="form_div"> 
			 <table width="550px;" id="main" align="center">
		        <tr>
		          <td width="103px;" nowrap align="right"><i>*</i>网站名称：</td>
		          <td width="400px;" nowrap><input name="site.name" type="text" class="input_text_normal" id="site" value="${siteForm.site.name}" style="width:370px;"  valid="string" tip="请输入任意字符串(不能为空)"/></td>
		        </tr>
		        <tr>
		          <td nowrap align="right"><c:if test="${siteForm.site.id != null}"></c:if> <c:if test="${siteForm.site.id == null}"></c:if> <i>*</i>域&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
		          <td nowrap><input type="text" class="input_text_normal" name="http" value="http://" style="width:68px" onkeydown="return getKey();" />
		          <input type="text" class="input_text_normal" name="site.domainName" id="domainName" value="${siteForm.site.domainName }"   style="width:295px;"  valid="string" tip="请输入任意字符串(不能为空)"/></td>
		        </tr>
		       
			   <!-- 	
			   <tr>
		          <td height="23px;" align="right">url后缀：</td>
		          <td><select id="urlSuffix" style="width:255px;" class="input_select_normal" name="site.urlSuffix">
		            <option value="php">php</option>
		            <option value="jspa">jspa</option>
		            <option value="htm">htm</option>
		            <option value="asp">asp</option>
		            <option value="aspx">aspx</option>
		          </select></td>
		        </tr>
		        <tr>
		          <td height="23px;" align="right">页面编码：</td>
		          <td><select id="site.pageEncoding" class="input_select_normal" name="site.pageEncoding" style="width:255px;">
		            <c:if test="${siteForm.site.pageEncoding != null}"> 
						<c:if test="${siteForm.site.pageEncoding == 'UTF-8'}">
						<option value="UTF-8" selected>UTF-8</option>
						<option value="GBK">GBK</option>
		            	</c:if> 
						<c:if test="${siteForm.site.pageEncoding == 'GBK'}">
							<option value="UTF-8">UTF-8</option>
							<option value="GBK" selected>GBK</option>
						</c:if> 
					</c:if> 
					<c:if test="${siteForm.site.pageEncoding == null}">
						<option value="UTF-8">UTF-8</option>
						<option value="GBK">GBK</option>
		            </c:if>
		          </select></td>
		        </tr>
		         -->

	
		        <tr>
		          <td height="23px;" align="right"><i>*</i>主页标题：</td>
		          <td><input name="site.homePageTitle" type="text" class="input_text_normal" id="homePageTitle" value="${siteForm.site.homePageTitle}"  style="width:370px"  valid="string" tip="请输入任意字符串(不能为空)"/></td>
		        </tr>
		        <tr>
		          <td height="160px;" align="right">网站描述：</td>
		          <td><textarea style="width:460px; height:160px;"  class="input_textarea_normal" id="description" name="site.description">${siteForm.site.description}</textarea></td>
		        </tr>
		        <tr>
		          <td height="23px;" align="right">发布方式：</td>
		          <td>
		          	<select id="publishWay" name="site.publishWay" class="input_select_normal" style="width:100px;">
		          		<c:choose>
		          			<c:when test="${siteForm.site.publishWay == 'remote'}">
		          				<option value="local">本地</option>
		          				<option value="remote" selected="selected">socket发布</option>
								<option value="ftp">ftp发布</option>
							</c:when>
							<c:when test="${siteForm.site.publishWay == 'ftp'}">
		          				<option value="local">本地</option>
		          				<option value="remote">socket发布</option>
								<option value="ftp"  selected="selected">ftp发布</option>					
							</c:when>
							<c:otherwise>
								<option value="local"  selected="selected">本地</option>
		          				<option value="remote">socket发布</option>
								<option value="ftp">ftp发布</option>
							</c:otherwise>
						</c:choose>
		          	</select>
		          </td>
		        </tr>
		        <tr id="localTr">
		          <td height="23px;" align="right">发布目录：</td>
		          <td><input id="publishDir" name="site.publishDir" type="text" class="input_text_normal" value="${siteForm.site.publishDir}" style="width:370px"/></td>
		        </tr>
		        <tr id="remoteTr">
		          <td height="23px;" align="right">服务器IP：</td>
		          <td>
		          	<input id="remoteIp" name="site.remoteIP" type="text" class="input_text_normal" valid="ip" empty="true" value="${siteForm.site.remoteIP}" style="width:300px;" maxlength="15" />
		          	端口：<input id="remotePort" name="site.remotePort" type="text" class="input_text_normal" valid="num" empty="true" style="width:100px;" value="${siteForm.site.remotePort}" />
		          </td>
		        </tr>
				<tr id="ftpTr">
		          <td height="23px;" align="right">用户名：</td>
		          <td>
		          	<input id="ftpUserName" name="site.ftpUserName" type="text" class="input_text_normal"   value="${siteForm.site.ftpUserName}" style="width:100px;" />
		          	密码：<input id="ftpPassWord" name="site.ftpPassWord" type="text" class="input_text_normal"  style="width:100px;" value="${siteForm.site.ftpPassWord}" />					
		          </td>
		        </tr>
				<tr id="ftpTr1">
				  <td height="23px;" align="right">ftp远程目录：</td>
		          <td>
		          	<input id="ftpFilePath" name="site.ftpFilePath" type="text" class="input_text_normal"   value="${siteForm.site.ftpFilePath}" style="width:300px;" />		          						
		          </td>
		        </tr>
		        <tr>
		          <td height="23px;" colspan="2" align="center">
		          
				    <!-- 如果是修改网站页面 start -->
		          	<c:if test="${siteForm.site.id != null}"> 
				         <input type="button" class="btn_normal" value="修改" onClick="button_update_onclick()"/>
			        </c:if>
		                <!-- 如果是修改网站页面 end -->
		                <!-- 如果是添加网站页面 start -->
		                  <c:if test="${siteForm.site.id == null}">
		                <input type="button" class="btn_normal" value="保存" onClick="saveSite()"/>
		                  </c:if>
		                <!-- 如果是添加网站页面 end -->
		            </td>
		        </tr>
			</table>
 		<p>&nbsp;</p>
 		</div>

</body>
</html>
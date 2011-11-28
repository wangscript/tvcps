<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>CCMS系统管理页面</title>	
	<script type="text/javascript" src="<c:url value='/script/jquery-1.2.6.js'/>" ></script>
	<script type="text/javascript" src="<c:url value="/module/message_manager/class_msn_message.js"/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>"/>
	<style type="text/css">
	    body {
	    	margin:0px; padding:0px;
	    }
	    * {
	    	font-size:12px;
			font-family: "宋体",Arial;
	    }
		#head {
			height:59px;
			background:url("../images/main/head/tu_10.gif") repeat-x;
		}
		#right {
			width:130px;
			background:url("../images/main/head/tu_10.gif") repeat-x;
		}

		#left1 {
			margin:0 0 10px 30px;
			margin:0 0 10px 25px;
		}
		#left2 {
			margin:0 0 0 45px;
			margin:0 0 0 40px;
		}

		#left3 {
			margin:0 0 0 40px;
			margin:0 0 0 35px;
		}
		
	   .padbottom {
	     padding-bottom:12px;
	     cursor:pointer;
		padding-left:5px;}
	a.twhite {color:#FFFFFF;  text-decoration:none; padding:10px; white-space:nowrap;}
	a.twhite hover{color:#FFFFFF;  text-decoration:none; white-space:nowrap;}
	</style>
	<%
		//当前页面地址
		String pageurl = request.getRequestURL().toString();
		pageurl = pageurl.substring(0,pageurl.lastIndexOf("/"));
	%>
	<script type="text/javascript">
		var http_request = false; 
	    function makeRequest(url) { 
	        http_request = false; 
	        if (window.XMLHttpRequest) { // Mozilla, Safari,... 
	            http_request = new XMLHttpRequest(); 
	            if (http_request.overrideMimeType) { 
	                http_request.overrideMimeType('text/xml'); 
	            } 
	        } else if (window.ActiveXObject) { // IE 
	            try { 
	                http_request = new ActiveXObject("Msxml2.XMLHTTP"); 
	            } catch (e) { 
	                try { 
	                   http_request = new ActiveXObject("Microsoft.XMLHTTP"); 
	                } catch (e) {
	                } 
	            } 
	        } 
	        if (!http_request) { 
	            alert('Giving up :( Cannot create an XMLHTTP instance'); 
	            return false; 
	        } 
	        http_request.onreadystatechange = alertContents; 
	        http_request.open('GET', url, true); 
	        http_request.send(null);  
	    } 

	    function alertContents() { 
	        if (http_request.readyState == 4) { 
	            if (http_request.status == 200) { 
	                window.close(); 
	            } else { 
	                alert('在请求中遇到一个问题.'); 
	            } 
	        } 
	    } 

	    window.onunload = function() { 
	    	//if ((event.clientX>document.body.clientWidth&&event.clientY<0||event.altKey) || (event.clientY > document.body.clientHeight || event.altKey)){
		    if(window.screenLeft>10000) {	
		        makeRequest("<%=pageurl%>/accountUnbound.jsp"); 
	    	} else {
	    	} 
	    }
		

		//判断踢人 
		function kick()	{
			if(window.ActiveXObject) {
				xml = new ActiveXObject("Microsoft.XMLHTTP");
			} else if(window.XMLHttpRequest) {
				xml = new XMLHttpRequest();  
			}	
			var post=" ";//构造要携带的数据 
			xml.open("POST", "<%=pageurl%>/top_kick.jsp",false);//使用POST方法打开一个到服务器的连接，以异步方式通信 
			xml.setRequestHeader("content-length",post.length); 
			xml.setRequestHeader("content-type","application/x-www-form-urlencoded");
			xml.onReadyStateChange = function(){ 
				if(xml.readyState==4){
					if(xml.status==200){
						var res = xml.responseText;//接收服务器返回的数据
						if(res=="KICK"){
							alert('您已在另一台计算机上登录了本系统，请点击“确定”重新登录！');
							top.location.href='../';
							return;
						}
					}
				}
			}
			xml.send(post);//发送数据
			xml.abort();
			setTimeout("kick()",10000*1);	//每隔60*1秒钟轮询一次
		}
	</script>

	<script type="text/javascript">	  
		$(document).ready(function(){
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			var day = date.getDate();
			var Week = ['日','一','二','三','四','五','六'];
			var week = Week[date.getDay()];
			var displayDate = year + "年" + month + "月" + day + "日&nbsp;&nbsp;星期" + week;   
			$("#systemDate").html(displayDate);
			
		//	test = setInterval("receiveMessage(),kick();",6000);
			
			document.getElementById("messageNum").value = parent.document.getElementById("messageNum").value;
			var personalName = parent.document.getElementById("personalName").value;
			var roleName = parent.document.getElementById("roleName").value;
			if(roleName == null){
				roleName = "";
			}
			document.getElementById("personalName").innerHTML =	personalName;
			document.getElementById("roleName").innerHTML = roleName;
			var messageNum = document.getElementById("messageNum").value;
			if(messageNum > 0) {
	        	document.getElementById("messageNum").value = messageNum;
	            document.getElementById("message").innerHTML = "短信(<font color=\"#FF0000\">" + messageNum + "</font>)";
	        } else {
	        	document.getElementById("messageNum").value = 0;
	            document.getElementById("message").innerHTML = "短信(0)";
		    }
			 
		    //如果父亲页面有系统设置这个菜单，则让top页面显示
			if(parent.document.getElementById("m010")){			
				if(parent.document.getElementById("m010").value == "m010"){
					document.all.topm101.style.display = "";
					document.all.topm102.style.display = "";
				} 
			}
		 

		});

		function countMessageNum(){
			var messageNum = document.getElementById("messageNum").value - 1;
			if(messageNum > 0) {
	        	document.getElementById("messageNum").value = messageNum;
	            document.getElementById("message").innerHTML = "短信(<font color=\"#FF0000\">" + messageNum + "</font>)";
	        } else {
	        	document.getElementById("messageNum").value = 0;
	            document.getElementById("message").innerHTML = "短信(0)";
		    }
			return document.getElementById("messageNum").value;
		}

		function receiveMessage(){	
			$.ajax(
				{	
					url:"<c:url value='/messageTips.do?dealMethod=showMessageTips'/>",
					cache:false,
					type:"post",
					success:function(messageNum){	
		                var cnt = parseInt(messageNum);
		                cnt = (cnt == "") ? 0 : cnt;		       
						if(cnt > 0){
							document.getElementById("message").innerHTML = "短信(<font color=\"#FF0000\">" + cnt + "</font>)";
						var MSG1 = new CLASS_MSN_MESSAGE("${appName}","aa",200,120,"短消息提示：","您有" + messageNum + "个未读消息",messageNum,"点击查看!");  
							MSG1.rect(null,null,null,screen.height-50); 
							MSG1.speed = 10; 
							MSG1.step = 5; 
							MSG1.show();  
						}
					},
					error:function(){
						clearInterval(test);
					}
				}
			);
		}
	</script>
</head>
<body>
<input type="hidden" name="messageNum" id="messageNum"/>
<table id="head" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align=left valign="top" id="left" width="80%">
			<table width="580" height="46" align="left" cellpadding="0" cellspacing="0"  border="0">
				<tr>
					<td width="300" align="left" valign="middle" id="left1" ><img src="../images/main/head/tu_02.gif" width="136" height="43" border="0" id="left1"/></td>
					<td width="21"valign="top"><img src="../images/main/head/tu_04.gif" width="22" height="41" id="left2"/></td>
					<td width="100"  id="topm111"   align="left" valign="bottom" class="padbottom" onClick="parent.changeSite()"><a href="#" class="twhite">系统切换</a></td>
					<td width="28" id="topm112"   valign="top" ><img src="../images/main/head/tu_06.gif" width="28" height="44" id="left3"/></td>						
					<td width="100" id="topm101" style="display: none" align="left" valign="bottom" class="padbottom" onClick="parent.systemSet()"><a href="#" class="twhite">系统设置</a></td>
					<td width="26" id="topm102" style="display: none" valign="top" ><img src="../images/main/head/tu_08.gif" width="25" height="42" id="left3"/></td>
					<td width="100" align="left" valign="bottom"  class="padbottom" onClick="parent.messageManager()"><a class="twhite" ><div id="message">短信(0)</div></a></td>
					<td width="30" id="topm103"  valign="middle"><img src="../images/main/head/tu_14.gif" id="left3"/></td>
					<td width="100" align="left" valign="bottom"  class="padbottom" onClick="parent.currentLineUser()"><a class="twhite" ><div id="message">在线用户</div></a></td>
					<td>&nbsp;</td>
				</tr>
	  		</table>
	  	</td>
		<td width="3%" align="left" valign="top"  id="right">
			<table  border="0" cellpadding="0" style="padding-top: 5px"  cellspacing="0">
			  <tr>
				<td valign="top" align="left">
					<img src="../images/main/head/tu_12.gif" width="31" height="46">
			    </td>
			  </tr>
			</table>
	  	</td>
		<td width="17%">
			<table  border="0" cellpadding="0" style="padding-top: 5px"  cellspacing="0">
				<tr align="left">
					<td align="left" style="padding-top: 5px" >				 
						<a class="twhite">
							您好:<b><span id="personalName" style="line-height:10px;margin-top:20px;"></span></b>
						</a>				 						
					</td>	
					<td  style="padding-top:5px">
						<a class="twhite" style="cursor:hand;" onClick="parent.menuManager()" ><font color="#ffde00">【个人设置】</font></a>
					</td>	
			 	</tr>
				<tr align="left">			
					<td align="left" valign="middle" style="padding-top:5px" >
						<a class="twhite">
							&nbsp;&nbsp;<span style="line-height:10px;margin-top:20px;" id="systemDate"></span> <span id="roleName" style="display:none"></span>
						</a>	
					</td>
					<td style="padding-top: 5px" >&nbsp;</td>
				</tr>
			</table> 
		</td>
	</tr>
</table>
</body>
</html>
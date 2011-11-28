<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>属性设置</title>
	<%@include file="/templates/headers/header.jsp"%>
	<script type="text/javascript">
	//加载设置
			$(document).ready(function loadSet(){
				if("${guestBookForm.infoMessage}" == "发布留言本目录成功"){
					alert("${guestBookForm.infoMessage}");
				}
				var opentype =$("#openType").val();
				//开放类型
					if(opentype=="0"){//关闭
						document.guestBookForm.openType1[0].checked=true;
					}else if(opentype=="1"){//开放
						document.guestBookForm.openType1[1].checked=true;
					}else if(opentype=="2"){//定时开放
						document.guestBookForm.openType1[2].checked=true;
						var openhour = $("#openHour").val();//开放时间
						$.each($("#timeId option"), function(i, n){//ID遍历
							if (n.value == openhour) {
								$(n).attr("selected", "selected");
							}
						});
						var openminute=$("#openMinute").val();
						$.each($("#minuteId option"),function(i,n){
								if(n.value==openminute){
										$(n).attr("selected","selected");
									}
						});	
						var openhour1=$("#openHour1").val();
						$.each($("#timeId1 option"),function(i,n){
								if(n.value==openhour1){
									$(n).attr("selected","selected");
								}
						});
						var openninute1=$("#openMinute1").val();			
						$.each($("#minuteId1 option"),function(i,n){
							if(n.value==openninute1){
								$(n).attr("selected","selected");
							}
						});
					}else if(opentype=="3"){//达到数量时候关闭
						document.guestBookForm.openType1[3].checked=true;
						var rowCount=$("#leaveCount").val();
						var msg=$("#leaveMsg").val();
						$("#leaveMsgCount").val(rowCount);
						$("#leaveMsgarea").val(msg);						
					}
				var audit=$("#isAudit").val();
				if(audit=="0"){//是否审核
					document.guestBookForm.audit[0].checked=true;
				}else{
					document.guestBookForm.audit[1].checked=true;
				}
				var isWord=$("#isPublish").val();
				if(isWord=="0"){//有敏感词是否发布
					document.guestBookForm.publish[0].checked=true;
				}else{
					document.guestBookForm.publish[1].checked=true;
				}
				
			});
			function onselectChange(){
					document.getElementById('timeId1').options.length = 0;
					
					for(var i=parseInt(document.getElementById("timeId").value);i<=23;i++){
						document.getElementById('timeId1')[document.getElementById('timeId1').options.length]=new Option(i,i);	
					}
			}
			//保存设置
			function saveSet(){
				if(document.guestBookForm.openType1[0].checked){
					$("#openType").val("0");
				}else if(document.guestBookForm.openType1[1].checked){
					$("#openType").val("1");
				}else if(document.guestBookForm.openType1[2].checked){
					$("#openType").val("2");
						var timeHour = document.getElementById("timeId").options[document.getElementById("timeId").selectedIndex].value;
						$("#openHour").val(timeHour);
					
						var timeMinute= document.getElementById("minuteId").options[document.getElementById("minuteId").selectedIndex].value;
						$("#openMinute").val(timeMinute);
					
						var timeHour = document.getElementById("timeId1").options[document.getElementById("timeId1").selectedIndex].value;
						$("#openHour1").val(timeHour);
					
						var timeMinute1= document.getElementById("minuteId1").options[document.getElementById("minuteId1").selectedIndex].value;
						$("#openMinute1").val(timeMinute1);
									
				}else if(document.guestBookForm.openType1[3].checked){
					$("#openType").val("3");
					var m=$("#leaveMsgCount").val();
					if(m.trim()==""||parseInt(m.trim())<0||isNaN(m)){
						alert("请填写留言限定条数,必须是数字！");
						return false;
					}else{
						$("#leaveCount").val(m);
					}
					var msg=$("#leaveMsgarea").val();
					if(msg.trim()==""){
						alert("请填写提示信息");
						return false;
					}else{
						$("#leaveMsg").val(msg);
					}
				}
				if(document.guestBookForm.audit[0].checked){
					$("#isAudit").val("0");
				}else if(document.guestBookForm.audit[1].checked){
					$("#isAudit").val("1");
				}
				if(document.guestBookForm.publish[0].checked){
					$("#isPublish").val("0");
				}else if(document.guestBookForm.publish[1].checked){
					$("#isPublish").val("1");
				}
				//if(confirm("确定保存刚刚修改的设置？")){
					$("#dealMethod").val("savepropertySet");
					$("#guestBookForm").submit();
				//}
			}
			function checkNum(){
				if(event.keyCode<48||event.keyCode>57){
						event.returnValue=false;
				}
			}

			function publishRss1() {
				$("#dealMethod").val("publishGuestBook");
				document.guestBookForm.submit();
			}
	</script>
</head>
<body>
</div>
<form action="<c:url value='/guestBook.do'/>" method="post" name="guestBookForm" id="guestBookForm">
<input type="hidden" name="dealMethod" id="dealMethod"/>
<input type="hidden" name="message" id="message"/>
<input type="hidden" name="openType" id="openType" value="${guestBookForm.openType}"/>
<input type="hidden" name="isAudit" id="isAudit" value="${guestBookForm.isAudit}"/>
<input type="hidden" name="isPublish" id="isPublish" value="${guestBookForm.isPublish}"/>
<input type="hidden" name="openHour" id="openHour" value="${guestBookForm.openHour}"/>
<input type="hidden" name="openMinute" id="openMinute" value="${guestBookForm.openMinute}"/>
<input type="hidden" name="openHour1" id="openHour1" value="${guestBookForm.openHour1}"/>
<input type="hidden" name="openMinute1" id="openMinute1" value="${guestBookForm.openMinute1}"/>
<input type="hidden" name="leaveCount" id="leaveCount" value="${guestBookForm.leaveCount}"/>
<input type="hidden" name="leaveMsg" id="leaveMsg" value="${guestBookForm.leaveMsg}"/>
<div class="currLocation">功能单元→ 留言本→ 基本设置→ 属性设置</div>
<table cellpadding="0" cellspacing="0" style="width:95%;height:auto;;margin-top:10px;border:2px #A4B5A3 solid;margin:10px auto 0px auto">
<tr>
	<td class="textAlign color1 color2 color3">系统开放或关闭：</td>
	<td>
		<table cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td><input type="radio" name="openType1" id="closeAll" class="color1"/>关闭</td>
			</tr>
			<tr>
				<td><input type="radio" name="openType1"  id="allOpen" class="color1"/>全天开放</td>
			</tr>
			<tr>
				<td>
					<input type="radio"  name="openType1"  id="timeOutClose" class="color1"/>定时开放，每天
					<select id="timeId" onchange="onselectChange()">
					<option value="0">0</option>
					<%for(int i=1;i<=23;i++){%>
						<option value="<%=i %>"><%=i %></option>
					<%} %>
					</select>点
					<select id="minuteId">
					<option value="0">00</option>
					<%for(int i=1;i<=59;i++){
							if(i<10){%>
							<option value="<%=i %>">0<%=i %></option>
					<%}else{%>
						
						<option value="<%=i %>"><%=i %></option>
					<%}} %>
					</select>分至
					<select id="timeId1">
					<option value="0">0</option>
					<%for(int i=1;i<=23;i++){%>
						<option value="<%=i %>"><%=i %></option>
					<%} %>
					</select>点
					<select id="minuteId1">
					<option value="0">00</option>
					<%for(int i=1;i<=59;i++){
							if(i<10){%>
							<option value="<%=i %>">0<%=i %></option>
					<%}else{%>
						
						<option value="<%=i %>"><%=i %></option>
					<%}} %>
					</select>分 开放
				</td>
			</tr>
			<tr>
				<td><input type="radio"  name="openType1"  id="CountClose" class="color1" />达到每天留言条数<input type="text" id="leaveMsgCount" class="color1 style1" onkeypress="checkNum();"/>条关闭(为0则为不限制)</td>
			</tr>
			<tr>
				<td><textarea id="leaveMsgarea" cols="50" rows="6"></textarea>(限定条数关闭时的提示信息)</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td class="textAlign color1 color2 color3">留言是否需要审核：</td><td style="height: 20px"><input type="radio" name="audit" id="auditId" class="color1"/>是<input type="radio" name="audit" id="isFalse" class="color1"/>否</td>
</tr>

<tr>
	<td class="textAlign color1 color2 color3">留言中有敏感词时：</td><td><input type="radio" name="publish" id="isTrue" class="color1"/>禁止发布<input type="radio" name="publish" id="isFalse" />可以发布但需要审核</td>
</tr>
</table>
<br>
	<center>
		<input type="button" value="保存设置" onclick="saveSet();" class="btn_normal"/>
		&nbsp;&nbsp;
		<input type="button" name="publishRss" onclick="publishRss1()" value="发布" class="btn_normal"/>
	</center>
</form>
</body>
</html>
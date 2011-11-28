<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<!-- 
	<link rel="stylesheet" type="text/css" href="css/style.css" ></script>
	<script type="text/javascript" src="<c:url value="/script/jquery-1.2.6.js"/>" ></script>
	<script type="text/javascript" src="<c:url value="/script/global.js"/>" ></script>
 	-->
	<!----> 
	<link rel="stylesheet" type="text/css" href="css/style.css" ></script>
	<script type="text/javascript" src="script/jquery-1.2.6.js"></script>
	<script type="text/javascript" src="script/global.js" ></script>
 	
<style type="text/css">

*
{
	font-size:12px;
	font-family: "宋体",Arial;
}

#wrap
{
	margin:20px auto;
	width:750px;
}
#left
{
	width:200px;
		
	float:left;
	
}


 h1
{
	text-align:center;
}
.bg
{
	background:#F3F3F3;
}

#left p
{
	text-indent:2em;
}
#right
{
	width:500px;
	float:left;
	margin-left:30px;
}

#right table td
{
height:40px;
border-right:1px #DBDBDB solid;
border-bottom:1px #DBDBDB solid;
}

.align_right
{
	text-align:right;
}

.align_left
{
	text-align:left;
}

.align_center
{
	text-align:center;
}

select
{
	width:120px;
}
.btn_bg
{
background-color:#F1F1ED;
}

.textColor
{
	color:red;
}




</style>
</head>
<script type="text/javascript">
function loadContent(){
		$('#categoryNameId').focus();
		var opentype=$("#openTypeId").val();
		var m =$("#messId").val();
		if(m=="0"){
			alert("验证码错误，请重新输入!"); 
		}
		var ms = $("#infoMessageId").val();//判断是否留言成功
		
		if(ms=="1"){
			alert("发表成功，待管理员审核");
			window.location="<c:url value='/guestOuter.do?dealMethod=showContentList'/>";
		}
		if(ms=="2"){
			alert("你的留言含有非法词语，请更正后再发表");
		}
		if(opentype=="0"){//关闭
			document.getElementById("right").style.display='none';
			document.getElementById("right1").style.display='block';
		}else if(opentype=="1"){//开放
			document.getElementById("right").style.display='block';
			document.getElementById("right1").style.display='none';
		}else if(opentype=="2"){//定时开放
			var d = new Date();
			var hour = d.getHours();
			var minute= d.getMinutes();
			var setHour =$("#openHourId").val();
			var setMinute =$("#openMinuteId").val();
			var setHour1 =$("#openHour1Id").val();
			var setMinute1 =$("#openMinute1Id").val();
			if(parseInt(setHour,10)<=parseInt(hour,10)&&parseInt(setMinute,10)<=parseInt(minute,10)
					&&parseInt(hour,10)<=parseInt(setHour1,10)&&parseInt(minute,10)<=parseInt(setMinute1,10)){//开放
				document.getElementById("right").style.display='block';
				document.getElementById("right1").style.display='none';
			}else{
				document.getElementById("right").style.display='none';
				document.getElementById("right1").style.display='block';
			}
			setTimeout("loadContent()",1000);
		}else if(opentype=="3"){//当留言条数达到限制条数时
			var infoMess = $("#leaveMsgId").val();//留言提示
			var leaveCount = $("#leaveCountId").val();//设置的留言数
			var factCount = $("#factCountId").val();//时间留言数
				if(parseInt(leaveCount,10)<=parseInt(factCount,10)&&leaveCount.trim()!="0"){
						alert(infoMess);
						document.getElementById("right").style.display='none';
						document.getElementById("right1").style.display='block';	
				}else{
					document.getElementById("right").style.display='block';
					document.getElementById("right1").style.display='none';
				}
			}
		}
function checkForm(){
		var name=$("#bookNameId").val();
		var email=$("#emailId").val();
		var title=$("#titleId").val();
		var content=$("#bookContentId").val();
		var randImg=$("#rand").val();
		if(name.trim()==""){
			alert("请填写姓名");
			return false;
		}else if(email.trim()==""){
			alert("请填写电子邮件");
			return false;
		}else if(document.getElementById("guestBookCategoryId")[0].selected){
			alert("请选择留言类别");
			return false;
		}else if(randImg==""){
			alert("请填写验证码");
			return false;
		}else if(title.trim()==""){
			alert("请填写留言主题");
			return false;
		}else if(content.trim()==""){
			alert("请填写留言内容");
			return false;
		}else{
			//$("#guestBookForm").submit();	
			document.guestBookForm.submit();
		}
}
function textCheck(thisArea, messageCount,maxLength){//根据onkeyup事件计算文本框中的字符个数，限制在250以内
    if (thisArea.value.length > maxLength){
      alert(maxLength + ' 个字限制. \r超出的将自动去除.');
      thisArea.value = thisArea.value.substring(0, maxLength);
      thisArea.focus();
    }
    /*回写span的值，当前填写文字的数量*/
    messageCount.innerText = thisArea.value.length;
  }
function checkRand(){
		var rand = $("#rand").val();
		if(rand.trim()!=""){
		$.ajax({
			  url:'<c:url value="/guestOuter.do?dealMethod=checkRand&rand="/>'+rand,
		     type:"post",
	      success:function(msg) {
			      if(msg.trim()=="0"){
						alert("验证码输入错误");
						document.getElementById("rand").value="";
						change();
					}
			  }
		  });
		}
}
function change(){
	var img = document.getElementById("newrand"); 
	img.src = "public/image.jsp?t="+ new Date();			
}
</script>
<body onload="loadContent();">
<!-- <form action="<c:url value='/guestOuter.do'/>" method="post" name="guestBookForm" id="guestBookForm"> -->
<form action="guestOuter.do" method="post" name="guestBookForm" id="guestBookForm">
<input type="hidden" name="dealMethod" id="dealMethod" value="addContent"/>
<input type='hidden' name='infoMessage' id='infoMessageId' value="${guestBookForm.infoMessage}"/>
<input type="hidden" name="mess" id="messId" value="${guestBookForm.mess}"/>
<input type="hidden" name="sites" id="sitesId" value="${guestBookForm.sites}"/>
<input type="hidden" name="ids" id="strId" value="${guestBookForm.ids}"/>
<input type="hidden" name="openType" id="openTypeId" value="${guestBookForm.openType}"/>
<c:if test="${guestBookForm.openType eq '2'}">
<input type="hidden" name="openHour" id="openHourId" value="${guestBookForm.openHour}"/>
<input type="hidden" name="openMinute" id="openMinuteId" value="${guestBookForm.openMinute}"/>
<input type="hidden" name="openHour1" id="openHour1Id" value="${guestBookForm.openHour1}"/>
<input type="hidden" name="openMinute1" id="openMinute1Id" value="${guestBookForm.openMinute1}"/>
</c:if>
<c:if test="${guestBookForm.openType eq '3'}">
<input type="hidden" name="leaveCount" id="leaveCountId" value="${guestBookForm.leaveCount}"/>
<input type="hidden" name="factCount" id="factCountId" value="${guestBookForm.factCount}"/>
<input type="hidden" name="leaveMsg" id="leaveMsgId" value="${guestBookForm.leaveMsg}"/>
</c:if>

<div id="wrap">
<div id="right1" style="display:none"><center><h2>留言已被关闭</h2></center></div>
<br/><br/>

<div id="left" class="bg">
<h1>公众留言须知</h1>
<p>一、本栏目仅用于政府和公众之间的交流，不用于公众互相之间的交流，公众可就政府职能范围内的相关工作进行咨询，提出宝贵意见。</p>

</div>
<div id="right" style="display:none">
<h1>公众留言</h1>
<table cellpadding="0" cellspacing="0"  style="border:1px #DBDBDB solid;" 	 width="500px">

<tr>
<td class="align_right">姓名：</td>
<td class="align_left"><input type="text" name="guestContent.bookName" id="bookNameId" size="20" maxlength="20" valid="string" tip="姓名不能为空"/>(<span class="textColor">*</span>)</td>
</tr>

<tr>
	<td class="bg align_right">电子邮件：</td>
	<td class="bg align_left"><input type="text" name="guestContent.email" size="20" maxlength="25" id="emailId" valid="email" tip="请输入合法的email地址" size="20"/>(<span class="textColor">*</span>)</td>
</tr>

<tr>
	<td class="align_right">联系电话：</td>
	<td class="align_left"><input type="text" name="guestContent.phone" id="phoneId" maxlength="13" /></td>
</tr>

<tr>
	<td class="bg align_right">所属省份：</td>
	<td class="bg align_left">
		<select id="areaId" name="guestContent.area">
			<OPTION value="" selected>------请选择-----</OPTION> 
			<OPTION value=北京市>北京市</OPTION> 
			<OPTION value=天津市>天津市</OPTION> 
			<OPTION value=河北省>河北省</OPTION>
			<OPTION value=山西省>山西省</OPTION>
			<OPTION value=内蒙古自治区>内蒙古自治区</OPTION> 
			<OPTION value=辽宁省>辽宁省</OPTION>
			<OPTION value=吉林省>吉林省</OPTION> 
			<OPTION value=黑龙江省>黑龙江省</OPTION>
			<OPTION value=上海市>上海市</OPTION> 
			<OPTION value=江苏省>江苏省</OPTION> 
			<OPTION value=浙江省>浙江省</OPTION>
			<OPTION value=安徽省>安徽省</OPTION> 
			<OPTION value=福建省>福建省</OPTION> 
			<OPTION value=江西省>江西省</OPTION>
			<OPTION value=山东省>山东省</OPTION> 
			<OPTION value=河南省>河南省</OPTION> 
			<OPTION value=湖北省>湖北省</OPTION>
			<OPTION value=湖南省>湖南省</OPTION> 
			<OPTION value=广东省>广东省</OPTION> 
			<OPTION value=广西壮族自治区>广西壮族自治区</OPTION> 
			<OPTION value=海南省>海南省</OPTION>
			<OPTION value=重庆市>重庆市</OPTION> 
			<OPTION value=四川省>四川省</OPTION> 
			<OPTION value=贵州省>贵州省</OPTION> 
			<OPTION value=云南省>云南省</OPTION> 
			<OPTION value=西藏自治区>西藏自治区</OPTION> 
			<OPTION value=陕西省>陕西省</OPTION> 
			<OPTION value=甘肃省>甘肃省</OPTION> 
			<OPTION value=青海省>青海省</OPTION> 
			<OPTION value=宁夏回族自治区>宁夏回族自治区</OPTION>
			<OPTION  value=新疆维吾尔自治区>新疆维吾尔自治区</OPTION>
			<OPTION value=香港>香港</OPTION>
			<OPTION value=澳门>澳门</OPTION> 
			<OPTION value=台湾>台湾</OPTION>
			<OPTION value=其他地区>其他地区</OPTION>
		</select>
	</td>
</tr>

<tr>
	<td class="align_right">联系地址：</td>
	<td class="align_left"><input type="text" name="guestContent.address" id="addressId" /></td>
</tr>

<tr>
	<td class="bg align_right">留言类别：</td>
	<td class="bg align_left">	
	<select id="guestBookCategoryId" name="guestBookCategory">
		<option selected="selected">--</option>
		<c:forEach items="${sessionScope.categoryList}" var="list">
			<option value="${list.id}">${list.categoryName}</option>
		</c:forEach>
	</select>(<span class="textColor">*</span>)</td>
</tr>

<tr>
	<td class="align_right">验证码：  </td>
	<td class="align_left">	
		<input type="text" name="rand" id="rand" class="test " onblur="checkRand();" maxlength="4" valid="string" tip="验证码不能为空"/>
		<img id="newrand" src="public/image.jsp" onclick="change()" alt="点击获取验证码"/>(<span class="textColor">*</span>)
		
	</td>
</tr>
<tr>
	<td class="align_right">留言主题：</td>
	<td class="align_left"><input type="text" id="titleId" name="guestContent.title" maxlength="30" valid="string" tip="留言主题不能为空"/>(<span class="textColor">*</span>)</td>
</tr>

<tr>
	<td class="bg align_right">留言内容：</td>
	<td class="bg align_left">
		<textarea id="bookContentId" rows="8" cols="45" name="guestContent.bookContent" onkeyup="textCheck(this,messageCount1,250);"></textarea >(<span class="textColor">*</span>)
	<BR>
	限250 个字符,已输入<FONT color=#cc0000><SPAN id="messageCount1">0</SPAN></FONT> 个字</span>
	</td>
</tr>

<tr>
	<td colspan="2" class="align_center">
	<input type="button" id="id1" onclick="checkForm();" value="确定" class="btn_bg"/>
	<input type="button" onclick="javascript:history.go(-1);" id="id2" value="取消" class="btn_bg"/>
	</td>
</tr>


</table>
</div>
</div>
</form>
</body>
</html>
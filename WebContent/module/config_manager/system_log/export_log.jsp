<%@page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>导出日志</title>	
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>	
<script>

	$(document).ready(function() {
		$("#keyWords").focus();
	});
	
	function exportLogs() {
		if(confirm("你确定要导出吗？")) {
			var keywords = $("#keyWords").val();
			if(keywords != null && keywords != "") {
				var strdt1 = document.getElementById("startTime").value.replace("-","/");
		        var strdt2 = document.getElementById("endTime").value.replace("-","/");            
		        var dt1 = new Date(Date.parse(strdt1));
		        var dt2 = new Date(Date.parse(strdt2));
				if(dt1 > dt2) {
					alert("您选择的开始时间不能大于结束时间");
					return false;
				}
			}
			var extent = $("#extent :selected").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			$.ajax({
				url:  "<c:url value='/systemLog.do?dealMethod=export&keyWords="+ encodeURI(keywords)+"'/>"+"&extent="+ extent +"&startTime="+ startTime +"&endTime="+ endTime,
			   type: "post", 
		    success: function(msg) {
					var url = "<c:url value='/systemLog.do?dealMethod=savaExportLogs'/>";
					window.location.href = url;
	     		}
			}); 
 		} else { 
 	 		return false;
 	 	}
	}

	/** 退出 */
	function button_quit_onclick() {
		rightFrame.closeChild(win);	
	}
</script>
</head> 
<body>
	<form id="systemLogForm" action="<c:url value="/systemLog.do"/>" method="post" name="systemLogForm">
		<input type="hidden" name="dealMethod" id="dealMethod" value="export"/>
		<div class="form_div">
			<table style="font:12px; width:500px;">
				<tr>
					<td class="td_left" width="100px;"><i>&nbsp;</i>关键字：</td>
					<td><input type="text" class="input_text_normal" name="keyWords" id="keyWords" style="width:240px;"/></td>
				</tr>
				<tr>
					<td class="td_left" width="100px;"><i>&nbsp;</i>范&nbsp;&nbsp;围：</td>
					<td>
						<select name="extent" id="extent" class="input_select_normal" style="width:148px;">
							<option value="userName">用户名</option>
							<option value="moduleName">模块名称</option>
						</select>
				</tr>
				<tr>
					<td class="td_left" width="100px;"><i>&nbsp;</i>时&nbsp;&nbsp;间：</td>
					<td>
						<span>
						<input type="text"  name="startTime" id="startTime" style="width:150px;" class="input_text_normal Wdate"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
						&nbsp;到：<input type="text"  name="endTime"   id="endTime"   style="width:150px;" class="input_text_normal Wdate"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
						</span>
					</td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td colspan="2" style="padding:0 0 0 50px; ">
							<font color="red">* 默认导出所有</font>
					</td>
				</tr>
				<tr><td><li>&nbsp;</li></td></tr>
				<tr><td><li>&nbsp;</li></td></tr>
				<tr>
					<td colspan="2" align="center">
							<input type="button"  class="btn_normal" value="导出" onclick="exportLogs()"/>&nbsp;&nbsp;
							<input type="button"  class="btn_normal" value="退出" onclick="button_quit_onclick()"/>
					</td>
				</tr>
			</table>
	    </div>		
	</form>
</body>
</html>

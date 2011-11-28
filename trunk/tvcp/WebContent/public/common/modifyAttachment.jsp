<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">	
<title>修改附件名称</title>
<%@include file="/templates/headers/header.jsp"%>
<style  type="text/css" media="all">
	*{
	 font-size:12px;
	}
</style>

<script type="text/javascript">
	var childOrgWin;

	
	$(document).ready(function (){
		$("#attachmentName").focus();
	});
	
    function closeChild() { 
  		childOrgWin.close();
  	}

	function modifyAttachment() {
		if($("#attachmentName").val().isEmpty()) {
			alert("附件名称不能 为空");
			return false;
		} else {
			parent.setVal($("#attachmentName").val(), $("#selectedIndex").val(), $("#selectedValue").val());
		}
	}
</script>

</head>
<body>
	<table align="center">
		<tr>
			<td class="td_left"><i>&nbsp;</i>附件显示名称</td>	
			<td>
				<input type="text" class="input_text_normal" id="attachmentName" name="attachmentName" value="<%=new String(request.getParameter("attachmentName").getBytes("ISO-8859-1"),"utf-8")%>"/>
				<input type="hidden" id="selectedIndex" name="selectedIndex" value="<%=request.getParameter("selectedIndex") %>" />
				<input type="hidden" id="selectedValue" name="selectedValue" value="<%=request.getParameter("selectedValue") %>" />
			</td>
		</tr>
		<tr>
			<td><input type="button" onclick="modifyAttachment()" value="修改"/>
				&nbsp;&nbsp;&nbsp;&nbsp; 
        		<input type="reset"  value="重置"/>
			</td>
		</tr>
	</table>
</body>
</html> 





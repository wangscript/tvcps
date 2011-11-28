<%@page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>新增热链</title>
<%@include file="/templates/headers/header.jsp"%>

<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>

<script type="text/javascript">
	
	$(document).ready(function() {
		var categoryid = document.getElementById("categoryId").value;
		if(document.getElementById("message").value =="1"){
			alert("热链文字不能重复");
			rightFrame.window.location.href="<c:url value='/author.do?dealMethod=link&categoryId="+categoryid+"&" + getUrlSuffixRandom() + "'/>";
			closeWindow(rightFrame.getWin());
		}	
		$("#authorName").focus();
		var categoryid = document.getElementById("categoryId").value;
		var columnId = $("#columnId").val();
		if(columnId != "" && columnId != 0) {
			$("#articleId").attr("readonly", true);
		}
		if(document.getElementById("message").value != null && document.getElementById("message").value != ""){	
		rightFrame.window.location.href="<c:url value='/author.do?dealMethod=link&categoryId="+categoryid+"&" + getUrlSuffixRandom() + "'/>";
		closeWindow(rightFrame.getWin());
		}
	});
	
	function closeChild() {
		closeWindow(win);
	}

	function back() {
		rightFrame.closeNewChild(); 
	}

	function save(){
		$("#generalSystemSetForm").submit();
	}

	function update(){
	   	$("#generalSystemSetForm").submit(); 
	}
</script>
</head>


<form action="author.do"  method="post" name="generalSystemSetForm" id="generalSystemSetForm" >
    <c:if test="${generalSystemSetForm.generalSystemSet.setContent== null}">
    	<input type="hidden" name="dealMethod" id="dealMethod" value="addLink"/>
    </c:if>
    <c:if test="${generalSystemSetForm.generalSystemSet.setContent!= null}">
    	<input type="hidden" name="dealMethod" id="dealMethod" value="linkUpdate"/>
    </c:if>
	<input type="hidden" name="message" id="message" value="${generalSystemSetForm.infoMessage}"/>
	<input type="hidden" name="generalSystemSet.id" id="id"  value="${generalSystemSetForm.generalSystemSet.id}"/>
  	<input type="hidden" name="generalSystemSet.createTime" id="createTime"  value="${generalSystemSetForm.generalSystemSet.createTime}"/>
    <input type ="hidden" name="categoryId"  id="categoryId" value="${generalSystemSetForm.categoryId}"/>
	<div class="form_div">
	<table style="width:770px;; font:12px;" >
		<tr>
			<td class="td_left" width="90px;"> 
				<i>*</i>热链文字：</td><td>
            	<input type="text" name="generalSystemSet.setContent" id="authorName" class="input_text_normal" style="width:230px;"   value="${generalSystemSetForm.generalSystemSet.setContent}" valid="string" tip="热链文字不能为空" />
			</td>  
		</tr> 
		<tr>
	    	<td class="td_left" width="90px;"> 
            	<i></i>热链地址：</td><td>
			    <input type="text" name="generalSystemSet.url" id="url" class="input_text_normal"   style="width:230px;"  value="${generalSystemSetForm.generalSystemSet.url}" valid="string" tip="非本地热链地址需加http://1" />
            </td>
	     	<td></td>  
		</tr>
		<tr>
		</tr>
        <tr height="50px">
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<td>
        </tr>
        	<tr hegight="200px">
		<tr>	
			<td width="90px;"></td>
			<td>
          	<c:if test="${generalSystemSetForm.generalSystemSet.setContent== null}">
		    	<input type="button" name="saveValue" class="btn_normal" onclick="save()" value="保存"/>
         	</c:if>
            &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;
            <c:if test="${generalSystemSetForm.generalSystemSet.setContent!= null}">
            	<input type="button" name="saveValue" class="btn_normal" onclick="update()" value="修改"/>
            </c:if>
             &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;
			<input type="button" class="btn_normal" onclick="back()" value="退出"/>
			</td>
		</tr>
	</table>
	</div>
</form>
</body>
</html>
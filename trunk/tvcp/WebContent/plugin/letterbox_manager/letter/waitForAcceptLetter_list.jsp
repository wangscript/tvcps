<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>等待受理的信件</title>
	<script type="text/javascript">
		//受理信件
		function button_accept_onclick(ee){
			if(document.getElementById("xx").value == null || document.getElementById("xx").value == ""){
				alert("请至少选择一条记录操作！");
        		return false;
			} else {
				document.getElementById("strid").value = document.getElementById("xx").value;
				$("#dealMethod").val("accept");
	    		$("#letterForm").submit();
			}
		}
	
		function button_delete_onclick(ee){
			var ids = document.getElementById("xx").value;
			if(document.getElementById("xx").value == null || document.getElementById("xx").value == "") {
				alert("请至少选择一条记录操作！");
        		return false;
        	} else{			 
				document.getElementById("strid").value = document.getElementById("xx").value;
				$("#dealMethod").val("deleteAcceptLetter");
		    	$("#letterForm").submit();
		    }
    	}

    	function userLetterDetail(id) {
    		window.location.href = "letter.do?dealMethod=showLetter&ids=" + id ;
        }

    	// 发布互动信箱目录
		function button_publish_onclick(){
   	   		var options = {	 	
    			  	url: "<c:url value='/letter.do?dealMethod=publishLetter'/>",
    		    success: function(msg) { 
    		    	alert(msg);		    		
    		    } 
    		};
   		$('#letterForm').ajaxSubmit(options);	 
   	 }
	</script> 

</head>
<body>
	<form id="letterForm" action="<c:url value='/letter.do'/>" method="post" name="letterForm">
	<div class="currLocation">待受理信件列表</div>
		<input type="hidden" name="dealMethod" id="dealMethod" value="waitForAccept"/>
		<input type="hidden" name="ids" id="strid"/>
		<complat:button name="button" buttonlist="accept|0|delete|0|publish" buttonalign ="left"></complat:button>
		<complat:grid ids="xx" width="100, 300, 150, 150" head="信件类别, 标题, 用户名, 日期"  element="[2,link,onclick,userLetterDetail]"
		page="${letterForm.pagination}"  form="letterForm" action="letter.do"/>
	</form>
</body>
</html>

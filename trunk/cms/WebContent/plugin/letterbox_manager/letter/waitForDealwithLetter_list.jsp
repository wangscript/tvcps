<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>等待处理的信件</title>
	<script type="text/javascript">
		function reply(id){
			window.location.href = "letter.do?dealMethod=beforReply&ids=" + id;
		}
		
		//转办,flag为页面跳转标记
		function transfer(id){	
			win = showWindow("modifyCategory","转办","letter.do?dealMethod=showOrgList&ids="+id + "&flag=1", 293, 0,350,450);
		}
		//子页面为transferOrg_list.jsp
		function getIds() {
			$("#dealMethod").val("transferLetter");
    		$("#letterForm").submit();
		}
		
		function button_delete_onclick(ee){
			var ids = document.getElementById("xx").value;
			if(document.getElementById("xx").value == null || document.getElementById("xx").value == "") {
				alert("请至少选择一条记录操作！");
        		return false;
        	} 	 
      		if(confirm("确定要删除吗？")){
				document.getElementById("strid").value = document.getElementById("xx").value;
				$("#dealMethod").val("deleteDealwithLetter");
		    	$("#letterForm").submit();
      		}
		    
    	}
    	
		function userLetterDetail(id) {
    		window.location.href = "letter.do?dealMethod=showLetter&ids=" + id ;
        }
    	
		function closeChild() {
			closeWindow(win);
			//closeWindow(rightFrame.getWin());
		}
	</script> 

</head>
<body>
	<form id="letterForm" action="letter.do" method="post" name="letterForm">
	<div class="currLocation">待处理信件列表</div>
	
		<input type="hidden" name="dealMethod" id="dealMethod" value="waitForDealwith"/>
		<input type="hidden" name="ids" id="strid"/>
		<input type="hidden" name="flag" id="flag" value="1"/>
		<input type="hidden" name="letterId" id="letterId" value=""/>
		<input type="hidden" name="organizationId" id="organizationId" value=""/>
		<complat:button name="button" buttonlist="delete" buttonalign ="left"></complat:button>
		<complat:grid ids="xx" width=" 100,*,170, 170, 130, 130" head="信件类别, 标题, 用户名, 日期, 回复, 转办"  element="[2,link,onclick,userLetterDetail][5,button,onclick,reply,回复][6,button,onclick,transfer,转办]" 
		page="${letterForm.pagination}"  form="letterForm" action="letter.do"/>
	</form>
</body>
</html>

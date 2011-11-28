<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>客户调查</title>
<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<script type="text/javascript">
	//加载页面方法		
	$(document).ready(function() {
		if(document.getElementById("message").value =="1"){
		    rightFrame.window.location.href="<c:url value='/onlineBulletin.do?dealMethod=list&" + getUrlSuffixRandom() + "'/>";
			closeWindow(rightFrame.getWin());           
		}	
	});


    //添加按钮
    function button_add_onclick(ee) {
    	rightFrame.window.location.href = "<c:url value='/onlineBulletin.do?dealMethod=detail&"+getUrlSuffixRandom() + "'/>";
    }
      
    //删除按钮
	function button_delete_onclick(ee) {
		var ids = document.getElementById("xx").value;
		if (ids == "" || ids == null) {
			alert("请至少选择一条记录操作！");
			return false;
		}
		if (confirm("你确定要删除吗？")) {
			document.getElementById("id").value = document.getElementById("xx").value;
			document.all("dealMethod").value = "delete";
			document.forms[0].submit();
		} else {
			return false;
		}
	}
	 
	//修改按钮
	function orgdetail(id) {
	    var url = "<c:url value='/onlineBulletin.do?dealMethod=update&id="+id+"'/>";
	    rightFrame.window.location.href = url;
	}

	// 发布网上公告目录
	function button_publish_onclick(){
		var options = {	 	
 		    	url: "<c:url value='/onlineBulletin.do?dealMethod=publishBulletin'/>",
 		    success: function(msg) { 
 		    	alert(msg);		    		
 		    } 
 		};
		$('#onlineBulletinForm').ajaxSubmit(options);	
	}

	// 绑定栏目
	function bindColumn(id){
		var url = "<c:url value='/onlineBulletin.do?dealMethod=findBindColumn&bulletinId='/>"+id;
		win = showWindow("onlineBulletinForm", "绑定栏目", url, 0, 0, 350, 400);
	}
</script>
</head>

<body>
  <form id="onlineBulletinForm" action="<c:url value='/onlineBulletin.do'/>"method="post" name="onlineBulletinForm">
     <input type="hidden" name="message" id="message" value="${onlineBulletinForm.infoMessage}"/>
	 <input type="hidden"  name="dealMethod" id="dealMethod" value="updated" /> 
	 <input type="hidden"  name="onlineBulletin.columnIds" id="columnIds" />
     <input	type="hidden" name="id" id="id" />
	 <div class="currLocation">网上公告</div>
	 <complat:button buttonlist="add|0|delete|0|publish" buttonalign="left" /> 
	 <complat:grid  ids="xx" width="200,200,120,200" head="公告名称,创建人,创建时间,绑定栏目"
		element="[1,link,onclick,orgdetail][4,button,onclick,bindColumn,绑定栏目]"
		page="${onlineBulletinForm.pagination}" form="onlineBulletinForm"
		action="onlineBulletin.do" />
   </form>
</body>
</html>

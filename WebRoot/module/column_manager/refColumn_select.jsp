<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>栏目同步</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
	<style type="text/css">
	 
	#contacter {
	 text-align:right;
	 float:right;
	 margin-right:5px;
	 margin-top:10px;
	}
	#treeboxbox_tree {
	 float:left;
	 margin-left:5px;
	 margin-top:10px;
	}
	 
	</style>

<script language="javascript">

	$(document).ready(function() {
		var refColumnNameAndId = document.getElementById("refColumnNameAndId").value;
		var selectObj = document.columnForm.selColumnNameStr;
		if(refColumnNameAndId != null && refColumnNameAndId != "") {
			var arr1 = refColumnNameAndId.split("::");
			for(var i = 0; i < arr1.length; i++) {
				var arr2 = arr1[i].split(",");
				if(arr2[0] == "" || arr2[0] == null) {
					//清空
					document.getElementById("selColumnNameStr").options.length = 0;
				}
				if(selectObj.length == 0) {
					selectObj.length = 1;
				} else {
					selectObj.length++;
				}
				selectObj.options[selectObj.length-1].value = arr2[1];
				selectObj.options[selectObj.length-1].text = arr2[0];
			}
		}
	});

	// 退出 
	function button_quit_onclick(ee){	
		closeWindow(rightFrame.getWin());	
	}
	
	//根据选择的网站切换栏目
	function changeSiteColumn(obj){ 
		if(obj.value != null && obj.value != "" && obj.value != "0" ) {
			$("#refSiteId").val(obj.value);
			var refSiteId = document.getElementById("refSiteId").value;
			var articleFormatId = document.getElementById("articleFormatId").value;
			$.ajax({	
					url:"<c:url value='/column.do?dealMethod=selSite&refSiteId="+ refSiteId+"&articleFormatId="+articleFormatId+"'/>",
					cache:false,
					type:"post",
					success:function(msg){	
						var strmsg = msg.split("###");
	 		 		  	var formatId = strmsg[1];
	 		 		  	var siteId = strmsg[0];
	 		 		  	var url = "<c:url value='/column.do?dealMethod=findRefSite&refSiteId="+ siteId+"&articleFormatId="+formatId+"'/>";
	 	 		 		document.getElementById("rightFrame").src = url;
					},
					error:function(){
						
					}
			});
		}
	}

	function sure() {
		var selectObj = document.columnForm.selColumnNameStr;
		var theObjOptions = selectObj.options;
		var idstr = "";
		var namestr = "";
		var sameIdStr = document.getElementById("sameFormatColumns").value;

		var columnid = document.getElementById("columnId").value;
		var sameIds = sameIdStr.split(",");
		for(var j = 0; j < theObjOptions.length; j++) {
			if(columnid == theObjOptions[j].value){
				alert("不能同步到自己！");
				return false;
			}
			for(var i = 0; i < sameIds.length; i++) {
				if(theObjOptions[j].value == sameIds[i]){
					alert("所选栏目中有的与当前栏目格式不一致,请重新选择");
					return false;
				}
			}
			
			idstr = idstr + theObjOptions[j].value + ",";
			namestr = namestr + theObjOptions[j].text + ",";
		}
		var windd = parent.document.getElementById("rightFrame").contentWindow;
		windd.document.getElementById("columnNames").value = namestr;
		windd.document.getElementById("refColumnIds").value = idstr;
		closeWindow(rightFrame.getWin());

	}

	function checkFormat(obj) {
		var sameIdStr = document.getElementById("sameFormatColumns").value;
		var sameIds = sameIdStr.split(",");
		
		//选中的栏目格式
	//	var formatid = node.attributes.formatid;
	    var isFire = "no";
		for(var i = 0; i < sameIds.length; i++) {
			if(sameIds[i] == obj.value) {
				isFire = "yes";
			}
		}
		if(isFire == "yes") {
			document.getElementById("fire").innerHTML = "<font color=\"#FF0000\">冲突</font>";
		} else {
			document.getElementById("fire").innerHTML = "<font color=\"#00FF00\">正常</font>";
		}
	}
	
	
</script>
</head>
<body style="background-color: white">
  	<form name="columnForm" method="post" id="columnForm" action="<c:url value="/column.do"/>">
		<input type="hidden" name="dealMethod" id="dealMethod" /> 
		<input type="hidden" name="ids" id="strid" />
		<input type="hidden" name="columnIds" id="columnIds" />
		<input type="hidden" name="columnId" id="columnId" value="${columnForm.columnId}"/>
		<input type="hidden" name="columnNames" id="columnNames" />
		<input type="hidden" name="articleFormatName" id="articleFormatName" value="${columnForm.articleFormat.name}"/>
		<input type="hidden" name="articleFormatId" id="articleFormatId" value="${columnForm.articleFormat.id}"/>
		<input type="hidden" name="refSiteId" id="refSiteId"  value="${columnForm.refSiteId}"/>
		<input type="hidden" name="refSiteName" id="refSiteName"  value="${columnForm.refSiteName}"/>
		<input type="hidden" name="sameFormatColumns" id="sameFormatColumns" value="${columnForm.sameFormatColumns}"/>
		<input type="hidden" name="refColumnNameAndId" id="refColumnNameAndId" value="${columnForm.refColumnNameAndId}"/>
		<table style="font:12px;" width="520px;" >
			<tr>
				<td><i>&nbsp;&nbsp;&nbsp;</i>请选择网站：
				<i>&nbsp;</i><select name="refSites" id="refSites" class="input_select" style="width:200px;" onchange="changeSiteColumn(this)">
						<c:forEach items="${columnForm.list}" var="list">
							<c:if test="${columnForm.refSiteId == list[0]}">
								<option value="${list[0]}" selected>${list[1]}</option>
							</c:if>
							<c:if test="${columnForm.refSiteId != list[0]}">
								<option value="${list[0]}">${list[1]}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
				<td width="200px;"><i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i>点击检查格式是否冲突：<a id="fire"></a>
				</td>
			</tr>
		</table>
			<div>
			<div id="treeboxbox_tree" style="width:300px; height:400px;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
	             <iframe  style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%;background:white; " 
					src="column.do?dealMethod=findRefSite&refSiteId=${columnForm.refSiteId}&articleFormatId=${columnForm.articleFormatId}"  name="rightFrame" id="rightFrame"  frameborder="0" scrolling="auto"></iframe>
				</div> 
			<div id="contacter" style="width:180px;; height:400px;background-color:#f5f5f5;border :1px solid Silver;scroll:auto;">
				
				<table>
					<tr>
						<td width="178px;" colspan="2" align="center"> 
							<span style="overflow-x:auto; width:175px;" > 
							<select name="selColumnNameStr"  id="selColumnNameStr"  size="24" style="width:320px;" onchange="checkFormat(this)">  
	        				</select> 
							</span>
    					</td> 
			 		</tr>
				</table>
				
			</div>
			<table width="500px;" >
				<tr><td>　　　　</td></tr>
				<tr>
					<td>
						<center>
						<input  type="button"  value="确定" class="btn_normal"  onClick="sure()" >		
						&nbsp;&nbsp;&nbsp;&nbsp;	
						<input  type="button"  value="退出" class="btn_normal"  name="button_quit" onClick="javascript:button_quit_onclick(this);" ></center>
					</td>
				</tr>
			</table>
		</div>
		
			
		</form>
</body>
</html>
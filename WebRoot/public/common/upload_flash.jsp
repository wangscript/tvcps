<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">	
	<title>flash 缩略图</title>
    <META NAME="Generator" CONTENT="EditPlus">
    <META NAME="Author" CONTENT="">
    <META NAME="Keywords" CONTENT="">
    <META NAME="Description" CONTENT="">
	<script type="text/javascript" src="<c:url value="/script/fckeditor/editor/js/fckfilemanager.js"/>"></script>

	</style>
	<script type="text/javascript">

	$(document).ready(function() {

		$("input[name='tb_idnull']").bind("click", function(){
			var str = $(this).attr("id").split("_");
			var id = str[0];
			if($(this).attr("checked")) {
				var dd = id + "_5";
				playmv($("#"+dd+"").html());
			} else {
				playmv("");
			}
		}); 
	});
	

	//显示Flash的属性
	function playmv(u){
		if (!u.isEmpty()) {
			u = u.substring(1);
		}
		document.getElementById("show" ).innerHTML = '<embed id="abc"  quality="high" width="100%" height="180" src="' + u + '" /></embed>'
	}	

	//预览Flash
	function showFla(id){	
		return false;    		
	//	var dd = id + "_5";
	//	playmv($("#"+dd+"").html());
	}
	
	function funKeyup3(){
		var width = document.getElementById('suolvwidth').value;
		var i_width = $("#width").val()/width;
		var number = checkNumber(i_width);
		document.getElementById('suolvheight').value = number;
	}
	
	function funKeyup4(){
		var height = document.getElementById('suolvheight').value;
		var i_height = $("#height").val()/height;
		var number = checkNumber(i_height);
		document.getElementById('suolvwidth').value = number;
	}
	
	function sure(){

		// 是否选择了
		var ids = $("#xx").val();
		if(ids.isEmpty()) {
			alert("请选择flash");
			return false;
		}
		var arr = ids.split(",");
		if(arr.length != 1) {
			alert("只能选择一个flash");
			return false;
		}
		var firstWin = top.getWin();	
		//文章管理 -->高级编辑器
		if(firstWin == null || firstWin == "undefined") {
			var location = "/${appName}" + $("#"+ids+"_5").html();
			var articleFlash = $("#articleFlash").val();
			if(location != null) {
				for(var i = 1; i < 10; i++) {
					if(articleFlash == ("article.media"+i)) {
						rightFrame.document.getElementById(articleFlash).value = $("#"+ids+"_5").html();
						rightFrame.closeChild();
						return null;
					}
				}
				var returnvalue = processDoFlash(document.documentForm, "sure", location);
				rightFrame.fck_insertHtml(returnvalue);
				rightFrame.closeWindow(rightFrame.getWin());	
				return null;			
			} else {
				alert("请选择对应的缩略图(提示：只能选择一张图片)");
				return null;
			}
		}
			
		var temp = firstWin.split("###");
		var dialogProperty = temp[0];
		var detailFrame = "_DialogFrame_"+dialogProperty;	 		   
		var tempWindow = top.document.getElementById(detailFrame).contentWindow;			
		var secondTempWindow = tempWindow.document.getElementById("unitEditArea").contentWindow;			
		var secondWin = secondTempWindow.getWin();					
		var secondtemp = secondWin.split("###");
		var seconddialogProperty = secondtemp[0];
		var seconddetailFrame = "_DialogFrame_"+seconddialogProperty;			
		var secondWindow = top.document.getElementById(seconddetailFrame).contentWindow;
		
		var location1 = "/${appName}" + $("#"+ids+"_5").html();
		var articleFlash1 = $("#articleFlash").val();
		if(!articleFlash1.isEmpty() && articleFlash1 != "" && articleFlash1 != "null") {
			rightFrame.document.getElementById(articleFlash1).value = location1; //location;
			rightFrame.closeChild();			
		} else {
			var returnvalue1 = processDoFlash(document.documentForm, "sure", location1);
			secondTempWindow.fck_insertHtml(returnvalue1);
			top.closeWindow(secondWin);	
		}
    }
    
	function button_add_onclick(ee) {
		if(document.getElementById("category").value == "") {
			alert("请先创建flash类别");
			return false;
		}
		var category = $("#category :selected").val();
		if(category.isEmpty() || category == 0) {
			alert("请先选择类别");
			return false;
		}
		var articleFlash = $("#articleFlash").val();
		var isScaleFlash = $("#isScaleFlash").val();
		var selectValue = $("#category :selected").val();
		win = showWindow("newflash","上传Flash","<c:url value='/module/document_manager/flash/flash_detail.jsp?isScaleFlash="+ isScaleFlash +"&articleFlash="+ articleFlash +"&nodeId="+ selectValue+"'/>", 293, 0, 480, 450);	
	}
	
	function button_delete_onclick(ee) {
		var ids = document.getElementById("xx").value;
		if(ids == null || ids == "" || ids == "null") {
			alert("删除操作不能为空");
			return false;
		}
		$("#strid").val(ids);
		if(confirm("确定要删除吗？")){
			$("#dealMethod").val("deleteFlash");
			$("#documentForm").submit();
		}else{
			return false;
		}
	}

	// 改变select中的值并分页显示选择的类别下的值
	function change(obj) {
	    var categoryid = obj.value;	
		$("#nodeId").val(categoryid);	
		$("#documentForm").submit();
	}
	
</script> 	
</head>
<body>   
	<form id="documentForm" action="flash.do" method="post" name="documentForm">	
    <input type="hidden" name="dealMethod" id="dealMethod" value="insertFlashList"/>
    <input type="hidden" name="isScaleFlash" id="isScaleFlash" value="1">
    <input type="hidden" name="nodeId" id="nodeId" value="${documentForm.nodeId}"/>
	<input type="hidden" name="message" id="message" value="${documentForm.infoMessage}"/>
    <input type="hidden" name="ids" id="strid"/>
	<input type="hidden" name="articleFlash" id="articleFlash" value="${documentForm.articleFlash}"/>
	<input type="hidden" name="categoryId" id="categoryId" value="${documentForm.categoryId }"/>
	
	<div id="Layer2" style="position:absolute; overflow: scroll;  left:4px; top:10px; width:642px; height:520px; ">
		<complat:button  name="button" buttonlist="add|0|delete" buttonalign="left"/>
			<label for="category"><i>&nbsp;</i>选择类别</label>
				<select id="category" style="width: 150px;" onChange="change(this)" class="input_select_normal">
					<c:forEach items="${documentForm.list}" var="list">
						<c:if test="${list.id == documentForm.categoryId}">
							<option value="${list.id}" selected="true">${list.name}</option>
						</c:if>
						<c:if test="${list.id != documentForm.categoryId}">
							<option value="${list.id}">${list.name}</option>
						</c:if>
					</c:forEach> 
				</select> 
		<complat:grid ids="xx" width="260,80,160,0,0"  head="flash名称,flash大小,创建时间, , " element="[1,link,onclick,showFla]"
			page="${documentForm.pagination}"  form="documentForm" action="flash.do"/>
	</div>

	<div id="Layer3" style="position:absolute; left:655px; top:11px; width:240px; height:460px;">
		<fieldset style="height:180px;"><legend>&nbsp;<font color=blue size="-1" style="color:blue;font:12px;font-weight:bold">动画预览</font>&nbsp;</legend>
			<div id="show" align="center"></div>
		</fieldset>
		<fieldset style="height:290"><legend>&nbsp;<font color=blue size="-1" style="color:blue;font:12px;font-weight:bold">Flash属性</font>&nbsp;</legend>
			<table style="font:12px;">
				<tr>	
					<td class="td_left"><i>&nbsp;</i>宽　　度</td>					
					<td><input type="text" class="input_text_normal" name="width" id="width"  style="width:144px;" valid="num" tip="请输入合法数字" maxlength="3" empty="true" onkeydown=""></td>
				</tr>
				<tr>	
					<td class="td_left"><i>&nbsp;</i>高　　度</td>					
					<td><input type="text" class="input_text_normal" name="height" id="height"  style="width:144px;" valid="num" tip="请输入合法数字" maxlength="3" empty="true" onkeydown=""></td>
				</tr>
				<tr>	
					<td class="td_left"><i>&nbsp;</i>水平距离</td>					
					<td><input type="text" class="input_text_normal" name="hspace" id="hspace"  style="width:144px;" valid="num" tip="请输入合法数字" maxlength="3" empty="true" onkeydown=""></td>
				</tr>
				<tr>	
					<td class="td_left"><i>&nbsp;</i>竖直距离</td>					
					<td><input type="text" class="input_text_normal" name="vspace" id="vspace"  style="width:144px;" valid="num" tip="请输入合法数字" maxlength="3" empty="true" onkeydown=""></td>
				</tr>
				<tr>
					<td class="td_left"><i>&nbsp;</i>自动播放</td>							
					<td><select name="type" style="width:144px;" class="input_select_normal">
                        	<option value="false">否</option>
							<option value="true">是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_left"><i>&nbsp;</i>是否循环</td>	
					<td>
						<select name="cycle" style="width:144px;" class="input_select_normal">
	                    	<option value="false">否</option>
							<option value="true">是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_left"><i>&nbsp;</i>对齐方式</td>	
					<td>
						<select name="align" style="width:144px;" class="input_select_normal">
							<option value="left">左</option>
							<option value="middle">中间</option>
							<option value="right">右</option>
							<option value="top">顶部</option>
							<option value="bottom">底部</option>
							<option value="absmiddle">绝对中间</option>
							<option value="absbottom">绝对底部</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_left"><i>&nbsp;</i>画面品质</td>	
					<td>
						<select name="quality" style="width:144px;" class="input_select_normal">
							<option value="high">高</option>
							<option value="low">低</option>
						</select>
					</td>
				</tr>
				<tr>
	    			<td class="td_left">&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  class="btn_normal" value="确 定" onclick="sure()"></td>
	 			</tr>
			</table>		
		</fieldset>
	  </div>
</form>
<iframe src="" name="delhidden" id="delhidden" width="100" height="0"></iframe>
</body>
</html>
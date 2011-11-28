<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>模板单元样式详细信息</title>
<%@include file="/templates/headers/header.jsp"%>
<style>
	table{width:760px;margin:32px auto 0 auto;font-size:12px;
	
	
	}
	td.title{width:82px;text-align:right;}
	td{padding:6px o;}
	textarea{width:520px;height:100px;}
	textarea.txt2{height:160px;}	
	select{width:180px;}
	.b-s{width:500px;margin-top:80px;}
	.b-s input{float:right;margin-right:18px;}
</style>
<script type="text/javascript">

	//原始样式名
	var nameStr;
	$(document).ready(function(){ 
		$("#templateUnitStyle.name").focus();
		nameStr = document.getElementById("templateUnitStyle.name").value;
		var message = document.getElementById("message").value;
		if(message == "添加或修改成功"){
			document.getElementById("message").value = "";
			window.location.href="templateUnitStyle.do?dealMethod=list&categoryId="+$("#categoryId").val();
		}
	});

	//选择的时候插入数据到文本框
	function insertAtCaret(txtobj, text){
		if (txtobj.createTextRange && txtobj.caretPos) { 
	     	var caretPos = txtobj.caretPos;               //获取光标所在的位置
	     	//替换光标处位置
	    	caretPos.text =caretPos.text.charAt(caretPos.text.length - 1) =='' ?text + '' : text; 
	     } else {
	     	txtobj.value = text;
	  	}   
	}
	//选择文本时保存光标位置-单击时同样
	function storePos (txtobj) { 
		if (txtobj.createTextRange) {  //获取选中的内容
			txtobj.caretPos = document.selection.createRange().duplicate();
		} 
	}

	//退出
	function quit(){
		window.location.href="templateUnitStyle.do?dealMethod=list&categoryId="+$("#categoryId").val();
	}

	//保存
	function save(){
		var name = document.getElementById("templateUnitStyle.name").value;
		if(name == null || name == "") {
			alert("请输入样式名称");
			return false;
		}
		var htmlContent = $("#htmlContent").val();
		if(htmlContent == null || htmlContent == "") {
			alert("请输入效果源码");
			return false;
		} 
		var names = $("#styleNameStr").val();
		var arr = new Array();
		arr = names.split(",");
		if(nameStr == name) {
			document.all("dealMethod").value="add";	
			document.forms[0].submit();	
		} else {
			for(var i = 0; i < arr.length; i++) {
				if(arr[i] == name) {
					alert("该类别已存在!");
					return false;
				}
			}	
			document.all("dealMethod").value="add";	
			document.forms[0].submit();	
		}
	}

	function fck_insertHtml(value){	
		var fck = FCKeditorAPI.GetInstance("templateUnitStyle.displayEffect");
		fck.InsertHtml(value);	
	}
</script>
</head>
<body>	
	<form id="templateUnitStyleForm" class="form-l" action="<c:url value='/templateUnitStyle.do'/>" method="post" name="templateUnitStyleForm">		
        <input type="hidden" name="dealMethod" id="dealMethod"/>
		<input type="hidden" name="categoryId" id="categoryId" value="${templateUnitStyleForm.categoryId}"/>
		<input type="hidden" name="styleId" id="styleId" value="${templateUnitStyleForm.styleId}"/>
		<input type="hidden" name="categoryName" id="categoryName" value="${templateUnitStyleForm.categoryName}"/>
		<input type="hidden" name="styleNameStr" id="styleNameStr" value="${templateUnitStyleForm.styleNameStr}"/>
		<input type="hidden" name="message" id="message" value="${templateUnitStyleForm.infoMessage}"/>
		<input type="hidden" name="isTemplateStyle" id="isTemplateStyle" value="1"/>
		<div class="form_div">
			<table>
				<tr>
					<td class="title">样式名称</td>
					<td   ><input type="text" class="input_text_normal" name="templateUnitStyle.name"   id="templateUnitStyle.name" value="${templateUnitStyleForm.templateUnitStyle.name}"></td>			
				</tr>
				<tr>
					<td class="title">单元类型</td>
					<td class="content">${templateUnitStyleForm.categoryName}</td>	
				</tr>
				<tr>
					<td></td>
					<td>		
						<SELECT id="htmlCode" name="htmlCode" class="input_select_normal" style="width:103px" onChange="insertAtCaret(htmlContent,this.value)">
							<OPTION VALUE=''>---HTML标签---</OPTION> 
							<option value="<table><tr><td></td></tr></table>">一行一列表格</option>
							<option value="<tr><td></td></tr>">表格插入一行</option>
							<option value="<td></td>">表格插入一列</option>
							<option value='<img src="" border="0"/>'/>图片</option>
							<option value="<br/>">回车</option>
							<option value="<pre></pre>">预格式化</option>
							<option value='<a href="" target="_self"></a>'>链接</option>
							<option value="<b></b>">粗体</option>
							<option value="<i></i>">斜体</option>
							<option value="<u></u>">下划线</option>
							<option value='<font size="" color=""></font>'>字体</option>
							<option value='<form method="post" name="" action="" target=""></form>'>表单</option>
							<option value='<input type="text" name="">'>输入框</option>
							<option value='<select name=""><option></option></select>'>列表框</option>
							<option value='<input type="radio" name="">'>单选按钮</option>
							<option value='<input type="checkbox" name="">'>复选框</option>
							<option value='<textarea name="" rows="" cols=""></textarea>'>文本框</option>
							<option value='<input type="reset" value="重置">'>重置按钮</option>
							<option value='<input type="submit" value="提交">'>提交按钮</option>
							<option value='<input type="password" name="">'>密码输入框</option>
							<option value='<input type="hidden" name="">'>隐含字段</option>
							<option value='<input type="image" src="">'>图片按钮</option>
						</SELECT>
						<SELECT NAME="unitCode" style="font:12px;width:103px" class="input_select_normal" onChange="insertAtCaret(htmlContent,this.value)">
							<option value='0'>---单元标签---</option>
							<c:forEach var="commonMap" items="${templateUnitStyleForm.commonMap}">
								<option value="<c:out value="${commonMap.value}" />">  
									<c:out value="${commonMap.key}" /> 
								</option>	
							</c:forEach>
							<c:forEach var="columnLinkMap" items="${templateUnitStyleForm.columnLinkMap}">
								<option value="<c:out value="${columnLinkMap.value}" />">  
									<c:out value="${columnLinkMap.key}" /> 
								</option>	
							</c:forEach>
							<c:forEach var="titleLinkMap" items="${templateUnitStyleForm.titleLinkMap}">									
								<option value="<c:out value="${titleLinkMap.value}" />"> 
									<c:out value="${titleLinkMap.key}" /> 
								</option>						
							</c:forEach>
							<c:forEach var="currentLocationMap" items="${templateUnitStyleForm.currentLocationMap}">									
								<option value="<c:out value="${currentLocationMap.value}" />"> 
									<c:out value="${currentLocationMap.key}" /> 
								</option>						
							</c:forEach>
							<c:forEach var="pictureNewsMap" items="${templateUnitStyleForm.pictureNewsMap}">									
								<option value="<c:out value="${pictureNewsMap.value}" />"> 
									<c:out value="${pictureNewsMap.key}" /> 
								</option>						
							</c:forEach>
							<c:forEach var="latestInfoMap" items="${templateUnitStyleForm.latestInfoMap}">									
								<option value="<c:out value="${latestInfoMap.value}" />"> 
									<c:out value="${latestInfoMap.key}" /> 
								</option>						
							</c:forEach>
							<c:forEach var="articleTextMap" items="${templateUnitStyleForm.articleTextMap}">									
								<option value="<c:out value="${articleTextMap.value}" />"> 
									<c:out value="${articleTextMap.key}" /> 
								</option>						
							</c:forEach>
						</SELECT>
					</td>
				</tr>
				<tr>
					<td class="title">效果源码</td>
					<td class="content"><TEXTAREA id="htmlContent" class="input_textarea_normal" name="htmlContent" onselect="storePos(this);" onclick="storePos(this);" onkeyup="storePos(this);">${templateUnitStyleForm.templateUnitStyle.content}</TEXTAREA></td>
				</tr>
				<tr>
					<td class="title">显示效果</td>	 
					<td class="content" width="520px"> 
						<c:if test="${templateUnitStyleForm.templateUnitStyle.displayEffect != null and templateUnitStyleForm.templateUnitStyle.displayEffect != ''}">
							<FCK:editor basePath="/script/fckeditor" instanceName="templateUnitStyle.displayEffect" value="${templateUnitStyleForm.templateUnitStyle.displayEffect}" toolbarSet="unit_style" height="230"></FCK:editor>
						</c:if>
						<c:if test="${templateUnitStyleForm.templateUnitStyle.displayEffect == null or templateUnitStyleForm.templateUnitStyle.displayEffect == ''}">
							<FCK:editor basePath="/script/fckeditor" instanceName="templateUnitStyle.displayEffect" value=" " toolbarSet="unit_style" height="230"></FCK:editor>
						</c:if>
					</td>
				</tr> 
				<tr>			
					<td colspan="2" align="center">				
					 	<input type="button" name="saveButton"  class="btn_normal"  id="saveButton" value="保存" onclick="save()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="backButton"  class="btn_normal" id="backButton" value="退出" onclick="quit()">  
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
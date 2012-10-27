<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>网上调查模板添加设置</title>
<%@include file="/templates/headers/header.jsp"%>
<style type="text/css" media="all">
	.form_cls label{
		width:60px;
		margin-right:5px;		
	}
	.form_cls{
		margin:10px auto;
	}
	.form_cls li{
		list-style-type:none;
		width:246px;
		margin:5px;	
		float:left; 
	}
	.form_cls .span{
		border:0px;
		width:60px;
		margin-right:5px;
		text-align:left;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		//注入调查类别
		var category = $("#Tcategory").val();
		$("#category").val(category);
		var more = $("#more").val();
		if(more != null && more != "" && more.split("##").length == 2){
			var moreExample = more.split("##")[1];
			$("#moreExample").val(moreExample);
		}
		if(category != null && category != ""){
			//一般调查全部主题或者主题列表
			if(category == "2" || category == "4"){
				$("#haveFeedBack").css("display", "none");
				//$("#haveMore").css("display", "");
				
			//一般调查指定主题
			}else if(category == "1"){
				$("#haveTheme").css("display", "");
				$("#theme").val($("#Ttheme").val());
				$("#haveQuestion").css("display", "");
				//$("#haveMore").css("display", "none");
				var question = $("#question").val();
				var str = question.split("###")[1].split(":::");
				var temp = "";
				for(var j = 0; j < str.length; j++){
					var t = j+1;
					temp += t+""+"."+str[j]+"\n";
				}
				document.getElementById("Tquestion").innerText = temp;
				
			//问卷调查指定主题
			}else if(category == "3"){
				$("#haveTheme").css("display", "");
				$("#theme").val($("#Ttheme").val());
				$("#haveAnswerCount").css("display", "");
				//$("#haveMore").css("display", "none");
			}
		}
	});

   	//改变调查类别
	function changeCategory(){
		var category = $("#category :selected").val();	
		var unit_unitId = $("#unit_unitId").val();
		var unit_categoryId = $("#unit_categoryId").val();
		var unit_columnId = $("#unit_columnId").val();
		var htmlContent = $("#htmlContent").html();
		// 不存在类别
		if(category == "0") {
			$("#haveTheme").css("display", "none");
			$("#haveQuestion").css("display", "none");
			$("#haveAnswerCount").css("display", "none");
			$("#haveFeedBack").css("display", "");
			$("#script").val("");
			$("#Tquestion").val("");
			$("#htmlContent").html("");
			//$("#haveMore").css("display","none");
			
		//一般调查指定主题
		}else if(category == "1"){
			$("#haveTheme").css("display", "");
			$("#haveQuestion").css("display", "");
			$("#haveFeedBack").css("display", "");
			$("#haveAnswerCount").css("display", "none");
			//$("#haveMore").css("display", "none");

			$("#moreExample").val("");
			$("#more").val("");
			
			$("#script").val("");
			$("#Tquestion").val("");
			var html = "<table width='95%' align='center'><tr><td align='center'><!--theme--></td></tr><!--for--><tr><td><!--number-->. <a href='#' onclick='getJsp(\"<!--url-->\")'><!--question--></a><p style='display:<!--display-->'><!--answer--></p></td></tr><!--/for--><tr><td align='center'><table style='display:<!--display-->'><tr><td><input type='button' value='投票' onclick='commit()'>  <input type='button' value='查看' onclick='check()'></td></tr></table></td></tr><tr style='display:<!--displayMore-->'><td align='right'><a href='<!--more-->'>更多>></a></td></tr></table>";
			$("#htmlContent").val(html);
			$.ajax({
				url:"onlineSurverySet.do?dealMethod=findTheme&category=1&unit_unitId="+unit_unitId+"&unit_categoryId="+unit_categoryId+"&unit_columnId="+unit_columnId,
			   type:"post",
			success:function(msg){
					var str = msg.split("###");
					var selobj = document.getElementById("theme");
					if(str[0] != "") {
						var themeIds = str[0];
						var themeNames = str[1];
						var themeId = themeIds.split(":::");
						var themeName = themeNames.split(":::");
						selobj.length = themeName.length+1;
						selobj.options[0].value = "0";
						selobj.options[0].text = "--请选择调查主题";
						for(var i = 0; i < themeName.length; i++) {
							selobj.options[i+1].value = themeId[i];
							selobj.options[i+1].text = themeName[i];
						}
						$("#theme").val("0");
					} else {   
						selobj.length = 1;
						selobj.options[0].value = "";
						selobj.options[0].text = "--请选择调查主题";
					}
				}
			});

		//一般调查全部主题或者主题列表
		}else if(category == "2" || category == "4"){
			$("#haveTheme").css("display", "none");
			$("#haveQuestion").css("display", "none");
			$("#haveFeedBack").css("display", "none");
			$("#haveAnswerCount").css("display", "none");
			//$("#haveMore").css("display","");
			$("#Tquestion").val("");

			$("#moreExample").val("");
			$("#more").val("");
			
			var html = "<table width='95%' align='center'><!--for--><tr><td style='border-bottom:1px dotted #CCCCCC;'><!--number-->. <a href='#' onclick='getJsp(\"<!--url-->\")'><!--kindname--></a></td></tr><!--/for--><tr style='display:<!--display-->'><td colspan='2' align='right'><a href='<!--more-->' target='_blank'>更多>></a></td></tr></table>";
			$("#htmlContent").val(html);			
			var script = "";
			if(category == "2"){
				//script = "<object type='text/x-scriptlet' data='/"+app+"/outOnlineSurvery.do?dealMethod=getNormalPagination&appName="+app+"&unit_unitId="+unit_unitId+"' width='100%' height='300'></object>";
				script = "<script src='/"+app+"/plugin/onlineSurvey_manager/out/out_normal_online_list.jsp?unit_unitId="+unit_unitId+"&appName="+app+"&display=none'><\/script>";
			}else {
				//script = "<object type='text/x-scriptlet' data='/"+app+"/outOnlineSurvery.do?dealMethod=getNormalPagination&appName="+app+"&unit_unitId="+unit_unitId+"' width='100%' height='300'></object>";
				script = "<script src='/"+app+"/plugin/onlineSurvey_manager/out/out_answer_online_list.jsp?unit_unitId="+unit_unitId+"&appName="+app+"'><\/script>";
			}
			$("#script").val(script);
			
		//问卷调查指定主题
		}else{
			$("#haveTheme").css("display", "");
			$("#haveQuestion").css("display", "none");
			$("#haveFeedBack").css("display", "");
			$("#haveAnswerCount").css("display", "");
			//$("#haveMore").css("display","none");
			$("#moreExample").val("");
			$("#more").val("");
			var theme = $("#theme :selected").val();
			var script = "<script src='/"+app+"/plugin/onlineSurvey_manager/out/out_answer_online_list.jsp?unit_unitId="+unit_unitId+"&appName="+app+"&theme="+theme+"&display=none'><\/script>";
			$("#script").val(script);
			$("#Tquestion").val("");
			var html = "<table width='95%' align='center'><tr><td align='center'><a href='#' onclick='getJsp(\"<!--url-->\")'><!--theme--></a></td></tr><tr><td><!--for--><!--number-->. <!--question--><br><!--/for--></td></tr><tr><td align='center'><table style='display:<!--display-->'><tr><td><input type='button' value='投票' onclick='commit()'>	<input type='button' value='查看' onclick='check()'></td></tr></table></td></tr></table>";
			$("#htmlContent").val(html);
			$.ajax({
				url:"onlineSurverySet.do?dealMethod=findTheme&category=3&unit_unitId="+unit_unitId+"&unit_categoryId="+unit_categoryId+"&unit_columnId="+unit_columnId,
			   type:"post",
			success:function(msg){
					var str = msg.split("###");
					var selobj = document.getElementById("theme");
					if(str[0] != "") {
						var themeIds = str[0];
						var themeNames = str[1];
						var themeId = themeIds.split(":::");
						var themeName = themeNames.split(":::");
						selobj.length = themeName.length+1;
						selobj.options[0].value = "0";
						selobj.options[0].text = "--请选择调查主题";
						for(var i = 0; i < themeName.length; i++) {
							selobj.options[i+1].value = themeId[i];
							selobj.options[i+1].text = themeName[i];
						}
						$("#theme").val("0");
					} else {   
						selobj.length = 1;
						selobj.options[0].value = "";
						selobj.options[0].text = "--请选择调查主题";
					}
				}
			});
		}
	}

	//显示样式管理
	function showStyleManager(){
		win = showWindow("showStyleManager","样式管理","<c:url value='/templateUnitStyle.do?dealMethod=list&categoryId=" + $("#unit_categoryId").val()+"'/>", 0, 0, 850, 590);	 			
	}

	// 指定栏目
	function fixedColumn1() {
		win = showWindow("chooseColumn", "指定栏目", "<c:url value='/module/template_set/unit/choose_column.jsp'/>", 170, 20, 300, 300);
	}	

	function managerCss() {
		var css = $("#unit_css").val();
		win = showWindow("css", "管理css", "<c:url value='/module/template_set/manage_css.jsp?css=" + css + "'/>", 0, 0, 490, 350);
	}
	
	function closeNewChild() {		
		closeWindow(win);
	}

	//选择的时候插入数据到文本框
	function insertAtCaret(txtobj, text){
		if(txtobj.createTextRange && txtobj.caretPos){ 
	    	var caretPos = txtobj.caretPos;               //获取光标所在的位置
	       	//替换光标处位置
	       	caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) =='' ?text + '' : text; 
	    }else{
	       	txtobj.value = text;   
	    }
	}
	   
	//选择文本时保存光标位置-单击时同样   
	function storePos(txtobj){ 
		if(txtobj.createTextRange){                     //获取选中的内容
			txtobj.caretPos = document.selection.createRange().duplicate(); 
		}
	} 
	
	// 用于键盘事件（只允许输入整数：包括负数）
	function myKeyDown(id) {
		var k = window.event.keyCode;   
		if(document.getElementById(id).value.length >= 1){
			if ((k==46)||(k==8)||(k==189)||(k==190)||(k==110)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)){
				
			}else if(k==13){
				window.event.keyCode = 9;
			}else{
				window.event.returnValue = false;
			}
		}else{
			if ((k==46)||(k==8)||(k==189)||(k==109)||(k==190)||(k==110)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)){
				
			}else if(k==13){
				window.event.keyCode = 9;
			}else{
				window.event.returnValue = false;
			}
		}
	}

	function save(){
		var category = $("#category :selected").val();
		if(category == "0"){
			alert("请选择调查类别");
			return false;
		}
		if(category == "1" || category == "3"){
			var theme = $("#theme :selected").val();
			if(theme == "0"){
				alert("请选择调查主题");
				return false;
			}
			if(category == "1"){
				var question = $("#Tquestion").val();
				if(question == ""){
					alert("请选择调查问题");
					return false;
				}
			}
		}
		$("#dealMethod").val("save");
    	var options = {	 	
 		    	url: "onlineSurverySet.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		  };
		$('#onlineSurverySetForm').ajaxSubmit(options);
    }

    function getQuestion(){
		var theme = $("#theme :selected").val();
		if(theme == "0"){
			alert("请选择指定主题");
			return false;
		}
		win = showWindow("queston", "选择问题", "<c:url value='/onlineSurveyConcret.do?dealMethod=findQuestionList&nodeId='/>"+theme, 0, 0, 650, 400);
    }

    function changesource(){
        var category = $("#category :selected").val();
        if(category == "3"){ 
			var themeId = $("#theme :selected").val();
			var unit_unitId = $("#unit_unitId").val();
			var script = "<script src='/${appName}/plugin/onlineSurvey_manager/out/out_answer_online_detail.jsp?unit_unitId="+unit_unitId+"&themeId="+themeId+"&appName="+app+"&display=none'><\/script>";
			$("#script").val(script); 
        }
    }

    function chooseMore(){
    	win = showWindow("chooseColumn", "指定栏目", "<c:url value='/module/template_set/unit/onlineSurvery_manager/fixed_more_column.jsp'/>", 170, 20, 300, 300);
    }
</script> 
</head>
<body>
<form id="onlineSurverySetForm" action="<c:url value="/onlineSurverySet.do"/>" name="onlineSurverySetForm" method="post">
  	<input type="hidden" value="${appName}" id="apprealpath"/>
	<input type="hidden" id="unit_unitId" name="unit_unitId" value="${onlineSurverySetForm.unit_unitId}"/>
	<input type="hidden" id="unit_categoryId" name="unit_categoryId" value="${onlineSurverySetForm.unit_categoryId}"/>
	<input type="hidden" id="unit_columnId" name="unit_columnId" value="${onlineSurverySetForm.unit_columnId }" />

	 <input type="hidden" name="answerRow" value="${onlineSurverySetForm.answerRow }"/>
    <input type="hidden" name="height" value="${onlineSurverySetForm.height }"/>
	<input type="hidden" name="width" value="${onlineSurverySetForm.width }"/>

	<input type="hidden" name="dealMethod" id="dealMethod" />
    <input type="hidden" name="message" id="message" value="${onlineSurverySetForm.infoMessage}" />
    <!-- 调查类别 -->
	<input type="hidden" id="Tcategory" value="${onlineSurverySetForm.category}" />
	<!-- 调查主题 -->
	<input type="hidden" id="Ttheme" value="${onlineSurverySetForm.theme}" />

	<div id="setParamArea" style="position:absolute; left:3px; top:10px; width:278px; height:400px; z-index:2;">
	<fieldset style="height:400">
		<legend style="color:#0000ff;">参数设置</legend>
		<div id="paramSet" class="form_cls"  style="width:100%;align:center;">
			<div>
				<li>
					<select name="category" id="category" style="width:100%;" onchange="changeCategory()">
						<option value="0">--请选择调查类别</option>
		                <option value="1">一般调查指定主题</option>
						<option value="2">一般调查全部问题</option>   
		                <option value="3">问卷调查指定主题 </option>
		                <option value="4">主题列表</option>
					</select>
				</li>
			</div>
			<div id="haveTheme" style="display:none;">
				<li>
					<select name="theme" id="theme" style="width:100%" onchange="changesource()">
						<option value='0' SELECTED>--请选择调查主题</option> 
						<c:if test="${onlineSurverySetForm.themeList != null}">
		                	<c:forEach var="list" items="${onlineSurverySetForm.themeList}">
		                    	<option value="${list.id}">${list.name}</option>
		                    </c:forEach>
		               	</c:if>
					</select>
				</li>
			</div>
			<div id="haveQuestion" style="display:none;">
				<li>
					<label>已选问题： </label>
					<input type="hidden" name="question" id="question" value="${onlineSurverySetForm.question }"/>
		            <textarea class="input"  id="Tquestion" cols="19" rows="4" readonly></textarea>	
					<input type="button" value="选择"  class="btn_small"   onclick="getQuestion()"/> 
				</li>
			</div>
		    <div id="haveAnswerCount" style="display:none;">
			<!-- 
				<li>
					<label>问卷列表&nbsp;&nbsp;&nbsp;</label>			
					<input type="text" name="answerCount" id="answerCount" value="${onlineSurverySetForm.answerCount }"  maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>&nbsp;列
		        </li>
		         -->
			</div>
        	<div id="haveFeedBack">
				<li>
					<label>反馈框大小</label>			
					<input type="text" name="rowCount" id="rowCount"  value="${onlineSurverySetForm.rowCount }"  maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')" />
					<span>行</span>				
					<input type="text" name="colCount" id="colCount"  value="${onlineSurverySetForm.colCount }"  maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')" />
					<span>列</span>
				</li>
            </div>
			<div id="haveMore">
				<li>
					<label>更多</label>
					<input type="hidden" name="more" id="more" value="${onlineSurverySetForm.more }"/>
					<input type="text" name="moreExample" id="moreExample" style="width:130px;" />
					<input type="button" onclick="chooseMore()" value="选择"/>
				</li>
			</div>
			<li>
				<label>列表默认</label>
				<input type="text" name="defaultCount" id="defaultCount"  value="${onlineSurverySetForm.defaultCount }"  maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')" />&nbsp;条
			</li> 
		</div>
	</fieldset>
	</div>		   
		
	<div id="displayArea"  style="position:absolute; left:283px; top:10px; width:320px; height:140px; z-index:2;">
		<fieldset style="width:320px;height:140px;"><legend style="color:#0000ff;"><b>嵌入源码</b>&nbsp;</legend>
			<div id="display" style="width:307px;height:135px;">
				<textarea rows="8" cols="37" name="script" id="script">${onlineSurverySetForm.script }</textarea>
			</div>
		</fieldset>  
	</div>
	<div id="codeArea"  style="position:absolute; left:283px; top:160px; width:320px; height:260px; z-index:2; ">
		<fieldset id="code" style="width:320px;height:250px;"><legend style="color:#0000ff;"><b>效果源码</b>&nbsp;</legend>
		<SELECT   id="htmlCode" name="htmlCode" style="width:103px" onChange="insertAtCaret(htmlContent,this.value)">
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
		<select  name="unitCode" id="unitCode" style="width:103px" onchange="insertAtCaret(htmlContent,this.value)">
			<option value=''>---单元标签---</option>
              	<c:forEach var="map" items="${onlineSurverySetForm.map}">
			<option value="<c:out value="${map.value}" />">  
				<c:out value="${map.key}" /> 
			</option>	
			</c:forEach>
		</select>
		<input type="button" value="样式管理" class="btn_small" onClick="showStyleManager()"/>	
		<input type="button" value="css管理" class="btn_small" onClick="managerCss()"/>
		<TEXTAREA id="htmlContent"  name="htmlContent" ROWS="11"  onselect="storePos(this);" onclick="storePos(this);" onkeyup="storePos(this);"  onfocus="storePos(this);" COLS="37" >${onlineSurverySetForm.htmlContent}</TEXTAREA>
		</fieldset> 				
    </div>
	<div id="saveArea" style="position:absolute;height:15px;z-index:4;left: 250px;top: 410px;">			
		<input type="button" value="保存" class="btn_normal" onclick="save()"/>
	</div>
</form>
</body>
</html>
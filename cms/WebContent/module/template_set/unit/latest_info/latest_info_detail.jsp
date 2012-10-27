<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>最新信息</title>
  <%@include file="/templates/headers/header.jsp"%>
  <style type="text/css" media="all">
	.text{
		width:350px;
		margin-left:106px;
	}
	#colDiv{
		width:350px;
		margin-left:110px;
	}
	
	.form_cls label {
		width:100px;
		margin-right:5px;		
	}
	.form_cls {
		margin:10px auto;
	}
	.form_cls li {
		list-style-type:none;
		width:555px;
		margin:5px;	
		float:left; 
	}
	.form_cls .span {
		border:0px;
		width:60px;
		margin-right:5px;
		text-align:left;
	}
	
</style>
  <script>

  	$(document).ready(function() {
  		document.getElementById("unit_css1").innerHTML = $("#unit_css").val();
  		var unitTypeValue = $("#unitTypeValue").val();
  		var tr1 = document.getElementById("colDiv");
  		if(unitTypeValue == 0){  		
  			document.all.unitType.value = unitTypeValue;
  			var selectValue = $("#selectColValue").val();
  			if(selectValue >= 1){
  	  			var allColumn = $("#allColumn").val();
  	  			if(allColumn == null || allColumn == "" || allColumn == 0) {
	  	  			tr1.innerText = "";
  	  			} else {
  					tr1.innerText = (allColumn.split("##"))[1];
  	  			}
  			}
  			//document.all.selectCol.value = selectValue;
  			if(selectValue == 0) {
				$("#chooseColumn0").attr("checked", true);
				document.getElementById("colDiv").style.display = "none";
				
  			} else if(selectValue == 1){
  				$("#chooseColumn1").attr("checked", true);
  				document.getElementById("colSelect_2").style.display = "none";
  				
  			} else {
  				$("#chooseColumn2").attr("checked", true);
  				document.getElementById("colSelect_1").style.display = "none";
  				document.getElementById("colSelect_2").style.display = "";
  			}
  			
  		}else if(unitTypeValue == 1){
  	  		$("#fixedColumnType").attr("checked", true);
  	  		$("#tr_1").attr("style", "display:none");
  	  		$("#tr_2").attr("style", "display:inline");
  			var chooseValue = $("#chooseColumnValue").val();
  			//$("#chooseColumn").val(chooseValue);
  			var strChooseValue = chooseValue.split("##");
  			if(strChooseValue != null ){
  				$("#fixedColumnExample").val(strChooseValue[1]);
  			}
  		}
  		if($("#columnPrefixPic").val() == 1) {
			$("#columnPrefixPic").attr("checked", true);
		}
		if($("#columnSuffixPic").val() == 1) {
			$("#columnSuffixPic").attr("checked", true);
		}
		if($("#moreLinkPic").val() == 1) {
			$("#moreLinkPic").attr("checked", true);
		}
		var page = $("#page").val();
		if(page == "yes") {
			document.getElementsByName("isPage")[0].checked = "true";
			document.getElementById("infoPage").style.display = "";
			document.getElementById("infoNotPage").style.display = "none";
		} 
		if($("#moreLinkColumn").val() != null && $("#moreLinkColumn").val() != "") {
			$("#chooseMoreColumnExample").val($("#moreLinkColumn").val().split("##")[1]);
		}
		$("#pageSite").val($("#latestInfoPager").val());
	}); 
	//参数设置与扩展参数切换
	function changeSet(paramSet){
		if(paramSet=="paramSet1"){
			document.getElementById('paramSet1').style.display="block";
			document.getElementById('paramSet2').style.display="none";
		}else if (paramSet=="paramSet2"){
			document.getElementById('paramSet1').style.display="none";
			document.getElementById('paramSet2').style.display="block";
		}else if (paramSet=="paramSet3"){
			document.getElementById('paramSet1').style.display="none";
			document.getElementById('paramSet2').style.display="none";
		}
	}

	//选择目录范围
	function checkSelectCol(obj) {
		var val = obj.value;
		var tr1 = document.getElementById("colDiv");
		var btn1 = document.getElementById("colSelect_1");
		var btn2 = document.getElementById("colSelect_2");

		switch (val) {
			case "0":
				tr1.style.display = "none";				
				btn1.style.display = "none";
				btn2.style.display = "none";
				break;
			case "1":
				tr1.style.display = "";				
				btn1.style.display = "";
				btn2.style.display = "none";
				if($("#selectColValue").val() == 1){
					if(!$("#allColumn").val().isEmpty()){
						tr1.innerText = $("#allColumn").val().split("##")[1];
					}else{
						tr1.innerText = "";
					} 
				}else{
					tr1.innerText = "";
				}
				break;
			case "2":
				tr1.style.display = "";				
				btn1.style.display = "none";
				btn2.style.display = "";
				if($("#selectColValue").val() == 2){
					if(!$("#allColumn").val().isEmpty()){
						tr1.innerText = $("#allColumn").val().split("##")[1];
					}else{
						tr1.innerText = "";
					} 
				}else{
					tr1.innerText = "";
				}
				break;
		}
	}

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
		if (txtobj.createTextRange)                      //获取选中的内容
			txtobj.caretPos = document.selection.createRange().duplicate(); 
	} 
	
	//单元类型
	function funchange(){
		var unittype = document.latestInfoForm.unitType[0].checked?0:1;		
		if( unittype *1 == 1 ){
			document.all.tr_1.style.display = "none";
			document.all.tr_2.style.display = "";
		}else{
			document.all.tr_1.style.display = "";
			document.all.tr_2.style.display = "none";
		}
	}

	
	function closeNewChild() {
		closeWindow(win);
	}

	
	//设置CSS
	function managerCss() {
		var css = $("#unit_css").val();
		win = showWindow("css", "管理css", "<c:url value='/module/template_set/manage_css.jsp?css=" + css + "'/>", 0, 130,490,350);
	}
	
	//选择栏目
	function selectColumn(choose) {
		if(choose == 1){
			win = showWindow("chooseColumn", "选择栏目", "<c:url value='/module/template_set/unit/choose_checkboxcolumn.jsp'/>", 0, 0, 300, 300);
		}else if(choose == 2){
			win = showWindow("chooseColumn", "选择栏目", "<c:url value='/module/template_set/unit/choose_column.jsp'/>", 0, 0, 300, 300);
		}
	}
	
	//保存数据
	function btn_save() {	
		if($("#htmlContent").val().trim() == null || $("#htmlContent").val().trim() == "") {
			alert("效果源码不能为空");
			return false;
		}
		// 如果单元类型选择其它，并且来源选择包含所选、不包含所选	
		var unitTypeValue = $("#unitTypeValue").val();
	
		if($("#fixedColumnType1").attr("checked")){
			var tr1 = document.getElementById("colDiv");
			if(!$("#chooseColumn0").attr("checked")){
				if(tr1.innerText.isEmpty()){
					alert("请选择栏目");
					return false;
				}
			}else{
				tr1.innerText = "";
			}
		}else {
			if($("#fixedColumnExample").val() == null || $("#fixedColumnExample").val() == ""){
				alert("请选择指定栏目");
				return false;
			}	
		}
		$("#dealMethod").val("saveConfig");
		if($("#columnPrefixPic").attr("checked") == true) {
			$("#columnPrefixPic").val(1);
		} else {
			$("#columnPrefixPic").val(0);
		}
		if($("#columnSuffixPic").attr("checked") == true) {
			$("#columnSuffixPic").val(1);
		} else {
			$("#columnSuffixPic").val(0);
		}
		if($("#moreLinkPic").attr("checked") == true) {
			$("#moreLinkPic").val(1);
		} else {
			$("#moreLinkPic").val(0);
		}
		var page = document.getElementsByName("isPage");
		var i;
		for(i = 0; i < page.length; i++){
			if(page[i].checked){
				$("#page").val(page[i].value);            
			}
		}
	 	var options = {	 	
	 		   type: "post",
 		    	url: "<c:url value='/latestInfo.do'/>",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		};
		$('#latestInfoForm').ajaxSubmit(options);	
		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}


	// 选择标题前缀
	function chooseTitlePrefix() {
		win = parent.showWindow("chooseTitlePrefix", "选择标题前缀","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=1&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}

	// 选择标题后缀
	function chooseTitleSuffix() {
		win = parent.showWindow("chooseTitleSuffix", "选择标题后缀","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=2&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}

	function changePage(param) {
		// 分页
		if(param == "yes") {
			document.getElementById("infoPage").style.display = "";
			document.getElementById("infoNotPage").style.display = "none";
			
		// 不分页		
		} else {
			document.getElementById("infoPage").style.display = "none";
			document.getElementById("infoNotPage").style.display = "";
		}
	}

	function chooseMoreLatestInfo() {
		win = showWindow("chooseMoreLatestInfoLink", "选择最新信息更多链接栏目", "<c:url value='/module/template_set/unit/latest_info/fixed_latestInfo_more_column.jsp'/>", 0, 0, 300, 300);
	}

	function clearMoreLatestInfo() {
		$("#chooseMoreColumnExample").val("");
		$("#moreLinkColumn").val("");
	}

	// 选择更多
	function chooseMorePic(){
		win = showWindow("chooseMore", "选择更多","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=3&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}


	// 用于键盘事件（只允许输入整数：包括负数）
	function myKeyDown(id) {
		var k=window.event.keyCode;   
		if(document.getElementById(id).value.length >=1 ){
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
</script>
</head>
<body>
<form action="<c:url value='latestInfo.do'/>" name="latestInfoForm" id="latestInfoForm">
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" id="unit_unitId" name="unit_unitId" value="${latestInfoForm.unit_unitId}"/>
	<input type="hidden" id="unit_categoryId" name="unit_categoryId" value="${latestInfoForm.unit_categoryId}"/>
	<input type="hidden" id="unitTypeValue" name="unitTypeValue"  value="${latestInfoForm.unitType}"/>
	<input type="hidden" id="allColumn" name="allColumn"  value="${latestInfoForm.allColumn}"/>
	<input type="hidden" id="selectColValue" name="selectColValue"  value="${latestInfoForm.selectCol}"/>
	<input type="hidden" id="chooseColumnValue" name="chooseColumnValue"  value="${latestInfoForm.chooseColumn}"/>
	<input type="hidden" id="hasSaved" name="hasSaved" value="N" />	
	<input type="hidden" id="latestInfoPager" value="${latestInfoForm.pageSite }"/> 
	<input type="hidden" id="page" name="page" value="${latestInfoForm.page }"/>
	<span style="display:none">
		<div id="unit_css1" ></div>		
		<TEXTAREA id="unit_css" name="unit_css" >${latestInfoForm.unit_css}</TEXTAREA>  		 
	</span>
	<div id="belowleft" style="position:absolute; left:3px; top:10px; width:580px;height:440px; ">	
		<fieldset style="height:440" id="obj1" style="display:">
			<legend style="color:#0000ff;">
				<!-- <a href="javascript:;"  >来源、嵌入源码</a> -->		
					<a href="javascript:;" onclick="changeSet('paramSet1');"><font style="color:#0000ff;">参数设置</font></a>&nbsp;||&nbsp;
					<a href="javascript:;" onclick="changeSet('paramSet2');"><font style="color:#0000ff;">效果源码</font></a>		
			</legend>
			<div id="paramSet1" class="form_cls"  style="display: ;width:100%;align:center">
				<li>
					<label>单元类型</label>
					<input type="radio" name="unitType"  id="fixedColumnType1" class="input_blank" value="0"  checked onclick='funchange();'>其它栏目
					&nbsp;&nbsp;&nbsp;&nbsp; 
					<input type="radio" name="unitType" id="fixedColumnType"  class="input_blank" value="1"  onclick='funchange();'>指定栏目
				</li>
				<li>
					<label>选择目录范围</label>		
					<span id="tr_1" style="display:">
						<input type="radio" name="selectCol" value="0" id="chooseColumn0" class="input_blank" onclick="checkSelectCol(this);" >包含所有栏目&nbsp; 		
						<input type="radio" name="selectCol" value="1" id="chooseColumn1" class="input_blank" onclick="checkSelectCol(this);" checked>包含所选栏目&nbsp; 
						<input type="button" id="colSelect_1"  class="btn_small"  style="display:"  value="√" onclick="selectColumn('1')">				
						<input type="radio" name="selectCol" value="2"	id="chooseColumn2" class="input_blank" onclick="checkSelectCol(this);">不包含所选栏目&nbsp;
						<input type="button" id="colSelect_2"  class="btn_small"  style="display:none"   value="√" onclick="selectColumn('1')"></td>
						<div id="colDiv"  style="width:350px;height:80px;border:1px #808080 solid;background-color:#FFFFFF;overflow:auto"></div>
			 
					</span>
					<span id="tr_2" style="display:none">
						指定栏目
						<input type="hidden" name="chooseColumn" id="fixedColumn" value="${latestInfoForm.chooseColumn}"/>
						<input type="text" readonly  name="fixedColumnExample" id="fixedColumnExample" />
					
						<input type="button" id="colSelect_3"  class="btn_small"   value="√" onclick="selectColumn('2')">
					</span>			
				</li>
				<li>
					<label>是否分页</label>
					<input type="radio" name="isPage" class="input_blank" value="yes" onclick="changePage('yes')"/>是
					<input type="radio" name="isPage" class="input_blank" value="no" checked onclick="changePage('no')"/>否
				</li>
				<li>
					<span  id="infoNotPage">
						<label>信息显示</label>
						<input type="text" name="row"  size="4" value="${latestInfoForm.row}" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>&nbsp;行
						<input type="text" name="col"  size="4" value="${latestInfoForm.col}" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>&nbsp;列
						<br><br>
						<label>更多</label>
						<input type="text" name="moreLink" id="moreLink" value="${latestInfoForm.moreLink}" maxlength="255"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox"  class="input_blank" name="moreLinkPic" id="moreLinkPic" value="${latestInfoForm.moreLinkPic}">图片&nbsp;
						<input title="选择图片" type="button"  class="btn_small"  value="√" onclick="chooseMorePic()">	
						<br><br>
						<label>更多分页</label>
						<input type="hidden" name="moreLinkColumn" id="moreLinkColumn"  value="${latestInfoForm.moreLinkColumn }" />
						<input type="text" name="chooseMoreColumnExample" id="chooseMoreColumnExample"  readonly/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input title="选择最新信息分页" type="button"  class="btn_small"  value="√" onclick="chooseMoreLatestInfo()">
						&nbsp;&nbsp;&nbsp;
						<input type="button" name="clearMore" class="btn_small" value="清空" onclick="clearMoreLatestInfo()"/>
					</span>
				</li>
				<li>
					<span id="infoPage" style="display:none;">
						<label>显示记录总数</label>
						<input type="text" name="count"  value="${latestInfoForm.count}" size="3" onkeyup="value=value.replace(/[^\d]/g,'')">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每页显示记录数
						<input type="text" name="pageCount"  value="${latestInfoForm.pageCount}" size="2" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')">
						<br><br>	
						<label>分页位置</label>
						<select id="pageSite" name="pageSite" style="width:164px;">
							<option value="left">居左对齐</option>
							<option value="center">居中对齐</option>
							<option value="right">居右对齐</option>
						</select>
					</span>	
				</li>
				<li>
					<label>标题字数</label>
					<input type="text" name="titleWordCount"  value="${latestInfoForm.titleWordCount}" size="3" maxlength="3"  onkeyup="value=value.replace(/[^\d]/g,'')">
				</li>
				<li>
					<label>标题前缀</label>
					<input type="text" name="titleHead" value="${latestInfoForm.titleHead}" id="columnPrefix" maxlength="255"/>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox"  class="input_blank" name="titleHeadPic" id="columnPrefixPic" value="${latestInfoForm.titleHeadPic }"/>图片&nbsp;
					<input title="选择图片"  type="button"  class="btn_small"  value="√" onclick="chooseTitlePrefix()"/>
				</li>
				<li>
					<label>标题后缀</label>
					<input type="text" name="titleEnd" value="${latestInfoForm.titleEnd}" id="columnSuffix"  maxlength="255"/>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox"  class="input_blank" name="titleEndPic" id="columnSuffixPic" value="${latestInfoForm.titleEndPic}"/>图片&nbsp;
					<input title="选择图片" type="button"  class="btn_small"  value="√" onclick="chooseTitleSuffix()"/>
				</li>
			</div>
			<div id="paramSet2" class="form_cls"  style="display:none;width:100%;align:center">
				<li>
					<label>嵌入源码</label>	
					<select name="unitcode" style="width:103px" onchange="insertAtCaret(htmlContent,this.value)">
						<option value=''>---单元标签---</option>
						<c:forEach var="commonMap" items="${latestInfoForm.commonMap}" >
							<c:if test="${commonMap.value != '<!--appName-->' and commonMap.value != '<!--siteId-->'}">
								<option value="<c:out value="${commonMap.value}" />"><c:out value="${commonMap.key}" /></option>
							</c:if>	
						</c:forEach>
						<c:forEach var="titleMap" items="${latestInfoForm.map}" >									
							<option value="<c:out value="${titleMap.value}" />"><c:out value="${titleMap.key}" /></option>						
						</c:forEach>
						<c:forEach var="latestMap" items="${latestInfoForm.latestMap}" >									
							<option value="<c:out value="${latestMap.value}" />"><c:out value="${latestMap.key}" /></option>						
						</c:forEach>
					</select>
					<input type="button" value="css管理"  class="btn_small"  onClick="managerCss()" />	
					<c:if test="${latestInfoForm.htmlContent != null and latestInfoForm.htmlContent != ''}">
						<textarea  class="text"  id="htmlContent" name="htmlContent" onselect="storePos(this);" onclick="storePos(this);" onkeyup="storePos(this);" rows="8" cols="52" wrap="soft">${latestInfoForm.htmlContent}</textarea>
					</c:if>
					<c:if test="${latestInfoForm.htmlContent == null or latestInfoForm.htmlContent == ''}">
						<textarea  class="text"  id="htmlContent" name="htmlContent" onselect="storePos(this);" onclick="storePos(this);" onkeyup="storePos(this);" rows="8" cols="52" wrap="soft"><!--for--><!--titlePrefix--><a href="<!--articleurl-->"><!--articletitle--></a><!--titleSuffix--> <!--articleauthor-->  (<!--createTime-->)<br><!--/for--></textarea>
					</c:if>
				</li> 
			</div>
		</fieldset>
	</div>		   

	<div id="saveArea1" style="position:absolute;height:15px;z-index:4;left: 250px;top: 420px;display:none">			
		<input type="button" value="保存"  class="btn_normal"   onclick="btn_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<div id="saveArea2" style="position:absolute;height:15px;z-index:4;left: 250px;top: 420px;display:">			
		<input type="button" value="保存"  class="btn_normal"  onclick="btn_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
</form>
 </BODY>
</HTML>

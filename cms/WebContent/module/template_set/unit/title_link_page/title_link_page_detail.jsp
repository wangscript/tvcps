<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>标题链接分页</title>
<%@include file="/templates/headers/header.jsp"%>
<style type="text/css" media="all">	
	.form_cls label {
		width:70px;
		margin-right:5px;		
	}
	.form_cls {
		margin:10px auto;
	}
	.form_cls li {
		list-style-type:none;
		width:246px;
		margin:5px;	
		float:left; 
	}
	.form_cls .span {
		border:0px;
		width:60px;
		margin-right:5px;
		text-align:left;
	}
	#display img {
	    max-width:307px;
	    max-height:135px;
		zoom:expression( function(elm) {
		          if(elm.width<elm.height) {
		             if (elm.height>135) {
		         		var oldVW = elm.height; elm.height=135;             
		                elm.width = elm.width*(135/oldVW );        
		             }
		          }
		          if(elm.width>elm.height) {
			     	 if (elm.width>307) {
		         		 var oldVW = elm.width; elm.width=307;             
		                 elm.height = 110;        
		              }
		          }
		          elm.style.zoom = '1';     
		      }
		(this));
	}
</style>
<script>
	$(document).ready(function() {
		document.getElementById("unit_css1").innerHTML = $("#unit_css").val();
		//选择显示样式
		var chooseViewStyle = document.getElementById("chooseViewStyle").value;
		//选择内容来源
		var chooseContextFrom = document.getElementById("chooseContextFrom").value;
		var titleHeadPic = "${titleLinkPageForm.titleHeadPic}";
		var titleEndPic = "${titleLinkPageForm.titleEndPic}";
		
		if(chooseViewStyle != null && chooseViewStyle != "" && chooseViewStyle != 0){
			document.all.viewStyle.value = chooseViewStyle;
			document.getElementById("display").innerHTML = $("#viewImg").val();
		}
		if(chooseContextFrom != null && chooseContextFrom != "" && chooseContextFrom != 0){
			document.all.contextFrom.value = chooseContextFrom;
			if(chooseContextFrom > 2) {
				document.getElementById("havecolumn").style.display = "";
				var fixedColumn = $("#fixedColumnExample").val();
				var columnName =  fixedColumn.split("##");
				$("#fixedColumnExample").val(columnName[1]);
			}
		}
		if(titleHeadPic == "1"){
			$("#columnPrefixPic").attr("checked", true);
		}
		if(titleEndPic == "1"){
			$("#columnSuffixPic").attr("checked", true);
		}
		$("#pageSite").val($("#titleLinkPager").val());
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
		if (txtobj.createTextRange)                      //获取选中的内容
			txtobj.caretPos = document.selection.createRange().duplicate(); 
	} 

	//参数设置与扩展参数切换
	function changeSet(paramSet){
		if(paramSet=="paramSet1"){
			document.getElementById('paramSet1').style.display="block";
			document.getElementById('paramSet2').style.display="none";
		}else{
			document.getElementById('paramSet1').style.display="none";
			document.getElementById('paramSet2').style.display="block";
		}
	}
	
	//改变显示样式
	function changeshow(txtobj,obj) {	
		/*var styleValue = obj.value;		
	    str = styleValue.split("##");  
	    if(str.length == 2) {
			document.getElementById("display").innerHTML = str[1];
			document.getElementById("htmlContent").value = str[0];
	   } else {
			document.getElementById("display").innerHTML = "";
			document.getElementById("htmlContent").value = "";
	   }*/
		$.ajax({
			url: "templateUnitStyle.do?dealMethod=findStyleById&styleId="+obj.value+"&categoryId="+$("#unit_categoryId").val(),
		   type: "post", 
	    success: function(msg) {		
			    str = msg.split("##");  
			    if(str.length == 2) {
				    document.getElementById("display").innerHTML = str[0];
					document.getElementById("htmlContent").value = str[1];
			   } else {
					document.getElementById("display").innerHTML = "";
					document.getElementById("htmlContent").value = "";
			   }			 
	    		}
		}); 
	}

	//显示样式管理
	function showStyleManager(){
		win = showWindow("showStyleManager","样式管理","<c:url value='/templateUnitStyle.do?dealMethod=list&categoryId="+$("#unit_categoryId").val()+"'/>",0,0,850,590);	 			
	}

	//保存数据
	function btn_save() {
		var viewStyle = $("#viewStyle :selected").val();
		if(viewStyle == 0 || viewStyle == "" || viewStyle == null) {	
			alert("你必须先选择样式");
			return false;
		}
		var contextFrom = $("#contextFrom :selected").val();
		if(contextFrom == 0) {
			alert("你必须先选择栏目内容来源");
			return false;
		}
		if(contextFrom > 2) {
			if($("#fixedColumn").val().isEmpty()) {
				alert("请选择指定栏目");
				return false;
			} 
		}
		if($("#htmlContent").val().trim() == null || $("#htmlContent").val().trim() == "") {
			alert("效果源码不能为空");
			return false;
		}	
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
		$("#dealMethod").val("saveConfig");
	 	var options = {	 	
 		    	url: "titleLinkPage.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		  };
		$('#titleLinkPageForm').ajaxSubmit(options);	
		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}

	//网站内保存数据
	function btn_site_save() {	
		var viewStyle = $("#viewStyle :selected").val();
		if(viewStyle == 0 || viewStyle == "" || viewStyle == null) {	
			alert("你必须先选择样式");
			return false;
		}
		var contextFrom = $("#contextFrom :selected").val();
		if(contextFrom == 0) {
			alert("你必须先选择栏目内容来源");
			return false;
		}
		if(contextFrom > 2) {
			if($("#fixedColumn").val().isEmpty()) {
				alert("请选择指定栏目");
				return false;
			} 
		}
		if($("#htmlContent").val().trim() == null || $("#htmlContent").val().trim() == "") {
			alert("效果源码不能为空");
			return false;
		}			
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
		$("#dealMethod").val("saveSiteConfig");
	 	var options = {	 	
 		    	url: "titleLinkPage.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		};
 		$('#titleLinkPageForm').ajaxSubmit(options);	
 		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}
	
	function closeNewChild() {
		closeWindow(win);
	}
	
	//设置CSS
	function managerCss() {
		var css = $("#unit_css").val();
		win = showWindow("css", "管理css", "<c:url value='/module/template_set/manage_css.jsp?css=" + css + "'/>", 0, 0,490,350);
	}

	// 指定栏目
	function fixedColumn11() {
		var type = $("#contextFrom :selected").val();
		win = showWindow("chooseColumn", "指定栏目", "<c:url value='/module/template_set/unit/title_link_page/fixed_titleLinkPage_column.jsp?type="+ type +"'/>", 170, 20, 300, 300);
	}

	// 选择标题前缀
	function chooseTitlePrefix() {
		win = parent.showWindow("chooseTitlePrefix", "选择标题前缀","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=1&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}

	// 选择标题后缀
	function chooseTitleSuffix() {
		win = parent.showWindow("chooseTitleSuffix", "选择标题后缀","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=2&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}
	
	// 改变内容来源
	function changesource(obj) {
		var columnId = $("#unit_columnId").val();
		if (obj.value > 2 ) {
			$("#fixedColumnExample").val("");
			$("#fixedColumn").val("");
			document.all.havecolumn.style.display = "";	
		}else{
			document.all.havecolumn.style.display = "none";		
		}
		// 网站下面的单元
		if(columnId == 0) {
			$("#fieldCode").attr("disabled", "disabled");
		} else {
			// 非栏目 、 指定栏目 、指定栏目的父栏目
			if(obj.value == 0 || obj.value == "" || obj.value == 3 || obj.value == 4) {
				$("#fieldCode").attr("disabled", "disabled");
			// 当前栏目、  当前栏目的父栏目
			} else if(obj.value == 1 || obj.value == 2) {
				$.ajax({
					url : '<c:url value="/titleLinkPage.do?dealMethod=findFieldCode&nodeId="/>'+ columnId + "&contextFrom="+ obj.value,
					type: "post",
				success : function(msg) {
						var str = msg.split("###");
						var selobj = document.getElementById("fieldCode");
						selobj.disabled = false;  
						if(str[0] != "") {
							var attributeName = str[0];
							var attributeFieldName = str[1];
							var name = attributeName.split(":::");			 // 标题
							var fieldName = attributeFieldName.split(":::"); // title
							selobj.length = name.length+1;
							selobj.options[0].value = "";
							selobj.options[0].text = "---字段标签---";
							var a = 1;
							for(var i = 0; i < name.length; i++) {
								if(fieldName[i] != "title" 
									&& fieldName[i] != "subtitle" 
									&& fieldName[i] != "leadingTitle"
									&& fieldName[i] != "url" 
									&& fieldName[i] != "author"
									&& fieldName[i] != "brief"
									&& fieldName[i] != "pic1"
									&& fieldName[i] != "createTime"
									&& fieldName[i] != "displayTime"
									&& fieldName[i] != "auditTime"
									&& fieldName[i] != "invalidTime"
									&& fieldName[i] != "publishTime" ) {
										selobj.options[a].value = "<!--"+fieldName[i]+"-->";
										selobj.options[a].text = name[i];
										a++; 
								}
							}
							selobj.length = a;
						} else {   
							selobj.length = 1;
							selobj.options[0].value = "";
							selobj.options[0].text = "---字段标签---";
						}
					}
				});
			}
		}	
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
	<form id="titleLinkPageForm" action="<c:url value="/titleLinkPage.do"/>" name="titleLinkPageForm" method="post">
	<input type="hidden" value="${appName}" id="apprealpath"/>	
	<input type="hidden" id="unit_unitId" name="unit_unitId" value="${titleLinkPageForm.unit_unitId}"/>
	<input type="hidden" id="unit_categoryId" name="unit_categoryId" value="${titleLinkPageForm.unit_categoryId}"/>
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="chooseViewStyle" id="chooseViewStyle" value='${titleLinkPageForm.viewStyle}' >
	<input type="hidden" name="chooseContextFrom" id="chooseContextFrom" value="${titleLinkPageForm.contextFrom}" > 
	<input type="hidden" id="unit_columnId" name="unit_columnId" value="${titleLinkPageForm.unit_columnId}"/>
	<input type="hidden" id="titleLinkPager" value="${titleLinkPageForm.pageSite }"/> 
	<input type="hidden" id="hasSaved" name="hasSaved" value="N" />	
	<span style="display:none">
		<div id="unit_css1"  ></div>		
		<TEXTAREA id="unit_css" name="unit_css" >${titleLinkPageForm.unit_css}</TEXTAREA>  		 
	</span>
	<input type="hidden" name="viewImg" id="viewImg" value='${titleLinkPageForm.viewImg}'/>
		<div id="belowleft" style="position:absolute; left:3px; top:10px; width:278px; height:400px; z-index:2; ">
			<fieldset style="height:400" id="obj1" style="display:">
				<legend style="color:#0000ff;">
					<a href="javascript:;" onclick="changeSet('paramSet1');"><font style="color:#0000ff;">参数设置</font></a>&nbsp;||&nbsp;
					<a href="javascript:;" onclick="changeSet('paramSet2');"><font style="color:#0000ff;">扩展参数</font></a>
				</legend>
				<div id="paramSet1"  class="form_cls"  style="display: ;width:100%;align:center">
					<li>
						<select name="viewStyle" id="viewStyle"   style="width:100%"  onchange="changeshow(htmlContent,this)">
							<option value='0' >请选择显示样式</option>
							<c:forEach var="templateUnitStyle" items="${titleLinkPageForm.templateUnitStyleList}" varStatus="s" step="1">									
								<option value='${templateUnitStyle.id}'>${templateUnitStyle.name}</option>						
							</c:forEach>
						</select>
					</li>
					<li>
						<select name="contextFrom"  id="contextFrom"   style="width:100%" onchange="changesource(this)">
							<option value='0'>请选择内容来源</option>						
							<option value="1" >当前栏目</option>   
							<option value="2">当前栏目的父栏目</option>   						
							<option value="3">指定栏目</option>   
							<option value="4">指定栏目的父栏目</option> 
						</select>
					</li>
					<span id="havecolumn" style="display:none;border:0">
						<li>
							<label>指定栏目</label>						
							<input type="text"   name="fixedColumnExample" id="fixedColumnExample" value="${titleLinkPageForm.fixedColumn}" maxlength="255" size="13" readonly />
							<input type="hidden" name="fixedColumn" id="fixedColumn" value="${titleLinkPageForm.fixedColumn}" />
							<input type="button"  class="btn_small"   title="指定栏目" value="√" onclick="fixedColumn11()">
						</li>
					</span>	
					<li>
							<label>每页信息数</label>
							<input type="text" name="pageInfoCount" id="pageInfoCount" value="${titleLinkPageForm.pageInfoCount }" maxlength="3" size="22" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</li>	
					<li>
						<label>标题字数</label>
						<span>
							<input type="text" name="titleLimit" value="${titleLinkPageForm.titleLimit}" id="titleLimit"   maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>
							&nbsp; 
							摘要字数&nbsp;&nbsp;<input type="text" name="briefLimit" value="${titleLinkPageForm.briefLimit}" id="briefLimit"   maxlength="3" SIZE="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>
						</span>
					</li>
					<li>
						<label>分页位置</label>
						<select id="pageSite" name="pageSite" style="width:164px;">
							<option value="left">居左对齐</option>
							<option value="center">居中对齐</option>
							<option value="right">居右对齐</option>
						</select>
					</li>				
				</div>
				<div id="paramSet2" class="form_cls"  style="display:none;width:100%;align:center">
					<li>
						<label>标题前缀</label>
						<input   type="text" name="titleHead" id="columnPrefix" value="${titleLinkPageForm.titleHead}" title="" maxlength="255" SIZE="10"/>
						<input type="button" class="btn_small" title="选择标题前缀图"  value="√" onclick="chooseTitlePrefix()"/>
					</li>
					<li>
						<label>前缀有效期</label>
						<input   type="text" name="titleHeadValidity" id="titleHeadValidity"   value="${titleLinkPageForm.titleHeadValidity}"  title="-1代表永远有效"   maxlength="4" SIZE="3" onkeydown="myKeyDown('titleHeadValidity')"/>
						<span>天</span>
						<input type="checkbox" class="input_blank"  name="titleHeadPic" id="columnPrefixPic" value="${titleLinkPageForm.titleHeadPic }"/>
						<span>图形</span>
					</li>
					<li>
						<label>标题后缀</label>
						<input   type="text" name="titleEnd"  id="columnSuffix" value="${titleLinkPageForm.titleEnd}" title="" maxlength="255" SIZE="10"/> 
						<input type="button"  class="btn_small" title="选择标题后缀图"  value="√" onclick="chooseTitleSuffix()"/>
					</li>
					<li>
						<label>后缀有效期</label>
						<input   type="text" name="titleEndValidity"   id="titleEndValidity"   value="${titleLinkPageForm.titleEndValidity}" title="-1代表永远有效"  maxlength="4" SIZE="3" onkeydown="myKeyDown('titleEndValidity')"/>
						<span>天</span>						
						<input type="checkbox" class="input_blank"  name="titleEndPic" id="columnSuffixPic"  value="${titleLinkPageForm.titleEndPic }">
						<span>图形</span>
					</li>
				</div>
			</fieldset>
		</div>		   
		<div id="belowrightTop" style="position:absolute; left:283px; top:10px; width:320px; height:140px; z-index:2;">
			<fieldset id="displayEffect" style="width:320px;height:140px;"><legend style="color:#0000ff;"><b>显示效果</b>&nbsp;</legend>	
				<div id="display"  style="width:307px;height:135px;"></div>
			</fieldset>  
		</div>
		
		<div id="belowleftBelow" style="position:absolute; left:283px; top:160px; width:320px; height:260px; z-index:2; ">
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
				<c:forEach var="commonMap" items="${titleLinkPageForm.commonMap}" >
					<c:if test="${commonMap.value != '<!--appName-->' and commonMap.value != '<!--siteId-->'}">
						<option value="<c:out value="${commonMap.value}" />"><c:out value="${commonMap.key}" /></option>
					</c:if>	
				</c:forEach>
				<c:forEach var="titleMap" items="${titleLinkPageForm.titleLinkPageMap}" >									
						<option value="<c:out value="${titleMap.value}" />"><c:out value="${titleMap.key}" /></option>						
				</c:forEach>
			</select>
			<select name="fieldCode" id="fieldCode"  style="width:103px" onChange="insertAtCaret(htmlContent,this.value)">
				<option value=''>---字段标签---</option>	
				<c:forEach var="articleAttribute" items="${titleLinkPageForm.articleAttributeList}" varStatus="s" step="1">	
					<c:if test="${articleAttribute.fieldName != 'title' 
							  and articleAttribute.fieldName != 'subtitle'
							  and articleAttribute.fieldName != 'leadingTitle'
							  and articleAttribute.fieldName != 'url'
							  and articleAttribute.fieldName != 'author'
							  and articleAttribute.fieldName != 'brief'
							  and articleAttribute.fieldName != 'pic1'
							  and articleAttribute.fieldName != 'createTime'
							  and articleAttribute.fieldName != 'displayTime'
							  and articleAttribute.fieldName != 'auditTime'
							  and articleAttribute.fieldName != 'invalidTime'
							  and articleAttribute.fieldName != 'publishTime'}">								
						<option value="<!--${articleAttribute.fieldName}-->" >${articleAttribute.attributeName}</option>
					</c:if>						
				</c:forEach>
			</select>

			<input type="button" value="样式管理"  class="btn_small"  onClick="showStyleManager()">	
			<input type="button" value="css管理"  class="btn_small"  onClick="managerCss()">		
				<TEXTAREA id="htmlContent"  name="htmlContent" ROWS="11"  onselect="storePos(this);" onclick="storePos(this);" onkeyup="storePos(this);" COLS="37">${titleLinkPageForm.htmlContent}</TEXTAREA>
			</fieldset> 				
	    </div>
		<div id="saveArea" style="position:absolute;height:15px;z-index:4;left: 250px;top: 410px;">			
			<input type="button" value="保存"  class="btn_normal"  onclick="btn_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="站内保存"  class="btn_normal"  onclick="btn_site_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	

		</div>
	</form>

</body>
</html>
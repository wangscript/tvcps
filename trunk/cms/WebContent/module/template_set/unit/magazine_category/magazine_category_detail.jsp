<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>期刊分类</title>
<%@include file="/templates/headers/header.jsp"%>
<style type="text/css" media="all">
	.form_cls label {
		width:50px;
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

	#display img
	{
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

<script type="text/javascript">
	$(document).ready(function() {
		document.getElementById("unit_css1").innerHTML = $("#unit_css").val();

		//选择显示样式
		var style = $("#getStyle").val();
		if(style != null && style != "" && style != 0) {
			$("#magazineCategoryStyle").val(style);
			document.getElementById("display").innerHTML = $("#viewImg").val();
		}
		//选择内容来源
		var infoSource = $("#getInfoSource").val();
		if(infoSource != null && infoSource != "" && infoSource != 0) {
			$("#contentSource").val(infoSource);
			if(infoSource == 2) {
				var fixedColumn = $("#fixedColumnExample").val();
				var str1 = fixedColumn.split("##");
				$("#fixedColumnExample").val(str1[1]);
				document.all.havecolumn.style.display = "";	
			}
		}
		if($("#columnPrefixPic").val() == 1) {
			$("#columnPrefixPic").attr("checked", true);
		}
		if($("#columnSuffixPic").val() == 1) {
			$("#columnSuffixPic").attr("checked", true);
		}
		var infoCategory = $("#infoCategory").val();
		if(infoCategory != "") {
			var tmp = infoCategory.split(":::");
			var values = "";
			for(var i = 0; i < tmp.length; i++) {
				var name = tmp[i].split("##")[1];
				values += "【" + name + "】";
			}
		}
		$("#infoCategoryTemp").val(values);
	});

	//保存数据
	function btn_save() {	
		var magazineCategoryStyle = $("#magazineCategoryStyle :selected").val();
		if(magazineCategoryStyle == 0 || magazineCategoryStyle == "" || magazineCategoryStyle == null) {	
			alert("你必须先选择样式");
			return false;
		}
		var contentSource = $("#contentSource :selected").val();
		if(contentSource == 0) {
			alert("你必须先选择栏目内容来源");
			return false;
		}
		if(contentSource == 2) {
			if($("#fixedColumn").val().isEmpty()) {
				alert("请选择指定栏目");
				return false;
			} 
		}
		if($("#infoCategoryTemp").val() == null || $("#infoCategoryTemp").val() == "") {
			alert("请选择信息分类");
			return false;
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
 		    	url: "magazineCategory.do",
 		    success: function(msg) { 		    		
 		    		 alert(msg); 		    		
 		    } 
 		};
		$('#magazineCategoryForm').ajaxSubmit(options);	
		//设置标记为已保存
		document.getElementById("hasSaved").value = "Y";
	}


	// 管理css
	function managerCss() {
		var css = $("#unit_css").val();
		win = showWindow("css", "管理css", "<c:url value='/module/template_set/manage_css.jsp?css=" + css + "'/>", 0, 0, 490, 350);
	}

	// 指定栏目
	function fixedColumn1() {
		var columnId = $("#fixedColumn").val().split("##")[0];
		win = showWindow("chooseColumn", "指定栏目", "<c:url value='/module/template_set/unit/magazine_category/fixed_magazinecategory_column.jsp'/>"+"?columnId="+columnId, 170, 20, 300, 300);
	}
 
	
	// 改变内容来源(在选择指定栏目时出现)
	function changesource(obj) {
		var columnId = $("#unit_columnId").val();
		if (obj.value == 2) {
			$("#fixedColumnExample").val("");
			$("#fixedColumn").val("");
			document.all.havecolumn.style.display = "";	
			$("#infoCategoryTemp").val("");
				
		}else {
			document.all.havecolumn.style.display = "none";
			$("#infoCategoryTemp").val("");
		}

		// 网站下面的单元
		if(columnId == 0) {
			$("#fieldCode").attr("disabled", "disabled");
		} else {
			//  指定栏目
			if(obj.value == 2) {
				$("#fieldCode").attr("disabled", "disabled");
			// 当前栏目
			} else if(obj.value == 1) {
				$.ajax({
					url : '<c:url value="/magazineCategory.do?dealMethod=findFieldCode&columnId="/>'+ columnId,
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
							for(var i = 0; i < name.length; i++) {
								selobj.options[i+1].value = "<!--"+fieldName[i]+"-->";
								selobj.options[i+1].text = name[i];
							}
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
	
	//选择的时候插入数据到文本框
	function insertAtCaret(txtobj, text) {
		if (txtobj.createTextRange && txtobj.caretPos) { 
	       var caretPos = txtobj.caretPos;               //获取光标所在的位置
	       //替换光标处位置
	       caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) =='' ?text + '' : text; 
	    } else {
	       txtobj.value = text;   
	    }
	}
	  
	//选择文本时保存光标位置-单击时同样   
	function storePos(txtobj) { 
		if (txtobj.createTextRange) {                     //获取选中的内容
			txtobj.caretPos = document.selection.createRange().duplicate(); 
		}
	} 

	//改变显示样式
	function changeshow(txtobj,obj) {	 
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
	// 显示样式管理
	function showStyleManager(){
		win = showWindow("showStyleManager", "样式管理", "<c:url value='/templateUnitStyle.do?dealMethod=list&categoryId=" + $("#unit_categoryId").val()+"'/>", 0, 0, 850, 590);	 			
	}

	// 选择栏目前缀
	function chooseTitlePrefix() {
		win = parent.showWindow("chooseTitlePrefix", "选择标题前缀","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=1&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}

	// 选择栏目后缀
	function chooseTitleSuffix() {
		win = parent.showWindow("chooseTitleSuffix", "选择标题后缀","<c:url value='/picture.do?dealMethod=uploadPicList&columnLink=2&nodeId=f004&nodeName=图片类别'/>", 0, 0, 940, 555);
	}

	// 信息分类
	function chooseInfoCategory() {
		var contentSource = $("#contentSource :selected").val();
		var columnId = "";
		if(contentSource == 2) {
			if($("#fixedColumnExample").val() == null || $("#fixedColumnExample").val() == "") {
				alert("请选择指定栏目");
				return false;
			} else {
				columnId = $("#fixedColumn").val().split("##")[0];
			}
		} else {
			 columnId = $("#unit_columnId").val();
		}
		var unitId = $("#unit_unitId").val();
		if($("#infoCategoryTemp").val() == null || $("#infoCategoryTemp").val() == "") {
			unitId = "0";
		}
		win = showWindow("categoryManager", "分类管理", "<c:url value='/magazineCategory.do?dealMethod=findInfoCategory'/>"+"&columnId="+columnId+"&unit_unitId="+unitId, 0, 0, 550, 400);
	}
	function closeNewChild() {		 
		closeWindow(win);
	}

</script>
</head>
<body>
<form id="magazineCategoryForm" action="<c:url value="/magazineCategory.do"/>" name="magazineCategoryForm" method="post">
	<input type="hidden" value="${appName}" id="apprealpath"/>
	<input type="hidden" id="unit_unitId" name="unit_unitId" value="${magazineCategoryForm.unit_unitId}"/>
	<input type="hidden" id="unit_categoryId" name="unit_categoryId" value="${magazineCategoryForm.unit_categoryId}"/>
	<input type="hidden" id="unit_columnId" name="unit_columnId" value="${magazineCategoryForm.unit_columnId }" />
	<input type="hidden" name="unit_css" id="unit_css" value="${magazineCategoryForm.unit_css }" />
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" id="getStyle" value='${magazineCategoryForm.magazineCategoryStyle }' title="样式"/>
	<input type="hidden" id="getInfoSource"  value='${magazineCategoryForm.contentSource }' title="来源"/>
	<input type="hidden" name="fixedColumn" id="fixedColumn" value="${magazineCategoryForm.fixedColumn}" />
	<input type="hidden" name="infoCategory" id="infoCategory" value="${magazineCategoryForm.infoCategory }"/> 
	<input type="hidden" name="viewImg" id="viewImg" value='${magazineCategoryForm.viewImg}'/>
	<input type="hidden" id="hasSaved" name="hasSaved" value="N" />	
	<span style="display:none">
		<div id="unit_css1"></div>		
		<TEXTAREA id="unit_css" name="unit_css">${magazineCategoryForm.unit_css}</TEXTAREA>  		 
	</span>
	<div id="setParamArea" style="position:absolute; left:3px; top:10px; width:278px; height:400px; z-index:2; ">
			<fieldset style="height:400" id="obj1" style="display:">
				<legend style="color:#0000ff;">参数设置</legend>
				<div id="paramSet" class="form_cls"  style="display: ;width:100%;align:center">
					<li>
						<select name="magazineCategoryStyle" id="magazineCategoryStyle" style="width:100%"  onchange="changeshow(htmlContent,this)">
							<option value='0' selected>请选择显示样式</option>
							<c:forEach var="templateUnitStyle" items="${magazineCategoryForm.templateUnitStyleList}" >									
								<option value='${templateUnitStyle.id}'>${templateUnitStyle.name}</option>						
							</c:forEach>
						</select>
					</li>
					<li>
						<select  name="contentSource" id="contentSource" style="width:100%" onchange="changesource(this)">
							<option value='0' SELECTED>请选择内容来源</option>	
							<option value="1">当前栏目</option>   
							<option value="2">指定栏目</option>   
						</select>
					</li>
					<span id="havecolumn" style="display:none;border:0">
						<li>
							<label>指定栏目</label>
							<input type="text" name="fixedColumnExample" id="fixedColumnExample" value="${magazineCategoryForm.fixedColumn}" maxlength="255" size="19" readonly >
							<input type="button"  class="btn_small"  title="指定栏目" value="√" onclick="fixedColumn1()">	
						</li>
					</span>	 
					<li>
						<label>信息分类</label>&nbsp;
						<input  type="text" name="infoCategory1" id="infoCategoryTemp" maxlength="255" SIZE="19" readonly/>
						<input  type="button" class="btn_small"  title="信息分类"  value="√" onclick="chooseInfoCategory()"/>
					</li>
					<li>
						<label>标题字数</label>&nbsp;
						<input  type="text" name="titleSize" id="titleSize" value="${magazineCategoryForm.titleSize}" size="8" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</li>
					<li>
						<label>标题前缀</label>&nbsp;
						<input  type="text" name="titlePrefix" id="columnPrefix"   value="${magazineCategoryForm.titlePrefix}"  maxlength="255" SIZE="19">
						<input  type="button" class="btn_small"  title="选择标题前缀图"  value="√" onclick="chooseTitlePrefix()">
					</li>
					<li style="padding-left:60px;">
						<input type="checkbox" class="input_blank"  name="titlePrefixPic" id="columnPrefixPic" value="${magazineCategoryForm.titlePrefixPic }"/>
						<span>图形</span>
					</li> 
					<li>
						<label>标题后缀</label>&nbsp;
						<input  type="text" name="titleSuffix"  id="columnSuffix" value="${magazineCategoryForm.titleSuffix}" maxlength="255" SIZE="19"> 
						<input type="button"   class="btn_small" title="选择标题后缀图"  value="√" onclick="chooseTitleSuffix()">
					</li>
					<li style="padding-left:60px;">
						<input type="checkbox" class="input_blank"  name="titleSuffixPic" id="columnSuffixPic" value="${magazineCategoryForm.titleSuffixPic}"/>
						<span>图形</span>
					</li>
				
				</div> 
			</fieldset>
		</div>		   
		<div id="displayArea"  style="position:absolute; left:283px; top:10px; width:320px; height:140px; z-index:2;">
			<fieldset id="displayEffect" style="width:320px;height:140px;"><legend style="color:#0000ff;"><b>显示效果</b>&nbsp;</legend>
				<div id="display" ></div>
			</fieldset>  
		</div>
		<div id="codeArea"  style="position:absolute; left:283px; top:160px; width:320px; height:260px; z-index:2; ">
			<fieldset id="code" style="width:320px;height:250px;"><legend style="color:#0000ff;"><b>效果源码</b>&nbsp;</legend>
			   <SELECT id="htmlCode" name="htmlCode" style="width:103px" onChange="insertAtCaret(htmlContent,this.value)">
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
			<select name="unitCode" id="unitCode" style="width:103px" onchange="insertAtCaret(htmlContent,this.value)">
				<option value=''>---单元标签---</option>
				<c:forEach var="commonMap" items="${magazineCategoryForm.commonMap}">
					<c:if test="${commonMap.value != '<!--appName-->' 
									and commonMap.value != '<!--siteId-->' 
									and commonMap.value != '<!--if--><!--else--><!--/if-->'}">
						<option value="<c:out value="${commonMap.value}" />">  
							<c:out value="${commonMap.key}" /> 
						</option>	
					</c:if>
				</c:forEach>
				<c:forEach var="titleMap" items="${magazineCategoryForm.titleMap}">
					<c:if test="${titleMap.value == '<!--titlePrefix-->'
								or titleMap.value == '<!--titleSuffix-->'}">
						<option value="<c:out value="${titleMap.value}" />">  
							<c:out value="${titleMap.key}" /> 
						</option>	
					</c:if>
				</c:forEach>
				<c:forEach var="magazineMap" items="${magazineCategoryForm.magazineCategoryMap}">	
					<option value="<c:out value="${magazineMap.value}"/>">
						<c:out value="${magazineMap.key}"/> 
				    </option>  
				</c:forEach>
			</select> 
			<select name="fieldCode" id="fieldCode"  style="width:103px" onChange="insertAtCaret(htmlContent,this.value)" >
				<option value=''>---字段标签---</option>	
				<c:forEach var="articleAttribute" items="${magazineCategoryForm.articleAttributeList}" varStatus="s" step="1">									
					<option value="<!--${articleAttribute.fieldName}-->" >${articleAttribute.attributeName}</option>						
				</c:forEach>
			</select>

			<input type="button" value="样式管理" class="btn_small" onClick="showStyleManager()"/>	
			<input type="button" value="css管理" class="btn_small" onClick="managerCss()"/>
			<TEXTAREA id="htmlContent"  name="htmlContent" ROWS="11"  onselect="storePos(this);" onclick="storePos(this);" onkeyup="storePos(this);" COLS="37">${magazineCategoryForm.htmlContent}</TEXTAREA>
			</fieldset> 				
	    </div>
		<div id="saveArea" style="position:absolute;height:15px;z-index:4;left: 250px;top: 410px;">			
			<input type="button" value="保存" class="btn_normal" onclick="btn_save()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
</form>
</body>
</html>
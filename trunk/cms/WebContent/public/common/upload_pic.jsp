<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">	
<title></title>
<%@include file="/templates/headers/header.jsp"%>
<script type="text/javascript"  src="<c:url value="/script/fckeditor/editor/js/fckfilemanager.js"/>"></script>
<style type="text/css"> 
    #Layer3 .td_left{
    	height:23px;
    }
    #image img{
	    max-width:220px;
	    max-height:145px;
		zoom:expression( function(elm) {
		          if(elm.width<elm.height) {
		             if (elm.height>145) {
		         		var oldVW = elm.height;
		         		elm.height=145;             
		                elm.width = elm.width*(145/oldVW );        
		             }
		          }
		           if(elm.width>elm.height) {
			     	if (elm.width>220) {
		         		var oldVW = elm.width; 
		         		elm.width=220;             
		                elm.height = 135;        
		             }
		          }
		      }
		(this));
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		//预览图片
		$("input[name='tb_idnull']").bind("click", function(){
			var str = $(this).attr("id").split("_");
			var id = str[0];
			if($(this).attr("checked")) {
				showpic(id);
				changeImg(id);
			} else {
				document.getElementById("image").innerHTML="";
			}

		}); 
	});
			
	function showpic(id) {
		var location = "/" + app +$("#"+id+"_8").html();
		var imgfile = location;
		var Img = new Image(); 
		Img.src = location; 
		//原图的大小
		document.getElementById('height').value = Img.height;
		document.getElementById('width').value = Img.width;
		var imgEndWith = imgfile.substr(imgfile.lastIndexOf("."), imgfile.length); 
		var imgEndWithLower = imgEndWith.toLowerCase();
		var imgEndWiths = new Array('.gif', '.jpg', '.png', '.bmp'); 
		var imgIsTrue = false; 
		for( var i = 0; i < imgEndWiths.length; i++) { 
			if(imgEndWithLower == imgEndWiths[i]) { 
				imgIsTrue = true; 
				break; 
			}
		} 
		if(!imgIsTrue) { 
			alert("图片格式错误"); 
			return false; 
		} 
		document.getElementById("image").innerHTML="<img src='"+location+"' style='border:6px double #ccc'>"
	}

	/*实现图片的按比例缩放*/ 
	function DrawImage(ImgD, iwidth, iheight) { 
	    var image = new Image(); 
	    image.src = ImgD.src; 
	    if(image.width > 0 && image.height > 0){ 
	        if(image.width / image.height >= iwidth / iheight){ 
	            if(image.width > iwidth){ 
	                ImgD.width = iwidth; 
	                ImgD.height = (image.height * iwidth) / image.width; 
	            } 
	            else{ 
	                ImgD.width = image.width; 
	                ImgD.height = image.height; 
	            } 
	        } 
	        else{ 
	            if(image.height > iheight){ 
	                ImgD.height = iheight; 
	                ImgD.width = (image.width * iheight) / image.height; 
	            } 
	            else{ 
	                ImgD.width = image.width; 
	                ImgD.height = image.height; 
	            } 
	        } 
	    } 
	  //缩略图的大小
		document.getElementById('suolvheight').value = ImgD.height;
		document.getElementById('suolvwidth').value = ImgD.width;
	} 
			
	function button_add_onclick(ee) {
		if(document.getElementById("category").value == "") {
			alert("请先创建图片类别");
			return false;
		}
		var category  = $("#category :selected").val();
		if(category.isEmpty() || category == 0) {
			alert("请先选择类别");
			return false;
		}
		var isScaleImage = $("#isScaleImage").val();
		var articlePicture = $("#articlePicture").val();
		var columnLink = $("#columnLink").val();
		if(columnLink.isEmpty() || columnLink == "null") {
			columnLink = 0;
		}
		win = showWindow("newpicture", "上传图片", "<c:url value='/picture.do?dealMethod=getPicColsWater&isScaleImage=" + isScaleImage + "&nodeId=" + category + "&columnLink="+ columnLink + "&articlePicture="+ articlePicture +"'/>", 0, 0, 490, 470);	
	}

	function button_delete_onclick(ee) {
		var ids = document.getElementById("xx").value;
		if(ids == null || ids == "" || ids == "null") {
			alert("请选择一条记录操作！");
			return false;
		}
		var selectValue = $("#category :selected").val(); 
		$("#nodeId").val(selectValue);
		$("#strid").val(ids);
		if(confirm("确定要删除吗？")){
			$("#dealMethod").val("deletePicture");
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

	// 点击确定
	function sure() {
		var ids = $("#xx").val();
		if(ids == null || ids == "" || ids == "null") {
			alert("请选择图片(提示：只能选择一张图片)");
			return false;
		}
		var arr = ids.split(",");
		if(arr.length != 1) {
			alert("只能选择一张图片");
			return false;
		}
		var location = $("#"+ids+"_8").html();//图片地址
		var h = $("#suolvheight").val();//图片高度
		var w = $("#suolvwidth").val();//图片宽度
		var id = ids;//图片ID
		if(document.documentForm.binsert[1].checked){//缩略图
			if(w==""){alert("请输入缩略图的宽度");return false;}
			if(h==""){alert("请输入缩略图的高度");return false;}
		}
		var firstWin = top.getWin();
		if($("#columnLink").val().isEmpty() || $("#columnLink").val() == "null"){
			$("#columnLink").val("0");
		}
		var columnLink = $("#columnLink").val();

		var tempWindow = "";
		var secondWindow = "";
		var newPicWindow = "";
		if(firstWin != null) {
			var temp = firstWin.split("###");
			var dialogProperty = temp[0];
			var detailFrame = "_DialogFrame_"+dialogProperty;	 		   
			tempWindow = top.document.getElementById(detailFrame).contentWindow;			
			var secondTempWindow = tempWindow.document.getElementById("unitEditArea").contentWindow;			
			var secondWin = secondTempWindow.getWin();					
			var secondtemp = secondWin.split("###");
			var seconddialogProperty = secondtemp[0];
			var seconddetailFrame = "_DialogFrame_"+seconddialogProperty;			
			secondWindow = top.document.getElementById(seconddetailFrame).contentWindow;
			newPicWindow = secondWindow.getWin();
		}
		
		// 插入原图
		if(document.documentForm.binsert[0].checked) {
			// 高级编辑器图片
			if(columnLink == 0) {
				var path = "/${appName}" + $("#"+ids+"_8").html();
				var returnvalue = doOptFileManager(document.documentForm, "sure", "", "", path);
				// 模版设置图片
				if(firstWin != null) {
					// 静态单元
					if(newPicWindow == null || newPicWindow == ""){
						secondTempWindow.fck_insertHtml(returnvalue);
						top.closeWindow(secondWin);
					// 样式管理
					}else {
						secondWindow.fck_insertHtml(returnvalue);
						top.closeWindow(newPicWindow);	
					}
				// 文章图片	
				}else {
					rightFrame.fck_insertHtml(returnvalue);
					rightFrame.closeWindow(rightFrame.getWin());
				}

			// 文章图片链接	
			}else if(columnLink == 4){
                var flag = true;
                for(var i = 0; i < rightFrame.maxPicCount; i++){
                    var articlePic = rightFrame.document.getElementById("article.pic"+i);
                    if(articlePic != null){
                        if(location == articlePic.value){
                            flag = false;
                            alert("该图片已经选择，不能重复选择！");
                        }
                    }
                }
                if(flag){
                    var articlePicture = $("#articlePicture").val();
                    rightFrame.document.getElementById(articlePicture).value = location;
                    rightFrame.document.getElementById(articlePicture).focus();
                    rightFrame.document.getElementById("imgPreview").innerHTML = "";
                    rightFrame.document.getElementById("imgPreview").innerHTML;
                    rightFrame.document.getElementById("displayPicPreview").style.display = "block";
                    for(var i = 0; i < rightFrame.maxPicCount; i++){
                        var articlePic = rightFrame.document.getElementById("article.pic"+i);
                        if(articlePic != null){
                            var a = articlePic.value;
                            if(a != null && a != ""){
                                rightFrame.document.getElementById("imgPreview").innerHTML += "<img src=\""+a.substring(1, a.length)+"\" width=\"50px\" height=\"50px\"/>&nbsp;&nbsp;";
                            }
                        }
                    }
                    closeWindow(rightFrame.getWin());
                }
				
			// 模版设置前缀	
			}else if(columnLink == 1){
				tempWindow.frames["unitEditArea"].document.getElementById("columnPrefix").value = location;
				tempWindow.frames["unitEditArea"].document.getElementById("columnPrefixPic").checked = "true";
				tempWindow.frames["unitEditArea"].document.getElementById("columnPrefix").focus();
				top.closeWindow(secondWin);

			// 模版设置后缀 	
			}else if(columnLink == 2){
				tempWindow.frames["unitEditArea"].document.getElementById("columnSuffix").value = location;
				tempWindow.frames["unitEditArea"].document.getElementById("columnSuffixPic").checked = "true";
				tempWindow.frames["unitEditArea"].document.getElementById("columnSuffix").focus();
				top.closeWindow(secondWin);
				
			// 模版设置更多链接	
			}else if(columnLink == 3){
				tempWindow.frames["unitEditArea"].document.getElementById("moreLink").value = location;
				tempWindow.frames["unitEditArea"].document.getElementById("moreLinkPic").checked = "true";
				tempWindow.frames["unitEditArea"].document.getElementById("moreLink").focus();
				top.closeWindow(secondWin);
			}

		// 插入缩略图	
		}else{
			$.ajax({
				  url:'<c:url value="/picture.do?dealMethod=scaleImage&imgW='+w+'&imgH='+h+'&imgid='+id+'&imgurl='+location+'&columnLink"/>'+columnLink,
			     type:"post",
		      success:function(msg) {
					var m = msg.split("###");
					if(m[0]=="图片缩放失败，请检查图片的宽高填写是否正确"){
						alert(m[0]);
					}else{
						var realPath = m[0].slice(app.length+1, m[0].length);
						// 高级编辑器图片
						if(columnLink == 0) {
							var returnvalue = doOptFileManager(document.documentForm, "sure", "", "", m[0]);
							// 模版设置图片
							if(firstWin != null) {
								// 静态单元
								if(newPicWindow == null || newPicWindow == ""){
									secondTempWindow.fck_insertHtml(returnvalue);
									top.closeWindow(secondWin);
								// 样式管理
								}else {
									secondWindow.fck_insertHtml(returnvalue);
									top.closeWindow(newPicWindow);	
								}
							// 文章图片	
							}else {
								rightFrame.fck_insertHtml(returnvalue);
								rightFrame.closeWindow(rightFrame.getWin());
							}

						// 文章图片链接	
						}else if(columnLink == 4){
							var articlePicture = $("#articlePicture").val();
							rightFrame.document.getElementById(articlePicture).value = realPath;
							rightFrame.document.getElementById(articlePicture).focus();
							closeWindow(rightFrame.getWin());
							
						// 模版设置前缀	
						}else if(columnLink == 1){
							tempWindow.frames["unitEditArea"].document.getElementById("columnPrefix").value = realPath;
							tempWindow.frames["unitEditArea"].document.getElementById("columnPrefixPic").checked = "true";
							tempWindow.frames["unitEditArea"].document.getElementById("columnPrefix").focus();
							top.closeWindow(secondWin);

						// 模版设置后缀 	
						}else if(columnLink == 2){
							tempWindow.frames["unitEditArea"].document.getElementById("columnSuffix").value = realPath;
							tempWindow.frames["unitEditArea"].document.getElementById("columnSuffixPic").checked = "true";
							tempWindow.frames["unitEditArea"].document.getElementById("columnSuffix").focus();
							top.closeWindow(secondWin);
							
						// 模版设置更多链接	
						}else if(columnLink == 3){
							tempWindow.frames["unitEditArea"].document.getElementById("moreLink").value = realPath;
							tempWindow.frames["unitEditArea"].document.getElementById("moreLinkPic").checked = "true";
							tempWindow.frames["unitEditArea"].document.getElementById("moreLink").focus();
							top.closeWindow(secondWin);
						}
					}
				}
			});
		}
	}
				
	function closeNewChild() {
		closeWindow(win);
	}
	
	function closeDetailChild() {
		closeWindow(win);
	}
	
	function closeSetChild() {		
		closeWindow(win);
	}

    //当文本框失去焦点,调用该方法,用于根据图片的高度自动计算宽度
    function changeImg(id){
    	if(document.documentForm.binsert[1].checked){//选择缩略图
        	var location = $("#"+id+"_7").html();//获取图片地址
        	var suolvW=$("#suolvwidth").val();
        	var image = new Image(); 
  	  		image.src = location; 
       	   	if(location != null && location != "" && location != "null"){//有图片地址
       			if(suolvW.trim()!="" && !isNaN(suolvW)){//输入的是数字
	  		    	if(image.width>parseInt(suolvW)){
		  	  		    var suolvH=parseInt(suolvW)*image.height/image.width+"";//根据宽度计算高度
		  	  			var sou2;
		  	  		    if(suolvH.indexOf(".")!=-1){//是否有小数
		  	  				sou2=suolvH.substring(0,suolvH.lastIndexOf("."));//截取显示的图片高度
		  	  		    }else{
							sou2=suolvH;
				   	    }
		  	  		    $("#suolvheight").val(sou2);
	  		    	}else{
						alert("你给的缩略图片宽度已经大于你图片的原始宽度");
		  		    }
	  		    }
	  	  	}
        }
 	}      
	 		    
	//只能是数字
	function checkNum(){ 
		if(event.keyCode<48||event.keyCode>57){     
			event.returnValue=false;        
		}   
	}
</script> 

</head>
<body>
<script type="text/javascript" src="<c:url value="/script/officecolor.js"/>"></script>
	<form id="documentForm" action="<c:url value="/picture.do"/>" method="post" name="documentForm">
		<input type="hidden" value="<%=request.getParameter("columnLink") %>" id="columnLink" name="columnLink"/>
        <input type="hidden" name="dealMethod" id="dealMethod" value="uploadPicList"/>
		<input type="hidden" value="<%=request.getParameter("articlePicture") %>" id="articlePicture" name="articlePicture"/>
        <input type="hidden" name="nodeId" id="nodeId" value="${documentForm.nodeId}"/>
		<input type="hidden" name="message" id="message" value="${documentForm.infoMessage}"/>
        <input type="hidden" name="ids" id="strid"/>
		<input type="hidden" name="categoryId" id="categoryId" value="${documentForm.categoryId}"/>
		<input type="hidden" name="isScaleImage" id="isScaleImage" value="1"/>
		<input type="hidden" name="isTemplateStyle" id="isTemplateStyle" />
		<input type="hidden" name="picurl" id="picurlId"/>
		<input type="hidden" name="scaleImgPath" id="scaleImgPathId" value="${documentForm.imgPath}">
		<div id="Layer2" style="position:absolute; overflow: scroll;  left:4px; top:10px; width:642px; height:520px; ">
			<complat:button  name="button" buttonlist="add|0|delete" buttonalign="left"/>
			<table width="625px;">
  				<tr>
					<td class="td_left" width="11%">选择类别：</td>
    				<td>
      				<select id="category" style="width:150px;" class="input_select_normal" onChange="change(this)">
						<c:forEach items="${documentForm.list}" var="list">
							<c:if test="${list.id == documentForm.categoryId}">
								<option value="${list.id}" selected>${list.name}</option>
							</c:if>
							<c:if test="${list.id != documentForm.categoryId}">
								<option value="${list.id}">${list.name}</option>
							</c:if>
						</c:forEach>  
					</select> 
					</td>
  				</tr>
				<tr>
					<td colspan="2">
					<complat:grid ids="xx" width="40,40,*,*,*,0,0,0"  head="图片, 名称, 类型, 大小, 创建时间, , , " 
						page="${documentForm.pagination}"  form="documentForm" action="picture.do"/>
					</td>
				</tr>
			</table>
		</div>
		<div id="Layer3" style="position:absolute; left:655px; top:11px; width:236px; height:390px;">
			<fieldset style="height:150px;width:225px;"><legend>&nbsp;<font color=blue size="-1" style="color:blue;font:12px;font-weight:bold">图片预览</font>&nbsp;</legend>
				<div id="image" align="center"></div>
			</fieldset>

			<fieldset style="height:12"><legend>&nbsp;<font color=blue size="-1" style="color:blue;font:12px;font-weight:bold">原图属性</font>&nbsp;</legend>
				<table style="font:12px;">
					<tr>
						<td class="td_left"><i>&nbsp;</i>宽</td>
						<td><input type="text" class="input_text_normal" name="width" id="width" style="width:70px" valid="num" tip="请输入合法数字" maxlength="3" empty="true" onkeypress="checkNum();"/>px </td>
						<td class="td_left"><i>&nbsp;&nbsp;&nbsp;&nbsp;</i>高</td>
						<td><input type="text" class="input_text_normal" name=height id="height" style="width:70px" valid="num" tip="请输入合法数字" maxlength="3" empty="true" onkeydown="" onkeyup="" onkeypress="checkNum();"/>px</td>	
					</tr>
				</table>
			</fieldset>
		
			<fieldset style="height:15"><legend>&nbsp;<font color=blue size="-1" style="color:blue;font:12px;font-weight:bold">缩略图属性</font>&nbsp;</legend>
				<table style="font:12px;">
					<tr>
						<td class="td_left"><i>&nbsp;</i>宽</td>
						<td><input type="text" onblur="changeImg();" class="input_text_normal" name="suolvwidth"  id="suolvwidth"  style="width:70px" valid="num" tip="请输入合法数字" maxlength="3" empty="true" onkeypress="checkNum();"/>px</td>
						<td class="td_left"><i>&nbsp;&nbsp;&nbsp;&nbsp;</i>高</td>
						<td><input type="text" class="input_text_normal" name="suolvheight"  id="suolvheight" style="width:70px" valid="num" tip="请输入合法数字" maxlength="3" empty="true" onkeypress="checkNum();"/>px</td>
					</tr>
				</table>
			</fieldset>
		
			<fieldset style="height:40"><legend>&nbsp;<font color=blue size="-1" style="color:blue;font:12px;font-weight:bold">插入属性</font>&nbsp;</legend>
				<table style="font:12px;">
					<tr>
						<td style="font:12px; ">
							<input type="radio" name="binsert" id="yuantuId" checked>插入原图 　　　
						</td>
						<td style="font:12px;">
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="binsert" id="suolvId" >插入缩略图
						</td>
					</tr>
				</table>
				<table style="font:12px;">
					<tr>
						<td class="td_left"><i>&nbsp;</i>对齐方式</td>
						<td><select name="align" style="width:144px;" class="input_select_normal" style="font:12px;">
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
						<td class="td_left"><i>&nbsp;</i>水平距离</td>
						<td><input type="text" class="input_text_normal" name="hspace" style="width:144px;" valid="num" tip="请输入合法数字" maxlength="3" empty="true" onkeypress="checkNum();">px</td>
					</tr>
					<tr>
						<td class="td_left"><i>&nbsp;</i>竖直距离</td>
						<td><input type="text" class="input_text_normal" name="vspace" style="width:144px;" valid="num" tip="请输入合法数字" maxlength="3" empty="true" onkeypress="checkNum();"/>px
							<input type="hidden" name="srcwidth" value=""/>
							<input type="hidden" name="srcheight" value=""/>
						</td>
					</tr>
					<tr>
						<td class="td_left"><i>&nbsp;</i>目标窗口</td>
						<td><select name="target" style="width:144px;" class="input_select_normal" style="font:12px;">
								<option value="_blank">新 窗 口</option>
								<option value="_self">本 窗 口</option>
								<option value="_parent">父 窗 口</option>
								<option value="_top">主 窗 口</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="td_left"><i>&nbsp;</i>边框宽度</td>
						<td><input type="text" class="input_text_normal" name="borderwidth" id="borderwidth"  style="width:144px;" valid="num" tip="请输入合法数字" maxlength="3" empty="true" onkeypress="checkNum();"/>px</td>
					</tr>	
					<tr>
						<td class="td_left"><i>&nbsp;</i>边框颜色</td>
						<td><input type="text" class="input_text_normal" style="width:144px;" onclick="colordialog()" name="inputcolor" id="inputcolor" /></td>
					</tr>	
					<tr>
						<td class="td_left"><i>&nbsp;</i>超级链接</td>
						<td><input type="text" class="input_text_normal" name="hyperlink" style="width:144px;" valid="string" tip="所有字符（包括空格）" empty="true" maxlength="255"></td>
					</tr>
					<tr>
						<td class="td_left"><i>&nbsp;</i>文字提示</td>
						<td><input type="text" class="input_text_normal" name="alt" style="width:144px;" valid="string" tip="所有字符（包括空格）" empty="true" maxlength="20" ></td>
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
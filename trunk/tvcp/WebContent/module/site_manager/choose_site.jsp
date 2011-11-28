<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>切换网站</title>

<style type="text/css" media="all">

</style>

<script type="text/javascript">

    // 初始化加载
	function funInit(){ 
	    var strWebName = document.siteForm.webname.value;     
		var strWebId = document.siteForm.webid.value;
	    var webid = document.siteForm.currentwebid.value;   
	    var selObj = document.siteForm.listSite;
	    selObj.length = 0;
	    document.siteForm.site.focus();
	    if(strWebName == "" || strWebId == "") {
		   selObj.length = 1;
	       selObj.options[0].value = "-1";
	       selObj.options[0].text = "";
	    }else{
	       var arrWebName = strWebName.split(":::");
	       var arrWebId = strWebId.split(":::");
	       if(arrWebName.length > 0 && arrWebId.length > 0 && arrWebName.length == arrWebId.length) {
               selObj.length = arrWebName.length;
               for(var i = 0; i < arrWebName.length; i++) {
                  selObj.options[i].value = arrWebId[i];
                  selObj.options[i].text = arrWebName[i];
				  if(selObj.options[i].value == webid) {
                      selObj.options[i].selected = true;
				  }
               }
	        }
	    }
	 }
	
	// 检索网站
	function searchWeb(obj) {
	    var strWebName = document.siteForm.webname.value;
	    var strWebId = document.siteForm.webid.value;
	    var webid = document.siteForm.currentwebid.value;
	    var selObj = document.siteForm.listSite;
	    selObj.length = 0;
	    var strinput = trim(obj.value);
	    if(strWebName == "" || strWebId == ""){
	       selObj.options[0].value = "-1";
	       selObj.options[0].text = "";
	    }else{
	      var arrWebName = strWebName.split(":::");
	      var arrWebId = strWebId.split(":::");
	      if(arrWebName.length > 0 && arrWebId.length > 0 
	           && arrWebName.length == arrWebId.length){
	           var k = 0;
	           var selWebIds = "";
	           var selWebNames = "";
	           with(selObj) {
	              for(var i = 0; i < arrWebName.length; i++) {
	                  if(strinput == ""){
                          length = arrWebName.length;
		                  options[i].value = arrWebId[i];
		                  options[i].text = arrWebName[i];
		                  if(options[i].value == webid){
		                      options[i].selected = true;
		                  }
	                  }else{
		                  if(arrWebName[i].indexOf(strinput) != -1){
		                      if(selWebIds == ""){
		                        selWebIds = arrWebId[i];
                                selWebNames = arrWebName[i];
		                      }else{
		                        selWebIds += ":::"+arrWebId[i];
                                selWebNames += ":::"+arrWebName[i];
		                      }
			                  k++;
		                  }
		               }
		           }
	               if(k > 0){
	                 var arr1 = selWebIds.split(":::"); 
	                 var arr2 = selWebNames.split(":::"); 
                     length = arr1.length;
                     for(var m = 0; m < arr1.length; m++){
	                    options[m].value = arr1[m];
	                    options[m].text = arr2[m];
                     }
		          }
	           }
	        }
	    }
	} 	

 	function ltrim(s){
 	 	return s.replace( /^\s*/, "" );
 	}

	function rtrim(s){
		return s.replace( /\s*$/, "" );
	}

	function trim(s){
		return rtrim(ltrim(s));
    }

	function setValue(obj){
		//选中的网站id
		var strId = siteForm.listSite.options[siteForm.listSite.selectedIndex].value;
		var siteOfRolesIn = document.getElementById("siteOfRolesIn").value;
		if(siteOfRolesIn != "超级管理员" && siteOfRolesIn != "网站管理员" && siteOfRolesIn != "系统管理员") {
			var arr = siteOfRolesIn.split(",");			
			//查找所选网站中是否含有当前用户的角色
			//标记  是否存在
			var exist = "no";
			for(var i = 0; i < arr.length-1; i++) {
				//去掉最后一个","带来的值
				//存在角色
				if(strId == arr[i]) {
					exist = "yes";
				} 
			}	
			if(exist == "no") {
				alert("无可选网站");
				return;
			}
		}
		if(siteOfRolesIn == "网站管理员") {
			alert("网站管理员不能进行系统切换");
			return;
		}
		var strName = obj.options[obj.selectedIndex].text;
		if(strName != ""){
		     document.all.site.value = strName;
		}
    } 

    function sure(){
           if(siteForm.listSite.selectedIndex == -1){
		       alert("请选择网站！");
		       return;
		    }
			if(siteForm.listSite.value == "-1") {
				alert("无可选网站");
				return;
			}
			var webId = siteForm.listSite.options[siteForm.listSite.selectedIndex].value;
			
		//	top.window.location.href = "loginaction.do?dealMethod=initSite&siteid=" + webId;
			top.window.location.href = "site.do?dealMethod=initSite&siteid=" + webId;
     }

   
</script>

</head>
<body onload="javascript:funInit();">
    <form action="<c:url value="/site.do"/>" method="post" name="siteForm" id="siteForm">
	<input type="hidden" name="dealMethod" id="dealMethod" />
	<input type="hidden" name="webname" value="${siteForm.webname }">
    <input type="hidden" name="webid" value="${siteForm.webid }">
    <input type="hidden" name="currentwebid" value="${siteForm.currentwebid }">
	<input type="hidden" name="siteOfRolesIn" id="siteOfRolesIn" value="${siteForm.siteOfRolesIn}">
        <div class="form_div">
		<table style="font:12px; width:400px;">
			<tr>
				<td class="td_left" width="80px;"><i>&nbsp;</i>选择网站：</td>
				<td><input type="text" class="input_text_normal" name="site" id="site" value="" style="width:300px;" onkeyup="searchWeb(this);"/></td>
			</tr>
			<tr>
				<td class="td_left" width="80px;"><i>&nbsp;</i>拥有网站：</td>
				<td>
					<select id="listSite" name="listSite"  style="width:300px; height:200px;" size="12" class="input_select_normal" onclick="setValue(this)">
                	</select>
				</td>
			</tr>
			<tr><td><li>&nbsp;</li></td></tr>
			<tr><td><li>&nbsp;</li></td></tr>
			<tr>
				<td colspan="2"><center>
					<input type="button" class="btn_normal" value="确定" onClick="sure()" />
					&nbsp;&nbsp;&nbsp;&nbsp;
       				<input type="reset" class="btn_normal" value="重置"/></center>
				</td>
			</tr>
		</table>
        </div>
   </form>       
</body>
</html>
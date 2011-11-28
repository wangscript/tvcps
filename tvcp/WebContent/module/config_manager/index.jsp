<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
	<%@include file="/templates/headers/header.jsp"%>
	<script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>
	<script type="text/javascript">
		function tree_onclick(node){ 
			if(node.id != 0) {
				var url = node.attributes.url;
				var str = url.split("&rs="); 
				if(str[0].lastIndexOf(".jsp") != -1) {
					url =  "/" + app + "/" + str[0] + "?" + new Date();
				} else{
					url = url + "&categoryId="+node.id+"&rs="+new Date();
				}
	 			parent.changeFrameUrl("rightFrame", url);	
			}
		}
		$(document).ready(
				function(){
					var url =  "${initForm.rightFrameUrl}";
					if(url != null && url != ""){
						if(url.lastIndexOf(".jsp") != -1) {
							url =  "/" + app + "/" + url + "?" + new Date();
							
						}else{
							url = url + "&rs=" + new Date(); 
						} 
						parent.changeFrameUrl("rightFrame",url);
					}else{
						parent.changeFrameUrl("rightFrame","/"+"${appName}"+"/module/config_manager/error.jsp");
					}
						
				}
			);
	</script>
</head>
<body>
 <complat:tree unique="init"  treeurl="init.do?dealMethod=getTree" />
</body>
</html>
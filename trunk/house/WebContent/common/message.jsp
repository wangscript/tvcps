<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">	
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
		var actionErr = "";
		var actionMsg = "";
		var fieldErr = "";
	</script>
</head>
<body>
<s:if test="hasFieldErrors()"> 
    <s:iterator value="fieldErrors">
    	<s:iterator value="value">
			<script type="text/javascript">
				fieldErr += "* " + "<s:property escape='false'/>" +"\n" ;
			</script>
		</s:iterator> 
    </s:iterator>
    <script type="text/javascript">
    	alert(fieldErr);
    </script>
</s:if>
<!-- ActionError错误输出 -->
<s:if test="hasActionErrors()">    
    <s:iterator value="actionErrors">
       	<script  language="JavaScript">
       		actionErr += "* " + "<s:property escape='false'/>" +"\n" ;    
       	</script>    
    </s:iterator> 
   	<script type="text/javascript">
    	alert(actionErr);
    </script>   
</s:if>
<!-- ActionMessage信息输出 -->
<s:if test="hasActionMessages()">    
   	<s:iterator value="actionMessages">    
   		<script language="JavaScript">
       		actionMsg = "* " + "<s:property escape='false'/>" +"\n";  
       	</script>    
   	</s:iterator> 
   	<script type="text/javascript">
    	alert(actionMsg);
    </script>
</s:if>
</body>
</html>
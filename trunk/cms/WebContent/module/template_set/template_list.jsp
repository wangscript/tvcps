<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<%@include file="/templates/headers/header.jsp"%>
	<style type="text/css">
		.inputClass {
			width:260px; 
		}
		a {
			text-decoration:none;
		}
		
		body {
			background-color:white; 
			font-size:12px;
			font-family: "宋体",Arial;
		}

		table{		 
			font-size:12px;
			font-family: "宋体",Arial;
		}

	</style>
	<script type="text/javascript">
		
		// 设置模板
		function setTemplate(type, columnId, instanceId) {
			var templateInstanceId = $("#"+instanceId).val();
			if(templateInstanceId == null || templateInstanceId == "" || templateInstanceId == "null") {
				alert("请选择模版实例");
				return false;
			}
			url = "<c:url value='/templateUnit.do?dealMethod=templateSet&templateType='/>" + type 
		     	 + "&ids=" + templateInstanceId + "&columnId=" + columnId;
			openWindow(url,"单元设置",8000,5000,0,0);
		}

		// 选择模板
		function chooseTemplate(type, columnId, templateInstanceName, templateInstanceId, isTemplateSeted) {
			win = showWindow("chooseTemplate", "模板设置->选择", "<c:url value='/templateUnit.do?dealMethod=chooseTemplate&isTemplateSeted="+ isTemplateSeted +"&templateInstanceIdValue="+ templateInstanceId +"&templateInstance="+ templateInstanceName +"&templateType=" + type + "&nodeId=" + columnId + "'/>", 300, 0, 600, 500);	
		}

		// 撤销选择的模板 
		function cancelTemplate(type, columnId, templateInstanceName, templateInstanceId, templateSet) {
			var instanceId = $("#"+templateInstanceId).val();
			if(instanceId == null || instanceId == "null" || instanceId == "") {
				return false;
			}
			$("#nodeId").val(columnId);
			$("#strid").val(instanceId);
			$("#templateType").val(type);
			$("#dealMethod").val("cancelTemplate");
			var options = {
					url : "templateUnit.do",
			 	success : function(msg) {
						alert(msg);
					}
			}; 
			if(templateSet != "" && templateSet != null) {
				document.getElementById(templateSet).innerHTML = "×";
			}
			$("#"+templateInstanceId).val("");
			$("#"+templateInstanceName).val("");
			$("#templateUnitForm").ajaxSubmit(options);	
		}

		function closeNewChild() {
			closeWindow(win);
		}

		function showColummTemplate(columnId) { 
			var articleTemplate = "a_"+columnId;
			if(document.getElementById(articleTemplate).style.display == "none") {
				document.getElementById(articleTemplate).style.display = "";
			} else {
				document.getElementById(articleTemplate).style.display = "none";
			}
			var columnTemplate = "c_"+columnId;
			if(document.getElementById(columnTemplate).style.display == "none") { 
				document.getElementById(columnTemplate).style.display = "";
			} else {
				document.getElementById(columnTemplate).style.display = "none";
			}
		}

		// (1, <=request.getParameter("nodeId") %>, "homeTemplateName", "homeTemplateId_${templateUnitForm.site.homeTemplate.id}", "")
		// 新增模版实例并选择
		function addTemplateInstance(type, columnId, templateInstanceName, templateInstanceId, isTemplateSeted) {
			win = showWindow("addTemplateInstance", "模板设置->新增", "<c:url value='/templateUnit.do?dealMethod=addTemplate&isTemplateSeted="+ isTemplateSeted +"&templateInstanceIdValue="+ templateInstanceId +"&templateInstance="+ templateInstanceName +"&templateType=" + type + "&nodeId=" + columnId + "'/>", 300, 0, 600, 520);
		} 

	</script>
</head>
<body>
<form  action="templateUnit.do" id="templateUnitForm" name="templateUnitForm" method="post">
	<input type="hidden" id="templateType" name="templateType"/>
	<input type="hidden" id="dealMethod" name="dealMethod" />
	<input type="hidden" id="strid" name="ids" />
	<input type="hidden" id="nodeId" name="nodeId" value="<%=request.getParameter("nodeId") %>" />
	<div class="currLocation">模板设置<complat:guide className="Column" nodeId="${templateUnitForm.nodeId}" sign="→ " ></complat:guide></div>

	<c:if test="${templateUnitForm.site != null}">	
		<br/>	
		<fieldset id="site">
			<legend>&nbsp;<a style='color:blue;font:12px;font-weight:bold'>网站默认模板</a>&nbsp;</legend>
			<c:if test="${templateUnitForm.columnTemplateSet eq 'yes'}">
				<input type="hidden" name="homeTemplateId"    id="homeTemplateId_${templateUnitForm.site.homeTemplate.id}"    value="${templateUnitForm.site.homeTemplate.id}"/>
				<input type="hidden" name="defaultArticleTemplateId" id="defaultArticleTemplateId_${templateUnitForm.site.articleTemplate.id}" value="${templateUnitForm.site.articleTemplate.id}"/>
				<input type="hidden" name="defaultColumnTemplateId"  id="defaultColumnTemplateId_${templateUnitForm.site.columnTemplate.id}"  value="${templateUnitForm.site.columnTemplate.id}"/>	
				<table class="form_cls"> 
				   <tr>
			         <td>
						<label>首页模板&nbsp;&nbsp;</label>
						<label>模板名称</label>			
						<input class="inputClass" type="text" id="homeTemplateName" name="homeTemplateName" value="${templateUnitForm.site.homeTemplate.name}" readonly/>					 
						<input type="button" value="新增"  class="btn_small" onclick='addTemplateInstance(1, <%=request.getParameter("nodeId") %>, "homeTemplateName", "homeTemplateId_${templateUnitForm.site.homeTemplate.id}", "")'/>
						<input type="button" value="选择"  class="btn_small" onclick='chooseTemplate(1, <%=request.getParameter("nodeId") %>, "homeTemplateName", "homeTemplateId_${templateUnitForm.site.homeTemplate.id}", "")'/>
						<input type="button" value="撤销"  class="btn_small" onclick='cancelTemplate(1, <%=request.getParameter("nodeId") %>, "homeTemplateName", "homeTemplateId_${templateUnitForm.site.homeTemplate.id}", "")'/>
						<input type="button" value="设置"  class="btn_small" onclick='setTemplate(1, <%=request.getParameter("nodeId") %>, "homeTemplateId_${templateUnitForm.site.homeTemplate.id}") '/>
					 </td>
					</tr>
					<tr>
					 <td>
						<label>栏目页模板</label>
						<label>模板名称</label>
						<input class="inputClass" type="text" id="defaultColumnTemplateName" name="columnTemplateName" value="${templateUnitForm.site.columnTemplate.name }" readonly/>
						<input type="button" value="新增"  class="btn_small" onclick='addTemplateInstance(3, <%=request.getParameter("nodeId") %>, "defaultColumnTemplateName", "defaultColumnTemplateId_${templateUnitForm.site.columnTemplate.id}", "")'/>
						<input type="button" value="选择"  class="btn_small" onclick='chooseTemplate(3, <%=request.getParameter("nodeId") %>, "defaultColumnTemplateName", "defaultColumnTemplateId_${templateUnitForm.site.columnTemplate.id}", "")'/>
						<input type="button" value="撤销"  class="btn_small" onclick='cancelTemplate(3, <%=request.getParameter("nodeId") %>, "defaultColumnTemplateName", "defaultColumnTemplateId_${templateUnitForm.site.columnTemplate.id}", "")'/>
						<input type="button" value="设置"  class="btn_small" onclick='setTemplate(3, <%=request.getParameter("nodeId") %>, "defaultColumnTemplateId_${templateUnitForm.site.columnTemplate.id}")'/>
					 </td>
				    </tr>  
				    <tr>
					 <td> 
						<label>文章页模板</label>
						<label>模板名称</label>
						<input class="inputClass" type="text" id="defaultArticleTemplateName" name="articleTemplateName" value="${templateUnitForm.site.articleTemplate.name}" readonly/>
						<input type="button" value="新增"  class="btn_small" onclick='addTemplateInstance(2, <%=request.getParameter("nodeId") %>, "defaultArticleTemplateName", "defaultArticleTemplateId_${templateUnitForm.site.articleTemplate.id}", "")'/>
						<input type="button" value="选择"  class="btn_small" onclick='chooseTemplate(2, <%=request.getParameter("nodeId") %>, "defaultArticleTemplateName", "defaultArticleTemplateId_${templateUnitForm.site.articleTemplate.id}", "")'/>
						<input type="button" value="撤销"  class="btn_small" onclick='cancelTemplate(2, <%=request.getParameter("nodeId") %>, "defaultArticleTemplateName", "defaultArticleTemplateId_${templateUnitForm.site.articleTemplate.id}", "")'/>
						<input type="button" value="设置"  class="btn_small" onclick='setTemplate(2, <%=request.getParameter("nodeId") %>, "defaultArticleTemplateId_${templateUnitForm.site.articleTemplate.id}")'/>
					 </td>
				    </tr>
			    </table>
			</c:if>
			<c:if test="${templateUnitForm.columnTemplateSet eq 'no'}">
				<font color="blue">&nbsp;&nbsp;&nbsp;&nbsp;没有网站默认模板的设置权限</font>
			</c:if>
		</fieldset>
		<br>
		<c:if test="${templateUnitForm.columnList != null}">
		<table width="100%" cellspacing="1" bgcolor="#a6bac3"  class="form_cls ">  
			<tr height="20px" bgcolor="#ffffff">  
				<td align="center" width="35%">
					栏目名称
				</td> 
				<td align="center"  width="33%">
					栏目模板是否已设
				</td>
				<td align="center"  width="32%">
					文章模板是否已设 
				</td>
			</tr> 
		</table> 
		<table width="100%"  cellspacing="1" bgcolor="#a6bac3"  class="form_cls">
		<c:forEach var="column" items="${templateUnitForm.columnList}"> 
			<tr height="20px" bgcolor="#ffffff">
				<td align="left" width="35%">
					<a href="javascript:showColummTemplate('${column.id}')"><font color="black">${column.name}</font></a>
				</td>
				<td align="center" id="columnTemplateSet_${column.id }" width="33%">
					<c:if test="${column.description eq 'yes'}">
						<c:if test="${column.columnTemplate != null}">
							<font color="red">√</font>
						</c:if> 
						<c:if test="${column.columnTemplate == null}">
							× 
						</c:if>
					</c:if> 
				</td>
				<td align="center" id="articleTemplateSet_${column.id }" width="32%">
					<c:if test="${column.description eq 'yes'}">
						<c:if test="${column.articleTemplate != null}"> 
							<font color="red">√</font> 
						</c:if>
						<c:if test="${column.articleTemplate == null}">
							×
						</c:if>
					</c:if>
				</td>
			</tr>
			<c:if test="${column.description eq 'yes'}">
				<!-- 多信息栏目才有栏目页设置 -->
				<c:if test="${column.columnType == 'multi'}">
					<tr id="c_${column.id }" style="display:none" bgcolor="#f5f8ff">
						<th colspan="3" align="left">	 
							<input type="hidden" name="columnTemplateId"  id="columnTemplateId_${column.id }"  value="${column.columnTemplate.id}"/>
							<font color="blue">栏目页模板&nbsp;&nbsp;模板名称&nbsp;</font>
							<input class="inputClass" type="text" id="columnTemplateName_${column.id}" name="columnTemplateName" value="${column.columnTemplate.name}" readonly/>
							<input type="button" value="新增"  class="btn_small" onclick='addTemplateInstance(5, "${column.id }", "columnTemplateName_${column.id}", "columnTemplateId_${column.id}", "columnTemplateSet_${column.id }")'/>
							<input type="button" value="选择"  class="btn_small" onclick='chooseTemplate(5, "${column.id }", "columnTemplateName_${column.id}", "columnTemplateId_${column.id}", "columnTemplateSet_${column.id }")'/>
							<input type="button" value="撤销"  class="btn_small" onclick='cancelTemplate(5, "${column.id }", "columnTemplateName_${column.id}", "columnTemplateId_${column.id}", "columnTemplateSet_${column.id }")'/>
							<input type="button" value="设置"  class="btn_small" onclick='setTemplate(5, "${column.id }", "columnTemplateId_${column.id}")'/>
						</th>
					</tr> 
					<tr id="a_${column.id}" style="display:none" bgcolor="#f5f8ff">
						<th colspan="3" align="left">  
							<input type="hidden" name="articleTemplateId" id="articleTemplateId_${column.id }" value="${column.articleTemplate.id}"/>
							<font color="blue">文章页模板&nbsp;&nbsp;模板名称&nbsp;</font>
							<input class="inputClass" type="text" id="articleTemplateName_${column.id }" name="articleTemplateName" value="${column.articleTemplate.name}" readonly/>
							<input type="button" value="新增"  class="btn_small" onclick='addTemplateInstance(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="选择"  class="btn_small" onclick='chooseTemplate(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="撤销"  class="btn_small" onclick='cancelTemplate(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="设置"  class="btn_small" onclick='setTemplate(4, "${column.id }", "articleTemplateId_${column.id }")'/>
						</th>
					</tr>
				</c:if>
				<!-- 多信息栏目才有栏目页设置 -->
				<c:if test="${column.columnType == 'single'}">
					<tr id="a_${column.id}" style="display:none" bgcolor="#f5f8ff">
						<th colspan="6" align="left">  
							<input type="hidden" name="articleTemplateId" id="articleTemplateId_${column.id }" value="${column.articleTemplate.id}"/>
							<font color="blue">文章页模板&nbsp;&nbsp;模板名称&nbsp;</font>
							<input class="inputClass" type="text" id="articleTemplateName_${column.id }" name="articleTemplateName" value="${column.articleTemplate.name}" readonly/>
							<input type="button" value="新增"  class="btn_small" onclick='addTemplateInstance(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="选择"  class="btn_small" onclick='chooseTemplate(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="撤销"  class="btn_small" onclick='cancelTemplate(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="设置"  class="btn_small" onclick='setTemplate(4, "${column.id }", "articleTemplateId_${column.id }")'/>
						</th>
					</tr>
				</c:if>
			</c:if>
			<c:if test="${column.description eq 'no'}">
				<tr id="c_${column.id }" style="display:none" bgcolor="#f5f8ff">
				<th colspan="3" align="left">
					<font color="blue">&nbsp;&nbsp;&nbsp;&nbsp;没有此栏目的模版设置权限</font>
				</th>
				</tr>
				<tr id="a_${column.id}" style="display:none" bgcolor="#f5f8ff">
					<th colspan="3" align="left"></th>
				</tr>
			</c:if>
		</c:forEach>
		</table>
		</c:if>
	</c:if>


	<c:if test="${templateUnitForm.column != null}">
		<br/>	
		<fieldset id="column">
			<input type="hidden" name="articleTemplateId" id="articleTemplateId_${templateUnitForm.column.articleTemplate.id}" value="${templateUnitForm.column.articleTemplate.id}"/>
			<input type="hidden" name="columnTemplateId"  id="columnTemplateId_${templateUnitForm.column.columnTemplate.id}"  value="${templateUnitForm.column.columnTemplate.id}"/>
			<legend>&nbsp;<a style='color:blue;font:12px;font-weight:bold'>模板</a>&nbsp;</legend>
			<table  class="form_cls">
			<c:if test="${templateUnitForm.columnTemplateSet eq 'yes'}">
				<!-- 多信息栏目才有栏目页设置 -->
				<c:if test="${templateUnitForm.column.columnType == 'multi'}">
				 	<tr>
						<td> 
							<label>栏目页模板</label>&nbsp;&nbsp;<label>模板名称&nbsp;</label>
							<input class="inputClass" type="text" id="columnTemplateName" name="columnTemplateName" value="${templateUnitForm.column.columnTemplate.name}" readonly/>
							<input type="button" value="新增"  class="btn_small" onclick='addTemplateInstance(5,  "<%=request.getParameter("nodeId")%>", "columnTemplateName", "columnTemplateId_${templateUnitForm.column.columnTemplate.id}", "")'/>
							<input type="button" value="选择"  class="btn_small" onclick='chooseTemplate(5,  "<%=request.getParameter("nodeId")%>", "columnTemplateName", "columnTemplateId_${templateUnitForm.column.columnTemplate.id}", "") '/>
							<input type="button" value="撤销"  class="btn_small" onclick='cancelTemplate(5, "<%=request.getParameter("nodeId") %>", "columnTemplateName", "columnTemplateId_${templateUnitForm.column.columnTemplate.id}", "")'/>
							<input type="button" value="设置"  class="btn_small" onclick='setTemplate(5, "<%=request.getParameter("nodeId") %>", "columnTemplateId_${templateUnitForm.column.columnTemplate.id}")'/>
						</td>
				   	</tr>
				</c:if>
			   	<tr>	
					<td>
						<label>文章页模板</label>&nbsp;&nbsp;<label>模板名称&nbsp;</label>
						<input class="inputClass" type="text" id="articleTemplateName" name="articleTemplateName" value="${templateUnitForm.column.articleTemplate.name}" readonly/>
						<input type="button" value="新增"  class="btn_small" onclick='addTemplateInstance(4,  "<%=request.getParameter("nodeId") %>", "articleTemplateName", "articleTemplateId_${templateUnitForm.column.articleTemplate.id}", "")'/>
						<input type="button" value="选择"  class="btn_small" onclick='chooseTemplate(4,  "<%=request.getParameter("nodeId") %>", "articleTemplateName", "articleTemplateId_${templateUnitForm.column.articleTemplate.id}", "")'/>
						<input type="button" value="撤销"  class="btn_small" onclick='cancelTemplate(4, "<%=request.getParameter("nodeId") %>", "articleTemplateName", "articleTemplateId_${templateUnitForm.column.articleTemplate.id}", "")'/>
						<input type="button" value="设置"  class="btn_small" onclick='setTemplate(4, "<%=request.getParameter("nodeId") %>", "articleTemplateId_${templateUnitForm.column.articleTemplate.id}")'/>
					</td>
 			 	</tr>
			</c:if>
			<c:if test="${templateUnitForm.columnTemplateSet eq 'no'}">
				<tr>
					<td colspan="2">	
						<font color="blue">&nbsp;&nbsp;&nbsp;&nbsp;没有该栏目的模版设置权限</font>
					</td>
				</tr>
			</c:if>
			</table>
		</fieldset>
		<c:if test="${templateUnitForm.columnList != null}">
		<br/> 
		<table width="100%" cellspacing="1" bgcolor="#a6bac3" class="form_cls">
			<tr height="20px" bgcolor="#ffffff">  
				<td align="center" width="35%">
					栏目名称
				</td>
				<td align="center" width="33%">
					栏目模板是否已设
				</td>
				<td align="center" width="32%">
					文章模板是否已设
				</td>
			</tr>
		</table>
		<table width="100%"  cellspacing="1" bgcolor="#a6bac3" class="form_cls">
			<c:forEach var="column" items="${templateUnitForm.columnList}"> 
			<tr height="20px" bgcolor="#ffffff">
				<td align="left" width="35%">
					<a href="javascript:showColummTemplate('${column.id}')"><font color="black">${column.name}</font></a>
				</td>
				<td align="center" id="columnTemplateSet_${column.id }" width="33%">
					<c:if test="${column.description eq 'yes'}">
						<c:if test="${column.columnTemplate != null}">
							<font color="red">√</font>
						</c:if>
						<c:if test="${column.columnTemplate == null}">
							× 
						</c:if>
					</c:if>
				</td>
				<td align="center" id="articleTemplateSet_${column.id }" width="32%">
					<c:if test="${column.description eq 'yes'}">
						<c:if test="${column.articleTemplate != null}">
							<font color="red">√</font> 
						</c:if>
						<c:if test="${column.articleTemplate == null}">   
							×
						</c:if>
					</c:if>
				</td>
			</tr>
			<c:if test="${column.description eq 'yes'}">
				<!-- 多信息栏目才有栏目页设置 -->
				<c:if test="${column.columnType == 'multi'}">
					<tr id="c_${column.id }" style="display:none"  bgcolor="#f5f8ff">
						<th colspan="3" align="left">	 
							<input type="hidden" name="columnTemplateId"  id="columnTemplateId_${column.id }"  value="${column.columnTemplate.id}"/>
							<font color="blue">栏目页模板&nbsp;&nbsp;模板名称&nbsp;</font>
							<input class="inputClass" type="text" id="columnTemplateName_${column.id}" name="columnTemplateName" value="${column.columnTemplate.name}" readonly/>
							<input type="button" value="新增"  class="btn_small" onclick='addTemplateInstance(5, "${column.id }", "columnTemplateName_${column.id}", "columnTemplateId_${column.id }", "columnTemplateSet_${column.id }")'/>
							<input type="button" value="选择"  class="btn_small" onclick='chooseTemplate(5, "${column.id }", "columnTemplateName_${column.id}", "columnTemplateId_${column.id }", "columnTemplateSet_${column.id }")'/>
							<input type="button" value="撤销"  class="btn_small" onclick='cancelTemplate(5, "${column.id }", "columnTemplateName_${column.id}", "columnTemplateId_${column.id }", "columnTemplateSet_${column.id }")'/>
							<input type="button" value="设置"  class="btn_small" onclick='setTemplate(5, "${column.id }", "columnTemplateId_${column.id}")'/>
						</th>
					</tr>
					<tr id="a_${column.id}" style="display:none"  bgcolor="#f5f8ff">
						<th colspan="3" align="left">  
							<input type="hidden" name="articleTemplateId"  id="articleTemplateId_${column.id }"  value="${column.articleTemplate.id}"/>
							<font color="blue">文章页模板&nbsp;&nbsp;模板名称&nbsp;</font>
							<input class="inputClass" type="text" id="articleTemplateName_${column.id }" name="articleTemplateName" value="${column.articleTemplate.name}" readonly/>
							<input type="button" value="新增"  class="btn_small" onclick='addTemplateInstance(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="选择"  class="btn_small" onclick='chooseTemplate(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="撤销"  class="btn_small" onclick='cancelTemplate(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="设置"  class="btn_small" onclick='setTemplate(4, "${column.id }", "articleTemplateId_${column.id }")'/>
						</th>
					</tr>
				</c:if> 
				<c:if test="${column.columnType == 'single'}">
					<tr id="a_${column.id}" style="display:none"  bgcolor="#f5f8ff">
						<th colspan="6" align="left">  
							<input type="hidden" name="articleTemplateId"  id="articleTemplateId_${column.id }"  value="${column.articleTemplate.id}"/>
							<font color="blue">文章页模板&nbsp;&nbsp;模板名称&nbsp;</font>
							<input class="inputClass" type="text" id="articleTemplateName_${column.id }" name="articleTemplateName" value="${column.articleTemplate.name}" readonly/>
							<input type="button" value="新增"  class="btn_small" onclick='addTemplateInstance(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="选择"  class="btn_small" onclick='chooseTemplate(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="撤销"  class="btn_small" onclick='cancelTemplate(4, "${column.id }", "articleTemplateName_${column.id }", "articleTemplateId_${column.id }", "articleTemplateSet_${column.id }")'/>
							<input type="button" value="设置"  class="btn_small" onclick='setTemplate(4, "${column.id }", "articleTemplateId_${column.id }")'/>
						</th>
					</tr>
				</c:if> 
				
			</c:if>
			<c:if test="${column.description eq 'no'}">
				<tr id="c_${column.id }" style="display:none" bgcolor="#f5f8ff">
				<th colspan="3" align="left">
					<font color="blue">&nbsp;&nbsp;&nbsp;&nbsp;没有此栏目的模版设置权限</font>
				</th>
				</tr>
				<tr id="a_${column.id}" style="display:none" bgcolor="#f5f8ff">
					<th colspan="3" align="left"></th>
				</tr>
			</c:if> 
			</c:forEach>
		</table>
		</c:if> 
	</c:if>
</form>
</body>
</html>
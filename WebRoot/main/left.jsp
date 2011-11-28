<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>CCMS系统管理页面</title>	
	<%@include file="/templates/headers/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>"/>
	<script type="text/javascript" src="<c:url value="/script/ext/myjs/tree/tree.js"/>"></script>	
	<script type="text/javascript">	
		// 切换网站
		function changeSite() {
			var win = showWindow("neworganization","切换网站","<c:url value='/module/site_manager/choose_site.jsp'/>",293, 0,480,400);
		}

		function messageManager(){
			var dhxAccord5 = dhxLayout.cells("b").attachAccordion();	
			dhxAccord5.addItem("messageManager","消息管理");		
			dhxAccord5.cells("messageManager").attachURL("/"+"${appName}"+"/module/message_manager/site_message/index.jsp");
		}
	</script>
</head>
<body style="width: 100%; height: 100%; margin: 0px; overflow: hidden;">
<input type="hidden" name="tempid" id="tempid" value="${firstId}"/>
<input type="hidden" name="tempname" id="tempname" value="${firstName}"/>
<input type="hidden" name="tempurl" id="tempurl" value="${firstIndex}"/>
<div class="Container" id="header">
		<!--//*主体顶部导航*//--> 
	<div id=logo>
		<div id="top3">
			<div id="top_logo"><img src="<c:url value='/images/main/cms-logo_12.gif'/>" width="116px" height="30px" /></div>
			<div id="top_site" style="cursor: pointer" onclick="changeSite();">切换网站</div>
			<div id="message" style="cursor: pointer" onclick="messageManager();">短消息</div>
			<div id="top2" style="cursor: pointer" >系统管理员</div>
			<div id="top4" style="cursor: pointer" >【登出】|【帮助】</div>
		</div>
	</div>
	<div id="line1"></div>
	<div id="menutop">		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
			<c:forEach var="menu" items="${menuList}">									
			<span id="${menu.id}" class="btn3_mouseout" 
				onmouseover="this.className='btn3_mouseover'"
				onmouseout="this.className='btn3_mouseout'" 
			 	onclick="loadIndex('${menu.id}','${menu.name}','${menu.indexPage}');">
			 	${menu.name}
			</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</c:forEach>
	</div>	
	<div id="line2"></div>
	<div id="tree-div" style="border:0px;"></div>
</div>

<script type="text/javascript">
	
		//初始化布局
		var dhxLayout = new dhtmlXLayoutObject(document.body, "4T","dhx_blue");
	
		/**
		 * 头设置(a)
		 */ 
		dhxLayout.cells("a").hideHeader();
		dhxLayout.cells("a").setHeight(83);	
		dhxLayout.cells("a").fixSize(true, true);
		dhxLayout.cells("a").attachObject("header");
	
		
		/**
		 * 左侧设置(b)
		 */	
		dhxLayout.cells("b").setText("CCMS系统管理");
		dhxLayout.cells("b").setWidth(180);
	//	dhxLayout.cells("b").fixSize(true, true);
		dhxLayout.cells("b").setHeight(10);
//		dhxLayout.cells("b").attachStatusBar();
		var dhxAccord = dhxLayout.cells("b").attachAccordion();			


		// 用户管理菜单
		if($("#tempname").val() != null && $("#tempname").val() == "用户管理"){	
			//获取用户管理所有的URL
			var indexurl = $("#tempurl").val();
			mystr = indexurl.split(",");			
			dhxAccord.addItem("organizationManager","机构管理");		
			dhxAccord.cells("organizationManager").attachURL("/"+"${appName}"+mystr[0]);
			
			dhxAccord.addItem("userManager","用户管理");			
			dhxAccord.cells("userManager").attachURL("/"+"${appName}"+mystr[1]);
		
			dhxAccord.addItem("roleManager","角色管理");
			dhxAccord.cells("roleManager").attachURL("/"+"${appName}"+mystr[2]);

			// 默认打开“机构管理”
			dhxAccord.openItem("organizationManager");	

		// 文章管理菜单
		} else if ($("#tempname").val() != null && $("#tempname").val() == "文章管理") {
			var indexurl = $("#tempurl").val();
			mystr = indexurl.split(",");			
			dhxAccord.addItem("articleManager","文章管理");		
			dhxAccord.cells("articleManager").attachURL("/"+"${appName}"+mystr[0]);
			
			dhxAccord.addItem("formatManager","格式管理");			
			dhxAccord.cells("formatManager").attachURL("/"+"${appName}"+mystr[1]);
		
			dhxAccord.openItem("articleManager");	
		} else {
			dhxAccord.addItem($("#tempid").val(),$("#tempname").val());
			//	alert("/"+"${appName}"+$("#tempurl").val());
			dhxAccord.cells($("#tempid").val()).attachURL("/"+"${appName}"+$("#tempurl").val());
		}
	
		dhxAccord._enableOpenEffect = true;		// 滑动效果


		function 
		
		
		/**
		 * 菜单单击后，加载
		 * menuId : 菜单ID
		 * menuName : 菜单名称
	     * indexPage : 菜单首页
		 */
		function loadIndex(menuId, menuName, indexPage){		
			var dhxAccord1 = dhxLayout.cells("b").attachAccordion();				
			if (menuName == "用户管理") {
			//	alert("indexPage==="+indexPage);
				var mystr = indexPage.split(",");			
				dhxAccord1.addItem("organizationManager","机构管理");
			    dhxAccord1.cells("organizationManager").attachURL("/"+"${appName}"+mystr[0]);
			    
				dhxAccord1.addItem("userManager","用户管理");			
				dhxAccord1.cells("userManager").attachURL("/"+"${appName}"+mystr[1]);

				dhxAccord1.addItem("roleManager","角色管理");				
				dhxAccord1.cells("roleManager").attachURL("/"+"${appName}"+mystr[2]);
				
				dhxAccord1.openItem("organizationManager");
			} else if (menuName == "文章管理") {
				var str = indexPage.split(",");			
				dhxAccord1.addItem("articleManager","文章管理");		
				dhxAccord1.cells("articleManager").attachURL("/"+"${appName}"+str[0]);
				
				dhxAccord1.addItem("formatManager","格式管理");			
				dhxAccord1.cells("formatManager").attachURL("/"+"${appName}"+str[1]);
			
				dhxAccord1.openItem("articleManager");	
			} else {
				dhxAccord1.addItem(menuId,menuName);
			    dhxAccord1.cells(menuId).attachURL("/"+"${appName}"+indexPage);
			    dhxAccord1.openItem(menuId);
			}
		}
		dhxLayout.cells("c").dock();


		/**
		 * 中部设置
		 */
		//dhxLayout.cells("c").attachURL("http://www.google.com");
		//dhxLayout.cells("c").attachURL("article_manager/list_article.html");
	//	dhxLayout.cells("c").attachStatusBar();
		var myTabbar = dhxLayout.cells("c").attachTabbar();
		myTabbar.setImagePath("<c:url value="/images/"/>");
		

		myTabbar.enableAutoSize(true, true);
	  //myTabbar.setOnSelectHandler(my_func);
	//	myTabbar.loadXML("<c:url value="/main/xml/tabs.xml"/>");
		

		/**
		 * 右侧设置
		 */
		//默认将d收起
		dhxLayout.cells("d").setText("快捷栏");
		dhxLayout.cells("d").setWidth(100);
		dhxLayout.cells("d").collapse();
		dhxLayout.cells("d").attachStatusBar();
		
		
		// 固定尺寸
	
		//var dhxTabbar = dhxLayout.cells("a").attachTabbear();
		//dhxTabbar.setImagePath("imgs");

	
</script>



</body>
</html>
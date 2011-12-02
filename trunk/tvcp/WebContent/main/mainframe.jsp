<%@page contentType="text/html; charset=utf-8" language="java" errorPage="" %>

<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>CPS管理系统</title>
    <%@include file="/templates/headers/header.jsp"%>
    <c:if test="${sessionScope.sessionid eq null}">
        <%
            response.sendRedirect(request.getContextPath() + "/failure.jsp");
        %>
    </c:if>    

      <link rel="stylesheet" type="text/css" href="<c:url value='/css/dhtmlx.css'/>">
    
    <script type="text/javascript" src="<c:url value='/script/dhtmlx.js'/>" ></script>  
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>"/>
 
    <link rel="stylesheet" type="text/css" href="<c:url value="/script/ext/resources/css/ext-all.css"/>"/>     
    <script type="text/javascript" src="<c:url value="/script/ext/adapter/ext/ext-base.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/script/ext/ext-all-debug.js"/>"></script>     
    <style type="text/css">
        * {
            margin:0px; padding:0px;
        }
    </style>  
    <script type="text/javascript">  
    
        var winId;

        var tmp = '${siteForm.loginMessage}';
        if(tmp != null && tmp != "") {        
            parent.window.location.href = "/"+"${appName}"+"/main/cps.jsp?name=${siteForm.siteloginname}&password=${siteForm.siteloginpassword}";
        }

        function logout() {
            parent.window.location.href = "loginaction.do?logintype=loginout";
        }

        
        var test;
        $(document).ready(
            function(){
            //    var personalName = document.getElementById("personalName").value;
                    
                 if($("#loginmessage").val() != null && $("#loginmessage").val() != "") {
                    parent.document.loginForm.name.value = $("#siteloginname").val();
                    parent.document.loginForm.password.value = $("#siteloginpassword").val();
                    parent.document.loginForm.submit();
                }
            }
        );

        //短消息
        function messageManager(){
            // 判断网站是否创建
            if($("#tempid").val().isEmpty()) {
                alert("请先创建网站!");
                return false;
            }
            document.getElementById(currMenu).className = "normal";
            $("#"+currMenu).css("color", "#FFFFFF");
            document.getElementById("leftFrame").innerHTML = "";    
            dhxAccord = new dhtmlXAccordion("leftFrame");    
            dhxAccord.addItem("messageManager","消息管理");    
            dhxAccord.cells("messageManager").attachURL("/"+"${appName}"+"/module/message_manager/index.jsp");
        }
        
        <%
            if(request.getAttribute("siteForm.loginMessage") != null) {
                return;                
            }
        %>

        // 切换网站
        function changeSite() {
            // 判断网站是否创建
            if($("#tempid").val().isEmpty()) {
                alert("请先创建网站!");
                return false;
            }
            var win = showWindow("neworganization","切换网站","<c:url value='/site.do?dealMethod=changeSite'/>",293, 0,430,350);
        }

        // 系统设置
        function systemSet() {
            // 判断网站是否创建
            if($("#tempid").val().isEmpty()) {
                alert("请先创建网站!");
                return false;
            }
            if(document.getElementById(currMenu)){
                document.getElementById(currMenu).className = "normal";
                $("#"+currMenu).css("color", "#FFFFFF");
            }
            document.getElementById("leftFrame").innerHTML = "";    
            dhxAccord = new dhtmlXAccordion("leftFrame");    
            dhxAccord.addItem("systemSet","系统设置");    
        //    dhxAccord.cells("systemSet").attachURL("/"+"${appName}"+"/module/config_manager/index.jsp");
            dhxAccord.cells("systemSet").attachURL("<c:url value='/init.do?dealMethod=load'/>");
        }
        
        if(self != top) {
           //top.location = self.location;
        }
    
        function switchSysBar(ele) {
            var sp = document.getElementById("switchPoint").value;
            if (sp == 0) {
                document.getElementById("switchPoint").value = 1;
                document.getElementById("leftNavigate").style.display="none";
            } else {
                document.getElementById("switchPoint").value = 0;
                document.getElementById("leftNavigate").style.display="";
            }
            changeImgBg(ele, 'out');
        }
    
        function changeImgBg(element, status) {
            var sp = document.getElementById("switchPoint").value;
            if (sp == 0) {
                if (status == 'out') {
                    element.src = "/${appName}/images/main/nav_porinter_open_out.gif";
                } else {
                    element.src = "/${appName}/images/main/nav_porinter_open_over.gif";
                }
            } else {
                if (status == 'out') {
                    element.src = "/${appName}/images/main/nav_porinter_close_out.gif";
                } else {
                    element.src = "/${appName}/images/main/nav_porinter_close_over.gif";
                }
            }
        }

        //当前用户设置
        function userManager(){
            // 判断网站是否创建
            if($("#tempid").val().isEmpty()) {
                alert("请先创建网站!");
                return false;
            }
            var win = showWindow("personalInfo","个人信息","<c:url value="/user.do?dealMethod=personalInfoDetail"/>",0, 0,850,260);
            
        }

        // 菜单管理
        function menuManager() {
            // 判断网站是否创建
            if($("#tempid").val().isEmpty()) {
                alert("请先创建网站!");
                return false;
            }
            if(document.getElementById(currMenu)){
                document.getElementById(currMenu).className = "normal";
            }
            document.getElementById("leftFrame").innerHTML = "";
            dhxAccord = new dhtmlXAccordion("leftFrame");
            dhxAccord.addItem("menuManager","个人设置");
            dhxAccord.cells("menuManager").attachURL("/"+"${appName}"+"/module/user_manager/menu/index.jsp");
        }

        // 个人菜单管理
        function personSetManager() {
            // 判断网站是否创建
            if($("#tempid").val().isEmpty()) {
                alert("请先创建网站!");
                return false;
            }
            document.getElementById("leftFrame").innerHTML = "";
            dhxAccord = new dhtmlXAccordion("leftFrame");
            dhxAccord.addItem("menuManager","个人设置");
            dhxAccord.cells("menuManager").attachURL("/"+"${appName}"+"/module/user_manager/menu/index.jsp");
        }

        function currentLineUser() {
            // 判断网站是否创建
            if($("#tempid").val().isEmpty()) {
                alert("请先创建网站!");
                return false;  
            }  
            win = showWindow("currentLineUser","当前在线用户","<c:url value='/user.do?dealMethod=currentLineUser'/>",0, 0,500,260);
        }

        function help() {
            win = showWindow("help", "关于CPS--网页对话框", "<c:url value='/main/help.jsp'/>",0, 0,500,370);
        }
      
    </script>
</head>
<body>
<input type="hidden" name="switchPoint" id="switchPoint" value="0"/>
<input type="hidden" name="tempid" id="tempid" value="${firstId}"/>
<input type="hidden" name="tempname" id="tempname" value="${firstName}"/>
<input type="hidden" name="tempurl" id="tempurl" value="${firstIndex}"/>
<input type="hidden" name="tempPage" id="tempPage" value="${firstPage}"/>
<input type="hidden" name="num" id="num" value=""/>
<input type="hidden" name="messageNum" id="messageNum" value="${messageNum}"/>
<input type="hidden" name="personalName" id="personalName" value="${personalName}"/>
<input type="hidden" name="roleName" id="roleName" value="${roleName}"/>
<input type="hidden" name="menuFunction" id="menuFunction" value="${menuFunction}"/>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" id="mainPanel">
    <tr>
        <td height="60">
        <table width="100%" height="60" border="0" cellspacing="0"
            cellpadding="0">
            <tr>
                <td><iframe frameborder="0" id="topFrame" name="topFrame" scrolling="no"
                    style="height: 69px; visibility: inherit; width: 100%; z-index: 3"
                    src="main/top.jsp"></iframe>
                </td>
            </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td height="30" valign="top">
            <table id="menu" width="100%" border="0" cellspacing="0" cellpadding="0" height="20">
                <tr>
                    <c:forEach var="menu" items="${menuList}">
                        <c:if test="${menu.id != 'm010'}">
                            <td align="center" width="95"><a id="${menu.id}" onFocus="this.blur()" style=" font-weight:900;" href="javascript:loadIndex('${menu.id}','${menu.name}','${menu.indexPage}','${menu.contentPage}')" >${menu.name}</a></td>
                            <td align="center" width="2"><img src="images/main/line.gif"/></td>
                        </c:if>
	                    <c:if test="${menu.id == 'm010'}">
	                        <input type="hidden" name="m010" id="m010" value="m010"/>
	                    </c:if> 
                    </c:forEach>
                    <td align="right" style="padding:0px 115px;">
                        <table width="160" height="18" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="80" align="right" style="cursor:pointer;" onClick="parent.personSetManager()" ><font color="#D47903">个人设置&nbsp;|&nbsp;</font></td>
                            <!-- 
                            <td width="20" align="right"><img src="images/main/3_27.gif" width="14" height="14" onclick="help();"></td>
                             -->
                            <td width="45" align="center" style="cursor:pointer;" onclick="help();"><font color="#D47903">关于&nbsp;|</font></td>
                            <!-- 
                            <td width="25" align="right" class="border_left"><img src="images/main/3_29.gif" width="14" height="14" style="cursor:pointer;" onclick="logout();"></td>
                             -->
                            <td width="35"  align="center" style="cursor:pointer;" onclick="logout();"><font color="#D47903">&nbsp;退出</font></td>
                          </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="height:100%;">
                <tr>
                    <td align="center" id="leftNavigate">
                    <div id="leftFrame" style="width:200px;height:100%;margin:0px;padding:0px;"></div>
                    <!-- 
                    <iframe frameborder="0" id="leftFrame" name="leftFrame" scrolling="auto" style="height:100%;visibility:inherit;width:200px;z-index: 2"
                            src="left.jsp">
                    </iframe>
                     --></td>
                    <td style="width:9px" class="division">
                    <table border="0" cellpadding="0" cellspacing="0"
                            style="height:100%">
                            <tr>
                                <td bgcolor="#9FAEB3" style="height:100%"><img
                                    style="cursor:pointer;"
                                    src="/${appName}/images/main/nav_porinter_open_out.gif"
                                    
                                    onclick="switchSysBar(this);"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td style="width:100%;border:1px;border-color:#9FAEB3">
                        <table style="height:1px;background-color:#9FAEB3;width:100%"><tr><td></td></tr></table>
                        <!-- <div style="margin:0;padding:0;height:1px;background-color:red;"></div>
                        -->
                        <iframe frameborder="0" id="rightFrame"
                            name="rightFrame" scrolling="yes"
                            style="height:100%;visibility:inherit;width:100%;z-index:1;padding:0 0 0 0px;"
                            src="">
                        </iframe>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<script type="text/javascript">

    // 初始化菜单
    var currMenu = "${firstId}";

    //初始化布局
    var dhxAccord = new dhtmlXAccordion("leftFrame");
    var currAccordId;    
    var currAccordUrl;
    dhxAccord.attachEvent("onActive", function(itemId){
        currAccordId = itemId;
    });     
    var indexurl = $("#tempurl").val();
    var mystr = indexurl.split(",");    

//    alert("righturl =111=="+document.getElementById("tempPage").value);
    // 判断网站是否创建
    if($("#tempid").val().isEmpty()) {
    
        dhxAccord.addItem("1000001","");
        changeFrameUrl("rightFrame", "/"+"${appName}"+"/site_null.jsp");
    } else {
        if($("#tempid").val() == "1"){
            //如果只有系统设置菜单
            dhxAccord.addItem("1000001","");
            changeFrameUrl("rightFrame", "");
        }else{
            
            document.getElementById("${firstId}").className = "active";
            $("#${firstId}").css("color", "#0000FF");
            // 用户管理菜单
            if($("#tempname").val() != null && $("#tempname").val() == "用户管理"){    
            
            
                dhxAccord.addItem("organizationManager","机构管理");
                changeAccordUrl(dhxAccord,"organizationManager","/"+"${appName}"+mystr[0]+"?t="+new Date());        
                
                dhxAccord.addItem("userManager","用户管理");
                changeAccordUrl(dhxAccord,"userManager","/"+"${appName}"+mystr[1]+"?t="+new Date());            
            
                dhxAccord.addItem("roleManager","角色管理");
                changeAccordUrl(dhxAccord,"roleManager","/"+"${appName}"+mystr[2]);
    
                // 默认打开“机构管理”
                dhxAccord.openItem("organizationManager");    
                currAccordId = "organizationManager";
                changeFrameUrl("rightFrame", "/"+"${appName}/"+"organization.do?dealMethod=");
            // 文章管理菜单
            } else if ($("#tempname").val() != null && $("#tempname").val() == "文章管理") {
            //    alert("righturl ==="+$("tempPage").val());
                if($("#menuFunction").val() == "admin"){
                    dhxAccord.addItem("articleManager","文章管理");
                    changeAccordUrl(dhxAccord,"articleManager","/"+"${appName}"+mystr[0]+"?t="+new Date());        
                    
                    dhxAccord.addItem("formatManager","格式管理");    
                    changeAccordUrl(dhxAccord,"formatManager","/"+"${appName}"+mystr[1]+"?t="+new Date());        
                
                    dhxAccord.openItem("articleManager");    
                    currAccordId = "articleManager";
                    changeFrameUrl("rightFrame", "/"+"${appName}/"+document.getElementById("tempPage").value);
                }else if($("#menuFunction").val() == "1"){
                    dhxAccord.addItem("articleManager","文章管理");
                    changeAccordUrl(dhxAccord,"articleManager","/"+"${appName}"+mystr[0]+"?t="+new Date());    
                    changeFrameUrl("rightFrame", "/"+"${appName}/article.do?dealMethod=&operationType=article&t"+new Date());    
                }else if($("#menuFunction").val() == "2"){
                    dhxAccord.addItem("formatManager","格式管理");    
                    changeAccordUrl(dhxAccord,"formatManager","/"+"${appName}"+mystr[1]+"?t="+new Date());    
                    changeFrameUrl("rightFrame", "/"+"${appName}/articleFormat.do?dealMethod=&t"+new Date());                    
                }
            }  else if ($("#tempname").val() != null && $("#tempname").val() == "功能单元") {
                dhxAccord.addItem($("#tempid").val(),$("#tempname").val());
                changeAccordUrl(dhxAccord,$("#tempid").val(),"/"+"${appName}"+$("#tempurl").val());
                currAccordId = $("#tempid").val();
            } else {

                dhxAccord.addItem($("#tempid").val(),$("#tempname").val());
                
                currAccordId = $("#tempid").val();
                if($("#tempname").val() != null && $("#tempname").val() != "系统切换" && $("#tempname").val() != "系统设置 "){
                    changeAccordUrl(dhxAccord,$("#tempid").val(),"/"+"${appName}"+$("#tempurl").val()+"?t="+new Date());
                    changeFrameUrl("rightFrame", "/"+"${appName}/"+document.getElementById("tempPage").value);
                }
            }
        }
        
    }

    //dhxAccord._enableOpenEffect = true;        // 滑动效果
    
    function reloadAccordion(url) {
        dhxAccord.cells(currAccordId).attachURL(url);
    }
    
    function reloadLeftFrame() {
        //dhxAccord.
    }

    /**
     * 菜单单击后，加载
     * menuId : 菜单ID
     * menuName : 菜单名称
     * indexPage : 菜单首页
     * contentPage: 右侧内容页
     */
    function loadIndex(menuId, menuName, indexPage, contentPage){
        if (currMenu != menuId) {
            document.getElementById(currMenu).className = "normal";
            document.getElementById(menuId).className = "active";
            $("#"+menuId).css("color", "#0000FF");
            $("#"+currMenu).css("color", "#FFFFFF");
            currMenu = menuId;
        }
        document.getElementById("leftFrame").innerHTML = "";
        dhxAccord = new dhtmlXAccordion("leftFrame");
        if (menuName == "用户管理") {
            document.getElementById(currMenu).className = "normal";
            document.getElementById(menuId).className = "active";
            var mystr = indexPage.split(",");
            dhxAccord.addItem("organizationManager","机构管理");
            changeAccordUrl(dhxAccord,"organizationManager","/"+"${appName}"+mystr[0]+"?t="+new Date());
            
            dhxAccord.addItem("userManager","用户管理");
            changeAccordUrl(dhxAccord,"userManager","/"+"${appName}"+mystr[1]+"?t="+new Date());
            
            dhxAccord.addItem("roleManager","角色管理");
            changeAccordUrl(dhxAccord,"roleManager","/"+"${appName}"+mystr[2]);
            
            dhxAccord.openItem("organizationManager");
            currAccordId = "organizationManager";
            changeFrameUrl("rightFrame", "/${appName}/"+contentPage+"&"+getUrlSuffixRandom());
        } else if (menuName == "文章管理") {
            document.getElementById(currMenu).className = "normal";
            document.getElementById(menuId).className = "active";
            var str = indexPage.split(",");
            if($("#menuFunction").val() == "admin"){
                dhxAccord.addItem("articleManager","文章管理");
                changeAccordUrl(dhxAccord,"articleManager","/"+"${appName}"+str[0]+"?t="+new Date());
                
                dhxAccord.addItem("formatManager","格式管理");
                changeAccordUrl(dhxAccord,"formatManager","/"+"${appName}"+str[1]+"?t="+new Date());
            
                dhxAccord.openItem("articleManager");
                currAccordId = "articleManager";
                 changeFrameUrl("rightFrame", "/"+"${appName}/article.do?dealMethod=&operationType=article&t"+new Date());
            }else if($("#menuFunction").val() == "1"){
                dhxAccord.addItem("articleManager","文章管理");
                changeAccordUrl(dhxAccord,"articleManager","/"+"${appName}"+str[0]+"?t="+new Date());
                dhxAccord.openItem("articleManager");
                currAccordId = "articleManager";
                changeFrameUrl("rightFrame", "/"+"${appName}/article.do?dealMethod=&operationType=article&t"+new Date());
            }else if($("#menuFunction").val() == "2"){
                dhxAccord.addItem("formatManager","格式管理");
                changeAccordUrl(dhxAccord,"formatManager","/"+"${appName}"+str[1]+"?t="+new Date());
                dhxAccord.openItem("formatManager");
                currAccordId = "formatManager";
                changeFrameUrl("rightFrame", "/"+"${appName}/articleFormat.do?dealMethod=&t"+new Date());                    
            }
            
        } else if (menuName== "功能单元") {
            dhxAccord.addItem(menuId,menuName);
            changeAccordUrl(dhxAccord,menuId,"/"+"${appName}"+indexPage);
            dhxAccord.openItem(menuId);
            currAccordId = menuId;
        } else {
            dhxAccord.addItem(menuId,menuName);
            changeAccordUrl(dhxAccord,menuId,"/"+"${appName}"+indexPage+"?t="+new Date());
            dhxAccord.openItem(menuId);
            currAccordId = menuId;
            changeFrameUrl("rightFrame", "/${appName}/"+contentPage+"&"+getUrlSuffixRandom());
        }
    }
    
    /**
     * 改变according中附加的url
     * accord:accord对象
     * cell:  accord中的一个条目
     * url:   链接地址
     */
    function changeAccordUrl(accord, cell, url) {
        accord.cells(cell).attachURL(url);
    }
</script>
</body>
</html>

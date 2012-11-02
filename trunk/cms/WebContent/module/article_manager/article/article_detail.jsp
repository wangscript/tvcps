<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt"  prefix="fmt_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文章管理</title>
<%@include file="/templates/headers/header.jsp"%>

<script type="text/javascript" src="<c:url value="/script/My97DatePicker/WdatePicker.js"/>"></script>
<style type="text/css" media="all">
    label {
        width:100px;
    }
    #main {
        margin:5px auto;
    }
    #main li {
        margin:10px;
    }
    .normal {
        margin:5px auto;
    }
    .normal li {
        margin:10px;
    }
    
    #btn {
        padding:20px 0 0 105px;
    }
    
</style>
<script type="text/javascript" src="<c:url value="/script/fckeditor/fckeditor.js"/>"></script>
<script type="text/javascript">

    $(document).ready(function() {

        //保存并继续添加后重置
        var message = document.getElementById("message").value;
        if(message == "保存并继续添加文章成功") {
            document.getElementById("articleId").value = "";
        }
        // 显示文章明细
        if (!$("#articleId").val().isEmpty() && $("#articleId").val() != 0) {
            $("#dealMethod").val("detail");
        //    loadArticleForm();
        } else {
            // 显示文章录入表单
            var formatId = $("#formats option:selected").val();
            $("#formatId").val(formatId);
            $("#dealMethod").val("getForm");
        //    loadArticleForm();
        }
        /**
         * 绑定格式改变事件
         * 动态修改文章表单
         */ 
         $("#formats").change(function() { 
            $("#dealMethod").val("getForm");
            var formatId = $("#formats option:selected").val();
            $("#formatId").val(formatId);
        //    loadArticleForm();
        });
         

        $.each($("textarea"), function(i, n) {
            // 初始化高级编辑器
            var oFCKeditor = new FCKeditor($(n).attr("name")) ;
            oFCKeditor.BasePath = "<c:url value='/script/fckeditor/'/>" ;
            oFCKeditor.Width = "90%";
            oFCKeditor.Height = "300";
            oFCKeditor.Value = $(n).val();
            oFCKeditor.ToolbarSet="CPS_openbasic";
            oFCKeditor.ReplaceTextarea() ;
        });
    
        
    });
    
    // 装载文章表单
    function loadArticleForm() {
        $("#articleForm").ajaxSubmit({
              success: function(data) {    
                setTimeout($.unblockUI, 1000);
                $("#articleDiv").html(data);
    /*
                //为作者select控件对象赋值
                var generalSystemSet = document.getElementById("generalSystemSetList").value;
                var  generalSystemSetList=generalSystemSet.split("*");
                var selectData = document.getElementById('generalSystemSetHtml');
                for(var i=0;i<generalSystemSetList.length-1;i++){
                    var option=new Option(generalSystemSetList[i],generalSystemSetList[i]);                    
                    selectData.options.add(option);
                  }
    */
                //控制审核并保存按钮
                var articleAuditedRights = document.getElementById("articleAuditedRights").value;
                if(articleAuditedRights == "RIGHT") {
                    document.getElementById("articleAuditRight").style.display = "";
                }
                /*var columnAudited = document.getElementById("columnAudited").value;
                if(columnAudited == "true"){
                    document.getElementById("articleAuditRight").style.display = "none";
                } */
                
                //来源设置
/*
                var generalSystemSetOrgin = document.getElementById("generalSystemSetOrgin").value;
                var  generalSystemSetOrginList=generalSystemSetOrgin.split("*");
                var selectDataOrgin = document.getElementById('yy');
                for(var j=0;j<generalSystemSetOrginList.length;j++){                
                    //alert("value==="+generalSystemSetOrginList[j]);                    
                    var orgin= new Option(generalSystemSetOrginList[j],generalSystemSetOrginList[j]);            
                    selectDataOrgin.options.add(orgin);
                  }
        */                  
                $.each($("textarea"), function(i, n) {
                    // 初始化高级编辑器
                    var oFCKeditor = new FCKeditor($(n).attr("name")) ;
                    oFCKeditor.BasePath = "<c:url value='/script/fckeditor/'/>" ;
                    oFCKeditor.Width = "95%";
                    oFCKeditor.Height = "300";
                    oFCKeditor.Value = $(n).val();
                    oFCKeditor.ToolbarSet="CPS_openbasic";
                    oFCKeditor.ReplaceTextarea() ;
                });
              },
              dataType: "text",
              type: "post"
        });


        
    }

    //去除字符串前后的空格
    function myTrim(str) {     
        return str.replace(/^\s+/,'').replace(/\s+$/,'');   
    }
    
    // 保存
    function btn_confirm() {
        var ref = $("#isref").val();
        if(ref == 1) {
            alert("引用的文章不能进行该操作,请点击返回");
            return false;
        }

        var articleTitle = document.getElementById("articleTitle").value;
        articleTitle = myTrim(articleTitle);
        $("#articleTitle").val(articleTitle);
        if(articleTitle == null || articleTitle == "") {
            alert("请输入文章标题");
            return false;
        }
        
        /*var top = $("#articletop").attr("checked");
        $("#articletop").val(top);*/
        if ($("#articleId").val().isEmpty() || $("#articleId").val() == 0) {
            $("#dealMethod").val("add");
        } else {
            $("#dealMethod").val("modify");

            // 设置栏目ID和格式ID
            $("#columnId").val($("#colId").val());
            $("#formats").val($("#forId").val());

        }
        $("#articleForm").submit();
    }

    // 审核
    function btn_audit() {
        /*var article = $("#articleaudited").val();
        if(article == "false") {
            $("#articleaudited").val("true");
        } 
        */
        
        var ref = $("#isref").val();
        if(ref == 1) {
            alert("引用的文章不能进行该操作,请点击返回");
            return false;
        }

        var articleTitle = document.getElementById("articleTitle").value;
        articleTitle = myTrim(articleTitle);
        $("#articleTitle").val(articleTitle);
        if(articleTitle == null || articleTitle == "") {
            alert("请输入文章标题");
            return false;
        }
        /*var top = $("#articletop").attr("checked");
        $("#articletop").val(top);*/
        $("#dealMethod").val("auditAndSave"); 
        if($("#columnId").val() == "" || $("#columnId").val() == null) {
            $("#columnId").val($("#colId").val());
        }
        // 设置栏目ID和格式ID
        //$("#columnId").val($("#columnId").val());
        //$("#formats").val($("#formatId").val());
        $("#articleForm").submit();
    }

    //保存并继续添加
    function btn_saveAndAdd() {
        var ref = $("#isref").val();
        if(ref == 1) {
            alert("引用的文章不能进行该操作,请点击返回");
            return false;
        }

        var articleTitle = document.getElementById("articleTitle").value;
        articleTitle = myTrim(articleTitle);
        $("#articleTitle").val(articleTitle);
        if(articleTitle == null || articleTitle == "") {
            alert("请输入文章标题");
            return false;
        }

        if ($("#articleId").val().isEmpty() || $("#articleId").val() == 0) {
            $("#dealMethod").val("saveAndAdd");
        } else {
            $("#dealMethod").val("modifyAndAdd");

            // 设置栏目ID和格式ID
            $("#columnId").val($("#colId").val());
            $("#formats").val($("#forId").val());

        }
        $("#articleForm").submit();
    }
    
    // 选择图片
    function uploadPicture(pic) {
        win = showWindow("uploadPicture", "选择图片", "<c:url value='/picture.do?dealMethod=uploadPicList&articlePicture="+ pic +"&columnLink=4&nodeId=f004&nodeName=图片类别'/>", 0, 0, 918, 550);
    } 

    // 清空图片
    function clearPic(pic) {
        document.getElementById(pic).value = "";
        document.getElementById("imgPreview").innerHTML = "";
        document.getElementById("imgPreview").innerHTML;
        document.getElementById("displayPicPreview").style.display = "none";
        var flag = false;
        for(var i = 0; i < maxPicCount; i++){
            var articlePic = document.getElementById("article.pic"+i);
            if(articlePic != null){
                var a = articlePic.value;
                if(a != null && a != ""){
                    flag = true;
                    document.getElementById("imgPreview").innerHTML += "<img src=\""+a.substring(1, a.length)+"\" width=\"50px\" height=\"50px\"/>&nbsp;&nbsp;";
                }
            }
        }
        if(flag){
            document.getElementById("displayPicPreview").style.display = "block";
        }
    }
    
    // 选择flash
    function uploadFlash(flash) {
        win = showWindow("uploadFlash", "选择flash", "<c:url value='/flash.do?dealMethod=insertFlashList&articleFlash="+ flash +"&nodeId=f005&nodeName=flash类别'/>", 0, 0, 918, 550);
    }

    // 清空flash
    function clearFlash(flash) {
        document.getElementById(flash).value = "";
    }
    
    // 选择附件
    function uploadAtta(atta) {
        win = showWindow("uploadAtta", "选择附件", "<c:url value='/attachment.do?dealMethod=uploadAttachmentList&articleAtta="+ atta +"&nodeId=f006&nodeName=附件类别'/>", 0, 0, 918, 550);
    }

    // 清空附件
    function clearAtta(atta) {
        document.getElementById(atta).value = "";
    }

    function closeChild() {
        closeWindow(win);
    }

    function fck_insertHtml(value){     
        var fck = FCKeditorAPI.GetInstance("article.textArea");
        fck.InsertHtml(value);    
    }

    function returnArticleList()  {
        var url = '<c:url value="/article.do?dealMethod=&columnId=${articleForm.columnId}&formatId=${articleForm.formatId}&operationType=article"/>'+"&"+getUrlSuffixRandom();
        parent.changeFrameUrl("rightFrame", url);
    }

    function changeValueSelect(obj){
        $("#author").val(obj.value);
    }
     function  changeValueOrgin(obj){  
         $("#infoSource").val(obj.value);    
     }
     
    function changeValue(selId){
        var obj = document.getElementById(selId);
        if(selId == "enumeration1") {
            $("#enumeration10").val(obj.value);
          }
        if(selId == "enumeration2") {
            $("#enumeration20").val(obj.value);
        }
        if(selId == "enumeration3") {
            $("#enumeration30").val(obj.value);
        }
        if(selId == "enumeration4") {
            $("#enumeration40").val(obj.value);
        }
        if(selId == "enumeration5") {
            $("#enumeration50").val(obj.value);
        }
        
    }

    function setDefault() {
        var articleId = document.getElementById("articleId").value;
        if(articleId == null || articleId == "" || articleId == "0") {
            return false;
        }
        var initUrl = document.getElementById("initUrl").value;
        $("#url").val(initUrl);
    }

    function checklenth(id, name) { 
        if($("#"+id+"").val().length > 255) {
            alert(name+"的长度大于255字节，将被截取");
            $("#"+id+"").val($("#"+id+"").val().substring(0,255));
        }
    }

    function generate_Brief(){ 
        var length = 255;
        var fck = FCKeditorAPI.GetInstance("article.textArea");
        var text = fck.GetHTML().trim();     
        if(text.length < length) return text; 
        var Foremost = text.substr(0,length); 
        var re =  /<(\/?)(BODY|SCRIPT|P|DIV|TBODY|STRONG|H1|H2|H3|H4|H5|H6|ADDRESS|PRE|TABLE|TR|TD|TH|INPUT|SELECT|TEXTAREA|OBJECT|A|UL|OL|LI|BASE|META|LINK|HR|BR|PARAM|IMG|AREA|INPUT|SPAN)[^>]*(>?)/ig
    
        Foremost = Foremost.replace(re,"");
        var Singlable = /BASE|META|LINK|HR|BR|PARAM|IMG|AREA|INPUT/i ;
        Foremost = Foremost.replace(Singlable,"");
 
        document.getElementById("brief").value = Foremost ;
        return Foremost;
        } 

    var keylis = new Array("贸易","制裁","改造","中国","经济","中国经济","拯救","美国","美国经济","整顿","银行业","银行","速度","温家宝","美中","逆差","贸易逆差","结构性","转移性","机电","进出口","商会","彩电","倾销","税率","交涉","东北","亚洲","亚洲经济","论坛","增长","失业率","失业","回落","批评","限制","纺织品","进口","加拿大","统计","移民","收入","差异","中央银行","中央","商业银行","政策","措施","经济发展","发展","日本","日本经济","复苏","决定","锻铸","铁管","管件","征收","反倾销","垄断","倾销税","联合国","联合","实施","伊拉克","石油","食品","计划","发展中国家","发达国家","国家","家电","电信","产业","电子","信息","产销","俄罗斯","检察官","检察","首富","犯罪","调查","结束","欧盟","争端","投资","受挫","改革","委员会","委员","实行","临时","价格","干预","施行","美联储","警告","通货膨胀","膨胀","下滑","衰退","季度","扬言","演示简单","后台");
    var keydrop="";
    function delHtmlTag(str) { 
       return str.replace(/<[^>]+>/g,"");//去掉所有的html标记 
    }
    function showKeyWords(){
        win = showWindow("keywords", "关键词详细信息", "<c:url value='/module/article_manager/article/showKeyWords.jsp'/>", 0, 0, 500, 320);
    }
    function getKeyText(){
        var fck = FCKeditorAPI.GetInstance("article.textArea");
        var text = fck.GetHTML().trim();
        return text;
    }
    function generate_KeyWords(a) {
        var fck = FCKeditorAPI.GetInstance("article.textArea");
        var text = fck.GetHTML().trim();
        var str = delHtmlTag(text);//获取字符串
        var keys = new Array();//词表存储序列
        var titles = new Array();
        var key = new Array;//关键词对象存储序列
        var gotkey = new Array();//关键词对象
        var name = new Array();//关键词name
        var address = new Array();//关键词在词表中位置
        var times = new Array();//关键词在本篇目中的出现次数
        var tfx = new Array();//关键词tfx值
        var stopkey = new Array();//关键词是否为停用词
        var desc = new Array();//关键词排名
        var strkey;// 声明变量预存关键字
        var strl = str.length;//获取字符串长度
        getkeywords(keys,titles);//初始化关键词表和标题序列
        getkey(str,strkey,strl,keys,key,name,address,stopkey);//获取关键词，词表位置，是否停用
        timesn(times,address);//获取n（出现多少次）
        gettfx(times,tfx);
        toobject(key,address,times,tfx,stopkey,name);
        outresult(key,address,times,tfx,stopkey,name,str);
    }
    function getkeywords(keys,titles){
        var titl = 1;
        var keyl = keylis.length;
        var keyd = keydrop.length;
        for(i=0;i<keyl;i++){
            keys[i] = keylis[i];
        }
        for(i=0;i<keyd;i++){
            keys[i+keyl] = keydrop[i].childNodes[0].nodeValue;
        }
        for(i=0;i<titl;i++){
            titles[i] = document.getElementById("textArea").innerText;
        }
    }
    function timesn(times,address){
     var k = 0;
     for(i=0;i<address.length;i++)
      {
       for(j=0;j<address.length;j++)
        { 
         if(address[i] == address[j])
         { k = k+1;}
        }
       times.push(k);
       k = 0;
      }
    }
    function gettfx(times,tfx){
     var k = Math.log(10);
     var l;
     var j;
     var m;
     var n;
     for(i=0;i<times.length;i++)
     {
      l = times[i]/1;
      j = Math.log(l);
      n = times[i]*j;
      tfx.push(n.toFixed(3));
     }
    }
    function toobject(key,address,times,tfx,stopkey,name){
     var gotdkey = new Array;
     key["name"] = name;
     key["address"] = address;
     key["tfx"] = tfx;
     key["stopkey"] = stopkey;
     key["times"] = times;
     
    }
    function getkey(str,strkey,strl,keys,key,name,address,stopkey){
     for(k=strl;k>0;k--){//控制循环次数
     
         label:
         for(j=6;j>0;j--)//通过最大关键字长度控制循环
        {
         var strkey = str.substr(k-j, j);
         
         //确定预检索字符串 strl-j 是位置 j是长度
         for(i=0;i<keys.length;i++)//通过关键字字库的数量确定循环次数
         {
          if(keys[i]==strkey){//如果现有关键字与字库匹配
           name.push(strkey);
           address.push(i);
           if(i>keylis.length){
           stopkey.push(false);
           }
           else{
           stopkey.push(true);
           }
           k-=j;
           k++;
           break label;
          }
          
         }
        } 
      }
    }
    function outresult(key,address,times,tfx,stopkey,name,str){

     var outtags = document.getElementById("keyword");
     var indesc = 5;
     var intfx = "";
     var outkeyarray = new Array();
     var outkeyarray1 = new Array();
     var outkeystoparray = new Array();
     var outwordarray = new Array();
     var outtfxarray = new Array();
     var outtfxarray1 = new Array(); 
     
     
     for(i=0;i<name.length;i++)
     {  
      if(key["stopkey"][i] == true)
      {
        outkeyarray.push(key["name"][i]);
        outtfxarray.push(key["tfx"][i]);
      }   
     }
     for(i=0;i<outkeyarray.length;i++)
     {
      for(j=outkeyarray.length;j>i;j--)
      {
       if(outkeyarray[i] == outkeyarray[j])
       {
        outkeyarray = outkeyarray.slice(0,j).concat(outkeyarray.slice(j+1,outkeyarray.length));
        outtfxarray = outtfxarray.slice(0,j).concat(outtfxarray.slice(j+1,outtfxarray.length));
       }
      }
     
      
     }
     
     
     //
     for(i=0;i<name.length;i++)
     {
      
      if(key["stopkey"][i] == false)
      {outkeystoparray.push(key["name"][i]);}
     }
     
      
     for(i=0;i<outkeyarray.length;i++)
     {
      
      if(outtfxarray[i]>intfx)
      {outwordarray.push(outkeyarray[i]);
       outtfxarray1.push(outtfxarray[i])}
     }

     for(i=0;i<outwordarray.length;i++)
     { var k,l;
      for(j=i+1;j<outwordarray.length;j++)
      {
       if(outtfxarray1[i]<outtfxarray1[j])
       { k=outtfxarray1[i];outtfxarray1[i]=outtfxarray1[j];outtfxarray1[j]=k;
        l=outwordarray[i];outwordarray[i]=outwordarray[j];outwordarray[j]=l;
       
       }
     }
     
     }
     
     outwordarray = outwordarray.slice(0,indesc)

     outtags.value = outwordarray.join(",");
    }

    function deletePic(id){
        $("#"+id).remove();
    }

    var maxPicCount = ${articleForm.maxPicCount};
    var maxMediaCount = ${articleForm.maxMediaCount};
    var maxAttachCount = ${articleForm.maxAttachCount};
    
    var picNum = ${fn:length(articleForm.picList)};
    var mediaNum = ${fn:length(articleForm.mediaList)};
    var attachNum = ${fn:length(articleForm.attachList)};
    function addPic() {
        if(picNum == 0){
            picNum = 1;
        }
        picNum += 1;
        var a = picNum-1;
        var b = "#pic" + a;
        var str = "<tr id='pic"+picNum+"'>";
        str += "<td class='td_left'>图片"+picNum+"：</td>";
        str += "<td valign='middle'>";
        str += "<input name='pic"+picNum+"' type='text' class='input_text_normal' id='article.pic"+picNum+"' style='width:470px' readonly  value=''/>";
        str += "&nbsp;&nbsp;&nbsp;<input name='button' type='button' value='上传' class='btn_samll' onclick='uploadPicture(\"article.pic"+picNum+"\")'/>";
        str += "&nbsp;&nbsp;&nbsp;<input name='button2' type='button' value='清空' class='btn_samll' onclick='clearPic(\"article.pic"+picNum+"\")'/>";
        str += "</td>";
        str += "</tr>";
        if(picNum <= maxPicCount) {
            $(b).after(str);
        }
    }
    function addMedia(){
        if(mediaNum == 0){
            mediaNum = 1;
        }
        mediaNum += 1;
        var a = mediaNum-1;
        var b = "#media" + a;
        var str = "<tr id='media"+mediaNum+"'>";
        str += "<td class='td_left'>视频"+mediaNum+"：</td>";
        str += "<td valign='middle'>";
        str += "<input name='media"+mediaNum+"' type='text' class='input_text_normal' id='article.media"+mediaNum+"' style='width:470px' readonly  value=''/>";
        str += "&nbsp;&nbsp;&nbsp;<input name='button' type='button' value='上传' class='btn_samll' onclick='uploadFlash(\"article.media"+mediaNum+"\")'/>";
        str += "&nbsp;&nbsp;&nbsp;<input name='button2' type='button' value='清空' class='btn_samll' onclick='clearFlash(\"article.media"+mediaNum+"\")'/>";
        str += "</td>";
        str += "</tr>";
        if(mediaNum <= maxMediaCount) {
            $(b).after(str);
        }
    }
    function addAttach() {
        if(attachNum == 0){
            attachNum = 1;
        }
        attachNum += 1;
        var a = attachNum-1;
        var b = "#attach" + a;
        var str = "<tr id='attach"+attachNum+"'>";
        str += "<td class='td_left'>附件"+attachNum+"：</td>";
        str += "<td valign='middle'>";
        str += "<input name='attach"+attachNum+"' type='text' class='input_text_normal' id='article.attach"+attachNum+"' style='width:470px' readonly  value=''/>";
        str += "&nbsp;&nbsp;&nbsp;<input name='button' type='button' value='上传' class='btn_samll' onclick='uploadAtta(\"article.attach"+attachNum+"\")'/>";
        str += "&nbsp;&nbsp;&nbsp;<input name='button2' type='button' value='清空' class='btn_samll' onclick='clearAtta(\"article.attach"+attachNum+"\")'/>";
        str += "</td>";
        str += "</tr>";
        if(attachNum <= maxAttachCount) {
            $(b).after(str);
        }
    }

    function autoGetArticleTextAttach(){
        var fck = FCKeditorAPI.GetInstance("article.textArea");
        var text = fck.GetHTML().trim();
        var reg = /src="([^"]*)"/g;
        while(arr=reg.exec(text)){
            alert(arr[1])
        }
    }
</script>
</head>
<body>
<form id="articleForm" name="articleForm" action="<c:url value="/article.do"/>" method="post" enctype="multipart/form-data">
     <input type="hidden" name="dealMethod" id="dealMethod" />
    <input type="hidden" name="operationType" id="operationType" value="article"/>
    <input type="hidden" name="columnId"   id="columnId"  value="${articleForm.columnId}" />
    <input type="hidden" name="formatId"   id="formatId"  value="${articleForm.formatId}" />
    <input type="hidden" name="article.id" id="articleId" value="${articleForm.article.id}" />
    <input type="hidden" name="siteId" id="siteId" value="${articleForm.siteId}" />
    <input type="hidden" name="message"    id="message"   value="${articleForm.infoMessage}" />
    <input type="hidden" id="enumInfoStr" name="enumInfoStr" value="${articleForm.enumInfoStr}"/>
    <input type="hidden" id="enumerationId" name="enumerationId" value="${articleForm.enumerationId}"/>
    <input type="hidden" id="generalSystemSetList" name="generalSystemSetList" value="${articleForm.generalSystemSetList}"/>
    <input type="hidden" id="generalSystemSetOrgin" name="generalSystemSetOrgin" value="${articleForm.generalSystemSetOrgin}"/>
    <input type="hidden" id="articleAuditedRights" name="articleAuditedRights" value="${articleForm.articleAuditedRights}"/>
    <input type="hidden" id="columnAudited" name="columnAudited" value="${articleForm.columnAudited}"/>
    <input type="hidden" name="article.initUrl" id="initUrl"  value="${articleForm.article.initUrl}" />
    <input type="hidden" name="creatorId" id="creatorId" value="${articleForm.creatorId}"/>
    <input type="hidden" name="orders"     id="orders"   value="${articleForm.orders}" />
    <input type="hidden" name="refId"      id="refId"    value="${articleForm.refId}" />
    <input type="hidden" name="isref"      id="isref"    value="${articleForm.isref}" />
    <input type="hidden" name="auditorId" id="auditorId" value="${articleForm.auditorId }"/> 
     <!-- 
        <table style="width:150px;">
            <tr>
                <td>格&nbsp;&nbsp;式： </td>
                <td>                 
                    <font color="red"> ${articleForm.formatName} </font>                 
                </td>
            </tr>
        </table>
        <hr/>
  -->
    <div id="articleDiv" class="normal">
    <ul>
        <input type="hidden" name="article.deleted" id="article.deleted" value="false" />
        <input type="hidden" name="article.audited" id="articleaudited" value="false" />
        <input type="hidden" name="article.publishState" id="articlepublished" value="0" />
        <script type="text/javascript">$("#articleTitle").focus();</script>
        <div class="form_div">
        <table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td class="td_left" width="90px;"><i>*</i>标题：</td>
            <td valign="middle">
                <input name="article.title" type="text" class="input_text_normal" tip="标题不能为空" valid="string" id="articleTitle" style="width: 747px" empty="false" value="${articleForm.article.title}" />
            </td>
        </tr>
        <tr>
            <td class="td_left">副标题：</td>
            <td valign="middle">
                <input name="article.subtitle" type="text" class="input_text_normal" id="subtitle" style="width:747px" empty="true" valid="string" value="${articleForm.article.subtitle}" />
            </td>
        </tr>
        <tr>
            <td class="td_left">引题：</td>
            <td valign="middle"><input name="article.leadingTitle"
                type="text" class="input_text_normal" id="leadingTitle"
                style="width: 747px" empty="true" valid="string"  value="${articleForm.article.leadingTitle}" /></td>
        </tr>
        <tr>
            <td class="td_left">链接地址：</td>
            <td valign="middle"><input name="article.url" type="text"
                class="input_text_normal" id="url" style="width: 600px" empty="true"
                valid="string"  value="${articleForm.article.url}" /> &nbsp;&nbsp;&nbsp;<input type="button"
                value="恢复默认" class="btn_small" onClick="setDefault()" /></td>
        </tr>
        <tr>
            <td class="td_left">信息来源：</td>
            <td valign="middle">
                <input name="article.infoSource" type="text" id="infoSource" class="input_text_normal" style="width:250px" empty="true" valid="string" value="${articleForm.article.infoSource}" />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <select name="generalSystemSetOrgin" id="yy" style="width:155px;" class="input_select_normal" onchange="changeValueOrgin(this)"></select>
            </td>
        </tr>
        <tr>
            <td class="td_left">作者：</td>
            <td valign="middle" width="600px">
                <input name="article.author" type="text" id="author" class="input_text_normal" style="width:250px;" empty="true" valid="string" value="${articleForm.article.author}" />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <select name="generalSystemSetHtml" id="generalSystemSetHtml" style="width: 155px;" class="input_select_normal" onchange="changeValueSelect(this)"></select>
            </td>
        </tr>
        <tr>
            <td class="td_left">内容：</td>
            <td>
                <textarea name="article.textArea" id="textArea" class="input_textarea_normal" cols="98" rows="10"> ${articleForm.article.textArea}</textarea>
            </td>
        </tr>
        <tr>
            <td class="td_left">&nbsp;</td>
            <td>
                <input type="button" name="keywordbutton" id="keywordbutton" value="自动提取" class="btn_small" onclick="autoGetArticleTextAttach()" />
            </td>
        </tr>
        <tr>
            <td class="td_left">关键词：</td>
            <td valign="middle"><input name="article.keyword" type="text"
                class="input_text_normal" id="keyword" style="width: 400px"
                empty="true" valid="string" onblur="checklenth('keyword', '关键词')"
                  value="${articleForm.article.keyword}" />&nbsp;&nbsp;&nbsp;<input type="button"
                name="keywordbutton" id="keywordbutton" value="自动提取"
                class="btn_small" onclick="generate_KeyWords()" />
            &nbsp;&nbsp;&nbsp;<input type="button" name="keywordbuttonInfo"
                id="keywordbuttonInfo" value="详细信息" class="btn_small"
                onclick="showKeyWords()()" /></td>
        </tr>
        <tr>
            <td class="td_left">摘要 ：</td>
            <td><input type="text" name="article.brief" cols="45"
                class="input_text_normal" rows="5" id="brief" style="width: 400px"
                empty="true" valid="string" onblur="checklenth('brief', '摘要')"
                 value="${articleForm.article.brief}"  />&nbsp;&nbsp;&nbsp;<input type="button" name="briefbutton"
                id="briefbutton" value="自动提取" class="btn_small"
                onclick="generate_Brief()" /></td>
        </tr>
        <c:if test="${articleForm.picList != null && fn:length(articleForm.picList)>0}">
            <c:forEach items="${articleForm.picList}" var="item" varStatus="i">
            <tr id="pic${i.index+1 }">
                <td class="td_left">图片${i.index+1 }：</td>
                <td valign="middle">
                    <input name="pic${i.index+1 }" type="text" class="input_text_normal" id="article.pic${i.index+1 }" style="width:470px" readonly  value="${item.path }"/>
                    &nbsp;&nbsp;<input name="button" type="button" value="上传" class="btn_samll" onclick="uploadPicture('article.pic${i.index+1 }')"/>
                    &nbsp;&nbsp;<input name="button2" type="button" value="清空" class="btn_samll" onclick="clearPic('article.pic${i.index+1 }')"/>
                    <c:if test="${i.index==0}">
                        &nbsp;&nbsp;<input class="btn_normal" type="button" value="添加" onclick="addPic()" />
                    </c:if>
                </td>
            </tr>
            </c:forEach>
        </c:if>
        <c:if test="${articleForm.picList == null || fn:length(articleForm.picList)==0}">
            <tr id="pic1">
                <td class="td_left">图片1：</td>
                <td valign="middle">
                    <input name="pic1" type="text" class="input_text_normal" id="article.pic1" style="width:470px" readonly  value=""/>
                    &nbsp;&nbsp;<input name="button" type="button" value="上传" class="btn_samll" onclick="uploadPicture('article.pic1')"/>
                    &nbsp;&nbsp;<input name="button2" type="button" value="清空" class="btn_samll" onclick="clearPic('article.pic1')"/>
                    &nbsp;&nbsp;<input class="btn_normal" type="button" value="添加" onclick="addPic()" />
                </td>
            </tr>
        </c:if>
        <tr <c:if test="${articleForm.picList == null || fn:length(articleForm.picList)==0}">style="display:none;"</c:if> id="displayPicPreview">
            <td class="td_left">&nbsp;</td>
            <td valign="middle">
            <div id="imgPreview">
                <c:forEach items="${articleForm.picList}" var="item" varStatus="i">
                    <img src="${fn:substring(item.path, 1, -1)}" width="50px" height="50px"/>&nbsp;&nbsp;
                </c:forEach>
            </div>
            </td>
        </tr>
        <c:if test="${articleForm.attachList != null && fn:length(articleForm.attachList)>0}">
            <c:forEach items="${articleForm.attachList}" var="item" varStatus="i">
            <tr id="attach${i.index+1 }">
                <td class="td_left">附件${i.index+1 }：</td>
                <td valign="middle">
                    <input name="attach${i.index+1 }" type="text" class="input_text_normal" id="article.attach${i.index+1 }" style="width:470px" readonly  value="${item.path }"/>
                    &nbsp;&nbsp;<input name="button" type="button" value="上传" class="btn_samll" onclick="uploadAtta('article.attach${i.index+1 }')"/>
                    &nbsp;&nbsp;<input name="button2" type="button" value="清空" class="btn_samll" onclick="clearAtta('article.attach${i.index+1 }')"/>
                    <c:if test="${i.index==0}">
                        &nbsp;&nbsp;<input class="btn_normal" type="button" value="添加" onclick="addAttach()" />
                    </c:if>
                </td>
            </tr>
            </c:forEach>
        </c:if>
        <c:if test="${articleForm.attachList == null || fn:length(articleForm.attachList)==0}">
            <tr id="attach1">
            <td class="td_left">附件1：</td>
            <td valign="middle">
                <input name="attach1" type="text" class="input_text_normal" id="article.attach1" readonly style="width: 470px"   value=""  /> 
                &nbsp;&nbsp;<input name="button" type="button" value="上传" class="btn_samll" onclick="uploadAtta('article.attach1')" /> 
                &nbsp;&nbsp;<input name="button2" type="button" class="btn_samll" onclick="clearAtta('article.attach1')" value="清空" />
                &nbsp;&nbsp;<input class="btn_normal" type="button" value="添加" onclick="addAttach()" />
            </td>
            </tr>
        </c:if>
        <c:if test="${articleForm.mediaList != null && fn:length(articleForm.mediaList)>0}">
            <c:forEach items="${articleForm.mediaList}" var="item" varStatus="i">
            <tr id="media${i.index+1 }">
                <td class="td_left">媒体${i.index+1 }：</td>
                <td valign="middle">
                    <input name="media${i.index+1 }" type="text" class="input_text_normal" id="article.media${i.index+1 }" style="width:470px" readonly  value="${item.path }"/>
                    &nbsp;&nbsp;<input name="button" type="button" value="上传" class="btn_samll" onclick="uploadFlash('article.media${i.index+1 }')"/>
                    &nbsp;&nbsp;<input name="button2" type="button" value="清空" class="btn_samll" onclick="clearFlash('article.media${i.index+1 }')"/>
                    <c:if test="${i.index==0}">
                        &nbsp;&nbsp;<input class="btn_normal" type="button" value="添加" onclick="addMedia()" />
                    </c:if>
                </td>
            </tr>
            </c:forEach>
        </c:if>
        <c:if test="${articleForm.mediaList == null || fn:length(articleForm.mediaList)==0}">
            <tr id="media1">
                <td class="td_left">媒体1：</td>
                <td valign="middle">
                    <input name="media1" type="text" class="input_text_normal" id="article.media1" readonly style="width: 470px"  value=""  /> 
                    &nbsp;&nbsp;<input name="button" type="button" value="上传" class="btn_samll" onclick="uploadFlash('article.media1')" /> 
                    &nbsp;&nbsp;<input name="button2" type="button" onclick="clearFlash('article.media1')" class="btn_samll" value="清空" />
                    &nbsp;&nbsp;<input class="btn_normal" type="button" value="添加" onclick="addMedia()" />
                </td>
            </tr>
        </c:if>
        <tr>
            <td class="td_left">显示时间：</td>
            <td><input type="text" empty="true" name="article.displayTime" id="article.displayTime" class="Wdate" style="width: 140px"
                onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  value="<fmt_rt:formatDate value='${articleForm.article.displayTime}' pattern='yyyy-MM-dd:HH:mm:ss' />" /> 
              发布时间： <input type="text"
                readonly="readonly" empty="true" name="article.publishTime"
                id="article.publishTime" class="Wdate" style="width: 140px"   value="<fmt_rt:formatDate value="${articleForm.article.publishTime}"  pattern="yyyy-MM-dd:HH:mm:ss" />"/>
            </td>
        </tr>
        <tr>
            <td class="td_left">
            失效时间：</td><td> <input
                type="text" empty="true" name="article.invalidTime"
                id="article.invalidTime" class="Wdate" style="width: 140px"
                onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  value="<fmt_rt:formatDate value="${articleForm.article.invalidTime}"  pattern="yyyy-MM-dd:HH:mm:ss" />" />
            </td> 
        </tr>
        <tr>
            <td class="td_left">置顶：</td>
            <td><input type="radio" name="article.toped" value="true" />是 <input
                type="radio" name="article.toped" value="false" checked="checked" />否
            </td>
        </tr>
        <tr>
            <td class="td_left">过滤关键词：</td>
            <td><input type="radio" name="article.keyFilter" value="true" />是
            <input type="radio" name="article.keyFilter" value="false"
                checked="checked" />否</td>
        </tr>
    </table>
    </div>
    <li id="btn"><input type="hidden" id="selectNameStr"
        name="selectNameStr" value="" />
    <table width="500px;" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td class="td_right" width="60px;"><input type="button"
                style="text-align: center;" class="btn_normal" value="确定"
                onclick="btn_confirm();" /></td>
            <td width="60px;"><input type="reset" class="btn_normal"
                value="重置" /></td>
            <td id="articleAuditRight" width="100px;" >
            <input type="button" class="btn_normal" value="审核并保存"
                onclick="btn_audit()" /></td>
            <td width="120px;"><input type="button" class="btn_normal"
                value="保存并继续添加" onclick="btn_saveAndAdd()" /></td>
            <td width="60px;"><input type="button" class="btn_normal"
                value="返回" onclick="returnArticleList()" /></td>
        </tr>
    </table>
    </li>
</ul>

</div>
    
</form>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/templates/headers/header.jsp"%>
<html>
<head>
<title>文档管理中的图片预览</title>
<script type="text/javascript">
	var currRowNum = 0;//定义全局变量 用于标识当前是第几张
	
	var allCount=rightFrame.getArray().length-1; //总共的个数  
	
	$(document)	.ready(//页面加载是该调用的方法，
					function() {
						var currIdAndRowNum = rightFrame.getIdAndRowNum();//调用方法，从原来的主窗体中获取
					
						var array = currIdAndRowNum.split(",");//传过来的是图片地址和编号，开始切割
						
						currRowNum = array[1];//0为地址，第二个才是编号
					
						var imgPath = '/${appName}' + rightFrame.getPicPath( array[0]);//获取图片地址
					
						var imgName=rightFrame.$("#"+rightFrame.getId(currRowNum)+"_1").html(); //图片名字
						
						//设置图片左上角的标题，为图片的名字
						top.document.getElementById("imgFileTitle").innerHTML =imgName;
						
									//显示图片
									document.getElementById("imgSrc").src=imgPath;
									if(currRowNum==1){ //第一张，隐藏上一张按钮
										document.getElementById("backImg").style.visibility="hidden";
									}
									
									if(currRowNum==rightFrame.getArray().length-1){ //最后一张隐藏，下一站按钮
										document.getElementById("nextImg").style.visibility="hidden";
									}
									//显示当前是第几页，共多少张
									document.getElementById("countId").innerHTML="第"+currRowNum+"个 共"+allCount+"个";
								});
	//点击下一张的时候所要调用的方法
	function clickNext(){
		var imgIndex=currRowNum;
		var imgName=rightFrame.$("#"+rightFrame.getId(++imgIndex)+"_1").html();//获取图片的名字
		top.document.getElementById("imgFileTitle").innerHTML =imgName; //在左上角显示图片标题

		//获取图片的路径
		var imgPath = '/${appName}' + rightFrame.getPicPath(rightFrame.getId(++currRowNum));
		//判断，如果图片为最后一张，就隐藏下一张按钮
			if(currRowNum==rightFrame.getArray().length-1)
			{
				document.getElementById("imgSrc").src=imgPath;
				document.getElementById("countId").innerHTML="第 "+allCount+" 个 共"+allCount+"个";
				document.getElementById("backImg").style.visibility ="visible";
				document.getElementById("nextImg").style.visibility="hidden";
				return;
			}
			else
			{
				document.getElementById("imgSrc").src=imgPath;
				document.getElementById("countId").innerHTML="第"+currRowNum+"个 共"+allCount+"个";
				blockImg();
			}
	}

	//上一张按钮事件
	function clickBack(){
		var imgIndex=currRowNum;
			var imgPath = '/${appName}' + rightFrame.getPicPath(rightFrame.getId(--currRowNum));//获取图片的路径
			var imgName=rightFrame.$("#"+rightFrame.getId(--imgIndex)+"_1").html();//获取图片的名字
			top.document.getElementById("imgFileTitle").innerHTML =imgName;//在左上角显示图片标题

			if(currRowNum==1){ //第一张不显示上一张按钮
				document.getElementById("imgSrc").src=imgPath;
				document.getElementById("countId").innerHTML="第 1 个 共"+allCount+"个";
				document.getElementById("nextImg").style.visibility ="visible";
				document.getElementById("backImg").style.visibility="hidden";
				return;
			}
			else
			{
				document.getElementById("imgSrc").src=imgPath;
				document.getElementById("countId").innerHTML="第"+currRowNum+"个 共"+allCount+"个";
				blockImg();
			}
	}
	
	function blockImg(){
		document.getElementById("backImg").style.visibility ="visible";
		document.getElementById("nextImg").style.visibility ="visible";
		}

	//图片缩放
	function resizeimg(ImgD,iwidth,iheight) {
	     var image=new Image();
	     image.src=ImgD.src;
	     if(image.width>0 && image.height>0){
	        if(image.width/image.height>= iwidth/iheight){
	           if(image.width>iwidth){
	               ImgD.width=iwidth;
	               ImgD.height=(image.height*iwidth)/image.width;
	           }else{
	                  ImgD.width=image.width;
	                  ImgD.height=image.height;
	                }
	               ImgD.alt="原始大小是:"+image.width+"×"+image.height;
	        }else{
	                if(image.height>iheight){
	                       ImgD.height=iheight;
	                       ImgD.width=(image.width*iheight)/image.height;
	                }else{
	                        ImgD.width=image.width;
	                        ImgD.height=image.height;
	                     }
	                ImgD.alt="原始大小是:"+image.width+"×"+image.height;
	            }
				ImgD.style.cursor= "pointer"; //改变鼠标指针
				ImgD.onclick = function() { window.open(this.src);} //点击打开大图片
				if (navigator.userAgent.toLowerCase().indexOf("ie") > -1) { //判断浏览器，如果是IE
				//　　ImgD.title = "请使用鼠标滚轮缩放图片，点击图片可在新窗口打开";
						ImgD.onmousewheel = function img_zoom() //滚轮缩放
							{
							var zoom = parseInt(this.style.zoom, 10) || 100;
							zoom += event.wheelDelta / 12;
							if (zoom> 0) this.style.zoom = zoom + "%";
							return false;
			　　　		　　 }
		　　		　  } else { //如果不是IE
						ImgD.title = "点击图片可在新窗口打开";
				   }
	   		 }
	}

</script>
</head>
<body>
<center>
<div id="show"
	style="margin: 5px; width: 940px; height: 500px; overflow: auto; text-align: center;">
<img src="" id="imgSrc"  style="vertical-align:middle" onload="javascript:resizeimg(this,900,500)"/></div>
<table>
	<tr>
		<td><font color="red"><span id="countId"></span> <span
			onclick="clickBack();" id="backImg" style="cursor: hand;">&lt;上一张</span>
		<span onclick="clickNext();" id="nextImg" style="cursor: hand">下一张
		&gt;</span></font></td>
	</tr>
</table>
</center>
</body>
</html>
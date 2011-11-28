/**
 * 颜色选择器
 * date 2009-5-6
 * author louwf
 * 在需要调用html标签 id="inputcolor"  <input type="text" value="颜色选择" onclick="coloropen(event,'inputcolor')" id="inputcolor" /> 
 */
var sb = "<div id='colorpane' style='position:absolute;z-index:999;display:none;'><iframe id='coloriframe' src='javascript:false'  style='position:absolute; visibility:inherit; top:0px; left:0px; width:400px; height:400px; z-index:-1;' filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';></iframe></div>";


//div层

document.write(sb);
var ColorHex=new Array('00','33','66','99','CC','FF') 
var SpColorHex=new Array('FF0000','00FF00','0000FF','FFFF00','00FFFF','FF00FF') 
var current=null 
var xx = "";
function initcolor(obj) 
{
	xx = obj;
	var colorTable='' ;
	for (i=0;i<2;i++) 
	{ 
	for (j=0;j<6;j++) 
	{ 
		colorTable=colorTable+'<tr height=15>' 
		colorTable=colorTable+'<td width=15 style="background-color:#000000">' 
		if (i==0){ 
			colorTable=colorTable+'<td width=15 style="cursor:pointer;background-color:#'+ColorHex[j]+ColorHex[j]+ColorHex[j]+'" onclick="doclick(this.style.backgroundColor);">'} 
		else{ 
			colorTable=colorTable+'<td width=15 style="cursor:pointer;background-color:#'+SpColorHex[j]+'" onclick="doclick(this.style.backgroundColor);">'} 
			colorTable=colorTable+'<td width=15 style="background-color:#000000">' 
			for (k=0;k<3;k++) { 
				for (l=0;l<6;l++) { 
					colorTable=colorTable+'<td width=15 style="cursor:pointer;background-color:#'+ColorHex[k+i*3]+ColorHex[l]+ColorHex[j]+'" onclick="doclick(this.style.backgroundColor);">' 
				} 
			} 
		} 
	} 
	colorTable='<table border="0" cellspacing="0" cellpadding="0" style="border:1px #000000 solid;border-bottom:none;border-collapse: collapse;width:337px;" bordercolor="000000">' 
	+'<tr height=20><td colspan=21 bgcolor=#ffffff style="font:12px tahoma;padding-left:2px;">' 
	+'<span style="float:left;color:#999999;">颜色选择器</span>' 
	+'<span style="float:right;padding-right:3px;cursor:pointer;" onclick="colorclose()">×关闭</span>' 
	+'</td></table>' 
	+'<table border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="000000" style="cursor:pointer;">' 
	+colorTable+'</table>'; 
	document.getElementById("colorpane").innerHTML=colorTable; 

	var current_x = document.getElementById(obj).offsetLeft; 
	var current_y = document.getElementById(obj).offsetTop; 
	//alert(current_x + "-" + current_y) 
	document.getElementById("colorpane").style.left = current_x + "px"; 
	document.getElementById("colorpane").style.top = current_y + "px"; 
/**	if (window.ActiveXObject) {
		document.write("<iframe id='iframe' src='javascript:false'  style='position:absolute; visibility:inherit; top:0px; left:0px; width:400px; height:400px; z-index:-1;display=none' filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';></iframe>");
	} else {		    
	}*/
} 
function doclick(obj){ 

//document.getElementById("inputcolor").value=obj;
document.getElementById(xx).value=obj;
colorclose();
} 
function colorclose(){ 
document.getElementById("colorpane").style.display = "none";
document.getElementById("coloriframe").style.display = "none"; 
//alert("ok"); 
} 
function coloropen(ee,obj){ 
	initcolor(obj);
	document.getElementById("colorpane").style.display = ""; 


} 
//window.onload = initcolor(); 
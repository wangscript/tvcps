
/**
*
*string:原始字符串
*substr:子字符串
*isIgnoreCase:忽略大小写
*/
function contains(string,substr,isIgnoreCase)
{
    if(isIgnoreCase)
    {
     string=string.toLowerCase();
     substr=substr.toLowerCase();
    }
     var startChar=substr.substring(0,1);
     var strLen=substr.length;
         for(var j=0;j<string.length-strLen+1;j++)
         {
             if(string.charAt(j)==startChar)//如果匹配起始字符,开始查找
             {
                   if(string.substring(j,j+strLen)==substr)//如果从j开始的字符与str匹配，那ok
                   {
                         return true;
                   }   
             }
         }
         return false;
}

/**
 * 去除首位空格
 * @return 字符串
 */
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 判断字符串是否为空
 * @return
 */
String.prototype.isEmpty = function() {
	return this.trim().length == 0;
}
var rightFrame;
if(parent.document.getElementById("rightFrame")){
	rightFrame =  parent.document.getElementById("rightFrame").contentWindow;
}


//新窗口的
var win;
var isIE=navigator.userAgent.toLowerCase().indexOf("msie")!=-1;
var isIE6=navigator.userAgent.toLowerCase().indexOf("msie 6.0")!=-1;
var isIE7=navigator.userAgent.toLowerCase().indexOf("msie 7.0")!=-1&&!window.XDomainRequest;
var isIE8=!!window.XDomainRequest;
var isGecko=navigator.userAgent.toLowerCase().indexOf("gecko")!=-1;
var isQuirks=document.compatMode=="BackCompat";

/**
 * Grid_js 
 * author: 杨信
 * since: 2009-06-10 22:42
 */
function do_checkAll(checkName,checkedIds) {
	var boxes = document.getElementsByName(checkName);
	var ids = "";
	for ( var i = 0; i < boxes.length; i++) {
		if (boxes[i].disabled == true) {
			continue;
		}
		if (boxes[i].checked == false) {
			boxes[i].checked = true;
			ids = ids + "," + boxes[i].value;
		} else {
			boxes[i].checked = false;
			ids = ids.replace(boxes[i].value, "");
			ids = ids.replace(",,", ",");
		}
	}
	var regExp = /(^,)|(,$)/gi;
	ids = ids.replace(regExp, "");
	document.getElementById(checkedIds).value = ids;
}

function do_onCheck(obj,checkedIds) {
	var ids = document.getElementById(checkedIds).value;
	if (obj.checked == true) {
		ids = ids + "," + obj.value;
	} else {
		ids = ids.replace(obj.value, "");
		ids = ids.replace(",,", ",");
	}
	var regExp = /(^,)|(,$)/gi;
	ids = ids.replace(regExp, "");
	document.getElementById(checkedIds).value = ids;
	
	// 调用程序员自己的函数  checkbox_onclick
	if (typeof(checkbox_onclick) == "function") {
		checkbox_onclick(obj.value); 
    }
}

function do_search(obj) {
	var formId = document.getElementById('_srch_frm').value;
	document.getElementById(formId).submit();
}

/**
 * date 2009-2-19 author louwf
 */
/**
 * dhxname 弹出框的名字 title 弹出框的标题 imagepath 弹出框使用的图片路径 url 弹出框需要加载的页面的url x
 * 弹出框x轴方向的偏移位置 y 弹出框Y轴方向的偏移位置 width 弹出框的宽度 height 弹出框的高度
 */


function showWindow(dhxname, strtitle, strurl, x, y, strwidth, strheight) {	
	var window = $.funkyUI({
		title:strtitle,
		url:strurl,	
		css:{width:strwidth,height:strheight}
	});
	return window;
}

function closeWindow(newwindow){
	var strChild = newwindow.split("###");		
	$.unfunkyUI(strChild[0],strChild[1]);		
}


/**
 * 
 * 改变图片背景
 * 
 * @param element
 *            this
 * @param img
 *            图片路径
 * @return
 */
function changeImgBg(element, imgPath) {
	element.src = imgPath;
}

function changeBgColor(ele, color) {
	var sTable = document.getElementById("grid_table");
	for (var i = 1; i<sTable.rows.length; i++) {
		sTable.rows[i].style.background = "#fff";
	}
	ele.style.background = color;
}

function showMsg(msg) {
	Ext.Msg.alert(msg);
}

function confirmQuestion(msg) {
	Ext.MessageBox.show( {
		title : '',
		msg : msg,
		buttons : Ext.MessageBox.YESNOCANCEL,
		fn : showResult,
		icon : Ext.MessageBox.QUESTION
	});
}

/**
 * 生成缩略图
 */
var flag = false;

function DrawImage(ImgD, imageWidth, imageHeight) {
	var image = new Image();
	image.src = ImgD.src;
	if (image.width > 0 && image.height > 0) {
		flag = true;
		if (image.width / image.height >= imageWidth / imageHeight) {
			if (image.width > imageWidth) {
				ImgD.width = imageWidth;
				ImgD.height = (image.height * imageWidth) / image.width;
			} else {
				ImgD.width = image.width;
				ImgD.height = image.height;
			}
		} else {
			if (image.height > imageHeight) {
				ImgD.height = imageHeight;
				ImgD.width = (image.width * imageHeight) / image.height;
			} else {
				ImgD.width = image.width;
				ImgD.height = image.height;
			}
		}
	}
}

// 成比例增减
function checkNumber(number) {
	var pot = number.toString().indexOf(".");
	if (pot > 0) {
		if (number.toString().substring(pot + 1, pot + 2) > 5
				&& number.toString().substring(pot + 1, pot + 2) > 0) {
			number = parseInt(number.toString().substring(0, pot)) + 1;
		} else {
			number = parseInt(number.toString().substring(0, pot));
		}
	}
	return number;
}

/**
 * 
 * 弹出窗口 window.open函数参数列表 window = object.open([URL ][, name ][, features ][,
 * replace]]]]) URL：新窗口的URL地址 name：新窗口的名称，可以为空
 * featurse：属性控制字符串，在此控制窗口的各种属性，属性之间以逗号隔开。 fullscreen= { yes/no/1/0 } 是否全屏，默认no
 * channelmode= { yes/no/1/0 } 是否显示频道栏，默认no toolbar= { yes/no/1/0 } 是否显示工具条，默认no
 * location= { yes/no/1/0 } 是否显示地址栏，默认no directories = { yes/no/1/0 }
 * 是否显示转向按钮，默认no status= { yes/no/1/0 } 是否显示窗口状态条，默认no menubar= { yes/no/1/0 }
 * 是否显示菜单，默认no scrollbars= { yes/no/1/0 } 是否显示滚动条，默认yes resizable= { yes/no/1/0 }
 * 是否窗口可调整大小，默认no width=number 窗口宽度（像素单位） height=number 窗口高度（像素单位） top=number
 * 窗口离屏幕顶部距离（像素单位） left=number 窗口离屏幕左边距离（像素单位）
 */

function openWindow(url, name, width, height, top, left) {
	if (width != 0 && height != 0) {
		window
				.open(
						url,
						name,
						'height='
								+ height
								+ ', width='
								+ width
								+ ', top='
								+ top
								+ ', left='
								+ left
								+ ', toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no,fullscreen=true');
	} else {
		window
				.open(
						url,
						name,
						'top='
								+ top
								+ ', left='
								+ left
								+ ', toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no,fullscreen=true');
	}

}


//定义重复提交标志变量 
var repeatSubmitFlag = false;
// 重复提交检查函数 
function checkSubmit(){
    if(repeatSubmitFlag){ // 如果标志为true，则说明页面已经提交     
        window.alert('禁止重复提交！');
        return false;
    }
    else{
        repeatSubmitFlag = true;
        return true;
    }
} 


function getWin(){
	return win;
}

function changeFrameUrl(frameId, url) {
	document.getElementById(frameId).src = url;
}


/**
 * systemDate 获取的系统时间
 * userDate   用户设置的时间
 * 如果系统时间大于用户设置的时间返回true
 */
function checkDate(userDate){
	var curDate = new Date(); 
	var curYear =curDate.getFullYear(); 
	var curMonth = curDate.getMonth()+1;
	var curDay = curDate.getDate();
	var date = curYear+"-"+curMonth+"-"+curDay; 

	var strJHRQ=date; //获得系统日期的文本值
	var strJHWCSJ=userDate; //获得用户选择的日期文本值
	var arrJHRQ=strJHRQ.split('-'); //转成成数组，分别为年，月，日，下同
	var arrJHWCSJ=strJHWCSJ.split('-');
	var dateJHRQ=new Date(parseInt(arrJHRQ[0]),parseInt(arrJHRQ[1])-1,parseInt(arrJHRQ[2]),0,0,0); //新建日期对象
	var dateJHWCSJ=new Date(parseInt(arrJHWCSJ[0]),parseInt(arrJHWCSJ[1])-1,parseInt(arrJHWCSJ[2]),0,0,0);
	//alert(dateJHWCSJ.getTime());
	//如果系统时间大于用户设置的时间返回true
	if (dateJHRQ.getTime()<dateJHWCSJ.getTime()) {
		return false;
	}else{
		return true;
	}
}


/**
 * 获取URL的参数
 * @return
 */
function $G(){
	var Url=top.window.location.href;//如果想获取框架顶部的url可以用 top.window.location.href
	var u,g,StrBack='';
	if(arguments[arguments.length-1]=="#")
	   u=Url.split("#");
	else
	   u=Url.split("?");
	if (u.length==1) g='';
	else g=u[1];
	if(g!=''){
	   gg=g.split("&");
	   var MaxI=gg.length;
	   str = arguments[0]+"=";
	   for(xm=0;xm<MaxI;xm++){
	      if(gg[xm].indexOf(str)==0) {
	        StrBack=gg[xm].replace(str,"");
	        break;
	      }
	   }
	}
	return StrBack;
}


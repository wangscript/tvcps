$(document).ready(function() {
	var validator = $("#myform1").validate({
		debug: false,       //调试模式,调试时可置为true
		errorClass: "error",
		focusInvalid: false,
		onkeyup: false,
		errorPlacement: function(error, element) {  //验证消息放置的地方
	    	error.appendTo( element.parent("span").next("span") );
	    },
	    highlight: function(element, errorClass) {  //针对验证的表单设置高亮
		    $(element).addClass(errorClass);
	    },
	    success: function(label) {  
	        label.addClass("valid").html("&hearts;") ; 
		}
	});
});

/**
 * 显示/隐藏 指定ID模块
 * @param id
 * @return
 */
function showOrHide(id){
	if(id != null && id != ""){
		var myDiv = document.getElementById(id);
		if(myDiv != undefined){
			if(myDiv.style.display == "" || myDiv.style.display == "block"){
				myDiv.style.display = "none";
			}else{
				myDiv.style.display = "block";
			}
		}
	}
}

/**
 * 分页JS
 * @param formId
 * @param action
 * @return
 */
function submitForm(formId,currPage,action){	 
	document.getElementById("currPage").value = currPage;
	var form = document.getElementById(formId);	
	form.action = action; 
	form.submit();
}
 
/**
 * 表格样式替换标签
 * @param tr
 * @return
 */
function add_event(tr){
	tr.onmouseover = function(){
		tr.className += ' hover';
	};
	tr.onmouseout = function(){
		tr.className = tr.className.replace(' hover', '');
	};
}

function stripe(table) {
	var trs = table.getElementsByTagName("tr");
	for(var i=1; i<trs.length; i++){
		var tr = trs[i];
		tr.className = i%2 != 0? 'odd' : 'even';
		add_event(tr);
	}
}
/**
 * 页面加载表格样式
 */
window.onload = function(){
	var tables = document.getElementsByTagName('table');
	for(var i=0; i<tables.length; i++){
		var table = tables[i];
		if(table.className == 'tableStyle' ){
			stripe(tables[i]);
		}
	}
}	

/**
 * 选择框全选
 */
function checkedAll(){
	var allChecked = document.getElementById("allChecked");
	var checkeds=document.getElementsByName("checkbox");
	if(allChecked.checked){
		 for(var i=0;i<checkeds.length;i++){
			 checkeds[i].checked=true;
		 }
	}else{		
	 for(var i=0;i<checkeds.length;i++){
		 checkeds[i].checked=false;		 
	 }
	}
}
 /**
  * 全选列表
  * @param statue 状态（true false）
  * @param itemName 列表项目名称
  * @return
  */
function checkedAll(statue,itemName){
	 var items = document.getElementsByName(itemName);
	 if(items != undefined){
		 for(var i=0;i<items.length;i++){
			 items[i].checked = statue;
		 }
	 }
 }  


/**
 * 取消全选
 */
function cancelCheckedAll(boxname,checkboxid){
	var allChecked = document.getElementById(checkboxid);
	var checkeds=document.getElementsByName(boxname);
	if(allChecked.checked){
		for(var i=0;i<checkeds.length;i++){
			 checkeds[i].checked=false;		 
		 }
	}
}
/**
* 选择框全选
*/
function allChecked(boxname,checkboxid){
	var allChecked = document.getElementById(checkboxid);
	var checkeds=document.getElementsByName(boxname);
	if(!allChecked.checked){
		 for(var i=0;i<checkeds.length;i++){
			 checkeds[i].checked=true;
		 }
	}
}
/**
 * 模态窗口取消
 */
function cancel(){
	window.close(); 
}

function changeFrameUrl(frameId, url) {
	top.document.getElementById(frameId).src = url;
}

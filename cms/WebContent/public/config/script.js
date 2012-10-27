var unitsWin;

/**
 * 打开设置窗口
 */
function openSetWin(id) {
	/*var features =
		'dialogWidth:'  + 2000 + 'px;' +
		'dialogHeight:' + 1000 + 'px;' +
		'dialogLeft:'   + 0 + 'px;' +
		'dialogTop:'    + 0 + 'px;'+
		'directories:yes; localtion:yes; menubar:no; status=no; toolbar=no;scrollbars:no;Resizeable=no';

	window.showModalDialog("http://www.google.com", "单元设置", features);*/
	var instanceId = document.getElementById("instanceId");
	var url = "templateUnit.do?dealMethod=getUnitForm&unitId=" + id + "&instanceId=" + instanceId;
	unitsWin = showWindow("unitsWin","单元设置",url,293, 20,800,600);
}

//------------------------图片管理---------------------------------------
var InserImage=function(){};

InserImage.GetState=function() {
	return FCK_TRISTATE_OFF; //we dont want the button to be toggled
}
InserImage.Execute=function() {	
	if (document.readyState!="complete") return ;	
	parent.win = parent.showWindow("pic","上传图片",encodeURI("/"+parent.app+"/public/common/upload_forward.jsp?action=picture.do&dealMethod=uploadPicList&nodeId=f004&nodeName=图片类别"),0, 0,935,555);	

} 

FCKCommands.RegisterCommand( 'ImageManager', InserImage ) ;
var oImagemanagerItem = new FCKToolbarButton( 'ImageManager', FCKLang.ImagemanagerBtn ) ;
oImagemanagerItem.IconPath = FCKPlugins.Items['filemanager'].Path + '/images/img.gif' ; 
FCKToolbarItems.RegisterItem( 'ImageManager', oImagemanagerItem ) ;

//-----------------------Flash管理--------------------------------------
var InserFlash=function(){};
InserFlash.GetState=function() {
	return FCK_TRISTATE_OFF; //we dont want the button to be toggled
}
InserFlash.Execute=function() {
	if (document.readyState!="complete") return ; 
	parent.win = parent.showWindow("fla","上传Flash",encodeURI("/"+parent.app+"/public/common/upload_forward.jsp?action=flash.do&dealMethod=insertFlashList&nodeId=f005&nodeName=Flash类别"),0, 0,935,555);
}

FCKCommands.RegisterCommand( 'FlashManager', InserFlash ) ;
//FCKCommands.RegisterCommand( 'Flashmanager', new FCKDialogCommand( 'Flashmanager', FCKLang.PicsmanagerDlgTitle, FCKPlugins.Items['filemanager'].Path + 'fck_placeholder.html', 340, 160 ) ) ;

var oFlashmanagerItem = new FCKToolbarButton( 'FlashManager', FCKLang.FlashmanagerBtn ) ;
oFlashmanagerItem.IconPath = FCKPlugins.Items['filemanager'].Path + '/images/flash.gif' ;
FCKToolbarItems.RegisterItem( 'FlashManager', oFlashmanagerItem ) ;

//-----------------------附件管理--------------------------------------
var InserAttach=function(){};
InserAttach.GetState=function() {
	return FCK_TRISTATE_OFF; //we dont want the button to be toggled
}
InserAttach.Execute=function() {
	parent.win = parent.showWindow("attachment","上传附件",encodeURI("/"+parent.app+"/public/common/upload_forward.jsp?action=attachment.do&dealMethod=uploadAttachmentList&nodeId=f006&nodeName=附件类别"),0, 0,918,550);
}

FCKCommands.RegisterCommand( 'AttachManager', InserAttach ) ;

var oAttachmanagerItem = new FCKToolbarButton( 'AttachManager', FCKLang.AttachmanagerBtn ) ;
oAttachmanagerItem.IconPath = FCKPlugins.Items['filemanager'].Path + '/images/attach.gif' ;
FCKToolbarItems.RegisterItem( 'AttachManager', oAttachmanagerItem ) ;

//-----------------------视频管理--------------------------------------
var InserMedia=function(){};
InserMedia.GetState=function() {
	return FCK_TRISTATE_OFF; //we dont want the button to be toggled
}
InserMedia.Execute=function() {
	var page = "../../../public/common/upload_media.jsp";
	var html = showModalDialog( page,window,"dialogWidth:750px;dialogHeight:510px;");
	if(html)
		FCK.InsertHtml(html);
}

FCKCommands.RegisterCommand( 'MediaManager', InserMedia ) ;

var oMediamanagerItem = new FCKToolbarButton( 'MediaManager', FCKLang.MediamanagerBtn ) ;
oMediamanagerItem.IconPath = FCKPlugins.Items['filemanager'].Path + '/images/media.gif' ;
FCKToolbarItems.RegisterItem( 'MediaManager', oMediamanagerItem ) ;

//-----------------------脚本管理--------------------------------------
var InserScript=function(){};
InserScript.GetState=function() {
	return FCK_TRISTATE_OFF ;
}
InserScript.Execute=function() {
	
	parent.win = parent.showWindow("js","上传脚本",encodeURI("/"+parent.app+"/public/common/upload_forward.jsp?action=js.do&dealMethod=uploadScriptList&nodeId=f006&nodeName=JS脚本类别"),0, 0,940,555);
	
//	var page = "../../../public/common/insert_script.jsp";
//	var html = showModalDialog( page,window,"dialogWidth:750px;dialogHeight:510px;");
//	if(html){
//		if(FCK.EditMode==FCK_EDITMODE_WYSIWYG){
//			FCK.InsertHtml(html);
//		}else{
//			var textRange = document.selection.createRange();
//			if(textRange.parentElement()==FCK.EditingArea.Textarea)
//				textRange.text = html;
//		}
//	}
}

FCKCommands.RegisterCommand( 'ScriptManager', InserScript ) ;

var oScriptmanagerItem = new FCKToolbarButton( 'ScriptManager', FCKLang.ScriptmanagerBtn ) ;
oScriptmanagerItem.SourceView = true;
oScriptmanagerItem.IconPath = FCKPlugins.Items['filemanager'].Path + '/images/script.gif' ;
FCKToolbarItems.RegisterItem( 'ScriptManager', oScriptmanagerItem ) ;

//-----------------------右键插入本地图片--------------------------------------


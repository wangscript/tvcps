/*
id Number 唯一的ID号  
name String 节点的文本标签 
url String 节点的Url 
leaf 是否是叶子节点
checked 是否被选中 true 或者false
title String 节点的Title 
target String 节点的target 
icon String 用做节点的图标,节点没有指定图标时使用默认值 
iconOpen String 用做节点打开的图标,节点没有指定图标时使用默认值 
open Boolean 判断节点是否打开
leaf 是否是叶子节点 true代表是，false代表否
*/
function Node(id, name, url,leaf,checked, title, target, icon, iconOpen, open) {
	this.id = id;
	this.nodeId=0;
//	this.pid = pid;	
	this.name = name;
	this.url = url;
	this.leaf = leaf;
	this.checked = checked;
	this.title = title;
	this.target = target;
	this.icon = icon;
	this.iconOpen = iconOpen;
	// inner attributes
	this._io = open || false;
	this._is = false;
	this._ls = false;//判断是否为最后一个节点
	this._hc = false;
	this._ai = 0;
	this._p;

	//  faster initialize
	this._fcp = 0;
	this._lcp = 0;
	//  for dynamic load
	this._layer = 1;
	this._completed = false;
};

// Tree object
/**变量 类型 默认值 描述 
target String true 所有节点的target 
folderLinks Boolean true 文件夹可链接 
useSelection Boolean true 节点可被选择(高亮) 
useCookies Boolean true 树可以使用cookies记住状态 
useLines Boolean true 创建带线的树 
useIcons Boolean true 创建带有图标的树 
useStatusText Boolean false 用节点名替代显示在状态栏的节点url 
closeSameLevel Boolean false 只有一个有父级的节点可以被展开,当这个函数可用时openAll() 和 closeAll() 函数将不可用 
inOrder Boolean false 如果父级节点总是添加在子级节点之前,使用这个参数可以加速菜单显示. 
*/
function dTree(objName,checkbox) {
	this.showchkboxflag	= checkbox;			// if checkbox is show是否显示checkbox
	this.checksubflag	= 1;			// check sub node
	this.config = {
		target			: null,			// default target
		folderLinks		: true,			// folder has link?
		useSelection	: true,			// can select node?
		useCookies		: true,			// seems must be true
		stepDepth		: 0,
		useLines		: true,
		useIcons		: true,
		useStatusText	: false,
		closeSameLevel	: false
	}

	this.icon = {
		root		: '/'+app+'/images/dtree/base.gif',
		folder		: '/'+app+'/images/dtree/folder.gif',
		folderOpen	: '/'+app+'/images/dtree/folderopen.gif',
		node		: '/'+app+'/images/dtree/page.gif',
		empty		: '/'+app+'/images/dtree/empty.gif',
		line		: '/'+app+'/images/dtree/line.gif',
		join		: '/'+app+'/images/dtree/join.gif',
		joinBottom	: '/'+app+'/images/dtree/joinbottom.gif',
		plus		: '/'+app+'/images/dtree/plus.gif',
		plusBottom	: '/'+app+'/images/dtree/plusbottom.gif',
		minus		: '/'+app+'/images/dtree/minus.gif',
		minusBottom	: '/'+app+'/images/dtree/minusbottom.gif',
		nlPlus		: '/'+app+'/images/dtree/nolines_plus.gif',
		nlMinus		: '/'+app+'/images/dtree/nolines_minus.gif'
	};

	this.obj = objName;
	this.aNodes = [];//存放NODE对象
	this.ids = [];//存放真实ID
	this.aIndent = [];//存放第几级树
	this.indentLayer = []; //存放这级不是最后一级的节点级别
	this.indentLayerIds = []; //存放这级不是最后一级的节点ID
	this.root = new Node(0);
	this.selectedNode = null;
	this.selectedFound = false;
};
var pNode;
// Adds a new node to the node array
//add()  向树里添加一个节点  只能在树被创建之前调用. 必须 id, name, url,leaf
dTree.prototype.add = function(id, name, url,leaf,checked, title, target, icon, iconOpen, open) {	
	this.aNodes[this.aNodes.length] = new Node(id, name, url,leaf,checked, title, target, icon, iconOpen, open);
	this.ids[this.ids.length] = id;
};

// Open/close all nodes
//打开所有节点 可在树被创建以前或以后调用. 
dTree.prototype.openAll = function() {
	this.oAll(true);
};

//关闭所有节点 可在树被创建以前或以后调用. 
dTree.prototype.closeAll = function() {
	this.oAll(false);
};

// Outputs the tree to the page
//树加载第一个执行的方法
dTree.prototype.toString = function() {
 
	if (this.config.stepDepth < 0 || (this.showchkboxflag==1 && this.checksubflag==1)) {
	    this.config.stepDepth = 0;
	}
	var str = '<div class="dtree">\n';
	if (document.getElementById) {
		if (this.config.useCookies) {		
		    this.selectedNode = this.getSelected();
		}
	 
		//加载根节点	 
		this.initialize();
		//加载根节点下的子节点
	
		str += this.addNode(pNode, 1);

	} else str += 'Browser not supported.';
	str += '</div>';

	if (!this.selectedFound) this.selectedNode = null;
	if (this.config.useCookies) this.updateCookie();

	return str;
};

// initialize all nodes before output
//初始化加载所有节点
dTree.prototype.initialize = function() {
	// get all open node id
	var aOpen = null;
	if(this.config.useCookies) {
	    aOpen = this.getCookie('co' + this.obj).split('.');
	}

	var node;

	//根据多个节点的aNodes集合循环获取每一个节点的对象
    for(var n=0; n<this.aNodes.length; n++) {	
	    node = this.aNodes[n];
		node._ai = n;
		// is selected?
		if(this.config.useSelection && !this.selectedFound && node.id == this.selectedNode) {
			node._is = true;
			this.selectedNode = n;
			this.selectedFound = true;
		}
		// target
		if (!node.target && this.config.target) node.target = this.config.target;	

		pNode = node;
		pNode._lcp = n;

		//是否是叶子节点
		if(!pNode.leaf){
			pNode._hc = true;		
		}else{
			pNode._hc = false;		
		}
		//是否是最后一个节点
		if(n == (this.aNodes.length-1)){
			pNode._ls = true;
		}else{
			pNode._ls = false;
		}
		
	}
};


// Creates the tree structure
//增加树节点
dTree.prototype.addNode = function(pNode, currentDepth) {
	var canAddChild = this.config.stepDepth == 0 || currentDepth < this.config.stepDepth;
	var str = '';
 
	var n = (pNode._fcp > 0) ? pNode._fcp : 0;
	for (; n<=pNode._lcp; n++) {
		// find pNode's children
			var cn = this.aNodes[n];
			//动态加载时使用
			cn._layer = pNode._layer ;
			cn._io = cn._io && canAddChild;		 
			if(!cn.leaf){
				cn._hc = true;			
			}	 
			// get string
			str += this.node(cn, n,false,0,true);
			// add children

			if(cn._hc) {
				str += '<div class="clip" id="d' + this.obj + n + '" style="display:' + ((cn._io) ? 'block' : 'none') + ';">';
			/*	if(canAddChild) {
					str += this.addNode(cn, currentDepth + 1);
				}*/
				str += '</div>';
			}
	 
			this.aIndent.pop();
		//	this.aIndent[this.aIndent.length] = pNode.id;
 
	 
	}
 
	pNode._completed = true;
	return str;
};


//增加一级树节点
dTree.prototype.addLevelNode = function(pNode, currentDepth,pid,bottom,status) {
	var canAddChild = this.config.stepDepth == 0 || currentDepth < this.config.stepDepth;
	var str = '';
	var n = (pNode._fcp > 0) ? pNode._fcp : 0;
 
	for (; n<=pNode._lcp; n++) {
		// find pNode's children
			var cn = this.aNodes[n];				 
			//	this.aIndent.length = this.aNodes[pid]._layer ;
			this.aIndent.length = this.aNodes[pid]._layer ;
			
		 	if(pNode._layer >= 1){
				//动态加载时使用
				cn._layer = this.aNodes[pid]._layer+1 ;			
		 	}
		 
			cn._io = cn._io && canAddChild;		
	 
			if(!cn.leaf){
				cn._hc = true;			
			}	
			
		//	bottom = cn._ls;
			//if(n == (pNode._lcp-1)){
			
			//}
			// get string
			str += this.node(cn, n,bottom,pid,status);
			// add children
		 
		 	if(cn._hc) {
				str += '<div class="clip" id="d' + this.obj + n + '" style="display:' + ((cn._io) ? 'block' : 'none') + ';">';	 
				str += '</div>';
			} 

//	this.aIndent.pop();
		//	this.aIndent[this.aIndent.length] = pNode.id;

	 
	}

	pNode._completed = true;
	return str;
};

// Creates the node icon, url and text
//创建节点的图片，url，及文本
dTree.prototype.node = function(node, nodeId,bottom,pid,status) {
	var str = '<div class="dTreeNode">' ;
//	alert("status=="+status);
 	//alert("this.aIndent.length=111111=="+this.aIndent.length);
	var temp = [];
	var bool = false;
	if(this.aIndent.length >= 1){
		if(bottom){	 
			//如果是最后一个节点
			if(this.aIndent.length > 1){	
				var yy = 0;
			
				for (var n=0; n<this.aIndent.length; n++){
					for(var m = 0 ; m < this.indentLayer.length; m++){		
					//	alert("22==="+(this.indentLayerIds[m] == this.ids[pid]));
					//	alert("this.ids[pid]="+this.ids[pid]);
					//	alert("33==="+(this.indentLayer[m] == n));
						if((this.indentLayer[m] == n) && (this.indentLayerIds[m] == this.ids[pid]) && (yy == 0)){						
							yy = 1;							
						} 
						if(this.indentLayerIds[m] == node.id){						 
							if(temp.length == 0){
								temp[temp.length] = n;									
							}else{								
								for(var i = 0 ; i < temp.length; i++){
									if(temp[i] == n){												
										bool = true;										
									}
								}
								if(bool){
									temp[temp.length] = n;
								//	yy = 3;
									bool = false;
								}
								if(!bool){
								//	yy = 2;
									bool = true;
								}	
							}
						} 						
					}	
					
					if((yy == 0) || (yy == 2)){
						str += '<img src="' +  this.icon.empty + '" alt="" />';						
					}
					if( yy == 1){
						str += '<img src="' +  this.icon.line + '" alt="" />';
						this.indentLayer[this.indentLayer.length] = n;
					//	alert(" node.id==="+node.id);
						this.indentLayerIds[this.indentLayerIds.length] = node.id;
						yy = 0;
				//		alert("this.indentLayer.length==="+this.indentLayer.length);
				//		alert("this.indentLayerIds.length==="+this.indentLayerIds.length);
					}
					 if(yy == 3){
						str += '<img src="' +  this.icon.line + '" alt="" />';
						yy = 0;
					} 
										 
				}
			}else{			
				//如果是第一级节点
				if(this.aIndent.length == 1){
					str += '<img src="' +  this.icon.empty + '" alt="" />';
				}else{
					for (var n = 0; n < this.aIndent.length; n++){
						str += '<img src="' +  this.icon.line + '" alt="" />';
					}
				}
			}
		}else{
			if(this.aIndent.length <= 2){		
				var yy = 0;			 
			 	for (var n=0; n<this.aIndent.length; n++){
			 		for(var m = 0 ; m < this.indentLayer.length; m++){	
			 			if((this.indentLayer[m] == n) && (this.indentLayerIds[m] == this.ids[pid])   && (yy == 0)){									
							yy = 1;
						}
			 			if(this.indentLayerIds[m] == node.id){						 
							if(temp.length == 0){
								temp[temp.length] = n;									
							}else{								
								for(var i = 0 ; i < temp.length; i++){
									if(temp[i] == n){												
										bool = true;										
									}
								}
								if(bool){
									temp[temp.length] = n;								
									bool = false;
								}
								if(!bool){					
									bool = true;
								}	
							}
						}
					}		 	
			 		if((yy == 1) || (n == (this.aIndent.length-1))){
						str += '<img src="' +  this.icon.line + '" alt="" />';
						this.indentLayer[this.indentLayer.length] = (this.aIndent.length-1);
						this.indentLayerIds[this.indentLayerIds.length] = node.id;	
						yy = 0;
					}else{
						str += '<img src="' +  this.icon.empty + '" alt="" />';	
					}
			 	}
			}else{
				//如果不是最后一个节点
			 	var yy = 0;
				for (var n=0; n<this.aIndent.length; n++){					   
					for(var m = 0 ; m < this.indentLayer.length; m++){									 
						if((this.indentLayer[m] == n) && (this.indentLayerIds[m] == this.ids[pid])   && (yy == 0)){									
							yy = 1;
						}
						if(this.indentLayerIds[m] == node.id){						 
							if(temp.length == 0){
								temp[temp.length] = n;									
							}else{								
								for(var i = 0 ; i < temp.length; i++){
									if(temp[i] == n){												
										bool = true;										
									}
								}
								if(bool){
									temp[temp.length] = n;								
									bool = false;
								}
								if(!bool){					
									bool = true;
								}	
							}
						}
					}
					
				/*	if(!yy){
						str += '<img src="' +  this.icon.empty + '" alt="" />';						
					}*/ 
			//		alert("yy====="+yy);
				/*	if(yy == 0){
						str += '<img src="' +  this.icon.empty + '" alt="" />';			
					}*/
					if((yy == 1) || (n == (this.aIndent.length-1))){
						str += '<img src="' +  this.icon.line + '" alt="" />';
						this.indentLayer[this.indentLayer.length] = n;
						this.indentLayerIds[this.indentLayerIds.length] = node.id;	
						yy = 0;
					}else{
						str += '<img src="' +  this.icon.empty + '" alt="" />';	
					}
				/*	if(n == (this.aIndent.length-1)){
						str += '<img src="' +  this.icon.line + '" alt="" />';		
						this.indentLayer[this.indentLayer.length] = n;
						this.indentLayerIds[this.indentLayerIds.length] = node.id;	
						y = 0;
					} */
				}	
		 	}
			
			
		}

	}	
	str += this.indent(node, nodeId);
	// icon

	if (this.config.useIcons) {
		if (node.id == "0") {
			node.icon = this.icon.root;
			node.iconOpen = this.icon.root;
		}
	
		if (!node.icon) node.icon = (!node.leaf) ? this.icon.folder : this.icon.node;
		if (!node.iconOpen) node.iconOpen = (node._hc) ? this.icon.folderOpen : this.icon.node;
        var ss = (this.showchkboxflag == 1) ? 'style="display:none"' : '';
	  
		str += '<img '+ ss +' id="i' + this.obj + nodeId + '" src="' + ((node._io) ? node.iconOpen : node.icon) + '" alt="" />';
        if(this.showchkboxflag==1) {
			str += '<input type="checkbox" name="ckx' + nodeId + '"';
			str += ' onclick="javascript: ' + this.obj + '.ck(' + nodeId + ');" >';
			node.nodeId = nodeId;
		}
	}
	// url
	if (node.url) {
		str += '<a id="s' + this.obj + nodeId + '" class="' + ((this.config.useSelection) ? ((node._is ? 'nodeSel' : 'node')) : 'node') + '"';
		if (node.title) str += ' title="' + node.title + '"';
		if (node.target) str += ' target="' + node.target + '"';
		if (this.config.useStatusText) str += ' onmouseover="window.status=\'' + node.name + '\';return true;" onmouseout="window.status=\'\';return true;" ';
		if (this.config.useSelection && ((node._hc && this.config.folderLinks) || !node._hc))
			str += ' onclick="javascript: ' + this.obj + '.s(' + nodeId + ');"';
		str += 'style="cursor: hand" ';
		str += '>';
	} else if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id) {
		str += '<a onclick="javascript: ' + this.obj + '.s(' + nodeId + ');" class="node">';
	}

	str += node.name;

	if (node.url || ((!this.config.folderLinks || !node.url) && node._hc)) str += '</a>';

	str += '</div>';
	
	return str;
};

// Adds the empty and line icons
//加载树前面的空格图片及树节点图片
dTree.prototype.indent = function(node, nodeId) {
	var str = '';
	if (this.root.id != node.id) {
		for (var n=0; n<this.aIndent.length-1000; n++){
			str += '<img src="' + ( (this.aIndent[n] == 1 && this.config.useLines) ? this.icon.line : this.icon.empty ) + '" alt="" />';
		}	
		(node._ls) ? this.aIndent.push(0) : this.aIndent.push(1);
	 
		if (node._hc) {			 
			str += '<a href="javascript: ' + this.obj + '.o(' + nodeId + ');"><img border="0" id="j' + this.obj + nodeId + '" src="'; 
			if (!this.config.useLines){
				str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;
			}else {
				str += ( (node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus ) );
			}
			str += '" alt="" /></a>';
		} else {
 
			str += '<img src="' + ( (this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join ) : this.icon.empty) + '" alt="" />';
		}
	}

	return str;
};

//get node by id
//根据节点ID返回node对象
dTree.prototype.getNodeById = function(id) {
    for(var n=0; n<this.aNodes.length; n++) {
	    var node = this.aNodes[n];
	    if(node.pid == id) {
		    return node._p;
		} else if(node.id == id) {
		    return node;
		}
	}
	return null;
}

// Returns the selected node
//返回选择的节点
dTree.prototype.getSelected = function() {
	var sn = this.getCookie('cs' + this.obj);
	return (sn) ? sn : null;
};



// Highlights the selected node
//树点击事件
dTree.prototype.s = function(id) {
 
	this.treeClick(this.aNodes[id]);

};
//树点击节点事件自定义方法
dTree.prototype.treeClick = function(node){
	var id = node.id;
    var name = node.name;
	var url = node.url;
	if (typeof(tree_onclick) == "function") {	
 		tree_onclick(node);
	}else if (url && url != "") {
		if (parent) {			
			parent.changeFrameUrl("rightFrame", url+'&nodeId='+id+'&nodeName='+name+'&'+getUrlSuffixRandom());
	 	} 
	} 	  
}
// 树节点的打开和关闭方法
dTree.prototype.o = function(id) {
	var cn = this.aNodes[id];
 
//	if(!cn._completed) return; 
	 
 	this.nodeStatus(!cn._io, id, cn._ls);
	 
	if (this.config.closeSameLevel) this.closeLevel(cn);
	if (this.config.useCookies) this.updateCookie();
};
//定义全局变量供自定义标签使用
var dtreeData;
//根据父亲节点动态ajax加载子节点
dTree.prototype.getChildren = function(pid,status, bottom){ 
	//获取自定义标签的getDtree方法，给dtreeData赋值
	
	getDTree(this.ids[pid]);
	var temp = dtreeData;
	var tt = {'node1':temp};
	var len1 = tt.node1.length;
 
	
    var str = "";
    var templength = "";
    var xx = true;
    var length2 =  this.aNodes.length;
    for(var r = 0 ; r < len1;r++){
    	for(var j = 0 ; j < this.aNodes.length; j++){
   		 if(this.aNodes[j].id == tt.node1[r].id){
   			 xx = false;
			 this.aNodes[j]._lcp = j;
			 this.aNodes[j]._fcp = j;
			 this.config.useIcons = true;
			 //状态是打开还是关闭
			 this.aNodes[j]._io = false; 
			 //加载根节点下的子节点 	 
			 /*	 
			 if(j == (this.aNodes.length - 1)){
				this.aNodes[j]._ls = true;
			 }else{
				this.aNodes[j]._ls = false;
			 }
	  */
		     str += this.addLevelNode(this.aNodes[j], 1,pid,bottom,status); 
		
			// clear aIndent
		 //	this.aIndent.length = 0;
			if (this.config.useCookies) this.updateCookie();
   		 }
   	 }
   	 if(xx){
		 this.add(tt.node1[r].id, tt.node1[r].text, tt.node1[r].url,tt.node1[r].leaf);
		 if(this.aNodes[this.aNodes.length-1].id == tt.node1[r].id){   			 
			 this.aNodes[this.aNodes.length-1]._lcp = j;
			 this.aNodes[this.aNodes.length-1]._fcp = j;
			 this.config.useIcons = true;
			 //状态是打开还是关闭
			 this.aNodes[this.aNodes.length-1]._io = false; 
			 //加载根节点下的子节点 	 
			 
			 if(r==(len1-1)){
				this.aNodes[this.aNodes.length-1]._ls = true;
			 }else{
				this.aNodes[this.aNodes.length-1]._ls = false;
			 }
	  
		     str += this.addLevelNode(this.aNodes[this.aNodes.length-1], 1,pid,bottom,status); 
		
			// clear aIndent
		// 	this.aIndent.length = 0;
			if (this.config.useCookies) this.updateCookie();
   		 }
		 xx = true;
   	 }
    }
 /*   var xx = true;
	    for(var i = length2;i < (len1 + length2);i++){  
		//   this.aNodes[tt.node1[i].id] = new Node(tt.node1[i].id, tt.node1[i].text, tt.node1[i].url,tt.node1[i].leaf);
	    	 
				 this.aNodes[i]._lcp = i;
				 this.aNodes[i]._fcp = i;
				 this.config.useIcons = true;
				 //状态是打开还是关闭
				 this.aNodes[i]._io = false; 
				 //加载根节点下的子节点 	 
				 
				 if(i == (len1 + length2) - 1){
					this.aNodes[i]._ls = true;
				 }else{
					this.aNodes[i]._ls = false;
				 }
		  
			     str += this.addLevelNode(this.aNodes[i], 1,pid,bottom,status); 
			
				// clear aIndent
			 	this.aIndent.length = 0;
				if (this.config.useCookies) this.updateCookie();
			
	    	// }
	   }
*/
    eDiv = document.getElementById('d' + this.obj + pid);
	eDiv.innerHTML = str;
	eJoin = document.getElementById('j' + this.obj + pid);

 	eJoin.src = (this.config.useLines)?
	((status)?((bottom)?this.icon.minusBottom:this.icon.minus):((bottom)?this.icon.plusBottom:this.icon.plus)):
	((status)?this.icon.nlMinus:this.icon.nlPlus);	 
 
 	eDiv.style.display = (status) ? 'block': 'none';	
    this.aNodes[pid]._io = status;

   
 
};

// Change the status of a node(open or closed)
//改变节点的状态，将状态改成打开或关闭状态
dTree.prototype.nodeStatus = function(status, id, bottom) {
    // if children is not shown before, show this time
 /**   if(!this.aNodes[id]._completed) {
//	    this._dynamicLoad(id);
	
	}*/

	// 	this.getChildren(id);
	eDiv	= document.getElementById('d' + this.obj + id);
	eJoin	= document.getElementById('j' + this.obj + id);
	if (this.config.useIcons) {
		eIcon	= document.getElementById('i' + this.obj + id);	
    	eIcon.src = (status) ? this.aNodes[id].iconOpen : this.aNodes[id].icon;
	}
 
 	eJoin.src = (this.config.useLines)?
	((status)?((bottom)?this.icon.minusBottom:this.icon.minus):((bottom)?this.icon.plusBottom:this.icon.plus)):
	((status)?this.icon.nlMinus:this.icon.nlPlus);

//	alert("eDiv.style.display ==="+eDiv.style.display);
//	eDiv.style.display = (status) ? 'block': 'none';
 
 
	this.getChildren(id,status, bottom);
	 
	//alert("status==="+status);
	this.aNodes[id]._io = status;
};

//选择节点checkbox触发的事件
dTree.prototype.ck = function(id) {
	var node = this.aNodes[id];
	var checkflag = document.all('ckx' + id).checked;
    if(this.checksubflag == 1) {
		for (var n=node._fcp; n<node._lcp; n++) {
		//	if (this.aNodes[n].pid == node.id ) {
				var obj = document.all('ckx' + this.aNodes[n].nodeId);
				obj.checked = checkflag;
				this.ck(this.aNodes[n].nodeId);
		//	}
		}
	}


//增加获取选中的节点的方法
 	 var checkedIds = "";
	 var checkedTexts = "";
	 for(var i = 0; i < checkflag.length; i++){
		 if (checkflag[i].id != 0) {
			checkedIds = checkedIds + "," + checkflag[i].id;
			checkedTexts = checkedTexts + "," + checkflag[i].name;
		 }
	 } 
	 var regExp = /^,/gi;
	 document.getElementById("tree_checkedIds").value = checkedIds.replace(regExp, "");
	 document.getElementById("tree_checkedTexts").value = checkedTexts.replace(regExp, "");
	 if (typeof(tree_oncheck) == "function") {
		  tree_oncheck(node);
	 }


};

//return selected text
//返回选择的文本内容
dTree.prototype.getSelectText = function(str) {
	var ss = "";
	for(var i=0; i<this.aNodes.length; i++) {
		var objnode = this.aNodes[i];
		if(objnode.nodeId != 0) {
			var objchx = document.all('ckx' + objnode.nodeId);
			if(objchx.checked) ss += objnode.name + str;
		}
	}
	if(ss!="") ss = ss.substr(0, ss.length-1);
	return ss;
};

//return selected id
//返回选择的ID
dTree.prototype.getSelectId = function(str) {
	var ss = "";
	for(var i=0; i<this.aNodes.length; i++) {
		var objnode = this.aNodes[i];
		if(objnode.nodeId!=0) {
			var objchx = document.all('ckx' + objnode.nodeId);
			if(objchx.checked) ss += objnode.id + str;
		}
	}
	if(ss!="") ss = ss.substr(0, ss.length-1);
	return ss;
};

// Open or close all nodes
//打开或者关闭所有节点
dTree.prototype.oAll = function(status) {
	for (var n=0; n<this.aNodes.length; n++) {
		var node = this.aNodes[n];
		if (node._hc && node.pid != this.root.id && node._completed) {
			this.nodeStatus(status, n, node._ls)
		}
	}

	if (this.config.useCookies) this.updateCookie();
};

// Opens the tree to a specific node
//根据某个节点打开
//只能在树被创建以后调用.. 
/*
*id Number 节点唯一的ID号 
*select Boolean 判断节点是否被选择 
*/
dTree.prototype.openTo = function(id, bSelect) {
    if(this.root.id == id) return;
    var cn = this.getNodeById(id);
	if(!cn) return;
	// if parent not complete, complete parent
	if(!cn._p._completed) {
	    this._dynamicLoad(cn._p._ai);
	}
	if(bSelect) {
	    this.s(cn._ai);
	}
	pNode = cn._p;
	while(pNode && pNode._p && pNode.pid != this.root.id) {
		this.nodeStatus(true, pNode._ai, pNode._ls);
		if (this.config.closeSameLevel) this.closeLevel(pNode);
		pNode = pNode._p;
	}

	if (this.config.useCookies) this.updateCookie();
};

// Closes all nodes on the same level as certain node
dTree.prototype.closeLevel = function(node) {
	 pNode = node._p;
	for (var n=pNode._fcp; n<=pNode._lcp; n++) {
		if (this.aNodes[n].pid == node.pid && this.aNodes[n].id != node.id && this.aNodes[n]._io) {
			this.nodeStatus(false, n, this.aNodes[n]._ls);
			//this.closeAllChildren(this.aNodes[n]);
		}
	}
};

// Closes all children of a node
dTree.prototype.closeAllChildren = function(node) {
	for (var n=node._fcp; n<=node._lcp; n++) {
		if (this.aNodes[n]._hc && this.aNodes[n]._completed) {
			if (this.aNodes[n]._io) {
				this.nodeStatus(false, n, this.aNodes[n]._ls);
			}
			this.closeAllChildren(this.aNodes[n]);
		}
	}
};



// [Cookie] Clears a cookie
dTree.prototype.clearCookie = function() {
	var now = new Date();
	var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);
	this.setCookie('co'+this.obj, 'cookieValue', yesterday);
	this.setCookie('cs'+this.obj, 'cookieValue', yesterday);
};

// [Cookie] Sets value in a cookie
dTree.prototype.setCookie = function(cookieName, cookieValue, expires, path, domain, secure) {
	document.cookie =
		escape(cookieName) + '=' + escape(cookieValue)
		+ (expires ? '; expires=' + expires.toGMTString() : '')
		+ (path ? '; path=' + path : '')
		+ (domain ? '; domain=' + domain : '')
		+ (secure ? '; secure' : '');
};

// [Cookie] Gets a value from a cookie
dTree.prototype.getCookie = function(cookieName) {
	var cookieValue = '';
	var posName = document.cookie.indexOf(escape(cookieName) + '=');
	if (posName != -1) {
		var posValue = posName + (escape(cookieName) + '=').length;
		var endPos = document.cookie.indexOf(';', posValue);
		if (endPos != -1) cookieValue = unescape(document.cookie.substring(posValue, endPos));
		else cookieValue = unescape(document.cookie.substring(posValue));
	}

	return (cookieValue);
};

// [Cookie] Returns ids of open nodes as a string
dTree.prototype.updateCookie = function() {
	var str = '';
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n]._io && this.aNodes[n].pid != this.root.id) {
			if (str) str += '.';
			str += this.aNodes[n].id;
		}
	}

	this.setCookie('co' + this.obj, str);
};

// [Cookie] Checks if a node id is in a cookie
dTree.prototype.isOpen = function(id) {
	var aOpen = this.getCookie('co' + this.obj).split('.');
	for (var n=0; n<aOpen.length; n++)
		if (aOpen[n] == id) return true;

	return false;
};

// If Push and pop is not implemented by the browser
if (!Array.prototype.push) {
	Array.prototype.push = function array_push() {
		for(var i=0;i<arguments.length;i++)
			this[this.length]=arguments[i];
		return this.length;
	}
};

if (!Array.prototype.pop) {
	Array.prototype.pop = function array_pop() {
		lastElement = this[this.length-1];
		this.length = Math.max(this.length-1,0);
		return lastElement;
	}
};

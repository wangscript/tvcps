Ext.onReady(function() {
	var root = new Ext.tree.AsyncTreeNode({
		id : '0',
		text : '工商局 '
	});
    var tree = new Ext.tree.TreePanel({
    	id : 'tree_Panel',
		root : root,
		renderTo : 'tree-div',
		split : true,
		border : false,
		collapsible : true,
		autoScroll : true,
		width : 240,
		minSize : 140,
		maxSize : 240,
		loader : new Ext.tree.TreeLoader({
			url : '/ccms-server/main/get_treedata.jsp?id=0'
		})
	});
	function treeClick(node, event) {
		alert(event);
		if (node.isLeaf()) {
			event.stopEvent();
			var url = node.attributes.url;
			if (url && url != "") {
				parent.dhxLayout.cells("c").attachURL(url);
			}
		} else {
			node.expand();
		}
	};
	function treeBeforeLoad(node) {
		var tree = Ext.getCmp('tree_Panel');
		var treeId = node.id;
		alert(treeId);
		if (treeId != '0') {
			var url = '/ccms-server/main/get_treedata.jsp?id=' + treeId;
			alert(url);
			tree.loader.url = url;
		}
		
	}
	tree.on('click', treeClick);
	tree.on('beforeload', treeBeforeLoad);
});
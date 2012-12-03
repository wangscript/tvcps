 
var pager;
//totalRecordCount总记录数
//pageSize每页条数
var Pager = function(totalRecordCount, pageSize) {    
	pager = this;
    var handler;
    this.pageSize = pageSize;
    this.currentPage = 1;
    this.totalRecordCount = totalRecordCount;
	this.totalPageCount = this.totalRecordCount % pageSize != 0 ? Math.floor(this.totalRecordCount / pageSize) + 1 : Math.floor(this.totalRecordCount / pageSize);
    var ele;
	/**
	this.getBeginPageNum = function() {
		if (this.pageSize > this.totalPageCount) {
			return 1;
		}
		if (this.currentPage > this.totalPageCount) {
			return this.totalPageCount - this.pageSize + 1;
		}else {
			return Math.floor((this.currentPage - 1) / this.pageSize) * this.pageSize + 1;
		}    
	};
	this.getEndPageNum = function() {
		var x = Math.floor((this.currentPage - 1) / this.pageSize) * this.pageSize + this.pageSize;
		if (this.pageSize > this.totalPageCount || this.currentPage > this.totalPageCount || x > this.totalPageCount) {
			return this.totalPageCount;
		}
		return Number(x);
	};*/
	/**
	 * currentPage当前 页
	 * 
	 */
	this.init = function(htmlElementName, handlerMethod,currentPage,id,unitId,dir, appName,siteId) {

		ele = htmlElementName;
		handler = handlerMethod;
		//var beginPageNum = this.getBeginPageNum();
		 
			
		//var endPageNum = this.getEndPageNum();
 
		var str = "";
 
		handler(currentPage,id,unitId,dir, appName,siteId);	
	/**	if (this.totalRecordCount != -1) {
			// 生成分页HTML字符串
			if (this.currentPage > this.pageSize) {
				str = " <span onclick='"+'pager.changePage("' + (beginPageNum - 1) + '" ,"'+realPage+'","'+id+'","'+unitId+'","'+dir+'", "'+appName+'","'+siteId+'")'+"' style=\"cursor:pointer;\"> << </span> ";
			//	str = " <span onclick='pager.changePage('" + (beginPageNum - 1) + "','"+realPage+"','"+page+"','"+id+"','"+unitId+"','"+dir+"', "+appName+","+siteId+")' style=\"cursor:pointer;\"> << </span>  ";
			}
			for (var i = beginPageNum; i <= endPageNum; i++) {
				if (i == this.currentPage) {
					str = str + " <span style='color:red;'>" + i.toString() + "</span> ";
				}else {
					str = str + " <a href='#'  onclick='"+'pager.changePage("' + i + '" ,"'+realPage+'","'+id+'","'+unitId+'","'+dir+'", "'+appName+'","'+siteId+'")'+"' style=\"cursor:pointer;\">" + i.toString() + "</a> ";
				}
			}
			if (this.totalPageCount > endPageNum) {
				var nextPageBeginNum = endPageNum + 1 < this.totalPageCount ? endPageNum + 1 : this.totalPageCount;
				 
				str = str + " <span onclick='"+'pager.changePage("' + nextPageBeginNum + '" ,"'+realPage+'","'+id+'","'+unitId+'","'+dir+'", "'+appName+'","'+siteId+'")'+"' style=\"cursor:pointer;\"> >> </span> ";
			//	str = str + " <span onclick='pager.changePage('" + nextPageBeginNum + "','"+realPage+"','"+page+"','"+id+"','"+unitId+"','"+dir+"', "+appName+","+siteId+")' style=\"cursor:pointer;\"> >> </span>  ";
			}
		}*/

		var currentPage = currentPage || 0;
	
		if(currentPage == 0){
			str = "";
		}else if(currentPage >= 1){
			str = str + " <a href='#'  onclick='"+'pager.changePage("1" ,"'+id+'","'+unitId+'","'+dir+'", "'+appName+'","'+siteId+'")'+"' style=\"cursor:pointer;\">首页</a>&nbsp;&nbsp;";
		}

		if(currentPage == 0){
			str = str + "";
		}else if(currentPage > 1){ 
			str = str + " <a href='#'  onclick='"+'pager.changePage("'+(Number(currentPage)-1)+'" ,"'+id+'","'+unitId+'","'+dir+'", "'+appName+'","'+siteId+'")'+"' style=\"cursor:pointer;\">上一页</a>&nbsp;&nbsp;";
		}
		if(currentPage == (this.totalPageCount)){
			str = str + "";
		}else{
			str = str + "<a href='#'  onclick='"+'pager.changePage("'+(Number(currentPage)+1)+'" ,"'+id+'","'+unitId+'","'+dir+'", "'+appName+'","'+siteId+'")'+"' style=\"cursor:pointer;\">下一页</a>&nbsp;&nbsp;";
		}
		//if(currentPage == (this.totalPageCount)){
		//	str = str + "";
		//}else{
			str = str + " <a href='#'  onclick='"+'pager.changePage("'+(this.totalPageCount)+'" ,"'+id+'","'+unitId+'","'+dir+'", "'+appName+'","'+siteId+'")'+"' style=\"cursor:pointer;\">尾页</a>&nbsp;";
		//}
		str = str + "&nbsp;&nbsp;" + currentPage+"/"+this.totalPageCount;
 
		document.getElementById(ele).innerHTML = "";
		document.getElementById(ele).innerHTML = str;
	 
	};
	this.changePage = function(currentPage,id,unitId,dir, appName,siteId) {
		this.currentPage = currentPage;		 
		this.init(ele, handler,currentPage,id,unitId,dir, appName,siteId);
	};
	this.changeTotalRecordCount = function(totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
		this.init(ele, handler);
	};
}
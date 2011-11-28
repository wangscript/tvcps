/*
******生成js分页脚******
****没剑(2008-03-05)****
修改日期：2008-3-12
添加两个参数：displaynum，displaylastNum可以自由定制显示的页码数量

参数：  pagesize:10  //每页显示的页码数
        ,count:0                //数据条数
        ,css:"mj_pagefoot"      //分页脚css样式类
        ,current:1              //当前页码
		,displaynum:7			//中间显示页码数
		,displaylastNum:5		//最后显示的页码数
        ,previous:"上一页"      //上一页显示样式
        ,next:"下一页"          //下一页显示样式
        ,paging:null            //分页事件触发时callback函数
        
使用：
	$("div").pagefoot({
	    pagesize:10,
	    count:500,
	    css:"mj_pagefoot",
	    previous:"<",
	    next:">",
	    paging:function(page){
			    alert("当前第"+page+"页");
		    }
	});
	以上代码为所有div加上分页脚代码
*/
jQuery.pagefoot = 
{
    //生成分页脚
    create:function(_this,s){
        var pageCount = 0;
        //计算总页码
        pageCount = (s.count/s.pagesize<=0)?1:(parseInt(s.count/s.pagesize)+((s.count%s.pagesize>0)?1:0));
        s.current = (s.current>pageCount)?pageCount:s.current;
        //循环生成页码
        var strPage = "";
        //创建上一页
        if(s.current <= 1){
            strPage += "<span class=\"disabled\">"+s.previous+"</span>";
        }else{
            strPage += "<a href=\""+(s.current-1)+"\">"+s.previous+"</a>";
        }
		//开始的页码
        var startP = 1;
		if(pageCount <= s.allPage){
			for(i = startP; i <= pageCount; i++){
				if(s.current == i){
					strPage += "<span class=\"current\">"+i+"</span>";
				}else{
					strPage += "<a href=\""+i+"\">"+i+"</a>";
				}
			}
		}else{
			var pCount = s.allPage-s.displaylastNum;
			// 页码数太多时，则隐藏多余的页码 (没有显示中间的内容，只是前段和后段)
			if(s.current < pCount || s.current > (pageCount-s.displaylastNum+1)){
				startP = s.current;
				for(j = 1; j <= pCount; j++){
					if(s.current == j){
						strPage += "<span class=\"current\">"+j+"</span>";
					}else{
						strPage += "<a href=\""+j+"\">"+j+"</a>";
					}
				}
				strPage += "...";
				for(i = (pageCount-s.displaylastNum+1); i <= pageCount; i++){
					if(s.current == i){
						strPage += "<span class=\"current\">"+i+"</span>";
					}else{
						strPage += "<a href=\""+i+"\">"+i+"</a>";
					}
				}
			} else {
				startP = s.current;
				// 页码左右显示最大页码数
				var anyMore = parseInt(s.displaynum/2);
				var endP = (s.current+anyMore)>pageCount?pageCount:(s.current+anyMore);
				// 前段显示
				var start = s.allPage - s.displaynum - s.displaylastNum;
				for(j = 1; j <= start; j++){
					if(s.current == j){
						strPage += "<span class=\"current\">"+j+"</span>";
					}else{
						strPage += "<a href=\""+j+"\">"+j+"</a>";
					}
				}
				strPage += "...";
				// 结束段在最后显示范围内
				if(endP >= pageCount-s.displaylastNum){
					// 中间段显示
					if(s.current == pageCount-s.displaylastNum+1){
						for(i = (s.current-4); i <= pageCount; i++){
							if(s.current == i){
								strPage += "<span class=\"current\">"+i+"</span>";
							}else{
								strPage += "<a href=\""+i+"\">"+i+"</a>";
							}
						}
					}else{
						for(i = (s.current-(endP-(pageCount-s.displaylastNum))-1); i <= pageCount; i++){
							if(s.current == i){
								strPage += "<span class=\"current\">"+i+"</span>";
							}else{
								strPage += "<a href=\""+i+"\">"+i+"</a>";
							}
						}
					}
				}else{
					for(i = (s.current-1); i <= s.current+anyMore; i++){
						if(s.current == i){
							strPage += "<span class=\"current\">"+i+"</span>";
						}else{
							strPage += "<a href=\""+i+"\">"+i+"</a>";
						}
					}
					strPage += "...";
					for(i = pageCount-s.displaylastNum+1; i <= pageCount; i++){
						if(s.current == i){
							strPage += "<span class=\"current\">"+i+"</span>";
						}else{
							strPage += "<a href=\""+i+"\">"+i+"</a>";
						}
					}
				}
			}
		}
       //隐藏只有一页时的页码
		if(s.current==1&&pageCount==1){
			strPage="";
		}

        //创建下一页
        if(s.current >= pageCount){
            strPage += "<span class=\"disabled\">"+s.next+"</span>";
        }else{
            strPage += "<a href=\""+(s.current+1)+"\">"+s.next+"</a>";
        }
        $(_this).empty().append(strPage).find("a").click(function(){
            //得到翻页的页码
            var ln=this.href.lastIndexOf("/");
            var href=this.href;
            var page=parseInt(href.substring(ln+1,href.length));
            s.current=page; 
            //外部取消翻页时...
            if(!$.pagefoot.paging(page,s.paging)) {
				return false;
            }
            $.pagefoot.create(_this,s);
            return false;
        });
        return this;
    },
    paging:function(page,callback){
        if(callback){
            if(callback(page)==false) {
				return false;
			}
        }
        return true;
    }
}

jQuery.fn.pagefoot= function(opt)
{
	/*参数定义*/
    var setting = {
		pagesize:10
		,count:0                //数据条数
        ,css:"mj_pagefoot"      //分页脚css样式类
        ,current:1              //当前页码
		,displaynum:4   		//中间显示页码数
		,displaylastNum:4		//最后显示的页码数
		,allPage:10
        ,previous:"上一页"      //上一页显示样式
        ,next:"下一页"          //下一页显示样式
        ,paging:null            //分页事件触发时callback函数
	};
	opt = opt || {};
	$.extend(setting, opt);
    return this.each(function(){
        $(this).addClass(setting.css);
        $.pagefoot.create(this,setting);
    });
}
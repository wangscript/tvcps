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

/**
 * 获取url后缀的随机尾数
 * 如：...ac.jsp   -> ...ac.jsp...&rs=1092
 * @return rs=1091，不带"&"
 */
function getUrlSuffixRandom() {
	return 'rs=' + Math.round(Math.random()*10000);
}

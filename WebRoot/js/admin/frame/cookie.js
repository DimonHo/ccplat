function setCookie(name,value)
{
expires = new Date();
expires.setTime(expires.getTime() + (1000 * 86400 * 365));
document.cookie = name + "=" + escape(value) + "; expires=" + expires.toGMTString() + "; path=/";
}

// 获取 Cookie
function getCookie(name)
{
cookie_name = name + "=";
cookie_length = document.cookie.length;
cookie_begin = 0;
while (cookie_begin < cookie_length)
{
value_begin = cookie_begin + cookie_name.length;
if (document.cookie.substring(cookie_begin, value_begin) == cookie_name)
{
var value_end = document.cookie.indexOf ( ";", value_begin);
if (value_end == -1)
{
value_end = cookie_length;
}
return unescape(document.cookie.substring(value_begin, value_end));
}
cookie_begin = document.cookie.indexOf ( " ", cookie_begin) + 1;
if (cookie_begin == 0)
{
break;
}
}
return null;
}

// 清除 Cookie
function delCookie ( name )
{
var expireNow = new Date();
document.cookie = name + "=" + "; expires=Thu, 01-Jan-70 00:00:01 GMT" + "; path=/";
} 
function initPage(){
	var functions=getCookie("functions_ccplat");
		array=functions.split(",");
		for(var j=0;j<pagefunction.length;j++){
			for(var i=0;i<array.length;i++){
				if('"'+pagefunction[j]+'"'==array[i]){
					
					$("[productsid='"+pagefunction[j]+"']:first").show();
					
							break;
					}
				}
		}
}

/**
 * 获取所有登陆账号
 * @return
 */
function acountList(){
	var allCookies = document.cookie;
	//将多cookie切割为多个名/值对
	var arrCookie=allCookies.split("; ");
	var arrAcount = new Array();//用了存放账号的数组
	//遍历cookie数组，处理每个cookie对
	for(var i=0,j=0;i<arrCookie.length;i++){
		var arr=arrCookie[i].split("=");
		//找到存放账号的cookie，将它添加到arrAcount中
		if(-1 != arr[0].indexOf("_ccloginAcount")){
			arrAcount[j] = arr[1];
			j++;
		}
	} 
	//将账号格式转换为 { id: acount, text: acount }
	for(var i=0;i<arrAcount.length;i++){
		var obj = { id:arrAcount[i],text:arrAcount[i] };
		arrAcount[i] = obj;
	}
	return arrAcount;
}

/**
 * 将新的账号存放到cookie中
 * @param acount
 * @return
 */
function saveAcount(acount){
	var name = "_ccloginAcount"+acount;
	var value = acount;
	var _acount = getCookie(name);
	//若不存在，则将新账号添加到cookie中
	if(_acount == null){
		setCookie(name,value);
	}
}
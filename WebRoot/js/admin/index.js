//添加中部菜单点击事件
$(".big-link").click(function(e){
	var url=e.currentTarget.getAttribute("url");
	showPage(url);
});
//加载对话框中页面
function showPage(url){
	$("#frame").attr("src",url);
}
//设置样式
var h=document.documentElement.clientHeight;
$("#Bigcont").css("height",h-96);
$(".nav01").css("height",h-135);

//当浏览器窗口大小改变时，设置显示内容的宽度  
window.onresize=function(){
    if(document.body.clientWidth<1349){
		$(".indexclass").css("width","1366px");
		$(".indexclass").css("height","585px");
	}
} 
if(document.body.clientWidth<1349){
	$(".indexclass").css("width","1366px");
	$("#Bigcont").css("height","535px");
	$(".nav01").css("height","495px");
}
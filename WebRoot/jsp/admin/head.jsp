<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--头部-->
<div id="Headcont">
	<div class="logo">
	</div>
	<ul class="detail">
		<li class="w100">
			<span class="de01"></span>
		</li>
		<li class="w100">
			<span class="de02"></span>
		</li>
		<li class="w100">
			<span class="de03"></span>
		</li>
		<li class="w100">
			<span class="de04"></span>
		</li>
		<li class="w175">
			<img src="images/index/man.png" width="45" height="45" class="man" />
			<a href="#" class="nametxt">${session_key_user.realname }</a>
			<span class="de05"></span>
		</li>
		<li class="w175">
			<span class="de06"></span>
		</li>
		<li class="w100">
			<a href="login/exit.action">
			<span class="de07"></span>
			</a>
		</li>
	</ul>
	<div class="clr">
	</div>
</div>
<script type="text/javascript" src="js/ui/scripts/jquery-1.6.2.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){

	var date = new Date();
	var year = date.getFullYear();
	var month = eval(date.getMonth()+1);
	if(month < 10){
		month="0"+month;
	}
	var day = date.getDate();
 	var time =year+'年'+month+'月'+day+'日'+ '&nbsp;' +' 星期'+'日一二三四五六'.charAt(date.getDay())+'&nbsp;';
	$(".de06").html(time);
});

$(".bglist").height($(".nav").height()+35);

</script>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'v.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="union/css/styles.css" />
<script type="text/javascript" src="union/js/jquery.js"></script>
<script type="text/javascript" src="union/js/sliding_effect.js"></script>
<script src="union/js/bootstrap.js"></script>
<SCRIPT type="text/javascript">
	function tip() {
		var volum=document.getElementById("volum");
		var nav=document.getElementById("v_0");
		volum.innerHTML= nav.style.display=="none"
					?"<span style='text-align:right; color:#00DDDD'>展&nbsp;开</span>"
				:"<span style='text-align:right; color:#00DDDD'>收&nbsp;缩</span>";
	}
	function tipout(){
		var volum=document.getElementById("volum");
		volum.innerText="目  录";
	}
	
	function shrinkexpend() {
		for(var i=0;i<7;i++){
			var nav=document.getElementById("v_"+i);
			nav.style.display=nav.style.display=="block"?"none":"block";
		}
		tip();
	}
	
</SCRIPT>

</head>

<body >
	<div id="navigation-block" >
		<ul id="sliding-navigation" onselectstart="return false;">
			<li class="sliding-element" onclick="shrinkexpend()">
			<h3 onmouseout="tipout()" onmouseover="tip()" id="volum" title="">目 录</h3>
			</li>
			<li class="sliding-element" id="v_0"><a href="#0">研究背景</a></li>
			<li class="sliding-element" id="v_1"><a href="#1">意义应用</a></li>
			<li class="sliding-element" id="v_2"><a href="#2">当前现状</a></li>
			<li class="sliding-element" id="v_3"><a href="#3">开发架构</a></li>
			<li class="sliding-element" id="v_4"><a href="#4">参考文献</a></li>
			<li class="sliding-element" id="v_5"><a href="#5">准备工作</a></li>
			<li class="sliding-element" id="v_6"><a href="#6">项目流程</a></li>
		</ul>
	</div>
</body>
</html>

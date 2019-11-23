<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'v_intro.jsp' starting page</title>

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
	<SCRIPT type="text/javascript">
	function tip() {
		var volum=document.getElementById("volum");
		var nav=document.getElementById("v_0");
		volum.innerHTML= nav.style.display=="none"
					?"<span style='text-align:right; color:#00DDDD'>展&nbsp;&nbsp;开</span>"
				:"<span style='text-align:right; color:#00DDDD'>收&nbsp;&nbsp;缩</span>";
	}
	function tipout(){
		var volum=document.getElementById("volum");
		volum.innerText="团队人员";
	}
	function shrinkexpend() {
		for(var i=0;i<6;i++){
			var nav=document.getElementById("v_"+i);
			nav.style.display=nav.style.display=="block"?"none":"block";
		}
		tip();
	} 
</SCRIPT>
  </head>
  
  <body>
    <div id="navigation-block" >
		<ul id="sliding-navigation" onselectstart="return false;" >
			<li class="sliding-element" onclick="shrinkexpend()">
				<h3 id="volum" onmouseover="tip()" onmouseout="tipout()" title="">团队人员</h3>
			</li>
			<li class="sliding-element" id="v_0"><a href="#0">吉根林（老&nbsp;&nbsp;&nbsp;师）</a></li>
			<li class="sliding-element" id="v_1"><a href="#1">余&nbsp;&nbsp;&nbsp;聪（可视化）</a></li>
			<li class="sliding-element" id="v_2"><a href="#2">魏&nbsp;&nbsp;&nbsp;铭（算&nbsp;&nbsp;&nbsp;法）</a></li>
			<li class="sliding-element" id="v_3"><a href="#3">史覃覃（算&nbsp;&nbsp;&nbsp;法）</a></li>
			<li class="sliding-element" id="v_4"><a href="#4">薛文满（算&nbsp;&nbsp;&nbsp;法）</a></li>
			<li class="sliding-element" id="v_5"><a href="#5">王思聪（算&nbsp;&nbsp;&nbsp;法）</a></li>
		</ul>
	</div>
  </body>
</html>

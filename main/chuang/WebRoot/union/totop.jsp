<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'totop.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		#btn {width:40px; height:40px; position:fixed; right:20%; bottom:10px; display:none; background:url(union/image/top_bg.png) no-repeat left top; }
		#btn:hover {background:url(union/image/top_bg.png) no-repeat 0 -39px;}
		
	</style>
  </head>
  
  <body>
   <script >
	function pageScroll() {
		window.scrollBy(0, -300);
		scrolldelay = setTimeout('pageScroll()', 10);
		if(window.scrollX==0&&window.scrollY==0)
			clearTimeout(scrolldelay);
	}
	window.onscroll=function () {
		var btn=document.getElementById("btn");
		if(window.scrollY<=500)
			btn.style.display="none";
		else
			btn.style.display="block";
	}
   </script>
   <a href="" onclick="pageScroll();return false" id="btn" title="回到顶部"></a>
  
    
  </body>
</html>

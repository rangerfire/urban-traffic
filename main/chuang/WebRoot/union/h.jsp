<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="union/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="union/js/h_menu.js"></script>
<link rel="stylesheet" type="text/css" href="union/css/h_styles.css" />
</head>

<body>

<div class="navbar" >
	<ul id="mobanwang_com" onselectstart="return false;" style="padding-left:37%;" class="first-menu">
		
		<li><a class="menu" href="index.jsp"
				style="color:#ff0; background:none; border:none;" target="_self"><span style="font-family:'华文行楷'">大创-南师大</span></a>
		</li>
		
		<li>
			<a class="menu" href="index.jsp" target="_self"><strong> 首    页</strong> </a>
		</li>
		<li>
			<a class="menu" href="javascript:;"> <strong>成    果</strong> </a>
			<ul id="subNews" class="second-menu">
				<li>
					<a class="menu" href="hotspot.jsp" class="mobanwang" target="_self"><strong>热点地区</strong></a>
				</li>
				<li>
					<a class="menu" href="trafjam.jsp" class="mobanwang" target="_self"><strong>拥堵模式</strong></a>
				</li>

			</ul>
		</li>
		<li>
			<a class="menu" href="javascript:;" class="mobanwang" target="_self"><strong>其    他</strong></a>
			<ul id="subNews" class="second-menu">
				<li>
					<a class="menu" href="intro.jsp" class="mobanwang" target="_self"><strong>团队介绍</strong></a>			
				</li>
				<li>
					<a class="menu" 
					href="contact.jsp"
					class="mobanwang" target="_self"><strong>联系我们</strong></a>
				</li>
			</ul>
	</ul>
</div>	

</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>团队人员介绍</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	<link rel="stylesheet" type="text/css" href="/union/css/intro.css">
	-->
<style>
body {
	font-family: Verdana;
	font-size: 14px;
	margin: 0;
}

#container {
	margin-left: 26%;
	width: 900px;
	
}

#menu {
	height: 30px;
	text-align:center;
	background-color: #444444 ;
	margin-bottom: 5px;
	border-radius:6px;
}

#menu span{
	font-family:"微软雅黑","华文行楷";
	font-size:+19px;
}

#mainContent {
	height: 300px;
	margin-bottom: 5px;
}

#sidebar {
	float: right;
	width: 200px;
	border-radius:6px;
}
#sidebar img{
	width:200px;
	height:300px;
	border-radius:6px;
}
.cover{
	float: left;
	width: 695px;
	height: 300px;
	background: #AAAAAA;
	font-family:"微软雅黑";
	line-height:22px;
	color:white;
} 
#content {
	float: left;
	width: 695px;
	height: 300px;
	background: #444444;
	font-family:"微软雅黑";
	line-height:22px;
	color:white;
	border-radius:6px;
} /*因为是固定宽度，采用左右浮动方法可有效避免ie 3像素bug*/
.hl{
	color:#77DDFF;
}

#footer {
	font-family:"微软雅黑";
	line-height:22px;
	color:white;
	height: 60px;
	background: #444444;
	border-radius:6px;
}
.fover{
	font-family:"微软雅黑";
	line-height:22px;
	color:white;
	height: 60px;
	background: #AAAAAA;
}
</style>



</head>

<body style="background: url(union/image/gray.jpg)">
	<jsp:include page="union/h.jsp"></jsp:include>
	<jsp:include page="union/v_intro.jsp"></jsp:include>
	<br>
	<br>
	<br>
	<br>
	
	<h1 align="center"
		style="font-family:华文行楷 ; font-size:47px;color: black; ">
		<strong>团队人员介绍</strong>
		
	</h1>
	
	<br><br><br><br>
	<jsp:include page="union/totop.jsp"></jsp:include>
	<!-- 团队人员 -->
	<span id="0" style="height:100px;"> </span>
	<!-- styles设置为无链接效果 即下划线与颜色 -->
	
	<div id="container" name="container">
	<a target="_blank" style='text-decoration:none;' href="http://baike.baidu.com/link?url=nWk14irB4LCGW_B0LPkM7yeHats69m42tehVbsr5gDBTHqgspJoLIa0JvogIwbgwWRFnKztuSiE0Cub-q-JPiK">
		<div id="menu" name="menu"><span class="hl">&nbsp;吉根林（指导老师）</span></div>
		<div id="mainContent">
			<div id="sidebar" name="sidebar"><img alt="" src="union/image/ji.jpg"></div>
			<div id="content" name="content">
				<br>
				&nbsp;&nbsp;博士，教授，博士生导师，南京师范大学计算机科学与技术学院院长。<br>
				&nbsp;&nbsp;江苏省“青蓝工程”中青年学术带头人。<br> 
   				&nbsp;&nbsp;主要研究方向为数据挖掘技术。<br>
   				&nbsp;&nbsp;主持完成了2项国家自然科学基金项目，正在主持1项国家自然科学基金项目。<br>
   				&nbsp;&nbsp;在权威核心期刊发表学术论文90多篇，SCI/EI收录20多篇。<br>
   				&nbsp;&nbsp;至今指导博士硕士研究生40多名，1篇硕士论文获江苏省优秀硕士论文；<br>
   				&nbsp;&nbsp;指导本科毕业设计，2篇本科毕业论文获得江苏省优秀毕业论文；<br>
   				&nbsp;&nbsp;指导学生获得江苏省大学生实践创新训练项目重点项目；<br>
   				&nbsp;&nbsp;指导学生获得学校“英才培养计划”项目；主持学校教改重中之重项目；<br>
   				&nbsp;&nbsp;编写出版教材6部，其中1部被评为国家“十一五”、“十二五”规划教材；<br>
   				&nbsp;&nbsp;获得江苏省教学成果二等奖，校教书育人奖，校教学名师称号。<br>
   				<br>
			</div>
		</div>
		<div id="footer" name="footer">
			<br>
			&nbsp;&nbsp;<span class="hl">个人签名：</span> 两个月后还是一条好汉。
		</div>
		</a>
	</div>
	
	<span id="1" style="height:100px;"> </span>
	<br><br>
	<br><br>
	 
	<div id="container" name="container">
	<a href="http://my.oschina.net/moyuit" style='text-decoration:none;' target="_blank">
		<div id="menu" name="menu"><span class="hl">&nbsp;余&nbsp;&nbsp;聪（可视化设计）</span></div>
		<div id="mainContent">
			<div id="sidebar" name="sidebar"><img alt="" src="union/image/yu.jpg"></div>
			<div id="content" name="content">
				<br>
				&nbsp;&nbsp;<span class="hl">个人简介</span><br>
				&nbsp;&nbsp;&nbsp;&nbsp;男，普通的本科大学生一枚。<br>
				&nbsp;&nbsp;&nbsp;&nbsp;家乡：江西省南昌市安义 <br> 
   				&nbsp;&nbsp;&nbsp;&nbsp;对编程具有极大的兴趣。<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;曾获“甲骨文Java竞赛”华东地区二等奖。<br>
   				&nbsp;&nbsp;<span class="hl">联系方式</span><br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Q&nbsp;Q&nbsp;&nbsp;&nbsp;： 492899414<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Email&nbsp;： cong25825933@qq.com<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Phone： 15651828732<br>
   				&nbsp;&nbsp;<span class="hl">主要贡献</span><br>
   				&nbsp;&nbsp;&nbsp;&nbsp;完成可视化工作及相关业务逻辑。<br>
   				<br>
			</div>
		</div>
		<div id="footer" name="footer">
			<br>
			&nbsp;&nbsp;<span class="hl">个人签名：</span> ( •̀∀•́ ) &nbsp;I Like Developing.
		</div>
		</a>
	</div>
	
	<span id="2" style="height:100px;"> </span>
	<br><br>
	<br><br> 
	
	<div id="container" name="container">
	<a href="http://user.qzone.qq.com/1044186880" style='text-decoration:none;' target="_blank">
		<div id="menu" name="menu" ><span class="hl">&nbsp;魏&nbsp;&nbsp;铭（算法设计）</span></div>
		<div id="mainContent">
			<div id="sidebar" name="sidebar"><img alt="weiming" src="union/image/wei.jpg"></div>
			<div id="content" name="content">
				<br>
				&nbsp;&nbsp;<span class="hl">个人简介</span><br>
				&nbsp;&nbsp;&nbsp;&nbsp;女，普通的本科大学生一枚。<br>
				&nbsp;&nbsp;&nbsp;&nbsp;家乡：江苏省 <br> 
   				&nbsp;&nbsp;&nbsp;&nbsp;热爱学习，热爱新知识。<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;成绩优异，办事用心。<br>
   				&nbsp;&nbsp;<span class="hl">联系方式</span><br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Q&nbsp;Q&nbsp;&nbsp;&nbsp;： 1044186880<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Email&nbsp;： 15651885365@163.com<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Phone： 15651885365<br>
   				&nbsp;&nbsp;<span class="hl">主要贡献</span><br>
   				&nbsp;&nbsp;&nbsp;&nbsp;完成算法工作及把握团队进度。<br>
   				<br>
			</div>
		</div>
		<div id="footer" name="footer">
			<br>
			&nbsp;&nbsp;<span class="hl">个人签名：</span> Monsters University
		</div>
		</a>
	</div>
	
	<span id="3" style="height:100px;"> </span>
	<br><br>
	<br><br> 
	
	<div id="container" name="container">
	<a href="http://user.qzone.qq.com/1509620113" style='text-decoration:none;' target="_blank">
		<div id="menu" name="menu"><span class="hl">&nbsp;史覃覃（算法设计）</span></div>
		<div id="mainContent">
			<div id="sidebar" name="sidebar"><img alt="" src="union/image/shi.jpg"></div>
			<div id="content" name="content">
				<br>
				&nbsp;&nbsp;<span class="hl">个人简介</span><br>
				&nbsp;&nbsp;&nbsp;&nbsp;女，普通的本科大学生一枚。<br>
				&nbsp;&nbsp;&nbsp;&nbsp;家乡：江苏省 <br> 
   				&nbsp;&nbsp;&nbsp;&nbsp;成绩优异，办事周全。<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;曾获“财付通杯”程序设计大赛特等奖。<br>
   				&nbsp;&nbsp;<span class="hl">联系方式</span><br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Q&nbsp;Q&nbsp;&nbsp;&nbsp;： 1509620113<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Email&nbsp;： 15605195782@163.com<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Phone： 15605195782<br>
   				&nbsp;&nbsp;<span class="hl">主要贡献</span><br>
   				&nbsp;&nbsp;&nbsp;&nbsp;把握项目进程，文案设计，查阅文献资料，算法设计。<br>
   				<br>
			</div>
		</div>
		<div id="footer" name="footer">
			<br>
			&nbsp;&nbsp;<span class="hl">个人签名：</span> Get busy dying or get busy living.
		</div>
		</a>
	</div>
	
	<span id="4" style="height:100px;"> </span>
	<br><br>
	<br><br> 
	
	<div id="container" name="container">
	<a href="http://user.qzone.qq.com/710341464" style='text-decoration:none;' target="_blank">
		<div id="menu" name="menu"><span class="hl">&nbsp;薛文满（算法设计）</span></div>
		<div id="mainContent">
			<div id="sidebar" name="sidebar"><img alt="" src="union/image/xue.jpg"></div>
			<div id="content" name="content">
				<br>
				&nbsp;&nbsp;<span class="hl">个人简介</span><br>
				&nbsp;&nbsp;&nbsp;&nbsp;男，普通的本科大学生一枚。<br>
				&nbsp;&nbsp;&nbsp;&nbsp;家乡：江苏省 <br> 
   				&nbsp;&nbsp;&nbsp;&nbsp;对编程有兴趣。<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;参加数学建模大赛。<br>
   				&nbsp;&nbsp;<span class="hl">联系方式</span><br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Q&nbsp;Q&nbsp;&nbsp;&nbsp;： 710341464<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Email&nbsp;： 710341464@qq.com<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Phone： 15651885662<br>
   				&nbsp;&nbsp;<span class="hl">主要贡献</span><br>
   				&nbsp;&nbsp;&nbsp;&nbsp;完成算法工作。<br>
   				<br>
			</div>
		</div>
		<div id="footer" name="footer">
			<br>
			&nbsp;&nbsp;<span class="hl">个人签名：</span> Don't believe me just watch.
		</div>
		</a>
	</div>
	
	<span id="5" style="height:100px;"> </span>
	<br><br>
	<br><br>
	
	<div id="container" name="container">
	<a target="_blank" style='text-decoration:none;' href="http://baike.baidu.com/link?url=4B_1z2SDxd1HsMMGCNZ6UK1xI5jTlVt8Hi1edef4Gw4OgMyk0E_WM-pQtub23YtuqNeFp965cJN8o5a_hIs9slT-cBAOprKCKk-f1PLUGY_">
		<div id="menu" name="menu"><span class="hl">&nbsp;王思聪（算法设计）</span></div>
		<div id="mainContent">
			<div id="sidebar" name="sidebar"><img alt="" src="union/image/wang.jpg"></div>
			<div id="content" name="content">
				<br>
				&nbsp;&nbsp;<span class="hl">个人简介</span><br>
				&nbsp;&nbsp;&nbsp;&nbsp;男，普通的本科大学生一枚。<br>
				&nbsp;&nbsp;&nbsp;&nbsp;家乡：江苏省 <br> 
   				&nbsp;&nbsp;&nbsp;&nbsp;对编程有兴趣。<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;曾参加甲骨文Java竞赛。<br>
   				&nbsp;&nbsp;<span class="hl">联系方式</span><br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Q&nbsp;Q&nbsp;&nbsp;&nbsp;： 420554242<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Email&nbsp;： 420554242@qq.com<br>
   				&nbsp;&nbsp;&nbsp;&nbsp;Phone： 15651885816<br>
   				&nbsp;&nbsp;<span class="hl">主要贡献</span><br>
   				&nbsp;&nbsp;&nbsp;&nbsp;完成算法设计。<br>
   				<br>
			</div>
		</div>
		<div id="footer" name="footer">
			<br>
			&nbsp;&nbsp;<span class="hl">个人签名：</span> 有钱任性。
		</div>
		</a>
	</div>
	
	<br>
	<br>
	<br>
	
	<SCRIPT type="text/javascript">
	window.onload=Init;
	function Init(){
		var menus=document.getElementsByName("menu");
		var contents=document.getElementsByName("content");
		var sidebars=document.getElementsByName("sidebar");
		var footers=document.getElementsByName("footer");
		for(var i=0;i<6;i++)
		{
			var nav=document.getElementById("v_"+i);
			setOverOut(menus[i],contents[i],sidebars[i],footers[i],nav);
			setOverOut(contents[i],menus[i],sidebars[i],footers[i],nav);
			setOverOut(sidebars[i],contents[i],menus[i],footers[i],nav);
			setOverOut(footers[i],contents[i],menus[i],sidebars[i],nav);
		}
	}
	
	function setOverOut(thisOne,other,another,thr,nav){
		setNavOverOut(nav,thisOne,other,another);
		var outHTML=nav.innerHTML;
		var overHTML="<a style='color:yellow;'>"+nav.innerText+"</a>";
		thisOne.onmouseover=function(){
			this.style.backgroundColor="black";
			other.style.backgroundColor="black";
			another.style.backgroundColor="black";
			thr.style.backgroundColor="black";
			nav.innerHTML=overHTML;
		}
		thisOne.onmouseout=function(){
			this.style.backgroundColor="#444444 ";
			other.style.backgroundColor="#444444 ";
			another.style.backgroundColor="#444444 ";
			thr.style.backgroundColor="#444444";
			nav.innerHTML=outHTML;
		}
	}
	function setNavOverOut(thisNav,one,two,thr)
	{
		thisNav.onmouseover=function(){
			one.style.background="black";
			two.style.background="black";
			thr.style.background="black";
		}
		thisNav.onmouseout=function(){
			one.style.background="#444444";
			two.style.background="#444444 ";
			thr.style.background="#444444 ";
		}
	}
</SCRIPT>
	
</body>
<jsp:include page="union/footer.jsp"></jsp:include>
</html>

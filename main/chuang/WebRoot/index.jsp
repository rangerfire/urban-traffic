<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>利用车辆轨迹数据挖掘城市交通模式</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="union/css/index.css">
<style>
u:hover{
    color:#C63300;
}
</style>

</head>

<body background="union/image/gray.jpg">
	<a id="top"></a>
	<jsp:include page="union/h.jsp"></jsp:include>

	<jsp:include page="union/v_main.jsp"></jsp:include>
	<br>
	<br>
	<br>
	<br>
	<h1 align="center"
		style="font-family:华文行楷 ; font-size:47px;color: black; ">
		<strong>利用车辆轨迹数据挖掘城市交通模式</strong>
	</h1>
	<br>
	<br>
	<br>
	<ol type="1" style="line-height:25px;">
	
		<li>
		<br>
		<br>
		<!-- span中有个空格 -->
		<span id="0" style="height:100px"> </span> 		
		<a  class="text" style="font-family:楷体 ;"><strong>研究背景</strong></a>
			<br> <br>
			<p class="content" style="font-family:微软雅黑 ; line-height:40px">
				近年来，无线通信、
				<a target=_blank href="https://www.baidu.com/link?url=RKhGq3ETFXaHX3lUQBEYpR_uANMtyBsqYOPQjY13rvP2OahHuNuPgtaufd8ImAiNt2KzmvUOx4mal0neiIQ4F_&wd=&eqid=ebf930e30003bac300000003559e9111&qid=ebf930e30003bac3&bdlkc=1">
				<u>GPS定位</u></a>
				等移动感知技术的飞速发展，使得全球范围内的各种移动对象的运动信息均可以得到有效的记录，因此产生了越来越多的具有时空特征的轨迹数据。从大量用户的轨迹数据集合中可分析出一个区域内人们的生活模式和社会规律，如发现热点地区、经典旅行线路和交通状况等。移动对象不断变化的空间位置数据蕴含着大量的信息，进而出现了许多基于位置的服务计算，例如智能交通系统中交通流量的实时监控与调度。
				随着城市化进程的加快和机动车数量的快速增加，各种交通方式在为人们出行带来便捷的同时也带来了交通事故、交通拥挤等诸多负面的影响。在这种情况下，智能交通系统作为利用先进技术来解决交通问题的重要手段已经得到人们的认可和重视。基于车辆轨迹数据的挖掘不但能够提供路网交通状态的时变数据，能够提供及时准确的分析出城市交通的运行模式，发现城市热点区域，而且有助于合理地缓解城市交通拥堵，提高交通管理水平。
			</p></li>
		<li><br> <br> 
		<span id="1" style="height:100px"> </span> 
		<a class="text"
			style="font-family:楷体 ;"><strong>意义应用</strong></a> <br> <br>
			<p class="content" style="font-family:微软雅黑 ; line-height:40px">
				时空轨迹挖掘是数据挖掘领域的前沿研究课题，通过研究和开发时空轨迹挖掘技术，来发现隐藏在轨迹大数据中有价值的规律和知识以供决策支持。
				在道路交通模式分析方面的应用是车辆轨迹数据应用较为广泛的一个方面。车辆行驶轨迹是驾驶人员主观意愿和道路客观约束条件综合作用的结果,
				从海量车辆轨迹中可以挖掘出道路的实时交通信息，不仅可以为道路可行性研究提供决策支持，还可以为交委提供统计决策依据和进行交通诱导，为智能交通服务。
				交通模式的预测，是智能交通系统研究的一个重要问题。ITS（Intelligent Transport System智能交通系统
				）中先进的交通控制系统和交通管理系统不仅要求有实时的道路检测数据，还要求获得实时，可靠的预测信息，以实现交通流的动态诱导，并且为用户提供有效的出行参考信息。交通数据采集系统长时间通过人工采集和自动采集，积累了大量车辆轨迹数据，利用数据挖掘技术，构建预测模型，可以有效的利用这些实时的数据，对交通模式进行分析，对未来时间的交通流状况进行预测，为智能交通系统中的控制系统服务，也为决策系统和诱导系统等提供数据。因此，对于车辆轨迹数据进行分析挖掘有着重要的现实意义。

			</p></li>
		<li><br> <br>
		
		<span id="2" style="height:100px"> </span> 
		<a class="text"
			style="font-family:楷体 ;"><strong>当前现状</strong></a> <br> <br>
			<p class="content" style="font-family:微软雅黑 ; line-height:40px">国外著名人士X.Li
				提出了基于密集度的挖掘算法FlowScan,
				并以美国旧金山海湾道路为实验区发掘出“繁忙路段”等特征。J.W.Chang等提出了基于时空距离STDist
				的运动轨迹相似度算法，而忽略了运动物体本身对运动轨迹相似度的影响。国内也有一些研究者对移动轨迹挖掘进行研究，主要集中在对移动数据的频繁度、根据历史轨迹数据对交通流量进行预测等方面以及增量挖掘的移动模式挖掘和浮动车数据等方面。本课题主要研究利用车辆轨迹数据来挖掘城市的交通模式。
			</p></li>
		<li><br> <br>
		<span id="3" style="height:100px"> </span>
		 <a class="text"
			style="font-family:楷体 ;"><strong>开发架构</strong></a> <br> <br>
			<p class="content" style="font-family:微软雅黑 ; line-height:25px">
				<img src="union/image/pic.jpg"></img>
			</p></li>
		<li><br> <br>
		<span id="4" style="height:100px"> </span>  
		<a class="text"
			style="font-family:楷体 ;"><strong>参考文献</strong></a> <br> <br>
			<p class="content" style="font-family:微软雅黑 ; line-height:40px">
				[1]<a target=_blank href="http://baike.baidu.com/link?url=gQ_4b9DEHOvQNSnIlhjafGlGc5Rf4JVWc1Sf1zqf0NXyWN1BQnHgZJsCk9XLnGjiICHzdwQ2GSuiztrk8b5Cma">
				<u>吉根林</u></a>,赵斌.时空轨迹大数据模式挖掘研究进展[J].Journal of Data Acquisition and
				Processing Vol.30,No.1,Jan.2015,pp.47-58.<br> [2]
				郭旦怀,崔伟宏.面向实时交通信息提取的车辆轨迹数据挖掘[J].武汉理工大学学报(交通科学与工程版),2012,34(1):6-9.<br>
				[3]王少华,卢浩,黄骞,曹嘉.智慧交通系统关键技术研究[J].测绘与空间地理信息,2013(8):88-91.<br>
				[4]王文焕,面向车辆轨迹分析的数据挖掘算法研究[D].山东科技大学,2013.<br>
				[5]齐观德,潘纲,李石坚,张大庆.当出租车轨迹挖掘遇见智能交通[J].中国计算机学会通讯,2013,9(8):30-37<br>
				[6]徐甲平.面向智能交通系统的空间数据挖掘技术研究[D].同济大学软件学院,2007.<br>
			</p></li>
		<li><br> <br>
		<span id="5" style="height:100px"> </span> 
		<a class="text"
			style="font-family:楷体 ;"><strong>准备工作</strong></a> <br> <br>
			<p class="content" style="font-family:微软雅黑 ; line-height:40px">
				1、利用学校提供的图书资源和数字资源收集和查阅了大量相关文献，对车辆轨迹数据的挖掘算法和处理技术都有了深刻了解，对课题的研究方向甚感兴趣，具备开展研究的基础。<br>
				2、在指导老师的建议下，有目的性的参加一些讲座或者网上课堂去学习一些轨迹数据挖掘算法，加深对数据挖掘算法的认识与了解。<br>
				3、团队就项目实验内容进行多次请教与沟通，在指导老师的指导下，制定初步的研究方案，详细见“组织实施”。<br>
				4、团队内部进行多次讨论，对项目进行模块化分析，根据个人所擅长之处，明确各个组成部分和分工情况，以及各个阶段进行的内容，形成统筹的解决思路。<br>
			</p></li>
		<li><br> <br>
		<span id="6" style="height:100px"> </span> 
		<a class="text"
			style="font-family:楷体 ;"><strong>项目流程</strong></a> <br> <br>
			<p class="content" style="font-family:微软雅黑 ; line-height:40px">
				阶段一：2015-6-1~2015-6-30<br>
				阅读文献，并收集到所需出租车时空轨迹数据，确定挖掘交通模式的具体实施方案。<br>
				阶段二：2015-7-1~2015-8-31<br> 学习相关算法和数据处理方法，设计交通热点地区、交通堵塞模式的挖掘算法。<br>
				阶段三：2015-9-1-~2015-9-30<br> 熟练掌握编程工具和数据分析工具，编程实现算法。<br>
				阶段四：2015-10-1-~2015-12-30<br> 系统测试，并实现交通状况的可视化。<br>
				阶段五：2015-1-1-~2016-3-30<br> 对算法做一定地改进，提高系统性能。<br>
				阶段六：2016-4-1-~2016-6<br> 项目总结，编写研发报告。<br>
			</p></li>
	</ol>
	<br>
	<br>
	<br>
	
	<script type="text/javascript">
	window.onload=InitAll();
	
	function InitAll(){
		var contents=document.getElementsByTagName("p");
		for(var i=0;i<7;i++)
		{
			var nav=document.getElementById("v_"+i);
			HighLight(nav,contents[i]);
		}
	}
	
	function HighLight(thisNav,thisCont){
		thisNav.onmouseover=function(){
			thisCont.className="highlight1";
		}
		thisNav.onmouseout=function(){
			thisCont.className="content";
		}
	}
</script>
	<jsp:include page="union/totop.jsp"></jsp:include>
</body>
<jsp:include page="union/footer.jsp"></jsp:include>
</html>

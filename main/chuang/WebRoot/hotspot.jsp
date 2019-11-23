<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>热点地区</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<style>
    rect.bordered {
        stroke: #E6E6E6;
        stroke-width: 2px;
    }
	label {
	font-family: 幼圆
	}
    text.mono {
        font-size: 9pt;
        font-family: Consolas, courier;
        fill: #aaa;
    }

    text.axis-workweek {
        fill: #000;
    }

    text.axis-worktime {
        fill: #000;
    }
    html{height:100%}
    body{height:100%;margin:0px;padding:0px}
    #show0{width:900px;height:600px;margin:auto;border-radius:12px;}
    #footer0{width:900px;height:150px;margin:auto; margin-top:-20px;
    	border-radius:12px;
    }
    #timeline0 {
        width: 900px;
        height: 130px;
        margin: auto;
        margin-top: -30px;
    }
    .anchorBL {
        display: none;
    }
</style>
 
<!--在线导入百度地图API-->  
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=YLa8sZyeFYuvoauXNg0T4hRA"></script>

<!--区域固定需要导入的js文件-->
<script type="text/javascript" src="union/js/AreaRestriction_min.js"></script>
<!-- d3.js:Day/Hour Heatmap-->
<script type="text/javascript" src="union/js/d3.v3.js"></script>
<link href="union/css/timestyle.css" rel="stylesheet" type="text/css" />
<!--时间轴-->
<script type="text/javascript" src="union/js/jquery1.10.2.js"></script>
<!-- heatmap.js-->
<script type="text/javascript" src="union/js/HeatMap_min.js"></script>
<script type="text/javascript">
    $(function () {
    //首页大事记
       $('.course_nr2 li').hover(function () {
         $(this).find('.shiji').slideDown(600);
       }, function () {
          $(this).find('.shiji').slideUp(400);
        });
    });
    
    
</script>
</head>
  
  <body style="background:url(union/image/gray.jpg)">
    <jsp:include page="/union/h.jsp"></jsp:include>
    <jsp:include page="/union/totop.jsp"></jsp:include>
    <br><br><br>
	<h1 align="center"
		style="font-family:华文行楷 ; font-size:47px;color: black; ">
		<strong>热点地区</strong>
	</h1>
	<br><br><br><br>
	<!--容器有长宽,居中-->
<div id="show0"  onselectstart="return false;"></div>
<div id="timeline0" style="background:white">
    <div class="clearfix course_nr">
        <ul class="course_nr2">
				<li class="timeli">
					<span id="time_0">0</span>
					<div class="shiji" name="timeline">
						<h1>0-1</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_1">1</span>
					<div class="shiji" name="timeline">
						<h1>1-2</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_2">2</span>
					<div class="shiji" name="timeline">
						<h1>2-3</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_3">3</span>
					<div class="shiji" name="timeline">
						<h1>3-4</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_4">4</span>
					<div class="shiji" name="timeline">
						<h1>4-5</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id='time_5'>5</span>
					<div class="shiji" name="timeline">
						<h1>5-6</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_6">6</span>
					<div class="shiji" name="timeline">
						<h1>6-7</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_7">7</span>
					<div class="shiji" name="timeline">
						<h1>7-8</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_8">8</span>
					<div class="shiji" name="timeline">
						<h1>8-9</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_9">9</span>
					<div class="shiji" name="timeline">
						<h1>9-10</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_10">10</span>
					<div class="shiji" name="timeline">
						<h1>10-11</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_11">11</span>
					<div class="shiji" name="timeline">
						<h1>11-12</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_12">12</span>
					<div class="shiji" name="timeline">
						<h1>12-13</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_13">13</span>
					<div class="shiji" name="timeline">
						<h1>13-14</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_14">14</span>
					<div class="shiji" name="timeline">
						<h1>14-15</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_15">15</span>
					<div class="shiji" name="timeline">
						<h1>15-16</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_16">16</span>
					<div class="shiji" name="timeline">
						<h1>16-17</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_17">17</span>
					<div class="shiji" name="timeline">
						<h1>17-18</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_18">18</span>
					<div class="shiji" name="timeline">
						<h1>18-19</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_19">19</span>
					<div class="shiji" name="timeline">
						<h1>19-20</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_20">20</span>
					<div class="shiji" name="timeline">
						<h1>20-21</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_21">21</span>
					<div class="shiji" name="timeline">
						<h1>21-22</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_22">22</span>
					<div class="shiji" name="timeline">
						<h1>22-23</h1>
						<p>查看</p>
					</div>
				</li>
				<li class="timeli">
					<span id="time_23">23</span>
					<div class="shiji" name="timeline">
						<h1>23-24</h1>
						<p>查看</p>
					</div>
				</li>
			</ul>
    </div>
</div>
<div id="footer0" style="background:url(union/image/white.jpg)"></div>
<!--引入封装好的方法(关于BMap)-->
<script type="text/javascript" src="union/js/myBMap.js"></script>
<!--引入封装好的方法(关于d3.js)-->
<script type="text/javascript" src="union/js/myd3heat.js"></script>

<script>
    var map = LoadMap("show0",0);//加载地图,0表示在线加载
    SetMapBounds(map);
    var posOfhours;//某些典型点的24小时热点信息
	var xhr = new XMLHttpRequest();
	xhr.open("GET", "heatdata/heatPosHours.txt");
	xhr.send(null);
	xhr.onreadystatechange = function () {
	    if (this.readyState == 4) {
	        if (this.status == 200) {
	            posOfhours = eval(xhr.responseText);
                //首次打开页面加载第一个点的热点信息
	            ShowD3HeatMap(posOfhours[0].data,["跳跃点"]);
	            //首次打开页面加载所有典型点
	            ShowMarkerofHour(map, posOfhours);
	        }
	        else {
	            alert("There was a problem with the request " + this.status)
	        }
	    }
	}

        var pre=-1;
        var timelines = document.getElementsByName("timeline");
        for (var i = 0; i < timelines.length; i++) {
            timelines[i].index = i;
            timelines[i].onclick = function () {
                if (timer != null) {
                    clearInterval(timer);
                    timer = null;
                }
                if (poss[this.index] == null) {
                    var xhr1 = new XMLHttpRequest();
                    xhr1.index = this.index;
                    xhr1.time = this;
                    xhr1.open("GET", "heatdata/heatpos" + this.index + ".txt");
                    xhr1.send(null);
                    xhr1.onreadystatechange = function () {
                        if (this.readyState == 4) {
                            if (this.status == 200) {
                                poss[this.index] = eval("("+this.responseText+")");
                                ShowHeatMapPos(poss[this.index].data,poss[this.index].radius);
                                var p = this.time.getElementsByTagName("p")[0]
                                p.innerHTML = "<p style='color:red'>" + "选中" + "</p>";
                                document.getElementById("time_"+this.index).style.color='red'
                                if(preindex!=null)
                                	document.getElementById("time_"+preindex).style.color='black'
                                preindex=this.index
                                if (pre != -1 && pre!=this.index) {
                                    var p = timelines[pre].getElementsByTagName("p")[0]
                                    p.innerHTML = "<p>" + "查看" + "</p>";
                                }
                                pre=this.index;
                            }
                            else {
                                alert("There was a problem with the request " + this.status)
                            }
                        }
                    }
                } else {
                    ShowHeatMapPos(poss[this.index].data,poss[this.index].radius);
                    var p=this.getElementsByTagName("p")[0]
                    p.innerHTML = "<p style='color:red'>" + "选中" + "</p>";
                    document.getElementById("time_"+this.index).style.color='red'
                    if(preindex!=null)
                    	document.getElementById("time_"+preindex).style.color='black'
                    preindex=this.index
                    if (pre != -1 && pre!=this.index ) {
                        var p = timelines[pre].getElementsByTagName("p")[0]
                        p.innerHTML = "<p>" + "查看" + "</p>";
                    }
                    pre = this.index;
                }
            }
        }
        //加载第一个小时热力图
        var xhr2 = new XMLHttpRequest();
        xhr2.open("GET", "heatdata/heatpos0.txt");
        xhr2.send(null);
        xhr2.onreadystatechange = function () {
            if (this.readyState == 4) {
                if (this.status == 200) {
                    poss[0] = eval("("+xhr2.responseText+")");
                    ShowHeatMapPos(poss[0].data,poss[0].radius);
                    timelines[0].style.display = "block"                            
                }
                else {
                    alert("There was a problem with the request " + this.status)
                }
            }
        }
        var poss=[];
        var curIndex = 1;
        var preindex=null;
        var timer = setInterval(function () {
            if (poss[curIndex] == null) {
                var xhr1 = new XMLHttpRequest();
                xhr1.open("GET", "heatdata/heatpos"+curIndex+".txt");
                xhr1.send(null);
                xhr1.onreadystatechange = function () {
                    if (this.readyState == 4) {
                        if (this.status == 200) {
                            poss[curIndex] = eval("("+xhr1.responseText+")");
                            ShowHeatMapPos(poss[curIndex].data,poss[curIndex].radius);
                            if(curIndex==22)
                            	var s;
                            if (curIndex != 0)
                                timelines[curIndex - 1].style.display = "none";
                            else
                                timelines[timelines.length - 1].style.display = "none";
                            timelines[curIndex].style.display = "block";
                            curIndex = ++curIndex % timelines.length;
                        }
                        else {
                            alert("There was a problem with the request " + this.status)
                        }
                    }
                }
            }
            else{
            	if (curIndex != 0)
                	timelines[curIndex - 1].style.display = "none";
                else
                	timelines[timelines.length - 1].style.display = "none";
                timelines[curIndex].style.display = "block";
                ShowHeatMapPos(poss[curIndex].data,poss[curIndex].radius);
                curIndex = ++curIndex % timelines.length;
           }
        }, 2000);
</script>
  </body>
  <jsp:include page="union/footer.jsp"></jsp:include>
</html>


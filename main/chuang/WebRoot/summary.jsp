<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>数据总括</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<style type="text/css">
	.container {margin:0 auto; width:900px;}
	.header { height:100px; margin-bottom:5px;}
	.mainContent { height:500px; margin-bottom:5px;}
	.footer { height:25px; }
	#show0{ width:900px; height:475px;}
	#show1{width:900px; height:475px;}
	label{font-family:幼圆}	
	</style>
	<script src="union/js/esl.js"></script>
    <script src="union/js/echarts-all.js"></script>
  </head>
  
  <body style="background:url(union/image/gray.jpg)">
    <jsp:include page="/union/h.jsp"></jsp:include>
    <jsp:include page="/union/v_hot.jsp"></jsp:include>
    <jsp:include page="/union/totop.jsp"></jsp:include>
    <br><br><br><br>
	<h1 align="center"
		style="font-family:华文行楷 ; font-size:47px;color: black; ">
		<strong>数据总括</strong>
	</h1>
	<div id="allshow">
	
	<div class="container" name="container_0">
	<div class="header"></div>
	<div class="mainContent"><div id="show0"></div></div>
	<div class="footer">
	<lable >主题选择</lable>
	<select id="theme-select_0">
	<option selected="true" value="macarons"> macarons </option>
	<option value="infographic"> infographic </option>
	<option value="default"> default </option>
	</select>
	<button type="button" >刷 新</button>
	</div>
	</div>
	
	<div class="container" name="container_1">
	<div class="header"></div>
	<div class="mainContent"><div id="show1"></div></div>
	<div class="footer">
	<lable >主题选择</lable>
	<select id="theme-select_1">
	<option selected="true" value="macarons"> macarons </option>
	<option value="infographic"> infographic </option>
	<option value="default"> default </option>
	</select>
	<button type="button" >刷 新</button>
	</div>
	</div>
	
	</div>
	
	
	
	<script type="text/javascript">
	
	var allinfos;
	var placeList=[];
	//[{name:'444444', geoCoord:[121.15, 31.89]},
    //{name:'888888', geoCoord:[109.781327, 39.608266]}]
	
	var xhr = new XMLHttpRequest();
	var obj
       xhr.open("POST", "union/response.jsp");
       xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded")
       xhr.send("id=1");
       xhr.onreadystatechange = function () {
           if (xhr.readyState == 4) {
               if (xhr.status == 200) {
                  allinfos=eval(xhr.responseText);
                  DatatoPos()
               } else {
                   alert("error num:" + xhr.status);
               }
           }
       }
    function DatatoPos() {
		for(var i=0;i<allinfos.length;i++){
			var x=allinfos[i];
			placeList.push({name:x.car_id,geoCoord:[x.longitude,x.latitude]});
		}
	}
// 
    require.config({
        packages: [
    {
       name: 'echarts',
       location: 'union/js/echarts/src',
       main: 'echarts'
   },
   {
       name: 'zrender',
       location: 'union/js/zrender/src',
       main: 'zrender'
   }
        ]
    });
    
    require(
            [
                'echarts',
                'echarts/chart/map',
            ],
            function (ec) {
            	charts=[]
            	charts.push(ec.init(document.getElementById('show0'), "macarons"))
            	charts.push(ec.init(document.getElementById('show1'), "macarons"))
            	var myChart0 = charts[0];
            	var myChart1 = charts[1]
            //	var myChart2 = ec.init(document.getElementById('show2'), "macarons");
            	//过渡动画
            	var loadingTicket;
				myChart0.showLoading({
				    text : 'loading',
				    textStyle : {
				        fontSize : 20
				    }
				});
            	require('echarts/util/mapData/params').params.beijing = {
                    getGeoJson: function (callback) {
                        $.getJSON('union/bei_jing_geo.txt', callback);
                    }
                }
   	            var option = {
   	                 backgroundColor: '#1b1b1b',
   	                 color: [
   	                     'rgba(255, 255, 255, 0.8)',
   	                     'rgba(14, 241, 242, 0.8)',
   	                     'rgba(37, 140, 249, 0.8)'
   	                 ],
   	                 tooltip : {
				        trigger: 'item',
				        formatter: '{b}'
				    },
   	                 title : {
   	                     text: '全局数据概括',
   	                     subtext: '北京市2012年出租车信息',
   	                     x:'center',
   	                     textStyle : {
   	                         color: '#fff'
   	                     }
   	                 },
   	                 legend: {
   	                     orient: 'vertical',
   	                     x:'left',
   	                     data:['显示'],
   	                     textStyle : {
   	                         color: '#fff'
   	                     }
   	                 },
   	                 toolbox: {
   	                     show : true,
   	                     orient : 'vertical',
   	                     x: 'right',
   	                     y: 'center',
   	                     feature : {
   	                         mark : {show: true},
   	                         dataView:{
   	                         	show: true,
   	                         	title:'数据视图',
   	                         	readOnly:true,
   	                         	optionToContent:function(opt){
   	                         		var table='<table style="width:100%;text-align:center"><tbody><tr>'
   	                         		+'<td>汽车标号</td>'
   	                         		+'<td>经度</td>'
   	                         		+'<td>维度</td>'
   	                         		+'<td>行驶速度(km/h))</td>'
   	                         		+'<td>记录时间</td>'
   	                         		+'</tr>';
   	                         		for(var i=0;i<allinfos.length;i++)
   	                         		{
   	                         			table+='<tr>'
   	                         				 +'<td>'+allinfos[i].car_id+'</td>'
   	                         				 +'<td>'+allinfos[i].longitude+'</td>'
   	                         				 +'<td>'+allinfos[i].latitude+'</td>'
   	                         				 +'<td>'+allinfos[i].speed+'</td>'
   	                         				 +'<td>'+allinfos[i].time+'</td>'
   	                         				 +'</tr>';
   	                         		}
   	                         		return table;
   	                         	}
   	                         },
   	                         restore : {show: true}   	                         
   	                     }
   	                 },
   	                 series : [
   	                     {
   	                         name: '显示',
   	                         type: 'map',
   	                         mapType: 'beijing',
   	                         roam: true,
				             hoverable: false,
   	                         itemStyle:{
   	                             normal:{
   	                                 borderColor:'rgba(100,149,237,1)',
   	                                 borderWidth:1.5,
   	                                 areaStyle:{
   	                                     color: '#1b1b1b'
   	                                 }
   	                             }
   	                         },
   	                         data : [],
   	                         markPoint : {
   	                             symbolSize: 2,
   	                             large: true,
   	                             effect : {
   	                                 show: true
   	                             },
   	                             data : (function(){
   	                                 var data = [];
   	                                 var len = placeList.length;
   	                                 var geoCoord1
   	                                 while(len--) {
   	                                     geoCoord1 = placeList[len ].geoCoord;
   	                                     data.push({
   	                                         name : placeList[len ].name,
   	                                         value : 10,
   	                                         geoCoord : [
   	                                             geoCoord1[0],
   	                                             geoCoord1[1]
   	                                         ]
   	                                     })
   	                                 }
   	                                 return data;
   	                             })()
   	                         }
   	                     },
   	                     
   	                 ]
   	             };
            	 //过渡动画取消
            	clearTimeout(loadingTicket);
				loadingTicket = setTimeout(function (){
				    myChart0.hideLoading();
				    myChart0.setOption(option);
				},1000);
				
				//show_1  beijing
			/*	require('echarts/util/mapData/params').params.beijing = {
                    getGeoJson: function (callback) {
                        $.getJSON('union/bei_jing_geo.txt', callback);
                    }
                }*/
                //过渡动画
				myChart1.showLoading({
				    text : 'loading',
				    textStyle : {
				        fontSize : 20
				    }
				});
				var loadingTicket1 ;
				//过渡动画取消
            	clearTimeout(loadingTicket1);
				loadingTicket1 = setTimeout(function (){
				    myChart1.hideLoading();
				    myChart1.setOption(option);
				},1200);
				
				window.onload=function(){
					var btns=document.getElementsByTagName("button");
					for(var i=0;i<btns.length;i++)
					{
						RefreshSet(i,btns[i],charts[i])
					}
				}
				function RefreshSet(i,btn,chart){
					btn.onclick=refresh;
					function refresh(){
						var item=document.getElementById("theme-select_"+i)
						chart.setTheme(item.value);						
					}
				}
            })
            
       
	</script>
  </body>
</html>

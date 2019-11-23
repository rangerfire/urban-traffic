var bgpoint = new BMap.Point(116.404, 39.915);  // 创建点坐标   北京
var heatmapOverlay;
var limit=8;//速度分解线
var maxlimit=16;
//默认参数
var outputPath = './tiles/';
var minLevel = 10;
var maxLevel = 15;
var format = '.png';
var pointsStr = '';
function LoadMap(id,isOnline,pos,lev){
	
	pos=arguments[2]?arguments[2]:bgpoint;
	lev=arguments[3]?arguments[3]:minLevel;
	var map;
	if(isOnline==0)//在线
		map = new BMap.Map(id,{enableMapClick:false,minZoom:10,maxZoom:17});   // 创建地图实例
	else{//离线
		var tileLayer = new BMap.TileLayer();
		tileLayer.getTilesUrl = function(tileCoord, zoom) {
			var x = tileCoord.x;
			var y = tileCoord.y;
			var url = outputPath + zoom +'/' + x + '/' + y + format;
			return url;
		}
		var tileMapType = new BMap.MapType('tileMapType', tileLayer, {
			minZoom : minLevel,
			maxZoom : maxLevel
		});
		map = new BMap.Map('show0', {
			mapType : tileMapType
		});	
	}
	map.setMapStyle({style:'googlelite'});
	map.centerAndZoom(pos, lev);                 // 初始化地图，设置中心点坐标和地图级别  越大越详细
	map.setCurrentCity("北京");          		 // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	map.disableDoubleClickZoom()
	//map.disable3DBuilding()
	getBoundary(map)
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	var copyright=new BMap.Copyright();
	var cpctr=new BMap.CopyrightControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT})
	copyright.id=0;
	copyright.content="<strong >© 2015.njnu.yc</strong>";
	//copyright.bounds=new BMap.Bounds(p1,p2)
	cpctr.addCopyright(copyright);
	
	map.addControl(top_left_control);   //添加控件
	map.addControl(top_left_navigation);   //添加控件
	map.addControl(cpctr);   //添加控件
	return map;
}
//13级别开始换mapstyle:城市主干道为绿色
function getBoundary(map){       
	var bdary = new BMap.Boundary();
	bdary.get("北京", function(rs){       //获取行政区域
	//	map.clearOverlays();        //清除地图覆盖物       
		var count = rs.boundaries.length; //行政区域的点有多少个
		for(var i = 0; i < count; i++){
			var ply = new BMap.Polygon(rs.boundaries[i],
					{strokeWeight: 2, strokeColor: "#ff0000",
				//	在调用map.clearOverlays不清除此覆盖物
				enableMassClear:false}); //建立多边形覆盖物
			ply.setFillColor(0);
			ply.setStrokeOpacity(0.5);
			ply.setStrokeStyle("dashed");
			map.addOverlay(ply);  //添加覆盖物
		//	map.setViewport(ply.getPath());    //调整视野         
		}                
	});   
}
/**
 * 设置map的区域范围,超出该范围将移回(默认为北京区域)
 *@param map:设区域的map
 *@param letop:区域的左上角
 *@param rgbot:区域的右下角
 */
var p1=new BMap.Point(116.027143, 39.772348)
var p2=new BMap.Point(116.832025, 40.126349)
 function SetMapBounds(map,letop,rgbot){
	letop=arguments[1]?arguments[1]:p1
	rgbot=arguments[2]?arguments[2]:p2
	var b = new BMap.Bounds(letop,rgbot);
	try {	
	 	BMapLib.AreaRestriction.setBounds(map, b);
	} catch (e) {
		alert(e);
	}//将地图显示范围设定在指定区域，地图拖出该区域后会重新弹回。 //需要加载AreaRestriction.
 }
 function ShowMarkerofHour(map, poss) {
     var marks = [];
     for (var i=0;i<poss.length;i++) {
         var lng = poss[i].longitude;
         var lat = poss[i].latitude;
         var mark = new BMap.Marker(new BMap.Point(lng, lat));
         marks.push(mark);
         var opts = {
             width: 210,     // 信息窗口宽度
             height: 110,     // 信息窗口高度
             title: "该点位置信息", // 信息窗口标题
         }
         mark.postion = new BMap.Point(lng, lat);
         mark.info = poss[i].info
         mark.onrightclick = function (event) {
             var infoWindow = new BMap.InfoWindow("经度: " + this.postion.lng 
            		 + " <br>纬度: " + this.postion.lat
            		 +"<br>地址: "+this.info, opts);  // 创建信息窗口对象 
             map.openInfoWindow(infoWindow,event.point);
         }
         map.addOverlay(mark)
         mark.onclick = function () {
             //其他的取消动画,本身加上弹跳动画
             for (var i = 0; i < marks.length; i++) {
                 if (marks[i] != this)
                     marks[i].setAnimation(null);
                 else {
                     this.setAnimation(BMAP_ANIMATION_BOUNCE);
                     ShowD3HeatMap(poss[i].data,["跳跃点"]);
                 }
             }
             
         }
     }
     marks[0].setAnimation(BMAP_ANIMATION_BOUNCE);
 }
 function removeRount(map){
//	 for(var i in rountOverlays)
//	 	map.removeOverlay(rountOverlays[i])
	 map.clearOverlays()
 }

 //判断浏览区是否支持canvas
 function isSupportCanvas() {
     var elem = document.createElement('canvas');
     return !!(elem.getContext && elem.getContext('2d'));
 }
//加载热力图

 function ShowHeatMapPos(points,radius) {
     //参数说明如下:
     /* visible 热力图是否显示,默认为true
      * opacity 热力的透明度,1-100
      * radius 势力图的每个点的半径大小   
      * gradient  {JSON} 热力图的渐变区间 . gradient如下所示
      *	{
             .2:'rgb(0, 255, 255)',
             .5:'rgb(0, 110, 255)',
             .8:'rgb(100, 0, 255)'
         }
         其中 key 表示插值的位置, 0~1. 
             value 为颜色值. 
      */
     if (!isSupportCanvas()) {
         alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
     }
     map.removeOverlay(heatmapOverlay)
     heatmapOverlay = new BMapLib.HeatmapOverlay({ "radius": radius, "gradient": { .1428: colorlist[0], .2856: colorlist[1], .4284: colorlist[2], .5712: colorlist[3], .714: colorlist[4], 0.8568: colorlist[5], 1: colorlist[6] } });
     map.addOverlay(heatmapOverlay);
     
     //???max:
     heatmapOverlay.setDataSet({ data: points, max: 100 });
     heatmapOverlay.show();
 }
 
 function InitMapforJam(map){
	 var style1 = [ {
			"featureType" : "highway",
			"elementType" : "geometry.fill",
			"stylers" : {
				"color" : "#00DD00"
			}
		}, {
			"featureType" : "arterial",
			"elementType" : "geometry.fill",
			"stylers" : {}
		} ], style2 = [ {
			"featureType" : "highway",
			"elementType" : "geometry.fill",
			"stylers" : {
				"color" : "#00DD00"
			}
		}, {
			"featureType" : "arterial",
			"elementType" : "geometry.fill",
			"stylers" : {
				"color" : "#00DD00"
			}
		} ]
		map.setMapStyle({
			styleJson : style1
		});
		map.setMinZoom(11);
		//计时器  监听地图级别  更改地图style
		var isChange1 = false//, isChange2 = false;
		map.addEventListener("zoomend", function() {
			if (!isChange1 && map.getZoom() >= 14) {
				isChange1 = true;
				map.setMapStyle({
					styleJson : style2
				})
			}
			else if (isChange1 && map.getZoom() < 14) {
				isChange1 = false
				map.setMapStyle({
					styleJson : style1
				})
			}
		});
 }
 
 function DrawRount(map,obArr,direction,sp,id){
	 if(!direction) 
		 obArr.reverse()
	 var posArr=[]
	 for(var i in obArr){
		 posArr.push(new BMap.Point(obArr[i].lng,obArr[i].lat))
	 }
	 var cl=sp>limit?"orange":"#CC0000"
	 var ply = new BMap.Polyline(posArr,{
			strokeColor:cl,strokeWeight:5,strokeOpacity:1})
		map.addOverlay(ply);
	 var label = new BMap.Label("路段"+id,
			 {  position:new BMap.Point((obArr[0].lng+obArr[1].lng)/2,(obArr[0].lat+obArr[1].lat)/2),
		 		offset:new BMap.Size(10,-25)});
	 	
		 label.setStyle({
			 color : cl,
			 fontSize : "12px",
			 height : "20px",
			 lineHeight : "20px",
			 fontFamily:"微软雅黑"
		 }); 
		map.addOverlay(label);
	 var pois=[]
	 for(var i in posArr){
		if(i==posArr.length-1)
	 		pois.push({lng:posArr[i].lng,lat:posArr[i].lat,pauseTime:2,html:'结束于:'+obArr[i].info})
	 	else
	 		pois.push({lng:posArr[i].lng,lat:posArr[i].lat,pauseTime:1.5,html:'经过了:'+obArr[i].info})
	 }
	 var lushu = new BMapLib.LuShu(map,posArr,{
			defaultContent:"平均速度:"+sp+"km/h",
			autoView:true,
			icon  : new BMap.Icon('union/image/car.png', 
					new BMap.Size(52,26),
					{anchor : new BMap.Size(27, 13)}),
			speed: 3000,// m/s
			enableRotation:true,//是否设置marker随着道路的走向进行旋转
			landmarkPois:pois
			});
		ply.onclick=function(event){
			lushu.start();
			map.setViewport(this.getPath());//
			clearInterval(timer);
		}
		ply.onrightclick=function(event){
			clearInterval(timer);
			var opts = {
		            width: 250,     // 信息窗口宽度
		            height: 30+28*posArr.length,     // 信息窗口高度
		            title: "路段信息", // 信息窗口标题
		        }
			var pss=this.getPath();
			var mess="";
			for(var i in posArr){
				var lab;
				if(i==0)
					lab="开始于："
				else if(i==posArr.length-1)
					lab="结束于："
				else
					lab="经过了："
				mess += lab+obArr[i].info+"<br>";
			}
		var infoWindow = new BMap.InfoWindow(mess, opts);  // 创建信息窗口对象
        map.openInfoWindow(infoWindow, event.point);    
	}
 }
 
 
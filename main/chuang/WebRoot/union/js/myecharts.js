function LoadEcharts(work){
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
                'echarts/chart/bar',
                'echarts/chart/line',
            ],work)
}

function LoadBeijing(){
	require('echarts/util/mapData/params').params.beijing = {
        getGeoJson: function (callback) {
            $.getJSON('union/bei_jing_geo.txt', callback);
        }
    }
}
function PostRequest(data,work){
	var xhr = new XMLHttpRequest();
    xhr.open("POST", "union/response.jsp");
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded")
    xhr.send(data);
    	xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
           if (xhr.status == 200) {
              work(xhr.responseText)
           } else {
               alert("error num:" + xhr.status);
           }
       }
   }
}
function setAnimation(myChart,option){
	//过渡动画
	myChart.showLoading({
	    text : 'loading',
	    textStyle : {
	        fontSize : 20
	    }
	});
	var loadingTicket;
	//过渡动画取消
	clearTimeout(loadingTicket);
	loadingTicket = setTimeout(function (){
	    myChart.hideLoading();
	    myChart.setOption(option);
	},1200);
}

function InitThemeChange(charts){
	var btns=document.getElementsByTagName("button");
	for(var i=1;i<btns.length;i++)
	{
		RefreshSet(i-1,btns[i],charts[i-1])
	}
}
function RefreshSet(i,btn,chart){
	btn.onclick=myrefresh;
	function myrefresh(){
		var item=document.getElementById("theme-select_"+i)
		chart.setTheme(item.value);
		chart.refresh()
	}
}
/**
 * Created by Yc on 2016/2/5.
 */
$(function () {
    var localosm = L.tileLayer('tiles/{z}/{x}/{y}.png');

    map = L.map('map',{
        center:[39.9,116.39],
        zoom:10,
        layers:[localosm],
        maxZoom: 15,
        minZoom: 10,
        maxBounds: L.latLngBounds(L.latLng(40.41558, 117.17331), L.latLng(39.42346, 115.60776)),
        zoomControl:false,
        attributionControl:false
    });
    //zoom in and zoom out add to map (left top)
    map.zoom = L.control.zoom({zoomInTitle:'放大',zoomOutTitle:'缩小'}).addTo(map)
    //copyright add to map (right bottom)
    map.copyright = L.control.attribution({prefix:'<a target="_blank" href="http://moyuyc.xyz">&copy; njnu</a>'}).addTo(map)
    map.scale = L.control.scale().addTo(map);
    map.areaSelect = L.areaSelect({width:200, height:250}).addTo(map);
    map.popup = new L.popup();

    map.on('mousemove', function (e) {
        var t = '('+e.latlng.lat.toFixed(3)+','+e.latlng.lng.toFixed(3)+')';
        if(map.copyright.prev)
            map.copyright.removeAttribution(map.copyright.prev);
        map.copyright.prev = t;
        map.copyright.addAttribution(t);
    });
    echarts.hours=[
        '12a~1a','1a~2a','2a~3a','3a~4a','4a~5a','5a~6a','6a~7a','7a~8a','8a-9a','9a~10a','10a~11a','11a~12p',
        '12p~1p','1p~2p','2p~3p','3p~4p','4p~5p','5p~6p','6p~7p','7p~8p','8p-9p','9p~10p','10p~11p','11p~12p',
    ];
    //heatmap init
    (function(){
        echarts.heatmap = echarts.init(document.getElementById('heatmap'));
        var hours = ['12a', '1a', '2a', '3a', '4a', '5a', '6a',
            '7a', '8a', '9a','10a','11a',
            '12p', '1p', '2p', '3p', '4p', '5p',
            '6p', '7p', '8p', '9p', '10p', '11p'];
        var days = ['11-01', '11-02'];
        var data = [
            [0,0,5],[0,1,1],[0,2,0],[0,3,0],[0,4,0],[0,5,0],[0,6,0],[0,7,0],[0,8,0],[0,9,0],[0,10,0],[0,11,2],[0,12,4],[0,13,1],[0,14,1],[0,15,3],[0,16,4],[0,17,6],[0,18,4],[0,19,4],[0,20,3],[0,21,3],[0,22,2],[0,23,5],
            [1,0,7],[1,1,0],[1,2,0],[1,3,0],[1,4,0],[1,5,0],[1,6,0],[1,7,0],[1,8,0],[1,9,0],[1,10,5],[1,11,2],[1,12,2],[1,13,6],[1,14,9],[1,15,11],[1,16,6],[1,17,7],[1,18,8],[1,19,12],[1,20,5],[1,21,5],[1,22,7],[1,23,2]
            ];
        data = data.map(function (item) {
            return [item[1], item[0], item[2] || '-'];
        });
        var option = {
            title:{
                show:true,
                text:'道路情况汇总',
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: true},
                    dataZoom: {show:true},
                    restore: {show:true},
                    saveAsImage : {show: true},
                }
            },
            tooltip: {
                position: 'top',
                formatter: function (params,ticket,callback) {
                    console.log([ticket,callback])
                    return '时间: '+days[params.data[1]]+' '+params.name+'' +
                        '<br>速度: '+params.data[2]+'km/h'
                },
            },
            animation: false,
            grid: {
                height: '25%',
                y: '10%'
            },
            xAxis: {
                type: 'category',
                data: hours
            },
            yAxis: {
                type: 'category',
                data: days
            },
            visualMap: {
                min: 1,
                max: 12,
                calculable: true,
                orient: 'horizontal',
                left: 'center',
                bottom: '100'
            },
            series: [{
                type: 'heatmap',

                data: data,
                label: {
                    normal: {
                        show: true,
                        textStyle : {
                            color : 'black'
                        }
                    }
                },
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)',
                    }
                }
            }]
        };
        echarts.heatmap.setOption(option);
        //heatmap.showLoading()
    })();
    //gauge init
    (function(){
        echarts.gauge = echarts.init(document.getElementById('gauge'));
        var option = {
            title:{
                show:true,
                text:'速度表',
                //textStyle:{
                //    fontSize:15
                //},
                subtext:'道路1'
            },
            tooltip : {
                formatter: "速度 : {c}km/h"
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    saveAsImage : {show: true}
                }
            },
            series : [
                {
                    name:'速度表',
                    type:'gauge',
                    splitNumber:4,
                    pointer:{
                        width:4,
                    },
                    axisLine : {
                        show : true,
                        lineStyle : {
                            width:10
                        }
                    },
                    detail : {
                        formatter:'{value}km/h',
                        textStyle:{
                            fontSize:15
                        }
                    },
                    data:[{value: 50}],
                }
            ]
        };
        echarts.gauge.setOption(option);
    })();
    //certain-car-chart init
    (function(){
        var dayHourFormat = function (days) {
            var d = [];
            for (var i = 0; i < days.length; i++) {
                for (var j = 0; j < echarts.hours.length; j++) {
                    d.push(days[i]+' '+echarts.hours[j]);
                }
            }
            return d;
        };
        echarts.certainCarsChart = echarts.init(document.getElementById('certain-cars-chart'));
        var option = {
            color:['#7744FF','#00AAAA'],
            title:{
                text:'车辆平均速度比较',
                subtext:'104010 - 104011'
            },
            tooltip : {
                trigger: 'item',
                formatter:'时间: {b}<br>车号: {a}<br>速度: {c}km/h'
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: true},
                    dataZoom: {show:true},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show:true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            legend: {
                data:['104010','104011'],
                itemGap: 15
            },
            grid: {
                top: '16%',
                left: '20px',
                right: '10%',
                containLabel: true
            },
            xAxis: [
                {
                    type : 'category',
                    data : dayHourFormat(['11-01','11-02'])
                }
            ],
            yAxis: [
                {
                    type : 'value',
                    name : '速度 ( km/h )',
                }
            ],
            dataZoom: [
                {
                    show: true,
                    start: 30,
                    end: 70,
                    handleSize: 8
                },
                {//滚轮
                    type: 'inside',
                },
                {
                    show: true,
                    yAxisIndex: 0,
                    filterMode: 'empty',
                    width: 12,
                    height: '70%',
                    handleSize: 8,
                    showDataShadow: false,
                    left: '93%'
                }
            ],
            series : [
                {
                    name: '104010',
                    type: 'bar',
                    data: [1,1,2,3.4,4,6]
                },

                {
                    name: '104011',
                    type: 'bar',
                    data: [4,6,7,1,2,5]
                },

            ]
        };
        echarts.certainCarsChart.setOption(option);
    })();
    //all-qty-chart init
    (function () {
        var hourFormat = function (hour) {
            return {value:hour,tooltip:{position:'top',formatter:'{b}'}};
        }
        echarts.allQtyChart = echarts.init(document.getElementById('all-qty-chart'));
        echarts.allQtyChart.setOption({
            baseOption: {
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: true},
                        dataZoom: {show:true},
                        magicType: {show: true, type: ['line', 'bar' ]},
                        restore: {show:true},
                        saveAsImage : {show: true}
                    }
                },
                timeline: {
                    axisType: 'category',
                    loop: false,
                    autoPlay: false,
                    playInterval: 1000,
                    data: [
                        hourFormat('12a~1a'), hourFormat('1a~2a'), hourFormat('2a~3a'),
                        {
                            value: '3a~4a',
                            tooltip: {
                                position:'top',
                                formatter: '{b} 拥堵高峰'
                            },
                            symbol: 'diamond',
                            symbolSize: 16
                        },
                        hourFormat('4a~5a'), hourFormat('5a~6a'), hourFormat('6a~7a'), hourFormat('7a~8a'),
                        {
                            value: '8a~9a',
                            tooltip: {
                                position:'top',
                                formatter: function (params) {
                                    console.log(params);
                                    return params.name + ' 又一个拥堵高峰';
                                }
                            },
                            symbol: 'diamond',
                            symbolSize: 18
                        },
                        hourFormat('9a~10a'), hourFormat('10a~11a'), hourFormat('11a~12p'), hourFormat('12p~1p'), hourFormat('1p~2p'), hourFormat('2p~3p'),
                        hourFormat('6p~7p'),hourFormat('7p~8p'),hourFormat('8p~9p'),hourFormat('9p~10p'), hourFormat('10p~11p'),hourFormat('11p~12p'),
                    ]
                },
                legend: {
                    data: ['拥堵', '缓行','畅通'],
                },
                calculable: true,
                grid: {
                    top: 80,
                    bottom: 100
                },
                tooltip: {},
                xAxis: [
                    {
                        'type': 'category',
                        'axisLabel': {'interval': 0},
                        'data': [
                            '11-01', '11-02'
                        ],
                        splitLine: {show: false}
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        name: '道路数量（段）',
                        max: 100
                    }
                ],
                series: [
                    {name: '拥堵', type: 'bar'},
                    {name: '缓行', type: 'bar'},
                    {name: '畅通', type: 'bar'},
                    {
                        name: '路段情况占比',
                        type: 'pie',
                        center: ['75%', '35%'],
                        radius: '28%'
                    }
                ],
            },
            options: [
                {
                    title: {text: '12a-1a 各路况道路数汇总'},
                    series: [//01 - 02
                        {data: [1,2]},//拥堵
                        {data: [2,3]},//缓行
                        {data: [6,5]},//畅通
                        {data: [
                            {name: '拥堵总路段数', value: [3]},
                            {name: '缓行总路段数', value: [5]},
                            {name: '畅通总路段数', value: [11]}
                        ]}
                    ],
                    tooltip:{
                        formatter:'日期: {b}<br>时间段: 12a-1a<br>{a}路段数: {c}'
                    }
                },
                {
                    title: {text: '1a-2a 各路况道路数汇总'},
                    series: [//01 - 02
                        {data: [3,12]},//拥堵
                        {data: [20,13]},//缓行
                        {data: [16,15]},//畅通
                        {data: [
                            {name: '拥堵总路段数', value: [15]},
                            {name: '缓行总路段数', value: [33]},
                            {name: '畅通总路段数', value: [31]}
                        ]}
                    ],
                    tooltip:{
                        formatter:'日期: {b}<br>时间段: 1a-2a<br>{a}路段数: {c}'
                    }
                },
                {
                    title: {text: '2a-3a 各路况道路数汇总'},
                    series: [//01 - 02
                        {data: [21,12]},//拥堵
                        {data: [22,23]},//缓行
                        {data: [26,25]},//畅通
                        {data: [
                            {name: '拥堵总路段数', value: [33]},
                            {name: '缓行总路段数', value: [45]},
                            {name: '畅通总路段数', value: [51]}
                        ]}
                    ],
                    tooltip:{
                        formatter:'日期: {b}<br>时间段: 2a-3a<br>{a}路段数: {c}'
                    }
                },
            ]
        })
    })();
    
})
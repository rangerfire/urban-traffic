colorlist = ['#99BBFF', '#00DDDD', '#BBFF00', '#FFFF00', '#FF8800', '#FF5511', '#CC0000']

function ShowD3HeatMap(data,lab,isjam) {
          if(data.length==0)
        	  lab=["无数据"];
          var margin = { top: 50, right: 0, bottom: 100, left: 60 },
          width = 870 - margin.left - margin.right,
          height = 165+35*lab.length - margin.top - margin.bottom,
          gridSize = Math.floor(width / 24),
          legendElementWidth = gridSize * 2,
          buckets = 9,
		  //color lists
          colors = colorlist, // alternatively colorbrewer.YlGnBu[9]
          days = lab,
          times = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"];
          
          var colorScale = d3.scale.quantile()
              .domain([0, buckets - 1, d3.max(data, function (d) { return d.value; })])
              .range(colors);
          //行政区域也为svg
          var svgs=d3.selectAll("svg")
          if(svgs[0].length!=0)
          	svgs[0][svgs[0].length-1].remove()
          var svg = d3.select("#footer0").append("svg")
              .attr("width", width + margin.left + margin.right)
              .attr("height", height + margin.top + margin.bottom)
              .append("g")
              .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
          // svg.selectAll(".dayLabel").remove()
          var dayLabels = svg.selectAll(".dayLabel")
              .data(days)
              .enter().append("text")
                .text(function (d) { return d; })
                .attr("x", 0)
                .attr("y", function (d, i) { return i * gridSize; })
                .style("text-anchor", "end")
                .attr("transform", "translate(-6," + gridSize / 1.5 + ")")
                .attr("class", function (d, i) { return ((i >= 0 && i <= 4) ? "dayLabel mono axis axis-workweek" : "dayLabel mono axis"); });

          var timeLabels = svg.selectAll(".timeLabel")
              .data(times)
              .enter().append("text")
                .text(function(d) { return d; })
                .attr("x", function(d, i) { return i * gridSize; })
                .attr("y", 0)
                .style("text-anchor", "middle")
                .attr("transform", "translate(" + gridSize / 2 + ", -6)")
                .attr("class", function(d, i) { return ((i >= 7 && i <= 16) ? "timeLabel mono axis axis-worktime" : "timeLabel mono axis"); });

          var heatMap = svg.selectAll(".hour")
              .data(data)
              .enter().append("rect")
              .attr("x", function(d) { return (d.hour ) * gridSize; })
              .attr("y", function(d) { return (d.day ) * gridSize; })
              .attr("rx", 4)
              .attr("ry", 4)
              .attr("class", "hour bordered")
              .attr("width", gridSize)
              .attr("height", gridSize)
              .style("fill", colors[0]);

          heatMap.transition().duration(1000)
              .style("fill", function(d) { return colorScale(d.value); });

          heatMap.append("title").text(function(d) { return d.value; });
              
          var legend = svg.selectAll(".legend")
              .data([0].concat(colorScale.quantiles()), function(d) { return d; })
              .enter().append("g")
              .attr("class", "legend");

          legend.append("rect")
            .attr("x", function(d, i) { return legendElementWidth * i; })
            .attr("y", height)
            .attr("width", legendElementWidth)
            .attr("height", gridSize / 2)
            .style("fill", function(d, i) { return colors[i]; });

          legend.append("text")
            .attr("class", "mono")
            .text(function(d) { return "≥ " + Math.round(d); })
            .attr("x", function(d, i) { return legendElementWidth * i; })
            .attr("y", height + gridSize);

        }
function ShowD3JamHour(data,lab) {
	var colorli=['#CC0000','orange','#00AA55','gray'];
	var colorli1=['gray','#CC0000','orange','#00AA55'];
	var spli=[0,limit,maxlimit,"N/A"];
	var spinal=spli[spli.length-1];
    if(data.length==0)
  	  lab=["无数据"];
    var margin = { top: 50, right: 0, bottom: 100, left: 60 },
    width = 870 - margin.left - margin.right,
    height = 165+35*lab.length - margin.top - margin.bottom,
    gridSize = Math.floor(width / 24),
    legendElementWidth = gridSize * 2,
    buckets = 9,
	  //color lists
    colors = colorli1, // alternatively colorbrewer.YlGnBu[9]
    days = lab,
    times = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"];
    //！！！！
    var colorScale = d3.scale.quantile()
        .domain([-1,0,limit,maxlimit,d3.max(data, function (d) {return d.value; })])
        .range(colors);
    
   
    var svgs=d3.selectAll("svg")
    if(svgs[0].length!=0)
    	svgs[0][svgs[0].length-1].remove()
    var svg = d3.select("#footer0").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    // svg.selectAll(".dayLabel").remove()
    var dayLabels = svg.selectAll(".dayLabel")
        .data(days)
        .enter().append("text")
          .text(function (d) { return d; })
          .attr("x", 0)
          .attr("y", function (d, i) { return i * gridSize; })
          .style("text-anchor", "end")
          .attr("transform", "translate(-6," + gridSize / 1.5 + ")")
          .attr("class", function (d, i) { return ((i >= 0 && i <= 4) ? "dayLabel mono axis axis-workweek" : "dayLabel mono axis"); });

    var timeLabels = svg.selectAll(".timeLabel")
        .data(times)
        .enter().append("text")
          .text(function(d) { return d; })
          .attr("x", function(d, i) { return i * gridSize; })
          .attr("y", 0)
          .style("text-anchor", "middle")
          .attr("transform", "translate(" + gridSize / 2 + ", -6)")
          .attr("class", function(d, i) { return ((i >= 7 && i <= 16) ? "timeLabel mono axis axis-worktime" : "timeLabel mono axis"); });

    var heatMap = svg.selectAll(".hour")
        .data(data)
        .enter().append("rect")
        .attr("x", function(d) { return (d.hour ) * gridSize; })
        .attr("y", function(d) { return (d.day ) * gridSize; })
        .attr("rx", 4)
        .attr("ry", 4)
        .attr("class", "hour bordered")
        .attr("width", gridSize)
        .attr("height", gridSize)
        .style("fill", colors[0]);

    heatMap.transition().duration(1000)
        .style("fill", function(d) { return colorScale(d.value); });

    heatMap.append("title").text(function(d) { return d.value==-1?spinal:d.value; });
        
    var legend = svg.selectAll(".legend")
        .data(spli)
        .enter().append("g")
        .attr("class", "legend");
    legend.append("rect")
      .attr("x", function(d, i) { return legendElementWidth * i; })
      .attr("y", height)
      .attr("width", legendElementWidth)
      .attr("height", gridSize / 2)
      .style("fill", function(d, i) { return colorli[i]; });
    legend.append("text")
      .attr("class", "mono")
      .text(function(d) { var t=spli.shift(); spli.push(t);  return t!=spinal ?"≥ " + t:". "+t; })
      .attr("x", function(d, i) { return legendElementWidth * i; })
      .attr("y", height + gridSize);

  }
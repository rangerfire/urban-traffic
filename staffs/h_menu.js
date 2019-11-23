$(function() {
	$('#mobanwang_com li').hover(function() {
		$(this).children('ul').stop(true, true).show('slow');
	}, function() {
		$(this).children('ul').stop(true, true).hide('slow');
	});

	$('#mobanwang_com li').hover(function() {
		$(this).children('div').stop(true, true).show('slow');
	}, function() {
		$(this).children('div').stop(true, true).hide('slow');
	});
});

function pageScroll() {
	window.scrollBy(0, -10);
	scrolldelay = setTimeout('pageScroll()', 100);
}

BackTop = function(btnId) {
	varbtn = document.getElementById(btnId);
	vard = document.documentElement;
	window.onscroll = set;
	btn.onclick = function() {
		btn.style.display = "none";
		window.onscroll = null;
		this.timer = setInterval(function() {
			d.scrollTop -= Math.ceil(d.scrollTop * 0.1);
			if (d.scrollTop == 0)
				clearInterval(btn.timer, window.onscroll = set);
		}, 10);
	};
	function set() {
		btn.style.display = d.scrollTop ? 'block' : "none";
	}
};
BackTop('gotop');

function gotoTop(acceleration, stime) {
	acceleration = acceleration || 0.1;
	stime = stime || 10;
	var x1 = 0;
	var y1 = 0;
	var x2 = 0;
	var y2 = 0;
	var x3 = 0;
	var y3 = 0;
	if (document.documentElement) {
		x1 = document.documentElement.scrollLeft || 0;
		y1 = document.documentElement.scrollTop || 0;
	}
	if (document.body) {
		x2 = document.body.scrollLeft || 0;
		y2 = document.body.scrollTop || 0;
	}
	var x3 = window.scrollX || 0;
	var y3 = window.scrollY || 0;
	var x = Math.max(x1, Math.max(x2, x3));
	var y = Math.max(y1, Math.max(y2, y3));
	var speeding = 1 + acceleration;
	window.scrollTo(Math.floor(x / speeding), Math.floor(y / speeding));
	if (x > 0 || y > 0) {
		var run = "gotoTop(" + acceleration + ", " + stime + ")";
		window.setTimeout(run, stime);
	}
}
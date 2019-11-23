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


$(document).ready(function(){
	
	var WinHeight = $('.billboard').height() - 100;
	var BgColor = 'rgba(57, 109, 135, .5)';
	var DarkBgColor = 'rgba(57, 109, 135, .9)';
	
	$(window).scroll(function () {
	    if ($(window).scrollTop() < WinHeight) {
	    	$("header").css("background-color", BgColor);
	    } else {
	    	$("header").css("background-color", DarkBgColor);
	    }
	})

});

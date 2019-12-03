// requires jQuery
function getContextRootPath() {
	return $("#contextRootPath").val();
}

function scrollTo(target, time) {
	$("html,body").animate({scrollTop: target.offset().top}, time);
}

function isMobile() {
	return (/android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(navigator.userAgent.toLowerCase()));
}

// inject events
$(function() {
	// version label - scroll to top
	$(".version").click(function() {
		scrollTo($("body"), 250);
	});

	// insert currentDateTimeButton after created/updated elements
	$("input[type=text]#created,input[type=text]#updated")
		.after("<button type='button' class='currentDateTimeButton'>Set Now</button>");

	// class currentDateTimeButton - input current timestamp
	$(".currentDateTimeButton").click(function() {
		var $this = $(this),
			$target = $this.prev();
		$target.val(Date.today().setTimeToNow().toString("yyyyMMddHHmmss"));
	});

});

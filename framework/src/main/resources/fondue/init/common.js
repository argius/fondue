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

function actSessionAvailableOrNot(okAction, ngAction, errorAction) {
	$.ajax({
		type: "get",
		url: getContextRootPath() + "login/api/sessionAvailable",
		dataType: "json"
	}).done(function(result) {
		var isAvailable = result["available"] == true;
		console.log("result-available:", isAvailable);
		if (isAvailable) {
			okAction();
		} else {
			ngAction();
		}
	}).fail(function(result, status) {
		console.log("failed: result.status,status=", result.status, status);
		errorAction("ajax-fail: r.s=" + result.status + ",st=" + status);
	});
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

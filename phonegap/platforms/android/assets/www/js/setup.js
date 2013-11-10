function onLoad() {
	document.addEventListener('deviceready', onDeviceReady, false);
}

function onDeviceReady() {
	console.log('ready running ver'+device.version);
	navigator.splashscreen.hide();
	var numOpen = window.localStorage.getItem("numOpen");
	console.log("Opened for the " + numOpen + "st/nd/rd/th time. (:");
	if (numOpen == null)
		window.localStorage.setItem("numOpen", 1);
	else
		window.localStorage.setItem("numOpen", Number(numOpen) + 1);
	// TODO: Check pet status
	initDB();
	checkPetStatus();
	setup();
	// TODO: Open Database (Places Visited, People Met)
}

function checkPetStatus() {
	
}

function setup() {
	var sliding = startPageX = startPixelOffset = pixelOffset = currentDoor = 0, doorCount = $('.door').length - 12;

	var i = document.location.href.search('room');
	if (i > 0) {
		pixelOffset = document.location.href.substr(i + 5) * -$('body').width();
		$('#doors').addClass('animate').css('transform',
				'translate3d(' + pixelOffset + 'px,0,0)');
	}

	$('html').on('mousedown touchstart', slideStart);
	$('html').on('mouseup touchend', slideEnd);
	$('html').on('mousemove touchmove', slide);

	function slideStart(event) {
		if (event.originalEvent.touches)
			event = event.originalEvent.touches[0];
		if (sliding == 0) {
			sliding = 1;
			startPageX = event.pageX;
		}
	}

	function slide(event) {
		event.preventDefault();
		if (event.originalEvent.touches)
			event = event.originalEvent.touches[0];
		var deltaSlide = event.pageX - startPageX;

		if (sliding == 1 && Math.abs(deltaSlide) > 30) {
			sliding = 2;
			startPixelOffset = pixelOffset;
		}

		if (sliding == 2) {
			var touchPixelRatio = 1 / 5; // 5x scrolling speed
			var endPixelOffset = startPixelOffset + deltaSlide
					/ touchPixelRatio;
			if (endPixelOffset < 0
					&& endPixelOffset > (doorCount - 1) * -$('body').width())
				pixelOffset = endPixelOffset;
			$('#doors').css('transform',
					'translate3d(' + pixelOffset + 'px,0,0)').removeClass();
		}
	}

	function slideEnd(event) {
		if (sliding == 2) {
			if (event.originalEvent.touches)
				event = event.originalEvent.touches[0];
			sliding = 0;
			currentDoor = -Math.round(pixelOffset / $('body').width());
			pixelOffset = currentDoor * -$('body').width();
			$('#doors').addClass('animate').css('transform',
					'translate3d(' + pixelOffset + 'px,0,0)');
		} else if (sliding == 1) {
			openDoor(currentDoor, 1);
		}
	}

	// animate door opening
	function openDoor(num, seq) {
		if (seq <= 3) {
			var x_offset = (doorCount - 1 + num * 3 + seq) * -$('body').width();
			$('#doors').css('transform', 'translate3d(' + x_offset + 'px,0,0)')
					.removeClass();
			setTimeout(function() {
				openDoor(num, seq + 1);
			}, 500);
		} else {
			document.location.href = "room" + num + ".html";
		}
	}
}
var sliding = startClientX = startPixelOffset = pixelOffset = currentSlide = 0,
 slideCount = document.getElementsByClassName('door').length;

window.addEventListener('mousedown touchstart', slideStart);
window.addEventListener('mouseup touchend', slideEnd);
window.addEventListener('mousemove touchmove', slide);

function slideStart(event) {
    if (event.originalEvent.touches)
      event = event.originalEvent.touches[0];
    if (sliding == 0) {
      sliding = 1;
      startClientX = event.clientX;
    }
  }

 function slide(event) {
    event.preventDefault();
    if (event.originalEvent.touches)
      event = event.originalEvent.touches[0];
     var deltaSlide = event.clientX - startClientX;

if (sliding == 1 && deltaSlide != 0) {
      sliding = 2;
      startPixelOffset = pixelOffset;
    }

if (sliding == 2) {
      var touchPixelRatio = 1;
      if ((currentSlide == 0 && event.clientX > startClientX) ||
          (currentSlide == slideCount - 1 && event.clientX < startClientX))
        touchPixelRatio = 3;
      pixelOffset = startPixelOffset + deltaSlide / touchPixelRatio;
      $('#doors').css('transform', 'translate3d(' + pixelOffset + 'px,0,0)').removeClass();
    }
  }

function slideEnd(event) {
    if (sliding == 2) {
      sliding = 0;
      currentSlide = pixelOffset < startPixelOffset ? currentSlide + 1 : currentSlide - 1;
      currentSlide = Math.min(Math.max(currentSlide, 0), slideCount - 1);
      pixelOffset = currentSlide * -$('body').width();
      document.getElementById('#temp').remove();
      ('<style id="temp">#doors.animate{transform:translate3d(' + pixelOffset + 'px,0,0)}</style>').appendTo('head');
      document.getElementById('doors').addClass('animate').css('transform', '');
    }
 }

/*var wallpaper, ctx, door = 0, seq = 0;
var nav = {};
for ( var i = 0; i < 7; i++) {
	var pic = new Image();
	pic.src = "img/nav" + i + ".png";
	nav[i] = pic;
}
*/

//init animation images
var openAnimate = {};
for ( var i = 0; i < 1; i++) {
	openAnimate[i] = {};
	for ( var j = 0; j < 3; j++) {
		var pic = new Image();
		pic.src = "img/door" + i + j + ".png";
		openAnimate[i][j] = pic;
	}
}

/*
//get access to all elements
function init() {
	wallpaper = document.getElementById('wallpaper');
	ctx = wallpaper.getContext("2d");
	splash = document.getElementById('splash');

	loading();
	setTimeout(paintWallpaper, 5000);
}

//loading page
function loading() {
	splash.innerHTML += ".";
	if (splash.style.display != 'none') {
		setTimeout(loading, 1000);
	}
}

function paintWallpaper() {
	wallpaper.width = window.innerWidth * 7;
	wallpaper.height = window.innerHeight;

	splash.style.display = 'none';
	drawBG();
}

function drawBG() {
	ctx.clearRect(0, 0, wallpaper.width, wallpaper.height);
	for ( var i = 0; i < 7; i++) {
		ctx.drawImage(nav[i], i * window.innerWidth, 0, window.innerWidth,
				window.innerHeight);
	}
}

var touchStarted = false;

window.addEventListener("touchstart", function() {
	touchStarted = true;
}, false);


window.addEventListener("touchmove", function(evt) {
	touchStarted = false;
}, false);


window.addEventListener("touchend", function() {
	if (touchStarted)
		// open door on tap.
		openDoor();
	else {
		// snaps doors to the window.
		door = Math.round(scrollX / window.innerWidth);
		scrollTo(door * window.innerWidth, 0);
	}
}, false);

function openDoor() {
	// redraw image
	for ( var i = 1; i < 4; i++) {
		setTimeout(openAnimation, i * 500);
	}
}

function openAnimation() {
	console.log("sequence = "+seq)
	ctx.drawImage(openAnimate[door][seq], door * window.innerWidth, 0,
			window.innerWidth, window.innerHeight);
	seq++;
	if(seq==3){
		seq=0;
	}
}

if (!window.onload) {
	window.onload = init;
}
*/
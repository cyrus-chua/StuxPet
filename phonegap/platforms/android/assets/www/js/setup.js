var wallpaper, ctx, door = 0, seq = 0;
var nav = {};
for ( var i = 0; i < 7; i++) {
	var pic = new Image();
	pic.src = "img/nav" + i + ".png";
	nav[i] = pic;
}
var openAnimate = {};
for ( var i = 0; i < 1; i++) {
	openAnimate[i] = {};
	for ( var j = 0; j < 3; j++) {
		var pic = new Image();
		pic.src = "img/door" + i + j + ".png";
		openAnimate[i][j] = pic;
	}
}

function init() {
	wallpaper = document.getElementById('wallpaper');
	ctx = wallpaper.getContext("2d");
	splash = document.getElementById('splash');

	loading();
	setTimeout(paintWallpaper, 5000);
}

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
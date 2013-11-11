var currentRoom;
function onLoad(){
	document.addEventListener('deviceready', onDeviceReady, false);
	currentRoom = {
			title : document.title,
			num : document.location.href.split('room')[1].charAt(0)
	}
	$('.room').load(function(){
		console.log('loadedroom');
		navigator.splashscreen.hide();
	});
}

function onDeviceReady(){
	document.addEventListener('backbutton', returnToDoor, false);
}

function returnToDoor(){	
	navigator.splashscreen.show();
	document.location.href = "index.html?room="+currentRoom.num;
}
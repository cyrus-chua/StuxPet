var currentRoom;
function onLoad(){
	document.addEventListener('deviceready', onDeviceReady, false);
	currentRoom = {
			title : document.title,
			num : document.location.href.split('room')[1].charAt(0)
	}
}

function onDeviceReady(){
	document.addEventListener('backbutton', returnToDoor, false);
}

function returnToDoor(){
	document.location.href = "index.html?room="+currentRoom.num;
}
var currentRoom;
function onLoad() {
	document.addEventListener('deviceready', onDeviceReady, false);
	currentRoom = {
		title : document.title,
		num : document.location.href.split('room')[1].charAt(0)
	}
	initDB();
	$('#pet').css('background', 'url(img/' + stats.type + '_happy.gif)');
	$('#pet').css('background-position', '45% 75%');
	$('#pet').css('background-repeat', 'no-repeat');
	$('#pet').css('background-size', '50%');
}

function onDeviceReady() {
	document.addEventListener('backbutton', returnToDoor, false);
	switch (currentRoom.num) {
	case "1":
		var birthday = new Date(Number(stats.birthday));
		var age = new Date(new Date - birthday);
		var hours = Math.floor(age / (60 * 60 * 1000));
		var minutes = Math.floor((age % (60 * 60 * 1000)) / (60 * 1000));
		var seconds = Math.floor(((age % (60 * 60 * 1000)) % 60) / 1000);
		$('#room1').append('&nbsp;&nbsp;name: ' + stats.name + '<br />');
		$('#room1').append(
				'&nbsp;&nbsp;species_name: ' + stats.species + '<br />');
		$('#room1').append(
				'&nbsp;&nbsp;birthday: ' + birthday.getDate() + '/'
						+ birthday.getMonth() + 1 + '/'
						+ birthday.getFullYear() + '<br />');
		$('#room1').append(
				'&nbsp;&nbsp;age: ' + hours + 'H ' + minutes + 'M ' + seconds
						+ 'S<br />');
		break;
	case "2":
		$('#bread, #cake, #vitamin').on('touchstart', function(event) {
			var parent = $(this);
			var id = parent.attr('id');
			parent.css('background-color', 'white');
			setTimeout(function() {
				parent.removeAttr('style');
				setTimeout(function(){
					if (id == "bread"){
						updateStats("hunger");
					} else if (id == "cake"){
						updateStats("happiness");
					} else if (id == "vitamin"){
						updateStats("health");
					}
				}, 200);
			}, 100);
		});
		break;
	}
}

function returnToDoor() {
	navigator.splashscreen.show();
	document.location.href = "index.html?room=" + currentRoom.num;
}
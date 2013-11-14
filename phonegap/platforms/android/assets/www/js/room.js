var currentRoom;
function onLoad() {
	document.addEventListener('deviceready', onDeviceReady, false);
	currentRoom = {
		title : document.title,
		num : document.location.href.split('room')[1].charAt(0)
	}
	initDB();
	$('#pet').css('background-image', 'url(img/' + stats.type + '_happy.gif)');
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
			$(this).off('touchstart');
			$(this).css('background-color', 'white');
			var id = $(this).attr('id');
			if (id == "bread"){
				$('#pet').css('background-image', 'url(img/' + stats.type + '_eating.gif)');
				updateStats("hunger");
			} else if (id == "cake"){
				$('#pet').css('background-image', 'url(img/' + stats.type + '_eatingcake.gif)');
				updateStats("happiness");
			} else if (id == "vitamin"){
				$('#pet').css('background-image', 'url(img/' + stats.type + '_eatingmedicine.gif)');
				updateStats("health");
			}
			$('#pet').css('background-position', '10% 75%');
			$('#pet').css('background-repeat', 'no-repeat');
			$('#pet').css('background-size', '90%');
			setTimeout(function(){
				$('#pet').css('background-image', 'url(img/' + stats.type + '_happy.gif)');
				$('#pet').css('background-position', '45% 75%');
				$('#pet').css('background-repeat', 'no-repeat');
				$('#pet').css('background-size', '50%');
			}, 5000);
		});
		break;
	}
}

function returnToDoor() {
	//navigator.splashscreen.show();
	document.location.href = "index.html?room=" + currentRoom.num;
}
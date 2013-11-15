var db, stats = {
	name : '',
	health : 0,
	hunger : 0,
	happiness : 0,
	fitness : 0,
	intel : 0,
	shit : 0
}, friends = [0];

function initDB() {
	db = window.sqlitePlugin.openDatabase({
		name : 'stuxpair'
	});
	var name = "";

	db.transaction(queryDB, onQueryError);
	if (stats.name == "awiefh") {
		name = prompt('Name your little monster :p');
		db.transaction(updateName, onQueryError);
	}

	function queryDB(tx) {
		tx.executeSql('SELECT * FROM stuxpet;', [], querySuccess, onQueryError);
	}

	function querySuccess(tx, results) {
		stats = results.rows.item(0);
		console.log(JSON.stringify(stats));
	}

	function updateName(tx) {
		var query = 'UPDATE stuxpet SET name = ? where name = \'awiefh\';';
		tx.executeSql(query, [ name ], nameSuccess, onQueryError);
	}

	function nameSuccess(tx, results) {
		console.log("named pet " + stats.name);
	}

	function onQueryError(error) {
		console.log("Error processing SQL: " + JSON.stringify(error.message)
				+ JSON.stringify(error.result));
	}

	window.updateStats = function(statsName) {
		db.transaction(function(tx) {
			var query = 'UPDATE stuxpet SET ' + statsName
					+ ' = 5 where birthday is NOT NULL;';
			tx.executeSql(query, [], function(tx, results) {
				console.log(statsName + " updated " + results.rowsAffected
						+ " (1)rows");
			}, function(error) {
				console.log("updateStatsError: " + error.message);
			});
		}, onQueryError);
	}
	
	window.updateShit = function(statsName) {
		db.transaction(function(tx) {
			var query = 'UPDATE stuxpet SET ' + statsName
					+ ' = 0 where social <> -1;';
			tx.executeSql(query, [], function(tx, results) {
				console.log(statsName + " updated " + results.rowsAffected
						+ " (1)rows");
			}, function(error) {
				console.log("updateStatsError: " + error.message);
			});
		}, onQueryError);
	}

	window.addStats = function(statsName) {
		db.transaction(function(tx) {
			var query = 'Select ' + statsName
					+ ' FROM stuxpet where social <> -1;';
			tx.executeSql(query, [], function(tx, results) {
				console.log(statsName + " updated " + results.rowsAffected + " (1)rows");
				for (var key in results.rows.item(0))
					var value = results.rows.item(0)[key];
				query = 'UPDATE stuxpet SET ' + statsName
				+ ' = ? where social <> -1;';
				tx.executeSql(query, [Number(value+1)], function(tx, results){
					console.log(statsName + " updated " + results.rowsAffected + " (1)rows");
				},
				function(error) {
					console.log("addStats: " + error.message);
				})
			}, 
			function(error) {
				console.log("addStats: " + error.message);
			});
		}, onQueryError);
	}	

}
	
function addFriend(text){
	var friendStats = text.split("/");
	db.transaction(insertFriend, function(err){
		console.log("addFriend: " + error.message);
	});
	
	function insertFriend(tx){
		var query = "INSERT INTO stuxpet (name, species_name, type, birthday, social) VALUES (?,?,?,?,-1)";
		tx.executeSql(query, friendStats,  function(tx, results) {
				console.log(statsName + " updated " + results.rowsAffected + " (1)rows");
		}, function(error) {
			console.log("insertFriend: " + error.message);
		});
	}
	addStats("social");
}

function getFriends(){
	db.transaction(queryDB, onQueryError);
	
	function queryDB(tx) {
		tx.executeSql('SELECT * FROM stuxpet where social = -1;', [], querySuccess, onQueryError);
	}
	
	function querySuccess(tx, results) {
		var len = results.rows.length;
		for (var i=0; i<len; i++){
			friends[i] = results.rows.item(i);
		}
		console.log(JSON.stringify(friends));
	}
	
	function onQueryError(error) {
		console.log("Error processing SQL: " + JSON.stringify(error.message)
				+ JSON.stringify(error.result));
	}
}
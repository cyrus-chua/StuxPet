var db, stats = {
	name : '',
	health : 0,
	hunger : 0,
	happiness : 0,
	fitness : 0,
	intel : 0,
	shit : 0
};
function initDB() {
	db = window.sqlitePlugin.openDatabase({
		name : 'stuxpair'
	});
	var name = "";

	db.transaction(queryDB, onQueryError);
	if (stats.name == "awiefh") {
		name = prompt('Name your little monster :p');
	}
	db.transaction(updateName, onQueryError);

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
					+ ' = 5 where birthday IS NOT NULL;';
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
					+ ' FROM stuxpet where birthday IS NOT NULL;';
			tx.executeSql(query, [], function(tx, results) {
				console.log(statsName + " updated " + results.rowsAffected + " (1)rows");
				for (var key in results.rows.item(0))
					var value = results.rows.item(0)[key];
				query = 'UPDATE stuxpet SET ' + statsName
				+ ' = ? where birthday IS NOT NULL;';
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
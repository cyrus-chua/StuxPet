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
	var db = window.sqlitePlugin.openDatabase({
		name : 'stuxpair'
	});
	var name = "";
	
	//if (stats.name === "awiefh") {
		name = prompt('Name your little monster :p');
	//}
	db.transaction(updateName, onQueryError);
	db.transaction(queryDB, onQueryError);

	function queryDB(tx) {
		tx.executeSql('SELECT * FROM stuxpet;', [], querySuccess, onQueryError);
	}

	function querySuccess(tx, results) {
		stats = results.rows.item(0);
		console.log(JSON.stringify(stats));
	}

	function updateName(tx) {
		var query = 'UPDATE stuxpet SET name = \'' + name + '\' WHERE name = \'awiefh\';';
		console.log(query);
		tx.executeSql(query, [], nameSuccess, onQueryError);
	}

	function nameSuccess(tx, results) {
		console.log("pet named as " + results.rows.item(0)._id);
	}

	function onQueryError(error) {
		console.log("Error processing SQL: " + JSON.stringify(error));
	}

}

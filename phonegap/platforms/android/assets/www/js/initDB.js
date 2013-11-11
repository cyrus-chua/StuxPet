var db, stats={health:0,hunger:0,happiness:0,fitness:0,intel:0};
function initDB(){
	var db = window.sqlitePlugin.openDatabase({name:'stuxpair'});
	
	db.transaction(queryDB, onQueryError);
	
	
	function queryDB(tx) {
        tx.executeSql('SELECT * FROM stuxpet;', [], querySuccess, onQueryError);
    }
	
	function querySuccess(tx, results){
		stats=results.rows.item(0);
		console.log(JSON.stringify(stats));
	}

	function onQueryError(error){
		console.log("Error processing SQL: "+JSON.stringify(error));
	}
}
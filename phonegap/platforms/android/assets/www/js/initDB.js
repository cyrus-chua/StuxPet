var db, stats={health:5,hunger:5,happy:5};
function initDB(){
	var db = window.sqlitePlugin.openDatabase({name:'stuxpair'});
	
	db.transaction(queryDB, onQueryError);
	
	function queryDB(tx) {
        tx.executeSql('SELECT * FROM stuxpet;', [], querySuccess, onQueryError);
    }
	
	function querySuccess(tx, results){
		console.log("Num rows(1):"+results.rows.length);
		for (var j = 0; j<results.rows.length;j++){
			console.log("row"+j+":"+results.rows.item(j));
		}
	}

	function onQueryError(error){
		console.log("Error processing SQL: "+JSON.stringify(error));
	}
}
var db, stats={health:5,hunger:5,happy:5};
function initDB(){
	db = window.sqlitePlugin.openDatabase({name: "stuxpair"});
	
	db.transaction(queryDB, onQueryError);
	
	function queryDB(tx) {
        tx.executeSql('SELECT * FROM stuxpet', [], querySuccess, onQueryError);
    }
	
	function querySucess(tx, results){
		console.log("Num rows(1):"+results.rows.length);
		for (var j = 0; j<results.rows.length;j++){
			console.log("col"+j+":"+results.rows[j]);
		}
		
	}

	function onQueryError(error){
		console.log("Error processing SQL: "+err.code);
	}
}
package com.stuxpair.stuxpet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class StuxPetDB {

	final Context context;
	StuxPetDBHelper DBHelper;
	SQLiteDatabase db;

	public StuxPetDB(Context ctx) {
		this.context = ctx;
		DBHelper = new StuxPetDBHelper(this.context);
	}

	// ---opens the database---
	public StuxPetDB open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	public boolean hasPet() {
		Cursor c = db.query(StuxPetDBHelper.TABLE_NAME, new String[] {
				StuxPetDBHelper.COLUMN_NAME_ID,
				StuxPetDBHelper.COLUMN_NAME_SPECIES }, null, null, null, null,
				null);
		return c != null;
	}

	// --- insert a pet into the database ---
	public long insertPet(String name, Long birthtime) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(StuxPetDBHelper.COLUMN_NAME_SPECIES, name);
		initialValues.put(StuxPetDBHelper.COLUMN_NAME_BIRTHDAY, birthtime);
		return db.insert(StuxPetDBHelper.TABLE_NAME, null, initialValues);
	}

	// --- retrieves pets stats --
	public Cursor getStats() {
		return db.query(StuxPetDBHelper.TABLE_NAME, new String[] {
				StuxPetDBHelper.COLUMN_NAME_SPECIES,
				StuxPetDBHelper.COLUMN_NAME_ID,
				StuxPetDBHelper.COLUMN_NAME_HEALTH,
				StuxPetDBHelper.COLUMN_NAME_HUNGER,
				StuxPetDBHelper.COLUMN_NAME_HAPPY,
				StuxPetDBHelper.COLUMN_NAME_FITNESS,
				StuxPetDBHelper.COLUMN_NAME_INTEL }, null, null, null, null,
				null);
	}

	// --- delete a particular contact ---
	public boolean deletePet(long rowId) {
		return db.delete(StuxPetDBHelper.TABLE_NAME,
				StuxPetDBHelper.COLUMN_NAME_ID + "=" + rowId, null) > 0;
	}

	// --- update a contact ---
	public boolean updateStats(long rowId, String species, int hunger,
			int health, int happiness) {

		Cursor c = getStats();
		
		ContentValues initialValues = new ContentValues();
		if (!species.isEmpty())
			initialValues.put(StuxPetDBHelper.COLUMN_NAME_SPECIES, species);
		
		hunger = c.getInt(c.getColumnIndex(StuxPetDBHelper.COLUMN_NAME_HUNGER)) + hunger;
		if (hunger < 0) hunger = 0;
		
		health = c.getInt(c.getColumnIndex(StuxPetDBHelper.COLUMN_NAME_HEALTH)) + health;
		if (health < 0) health = 0;
		
		happiness = c.getInt(c.getColumnIndex(StuxPetDBHelper.COLUMN_NAME_HAPPY)) + happiness;
		if (happiness < 0) happiness = 0;
			
		initialValues.put(StuxPetDBHelper.COLUMN_NAME_HUNGER, hunger);
		initialValues.put(StuxPetDBHelper.COLUMN_NAME_HEALTH, hunger);
		initialValues.put(StuxPetDBHelper.COLUMN_NAME_HAPPY, hunger);

		return db.update(StuxPetDBHelper.TABLE_NAME, initialValues,null,null) > 0;
	}
}

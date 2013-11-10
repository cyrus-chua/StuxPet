package com.stuxpair.stuxpet;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
		return c.getCount() != 0;
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
		return db.query(StuxPetDBHelper.TABLE_NAME, null, null, null, null,
				null, null);
	}

	public JSONObject getStatsJSON() {
		Cursor c = getStats();
		JSONObject obj = new JSONObject();
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getColumnCount(); i++) {
				try {
					obj.put(c.getColumnName(i), c.getString(i));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
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
		if (c.moveToFirst()) {
			if (!species.isEmpty())
				initialValues.put(StuxPetDBHelper.COLUMN_NAME_SPECIES, species);

			hunger = c.getInt(c
					.getColumnIndex(StuxPetDBHelper.COLUMN_NAME_HUNGER))
					+ hunger;
			if (hunger < 0)
				hunger = 0;

			health = c.getInt(c
					.getColumnIndex(StuxPetDBHelper.COLUMN_NAME_HEALTH))
					+ health;
			if (health < 0)
				health = 0;

			happiness = c.getInt(c
					.getColumnIndex(StuxPetDBHelper.COLUMN_NAME_HAPPY))
					+ happiness;
			if (happiness < 0)
				happiness = 0;

			initialValues.put(StuxPetDBHelper.COLUMN_NAME_HUNGER, hunger);
			initialValues.put(StuxPetDBHelper.COLUMN_NAME_HEALTH, health);
			initialValues.put(StuxPetDBHelper.COLUMN_NAME_HAPPY, happiness);
		}
		return db.update(StuxPetDBHelper.TABLE_NAME, initialValues, null, null) > 0;
	}
}

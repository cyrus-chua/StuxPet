package com.stuxpair.stuxpet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FriendsDB {

	final Context context;
	StuxPetDBHelper DBHelper;
	SQLiteDatabase db;

	public FriendsDB(Context ctx) {
		this.context = ctx;
		DBHelper = new StuxPetDBHelper(this.context);
	}

	// ---opens the database---
	public FriendsDB open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	public boolean hasPet(){
		Cursor c = db.query(StuxPetDBHelper.TABLE_NAME, new String[] {
				StuxPetDBHelper.COLUMN_NAME_ID,
				StuxPetDBHelper.COLUMN_NAME_SPECIES_NAME}, null, null, null,
				null, null);
		return c!=null;
	}

	
	// --- insert a pet into the database ---
	public long insertPet(String name, Long birthtime) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(StuxPetDBHelper.COLUMN_NAME_SPECIES_NAME, name);
		initialValues.put(StuxPetDBHelper.COLUMN_NAME_BIRTHDAY, birthtime);
		return db.insert(StuxPetDBHelper.TABLE_NAME, null, initialValues);
	}

	
	// --- retrieves pets stats --
	public Cursor getStats() {
		return db.query(StuxPetDBHelper.TABLE_NAME, new String[] {
				StuxPetDBHelper.COLUMN_NAME_SPECIES_NAME,
				StuxPetDBHelper.COLUMN_NAME_ID,
				StuxPetDBHelper.COLUMN_NAME_HEALTH,
				StuxPetDBHelper.COLUMN_NAME_HUNGER,
				StuxPetDBHelper.COLUMN_NAME_HAPPY,
				StuxPetDBHelper.COLUMN_NAME_FITNESS,
				StuxPetDBHelper.COLUMN_NAME_INTEL}, null, null, null,
				null, null);
	}
/*
	// --- retrieves a particular contact ---
	public Cursor getContact(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, StuxPetDBHelper.TABLE_NAME,
				new String[] { StuxPetDBHelper.COLUMN_NAME_ID,
						StuxPetDBHelper.COLUMN_NAME_CONTACTNAME,
						StuxPetDBHelper.COLUMN_NAME_CONTACTNUMBER },
				StuxPetDBHelper.COLUMN_NAME_ID + "=" + rowId, null, null, null,
				null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// --- delete a particular contact ---
	public boolean deleteContact(long rowId) {
		return db.delete(StuxPetDBHelper.TABLE_NAME,
				StuxPetDBHelper.COLUMN_NAME_ID + "=" + rowId, null) > 0;
	}

	// --- update a contact ---
	public boolean updateContact(long rowId, String name, String number) {

		ContentValues initialValues = new ContentValues();

		initialValues.put(StuxPetDBHelper.COLUMN_NAME_CONTACTNAME, name);
		initialValues.put(StuxPetDBHelper.COLUMN_NAME_CONTACTNUMBER, number);

		return db.update(StuxPetDBHelper.TABLE_NAME, initialValues,
				StuxPetDBHelper.COLUMN_NAME_ID + "=" + rowId, null) > 0;
	}
*/
}

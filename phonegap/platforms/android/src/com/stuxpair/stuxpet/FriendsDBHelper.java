package com.stuxpair.stuxpet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FriendsDBHelper extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "stuxpet";
	public static final String COLUMN_NAME_ID = "_id";
	public static final String COLUMN_NAME_SPECIES = "species";
	public static final String COLUMN_NAME_HEALTH = "health";
	public static final String COLUMN_NAME_HUNGER = "hunger";
	public static final String COLUMN_NAME_HAPPY = "happiness";
	public static final String COLUMN_NAME_BIRTHDAY = "birthday";
	public static final String COLUMN_NAME_FITNESS = "fitness";
	public static final String COLUMN_NAME_INTEL = "intel";
	public static final String COLUMN_NAME_STEPS = "steps";
	
	private static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME
			+ " (" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ COLUMN_NAME_SPECIES + " TEXT NOT NULL,"
			+ COLUMN_NAME_HEALTH + " INTEGER DEFAULT 5,"
			+ COLUMN_NAME_HUNGER + " INTEGER DEFAULT 5,"
			+ COLUMN_NAME_HAPPY + " INTEGER DEFAULT 5,"
			+ COLUMN_NAME_BIRTHDAY + " INTEGER NOT NULL,"
			+ COLUMN_NAME_FITNESS + " INTEGER DEFAULT 0,"
			+ COLUMN_NAME_INTEL + " INTEGER DEFAULT 0,"
			+ COLUMN_NAME_STEPS + " INTEGER DEFAULT 0);";

	private static final String SQL_DELETE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "stuxpair";

	public FriendsDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// upgrade policy here is simply to discard the data and start all over
		db.execSQL(SQL_DELETE);
		onCreate(db);
	}

}

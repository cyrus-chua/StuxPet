package com.stuxpair.stuxpet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StuxPetDBHelper extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "stuxpet";
	public static final String COLUMN_NAME_ID = "_id";
	public static final String COLUMN_NAME_NAME = "name";
	public static final String COLUMN_NAME_SPECIES_NAME = "species";
	public static final String COLUMN_NAME_TYPE = "type";
	public static final String COLUMN_NAME_HEALTH = "health";
	public static final String COLUMN_NAME_HUNGER = "hunger";
	public static final String COLUMN_NAME_HAPPY = "happiness";
	public static final String COLUMN_NAME_BIRTHDAY = "birthday";
	public static final String COLUMN_NAME_FITNESS = "fitness";
	public static final String COLUMN_NAME_INTEL = "intel";
	public static final String COLUMN_NAME_SOCIAL = "social";
	public static final String COLUMN_NAME_SHIT = "shit";
	
	private static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME
			+ " (" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ COLUMN_NAME_NAME + " TEXT DEFAULT \'awiefh\',"
			+ COLUMN_NAME_SPECIES_NAME + " TEXT,"
			+ COLUMN_NAME_TYPE + " TEXT,"
			+ COLUMN_NAME_HEALTH + " INTEGER DEFAULT 5,"
			+ COLUMN_NAME_HUNGER + " INTEGER DEFAULT 5,"
			+ COLUMN_NAME_HAPPY + " INTEGER DEFAULT 5,"
			+ COLUMN_NAME_BIRTHDAY + " TEXT NOT NULL,"
			+ COLUMN_NAME_FITNESS + " INTEGER DEFAULT 0,"
			+ COLUMN_NAME_INTEL + " INTEGER DEFAULT 0,"
			+ COLUMN_NAME_SHIT + " INTEGER DEFAULT 0,"
			+ COLUMN_NAME_SOCIAL + " INTEGER DEFAULT 0);";

	private static final String SQL_DELETE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;

	public static final String TYPE_BABY = "baby";
	public static final String TYPE_CHILD = "child";
	public static final String TYPE_TEEN1 = "teen1";
	public static final String TYPE_TEEN2 = "teen2";
	public static final String TYPE_ATHLETE = "athlete";
	public static final String TYPE_OTAKU = "otaku";
	public static final String TYPE_SOCIALITE = "socialite";
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "stuxpair";

	public StuxPetDBHelper(Context context) {
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

package com.prisch.model;

import android.database.sqlite.SQLiteDatabase;

public class Game {

    // ===== Database Scaffolding =====

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCRIPT);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SCRIPT);
        onCreate(db);
    }

    // ===== Constants =====

    public static final String TABLE = "game";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ACTIVE = "active";

    private static final String CREATE_SCRIPT = "CREATE TABLE " + TABLE + " ("
                                                + COLUMN_ID     + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + COLUMN_DATE   + " INTEGER NOT NULL UNIQUE,"
                                                + COLUMN_NAME   + " TEXT NOT NULL,"
                                                + COLUMN_ACTIVE + " NUMERIC NOT NULL);";
    private static final String DROP_SCRIPT = "DROP TABLE " + TABLE;

}

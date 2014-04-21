package com.prisch.model;

import android.database.sqlite.SQLiteDatabase;

public class Game {

    // ===== Convenience Methods =====

    public static String prefixTable(String column) {
        return TABLE + "." + column;
    }

    // ===== Database Scaffolding =====

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCRIPT);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SCRIPT);
        onCreate(db);
    }

    // ===== Constants =====

    public static final String ID = "_id";
    public static final String DATE = "date";
    public static final String NAME = "name";
    public static final String ACTIVE = "active";

    public static final String TABLE = "game";

    private static final String CREATE_SCRIPT = "CREATE TABLE " + TABLE + " ("
                                                + ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + DATE      + " INTEGER NOT NULL UNIQUE,"
                                                + NAME      + " TEXT NOT NULL,"
                                                + ACTIVE    + " NUMERIC NOT NULL);";
    private static final String DROP_SCRIPT = "DROP TABLE " + TABLE;

}

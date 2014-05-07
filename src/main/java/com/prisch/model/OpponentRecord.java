package com.prisch.model;

import android.database.sqlite.SQLiteDatabase;

public class OpponentRecord {

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
    public static final String GAME_ID = "game_id";
    public static final String ACTION = "action";

    public static final String TABLE = "opponent_record";

    private static final String CREATE_SCRIPT = "CREATE TABLE " + TABLE + " ("
                                                + ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + DATE      + " INTEGER NOT NULL,"
                                                + GAME_ID   + " INTEGER NOT NULL,"
                                                + ACTION    + " TEXT NOT NULL);";
    private static final String DROP_SCRIPT = "DROP TABLE " + TABLE;

}

package com.prisch.model;

import android.database.sqlite.SQLiteDatabase;

public class Team {

    // ===== Database Scaffolding =====

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCRIPT);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SCRIPT);
        onCreate(db);
    }

    // ===== Constants =====

    public static final String TABLE = "team";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GAME_ID = "game_id";
    public static final String COLUMN_PLAYER_ID = "player_id";
    public static final String COLUMN_POSITION = "position";

    private static final String CREATE_SCRIPT = "CREATE TABLE " + TABLE + " ("
                                                + COLUMN_ID         + " INTEGER PRIMARY KEY, "
                                                + COLUMN_GAME_ID    + " INTEGER NOT NULL"
                                                + COLUMN_PLAYER_ID  + " INTEGER NOT NULL"
                                                + COLUMN_POSITION   + " TEXT NOT NULL);";
    private static final String DROP_SCRIPT = "DROP TABLE " + TABLE;

}

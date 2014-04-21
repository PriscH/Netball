package com.prisch.model;

import android.database.sqlite.SQLiteDatabase;

public class Team {

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
    public static final String GAME_ID = "game_id";
    public static final String PLAYER_ID = "player_id";
    public static final String POSITION = "position";

    public static final String TABLE = "team";
    public static final String TABLE_JOIN_PLAYERS = String.format("%s LEFT OUTER JOIN %s", TABLE, Player.TABLE)
                                                    + String.format(" ON (%s = %s)", prefixTable(PLAYER_ID), Player.prefixTable(Player.ID));

    private static final String CREATE_SCRIPT = "CREATE TABLE " + TABLE + " ("
                                                + ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + GAME_ID   + " INTEGER NOT NULL,"
                                                + PLAYER_ID + " INTEGER NOT NULL,"
                                                + POSITION  + " TEXT NOT NULL);";
    private static final String DROP_SCRIPT = "DROP TABLE " + TABLE;

}

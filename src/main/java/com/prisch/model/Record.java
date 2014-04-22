package com.prisch.model;

import android.database.sqlite.SQLiteDatabase;

public class Record {

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
    public static final String TEAM_ID = "team_id";
    public static final String ACTION = "action";

    public static final String TABLE = "record";
    public static final String TABLE_JOIN_TEAM_JOIN_PLAYER = String.format("%s LEFT OUTER JOIN %s", TABLE, Team.TABLE)
                                                             + String.format(" ON (%s = %s)", prefixTable(TEAM_ID), Team.prefixTable(Team.ID))
                                                             + String.format(" LEFT OUTER JOIN %s", Player.TABLE)
                                                             + String.format(" ON (%s = %s)", Team.prefixTable(Team.PLAYER_ID), Player.prefixTable(Player.ID));

    private static final String CREATE_SCRIPT = "CREATE TABLE " + TABLE + " ("
                                                + ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + DATE      + " INTEGER NOT NULL,"
                                                + TEAM_ID   + " INTEGER NOT NULL,"
                                                + ACTION    + " TEXT NOT NULL);";
    private static final String DROP_SCRIPT = "DROP TABLE " + TABLE;
}

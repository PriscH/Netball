package com.prisch.model;

import android.database.sqlite.SQLiteDatabase;

public class Record {

    // ===== Database Scaffolding =====

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCRIPT);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SCRIPT);
        onCreate(db);
    }

    // ===== Constants =====

    public static final String TABLE = "record";
    // TODO: This is a clear example that referring to the constants simply doesn't work, maybe drop it or have a construction method
    public static final String TABLE_JOIN_TEAM_JOIN_PLAYER = TABLE + " LEFT OUTER JOIN " + Team.TABLE
                                                              + "  ON (record.team_id = team._id)"
                                                              + " LEFT OUTER JOIN " + Player.TABLE
                                                              + " ON (team.player_id = player._id)";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TEAM_ID = "team_id";
    public static final String COLUMN_ACTION = "action";

    private static final String CREATE_SCRIPT = "CREATE TABLE " + TABLE + " ("
            + COLUMN_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATE    + " INTEGER NOT NULL,"
            + COLUMN_TEAM_ID + " INTEGER NOT NULL,"
            + COLUMN_ACTION  + " TEXT NOT NULL);";
    private static final String DROP_SCRIPT = "DROP TABLE " + TABLE;
}

package com.prisch.model;

import android.database.sqlite.SQLiteDatabase;

public class Player {

    private final long id;
    private final String name;

    // ===== Constructors =====

    public Player(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // ===== Accessors =====

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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
    public static final String NAME = "name";

    public static final String TABLE = "player";

    private static final String CREATE_SCRIPT = "CREATE TABLE " + TABLE + " ("
                                                + ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + NAME  + " TEXT NOT NULL UNIQUE);";
    private static final String DROP_SCRIPT = "DROP TABLE " + TABLE;
}


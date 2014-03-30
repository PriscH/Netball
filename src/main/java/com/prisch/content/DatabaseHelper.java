package com.prisch.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.prisch.model.Player;

public class DatabaseHelper extends SQLiteOpenHelper {

    // ===== Inherited Operations =====

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Player.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Player.onUpgrade(db, oldVersion, newVersion);
    }

    // ===== Constants =====

    private static final String DATABASE_NAME = "netball.db";
    private static final int DATABASE_VERSION = 1;
}

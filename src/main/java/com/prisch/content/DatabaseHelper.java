package com.prisch.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.prisch.model.Game;
import com.prisch.model.Player;
import com.prisch.model.Record;
import com.prisch.model.TeamMember;

public class DatabaseHelper extends SQLiteOpenHelper {

    // ===== Inherited Operations =====

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Player.onCreate(db);
        Game.onCreate(db);
        TeamMember.onCreate(db);
        Record.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Player.onUpgrade(db, oldVersion, newVersion);
        Game.onUpgrade(db, oldVersion, newVersion);
        TeamMember.onUpgrade(db, oldVersion, newVersion);
        Record.onUpgrade(db, oldVersion, newVersion);
    }

    // ===== Constants =====

    private static final String DATABASE_NAME = "netball.db";
    private static final int DATABASE_VERSION = 1;
}

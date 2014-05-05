package com.prisch.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TeamMember {

    private final long id;
    private final long gameId;
    private final long playerId;
    private final Position position;
    private final boolean active;

    // ===== Constructors =====

    public TeamMember(Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(ID));
        this.gameId = cursor.getLong(cursor.getColumnIndex(GAME_ID));
        this.playerId = cursor.getLong(cursor.getColumnIndex(PLAYER_ID));
        this.position = Position.fromAcronym(cursor.getString(cursor.getColumnIndex(POSITION)));
        this.active = (cursor.getInt(cursor.getColumnIndex(ACTIVE)) > 0);
    }

    public TeamMember(long id, long gameId, long playerId, Position position, boolean active) {
        this.id = id;
        this.gameId = gameId;
        this.playerId = playerId;
        this.position = position;
        this.active = active;
    }

    // ===== Accessors =====

    public long getId() {
        return id;
    }

    public long getGameId() {
        return gameId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
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
    public static final String GAME_ID = "game_id";
    public static final String PLAYER_ID = "player_id";
    public static final String POSITION = "position";
    public static final String ACTIVE = "active";

    public static final String TABLE = "team_member";
    public static final String TABLE_JOIN_PLAYERS = String.format("%s LEFT OUTER JOIN %s", TABLE, Player.TABLE)
                                                    + String.format(" ON (%s = %s)", prefixTable(PLAYER_ID), Player.prefixTable(Player.ID));

    private static final String CREATE_SCRIPT = "CREATE TABLE " + TABLE + " ("
                                                + ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + GAME_ID   + " INTEGER NOT NULL,"
                                                + PLAYER_ID + " INTEGER NOT NULL,"
                                                + POSITION  + " TEXT NOT NULL,"
                                                + ACTIVE    + " NUMERIC NOT NULL);";
    private static final String DROP_SCRIPT = "DROP TABLE " + TABLE;

}

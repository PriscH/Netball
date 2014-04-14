package com.prisch.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.prisch.model.Game;
import com.prisch.model.Player;
import com.prisch.model.Team;

import java.util.HashMap;
import java.util.Map;

public class NetballContentProvider extends ContentProvider {

    // ===== Internal Constants =====

    private static final String AUTHORITY = "com.prisch.NetballTracker";

    private static final int CODE_PLAYERS = 1;
    private static final int CODE_GAMES = 2;
    private static final int CODE_TEAMS = 3;
    private static final int CODE_ACTIVE_TEAM = 301;

    private static final String PATH_PLAYERS = "players";
    private static final String PATH_GAMES = "games";
    private static final String PATH_TEAMS = "teams";
    private static final String PATH_ACTIVE_TEAM = PATH_TEAMS + "/active";

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        URI_MATCHER.addURI(AUTHORITY, PATH_PLAYERS, CODE_PLAYERS);
        URI_MATCHER.addURI(AUTHORITY, PATH_GAMES, CODE_GAMES);
        URI_MATCHER.addURI(AUTHORITY, PATH_TEAMS, CODE_TEAMS);
        URI_MATCHER.addURI(AUTHORITY, PATH_ACTIVE_TEAM, CODE_ACTIVE_TEAM);
    }

    private static final Map<Integer, String> URI_QUERY_TABLE_MAP = new HashMap<Integer, String>();
    static {
        URI_QUERY_TABLE_MAP.put(CODE_PLAYERS, Player.TABLE);
        URI_QUERY_TABLE_MAP.put(CODE_GAMES, Game.TABLE);
        URI_QUERY_TABLE_MAP.put(CODE_TEAMS, Team.TABLE_JOIN_PLAYERS);
    }

    private static final Map<Integer, String> URI_WRITE_TABLE_MAP = new HashMap<Integer, String>();
    static {
        URI_WRITE_TABLE_MAP.put(CODE_PLAYERS, Player.TABLE);
        URI_WRITE_TABLE_MAP.put(CODE_GAMES, Game.TABLE);
        URI_WRITE_TABLE_MAP.put(CODE_TEAMS, Team.TABLE);
    }

    private static final Map<Integer, String> URI_PATH_MAP = new HashMap<Integer, String>();
    static {
        URI_PATH_MAP.put(CODE_PLAYERS, PATH_PLAYERS);
        URI_PATH_MAP.put(CODE_GAMES, PATH_GAMES);
        URI_PATH_MAP.put(CODE_TEAMS, PATH_TEAMS);
    }

    // ===== Exposed Constants =====

    public static final Uri URI_PLAYERS = Uri.parse("content://" + AUTHORITY + "/" + PATH_PLAYERS);
    public static final Uri URI_GAMES = Uri.parse("content://" + AUTHORITY + "/" + PATH_GAMES);
    public static final Uri URI_TEAMS = Uri.parse("content://" + AUTHORITY + "/" + PATH_TEAMS);
    public static final Uri URI_ACTIVE_TEAM = Uri.parse("content://" + AUTHORITY + "/" + PATH_ACTIVE_TEAM);

    // ===== Fields =====

    private DatabaseHelper databaseHelper;

    // ===== Inherited Operations =====

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        Cursor cursor;
        int uriType = URI_MATCHER.match(uri);
        switch (uriType) {
            case CODE_ACTIVE_TEAM:
                cursor = getActiveTeam();
                break;
            default:
                if (!URI_QUERY_TABLE_MAP.containsKey(uriType)) {
                    throw new IllegalArgumentException("Unknown URI: " + uri);
                }
                queryBuilder.setTables(URI_QUERY_TABLE_MAP.get(uriType));

                cursor = queryBuilder.query(databaseHelper.getWritableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        int uriType = URI_MATCHER.match(uri);
        if (!URI_WRITE_TABLE_MAP.containsKey(uriType)) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        long id = database.insert(URI_WRITE_TABLE_MAP.get(uriType), null, values);

        getContext().getContentResolver().notifyChange(uri, null);

        String path = URI_PATH_MAP.get(uriType);
        return Uri.parse(path + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        int uriType = URI_MATCHER.match(uri);
        if (!URI_WRITE_TABLE_MAP.containsKey(uriType)) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        int count = database.delete(URI_WRITE_TABLE_MAP.get(uriType), selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        int uriType = URI_MATCHER.match(uri);
        if (!URI_WRITE_TABLE_MAP.containsKey(uriType)) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        int count = database.update(URI_WRITE_TABLE_MAP.get(uriType), values, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    // ===== Query Operations =====

    private Cursor getActiveTeam() {
        final String QUERY = "SELECT * FROM " + Team.TABLE_JOIN_PLAYERS
                             + " WHERE " + Team.COLUMN_GAME_ID + " IN "
                             + "(SELECT " + Game.COLUMN_ID + " FROM " + Game.TABLE
                               + " WHERE " + Game.COLUMN_ACTIVE + " = 1)";

        return databaseHelper.getWritableDatabase().rawQuery(QUERY, null);
    }
}

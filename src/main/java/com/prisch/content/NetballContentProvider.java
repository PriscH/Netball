package com.prisch.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.prisch.model.Player;

public class NetballContentProvider extends ContentProvider {

    // ===== Internal Constants =====

    private static final String AUTHORITY = "com.prisch.NetballTracker";

    private static final int CODE_PLAYERS = 1;
    private static final String PATH_PLAYERS = "players";

    // ===== Exposed Constants =====

    public static final Uri URI_PLAYERS = Uri.parse("content://" + AUTHORITY + "/" + PATH_PLAYERS);

    // ===== Fields =====

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private DatabaseHelper databaseHelper;

    // ===== Static Initialization =====

    static {
        URI_MATCHER.addURI(AUTHORITY, PATH_PLAYERS, CODE_PLAYERS);
    }

    // ===== Inherited Operations =====

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = URI_MATCHER.match(uri);
        switch (uriType) {
            case CODE_PLAYERS:
                queryBuilder.setTables(Player.TABLE);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
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

        long id;
        int uriType = URI_MATCHER.match(uri);
        switch (uriType) {
            case CODE_PLAYERS:
                id = database.insert(Player.TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }


        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(PATH_PLAYERS + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        int count;
        int uriType = URI_MATCHER.match(uri);
        switch (uriType) {
            case CODE_PLAYERS:
                count = database.delete(Player.TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        int count;
        int uriType = URI_MATCHER.match(uri);
        switch (uriType) {
            case CODE_PLAYERS:
                count = database.update(Player.TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}

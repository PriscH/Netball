package com.prisch.repositories;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerRepository {

    private Context context;

    public PlayerRepository(Context context) {
        this.context = context;
    }

    // ===== Interface =====

    public long createPlayer(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Player.COLUMN_NAME, name);

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_PLAYERS, contentValues);
        return ContentUris.parseId(resultUri);
    }

    public void deletePlayer(Long id) {
        String where = Player.COLUMN_ID + "=?";
        String[] parameters = new String[] {id.toString()};

        context.getContentResolver().delete(NetballContentProvider.URI_PLAYERS, where, parameters);
    }

    public void renamePlayer(Long id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Player.COLUMN_NAME, name);

        String where = Player.COLUMN_ID + "=?";
        String[] parameters = new String[] {id.toString()};

        context.getContentResolver().update(NetballContentProvider.URI_PLAYERS, contentValues, where, parameters);
    }

    public Map<Long, String> getPlayers() {
        Cursor cursor = context.getContentResolver().query(NetballContentProvider.URI_PLAYERS, null, null, null, null);

        Map<Long, String> playerMap = new HashMap<Long, String>();
        while (cursor.moveToNext()) {
            long playerId = cursor.getLong(cursor.getColumnIndex(Player.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(Player.COLUMN_NAME));
            playerMap.put(playerId, name);
        }

        return playerMap;
    }
}

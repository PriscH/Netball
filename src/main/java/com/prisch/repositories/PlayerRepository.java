package com.prisch.repositories;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Player;

public class PlayerRepository {

    private Context context;

    public PlayerRepository(Context context) {
        this.context = context;
    }

    // ===== Interface =====

    public long createPlayer(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Player.NAME, name);

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_PLAYERS, contentValues);
        return ContentUris.parseId(resultUri);
    }

    public void deletePlayer(Long id) {
        String where = Player.ID + "=?";
        String[] parameters = new String[] {id.toString()};

        context.getContentResolver().delete(NetballContentProvider.URI_PLAYERS, where, parameters);
    }

    public void renamePlayer(Long id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Player.NAME, name);

        String where = Player.ID + "=?";
        String[] parameters = new String[] {id.toString()};

        context.getContentResolver().update(NetballContentProvider.URI_PLAYERS, contentValues, where, parameters);
    }

    public Loader<Cursor> getAllPlayers() {
        return new CursorLoader(context, NetballContentProvider.URI_PLAYERS, null, null, null, Player.NAME);
    }
}

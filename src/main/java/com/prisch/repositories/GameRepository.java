package com.prisch.repositories;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Game;

import java.util.Date;

public class GameRepository {

    private Context context;

    public GameRepository(Context context) {
        this.context = context;
    }

    // ===== Interface =====

    public long createGame(Date date, String name, boolean active) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Game.COLUMN_DATE, date.getTime());
        contentValues.put(Game.COLUMN_NAME, name);
        contentValues.put(Game.COLUMN_ACTIVE, active);

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_GAMES, contentValues);
        return ContentUris.parseId(resultUri);
    }

    public Loader<Cursor> getActiveGame() {
        String where = Game.COLUMN_ACTIVE + "=?";
        String[] parameters = new String[] {"1"};

        return new CursorLoader(context, NetballContentProvider.URI_GAMES, null, where, parameters, null);
    }

    public void stopActiveGame() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Game.COLUMN_ACTIVE, false);

        String where = Game.COLUMN_ACTIVE + "=?";
        String[] parameters = new String[] {"1"};

        context.getContentResolver().update(NetballContentProvider.URI_GAMES, contentValues, where, parameters);
    }

}

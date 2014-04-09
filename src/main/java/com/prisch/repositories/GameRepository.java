package com.prisch.repositories;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
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

    public long createGame(Date date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Game.COLUMN_DATE, date.getTime());

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_GAMES, contentValues);
        return ContentUris.parseId(resultUri);
    }

}

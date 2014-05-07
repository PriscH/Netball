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
        contentValues.put(Game.DATE, date.getTime());
        contentValues.put(Game.NAME, name);
        contentValues.put(Game.TEAM_SCORE, 0);
        contentValues.put(Game.OPPONENT_SCORE, 0);
        contentValues.put(Game.ACTIVE, active);

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_GAMES, contentValues);
        return ContentUris.parseId(resultUri);
    }

    public Loader<Cursor> getAllGames() {
        return new CursorLoader(context, NetballContentProvider.URI_GAMES, null, null, null, Game.DATE + " DESC");
    }

    public Cursor getActiveGame() {
        String where = Game.ACTIVE + "=?";
        String[] parameters = new String[] {"1"};

        return context.getContentResolver().query(NetballContentProvider.URI_GAMES, null, where, parameters, null);
    }

    public Loader<Cursor> getActiveGameLoader() {
        String where = Game.ACTIVE + "=?";
        String[] parameters = new String[] {"1"};

        return new CursorLoader(context, NetballContentProvider.URI_GAMES, null, where, parameters, null);
    }

    public void updateGameScores(Long gameId, Long teamScore, Long opponentScore) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Game.TEAM_SCORE, teamScore);
        contentValues.put(Game.OPPONENT_SCORE, opponentScore);

        String where = Game.ID + "=?";
        String[] parameters = new String[] {Long.toString(gameId)};

        context.getContentResolver().update(NetballContentProvider.URI_GAMES, contentValues, where, parameters);
    }

    public void stopActiveGame() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Game.ACTIVE, false);

        String where = Game.ACTIVE + "=?";
        String[] parameters = new String[] {"1"};

        context.getContentResolver().update(NetballContentProvider.URI_GAMES, contentValues, where, parameters);
    }

}

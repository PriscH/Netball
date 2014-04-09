package com.prisch.repositories;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Position;
import com.prisch.model.Team;

public class TeamRepository {

    private Context context;

    public TeamRepository(Context context) {
        this.context = context;
    }

    // ===== Interface =====

    public long createPlayerOnTeam(long gameId, long playerId, Position position) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Team.COLUMN_GAME_ID, gameId);
        contentValues.put(Team.COLUMN_PLAYER_ID, playerId);
        contentValues.put(Team.COLUMN_POSITION, position.toString());

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_TEAMS, contentValues);
        return ContentUris.parseId(resultUri);
    }

}

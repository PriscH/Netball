package com.prisch.repositories;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Position;
import com.prisch.model.Team;

import java.util.HashMap;
import java.util.Map;

public class TeamRepository {

    private Context context;

    public TeamRepository(Context context) {
        this.context = context;
    }

    // ===== Interface =====

    public void createTeam(long gameId, Map<Long, Position> teamMap) {
        for (long playerId : teamMap.keySet()) {
            createPlayerOnTeam(gameId, playerId, teamMap.get(playerId));
        }
    }

    public long createPlayerOnTeam(long gameId, long playerId, Position position) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Team.COLUMN_GAME_ID, gameId);
        contentValues.put(Team.COLUMN_PLAYER_ID, playerId);
        contentValues.put(Team.COLUMN_POSITION, position.toString());

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_TEAMS, contentValues);
        return ContentUris.parseId(resultUri);
    }

    public Map<Long, Position> getLatestTeam() {
        Cursor cursor = context.getContentResolver().query(NetballContentProvider.URI_TEAMS, null, null, null, Team.COLUMN_GAME_ID);

        Map<Long, Position> teamMap = new HashMap<Long, Position>();
        while (cursor.moveToNext() && teamMap.size() < 7) {
            long playerId = cursor.getLong(cursor.getColumnIndex(Team.COLUMN_PLAYER_ID));
            Position position = Position.fromString(cursor.getString(cursor.getColumnIndex(Team.COLUMN_POSITION)));
            teamMap.put(playerId, position);
        }

        return teamMap;
    }

}

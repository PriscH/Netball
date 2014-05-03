package com.prisch.repositories;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Position;
import com.prisch.model.Team;

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
        contentValues.put(Team.GAME_ID, gameId);
        contentValues.put(Team.PLAYER_ID, playerId);
        contentValues.put(Team.POSITION, position.getAcronym());

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_TEAMS, contentValues);
        return ContentUris.parseId(resultUri);
    }

    public Loader<Cursor> getActiveTeam() {
        return new CursorLoader(context, NetballContentProvider.URI_ACTIVE_TEAM, null, null, null, null);
    }

    public Cursor getTeamForGame(long gameId) {
        String where = Team.GAME_ID + "=?";
        String[] parameters = new String[] {Long.toString(gameId)};

        return context.getContentResolver().query(NetballContentProvider.URI_TEAMS, null, where, parameters, null);
    }
}

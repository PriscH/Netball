package com.prisch.repositories;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Position;
import com.prisch.model.TeamMember;

import java.util.*;

public class TeamMemberRepository {

    private Context context;

    public TeamMemberRepository(Context context) {
        this.context = context;
    }

    // ===== Interface =====

    public void createTeam(long gameId, Map<Long, Position> teamMap) {
        for (long playerId : teamMap.keySet()) {
            createTeamMember(gameId, playerId, teamMap.get(playerId));
        }
    }

    public void updateTeam(long gameId, Map<Long, Position> teamMap) {
        List<TeamMember> currentTeamMembers = new LinkedList<TeamMember>();
        Cursor currentTeamCursor = getTeamForGame(gameId);
        while (currentTeamCursor.moveToNext()) {
            currentTeamMembers.add(new TeamMember(currentTeamCursor));
        }

        ContentValues deactivateContentValues = new ContentValues();
        deactivateContentValues.put(TeamMember.ACTIVE, false);

        String whereGame = TeamMember.GAME_ID + "=?";
        String[] gameParameters = new String[] {Long.toString(gameId)};

        context.getContentResolver().update(NetballContentProvider.URI_TEAMS, deactivateContentValues, whereGame, gameParameters);

        for (Long playerId : teamMap.keySet()) {
            Position position = teamMap.get(playerId);

            TeamMember currentTeamMember = null;
            for (TeamMember teamMember : currentTeamMembers) {
                if (playerId.equals(teamMember.getPlayerId()) && position.equals(teamMember.getPosition())) {
                    currentTeamMember = teamMember;
                    break;
                }
            }

            if (currentTeamMember == null) {
                createTeamMember(gameId, playerId, position);
            } else {
                activateTeamMember(currentTeamMember.getId());
            }
        }
    }

    public long createTeamMember(long gameId, long playerId, Position position) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TeamMember.GAME_ID, gameId);
        contentValues.put(TeamMember.PLAYER_ID, playerId);
        contentValues.put(TeamMember.POSITION, position.getAcronym());
        contentValues.put(TeamMember.ACTIVE, true);

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_TEAMS, contentValues);
        return ContentUris.parseId(resultUri);
    }

    public void activateTeamMember(long teamMemberId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TeamMember.ACTIVE, true);

        String where = TeamMember.ID + "=?";
        String[] parameters = new String[] {Long.toString(teamMemberId)};

        context.getContentResolver().update(NetballContentProvider.URI_TEAMS, contentValues, where, parameters);
    }

    public Loader<Cursor> getActiveTeam() {
        return new CursorLoader(context, NetballContentProvider.URI_ACTIVE_TEAM, null, null, null, null);
    }

    public Cursor getTeamForGame(long gameId) {
        String where = TeamMember.GAME_ID + "=?";
        String[] parameters = new String[] {Long.toString(gameId)};

        return context.getContentResolver().query(NetballContentProvider.URI_TEAMS, TeamMember.DEFAULT_PROJECTION, where, parameters, null);
    }

    public Loader<Cursor> getTeamForGameLoader(long gameId) {
        String where = TeamMember.GAME_ID + "=?";
        String[] parameters = new String[] {Long.toString(gameId)};

        return new CursorLoader(context, NetballContentProvider.URI_TEAMS, TeamMember.DEFAULT_PROJECTION, where, parameters, null);
    }
}

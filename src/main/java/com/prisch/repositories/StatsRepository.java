package com.prisch.repositories;

import android.content.Context;
import android.database.Cursor;
import com.prisch.model.*;

public class StatsRepository {

    private Context context;
    private TeamMemberRepository teamMemberRepository;
    private RecordRepository recordRepository;

    public StatsRepository(Context context) {
        this.context = context;
        this.teamMemberRepository = new TeamMemberRepository(context);
        this.recordRepository = new RecordRepository(context);
    }

    // ===== Interface =====

    public GameStats getStatsForGame(Long gameId) {
        GameStats gameStats = new GameStats(gameId);

        // Add the recorded actions for the players
        Cursor recordsCursor = recordRepository.getRecordsForGame(gameId);
        while (recordsCursor.moveToNext()) {
            String playerName = recordsCursor.getString(recordsCursor.getColumnIndex(Player.NAME));
            Position playerPosition = Position.fromAcronym(recordsCursor.getString(recordsCursor.getColumnIndex(TeamMember.POSITION)));
            Action playerAction = Action.fromString(recordsCursor.getString(recordsCursor.getColumnIndex(Record.ACTION)));

            PlayerStats playerStats = gameStats.getOrCreate(playerName, playerPosition);
            playerStats.incrementActionCount(playerAction);
        }

        // Add any players for whom no actions were recorded, but that are included in the team
        Cursor teamCursor = teamMemberRepository.getTeamForGame(gameId);
        while (teamCursor.moveToNext()) {
            String playerName = teamCursor.getString(teamCursor.getColumnIndex(Player.NAME));
            Position playerPosition = Position.fromAcronym(teamCursor.getString(teamCursor.getColumnIndex(TeamMember.POSITION)));
            gameStats.getOrCreate(playerName, playerPosition);
        }

        return gameStats;
    }

}

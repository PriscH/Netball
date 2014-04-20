package com.prisch.repositories;

import android.content.Context;
import android.database.Cursor;
import com.prisch.model.*;

public class StatsRepository {

    private Context context;
    private RecordRepository recordRepository;

    public StatsRepository(Context context) {
        this.context = context;
        this.recordRepository = new RecordRepository(context);
    }

    // ===== Interface =====

    public GameStats getStatsForGame(Long gameId) {
        GameStats gameStats = new GameStats(gameId);

        Cursor recordsCursor = recordRepository.getRecordsForGame(gameId);
        while (recordsCursor.moveToNext()) {
            String playerName = recordsCursor.getString(recordsCursor.getColumnIndex(Player.COLUMN_NAME));
            Position playerPosition = Position.fromAcronym(recordsCursor.getString(recordsCursor.getColumnIndex(Team.COLUMN_POSITION)));
            Action playerAction = Action.fromString(recordsCursor.getString(recordsCursor.getColumnIndex(Record.COLUMN_ACTION)));

            PlayerStats playerStats = gameStats.getOrCreate(playerName, playerPosition);
            playerStats.incrementActionCount(playerAction);
        }

        return gameStats;
    }

}

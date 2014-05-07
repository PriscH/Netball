package com.prisch.repositories;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.*;

import java.util.Date;

public class RecordRepository {

    private Context context;

    private GameRepository gameRepository;

    public RecordRepository(Context context) {
        this.context = context;

        this.gameRepository = new GameRepository(context);
    }

    // ===== Interface =====

    public long createRecord(Date date, Long teamAssignmentId, Action action) {
        if (action == Action.GOAL) {
            long gameId = 0;
            long teamScore = 0;
            long opponentScore = 0;

            Cursor activeGameCursor = gameRepository.getActiveGame();
            if (activeGameCursor.moveToNext()) {
                gameId = activeGameCursor.getLong(activeGameCursor.getColumnIndex(Game.ID));
                teamScore = activeGameCursor.getLong(activeGameCursor.getColumnIndex(Game.TEAM_SCORE));
                opponentScore = activeGameCursor.getLong(activeGameCursor.getColumnIndex(Game.OPPONENT_SCORE));
            }

            ++teamScore;
            gameRepository.updateGameScores(gameId, teamScore, opponentScore);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Record.DATE, date.getTime());
        contentValues.put(Record.TEAM_ID, teamAssignmentId);
        contentValues.put(Record.ACTION, action.toString());

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_RECORDS, contentValues);
        return ContentUris.parseId(resultUri);
    }

    public Cursor getRecordsForGame(Long gameId) {
        String where = TeamMember.GAME_ID + "=?";
        String[] parameters = new String[] {gameId.toString()};
        return context.getContentResolver().query(NetballContentProvider.URI_RECORDS, null, where, parameters, null);
    }
}

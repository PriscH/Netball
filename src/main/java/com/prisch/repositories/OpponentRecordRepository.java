package com.prisch.repositories;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Game;
import com.prisch.model.OpponentAction;
import com.prisch.model.OpponentRecord;

import java.util.Date;

public class OpponentRecordRepository {

    private Context context;

    private GameRepository gameRepository;

    public OpponentRecordRepository(Context context) {
        this.context = context;

        this.gameRepository = new GameRepository(context);
    }

    // ===== Interface =====

    public long createRecord(Date date, OpponentAction action) {
        long gameId = 0;
        long teamScore = 0;
        long opponentScore = 0;

        Cursor activeGameCursor = gameRepository.getActiveGame();
        if (activeGameCursor.moveToNext()) {
            gameId = activeGameCursor.getLong(activeGameCursor.getColumnIndex(Game.ID));
            teamScore = activeGameCursor.getLong(activeGameCursor.getColumnIndex(Game.TEAM_SCORE));
            opponentScore = activeGameCursor.getLong(activeGameCursor.getColumnIndex(Game.OPPONENT_SCORE));
        }

        if (action == OpponentAction.GOAL) {
            ++opponentScore;
            gameRepository.updateGameScores(gameId, teamScore, opponentScore);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(OpponentRecord.DATE, date.getTime());
        contentValues.put(OpponentRecord.GAME_ID, gameId);
        contentValues.put(OpponentRecord.ACTION, action.toString());

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_OPPONENT_RECORDS, contentValues);
        return ContentUris.parseId(resultUri);
    }
}

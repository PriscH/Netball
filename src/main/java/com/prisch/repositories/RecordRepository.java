package com.prisch.repositories;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Action;
import com.prisch.model.Record;
import com.prisch.model.Team;

import java.util.Date;

public class RecordRepository {

    private Context context;

    public RecordRepository(Context context) {
        this.context = context;
    }

    // ===== Interface =====

    public long createRecord(Date date, Long teamAssignmentId, Action action) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Record.COLUMN_DATE, date.getTime());
        contentValues.put(Record.COLUMN_TEAM_ID, teamAssignmentId);
        contentValues.put(Record.COLUMN_ACTION, action.toString());

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_RECORDS, contentValues);
        return ContentUris.parseId(resultUri);
    }

    public Cursor getRecordsForGame(Long gameId) {
        String where = Team.COLUMN_GAME_ID + "=?";
        String[] parameters = new String[] {gameId.toString()};
        return context.getContentResolver().query(NetballContentProvider.URI_RECORDS, null, where, parameters, null);
    }
}

package com.prisch.views;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.prisch.R;
import com.prisch.model.Game;
import com.prisch.util.DateUtils;

import java.util.Date;

public class GameAdapter extends CursorAdapter {

    private static final int LAYOUT_ID = R.layout.list_games;

    // ===== Constructor =====

    public GameAdapter(Context context, Cursor cursor) {
        super(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER);
    }

    // ===== Adapter Operations =====

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(LAYOUT_ID, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView)view.findViewById(R.id.text_gameName);
        nameTextView.setText(cursor.getString(cursor.getColumnIndex(Game.NAME)));

        TextView dateTextView = (TextView)view.findViewById(R.id.text_gameDate);
        dateTextView.setText(DateUtils.formatDate(new Date(cursor.getLong(cursor.getColumnIndex(Game.DATE)))));

        TextView resultTextView = (TextView)view.findViewById(R.id.text_gameResult);

        long teamScore = cursor.getLong(cursor.getColumnIndex(Game.TEAM_SCORE));
        long opponentScore = cursor.getLong(cursor.getColumnIndex(Game.OPPONENT_SCORE));

        StringBuilder resultBuilder = new StringBuilder();
        if (cursor.getLong(cursor.getColumnIndex(Game.ACTIVE)) > 0) {
            resultBuilder.append("IN PROGRESS");
            resultTextView.setTextColor(context.getResources().getColor(android.R.color.holo_blue_bright));
        } else if (teamScore > opponentScore) {
            resultBuilder.append("WON");
            resultTextView.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
        } else if (teamScore < opponentScore) {
            resultBuilder.append("LOST");
            resultTextView.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
        } else {
            resultBuilder.append("DREW");
            resultTextView.setTextColor(context.getResources().getColor(android.R.color.holo_orange_light));
        }
        resultBuilder.append(" " + teamScore + " - " + opponentScore);

        resultTextView.setText(resultBuilder.toString());
    }
}
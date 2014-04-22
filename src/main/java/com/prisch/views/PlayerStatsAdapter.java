package com.prisch.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.prisch.R;

public class PlayerStatsAdapter extends ArrayAdapter<PlayerStatsListItem> {

    private static final int LAYOUT_ID = R.layout.list_playerstats;

    public PlayerStatsAdapter(Context context) {
        super(context, LAYOUT_ID);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PlayerStatsListItem listItem = getItem(position);

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listItemView = layoutInflater.inflate(LAYOUT_ID, parent, false);

        TextView nameText = (TextView)listItemView.findViewById(R.id.text_actionName);
        nameText.setText(listItem.getItemName());

        TextView valueText = (TextView)listItemView.findViewById(R.id.text_actionCount);
        valueText.setText(listItem.getItemValue());

        if (listItem.isHeader()) {
            nameText.setTypeface(null, Typeface.BOLD);
            nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);

            valueText.setTypeface(null, Typeface.BOLD);
            valueText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        }

        return listItemView;
    }
}

package com.prisch.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.prisch.R;

public class PlayerStatsAdapter extends ArrayAdapter<PlayerStatsListItem> {

    private static final int HEADER_LAYOUT_ID = R.layout.list_header;
    private static final int ITEM_LAYOUT_ID = R.layout.list_playerstats;

    public PlayerStatsAdapter(Context context) {
        super(context, ITEM_LAYOUT_ID);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listView;
        final PlayerStatsListItem listItem = getItem(position);
        if (listItem.isHeader()) {
            listView = layoutInflater.inflate(HEADER_LAYOUT_ID, parent, false);

            TextView nameText = (TextView)listView.findViewById(R.id.text_header);
            nameText.setText(listItem.getItemName());
        } else {
            listView = layoutInflater.inflate(ITEM_LAYOUT_ID, parent, false);

            TextView nameText = (TextView)listView.findViewById(R.id.text_actionName);
            nameText.setText(listItem.getItemName());

            TextView valueText = (TextView)listView.findViewById(R.id.text_actionCount);
            valueText.setText(listItem.getItemValue());
        }

        return listView;
    }
}

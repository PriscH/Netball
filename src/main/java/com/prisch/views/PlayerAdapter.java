package com.prisch.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.prisch.R;
import com.prisch.model.Player;

public class PlayerAdapter extends ArrayAdapter<Player> {

    private static final int ITEM_LAYOUT_ID = R.layout.list_players;

    public PlayerAdapter(Context context) {
        super(context, ITEM_LAYOUT_ID);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listViewItem = layoutInflater.inflate(ITEM_LAYOUT_ID, parent, false);

        TextView nameText = (TextView)listViewItem.findViewById(R.id.text_playerName);
        nameText.setText(getItem(position).getName());

        return listViewItem;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }
}
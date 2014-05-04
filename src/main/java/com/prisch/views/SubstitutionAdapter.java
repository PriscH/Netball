package com.prisch.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.prisch.R;
import com.prisch.model.Player;

import java.util.ArrayList;
import java.util.List;

public class SubstitutionAdapter extends BaseAdapter {

    private static final int NUMBER_OF_HEADERS = 2;
    private static final String TEAM_HEADER = "Team Members";
    private static final String OTHER_HEADER = "Other Players";

    private static final int HEADER_LAYOUT_ID = R.layout.list_header;
    private static final int PLAYER_LAYOUT_ID = R.layout.list_players;

    private Context context;

    private List<Player> teamPlayers = new ArrayList<Player>();
    private List<Player> otherPlayers = new ArrayList<Player>();

    // ===== Constructor =====

    public SubstitutionAdapter(Context context) {
        this.context = context;
    }

    // ===== Own Interface =====

    public void addTeamPlayers(List<Player> teamPlayers) {
        this.teamPlayers.addAll(teamPlayers);
    }

    public void addOtherPlayers(List<Player> otherPlayers) {
        this.otherPlayers.addAll(otherPlayers);
    }

    public void clearAll() {
        this.teamPlayers.clear();
        this.otherPlayers.clear();
    }

    // ===== Inherited Operations =====

    @Override
    public int getCount() {
        return teamPlayers.size() + otherPlayers.size() + NUMBER_OF_HEADERS;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return TEAM_HEADER;
        }
        if (position > 0 && position < teamPlayers.size() + 1) {
            return teamPlayers.get(position - 1);
        }
        if (position == teamPlayers.size() + 1) {
            return OTHER_HEADER;
        }
        if (position >= teamPlayers.size() + NUMBER_OF_HEADERS) {
            return otherPlayers.get(position - teamPlayers.size() - NUMBER_OF_HEADERS);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        Object item = getItem(position);
        if (item instanceof Player) {
            return ((Player)item).getId();
        }
        return -1L;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listViewItem;
        final Object listItem = getItem(position);
        if (listItem instanceof Player) {
            listViewItem = layoutInflater.inflate(PLAYER_LAYOUT_ID, parent, false);

            TextView nameText = (TextView)listViewItem.findViewById(R.id.text_playerName);
            nameText.setText(((Player)listItem).getName());
        } else {
            listViewItem = layoutInflater.inflate(HEADER_LAYOUT_ID, parent, false);

            TextView headerText = (TextView)listViewItem.findViewById(R.id.text_header);
            headerText.setText(listItem.toString());
        }

        return listViewItem;
    }
}
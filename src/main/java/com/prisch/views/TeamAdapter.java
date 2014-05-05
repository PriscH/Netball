package com.prisch.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.prisch.R;
import com.prisch.model.Player;
import com.prisch.model.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamAdapter extends BaseAdapter {

    private static final int HEADER_LAYOUT_ID = R.layout.list_header;
    private static final int PLAYER_LAYOUT_ID = R.layout.list_players;

    private final Context context;
    private final String[] groupsInOrder;
    private final boolean displayHeadings;

    private Map<Long, Position> playerPositionMap = new HashMap<Long, Position>();
    private Map<String, List<Player>> playerGroupMap = new HashMap<String, List<Player>>();

    // ===== Constructor =====

    public TeamAdapter(Context context, String[] groupsInOrder) {
        this(context, groupsInOrder, true);
    }

    public TeamAdapter(Context context, String[] groupsInOrder, boolean displayHeadings) {
        this.context = context;
        this.groupsInOrder = groupsInOrder;
        this.displayHeadings = displayHeadings;
    }

    // ===== Custom Operations =====

    public void putPlayers(String group, List<Player> players) {
        playerGroupMap.put(group, players);
        this.notifyDataSetChanged();
    }

    public void setPlayerPositionMap(Map<Long, Position> playerPositionMap) {
        this.playerPositionMap = playerPositionMap;
        this.notifyDataSetChanged();
    }

    public void clearPlayers() {
        this.playerGroupMap.clear();
        this.notifyDataSetChanged();
    }

    // ===== Adapter Operations =====

    @Override
    public int getCount() {
        int count = 0;

        for (List<Player> players : playerGroupMap.values()) {
            count += players.size();
        }

        if (displayHeadings) {
            count += groupsInOrder.length;
        }

        return count;
    }

    @Override
    public Object getItem(int position) {
        int remaining = position;

        for (String group : groupsInOrder) {
            if (displayHeadings) {
                if (remaining == 0) {
                    return group;
                }

                --remaining; // The group counts as an item
            }

            List<Player> players = playerGroupMap.get(group);
            if (players != null) {
                if (players.size() > remaining) {
                    return players.get(remaining);
                }

                remaining -= players.size(); // The initial position is past this group
            }
        }

        return null; // Index is too large
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

        View itemView;
        final Object item = getItem(position);

        if (item instanceof Player) {
            Player player = (Player)item;

            itemView = layoutInflater.inflate(PLAYER_LAYOUT_ID, parent, false);

            TextView nameText = (TextView)itemView.findViewById(R.id.text_playerName);
            nameText.setText(player.getName());

            if (playerPositionMap.containsKey(player.getId())) {
                TextView positionText = (TextView)itemView.findViewById(R.id.text_playerPosition);
                positionText.setText(playerPositionMap.get(player.getId()).getAcronym());
            }
        } else {
            itemView = layoutInflater.inflate(HEADER_LAYOUT_ID, parent, false);

            TextView headerText = (TextView)itemView.findViewById(R.id.text_header);
            headerText.setText(item.toString());
        }

        return itemView;
    }

}

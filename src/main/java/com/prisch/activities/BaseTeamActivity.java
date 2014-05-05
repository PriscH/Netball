package com.prisch.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.prisch.R;
import com.prisch.model.Player;
import com.prisch.model.Position;
import com.prisch.views.TeamAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class BaseTeamActivity extends Activity {

    // List of Positions that have not yet been assigned
    private List<Position> outstandingPositions = new LinkedList<Position>();

    // Map from a Player's ID to the Position currently assigned to that Player
    private Map<Long, Position> playerPositionMap = new HashMap<Long, Position>(7);

    private ListView listView;
    private TeamAdapter teamAdapter;

    // ===== Constructor =====

    protected BaseTeamActivity() {
        for (Position position : Position.values()) {
            outstandingPositions.add(position);
        }
    }

    // ===== Abstract Operations =====

    protected abstract TeamAdapter createAdapter();

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);

        teamAdapter = createAdapter();
        listView = (ListView)findViewById(R.id.listview_players);
        listView.setAdapter(teamAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getAdapter().getItem(position) instanceof Player) {
                    togglePlayerPosition(id);
                }
            }
        });

        getActionBar().setCustomView(R.layout.actionbar_selectteam);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        updateActionBar();
    }

    // ===== Accessors =====

    protected TeamAdapter getAdapter() {
        return teamAdapter;
    }

    protected Map<Long, Position> getPlayerPositionMap() {
        return playerPositionMap;
    }

    protected View findDoneButton() {
        return findViewById(R.id.custombutton_selectDone);
    }

    // ===== Operations =====

    protected void initialisePlayerPositionMap(Map<Long, Position> playerPositionMap) {
        this.playerPositionMap = playerPositionMap;
        this.teamAdapter.setPlayerPositionMap(playerPositionMap);

        for (Position position : playerPositionMap.values()) {
            outstandingPositions.remove(position);
        }

        updateActionBar();
    }

    private void togglePlayerPosition(long playerId) {
        if (playerPositionMap.containsKey(playerId)) {
            Position previousPosition = playerPositionMap.remove(playerId);
            outstandingPositions.add(0, previousPosition);
        } else if (!outstandingPositions.isEmpty()) {
            Position currentPosition = outstandingPositions.remove(0);
            playerPositionMap.put(playerId, currentPosition);
        }

        getAdapter().setPlayerPositionMap(playerPositionMap);
        updateActionBar();
    }

    private void updateActionBar() {
        TextView instructionText = (TextView)findViewById(R.id.text_selectPosition);
        View acceptButton = findViewById(R.id.custombutton_selectDone);

        if (outstandingPositions.isEmpty()) {
            instructionText.setText("");
            acceptButton.setEnabled(true);
        } else {
            instructionText.setText("Select the " + outstandingPositions.get(0));
            acceptButton.setEnabled(false);
        }
    }
}

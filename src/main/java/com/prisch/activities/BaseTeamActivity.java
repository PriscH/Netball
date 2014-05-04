package com.prisch.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.prisch.R;
import com.prisch.model.Position;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseTeamActivity extends Activity {

    // List of Positions that have not yet been assigned
    private List<Position> outstandingPositions = new LinkedList<Position>();

    // Map from a Player's ID to the Position currently assigned to that Player
    private Map<Long, Position> teamMap = new HashMap<Long, Position>(7);

    // ===== Constructor =====

    protected BaseTeamActivity() {
        for (Position position : Position.values()) {
            outstandingPositions.add(position);
        }
    }

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setCustomView(R.layout.actionbar_selectteam);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        updateActionBar();
    }

    // ===== Accessors =====

    protected View findDoneButton() {
        return findViewById(R.id.custombutton_selectDone);
    }

    protected Map<Long, Position> getTeamMap() {
        return teamMap;
    }

    // ===== Operations =====

    protected void togglePlayerPosition(long playerId, TextView positionView) {
        if (teamMap.containsKey(playerId)) {
            Position previousPosition = teamMap.remove(playerId);
            outstandingPositions.add(0, previousPosition);
            positionView.setText("");
        } else if (!outstandingPositions.isEmpty()) {
            Position currentPosition = outstandingPositions.remove(0);
            teamMap.put(playerId, currentPosition);
            positionView.setText(currentPosition.getAcronym());
        }

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

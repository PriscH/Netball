package com.prisch.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.prisch.R;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TeamActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter adapter;

    private List<String> outstandingPositions = new LinkedList<String>();
    private Map<Long, String> teamMap = new HashMap<Long, String>(7);

    // ===== Constructor =====

    public TeamActivity() {
        outstandingPositions.add("GS");
        outstandingPositions.add("GA");
        outstandingPositions.add("WA");
        outstandingPositions.add("C");
        outstandingPositions.add("WD");
        outstandingPositions.add("GD");
        outstandingPositions.add("GK");
    }

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);

        adapter = new SimpleCursorAdapter(this, R.layout.list_teamassign, null, new String[] {Player.COLUMN_NAME}, new int[] {R.id.playerName}, 0);

        ListView listView = (ListView)findViewById(R.id.playersListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                togglePlayerPosition(id, (TextView)view.findViewById(R.id.playerPosition));
            }
        });

        getLoaderManager().initLoader(0, null, this);

        getActionBar().setCustomView(R.layout.actionbar_selectteam);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        updateActionBar();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, NetballContentProvider.URI_PLAYERS, null, null, null, Player.COLUMN_NAME);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    // ===== Event Handlers =====

    private void togglePlayerPosition(long playerId, TextView positionView) {
        String previousPosition = teamMap.remove(playerId);

        if (outstandingPositions.isEmpty()) {
            positionView.setText("");
        } else {
            String position = outstandingPositions.remove(0);
            teamMap.put(playerId, position);
            positionView.setText(position);
        }

        if (previousPosition != null) {
            outstandingPositions.add(0, previousPosition);
        }

        updateActionBar();
    }

    // ===== Helper Methods =====

    private void updateActionBar() {
        TextView instructionText = (TextView)getActionBar().getCustomView().findViewById(R.id.text_selectPosition);
        Button acceptButton = (Button)getActionBar().getCustomView().findViewById(R.id.button_teamAccept);
        if (outstandingPositions.isEmpty()) {
            instructionText.setText("");
            acceptButton.setEnabled(true);
        } else {
            instructionText.setText("Select the " + outstandingPositions.get(0));
            acceptButton.setEnabled(false);
        }
    }
}

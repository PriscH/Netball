package com.prisch.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.prisch.R;
import com.prisch.model.Player;
import com.prisch.model.Position;
import com.prisch.repositories.GameRepository;
import com.prisch.repositories.PlayerRepository;
import com.prisch.repositories.TeamRepository;

import java.util.*;

public class TeamActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PLAYER_LOADER = 0;

    private PlayerRepository playerRepository;
    private GameRepository gameRepository;
    private TeamRepository teamRepository;

    private CursorAdapter adapter;

    private List<Position> outstandingPositions = new LinkedList<Position>();
    private Map<Long, Position> teamMap = new HashMap<Long, Position>(7);

    // ===== Constructor =====

    public TeamActivity() {
        for (Position position : Position.values()) {
            outstandingPositions.add(position);
        }
    }

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);

        playerRepository = new PlayerRepository(this);
        gameRepository = new GameRepository(this);
        teamRepository = new TeamRepository(this);

        adapter = new SimpleCursorAdapter(this, R.layout.list_players, null, new String[] {Player.COLUMN_NAME}, new int[] {R.id.text_playerName}, 0);

        ListView listView = (ListView)findViewById(R.id.listview_players);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                togglePlayerPosition(id, (TextView)view.findViewById(R.id.text_playerPosition));
            }
        });

        getLoaderManager().initLoader(PLAYER_LOADER, null, this);

        getActionBar().setCustomView(R.layout.actionbar_selectteam);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        View doneButton = findViewById(R.id.custombutton_selectDone);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptTeam();
            }
        });

        updateActionBar();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return playerRepository.getAllPlayers();
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

    private void acceptTeam() {
        long gameId = gameRepository.createGame(new Date());
        teamRepository.createTeam(gameId, teamMap);

        Intent positionsIntent = new Intent(getApplicationContext(), PositionsActivity.class);
        startActivity(positionsIntent);
    }

    private void togglePlayerPosition(long playerId, TextView positionView) {
        Position previousPosition = teamMap.remove(playerId);

        if (outstandingPositions.isEmpty()) {
            positionView.setText("");
        } else {
            Position position = outstandingPositions.remove(0);
            teamMap.put(playerId, position);
            positionView.setText(position.toString());
        }

        if (previousPosition != null) {
            outstandingPositions.add(0, previousPosition);
        }

        updateActionBar();
    }

    // ===== Helper Methods =====

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

package com.prisch.activities;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

    // List of Positions that have not yet been assigned
    private List<Position> outstandingPositions = new LinkedList<Position>();
    // Map from a Player's ID to the Position currently assigned to that Player
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

        adapter = new SimpleCursorAdapter(this, R.layout.list_players, null, new String[] {Player.NAME}, new int[] {R.id.text_playerName}, 0);

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
                showTeamNameDialog();
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

    private void showTeamNameDialog() {
        final EditText nameInput = new EditText(this);

        final Dialog dialog = new AlertDialog.Builder(this)
            .setTitle("Record Game")
            .setMessage("Enter a name for the game")
            .setView(nameInput)
            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    acceptTeam(nameInput.getText().toString());
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do Nothing
                }
            }).create();

        nameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        dialog.show();
    }

    private void acceptTeam(String name) {
        long gameId = gameRepository.createGame(new Date(), name, true);
        teamRepository.createTeam(gameId, teamMap);

        Intent positionsIntent = new Intent(getApplicationContext(), PositionsActivity.class);
        startActivity(positionsIntent);
    }

    private void togglePlayerPosition(long playerId, TextView positionView) {
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

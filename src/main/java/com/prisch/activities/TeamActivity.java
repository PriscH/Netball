package com.prisch.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
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
import com.prisch.repositories.GameRepository;
import com.prisch.repositories.PlayerRepository;
import com.prisch.repositories.TeamRepository;

import java.util.Date;

public class TeamActivity extends BaseTeamActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PLAYER_LOADER = 0;

    private PlayerRepository playerRepository;
    private GameRepository gameRepository;
    private TeamRepository teamRepository;

    private CursorAdapter adapter;



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

        findDoneButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTeamNameDialog();
            }
        });

        getLoaderManager().initLoader(PLAYER_LOADER, null, this);
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
        teamRepository.createTeam(gameId, getTeamMap());

        Intent positionsIntent = new Intent(getApplicationContext(), PositionsActivity.class);
        startActivity(positionsIntent);
    }

}

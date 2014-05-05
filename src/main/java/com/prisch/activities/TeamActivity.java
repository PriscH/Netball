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
import android.widget.EditText;
import com.prisch.model.Player;
import com.prisch.repositories.GameRepository;
import com.prisch.repositories.PlayerRepository;
import com.prisch.repositories.TeamMemberRepository;
import com.prisch.views.TeamAdapter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TeamActivity extends BaseTeamActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PLAYER_LOADER = 0;

    private static final String DEFAULT_HEADING = "Default";

    private PlayerRepository playerRepository;
    private GameRepository gameRepository;
    private TeamMemberRepository teamMemberRepository;

    // ===== Inherited Operations =====

    protected TeamAdapter createAdapter() {
        return new TeamAdapter(this, new String[] {DEFAULT_HEADING}, false);
    }

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playerRepository = new PlayerRepository(this);
        gameRepository = new GameRepository(this);
        teamMemberRepository = new TeamMemberRepository(this);

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
        List<Player> players = new LinkedList<Player>();
        while (data.moveToNext()) {
            players.add(new Player(data.getLong(data.getColumnIndex(Player.ID)), data.getString(data.getColumnIndex(Player.NAME))));
        }
        getAdapter().putPlayers(DEFAULT_HEADING, players);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        getAdapter().clearPlayers();
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
        teamMemberRepository.createTeam(gameId, getPlayerPositionMap());

        Intent positionsIntent = new Intent(getApplicationContext(), PositionsActivity.class);
        startActivity(positionsIntent);
    }

}

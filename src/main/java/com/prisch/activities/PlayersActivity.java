package com.prisch.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.prisch.R;
import com.prisch.model.Player;
import com.prisch.repositories.PlayerRepository;

public class PlayersActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static int PLAYER_LOADER = 0;

    private PlayerRepository playerRepository;

    private CursorAdapter adapter;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);

        playerRepository = new PlayerRepository(this);

        adapter = new SimpleCursorAdapter(this, R.layout.list_players, null, new String[] {Player.COLUMN_NAME}, new int[] {R.id.text_playerName}, 0);

        ListView listView = (ListView)findViewById(R.id.listview_players);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showModifyPlayerDialog(position);
                return true;
            }
        });

        getLoaderManager().initLoader(PLAYER_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.players, menu);
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_addplayer:
                showAddPlayerDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // ===== Helper Methods =====

    private void showAddPlayerDialog() {
        final EditText nameInput = new EditText(this);

        final Dialog dialog = new AlertDialog.Builder(this)
            .setTitle("Add new player")
            .setMessage("Enter the player's name")
            .setView(nameInput)
            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    playerRepository.createPlayer(nameInput.getText().toString());
                }
            })
            .setNeutralButton("Add Another", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    playerRepository.createPlayer(nameInput.getText().toString());
                    showAddPlayerDialog();
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

    private void showRenamePlayerDialog(final int position) {
        final EditText nameInput = new EditText(this);
        Cursor cursor = (Cursor)adapter.getItem(position);
        nameInput.append(cursor.getString(cursor.getColumnIndex(Player.COLUMN_NAME)));

        final Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("Rename player")
                .setMessage("Enter the player's name")
                .setView(nameInput)
                .setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Long id = adapter.getItemId(position);
                        playerRepository.renamePlayer(id, nameInput.getText().toString());
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

    private void showModifyPlayerDialog(final int position) {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_modifyplayer, null);
        final Dialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();

        dialogView.findViewById(R.id.button_changeName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                showRenamePlayerDialog(position);
            }
        });

        dialogView.findViewById(R.id.button_deletePlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerRepository.deletePlayer(adapter.getItemId(position));

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

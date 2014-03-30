package com.prisch.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.prisch.R;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Player;

public class PlayersActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter adapter;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);

        ListView listView = (ListView)findViewById(R.id.playersListView);
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[] {Player.COLUMN_NAME}, new int[] {android.R.id.text1}, 0);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showModifyPlayerDialog(position);
                return true;
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.players, menu);
        return true;
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

        new AlertDialog.Builder(this)
            .setTitle("Add new player")
            .setMessage("Enter the player's name")
            .setView(nameInput)
            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addPlayer(nameInput.getText().toString());
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do Nothing
                }
            }).show();
    }

    private void showRenamePlayerDialog(final int position) {
        final EditText nameInput = new EditText(this);
        Cursor cursor = (Cursor)adapter.getItem(position);
        nameInput.append(cursor.getString(cursor.getColumnIndex(Player.COLUMN_NAME)));

        new AlertDialog.Builder(this)
                .setTitle("Rename player")
                .setMessage("Enter the player's name")
                .setView(nameInput)
                .setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        renamePlayer(position, nameInput.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do Nothing
                    }
                }).show();
    }

    private void showModifyPlayerDialog(final int position) {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_modifyplayer, null);
        final Dialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();

        dialogView.findViewById(R.id.button_changename).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                showRenamePlayerDialog(position);
            }
        });

        dialogView.findViewById(R.id.button_deleteplayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String where = Player.COLUMN_ID + "=?";
                String[] parameters = new String[] {Long.toString(adapter.getItemId(position))};
                getContentResolver().delete(NetballContentProvider.URI_PLAYERS, where, parameters);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addPlayer(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Player.COLUMN_NAME, name);
        getContentResolver().insert(NetballContentProvider.URI_PLAYERS, contentValues);
    }

    private void renamePlayer(int position, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Player.COLUMN_NAME, name);
        String where = Player.COLUMN_ID + "=?";
        String[] parameters = new String[] {Long.toString(adapter.getItemId(position))};
        getContentResolver().update(NetballContentProvider.URI_PLAYERS, contentValues, where, parameters);
    }
}

package com.prisch.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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

        final ListView playersListView = (ListView)findViewById(R.id.playersListView);
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[] {Player.COLUMN_NAME}, new int[] {android.R.id.text1}, 0);
        playersListView.setAdapter(adapter);

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

    private void addPlayer(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Player.COLUMN_NAME, name);
        getContentResolver().insert(NetballContentProvider.URI_PLAYERS, contentValues);
    }
}
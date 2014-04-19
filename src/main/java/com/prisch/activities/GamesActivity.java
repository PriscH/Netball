package com.prisch.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.prisch.R;
import com.prisch.model.Game;
import com.prisch.repositories.GameRepository;
import com.prisch.util.DateUtils;

import java.util.Date;

public class GamesActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static int GAMES_LOADER = 0;

    private GameRepository gameRepository;

    private SimpleCursorAdapter adapter;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games);

        gameRepository = new GameRepository(this);

        adapter = new SimpleCursorAdapter(this, R.layout.list_games, null, new String[] {Game.COLUMN_NAME, Game.COLUMN_DATE}, new int[] {R.id.text_gameName, R.id.text_gameDate}, 0);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == cursor.getColumnIndex(Game.COLUMN_DATE)) {
                    TextView textView = (TextView)view;
                    textView.setText(DateUtils.formatDate(new Date(cursor.getLong(columnIndex))));
                    return true;
                }
                return false;
            }
        });

        ListView listView = (ListView)findViewById(R.id.listview_games);
        listView.setAdapter(adapter);

        getLoaderManager().initLoader(GAMES_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return gameRepository.getAllGames();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

}

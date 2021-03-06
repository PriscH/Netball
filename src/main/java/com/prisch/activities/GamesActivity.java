package com.prisch.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.prisch.R;
import com.prisch.repositories.GameRepository;
import com.prisch.views.GameAdapter;

public class GamesActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static int GAMES_LOADER = 0;

    private GameRepository gameRepository;

    private GameAdapter adapter;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games);

        gameRepository = new GameRepository(this);

        adapter = new GameAdapter(this, null);

        ListView listView = (ListView)findViewById(R.id.listview_games);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent statsIntent = new Intent(getApplicationContext(), GameStatsActivity.class);
                statsIntent.putExtra(GameStatsActivity.GAME_ID_KEY, id);
                startActivity(statsIntent);
            }
        });

        getLoaderManager().initLoader(GAMES_LOADER, null, this);
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

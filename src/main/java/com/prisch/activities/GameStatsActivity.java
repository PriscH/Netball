package com.prisch.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import com.prisch.R;
import com.prisch.loaders.GameStatsLoader;
import com.prisch.model.GameStats;
import com.prisch.model.PlayerStats;
import com.prisch.views.PlayerStatsAdapter;
import com.prisch.views.PlayerStatsListItem;

import java.util.LinkedList;
import java.util.List;

public class GameStatsActivity extends Activity implements LoaderManager.LoaderCallbacks<GameStats> {

    public static final String GAME_ID_KEY = "GAME_ID_KEY";

    private final static int GAME_STATS_LOADER = 0;

    private PlayerStatsAdapter adapter;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamestats);

        adapter = new PlayerStatsAdapter(this);
        ListView listView = (ListView)findViewById(R.id.listView_gameStats);
        listView.setAdapter(adapter);

        getLoaderManager().initLoader(GAME_STATS_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public Loader<GameStats> onCreateLoader(int id, Bundle args) {
        Long gameId = getIntent().getLongExtra(GAME_ID_KEY, 0);
        return new GameStatsLoader(this, gameId);
    }

    @Override
    public void onLoadFinished(Loader<GameStats> loader, GameStats data) {
        List<PlayerStatsListItem> listItems = new LinkedList<PlayerStatsListItem>();
        for (PlayerStats playerStats : data.getPlayerStats()) {
            listItems.addAll(PlayerStatsListItem.buildFrom(playerStats));
        }

        adapter.addAll(listItems);
    }

    @Override
    public void onLoaderReset(Loader<GameStats> loader) {
        adapter.clear();
    }
}

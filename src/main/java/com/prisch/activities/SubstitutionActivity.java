package com.prisch.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ListView;
import com.prisch.R;
import com.prisch.loaders.SubstitutionLoader;
import com.prisch.model.Player;
import com.prisch.views.SubstitutionAdapter;

import java.util.List;
import java.util.Map;

public class SubstitutionActivity extends BaseTeamActivity implements LoaderManager.LoaderCallbacks<Map<Boolean, List<Player>>> {

    public static final String GAME_KEY = "GAME_KEY";

    private static final int SUBSTITUTION_LOADER = 0;

    private SubstitutionAdapter adapter;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);

        adapter = new SubstitutionAdapter(this);
        ListView playerListView = (ListView)findViewById(R.id.listview_players);
        playerListView.setAdapter(adapter);

        getLoaderManager().initLoader(SUBSTITUTION_LOADER, null, this);
    }

    @Override
    public SubstitutionLoader onCreateLoader(int id, Bundle args) {
        return new SubstitutionLoader(this, getIntent().getLongExtra(GAME_KEY, 0));
    }

    @Override
    public void onLoadFinished(Loader<Map<Boolean, List<Player>>> loader, Map<Boolean, List<Player>> data) {
        adapter.addTeamPlayers(data.get(true));
        adapter.addOtherPlayers(data.get(false));
    }

    @Override
    public void onLoaderReset(Loader<Map<Boolean, List<Player>>> loader) {
        adapter.clearAll();
    }

}

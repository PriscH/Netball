package com.prisch.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import com.prisch.R;
import com.prisch.model.Player;
import com.prisch.model.Team;
import com.prisch.repositories.TeamRepository;
import com.prisch.views.PlayerAdapter;

import java.util.LinkedList;
import java.util.List;

public class SubstitutionActivity extends BaseTeamActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String GAME_KEY = "GAME_KEY";

    private static final int TEAM_LOADER = 0;

    private TeamRepository teamRepository;

    private PlayerAdapter teamAdapter;

    private List<Player> teamPlayers = new LinkedList<Player>();

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.substitution);

        teamRepository = new TeamRepository(this);

        teamAdapter = new PlayerAdapter(this);
        ListView teamListView = (ListView)findViewById(R.id.listview_teamMembers);
        teamListView.setAdapter(teamAdapter);

        getLoaderManager().initLoader(TEAM_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return teamRepository.getTeamForGameLoader(getIntent().getLongExtra(GAME_KEY, 0));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        while (data.moveToNext()) {
            Player player = new Player(data.getLong(data.getColumnIndex(Team.PLAYER_ID)), data.getString(data.getColumnIndex(Player.NAME)));
            teamPlayers.add(player);
        }

        teamAdapter.addAll(teamPlayers);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        teamAdapter.clear();
    }

}

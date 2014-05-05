package com.prisch.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import com.prisch.R;
import com.prisch.model.Player;
import com.prisch.model.Position;
import com.prisch.model.Team;
import com.prisch.repositories.PlayerRepository;
import com.prisch.repositories.TeamRepository;
import com.prisch.views.SubstitutionAdapter;

import java.util.HashSet;
import java.util.Set;

public class SubstitutionActivity extends BaseTeamActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String GAME_KEY = "GAME_KEY";

    private static final int TEAM_LOADER = 0;
    private static final int PLAYERS_LOADER = 1;

    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;

    private ListView playerListView;
    private SubstitutionAdapter adapter;

    private Set<Long> teamPlayerIds = new HashSet<Long>();

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);

        teamRepository = new TeamRepository(this);
        playerRepository = new PlayerRepository(this);

        adapter = new SubstitutionAdapter(this);
        playerListView = (ListView)findViewById(R.id.listview_players);
        playerListView.setAdapter(adapter);

        getLoaderManager().initLoader(TEAM_LOADER, null, this);
    }

    // ===== Event Handlers =====

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case TEAM_LOADER:
                return teamRepository.getActiveTeam();
            case PLAYERS_LOADER:
                return playerRepository.getAllPlayers();
            default:
                throw new IllegalArgumentException("No Loader registered for " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case TEAM_LOADER:
                while (data.moveToNext()) {
                    Long playerId = data.getLong(data.getColumnIndex(Team.PLAYER_ID));
                    Position position = Position.fromAcronym(data.getString(data.getColumnIndex(Team.POSITION)));
                    Boolean active = (data.getInt(data.getColumnIndex(Team.ACTIVE)) > 0);

                    teamPlayerIds.add(playerId);
                    if (active) {
                        getTeamMap().put(playerId, position);
                    }
                }

                getLoaderManager().initLoader(PLAYERS_LOADER, null, this);

                break;
            case PLAYERS_LOADER:
                while (data.moveToNext()) {
                    Player player = new Player(data.getLong(data.getColumnIndex(Player.ID)), data.getString(data.getColumnIndex(Player.NAME)));
                    if (teamPlayerIds.contains(player.getId())) {
                        adapter.addTeamPlayer(player);
                    } else {
                        adapter.addOtherPlayer(player);
                    }
                }

                break;
            default:
                throw new IllegalArgumentException("No Loader registered for " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case TEAM_LOADER:
                teamPlayerIds.clear();
                getTeamMap().clear();
                break;
            case PLAYERS_LOADER:
                adapter.clearAll();
                break;
            default:
                throw new IllegalArgumentException("No Loader registered for " + loader.getId());
        }
    }
}

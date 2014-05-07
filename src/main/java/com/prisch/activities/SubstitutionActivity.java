package com.prisch.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import com.prisch.model.Player;
import com.prisch.model.Position;
import com.prisch.model.TeamMember;
import com.prisch.repositories.PlayerRepository;
import com.prisch.repositories.TeamMemberRepository;
import com.prisch.views.TeamAdapter;

import java.util.*;

public class SubstitutionActivity extends BaseTeamActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String GAME_KEY = "GAME_KEY";

    private static final String TEAM_HEADING = "Team Members";
    private static final String PLAYERS_HEADING = "Other Players";

    private static final int TEAM_LOADER = 0;
    private static final int PLAYERS_LOADER = 1;

    private TeamMemberRepository teamMemberRepository;
    private PlayerRepository playerRepository;

    private Set<Long> teamPlayerIds = new HashSet<Long>();

    // ===== Inherited Operations =====

    protected TeamAdapter createAdapter() {
        return new TeamAdapter(this, new String[] {TEAM_HEADING, PLAYERS_HEADING});
    }

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        teamMemberRepository = new TeamMemberRepository(this);
        playerRepository = new PlayerRepository(this);

        findDoneButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        teamMemberRepository.updateTeam(getIntent().getLongExtra(GAME_KEY, 0), getPlayerPositionMap());
                    }
                }).start();

                finish();
            }
        });

        getLoaderManager().initLoader(TEAM_LOADER, null, this);
    }

    // ===== Event Handlers =====

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case TEAM_LOADER:
                return teamMemberRepository.getTeamForGameLoader(getIntent().getLongExtra(GAME_KEY, 0));
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
                Map<Long, Position> playerPositionMap = new HashMap<Long, Position>(Position.values().length);

                while (data.moveToNext()) {
                    TeamMember teamMember = new TeamMember(data);

                    teamPlayerIds.add(teamMember.getPlayerId());
                    if (teamMember.isActive()) {
                        playerPositionMap.put(teamMember.getPlayerId(), teamMember.getPosition());
                    }
                }

                initialisePlayerPositionMap(playerPositionMap);
                getLoaderManager().initLoader(PLAYERS_LOADER, null, this);

                break;
            case PLAYERS_LOADER:
                List<Player> teamPlayers = new LinkedList<Player>();
                List<Player> otherPlayers = new LinkedList<Player>();

                while (data.moveToNext()) {
                    Player player = new Player(data.getLong(data.getColumnIndex(Player.ID)), data.getString(data.getColumnIndex(Player.NAME)));
                    if (teamPlayerIds.contains(player.getId())) {
                        teamPlayers.add(player);
                    } else {
                        otherPlayers.add(player);
                    }
                }

                getAdapter().putPlayers(TEAM_HEADING, teamPlayers);
                getAdapter().putPlayers(PLAYERS_HEADING, otherPlayers);

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
                break;
            case PLAYERS_LOADER:
                getAdapter().clearPlayers();
                break;
            default:
                throw new IllegalArgumentException("No Loader registered for " + loader.getId());
        }
    }
}

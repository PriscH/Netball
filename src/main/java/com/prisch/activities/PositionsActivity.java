package com.prisch.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.prisch.R;
import com.prisch.model.Player;
import com.prisch.model.Position;
import com.prisch.model.TeamMember;
import com.prisch.repositories.TeamMemberRepository;

import java.util.HashMap;
import java.util.Map;

public class PositionsActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TEAM_LOADER = 0;

    private static final Map<Position, Integer> POSITION_BUTTON_MAP = new HashMap<Position, Integer>();
    static {
        POSITION_BUTTON_MAP.put(Position.GS, R.id.button_goalShoot);
        POSITION_BUTTON_MAP.put(Position.GA, R.id.button_goalAttack);
        POSITION_BUTTON_MAP.put(Position.WA, R.id.button_wingAttack);
        POSITION_BUTTON_MAP.put(Position.C, R.id.button_center);
        POSITION_BUTTON_MAP.put(Position.WD, R.id.button_wingDefense);
        POSITION_BUTTON_MAP.put(Position.GD, R.id.button_goalDefense);
        POSITION_BUTTON_MAP.put(Position.GK, R.id.button_goalKeeper);
    }

    private TeamMemberRepository teamMemberRepository;

    // Map from a Position to the Team assignment ID (Player and Position) currently assigned to that Position
    private Map<Position, Long> positionMap = new HashMap<Position, Long>(7);

    private Long gameId;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.positions);

        teamMemberRepository = new TeamMemberRepository(this);

        getLoaderManager().initLoader(TEAM_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.positions, menu);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return teamMemberRepository.getActiveTeam();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        positionMap.clear();
        while (data.moveToNext()) {
            Position position = Position.fromAcronym(data.getString(data.getColumnIndex(TeamMember.POSITION)));
            Long teamAssignmentId = data.getLong(data.getColumnIndex(TeamMember.ID));
            String playerName = data.getString(data.getColumnIndex(Player.NAME));
            configureButton(position, teamAssignmentId, playerName);

            gameId = data.getLong(data.getColumnIndex(TeamMember.GAME_ID));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Nothing to cleanup
    }

    // ===== Event Listeners =====

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_substitutions:
                Intent substitutionIntent = new Intent(getApplicationContext(), SubstitutionActivity.class);
                substitutionIntent.putExtra(SubstitutionActivity.GAME_KEY, gameId);
                startActivity(substitutionIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // ===== Helper Methods =====

    private void configureButton(final Position position, final Long teamAssignmentId, String playerName) {
        final String BUTTON_FORMAT_PATTERN = "%s<br/><small><small><small>%s</small></small></small>";

        Button button = (Button)findViewById(POSITION_BUTTON_MAP.get(position));
        button.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, position.getAcronym(), playerName)));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                actionsIntent.putExtra(ActionsActivity.POSITION_KEY, position);
                actionsIntent.putExtra(ActionsActivity.TEAM_ID_KEY, teamAssignmentId);
                startActivity(actionsIntent);
            }
        });
    }
}


package com.prisch.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.prisch.R;
import com.prisch.model.Player;
import com.prisch.model.Position;
import com.prisch.model.Team;
import com.prisch.repositories.TeamRepository;

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

    private TeamRepository teamRepository;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.positions);

        teamRepository = new TeamRepository(this);

        getLoaderManager().initLoader(TEAM_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.prisch.R.menu.main, menu);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return teamRepository.getActiveTeam();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        while (data.moveToNext()) {
            Position position = Position.fromAcronym(data.getString(data.getColumnIndex(Team.COLUMN_POSITION)));
            String playerName = data.getString(data.getColumnIndex(Player.COLUMN_NAME));
            configureButton(position, playerName);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Nothing to cleanup
    }

    // ===== Helper Methods =====

    private void configureButton(final Position position, String playerName) {
        final String BUTTON_FORMAT_PATTERN = "%s<br/><small><small><small>%s</small></small></small>";

        Button button = (Button)findViewById(POSITION_BUTTON_MAP.get(position));
        button.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, position.getAcronym(), playerName)));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                actionsIntent.putExtra(ActionsActivity.POSITION_KEY, position);
                startActivity(actionsIntent);
            }
        });
    }
}


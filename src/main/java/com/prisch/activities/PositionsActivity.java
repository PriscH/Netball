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

public class PositionsActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TEAM_LOADER = 0;
    private static final String BUTTON_FORMAT_PATTERN = "%s<br/><small><small><small>%s</small></small></small>";

    private TeamRepository teamRepository;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.positions);

        teamRepository = new TeamRepository(this);

        getLoaderManager().initLoader(TEAM_LOADER, null, this);

        configureButtons();
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
            String name = data.getString(data.getColumnIndex(Player.COLUMN_NAME));
            Position position = Position.fromString(data.getString(data.getColumnIndex(Team.COLUMN_POSITION)));

            switch (position) {
                case GS:
                    Button buttonGS = (Button)findViewById(R.id.button_goalShoot);
                    buttonGS.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "GS", name)));
                    break;
                case GA:
                    Button buttonGA = (Button)findViewById(R.id.button_goalAttack);
                    buttonGA.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "GA", name)));
                    break;
                case WA:
                    Button buttonWA = (Button)findViewById(R.id.button_wingAttack);
                    buttonWA.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "WA", name)));
                    break;
                case C:
                    Button buttonC = (Button)findViewById(R.id.button_center);
                    buttonC.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "C", name)));
                    break;
                case WD:
                    Button buttonWD = (Button)findViewById(R.id.button_wingDefense);
                    buttonWD.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "WD", name)));
                    break;
                case GD:
                    Button buttonGD = (Button)findViewById(R.id.button_goalDefense);
                    buttonGD.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "GD", name)));
                    break;
                case GK:
                    Button buttonGK = (Button)findViewById(R.id.button_goalKeeper);
                    buttonGK.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "GK", name)));
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Nothing to cleanup
    }

    // ===== Helper Methods =====

    private void configureButtons() {
        Button buttonGS = (Button)findViewById(R.id.button_goalShoot);
        buttonGS.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "GS", "Goal Shoot")));
        buttonGS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonGA = (Button)findViewById(R.id.button_goalAttack);
        buttonGA.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "GA", "Goal Attack")));
        buttonGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonWA = (Button)findViewById(R.id.button_wingAttack);
        buttonWA.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "WA", "Wing Attack")));
        buttonWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonC = (Button)findViewById(R.id.button_center);
        buttonC.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "C", "Center")));
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonWD = (Button)findViewById(R.id.button_wingDefense);
        buttonWD.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "WD", "Wing Defense")));
        buttonWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonGD = (Button)findViewById(R.id.button_goalDefense);
        buttonGD.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "GD", "Goal Defense")));
        buttonGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonGK = (Button)findViewById(R.id.button_goalKeeper);
        buttonGK.setText(Html.fromHtml(String.format(BUTTON_FORMAT_PATTERN, "GK", "Goal Keeper")));
        buttonGK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });
    }
}


package com.prisch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.prisch.R;
import com.prisch.model.Position;
import com.prisch.repositories.PlayerRepository;
import com.prisch.repositories.TeamRepository;

import java.util.Map;

public class PositionsActivity extends Activity {

    private static final String BUTTON_FORMAT_PATTERN = "%s<br/><small><small><small>%s</small></small></small>";

    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.positions);

        teamRepository = new TeamRepository(this);
        playerRepository = new PlayerRepository(this);

        configureButtons();

        // TODO: This in a non blocking way (Content Loader? But that will require URIs that join Games with Teams :[)
        Map<Long, Position> teamMap = teamRepository.getLatestTeam();
        Map<Long, String> playerMap = playerRepository.getPlayers();
        for (long playerId : teamMap.keySet()) {
            String name = playerMap.get(playerId);

            switch (teamMap.get(playerId)) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.prisch.R.menu.main, menu);
        return true;
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


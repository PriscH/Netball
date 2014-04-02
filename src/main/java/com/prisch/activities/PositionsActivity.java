package com.prisch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.prisch.R;

public class PositionsActivity extends Activity {

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.positions);
        configureButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.prisch.R.menu.main, menu);
        return true;
    }

    // ===== Helper Methods =====

    private void configureButtons() {
        final String FORMAT_PATTERN = "%s<br/><small><small><small>%s</small></small></small>";

        Button buttonGS = (Button)findViewById(R.id.button_goalShoot);
        buttonGS.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "GS", "Goal Shoot")));
        buttonGS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonGA = (Button)findViewById(R.id.button_goalAttack);
        buttonGA.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "GA", "Goal Attack")));
        buttonGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonWA = (Button)findViewById(R.id.button_wingAttack);
        buttonWA.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "WA", "Wing Attack")));
        buttonWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonC = (Button)findViewById(R.id.button_center);
        buttonC.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "C", "Center")));
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonWD = (Button)findViewById(R.id.button_wingDefense);
        buttonWD.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "WD", "Wing Defense")));
        buttonWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonGD = (Button)findViewById(R.id.button_goalDefense);
        buttonGD.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "GD", "Goal Defense")));
        buttonGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonGK = (Button)findViewById(R.id.button_goalKeeper);
        buttonGK.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "GK", "Goal Keeper")));
        buttonGK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });
    }
}


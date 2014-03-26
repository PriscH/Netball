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

        Button buttonGS = (Button)findViewById(R.id.button_goalshoot);
        buttonGS.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "GS", "Goal Shoot")));
        buttonGS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionsIntent = new Intent(getApplicationContext(), ActionsActivity.class);
                startActivity(actionsIntent);
            }
        });

        Button buttonGA = (Button)findViewById(R.id.button_goalattack);
        buttonGA.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "GA", "Goal Attack")));

        Button buttonWA = (Button)findViewById(R.id.button_wingattack);
        buttonWA.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "WA", "Wing Attack")));

        Button buttonC = (Button)findViewById(R.id.button_center);
        buttonC.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "C", "Center")));

        Button buttonWD = (Button)findViewById(R.id.button_wingdefense);
        buttonWD.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "WD", "Wing Defense")));

        Button buttonGD = (Button)findViewById(R.id.button_goaldefense);
        buttonGD.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "GD", "Goal Defense")));

        Button buttonGK = (Button)findViewById(R.id.button_goalkeeper);
        buttonGK.setText(Html.fromHtml(String.format(FORMAT_PATTERN, "GK", "Goal Keeper")));
    }
}


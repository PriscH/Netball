package com.prisch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.prisch.R;

public class DashboardActivity extends Activity {

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        configureButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // ===== Helper Methods =====

    private void configureButtons() {
        Button buttonRecordGame = (Button)findViewById(R.id.button_recordGame);
        buttonRecordGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent positionsIntent = new Intent(getApplicationContext(), TeamActivity.class);
                startActivity(positionsIntent);
            }
        });

        Button buttonManagePlayers = (Button)findViewById(R.id.button_managePlayers);
        buttonManagePlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playersIntent = new Intent(getApplicationContext(), PlayersActivity.class);
                startActivity(playersIntent);
            }
        });
    }
}

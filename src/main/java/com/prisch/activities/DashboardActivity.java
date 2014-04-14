package com.prisch.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.prisch.R;
import com.prisch.repositories.GameRepository;

public class DashboardActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int GAME_LOADER = 0;

    private GameRepository gameRepository;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        gameRepository = new GameRepository(this);

        getLoaderManager().initLoader(GAME_LOADER, null, this);
        configureButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return gameRepository.getActiveGame();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() == 0) {
            switchToStandbyMode();
        } else {
            switchToRecordingMode();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Nothing to cleanup
    }

    // ===== Helper Methods =====

    private void configureButtons() {
        Button buttonRecordGame = (Button)findViewById(R.id.button_recordGame);
        buttonRecordGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teamIntent = new Intent(getApplicationContext(), TeamActivity.class);
                teamIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(teamIntent);
            }
        });

        Button continueRecording = (Button)findViewById(R.id.button_continueRecording);
        continueRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent positionIntent = new Intent(getApplicationContext(), PositionsActivity.class);
                startActivity(positionIntent);
            }
        });

        Button stopRecording = (Button)findViewById(R.id.button_stopRecording);
        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameRepository.stopActiveGame();
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

    private void switchToRecordingMode() {
        findViewById(R.id.button_recordGame).setVisibility(View.GONE);
        findViewById(R.id.button_continueRecording).setVisibility(View.VISIBLE);
        findViewById(R.id.button_stopRecording).setVisibility(View.VISIBLE);
    }

    private void switchToStandbyMode() {
        findViewById(R.id.button_recordGame).setVisibility(View.VISIBLE);
        findViewById(R.id.button_continueRecording).setVisibility(View.GONE);
        findViewById(R.id.button_stopRecording).setVisibility(View.GONE);
    }
}

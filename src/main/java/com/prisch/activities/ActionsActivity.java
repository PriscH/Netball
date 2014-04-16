package com.prisch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import com.prisch.R;
import com.prisch.model.Action;
import com.prisch.model.Position;
import com.prisch.repositories.RecordRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActionsActivity extends Activity {

    public static final String POSITION_KEY = "POSITION_KEY";
    public static final String TEAM_ID_KEY = "TEAM_ID";

    // Maps action button IDs to their corresponding Action values
    public static final Map<Integer, Action> ACTION_BUTTON_MAP = new HashMap<Integer, Action>(Action.values().length);
    static {
        ACTION_BUTTON_MAP.put(R.id.button_goal,         Action.GOAL);
        ACTION_BUTTON_MAP.put(R.id.button_miss,         Action.MISSED);
        ACTION_BUTTON_MAP.put(R.id.button_rebound,      Action.REBOUND);
        ACTION_BUTTON_MAP.put(R.id.button_stepping,     Action.STEPPING);
        ACTION_BUTTON_MAP.put(R.id.button_offside,      Action.OFFSIDE);
        ACTION_BUTTON_MAP.put(R.id.button_holding,      Action.HOLDING);
        ACTION_BUTTON_MAP.put(R.id.button_contact,      Action.CONTACT);
        ACTION_BUTTON_MAP.put(R.id.button_obstruction,  Action.OBSTRUCTION);
        ACTION_BUTTON_MAP.put(R.id.button_handling,     Action.HANDLING);
        ACTION_BUTTON_MAP.put(R.id.button_badPass,      Action.BADPASS);
        ACTION_BUTTON_MAP.put(R.id.button_badCatch,     Action.BADCATCH);
        ACTION_BUTTON_MAP.put(R.id.button_breaking,     Action.BREAKING);
        ACTION_BUTTON_MAP.put(R.id.button_interception, Action.INTERCEPTION);
        ACTION_BUTTON_MAP.put(R.id.button_pressure,     Action.PRESSURE);
    }

    private RecordRepository recordRepository;
    private Handler handler;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actions);

        recordRepository = new RecordRepository(this);
        handler = new Handler();

        disableIrrelevantButtons();
        setupButtonListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.prisch.R.menu.main, menu);
        return true;
    }

    // ===== Helper Methods =====

    private void disableIrrelevantButtons() {
        // TODO: Figure out why the disabled state isn't correctly inherited
        View goalButton = findViewById(R.id.custombutton_goal);
        ImageButton goalImageButton = (ImageButton)findViewById(R.id.button_goal);

        View missedButton = findViewById(R.id.custombutton_miss);
        ImageButton missedImageButton = (ImageButton)findViewById(R.id.button_miss);

        View reboundButton = findViewById(R.id.custombutton_rebound);
        ImageButton reboundImageButton = (ImageButton)findViewById(R.id.button_rebound);

        View breakingButton = findViewById(R.id.custombutton_breaking);
        ImageButton breakingImageButton = (ImageButton)findViewById(R.id.button_breaking);


        Position position = (Position)getIntent().getExtras().get(POSITION_KEY);
        if (position != null) {
            switch (position) {
                case GK:
                    goalButton.setEnabled(false);
                    missedButton.setEnabled(false);

                    goalImageButton.setEnabled(false);
                    missedImageButton.setEnabled(false);

                    // Fall through
                case GS:
                    breakingButton.setEnabled(false);
                    breakingImageButton.setEnabled(false);

                    break;
                case C:
                    breakingButton.setEnabled(false);
                    breakingImageButton.setEnabled(false);
                    // Fall through
                case WA:
                    // Fall through
                case WD:
                    goalButton.setEnabled(false);
                    missedButton.setEnabled(false);
                    reboundButton.setEnabled(false);

                    goalImageButton.setEnabled(false);
                    missedImageButton.setEnabled(false);
                    reboundImageButton.setEnabled(false);

                    break;
                case GA:
                    // Fall through
                case GD:
                    break;
            }
        }
    }

    private void setupButtonListeners() {
        for (Integer buttonId : ACTION_BUTTON_MAP.keySet()) {
            final Long teamAssignmentId = getIntent().getExtras().getLong(TEAM_ID_KEY);
            final Action action = ACTION_BUTTON_MAP.get(buttonId);

            ImageButton button = (ImageButton)findViewById(buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Perform the insertion asynchronously to ensure the interface is responsive
                    new Thread(new Runnable() {
                        public void run() {
                            recordRepository.createRecord(new Date(), teamAssignmentId, action);
                        }
                    }).start();

                    finish();
                }
            });
        }
    }

}

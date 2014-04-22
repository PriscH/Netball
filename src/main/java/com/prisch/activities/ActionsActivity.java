package com.prisch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import com.prisch.R;
import com.prisch.controls.ActionButton;
import com.prisch.model.Action;
import com.prisch.model.Position;
import com.prisch.repositories.RecordRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActionsActivity extends Activity {

    public static final String POSITION_KEY = "POSITION_KEY";
    public static final String TEAM_ID_KEY = "TEAM_ID";

    public static final Map<Action, Integer> ACTION_BUTTON_MAP = new HashMap<Action, Integer>(Action.values().length);
    static {
        ACTION_BUTTON_MAP.put(Action.GOAL,          R.id.actionbutton_goal);
        ACTION_BUTTON_MAP.put(Action.MISSED,        R.id.actionbutton_miss);
        ACTION_BUTTON_MAP.put(Action.REBOUND,       R.id.actionbutton_rebound);
        ACTION_BUTTON_MAP.put(Action.STEPPING,      R.id.actionbutton_stepping);
        ACTION_BUTTON_MAP.put(Action.OFFSIDE,       R.id.actionbutton_offside);
        ACTION_BUTTON_MAP.put(Action.HOLDING,       R.id.actionbutton_holding);
        ACTION_BUTTON_MAP.put(Action.CONTACT,       R.id.actionbutton_contact);
        ACTION_BUTTON_MAP.put(Action.OBSTRUCTION,   R.id.actionbutton_obstruction);
        ACTION_BUTTON_MAP.put(Action.HANDLING,      R.id.actionbutton_handling);
        ACTION_BUTTON_MAP.put(Action.BADPASS,       R.id.actionbutton_badPass);
        ACTION_BUTTON_MAP.put(Action.BADCATCH,      R.id.actionbutton_badCatch);
        ACTION_BUTTON_MAP.put(Action.BREAKING,      R.id.actionbutton_breaking);
        ACTION_BUTTON_MAP.put(Action.INTERCEPTION,  R.id.actionbutton_interception);
        ACTION_BUTTON_MAP.put(Action.PRESSURE,      R.id.actionbutton_pressure);
    }

    private RecordRepository recordRepository;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actions);

        recordRepository = new RecordRepository(this);

        disableButtonsAccordingToPosition();
        setupButtonListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.prisch.R.menu.main, menu);
        return true;
    }

    // ===== Helper Methods =====

    private void disableButtonsAccordingToPosition() {
        Position position = (Position)getIntent().getExtras().get(POSITION_KEY);
        for (Action action : ACTION_BUTTON_MAP.keySet()) {
            if (!position.getAllowedActions().contains(action)) {
                ActionButton button = (ActionButton)findViewById(ACTION_BUTTON_MAP.get(action));
                button.setEnabled(false);
            }
        }
    }

    private void setupButtonListeners() {
        final Long teamAssignmentId = getIntent().getExtras().getLong(TEAM_ID_KEY);

        for (final Action action : ACTION_BUTTON_MAP.keySet()) {
            ActionButton button = (ActionButton)findViewById(ACTION_BUTTON_MAP.get(action));

            // TODO: Figure out how to attach the listener directly to the ActionButton instead of to its child layout
            button.findViewById(R.id.layout_action).setOnClickListener(new View.OnClickListener() {
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

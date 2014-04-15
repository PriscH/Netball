package com.prisch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import com.prisch.R;
import com.prisch.model.Position;

import java.util.LinkedList;
import java.util.List;

public class ActionsActivity extends Activity {

    public static final String POSITION_KEY = "POSITION_KEY";

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.actions);
        setupButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.prisch.R.menu.main, menu);
        return true;
    }

    // ===== Helper Methods =====

    private void setupButtons() {
        View goalButton = findViewById(R.id.custombutton_goal);
        View missedButton = findViewById(R.id.custombutton_miss);
        View reboundButton = findViewById(R.id.custombutton_rebound);
        View breakingButton = findViewById(R.id.custombutton_breaking);

        Position position = (Position)getIntent().getExtras().get(POSITION_KEY);
        if (position != null) {
            switch (position) {
                case GK:
                    goalButton.setEnabled(false);
                    missedButton.setEnabled(false);
                    // Fall through
                case GS:
                    breakingButton.setEnabled(false);
                    break;
                case C:
                    breakingButton.setEnabled(false);
                    // Fall through
                case WA:
                    // Fall through
                case WD:
                    goalButton.setEnabled(false);
                    missedButton.setEnabled(false);
                    reboundButton.setEnabled(false);
                    break;
                case GA:
                    // Fall through
                case GD:
                    break;
            }
        }

        List<ImageButton> buttons = new LinkedList<ImageButton>();
        buttons.add((ImageButton)findViewById(R.id.button_goal));
        buttons.add((ImageButton)findViewById(R.id.button_miss));
        buttons.add((ImageButton)findViewById(R.id.button_rebound));
        buttons.add((ImageButton)findViewById(R.id.button_stepping));
        buttons.add((ImageButton)findViewById(R.id.button_offside));
        buttons.add((ImageButton)findViewById(R.id.button_holding));
        buttons.add((ImageButton)findViewById(R.id.button_contact));
        buttons.add((ImageButton)findViewById(R.id.button_obstruction));
        buttons.add((ImageButton)findViewById(R.id.button_handling));
        buttons.add((ImageButton)findViewById(R.id.button_badPass));
        buttons.add((ImageButton)findViewById(R.id.button_badCatch));
        buttons.add((ImageButton)findViewById(R.id.button_breaking));
        buttons.add((ImageButton)findViewById(R.id.button_interception));
        buttons.add((ImageButton)findViewById(R.id.button_pressure));

        for (ImageButton button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}

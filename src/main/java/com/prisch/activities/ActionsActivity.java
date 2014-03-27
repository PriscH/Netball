package com.prisch.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import com.prisch.R;
import com.prisch.fragments.*;

public class ActionsActivity extends Activity {

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.actions);
        addActionFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.prisch.R.menu.main, menu);
        return true;
    }

    // ===== Helper Methods =====

    private void addActionFragments() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        Fragment goalShootFragment = new GoalShootFragment();
        fragmentTransaction.add(R.id.layout_actions, goalShootFragment);

        Fragment mistakes1Fragment = new Mistakes1Fragment();
        fragmentTransaction.add(R.id.layout_actions, mistakes1Fragment);

        Fragment mistakes2Fragment = new Mistakes2Fragment();
        fragmentTransaction.add(R.id.layout_actions, mistakes2Fragment);

        Fragment misplaysFragment = new MisplaysFragment();
        fragmentTransaction.add(R.id.layout_actions, misplaysFragment);

        Fragment goodPlaysFragment = new GoodPlaysFragment();
        fragmentTransaction.add(R.id.layout_actions, goodPlaysFragment);

        fragmentTransaction.commit();
    }
}

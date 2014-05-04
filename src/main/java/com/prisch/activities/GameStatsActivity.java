package com.prisch.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import com.prisch.R;
import com.prisch.fragments.PositionStatsFragment;
import com.prisch.loaders.GameStatsLoader;
import com.prisch.model.GameStats;
import com.prisch.model.PlayerStats;
import com.prisch.model.Position;
import com.prisch.views.GameStatsAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStatsActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<GameStats>, ActionBar.TabListener {

    public static final String GAME_ID_KEY = "GAME_ID_KEY";

    private final static int GAME_STATS_LOADER = 0;

    private ViewPager viewPager;
    private Map<Position, PositionStatsFragment> positionFragmentMap;

    // ===== Lifecycle Methods =====

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamestats);

        final ActionBar actionBar = getActionBar();
        initializeFragments();

        GameStatsAdapter adapter = new GameStatsAdapter(getSupportFragmentManager(), positionFragmentMap);
        viewPager = (ViewPager)findViewById(R.id.pager_gamestats);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Nothing needed
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Nothing needed
            }
        });

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (Position position : Position.values()) {
            ActionBar.Tab tab = actionBar.newTab().setText(position.getAcronym()).setTabListener(this);
            actionBar.addTab(tab);
        }

        getLoaderManager().initLoader(GAME_STATS_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gamestats, menu);
        return true;
    }

    @Override
    public Loader<GameStats> onCreateLoader(int id, Bundle args) {
        Long gameId = getIntent().getLongExtra(GAME_ID_KEY, 0);
        return new GameStatsLoader(this, gameId);
    }

    @Override
    public void onLoadFinished(Loader<GameStats> loader, GameStats data) {
        Map<Position, List<PlayerStats>> positionStatsMap = data.getPlayerStatsByPosition();
        for (Position position : positionFragmentMap.keySet()) {
            PositionStatsFragment fragment = positionFragmentMap.get(position);
            fragment.setData(positionStatsMap.get(position));
        }
    }

    @Override
    public void onLoaderReset(Loader<GameStats> loader) {
        for (PositionStatsFragment fragment : positionFragmentMap.values()) {
            fragment.clearData();
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Nothing needed
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Nothing needed
    }

    // ===== Helper Methods =====

    private void initializeFragments() {
        positionFragmentMap = new HashMap<Position, PositionStatsFragment>();
        for (Position position : Position.values()) {
            positionFragmentMap.put(position, new PositionStatsFragment(this));
        }
    }
}

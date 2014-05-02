package com.prisch.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.prisch.fragments.PositionStatsFragment;
import com.prisch.model.Position;

import java.util.Map;

public class GameStatsAdapter extends FragmentPagerAdapter {

    private Map<Position, PositionStatsFragment> positionFragmentMap;

    public GameStatsAdapter(FragmentManager fragmentManager, Map<Position, PositionStatsFragment> positionFragmentMap) {
        super(fragmentManager);
        this.positionFragmentMap = positionFragmentMap;
    }

    @Override
    public int getCount() {
        return Position.values().length;
    }

    @Override
    public Fragment getItem(int index) {
        if (index < 0 || index >= Position.values().length) {
            return null;
        }

        return positionFragmentMap.get(Position.withIndex(index));
    }
}

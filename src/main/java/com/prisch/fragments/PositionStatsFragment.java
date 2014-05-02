package com.prisch.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.prisch.R;
import com.prisch.model.PlayerStats;
import com.prisch.views.PlayerStatsAdapter;
import com.prisch.views.PlayerStatsListItem;

import java.util.List;

public class PositionStatsFragment extends Fragment {

    private PlayerStatsAdapter adapter;

    public PositionStatsFragment(Context context) {
        this.adapter = new PlayerStatsAdapter(context);
    }

    // ===== Lifecycle Methods =====

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_positionstats, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listView_positionStats);
        listView.setAdapter(adapter);

        return rootView;
    }

    // ===== Custom Interface =====

    public void setData(List<PlayerStats> playerStatsList) {
        adapter.addAll(PlayerStatsListItem.buildFrom(playerStatsList));
    }

    public void clearData() {
        adapter.clear();
    }
}

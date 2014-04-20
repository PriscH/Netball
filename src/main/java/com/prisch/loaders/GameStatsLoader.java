package com.prisch.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.prisch.model.GameStats;
import com.prisch.repositories.StatsRepository;

public class GameStatsLoader extends AsyncTaskLoader<GameStats> {

    private final Long gameId;
    private final StatsRepository statsRepository;

    public GameStatsLoader(Context context, Long gameId) {
        super(context);

        this.gameId = gameId;
        this.statsRepository = new StatsRepository(context);

        onContentChanged();
    }

    @Override
    public GameStats loadInBackground() {
        return statsRepository.getStatsForGame(gameId);
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}

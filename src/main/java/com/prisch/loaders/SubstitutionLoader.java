package com.prisch.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.prisch.model.Player;
import com.prisch.repositories.PlayerRepository;

import java.util.List;
import java.util.Map;

public class SubstitutionLoader extends AsyncTaskLoader<Map<Boolean, List<Player>>> {

    private final Long gameId;
    private final PlayerRepository playerRepository;

    public SubstitutionLoader(Context context, Long gameId) {
        super(context);

        this.gameId = gameId;
        this.playerRepository = new PlayerRepository(context);

        onContentChanged();
    }

    @Override
    public Map<Boolean, List<Player>> loadInBackground() {
        return playerRepository.getAllPlayersPartitionedByGame(gameId);
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

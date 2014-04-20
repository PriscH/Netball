package com.prisch.model;

import java.util.HashMap;
import java.util.Map;

public class PlayerStats {

    private final String playerName;
    private final Position playerPosition;
    private final Map<Action, Integer> playerActionCountMap;

    public PlayerStats(String playerName, Position playerPosition) {
        this.playerName = playerName;
        this.playerPosition = playerPosition;
        this.playerActionCountMap = new HashMap<Action, Integer>(Action.values().length);
    }

    public String getPlayerName() {
        return playerName;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public Integer getActionCount(Action action) {
        if (!playerActionCountMap.containsKey(action)) {
            return 0;
        }
        return playerActionCountMap.get(action);
    }

    public void incrementActionCount(Action action) {
        int previousValue = getActionCount(action);
        playerActionCountMap.put(action, ++previousValue);
    }
}

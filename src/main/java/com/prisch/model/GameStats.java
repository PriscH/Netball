package com.prisch.model;

import java.util.*;

public class GameStats {

    private final Long gameId;
    private final Map<String, Map<Position, PlayerStats>> playerStatsMap;

    public GameStats(Long gameId) {
        this.gameId = gameId;
        this.playerStatsMap = new HashMap<String, Map<Position, PlayerStats>>();
    }

    public Long getGameId() {
        return gameId;
    }

    public List<PlayerStats> getPlayerStats() {
        List<PlayerStats> playerStatsList = new LinkedList<PlayerStats>();
        for (String playerName : playerStatsMap.keySet()) {
            Map<Position, PlayerStats> positionMap = playerStatsMap.get(playerName);
            for (Position position : positionMap.keySet()) {
                playerStatsList.add(positionMap.get(position));
            }
        }
        return playerStatsList;
    }

    public PlayerStats getOrCreate(String playerName, Position position) {
        if (!playerStatsMap.containsKey(playerName)) {
            playerStatsMap.put(playerName, new HashMap<Position, PlayerStats>());
        }

        Map<Position, PlayerStats> positionMap = playerStatsMap.get(playerName);
        if (!positionMap.containsKey(position)) {
            positionMap.put(position, new PlayerStats(playerName, position));
        }

        return positionMap.get(position);
    }
}

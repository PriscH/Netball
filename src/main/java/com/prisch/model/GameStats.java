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

    public Map<Position, List<PlayerStats>> getPlayerStatsByPosition() {
        Map<Position, List<PlayerStats>> positionStatsMap = new HashMap<Position, List<PlayerStats>>();

        for (String playerName : playerStatsMap.keySet()) {
            Map<Position, PlayerStats> playerPositionMap = playerStatsMap.get(playerName);
            for (Position position : playerPositionMap.keySet()) {
                if (!positionStatsMap.containsKey(position)) {
                    positionStatsMap.put(position, new LinkedList<PlayerStats>());
                }
                positionStatsMap.get(position).add(playerPositionMap.get(position));
            }
        }

        return positionStatsMap;

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

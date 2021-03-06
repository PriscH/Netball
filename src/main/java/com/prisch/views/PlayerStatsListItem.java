package com.prisch.views;

import com.prisch.model.Action;
import com.prisch.model.PlayerStats;

import java.util.*;

public class PlayerStatsListItem {

    private static final String GOAL_RATIO = "Goal Ratio";

    private final String itemName;
    private final String itemValue;
    private final boolean header;

    // ===== Static Constructors =====

    public static List<PlayerStatsListItem> buildFrom(PlayerStats playerStats) {
        List<PlayerStatsListItem> listItems = new LinkedList<PlayerStatsListItem>();
        Set<Action> actions = new HashSet<Action>(playerStats.getPlayerPosition().getAllowedActions());

        // Add the player name item
        listItems.add(new PlayerStatsListItem(playerStats.getPlayerName(), playerStats.getPlayerPosition().getAcronym(), true));

        // GOAL and MISSED are handled as a single item
        if (actions.remove(Action.GOAL) && actions.remove(Action.MISSED)) {
            int goalCount = playerStats.getActionCount(Action.GOAL);
            int missCount = playerStats.getActionCount(Action.MISSED);

            String goalRatio = String.format("%d / %d", goalCount, (goalCount + missCount));
            listItems.add(new PlayerStatsListItem(GOAL_RATIO, goalRatio, false));
        }

        // Add the other Actions
        for (Action action : actions) {
            listItems.add(new PlayerStatsListItem(action.getDescription(), Integer.toString(playerStats.getActionCount(action)), false));
        }

        return listItems;
    }

    public static List<PlayerStatsListItem> buildFrom(Collection<PlayerStats> playerStatsList) {
        List<PlayerStatsListItem> listItems = new LinkedList<PlayerStatsListItem>();

        for (PlayerStats playerStats : playerStatsList) {
            listItems.addAll(buildFrom(playerStats));
        }

        return listItems;
    }

    // ===== Main Definition =====

    public PlayerStatsListItem(String itemName, String itemValue, boolean header) {
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.header = header;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public boolean isHeader() {
        return header;
    }
}

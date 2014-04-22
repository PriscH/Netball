package com.prisch.views;

import com.prisch.model.Action;
import com.prisch.model.PlayerStats;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PlayerStatsListItem {

    private static final String GOAL_RATIO = "Goal Ratio";

    private final String itemName;
    private final String itemValue;

    // ===== Static Constructors =====

    public static List<PlayerStatsListItem> buildFrom(PlayerStats playerStats) {
        List<PlayerStatsListItem> listItems = new LinkedList<PlayerStatsListItem>();
        Set<Action> actions = new HashSet<Action>(playerStats.getPlayerPosition().getAllowedActions());

        // Add the player name item
        listItems.add(new PlayerStatsListItem(playerStats.getPlayerName(), playerStats.getPlayerPosition().getAcronym()));

        // GOAL and MISSED are handled as a single item
        if (actions.remove(Action.GOAL) && actions.remove(Action.MISSED)) {
            int goalCount = playerStats.getActionCount(Action.GOAL);
            int missCount = playerStats.getActionCount(Action.MISSED);

            String goalRatio = String.format("%d / %d", goalCount, (goalCount + missCount));
            listItems.add(new PlayerStatsListItem(GOAL_RATIO, goalRatio));
        }

        // Add the other Actions
        for (Action action : actions) {
            listItems.add(new PlayerStatsListItem(action.getDescription(), Integer.toString(playerStats.getActionCount(action))));
        }

        return listItems;
    }

    // ===== Main Definition =====

    public PlayerStatsListItem(String itemName, String itemValue) {
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemValue() {
        return itemValue;
    }
}

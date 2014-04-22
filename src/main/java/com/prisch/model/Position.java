package com.prisch.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Position {

    GS ("GS", "Goal Shoot",   Action.GOAL, Action.MISSED, Action.REBOUND),
    GA ("GA", "Goal Attack",  Action.GOAL, Action.MISSED, Action.REBOUND, Action.BREAKING),
    WA ("WA", "Wing Attack",  Action.BREAKING),
    C  ("C",  "Center"),
    WD ("WD", "Wing Defense", Action.BREAKING),
    GD ("GD", "Goal Defense", Action.REBOUND, Action.BREAKING),
    GK ("GK", "Goal Keeper",  Action.REBOUND);

    private final String acronym;
    private final String description;
    private final Set<Action> allowedActions;

    private Position(String acronym, String description, Action... allowedActions) {
        this.acronym = acronym;
        this.description = description;
        this.allowedActions = new HashSet<Action>(Arrays.asList(allowedActions));

        // Add the Actions that are common to everyone
        this.allowedActions.add(Action.STEPPING);
        this.allowedActions.add(Action.OFFSIDE);
        this.allowedActions.add(Action.HOLDING);
        this.allowedActions.add(Action.CONTACT);
        this.allowedActions.add(Action.OBSTRUCTION);
        this.allowedActions.add(Action.HANDLING);
        this.allowedActions.add(Action.BADPASS);
        this.allowedActions.add(Action.BADCATCH);
        this.allowedActions.add(Action.INTERCEPTION);
        this.allowedActions.add(Action.PRESSURE);
    }

    public String getAcronym() {
        return acronym;
    }

    public String getDescription() {
        return description;
    }

    public Set<Action> getAllowedActions() {
        return allowedActions;
    }

    public static Position fromAcronym(String acronym) {
        for (Position position : values()) {
            if (position.getAcronym().equals(acronym)) {
                return position;
            }
        }
        return null;
    }
}

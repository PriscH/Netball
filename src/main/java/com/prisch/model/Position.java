package com.prisch.model;

public enum Position {

    GS ("GS", "Goal Shoot"),
    GA ("GA", "Goal Attack"),
    WA ("WA", "Wing Attack"),
    C  ("C",  "Center"),
    WD ("WD", "Wing Defense"),
    GD ("GD", "Goal Defense"),
    GK ("GK", "Goal Keeper");

    private final String acronym;
    private final String description;

    private Position(String acronym, String description) {
        this.acronym = acronym;
        this.description = description;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getDescription() {
        return description;
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

package com.prisch.model;

public enum Action {

    GOAL            ("Goals"),
    MISSED          ("Missed Attempts"),
    REBOUND         ("Rebounds Caught"),
    STEPPING        ("Stepping Faults"),
    OFFSIDE         ("Caught Offside"),
    HOLDING         ("Holding the Ball"),
    CONTACT         ("Contact Faults"),
    OBSTRUCTION     ("Obstructing Players"),
    HANDLING        ("Handling the Ball"),
    BADPASS         ("Poor Passes"),
    BADCATCH        ("Poor Catches"),
    BREAKING        ("Breaking the Line"),
    INTERCEPTION    ("Ball Interceptions"),
    PRESSURE        ("Pressuring Players");

    private final String description;

    private Action(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Action fromString(String string) {
        for (Action action : values()) {
            if (action.toString().equals(string)) {
                return action;
            }
        }
        return null;
    }

}

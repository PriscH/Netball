package com.prisch.model;

public enum Action {

    GOAL,
    MISSED,
    REBOUND,
    STEPPING,
    OFFSIDE,
    HOLDING,
    CONTACT,
    OBSTRUCTION,
    HANDLING,
    BADPASS,
    BADCATCH,
    BREAKING,
    INTERCEPTION,
    PRESSURE;

    public static Action fromString(String string) {
        for (Action action : values()) {
            if (action.toString().equals(string)) {
                return action;
            }
        }
        return null;
    }

}

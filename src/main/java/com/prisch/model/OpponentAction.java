package com.prisch.model;

public enum OpponentAction {

    GOAL    ("Goals");

    private final String description;

    private OpponentAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static OpponentAction fromString(String string) {
        for (OpponentAction action : values()) {
            if (action.toString().equals(string)) {
                return action;
            }
        }
        return null;
    }
}

package com.prisch.model;

public enum Position {

    GS, GA, WA, C, WD, GD, GK;

    public static Position fromString(String string) {
        for (Position position : values()) {
            if (position.toString().equals(string)) {
                return position;
            }
        }
        return null;
    }

}

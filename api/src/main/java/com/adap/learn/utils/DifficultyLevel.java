package com.adap.learn.utils;

public enum DifficultyLevel {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String label;

    DifficultyLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Safely parse a string into a DifficultyLevel.
     * Defaults to MEDIUM if input is invalid or null.
     */
    public static DifficultyLevel fromString(String value) {
        if (value == null) return MEDIUM;
        return switch (value.trim().toUpperCase()) {
            case "EASY" -> EASY;
            case "HARD" -> HARD;
            default -> MEDIUM;
        };
    }
}

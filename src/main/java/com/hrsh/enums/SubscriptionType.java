package com.hrsh.enums;

public enum SubscriptionType {
    FREE(0),
    BASIC(1),
    PREMIUM(2);

    private final int priority;

    private SubscriptionType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}

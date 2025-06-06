package com.openclassrooms.mddapi.exception;

public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException() {
        super("Subscription not found");
    }
}

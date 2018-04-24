package com.adam.common;

public class AdministratorMessage {
    private final String message;

    public AdministratorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "AdministratorMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}

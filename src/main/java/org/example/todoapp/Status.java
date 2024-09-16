package org.example.todoapp;

public enum Status {
    OPEN("open"),
    IN_PROGRESS("in progress"),
    DEFERRED("deferred"),
    FINISHED("finished");

    private final String value;

    Status(String value){this.value = value;};

    public String getValue() {
        return value;
    }


}

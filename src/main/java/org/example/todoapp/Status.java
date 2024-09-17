package org.example.todoapp;

public enum Status {
    OPEN("open"),
    IN_PROGRESS("doing"),

    DONE("done");

    private final String value;

    Status(String value){this.value = value;};

    public String getValue() {
        return value;
    }


}

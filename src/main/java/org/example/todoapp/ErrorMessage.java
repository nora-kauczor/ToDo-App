package org.example.todoapp;

import java.time.Instant;

public record ErrorMessage(
        String message,
        Instant timestamp
) {
}

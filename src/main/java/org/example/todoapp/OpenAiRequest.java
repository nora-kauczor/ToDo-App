package org.example.todoapp;

import java.util.List;

public record OpenAiRequest(String model,
                            List<OpenAiMessage> messages,
                            double temperature) {
}

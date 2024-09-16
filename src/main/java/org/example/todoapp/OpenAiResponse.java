package org.example.todoapp;

import java.util.List;

public record OpenAiResponse(List<OpenAiChoice> choices) {
    public String getAnswer(){
        return choices().get(0).message().content();
    }
}

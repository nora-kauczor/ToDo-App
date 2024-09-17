package org.example.todoapp;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
public class ToDoService {
    private final ToDoRepo toDoRepo;
    private final RestClient restClient;


    public ToDoService(ToDoRepo toDoRepo, @Value("${BASE_URL}") String baseUrl,
                       @Value("${AUTH_KEY") String key, RestClient.Builder builder) {
        this.toDoRepo = toDoRepo;
        restClient = builder
                .defaultHeader("Authorization", "Bearer " + key)
                .baseUrl(baseUrl).build();
    }


    public String correctDescription(String description) {
        String question = "Correct spelling and grammar mistakes: " + description;
        OpenAiRequest request = new OpenAiRequest("gpt-4o-mini",
                List.of(new OpenAiMessage("user", question)),
                0.2);
        OpenAiResponse response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(OpenAiResponse.class);
        assert response != null;
        return response.getAnswer();
    }

    public List<ToDo> getAllToDos() {
        return toDoRepo.findAll();
    }

    public ToDo getToDo(String id) {
        return toDoRepo.findById(id).orElseThrow();
    }

    public ToDo createToDo(ToDoDTO toDoDTO) {
        String correctedDescription = correctDescription(toDoDTO.description());
        ToDo newToDo = new ToDo(IdService.generateId(), correctedDescription,
                toDoDTO.status());
        return toDoRepo.save(newToDo);
    }

    public ToDo editToDo(ToDo editedToDo) {
        ToDo oldToDo = toDoRepo.findById(editedToDo.id()).orElseThrow();
        if (oldToDo.description().equals(editedToDo.description())){
         return toDoRepo.save(editedToDo);
        }
        String correctedDescription = correctDescription(editedToDo.description());
        ToDo toDoWithCorrectedDescription = new ToDo(editedToDo.id(), correctedDescription, editedToDo.status());
        return toDoRepo.save(toDoWithCorrectedDescription);
    }

    public String deleteToDo(String id) {
        toDoRepo.deleteById(id);
        return "ToDo successfully deleted.";
    }
}

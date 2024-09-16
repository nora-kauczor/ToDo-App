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
                       @Value("${AUTH_KEY") String key) {
        this.toDoRepo = toDoRepo;
        restClient = RestClient.builder()
                .defaultHeader("Authorization", "Bearer " + key)
                .baseUrl(baseUrl).build();
    }

    public ToDo correctDescription(ToDo ToDo) {
        String question = "Correct spelling and grammar mistakes: "+ToDo.description();
        OpenAiRequest request = new OpenAiRequest("gpt-4o-mini",
                List.of(new OpenAiMessage("user", question )),
                0.2);
        OpenAiResponse response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(OpenAiResponse.class);
        assert response != null;
        String correctedDescription = response.getAnswer();
        return new ToDo(ToDo.id(), correctedDescription, ToDo.status());
    }

    public List<ToDo> getAllToDos() {
        return toDoRepo.findAll();
    }

    public ToDo getToDo(String id) {
        return toDoRepo.findById(id).orElseThrow();
    }

    public ToDo createToDo(ToDoDTO toDoDTO) {
        ToDo newToDo = new ToDo(IdService.generateId(), toDoDTO.description(),
                toDoDTO.status());
        return toDoRepo.save(newToDo);
    }

    public ToDo editToDo(ToDo editedToDo) {
        return toDoRepo.save(editedToDo);
    }

    public String deleteToDo(String id) {
        toDoRepo.deleteById(id);
        return "ToDo successfully deleted.";
    }
}

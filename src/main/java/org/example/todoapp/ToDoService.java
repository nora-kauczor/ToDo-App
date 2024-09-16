package org.example.todoapp;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@AllArgsConstructor
@Service
public class ToDoService {
    private final ToDoRepo toDoRepo;


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

    public String deleteToDo(String id){
        toDoRepo.deleteById(id);
        return "ToDo successfully deleted.";
    }
}

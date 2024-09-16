package org.example.todoapp;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public ToDo createToDo(ToDoDTO toDoDTO){
        // String id = ...

        return toDoRepo.save()
    }
}

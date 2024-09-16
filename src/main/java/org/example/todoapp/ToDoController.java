package org.example.todoapp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/todo")
public class ToDoController {
    private final ToDoService toDoService;


    @GetMapping
    public List<ToDo> getAllToDos() {
        return toDoService.getAllToDos();
    }

    @GetMapping("/{id}")
    public ToDo getToDo(@RequestParam String id) {
        return toDoService.getToDo(id);
    }

    @PutMapping
    public ToDo createToDo(@RequestBody ToDoDTO toDoDTO) {
        return toDoService.createToDo(toDoDTO);
    }

    @PostMapping
    public ToDo editToDo(@RequestParam ToDo editedToDo){
        return toDoService.editToDo(editedToDo);
    }

    @DeleteMapping("/{id}")
    public String deleteToDo(@RequestParam String id){
        return toDoService.deleteToDo(id);
    }
}

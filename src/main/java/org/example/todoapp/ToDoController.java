package org.example.todoapp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/todo")
public class ToDoController {
    private final ToDoService toDoService;

    @GetMapping("/correct")
    public ToDo correctDescription(@RequestBody ToDo toDo){
        return toDoService.correctDescription(toDo);
    }
    @GetMapping
    public List<ToDo> getAllToDos() {
        return toDoService.getAllToDos();
    }

    @GetMapping("/{id}")
    public ToDo getToDo(@PathVariable String id) {
        return toDoService.getToDo(id);
    }

    @PostMapping
    public ToDo createToDo(@RequestBody ToDoDTO toDoDTO) {
        return toDoService.createToDo(toDoDTO);
    }

    @PutMapping("/{id}")
    public ToDo editToDo(@RequestBody ToDo editedToDo){
        return toDoService.editToDo(editedToDo);
    }

    @DeleteMapping("/{id}")
    public String deleteToDo(@PathVariable String id){
        return toDoService.deleteToDo(id);
    }


}

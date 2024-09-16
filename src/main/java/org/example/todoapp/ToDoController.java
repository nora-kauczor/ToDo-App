package org.example.todoapp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/todo")
public class ToDoController {
    ToDoService toDoService;


    @GetMapping
    public List<ToDo> getAllToDos(){
        return toDoService.getAllToDos();
    }

    @GetMapping
    public List<ToDo> getToDo(@RequestParam String id){
        return toDoService.getToDo(id);
    }
}

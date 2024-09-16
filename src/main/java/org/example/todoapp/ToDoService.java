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

    public ToDo createToDo(ToDoDTO toDoDTO) {
        ToDo newToDo = new ToDo(IdService.generateId(), toDoDTO.description(),
                toDoDTO.status());
        toDoRepo.save(newToDo);
        return newToDo;
    }

    public ToDo editToDo(ToDo editedToDo) {
        toDoRepo.deleteById(editedToDo.id());
        toDoRepo.save(editedToDo);
        return editedToDo;
    }

    public String deleteToDo(String id){
        toDoRepo.deleteById(id);
        return "ToDo successfully deleted.";
    }
}

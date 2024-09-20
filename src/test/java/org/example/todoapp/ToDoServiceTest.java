package org.example.todoapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ToDoServiceTest {
    private final ToDoRepo mockedRepo = mock(ToDoRepo.class);
    RestClient.Builder builder = RestClient.builder();
    private final ToDoService testService = new ToDoService(mockedRepo, "XYZ", "123", builder);


    @Test
    void getAllToDos() {
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.IN_PROGRESS.getValue());
        mockedRepo.save(testToDo);
        List<ToDo> expected = List.of(testToDo);
        when(mockedRepo.findAll()).thenReturn(expected);
        List<ToDo> actual = testService.getAllToDos();
        assertEquals(expected, actual);
        verify(mockedRepo).findAll();
    }


    @Test
    void getToDo() {
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.IN_PROGRESS.getValue());
        mockedRepo.save(testToDo);
        ToDo expected = testToDo;
        when(mockedRepo.findById(testToDo.id())).thenReturn(Optional.of(expected));
        ToDo actual = testService.getToDo(testToDo.id());
        assertEquals(expected, actual);
        verify(mockedRepo).findById(testToDo.id());
    }

    @Test
    void createToDo() {
        ToDoDTO testToDoDTO = new ToDoDTO("buy birthday present", Status.IN_PROGRESS.getValue());
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.IN_PROGRESS.getValue());
        mockedRepo.save(testToDo);
        ToDo expected = testToDo;
        when(mockedRepo.save(any(ToDo.class))).thenReturn(expected);
        ToDo actual = testService.createToDo(testToDoDTO);
        verify(mockedRepo).save(testToDo);
        assertEquals(expected, actual);
    }


    @Test
    void editToDo() {
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.IN_PROGRESS.getValue());
        mockedRepo.save(testToDo);
        ToDo expected = testToDo;
        when(mockedRepo.save(any(ToDo.class))).thenReturn(expected);
        ToDo actual = testService.editToDo(testToDo);
        assertEquals(expected, actual);

    }

    @Test
    void deleteToDo() {
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.IN_PROGRESS.getValue());
        mockedRepo.save(testToDo);
        String expected = "ToDo successfully deleted.";
        String actual = testService.deleteToDo(testToDo.id());
        verify(mockedRepo).deleteById(testToDo.id());
        assertEquals(expected, actual);

    }


}
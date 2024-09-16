package org.example.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.data.mongodb.core.query.Update.update;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ToDoControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ToDoRepo testRepo;

    @Test
    @DirtiesContext
    void getAllToDos() throws Exception {
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.DOING.getValue());
        testRepo.save(testToDo);
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                                            [    {"id":"39abc", "description":"buy birthday present", "status":"in progress"}]
                                """
                ));
    }


    @Test
    @DirtiesContext
    void getToDo() throws Exception {
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.DOING.getValue());
        testRepo.save(testToDo);
        mockMvc.perform(get("/api/todo/39abc"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                                               {"id":"39abc", "description":"buy birthday present", "status":"in progress"}
                                """
                ));
    }


    @Test
    @DirtiesContext
    void createToDo() throws Exception {

        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "description":"buy birthday present", "status":"in progress"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                       { "description":"buy birthday present", "status":"in progress"}
                        """)
                ).andExpect(jsonPath("$.id").isNotEmpty());
        ;
    }


    @Test
    @DirtiesContext
    void editToDo() throws Exception {
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.DOING.getValue());
        testRepo.save(testToDo);
        mockMvc.perform(put("/api/todo/39abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "id":"39abc", "description":"buy birthday presents and champaign", "status":"in progress"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                                               {"id":"39abc", "description":"buy birthday presents and champaign", "status":"in progress"}
                                """
                ));
    }

    @Test
    @DirtiesContext
    void deleteToDo() throws Exception {
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.DOING.getValue());
        testRepo.save(testToDo);
        mockMvc.perform(delete("/api/todo/39abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("ToDo successfully deleted.")
                );
    }


}
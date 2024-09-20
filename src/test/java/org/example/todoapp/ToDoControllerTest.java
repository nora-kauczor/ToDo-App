package org.example.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer

class ToDoControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ToDoRepo testRepo;

    @Autowired
    MockRestServiceServer mockRestServiceServer;

    @Test
    @DirtiesContext
    void getAllToDos() throws Exception {
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.IN_PROGRESS.getValue());
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
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.IN_PROGRESS.getValue());
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
    void createToDo_ApiTest() throws Exception {
        mockRestServiceServer.expect(requestTo("http://localhost:8000"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().json("""
                        {
                            "model": "gpt-4o-mini",
                            "messages": [
                                {
                                    "role": "user",
                                    "content": "Correct spelling and grammar mistakes: bouy birtday prasent"
                                }
                            ],
                            "temperature": 0.2
                           }
                        """))
                .andRespond(withSuccess("""
                        {
                            "id": "chatcmpl-abc123",
                            "object": "chat.completion",
                            "created": 1677858242,
                            "model": "gpt-4o-mini",
                            "usage": {
                                "prompt_tokens": 13,
                                "completion_tokens": 7,
                                "total_tokens": 20,
                                "completion_tokens_details": {
                                    "reasoning_tokens": 0
                                }
                            },
                            "choices": [
                                {
                                    "message": {
                                        "role": "assistant",
                                        "content": "\\n\\nThis is a test!"
                                    },
                                    "logprobs": null,
                                    "finish_reason": "stop",
                                    "index": 0
                                }
                            ]
                        }
                        
                        """, MediaType.APPLICATION_JSON));
        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "description":"bouy birtday prasent", "status":"in progress"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                       { "description":"\\n\\nThis is a test!", "status":"in progress"}
                        """)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }


    @Test
    @DirtiesContext
    void editToDo() throws Exception {
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.IN_PROGRESS.getValue());
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
        ToDo testToDo = new ToDo("39abc", "buy birthday present", Status.IN_PROGRESS.getValue());
        testRepo.save(testToDo);
        mockMvc.perform(delete("/api/todo/39abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("ToDo successfully deleted.")
                );
    }


}
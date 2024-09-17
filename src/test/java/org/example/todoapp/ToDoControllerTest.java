package org.example.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;


import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
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


//    @Test
//    @DirtiesContext
//    void createToDo_clientTest() throws Exception {
//
//        mockMvc.perform(post("/api/todo")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                                { "description":"buy birthday present", "status":"in progress"}
//                                """))
//                .andExpect(status().isOk())
//                .andExpect(content().json("""
//                                                       { "description":"buy birthday present", "status":"in progress"}
//                        """)
//                ).andExpect(jsonPath("$.id").isNotEmpty());
//        ;
//    }


    // testen wir nur ob er irgendwas zurückschickt????
    @Test
    @DirtiesContext
    void createToDo_ApiTest() throws Exception {
        mockRestServiceServer.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
//                .andExpect(MockRestRequestMatchers.content().json("""
//{
//    "model": "gpt-4o-mini",
//    "messages": [
//        {
//            "role": "user",
//            "content": "Correct spelling and grammar mistakes: bouy birtday prasent"
//        }
//    ],
//    "response_format": {
//        "type": "json_object"
//         }
//   }
//"""))
                .andRespond(withSuccess("""
                        {
                          "choices": [{
                        
                            "message": {
                              "role": "assistant",
                              "content": "Buy birthday present"
                            }
                          }]
                        }
                        
                        """, MediaType.APPLICATION_JSON));


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
package mael.dev.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArmuControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldAssembleAnArmy() throws Exception {
        mockMvc.perform(post("/army")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"weight\": 100}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.persons").value(hasSize(1)))
                .andExpect(jsonPath(("$.persons[0].name")).value("Luke Skywalker"))
                .andExpect(jsonPath(("$.persons[0].weight")).value(172));
    }

}

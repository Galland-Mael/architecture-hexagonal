package mael.dev.api.controller;

import mael.dev.api.configuration.DomaineConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArmyController.class)
@Import(DomaineConfiguration.class)
class ArmyControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldAssembleAnArmy() throws Exception {
        mockMvc.perform(post("/army")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"weight\": 101}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.persons").value(hasSize(2)))
                .andExpect(jsonPath(("$.persons[0].name")).value("John"))
                .andExpect(jsonPath(("$.persons[0].weight")).value(100));
    }

}

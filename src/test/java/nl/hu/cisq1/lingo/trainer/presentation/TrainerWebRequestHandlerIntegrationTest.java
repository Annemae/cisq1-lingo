package nl.hu.cisq1.lingo.trainer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.DefaultLengthStrategy;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GuessDTORequest;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class TrainerWebRequestHandlerIntegrationTest {
    private String id;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordService wordService;

    @BeforeEach
    void beforeEachTest() throws Exception {
        when(wordService.provideRandomWord(any()))
                .thenReturn("APPLE");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/trainer/start")).andReturn();

        String content = result.getResponse().getContentAsString();
        id = JsonPath.read(content, "id");
    }

    @Test
    @DisplayName("start game gives back correct values")
    void startGameWorks() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/trainer/start");

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.score").value(0))
                .andExpect(jsonPath("$.feedback").value(IsNull.nullValue()));
    }

    @Test
    @DisplayName("get gives back correct values")
    void getProgressWorks() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/trainer/{id}", id);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("guess gives back correct values")
    void guessWorks() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/trainer/{id}/{guess}", id, "APPLE");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }


}
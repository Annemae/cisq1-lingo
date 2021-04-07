package nl.hu.cisq1.lingo.trainer.presentation;

import com.jayway.jsonpath.JsonPath;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.words.application.WordService;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
                .andExpect(jsonPath("$.feedback").exists())
                .andExpect(jsonPath("$.hint").exists())
                .andExpect(jsonPath("$.id").exists());
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

    @Test
    @DisplayName("get with wrong id throws exception")
    void wrongIdThrowsNoGameFoundException() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/trainer/{id}", UUID.randomUUID());

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertEquals("Game was not found with given ID.", errorMessage);
    }

    @Test
    @DisplayName("wrong guess throws exception")
    void invalidGuessThrowsInvalidGuessException() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/trainer/{id}/{guess}", id, "INVALID");

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertEquals("Guess is invalid, because guess is not the right length or starts with wrong letter.", errorMessage);
    }

    @Test
    @DisplayName("too many guesses throws exception")
    void tooManyGuessesThrowsInvalidGameStateException() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/trainer/{id}/{guess}", id, "ACORN");

        mockMvc.perform(request);
        mockMvc.perform(request);
        mockMvc.perform(request);
        mockMvc.perform(request);
        mockMvc.perform(request);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isNotAcceptable())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertEquals("This game isn't active anymore, you can't take a guess.", errorMessage);
    }
}
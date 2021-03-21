package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.DefaultLengthStrategy;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class TrainerWebRequestHandlerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test //todo see if i can make mock content
    @DisplayName("start game gives back correct values")
    void startGameWorks() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/trainer/start");

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.score").value(0))
                .andExpect(jsonPath("$.feedback").value(IsNull.nullValue()));
    }

//    @Test
//    @DisplayName("get gives back correct values")
//    void getProgressWorks() throws Exception {
//        Game game = new Game(new DefaultLengthStrategy());
//        game.createNewRound("APPLE");
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .get("/trainer/{id}", UUID.randomUUID());
//
//        mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(game.getId()));
//    }

//    @Test
//    @DisplayName("guess gives back correct values")
//    void guessWorks() throws Exception {
//        JSONObject json = new JSONObject();
//        json.put("attempt", "APPLE");
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/trainer/guess/{id}", id)
//                .content(String.valueOf(json));
//
//        mockMvc.perform(request)
//                .andExpect(status().isOk());
//    }
}
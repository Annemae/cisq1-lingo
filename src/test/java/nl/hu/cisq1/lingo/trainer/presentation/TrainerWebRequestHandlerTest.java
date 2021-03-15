package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.domain.game.GameResult;
import nl.hu.cisq1.lingo.trainer.domain.game.GameStatus;
import nl.hu.cisq1.lingo.trainer.presentation.dto.ProgressDTOResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerWebRequestHandlerTest {
    GameResult gameResult;

    @BeforeEach
    void setUp() {
        Word wordToGuess = Word.of("APPLE");

        gameResult = new GameResult(UUID.randomUUID(),
                0, GameStatus.WAITING_FOR_ROUND,
                null, new Hint(Collections.emptyList(), wordToGuess),
                List.of(new Round(wordToGuess))
        );
    }

    @Test
    @DisplayName("start game gives back game result")
    void startGame() {
        TrainerService trainerService = mock(TrainerService.class);

        when(trainerService.startGame())
                .thenReturn(gameResult);

        TrainerWebRequestHandler trainerWebRequestHandler = new TrainerWebRequestHandler(trainerService);
        ResponseEntity<ProgressDTOResponse> responseEntity = trainerWebRequestHandler.startGame();
        ProgressDTOResponse progressDTOResponse = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(gameResult.getId(), progressDTOResponse.getId());
    }
}
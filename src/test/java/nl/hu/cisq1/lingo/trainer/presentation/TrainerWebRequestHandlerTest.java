package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.domain.game.GameProgress;
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
    private GameProgress gameProgress;
    private final TrainerService trainerService = mock(TrainerService.class);
    private final TrainerWebRequestHandler trainerWebRequestHandler = new TrainerWebRequestHandler(trainerService);

    @BeforeEach
    void setUp() {
        Word wordToGuess = Word.of("APPLE");

        gameProgress = new GameProgress(UUID.randomUUID(),
                0,
                GameStatus.WAITING_FOR_ROUND,
                null,
                new Hint(Collections.emptyList(), wordToGuess),
                List.of(new Round(wordToGuess))
        );
    }

    @Test
    @DisplayName("start game uses service start game")
    void startGameUsesServiceMethod() {
        when(trainerService.startGame()) //GIVEN
                .thenReturn(gameProgress);

        trainerWebRequestHandler.startGame(); //WHEN

        verify(trainerService, times(1)).startGame(); //THEN
    }

    @Test
    @DisplayName("start game uses service start game")
    void takeGuessUsesServiceMethod() {
        when(trainerService.guess(any(), any())) //GIVEN
                .thenReturn(gameProgress);

        trainerWebRequestHandler.takeGuess(UUID.randomUUID(), "ANY"); //WHEN

        verify(trainerService, times(1)).guess(any(), any()); //THEN
    }

    @Test
    @DisplayName("start game uses service start game")
    void showProgressUsesServiceMethod() {
        when(trainerService.showProgress(any())) //GIVEN
                .thenReturn(gameProgress);

        trainerWebRequestHandler.showProgress(UUID.randomUUID()); //WHEN

        verify(trainerService, times(1)).showProgress(any()); //THEN
    }
}
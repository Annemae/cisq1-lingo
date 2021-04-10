package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.application.exception.NoGameFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.DefaultLengthStrategy;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class TrainerServiceIntegrationTest {

    @Autowired
    private TrainerService trainerService;

    @MockBean
    private WordService wordService;

    @MockBean
    private SpringGameRepository repository;

    private final Game game = new Game(new DefaultLengthStrategy());

    @BeforeEach
    void beforeEachTest() {
        when(wordService.provideRandomWord(any()))
                .thenReturn("APPLE");

        when(wordService.wordDoesExist(any()))
                .thenReturn(true);

        when(repository.findById(any()))
                .thenReturn(Optional.of(game));

        when(repository.save(any(Game.class)))
                .thenReturn(game);

        game.createNewRound("APPLE");
    }

    @Test
    @DisplayName("throws exception when game is not found")
    void gameNotFound() {
        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        UUID uuid = UUID.randomUUID();

        assertThrows(
                NoGameFoundException.class,
                () -> trainerService.guess(uuid, "ATTEMPT")
        );

        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("gives back game")
    void gameIsFound() {
        UUID actual = trainerService.guess(UUID.randomUUID(), "APPLE").getId();

        assertEquals(actual, game.getId());
        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("start game saves a game")
    void startGameSaves() {
        GameProgress gameProgress = trainerService.startGame();

        assertEquals(game.getId(), gameProgress.getId());
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("start game saves")
    void GuessSaves() {
        GameProgress gameProgress = trainerService.guess(UUID.randomUUID(), "ACORN");

        assertEquals(game.createGameProgress().getId(), gameProgress.getId());
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("guess takes a guess")
    void GuessTakesGuess() {
        Game game = mock(Game.class);

        when(repository.findById(any()))
                .thenReturn(Optional.of(game));

        when(repository.save(any(Game.class)))
                .thenReturn(game);

        trainerService.guess(UUID.randomUUID(), "ACORN");

        verify(game, times(1)).takeGuess(any());
    }

    @Test
    @DisplayName("start game uses word service")
    void startGameUsesWordService() {
        trainerService.startGame();

        verify(wordService, times(1)).provideRandomWord(any());
    }

    @Test
    @DisplayName("start game uses word service")
    void GuessUsesWordService() { //when word is guessed
        trainerService.guess(UUID.randomUUID(), "APPLE");

        verify(wordService, times(1)).provideRandomWord(any());
    }

    @Test
    @DisplayName("start game makes new round")
    void startGameMakesRound() {
        GameProgress gameProgress = trainerService.startGame();

        assertEquals(1, gameProgress.getRounds().size());
    }

    @Test
    @DisplayName("guess makes new round when round is over")
    void guessMakesRound() {
        GameProgress gameProgress = trainerService.guess(game.getId(), "APPLE");

        assertEquals(2, gameProgress.getRounds().size());
    }

    @Test
    @DisplayName("guess makes no new round when round is not over")
    void guessDoesNotMakeRound() {
        GameProgress gameProgress = trainerService.guess(game.getId(), "ACORN");

        assertEquals(1, gameProgress.getRounds().size());
    }

    @Test
    @DisplayName("progress returns right data")
    void progressWorks() {
        GameProgress gameProgress = trainerService.showProgress(UUID.randomUUID());

        assertEquals(game.getId(), gameProgress.getId());
    }

}
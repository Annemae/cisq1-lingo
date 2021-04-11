package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.application.exception.NoGameFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
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
                .thenReturn("aarde");

        when(wordService.wordDoesExist(any()))
                .thenReturn(true);

        when(repository.findById(any()))
                .thenReturn(Optional.of(game));

        when(repository.save(any(Game.class)))
                .thenReturn(game);

        game.createNewRound("aarde");
    }

    @Test
    @DisplayName("game is not found and throws exception")
    void gameNotFound() {
        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        UUID uuid = UUID.randomUUID();

        assertThrows(
                NoGameFoundException.class,
                () -> trainerService.guess(uuid, "attempt")
        );

        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("game is found")
    void gameIsFound() {
        UUID actual = trainerService.guess(UUID.randomUUID(), "aarde").getId();

        assertEquals(actual, game.getId());
        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("start game saves game")
    void startGameSaves() {
        GameProgress gameProgress = trainerService.startGame();

        assertEquals(game.getId(), gameProgress.getId());
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("guess saves game")
    void GuessSaves() {
        GameProgress gameProgress = trainerService.guess(UUID.randomUUID(), "aasje");

        assertEquals(game.createGameProgress().getId(), gameProgress.getId());
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("guess uses takes a guess method")
    void GuessTakesGuess() {
        Game game = mock(Game.class);
        Round round = mock(Round.class);

        when(repository.findById(any()))
                .thenReturn(Optional.of(game));

        when(game.getCurrentRound())
                .thenReturn(round);

        when(round.getWordToGuess())
                .thenReturn(Word.of("aarde"));

        when(repository.save(any(Game.class)))
                .thenReturn(game);

        trainerService.guess(UUID.randomUUID(), "aarde");

        verify(game, times(1)).takeGuess(any());
    }

    @Test
    @DisplayName("start game uses word service")
    void startGameUsesWordService() {
        trainerService.startGame();

        verify(wordService, times(1)).provideRandomWord(any());
    }

    @Test
    @DisplayName("guess uses word service")
    void GuessUsesWordService() {
        trainerService.guess(UUID.randomUUID(), "aarde");

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
        GameProgress gameProgress = trainerService.guess(game.getId(), "aarde");

        assertEquals(2, gameProgress.getRounds().size());
    }

    @Test
    @DisplayName("guess throws exception")
    void guessThrowsInvalidGuessException() {
        when(wordService.wordDoesExist(any()))
                .thenReturn(false);

        UUID uuid = UUID.randomUUID();

        assertThrows(InvalidGuessException.class, () -> trainerService.guess(uuid, "ikbestaniet"));
    }

    @Test
    @DisplayName("progress gives back correct data")
    void progressGivesCorrectData() {
        GameProgress gameProgress = trainerService.showProgress(UUID.randomUUID());

        assertEquals(game.getId(), gameProgress.getId());
    }
}
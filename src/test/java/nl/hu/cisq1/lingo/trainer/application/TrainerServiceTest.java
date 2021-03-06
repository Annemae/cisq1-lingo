package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.application.exception.NoGameFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.DefaultLengthStrategy;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.GameProgress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {
    private final WordService wordService = mock(WordService.class);
    private final SpringGameRepository repository = mock(SpringGameRepository.class);
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
    @DisplayName("game not found throws exception")
    void gameNotFound() {
        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        TrainerService trainerService = new TrainerService(repository, wordService);

        UUID uuid = UUID.randomUUID();

        assertThrows(
                NoGameFoundException.class,
                () -> trainerService.guess(uuid, "ATTEMPT")
        );

        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("game is found")
    void gameIsFound() {
        TrainerService trainerService = new TrainerService(repository, wordService);

        UUID actual = trainerService.guess(UUID.randomUUID(), "APPLE").getId();

        assertEquals(actual, game.getId());
        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("start game saves game")
    void startGameSaves() {
        TrainerService trainerService = new TrainerService(repository, wordService);

        GameProgress gameProgress = trainerService.startGame();

        assertEquals(game.createGameProgress().getId(), gameProgress.getId());
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("guess saves game")
    void GuessSaves() {
        TrainerService trainerService = new TrainerService(repository, wordService);

        GameProgress gameProgress = trainerService.guess(UUID.randomUUID(), "ACORN");

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

        TrainerService trainerService = new TrainerService(repository, wordService);

        trainerService.guess(UUID.randomUUID(), "ACORN");

        verify(game, times(1)).takeGuess(any());
    }

    @Test
    @DisplayName("start game uses word service")
    void startGameUsesWordService() {
        TrainerService trainerService = new TrainerService(repository, wordService);

        trainerService.startGame();

        verify(wordService, times(1)).provideRandomWord(any());
    }

    @Test
    @DisplayName("guess uses word service")
    void GuessUsesWordService() {
        TrainerService trainerService = new TrainerService(repository, wordService);

        trainerService.guess(UUID.randomUUID(), "APPLE");

        verify(wordService, times(1)).provideRandomWord(any());
    }

    @Test
    @DisplayName("start game makes new round")
    void startGameMakesRound() {
        TrainerService trainerService = new TrainerService(repository, wordService);

        GameProgress gameProgress = trainerService.startGame();

        assertEquals(1, gameProgress.getRounds().size());
    }

    @Test
    @DisplayName("guess makes new round when round is over")
    void guessMakesRound() {
        TrainerService trainerService = new TrainerService(repository, wordService);

        GameProgress gameProgress = trainerService.guess(game.getId(), "APPLE");

        assertEquals(2, gameProgress.getRounds().size());
    }

    @Test
    @DisplayName("guess makes no new round when round is not over")
    void guessDoesNotMakeRound() {
        TrainerService trainerService = new TrainerService(repository, wordService);

        GameProgress gameProgress = trainerService.guess(game.getId(), "ACORN");

        assertEquals(1, gameProgress.getRounds().size());
    }

    @Test
    @DisplayName("guess throws exception")
    void guessThrowsInvalidGuessException() {
        when(wordService.wordDoesExist(any()))
                .thenReturn(false);

        TrainerService trainerService = new TrainerService(repository, wordService);

        UUID uuid = UUID.randomUUID();

        assertThrows(InvalidGuessException.class, () -> trainerService.guess(uuid, "ikbestaniet"));
    }

    @Test
    @DisplayName("progress gives back correct data")
    void progressGivesCorrectData() {
        TrainerService trainerService = new TrainerService(repository, wordService);

        GameProgress gameProgress = trainerService.showProgress(UUID.randomUUID());

        assertEquals(game.getId(), gameProgress.getId());
    }
}
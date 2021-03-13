package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {
    @Mock
    WordService wordService;

    @Test
    @DisplayName("throws exception when game is not found")
    void gameNotFound() {
        SpringGameRepository repository = mock(SpringGameRepository.class);

        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        TrainerService trainerService = new TrainerService(repository, wordService);

        assertThrows(
                NoGameFoundException.class,
                () -> trainerService.getGame(null)
        );
        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("gives back game")
    void gameIsFound() {
        SpringGameRepository repository = mock(SpringGameRepository.class);

        Game game = new Game();

        when(repository.findById(any()))
                .thenReturn(Optional.of(game));

        TrainerService trainerService = new TrainerService(repository, wordService);

        assertEquals(trainerService.getGame(game.getId()), game);
        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("start game saves")
    void startGameSaves() {
        SpringGameRepository repository = mock(SpringGameRepository.class);
        WordService wordService = mock(WordService.class);

        when(wordService.provideRandomWord(any()))
                .thenReturn("APPLE");

        TrainerService trainerService = new TrainerService(repository, wordService);
        trainerService.startGame();

        verify(repository, times(1)).save(any());
    }
}
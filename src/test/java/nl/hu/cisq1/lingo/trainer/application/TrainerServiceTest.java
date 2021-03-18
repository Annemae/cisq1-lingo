package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.GameProgress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {
    WordService wordService = mock(WordService.class);
    Game game = new Game();

    @BeforeEach
    void setUp() {
        when(wordService.provideRandomWord(any()))
                .thenReturn("APPLE");

        game.createNewRound("APPLE");
    }

//    @Test
//    @DisplayName("throws exception when game is not found")
//    void gameNotFound() {
//        SpringGameRepository repository = mock(SpringGameRepository.class);
//
//        when(repository.findById(any()))
//                .thenReturn(Optional.empty());
//
//        TrainerService trainerService = new TrainerService(repository, wordService);
//
//        assertThrows(
//                NoGameFoundException.class,
//                () -> trainerService.getGame(null)
//        );
//
//        verify(repository, times(1)).findById(any());
//    }
//
//    @Test
//    @DisplayName("gives back game")
//    void gameIsFound() {
//        SpringGameRepository repository = mock(SpringGameRepository.class);
//
//        Game game = new Game();
//
//        when(repository.findById(any()))
//                .thenReturn(Optional.of(game));
//
//        TrainerService trainerService = new TrainerService(repository, wordService);
//
//        assertEquals(trainerService.getGame(game.getId()), game);
//        verify(repository, times(1)).findById(any());
//    }
//
//    @Test
//    @DisplayName("start game saves")
//    void startGameSaves() {
//        SpringGameRepository repository = mock(SpringGameRepository.class);
//
//        when(repository.save(any(Game.class)))
//                .thenReturn(game);
//
//        TrainerService trainerService = new TrainerService(repository, wordService);
//        GameProgress gameProgress = trainerService.startGame();
//
//        assertEquals(game.createGameResult().getId(), gameProgress.getId());
//        verify(repository, times(1)).save(any());
//    }
}
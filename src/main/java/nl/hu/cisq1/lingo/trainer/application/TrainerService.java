package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.application.exception.NoGameFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.DefaultLengthStrategy;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.WordLengthStrategy;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static nl.hu.cisq1.lingo.trainer.domain.game.GameStatus.PLAYING;

@Service
@Transactional
public class TrainerService {
    private final SpringGameRepository repository;
    private final WordService wordService;

    public TrainerService(SpringGameRepository repository, WordService wordService) {
        this.repository = repository;
        this.wordService = wordService;
    }

    private Game getGame(UUID id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new NoGameFoundException("Game was not found with given ID."));
    }

    private void startNewRound(Game game) {
        String word;

        if(game.getRounds().isEmpty()) {
            word = wordService.provideRandomWord(5);
        } else {
            Round currentRound = game.getCurrentRound();
            Word currentWordToGuess = currentRound.getWordToGuess();
            int previousLength = currentWordToGuess.getLength();

            WordLengthStrategy wordLengthStrategy = game.getWordLengthStrategy();

            Integer nextWordLength = wordLengthStrategy.calculateWordLength(previousLength);
            word = wordService.provideRandomWord(nextWordLength);
        }

        game.createNewRound(word);
    }

    public GameProgress showProgress(UUID id) { return this.getGame(id).createGameProgress(); }

    public GameProgress startGame() {
        Game game = new Game(new DefaultLengthStrategy());

        this.startNewRound(game);

        return repository.save(game).createGameProgress();
    }

    public GameProgress guess(UUID id, String attempt) {
        Game game = this.getGame(id);

        if(!wordService.wordDoesExist(attempt) || attempt.toCharArray().length != game.getCurrentRound().getWordToGuess().getLength()) {
            throw new InvalidGuessException("Guess is invalid because guess is not the right length, not a word or starts with wrong letter.");
        }

        game.takeGuess(attempt);

        if(game.getGameStatus() == PLAYING) { this.startNewRound(game); }

        return repository.save(game).createGameProgress();
    }
}

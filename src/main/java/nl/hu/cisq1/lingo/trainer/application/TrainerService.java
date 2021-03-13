package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TrainerService {
    private final SpringGameRepository gameRepository;
    private final WordService wordService;

    public TrainerService(SpringGameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    private Game getGame(UUID id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if(optionalGame.isPresent()) {
            return optionalGame.get();
        } else throw new NoGameFoundException("Game was not found with given ID.");
    }

    public Progress startGame() {
        Game game = new Game();
        String word = wordService.provideRandomWord(5);

        game.createNewRound(word);

        gameRepository.save(game);

        return game.showProgress();
    }

    public void startNewRound(UUID id) {
        Game game = this.getGame(id);
    }

    public Progress guess(UUID id, String attempt) {
        Game game = this.getGame(id);

        game.takeGuess(attempt);

        gameRepository.save(game);

        return game.showProgress();
    }

    //get game
    //take guess
    //ask progress
}

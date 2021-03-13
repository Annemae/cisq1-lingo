package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.Progress;
import nl.hu.cisq1.lingo.trainer.domain.game.state.InvalidGameStateException;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GameDTOResponse;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GuessDTORequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController //HATEOAS, STATUSCODE
@RequestMapping("/trainer")
public class TrainerWebRequestHandler {
    private final TrainerService service;

    public TrainerWebRequestHandler(TrainerService service) {
        this.service = service;
    }

    @PostMapping(value = "/start")
    public ResponseEntity<GameDTOResponse> startGame() {
        Game game = service.startGame();

        return new ResponseEntity<>(createGameDTOResponse(game), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/round/start")
    public ResponseEntity<GameDTOResponse> startNewRound(@PathVariable UUID id) {
        Game game = service.startNewRound(id);

        return new ResponseEntity<>(createGameDTOResponse(game), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GameDTOResponse> getGame(@PathVariable UUID id) {
        Game game = service.getGame(id);

        return new ResponseEntity<>(createGameDTOResponse(game), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/guess")
    public ResponseEntity<GameDTOResponse> takeGuess(@PathVariable UUID id, @Valid @RequestBody GuessDTORequest dto) {
        Game game = service.guess(id, dto.attempt);

        return new ResponseEntity<>(createGameDTOResponse(game), HttpStatus.OK);
    }


    private GameDTOResponse createGameDTOResponse(Game game) {
        return new GameDTOResponse(game.getId(), game.getScore(), game.getGameStatus());
    }


    @ExceptionHandler(value = InvalidGameStateException.class)
    public ResponseEntity<String> igseHandler(InvalidGameStateException igse){
        return new ResponseEntity<>(igse.getMessage(), HttpStatus.NOT_FOUND); //TODO andere status code
    }

    @ExceptionHandler(value = InvalidGuessException.class)
    public ResponseEntity<String> igeHandler(InvalidGuessException ige){
        return new ResponseEntity<>(ige.getMessage(), HttpStatus.NOT_FOUND); //TODO andere status code
    }
}

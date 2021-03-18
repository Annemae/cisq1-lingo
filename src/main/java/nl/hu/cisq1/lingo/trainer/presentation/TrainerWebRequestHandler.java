package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
import nl.hu.cisq1.lingo.trainer.domain.game.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.game.state.InvalidGameStateException;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GuessDTORequest;
import nl.hu.cisq1.lingo.trainer.presentation.dto.ProgressDTOResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController //HATEOAS, STATUSCODE //todo vooral integratietest belanhrijk unit, roept hij serviec aan??
@RequestMapping("/trainer")
public class TrainerWebRequestHandler {
    private final TrainerService service;

    public TrainerWebRequestHandler(TrainerService service) {
        this.service = service;
    }

    @PostMapping(value = "/start")
    public ResponseEntity<ProgressDTOResponse> startGame() {
        GameProgress gameProgress = service.startGame();

        return new ResponseEntity<>(createProgressDTOResponse(gameProgress), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/round/start")
    public ResponseEntity<ProgressDTOResponse> startNewRound(@PathVariable UUID id) {
        GameProgress gameProgress = service.startNewRound(id);

        return new ResponseEntity<>(createProgressDTOResponse(gameProgress), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/guess")
    public ResponseEntity<ProgressDTOResponse> takeGuess(@PathVariable UUID id, @Valid @RequestBody GuessDTORequest dto) {
        GameProgress gameProgress = service.guess(id, dto.attempt);

        return new ResponseEntity<>(createProgressDTOResponse(gameProgress), HttpStatus.OK);
    }


    private ProgressDTOResponse createProgressDTOResponse(GameProgress gameProgress) {
        return new ProgressDTOResponse(gameProgress.getId(), gameProgress.getScore(), gameProgress.getFeedback(), gameProgress.getHint());
    }


    @ExceptionHandler(value = InvalidGameStateException.class)
    public ResponseEntity<String> igseHandler(InvalidGameStateException igse) {
        return new ResponseEntity<>(igse.getMessage(), HttpStatus.NOT_FOUND); //TODO andere status code
    }

    @ExceptionHandler(value = InvalidGuessException.class)
    public ResponseEntity<String> igeHandler(InvalidGuessException ige) {
        return new ResponseEntity<>(ige.getMessage(), HttpStatus.NOT_FOUND); //TODO andere status code
    }
}

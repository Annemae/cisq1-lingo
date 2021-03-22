package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.NoGameFoundException;
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

@RestController //HATEOAS, STATUSCODE
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

    @PostMapping(value = "/{id}/{guess}")
    public ResponseEntity<ProgressDTOResponse> takeGuess(@PathVariable UUID id, @PathVariable String guess) {
        GameProgress gameProgress = service.guess(id, guess);

        return new ResponseEntity<>(createProgressDTOResponse(gameProgress), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProgressDTOResponse> showProgress(@PathVariable UUID id) {
        GameProgress gameProgress = service.showProgress(id);

        return new ResponseEntity<>(createProgressDTOResponse(gameProgress), HttpStatus.OK);
    }


    private ProgressDTOResponse createProgressDTOResponse(GameProgress gameProgress) {
        return new ProgressDTOResponse(gameProgress.getId(), gameProgress.getScore(), gameProgress.getFeedback(), gameProgress.getHint());
    }


    @ExceptionHandler(value = NoGameFoundException.class)
    public ResponseEntity<String> ngfeHandler(NoGameFoundException ngfe) {
        return new ResponseEntity<>(ngfe.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidGameStateException.class)
    public ResponseEntity<String> igseHandler(InvalidGameStateException igse) {
        return new ResponseEntity<>(igse.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}

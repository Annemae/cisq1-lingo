package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.application.exception.NoGameFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
import nl.hu.cisq1.lingo.trainer.domain.game.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.game.state.exception.InvalidGameStateException;
import nl.hu.cisq1.lingo.trainer.presentation.dto.ProgressDTOResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/trainer")
public class TrainerWebRequestHandler {
    private final TrainerService service;

    public TrainerWebRequestHandler(TrainerService service) { this.service = service; }

    private void addShowProgressLink(ProgressDTOResponse response) {
        response.add(linkTo(methodOn(TrainerWebRequestHandler.class)
                .showProgress(response.getId()))
                .withRel("get"));
    }

    private void addGuessLink(ProgressDTOResponse response) {
        response.add(linkTo(methodOn(TrainerWebRequestHandler.class)
                .takeGuess(response.getId(), "x"))
                .withRel("post"));
    }

    private void addStartLink(ProgressDTOResponse response) {
        response.add(linkTo(methodOn(TrainerWebRequestHandler.class)
                .startGame())
                .withRel("post"));
    }

    @PostMapping(value = "/start")
    public ResponseEntity<ProgressDTOResponse> startGame() {
        GameProgress gameProgress = service.startGame();

        ProgressDTOResponse response = createProgressDTOResponse(gameProgress);
        addShowProgressLink(response);
        addGuessLink(response);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/{guess}")
    public ResponseEntity<ProgressDTOResponse> takeGuess(@PathVariable UUID id, @PathVariable String guess) {
        GameProgress gameProgress = service.guess(id, guess.toLowerCase());

        ProgressDTOResponse response = createProgressDTOResponse(gameProgress);
        addStartLink(response);
        addShowProgressLink(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProgressDTOResponse> showProgress(@PathVariable UUID id) {
        GameProgress gameProgress = service.showProgress(id);

        ProgressDTOResponse response = createProgressDTOResponse(gameProgress);
        addStartLink(response);
        addGuessLink(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ProgressDTOResponse createProgressDTOResponse(GameProgress gameProgress) {
        return new ProgressDTOResponse(gameProgress.getId(), gameProgress.getGameStatus(), gameProgress.getScore(),
                gameProgress.getAmountOfAttempts(), gameProgress.getWord(), gameProgress.getFeedback().getMarks(), gameProgress.getHint());
    }

    @ExceptionHandler(value = NoGameFoundException.class)
    public ResponseEntity<String> ngfeHandler(NoGameFoundException ngfe) {
        return new ResponseEntity<>(ngfe.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidGameStateException.class)
    public ResponseEntity<String> igseHandler(InvalidGameStateException igse) {
        return new ResponseEntity<>(igse.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = InvalidGuessException.class)
    public ResponseEntity<String>  igeHandler(InvalidGuessException ige) {
        return new ResponseEntity<>(ige.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //HATEOAS, STATUSCODE
@RequestMapping("/trainer")
public class TrainerWebRequestHandler {
    private final TrainerService service;

    public TrainerWebRequestHandler(TrainerService service) {
        this.service = service;
    }
}

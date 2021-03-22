package nl.hu.cisq1.lingo.trainer;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.DefaultLengthStrategy;

import org.springframework.boot.CommandLineRunner;

import java.util.UUID;

public class GameTestDataFixtures implements CommandLineRunner {
    private final SpringGameRepository repository;

    public GameTestDataFixtures(SpringGameRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
    }

}

package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Game { //state
    private List<Round> rounds;
    private State state;

    public Game() {
        this.rounds = new ArrayList<>();
        //new state starting...
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void createNewRound(String wordToGuess) {
        state.createNewRound(Word.of(wordToGuess));
    }
}

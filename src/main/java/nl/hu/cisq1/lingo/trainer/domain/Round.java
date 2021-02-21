package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Round {
    private Word wordToGuess;
    private List<List<Character>> hints;
    private List<List<Mark>> feedback;

    public Round(Word wordToGuess, Random random) { //todo index: Random.nextInt() moet waarschijnlijk in service.
        this.wordToGuess = wordToGuess;
        this.hints = new ArrayList<>();
        this.feedback = new ArrayList<>();

        this.makeFirstHint(random);
    }

    private void makeFirstHint(Random random) {
        List<Character> firstHint = new ArrayList<>();
        List<Character> wordArray = this.wordToGuess.getWord();

        for(int i = 0; i < wordArray.size(); i++) {
            if(i == random.nextInt(wordArray.size())) {
                firstHint.add(wordArray.get(i));
            } else {
                firstHint.add('.');
            }
        }

        this.hints.add(firstHint);
    }

    public Word getWordToGuess() {
        return wordToGuess;
    }

    public List<List<Character>> getHints() { //Returns the most recent hint that's been added to the list.
        return hints;
    }
}

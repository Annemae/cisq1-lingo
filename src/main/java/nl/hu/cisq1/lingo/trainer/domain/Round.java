package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.ABSENT;

public class Round {
    private Word wordToGuess;
    private List<Feedback> feedback;

    public Round(Word wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public void takeGuess(String attempt) {

    }

//    private void makeFirstHint(Random random) {
//        List<Character> firstHint = new ArrayList<>();
//        List<Character> wordArray = this.wordToGuess.getWord();
//
//        for(int i = 0; i < wordArray.size(); i++) {
//            if(i == random.nextInt(wordArray.size())) {
//                firstHint.add(wordArray.get(i));
//            } else {
//                firstHint.add('.');
//            }
//        }
//
//        this.hints.add(firstHint);
//    }

//    public List<Character> takeGuess(String attempt) {
//        Feedback feedback = Feedback.of(attempt, this.wordToGuess);
//        this.feedback.add(feedback);
//
//        Hint hint = Hint.of(this.getLastHint(), wordToGuess, feedback.getMarks());
//        this.hints.add(hint.getHint());
//
//        return this.getLastHint();
//    }
}

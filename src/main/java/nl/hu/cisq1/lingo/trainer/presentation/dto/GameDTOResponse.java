package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.game.GameStatus;

import java.util.UUID;

public class GameDTOResponse {
    private UUID id;
    private int score;
    private GameStatus gameStatus;

    public GameDTOResponse(UUID id, int score, GameStatus gameStatus) {
        this.id = id;
        this.score = score;
        this.gameStatus = gameStatus;
    }

    public UUID getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
}

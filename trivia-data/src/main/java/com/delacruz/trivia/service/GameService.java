package com.ectrvia.trivia.service;

import com.ectrvia.trivia.model.GameState;
import com.ectrvia.trivia.model.LeaderboardEntry;

import java.util.List;

public interface GameService {
    GameState getGameState(String roomCode);
    void advanceToNextQuestion(String roomCode);
    void endGame(String roomCode);
    List<LeaderboardEntry> getLeaderboard(String roomCode);
}

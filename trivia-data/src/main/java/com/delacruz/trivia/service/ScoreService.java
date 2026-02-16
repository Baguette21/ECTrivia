package com.ectrvia.trivia.service;

import com.ectrvia.trivia.model.PlayerAnswer;

public interface ScoreService {
    PlayerAnswer submitAnswer(String roomCode, Long playerId, Long questionId, Integer selectedAnswerIndex, Integer answerTimeMs);
    int calculateScore(boolean isCorrect, int answerTimeMs, int timerSeconds, int currentStreak);
}

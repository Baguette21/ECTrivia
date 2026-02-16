package com.ectrvia.trivia.service;

import com.ectrvia.trivia.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestionsForRoom(String roomCode);
    List<Question> getQuestionsForCategory(Long categoryId, Integer limit);
    Question addQuestion(String roomCode, String questionText, List<String> answers, Integer correctIndex, Integer timerSeconds);
    Question addQuestionToCategory(Long categoryId, String questionText, List<String> answers, Integer correctIndex, Integer timerSeconds);
    Question updateCategoryQuestion(Long categoryId, Long questionId, String questionText, List<String> answers, Integer correctIndex, Integer timerSeconds);
    Question updateQuestion(String roomCode, Long questionId, String questionText, List<String> answers, Integer correctIndex, Integer timerSeconds);
    void deleteQuestion(String roomCode, Long questionId);
    void deleteCategoryQuestion(Long categoryId, Long questionId);
    void copyQuestionsFromCategory(String roomCode, Long categoryId, Integer limit);
}

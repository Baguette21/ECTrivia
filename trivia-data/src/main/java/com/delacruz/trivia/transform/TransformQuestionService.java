package com.ectrvia.trivia.transform;

import com.ectrvia.trivia.entity.QuestionData;
import com.ectrvia.trivia.model.Question;

public interface TransformQuestionService {
    Question transform(QuestionData questionData);
    QuestionData transform(Question question);
}

package org.example.avanzoapi.services;

import org.example.avanzoapi.domain.entitites.Question;
import org.example.avanzoapi.domain.entitites.Quiz;

import java.util.List;

public interface QuizXQuestionService {
    void saveQuizQuestions(Quiz quiz, List<Question> questions);
}

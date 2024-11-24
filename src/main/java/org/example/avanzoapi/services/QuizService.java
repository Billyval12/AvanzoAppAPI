package org.example.avanzoapi.services;

import org.example.avanzoapi.domain.dtos.quiz.NotesDTO;
import org.example.avanzoapi.domain.dtos.quiz.QuizAnswersDTO;
import org.example.avanzoapi.domain.dtos.quiz.ScoreDTO;
import org.example.avanzoapi.domain.entitites.*;

import java.util.List;


public interface QuizService {
    ScoreDTO getQuizScore (List<Integer> selectedAnswersIds, List<Question> questions);
    Quiz saveQuiz(QuizAnswersDTO quizResponses, Float quizScore, User user, Subject subject);

    List<NotesDTO> notes (User user);
}

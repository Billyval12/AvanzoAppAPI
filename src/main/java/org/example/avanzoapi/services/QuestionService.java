package org.example.avanzoapi.services;

import org.example.avanzoapi.domain.dtos.quiz.QuestionDTO;
import org.example.avanzoapi.domain.entitites.Question;

import java.util.List;

public interface QuestionService {
    List<Integer> getQuestionsBySubject(String subject);
    List<Integer> shuffleQuestions(List<Integer> questionsIds);
    List<Question> getQuestionsById(List<Integer> questionsIds);
    List<QuestionDTO> getQuestions(List<Integer> Questions);
    List<QuestionDTO> getAllQuestions();

    List<Integer> getAllByAnswerAndQuestion(List<String> answers, List<Question> question);

}

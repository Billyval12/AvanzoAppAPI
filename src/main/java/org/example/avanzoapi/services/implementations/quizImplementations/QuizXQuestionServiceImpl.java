package org.example.avanzoapi.services.implementations.quizImplementations;

import lombok.extern.slf4j.Slf4j;
import org.example.avanzoapi.domain.entitites.Question;
import org.example.avanzoapi.domain.entitites.Quiz;
import org.example.avanzoapi.domain.entitites.QuizXQuestion;
import org.example.avanzoapi.repositories.quiz.QuizXQuestionRepository;
import org.example.avanzoapi.services.QuizXQuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QuizXQuestionServiceImpl implements QuizXQuestionService {

    private final QuizXQuestionRepository quizXQuestionRepository;

    public QuizXQuestionServiceImpl(QuizXQuestionRepository quizXQuestionRepository) {
        this.quizXQuestionRepository = quizXQuestionRepository;
    }


    @Override
    public void saveQuizQuestions(Quiz quiz, List<Question> questions) {

        questions.forEach(question -> {
            QuizXQuestion quizXQuestion = new QuizXQuestion();
            quizXQuestion.setQuiz(quiz);
            quizXQuestion.setQuestion(question);
            quizXQuestionRepository.save(quizXQuestion);
        });
    }
}

package org.example.avanzoapi.services.implementations.quizImplementations;

import lombok.extern.slf4j.Slf4j;
import org.example.avanzoapi.domain.dtos.quiz.NotesDTO;
import org.example.avanzoapi.domain.dtos.quiz.QuizAnswersDTO;
import org.example.avanzoapi.domain.dtos.quiz.ScoreDTO;
import org.example.avanzoapi.domain.entitites.*;
import org.example.avanzoapi.repositories.quiz.AnswerRepository;
import org.example.avanzoapi.repositories.quiz.QuizRepository;
import org.example.avanzoapi.services.QuizService;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public ScoreDTO getQuizScore(List<Integer> selectedAnswersIds, List<Question> questions) {
        float totalScore = 0.0f;
        float correctAnswers = 0f;
        float questionPercent = 10.0f/15.0f;
        int j = 0;

        for (Question question : questions) { // If are equals the question is correct!

            if (Objects.equals(question.getCorrectAnswer(), selectedAnswersIds.get(j))) {
                totalScore += questionPercent;
                correctAnswers++;
            }
            j += 1;
        }

        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setScore(Math.round(totalScore * 10.0f) / 10.0f);
        scoreDTO.setCorrectAnswers(correctAnswers);

        return scoreDTO;
    }

    @Override
    public Quiz saveQuiz(QuizAnswersDTO quizResponses, Float quizScore, User user, Subject subject) {
        Quiz quiz = new Quiz();

        quiz.setMode(quizResponses.getMode());
        quiz.setMonth(LocalDate.now().getMonthValue());
        quiz.setScore(quizScore);
        quiz.setYear(LocalDate.now().getYear());
        quiz.setUser(user);
        quiz.setSubject(subject);
        quizRepository.save(quiz);

        return quiz;
    }

    @Override
    public List<NotesDTO> notes(User user) {
        List<Quiz> quizzes = quizRepository.findAllByUser(user).orElse(null);
        List<NotesDTO> notesDTOs = new ArrayList<>();

        if (quizzes != null) {
            quizzes.forEach(quiz -> {
                NotesDTO notesDTO = new NotesDTO();
                notesDTO.setSubjectId(quiz.getSubject().getSubjectId().toString());
                notesDTO.setUserId(quiz.getUser().getUserId().toString());
                notesDTO.setMonth(quiz.getMonth());
                notesDTO.setYear(quiz.getYear());
                notesDTO.setScore(quiz.getScore().doubleValue());

                notesDTOs.add(notesDTO);
            });
        }

        return notesDTOs;
    }
}

package org.example.avanzoapi.controllers;

import org.example.avanzoapi.domain.dtos.GeneralResponse;
import org.example.avanzoapi.domain.dtos.quiz.NotesDTO;
import org.example.avanzoapi.domain.dtos.quiz.QuizAnswersDTO;
import org.example.avanzoapi.domain.dtos.quiz.ScoreDTO;
import org.example.avanzoapi.domain.entitites.*;
import org.example.avanzoapi.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final QuestionService questionService;
    private final QuizXQuestionService quizXQuestionService;

    public QuizController(QuizService quizService, UserService userService, SubjectService subjectService, QuestionService questionService, QuizXQuestionService quizXQuestionService) {
        this.quizService = quizService;
        this.userService = userService;
        this.subjectService = subjectService;
        this.questionService = questionService;
        this.quizXQuestionService = quizXQuestionService;
    }

    @PostMapping("/getScore")
    public ResponseEntity<GeneralResponse> getAnswers(@RequestBody QuizAnswersDTO quizAnswersDTO) {

        List<Question> questions = questionService.getQuestionsById(quizAnswersDTO.getQuestionsIds()); // Obtain questions entities
        List<Integer> selectedAnswersIds = questionService.getAllByAnswerAndQuestion(quizAnswersDTO.getSelectedAnswers(), questions);

        ScoreDTO quizScore = quizService.getQuizScore(selectedAnswersIds, questions); // Send to Score to obtain score

        User user = userService.findUserAuthenticated(); // Obtain the user
        Subject subject = subjectService.getSubjectById(Integer.toUnsignedLong(quizAnswersDTO.getSubjectId())); // Obtain the subject quiz

        Quiz quiz = quizService.saveQuiz(quizAnswersDTO,quizScore.getScore(), user, subject); // Save Quiz result and details
        quizXQuestionService.saveQuizQuestions(quiz, questions); // Save quiz questions from the last quiz

        userService.updateUserPoints(quizScore.getScore(), user);

        return GeneralResponse.getResponse(HttpStatus.OK, quizScore.getCorrectAnswers());
    }

    @GetMapping("/myNotes")
    public ResponseEntity<GeneralResponse> getNotes() {
        User user = userService.findUserAuthenticated();

        List<NotesDTO> myNotes = quizService.notes(user);

        return GeneralResponse.getResponse(HttpStatus.OK, myNotes);
    }

}

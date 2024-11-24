package org.example.avanzoapi.controllers;

import org.example.avanzoapi.domain.dtos.GeneralResponse;
import org.example.avanzoapi.domain.dtos.quiz.QuestionDTO;
import org.example.avanzoapi.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/getQuestions/{subject}")
    public ResponseEntity<GeneralResponse> getQuestions(@PathVariable String subject) {
        System.out.println("Sos un crack");
        List<Integer> ids = questionService.getQuestionsBySubject(subject); // Obtain all subjects questions ids
        List<Integer> shuffledIds = questionService.shuffleQuestions(ids); // Obtain fifteen random questions ids
        List<QuestionDTO> questions = questionService.getQuestions(shuffledIds); // Obtain the fifteen questions with the answers

        return GeneralResponse.getResponse(HttpStatus.OK, questions);
    }

    @GetMapping("/getAllQuestions")
    public ResponseEntity<GeneralResponse> getAllQuestions() {
        List<QuestionDTO> questions = questionService.getAllQuestions();
        System.out.println("Hice la consulta a la API");
        return GeneralResponse.getResponse(HttpStatus.OK, questions);
    }
}

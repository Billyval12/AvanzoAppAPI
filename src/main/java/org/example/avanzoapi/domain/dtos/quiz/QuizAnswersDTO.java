package org.example.avanzoapi.domain.dtos.quiz;

import lombok.Data;

import java.util.List;

@Data
public class QuizAnswersDTO {

    private String mode;
    private Integer subjectId;

    private List<Integer> questionsIds;
    private List<String> selectedAnswers;
}

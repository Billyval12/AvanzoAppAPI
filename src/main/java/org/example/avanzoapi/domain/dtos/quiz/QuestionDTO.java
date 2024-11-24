package org.example.avanzoapi.domain.dtos.quiz;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
    private int questionId;
    private String question;
    private String subjectId;
    List<AnswerDTO> answers;
    private int correctAnswer;
}

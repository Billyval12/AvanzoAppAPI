package org.example.avanzoapi.domain.dtos.quiz;

import lombok.Data;

@Data
public class NotesDTO {
    private String subjectId;
    private String userId;
    private Integer month;
    private Integer year;
    private Double score;
}

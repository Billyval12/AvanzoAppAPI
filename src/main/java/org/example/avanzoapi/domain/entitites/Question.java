package org.example.avanzoapi.domain.entitites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "question_table")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer questionId;

    @Column(length = 500)
    private String question;
    private Integer correctAnswer;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Answer> answers;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<QuizXQuestion> quizXQuestions;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}

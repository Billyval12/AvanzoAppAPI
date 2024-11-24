package org.example.avanzoapi.repositories.quiz;

import org.example.avanzoapi.domain.entitites.Answer;
import org.example.avanzoapi.domain.entitites.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findAllByQuestion (Question question);
    Answer findByAnswerAndQuestion(String answer, Question currentQuestion);
}

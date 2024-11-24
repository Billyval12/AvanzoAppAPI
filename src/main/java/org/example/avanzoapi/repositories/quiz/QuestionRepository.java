package org.example.avanzoapi.repositories.quiz;

import org.example.avanzoapi.domain.entitites.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findByQuestionId(Integer questionId);
}

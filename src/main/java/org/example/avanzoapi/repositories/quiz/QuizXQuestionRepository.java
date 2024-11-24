package org.example.avanzoapi.repositories.quiz;

import org.example.avanzoapi.domain.entitites.QuizXQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuizXQuestionRepository extends JpaRepository<QuizXQuestion, UUID> {
}

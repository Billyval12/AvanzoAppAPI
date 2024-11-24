package org.example.avanzoapi.repositories.quiz;

import org.example.avanzoapi.domain.entitites.Quiz;
import org.example.avanzoapi.domain.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    Optional<List<Quiz>> findAllByUser(User user);
}

package org.example.avanzoapi.repositories;

import org.example.avanzoapi.domain.entitites.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findBySubjectId(Long subjectId);
}

package org.example.avanzoapi.services;

import org.example.avanzoapi.domain.entitites.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubjects();
    Subject getSubjectById(Long subjectId);
}

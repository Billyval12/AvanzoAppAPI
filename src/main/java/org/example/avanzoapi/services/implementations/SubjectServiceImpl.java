package org.example.avanzoapi.services.implementations;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.avanzoapi.domain.entitites.Subject;
import org.example.avanzoapi.repositories.SubjectRepository;
import org.example.avanzoapi.services.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSubjectById (Long subjectId) {
        return subjectRepository.findBySubjectId(subjectId).orElse(null);
    }
}

package org.example.avanzoapi.controllers;

import org.example.avanzoapi.domain.dtos.GeneralResponse;
import org.example.avanzoapi.domain.entitites.Subject;
import org.example.avanzoapi.services.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/subjects")
    public ResponseEntity<GeneralResponse> getAllSubjects() {

        List<Subject> subjects = subjectService.getAllSubjects();

        return GeneralResponse.getResponse(HttpStatus.OK, subjects);
    }
}

package org.example.avanzoapi.controllers;

import org.example.avanzoapi.domain.dtos.GeneralResponse;
import org.example.avanzoapi.domain.dtos.RankingDTO;
import org.example.avanzoapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    private final UserService userService;

    public RankingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/globalRank")
    public ResponseEntity<GeneralResponse> globalRank() {
        List<RankingDTO> ranking = userService.globalRanking();

        return GeneralResponse.getResponse(HttpStatus.OK, ranking);
    }
}

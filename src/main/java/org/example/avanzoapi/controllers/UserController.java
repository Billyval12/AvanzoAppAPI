package org.example.avanzoapi.controllers;

import jakarta.validation.Valid;
import org.example.avanzoapi.domain.dtos.ChangePassDTO;
import org.example.avanzoapi.domain.dtos.GeneralResponse;
import org.example.avanzoapi.domain.entitites.User;
import org.example.avanzoapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping("/changePass")
    public ResponseEntity<GeneralResponse> changePass (@RequestBody @Valid ChangePassDTO info) {
        User user = userService.findUserAuthenticated();

        Boolean changed = userService.changePass(user, info);

        if (!changed) {
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, "The passwords doesn't match");
        }

        return GeneralResponse.getResponse(HttpStatus.OK, "Password updated successfully");
    }
}

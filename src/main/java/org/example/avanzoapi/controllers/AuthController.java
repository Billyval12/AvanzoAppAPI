package org.example.avanzoapi.controllers;

import jakarta.validation.Valid;
import org.example.avanzoapi.domain.dtos.GeneralResponse;
import org.example.avanzoapi.domain.dtos.TokenDTO;
import org.example.avanzoapi.domain.dtos.auth.LoginDTO;
import org.example.avanzoapi.domain.dtos.auth.UserRegisterDTO;
import org.example.avanzoapi.domain.dtos.auth.WhoamiDTO;
import org.example.avanzoapi.domain.entitites.Token;
import org.example.avanzoapi.domain.entitites.User;
import org.example.avanzoapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> register(@RequestBody @Valid UserRegisterDTO info) {
        System.out.println("Registering user: " + info.getEmail());
        User existUser = userService.findUserByEmail(info.getEmail());

        if (existUser != null) {
            return GeneralResponse.getResponse(HttpStatus.CONFLICT, "User already exist!");
        }

        userService.registerUser(info);

        return GeneralResponse.getResponse(HttpStatus.CREATED, "User registered!");
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> login(@RequestBody @Valid LoginDTO info) {

        User user = userService.findUserByEmail(info.getUserEmail());

        if (user == null) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "User not found!");
        }

        if (userService.checkPassword(user, info.getPassword())) {
            return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials!");
        }

        if (!user.isActiveUser()) {
            return GeneralResponse.getResponse(HttpStatus.FORBIDDEN, "User is not active!");
        }

        Token token = userService.registerToken(user);

        return GeneralResponse.getResponse(HttpStatus.OK, "Login successful!", token.getContent());
    }

    @GetMapping("/whoami")
    public ResponseEntity<GeneralResponse> whoami() {
        User user = userService.findUserAuthenticated();

        WhoamiDTO info = userService.whoami(user);

        return GeneralResponse.getResponse(HttpStatus.OK, "Whoami successful!", info);
    }
}

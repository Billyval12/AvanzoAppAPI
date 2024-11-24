package org.example.avanzoapi.domain.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank
    private String userEmail;

    @NotBlank
    private String password;
}

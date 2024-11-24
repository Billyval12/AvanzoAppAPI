package org.example.avanzoapi.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePassDTO {

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}

package org.example.avanzoapi.domain.dtos.auth;

import lombok.Data;

@Data
public class WhoamiDTO {

    private String firstName;
    private String lastName;
    private String email;
    private Float points;
}

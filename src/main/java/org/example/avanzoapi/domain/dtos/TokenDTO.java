package org.example.avanzoapi.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.avanzoapi.domain.entitites.Token;

@Data
@NoArgsConstructor
public class TokenDTO {
    private String token;

    public TokenDTO(Token token) {
        this.token = token.getContent();
    }
}

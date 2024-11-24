package org.example.avanzoapi.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralResponse {
    private String message;
    private Object data;

    // Status, message, data
    public static ResponseEntity<GeneralResponse> getResponse(HttpStatus status, String message, Object data) {
        return new ResponseEntity<>(
                new GeneralResponse(message, data),
                status
        );
    }

    // Status, message
    public static ResponseEntity<GeneralResponse> getResponse(HttpStatus status, String message) {
        return getResponse(status, message, null);
    }

    // Status, data
    public static ResponseEntity<GeneralResponse> getResponse(HttpStatus status, Object data) {
        return getResponse(status, status.getReasonPhrase(), data);
    }
}
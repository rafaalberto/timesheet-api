package br.com.api.timesheet.resource.auth;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private boolean isValid;
}

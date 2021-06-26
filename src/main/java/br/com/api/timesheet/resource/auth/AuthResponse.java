package br.com.api.timesheet.resource.auth;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;

@JsonAutoDetect(fieldVisibility = ANY)
@AllArgsConstructor
public class AuthResponse {

  private String token;
  private boolean isValid;
}

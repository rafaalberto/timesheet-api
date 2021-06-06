package br.com.api.timesheet.security;

import br.com.api.timesheet.config.ApiErrorConfig;
import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.utils.JwtUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

import static br.com.api.timesheet.exception.ErrorResponse.ApiError;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (IOException e) {
            throw new BusinessException("error-position-8", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String username = ((UserSpringSecurity) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        String name = ((UserSpringSecurity) authResult.getPrincipal()).getName();

        Object json = new ObjectMapper().writeValueAsString(new UserResponse(username, token, name));
        response.getWriter().append(json.toString());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        Object json = new ObjectMapper().writeValueAsString(Collections.singletonList(
                new ApiError(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                        ApiErrorConfig.apiErrorMessageSource().getMessage("error-user-10", null, Locale.getDefault()))));

        response.getWriter().append(json.toString());
    }

    @RequiredArgsConstructor @Getter
    @JsonAutoDetect(fieldVisibility = ANY)
    private static class UserResponse {

        private final String username;
        private final String token;
        private final String name;
    }

}

package br.com.api.timesheet.resource.auth;

import br.com.api.timesheet.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class AuthResource {

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/auth/token/validate/{token}")
    public ResponseEntity<AuthResponse> validate(@PathVariable String token) {
        return ResponseEntity.ok(new AuthResponse(token, jwtUtil.isValidToken(token)));
    }

}

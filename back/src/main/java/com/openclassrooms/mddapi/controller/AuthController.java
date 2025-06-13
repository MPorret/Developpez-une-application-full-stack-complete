package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.LoginDTO;
import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.dto.TokenDTO;
import com.openclassrooms.mddapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for manage authentication
 * Endpoint for register and log user, and check if token is valid
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    /**
     * Save a new user
     *
     * @param registerDTO User infos needed to be registered
     * @return Response ok with JWT token if authentication succeed
     * */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) throws BadRequestException {
        String token = userService.registerUser(registerDTO);
        return ResponseEntity.ok().body(new TokenDTO(token));
    }

    /**
     * Log a user
     *
     * @param loginDto User infos needed to be logged
     * @return Response ok with JWT token if authentication succeed
     * */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto){
        String token = userService.loginUser(loginDto);
        return ResponseEntity.ok().body(new TokenDTO(token));
    }

    /**
     * Check if token is valid
     *
     * @param authentication Authentication info of logged user
     * @return Response ok if token is valid
     * */
    @GetMapping("/token")
    public ResponseEntity<?> validToken (Authentication authentication) {
        authentication.isAuthenticated();
        return ResponseEntity.ok().body("");
    }
}

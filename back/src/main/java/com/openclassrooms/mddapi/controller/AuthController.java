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

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) throws BadRequestException {
        String token = userService.registerUser(registerDTO);
        return ResponseEntity.ok().body(new TokenDTO(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto){
        String token = userService.loginUser(loginDto);
        return ResponseEntity.ok().body(new TokenDTO(token));
    }

    @GetMapping("/token")
    public ResponseEntity<?> validToken (Authentication authentication) {
        authentication.isAuthenticated();
        return ResponseEntity.ok().body("");
    }
}

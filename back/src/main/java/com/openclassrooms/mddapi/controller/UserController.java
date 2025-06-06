package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getUserInfos(Authentication authentication){
        return ResponseEntity.ok().body(userService.findUser(authentication));
    }

    @PatchMapping("/")
    public ResponseEntity<?> updateUserInfos(Authentication authentication, @RequestBody RegisterDTO registerDTO) throws BadRequestException {
        return ResponseEntity.ok().body(userService.updateUser(authentication, registerDTO));
    }
}

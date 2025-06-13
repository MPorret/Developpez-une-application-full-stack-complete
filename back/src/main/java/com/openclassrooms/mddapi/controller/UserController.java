package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for manage user
 * Endpoint for find and update user
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Find user
     *
     * @param authentication Authentication info of user logged
     * @return Response ok with user information
     * */
    @GetMapping("/")
    public ResponseEntity<?> getUserInfos(Authentication authentication){
        return ResponseEntity.ok().body(userService.findUser(authentication));
    }

    /**
     * Update user
     *
     * @param authentication Authentication info of logged user
     * @param registerDTO Updated info of the user
     * @return Response ok with JWT token if authentication succeed
     * */
    @PatchMapping("/")
    public ResponseEntity<?> updateUserInfos(Authentication authentication, @RequestBody RegisterDTO registerDTO) throws BadRequestException {
        return ResponseEntity.ok().body(userService.updateUser(authentication, registerDTO));
    }
}

package com.openclassrooms.mddapi.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.DTO.LoginRequest;
import com.openclassrooms.mddapi.DTO.LoginResponse;
import com.openclassrooms.mddapi.DTO.RegisterRequest;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.services.AuthService;
import com.openclassrooms.mddapi.services.DBUserService;
import com.openclassrooms.mddapi.services.JWTService;


@RestController
public class LoginController {

  @Autowired
  private DBUserService dbUserService;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  public JWTService jwtService;
  @Autowired
  public AuthService authService;

  private final AuthenticationManager authenticationManager;
  public Logger logger = LoggerFactory.getLogger(LoginController.class);

  public LoginController(JWTService jwtService, AuthenticationManager authenticationManager) {
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/api/auth/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

    logger.info("Login request email : " + loginRequest.getIdentifier());
    logger.info("Login request password : " + loginRequest.getPassword());
    logger.info("encrypted password : " + bCryptPasswordEncoder.encode(loginRequest.getPassword()));

    try {

      logger.info("Trying to authenticate user");
      // Appeler le service d'authentification pour obtenir le token
      LoginResponse loginResponse = authService.login(loginRequest.getIdentifier(), loginRequest.getPassword());

      logger.info("Login Response : " + loginResponse);

      return ResponseEntity.ok(loginResponse);

    } catch (BadCredentialsException ex) {

      throw new BadCredentialsException("Invalid credentials provided.");

    }
  }

  @PostMapping("/api/auth/register")
  public ResponseEntity<LoginResponse> registerUser(@RequestBody RegisterRequest newUser) {

      logger.info("New user email : " + newUser.getEmail());
      logger.info("New user password : " + newUser.getPassword());

      logger.info("encrypted password : " + bCryptPasswordEncoder.encode(newUser.getPassword()));

      // Sauvegarder le nouvel utilisateur :
      // appelle le service et construit un nouvel user sur la base de register request
      dbUserService.createUserFromRegisterRequest(newUser);

      try {
        DBUser dbUser = dbUserService.findByEmail(newUser.getEmail());

        // Convertir DBUser en User générer le token à renvoyer
        User userDetails = new User(dbUser.getEmail(), dbUser.getPassword(), Collections.emptyList());

        // Générer le token (cela permet de le renvoyer afin de connecter l'utilisateur directement après son inscription)
        String token = jwtService.generateToken(userDetails);

        // Envoi du token JWT
        LoginResponse loginResponse = new LoginResponse(token);

        return ResponseEntity.ok(loginResponse);
    } catch (Exception ex) {
        logger.error("Error :", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/api/logout")
  public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth != null) {
          new SecurityContextLogoutHandler().logout(request, response, auth);
      }

      Map<String, String> responseBody = new HashMap<>();
      responseBody.put("message", "logged out successfully");

      return ResponseEntity.ok(responseBody);
  }

}

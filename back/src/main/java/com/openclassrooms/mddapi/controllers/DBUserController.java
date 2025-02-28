package com.openclassrooms.mddapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.DTO.DBUserDTO;
import com.openclassrooms.mddapi.services.DBUserService;

@RestController
@RequestMapping("/api")
public class DBUserController {

  @Autowired
  private DBUserService DBUserService;

  // @GetMapping(value = "/user/{id}", produces = "application/json")
  // public ResponseEntity<Optional<DBUserDTO>> getUserById(@PathVariable Long id) {

  //   // Récupération des informations de l'utilisateur
  //   Optional<DBUserDTO> dBUserDTO = DBUserService.getUserById(id);

  //   if (dBUserDTO == null) {
  //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
  //   }

  //   return ResponseEntity.ok(dBUserDTO);
  // }

  @GetMapping("/auth/me")
  public ResponseEntity<Optional<DBUserDTO>> getCurrentUser() {

    // Récupération de l'utilisateur actuellement connecté
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(401).build();
    }

    String email = authentication.getName();

    Optional<DBUserDTO> user = DBUserService.getUserByEmail(email);

    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(user);
  }
}

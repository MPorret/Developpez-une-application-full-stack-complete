package com.openclassrooms.mddapi.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class JWTService {

  private final JwtEncoder jwtEncoder;
  private final Dotenv dotenv = Dotenv.configure().load();
  private final int jwtExpirationDays = Integer.parseInt(dotenv.get("JWT_EXPIRATION_DAYS", "1"));

  public JWTService(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  public String generateToken(User authenticatedUser) {
    // initialisation de la date actuelle (pour gérer la date d'expiration du token)
    Instant now = Instant.now();
    // Récupération des rôles de l'utilisateur authentifié
    String scope = authenticatedUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    // Les claims sont les informations a stocker dans le token
    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt(now)
      .expiresAt(now.plus(jwtExpirationDays, ChronoUnit.DAYS))
      .subject(authenticatedUser.getUsername())
      .claim("scope", scope)
      .build();

    // Création des paramètres d'encodage du token (algorithme de signature et claims)
    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);

    // Génétration et encodage du token selon les paramètres définis
    return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
  }
}

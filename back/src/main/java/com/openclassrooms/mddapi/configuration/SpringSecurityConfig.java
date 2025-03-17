package com.openclassrooms.mddapi.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

import io.github.cdimascio.dotenv.Dotenv;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  private final Dotenv dotenv = Dotenv.configure().load();
  private final String jwtKey = dotenv.get("256_JWT_KEY");

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
        // On désactive le stockage de la session sur le serveur
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Routes accessibles sans authentification
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(new AntPathRequestMatcher("/api/auth/login")).permitAll();
                auth.requestMatchers(new AntPathRequestMatcher("/api/auth/register")).permitAll();
                // Toutes les autres routes nécessitent une authentification
                auth.anyRequest().authenticated();
            })
            // On indique que le serveur accepte des tokens JWT pour l'authentification
            .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
            .build();
    }

    // Méthode permettant de décoder le token JWT
  @Bean
  public JwtDecoder jwtDecoder() {
    // Définition de la clé secrète
    SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length,"RSA");
    // Création du décodeur
    NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();

    // Vérifier l'expiration du jeton
    jwtDecoder.setJwtValidator(JwtValidators.createDefaultWithIssuer("self"));

    return jwtDecoder;
  }

  // Méthode permettant d'encoder le token JWT
  @Bean
  public JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
  }

  // Méthode permettant de d'encrypter le mot de passe
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Méthode permettant de configurer le gestionnaire d'authentification
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailService) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http
      .getSharedObject(AuthenticationManagerBuilder.class);

    authenticationManagerBuilder
            .userDetailsService(userDetailService)
            .passwordEncoder(bCryptPasswordEncoder);
    return authenticationManagerBuilder.build();
  }

}

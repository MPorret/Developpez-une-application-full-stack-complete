package com.openclassrooms.mddapi.service;

import ch.qos.logback.core.util.StringUtil;
import com.openclassrooms.mddapi.dto.LoginDTO;
import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.dto.TokenDTO;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper mapper;

    public String registerUser(RegisterDTO registerDTO) {

        if (!StringUtils.hasLength(registerDTO.getEmail()) || !StringUtils.hasLength(registerDTO.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email or username empty");
        }

        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()
                || userRepository.findByName(registerDTO.getName()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"email or username already used");
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()!?])(?=\\S+$).{8,255}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(registerDTO.getPassword());
        if (!m.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid password");
        }

        User newUser = mapper.toEntity(registerDTO);
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userRepository.save(newUser);

        return authUser(
                registerDTO.getEmail(),
                registerDTO.getPassword()
        );
    }

    public String loginUser(LoginDTO user) {
        User foundUser;

        if (user.getUsername().contains("@")) {
            foundUser = userRepository.findByEmail(user.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        } else {
            foundUser = userRepository.findByName(user.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        }

        if (!passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Password incorrect");
        }

        return authUser(foundUser.getEmail(), user.getPassword());
    }

    public String authUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return formatTokenResponse(authentication);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed: " + ex.getMessage());
        }
    }

    public String formatTokenResponse(Authentication authentication){
        return jwtService.generateToken(authentication);
    }
}

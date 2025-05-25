package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper mapper;

    public String registerUser(RegisterDTO registerDTO) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()!?])(?=\\S+$).{8,255}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(registerDTO.getPassword());

        if (registerDTO.getEmail().isEmpty() || registerDTO.getName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email or username empty");
        }

        if (!m.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid password");
        }

        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()
                || userRepository.findByName(registerDTO.getName()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"email or username already used");
        }

        User newUser = mapper.toEntity(registerDTO);
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userRepository.save(newUser);

        return authUser(
                registerDTO.getEmail(),
                registerDTO.getPassword()
        );
    }

    public String authUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return formatTokenResponse(authentication);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed: " + ex.getMessage());
        }
    }

    public String formatTokenResponse(Authentication authentication){
        return jwtService.generateToken(authentication);
    }
}

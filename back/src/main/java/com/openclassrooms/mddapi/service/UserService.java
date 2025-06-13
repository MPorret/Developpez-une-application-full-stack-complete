package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.exception.ForbiddenException;
import com.openclassrooms.mddapi.exception.UnauthorizedException;
import com.openclassrooms.mddapi.exception.UserNameNotFoundException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service to manage users.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper mapper;
    private final TopicService topicService;

    /**
     * Save a new user
     *
     * @param registerDTO User infos needed to be registered
     * @return JWT token if authentication succeed
     * @throws BadRequestException if email or username are empty, if  email or username are already used, if password is not valid
     * */
    public String registerUser(RegisterDTO registerDTO) throws BadRequestException {

        if (!StringUtils.hasLength(registerDTO.getEmail()) || !StringUtils.hasLength(registerDTO.getName())){
            throw new BadRequestException("email or username empty");
        }

        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()
                || userRepository.findByName(registerDTO.getName()).isPresent()){
            throw new BadRequestException("email or username already used");
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()!?])(?=\\S+$).{8,255}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(registerDTO.getPassword());
        if (!m.matches()) {
            throw new BadRequestException("invalid password");
        }

        User newUser = mapper.toEntity(registerDTO);
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userRepository.save(newUser);

        return authUser(
                registerDTO.getEmail(),
                registerDTO.getPassword()
        );
    }

    /**
     * Log a user
     *
     * @param user User infos needed to be logged
     * @return JWT token if authentication succeed
     * @throws UserNameNotFoundException if email or username does not exist
     * @throws ForbiddenException if password does not match with saved password
     * */
    public String loginUser(LoginDTO user) {
        User foundUser;

        if (user.getUsername().contains("@")) {
            foundUser = userRepository.findByEmail(user.getUsername())
                    .orElseThrow(() -> new UserNameNotFoundException(user.getUsername()));
        } else {
            foundUser = userRepository.findByName(user.getUsername())
                    .orElseThrow(() -> new UserNameNotFoundException(user.getUsername()));
        }

        if (!passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new ForbiddenException("Password incorrect");
        }

        return authUser(foundUser.getEmail(), user.getPassword());
    }

    /**
     * Auth user
     *
     * @param email User email to authenticate it
     * @param password User password to authenticate it
     * @return JWT token if authentication succeed
     * @throws UnauthorizedException if authentication failed
     * */
    public String authUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return formatTokenResponse(authentication);
        } catch (Exception ex) {
            throw new UnauthorizedException("Authentication failed: " + ex.getMessage());
        }
    }

    /**
     * Generate token
     *
     * @param authentication Authentication infos
     * @return JWT token with authentication infos
     * */
    public String formatTokenResponse(Authentication authentication){
        return jwtService.generateToken(authentication);
    }

    /**
     * Find user
     *
     * @param authentication Authentication info of user logged
     * @return User information
     * @throws UserNameNotFoundException if user is not found with this name
     * */
    public UserDTO findUser(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNameNotFoundException(authentication.getName()));

        List<Topic> topics = topicService.findAllTopicsByUser(user);

        UserDTO userDTO = mapper.toDto(user);
        userDTO.setTopics(topics);

        return userDTO;
    }

    /**
     * Update user
     *
     * @param authentication Authentication info of logged user
     * @param updatedUser Updated info of the user
     * @return JWT token if authentication succeed
     * @throws UserNameNotFoundException if user is not found with this name
     * @throws BadRequestException if email or username are empty, if  email or username are already used, if password is not valid
     * */
    public UserDTO updateUser(Authentication authentication, RegisterDTO updatedUser) throws BadRequestException {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNameNotFoundException(authentication.getName()));

        if (!StringUtils.hasLength(updatedUser.getEmail()) || !StringUtils.hasLength(updatedUser.getName())){
            throw new BadRequestException("email or username empty");
        }

        if (!user.getEmail().equals(updatedUser.getEmail())) {
            if (userRepository.findByEmail(updatedUser.getEmail()).isPresent()){
                throw new BadRequestException("email already used");
            }
        }
        user.setEmail(updatedUser.getEmail());

        if (!user.getName().equals(updatedUser.getName())) {
            if (userRepository.findByName(updatedUser.getName()).isPresent()){
                throw new BadRequestException("username already used");
            }
        }
        user.setName(updatedUser.getName());

        if (StringUtils.hasLength(updatedUser.getPassword())) {
            String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()!?])(?=\\S+$).{8,255}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(updatedUser.getPassword());
            if (!m.matches()) {
                throw new BadRequestException("invalid password");
            }
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        List<Topic> topics = topicService.findAllTopicsByUser(user);

        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, List.of());

        User userUpdated = userRepository.save(user);
        UserDTO userDTO = mapper.toDto(userUpdated);
        userDTO.setTopics(topics);
        userDTO.setToken(formatTokenResponse(newAuthentication));

        return userDTO;
    }
}

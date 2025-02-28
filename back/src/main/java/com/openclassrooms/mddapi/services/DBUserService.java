package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.DTO.DBUserDTO;
import com.openclassrooms.mddapi.DTO.RegisterRequest;
import com.openclassrooms.mddapi.controllers.LoginController;
import com.openclassrooms.mddapi.exceptions.EmailAlreadyRegisteredException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.DBUserRepository;

@Service
public class DBUserService {

  @Autowired
  private DBUserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public Logger logger = LoggerFactory.getLogger(LoginController.class);


  public DBUser findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  // Créer un nouvel utilisateur
    public DBUserDTO createUser(DBUserDTO userDTO) {
        DBUser user = new DBUser();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        DBUser savedUser = userRepository.save(user);
        return new DBUserDTO(savedUser.getId(), savedUser.getEmail(), savedUser.getUsername(), savedUser.getPassword());
    }

    // Récupérer un utilisateur par son ID
    public Optional<DBUserDTO> getUserById(Long id) {
        Optional<DBUser> user = userRepository.findById(id);
        return user.map(u -> new DBUserDTO(u.getId(), u.getEmail(), u.getUsername(), u.getPassword()));
    }

    // Récupérer un utilisateur par son nom d'utilisateur
    public Optional<DBUserDTO> getUserByUsername(String username) {
        Optional<DBUser> user = userRepository.findByUsername(username);
        return user.map(u -> new DBUserDTO(u.getId(), u.getEmail(), u.getUsername(), u.getPassword()));
    }

    // Récupérer un utilisateur par son email
    public Optional<DBUserDTO> getUserByEmail(String email) {
      Optional<DBUser> user = userRepository.findByEmail(email);
      logger.info("Found user in DB: " + user);
      return user.map(u -> new DBUserDTO(u.getId(), u.getEmail(), u.getUsername(), u.getPassword()));
    }

    // Récupérer tous les utilisateurs
    public List<DBUserDTO> getAllUsers() {
        List<DBUser> users = userRepository.findAll();
        return users.stream()
                .map(user -> new DBUserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getPassword()))
                .toList();
    }

    // Mettre à jour un utilisateur
    public Optional<DBUserDTO> updateUser(Long id, DBUserDTO userDTO) {
        Optional<DBUser> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            DBUser user = userOptional.get();
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            DBUser updatedUser = userRepository.save(user);
            return Optional.of(new DBUserDTO(updatedUser.getId(), updatedUser.getEmail(), updatedUser.getUsername(), updatedUser.getPassword()));
        }
        return Optional.empty();
    }

    // Supprimer un utilisateur
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public DBUser createUserFromRegisterRequest(RegisterRequest request) {

      if(userRepository.findByUsername(request.getUsername()).isPresent()) {
        throw new EmailAlreadyRegisteredException("Email already registered");
      }

      // Créer un nouvel objet DBUser
      DBUser newUser = new DBUser();

      // Mapper les champs de RegisterRequest à DBUser
      newUser.setUsername(request.getUsername());
      newUser.setEmail(request.getEmail());

      String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
      newUser.setPassword(encodedPassword);

      // Sauvegarder l'utilisateur dans la base de données
      return userRepository.save(newUser);
    }

    // /!\ L'enregistrement ne fonctionne pas /!\

}

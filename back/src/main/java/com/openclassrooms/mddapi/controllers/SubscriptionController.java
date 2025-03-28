package com.openclassrooms.mddapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.DTO.SubscriptionDTO;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.services.SubscriptionService;


@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

  @Autowired
  private DBUserRepository dbUserRepository;

  public Logger logger = LoggerFactory.getLogger(LoginController.class);

  @Autowired
    private SubscriptionService subscriptionService;

    // @PostMapping
    // public ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
    //     SubscriptionDTO createdSubscription = subscriptionService.createSubscription(
    //         subscriptionDTO.getUserId(),
    //         subscriptionDTO.getTopicId()
    //     );

    //     return ResponseEntity.ok(createdSubscription);
    // }

    @PostMapping
    public ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody Map<String, Long> requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String email = authentication.getName();

        Optional<DBUser> userOpt = dbUserRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        DBUser user = userOpt.get();
        Long userId = user.getId();

        Long topicId = requestBody.get("topicId");

        if (topicId == null) {
            return ResponseEntity.status(400).body(null);
        }

        SubscriptionDTO createdSubscription = subscriptionService.createSubscription(userId, topicId);

        return ResponseEntity.ok(createdSubscription);
    }


    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getUsersSubscriptions() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null || !authentication.isAuthenticated()) {
          return ResponseEntity.status(401).build();
      }

      String email = authentication.getName();

      Optional<DBUser> userOpt = dbUserRepository.findByEmail(email);
      if (userOpt.isEmpty()) {
          return ResponseEntity.status(404).body(null);
      }

      DBUser user = userOpt.get();
      Long userId = user.getId();

      List<SubscriptionDTO> usersSubscriptions = subscriptionService.getSubscriptionsByUserId(userId);

      return ResponseEntity.ok(usersSubscriptions);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteSubscription(@RequestParam Long topicId) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null || !authentication.isAuthenticated()) {
          return ResponseEntity.status(401).build();
      }

      String email = authentication.getName();

      Optional<DBUser> userOpt = dbUserRepository.findByEmail(email);
      if (userOpt.isEmpty()) {
          return ResponseEntity.status(404).body(null);
      }

      DBUser user = userOpt.get();
      Long userId = user.getId();

      subscriptionService.deleteSubscription(userId, topicId);

      Map<String, String> response = new HashMap<>();
      response.put("message", "Subscription deleted successfully");
      return ResponseEntity.ok(response);
    }

}

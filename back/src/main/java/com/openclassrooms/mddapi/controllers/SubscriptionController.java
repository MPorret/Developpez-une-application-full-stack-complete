package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.DTO.SubscriptionDTO;
import com.openclassrooms.mddapi.services.SubscriptionService;


@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

  public Logger logger = LoggerFactory.getLogger(LoginController.class);

  @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO createdSubscription = subscriptionService.createSubscription(
            subscriptionDTO.getUserId(),
            subscriptionDTO.getTopicId()
        );

        return ResponseEntity.ok(createdSubscription);
    }

    @GetMapping
    public List<SubscriptionDTO> getUsersSubscriptions(@RequestParam Long userId) {
      // logger.info("Getting subscriptions for user with id: " + userId);
        List<SubscriptionDTO> usersSubscriptions = subscriptionService.getSubscriptionsByUserId(userId);

        return usersSubscriptions;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSubscription(@RequestParam Long userId, @RequestParam Long topicId) {
        subscriptionService.deleteSubscription(userId, topicId);
        return ResponseEntity.ok("Subscription deleted successfully");
    }

// /!\ Les requêtes renvoient toutes des erreurs 405 /!\
// /!\ Les requêtes renvoient toutes des erreurs 405 /!\
// /!\ Les requêtes renvoient toutes des erreurs 405 /!\
// /!\ Les requêtes renvoient toutes des erreurs 405 /!\
// /!\ Les requêtes renvoient toutes des erreurs 405 /!\


}

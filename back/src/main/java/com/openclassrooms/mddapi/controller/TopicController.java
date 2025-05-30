package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubscriptionDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.TopicsDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @GetMapping("/")
    public ResponseEntity<?> getAllTopics (Authentication authentication){
        List<TopicsDTO> topics = topicService.findAllTopics(authentication);
        return ResponseEntity.ok().body(topics);
    }

    @PostMapping("/")
    public ResponseEntity<?> createTopic (@RequestBody TopicDTO topicDTO){
        topicService.createTopic(topicDTO);
        return ResponseEntity.ok().body("Topic created");
    }

    @PostMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribeTopic (Authentication authentication, @PathVariable Integer id) {
        try {
            List<TopicsDTO> topicsUpdated = topicService.subscribeTopic(authentication, id);
            return ResponseEntity.ok().body(topicsUpdated);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PostMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribeTopic (Authentication authentication, @PathVariable Integer id) {
        try {
            List<TopicsDTO> subscriptionUpdated = topicService.unsubscribeTopic(authentication, id);
            return ResponseEntity.ok().body(subscriptionUpdated);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}

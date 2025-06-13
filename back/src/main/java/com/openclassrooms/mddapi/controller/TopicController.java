package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.TopicsDTO;
import com.openclassrooms.mddapi.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for manage topic
 * Endpoint for find all topics and find, subscribe and unsubscribe a topic
 */
@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    /**
     * Find all topics
     *
     * @param authentication Authentication info of logged user
     * @return Response Entity with list of all topics with subscription info
     * */
    @GetMapping("/")
    public ResponseEntity<?> getAllTopics (Authentication authentication){
        List<TopicsDTO> topics = topicService.findAllTopics(authentication);
        return ResponseEntity.ok().body(topics);
    }

    /**
     * Save a new topic
     *
     * @param topicDTO Authentication info of logged user
     * */
    @PostMapping("/")
    public ResponseEntity<?> createTopic (@RequestBody TopicDTO topicDTO){
        topicService.createTopic(topicDTO);
        return ResponseEntity.ok().body("Topic created");
    }

    /**
     * Subscribe a topic
     *
     * @param authentication Authentication info of logged user
     * @param id Id of topic to subscribe
     * @return Response Entity with list of all topics with subscription info
     * */
    @PostMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribeTopic (Authentication authentication, @PathVariable Integer id) {
        List<TopicsDTO> topicsUpdated = topicService.subscribeTopic(authentication, id);
        return ResponseEntity.ok().body(topicsUpdated);
    }

    /**
     * Unsubscribe a topic
     *
     * @param authentication Authentication info of logged user
     * @param id Id of topic to unsubscribe
     * @return Response Entity with list of all topics with subscription info
     * */
    @PostMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribeTopic (Authentication authentication, @PathVariable Integer id) {
        List<TopicsDTO> subscriptionUpdated = topicService.unsubscribeTopic(authentication, id);
        return ResponseEntity.ok().body(subscriptionUpdated);
    }
}

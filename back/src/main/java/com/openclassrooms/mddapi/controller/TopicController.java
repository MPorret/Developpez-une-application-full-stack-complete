package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.TopicService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @GetMapping("/")
    public ResponseEntity<?> getAllTopics (){
        List<Topic> topics = topicService.findAllTopics();
        return ResponseEntity.ok().body(topics);
    }

    @PostMapping("/")
    public ResponseEntity<?> createTopic (@RequestBody TopicDTO topicDTO){
        topicService.createTopic(topicDTO);
        return ResponseEntity.ok().body("Topic created");
    }
}

package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.DTO.TopicDTO;
import com.openclassrooms.mddapi.services.TopicService;


@RestController
@RequestMapping("/api/topics")
public class TopicsController {

  @Autowired
  private TopicService topicService;

  @GetMapping
  public List<TopicDTO> getAllTopics(){
    return topicService.getAllTopics();
  }

  @GetMapping("/user/{userId}")
  public List<TopicDTO> getTopicsByUserId(@PathVariable Long userId) {
    return topicService.getTopicsByUserId(userId);
  }

}

package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.DTO.TopicDTO;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

@Service
public class TopicService {

  // private static final Logger logger = LoggerFactory.getLogger(TopicService.class);

  @Autowired
  private TopicRepository topicRepository;

  public List<TopicDTO> getAllTopics(){
    List<Topic> topics = topicRepository.findAll();

    // logger.info("Number of topics retrieved: {}", topics.size());

    return topics.stream()
                .map(topic -> new TopicDTO(topic.getId(), topic.getName(), topic.getDescription()))
                .collect(Collectors.toList());
  }
}

package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.DTO.TopicDTO;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;

@Service
public class TopicService {

  @Autowired
  private TopicRepository topicRepository;

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  public List<TopicDTO> getAllTopics(){
    List<Topic> topics = topicRepository.findAll();

    // logger.info("Number of topics retrieved: {}", topics.size());

    return topics.stream()
                .map(topic -> new TopicDTO(topic.getId(), topic.getName(), topic.getDescription()))
                .collect(Collectors.toList());
  }

  public List<TopicDTO> getTopicsByUserId(Long userId) {
    List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);

    List<Long> topicIds = subscriptions.stream()
                                      .map(Subscription::getTopicId)
                                      .collect(Collectors.toList());

    List<Topic> topics = topicRepository.findAllById(topicIds);

    return topics.stream()
                .map(topic -> new TopicDTO(
                  topic.getId(),
                  topic.getName(),
                  topic.getDescription()
                ))
                .collect(Collectors.toList());
  }
}

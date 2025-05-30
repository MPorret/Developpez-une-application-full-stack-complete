package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.TopicsDTO;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final TopicMapper mapper;

    public List<TopicsDTO> findAllTopics(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<TopicsDTO> topics = mapper.toDtoList(topicRepository.findAll());

        for (TopicsDTO topicDTO: topics) {
            Topic topic = topicRepository.findById(topicDTO.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
            Optional<Subscription> subscription = subscriptionRepository.findByUserAndTopic(user, topic);
            topicDTO.setIsSubscribed(subscription.isPresent());
        }

        return topics;
    }

    public void createTopic (TopicDTO topicDTO) {
        topicRepository.save(mapper.toEntity(topicDTO));
    }

    public List<TopicsDTO> subscribeTopic(Authentication authentication, Integer topicId){
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        Subscription subscription = new Subscription(user, topic);
        subscriptionRepository.save(subscription);

        return findAllTopics(authentication);
    }
}

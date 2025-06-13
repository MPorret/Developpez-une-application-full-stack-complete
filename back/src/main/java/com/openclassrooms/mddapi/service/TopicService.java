package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.TopicsDTO;
import com.openclassrooms.mddapi.exception.SubscriptionNotFoundException;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.exception.UserNameNotFoundException;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service to manage topics.
 */
@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final TopicMapper mapper;


    /**
     * Find all topics
     *
     * @param authentication Authentication info of logged user
     * @return List of all topics with subscription info
     * @throws UserNameNotFoundException if user is not found with this name
     * @throws TopicNotFoundException if topic doesn't exist with id
     * */
    public List<TopicsDTO> findAllTopics(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNameNotFoundException(authentication.getName()));

        List<TopicsDTO> topics = mapper.toDtoList(topicRepository.findAll());

        for (TopicsDTO topicDTO: topics) {
            Topic topic = topicRepository.findById(topicDTO.getId())
                    .orElseThrow(() -> new TopicNotFoundException(topicDTO.getId()));
            Optional<Subscription> subscription = subscriptionRepository.findByUserAndTopic(user, topic);
            topicDTO.setIsSubscribed(subscription.isPresent());
        }

        return topics;
    }

    /**
     * Save a new topic
     *
     * @param topicDTO Authentication info of logged user
     * */
    public void createTopic (TopicDTO topicDTO) {
        topicRepository.save(mapper.toEntity(topicDTO));
    }

    /**
     * Subscribe a topic
     *
     * @param authentication Authentication info of logged user
     * @param topicId Id of topic to subscribe
     * @return List of all topics with subscription info
     * @throws UserNameNotFoundException if user is not found with this name
     * @throws TopicNotFoundException if topic doesn't exist with id
     * */
    public List<TopicsDTO> subscribeTopic(Authentication authentication, Integer topicId){
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNameNotFoundException(authentication.getName()));
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId));
        Subscription subscription = new Subscription(user, topic);
        subscriptionRepository.save(subscription);

        return findAllTopics(authentication);
    }

    /**
     * Unsubscribe a topic
     *
     * @param authentication Authentication info of logged user
     * @param topicId Id of topic to unsubscribe
     * @return List of all topics with subscription info
     * @throws UserNameNotFoundException if user is not found with this name
     * @throws TopicNotFoundException if topic doesn't exist with id
     * @throws SubscriptionNotFoundException if subscription doesn't exist with id
     * */
    public List<TopicsDTO> unsubscribeTopic (Authentication authentication, Integer topicId) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNameNotFoundException(authentication.getName()));
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId));
        Subscription subscription = subscriptionRepository.findByUserAndTopic(user, topic).orElseThrow(SubscriptionNotFoundException::new);
        subscriptionRepository.delete(subscription);
        return mapper.toDtoList(findAllTopicsByUser(user));
    }

    /**
     * Find all topics of a user
     *
     * @param user User info
     * @return List of all topics
     * */
    public List<Topic> findAllTopicsByUser(User user){
        List<Topic> topics = new ArrayList<>();
        List<Subscription> subscriptions = subscriptionRepository.findAllByUser(user);
        for (Subscription subscription : subscriptions) {
            topics.add(subscription.getTopic());
        }
        return topics;
    }
}

package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.DTO.SubscriptionDTO;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    @Autowired
  private SubscriptionRepository subscriptionRepository;

  public List<SubscriptionDTO> getSubscriptionsByUserId(Long userId) {
    List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);

    return subscriptions.stream()
                        .map(subscription -> new SubscriptionDTO(
                            subscription.getId(),
                            subscription.getUserId(),
                            subscription.getTopicId()
                        ))
                        .collect(Collectors.toList());
  }

  public SubscriptionDTO createSubscription(Long userId, Long topicId) {
    Subscription subscription = new Subscription();
    subscription.setUserId(userId);
    subscription.setTopicId(topicId);

    Subscription savedSubscription = subscriptionRepository.save(subscription);

    return new SubscriptionDTO(
        savedSubscription.getId(),
        savedSubscription.getUserId(),
        savedSubscription.getTopicId()
    );
  }

  public void deleteSubscription(Long userId, Long topicId) {
    Subscription subscription = subscriptionRepository.findByUserIdAndTopicId(userId, topicId)
        .orElseThrow(() -> new RuntimeException("Subscription not found"));

    subscriptionRepository.delete(subscription);
  }

}

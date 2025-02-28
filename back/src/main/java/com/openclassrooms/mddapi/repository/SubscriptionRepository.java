package com.openclassrooms.mddapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.models.Subscription;
// import com.openclassrooms.mddapi.models.Topic;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
  public List<Subscription> findByUserId(Long userId);
  public Optional<Subscription> findByUserIdAndTopicId(Long userId, Long topicId);
  // public Optional<Subscription> findByUserAndTopic(DBUser user, Topic topic);
}

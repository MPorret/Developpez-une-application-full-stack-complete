package com.openclassrooms.mddapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.models.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

}

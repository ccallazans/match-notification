package com.ccallazans.matchnotification.subscription.repository;

import com.ccallazans.matchnotification.subscription.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findByName(String name);

    boolean existsByName(String name);
}

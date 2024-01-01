package com.ccallazans.matchnotification.notification.repository;

import com.ccallazans.matchnotification.notification.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByEmail(String email);

    List<Subscription> findByTopicsName(String topic);

    List<Subscription> findByTopicsNameIn(List<String> topics);
}

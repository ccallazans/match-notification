package com.ccallazans.matchnotification.subscription.repository;

import com.ccallazans.matchnotification.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByEmail(String email);
}

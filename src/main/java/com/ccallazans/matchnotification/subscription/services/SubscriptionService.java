package com.ccallazans.matchnotification.subscription.services;

import com.ccallazans.matchnotification.exceptions.ValidationException;
import com.ccallazans.matchnotification.subscription.domain.SubscriptionDomain;
import com.ccallazans.matchnotification.subscription.entity.Subscription;
import com.ccallazans.matchnotification.subscription.entity.Topic;
import com.ccallazans.matchnotification.subscription.mappers.SubscriptionMapper;
import com.ccallazans.matchnotification.subscription.repository.SubscriptionRepository;
import com.ccallazans.matchnotification.subscription.repository.TopicRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class SubscriptionService {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final SubscriptionRepository subscriptionRepository;
    private final TopicRepository topicRepository;

    @Transactional
    public SubscriptionDomain subscribe(String email, String topicName) {
        validateEmail(email);
        validateTopicName(topicName);

        var topic = getValidTopic(topicName.toUpperCase());

        var subscription = Subscription.builder()
                .email(email.toLowerCase())
                .topics(Set.of(topic))
                .build();

        if (subscriptionRepository.existsByEmail(subscription.getEmail())) {
            throw new ValidationException("Email already exists: " + subscription.getEmail());
        }

        var savedSubscription = subscriptionRepository.saveAndFlush(subscription);

        return SubscriptionMapper.INSTANCE.toSubscriptionDomain(savedSubscription);
    }

    private void validateEmail(String email) {
        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new ValidationException("Email is not valid: " + email);
        }
    }

    private void validateTopicName(String topicName) {
        if (topicName.isBlank()) {
            throw new ValidationException("Topic cannot be empty");
        }
    }

    private Topic getValidTopic(String topicName) {
        return Optional.ofNullable(topicRepository.findByName(topicName))
                .orElseThrow(() -> new ValidationException("Topic is not valid: " + topicName));
    }
}

package com.ccallazans.matchnotification.notification.services;

import com.ccallazans.matchnotification.exceptions.NotFoundException;
import com.ccallazans.matchnotification.exceptions.ValidationException;
import com.ccallazans.matchnotification.notification.domain.SubscriptionDomain;
import com.ccallazans.matchnotification.notification.domain.mappers.SubscriptionMapper;
import com.ccallazans.matchnotification.notification.entity.Subscription;
import com.ccallazans.matchnotification.notification.entity.Topic;
import com.ccallazans.matchnotification.notification.repository.SubscriptionRepository;
import com.ccallazans.matchnotification.notification.repository.TopicRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

        var topic = validateTopic(topicName.toUpperCase());

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

    public SubscriptionDomain getSubscriptionById(Long id) {
        var subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invalid subscription id: " + id));

        return SubscriptionMapper.INSTANCE.toSubscriptionDomain(subscription);
    }

    public List<SubscriptionDomain> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return SubscriptionMapper.INSTANCE.toSubscriptionDomains(subscriptions);
    }

    public List<SubscriptionDomain> getSubscriptionsByTopic(String topic) {
        List<Subscription> subscriptions = subscriptionRepository.findByTopicsName(topic);
        return SubscriptionMapper.INSTANCE.toSubscriptionDomains(subscriptions);
    }

    private void validateEmail(String email) {
        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new ValidationException("Email is not valid: " + email);
        }
    }

    private Topic validateTopic(String topicName) {
        if (topicName.isBlank()) {
            throw new ValidationException("Topic cannot be empty");
        }

        return Optional.ofNullable(topicRepository.findByName(topicName))
                .orElseThrow(() -> new ValidationException("Topic is not valid: " + topicName));
    }
}

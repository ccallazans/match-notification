package com.ccallazans.matchnotification.subscription;

import com.ccallazans.matchnotification.exceptions.NotFoundException;
import com.ccallazans.matchnotification.exceptions.ValidationException;
import com.ccallazans.matchnotification.notification.repository.TopicRepository;
import com.ccallazans.matchnotification.subscription.domain.SubscriptionDomain;
import com.ccallazans.matchnotification.subscription.entity.Subscription;
import com.ccallazans.matchnotification.subscription.mappers.SubscriptionMapper;
import com.ccallazans.matchnotification.subscription.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubscriptionService {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final SubscriptionRepository subscriptionRepository;
    private final TopicRepository topicRepository;

    @Transactional
    public SubscriptionDomain subscribe(String email, List<String> topics) {

        if (topics.isEmpty()) {
            throw new ValidationException("Empty topic list!");
        }

        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new ValidationException("Email is not valid: " + email);
        }

        if (subscriptionRepository.existsByEmail(email)) {
            throw new ValidationException("Email already exists");
        }

        var validTopics = topics.stream()
                .map(topic -> Optional.ofNullable(topicRepository.findByName(topic))
                        .orElseThrow(() -> new ValidationException("Topic is not valid: " + topic.toUpperCase()))
                )
                .collect(Collectors.toSet());

        var subscription = Subscription.builder()
                .email(email.toLowerCase())
                .topics(validTopics)
                .build();

        subscriptionRepository.save(subscription);

        return SubscriptionMapper.INSTANCE.toSubscriptionDomain(subscription);
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

    public List<SubscriptionDomain> getSubscriptionsByTopics(List<String> topic) {
        List<Subscription> subscriptions = subscriptionRepository.findByTopicsNameIn(topic);
        return SubscriptionMapper.INSTANCE.toSubscriptionDomains(subscriptions);
    }
}

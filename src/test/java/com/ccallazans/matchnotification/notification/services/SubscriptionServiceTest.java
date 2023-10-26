package com.ccallazans.matchnotification.notification.services;

import com.ccallazans.matchnotification.exceptions.ValidationException;
import com.ccallazans.matchnotification.notification.entity.Topic;
import com.ccallazans.matchnotification.notification.repository.SubscriptionRepository;
import com.ccallazans.matchnotification.notification.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private TopicRepository topicRepository;

    private SubscriptionService subscriptionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subscriptionService = new SubscriptionService(subscriptionRepository, topicRepository);
    }

    @Test
    void shouldThrowValidationExceptionForEmptyTopicList() {
        List<String> emptyTopics = new ArrayList<>();

        var exception = assertThrows(ValidationException.class, () -> subscriptionService.subscribe("example@email.com", emptyTopics));
        assertEquals("Empty topic list!", exception.getMessage());
    }

    @Test
    void shouldThrowsValidationExceptionForInvalidTopic() {
        List<String> invalidTopics = new ArrayList<>();
        invalidTopics.add("invalidTopic");

        try {
            subscriptionService.subscribe("example@email.com", invalidTopics);
        } catch (ValidationException e) {
            assertEquals("Topic is not valid: INVALIDTOPIC", e.getMessage());
        }
    }

    @Test
    void shouldThrowsValidationExceptionForInvalidEmail() {
        List<String> validTopics = new ArrayList<>();
        validTopics.add("validTopic");


        var exception = assertThrows(ValidationException.class, () ->
                subscriptionService.subscribe("invalidEmail", validTopics));

        assertEquals("Email is not valid: invalidEmail", exception.getMessage());

    }

    @Test
    void shouldThrowsValidationExceptionForExistingEmail() {
        List<String> validTopics = new ArrayList<>();
        validTopics.add("validTopic");

        when(topicRepository.findByName(any())).thenReturn(Topic.builder().build());
        when(subscriptionRepository.existsByEmail(anyString())).thenReturn(true);

        var exception = assertThrows(ValidationException.class, () ->
                subscriptionService.subscribe("example@email.com", validTopics));

        assertEquals("Email already exists: example@email.com", exception.getMessage());
    }

    @Test
    void shouldCreatesAndReturnsValidSubscription() {
        List<String> validTopics = new ArrayList<>();
        validTopics.add("validTopic");

        when(topicRepository.findByName(any()))
                .thenReturn(Topic.builder()
                        .name("validTopic")
                        .build());
        when(subscriptionRepository.existsByEmail(anyString())).thenReturn(false);

        var subscription = subscriptionService.subscribe("example@email.com", validTopics);

        assertNotNull(subscription);
        assertEquals("example@email.com", subscription.getEmail());
        assertEquals(validTopics.get(0), subscription.getTopics().stream().findFirst().get().getName());
    }
}